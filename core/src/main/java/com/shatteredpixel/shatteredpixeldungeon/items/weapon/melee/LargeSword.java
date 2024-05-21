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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;

import java.text.DecimalFormat;

public class LargeSword extends MeleeWeapon {

	{
		image = ItemSpriteSheet.LARGE_SHORD;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 0.8f;

		tier = 5;
		DLY = 2f; //0.5x speed
	}

	@Override
	public int max(int lvl) {
		return  Math.round(6.67f*(tier+1)) +    //40 base, up from 30
				lvl*Math.round(1.33f*(tier+1)); //+8 per level, up from +6
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		LargeSwordBuff buff = Dungeon.hero.buff(LargeSwordBuff.class);
		if (buff != null) {
			damage *= buff.getDamageFactor();
			if (buff.getDamageFactor() > 2) {
				int cell = defender.pos;
				for (int k : PathFinder.NEIGHBOURS8){
					Char ch = Actor.findChar( cell+k );
					if (ch != null && ch.alignment != Char.Alignment.ALLY){
						ch.damage(Math.round(damage * 0.4f), Dungeon.hero);
					}
				}

				WandOfBlastWave.BlastWave.blast(cell);
				Sample.INSTANCE.play( Assets.Sounds.BLAST );
			}
			buff.detach();
		}
		return super.proc( attacker, defender, damage );
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		beforeAbilityUsed(hero, null);
		Buff.affect(hero, LargeSwordBuff.class).setDamageFactor(this.buffedLvl(), 4+buffedLvl(), (Dungeon.hero.belongings.secondWep instanceof LargeSword));
		hero.sprite.operate(hero.pos);
		hero.spendAndNext(Actor.TICK);
		afterAbilityUsed(hero);
	}

	@Override
	public String abilityInfo() {
		if (levelKnown){
			return Messages.get(this, "ability_desc", 4+buffedLvl());
		} else {
			return Messages.get(this, "typical_ability_desc", 4);
		}
	}

	public static class LargeSwordBuff extends Buff {

		{
			type = buffType.NEUTRAL;
			announced = false;
		}

		float damageFactor = 1;
		float accuracyFactor = 1;
		float defenseFactor = 0.90f;
		float maxDamage;
		float maxAccuracy;
		int turn = 0;

		boolean secondWep = false;

		public void setDamageFactor(float lvl, boolean isSecond) {
			setDamageFactor(lvl, 1, isSecond);
		}

		public void setDamageFactor(float lvl, int amount, boolean isSecond) {
			secondWep = isSecond;
			turn += amount;
			maxDamage = 1.80f + 0.20f * (lvl + 1);
			maxAccuracy = 1.40f + 0.10f * (lvl + 1);
			damageFactor += 0.20f;
			accuracyFactor += 0.10f;
			defenseFactor = (float)Math.pow(0.90f, lvl + 1);

			if (damageFactor > maxDamage) {
				damageFactor = maxDamage;
			}

			if (accuracyFactor > maxAccuracy) {
				accuracyFactor = maxAccuracy;
			}
		}

		public float getDefenseFactor() {
			return defenseFactor;
		}

		public float getDamageFactor() {
			return damageFactor;
		}

		public float getAccuracyFactor() {
			return accuracyFactor;
		}

		public float duration() {
			return visualcooldown();
		}

		@Override
		public int icon() {
			return BuffIndicator.WEAPON;
		}

		@Override
		public void tintIcon(Image icon) {
			if (damageFactor <= 1.2f){
				icon.hardlight(0.5f, 1, 0);
			} else if (damageFactor <= 1.4f) {
				icon.hardlight(1, 1, 0);
			} else if (damageFactor <= 1.6f){
				icon.hardlight(1, 0.67f, 0);
			} else if (damageFactor <= 1.8f){
				icon.hardlight(1, 0.33f, 0);
			} else {
				icon.hardlight(1, 0, 0);
			}
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, 1 - (damageFactor - 1) / (maxDamage - 1));
		}

		public int pos = -1;
		Item item = null;

		@Override
		public boolean act() {
			if (pos == -1) pos = target.pos;
			if (item == null) {
				if (secondWep) {
					item = hero.belongings.secondWep;
				} else {
					item = hero.belongings.weapon;
				}
			}
			if (item != hero.belongings.weapon && item != hero.belongings.secondWep) {
				detach();
			}
			if (pos != target.pos) {
				detach();
			}
			spend(TICK);
			return true;
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc",
					turn,
					new DecimalFormat("#").format((getDamageFactor() - 1) * 100),
					new DecimalFormat("#").format((maxDamage - 1) * 100),
					new DecimalFormat("#").format((getAccuracyFactor() - 1) * 100),
					new DecimalFormat("#").format((maxAccuracy - 1) * 100),
					new DecimalFormat("#.##").format(((1 - defenseFactor) * 100)));
		}

		private static final String DAMAGE_FACTOR = "damageFactor";
		private static final String ACCURACY_FACTOR = "accuracyFactor";
		private static final String MAX_DAMAGE = "maxDamage";
		private static final String MAX_ACCURACY = "maxAccuracy";
		private static final String DEFENSE_FACTOR = "defenseFactor";
		private static final String TURN = "turn";
		private static final String POS = "pos";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(DAMAGE_FACTOR, damageFactor);
			bundle.put(ACCURACY_FACTOR, accuracyFactor);
			bundle.put(MAX_DAMAGE, maxDamage);
			bundle.put(MAX_ACCURACY, maxAccuracy);
			bundle.put(DEFENSE_FACTOR, defenseFactor);
			bundle.put(TURN, turn);
			bundle.put(POS, pos);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			damageFactor = bundle.getFloat(DAMAGE_FACTOR);
			accuracyFactor = bundle.getFloat(ACCURACY_FACTOR);
			maxDamage = bundle.getFloat(MAX_DAMAGE);
			maxAccuracy = bundle.getFloat(MAX_ACCURACY);
			defenseFactor = bundle.getFloat(DEFENSE_FACTOR);
			turn = bundle.getInt(TURN);
			pos = bundle.getInt(POS);
		}
	}

}