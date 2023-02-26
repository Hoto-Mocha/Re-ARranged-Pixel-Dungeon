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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.samurai;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Kunai;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MissileSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Abil_Kunai extends ArmorAbility {

	{
		baseChargeUse = 75f;
	}

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

	@Override
	protected void activate(ClassArmor armor, Hero hero, Integer target) {
		if (target == null){
			return;
		}

		if (Actor.findChar(target) == hero){
			GLog.w(Messages.get(this, "self_target"));
			return;
		}

		Ballistica b = new Ballistica(hero.pos, target, Ballistica.STOP_SOLID);
		final HashSet<Char> targets = new HashSet<>();

		Char enemy = findChar(b, hero, -1, targets);

		if (enemy == null){
			GLog.w(Messages.get(this, "no_target"));
			return;
		}

		targets.add(enemy);

		while (targets.size() > 1){
			Char furthest = null;
			for (Char ch : targets){
				if (furthest == null){
					furthest = ch;
				} else if (Dungeon.level.trueDistance(enemy.pos, ch.pos) >
						Dungeon.level.trueDistance(enemy.pos, furthest.pos)){
					furthest = ch;
				}
			}
			targets.remove(furthest);
		}

		armor.charge -= chargeUse(hero);
		Item.updateQuickslot();

		Item proto = new Kunai();

		final HashSet<Callback> callbacks = new HashSet<>();

		for (Char ch : targets) {
			Callback callback = new Callback() {
				@Override
				public void call() {
					float dmgMulti = ch == enemy ? 1f : 0.5f;
					float accmulti = 1f + 0.25f*hero.pointsInTalent(Talent.MYSTICAL_KUNAI);
					hero.attack( ch, dmgMulti, 0, accmulti );
					Buff.affect(enemy, Vulnerable.class, 20f);
					if (hero.hasTalent(Talent.KUNAI_OF_DOOM) && Random.Int(20) < hero.pointsInTalent(Talent.KUNAI_OF_DOOM)) {
						Buff.affect(enemy, Doom.class);
					}
					if (hero.hasTalent(Talent.CORROSIVE_KUNAI)) {
						if (enemy.properties().contains(Char.Property.BOSS)
								|| enemy.properties().contains(Char.Property.MINIBOSS)){
							Buff.affect(enemy, Corrosion.class).set(3f, 2*hero.pointsInTalent(Talent.CORROSIVE_KUNAI));
						} else{
							Buff.affect(enemy, Corrosion.class).set(5f, 2*hero.pointsInTalent(Talent.CORROSIVE_KUNAI));
						}
					}
					if (hero.hasTalent(Talent.MYSTICAL_KUNAI)) {
						int dur = 5 + 5*Dungeon.hero.pointsInTalent(Talent.MYSTICAL_KUNAI);
						Buff.append(Dungeon.hero, TalismanOfForesight.CharAwareness.class, dur).charID = ch.id();
					}
					callbacks.remove( this );
					if (callbacks.isEmpty()) {
						Invisibility.dispel();
						hero.spendAndNext( hero.attackDelay() );
					}
				}
			};

			MissileSprite m = ((MissileSprite)hero.sprite.parent.recycle( MissileSprite.class ));
			m.reset( hero.sprite, ch.pos, proto, callback );
			m.hardlight(0.6f, 1f, 1f);
			m.alpha(0.8f);

			callbacks.add( callback );
		}

		hero.sprite.zap( enemy.pos );
		hero.busy();
	}

	private Char findChar(Ballistica path, Hero hero, int wallPenetration, HashSet<Char> existingTargets){
		for (int cell : path.path){
			Char ch = Actor.findChar(cell);
			if (ch != null){
				if (ch == hero || existingTargets.contains(ch) || ch.alignment == Char.Alignment.ALLY){
					continue;
				} else {
					return ch;
				}
			}
			if (Dungeon.level.solid[cell]){
				wallPenetration--;
				if (wallPenetration < 0){
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public int icon() {
		return HeroIcon.KUNAI;
	}

	@Override
	public Talent[] talents() {
		return new Talent[]{Talent.KUNAI_OF_DOOM, Talent.MYSTICAL_KUNAI, Talent.CORROSIVE_KUNAI, Talent.HEROIC_ENERGY};
	}
}
