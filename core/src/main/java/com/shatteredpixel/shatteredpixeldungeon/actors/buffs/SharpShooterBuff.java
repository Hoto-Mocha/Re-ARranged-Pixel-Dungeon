package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BulletParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.bow.BowWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
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
        return "";
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

    private int randomDirection() {
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
        hero.next();
        try {
            return Random.element(candidates);
        } catch (NullPointerException e) {
            return -1;
        }
    }

    private void randomlyShootBullet(Gun gun, float delay, boolean isLast) {
        int direction = randomDirection();
        if (direction == -1 || gun.round() <= 0) {
            hero.next();
            return;
        }

        hero.sprite.parent.add(new Tweener(hero.sprite.parent, delay) {
            @Override
            protected void updateValues(float progress) {
                hero.busy();
            }

            @Override
            protected void onComplete() {
                super.onComplete();
                Gun.Bullet bullet = gun.knockBullet();
                bullet.cast(hero, direction, true, 0, new Callback() {
                    @Override
                    public void call() {
                        if (isLast) hero.spendAndNext(bullet.castDelay(hero, direction));
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
            hero.next();
            return;
        }

        hero.sprite.parent.add(new Tweener(hero.sprite.parent, delay) {
            @Override
            protected void updateValues(float progress) {
                hero.busy();
            }

            @Override
            protected void onComplete() {
                super.onComplete();
                BowWeapon.Arrow arrow = bow.knockArrow();
                arrow.cast(hero, direction, true, 0, new Callback() {
                    @Override
                    public void call() {
                        if (isLast) hero.spendAndNext(arrow.castDelay(hero, direction));
                    }
                });
                arrow.throwSound();
                Dungeon.hero.sprite.zap(direction);
            }
        });
    }

    private void bulletBurst(Gun gun, float delay, final int direction, boolean isLast) {
        if (direction == -1 || gun.round() <= 0) {
            hero.next();
            return;
        }

        hero.sprite.parent.add(new Tweener(hero.sprite.parent, delay) {
            @Override
            protected void updateValues(float progress) {
                hero.busy();
            }

            @Override
            protected void onComplete() {
                super.onComplete();
                Gun.Bullet bullet = gun.knockBullet();
                bullet.cast(hero, direction, true, 0, new Callback() {
                    @Override
                    public void call() {
                        if (isLast) hero.spendAndNext(bullet.castDelay(hero, direction));
                    }
                });
                bullet.throwSound();
                Dungeon.hero.sprite.zap(direction);
            }
        });
    }

    private void arrowBurst(BowWeapon bow, float delay, final int direction, boolean isLast) {
        if (direction == -1 || Dungeon.bullet <= 0) {
            hero.next();
            return;
        }

        hero.sprite.parent.add(new Tweener(hero.sprite.parent, delay) {
            @Override
            protected void updateValues(float progress) {
                hero.busy();
            }

            @Override
            protected void onComplete() {
                super.onComplete();
                BowWeapon.Arrow arrow = bow.knockArrow();
                arrow.cast(hero, direction, true, 0, new Callback() {
                    @Override
                    public void call() {
                        if (isLast) hero.spendAndNext(arrow.castDelay(hero, direction));
                    }
                });
                arrow.throwSound();
                Dungeon.hero.sprite.zap(direction);
            }
        });
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
        }

        @Override
        public String prompt() {
            return Messages.get(SpiritBow.class, "prompt");
        }
    };
}
