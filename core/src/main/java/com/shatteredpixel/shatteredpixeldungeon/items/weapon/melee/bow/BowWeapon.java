package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.bow;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.ArrowItem;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.DisposableMissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BowWeapon extends MeleeWeapon {
    public static final String AC_SHOOT		= "SHOOT";
    {
        defaultAction = AC_SHOOT;
        usesTargeting = true;
    }
    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (isEquipped( hero )) {
            actions.add(AC_SHOOT);
        }
        return actions;
    }

    @Override
    public int max(int lvl) {
        return tier()*2
                +lvl;
    }

    public int arrowMin() {
        return arrowMin(this.buffedLvl());
    }

    public int arrowMin(int lvl) {
        return tier()
                +lvl;
    }

    public int arrowMax(int lvl) {
        return 5*(tier()+1) +
                lvl*(tier()+1);
    }

    public int arrowMax() {
        return arrowMax(this.buffedLvl());
    }

    public int arrowDamage() {
        int damage = Random.NormalIntRange(arrowMin(), arrowMax());

        damage = augment.damageFactor(damage);  //증강에 따라 변화하는 효과
        return damage;
    }

    @Override
    public String info() {
        String info = super.info();
        //근접 무기의 설명을 모두 가져옴, 여기에서 할 것은 근접 무기의 설명에 추가로 생기는 문장을 더하는 것
        if (levelKnown) { //감정되어 있을 때
            info += "\n\n" + Messages.get(this, "bow_desc",
                    augment.damageFactor(arrowMin(buffedLvl())), augment.damageFactor(arrowMax(buffedLvl())));
        } else { //감정되어 있지 않을 때
            info += "\n\n" + Messages.get(this, "bow_typical_desc",
                    augment.damageFactor(arrowMin(0)), augment.damageFactor(arrowMax(0)));
        }

        return info;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_SHOOT)) {
            if (!isEquipped( hero )) {
                usesTargeting = false;
                GLog.w(Messages.get(this, "not_equipped"));
            } else {
                if (Dungeon.bullet <= 0) {
                    usesTargeting = false;
                    GLog.w(Messages.get(this, "no_arrow"));
                } else {
                    usesTargeting = true;
                    curUser = hero;
                    curItem = this;
                    GameScene.selectCell(shooter);
                }
            }
        }
    }

    public Arrow knockArrow(){
        return new Arrow();
    }

    public class Arrow extends DisposableMissileWeapon {
        {
            image = ItemSpriteSheet.NORMAL_ARROW;
        }

        @Override
        public int proc(Char attacker, Char defender, int damage) {
            return BowWeapon.this.proc(attacker, defender, damage);
        }

        @Override
        public int buffedLvl(){
            return BowWeapon.this.buffedLvl();
        }

        @Override
        public int damageRoll(Char owner) {
            int damage = arrowDamage();
            return damage;
        }

        @Override
        public boolean hasEnchant(Class<? extends Enchantment> type, Char owner) {
            return BowWeapon.this.hasEnchant(type, owner);
        }

        @Override
        public float delayFactor(Char owner) {
            return BowWeapon.this.delayFactor(owner);
        }

        @Override
        public float accuracyFactor(Char owner, Char target) {
            return BowWeapon.this.accuracyFactor(owner, target);
        }

        @Override
        public int STRReq() {
            return BowWeapon.this.STRReq();
        }

        @Override
        protected void onThrow(int cell) {
            Char enemy = Actor.findChar( cell );
            if (enemy != null) {
                if (curUser.shoot( enemy, this )) {
                    if (Random.Float() < 0.5f) {
                        if (enemy.isAlive()) {
                            Buff.affect(enemy, ArrowAttached.class).hit();
                        } else {
                            dropArrow(cell);
                        }
                    }
                } else {
                    dropArrow(cell);
                }
            }

            if (enemy == null) {
                dropArrow(cell);
            }

            onShoot();
        }

        public void dropArrow(int cell) {
            Dungeon.level.drop(new ArrowItem(), cell).sprite.drop(cell);
        }

        public void onShoot() {
            Dungeon.bullet--;

            updateQuickslot();
        }
    }

    private CellSelector.Listener shooter = new CellSelector.Listener() {
        @Override
        public void onSelect( Integer target ) {
            if (target != null) {
                if (target == curUser.pos) {
                    return;
                } else {
                    knockArrow().cast(curUser, target);
                }
            }
        }
        @Override
        public String prompt() {
            return Messages.get(SpiritBow.class, "prompt");
        }
    };

    public static class ArrowAttached extends Buff {
        {
            type = buffType.NEUTRAL;
        }

        public static final float DURATION = 50f;

        int arrows = 0;
        float left;

        public void hit() {
            arrows++;
            left = DURATION;

            if (arrows >= 10) {
                Badges.validateHedgehog();
            }
        }

        @Override
        public boolean act() {
            left-=TICK;
            spend(TICK);
            if (left <= 0) {
                detach();
            }
            return true;
        }

        private final String ARROWS = "arrows";
        private final String LEFT = "left";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(ARROWS, arrows);
            bundle.put(LEFT, left);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            arrows = bundle.getInt(ARROWS);
            left = bundle.getFloat(LEFT);
        }

        @Override
        public int icon() {
            return BuffIndicator.ARROW_ATTACHED;
        }

        @Override
        public float iconFadePercent() {
            return (DURATION-left)/DURATION;
        }

        @Override
        public void detach() {
            super.detach();
            Dungeon.level.drop(new ArrowItem().quantity(arrows), target.pos).sprite.drop(target.pos);
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", arrows, left);
        }
    }
}
