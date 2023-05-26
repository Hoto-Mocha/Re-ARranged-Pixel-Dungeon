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

package com.shatteredpixel.shatteredpixeldungeon.items.rings;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.text.DecimalFormat;

public class RingOfReload extends Ring {

    {
        icon = ItemSpriteSheet.Icons.RING_RELOAD;
    }

    public String statsInfo() {
        if (isIdentified()){
            return Messages.get(this, "stats", new DecimalFormat("#.##").format(100f * (1f - Math.pow(0.825f, soloBuffedBonus()))));
        } else {
            return Messages.get(this, "typical_stats", new DecimalFormat("#.##").format(17.5f));
        }
    }

    @Override
    protected RingBuff buff() {
            return new Reload();
    }


    public static float reloadMultiplier( Char target ){
        float speed = (float)Math.pow(0.825f, getBuffedBonus(target, RingOfReload.Reload.class));
        if (Dungeon.hero.hasTalent(Talent.FAST_RELOAD) && Dungeon.hero.belongings.weapon != null) {
            speed *= Math.pow((1 - 0.05f * Dungeon.hero.pointsInTalent(Talent.FAST_RELOAD)), Math.max(0, (Dungeon.hero.STR() - Dungeon.hero.belongings.weapon.STRReq())));
        }
        if (Dungeon.hero.hasTalent(Talent.RELOAD_PRACTICE)) {
            speed *= 1+0.1*Dungeon.hero.pointsInTalent(Talent.RELOAD_PRACTICE);
        }
        return speed;
    }

    public class Reload extends RingBuff {
    }
}
