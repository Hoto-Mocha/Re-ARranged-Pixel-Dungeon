package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArmorEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.KnightsBlocking;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ParalysisTracker;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ShieldCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WeaponEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.MagicalHolster;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class KnightsShield extends Item {

	{
		image = ItemSpriteSheet.KNIGHT_SHIELD;

		defaultAction = AC_SHIELD;

		bones = false;
		unique = true;
	}

	private static final String AC_SHIELD = "SHIELD";

	@Override
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_SHIELD );
		return actions;
	}

	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals(AC_SHIELD)) {
			if (hero.buff(ShieldCoolDown.class) != null) {
				GLog.w(Messages.get(this, "cooldown"));
			} else if (hero.belongings.armor == null) {
				GLog.w(Messages.get(this, "no_armor"));
			} else {
				hero.sprite.operate(hero.pos);
				hero.spendAndNext(Actor.TICK);
				Sample.INSTANCE.play(Assets.Sounds.MISS);
				Buff.affect(hero, KnightsBlocking.class).set( hero.lvl + hero.belongings.armor.buffedLvl(), 1);
				Buff.affect(hero, ShieldCoolDown.class).set();
				if (hero.hasTalent(Talent.BLOCKING)) {
					Buff.affect(hero, ArmorEmpower.class).set(hero.pointsInTalent(Talent.BLOCKING), 10f);
				}
				if (hero.hasTalent(Talent.IMPREGNABLE_WALL)) {
					Buff.affect(hero, Barrier.class).setShield(Math.round(((hero.lvl + hero.belongings.armor.buffedLvl())/3f)*hero.pointsInTalent(Talent.IMPREGNABLE_WALL)));
				}
				if (hero.hasTalent(Talent.SHIELD_SLAM) && Random.Int(3) < hero.pointsInTalent(Talent.SHIELD_SLAM)) {
					Buff.affect(hero, ParalysisTracker.class);
				}
				if (hero.hasTalent(Talent.HOLY_SHIELD)) {
					Buff.affect(hero, Bless.class, 3*hero.pointsInTalent(Talent.HOLY_SHIELD));
				}
				GLog.p(Messages.get(this, "shield"));
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
}
