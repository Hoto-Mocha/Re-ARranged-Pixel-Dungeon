/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.actors;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.level;
import static com.shatteredpixel.shatteredpixeldungeon.items.Item.updateQuickslot;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Electricity;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AllyBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArcaneArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArrowEnhance;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AscensionChallenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Berserk;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChampionEnemy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.DamageEnhance;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Demonization;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Dread;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ExtraBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Flurry;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Focusing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Fury;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.GodFury;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Iaido;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LifeLink;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LostInventory;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Momentum;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MonkEnergy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Preparation;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PrismaticGuard;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.QuickStep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SerialAttack;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sheathing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ShieldBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SnipersMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SoulMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Speed;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WantedTracker;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.Challenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.ReinforcedArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.Riot;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.nurse.AngelWing;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.planter.TreasureMap;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.DeathMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.samurai.Awake;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.samurai.ShadowBlade;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.Endure;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Elemental;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Tengu;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.MirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.PrismaticImage;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.AntiMagic;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Potential;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Viscosity;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfElements;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfFury;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfRush;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfVampire;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfChallenge;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLightning;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLivingEarth;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.GoldenBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.NaturesBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.PoisonBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.WindBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Blazing;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Blooming;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Kinetic;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Shocking;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.*;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Cross;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.ShockingDart;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Door;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.GrimTrap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Earthroot;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;

public abstract class Char extends Actor {

	public int pos = 0;

	public CharSprite sprite;

	public int HT;
	public int HP;

	protected float baseSpeed	= 1;
	protected PathFinder.Path path;

	public int paralysed	    = 0;
	public boolean rooted		= false;
	public boolean flying		= false;
	public int invisible		= 0;

	//these are relative to the hero
	public enum Alignment{
		ENEMY,
		NEUTRAL,
		ALLY
	}
	public Alignment alignment;

	public int viewDistance	= 8;

	public boolean[] fieldOfView = null;

	private LinkedHashSet<Buff> buffs = new LinkedHashSet<>();

	@Override
	protected boolean act() {
		if (fieldOfView == null || fieldOfView.length != Dungeon.level.length()){
			fieldOfView = new boolean[Dungeon.level.length()];
		}
		Dungeon.level.updateFieldOfView( this, fieldOfView );

		//throw any items that are on top of an immovable char
		if (properties().contains(Property.IMMOVABLE)){
			throwItems();
		}
		return false;
	}

	protected void throwItems(){
		Heap heap = Dungeon.level.heaps.get( pos );
		if (heap != null && heap.type == Heap.Type.HEAP) {
			int n;
			do {
				n = pos + PathFinder.NEIGHBOURS8[Random.Int( 8 )];
			} while (!Dungeon.level.passable[n] && !Dungeon.level.avoid[n]);
			Item item = heap.peek();
			if (!(item instanceof Tengu.BombAbility.BombItem || item instanceof Tengu.ShockerAbility.ShockerItem)){
				Dungeon.level.drop( heap.pickUp(), n ).sprite.drop( pos );
			}
		}
	}

	public String name(){
		return Messages.get(this, "name");
	}

	public boolean canInteract(Char c){
		if (Dungeon.level.adjacent( pos, c.pos )){
			return true;
		} else if (c instanceof Hero
				&& alignment == Alignment.ALLY
				&& Dungeon.level.distance(pos, c.pos) <= 2*Dungeon.hero.pointsInTalent(Talent.ALLY_WARP)){
			return true;
		} else {
			return false;
		}
	}

	//swaps places by default
	public boolean interact(Char c){

		//don't allow char to swap onto hazard unless they're flying
		//you can swap onto a hazard though, as you're not the one instigating the swap
		if (!Dungeon.level.passable[pos] && !c.flying){
			return true;
		}

		//can't swap into a space without room
		if (properties().contains(Property.LARGE) && !Dungeon.level.openSpace[c.pos]
			|| c.properties().contains(Property.LARGE) && !Dungeon.level.openSpace[pos]){
			return true;
		}

		int curPos = pos;

		//warp instantly with allies in this case
		if (c == Dungeon.hero && Dungeon.hero.hasTalent(Talent.ALLY_WARP)){
			PathFinder.buildDistanceMap(c.pos, BArray.or(Dungeon.level.passable, Dungeon.level.avoid, null));
			if (PathFinder.distance[pos] == Integer.MAX_VALUE){
				return true;
			}
			ScrollOfTeleportation.appear(this, c.pos);
			ScrollOfTeleportation.appear(c, curPos);
			Dungeon.observe();
			GameScene.updateFog();
			return true;
		}

		//can't swap places if one char has restricted movement
		if (rooted || c.rooted || buff(Vertigo.class) != null || c.buff(Vertigo.class) != null){
			return true;
		}

		moveSprite( pos, c.pos );
		move( c.pos );

		c.sprite.move( c.pos, curPos );
		c.move( curPos );

		if (c.buff(QuickStep.class) != null) {
			c.spend(-1 / speed());
			c.buff(QuickStep.class).detach();
		}
		c.spend( 1 / c.speed() );

		if (c == Dungeon.hero){
			if (Dungeon.hero.subClass == HeroSubClass.FREERUNNER){
				Buff.affect(Dungeon.hero, Momentum.class).gainStack();
			}

			Dungeon.hero.busy();
		}

		if (c == hero && hero.hasTalent(Talent.QUICK_RELOAD)) {
			int chance = 2*hero.pointsInTalent(Talent.QUICK_RELOAD);
			KindOfWeapon wep = hero.belongings.attackingWeapon();
			KindOfWeapon eqWep = hero.belongings.weapon;
			if (wep instanceof CrudePistol && ((CrudePistol)eqWep).round < ((CrudePistol)eqWep).max_round && Random.Int(100) < chance) {
				((CrudePistol)eqWep).round = Math.min(((CrudePistol)eqWep).round+1, ((CrudePistol)eqWep).max_round);

			} else if (wep instanceof Pistol && ((Pistol)eqWep).round < ((Pistol)eqWep).max_round && Random.Int(100) < chance) {
				((Pistol)eqWep).round = Math.min(((Pistol)eqWep).round+1, ((Pistol)eqWep).max_round);

			} else if (wep instanceof GoldenPistol && ((GoldenPistol)eqWep).round < ((GoldenPistol)eqWep).max_round && Random.Int(100) < chance) {
				((GoldenPistol)eqWep).round = Math.min(((GoldenPistol)eqWep).round+1, ((GoldenPistol)eqWep).max_round);

			} else if (wep instanceof Handgun && ((Handgun)eqWep).round < ((Handgun)eqWep).max_round && Random.Int(100) < chance) {
				((Handgun)eqWep).round = Math.min(((Handgun)eqWep).round+1, ((Handgun)eqWep).max_round);

			} else if (wep instanceof Magnum && ((Magnum)eqWep).round < ((Magnum)eqWep).max_round && Random.Int(100) < chance) {
				((Magnum)eqWep).round = Math.min(((Magnum)eqWep).round+1, ((Magnum)eqWep).max_round);

			} else if (wep instanceof TacticalHandgun && ((TacticalHandgun)eqWep).round < ((TacticalHandgun)eqWep).max_round && Random.Int(100) < chance) {
				((TacticalHandgun)eqWep).round = Math.min(((TacticalHandgun)eqWep).round+1, ((TacticalHandgun)eqWep).max_round);

			} else if (wep instanceof AutoHandgun && ((AutoHandgun)eqWep).round < ((AutoHandgun)eqWep).max_round && Random.Int(100) < chance) {
				((AutoHandgun)eqWep).round = Math.min(((AutoHandgun)eqWep).round+1, ((AutoHandgun)eqWep).max_round);

			} else if (wep instanceof DualPistol && ((DualPistol)eqWep).round < ((DualPistol)eqWep).max_round && Random.Int(100) < chance*3) {
				((DualPistol)eqWep).round = Math.min(((DualPistol)eqWep).round+1, ((DualPistol)eqWep).max_round);

			} else if (wep instanceof SubMachinegun && ((SubMachinegun)eqWep).round < ((SubMachinegun)eqWep).max_round && Random.Int(100) < chance*3) {
				((SubMachinegun)eqWep).round = Math.min(((SubMachinegun)eqWep).round+1, ((SubMachinegun)eqWep).max_round);

			} else if (wep instanceof AssultRifle && ((AssultRifle)eqWep).round < ((AssultRifle)eqWep).max_round && Random.Int(100) < chance*3) {
				((AssultRifle)eqWep).round = Math.min(((AssultRifle)eqWep).round+1, ((AssultRifle)eqWep).max_round);

			} else if (wep instanceof HeavyMachinegun && ((HeavyMachinegun)eqWep).round < ((HeavyMachinegun)eqWep).max_round && Random.Int(100) < chance*3) {
				((HeavyMachinegun)eqWep).round = Math.min(((HeavyMachinegun)eqWep).round+1, ((HeavyMachinegun)eqWep).max_round);

			} else if (wep instanceof MiniGun && ((MiniGun)eqWep).round < ((MiniGun)eqWep).max_round && Random.Int(100) < chance*3) {
				((MiniGun)eqWep).round = Math.min(((MiniGun)eqWep).round+1, ((MiniGun)eqWep).max_round);

			} else if (wep instanceof Revolver && ((Revolver)eqWep).round < ((Revolver)eqWep).max_round && Random.Int(100) < chance/2) {
				((Revolver)eqWep).round = Math.min(((Revolver)eqWep).round+1, ((Revolver)eqWep).max_round);

			} else if (wep instanceof HuntingRifle && ((HuntingRifle)eqWep).round < ((HuntingRifle)eqWep).max_round && Random.Int(100) < chance/2) {
				((HuntingRifle)eqWep).round = Math.min(((HuntingRifle)eqWep).round+1, ((HuntingRifle)eqWep).max_round);

			} else if (wep instanceof Carbine && ((Carbine)eqWep).round < ((Carbine)eqWep).max_round && Random.Int(100) < chance/2) {
				((Carbine)eqWep).round = Math.min(((Carbine)eqWep).round+1, ((Carbine)eqWep).max_round);

			} else if (wep instanceof SniperRifle && ((SniperRifle)eqWep).round < ((SniperRifle)eqWep).max_round && Random.Int(100) < chance/2) {
				((SniperRifle)eqWep).round = Math.min(((SniperRifle)eqWep).round+1, ((SniperRifle)eqWep).max_round);

			} else if (wep instanceof AntimaterRifle && ((AntimaterRifle)eqWep).round < ((AntimaterRifle)eqWep).max_round && Random.Int(100) < chance/2) {
				((AntimaterRifle)eqWep).round = Math.min(((AntimaterRifle)eqWep).round+1, ((AntimaterRifle)eqWep).max_round);

			} else if (wep instanceof WA2000 && ((WA2000)eqWep).round < ((WA2000)eqWep).max_round && Random.Int(100) < chance/2) {
				((WA2000)eqWep).round = Math.min(((WA2000)eqWep).round+1, ((WA2000)eqWep).max_round);

			} else if (wep instanceof MarksmanRifle && ((MarksmanRifle)eqWep).round < ((MarksmanRifle)eqWep).max_round && Random.Int(100) < chance/2) {
				((MarksmanRifle)eqWep).round = Math.min(((MarksmanRifle)eqWep).round+1, ((MarksmanRifle)eqWep).max_round);

			} else if (wep instanceof ShotGun && ((ShotGun)eqWep).round < ((ShotGun)eqWep).max_round && Random.Int(100) < chance/2) {
				((ShotGun)eqWep).round = Math.min(((ShotGun)eqWep).round+1, ((ShotGun)eqWep).max_round);

			} else if (wep instanceof KSG && ((KSG)eqWep).round < ((KSG)eqWep).max_round && Random.Int(100) < chance/2) {
				((KSG)eqWep).round = Math.min(((KSG)eqWep).round+1, ((KSG)eqWep).max_round);

			} else if (wep instanceof PlasmaCannon && ((PlasmaCannon)eqWep).round < ((PlasmaCannon)eqWep).max_round && Random.Int(100) < chance/2) {
				((PlasmaCannon)eqWep).round = Math.min(((PlasmaCannon)eqWep).round+1, ((PlasmaCannon)eqWep).max_round);

			} else if (wep instanceof FlameThrower && ((FlameThrower)eqWep).round < ((FlameThrower)eqWep).max_round && Random.Int(100) < chance/2) {
				((FlameThrower)eqWep).round = Math.min(((FlameThrower)eqWep).round+1, ((FlameThrower)eqWep).max_round);

			} else if (wep instanceof RocketLauncher && ((RocketLauncher)eqWep).round < ((RocketLauncher)eqWep).max_round && Random.Int(100) < chance/2) {
				((RocketLauncher)eqWep).round = Math.min(((RocketLauncher)eqWep).round+1, ((RocketLauncher)eqWep).max_round);

			} else if (wep instanceof RPG7 && ((RPG7)eqWep).round < ((RPG7)eqWep).max_round && Random.Int(100) < chance/2) {
				((RPG7)eqWep).round = Math.min(((RPG7)eqWep).round+1, ((RPG7)eqWep).max_round);

			}
			updateQuickslot();
		}

		if (c == Dungeon.hero){
			if (hero.subClass == HeroSubClass.SLAYER && hero.buff(Demonization.class) == null) {
				Buff.affect(hero, Demonization.class).indicate();
			}

			Dungeon.hero.busy();
		}


		return true;
	}

	protected boolean moveSprite( int from, int to ) {

		if (sprite.isVisible() && (Dungeon.level.heroFOV[from] || Dungeon.level.heroFOV[to])) {
			sprite.move( from, to );
			return true;
		} else {
			sprite.turnTo(from, to);
			sprite.place( to );
			return true;
		}
	}

	public void hitSound( float pitch ){
		Sample.INSTANCE.play(Assets.Sounds.HIT, 1, pitch);
	}

	public boolean blockSound( float pitch ) {
		return false;
	}

	protected static final String POS       = "pos";
	protected static final String TAG_HP    = "HP";
	protected static final String TAG_HT    = "HT";
	protected static final String TAG_SHLD  = "SHLD";
	protected static final String BUFFS	    = "buffs";

	@Override
	public void storeInBundle( Bundle bundle ) {

		super.storeInBundle( bundle );

		bundle.put( POS, pos );
		bundle.put( TAG_HP, HP );
		bundle.put( TAG_HT, HT );
		bundle.put( BUFFS, buffs );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {

		super.restoreFromBundle( bundle );

		pos = bundle.getInt( POS );
		HP = bundle.getInt( TAG_HP );
		HT = bundle.getInt( TAG_HT );

		for (Bundlable b : bundle.getCollection( BUFFS )) {
			if (b != null) {
				((Buff)b).attachTo( this );
			}
		}
	}

	final public boolean attack( Char enemy ){
		if (hero.buff(Awake.awakeTracker.class) != null) {
			return attack(enemy, 1.2f+0.2f*hero.pointsInTalent(Talent.AWAKE_LIMIT), 0f, 1f);
		} else {
			return attack(enemy, 1f, 0f, 1f);
		}
	}

	public boolean attack( Char enemy, float dmgMulti, float dmgBonus, float accMulti ) {

		if (enemy == null) return false;

		boolean visibleFight = Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[enemy.pos];

		if (enemy.isInvulnerable(getClass())) {

			if (visibleFight) {
				enemy.sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "invulnerable") );

				Sample.INSTANCE.play(Assets.Sounds.HIT_PARRY, 1f, Random.Float(0.96f, 1.05f));
			}

			return false;

		} else if (hit( this, enemy, accMulti, false )) {

			int dr = Math.round(enemy.drRoll() * AscensionChallenge.statModifier(enemy));

			Barkskin bark = enemy.buff(Barkskin.class);
			if (bark != null)   dr += Random.NormalIntRange( 0 , bark.level() );

			ReinforcedArmor.reinforcedArmorTracker rearmor = enemy.buff(ReinforcedArmor.reinforcedArmorTracker.class);
			if (rearmor != null)  dr += rearmor.blockingRoll();

			if (this.alignment == Alignment.ALLY && !(this instanceof Hero) && hero.hasTalent(Talent.CHARISMA)) {
				dr *= Math.pow(1.1f, hero.pointsInTalent(Talent.CHARISMA));
			}

			if (this instanceof Hero && enemy.buff(WantedTracker.Wanted.class) != null) {
				dmgMulti *= 1.1f + 0.05f * hero.pointsInTalent(Talent.JUSTICE_BULLET);
			}

			if (this instanceof Hero && hero.subClass == HeroSubClass.WEAPONMASTER) {
				if ( hero.belongings.attackingWeapon() instanceof Spear
					|| hero.belongings.attackingWeapon() instanceof Glaive
					|| hero.belongings.attackingWeapon() instanceof Lance ) {
					dr *= 1 - 0.1f*(Math.min(hero.belongings.weapon.buffedLvl(), 10));
				}
			}

			if (this instanceof Hero && Dungeon.isChallenged(Challenges.PYRO)) {
				Buff.affect(enemy, Burning.class).reignite(enemy, 8f);
			}

			if (this instanceof Hero) {
				Hero h = (Hero) this;
				KindOfWeapon wep = h.belongings.weapon();
				KindOfWeapon equippedWep = h.belongings.weapon;
				if (wep instanceof TrueRunicBlade) {
					dr = 0;
				}
				if (wep instanceof RunicDagger
						&& ((Mob) enemy).surprisedBy(hero)) {
					dr = 0;
				}
			}

			if (this instanceof Hero){
				Hero h = (Hero)this;
				KindOfWeapon wep = h.belongings.attackingWeapon();
				KindOfWeapon equippedWep = h.belongings.weapon;
				if (h.belongings.attackingWeapon() instanceof MissileWeapon
						&& h.subClass == HeroSubClass.SNIPER
						&& !Dungeon.level.adjacent(h.pos, enemy.pos)){
					dr = 0;
				}
				if (hero.buff(ShadowBlade.shadowBladeTracker.class) != null && Random.Int(2) == 0) {
					if (hero.hasTalent(Talent.CRITICAL_SHADOW)) {
						dmgBonus += Random.NormalIntRange(0, 5*hero.pointsInTalent(Talent.CRITICAL_SHADOW));
					}
					if (hero.hasTalent(Talent.HERBAL_SHADOW)) {
						hero.heal(hero.pointsInTalent(Talent.HERBAL_SHADOW));
					}
					dr = 0;
				}
				if ((wep instanceof CrudePistol.Bullet && (equippedWep instanceof CrudePistolAP))
					  || (wep instanceof Pistol.Bullet && (equippedWep instanceof PistolAP))
					  || (wep instanceof GoldenPistol.Bullet && (equippedWep instanceof GoldenPistolAP))
					  || (wep instanceof Handgun.Bullet && (equippedWep instanceof HandgunAP))
					  || (wep instanceof Magnum.Bullet && (equippedWep instanceof MagnumAP))
					  || (wep instanceof TacticalHandgun.Bullet && (equippedWep instanceof TacticalHandgunAP))
					  || (wep instanceof AutoHandgun.Bullet && (equippedWep instanceof AutoHandgunAP))

					  || (wep instanceof DualPistol.Bullet && (equippedWep instanceof DualPistolAP))
					  || (wep instanceof SubMachinegun.Bullet && (equippedWep instanceof SubMachinegunAP))
					  || (wep instanceof AssultRifle.Bullet && (equippedWep instanceof AssultRifleAP))
					  || (wep instanceof HeavyMachinegun.Bullet && (equippedWep instanceof HeavyMachinegunAP))
					  || (wep instanceof MiniGun.Bullet && (equippedWep instanceof MiniGunAP))
					  || (wep instanceof AutoRifle.Bullet && (equippedWep instanceof AutoRifleAP))

					  || (wep instanceof Revolver.Bullet && (equippedWep instanceof RevolverAP))
					  || (wep instanceof HuntingRifle.Bullet && (equippedWep instanceof HuntingRifleAP))
					  || (wep instanceof Carbine.Bullet && (equippedWep instanceof CarbineAP))
					  || (wep instanceof SniperRifle.Bullet && (equippedWep instanceof SniperRifleAP))
					  || (wep instanceof AntimaterRifle.Bullet && (equippedWep instanceof AntimaterRifleAP))
					  || (wep instanceof MarksmanRifle.Bullet && (equippedWep instanceof MarksmanRifleAP))
					  || (wep instanceof WA2000.Bullet && (equippedWep instanceof WA2000AP))

					  || (wep instanceof ShotGun.Bullet && (equippedWep instanceof ShotGunAP))
					  || (wep instanceof KSG.Bullet && (equippedWep instanceof KSGAP))) {
					dr = 0;
				}
				if ((wep instanceof CrudePistol.Bullet && (equippedWep instanceof CrudePistolHP))
						|| (wep instanceof Pistol.Bullet && (equippedWep instanceof PistolHP))
						|| (wep instanceof GoldenPistol.Bullet && (equippedWep instanceof GoldenPistolHP))
						|| (wep instanceof Handgun.Bullet && (equippedWep instanceof HandgunHP))
						|| (wep instanceof Magnum.Bullet && (equippedWep instanceof MagnumHP))
						|| (wep instanceof TacticalHandgun.Bullet && (equippedWep instanceof TacticalHandgunHP))
						|| (wep instanceof AutoHandgun.Bullet && (equippedWep instanceof AutoHandgunHP))

						|| (wep instanceof DualPistol.Bullet && (equippedWep instanceof DualPistolHP))
						|| (wep instanceof SubMachinegun.Bullet && (equippedWep instanceof SubMachinegunHP))
						|| (wep instanceof AssultRifle.Bullet && (equippedWep instanceof AssultRifleHP))
						|| (wep instanceof HeavyMachinegun.Bullet && (equippedWep instanceof HeavyMachinegunHP))
						|| (wep instanceof MiniGun.Bullet && (equippedWep instanceof MiniGunHP))
						|| (wep instanceof AutoRifle.Bullet && (equippedWep instanceof AutoRifleHP))

						|| (wep instanceof Revolver.Bullet && (equippedWep instanceof RevolverHP))
						|| (wep instanceof HuntingRifle.Bullet && (equippedWep instanceof HuntingRifleHP))
						|| (wep instanceof Carbine.Bullet && (equippedWep instanceof CarbineHP))
						|| (wep instanceof SniperRifle.Bullet && (equippedWep instanceof SniperRifleHP))
						|| (wep instanceof AntimaterRifle.Bullet && (equippedWep instanceof AntimaterRifleHP))
						|| (wep instanceof MarksmanRifle.Bullet && (equippedWep instanceof MarksmanRifleHP))
						|| (wep instanceof WA2000.Bullet && (equippedWep instanceof WA2000HP))) {
					dr *= 2;
				}
				if ((wep instanceof ShotGun.Bullet && (equippedWep instanceof ShotGunHP))
						|| (wep instanceof KSG.Bullet && (equippedWep instanceof KSGHP))) {
					dr *= 2;
				}

				if (h.buff(MonkEnergy.MonkAbility.UnarmedAbilityTracker.class) != null){
					dr = 0;
				} else if (h.subClass == HeroSubClass.MONK) {
					//3 turns with standard attack delay
					Buff.prolong(h, MonkEnergy.MonkAbility.JustHitTracker.class, 4f);
				}
			}

			//we use a float here briefly so that we don't have to constantly round while
			// potentially applying various multiplier effects
			float dmg;
			Preparation prep = buff(Preparation.class);
			if (prep != null){
				dmg = prep.damageRoll(this);
				if (this == Dungeon.hero && Dungeon.hero.hasTalent(Talent.BOUNTY_HUNTER)) {
					Buff.affect(Dungeon.hero, Talent.BountyHunterTracker.class, 0.0f);
				}
			} else {
				dmg = damageRoll();
			}

			dmg = Math.round(dmg*dmgMulti);

			Berserk berserk = buff(Berserk.class);
			if (berserk != null) dmg = berserk.damageFactor(dmg);

			if (this instanceof Hero) {
				dmg *= RingOfRush.damageMultiplier(hero);
			}

			if (this.alignment == Alignment.ALLY && !(this instanceof Hero) && hero.hasTalent(Talent.POWERFUL_BOND)) {
				dmg *= Math.pow(1.1f, hero.pointsInTalent(Talent.POWERFUL_BOND));
			}

			if (Dungeon.isChallenged(Challenges.SUPERMAN) && this instanceof Hero) {
				dmg *= 3f;
			}

			if (this instanceof Hero && hero.buff(GodFury.class) != null) {
				dmg *= 3f;
			}

			if (this instanceof Hero && hero.subClass == HeroSubClass.WEAPONMASTER && hero.belongings.weapon != null) {
				if (Random.Int(100) < Math.min(hero.belongings.weapon.buffedLvl()+1, 10) && (
					hero.belongings.attackingWeapon() instanceof WornKatana
				 || hero.belongings.attackingWeapon() instanceof ShortKatana
				 || hero.belongings.attackingWeapon() instanceof Katana
				 || hero.belongings.attackingWeapon() instanceof LongKatana
				 || hero.belongings.attackingWeapon() instanceof LargeKatana
				 || hero.belongings.attackingWeapon() instanceof SharpKatana
				 || hero.belongings.attackingWeapon() instanceof WornKatana_Energy)) {
					dmg *= 2f;
					Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
					hero.sprite.showStatus( CharSprite.NEUTRAL, "!" );
				}
			}

			if (this instanceof Hero && hero.buff(Bless.class) != null && hero.subClass == HeroSubClass.CRUSADER) {
				int heal = Math.max(Math.round(0.1f * dmg), 1);
				int effect = Math.min( hero.HT - hero.HP, heal );
				int shield = 0;
				if (hero.hasTalent(Talent.DIVINE_SHIELD)){
					shield = heal - effect;
					int maxShield = Math.round(hero.HT * 0.2f*hero.pointsInTalent(Talent.DIVINE_SHIELD));
					int curShield = 0;
					if (hero.buff(Barrier.class) != null) curShield = hero.buff(Barrier.class).shielding();
					shield = Math.min(shield, maxShield-curShield);
				}
				if (effect > 0 || shield > 0) {
					hero.HP += effect;
					if (shield > 0) Buff.affect(hero, Barrier.class).incShield(shield);
					hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
				}
			}

			if (this instanceof Hero && hero.belongings.weapon != null && hero.belongings.attackingWeapon() instanceof MissileWeapon) {
				float procChance; //chance to be activated
				int lvl = hero.belongings.weapon.buffedLvl();
				if (hero.belongings.weapon instanceof SpellBook_Corrosion) {
					procChance = (lvl+1f)/(lvl+5f);
					if (Random.Float() < procChance) {
						Buff.affect(enemy, Ooze.class).set(3+lvl);
					}
				} else if (hero.belongings.weapon instanceof SpellBook_Corruption) {
					int procBonus = 0; //used for adding chances to corrupt
					if (enemy.buff(Weakness.class) != null) {
						procBonus += 1;
					}
					if (enemy.buff(Vulnerable.class) != null) {
						procBonus += 1;
					}
					if (enemy.buff(Cripple.class) != null) {
						procBonus += 1;
					}
					if (enemy.buff(Blindness.class) != null) {
						procBonus += 1;
					}
					if (enemy.buff(Terror.class) != null) {
						procBonus += 1;
					}
					if (enemy.buff(Amok.class) != null) {
						procBonus += 2;
					}
					if (enemy.buff(Slow.class) != null) {
						procBonus += 2;
					}
					if (enemy.buff(Hex.class) != null) {
						procBonus += 2;
					}
					if (enemy.buff(Paralysis.class) != null) {
						procBonus += 2;
					}
					if (enemy.buff(com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom.class) != null) {
						procChance = 1; //100% chance when enemy has a Doom debuff
					} else {
						procChance = (lvl+5f+procBonus)/(lvl+25f);
					}
					if (dmg >= enemy.HP
							&& Random.Float() < procChance
							&& !enemy.isImmune(Corruption.class)
							&& enemy.buff(Corruption.class) == null
							&& enemy instanceof Mob
							&& enemy.isAlive()){
						Mob mob = (Mob) enemy;
						Corruption.corruptionHeal(mob);
						AllyBuff.affectAndLoot(mob, hero, Corruption.class);
						dmg = 0;
					}
				} else if (hero.belongings.weapon instanceof SpellBook_Blast) {
					procChance = (lvl+1f)/(lvl+5f);
					if (Random.Float() < procChance) {
						Buff.affect(enemy, Paralysis.class, (lvl >= 10) ? 1f : 2f);
					}
				} else if (hero.belongings.weapon instanceof SpellBook_Earth) {
					procChance = 1/4f; //fixed at 1/4 regardless of lvl
					if (Random.Float() < procChance) {
						Buff.affect(hero, Earthroot.Armor.class).level(5+lvl);
					}
				} else if (hero.belongings.weapon instanceof SpellBook_Fire) {
					procChance = (lvl+1f)/(lvl+3f);
					if (Random.Float() < procChance) {
						if (enemy.buff(Burning.class) != null){
							Buff.affect(enemy, Burning.class).reignite(enemy, 8f);
							int burnDamage = Random.NormalIntRange( 1, 3 + Dungeon.depth/4 );
							enemy.damage( Math.round(burnDamage * 0.67f), this );
						} else {
							Buff.affect(enemy, Burning.class).reignite(enemy, 8f);
						}
						enemy.sprite.emitter().burst( FlameParticle.FACTORY, lvl + 1 );
					}
				} else if (hero.belongings.weapon instanceof SpellBook_Frost) {
					procChance = (lvl+1f)/(lvl+4f);
					if (Random.Float() < procChance) {
						//adds 3 turns of chill per proc, with a cap of 6 turns
						float durationToAdd = 3f;
						Chill existing = enemy.buff(Chill.class);
						if (existing != null){
							durationToAdd = Math.min(durationToAdd, 6f-existing.cooldown());
						}

						Buff.affect( enemy, Chill.class, durationToAdd );
						Splash.at( enemy.sprite.center(), 0xFFB2D6FF, 5);
					}
				} else if (hero.belongings.weapon instanceof SpellBook_Lightning) {
					ArrayList<Lightning.Arc> arcs = new ArrayList<>();
					ArrayList<Char> affected = new ArrayList<>();
					procChance = (lvl+1f)/(lvl+4f);
					if (Random.Float() < procChance) {
						affected.clear();
						arcs.clear();

						Shocking.arc(hero, enemy, 2, affected, arcs);

						affected.remove(enemy); //defender isn't hurt by lightning
						for (Char ch : affected) {
							if (ch.alignment != hero.alignment) {
								ch.damage(Math.round(dmg * 0.4f), this);
							}
						}

						hero.sprite.parent.addToFront( new Lightning( arcs, null ) );
						Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
					}
				} else if (hero.belongings.weapon instanceof SpellBook_Warding) {
					procChance = (lvl+1f)/(lvl+3f);
					if (Random.Float() < procChance) {
						boolean found = false;
						for (Mob m : Dungeon.level.mobs.toArray(new Mob[0])){
							if (m instanceof PrismaticImage){ //if the prismatic image is existing in the floor
								found = true;
								if (m.HP < m.HT) {
									m.HP = Math.min(m.HP+(int)(dmg/2), m.HT); //heals the prismatic image
									m.sprite.emitter().burst(Speck.factory(Speck.HEALING), 4);
									m.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( Math.min((int)(dmg/2), m.HT-m.HP) ) );
								}
							}
						}

						if (!found) {
							if (hero.buff(PrismaticGuard.class) != null) {
								Buff.affect(hero, PrismaticGuard.class).extend( (int)(dmg/2) ); //heals the buff's hp
							} else {
								Buff.affect(hero, PrismaticGuard.class).set( (int)(dmg/2) ); //affects a new buff to hero
							}
						}
					}
				} else if (hero.belongings.weapon instanceof SpellBook_Regrowth) {
					procChance = (lvl+1f)/(lvl+3f);
					if (Random.Float() < procChance) {
						boolean secondPlant = Random.Int(3) == 0;
						ArrayList<Integer> positions = new ArrayList<>();
						Blooming blooming = new Blooming();
						for (int i : PathFinder.NEIGHBOURS8) {
							positions.add(i);
						}
						Random.shuffle(positions);
						for (int i : positions) {
							if (blooming.plantGrass(enemy.pos + i)) {
								if (secondPlant) secondPlant = false;
								else break;
							}
						}
					}
				} else if (hero.belongings.weapon instanceof SpellBook_Transfusion) {
					//chance to heal scales from 5%-30% based on missing HP
					float missingPercent = (hero.HT - hero.HP) / (float)hero.HT;
					procChance = 0.05f + (0.25f+0.01f*lvl)*missingPercent;
					if (Random.Float() < procChance) {

						//heals for 50% of damage dealt
						int healAmt = Math.round(dmg * 0.5f);
						hero.heal(healAmt);

					}
				} else if (hero.belongings.weapon instanceof SpellBook_Prismatic) {
					procChance = (lvl+1f)/(lvl+3f);
					if (Random.Float() < procChance) {
						Buff.affect(enemy, Blindness.class, 2+lvl);
					}
					if (enemy.properties().contains(Char.Property.DEMONIC) || enemy.properties().contains(Char.Property.UNDEAD)){
						enemy.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10+lvl );
						Sample.INSTANCE.play(Assets.Sounds.BURNING);

						dmg *= 1.3333f; //deals more damage to the demons and the undeads
					}
				}
			}

			if (this instanceof Hero && hero.belongings.attackingWeapon() instanceof Cross) {
				if (enemy.properties().contains(Char.Property.DEMONIC) || enemy.properties().contains(Char.Property.UNDEAD)){
					enemy.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10 );
					Sample.INSTANCE.play(Assets.Sounds.BURNING);

					dmg *= 1.3333f; //deals more damage to the demons and the undeads
				}
			}

			if (this instanceof Hero) {
				if (Dungeon.hero.belongings.attackingWeapon() instanceof AntimaterRifle.Bullet
						|| Dungeon.hero.belongings.attackingWeapon() instanceof AntimaterRifleAP.Bullet
						|| Dungeon.hero.belongings.attackingWeapon() instanceof AntimaterRifleHP.Bullet) {
					int distance = Dungeon.level.distance(hero.pos, enemy.pos) - 1;
					float multiplier = Math.min(4f, (float)Math.pow(1.2f, distance + 1));
					dmg = Math.round(dmg * multiplier);
				}
			}

			if (this instanceof Hero) {
				if (hero.buff(Iaido.class) != null) {
					dmg *= 1f + 0.1f * hero.buff(Iaido.class).getCount();
				}
			}

			if (this instanceof Hero && hero.hasTalent(Talent.BIOLOGY_PROJECT)) {
				if (!(enemy.properties().contains(Property.INORGANIC) || enemy.properties().contains(Property.UNDEAD))){
					enemy.sprite.emitter().start( ShadowParticle.UP, 0.05f, 3 );
					Sample.INSTANCE.play(Assets.Sounds.BURNING);

					dmg *= Math.pow(1.1f, hero.pointsInTalent(Talent.BIOLOGY_PROJECT));
				}
			}

			if (this instanceof Hero && hero.belongings.attackingWeapon() instanceof MissileWeapon && hero.hasTalent(Talent.SNARE) && hero.buff(Talent.SnareCooldown.class) == null) {
				Buff.prolong(enemy, Cripple.class, 5f);
				Buff.affect(hero, Talent.SnareCooldown.class, 10*(4-Dungeon.hero.pointsInTalent(Talent.SNARE)));
			}

			if (this instanceof Hero && Dungeon.level.map[hero.pos] == Terrain.WATER) {
				dmg += Random.NormalIntRange(1, hero.pointsInTalent(Talent.WATER_FRIENDLY));
				Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
			}

			if (this instanceof Hero && hero.buff(TreasureMap.GoldTracker.class) != null) {
				dmg *= 1 + 0.1f * hero.pointsInTalent(Talent.GOLD_HUNTER);
			}

			if (buff( Fury.class ) != null) {
				dmg *= 1.5f;
			}

			if (this instanceof Hero) {
				if (hero.buff(ShadowBlade.shadowBladeTracker.class) != null) {
					dmg *= 0.5f;
				}
			}

			if (this instanceof Hero && hero.hasTalent(Talent.VINE_WHIP) && hero.belongings.weapon instanceof MeleeWeapon) {
				dmg *= Math.pow(0.8, hero.pointsInTalent(Talent.VINE_WHIP));
			}

			if (this instanceof Hero && hero.belongings.weapon instanceof IronHammer && Random.Int(20) == 0) {
				Buff.affect(enemy, Paralysis.class, 1f);
			}

			if (this instanceof Hero && hero.belongings.weapon instanceof BeamSaber && Random.Int(40) == 0) {
				Buff.affect(enemy, Blindness.class, 2f);
			}

			if (this instanceof Hero
					&& hero.hasTalent(Talent.TAKEDOWN)
					&& hero.buff(Talent.TakeDownCooldown.class) == null
					&& (hero.belongings.attackingWeapon() instanceof Shovel || hero.belongings.attackingWeapon() instanceof GildedShovel || hero.belongings.attackingWeapon() instanceof Spade || hero.belongings.attackingWeapon() instanceof MinersTool)) {
				Buff.affect(hero, Talent.TakeDownCooldown.class, 15f);
			}

			if (this instanceof Hero) {
				if (hero.subClass == HeroSubClass.SLASHER) {
					if (hero.buff(SerialAttack.class) != null) {
						dmg *= 1f + 0.05f * hero.buff(SerialAttack.class).getCount();
						Buff.affect(hero, SerialAttack.class).hit(enemy);
					} else {
						dmg *= 1f;
						Buff.affect(hero, SerialAttack.class).hit(enemy);
					}
				}
			}
			if (this instanceof Hero) {
				if (this.buff(Talent.CriticalUpgradeTracker.class) != null) {
					dmg *= 1.5+1.5f*hero.pointsInTalent(Talent.CRITICAL_UPGRADE);
					this.buff(Talent.CriticalUpgradeTracker.class).detach();
				}
			}
			if (this instanceof Hero) {
				if (dmg >= enemy.HP
						&& hero.hasTalent(Talent.DEADS_BLESS)
						&& hero.buff(GodFury.class) != null
						&& !enemy.isImmune(AllyBuff.class)
						&& enemy.buff(AllyBuff.class) == null
						&& enemy instanceof Mob
						&& enemy.isAlive()){
					dmg = 0;
					int healAmt = enemy.HT-enemy.HP;
					enemy.HP += healAmt;
					enemy.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 5 );
					AllyBuff.affectAndLoot((Mob)enemy, hero, ScrollOfSirensSong.Enthralled.class);
					if (hero.pointsInTalent(Talent.DEADS_BLESS) > 1) {
						Buff.affect(enemy, Barrier.class).setShield(enemy.HT/5);
					}
					if (hero.pointsInTalent(Talent.DEADS_BLESS) > 2) {
						Buff.affect(enemy, Haste.class, 20f);
						Buff.affect(enemy, Adrenaline.class, 20f);
					}
				}
			}

			if (this instanceof Hero) {
				if (hero.hasTalent(Talent.DEFIBRILLATOR)) {
					if (!enemy.isAlive() && Random.Int(50) < hero.pointsInTalent(Talent.DEFIBRILLATOR)) {
						dmg = 0;
						int healAmt = 10;
						enemy.HP += healAmt;
						enemy.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
						enemy.sprite.flash();
						AllyBuff.affectAndLoot((Mob)enemy, hero, ScrollOfSirensSong.Enthralled.class);
					}
				}
			}

			if (this instanceof Hero) {
				if (Dungeon.hero.belongings.attackingWeapon() instanceof GrenadeLauncher.Rocket) {
					Buff.prolong(enemy, Vulnerable.class, 5f);
				}
				if (Dungeon.hero.belongings.attackingWeapon() instanceof GrenadeLauncherHP.Rocket) {
					Buff.prolong(enemy, Paralysis.class, 3f);
				}
				//if (Dungeon.hero.belongings.attackingWeapon() instanceof SleepGun.Dart) {
				//	{actPriority = VFX_PRIO;}
				//	Buff.affect(enemy, MagicalSleep.class);
				//}
				//if (Dungeon.hero.belongings.attackingWeapon() instanceof FrostGun.Dart) {
				//	{actPriority = VFX_PRIO;}
				//	Buff.affect(enemy, Frost.class, 20f);
				//}
				//ParalysisGun paralysisGun = new ParalysisGun();
				//if (Dungeon.hero.belongings.attackingWeapon() instanceof ParalysisGun.Dart) {
				//	{actPriority = VFX_PRIO;}
				//	Buff.affect(enemy, Paralysis.class, 5f + paralysisGun.buffedLvl());
				//}
			}

			if (this instanceof Hero){
				Hero h = (Hero)this;
				KindOfWeapon wep = h.belongings.weapon();
				KindOfWeapon equippedWep = h.belongings.weapon;
				if ((wep instanceof CrudePistol.Bullet && equippedWep instanceof CrudePistolAP)
				 || (wep instanceof Pistol.Bullet && equippedWep instanceof PistolAP)
				 || (wep instanceof GoldenPistol.Bullet && equippedWep instanceof GoldenPistolAP)
				 || (wep instanceof Handgun.Bullet && equippedWep instanceof HandgunAP)
				 || (wep instanceof Magnum.Bullet && equippedWep instanceof MagnumAP)
				 || (wep instanceof TacticalHandgun.Bullet && equippedWep instanceof TacticalHandgunAP)
				 || (wep instanceof AutoHandgun.Bullet && equippedWep instanceof AutoHandgunAP)
				 || (wep instanceof DualPistol.Bullet && equippedWep instanceof DualPistolAP)
				 || (wep instanceof SubMachinegun.Bullet && equippedWep instanceof SubMachinegunAP)
				 || (wep instanceof AssultRifle.Bullet && equippedWep instanceof AssultRifleAP)
				 || (wep instanceof HeavyMachinegun.Bullet && equippedWep instanceof HeavyMachinegunAP)
				 || (wep instanceof MiniGun.Bullet && equippedWep instanceof MiniGunAP)
				 || (wep instanceof AutoRifle.Bullet && equippedWep instanceof AutoRifleAP)
				 || (wep instanceof Revolver.Bullet && equippedWep instanceof RevolverAP)
				 || (wep instanceof HuntingRifle.Bullet && equippedWep instanceof HuntingRifleAP)
				 || (wep instanceof Carbine.Bullet && equippedWep instanceof CarbineAP)
				 || (wep instanceof SniperRifle.Bullet && equippedWep instanceof SniperRifleAP)
				 || (wep instanceof AntimaterRifle.Bullet && equippedWep instanceof AntimaterRifleAP)
				 || (wep instanceof MarksmanRifle.Bullet && equippedWep instanceof MarksmanRifleAP)
				 || (wep instanceof WA2000.Bullet && equippedWep instanceof WA2000AP)
				) {
					dmg *= 0.80f;
				} else if ((wep instanceof CrudePistol.Bullet && equippedWep instanceof CrudePistolHP)
						|| (wep instanceof Pistol.Bullet && equippedWep instanceof PistolHP)
						|| (wep instanceof GoldenPistol.Bullet && equippedWep instanceof GoldenPistolHP)
						|| (wep instanceof Handgun.Bullet && equippedWep instanceof HandgunHP)
						|| (wep instanceof Magnum.Bullet && equippedWep instanceof MagnumHP)
						|| (wep instanceof TacticalHandgun.Bullet && equippedWep instanceof TacticalHandgunHP)
						|| (wep instanceof AutoHandgun.Bullet && equippedWep instanceof AutoHandgunHP)
						|| (wep instanceof DualPistol.Bullet && equippedWep instanceof DualPistolHP)
						|| (wep instanceof SubMachinegun.Bullet && equippedWep instanceof SubMachinegunHP)
						|| (wep instanceof AssultRifle.Bullet && equippedWep instanceof AssultRifleHP)
						|| (wep instanceof HeavyMachinegun.Bullet && equippedWep instanceof HeavyMachinegunHP)
						|| (wep instanceof MiniGun.Bullet && equippedWep instanceof MiniGunHP)
						|| (wep instanceof AutoRifle.Bullet && equippedWep instanceof AutoRifleHP)
						|| (wep instanceof Revolver.Bullet && equippedWep instanceof RevolverHP)
						|| (wep instanceof HuntingRifle.Bullet && equippedWep instanceof HuntingRifleHP)
						|| (wep instanceof Carbine.Bullet && equippedWep instanceof CarbineHP)
						|| (wep instanceof SniperRifle.Bullet && equippedWep instanceof SniperRifleHP)
						|| (wep instanceof AntimaterRifle.Bullet && equippedWep instanceof AntimaterRifleHP)
						|| (wep instanceof MarksmanRifle.Bullet && equippedWep instanceof MarksmanRifleHP)
						|| (wep instanceof WA2000.Bullet && equippedWep instanceof WA2000HP)
				) {
					dmg *= 1.3f;
				}
			}

			if (this instanceof Hero && hero.subClass == HeroSubClass.VETERAN && hero.buff(Focusing.class) != null && hero.hasTalent(Talent.FOCUS_UPGRADE)){
				Hero h = (Hero) this;
				KindOfWeapon wep = h.belongings.weapon();
				if (wep instanceof CrudePistol.Bullet
						|| wep instanceof Pistol.Bullet
						|| wep instanceof GoldenPistol.Bullet
						|| wep instanceof Handgun.Bullet
						|| wep instanceof Magnum.Bullet
						|| wep instanceof TacticalHandgun.Bullet
						|| wep instanceof AutoHandgun.Bullet
						|| wep instanceof DualPistol.Bullet
						|| wep instanceof SubMachinegun.Bullet
						|| wep instanceof AssultRifle.Bullet
						|| wep instanceof HeavyMachinegun.Bullet
						|| wep instanceof MiniGun.Bullet
						|| wep instanceof AutoRifle.Bullet
						|| wep instanceof Revolver.Bullet
						|| wep instanceof HuntingRifle.Bullet
						|| wep instanceof Carbine.Bullet
						|| wep instanceof SniperRifle.Bullet
						|| wep instanceof AntimaterRifle.Bullet
						|| wep instanceof MarksmanRifle.Bullet
						|| wep instanceof WA2000.Bullet
						|| wep instanceof ShotGun.Bullet
						|| wep instanceof KSG.Bullet
						|| wep instanceof RocketLauncher.Rocket
						|| wep instanceof RPG7.Rocket
				) {
					BrokenSeal.WarriorShield shield = hero.buff(BrokenSeal.WarriorShield.class);
					dmg += Random.IntRange(0, shield.shielding());
				}
			}
			KindOfWeapon wep = hero.belongings.attackingWeapon();
			if (this instanceof Hero && hero.subClass == HeroSubClass.VETERAN && hero.buff(Focusing.class) != null && Random.Int(3) < hero.pointsInTalent(Talent.BARRIER_FORMATION)){
				if (wep.bullet) {
					BrokenSeal.WarriorShield shield = hero.buff(BrokenSeal.WarriorShield.class);
					if (shield.shielding() < shield.maxShield()) {
						shield.incShield();
						if (hero.pointsInTalent(Talent.FOCUS_UPGRADE) > 1 && hero.buff(Focusing.class) != null) {
							int healAmt = 1;
							healAmt = Math.min( healAmt, Dungeon.hero.HT - Dungeon.hero.HP );
							if (healAmt > 0 && Dungeon.hero.isAlive()) {
								Dungeon.hero.HP += healAmt;
								Dungeon.hero.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
								Dungeon.hero.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
							}
						}
					}
				}
			}


			if (this instanceof Hero) {
				if (Dungeon.hero.buff(ExtraBullet.class) != null) {
					if (wep.bullet) {
						dmg += 3;
					}
				}
			}

			if (Dungeon.hero.buff(Riot.riotTracker.class) != null) {
				if (wep.bullet) {
					dmg *= 0.5f;
				}

			}

			if (this instanceof Hero) {
				if (hero.heroClass == HeroClass.GUNNER) {
					if (wep.gun) {
						dmg += Random.NormalIntRange(0, hero.belongings.weapon.buffedLvl());
					}
				}
			}

			if (this instanceof Hero) {
				if (hero.subClass == HeroSubClass.ENGINEER && Random.Int(5) < hero.pointsInTalent(Talent.ELECTRIC_BULLET)) {
					if (wep.bullet) {
						ArrayList<Lightning.Arc> arcs = new ArrayList<>();
						ArrayList<Char> affected = new ArrayList<>();
						affected.clear();
						arcs.clear();

						Shocking.arc(hero, enemy, 2, affected, arcs);

						affected.remove(enemy); //defender isn't hurt by lightning
						for (Char ch : affected) {
							if (ch.alignment != hero.alignment) {
								ch.damage(Math.round(dmg*0.2f), this);
							}
						}

						hero.sprite.parent.addToFront( new Lightning( arcs, null ) );
						Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
					}
				}
			}

			if (this instanceof Hero && hero.belongings.attackingWeapon() instanceof MissileWeapon && hero.hasTalent(Talent.SHOOTING_EYES) && enemy.buff(Talent.ShootingEyesTracker.class) == null) {
				if (Random.Int(3) < hero.pointsInTalent(Talent.SHOOTING_EYES)) {
					Buff.affect(enemy, Blindness.class, 2f);
				}
				Buff.affect(enemy, Talent.ShootingEyesTracker.class);
			}

			if (this instanceof Hero && hero.belongings.attackingWeapon() instanceof MissileWeapon && hero.hasTalent(Talent.TARGET_SPOTTING) && hero.buff(SnipersMark.class) != null && hero.buff(SnipersMark.class).object == enemy.id()) {
				dmg *= 1+0.1f*hero.pointsInTalent(Talent.TARGET_SPOTTING);
			}

			if (this instanceof Hero && hero.subClass == HeroSubClass.VETERAN && hero.belongings.attackingWeapon() instanceof MissileWeapon && hero.pointsInTalent(Talent.FOCUS_UPGRADE) == 3) {
				BrokenSeal.WarriorShield shield = hero.buff(BrokenSeal.WarriorShield.class);
				if (shield != null){
					Buff.affect( this, Focusing.class ).hit( enemy );
				}
			}

			if (this instanceof Hero && hero.buff(DamageEnhance.class) != null) {
				dmg *= hero.buff(DamageEnhance.class).getDmg();
			}

			if (this instanceof Hero && hero.buff(ArrowEnhance.class) != null
					&& (hero.belongings.attackingWeapon() instanceof SpiritBow.SpiritArrow
						|| hero.belongings.attackingWeapon() instanceof GoldenBow.SpiritArrow
						|| hero.belongings.attackingWeapon() instanceof WindBow.SpiritArrow
						|| hero.belongings.attackingWeapon() instanceof PoisonBow.SpiritArrow
						|| hero.belongings.attackingWeapon() instanceof NaturesBow.SpiritArrow)) {
				dmg *= 1.5f;
			}

			if (this instanceof Hero && hero.belongings.attackingWeapon() instanceof MissileWeapon) {
				int distance = Dungeon.level.distance(hero.pos, enemy.pos) - 1;
				float multiplier = Math.min(2f, (float)Math.pow(1 + 0.025f * hero.pointsInTalent(Talent.RANGED_SNIPING), distance));
				dmg = Math.round(dmg * multiplier);
			}

			if (this instanceof Hero && hero.hasTalent(Talent.TACKLE) && level.adjacent(enemy.pos, hero. pos) && hero.belongings.armor != null && hero.belongings.attackingWeapon() instanceof MeleeWeapon) {
				dmg += hero.belongings.armor.DRMax()*0.05f*hero.pointsInTalent(Talent.TACKLE);
			}

			if (this instanceof Hero && enemy.buff(Charm.class) != null && hero.pointsInTalent(Talent.APPEASE) >= 2) {
				int healAmt = Math.round(dmg/10f);
				healAmt = Math.min( healAmt, Dungeon.hero.HT - Dungeon.hero.HP );
				if (healAmt > 0 && Dungeon.hero.isAlive()) {
					Dungeon.hero.HP += healAmt;
					Dungeon.hero.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
					Dungeon.hero.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
				}
			}

			if (this instanceof Hero) {
				if (RingOfVampire.vampiricProc( hero ) < 0) {
					if (Random.Float() < -RingOfVampire.vampiricProc( hero )) {
						this.damage(Math.round(dmg/5f), hero);
						CellEmitter.get(this.pos).burst(ShadowParticle.UP, 5);
						Sample.INSTANCE.play(Assets.Sounds.CURSED);
						GLog.w(Messages.get(RingOfVampire.class, "damage"));
					}
				} else {
					if (Random.Float() < RingOfVampire.vampiricProc( hero )) {
						int healAmt = Math.round(dmg/5f);
						healAmt = Math.min( healAmt, Dungeon.hero.HT - Dungeon.hero.HP );
						if (healAmt > 0 && Dungeon.hero.isAlive()) {
							Dungeon.hero.HP += healAmt;
							Dungeon.hero.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
							Dungeon.hero.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
						}
					}
				}
			}

			if (this instanceof Hero) {
				dmg *= RingOfFury.dealingMultiplier( hero );
			}

			if (this instanceof Hero && enemy.buff(Charm.class) != null && hero.pointsInTalent(Talent.APPEASE) == 3 && Random.Int(10) == 0 && !enemy.properties().contains(Property.BOSS) && !enemy.properties().contains(Property.MINIBOSS)) {
				dmg = 0;
				int healAmt = enemy.HT;
				healAmt = Math.min( healAmt, enemy.HT - enemy.HP );
				if (healAmt > 0) {
					enemy.HP += healAmt;
					enemy.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
					enemy.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
				}
				AllyBuff.affectAndLoot(((Mob) enemy), hero, ScrollOfSirensSong.Enthralled.class);
			}

			for (ChampionEnemy buff : buffs(ChampionEnemy.class)){
				dmg *= buff.meleeDamageFactor();
			}

			dmg *= AscensionChallenge.statModifier(this);

			//flat damage bonus is applied after positive multipliers, but before negative ones
			dmg += dmgBonus;

			//friendly endure
			Endure.EndureTracker endure = buff(Endure.EndureTracker.class);
			if (endure != null) dmg = endure.damageFactor(dmg);

			//enemy endure
			endure = enemy.buff(Endure.EndureTracker.class);
			if (endure != null){
				dmg = endure.adjustDamageTaken(dmg);
			}

			if (enemy.buff(ScrollOfChallenge.ChallengeArena.class) != null){
				dmg *= 0.67f;
			}

			if (enemy.buff(MonkEnergy.MonkAbility.Meditate.MeditateResistance.class) != null){
				dmg *= 0.2f;
			}

			if ( buff(Weakness.class) != null ){
				dmg *= 0.67f;
			}

			if ( buff(WantedTracker.Wanted.class) != null && hero.pointsInTalent(Talent.SURRENDER) > 1 && !(properties().contains(Char.Property.BOSS) || properties().contains(Property.MINIBOSS))) {
				dmg *= 0.67f;
			}

			if ( buff(SoulMark.class) != null && hero.hasTalent(Talent.MARK_OF_WEAKNESS)) {
				if (this.alignment != Alignment.ALLY) {
					dmg *= 1-0.1f*hero.pointsInTalent(Talent.MARK_OF_WEAKNESS);
				}
			}

			int effectiveDamage = enemy.defenseProc( this, Math.round(dmg) );
			effectiveDamage = Math.max( effectiveDamage - dr, 0 );

			if (enemy.buff(Viscosity.ViscosityTracker.class) != null){
				effectiveDamage = enemy.buff(Viscosity.ViscosityTracker.class).deferDamage(effectiveDamage);
				enemy.buff(Viscosity.ViscosityTracker.class).detach();
			}

			//vulnerable specifically applies after armor reductions
			if ( enemy.buff( Vulnerable.class ) != null){
				effectiveDamage *= 1.33f;
			}

			effectiveDamage = attackProc( enemy, effectiveDamage );

			if (visibleFight) {
				if (effectiveDamage > 0 || !enemy.blockSound(Random.Float(0.96f, 1.05f))) {
					hitSound(Random.Float(0.87f, 1.15f));
				}
			}

			// If the enemy is already dead, interrupt the attack.
			// This matters as defence procs can sometimes inflict self-damage, such as armor glyphs.
			if (!enemy.isAlive()){
				return true;
			}

			enemy.damage( effectiveDamage, this );

			if (buff(FireImbue.class) != null)  buff(FireImbue.class).proc(enemy);
			if (buff(FrostImbue.class) != null) buff(FrostImbue.class).proc(enemy);

			if (enemy.isAlive() && enemy.alignment != alignment && prep != null && prep.canKO(enemy)){
				enemy.HP = 0;
				if (!enemy.isAlive()) {
					enemy.die(this);
				} else {
					//helps with triggering any on-damage effects that need to activate
					enemy.damage(-1, this);
					DeathMark.processFearTheReaper(enemy);
				}
				enemy.sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Preparation.class, "assassinated"));
				if (Random.Int(5) < hero.pointsInTalent(Talent.ENERGY_DRAW)) {
					CloakOfShadows cloak = hero.belongings.getItem(CloakOfShadows.class);
					if (cloak != null) {
						cloak.overCharge(1);
						ScrollOfRecharging.charge( Dungeon.hero );
						SpellSprite.show( hero, SpellSprite.CHARGE );
					}
				}
			}

			Talent.CombinedLethalityTriggerTracker combinedLethality = buff(Talent.CombinedLethalityTriggerTracker.class);
			if (combinedLethality != null){
				if ( enemy.isAlive() && enemy.alignment != alignment && !Char.hasProp(enemy, Property.BOSS)
						&& !Char.hasProp(enemy, Property.MINIBOSS) && this instanceof Hero &&
						(enemy.HP/(float)enemy.HT) <= 0.10f*((Hero)this).pointsInTalent(Talent.COMBINED_LETHALITY)) {
					enemy.HP = 0;
					if (!enemy.isAlive()) {
						enemy.die(this);
					} else {
						//helps with triggering any on-damage effects that need to activate
						enemy.damage(-1, this);
						DeathMark.processFearTheReaper(enemy);
					}
					enemy.sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Talent.CombinedLethalityTriggerTracker.class, "executed"));
				}
				combinedLethality.detach();
			}

			enemy.sprite.bloodBurstA( sprite.center(), effectiveDamage );
			enemy.sprite.flash();

			if (!enemy.isAlive() && visibleFight) {
				if (enemy == Dungeon.hero) {

					if (this == Dungeon.hero) {
						return true;
					}

					if (this instanceof WandOfLivingEarth.EarthGuardian
							|| this instanceof MirrorImage || this instanceof PrismaticImage){
						Badges.validateDeathFromFriendlyMagic();
					}
					Dungeon.fail( getClass() );
					GLog.n( Messages.capitalize(Messages.get(Char.class, "kill", name())) );

				} else if (this == Dungeon.hero) {
					GLog.i( Messages.capitalize(Messages.get(Char.class, "defeat", enemy.name())) );
				}
			}

			return true;

		} else {

			if (enemy instanceof Hero) {
				Demonization demonization = hero.buff(Demonization.class);
				if (demonization != null
						&& demonization.isDemonated()
						&& Random.Int(5) == 0
				) {
					Buff.prolong(hero, Flurry.class, Flurry.DURATION);
				}
			}

			if (enemy instanceof Hero && hero.pointsInTalent(Talent.SWIFT_MOVEMENT) == 3) {
				Buff.prolong(hero, Invisibility.class, 1.0001f);
			}

			if (enemy instanceof Hero && Random.Int(5) < hero.pointsInTalent(Talent.COUNTER_ATTACK)) {
				Buff.affect(hero, Talent.CounterAttackTracker.class);
			}

			if (enemy instanceof Hero && hero.pointsInTalent(Talent.DETECTIVE_SLASHING) > 1 && enemy.buff(Sheathing.class) != null) {
				Buff.affect(hero, Talent.DetactiveSlashingTracker.class);
			}

			if (enemy instanceof Hero && hero.hasTalent(Talent.QUICK_PREP)) {
				Momentum momentum = hero.buff(Momentum.class);
				if (momentum != null) {
					momentum.quickPrep(hero.pointsInTalent(Talent.QUICK_PREP));
				}
			}

			if (enemy instanceof Hero && Random.Int(2) < hero.pointsInTalent(Talent.QUICK_RECOVER)) {
				int healAmt = 1;
				healAmt = Math.min( healAmt, hero.HT - hero.HP );
				if (healAmt > 0 && hero.isAlive()) {
					hero.HP += healAmt;
					hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1);
					hero.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
				}
			}

			enemy.sprite.showStatus( CharSprite.NEUTRAL, enemy.defenseVerb() );
			if (visibleFight) {
				//TODO enemy.defenseSound? currently miss plays for monks/crab even when they parry
				Sample.INSTANCE.play(Assets.Sounds.MISS);
			}

			return false;

		}
	}

	public static int INFINITE_ACCURACY = 1_000_000;
	public static int INFINITE_EVASION = 1_000_000;

	final public static boolean hit( Char attacker, Char defender, boolean magic ) {
		return hit(attacker, defender, magic ? 2f : 1f, magic);
	}

	public static boolean hit( Char attacker, Char defender, float accMulti, boolean magic ) {
		float acuStat = attacker.attackSkill( defender );
		float defStat = defender.defenseSkill( attacker );

		//invisible chars always hit (for the hero this is surprise attacking)
		if (attacker.invisible > 0 && attacker.canSurpriseAttack()){
			acuStat = INFINITE_ACCURACY;
		}

		if (defender.buff(MonkEnergy.MonkAbility.Focus.FocusBuff.class) != null && !magic){
			defStat = INFINITE_EVASION;
		}

		//if accuracy or evasion are large enough, treat them as infinite.
		//note that infinite evasion beats infinite accuracy
		if (defStat >= INFINITE_EVASION){
			return false;
		} else if (acuStat >= INFINITE_ACCURACY){
			return true;
		}

		float acuRoll = Random.Float( acuStat );
		if (attacker.buff(Bless.class) != null) {
			if (attacker instanceof Hero) {
				if (hero.hasTalent(Talent.BLESS_ENHANCE)) {
					acuRoll *= 1 + 0.25f * (1 + 0.2f * hero.pointsInTalent(Talent.BLESS_ENHANCE));
				} else {
					acuRoll *= 1.25f;
				}
			} else {
				acuRoll *= 1.25f;
			}
		}
		if (attacker.buff(  Hex.class) != null) acuRoll *= 0.8f;
		for (ChampionEnemy buff : attacker.buffs(ChampionEnemy.class)){
			acuRoll *= buff.evasionAndAccuracyFactor();
		}
		acuRoll *= AscensionChallenge.statModifier(attacker);

		float defRoll = Random.Float( defStat );
		if (defender.buff(Bless.class) != null) {
			if (defender instanceof Hero) {
				if (hero.hasTalent(Talent.BLESS_ENHANCE)) {
					defRoll *= 1 + 0.25f * (1 + 0.2f * hero.pointsInTalent(Talent.BLESS_ENHANCE));
				} else {
					defRoll *= 1.25f;
				}
			} else {
				defRoll *= 1.25f;
			}
		}
		if (defender.buff(  Hex.class) != null) defRoll *= 0.8f;
		for (ChampionEnemy buff : defender.buffs(ChampionEnemy.class)){
			defRoll *= buff.evasionAndAccuracyFactor();
		}
		defRoll *= AscensionChallenge.statModifier(defender);

		return (acuRoll * accMulti) >= defRoll;
	}

	public int attackSkill( Char target ) {
		return 0;
	}

	public int defenseSkill( Char enemy ) {
		return 0;
	}

	public String defenseVerb() {
		return Messages.get(this, "def_verb");
	}

	public int drRoll() {
		int dr = 0;

		Barkskin bark = buff(Barkskin.class);
		if (bark != null)   dr += Random.NormalIntRange( 0 , bark.level() );

		return dr;
	}

	public int damageRoll() {
		return 1;
	}

	//TODO it would be nice to have a pre-armor and post-armor proc.
	// atm attack is always post-armor and defence is already pre-armor

	public int attackProc( Char enemy, int damage ) {
		for (ChampionEnemy buff : buffs(ChampionEnemy.class)){
			buff.onAttackProc( enemy );
		}
		return damage;
	}

	public int defenseProc( Char enemy, int damage ) {

		Earthroot.Armor armor = buff( Earthroot.Armor.class );
		if (armor != null) {
			damage = armor.absorb( damage );
		}

		return damage;
	}

	public float speed() {
		float speed = baseSpeed;
		if ( buff( Cripple.class ) != null ) speed /= 2f;
		if ( buff( Stamina.class ) != null) speed *= 1.5f;
		if ( buff( Adrenaline.class ) != null) speed *= 2f;
		if ( buff( Haste.class ) != null) speed *= 3f;
		if ( buff( AngelWing.AngelWingBuff.class ) != null) speed *= 3f;
		if ( buff( Dread.class ) != null) speed *= 2f;
		return speed;
	}

	//currently only used by invisible chars, or by the hero
	public boolean canSurpriseAttack(){
		return true;
	}

	//used so that buffs(Shieldbuff.class) isn't called every time unnecessarily
	private int cachedShield = 0;
	public boolean needsShieldUpdate = true;

	public int shielding(){
		if (!needsShieldUpdate){
			return cachedShield;
		}

		cachedShield = 0;
		for (ShieldBuff s : buffs(ShieldBuff.class)){
			cachedShield += s.shielding();
		}
		needsShieldUpdate = false;
		return cachedShield;
	}

	public void damage( int dmg, Object src ) {

		if (!isAlive() || dmg < 0) {
			return;
		}

		if(isInvulnerable(src.getClass())){
			sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "invulnerable"));
			return;
		}

		for (ChampionEnemy buff : buffs(ChampionEnemy.class)){
			dmg = (int) Math.ceil(dmg * buff.damageTakenFactor());
		}
		dmg = (int)Math.ceil(dmg / AscensionChallenge.statModifier(this));

		if (!(src instanceof LifeLink) && buff(LifeLink.class) != null){
			HashSet<LifeLink> links = buffs(LifeLink.class);
			for (LifeLink link : links.toArray(new LifeLink[0])){
				if (Actor.findById(link.object) == null){
					links.remove(link);
					link.detach();
				}
			}
			dmg = (int)Math.ceil(dmg / (float)(links.size()+1));
			for (LifeLink link : links){
				Char ch = (Char)Actor.findById(link.object);
				if (ch != null) {
					ch.damage(dmg, link);
					if (!ch.isAlive()) {
						link.detach();
					}
				}
			}
		}

		Terror t = buff(Terror.class);
		if (t != null){
			t.recover();
		}
		Dread d = buff(Dread.class);
		if (d != null){
			d.recover();
		}
		Charm c = buff(Charm.class);
		if (c != null){
			c.recover(src);
		}
		if (this.buff(Frost.class) != null){
			Buff.detach( this, Frost.class );
		}
		if (this.buff(MagicalSleep.class) != null){
			Buff.detach(this, MagicalSleep.class);
		}
		if (this.buff(Doom.class) != null && !isImmune(Doom.class)){
			dmg *= 1.67f;
		}
		if (alignment != Alignment.ALLY && this.buff(DeathMark.DeathMarkTracker.class) != null){
			dmg *= 1.25f;
		}

		Class<?> srcClass = src.getClass();
		if (isImmune( srcClass )) {
			dmg = 0;
		} else {
			dmg = Math.round( dmg * resist( srcClass ));
		}

		//TODO improve this when I have proper damage source logic
		if (AntiMagic.RESISTS.contains(src.getClass()) && buff(ArcaneArmor.class) != null){
			dmg -= Random.NormalIntRange(0, buff(ArcaneArmor.class).level());
			if (dmg < 0) dmg = 0;
		}

		if (buff( Paralysis.class ) != null) {
			buff( Paralysis.class ).processDamage(dmg);
		}

		int shielded = dmg;
		//FIXME: when I add proper damage properties, should add an IGNORES_SHIELDS property to use here.
		if (!(src instanceof Hunger)){
			for (ShieldBuff s : buffs(ShieldBuff.class)){
				dmg = s.absorbDamage(dmg);
				if (dmg == 0) break;
			}
		}
		shielded -= dmg;
		HP -= dmg;

		if (HP < 0 && src instanceof Char){
			if (((Char) src).buff(Kinetic.KineticTracker.class) != null){
				int dmgToAdd = -HP;
				dmgToAdd -= ((Char) src).buff(Kinetic.KineticTracker.class).conservedDamage;
				dmgToAdd = Math.round(dmgToAdd * Weapon.Enchantment.genericProcChanceMultiplier((Char) src));
				if (dmgToAdd > 0) {
					Buff.affect((Char) src, Kinetic.ConservedDamage.class).setBonus(dmgToAdd);
				}
				((Char) src).buff(Kinetic.KineticTracker.class).detach();
			}
		}

		if (sprite != null) {
			sprite.showStatus(HP > HT / 2 ?
							CharSprite.WARNING :
							CharSprite.NEGATIVE,
					Integer.toString(dmg + shielded));
		}

		if (HP < 0) HP = 0;

		if (!isAlive()) {
			die( src );
		} else if (HP == 0 && buff(DeathMark.DeathMarkTracker.class) != null){
			DeathMark.processFearTheReaper(this);
		}
	}

	public void destroy() {
		HP = 0;
		Actor.remove( this );

		for (Char ch : Actor.chars().toArray(new Char[0])){
			if (ch.buff(Charm.class) != null && ch.buff(Charm.class).object == id()){
				ch.buff(Charm.class).detach();
			}
			if (ch.buff(Dread.class) != null && ch.buff(Dread.class).object == id()){
				ch.buff(Dread.class).detach();
			}
			if (ch.buff(Terror.class) != null && ch.buff(Terror.class).object == id()){
				ch.buff(Terror.class).detach();
			}
			if (ch.buff(SnipersMark.class) != null && ch.buff(SnipersMark.class).object == id()){
				ch.buff(SnipersMark.class).detach();
			}
		}
	}

	public void die( Object src ) {
		destroy();
		if (src != Chasm.class) sprite.die();
	}

	//we cache this info to prevent having to call buff(...) in isAlive.
	//This is relevant because we call isAlive during drawing, which has both performance
	//and thread coordination implications
	public boolean deathMarked = false;

	public boolean isAlive() {
		return HP > 0 || deathMarked;
	}

	public boolean isActive() {
		return isAlive();
	}

	@Override
	protected void spendConstant(float time) {
		TimekeepersHourglass.timeFreeze freeze = buff(TimekeepersHourglass.timeFreeze.class);
		if (freeze != null) {
			freeze.processTime(time);
			return;
		}

		Swiftthistle.TimeBubble bubble = buff(Swiftthistle.TimeBubble.class);
		if (bubble != null){
			bubble.processTime(time);
			return;
		}

		super.spendConstant(time);
	}

	@Override
	protected void spend( float time ) {

		float timeScale = 1f;
		if (buff( Slow.class ) != null) {
			timeScale *= 0.5f;
			//slowed and chilled do not stack
		} else if (buff( Chill.class ) != null) {
			timeScale *= buff( Chill.class ).speedFactor();
		}
		if (buff( Speed.class ) != null) {
			timeScale *= 2.0f;
		}

		super.spend( time / timeScale );
	}

	public synchronized LinkedHashSet<Buff> buffs() {
		return new LinkedHashSet<>(buffs);
	}

	@SuppressWarnings("unchecked")
	//returns all buffs assignable from the given buff class
	public synchronized <T extends Buff> HashSet<T> buffs( Class<T> c ) {
		HashSet<T> filtered = new HashSet<>();
		for (Buff b : buffs) {
			if (c.isInstance( b )) {
				filtered.add( (T)b );
			}
		}
		return filtered;
	}

	@SuppressWarnings("unchecked")
	//returns an instance of the specific buff class, if it exists. Not just assignable
	public synchronized  <T extends Buff> T buff( Class<T> c ) {
		for (Buff b : buffs) {
			if (b.getClass() == c) {
				return (T)b;
			}
		}
		return null;
	}

	public synchronized boolean isCharmedBy( Char ch ) {
		int chID = ch.id();
		for (Buff b : buffs) {
			if (b instanceof Charm && ((Charm)b).object == chID) {
				return true;
			}
		}
		return false;
	}

	public synchronized boolean add( Buff buff ) {

		if (buff(PotionOfCleansing.Cleanse.class) != null) { //cleansing buff
			if (buff.type == Buff.buffType.NEGATIVE
					&& !(buff instanceof AllyBuff)
					&& !(buff instanceof LostInventory)){
				return false;
			}
		}

		if (sprite != null && buff(Challenge.SpectatorFreeze.class) != null){
			return false; //can't add buffs while frozen and game is loaded
		}

		buffs.add( buff );
		if (Actor.chars().contains(this)) Actor.add( buff );

		if (sprite != null && buff.announced) {
			switch (buff.type) {
				case POSITIVE:
					sprite.showStatus(CharSprite.POSITIVE, Messages.titleCase(buff.name()));
					break;
				case NEGATIVE:
					sprite.showStatus(CharSprite.NEGATIVE, Messages.titleCase(buff.name()));
					break;
				case NEUTRAL:
				default:
					sprite.showStatus(CharSprite.NEUTRAL, Messages.titleCase(buff.name()));
					break;
			}
		}

		return true;

	}

	public synchronized boolean remove( Buff buff ) {
		buffs.remove( buff );
		Actor.remove( buff );

		return true;
	}

	public synchronized void remove( Class<? extends Buff> buffClass ) {
		for (Buff buff : buffs( buffClass )) {
			remove( buff );
		}
	}

	@Override
	protected synchronized void onRemove() {
		for (Buff buff : buffs.toArray(new Buff[buffs.size()])) {
			buff.detach();
		}
	}

	public synchronized void updateSpriteState() {
		for (Buff buff:buffs) {
			buff.fx( true );
		}
	}

	public float stealth() {
		return 0;
	}

	public final void move( int step ) {
		move( step, true );
	}

	//travelling may be false when a character is moving instantaneously, such as via teleportation
	public void move( int step, boolean travelling ) {

		if (travelling && Dungeon.level.adjacent( step, pos ) && buff( Vertigo.class ) != null) {
			sprite.interruptMotion();
			int newPos = pos + PathFinder.NEIGHBOURS8[Random.Int( 8 )];
			if (!(Dungeon.level.passable[newPos] || Dungeon.level.avoid[newPos])
					|| (properties().contains(Property.LARGE) && !Dungeon.level.openSpace[newPos])
					|| Actor.findChar( newPos ) != null)
				return;
			else {
				sprite.move(pos, newPos);
				step = newPos;
			}
		}

		if (Dungeon.level.map[pos] == Terrain.OPEN_DOOR) {
			Door.leave( pos );
		}

		pos = step;

		if (this != Dungeon.hero) {
			sprite.visible = Dungeon.level.heroFOV[pos];
		}

		Dungeon.level.occupyCell(this );
	}

	public int distance( Char other ) {
		return Dungeon.level.distance( pos, other.pos );
	}

	public void onMotionComplete() {
		//Does nothing by default
		//The main actor thread already accounts for motion,
		// so calling next() here isn't necessary (see Actor.process)
	}

	public void onAttackComplete() {
		next();
	}

	public void onOperateComplete() {
		next();
	}

	protected final HashSet<Class> resistances = new HashSet<>();

	//returns percent effectiveness after resistances
	//TODO currently resistances reduce effectiveness by a static 50%, and do not stack.
	public float resist( Class effect ){
		HashSet<Class> resists = new HashSet<>(resistances);
		for (Property p : properties()){
			resists.addAll(p.resistances());
		}
		for (Buff b : buffs()){
			resists.addAll(b.resistances());
		}

		float result = 1f;
		for (Class c : resists){
			if (c.isAssignableFrom(effect)){
				result *= 0.5f;
			}
		}
		return result * RingOfElements.resist(this, effect);
	}

	protected final HashSet<Class> immunities = new HashSet<>();

	public boolean isImmune(Class effect ){
		HashSet<Class> immunes = new HashSet<>(immunities);
		for (Property p : properties()){
			immunes.addAll(p.immunities());
		}
		for (Buff b : buffs()){
			immunes.addAll(b.immunities());
		}

		for (Class c : immunes){
			if (c.isAssignableFrom(effect)){
				return true;
			}
		}
		return false;
	}

	//similar to isImmune, but only factors in damage.
	//Is used in AI decision-making
	public boolean isInvulnerable( Class effect ){
		return buff(Challenge.SpectatorFreeze.class) != null;
	}

	protected HashSet<Property> properties = new HashSet<>();

	public HashSet<Property> properties() {
		HashSet<Property> props = new HashSet<>(properties);
		//TODO any more of these and we should make it a property of the buff, like with resistances/immunities
		if (buff(ChampionEnemy.Giant.class) != null) {
			props.add(Property.LARGE);
		}
		return props;
	}

	public enum Property{
		BOSS ( new HashSet<Class>( Arrays.asList(Grim.class, GrimTrap.class, ScrollOfRetribution.class, ScrollOfPsionicBlast.class)),
				new HashSet<Class>( Arrays.asList(AllyBuff.class, Dread.class) )),
		MINIBOSS ( new HashSet<Class>(),
				new HashSet<Class>( Arrays.asList(AllyBuff.class, Dread.class) )),
		BOSS_MINION,
		UNDEAD,
		DEMONIC,
		INORGANIC ( new HashSet<Class>(),
				new HashSet<Class>( Arrays.asList(Bleeding.class, ToxicGas.class, Poison.class) )),
		FIERY ( new HashSet<Class>( Arrays.asList(WandOfFireblast.class, Elemental.FireElemental.class)),
				new HashSet<Class>( Arrays.asList(Burning.class, Blazing.class))),
		ICY ( new HashSet<Class>( Arrays.asList(WandOfFrost.class, Elemental.FrostElemental.class)),
				new HashSet<Class>( Arrays.asList(Frost.class, Chill.class))),
		ACIDIC ( new HashSet<Class>( Arrays.asList(Corrosion.class)),
				new HashSet<Class>( Arrays.asList(Ooze.class))),
		ELECTRIC ( new HashSet<Class>( Arrays.asList(WandOfLightning.class, Shocking.class, Potential.class, Electricity.class, ShockingDart.class, Elemental.ShockElemental.class )),
				new HashSet<Class>()),
		LARGE,
		IMMOVABLE;

		private HashSet<Class> resistances;
		private HashSet<Class> immunities;

		Property(){
			this(new HashSet<Class>(), new HashSet<Class>());
		}

		Property( HashSet<Class> resistances, HashSet<Class> immunities){
			this.resistances = resistances;
			this.immunities = immunities;
		}

		public HashSet<Class> resistances(){
			return new HashSet<>(resistances);
		}

		public HashSet<Class> immunities(){
			return new HashSet<>(immunities);
		}

	}

	public static boolean hasProp( Char ch, Property p){
		return (ch != null && ch.properties().contains(p));
	}

	public void heal(int amount) {
		amount = Math.min( amount, this.HT - this.HP );
		if (amount > 0 && this.isAlive()) {
			this.HP += amount;
			this.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
			this.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( amount ) );
		}
	}
}
