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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.items.APBullet;
import com.shatteredpixel.shatteredpixeldungeon.items.HPBullet;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Evolution;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class SharpKatana extends MeleeWeapon {

    {
        image = ItemSpriteSheet.SHARP_KATANA;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1.3f;

        tier = 6;
    }

    @Override
    public int min(int lvl) {
        return 1;
    }

    @Override
    public int max(int lvl) {
        return  5*(tier+2) +
                lvl*(tier+2);
    }
}
