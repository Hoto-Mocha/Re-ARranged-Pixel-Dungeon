package com.shatteredpixel.shatteredpixeldungeon.items.spells;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Shocking;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class ElectricityImbue extends Spell {
    {
        image = ItemSpriteSheet.ELECTRICITY_IMBUE;

        talentChance = 1/(float) Recipe.OUT_QUANTITY;
    }

    @Override
    protected void onCast(Hero hero) {
        Buff.affect(hero, ElectricityImbueBuff.class).set(ElectricityImbueBuff.DURATION);
    }

    @Override
    public int value() {
        //prices of ingredients, divided by output quantity
        return Math.round(quantity * (30 /(float) Recipe.OUT_QUANTITY));
    }

    public static class ElectricityImbueBuff extends FlavourBuff {
        {
            type = buffType.POSITIVE;
            announced = true;
        }

        public static final float DURATION	= 20f;

        protected float left;

        private static final String LEFT	= "left";

        @Override
        public void storeInBundle( Bundle bundle ) {
            super.storeInBundle( bundle );
            bundle.put( LEFT, left );

        }

        @Override
        public void restoreFromBundle( Bundle bundle ) {
            super.restoreFromBundle( bundle );
            left = bundle.getFloat( LEFT );
        }

        public void set( float duration ) {
            this.left = duration;
        }

        @Override
        public boolean act() {
            spend(TICK);
            left -= TICK;
            if (left <= 0){
                detach();
            }

            return true;
        }

        public void proc(Char enemy, int damage){
            target.sprite.flash();
            enemy.sprite.flash();
            ArrayList<Lightning.Arc> arcs = new ArrayList<>();
            ArrayList<Char> affected = new ArrayList<>();
            affected.clear();
            arcs.clear();

            Shocking.arc(target, enemy, 2, affected, arcs);

            affected.remove(enemy); //defender isn't hurt by lightning
            for (Char ch : affected) {
                if (ch.alignment != target.alignment) {
                    ch.damage(Math.round(damage * 0.4f), target);
                }
            }
        }

        @Override
        public int icon() {
            return BuffIndicator.IMBUE;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0xFDFA31);
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, (DURATION - left) / DURATION);
        }

        @Override
        public String iconTextDisplay() {
            return Integer.toString((int)left);
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", dispTurns(left));
        }

        {
            immunities.add( ElectricityImbue.class );
            immunities.add( Paralysis.class );
        }

        @Override
        public boolean attachTo(Char target) {
            if (super.attachTo(target)){
                Buff.detach(target, Paralysis.class);
                return true;
            } else {
                return false;
            }
        }
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        private static final int OUT_QUANTITY = 3;

        {
            inputs =  new Class[]{ScrollOfRecharging.class};
            inQuantity = new int[]{1};

            cost = 10;

            output = ElectricityImbue.class;
            outQuantity = 3;
        }

    }
}
