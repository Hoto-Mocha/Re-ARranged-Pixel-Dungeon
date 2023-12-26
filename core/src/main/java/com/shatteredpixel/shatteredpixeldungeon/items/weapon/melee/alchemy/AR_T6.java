package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Evolution;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.UpgradeDust;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.AR.AR;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.AR.AR_T5;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;
import java.util.Arrays;

public class AR_T6 extends AR implements AlchemyWeapon {
    {
        image = ItemSpriteSheet.AR_T6;

        tier = 6;
    }

    @Override
    public ArrayList<Class<?extends Item>> weaponRecipe() {
        return new ArrayList<>(Arrays.asList(AR_T5.class, UpgradeDust.class, Evolution.class));
    }

}
