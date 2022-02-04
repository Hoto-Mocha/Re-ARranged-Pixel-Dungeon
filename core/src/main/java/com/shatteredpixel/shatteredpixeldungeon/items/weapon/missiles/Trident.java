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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Ballista;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Trident extends MissileWeapon {
	
	{
		image = ItemSpriteSheet.TRIDENT;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 0.9f;
		
		tier = 5;
	}

	private static Ballista bow;

	@Override
	public int min(int lvl) {
		if (bow != null) {
			return  4 * tier +
					bow.buffedLvl() +
					2*lvl;
		} else {
			return  2 * tier +
					2*lvl;
		}
	}

	@Override
	public int max(int lvl) {
		if (bow != null) {
			return  7 * tier +
					bow.buffedLvl()*(tier+1) +
					tier*lvl;
		} else {
			return  5 * tier +
					tier*lvl;
		}
	}

	private void updateBallista(){
		if (Dungeon.hero.belongings.weapon() instanceof Ballista){
			bow = (Ballista) Dungeon.hero.belongings.weapon();
		} else {
			bow = null;
		}
	}

	public boolean ballistaHasEnchant( Char owner ){
		return (bow != null && bow.enchantment != null && owner.buff(MagicImmune.class) == null);
	}

	@Override
	public boolean hasEnchant(Class<? extends Enchantment> type, Char owner) {
		if (bow != null && bow.hasEnchant(type, owner)){
			return true;
		} else {
			return super.hasEnchant(type, owner);
		}
	}

	@Override
	public int throwPos(Hero user, int dst) {
		updateBallista();
		return super.throwPos(user, dst);
	}

	@Override
	protected void onThrow(int cell) {
		updateBallista();
		super.onThrow(cell);
	}

	@Override
	public void throwSound() {
		updateBallista();
		if (bow != null) {
			Sample.INSTANCE.play(Assets.Sounds.ATK_CROSSBOW, 1, Random.Float(0.87f, 1.15f));
		} else {
			super.throwSound();
		}
	}

	@Override
	public String info() {
		updateBallista();
		if (bow != null && !bow.isIdentified()){
			int level = bow.level();
			//temporarily sets the level of the bow to 0 for IDing purposes
			bow.level(0);
			String info = super.info();
			bow.level(level);
			return info;
		} else {
			return super.info();
		}
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		if (bow != null){
			damage = bow.proc(attacker, defender, damage);
		}
		return super.proc(attacker, defender, damage);
	}
	
}
