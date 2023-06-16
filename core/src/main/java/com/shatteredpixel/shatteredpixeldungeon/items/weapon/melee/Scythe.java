/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2022 Evan Debenham
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
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Scythe extends MeleeWeapon {
	
	{
		image = ItemSpriteSheet.SCYTHE;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 0.8f;

		tier = 4;
		DLY = 1.5f; //0.67x speed
	}

	@Override
	public int max(int lvl) {
		return  Math.round(6.67f*(tier+1)) +
				lvl*Math.round(1.33f*(tier+1));
	}

	@Override
	public float abilityChargeUse(Hero hero, Char target) {
		return 3*super.abilityChargeUse(hero, target);
	}

	@Override
	public int proc( Char attacker, Char defender, int damage ) {
		if (Dungeon.hero.subClass == HeroSubClass.WEAPONMASTER && defender.alignment != Char.Alignment.ALLY) {
			Buff.affect( defender, Cripple.class, 2+buffedLvl() );
		}
		Buff.affect(defender, Bleeding.class).set(Math.round(damage*0.1f));
		return super.proc( attacker, defender, damage );
	}

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		Knife.knifeAbility(hero, target, 0.33f, this);
	}
}
