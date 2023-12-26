package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Evolution;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.UpgradeDust;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.HG.HG;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.HG.HG_T5;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;
import java.util.Arrays;

public class HG_T6 extends HG implements AlchemyWeapon {
    {
        image = ItemSpriteSheet.HG_T6;

        tier = 6;
    }

    @Override
    public ArrayList<Class<?extends Item>> weaponRecipe() {
        return new ArrayList<>(Arrays.asList(HG_T5.class, UpgradeDust.class, Evolution.class));
    }

}
