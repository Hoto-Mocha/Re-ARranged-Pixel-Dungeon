package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BulletParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.bow.BowWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.DisposableMissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.Tweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SharpShooterBuff extends Buff implements ActionIndicator.Action {

    {
        type = buffType.NEUTRAL;
        revivePersists = true;
    }

    @Override
    public boolean attachTo(Char target) {
        if (!(target instanceof Hero)) return false;

        ActionIndicator.setAction(this);
        return super.attachTo(target);
    }

    @Override
    public void detach() {
        ActionIndicator.clearAction();
        super.detach();
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        ActionIndicator.setAction(this);
    }

    @Override
    public String actionName() {
        return Messages.get(this, "action_name");
    }

    @Override
    public int actionIcon() {
        return HeroIcon.SHARPSHOOTING;
    }

    @Override
    public int indicatorColor() {
        return 0x1F1F1F;
    }

    @Override
    public void doAction() {
        GameScene.selectCell(listener);
    }

    private static int randomDirection() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (Char ch : Actor.chars()) {
            if (ch.alignment != Char.Alignment.ENEMY) continue;
            if (!Dungeon.level.heroFOV[ch.pos]) continue;

            int cell = ch.pos;
            Ballistica aim = new Ballistica(Dungeon.hero.pos, cell, Ballistica.MAGIC_BOLT);
            int destination = aim.collisionPos;
            if (cell == destination) {
                candidates.add(cell);
            }
        }
        Dungeon.hero.next();
        try {
            return Random.element(candidates);
        } catch (NullPointerException e) {
            return -1;
        }
    }

    private void randomlyShootBullet(Gun gun, float delay, boolean isLast) {
        int direction = randomDirection();
        if (direction == -1 || gun.round() <= 0) {
            Dungeon.hero.next();
            return;
        }

        Dungeon.hero.sprite.parent.add(new Tweener(Dungeon.hero.sprite.parent, delay) {
            @Override
            protected void updateValues(float progress) {
                Dungeon.hero.busy();
            }

            @Override
            protected void onComplete() {
                super.onComplete();
                Gun.Bullet bullet = gun.knockBullet();
                bullet.isBurst = true;
                bullet.cast(Dungeon.hero, direction, true, 0, new Callback() {
                    @Override
                    public void call() {
                        if (isLast) {
                            Dungeon.hero.spendAndNext(bullet.castDelay(Dungeon.hero, direction));
                            if (Dungeon.hero.hasTalent(Talent.PERFECT_SHOT)) {
                                gun.manualReload(Dungeon.hero.pointsInTalent(Talent.PERFECT_SHOT), false);
                            }
                        }
                    }
                });
                CellEmitter.heroCenter(Dungeon.hero.pos).burst(BulletParticle.factory(DungeonTilemap.tileCenterToWorld(direction)), 10);
                bullet.throwSound();
                Dungeon.hero.sprite.zap(direction);
            }
        });
    }

    private void randomlyShootArrow(BowWeapon bow, float delay, boolean isLast) {
        int direction = randomDirection();
        if (direction == -1 || Dungeon.bullet <= 0) {
            Dungeon.hero.next();
            return;
        }

        Dungeon.hero.sprite.parent.add(new Tweener(Dungeon.hero.sprite.parent, delay) {
            @Override
            protected void updateValues(float progress) {
                Dungeon.hero.busy();
            }

            @Override
            protected void onComplete() {
                super.onComplete();
                BowWeapon.Arrow arrow = bow.knockArrow();
                arrow.isBurst = true;
                arrow.cast(Dungeon.hero, direction, true, 0, new Callback() {
                    @Override
                    public void call() {
                        if (isLast) Dungeon.hero.spendAndNext(arrow.castDelay(Dungeon.hero, direction));
                    }
                });
                arrow.throwSound();
                Dungeon.hero.sprite.zap(direction);
            }
        });
    }

    private void bulletBurst(Gun gun, float delay, final int direction, boolean isLast) {
        if (direction == -1 || gun.round() <= 0) {
            Dungeon.hero.next();
            return;
        }

        Dungeon.hero.sprite.parent.add(new Tweener(Dungeon.hero.sprite.parent, delay) {
            @Override
            protected void updateValues(float progress) {
                Dungeon.hero.busy();
            }

            @Override
            protected void onComplete() {
                super.onComplete();
                Gun.Bullet bullet = gun.knockBullet();
                bullet.isBurst = true;
                bullet.cast(Dungeon.hero, direction, true, 0, new Callback() {
                    @Override
                    public void call() {
                        if (isLast) {
                            Dungeon.hero.spendAndNext(bullet.castDelay(Dungeon.hero, direction));
                            if (Dungeon.hero.hasTalent(Talent.PERFECT_SHOT)) {
                                gun.manualReload(Dungeon.hero.pointsInTalent(Talent.PERFECT_SHOT), false);
                            }
                        }
                    }
                });
                bullet.throwSound();
                Dungeon.hero.sprite.zap(direction);
            }
        });
    }

    private void arrowBurst(BowWeapon bow, float delay, final int direction, boolean isLast) {
        if (direction == -1 || Dungeon.bullet <= 0) {
            Dungeon.hero.next();
            return;
        }

        Dungeon.hero.sprite.parent.add(new Tweener(Dungeon.hero.sprite.parent, delay) {
            @Override
            protected void updateValues(float progress) {
                Dungeon.hero.busy();
            }

            @Override
            protected void onComplete() {
                super.onComplete();
                BowWeapon.Arrow arrow = bow.knockArrow();
                arrow.isBurst = true;
                arrow.cast(Dungeon.hero, direction, true, 0, new Callback() {
                    @Override
                    public void call() {
                        if (isLast) Dungeon.hero.spendAndNext(arrow.castDelay(Dungeon.hero, direction));
                    }
                });
                arrow.throwSound();
                Dungeon.hero.sprite.zap(direction);
            }
        });
    }

    public static void arrowStorm(Hero hero) {
        if (Random.Float() > hero.pointsInTalent(Talent.ARROW_STORM)/3f) return;

        int direction = randomDirection();
        if (direction == -1) return;

        LightArrow lightArrow = new LightArrow();
        lightArrow.cast(hero, direction, false, 0, null);
        lightArrow.throwSound();
    }

    public static class LightArrow extends DisposableMissileWeapon {
        {
            image = ItemSpriteSheet.LIGHT_ARROW;
            hitSound = Assets.Sounds.HIT_ARROW;
        }

        @Override
        public int min() {
            return 5;
        }

        @Override
        public int max() {
            return 10;
        }

        @Override
        public void throwSound() {
            Sample.INSTANCE.play( Assets.Sounds.ATK_SPIRITBOW, 1, Random.Float(0.87f, 1.15f) );
        }
    }

    CellSelector.Listener listener = new CellSelector.Listener() {
        @Override
        public void onSelect(Integer cell) {
            if (cell == null) return;

            KindOfWeapon weapon = Dungeon.hero.belongings.weapon();
            if (weapon == null) return;

            int shots = 3;
            float delay = 0.05f;

            if (weapon instanceof Gun) {
                if (((Gun) weapon).round() < shots) shots = ((Gun) weapon).round();
                if (shots <= 0) return;

                if (cell == Dungeon.hero.pos) {
                    for (int i = 0; i < shots; i++) {
                        randomlyShootBullet((Gun) weapon, i*delay, i == shots-1);
                    }
                } else {
                    for (int i = 0; i < shots; i++) {
                        bulletBurst((Gun) weapon, i*delay, cell, i == shots-1);
                    }
                }
            } else if (weapon instanceof BowWeapon) {
                if (Dungeon.bullet < shots) shots = Dungeon.bullet;
                if (shots <= 0) return;

                if (cell == Dungeon.hero.pos) {
                    for (int i = 0; i < shots; i++) {
                        randomlyShootArrow((BowWeapon) weapon, i*delay, i == shots-1);
                    }
                } else {
                    for (int i = 0; i < shots; i++) {
                        arrowBurst((BowWeapon) weapon, i*delay, cell, i == shots-1);
                    }
                }
            }

            Buff.affect(target, SharpShootingCoolDown.class);
            detach();
        }

        @Override
        public String prompt() {
            return Messages.get(SpiritBow.class, "prompt");
        }
    };

    public static void kill() {
        if (hero.buff(SharpShooterBuff.SharpShootingCoolDown.class) != null) {
            hero.buff(SharpShooterBuff.SharpShootingCoolDown.class).kill();
        }
    }

    public static class SharpShootingCoolDown extends Buff {
        {
            type = buffType.NEUTRAL;
        }

        int shoot = 0;

        private static final String SHOOT = "shoot";

        private int maxShoot() {
            return 9 - 2 * Dungeon.hero.pointsInTalent(Talent.FOCUS_SHOT);
        }

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);

            bundle.put(SHOOT, shoot);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);

            shoot = bundle.getInt(SHOOT);
        }

        @Override
        public int icon() {
            return BuffIndicator.TIME;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0x1F1F1F);
        }

        @Override
        public float iconFadePercent() {
            if (!Dungeon.hero.hasTalent(Talent.FOCUS_SHOT)) return super.iconFadePercent();

            int max = maxShoot();
            return Math.max(0, (max-shoot)/(float)max);
        }

        @Override
        public String iconTextDisplay() {
            if (!Dungeon.hero.hasTalent(Talent.FOCUS_SHOT)) return super.iconTextDisplay();

            return Integer.toString(maxShoot());
        }

        @Override
        public String desc() {
            if (!Dungeon.hero.hasTalent(Talent.FOCUS_SHOT)) return super.desc();

            return Messages.get(this, "alt_desc", maxShoot()-shoot);
        }

        public void kill() {
            Buff.affect(target, SharpShooterBuff.class);
            detach();
        }

        public void hit() {
            if (!Dungeon.hero.hasTalent(Talent.FOCUS_SHOT)) return;

            shoot++;
            if (shoot >= 9 - 2 * Dungeon.hero.pointsInTalent(Talent.FOCUS_SHOT)) {
                kill();
            }
        }

        public static void missileHit(Char attacker) {
            if (attacker == Dungeon.hero
                    && attacker.buff(SharpShooterBuff.SharpShootingCoolDown.class) != null
                    && ((Hero) attacker).hasTalent(Talent.FOCUS_SHOT)) {
                attacker.buff(SharpShooterBuff.SharpShootingCoolDown.class).hit();
            }
        }
    }
}
