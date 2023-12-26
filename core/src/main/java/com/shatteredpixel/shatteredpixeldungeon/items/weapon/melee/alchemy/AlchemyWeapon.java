package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;

import java.util.ArrayList;

public interface AlchemyWeapon {
    ArrayList<Class<?extends Item>> weaponRecipe();
}
