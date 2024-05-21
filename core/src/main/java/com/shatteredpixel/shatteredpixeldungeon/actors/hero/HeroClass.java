/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.QuickSlot;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.adventurer.Root;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.adventurer.Sprout;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.adventurer.TreasureMap;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.Challenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.ElementalStrike;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.Feint;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.FirstAidKit;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.ReinforcedArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.Riot;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.NaturesPower;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.SpectralBlades;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.SpiritHawk;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.ElementalBlast;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.WarpBeacon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.WildMagic;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.DeathMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.ShadowClone;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.SmokeBomb;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.samurai.Abil_Kunai;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.samurai.Awake;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.samurai.ShadowBlade;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.Endure;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.HeroicLeap;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.Shockwave;
import com.shatteredpixel.shatteredpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.shatteredpixeldungeon.items.BulletBelt;
import com.shatteredpixel.shatteredpixeldungeon.items.GunSmithingTool;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KingsCrown;
import com.shatteredpixel.shatteredpixeldungeon.items.Sheath;
import com.shatteredpixel.shatteredpixeldungeon.items.Teleporter;
import com.shatteredpixel.shatteredpixeldungeon.items.TengusMask;
import com.shatteredpixel.shatteredpixeldungeon.items.Waterskin;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClothArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.PlateArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.VelvetPouch;
import com.shatteredpixel.shatteredpixeldungeon.items.changer.OldAmulet;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfInvisibility;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfPurity;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAccuracy;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Dagger;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gloves;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Machete;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Rapier;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Shovel;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WornKatana;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WornShortsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.TacticalShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.AR.AR_T1;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.AR.AR_T2;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingSpike;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.DeviceCompat;

public enum HeroClass {

	WARRIOR( HeroSubClass.BERSERKER, HeroSubClass.GLADIATOR, HeroSubClass.VETERAN ),
	MAGE( HeroSubClass.BATTLEMAGE, HeroSubClass.WARLOCK, HeroSubClass.WIZARD ),
	ROGUE( HeroSubClass.ASSASSIN, HeroSubClass.FREERUNNER, HeroSubClass.CHASER ),
	HUNTRESS( HeroSubClass.SNIPER, HeroSubClass.WARDEN, HeroSubClass.FIGHTER ),
	DUELIST( HeroSubClass.CHAMPION, HeroSubClass.MONK, HeroSubClass.FENCER ),
	GUNNER( HeroSubClass.OUTLAW, HeroSubClass.GUNSLINGER, HeroSubClass.SPECIALIST ),
	SAMURAI( HeroSubClass.SLASHER, HeroSubClass.MASTER, HeroSubClass.SLAYER ),
	ADVENTURER( HeroSubClass.ENGINEER, HeroSubClass.EXPLORER, HeroSubClass.RESEARCHER );

	private HeroSubClass[] subClasses;

	HeroClass( HeroSubClass...subClasses ) {
		this.subClasses = subClasses;
	}

	public void initHero( Hero hero ) {

		hero.heroClass = this;
		Talent.initClassTalents(hero);

		Item i = new ClothArmor().identify();
		if (!Challenges.isItemBlocked(i)) hero.belongings.armor = (ClothArmor)i;

		i = new Food();
		if (!Challenges.isItemBlocked(i)) i.collect();

		new VelvetPouch().collect();
		Dungeon.LimitedDrops.VELVET_POUCH.drop();

		Waterskin waterskin = new Waterskin();
		waterskin.collect();

		new ScrollOfIdentify().identify();

		if (DeviceCompat.isDebug() || SPDSettings.customSeed().contains("test")) {
			new RingOfMight().identify().upgrade(10).collect();
			new RingOfHaste().identify().upgrade(100).collect();
			new RingOfAccuracy().identify().upgrade(100).collect();
			new AlchemistsToolkit().identify().upgrade(10).collect();
			new ElixirOfHoneyedHealing().identify().quantity(500).collect();
			new PotionOfExperience().identify().quantity(29).collect();
			new PlateArmor().identify().upgrade(100).collect();
			new TacticalShield().identify().upgrade(100).collect();
			new GunSmithingTool().quantity(200).collect();
			new Teleporter().collect();
			new TengusMask().collect();
			new KingsCrown().collect();
			new OldAmulet().collect();
		}

		switch (this) {
			case WARRIOR:
				initWarrior( hero );
				break;

			case MAGE:
				initMage( hero );
				break;

			case ROGUE:
				initRogue( hero );
				break;

			case HUNTRESS:
				initHuntress( hero );
				break;

			case DUELIST:
				initDuelist( hero );
				break;

			case GUNNER:
				initGunner( hero );
				break;

			case SAMURAI:
				initSamurai( hero );
				break;

			case ADVENTURER:
				initAdventurer( hero );
				break;
		}

		if (SPDSettings.quickslotWaterskin()) {
			for (int s = 0; s < QuickSlot.SIZE; s++) {
				if (Dungeon.quickslot.getItem(s) == null) {
					Dungeon.quickslot.setSlot(s, waterskin);
					break;
				}
			}
		}

	}

	public Badges.Badge masteryBadge() {
		switch (this) {
			case WARRIOR:
				return Badges.Badge.MASTERY_WARRIOR;
			case MAGE:
				return Badges.Badge.MASTERY_MAGE;
			case ROGUE:
				return Badges.Badge.MASTERY_ROGUE;
			case HUNTRESS:
				return Badges.Badge.MASTERY_HUNTRESS;
			case DUELIST:
				return Badges.Badge.MASTERY_DUELIST;
			case GUNNER:
				return Badges.Badge.MASTERY_GUNNER;
			case SAMURAI:
				return Badges.Badge.MASTERY_SAMURAI;
			case ADVENTURER:
				return Badges.Badge.MASTERY_ADVENTURER;
		}
		return null;
	}

	private static void initWarrior( Hero hero ) {
		(hero.belongings.weapon = new WornShortsword()).identify();
		ThrowingStone stones = new ThrowingStone();
		stones.quantity(3).collect();
		Dungeon.quickslot.setSlot(0, stones);

		if (hero.belongings.armor != null){
			hero.belongings.armor.affixSeal(new BrokenSeal());
		}

		new PotionOfHealing().identify();
		new ScrollOfRage().identify();
	}

	private static void initMage( Hero hero ) {
		MagesStaff staff;

		staff = new MagesStaff(new WandOfMagicMissile());

		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);

		new ScrollOfUpgrade().identify();
		new PotionOfLiquidFlame().identify();
	}

	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Dagger()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.artifact = cloak).identify();
		hero.belongings.artifact.activate( hero );

		ThrowingKnife knives = new ThrowingKnife();
		knives.quantity(3).collect();

		Dungeon.quickslot.setSlot(0, cloak);
		Dungeon.quickslot.setSlot(1, knives);

		new ScrollOfMagicMapping().identify();
		new PotionOfInvisibility().identify();
	}

	private static void initHuntress( Hero hero ) {

		(hero.belongings.weapon = new Gloves()).identify();
		SpiritBow bow = new SpiritBow();
		bow.identify().collect();

		Dungeon.quickslot.setSlot(0, bow);

		new PotionOfMindVision().identify();
		new ScrollOfLullaby().identify();
	}

	private static void initDuelist( Hero hero ) {

		(hero.belongings.weapon = new Rapier()).identify();
		hero.belongings.weapon.activate(hero);

		ThrowingSpike spikes = new ThrowingSpike();
		spikes.quantity(2).collect();

		Dungeon.quickslot.setSlot(0, hero.belongings.weapon);
		Dungeon.quickslot.setSlot(1, spikes);

		new PotionOfStrength().identify();
		new ScrollOfMirrorImage().identify();
	}

	private static void initGunner( Hero hero ) {
		(hero.belongings.weapon = new AR_T1()).identify();
		hero.belongings.weapon.activate(hero);

		BulletBelt bulletBelt = new BulletBelt();
		bulletBelt.quantity(5).collect();

		Dungeon.quickslot.setSlot(0, hero.belongings.weapon);
		Dungeon.quickslot.setSlot(1, bulletBelt);

		new PotionOfHaste().identify();
		new ScrollOfTeleportation().identify();
	}

	private static void initSamurai( Hero hero ) {

		WornKatana wornKatana = new WornKatana();
		(hero.belongings.weapon = wornKatana).identify();

		Sheath sheath = new Sheath();
		sheath.collect();

		ThrowingKnife knives = new ThrowingKnife();
		knives.quantity(3).collect();

		Dungeon.quickslot.setSlot(0, sheath);
		Dungeon.quickslot.setSlot(1, knives);

		new ScrollOfRetribution().identify();
		new PotionOfStrength().identify();
	}

	private static void initAdventurer( Hero hero ) {
		Shovel shovel = new Shovel();
		(hero.belongings.weapon = shovel).identify();
		hero.belongings.weapon.activate(hero);

		Machete machete = new Machete();
		machete.identify().collect();

		ThrowingStone stones = new ThrowingStone();
		stones.quantity(3).collect();

		Dungeon.quickslot.setSlot(0, shovel);
		Dungeon.quickslot.setSlot(1, machete);
		Dungeon.quickslot.setSlot(2, stones);

		new ScrollOfMagicMapping().identify();
		new PotionOfPurity().identify();
	}
//
//	private static void initKnight( Hero hero ) {
//		Saber saber = new Saber();
//		(hero.belongings.weapon = saber).identify();
//		hero.belongings.weapon.activate(hero);
//		KnightsShield shield = new KnightsShield();
//		shield.collect();
//		Dungeon.quickslot.setSlot(0, shield);
//		ThrowingStone stones = new ThrowingStone();
//		stones.quantity(3).collect();
//		Dungeon.quickslot.setSlot(1, stones);
//
//		if (Dungeon.isChallenged(Challenges.GAMBLER)) {
//			RingOfWealth wealth = new RingOfWealth();
//			(hero.belongings.ring = wealth).identify();
//			hero.belongings.ring.activate( hero );
//		}
//
//		new ScrollOfRemoveCurse().identify();
//		new PotionOfParalyticGas().identify();
//	}
//
//	private static void initNurse( Hero hero ) {
//		HealBook healBook = new HealBook();
//		(hero.belongings.weapon = healBook).identify();
//		hero.belongings.weapon.activate(hero);
//
//		GammaRayGun gammaRayGun = new GammaRayGun();
//		gammaRayGun.collect();
//		Dungeon.quickslot.setSlot(0, gammaRayGun);
//
//		HandMirror handMirror = new HandMirror();
//		handMirror.collect();
//		Dungeon.quickslot.setSlot(1, handMirror);
//
//		HealingDart healingDart = new HealingDart();
//		healingDart.quantity(2).collect();
//		Dungeon.quickslot.setSlot(2, healingDart);
//
//		if (Dungeon.isChallenged(Challenges.GAMBLER)) {
//			RingOfWealth wealth = new RingOfWealth();
//			(hero.belongings.ring = wealth).identify();
//			hero.belongings.ring.activate(hero);
//		}
//
//		new ScrollOfMirrorImage().identify();
//		new PotionOfHealing().identify();
//	}

	public String title() {
		return Messages.get(HeroClass.class, name());
	}

	public String desc(){
		return Messages.get(HeroClass.class, name()+"_desc");
	}

	public String shortDesc(){
		return Messages.get(HeroClass.class, name()+"_desc_short");
	}

	public HeroSubClass[] subClasses() {
		return subClasses;
	}

	public ArmorAbility[] armorAbilities(){
		switch (this) {
			case WARRIOR: default:
				return new ArmorAbility[]{new HeroicLeap(), new Shockwave(), new Endure()};
			case MAGE:
				return new ArmorAbility[]{new ElementalBlast(), new WildMagic(), new WarpBeacon()};
			case ROGUE:
				return new ArmorAbility[]{new SmokeBomb(), new DeathMark(), new ShadowClone()};
			case HUNTRESS:
				return new ArmorAbility[]{new SpectralBlades(), new NaturesPower(), new SpiritHawk()};
			case DUELIST:
				return new ArmorAbility[]{new Challenge(), new ElementalStrike(), new Feint()};
			case GUNNER:
				return new ArmorAbility[]{new Riot(), new ReinforcedArmor(), new FirstAidKit()};
			case SAMURAI:
				return new ArmorAbility[]{new Awake(), new ShadowBlade(), new Abil_Kunai()};
			case ADVENTURER:
				return new ArmorAbility[]{new Sprout(), new TreasureMap(), new Root()};
//			case KNIGHT:
//				return new ArmorAbility[]{new HolyShield(), new StimPack(), new UnstableAnkh()};
//			case NURSE:
//				return new ArmorAbility[]{new HealareaGenerator(), new AngelWing(), new GammaRayEmmit()};
		}
	}

	public String spritesheet() {
		switch (this) {
			case WARRIOR: default:
				return Assets.Sprites.WARRIOR;
			case MAGE:
				return Assets.Sprites.MAGE;
			case ROGUE:
				return Assets.Sprites.ROGUE;
			case HUNTRESS:
				return Assets.Sprites.HUNTRESS;
			case DUELIST:
				return Assets.Sprites.DUELIST;
			case GUNNER:
				return Assets.Sprites.GUNNER;
			case SAMURAI:
				return Assets.Sprites.SAMURAI;
			case ADVENTURER:
				return Assets.Sprites.ADVENTURER;
//			case KNIGHT:
//				return Assets.Sprites.KNIGHT;
//			case NURSE:
//				return Assets.Sprites.NURSE;
		}
	}

	public String splashArt(){
		switch (this) {
			case WARRIOR: default:
				return Assets.Splashes.WARRIOR;
			case MAGE:
				return Assets.Splashes.MAGE;
			case ROGUE:
				return Assets.Splashes.ROGUE;
			case HUNTRESS:
				return Assets.Splashes.HUNTRESS;
			case DUELIST:
				return Assets.Splashes.DUELIST;
			case GUNNER:
				return Assets.Splashes.GUNNER;
			case SAMURAI:
				return Assets.Splashes.SAMURAI;
			case ADVENTURER:
				return Assets.Splashes.ADVENTURER;
//			case KNIGHT:
//				return Assets.Splashes.KNIGHT;
//			case NURSE:
//				return Assets.Splashes.NURSE;
		}
	}
	
	public boolean isUnlocked(){
		//always unlock on debug builds
		if (DeviceCompat.isDebug()) return true;

		switch (this){
			case WARRIOR: default:
				return true;
			case MAGE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_MAGE);
			case ROGUE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_ROGUE);
			case HUNTRESS:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_HUNTRESS);
			case DUELIST:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_DUELIST);
			case GUNNER:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_GUNNER);
			case SAMURAI:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_SAMURAI);
			case ADVENTURER:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_ADVENTURER);
//			case KNIGHT:
//				return Badges.isUnlocked(Badges.Badge.UNLOCK_KNIGHT);
//			case NURSE:
//				return Badges.isUnlocked(Badges.Badge.UNLOCK_NURSE);
		}
	}
	
	public String unlockMsg() {
		return shortDesc() + "\n\n" + Messages.get(HeroClass.class, name()+"_unlock");
	}

}
