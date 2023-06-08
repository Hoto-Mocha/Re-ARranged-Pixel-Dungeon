/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class CrudePistol_Energy extends CrudePistol {
    
    {
        image = ItemSpriteSheet.CRUDE_PISTOL_ENERGY;

        tier = 1;

        bones = false;
    }

    @Override
    public int max(int lvl) {
        return 5 * (tier + 2) +
                lvl;
    }

    @Override
    public int Bulletmax(int lvl) {
        return 5 * (tier+2)   +
                lvl +
                RingOfSharpshooting.levelDamageBonus(hero);
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{CrudePistol.class, ScrollOfUpgrade.class, LiquidMetal.class};
            inQuantity = new int[]{1, 1, 10};

            cost = 8;

            output = CrudePistol_Energy.class;
            outQuantity = 1;
        }
    }

}