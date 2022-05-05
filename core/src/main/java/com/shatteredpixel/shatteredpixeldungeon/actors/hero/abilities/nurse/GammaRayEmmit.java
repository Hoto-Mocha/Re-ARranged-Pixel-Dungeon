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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.nurse;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Combo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Focusing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.Ratmogrify;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Acidic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Albino;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Bandit;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Bat;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Brute;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.CausticSlime;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Crab;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM100;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM200;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM201;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Elemental;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Eye;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.FetidRat;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Ghoul;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Gnoll;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Golem;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Guard;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Medic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Monk;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Necromancer;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Rat;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Researcher;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.SWAT;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Scorpio;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Senior;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Shaman;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Skeleton;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Slime;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Snake;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Soldier;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.SpectralNecromancer;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Spinner;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Succubus;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Supression;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Swarm;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Tank;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Thief;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Warlock;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Handgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Magnum;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RPG7;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RocketLauncher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SPAS;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SPASAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SPASHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.ui.TargetHealthIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class GammaRayEmmit extends ArmorAbility {

	{
		baseChargeUse = 50f;
	}

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

	@Override
	protected void activate(ClassArmor armor, Hero hero, Integer target) {

		if (target == null){
			return;
		}

		Char ch = Actor.findChar(target);

		if (ch == null) {
			GLog.w(Messages.get(this, "no_target"));
			return;
		} else if (ch == hero){
			GLog.w(Messages.get(this, "self_target"));
			return;
		} else if (ch.alignment != Char.Alignment.ENEMY || !(ch instanceof Mob)){
			GLog.w(Messages.get(this, "cant_transform"));
			return;
		} else if (Char.hasProp(ch, Char.Property.MINIBOSS) || Char.hasProp(ch, Char.Property.BOSS)){
			GLog.w(Messages.get(this, "too_strong"));
			return;
		} else {
			Mob enemy;
			int enemyproc = Random.Int(100);
			switch (hero.pointsInTalent(Talent.TRANSMOG_BIAS)) {
				case 0: default:
					if (enemyproc < 2) {
						enemy = Random.oneOf(
								new Rat(),
								new Albino(),
								new Snake(),
								new Gnoll(),
								new Swarm(),
								new Crab(),
								new Slime(),
								new CausticSlime());
					} else if (enemyproc < 5) {
						enemy = Random.oneOf(
								new Skeleton(),
								new Thief(),
								new Bandit(),
								new DM100(),
								new Guard(),
								new Necromancer(),
								new SpectralNecromancer());
					} else if (enemyproc < 10) {
						enemy = Random.oneOf(
								new Bat(),
								new Brute(),
								new Shaman.RedShaman(),
								new Shaman.BlueShaman(),
								new Shaman.PurpleShaman(),
								new Spinner(),
								new DM200(),
								new DM201());
					} else if (enemyproc < 20) {
						enemy = Random.oneOf(
								new Elemental.FireElemental(),
								new Elemental.FrostElemental(),
								new Elemental.ShockElemental(),
								new Elemental.ChaosElemental(),
								new Warlock(),
								new Ghoul(),
								new Monk(),
								new Senior(),
								new Golem());
					} else if (enemyproc < 50) {
						enemy = Random.oneOf(
								new Succubus(),
								new Eye(),
								new Scorpio(),
								new Acidic());
					} else /*if (enemyproc < 100)*/ {
						enemy = Random.oneOf(
								new Soldier(),
								new SWAT(),
								new Researcher(),
								new Medic(),
								new Supression(),
								new Tank());
					}
					break;
				case 1:
					if (enemyproc < 5) {
						enemy = Random.oneOf(
								new Rat(),
								new Albino(),
								new Snake(),
								new Gnoll(),
								new Swarm(),
								new Crab(),
								new Slime(),
								new CausticSlime());
					} else if (enemyproc < 11) {
						enemy = Random.oneOf(
								new Skeleton(),
								new Thief(),
								new Bandit(),
								new DM100(),
								new Guard(),
								new Necromancer(),
								new SpectralNecromancer());
					} else if (enemyproc < 20) {
						enemy = Random.oneOf(
								new Bat(),
								new Brute(),
								new Shaman.RedShaman(),
								new Shaman.BlueShaman(),
								new Shaman.PurpleShaman(),
								new Spinner(),
								new DM200(),
								new DM201());
					} else if (enemyproc < 35) {
						enemy = Random.oneOf(
								new Elemental.FireElemental(),
								new Elemental.FrostElemental(),
								new Elemental.ShockElemental(),
								new Elemental.ChaosElemental(),
								new Warlock(),
								new Ghoul(),
								new Monk(),
								new Senior(),
								new Golem());
					} else if (enemyproc < 60) {
						enemy = Random.oneOf(
								new Succubus(),
								new Eye(),
								new Scorpio(),
								new Acidic());
					} else /*if (enemyproc < 100)*/ {
						enemy = Random.oneOf(
								new Soldier(),
								new SWAT(),
								new Researcher(),
								new Medic(),
								new Supression(),
								new Tank());
					}
					break;
				case 2:
					if (enemyproc < 8) {
						enemy = Random.oneOf(
								new Rat(),
								new Albino(),
								new Snake(),
								new Gnoll(),
								new Swarm(),
								new Crab(),
								new Slime(),
								new CausticSlime());
					} else if (enemyproc < 17) {
						enemy = Random.oneOf(
								new Skeleton(),
								new Thief(),
								new Bandit(),
								new DM100(),
								new Guard(),
								new Necromancer(),
								new SpectralNecromancer());
					} else if (enemyproc < 30) {
						enemy = Random.oneOf(
								new Bat(),
								new Brute(),
								new Shaman.RedShaman(),
								new Shaman.BlueShaman(),
								new Shaman.PurpleShaman(),
								new Spinner(),
								new DM200(),
								new DM201());
					} else if (enemyproc < 50) {
						enemy = Random.oneOf(
								new Elemental.FireElemental(),
								new Elemental.FrostElemental(),
								new Elemental.ShockElemental(),
								new Elemental.ChaosElemental(),
								new Warlock(),
								new Ghoul(),
								new Monk(),
								new Senior(),
								new Golem());
					} else if (enemyproc < 70) {
						enemy = Random.oneOf(
								new Succubus(),
								new Eye(),
								new Scorpio(),
								new Acidic());
					} else /*if (enemyproc < 100)*/ {
						enemy = Random.oneOf(
								new Soldier(),
								new SWAT(),
								new Researcher(),
								new Medic(),
								new Supression(),
								new Tank());
					}
					break;
				case 3:
					if (enemyproc < 11) {
						enemy = Random.oneOf(
								new Rat(),
								new Albino(),
								new Snake(),
								new Gnoll(),
								new Swarm(),
								new Crab(),
								new Slime(),
								new CausticSlime());
					} else if (enemyproc < 23) {
						enemy = Random.oneOf(
								new Skeleton(),
								new Thief(),
								new Bandit(),
								new DM100(),
								new Guard(),
								new Necromancer(),
								new SpectralNecromancer());
					} else if (enemyproc < 40) {
						enemy = Random.oneOf(
								new Bat(),
								new Brute(),
								new Shaman.RedShaman(),
								new Shaman.BlueShaman(),
								new Shaman.PurpleShaman(),
								new Spinner(),
								new DM200(),
								new DM201());
					} else if (enemyproc < 65) {
						enemy = Random.oneOf(
								new Elemental.FireElemental(),
								new Elemental.FrostElemental(),
								new Elemental.ShockElemental(),
								new Elemental.ChaosElemental(),
								new Warlock(),
								new Ghoul(),
								new Monk(),
								new Senior(),
								new Golem());
					} else if (enemyproc < 80) {
						enemy = Random.oneOf(
								new Succubus(),
								new Eye(),
								new Scorpio(),
								new Acidic());
					} else /*if (enemyproc < 100)*/ {
						enemy = Random.oneOf(
								new Soldier(),
								new SWAT(),
								new Researcher(),
								new Medic(),
								new Supression(),
								new Tank());
					}
					break;
				case 4:
					if (enemyproc < 14) {
						enemy = Random.oneOf(
								new Rat(),
								new Albino(),
								new Snake(),
								new Gnoll(),
								new Swarm(),
								new Crab(),
								new Slime(),
								new CausticSlime());
					} else if (enemyproc < 29) {
						enemy = Random.oneOf(
								new Skeleton(),
								new Thief(),
								new Bandit(),
								new DM100(),
								new Guard(),
								new Necromancer(),
								new SpectralNecromancer());
					} else if (enemyproc < 50) {
						enemy = Random.oneOf(
								new Bat(),
								new Brute(),
								new Shaman.RedShaman(),
								new Shaman.BlueShaman(),
								new Shaman.PurpleShaman(),
								new Spinner(),
								new DM200(),
								new DM201());
					} else if (enemyproc < 80) {
						enemy = Random.oneOf(
								new Elemental.FireElemental(),
								new Elemental.FrostElemental(),
								new Elemental.ShockElemental(),
								new Elemental.ChaosElemental(),
								new Warlock(),
								new Ghoul(),
								new Monk(),
								new Senior(),
								new Golem());
					} else if (enemyproc < 90) {
						enemy = Random.oneOf(
								new Succubus(),
								new Eye(),
								new Scorpio(),
								new Acidic());
					} else /*if (enemyproc < 100)*/ {
						enemy = Random.oneOf(
								new Soldier(),
								new SWAT(),
								new Researcher(),
								new Medic(),
								new Supression(),
								new Tank());
					}
			}
			//1 chapter enemy = 2% + point*3%, max +12%, max 14%
			//2 chapter enemy = 3% + point*3%, max +12%, max 15%
			//3 chapter enemy = 5% + point*4%, max +16%, max 21%
			//4 chapter enemy = 10% + point*5%, max +20%, max 30%
			//5 chapter enemy = 30%	- point*5%, max -20%, max 10%
			//6 chapter enemy = 50% - point*10%, max -40%, max 10%
			//https://docs.google.com/spreadsheets/d/1mV4shXhdvJgxd0fJKqh0UIHfAX9ruS5C7yJtWYmEEE4/edit?usp=sharing check the table
			enemy.pos = ch.pos;

			Actor.remove( ch );
			ch.sprite.killAndErase();
			Dungeon.level.mobs.remove(ch);

			GameScene.add(enemy);

			TargetHealthIndicator.instance.target(null);
			CellEmitter.get(enemy.pos).burst(Speck.factory(Speck.WOOL), 4);
			Sample.INSTANCE.play(Assets.Sounds.PUFF);

			Dungeon.level.occupyCell(enemy);
		}

		armor.charge -= chargeUse(hero);
		armor.updateQuickslot();
		Invisibility.dispel();
		hero.spendAndNext(Actor.TICK);

	}

	@Override
	public int icon() {
		return HeroIcon.GAMMA_EMMIT;
	}

	@Override
	public Talent[] talents() {
		return new Talent[]{Talent.TRANSMOG_BIAS, Talent.IMPRINTING_EFFECT, Talent.SHEEP_TRANSMOG, Talent.HEROIC_ENERGY};
	}
}
