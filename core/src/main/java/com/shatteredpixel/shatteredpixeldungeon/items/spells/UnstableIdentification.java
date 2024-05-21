package com.shatteredpixel.shatteredpixeldungeon.items.spells;

import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class UnstableIdentification extends InventorySpell {
    {
        image = ItemSpriteSheet.UNSTABLE_IDENTIFY;
        talentChance = 1/(float) Recipe.OUT_QUANTITY;
    }

    @Override
    protected boolean usableOnItem(Item item) {
        return ((item instanceof EquipableItem && !(item instanceof MissileWeapon)) || item instanceof Wand) && !item.isIdentified() && !item.cursedKnown;
    }

    @Override
    protected void onItemSelected(Item item) {
        item.cursedKnown = true;
        if (item.cursed) {
            GLog.p(Messages.get(this, "cursed"));
        } else {
            GLog.p(Messages.get(this, "uncursed"));
        }
        updateQuickslot();
    }

    @Override
    public int value() {
        //prices of ingredients, divided by output quantity
        return Math.round((40 + 30)/(float) Recipe.OUT_QUANTITY);
    }

    @Override
    public int energyVal() {
        return (int)(20 * (quantity/(float) Recipe.OUT_QUANTITY));
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        private static final int OUT_QUANTITY = 6;

        {
            inputs =  new Class[]{UnstableSpell.class, ScrollOfIdentify.class};
            inQuantity = new int[]{1, 1};

            cost = 6;

            output = UnstableIdentification.class;
            outQuantity = 6;
        }

    }
}
