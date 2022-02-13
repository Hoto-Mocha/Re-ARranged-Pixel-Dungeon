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
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroAction;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NPC;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Effects;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Dong extends Buff implements ActionIndicator.Action {

	{
		actPriority = BUFF_PRIO - 1;
		announced = true;
	}

	public int blinkDistance(){
		return 500;
	}

	@Override
	public boolean act() {
		if (blinkDistance() > 0 && target == Dungeon.hero){
			ActionIndicator.setAction(this);
			spend(TICK);
		} else {
			detach();
		}
		return true;
	}

	@Override
	public void detach() {
		super.detach();
		ActionIndicator.clearAction(this);
	}

	@Override
	public int icon() {
		return BuffIndicator.STATE_DONG;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		String desc = Messages.get(this, "desc");
		return desc;
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		ActionIndicator.setAction(this);
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
	}

	@Override
	public Image getIcon() {
		if (Dungeon.hero.buff(Lead.class) != null) {
			Image actionIco = Effects.get(Effects.Type.WOUND);
			actionIco.tint(0xFFFF0000);
			return actionIco;
		} else {
			Image actionIco = new ItemSprite(new Item(){ {image = ItemSpriteSheet.SHEATH; }});
			tintIcon(actionIco);
			return actionIco;
		}
	}

	@Override
	public void doAction() {
		if (Dungeon.hero.buff(Lead.class) != null) {
			GameScene.selectCell(attack);
		} else {
			Buff.affect(Dungeon.hero, Lead.class);
			Dungeon.hero.spendAndNext(TICK);
		}
	}

	private CellSelector.Listener attack = new CellSelector.Listener() {

		@Override
		public void onSelect(Integer cell) {
			if (cell == null) return;
			final Char enemy = Actor.findChar( cell );
			if (enemy == null || Dungeon.hero.isCharmedBy(enemy) || enemy instanceof NPC || !Dungeon.level.heroFOV[cell] || enemy instanceof Hero){
				GLog.w(Messages.get(Dong.class, "no_target"));
			} else {

				//just attack them then!
				if (Dungeon.hero.canAttack(enemy)){
					Dungeon.hero.curAction = new HeroAction.Attack( enemy );
					Dungeon.hero.next();
					return;
				}
				PathFinder.buildDistanceMap(Dungeon.hero.pos, BArray.not(Dungeon.level.solid, null), blinkDistance());
				int dest = -1;
				for (int i : PathFinder.NEIGHBOURS8){
					//cannot blink into a cell that's occupied or impassable, only over them
					if (Actor.findChar(cell+i) != null)     continue;
					if (!Dungeon.level.passable[cell+i])    continue;

					if (dest == -1 || PathFinder.distance[dest] > PathFinder.distance[cell+i]){
						dest = cell+i;
						//if two cells have the same pathfinder distance, prioritize the one with the closest true distance to the hero
					} else if (PathFinder.distance[dest] == PathFinder.distance[cell+i]){
						if (Dungeon.level.trueDistance(Dungeon.hero.pos, dest) > Dungeon.level.trueDistance(Dungeon.hero.pos, cell+i)){
							dest = cell+i;
						}
					}

				}

				if (dest == -1 || PathFinder.distance[dest] == Integer.MAX_VALUE || Dungeon.hero.rooted){
					GLog.w(Messages.get(Dong.class, "out_of_reach"));
					return;
				}

				Dungeon.hero.pos = dest;
				Dungeon.level.occupyCell(Dungeon.hero);
				//prevents the hero from being interrupted by seeing new enemies
				Dungeon.observe();
				GameScene.updateFog();
				Dungeon.hero.checkVisibleMobs();

				Dungeon.hero.sprite.place( Dungeon.hero.pos );
				Dungeon.hero.sprite.turnTo( Dungeon.hero.pos, cell);
				CellEmitter.get( Dungeon.hero.pos ).burst( Speck.factory( Speck.WOOL ), 6 );
				Sample.INSTANCE.play( Assets.Sounds.PUFF );

				Dungeon.hero.curAction = new HeroAction.Attack( enemy );
				Dungeon.hero.next();
			}
		}

		@Override
		public String prompt() {
			return Messages.get(Dong.class, "prompt", blinkDistance());
		}
	};
}
