/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Tackle extends FlavourBuff implements ActionIndicator.Action {

	{ actPriority = HERO_PRIO+1; }

	public int object = 0;

	private static final String OBJECT    = "object";

	public static final float DURATION = 1f;

	{
		type = buffType.POSITIVE;
	}

	public void set(int object){
		this.object = object;
	}

	@Override
	public boolean attachTo(Char target) {
		ActionIndicator.setAction(this);
		return super.attachTo(target);
	}

	@Override
	public void detach() {
		super.detach();
		ActionIndicator.clearAction(this);
	}

	@Override
	public boolean act() {
		Char ch = (Char) Actor.findById(object);
		if (ch == null) {
			detach();
			spend(TICK);
			return true;
		}
		if (ch.alignment != Char.Alignment.ENEMY) {
			detach();
			spend(TICK);
			return true;
		}
		if (!Dungeon.level.adjacent(ch.pos, Dungeon.hero.pos)) {
			detach();
			spend(TICK);
			return true;
		}
		spend(TICK);
		return true;
	}

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( OBJECT, object );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		object = bundle.getInt( OBJECT );
	}

	@Override
	public String actionName() {
		return Messages.get(this, "tackle");
	}

	@Override
	public int actionIcon() {
		return HeroIcon.TACKLE;
	}

	@Override
	public int indicatorColor() {
		return 0xFFDB65;
	}

	@Override
	public void doAction() {
		Dungeon.hero.busy();
		Char ch = (Char) Actor.findById(object);
		Hero hero = Dungeon.hero;
		if (ch == null || hero == null || !Dungeon.level.adjacent(ch.pos, hero.pos)) {
			hero.next();
			detach();
			return;
		}

		if (hero.hasTalent(Talent.SUPER_ARMOR)) {
			Buff.affect(hero, SuperArmorTracker.class, Actor.TICK);
		}
		if (hero.hasTalent(Talent.MYSTICAL_TACKLE)) {
			Buff.affect(hero, MysticalTackleTracker.class, Actor.TICK);
		}

		target.sprite.attack(ch.pos, new Callback() {
			@Override
			public void call() {
				AttackIndicator.target(ch);
				float damageMulti = 0.4f + 0.2f*hero.pointsInTalent(Talent.POWERFUL_TACKLE);
				int damage = Math.round(hero.drRoll() * damageMulti); //deals 40%+(20*Talent.POWERFUL_TACKLE level)% of hero's dr
				Buff.affect(hero, TackleTracker.class);

				if (hero.attack(ch, 0f, damage, Char.INFINITE_ACCURACY)){
					Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
				}

				if (hero.buff(BrokenSeal.WarriorShield.class) != null) {
					BrokenSeal.WarriorShield shield = hero.buff(BrokenSeal.WarriorShield.class);
					int amount = shield.shielding()+1;
					if (hero.hasTalent(Talent.IMPROVED_TACKLE)) amount++;
					shield.supercharge(Math.min(shield.maxShield(), amount));
				}

				//pushes the enemy back
				int pushedPos = -1;
				Ballistica trajectory = new Ballistica(target.pos, ch.pos, Ballistica.STOP_TARGET);
				trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
				int dist = 1;
				if (hero.pointsInTalent(Talent.IMPROVED_TACKLE) > 1) dist++;

				if (!ch.isAlive() || (!ch.flying && hero.pointsInTalent(Talent.IMPROVED_TACKLE) < 3)) {
					while (dist > trajectory.dist ||
							(dist > 0 && Dungeon.level.pit[trajectory.path.get(dist)])) {
						dist--;
					}
				}
				WandOfBlastWave.throwChar(ch, trajectory, dist, true, true, hero.getClass());
				pushedPos = trajectory.path.get(dist); //checks the position of enemy after pushing
				if (Actor.findChar(pushedPos) != null) {
					int findPos = Math.max(0, dist-1);
					pushedPos = trajectory.path.get(findPos);
				}

				if (ch.isAlive() && hero.hasTalent(Talent.INCAPACITATION)) {
					switch (hero.pointsInTalent(Talent.INCAPACITATION)) {
						case 3:
							Buff.affect(ch, Vulnerable.class, 2f);
						case 2:
							Buff.affect(ch, Weakness.class, 2f);
						case 1:
							Buff.affect(ch, Hex.class, 2f);
						case 0: default:
							break;
					}
				};
				hero.spendAndNext(Actor.TICK);
				hero.buff(TackleTracker.class).detach();

				if (!ch.isAlive() && hero.hasTalent(Talent.DELAYED_GRENADE)) {
					if (pushedPos != -1) {
						int minDamage = Math.round(2.5f+2.5f*hero.pointsInTalent(Talent.DELAYED_GRENADE));
						int maxDamage = 10*(1+hero.pointsInTalent(Talent.DELAYED_GRENADE));

						ArrayList<Char> affected = new ArrayList<>();

						for (int n : PathFinder.NEIGHBOURS9) {
							int c = pushedPos + n;
							if (c >= 0 && c < Dungeon.level.length()) {
								if (Dungeon.level.heroFOV[c]) {
									CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
									CellEmitter.center(pushedPos).burst(BlastParticle.FACTORY, 4);
								}
								if (Dungeon.level.flamable[c]) {
									Dungeon.level.destroy(c);
									GameScene.updateMap(c);
								}
								Char enemy = Actor.findChar(c);
								if (enemy != null) {
									if (enemy != hero && enemy.alignment != Char.Alignment.ALLY) {
										affected.add(enemy);
									}
								}
							}
						}

						for (Char enemy : affected) {
							enemy.damage(Random.NormalIntRange(minDamage, maxDamage), hero);
						}
					}
				}
			}
		});

		detach();
	}

	public static class TackleTracker extends Buff{};
	public static class SuperArmorTracker extends FlavourBuff{
		{ actPriority = HERO_PRIO+1; }
	};
	public static class MysticalTackleTracker extends FlavourBuff{
		{ actPriority = HERO_PRIO+1; }
	};
}
