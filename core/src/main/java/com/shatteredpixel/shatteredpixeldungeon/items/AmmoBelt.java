package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.GunEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.HealingArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PoisonParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AmmoBelt extends Item {

	{
		image = ItemSpriteSheet.CARTRIDGE_BELT;

		defaultAction = AC_USE;
		usesTargeting = true;

		bones = false;
		unique = true;
	}

	private static final String AC_USE = "SHOOT";

	@Override
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_USE );
		return actions;
	}

	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals(AC_USE)) {
			curUser = hero;
			curItem = this;
			hero.busy();
			hero.sprite.operate(hero.pos);
			GLog.p(Messages.get(this, "empower"));
			hero.spend(Actor.TICK);
			GunEmpower buff = hero.buff(GunEmpower.class);
			if (buff != null) {
				Buff.affect(curUser, GunEmpower.class).levelUp();
			} else {
				Buff.affect(curUser, GunEmpower.class).reset(1, GunEmpower.DURATION);
			}
			OverHeat overHeat = hero.buff(OverHeat.class);
			if (buff != null) {
				Buff.affect(curUser, OverHeat.class).levelUp();
			} else {
				Buff.affect(curUser, OverHeat.class).reset(1, OverHeat.DURATION);
			}
			Item.updateQuickslot();
		}
	}

	public static class OverHeat extends Buff {

		public static float DURATION = 200f;

		{
			type = buffType.POSITIVE;
		}

		private int heat;
		public static float chance;
		private float interval;

		public void reset(int heat, float interval){
			this.heat = heat;
			this.interval = interval;
			switch (heat) {
				case 1: default:
					chance = 1/100f;
					break;
				case 2:
					chance = 1/10f;
					break;
				case 3:
					chance = 1/3f;
					break;
				case 4:
					chance = 1/2f;
					break;
				case 5:
					chance = 3/4f;
					break;
				case 6:
					chance = 9/10f;
					break;
			}
			spend(interval - cooldown());
		}

		public void levelUp() {
			if (Math.ceil(hero.lvl/5f) > heat) {
				this.heat ++;
			}
			this.interval = DURATION;
			switch (heat) {
				case 1: default:
					chance = 1/100f;
					break;
				case 2:
					chance = 1/10f;
					break;
				case 3:
					chance = 1/3f;
					break;
				case 4:
					chance = 1/2f;
					break;
				case 5:
					chance = 3/4f;
					break;
				case 6:
					chance = 9/10f;
					break;
			}
			spend(interval - cooldown());
		}

		public int boost(){
			return heat;
		}

		@Override
		public boolean act() {
			heat --;
			if (heat > 0){
				spend( interval );
			} else {
				detach();
			}
			switch (heat) {
				case 1: default:
					chance = 1/100f;
					break;
				case 2:
					chance = 1/10f;
					break;
				case 3:
					chance = 1/3f;
					break;
				case 4:
					chance = 1/2f;
					break;
				case 5:
					chance = 3/4f;
					break;
				case 6:
					chance = 9/10f;
					break;
			}
			updateQuickslot();
			return true;
		}

		@Override
		public int icon() {
			return BuffIndicator.TIME;
		}

		@Override
		public void tintIcon(Image icon) {
			icon.hardlight(1f, 0, 0);
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, (DURATION - visualcooldown()) / DURATION);
		}

		@Override
		public String iconTextDisplay() {
			return Integer.toString((int)visualcooldown());
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc", heat, new DecimalFormat("#.#").format(chance*100), dispTurns(visualcooldown()));
		}

		private static final String HEAT	    = "heat";
		private static final String CHANCE	    = "chance";
		private static final String INTERVAL	    = "interval";

		@Override
		public void storeInBundle( Bundle bundle ) {
			super.storeInBundle( bundle );
			bundle.put( HEAT, heat );
			bundle.put( CHANCE, chance );
			bundle.put( INTERVAL, interval );
		}

		@Override
		public void restoreFromBundle( Bundle bundle ) {
			super.restoreFromBundle( bundle );
			heat = bundle.getInt( HEAT );
			chance = bundle.getFloat( CHANCE );
			interval = bundle.getFloat(INTERVAL);
		}
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int value() {
		return -1;
	}
}
