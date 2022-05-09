package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.EnergyParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;

import java.util.ArrayList;

public class StunGunHP extends Item {

	{
		image = ItemSpriteSheet.STUN_GUN_HP;

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
			if (curUser.buff(StunGun.StunGunCooldown.class) == null) {
				Buff.affect(curUser, StunGun.StunningTracker.class, (Dungeon.hero.lvl == 30) ? 6 : 2+(Dungeon.hero.lvl/10));
				Buff.prolong(curUser, Recharging.class, 10+5*(Dungeon.hero.lvl/5)-1);
				Buff.affect(curUser, StunGun.StunGunCooldown.class, StunGun.StunGunCooldown.DURATION/2-1);
				GLog.p(Messages.get(StunGun.class, "stun"));
				curUser.sprite.operate(curUser.pos);
				curUser.sprite.centerEmitter().burst(SparkParticle.FACTORY, 2);
				Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
				SpellSprite.show( curUser, SpellSprite.CHARGE );
				Emitter e = curUser.sprite.centerEmitter();
				if (e != null) e.burst( EnergyParticle.FACTORY, 15 );
			} else {
				GLog.w(Messages.get(StunGun.class, "cooldown"));
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
		return Messages.get(this, "desc", (Dungeon.hero.lvl == 30) ? 6 : 2+(Dungeon.hero.lvl/10)+1, 10+5*(Dungeon.hero.lvl/5));
	}


}
