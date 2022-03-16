/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.GhostSpawner;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.FirstAidKit;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.NaturesPower;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.SpectralBlades;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.SpiritHawk;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.knight.StimPack;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.knight.UnstableAnkh;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.knight.HolyShield;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.ElementalBlast;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.WarpBeacon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.WildMagic;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.planter.Root;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.planter.Sprout;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.planter.TreasureMap;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.DeathMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.ShadowClone;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.SmokeBomb;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.ReinforcedArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.samurai.Awake;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.samurai.Abil_Kunai;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.samurai.ShadowBlade;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.Endure;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.HeroicLeap;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.Riot;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.Shockwave;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KingsCrown;
import com.shatteredpixel.shatteredpixeldungeon.items.KnightsShield;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.Teleporter;
import com.shatteredpixel.shatteredpixeldungeon.items.TengusMask;
import com.shatteredpixel.shatteredpixeldungeon.items.Waterskin;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClothArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.PlateArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.EtherealChains;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.SandalsOfNature;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.VelvetPouch;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfArmorEnhance;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfInvisibility;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfParalyticGas;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfPurity;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfWeaponEnhance;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfDragonsBlood;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfHealth;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfArmorUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfDragonsBreath;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfMagicalSight;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfStormClouds;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfWeaponUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAccuracy;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfFuror;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfReload;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfMetamorphosis;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPassage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.AdvancedEvolution;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.AquaBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.GunSmithingTool;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.HPBullet;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.HandyBarricade;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ScrollOfExtract;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAugmentation;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfRegrowth;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ChainFlail;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Dagger;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gloves;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.IronHammer;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Longsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Magnum;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MissileButton;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RPG7;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Saber;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Shovel;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Empty;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Warding;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TestWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WornKatana;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WornShortsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.watabou.utils.DeviceCompat;

public enum HeroClass {

	WARRIOR( HeroSubClass.BERSERKER, HeroSubClass.GLADIATOR, HeroSubClass.VETERAN ),
	MAGE( HeroSubClass.BATTLEMAGE, HeroSubClass.WARLOCK, HeroSubClass.ENGINEER ),
	ROGUE( HeroSubClass.ASSASSIN, HeroSubClass.FREERUNNER, HeroSubClass.CHASER ),
	HUNTRESS( HeroSubClass.SNIPER, HeroSubClass.WARDEN, HeroSubClass.FIGHTER ),
	GUNNER( HeroSubClass.LAUNCHER , HeroSubClass.RANGER , HeroSubClass.RIFLEMAN ),
	SAMURAI( HeroSubClass.SLASHER , HeroSubClass.MASTER , HeroSubClass.SLAYER ),
	PLANTER( HeroSubClass.TREASUREHUNTER, HeroSubClass.ADVENTURER, HeroSubClass.RESEARCHER),
	KNIGHT( HeroSubClass.WEAPONMASTER, HeroSubClass.FORTRESS, HeroSubClass.CRUSADER);

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

			case GUNNER:
				initGunner( hero );
				break;

			case SAMURAI:
				initSamurai( hero );
				break;

			case PLANTER:
				initPlanter( hero );
				break;

			case KNIGHT:
				initKnight( hero );
				break;
		}

		if (!PixelScene.landscape()) {
			for (int s = 0; s < 4; s++){
				if (Dungeon.quickslot.getItem(s) == null){
					Dungeon.quickslot.setSlot(s, waterskin);
					break;
				}
			}
		} else {
			for (int s = 0; s < 8; s++){
				if (Dungeon.quickslot.getItem(s) == null){
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

		if (Dungeon.isChallenged(Challenges.GAMBLER)) {
			RingOfWealth wealth = new RingOfWealth();
			(hero.belongings.ring = wealth).identify();
			hero.belongings.ring.activate( hero );
		}

		new PotionOfHealing().identify();
		new ScrollOfRage().identify();

		//test
		//new StoneOfAugmentation().collect();
		//new TacticalHandgun().identify().collect();
		//new TacticalHandgunAP().identify().collect();
		//new TacticalHandgunHP().identify().collect();
		//new Teleporter().collect();
		//new TestWeapon().identify().collect();
		//new PotionOfHealing().quantity(100).collect();
		//new PlateArmor().identify().upgrade(100).collect();
		//new RingOfMight().identify().upgrade(100).collect();
		//new RingOfAccuracy().identify().upgrade(100).collect();
		//new Longsword().identify().upgrade(10).collect();
		//new PotionOfMagicalSight().identify().quantity(100).collect();
		//new HandyBarricade().quantity(100).collect();
		//new PotionOfExperience().identify().quantity(29).collect();
		//new ScrollOfMagicMapping().identify().quantity(50).collect();
		//new PotionOfDragonsBreath().identify().quantity(100).collect();
		//new ScrollOfMetamorphosis().identify().quantity(20).collect();
		//new ScrollOfSirensSong().identify().quantity(100).collect();
		//new ScrollOfPsionicBlast().identify().quantity(100).collect();
		//new PotionOfMindVision().identify().quantity(100).collect();
		//new PotionOfHealing().identify().quantity(100).collect();
		//new Ankh().collect();
		//new Ankh().collect();
		//new Ankh().collect();
		//new Ankh().collect();
		//new ScrollOfEnchantment().identify().quantity(100).collect();
		//new Amulet().collect();
		//new ElixirOfDragonsBlood().identify().collect();
		//new PotionOfWeaponEnhance().identify().quantity(20).collect();
		//new PotionOfWeaponUpgrade().identify().quantity(20).collect();
		//new PotionOfArmorEnhance().identify().quantity(20).collect();
		//new PotionOfArmorUpgrade().identify().quantity(20).collect();
		//new ElixirOfHealth().identify().quantity(20).collect();
		//new KingsCrown().collect();
		//new HolySword().identify().collect();
		//new StoneOfEnchantment().quantity(50).collect();
		//new ScrollOfExtract().quantity(20).collect();
		//new RingOfFuror().identify().collect();
		//new RingOfRush().identify().collect();
		//new AlchemistsToolkit().identify().upgrade(10).collect();
		//new ScrollOfMysticalEnergy().identify().quantity(50).collect();
		//new PotionOfStrength().identify().quantity(50).collect();
		//new ArcaneCatalyst().quantity(20).collect();
		//new LiquidMetal().quantity(1000).collect();
		//new ScrollOfUpgrade().identify().quantity(100).collect();
		//new Cross().quantity(2).collect();
		//new ScrollOfAlchemy().quantity(2).collect();
		//new AlchemistsToolkit().upgrade(10).identify().collect();
		//new HolyBomb().collect();
		//new Bible().identify().collect();
		//new HeavyBoomerang().quantity(2).collect();
		//new SpellBook_Empty().identify().collect();
		//new SpellBook_Empty().identify().collect();
		//new SpellBook_Empty().identify().collect();
		//new SpellBook_Empty().identify().collect();
		//new SpellBook_Empty().identify().collect();
		//new SpellBook_Empty().identify().collect();
		//new WandOfFireblast().identify().collect();
		//new WandOfBlastWave().identify().collect();
		//new WandOfFrost().identify().collect();
		//new WandOfTransfusion().identify().collect();
		//new WandOfWarding().identify().collect();
		//new WandOfCorruption().identify().collect();
		//new WandOfMagicMissile().identify().collect();
		//new ArcaneResin().quantity(50).collect();
		//new ArcaneCatalyst().quantity(50).collect();
		//new ScrollOfAlchemy().quantity(10).collect();
		//new SpellBook_Blast()		.upgrade(10).identify().collect();
		//new SpellBook_Corrosion()	.upgrade(10).identify().collect();
		//new SpellBook_Corruption()	.upgrade(10).identify().collect();
		//new SpellBook_Disintegration().upgrade(10).identify().collect();
		//new SpellBook_Earth()		.upgrade(10).identify().collect();
		//new SpellBook_Fire()		.upgrade(10).identify().collect();
		//new SpellBook_Frost()		.upgrade(10).identify().collect();
		//new SpellBook_Lightning()	.upgrade(10).identify().collect();
		//new SpellBook_Prismatic()	.upgrade(10).identify().collect();
		//new SpellBook_Regrowth()	.upgrade(10).identify().collect();
		//new SpellBook_Transfusion()	.upgrade(10).identify().collect();
		//new SpellBook_Warding()		.upgrade(10).identify().collect();
		//new PotionOfStrength()		.identify().quantity(20).collect();
		//new TengusMask().collect();

		//new Ballista().identify().collect();
		//new TacticalShield().identify().collect();
		//new ForceGlove().identify().collect();
		//new HolySword().identify().collect();
		//new GrenadeLauncher().identify().collect();
		//new SleepGun().identify().collect();
		//new FishingSpear().quantity(20).collect();
		//new ThrowingSpear().quantity(20).collect();
		//new Javelin().quantity(20).collect();
		//new Trident().quantity(20).collect();
		//TODO:삭제필요
	}

	private static void initMage( Hero hero ) {
		MagesStaff staff;

		staff = new MagesStaff(new WandOfMagicMissile());

		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		if (Dungeon.isChallenged(Challenges.GAMBLER)) {
			RingOfWealth wealth = new RingOfWealth();
			(hero.belongings.ring = wealth).identify();
			hero.belongings.ring.activate( hero );
		}

		Dungeon.quickslot.setSlot(0, staff);

		new ScrollOfUpgrade().identify();
		new PotionOfLiquidFlame().identify();


		//new PotionOfWeaponEnhance().identify().quantity(20).collect();
		//new PotionOfWeaponUpgrade().identify().quantity(20).collect();
		//new PotionOfArmorEnhance().identify().quantity(20).collect();
		//new PotionOfArmorUpgrade().identify().quantity(20).collect();
		//new Sungrass.Seed().quantity(50).collect();
		//new CrudePistol().identify().collect();
		//new FrozenCarpaccio().quantity(100).collect();
		//new TestWeapon().identify().collect();
		//new ScrollOfMetamorphosis().identify().quantity(150).collect();
		//new PotionOfDivineInspiration().identify().quantity(4).collect();
		//new ElixirOfTalent().quantity(3).collect();
		//new KingsCrown().collect();
		//new PotionOfStrength().identify().quantity(20).collect();
		//new ScrollOfMagicMapping().identify().quantity(25).collect();
		//new PotionOfHealing().identify().quantity(150).collect();
		//new RingOfHaste().identify().upgrade(200).collect();
		//new TengusMask().collect();
		//new PotionOfExperience().identify().quantity(30).collect();
		//new SleepGun().identify().collect();
		//new FrostGun().identify().collect();
		//new ParalysisGun().identify().collect();
		//new ForceGlove().identify().collect();
	}

	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Dagger()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.artifact = cloak).identify();
		hero.belongings.artifact.activate( hero );

		ThrowingKnife knives = new ThrowingKnife();
		knives.quantity(3).collect();

		if (Dungeon.isChallenged(Challenges.GAMBLER)) {
			RingOfWealth wealth = new RingOfWealth();
			(hero.belongings.ring = wealth).identify();
			hero.belongings.ring.activate( hero );
		}

		Dungeon.quickslot.setSlot(0, cloak);
		Dungeon.quickslot.setSlot(1, knives);

		new ScrollOfMagicMapping().identify();
		new PotionOfInvisibility().identify();

		//new CrudePistol().identify().collect();
		//new Pistol().identify().collect();
		//new GoldenPistol().identify().collect();
		//new Handgun().identify().collect();
		//new Magnum().identify().collect();
		//new DualPistol().identify().collect();
		//new SubMachinegun().identify().collect();
		//new AssultRifle().identify().collect();
		//new HeavyMachinegun().identify().collect();
		//new HuntingRifle().identify().collect();
		//new SniperRifle().identify().collect();
		//new ShotGun().identify().collect();
		//new RocketLauncher().identify().collect();
		//new CapeOfThorns().identify().collect();
		//new LloydsBeacon().identify().collect();
		//new DriedRose().identify().collect();
		//new RingOfReload().identify().collect();
		//new RingOfSharpshooting().identify().collect();

		//new TengusMask().collect();
		//new KingsCrown().collect();

		//new PotionOfWeaponEnhance().identify().quantity(99).collect();
		//new PotionOfArmorEnhance().identify().quantity(99).collect();

		//new ScrollOfUpgrade().identify().quantity(99).collect();
		//new PotionOfExperience().identify().quantity(30).collect();
		//new PotionOfInvisibility().identify().quantity(30).collect();
		//new PotionOfStrength().identify().quantity(20).collect();
		//new ScrollOfMagicMapping().identify().quantity(50).collect();
		//new TestWeapon().identify().collect();
		//new TestWeapon().identify().collect();
		//new ScrollOfPsionicBlast().identify().quantity(100).collect();
		//new PotionOfMindVision().identify().quantity(100).collect();
		//new PotionOfCleansing().identify().quantity(100).collect();
		//new Noisemaker().quantity(100).collect();
		//new DriedRose().identify().upgrade(10).collect();
		//new PlateArmor().identify().upgrade(50).collect();
		//new MeatPie().quantity(10000).collect();
		//new Alchemize().quantity(999).collect();
	}

	private static void initHuntress( Hero hero ) {

		(hero.belongings.weapon = new Gloves()).identify();
		SpiritBow bow = new SpiritBow();
		bow.identify().collect();

		if (Dungeon.isChallenged(Challenges.GAMBLER)) {
			RingOfWealth wealth = new RingOfWealth();
			(hero.belongings.ring = wealth).identify();
			hero.belongings.ring.activate( hero );
		}

		Dungeon.quickslot.setSlot(0, bow);

		new PotionOfMindVision().identify();
		new ScrollOfLullaby().identify();


		//test
		//new CrudePistol().identify().collect();
		//new SubMachinegun().identify().collect();
		//new SniperRifle().identify().collect();
		//new RPG7().identify().collect();
		//new PlateArmor().identify().upgrade(100).collect();
		//new AdvancedEvolution().quantity(50).collect();
		//new RingOfMight().identify().upgrade(10).collect();
		//new PotionOfExperience().identify().quantity(30).collect();
		//new StoneOfAugmentation().quantity(50).collect();
		//new ScrollOfUpgrade().identify().quantity(100).collect();
		//new PotionOfHealing().identify().quantity(100).collect();
		//new TengusMask().collect();
		//new StoneOfEnchantment().quantity(50).collect();
		//new Teleporter().collect();
		//new Teleporter().collect();
		//new MissileButton().collect();
		//new PlateArmor().identify().upgrade(10).collect();
		//new PotionOfExperience().identify().quantity(30).collect();
		//new Longsword().identify().upgrade(10).collect();
		//new ScrollOfTeleportation().identify().quantity(50).collect();
		//new ScrollOfPassage().identify().quantity(50).collect();
		//new WandOfRegrowth().identify().collect();
		//new AquaBlast().collect();
		//new PotionOfStormClouds().identify().collect();

		//new NaturesBow().identify().collect();
		//new WindBow().identify().collect();
		//new PoisonBow().identify().collect();
		//new GoldenBow().identify().collect();

		//new Shuriken().quantity(50).collect();

		//new PotionOfMagicalSight().identify().quantity(20).collect();
		//new TengusMask().collect();
		//new ScrollOfMetamorphosis().identify().quantity(20).collect();

		//new RingOfSharpshooting().identify().collect();
		//new ScrollOfUpgrade().identify().quantity(50).collect();
		//new FishingSpear().quantity(10).collect();
		//new ThrowingSpear().quantity(10).collect();
		//new Javelin().quantity(10).collect();
		//new Trident().quantity(10).collect();
		//new TrueRunicBlade().identify().upgrade(10).collect();
		//new PotionOfStrength().identify().quantity(20).collect();
		//new Amulet().collect();
		//new TengusMask().collect();
		//new PotionOfExperience().identify().quantity(30).collect();
		//new PotionOfDivineInspiration().identify().quantity(30).collect();
		//new AdvancedEvolution().quantity(100).collect();
		//new TestWeapon().identify().collect();
		//new PotionOfHealing().identify().quantity(50).collect();
		//new DriedRose().identify().collect();
	}

	private static void initGunner( Hero hero ) {
		CrudePistol crude = new CrudePistol();
		(hero.belongings.weapon = crude).identify();
		RingOfReload reload = new RingOfReload();
		(hero.belongings.ring = reload).identify().upgrade(3);
		hero.belongings.ring.activate( hero );

		if (Dungeon.isChallenged(Challenges.GAMBLER)) {
			RingOfWealth wealth = new RingOfWealth();
			(hero.belongings.misc = wealth).identify();
			hero.belongings.misc.activate( hero );
		}

		Dungeon.quickslot.setSlot(0, crude);

		new PotionOfHaste().identify();
		new ScrollOfTeleportation().identify();

		//new MiniGun().identify().collect();
		//new GunSmithingTool().quantity(50).collect();
		//new Pistol().collect();
		//new StoneOfAugmentation().collect();
		//new HPBullet().collect();
		//new AutoHandgun().identify().collect();
		//new AutoRifle().identify().collect();
		//new MarksmanRifle().identify().collect();
		//new AlchemistsToolkit().identify().upgrade(10).collect();
		//new ScrollOfUpgrade().identify().quantity(100).collect();
		//new SniperRifle().identify().collect();
		//new Magnum().identify().collect();
		//new HeavyMachinegun().identify().collect();
		//new LiquidMetal().quantity(1000).collect();
		//new Teleporter().collect();
		//new TestWeapon().identify().collect();
		//new PotionOfHealing().quantity(100).collect();
		//new PlateArmor().identify().upgrade(100).collect();
		//new RingOfMight().identify().upgrade(100).collect();
		//new RingOfAccuracy().identify().upgrade(100).collect();
		//new PotionOfMagicalSight().identify().quantity(100).collect();
		//new HandyBarricade().quantity(100).collect();
		//new PotionOfExperience().identify().quantity(29).collect();
		//new ScrollOfMagicMapping().identify().quantity(50).collect();
		//new TengusMask().collect();
		//new PotionOfDragonsBreath().identify().quantity(100).collect();
		//new GrenadeLauncher().identify().collect();
		//new GrenadeLauncherAP().identify().collect();
		//new GrenadeLauncherHP().identify().collect();

		//new ScrollOfUpgrade().identify().quantity(100).collect();
		//new PotionOfExperience().identify().quantity(30).collect();
		//new PotionOfInvisibility().identify().quantity(30).collect();
		//new PotionOfStrength().identify().quantity(30).collect();
		//new TengusMask().collect();

		//new RocketLauncher().identify().upgrade(15).collect();
		//new AlchemistsToolkit().identify().collect();
		//new ArcaneResin().quantity(50).collect();
		//new AdvancedEvolution().quantity(50).collect();
		//new ArcaneCatalyst().quantity(20).collect();
		//new AlchemicalCatalyst().quantity(20).collect();

		//new TengusMask().collect();
		//new KingsCrown().collect();

		//new StoneOfAugmentation().quantity(99).collect();

		//new FlameThrower().identify().collect();
		//new FlameThrowerAP().identify().collect();
		//new FlameThrowerHP().identify().collect();
		//new HugeSword().identify().collect();
		//new IronHammer().identify().collect();
		//new BeamSaber().identify().collect();
		//new ScrollOfUpgrade().identify().quantity(99).collect();
		//new ScrollOfMetamorphosis().identify().quantity(99).collect();
		//new Evolution().quantity(50).collect();
		//new ScrollOfTransmutation().identify().quantity(50).collect();
		//new PotionOfExperience().identify().quantity(30).collect();
		//new ScrollOfUpgrade().identify().quantity(50).collect();
		//new PotionOfInvisibility().identify().quantity(30).collect();
		//new PotionOfStrength().identify().quantity(50).collect();
		//new ScrollOfIdentify().identify().quantity(50).collect();
		//new PotionOfMastery().identify().quantity(20).collect();
		//new CurseInfusion().quantity(50).collect();
		//new ScrollOfMagicMapping().identify().quantity(50).collect();
		//new PotionOfMindVision().identify().quantity(50).collect();
		//new PotionOfHealing().identify().quantity(800).collect();
		//new ScrollOfMysticalEnergy().quantity(50).collect();
	}

	private static void initSamurai( Hero hero ) {

		hero.STR = 8;

		WornKatana wornKatana = new WornKatana();
		(hero.belongings.weapon = wornKatana).identify();
		RingOfMight might = new RingOfMight();
		(hero.belongings.ring = might).identify().upgrade(1);
		hero.belongings.ring.activate( hero );

		ThrowingKnife knives = new ThrowingKnife();
		knives.quantity(3).collect();

		if (Dungeon.isChallenged(Challenges.GAMBLER)) {
			RingOfWealth wealth = new RingOfWealth();
			(hero.belongings.misc = wealth).identify();
			hero.belongings.misc.activate( hero );
		}

		Dungeon.quickslot.setSlot(0, knives);

		new ScrollOfRetribution().identify();
		new PotionOfStrength().identify();

		//new PotionOfStrength().quantity(50).collect();
		//new PotionOfExperience().identify().quantity(50).collect();
		//new PotionOfDivineInspiration().identify().quantity(4).collect();
		//new PotionOfHealing().identify().quantity(50).collect();
		//new ScrollOfMagicMapping().identify().quantity(50).collect();
		//new PotionOfMindVision().identify().quantity(50).collect();
		//new TengusMask().collect();
		//new KingsCrown().collect();
		//new ScrollOfUpgrade().identify().quantity(50).collect();
		//new ScrollOfEnchantment().identify().quantity(50).collect();
		//new Katana().collect();
		//new LargeKatana().collect();
		//new LongKatana().collect();
		//new FrozenCarpaccio().quantity(50).collect();
		//new LeatherArmor().collect();
		//new TestWeapon().identify().collect();
		//new CrudePistol().identify().collect();
		//new TrueRunicBlade().identify().collect();
		//new ScrollOfTransmutation().identify().quantity(50).collect();
		//new Evolution().quantity(50).collect();
		//new DriedRose().upgrade(10).identify().collect();
	}

	private static void initPlanter( Hero hero ) {
		Shovel shovel = new Shovel();
		(hero.belongings.weapon = shovel).identify();
		hero.belongings.weapon.activate(hero);
		ThrowingStone stones = new ThrowingStone();
		stones.quantity(3).collect();
		Dungeon.quickslot.setSlot(0, stones);

		SandalsOfNature sandals = new SandalsOfNature();
		Generator.Category cat = Generator.Category.ARTIFACT;
		cat.probs[5]--; //removes SandalsOfNature in pool
		(hero.belongings.artifact = sandals).identify();
		hero.belongings.artifact.activate( hero );

		if (Dungeon.isChallenged(Challenges.GAMBLER)) {
			RingOfWealth wealth = new RingOfWealth();
			(hero.belongings.ring = wealth).identify();
			hero.belongings.ring.activate( hero );
		}

		Dungeon.quickslot.setSlot(0, shovel);
		Dungeon.quickslot.setSlot(1, stones);

		new ScrollOfMirrorImage().identify();
		new PotionOfPurity().identify();

		//new PotionOfMindVision().identify().collect();
		//new PotionOfMagicalSight().identify().quantity(50).collect();
		//new Earthroot.Seed().quantity(50).collect();
		//new TengusMask().collect();
		//new PotionOfExperience().identify().quantity(30).collect();
		//new RegrowthBomb().quantity(50).collect();

		//new KingsCrown().collect();
		//new TestWeapon().identify().collect();
		//new PlateArmor().identify().upgrade(100).collect();
		//new Evolution().collect();
		//new AdvancedEvolution().collect();
		//new Shovel().identify().collect();
		//new ScrollOfUpgrade().identify().quantity(50).collect();
		//new ScrollOfTransmutation().identify().quantity(50).collect();
		//new Amulet().collect();
	}

	private static void initKnight( Hero hero ) {
		Saber saber = new Saber();
		(hero.belongings.weapon = saber).identify();
		hero.belongings.weapon.activate(hero);
		KnightsShield shield = new KnightsShield();
		shield.collect();
		Dungeon.quickslot.setSlot(0, shield);
		ThrowingStone stones = new ThrowingStone();
		stones.quantity(3).collect();
		Dungeon.quickslot.setSlot(1, stones);

		if (Dungeon.isChallenged(Challenges.GAMBLER)) {
			RingOfWealth wealth = new RingOfWealth();
			(hero.belongings.ring = wealth).identify();
			hero.belongings.ring.activate( hero );
		}

		new ScrollOfRemoveCurse().identify();
		new PotionOfParalyticGas().identify();

		//new ChainFlail().identify().upgrade(13).collect();
		//new PlateArmor().identify().upgrade(200).collect();
		//new PotionOfArmorEnhance().identify().quantity(20).collect();
		//new DriedRose().identify().upgrade(10).collect();
		//new SandalsOfNature().identify().upgrade(3).collect();
		//new RingOfAccuracy().identify().upgrade(3).collect();
		//new IronHammer().identify().upgrade(10).collect();
		//new EtherealChains().identify().upgrade(5).collect();
		//new RingOfAccuracy().identify().upgrade(3).collect();
		//new RingOfHaste().identify().upgrade(200).collect();
		//new TestWeapon().identify().collect();
		//new PotionOfExperience().identify().quantity(29).collect();
		//new PotionOfStrength().identify().quantity(20).collect();
		//new ScrollOfUpgrade().identify().quantity(500).collect();
		//new TengusMask().collect();
		//new KingsCrown().collect();
		//new ScrollOfMagicMapping().identify().quantity(30).collect();
		//new PotionOfHealing().identify().quantity(100).collect();
		//new Gloves().identify().collect();
		//new Dagger().identify().collect();
		//new SpellBook_Warding().identify().collect();
	}

	public String title() {
		return Messages.get(HeroClass.class, name());
	}

	public String desc(){
		return Messages.get(HeroClass.class, name()+"_desc");
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
			case GUNNER:
				return new ArmorAbility[]{new Riot(), new ReinforcedArmor(), new FirstAidKit()};
			case SAMURAI:
				return new ArmorAbility[]{new Awake(), new ShadowBlade(), new Abil_Kunai()};
			case PLANTER:
				return new ArmorAbility[]{new Sprout(), new TreasureMap(), new Root()};
			case KNIGHT:
				return new ArmorAbility[]{new HolyShield(), new StimPack(), new UnstableAnkh()};
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
			case GUNNER:
				return Assets.Sprites.GUNNER;
			case SAMURAI:
				return Assets.Sprites.SAMURAI;
			case PLANTER:
				return Assets.Sprites.PLANTER;
			case KNIGHT:
				return Assets.Sprites.KNIGHT;
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
			case GUNNER:
				return Assets.Splashes.GUNNER;
			case SAMURAI:
				return Assets.Splashes.SAMURAI;
			case PLANTER:
				return Assets.Splashes.PLANTER;
			case KNIGHT:
				return Assets.Splashes.KNIGHT;
		}
	}
	
	public String[] perks() {
		switch (this) {
			case WARRIOR: default:
				return new String[]{
						Messages.get(HeroClass.class, "warrior_perk1"),
						Messages.get(HeroClass.class, "warrior_perk2"),
						Messages.get(HeroClass.class, "warrior_perk3"),
						Messages.get(HeroClass.class, "warrior_perk4"),
						Messages.get(HeroClass.class, "warrior_perk5"),
				};
			case MAGE:
				return new String[]{
						Messages.get(HeroClass.class, "mage_perk1"),
						Messages.get(HeroClass.class, "mage_perk2"),
						Messages.get(HeroClass.class, "mage_perk3"),
						Messages.get(HeroClass.class, "mage_perk4"),
						Messages.get(HeroClass.class, "mage_perk5"),
				};
			case ROGUE:
				return new String[]{
						Messages.get(HeroClass.class, "rogue_perk1"),
						Messages.get(HeroClass.class, "rogue_perk2"),
						Messages.get(HeroClass.class, "rogue_perk3"),
						Messages.get(HeroClass.class, "rogue_perk4"),
						Messages.get(HeroClass.class, "rogue_perk5"),
				};
			case HUNTRESS:
				return new String[]{
						Messages.get(HeroClass.class, "huntress_perk1"),
						Messages.get(HeroClass.class, "huntress_perk2"),
						Messages.get(HeroClass.class, "huntress_perk3"),
						Messages.get(HeroClass.class, "huntress_perk4"),
						Messages.get(HeroClass.class, "huntress_perk5"),
				};
			case GUNNER:
				return new String[]{
						Messages.get(HeroClass.class, "gunner_perk1"),
						Messages.get(HeroClass.class, "gunner_perk2"),
						Messages.get(HeroClass.class, "gunner_perk3"),
						Messages.get(HeroClass.class, "gunner_perk4"),
						Messages.get(HeroClass.class, "gunner_perk5"),
				};
			case SAMURAI:
				return new String[]{
						Messages.get(HeroClass.class, "samurai_perk1"),
						Messages.get(HeroClass.class, "samurai_perk2"),
						Messages.get(HeroClass.class, "samurai_perk3"),
						Messages.get(HeroClass.class, "samurai_perk4"),
						Messages.get(HeroClass.class, "samurai_perk5"),
				};
			case PLANTER:
				return new String[]{
						Messages.get(HeroClass.class, "planter_perk1"),
						Messages.get(HeroClass.class, "planter_perk2"),
						Messages.get(HeroClass.class, "planter_perk3"),
						Messages.get(HeroClass.class, "planter_perk4"),
						Messages.get(HeroClass.class, "planter_perk5"),
				};
			case KNIGHT:
				return new String[]{
						Messages.get(HeroClass.class, "knight_perk1"),
						Messages.get(HeroClass.class, "knight_perk2"),
						Messages.get(HeroClass.class, "knight_perk3"),
						Messages.get(HeroClass.class, "knight_perk4"),
						Messages.get(HeroClass.class, "knight_perk5"),
				};
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
			case GUNNER:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_GUNNER);
			case SAMURAI:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_SAMURAI);
			case PLANTER:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_PLANTER);
			case KNIGHT:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_KNIGHT);
		}
	}
	
	public String unlockMsg() {
		switch (this){
			case WARRIOR: default:
				return "";
			case MAGE:
				return Messages.get(HeroClass.class, "mage_unlock");
			case ROGUE:
				return Messages.get(HeroClass.class, "rogue_unlock");
			case HUNTRESS:
				return Messages.get(HeroClass.class, "huntress_unlock");
			case GUNNER:
				return Messages.get(HeroClass.class, "gunner_unlock");
			case SAMURAI:
				return Messages.get(HeroClass.class, "samurai_unlock");
			case PLANTER:
				return Messages.get(HeroClass.class, "planter_unlock");
			case KNIGHT:
				return Messages.get(HeroClass.class, "knight_unlock");
		}
	}

}
