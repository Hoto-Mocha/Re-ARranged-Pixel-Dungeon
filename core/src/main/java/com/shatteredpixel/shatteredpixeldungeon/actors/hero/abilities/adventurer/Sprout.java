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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.adventurer;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Sprout extends ArmorAbility {

	{
		baseChargeUse = 35f;
	}

	@Override
	protected void activate(ClassArmor armor, Hero hero, Integer target) {
		Ballistica aim;
		ArrayList<Integer> plantCandidates = new ArrayList<>();
		if (hero.pos % Dungeon.level.width() > 10){
			aim = new Ballistica(hero.pos, hero.pos - 1, Ballistica.WONT_STOP);
		} else {
			aim = new Ballistica(hero.pos, hero.pos + 1, Ballistica.WONT_STOP);
		}

		int aoeSize = 4 + hero.pointsInTalent(Talent.FOREST);

		int projectileProps = Ballistica.STOP_SOLID | Ballistica.STOP_TARGET;

		ConeAOE aoe = new ConeAOE(aim, aoeSize, 360, projectileProps);

		final float effectMulti = 0.1f*hero.pointsInTalent(Talent.JUNGLE);

		for (int cell : aoe.cells) {
			int t = Dungeon.level.map[cell];
			if (Random.Float() < 0.2f+effectMulti) {
				if ((t == Terrain.EMPTY || t == Terrain.EMPTY_DECO || t == Terrain.EMBERS
						|| t == Terrain.GRASS || t == Terrain.FURROWED_GRASS)
						&& Dungeon.level.plants.get(cell) == null) {
					if (hero.hasTalent(Talent.REGROWTH) && Random.Int(20) < hero.pointsInTalent(Talent.REGROWTH)) {
						Dungeon.level.plant((Plant.Seed) Generator.randomUsingDefaults(Generator.Category.SEED), cell);
						CellEmitter.get( cell ).burst( LeafParticle.LEVEL_SPECIFIC, 8 );
					} else {
						Level.set(cell, Terrain.HIGH_GRASS);
						CellEmitter.get( cell ).burst( LeafParticle.LEVEL_SPECIFIC, 4 );
					}
					GameScene.updateMap(cell);
				}
			}

			if (hero.hasTalent(Talent.REGROWTH)) {

				int plants = hero.pointsInTalent(Talent.REGROWTH);

				for (int i = 0; i < plants; i++) {
					Integer plantPos = Random.element(plantCandidates);
					if (plantPos != null) {
						Dungeon.level.plant((Plant.Seed) Generator.randomUsingDefaults(Generator.Category.SEED), plantPos);
						plantCandidates.remove(plantPos);
					}
				}
			}
		}

		hero.spendAndNext(Actor.TICK);
		hero.sprite.operate( hero.pos );
		Invisibility.dispel();
		hero.busy();

		armor.charge -= chargeUse(hero);
		armor.updateQuickslot();

		Sample.INSTANCE.play( Assets.Sounds.TRAMPLE );

	}

	@Override
	public int icon() {
		return HeroIcon.SPROUT;
	}

	@Override
	public Talent[] talents() {
		return new Talent[]{Talent.JUNGLE, Talent.FOREST, Talent.REGROWTH, Talent.HEROIC_ENERGY};
	}
}
