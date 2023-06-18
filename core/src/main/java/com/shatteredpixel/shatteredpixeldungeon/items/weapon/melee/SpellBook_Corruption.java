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
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AllyBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpellBookCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorruption;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class SpellBook_Corruption extends SpellBook {

	{
		image = ItemSpriteSheet.CORRUPTION_SPELLBOOK;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;

		tier = 3;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		float procChance;
		int procBonus = 0; //used for adding chances to corrupt
		if (defender.buff(Weakness.class) != null) {
			procBonus += 1;
		}
		if (defender.buff(Vulnerable.class) != null) {
			procBonus += 1;
		}
		if (defender.buff(Cripple.class) != null) {
			procBonus += 1;
		}
		if (defender.buff(Blindness.class) != null) {
			procBonus += 1;
		}
		if (defender.buff(Terror.class) != null) {
			procBonus += 1;
		}
		if (defender.buff(Amok.class) != null) {
			procBonus += 2;
		}
		if (defender.buff(Slow.class) != null) {
			procBonus += 2;
		}
		if (defender.buff(Hex.class) != null) {
			procBonus += 2;
		}
		if (defender.buff(Paralysis.class) != null) {
			procBonus += 2;
		}
		if (defender.buff(com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom.class) != null) {
			procChance = 1; //100% chance when enemy has a Doom debuff
		} else {
			procChance = (buffedLvl()+5f+procBonus)/(buffedLvl()+25f);
		}
		if (damage >= defender.HP
				&& Random.Float() < procChance
				&& !defender.isImmune(Corruption.class)
				&& defender.buff(Corruption.class) == null
				&& defender instanceof Mob
				&& defender.isAlive()){
			Mob mob = (Mob) defender;
			Corruption.corruptionHeal(mob);
			AllyBuff.affectAndLoot(mob, hero, Corruption.class);
			damage = 0;
		}
		return super.proc( attacker, defender, damage );
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_READ)) {
			if (hero.buff(SpellBookCoolDown.class) != null) {
				GLog.w( Messages.get(SpellBook_Empty.class, "fail") );
			} else if (!isIdentified()) {
				GLog.w( Messages.get(SpellBook_Empty.class, "need_id") );
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
		public void onSelect( Integer target ) {
			if (target != null) {
				Char ch = Actor.findChar(target);
				if (ch != null) {
					if (ch != hero) {
						switch (Random.Int(15)) {
							case 0: case 1: default:
								Buff.affect( ch, Weakness.class, 3f+buffedLvl() );
								break;
							case 2: case 3:
								Buff.affect( ch, Vulnerable.class, 3f+buffedLvl() );
								break;
							case 4:
								Buff.affect( ch, Cripple.class, 3f+buffedLvl() );
								break;
							case 5:
								Buff.affect( ch, Blindness.class, 3f+buffedLvl() );
								break;
							case 6:
								Buff.affect( ch, Terror.class, 3f+buffedLvl() );
								break;
							case 7: case 8: case 9:
								Buff.affect( ch, Amok.class, 3f+buffedLvl() );
								break;
							case 10: case 11:
								Buff.affect( ch, Slow.class, 3f+buffedLvl() );
								break;
							case 12: case 13:
								Buff.affect( ch, Hex.class, 3f+buffedLvl() );
								break;
							case 14:
								Buff.affect( ch, Paralysis.class, 3f+buffedLvl() );
								break;
						}
						if (Random.Int(100) <= Math.min(hero.belongings.weapon.buffedLvl(), 9)) {					//1% base, +1% per lvl, max 10%
							Buff.affect( ch, com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom.class);
						}
						ch.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10 );
						Sample.INSTANCE.play(Assets.Sounds.BURNING);
					} else {
						GLog.p( Messages.get(SpellBook_Corruption.this, "cannot_hero") );
					}
				} else {
					GLog.p( Messages.get(SpellBook_Corruption.this, "cannot_cast") );
				}
				Buff.affect(hero, SpellBookCoolDown.class, Math.max(50f-2.5f*buffedLvl(), 25f));
				Invisibility.dispel();
				curUser.spend( Actor.TICK );
				curUser.busy();
				((HeroSprite)curUser.sprite).read();
				hero.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10 );
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
			inputs =  new Class[]{SpellBook_Empty.class, WandOfCorruption.class};
			inQuantity = new int[]{1, 1};

			cost = 10;

			output = SpellBook_Corruption.class;
			outQuantity = 1;
		}
	}
}
