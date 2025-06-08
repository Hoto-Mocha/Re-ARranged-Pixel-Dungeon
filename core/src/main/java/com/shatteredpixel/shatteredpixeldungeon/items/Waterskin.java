/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2025 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AdrenalineSurge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BlobImmunity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Levitation;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MindVision;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ToxicImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.trinkets.VialOfBlood;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.VelvetPouch;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Blindweed;
import com.shatteredpixel.shatteredpixeldungeon.plants.Earthroot;
import com.shatteredpixel.shatteredpixeldungeon.plants.Fadeleaf;
import com.shatteredpixel.shatteredpixeldungeon.plants.Firebloom;
import com.shatteredpixel.shatteredpixeldungeon.plants.Icecap;
import com.shatteredpixel.shatteredpixeldungeon.plants.Mageroyal;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.plants.Rotberry;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sorrowmoss;
import com.shatteredpixel.shatteredpixeldungeon.plants.Starflower;
import com.shatteredpixel.shatteredpixeldungeon.plants.Stormvine;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sungrass;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Waterskin extends Item {

	private static final int MAX_VOLUME	= 20;

	private static final String AC_DRINK	= "DRINK";

	private static final float TIME_TO_DRINK = 1f;

	private static final String TXT_STATUS	= "%d/%d";

	{
		image = ItemSpriteSheet.WATERSKIN;

		defaultAction = AC_DRINK;

		unique = true;
	}

	private int volume = 0;

	private static final String VOLUME	= "volume";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( VOLUME, volume );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		volume	= bundle.getInt( VOLUME );
	}

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (volume > 0) {
			actions.add( AC_DRINK );
		}
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_DRINK )) {

			if (volume > 0) {
				if (hero.hasTalent(Talent.HERB_EXTRACTION)) {
					GameScene.selectItem(itemSelector);
				} else {
					drink();
				}
			} else {
				GLog.w( Messages.get(this, "empty") );
			}

		}
	}

	public void drink() {
		float missingHealthPercent = 1f - (hero.HP / (float)hero.HT);

				//each drop is worth 5% of total health
				float dropsNeeded = missingHealthPercent / 0.05f;

				//we are getting extra heal value, scale back drops needed accordingly
				if (dropsNeeded > 1.01f && VialOfBlood.delayBurstHealing()){
					dropsNeeded /= VialOfBlood.totalHealMultiplier();
				}

				//add extra drops if we can gain shielding
				int curShield = 0;
				if (hero.buff(Barrier.class) != null) curShield = hero.buff(Barrier.class).shielding();
				int maxShield = Math.round(hero.HT *0.2f*hero.pointsInTalent(Talent.SHIELDING_DEW));
				if (hero.hasTalent(Talent.SHIELDING_DEW)){
					float missingShieldPercent = 1f - (curShield / (float)maxShield);
					missingShieldPercent *= 0.2f*hero.pointsInTalent(Talent.SHIELDING_DEW);
					if (missingShieldPercent > 0){
						dropsNeeded += missingShieldPercent / 0.05f;
					}
				}

				//trimming off 0.01 drops helps with floating point errors
				int dropsToConsume = (int)Math.ceil(dropsNeeded - 0.01f);
				dropsToConsume = (int)GameMath.gate(1, dropsToConsume, volume);

				if (Dewdrop.consumeDew(dropsToConsume, hero, true)){
					volume -= dropsToConsume;
					Catalog.countUses(Dewdrop.class, dropsToConsume);

			hero.spend(TIME_TO_DRINK);
			hero.busy();

			Sample.INSTANCE.play(Assets.Sounds.DRINK);
			hero.sprite.operate(hero.pos);

			updateQuickslot();
		}
	}

	@Override
	public String info() {
		String info = super.info();

		if (volume == 0){
			info += "\n\n" + Messages.get(this, "desc_water");
		} else {
			info += "\n\n" + Messages.get(this, "desc_heal");
		}

		if (isFull()){
			info += "\n\n" + Messages.get(this, "desc_full");
		}

		return info;
	}

	public void empty() {
		volume = 0;
		updateQuickslot();
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	public boolean isFull() {
		return volume >= MAX_VOLUME;
	}

	public void collectDew( Dewdrop dew ) {

		GLog.i( Messages.get(this, "collected") );
		volume += dew.quantity;
		if (volume >= MAX_VOLUME) {
			volume = MAX_VOLUME;
			GLog.p( Messages.get(this, "full") );
		}

		updateQuickslot();
	}

	public void fill() {
		volume = MAX_VOLUME;
		updateQuickslot();
	}

	public boolean canUseSeed(Item item){
		return item instanceof Plant.Seed;
	}

	@Override
	public String status() {
		return Messages.format( TXT_STATUS, volume, MAX_VOLUME );
	}

	protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

		@Override
		public String textPrompt() {
			return Messages.get(Waterskin.class, "seed");
		}

		@Override
		public Class<?extends Bag> preferredBag(){
			return VelvetPouch.class;
		}

		@Override
		public boolean itemSelectable(Item item) {
			return canUseSeed(item);
		}

		@Override
		public void onSelect( Item item ) {
			boolean isMastered = hero.pointsInTalent(Talent.HERB_EXTRACTION) == 2;
			if (item instanceof Plant.Seed) {
				if (item instanceof Blindweed.Seed) {
					Buff.affect(curUser, Invisibility.class, volume*2);
					if (!(isMastered && Random.Int(2) < 1)) {
						item.detach(hero.belongings.backpack);
					}
				}
				if (item instanceof Earthroot.Seed) {
					Buff.affect(curUser, Barkskin.class).set(Math.round(((hero.lvl)*volume)/20f + (Dungeon.depth*volume)/4f), 1);
					if (!(isMastered && Random.Int(2) < 1)) {
						item.detach(hero.belongings.backpack);
					}
				}
				if (item instanceof Fadeleaf.Seed) {
					Buff.affect(curUser, MindVision.class, volume);
					if (!(isMastered && Random.Int(2) < 1)) {
						item.detach(hero.belongings.backpack);
					}
				}
				if (item instanceof Firebloom.Seed) {
					Buff.affect(curUser, FireImbue.class).set(volume/4f);
					if (!(isMastered && Random.Int(2) < 1)) {
						item.detach(hero.belongings.backpack);
					}
				}
				if (item instanceof Icecap.Seed) {
					Buff.affect(curUser, FrostImbue.class, Math.round(volume/2f));
					if (!(isMastered && Random.Int(2) < 1)) {
						item.detach(hero.belongings.backpack);
					}
				}
				if (item instanceof Mageroyal.Seed) {
					Buff.affect(curUser, BlobImmunity.class, volume*2);
					if (!(isMastered && Random.Int(2) < 1)) {
						item.detach(hero.belongings.backpack);
					}
				}
				if (item instanceof Rotberry.Seed) {
					Buff.affect(curUser, AdrenalineSurge.class).reset(1, (AdrenalineSurge.DURATION/20)*volume);
				}
				if (item instanceof Sorrowmoss.Seed) {
					Buff.affect(curUser, ToxicImbue.class).set(volume/2f);
					if (!(isMastered && Random.Int(2) < 1)) {
						item.detach(hero.belongings.backpack);
					}
				}
				if (item instanceof Starflower.Seed) {
					Buff.affect(curUser, Bless.class, volume*3);
					if (!(isMastered && Random.Int(2) < 1)) {
						item.detach(hero.belongings.backpack);
					}
				}
				if (item instanceof Stormvine.Seed) {
					Buff.affect(curUser, Levitation.class, volume*2);
					if (!(isMastered && Random.Int(2) < 1)) {
						item.detach(hero.belongings.backpack);
					}
				}
				if (item instanceof Sungrass.Seed) {
					Buff.affect(curUser, Healing.class).setHeal((hero.HT/10)*volume, 0, 1);
					if (!(isMastered && Random.Int(2) < 1)) {
						item.detach(hero.belongings.backpack);
					}
				}
				if (item instanceof Swiftthistle.Seed) {
					Buff.affect(curUser, Haste.class, volume*2);
					if (!(isMastered && Random.Int(2) < 1)) {
						item.detach(hero.belongings.backpack);
					}
				}

				drink();
			}
		}
	};
}
