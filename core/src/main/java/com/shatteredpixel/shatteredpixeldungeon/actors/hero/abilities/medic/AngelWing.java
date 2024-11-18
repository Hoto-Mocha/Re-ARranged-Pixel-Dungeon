/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2022 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.medic;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.FeatherFall;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;

public class AngelWing extends ArmorAbility {

	{
		baseChargeUse = 25f;
	}

	@Override
	protected void activate(ClassArmor armor, Hero hero, Integer target) {

		Buff.prolong(hero, AngelWingBuff.class, AngelWingBuff.DURATION);

		if (hero.hasTalent(Talent.ANGELS_BLESS)) {
			Buff.affect(hero, Bless.class, 10*hero.pointsInTalent(Talent.ANGELS_BLESS));
		}

		hero.sprite.operate(hero.pos);
		hero.spendAndNext(Actor.TICK);

		armor.charge -= chargeUse(hero);
		armor.updateQuickslot();
		Invisibility.dispel();
	}

	@Override
	public int icon() {
		return HeroIcon.ANGEL_WING;
	}

	@Override
	public Talent[] talents() {
		return new Talent[]{Talent.LIGHT_LIKE_FEATHER, Talent.ANGELS_BLESS, Talent.HEALING_WING, Talent.HEROIC_ENERGY};
	}

	public static class AngelWingBuff extends FlavourBuff {

		{
			type = buffType.NEUTRAL;
			announced = false;
		}

		public static final float DURATION = 20f;

		@Override
		public boolean attachTo( Char target ) {
			if (super.attachTo( target )) {
				target.flying = true;
				Roots.detach( target, Roots.class );
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void detach() {
			target.flying = false;
			super.detach();
			if (Dungeon.hero.hasTalent(Talent.LIGHT_LIKE_FEATHER)) {
				Buff.affect(Dungeon.hero, FeatherFall.FeatherBuff.class, 2*Dungeon.hero.pointsInTalent(Talent.LIGHT_LIKE_FEATHER));
			}
			//only press tiles if we're current in the game screen
			if (ShatteredPixelDungeon.scene() instanceof GameScene) {
				Dungeon.level.occupyCell(target );
			}
		}

		@Override
		public void fx(boolean on) {
			if (on) target.sprite.add(CharSprite.State.LEVITATING);
			else target.sprite.remove(CharSprite.State.LEVITATING);
		}

		@Override
		public int icon() {
			return BuffIndicator.ANGELWING;
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, (DURATION - visualcooldown()) / DURATION);
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc", dispTurns());
		}
	}
}
