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
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Regrowth;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.EarthParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class Root extends ArmorAbility {

	{
		baseChargeUse = 25f;
	}

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

	@Override
	public void activate( ClassArmor armor, Hero hero, Integer target ) {
		if (target == null){
			return;
		}

		Char ch = Actor.findChar(target);

		if (ch == null){
			GLog.w(Messages.get(this, "no_target"));
			return;
		} else if (ch.alignment != Char.Alignment.ENEMY){
			if (ch == hero && hero.hasTalent(Talent.ROOT_ARMOR)) {
				if (armor.charge >= 2*chargeUse( hero )) {
					Buff.affect(ch, Barkskin.class).set(Math.round(hero.belongings.armor().DRMax() * 0.125f * hero.pointsInTalent(Talent.ROOT_ARMOR)), 1);
					hero.sprite.operate(ch.pos);
					armor.charge -= 2*chargeUse( hero );
					CellEmitter.get( ch.pos ).burst( LeafParticle.LEVEL_SPECIFIC, 8 );
					Sample.INSTANCE.play( Assets.Sounds.MISS );
				} else {
					GLog.w(Messages.get(this, "no_charge"));
				}
			} else {
				GLog.w(Messages.get(this, "ally_target"));
				return;
			}
		}

		if (ch != null && ch != hero){
			Buff.prolong(ch, Roots.class, 20f);
			if (hero.hasTalent(Talent.POISONOUS_ROOT)) {
				Buff.affect(ch, Poison.class).set(hero.pointsInTalent(Talent.POISONOUS_ROOT));
			}
			if (hero.hasTalent(Talent.ROOT_SPREAD)) {
				GameScene.add( Blob.seed(ch.pos, 30*hero.pointsInTalent(Talent.ROOT_SPREAD), Regrowth.class));
			}
			CellEmitter.bottom( ch.pos ).start( EarthParticle.FACTORY, 0.05f, 8 );
			hero.sprite.zap(target);
			armor.charge -= chargeUse( hero );
			Sample.INSTANCE.play( Assets.Sounds.PLANT );
		}

		hero.busy();
		armor.updateQuickslot();
		hero.spendAndNext(Actor.TICK);
	}

	@Override
	public int icon() {
		return HeroIcon.ROOT;
	}

	@Override
	public Talent[] talents() {
		return new Talent[]{Talent.POISONOUS_ROOT, Talent.ROOT_SPREAD, Talent.ROOT_ARMOR, Talent.HEROIC_ENERGY};
	}
}
