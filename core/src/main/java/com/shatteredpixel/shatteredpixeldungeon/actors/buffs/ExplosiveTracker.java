/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
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
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ShadowCaster;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Point;
import com.watabou.utils.PointF;

public class ExplosiveTracker extends Buff {

	public int pos;
	private int depth = Dungeon.depth;
	private int branch = Dungeon.branch;

	private int turnsLeft = 10;
	public boolean harmsAllies = true;

	private boolean[] fieldOfView;

	private static final int DIST = 8;

	@Override
	public boolean act() {

		if (branch != Dungeon.branch || depth != Dungeon.depth){
			spend(TICK);
			return true;
		}

		PointF p = DungeonTilemap.raisedTileCenterToWorld(pos);
		if (fieldOfView == null){
			fieldOfView = new boolean[Dungeon.level.length()];
		}

		if (turnsLeft > 0){
			FloatingText.show(p.x, p.y, pos, turnsLeft + "...", CharSprite.WARNING);
		}

		Point c = Dungeon.level.cellToPoint(pos);
		ShadowCaster.castShadow(c.x, c.y, Dungeon.level.width(), fieldOfView, Dungeon.level.solid, Math.min(3*Dungeon.hero.pointsInTalent(Talent.EXPLOSION_CMD), 11-turnsLeft));

		if (turnsLeft <= 0){
			detach();

			//if positive only, bombs do not harm allies
			if (!harmsAllies) {
				for (Char ch : Actor.chars()) {
					if (ch.alignment == Char.Alignment.ALLY) {
						Buff.affect(ch, NovaBombImmune.class, 0f);
					}
				}
			}

			switch (Dungeon.hero.pointsInTalent(Talent.EXPLOSION_CMD)) {
				case 3:
					Sample.INSTANCE.playDelayed(Assets.Sounds.BLAST, 0.5f);
				case 2:
					Sample.INSTANCE.playDelayed(Assets.Sounds.BLAST, 0.25f);
				case 1: default:
					Sample.INSTANCE.play(Assets.Sounds.BLAST);
			}
			PixelScene.shake( 2+Dungeon.hero.pointsInTalent(Talent.EXPLOSION_CMD), 0.5f*(1+Dungeon.hero.pointsInTalent(Talent.EXPLOSION_CMD)) );
			for (int i = 0; i < Dungeon.level.length(); i++){
				if (fieldOfView[i] && !Dungeon.level.solid[i]){
					new Command.CASBomb().explode(i); //yes, a bomb at every cell
					//this means that something in the blast effectively takes:
					//9x bomb dmg when fully inside
					//6x when along straight edge
					//3x when outside straight edge
					Dungeon.level.destroy(i);
					if (Actor.findChar(i) == Dungeon.hero){
						GameScene.flash(0x80FFFFFF);
					}
				}
			}
			GameScene.updateMap();

		} else {
			for (int i = 0; i < Dungeon.level.length(); i++){
				if (fieldOfView[i]){
					target.sprite.parent.add(new TargetedCell(i, 0xFF0000));
				}
			}
		}

		turnsLeft--;
		spend(TICK);
		return true;

	}

	public static class NovaBombImmune extends FlavourBuff{
		{
			immunities.add(Bomb.ConjuredBomb.class);
		}
	}


	public static final String POS = "pos";
	public static final String DEPTH = "depth";
	public static final String BRANCH = "branch";

	public static final String LEFT = "left";
	public static final String HARMS_ALLIES = "harms_allies";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(POS, pos);
		bundle.put(DEPTH, depth);
		bundle.put(BRANCH, branch);
		bundle.put(LEFT, turnsLeft);
		bundle.put(HARMS_ALLIES, harmsAllies);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		pos = bundle.getInt(POS);
		depth = bundle.getInt(DEPTH);
		branch = bundle.getInt(BRANCH);
		turnsLeft = bundle.getInt(LEFT);
		harmsAllies = bundle.getBoolean(HARMS_ALLIES);
	}

}
