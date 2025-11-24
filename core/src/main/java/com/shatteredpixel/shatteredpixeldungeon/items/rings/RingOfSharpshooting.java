/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2025 Evan Debenham
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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PinCushion;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.TelekineticGrab;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class RingOfSharpshooting extends Ring {

	{
		icon = ItemSpriteSheet.Icons.RING_SHARPSHOOT;
		buffClass = Aim.class;
	}

	public String statsInfo() {
		if (isIdentified()){
			String info = Messages.get(this, "stats",
					soloBuffedBonus(), Messages.decimalFormat("#.##", 100f * (Math.pow(1.2, soloBonus()) - 1f)));
			if (isEquipped(Dungeon.hero) && soloBuffedBonus() != combinedBuffedBonus(Dungeon.hero)){
				info += "\n\n" + Messages.get(this, "combined_stats",
						combinedBuffedBonus(Dungeon.hero), Messages.decimalFormat("#.##", 100f * (Math.pow(1.2, combinedBonus(Dungeon.hero)) - 1f)));
			}
			return info;
		} else {
			return Messages.get(this, "typical_stats", 1, Messages.decimalFormat("#.##", 20f));
		}
	}

	@Override
	public String upgradeStat1(int level) {
		if (cursed && cursedKnown) level = Math.min(-1, level-3);
		return Integer.toString(level+1);
	}

	@Override
	public String upgradeStat2(int level) {
		if (cursed && cursedKnown) level = Math.min(-1, level-3);
		return Messages.decimalFormat("#.##", 100f * (Math.pow(1.2, level+1)-1f)) + "%";
	}
	
	@Override
	public RingBuff buff() {
		return new Aim();
	}
	
	public static int levelDamageBonus( Char target ){
		return getBuffedBonus(target, RingOfSharpshooting.Aim.class);
	}
	
	public static float durabilityMultiplier( Char target ){
		return (float)(Math.pow(1.2, getBonus(target, Aim.class)));
	}

	public class Aim extends RingBuff {
	}

    @Override
    public int onHit(Hero hero, Char enemy, int damage) {
        if (enemy.buff(PinCushion.class) != null) {
            Item item = enemy.buff(PinCushion.class).grabOne();
            if (item.doPickUp(hero, enemy.pos)) {
                hero.spend(-Item.TIME_TO_PICK_UP); //casting the spell already takes a turn
                GLog.i( Messages.capitalize(Messages.get(hero, "you_now_have", item.name())) );
            } else {
                GLog.w(Messages.get(TelekineticGrab.class, "cant_grab"));
                Dungeon.level.drop(item, enemy.pos).sprite.drop();
            }
            WandOfBlastWave.throwChar(enemy, new Ballistica(hero.pos, enemy.pos, Ballistica.MAGIC_BOLT), 2, true, false, hero);
        }

        return damage;
    }
}
