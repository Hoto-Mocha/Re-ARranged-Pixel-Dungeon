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

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpellBookCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

import java.util.ArrayList;

public class SpellBook extends MeleeWeapon {

	public static final String AC_READ		= "READ";

	{
		defaultAction = AC_READ;
		usesTargeting = false;

		alchemy = true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_READ);
		return actions;
	}

	@Override
	public int max(int lvl) {
		return  3*(tier+1) +    //12 base, down from 20
				lvl*(tier);     //+3 per level, down from +4
	}

	@Override
	public float abilityChargeUse(Hero hero) {
		return super.abilityChargeUse(hero);
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		if (hero.buff(SpellBookCoolDown.class) == null) {
			GLog.i(Messages.get(this, "no_buff"));
			return;
		}
		beforeAbilityUsed(hero);
		hero.buff(SpellBookCoolDown.class).detach();
		hero.sprite.operate(hero.pos);
		hero.next();
		afterAbilityUsed(hero);
	}

}
