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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Inferno;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpellBookCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.ArcaneResin;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SpellBook_Fire_Sword extends SpellBook_Fire {

	{
		image = ItemSpriteSheet.FIRE_SPELLBOOK_SWORD;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;

		tier = 5;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		float procChance = (buffedLvl()+1f)/(buffedLvl()+3f);
		if (Random.Float() < procChance) {
			if (defender.buff(Burning.class) != null){
				Buff.affect(defender, Burning.class).reignite(defender, 8f);
				int burnDamage = Random.NormalIntRange( 1, 3 + Dungeon.depth/4 );
				defender.damage( Math.round(burnDamage * 0.67f), this );
			} else {
				Buff.affect(defender, Burning.class).reignite(defender, 8f);
			}
			defender.sprite.emitter().burst( FlameParticle.FACTORY, buffedLvl() + 1 );
		}
		return super.proc( attacker, defender, damage );
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_READ);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_READ)) {
			if (hero.buff(SpellBookCoolDown.class) != null) {
				GLog.w( Messages.get(SpellBook_Empty_Sword.class, "fail") );
			} else if (!isIdentified()) {
				GLog.w( Messages.get(SpellBook_Empty_Sword.class, "need_id") );
			} else {
				usesTargeting = true;
				curUser = hero;
				curItem = this;
				GameScene.selectCell(spell);
			}
		}
	}

	private CellSelector.Listener spell = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer cell ) {
			if (cell != null) {
				int c = cell;
				if (Dungeon.level.map[c] != Terrain.WALL && Dungeon.level.heroFOV[c]) {
					if (Dungeon.level.pit[c]) {
						GameScene.add(Blob.seed(c, 1, Fire.class));
					} else {
						if (buffedLvl() >= 10) {
							GameScene.add(Blob.seed(c, 300 + 20 * buffedLvl(), Inferno.class));
						} else {
							GameScene.add(Blob.seed(c, 5, Fire.class));
						}
					}
					Sample.INSTANCE.play(Assets.Sounds.BURNING);
				} else {
					GLog.w( Messages.get(SpellBook_Fire_Sword.this, "cannot_cast"));
				}
				Buff.affect(hero, FireImbue.class).set(Math.min(10+2*buffedLvl(), 50));
				Buff.affect(hero, SpellBookCoolDown.class, Math.max(100f-5*buffedLvl(), 50f));
				Invisibility.dispel();
				curUser.spend( Actor.TICK );
				curUser.busy();
				((HeroSprite)curUser.sprite).read();
				hero.sprite.emitter().burst( FlameParticle.FACTORY, buffedLvl() + 3 );
				Sample.INSTANCE.play(Assets.Sounds.READ);
			}
		}
		@Override
		public String prompt() {
			return Messages.get(SpiritBow.class, "prompt");
		}

	};

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{Greatsword.class, SpellBook_Fire.class, ArcaneResin.class};
			inQuantity = new int[]{1, 1, 4};

			cost = 10;

			output = SpellBook_Fire_Sword.class;
			outQuantity = 1;
		}
	}
}
