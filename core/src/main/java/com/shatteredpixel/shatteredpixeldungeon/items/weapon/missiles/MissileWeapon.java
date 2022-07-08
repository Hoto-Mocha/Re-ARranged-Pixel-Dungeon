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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Momentum;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PinCushion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RevealedArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PurpleParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.MagicalHolster;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.GoldenBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.NaturesBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.PoisonBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.WindBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Projecting;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Carbine;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CarbineAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CarbineHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrower;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrowerAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrowerHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FrostGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncherAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncherHP;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MissileButton;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ParalysisGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannonAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannonHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RPG7;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Revolver;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RevolverAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RevolverHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RocketLauncher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSG;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSGAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSGHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SleepGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Disintegration;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000AP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000HP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.Dart;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

abstract public class MissileWeapon extends Weapon {

	{
		stackable = true;
		levelKnown = true;
		
		bones = true;

		defaultAction = AC_THROW;
		usesTargeting = true;
	}
	
	protected boolean sticky = true;
	
	public static final float MAX_DURABILITY = 100;
	protected float durability = MAX_DURABILITY;
	protected float baseUses = 10;
	
	public boolean holster;
	
	//used to reduce durability from the source weapon stack, rather than the one being thrown.
	protected MissileWeapon parent;
	
	public int tier;
	
	@Override
	public int min() {
		return Math.max(0, min( buffedLvl() + RingOfSharpshooting.levelDamageBonus(hero) ));
	}
	
	@Override
	public int min(int lvl) {
		return  2 * tier +                      //base
				(tier == 1 ? lvl : 2*lvl);      //level scaling
	}
	
	@Override
	public int max() {
		return Math.max(0, max( buffedLvl() + RingOfSharpshooting.levelDamageBonus(hero) ));
	}
	
	@Override
	public int max(int lvl) {
		return  5 * tier +                      //base
				(tier == 1 ? 2*lvl : tier*lvl); //level scaling
	}
	
	public int STRReq(int lvl){
		return STRReq(tier, lvl) - 1; //1 less str than normal for their tier
	}
	
	@Override
	//FIXME some logic here assumes the items are in the player's inventory. Might need to adjust
	public Item upgrade() {
		if (!bundleRestoring) {
			durability = MAX_DURABILITY;
			if (quantity > 1) {
				MissileWeapon upgraded = (MissileWeapon) split(1);
				upgraded.parent = null;
				
				upgraded = (MissileWeapon) upgraded.upgrade();
				
				//try to put the upgraded into inventory, if it didn't already merge
				if (upgraded.quantity() == 1 && !upgraded.collect()) {
					Dungeon.level.drop(upgraded, hero.pos);
				}
				updateQuickslot();
				return upgraded;
			} else {
				super.upgrade();
				
				Item similar = hero.belongings.getSimilar(this);
				if (similar != null){
					detach(hero.belongings.backpack);
					Item result = similar.merge(this);
					updateQuickslot();
					return result;
				}
				updateQuickslot();
				return this;
			}
			
		} else {
			return super.upgrade();
		}
	}

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.remove( AC_EQUIP );
		return actions;
	}
	
	@Override
	public boolean collect(Bag container) {
		if (container instanceof MagicalHolster) holster = true;
		return super.collect(container);
	}
	
	@Override
	public int throwPos(Hero user, int dst) {

		boolean projecting = hasEnchant(Projecting.class, user);
		if (this instanceof PlasmaCannon.Bullet
		||	this instanceof PlasmaCannonAP.Bullet
		||	this instanceof PlasmaCannonHP.Bullet
		||	this instanceof FlameThrower.Bullet
		||	this instanceof FlameThrowerAP.Bullet
		||	this instanceof FlameThrowerHP.Bullet
		||	this instanceof MissileButton.Rocket) {
			projecting = true;
		}

		if (!projecting && Random.Int(3) < user.pointsInTalent(Talent.SHARED_ENCHANTMENT)) {
			if (this instanceof Dart && ((Dart) this).crossbowHasEnchant(hero)) {
				//do nothing
			} else if (this instanceof FishingSpear && ((FishingSpear) this).ballistaHasEnchant(hero)) {
				//do nothing
			} else if (this instanceof ThrowingSpear && ((ThrowingSpear) this).ballistaHasEnchant(hero)) {
				//do nothing
			} else if (this instanceof Javelin && ((Javelin) this).ballistaHasEnchant(hero)) {
				//do nothing
			} else if (this instanceof Trident && ((Trident) this).ballistaHasEnchant(hero)) {
				//do nothing
			} else {
				SpiritBow bow = hero.belongings.getItem(SpiritBow.class);
				WindBow	bow2 = hero.belongings.getItem(WindBow.class);
				GoldenBow bow3 = hero.belongings.getItem(GoldenBow.class);
				NaturesBow bow4 = hero.belongings.getItem(NaturesBow.class);
				PoisonBow bow5 = hero.belongings.getItem(PoisonBow.class);
				if (bow != null && bow.hasEnchant(Projecting.class, user)) {
					projecting = true;
				}
				if (bow2 != null && bow2.hasEnchant(Projecting.class, user)) {
					projecting = true;
				}
				if (bow3 != null && bow3.hasEnchant(Projecting.class, user)) {
					projecting = true;
				}
				if (bow4 != null && bow4.hasEnchant(Projecting.class, user)) {
					projecting = true;
				}
				if (bow5 != null && bow5.hasEnchant(Projecting.class, user)) {
					projecting = true;
				}
			}
		}
		if ((this instanceof CrudePistol.Bullet
				|| this instanceof CrudePistolAP.Bullet
				|| this instanceof CrudePistolHP.Bullet
				|| this instanceof Pistol.Bullet
				|| this instanceof PistolAP.Bullet
				|| this instanceof PistolHP.Bullet
				|| this instanceof GoldenPistol.Bullet
				|| this instanceof GoldenPistolAP.Bullet
				|| this instanceof GoldenPistolHP.Bullet
				|| this instanceof Handgun.Bullet
				|| this instanceof HandgunAP.Bullet
				|| this instanceof HandgunHP.Bullet
				|| this instanceof Magnum.Bullet
				|| this instanceof MagnumAP.Bullet
				|| this instanceof MagnumHP.Bullet
				|| this instanceof TacticalHandgun.Bullet
				|| this instanceof TacticalHandgunAP.Bullet
				|| this instanceof TacticalHandgunHP.Bullet
				|| this instanceof AutoHandgun.Bullet
				|| this instanceof AutoHandgunAP.Bullet
				|| this instanceof AutoHandgunHP.Bullet

				|| this instanceof DualPistol.Bullet
				|| this instanceof DualPistolAP.Bullet
				|| this instanceof DualPistolHP.Bullet
				|| this instanceof SubMachinegun.Bullet
				|| this instanceof SubMachinegunAP.Bullet
				|| this instanceof SubMachinegunHP.Bullet
				|| this instanceof AssultRifle.Bullet
				|| this instanceof AssultRifleAP.Bullet
				|| this instanceof AssultRifleHP.Bullet
				|| this instanceof HeavyMachinegun.Bullet
				|| this instanceof HeavyMachinegunAP.Bullet
				|| this instanceof HeavyMachinegunHP.Bullet
				|| this instanceof MiniGun.Bullet
				|| this instanceof MiniGunAP.Bullet
				|| this instanceof MiniGunHP.Bullet
				|| this instanceof AutoRifle.Bullet
				|| this instanceof AutoRifleAP.Bullet
				|| this instanceof AutoRifleHP.Bullet

				|| this instanceof Revolver.Bullet
				|| this instanceof RevolverAP.Bullet
				|| this instanceof RevolverHP.Bullet
				|| this instanceof HuntingRifle.Bullet
				|| this instanceof HuntingRifleAP.Bullet
				|| this instanceof HuntingRifleHP.Bullet
				|| this instanceof Carbine.Bullet
				|| this instanceof CarbineAP.Bullet
				|| this instanceof CarbineHP.Bullet
				|| this instanceof SniperRifle.Bullet
				|| this instanceof SniperRifleAP.Bullet
				|| this instanceof SniperRifleHP.Bullet
				|| this instanceof AntimaterRifle.Bullet
				|| this instanceof AntimaterRifleAP.Bullet
				|| this instanceof AntimaterRifleHP.Bullet
				|| this instanceof MarksmanRifle.Bullet
				|| this instanceof MarksmanRifleAP.Bullet
				|| this instanceof MarksmanRifleHP.Bullet
				|| this instanceof WA2000.Bullet
				|| this instanceof WA2000AP.Bullet
				|| this instanceof WA2000HP.Bullet

				|| this instanceof ShotGun.Bullet
				|| this instanceof ShotGunAP.Bullet
				|| this instanceof ShotGunHP.Bullet
				|| this instanceof KSG.Bullet
				|| this instanceof KSGAP.Bullet
				|| this instanceof KSGHP.Bullet

				|| this instanceof RocketLauncher.Rocket
				|| this instanceof RPG7.Rocket

				|| this instanceof GrenadeLauncher.Rocket
				|| this instanceof GrenadeLauncherAP.Rocket
				|| this instanceof GrenadeLauncherHP.Rocket
				|| this instanceof SleepGun.Dart
				|| this instanceof ParalysisGun.Dart
				|| this instanceof FrostGun.Dart)
				&& user.hasTalent(Talent.STREET_BATTLE)){
			if (!Dungeon.level.solid[dst]
					&& Dungeon.level.distance(user.pos, dst) <= ((projecting) ? 4+user.pointsInTalent(Talent.STREET_BATTLE) : 1+user.pointsInTalent(Talent.STREET_BATTLE))
					&& user.buff(Talent.StreetBattleCooldown.class) == null){
				Buff.affect(user, Talent.StreetBattleCooldown.class, 40-10*Dungeon.hero.pointsInTalent(Talent.STREET_BATTLE));
				return dst;
			} else {
				return super.throwPos(user, dst);
			}
		}

		if (hero.belongings.weapon instanceof SpellBook_Disintegration) {
			if (!Dungeon.level.solid[dst]
					&& Dungeon.level.distance(user.pos, dst) <= ((projecting) ? 4 + Math.min(10, ((SpellBook_Disintegration)hero.belongings.weapon).buffedLvl()) : 1 + Math.min(10, ((SpellBook_Disintegration)hero.belongings.weapon).buffedLvl()))) {
				return dst;
			} else {
				return super.throwPos(user, dst);
			}
		}

		if (this instanceof PlasmaCannon.Bullet
		||	this instanceof PlasmaCannonAP.Bullet
		||	this instanceof PlasmaCannonHP.Bullet
		||	this instanceof FlameThrower.Bullet
		||	this instanceof FlameThrowerAP.Bullet
		||	this instanceof FlameThrowerHP.Bullet
		||	this instanceof MissileButton.Rocket) {
			return dst;
		} else {
			if (projecting && !Dungeon.level.solid[dst] && Dungeon.level.distance(user.pos, dst) <= 4){
				return dst;
			} else {
				return super.throwPos(user, dst);
			}
		}
	}

	@Override
	public float accuracyFactor(Char owner) {
		float accFactor = super.accuracyFactor(owner);
		if (owner instanceof Hero && owner.buff(Momentum.class) != null && owner.buff(Momentum.class).freerunning()){
			accFactor *= 1f + 0.2f*((Hero) owner).pointsInTalent(Talent.PROJECTILE_MOMENTUM);
		}
		return accFactor;
	}

	@Override
	public void doThrow(Hero hero) {
		parent = null; //reset parent before throwing, just incase
		super.doThrow(hero);
	}

	@Override
	protected void onThrow( int cell ) {
		Char enemy = Actor.findChar( cell );
		if (enemy == null || enemy == curUser) {
			parent = null;

			//metamorphed seer shot logic
			if (curUser.hasTalent(Talent.SEER_SHOT)
					&& curUser.heroClass != HeroClass.HUNTRESS
					&& curUser.buff(Talent.SeerShotCooldown.class) == null){
				if (Actor.findChar(cell) == null) {
					RevealedArea a = Buff.affect(curUser, RevealedArea.class, 5 * curUser.pointsInTalent(Talent.SEER_SHOT));
					a.depth = Dungeon.depth;
					a.pos = cell;
					Buff.affect(curUser, Talent.SeerShotCooldown.class, 20f);
				}
			}

			super.onThrow( cell );
		} else {
			if (!curUser.shoot( enemy, this )) {
				rangedMiss( cell );
			} else {
				
				rangedHit( enemy, cell );

			}
		}
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		if (attacker == hero && Random.Int(3) < hero.pointsInTalent(Talent.SHARED_ENCHANTMENT)) {
			if (this instanceof Dart && ((Dart) this).crossbowHasEnchant(hero)) {
				//do nothing
			} else if (this instanceof FishingSpear && ((FishingSpear) this).ballistaHasEnchant(hero)) {
				//do nothing
			} else if (this instanceof ThrowingSpear && ((ThrowingSpear) this).ballistaHasEnchant(hero)) {
				//do nothing
			} else if (this instanceof Javelin && ((Javelin) this).ballistaHasEnchant(hero)) {
				//do nothing
			} else if (this instanceof Trident && ((Trident) this).ballistaHasEnchant(hero)) {
				//do nothing
			} else {
				SpiritBow bow = hero.belongings.getItem(SpiritBow.class);
				WindBow	bow2 = hero.belongings.getItem(WindBow.class);
				GoldenBow bow3 = hero.belongings.getItem(GoldenBow.class);
				NaturesBow bow4 = hero.belongings.getItem(NaturesBow.class);
				PoisonBow bow5 = hero.belongings.getItem(PoisonBow.class);
				if (bow != null && bow.enchantment != null && hero.buff(MagicImmune.class) == null) {
					damage = bow.enchantment.proc(this, attacker, defender, damage);
				}
				if (bow2 != null && bow2.enchantment != null && hero.buff(MagicImmune.class) == null) {
					damage = bow2.enchantment.proc(this, attacker, defender, damage);
				}
				if (bow3 != null && bow3.enchantment != null && hero.buff(MagicImmune.class) == null) {
					damage = bow3.enchantment.proc(this, attacker, defender, damage);
				}
				if (bow4 != null && bow4.enchantment != null && hero.buff(MagicImmune.class) == null) {
					damage = bow4.enchantment.proc(this, attacker, defender, damage);
				}
				if (bow5 != null && bow5.enchantment != null && hero.buff(MagicImmune.class) == null) {
					damage = bow5.enchantment.proc(this, attacker, defender, damage);
				}
			}
		}

		return super.proc(attacker, defender, damage);
	}

	@Override
	public Item random() {
		if (!stackable) return this;
		
		//2: 66.67% (2/3)
		//3: 26.67% (4/15)
		//4: 6.67%  (1/15)
		quantity = 2;
		if (Random.Int(3) == 0) {
			quantity++;
			if (Random.Int(5) == 0) {
				quantity++;
			}
		}
		return this;
	}
	
	@Override
	public float castDelay(Char user, int dst) {
		return delayFactor( user );
	}
	
	protected void rangedHit( Char enemy, int cell ){
		decrementDurability();
		if (durability > 0){
			//attempt to stick the missile weapon to the enemy, just drop it if we can't.
			if (sticky && enemy != null && enemy.isAlive() && enemy.alignment != Char.Alignment.ALLY){
				PinCushion p = Buff.affect(enemy, PinCushion.class);
				if (p.target == enemy){
					p.stick(this);
					return;
				}
			}
			Dungeon.level.drop( this, cell ).sprite.drop();
		}
	}
	
	protected void rangedMiss( int cell ) {
		parent = null;
		super.onThrow(cell);
	}

	public float durabilityLeft(){
		return durability;
	}

	public void repair( float amount ){
		durability += amount;
		durability = Math.min(durability, MAX_DURABILITY);
	}
	
	public float durabilityPerUse(){
		float usages = baseUses * (float)(Math.pow(3, level()));

		//+50%/75% durability
		if (hero.hasTalent(Talent.DURABLE_PROJECTILES)){
			usages *= 1.25f + (0.25f* hero.pointsInTalent(Talent.DURABLE_PROJECTILES));
		}
		if (holster) {
			usages *= MagicalHolster.HOLSTER_DURABILITY_FACTOR;
		}
		
		usages *= RingOfSharpshooting.durabilityMultiplier( hero );
		
		//at 100 uses, items just last forever.
		if (usages >= 100f) return 0;

		usages = Math.round(usages);
		
		//add a tiny amount to account for rounding error for calculations like 1/3
		return (MAX_DURABILITY/usages) + 0.001f;
	}
	
	protected void decrementDurability(){
		//if this weapon was thrown from a source stack, degrade that stack.
		//unless a weapon is about to break, then break the one being thrown
		if (parent != null){
			if (parent.durability <= parent.durabilityPerUse()){
				durability = 0;
				parent.durability = MAX_DURABILITY;
			} else {
				parent.durability -= parent.durabilityPerUse();
				if (parent.durability > 0 && parent.durability <= parent.durabilityPerUse()){
					if (level() <= 0)GLog.w(Messages.get(this, "about_to_break"));
					else             GLog.n(Messages.get(this, "about_to_break"));
				}
			}
			parent = null;
		} else {
			durability -= durabilityPerUse();
			if (durability > 0 && durability <= durabilityPerUse()){
				if (level() <= 0)GLog.w(Messages.get(this, "about_to_break"));
				else             GLog.n(Messages.get(this, "about_to_break"));
			}
		}
	}
	
	@Override
	public int damageRoll(Char owner) {
		int damage = augment.damageFactor(super.damageRoll( owner ));
		
		if (owner instanceof Hero) {
			int exStr = ((Hero)owner).STR() - STRReq();
			if (exStr > 0) {
				damage += Random.IntRange( 0, exStr );
			}
			if (owner.buff(Momentum.class) != null && owner.buff(Momentum.class).freerunning()) {
				damage = Math.round(damage * (1f + 0.15f * ((Hero) owner).pointsInTalent(Talent.PROJECTILE_MOMENTUM)));
			}
		}
		
		return damage;
	}
	
	@Override
	public void reset() {
		super.reset();
		durability = MAX_DURABILITY;
	}
	
	@Override
	public Item merge(Item other) {
		super.merge(other);
		if (isSimilar(other)) {
			durability += ((MissileWeapon)other).durability;
			durability -= MAX_DURABILITY;
			while (durability <= 0){
				quantity -= 1;
				durability += MAX_DURABILITY;
			}
		}
		return this;
	}
	
	@Override
	public Item split(int amount) {
		bundleRestoring = true;
		Item split = super.split(amount);
		bundleRestoring = false;
		
		//unless the thrown weapon will break, split off a max durability item and
		//have it reduce the durability of the main stack. Cleaner to the player this way
		if (split != null){
			MissileWeapon m = (MissileWeapon)split;
			m.durability = MAX_DURABILITY;
			m.parent = this;
		}
		
		return split;
	}
	
	@Override
	public boolean doPickUp(Hero hero, int pos) {
		parent = null;
		return super.doPickUp(hero, pos);
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public String info() {

		String info = desc();
		
		info += "\n\n" + Messages.get( MissileWeapon.class, "stats",
				tier,
				Math.round(augment.damageFactor(min())),
				Math.round(augment.damageFactor(max())),
				STRReq());

		if (STRReq() > hero.STR()) {
			info += " " + Messages.get(Weapon.class, "too_heavy");
		} else if (hero.STR() > STRReq()){
			info += " " + Messages.get(Weapon.class, "excess_str", hero.STR() - STRReq());
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

		info += "\n\n" + Messages.get(MissileWeapon.class, "distance");
		
		info += "\n\n" + Messages.get(this, "durability");
		
		if (durabilityPerUse() > 0){
			info += " " + Messages.get(this, "uses_left",
					(int)Math.ceil(durability/durabilityPerUse()),
					(int)Math.ceil(MAX_DURABILITY/durabilityPerUse()));
		} else {
			info += " " + Messages.get(this, "unlimited_uses");
		}
		
		return info;
	}
	
	@Override
	public int value() {
		return 6 * tier * quantity * (level() + 1);
	}
	
	private static final String DURABILITY = "durability";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(DURABILITY, durability);
	}
	
	private static boolean bundleRestoring = false;
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		bundleRestoring = true;
		super.restoreFromBundle(bundle);
		bundleRestoring = false;
		durability = bundle.getFloat(DURABILITY);
	}

	public static class PlaceHolder extends MissileWeapon {

		{
			image = ItemSpriteSheet.MISSILE_HOLDER;
		}

		@Override
		public boolean isSimilar(Item item) {
			return item instanceof MissileWeapon;
		}

		@Override
		public String info() {
			return "";
		}
	}
}
