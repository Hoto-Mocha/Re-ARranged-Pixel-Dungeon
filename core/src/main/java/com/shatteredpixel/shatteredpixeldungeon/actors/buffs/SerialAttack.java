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

package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndCombo;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

public class SerialAttack extends Buff {
	
	private int count = 0;
	private int dmgBonus = 0;
	private float comboTime = 0f;
	private float initialComboTime = 5f;

	@Override
	public int icon() {
		return BuffIndicator.COMBO;
	}
	
	@Override
	public void tintIcon(Image icon) {
		icon.hardlight(1f, 0f, 0f);
	}

	@Override
	public float iconFadePercent() {
		return Math.max(0, (initialComboTime - comboTime)/ initialComboTime);
	}

	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}
	
	public void hit( Char enemy ) {

		if (count < 4+Dungeon.hero.pointsInTalent(Talent.CONTINUOUS_ATTACK)) {
			count++;
			dmgBonus += 5;
		} else {
			count = 4+Dungeon.hero.pointsInTalent(Talent.CONTINUOUS_ATTACK);
			dmgBonus = 5*(4+Dungeon.hero.pointsInTalent(Talent.CONTINUOUS_ATTACK));
		}

		comboTime = 5f;
		if (Dungeon.hero.hasTalent(Talent.SERIAL_MOMENTUM)) {
			comboTime += Dungeon.hero.pointsInTalent(Talent.SERIAL_MOMENTUM);
		}

		initialComboTime = comboTime;

		BuffIndicator.refreshHero(); //refresh the buff visually on-hit
	}

	public void addTime( float time ){
		comboTime += time;
	}

	@Override
	public void detach() {
		super.detach();
	}

	@Override
	public boolean act() {
		comboTime-=TICK;
		spend(TICK);
		if (comboTime <= 0) {
			detach();
		}
		return true;
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", count, dmgBonus, dispTurns(comboTime));
	}

	private static final String COUNT = "count";
	private static final String TIME  = "combotime";
	private static final String INITIAL_TIME  = "initialComboTime";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(COUNT, count);
		bundle.put(TIME, comboTime);
		bundle.put(INITIAL_TIME, initialComboTime);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		count = bundle.getInt( COUNT );
		comboTime = bundle.getFloat( TIME );
		initialComboTime = bundle.getFloat( INITIAL_TIME );
	}

}
