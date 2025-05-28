package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.bow;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.ArrowItem;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.DisposableMissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Bow extends MeleeWeapon {
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
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_SHOOT)) {
            if (!isEquipped( hero )) {
                usesTargeting = false;
                GLog.w(Messages.get(this, "not_equipped"));
            } else {
                if (Dungeon.bullet <= 0) { //현재 탄창이 0이면 AC_RELOAD 버튼을 눌렀을 때처럼 작동
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
        @Override
        public int proc(Char attacker, Char defender, int damage) {
            return Bow.this.proc(attacker, defender, damage);
        }

        @Override
        public int buffedLvl(){
            return Bow.this.buffedLvl();
        }

        @Override
        public int damageRoll(Char owner) {
            int damage = arrowDamage();
            return damage;
        }

        @Override
        public boolean hasEnchant(Class<? extends Enchantment> type, Char owner) {
            return Bow.this.hasEnchant(type, owner);
        }

        @Override
        public float delayFactor(Char owner) {
            return Bow.this.delayFactor(owner);
        }

        @Override
        public float accuracyFactor(Char owner, Char target) {
            return Bow.this.accuracyFactor(owner, target);
        }

        @Override
        public int STRReq() {
            return Bow.this.STRReq();
        }

        @Override
        protected void onThrow(int cell) {
            Char enemy = Actor.findChar( cell );
            if (enemy != null) {
                if (curUser.shoot( enemy, this )) {
                    if (Random.Float() < 0.5f) {
                        Buff.affect(enemy, ArrowAttached.class).hit();
                    }
                }
            }

            if (enemy == null) {
                Dungeon.level.drop(new ArrowItem(), cell);
            }

            onShoot();
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

        int arrows = 0;

        public void hit() {
            arrows++;
        }

        private final String ARROWS = "arrows";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(ARROWS, arrows);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            arrows = bundle.getInt(ARROWS);
        }

        @Override
        public int icon() {
            return BuffIndicator.PINCUSHION;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0, 1f, 0);
        }

        @Override
        public void detach() {
            super.detach();
            Dungeon.level.drop(new ArrowItem().quantity(arrows), target.pos);
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", arrows);
        }
    }
}
