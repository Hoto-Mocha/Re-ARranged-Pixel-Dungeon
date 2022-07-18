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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.ArcaneResin;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FrostGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncherAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncherHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ParalysisGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SleepGun;
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
        if (item instanceof MeleeWeapon) {
            if ((((MeleeWeapon) item).hasCurseEnchant() || !isIdentified() || cursed || item.buffedLvl() <= 0)
                || (item instanceof SleepGun
                    || item instanceof FrostGun
                    || item instanceof ParalysisGun
                    || item instanceof GrenadeLauncher
                    || item instanceof GrenadeLauncherAP
                    || item instanceof GrenadeLauncherHP
                    || item instanceof MagesStaff)
            ) {
                return false;
            } else {
                return true;
            }
        } else if (item instanceof MissileWeapon) {
            if (buffedLvl() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    protected void onItemSelected(Item item) {

        Item result = changeItem(item);
        item.level(0);

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
            return extractWeapon((Weapon) item);
        } else {
            return null;
        }
    }

    private static Item extractWeapon( Weapon w ) {
        Item n;
        int level = Math.min(w.level(), 3);
        n = new ScrollOfAlchemy().quantity(level);

        return n;
    }

    @Override
    public int value() {
        return quantity * 300;
    }
}
