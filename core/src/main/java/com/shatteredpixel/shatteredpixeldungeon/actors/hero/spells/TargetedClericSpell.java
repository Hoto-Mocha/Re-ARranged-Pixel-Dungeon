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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Daze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Dread;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Drowsy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SoulMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HolyTome;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.utils.Random;

import java.util.HashMap;

public abstract class TargetedClericSpell extends ClericSpell {

	@Override
	public void onCast(HolyTome tome, Hero hero ){
		GameScene.selectCell(new CellSelector.Listener() {
			@Override
			public void onSelect(Integer cell) {
				onTargetSelected(tome, hero, cell);
			}

			@Override
			public String prompt() {
				return targetingPrompt();
			}
		});
	}

	@Override
	public int targetingFlags(){
		return Ballistica.MAGIC_BOLT;
	}

	protected String targetingPrompt(){
		return Messages.get(this, "prompt");
	}

	protected abstract void onTargetSelected(HolyTome tome, Hero hero, Integer target);

	private static final HashMap<Class<? extends FlavourBuff>, Float> DEBUFFS = new HashMap<>();
	static {
		DEBUFFS.put(Cripple.class,        1f);
		DEBUFFS.put(Blindness.class,      1f);
		DEBUFFS.put(Terror.class,         1f);
		DEBUFFS.put(Chill.class,          1f);
		DEBUFFS.put(Roots.class,          1f);
		DEBUFFS.put(Vertigo.class,        1f);
		DEBUFFS.put(Amok.class,           1f);
		DEBUFFS.put(Daze.class,           1f);
		DEBUFFS.put(Frost.class,          1f);
	}

	protected void onEnchanterSpellCast(Char target, float spellCost) {
		int chargeUse = (int)spellCost;
		if (target.alignment == Char.Alignment.ENEMY) {
			for (int i = 0; i < chargeUse; i++) {
				//do not consider buffs which are already assigned, or that the enemy is immune to.
				HashMap<Class<? extends FlavourBuff>, Float> debuffs = new HashMap<>(DEBUFFS);
				for (Buff existing : target.buffs()){
					if (existing instanceof FlavourBuff && debuffs.containsKey(existing.getClass())) {
						debuffs.put(((FlavourBuff) existing).getClass(), 0f);
					}
				}
				for (Class<?extends FlavourBuff> toAssign : debuffs.keySet()){
					if (debuffs.get(toAssign) > 0 && target.isImmune(toAssign)){
						debuffs.put(toAssign, 0f);
					}
				}

				//all buffs with a > 0 chance are flavor buffs
				Class<?extends FlavourBuff> debuffCls = Random.chances(debuffs);

				if (debuffCls != null){
					Buff.append(target, debuffCls, 5);
				} else {
					Buff.affect(target, Doom.class);
				}
			}
		}
	}

}
