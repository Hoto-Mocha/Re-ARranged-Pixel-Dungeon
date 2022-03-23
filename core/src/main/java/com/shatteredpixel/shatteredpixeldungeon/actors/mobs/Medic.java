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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.depth;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EvasiveMove;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MedicSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ResearcherSprite;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

public class Medic extends Mob {

	{
		spriteClass = MedicSprite.class;

		HP = HT = 80;
		defenseSkill = 15;
		viewDistance = Light.DISTANCE;

		EXP = 27;
		maxLvl = 30;

		loot = new PotionOfHealing();
		lootChance = 0.2f; //see createloot
	}

	@Override
	public float attackDelay() {
		return super.attackDelay()*0.5f;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 10, 20 );
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (mob.alignment == Alignment.ENEMY && Dungeon.level.distance(this.pos, mob.pos) <= 6) {
				int healAmt = 2*(7-Dungeon.level.distance(pos, mob.pos)); //different per each distance
				healAmt = Math.min( healAmt, mob.HT - mob.HP );
				if (healAmt > 0 && mob.isAlive()) {
					mob.HP += healAmt;
					if (Dungeon.level.heroFOV[mob.pos]) {
						mob.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 2 );
						mob.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
					}
				}
			}
		} //heals nearby enemies and herself per every attack

		return damage;
	}

	@Override
	public void rollToDropLoot() {
		lootChance *= ((6f - Dungeon.LimitedDrops.MEDIC_HP.count) / 6f);
		super.rollToDropLoot();
	}

	@Override
	public Item createLoot(){
		if (depth == 30) {
			return null;
		} else {
			Dungeon.LimitedDrops.MEDIC_HP.count++;
			return super.createLoot();
		}
	}

	@Override
	public int attackSkill( Char target ) {
		return 40;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 15);
	}

}
