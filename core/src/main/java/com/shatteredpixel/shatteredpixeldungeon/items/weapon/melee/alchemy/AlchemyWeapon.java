package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.UpgradeDust;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public interface AlchemyWeapon {
    ArrayList<Class<?extends Item>> weaponRecipe();

    static String hintString(ArrayList<Class<?extends Item>> recipe) {
        if (recipe.get(1).isAssignableFrom(UpgradeDust.class)) {
            return Messages.get(Item.class, "discover_hint_alchemy_one", Reflection.newInstance(recipe.get(0)).name());
        } else {
            return Messages.get(Item.class, "discover_hint_alchemy_two", Reflection.newInstance(recipe.get(0)).name(), Reflection.newInstance(recipe.get(1)).name());
        }
    }

    /*
    If a class implements this interface, you should override the following method with this for catalog item hint:

    @Override
    public String discoverHint() {
        return AlchemyWeapon.hintString(weaponRecipe());
    }
    */
}
