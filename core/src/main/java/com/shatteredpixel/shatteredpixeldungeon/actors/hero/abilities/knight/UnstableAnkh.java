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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.knight;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AnkhInvulnerability;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class UnstableAnkh extends ArmorAbility {

	{
		baseChargeUse = 100f;
	}

	@Override
	protected void activate(ClassArmor armor, Hero hero, Integer target) {

		int duration = 1;
		if (hero.hasTalent(Talent.ANKH_ENHANCE)) {
			int points = hero.pointsInTalent(Talent.ANKH_ENHANCE);
			if (points == 1) duration = (Random.Int(2) == 0) ? 1 : 2;
			if (points == 2) duration = 2;
			if (points == 3) duration = (Random.Int(2) == 0) ? 2 : 3;
			if (points == 4) duration = 3;
		}

		Buff.affect(hero, AnkhInvulnerability.class, duration);

		if (hero.hasTalent(Talent.COMPLETE_ANKH) && Random.Int(100) < hero.pointsInTalent(Talent.COMPLETE_ANKH)) {
			Ankh ankh = new Ankh();
			Dungeon.level.drop(ankh, Dungeon.hero.pos ).sprite.drop();
			new Flare(6, 32).color(0xFFAA00, true).show(hero.sprite, 4f);
			GLog.p(Messages.get(this, "ankh_dropped"));
		}

		if (hero.hasTalent(Talent.BLESSED_ANKH)) {
			int healAmt = 5*hero.pointsInTalent(Talent.BLESSED_ANKH);
			healAmt = Math.min( healAmt, hero.HT - hero.HP );
			if (healAmt > 0 && hero.isAlive()) {
				hero.HP += healAmt;
				hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), hero.pointsInTalent(Talent.BLESSED_ANKH) );
				hero.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
			}
		}
		hero.sprite.operate(hero.pos);
		Sample.INSTANCE.play(Assets.Sounds.TELEPORT);

		armor.charge -= chargeUse(hero);
		armor.updateQuickslot();
		Invisibility.dispel();
		hero.spendAndNext(Actor.TICK);
	}

	@Override
	public int icon() {
		return HeroIcon.UNSTABLE_ANKH;
	}

	@Override
	public Talent[] talents() {
		return new Talent[]{Talent.BLESSED_ANKH, Talent.ANKH_ENHANCE, Talent.COMPLETE_ANKH, Talent.HEROIC_ENERGY};
	}
}