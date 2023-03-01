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
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Focusing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.InfiniteBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Momentum;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.Riot;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.GoldenBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.NaturesBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.PoisonBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.WindBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Gun extends MeleeWeapon {
	public static final String AC_SHOOT		= "SHOOT";
	public static final String AC_RELOAD = "RELOAD";

	public int max_round;
	public int round;
	public boolean silencer = false;
	public boolean short_barrel = false;
	public boolean long_barrel = false;
	public boolean magazine = false;
	public boolean light = false;
	public boolean heavy = false;
	public boolean flash = false;
	public float reload_time;
	public static final String TXT_STATUS = "%d/%d";

	{
		defaultAction = AC_SHOOT;
		usesTargeting = true;

		hitSound = Assets.Sounds.HIT_CRUSH;
		hitSoundPitch = 0.8f;

		bones = false;
	}

	private static final String ROUND = "round";
	private static final String MAX_ROUND = "max_round";
	private static final String RELOAD_TIME = "reload_time";
	private static final String SILENCER = "silencer";
	private static final String SHORT_BARREL = "short_barrel";
	private static final String LONG_BARREL = "long_barrel";
	private static final String MAGAZINE = "magazine";
	private static final String LIGHT = "light";
	private static final String HEAVY = "heavy";
	private static final String FLASH = "flash";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(MAX_ROUND, max_round);
		bundle.put(ROUND, round);
		bundle.put(RELOAD_TIME, reload_time);
		bundle.put(SILENCER, silencer);
		bundle.put(SHORT_BARREL, short_barrel);
		bundle.put(LONG_BARREL, long_barrel);
		bundle.put(MAGAZINE, magazine);
		bundle.put(LIGHT, light);
		bundle.put(HEAVY, heavy);
		bundle.put(FLASH, flash);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		max_round = bundle.getInt(MAX_ROUND);
		round = bundle.getInt(ROUND);
		reload_time = bundle.getFloat(RELOAD_TIME);
		silencer = bundle.getBoolean(SILENCER);
		short_barrel = bundle.getBoolean(SHORT_BARREL);
		long_barrel = bundle.getBoolean(LONG_BARREL);
		magazine = bundle.getBoolean(MAGAZINE);
		light = bundle.getBoolean(LIGHT);
		heavy = bundle.getBoolean(HEAVY);
		flash = bundle.getBoolean(FLASH);
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped( hero )) {
			actions.add(AC_SHOOT);
			actions.add(AC_RELOAD);
		}
		return actions;
	}

	public void reload() {
	}

	public int getRound() { return round; }

	@Override
	public int STRReq(int lvl) {
		int needSTR = STRReq(tier, lvl);
		if (heavy) {
			needSTR += 2;
		}
		if (light) {
			needSTR -= 2;
		}
		return needSTR;
	}

	public int Bulletmin(int lvl) {
		return 0;
	}

	public int Bulletmax(int lvl) {
		return 0;
	}

	@Override
	public String info() {
		String info = desc();
		if (levelKnown) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_known", tier, augment.damageFactor(min()), augment.damageFactor(max()), STRReq());
			if (STRReq() > hero.STR()) {
				info += " " + Messages.get(Weapon.class, "too_heavy");
			} else if (hero.STR() > STRReq()){
				info += " " + Messages.get(Weapon.class, "excess_str", hero.STR() - STRReq());
			}
			info += "\n\n" + Messages.get(this, "stats_known",
					Bulletmin(this.buffedLvl()),
					Bulletmax(this.buffedLvl()),
					round, max_round, new DecimalFormat("#.##").format(reload_time));
		} else {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_unknown", tier, min(0), max(0), STRReq(0));
			if (STRReq(0) > hero.STR()) {
				info += " " + Messages.get(MeleeWeapon.class, "probably_too_heavy");
			}
			info += "\n\n" + Messages.get(Gun.class, "stats_unknown",
					Bulletmin(0),
					Bulletmax(0),
					round, max_round, new DecimalFormat("#.##").format(reload_time));
		}

		String statsInfo = statsInfo();
		if (!statsInfo.equals("")) info += "\n\n" + statsInfo;

		switch (augment) {
			case SPEED:
				info += " " + Messages.get(Weapon.class, "faster");
				break;
			case DAMAGE:
				info += " " + Messages.get(Weapon.class, "stronger");
				break;
			case NONE:
		}

		if (enchantment != null && (cursedKnown || !enchantment.curse())){
			info += "\n\n" + Messages.get(Weapon.class, "enchanted", enchantment.name());
			info += " " + Messages.get(enchantment, "desc");
		}

		if (cursed && isEquipped( hero )) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed");
		} else if (!isIdentified() && cursedKnown){
			info += "\n\n" + Messages.get(Weapon.class, "not_cursed");
		}

		if (silencer) {
			info += "\n\n" + Messages.get(Gun.class, "silencer");
		}
		if (short_barrel) {
			info += "\n\n" + Messages.get(Gun.class, "short_barrel");
		}
		if (long_barrel) {
			info += "\n\n" + Messages.get(Gun.class, "long_barrel");
		}
		if (magazine) {
			info += "\n\n" + Messages.get(Gun.class, "magazine");
		}
		if (light) {
			info += "\n\n" + Messages.get(Gun.class, "light");
		}
		if (heavy) {
			info += "\n\n" + Messages.get(Gun.class, "heavy");
		}
		if (flash) {
			info += "\n\n" + Messages.get(Gun.class, "flash");
		}

		if (Dungeon.isChallenged(Challenges.DURABILITY) && levelKnown) {
			info += "\n\n" + Messages.get(Item.class, "durability_weapon", durability(), maxDurability());
		}

		return info;
	}

	@Override
	public int targetingPos(Hero user, int dst) {
		return knockBullet().targetingPos(user, dst);
	}

	@Override
	protected float baseDelay(Char owner) {
		float delay = augment.delayFactor(this.DLY);
		if (owner instanceof Hero) {
			int encumbrance = STRReq() - ((Hero)owner).STR();
			if (encumbrance > 0){
				delay *= Math.pow( 1.2, encumbrance );
			}
		}
		if (Dungeon.hero.hasTalent(Talent.MARTIAL_ARTS)) {
			delay -= 0.1f * Dungeon.hero.pointsInTalent(Talent.MARTIAL_ARTS);
		}
		return delay;
	}

	public Bullet knockBullet(){
		return new Bullet();
	}
	public class Bullet extends MissileWeapon {

		{
			image = ItemSpriteSheet.SINGLE_BULLET;

			hitSound = Assets.Sounds.PUFF;
			tier = Gun.this.tier;
		}

		@Override
		public int buffedLvl(){
			return Gun.this.buffedLvl();
		}

		@Override
		public int damageRoll(Char owner) {
			Hero hero = (Hero)owner;
			int bulletdamage = Random.NormalIntRange(Bulletmin(Gun.this.buffedLvl()),
					Bulletmax(Gun.this.buffedLvl()));

			if (owner.buff(Momentum.class) != null && owner.buff(Momentum.class).freerunning()) {
				bulletdamage = Math.round(bulletdamage * (1f + 0.15f * ((Hero) owner).pointsInTalent(Talent.PROJECTILE_MOMENTUM)));
			}

			if (owner.buff(Focusing.class) != null) {
				bulletdamage = Math.round(bulletdamage * (1.10f + 0.05f * ((Hero) owner).pointsInTalent(Talent.ARM_VETERAN)));
			}
			return bulletdamage;
		}

		@Override
		public boolean hasEnchant(Class<? extends Enchantment> type, Char owner) {
			return Gun.this.hasEnchant(type, owner);
		}

		@Override
		public int proc(Char attacker, Char defender, int damage) {
			SpiritBow bow = hero.belongings.getItem(SpiritBow.class);
			WindBow bow2 = hero.belongings.getItem(WindBow.class);
			GoldenBow bow3 = hero.belongings.getItem(GoldenBow.class);
			NaturesBow bow4 = hero.belongings.getItem(NaturesBow.class);
			PoisonBow bow5 = hero.belongings.getItem(PoisonBow.class);
			if (Gun.this.enchantment == null
					&& Random.Int(3) < hero.pointsInTalent(Talent.SHARED_ENCHANTMENT)
					&& hero.buff(MagicImmune.class) == null
					&& bow != null
					&& bow.enchantment != null) {
				return bow.enchantment.proc(this, attacker, defender, damage);
			} else if (Gun.this.enchantment == null
					&& Random.Int(3) < hero.pointsInTalent(Talent.SHARED_ENCHANTMENT)
					&& hero.buff(MagicImmune.class) == null
					&& bow2 != null
					&& bow2.enchantment != null) {
				return bow2.enchantment.proc(this, attacker, defender, damage);
			} else if (Gun.this.enchantment == null
					&& Random.Int(3) < hero.pointsInTalent(Talent.SHARED_ENCHANTMENT)
					&& hero.buff(MagicImmune.class) == null
					&& bow3 != null
					&& bow3.enchantment != null) {
				return bow3.enchantment.proc(this, attacker, defender, damage);
			} else if (Gun.this.enchantment == null
					&& Random.Int(3) < hero.pointsInTalent(Talent.SHARED_ENCHANTMENT)
					&& hero.buff(MagicImmune.class) == null
					&& bow4 != null
					&& bow4.enchantment != null) {
				return bow4.enchantment.proc(this, attacker, defender, damage);
			} else if (Gun.this.enchantment == null
					&& Random.Int(3) < hero.pointsInTalent(Talent.SHARED_ENCHANTMENT)
					&& hero.buff(MagicImmune.class) == null
					&& bow5 != null
					&& bow5.enchantment != null) {
				return bow5.enchantment.proc(this, attacker, defender, damage);
			} else {
				return Gun.this.proc(attacker, defender, damage);
			}
		}

		@Override
		public float delayFactor(Char user) {
			if (hero.subClass == HeroSubClass.GUNSLINGER && hero.justMoved) {
				return 0;
			} else {
				if (hero.buff(Riot.riotTracker.class) != null) {
					return Gun.this.delayFactor(user)/2f;
				} else {
					return Gun.this.delayFactor(user);
				}
			}
		}

		@Override
		public float accuracyFactor(Char owner, Char target) {
			float accFactor = super.accuracyFactor(owner, target);
			if (Dungeon.hero.hasTalent(Talent.ENHANCED_FOCUSING)) {
				accFactor += 0.1f * Dungeon.hero.pointsInTalent(Talent.ENHANCED_FOCUSING);
			}
			return accFactor;
		}

		@Override
		public int STRReq(int lvl) {
			return Gun.this.STRReq();
		}

		@Override
		protected void onThrow( int cell ) {
			Char enemy = Actor.findChar( cell );
			if (enemy == null || enemy == curUser) {
				parent = null;
				CellEmitter.get(cell).burst(SmokeParticle.FACTORY, 2);
				CellEmitter.center(cell).burst(BlastParticle.FACTORY, 2);
			} else {
				if (!curUser.shoot( enemy, this )) {
					CellEmitter.get(cell).burst(SmokeParticle.FACTORY, 2);
					CellEmitter.center(cell).burst(BlastParticle.FACTORY, 2);
				}
			}
			if (hero.buff(InfiniteBullet.class) != null
					|| (hero.buff(Riot.riotTracker.class) != null && Random.Int(10) <= hero.pointsInTalent(Talent.ROUND_PRESERVE)-1)) {
				//round preserves
			} else {
				round --;
			}
			for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
				if (mob.paralysed <= 0
						&& Dungeon.level.distance(curUser.pos, mob.pos) <= 4
						&& mob.state != mob.HUNTING
						&& !silencer) {
					mob.beckon( curUser.pos );
				}
			}
			updateQuickslot();
			if (Dungeon.isChallenged(Challenges.DURABILITY)) {
				Gun.this.use();
			}
		}

		@Override
		public void throwSound() {
			Sample.INSTANCE.play( Assets.Sounds.HIT_CRUSH, 1, Random.Float(0.33f, 0.66f) );
		}

		@Override
		public void cast(final Hero user, final int dst) {
			super.cast(user, dst);
		}
	}
}
