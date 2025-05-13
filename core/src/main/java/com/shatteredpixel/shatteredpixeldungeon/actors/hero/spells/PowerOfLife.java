package com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HolyTome;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.watabou.utils.Bundle;

public class PowerOfLife extends ClericSpell {
    public static final PowerOfLife INSTANCE = new PowerOfLife();

    @Override
    public int icon(){
        return HeroIcon.POWER_OF_LIFE;
    }

    @Override
    public float chargeUse(Hero hero) {
        return 2;
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", hero.HP/5, 2*(hero.HP/5), 20*hero.pointsInTalent(Talent.POWER_OF_LIFE)) + "\n\n" + Messages.get(this, "charge_cost", (int)chargeUse(hero));
    }

    @Override
    public boolean canCast(Hero hero) {
        return hero.HP >= 5;
    }

    @Override
    public void onCast(HolyTome tome, Hero hero) {
        hero.busy();
        hero.spendAndNext(Actor.TICK);
        hero.sprite.operate(hero.pos);

        int HPUse = hero.HP/5;
        hero.HP -= HPUse;

        PowerOfLifeBarrier b = Buff.affect(hero, PowerOfLifeBarrier.class);
        b.usedHP = HPUse;
        b.setShield(HPUse*2);

        onSpellCast(tome, hero);
    }

    public static class PowerOfLifeBarrier extends Barrier {
        {
            announced = true;
        }

        public int usedHP;

        public void proc(int damage) {
            int healAmt = Math.min(usedHP, Math.round(0.2f*hero.pointsInTalent(Talent.POWER_OF_LIFE)*damage));
            if (healAmt > 0) {
                target.heal(healAmt);
                usedHP -= healAmt;
            }
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", 20* hero.pointsInTalent(Talent.POWER_OF_LIFE), shielding(), usedHP);
        }

        private static final String USED_HP = "usedHP";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(USED_HP, usedHP);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            usedHP = bundle.getInt(USED_HP);
        }
    }
}
