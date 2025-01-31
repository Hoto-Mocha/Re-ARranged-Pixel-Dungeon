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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Evolution;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Elastic;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gauntlet;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ForceCube;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;

public class ForceGlove extends MeleeWeapon implements AlchemyWeapon {
	
	{
		image = ItemSpriteSheet.FORCE_GLOVE;
		hitSound = Assets.Sounds.HIT_CRUSH;
		hitSoundPitch = 1.2f;
		
		tier = 6;
		RCH = 2;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		int dmg = super.proc( attacker, defender, damage );
		if (Dungeon.level.adjacent( attacker.pos, defender.pos )) {
			dmg = Math.round(damage*0.5f);
			//trace a ballistica to our target (which will also extend past them
			Ballistica trajectory = new Ballistica(attacker.pos, defender.pos, Ballistica.STOP_TARGET);
			//trim it to just be the part that goes past them
			trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size()-1), Ballistica.PROJECTILE);
			//knock them back along that ballistica
			WandOfBlastWave.throwChar(defender, trajectory, 20, true, true, Dungeon.hero.getClass());
		}
		return dmg;
	}
	@Override
	public int max(int lvl) {
		return  5*(tier) +    //base
				lvl*(tier);   //level scaling
	}

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		forceAbility(hero, target, this);
	}

	public static void forceAbility(Hero hero, Integer target, MeleeWeapon wep){
		if (target == null || target == -1){
			return;
		}

		int range = 4+wep.buffedLvl();

		if (Dungeon.level.distance(hero.pos, target) > range || Dungeon.level.distance(hero.pos, target) <= 1){
			GLog.w(Messages.get(ForceGlove.class, "ability_bad_position"));
			return;
		}

		Ballistica chain = new Ballistica(curUser.pos, target, Ballistica.STOP_TARGET);
		Char ch = Actor.findChar(target);

		if (ch == null || !Dungeon.level.heroFOV[target]) {
			GLog.w( Messages.get(ForceGlove.class, "no_target") );
			return;
		}

		if (ch.properties().contains(Char.Property.IMMOVABLE)) {
			GLog.w( Messages.get(ForceGlove.class, "immovable") );
			return;
		}

		int bestPos = -1;
		for (int i : chain.subPath(1, chain.dist)){
			//prefer to the earliest point on the path
			if (!Dungeon.level.solid[i]
					&& Actor.findChar(i) == null
					&& (!Char.hasProp(ch, Char.Property.LARGE) || Dungeon.level.openSpace[i])){
				bestPos = i;
				break;
			}
		}

		if (bestPos == -1) {
			GLog.i(Messages.get(ForceGlove.class, "does_nothing"));
			return;
		}

		final int pulledPos = bestPos;
		int finalDist = Dungeon.level.distance(target, bestPos);

		wep.beforeAbilityUsed(hero, ch);
		WandOfBlastWave.BlastWave.blast(target);
		Sample.INSTANCE.play(Assets.Sounds.BLAST);
		hero.sprite.zap(target);
			Actor.add(new Pushing(ch, ch.pos, pulledPos, new Callback() {
				public void call() {
					ch.pos = pulledPos;
					Dungeon.level.occupyCell(ch);
					Dungeon.observe();
					GameScene.updateFog();
					ch.damage(Random.NormalIntRange(finalDist, 2*finalDist), curUser);
					if (ch.isAlive()) {
						Paralysis.prolong(ch, Paralysis.class, 1 + finalDist/2f);
					}
					hero.spendAndNext(1f);
				}
			}));

		hero.busy();
		hero.next();
		wep.afterAbilityUsed(hero);
	}

	@Override
	public String abilityInfo() {
		int range = levelKnown ? 4+buffedLvl() : 4;
		if (levelKnown){
			return Messages.get(this, "ability_desc", range);
		} else {
			return Messages.get(this, "typical_ability_desc", range);
		}
	}

	@Override
	public ArrayList<Class<?extends Item>> weaponRecipe() {
		return new ArrayList<>(Arrays.asList(Gauntlet.class, ForceCube.class, Evolution.class));
	}

}
