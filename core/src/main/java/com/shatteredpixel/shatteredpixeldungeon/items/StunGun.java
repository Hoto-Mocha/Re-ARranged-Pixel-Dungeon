package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class StunGun extends Item {

	{
		image = ItemSpriteSheet.STUN_GUN;

		defaultAction = AC_USE;
		usesTargeting = false;

		bones = false;
		unique = true;
	}

	private static final String AC_USE = "USE";

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
			if (hero.buff(StunGunCooldown.class) == null) {
				Buff.affect(curUser, StunningTracker.class, (Dungeon.hero.lvl == 30) ? 12 : 5+(Dungeon.hero.lvl/5));
				Buff.affect(curUser, StunGunCooldown.class, StunGunCooldown.DURATION);
				GLog.p(Messages.get(this, "stun"));
				curUser.sprite.operate(curUser.pos);
				curUser.spendAndNext(Actor.TICK);
				curUser.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
				Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
			} else {
				GLog.w(Messages.get(this, "cooldown"));
			}
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

	@Override
	public String info() {
		return Messages.get(this, "desc", (Dungeon.hero.lvl == 30) ? 12 : 5+(Dungeon.hero.lvl/5));
	}

	public static class StunGunCooldown extends FlavourBuff {

		{
			type = buffType.NEUTRAL;
			announced = false;
		}

		public static final float DURATION = 100f;

		@Override
		public int icon() {
			return BuffIndicator.TIME;
		}

		@Override
		public void tintIcon(Image icon) {
			icon.hardlight(0xFFFF58);
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

	public static class StunningTracker extends FlavourBuff {

		{
			type = buffType.POSITIVE;
			announced = true;
		}

		public static final float DURATION = 10f;

		@Override
		public int icon() {
			return BuffIndicator.STUNNING;
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
