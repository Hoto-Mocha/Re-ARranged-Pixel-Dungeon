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

package com.shatteredpixel.shatteredpixeldungeon.items.spells;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.ArcaneResin;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class ScrollOfExtract extends InventorySpell {

    {
        image = ItemSpriteSheet.EXOTIC_PLUS;
        icon = ItemSpriteSheet.Icons.POTION_MASTERY;

        unique = true;
        bones = false;
    }

    @Override
    protected boolean usableOnItem(Item item) {
        if (item.isEquipped(Dungeon.hero) || item.unique) {
            return false;
        }
        if ((item instanceof MeleeWeapon && isIdentified() && item.buffedLvl() > 0) || (item instanceof MissileWeapon && item.buffedLvl() > 0)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onItemSelected(Item item) {

        Item result = changeItem(item);

        if (result == null){
            //This shouldn't ever trigger
            GLog.n( Messages.get(this, "nothing") );
            curItem.collect( curUser.belongings.backpack );
        } else {
            if (!result.collect()){
                Dungeon.level.drop(result, curUser.pos).sprite.drop();
            }
            if (result.isIdentified()){
                Catalog.setSeen(result.getClass());
            }
            Dungeon.hero.sprite.emitter().start( Speck.factory( Speck.UP ), 0.2f, 3 );
            GLog.p( Messages.get(this, "extract") );
        }

    }

    public static Item changeItem( Item item ){
        if (item instanceof MeleeWeapon || item instanceof MissileWeapon) {
            item.detach(Dungeon.hero.belongings.backpack);
            return extractWeapon((Weapon) item);
        } else {
            return null;
        }
    }

    private static Item extractWeapon( Weapon w ) {
        Item n;

        int level = w.level();
        if (w.curseInfusionBonus) level--;

        n = new ScrollOfAlchemy().quantity(level);

        return n;
    }

    @Override
    public int value() {
        return quantity * 220;
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{ArcaneCatalyst.class, ArcaneResin.class};
            inQuantity = new int[]{1, 6};

            cost = 10;

            output = ScrollOfExtract.class;
            outQuantity = 1;
        }
    }
}
