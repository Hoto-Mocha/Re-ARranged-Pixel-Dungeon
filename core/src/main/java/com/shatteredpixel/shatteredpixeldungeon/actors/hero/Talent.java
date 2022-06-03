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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import static com.shatteredpixel.shatteredpixeldungeon.items.Item.updateQuickslot;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AdrenalineSurge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArmorEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CertainCrit;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CounterBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CritBonus;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EnhancedRings;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ExtraBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.HealingArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.InfiniteBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LostInventory;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RevealedArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WandEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.Ratmogrify;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HornOfPlenty;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfChallenge;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Magnum;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ParalysisGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannonAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannonHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RPG7;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sungrass;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;

public enum Talent {

	//Warrior T1
	HEARTY_MEAL(0), ARMSMASTERS_INTUITION(1), TEST_SUBJECT(2), IRON_WILL(3), MAX_HEALTH(4),
	//Warrior T2
	IRON_STOMACH(5), RESTORED_WILLPOWER(6), RUNIC_TRANSFERENCE(7), LETHAL_MOMENTUM(8), IMPROVISED_PROJECTILES(9), DESTRUCTIVE_ATK(10),
	//Warrior T3
	HOLD_FAST(11, 3), STRONGMAN(12, 3),
	//Berserker T3
	ENDLESS_RAGE(13, 3), BERSERKING_STAMINA(14, 3), ENRAGED_CATALYST(15, 3), LETHAL_RAGE(16, 3),
	//Gladiator T3
	CLEAVE(17, 3), LETHAL_DEFENSE(18, 3), ENHANCED_COMBO(19, 3), QUICK_SWAP(20, 3),
	//Veteran T3
	ARM_VETERAN(21, 3), MARTIAL_ARTS(22, 3), ENHANCED_FOCUSING(23, 3), PUSHBACK(24, 3),
	//Heroic Leap T4
	BODY_SLAM(25, 4), IMPACT_WAVE(26, 4), DOUBLE_JUMP(27, 4),
	//Shockwave T4
	EXPANDING_WAVE(28, 4), STRIKING_WAVE(29, 4), SHOCK_FORCE(30, 4),
	//Endure T4
	SUSTAINED_RETRIBUTION(31, 4), SHRUG_IT_OFF(32, 4), EVEN_THE_ODDS(33, 4),

	//Mage T1
	EMPOWERING_MEAL(35), SCHOLARS_INTUITION(36), TESTED_HYPOTHESIS(37), BACKUP_BARRIER(38), CHARGE_PRESERVE(39),
	//Mage T2
	ENERGIZING_MEAL(40), ENERGIZING_UPGRADE(41), WAND_PRESERVATION(42), ARCANE_VISION(43), SHIELD_BATTERY(44), FASTER_CHARGER(45),
	//Mage T3
	EMPOWERING_SCROLLS(46, 3), ALLY_WARP(47, 3),
	//Battlemage T3
	EMPOWERED_STRIKE(48, 3), MYSTICAL_CHARGE(49, 3), EXCESS_CHARGE(50, 3), MANA_ENHANCE(51, 3),
	//Warlock T3
	SOUL_EATER(52, 3), SOUL_SIPHON(53, 3), NECROMANCERS_MINIONS(54, 3), MADNESS(55, 3),
	//Engineer T3
	AMMO_PRESERVE(56, 3), CONNECTING_CHARGER(57, 3), HIGH_VOLT(58, 3), STATIC_ENERGY(59, 3),
	//Elemental Blast T4
	BLAST_RADIUS(60, 4), ELEMENTAL_POWER(61, 4), REACTIVE_BARRIER(62, 4),
	//Wild Magic T4
	WILD_POWER(63, 4), FIRE_EVERYTHING(64, 4), CONSERVED_MAGIC(65, 4),
	//Warp Beacon T4
	TELEFRAG(66, 4), REMOTE_BEACON(67, 4), LONGRANGE_WARP(68, 4),

	//Rogue T1
	CACHED_RATIONS(70), THIEFS_INTUITION(71), SUCKER_PUNCH(72), PROTECTIVE_SHADOWS(73), EMERGENCY_ESCAPE(74),
	//Rogue T2
	MYSTICAL_MEAL(75), MYSTICAL_UPGRADE(76), WIDE_SEARCH(77), SILENT_STEPS(78), ROGUES_FORESIGHT(79), MOVESPEED_ENHANCE(80),
	//Rogue T3
	ENHANCED_RINGS(81, 3), LIGHT_CLOAK(82, 3),
	//Assassin T3
	ENHANCED_LETHALITY(83, 3), ASSASSINS_REACH(84, 3), BOUNTY_HUNTER(85, 3), ENERGY_DRAW(86, 3),
	//Freerunner T3
	EVASIVE_ARMOR(87, 3), PROJECTILE_MOMENTUM(88, 3), SPEEDY_STEALTH(89, 3), QUICK_PREP(90, 3),
	//Chaser T3
	INCISIVE_BLADE(91, 3), LETHAL_SURPRISE(92, 3), CHAIN_CLOCK(93, 3), IMAGE_OF_HORROR(94, 3),
	//Smoke Bomb T4
	HASTY_RETREAT(95, 4), BODY_REPLACEMENT(96, 4), SHADOW_STEP(97, 4),
	//Death Mark T4
	FEAR_THE_REAPER(98, 4), DEATHLY_DURABILITY(99, 4), DOUBLE_MARK(100, 4),
	//Shadow Clone T4
	SHADOW_BLADE(101, 4), CLONED_ARMOR(102, 4), PERFECT_COPY(103, 4),

	//Huntress T1
	NATURES_BOUNTY(105), SURVIVALISTS_INTUITION(106), FOLLOWUP_STRIKE(107), NATURES_AID(108), WATER_FRIENDLY(109),
	//Huntress T2
	INVIGORATING_MEAL(110), RESTORED_NATURE(111), REJUVENATING_STEPS(112), HEIGHTENED_SENSES(113), DURABLE_PROJECTILES(114), ADDED_MEAL(115),
	//Huntress T3
	POINT_BLANK(116, 3), SEER_SHOT(117, 3),
	//Sniper T3
	FARSIGHT(118, 3), SHARED_ENCHANTMENT(119, 3), SHARED_UPGRADES(120, 3), VISION_ARROW(121, 3),
	//Warden T3
	DURABLE_TIPS(122, 3), BARKSKIN(123, 3), SHIELDING_DEW(124, 3), DENSE_GRASS(125, 3),
	//Fighter T3
	SWIFT_MOVEMENT(126, 3), JAW_STRIKE(127, 3), RING_KNUCKLE(128, 3), SINGULAR_STRIKE(129, 3),
	//Spectral Blades T4
	FAN_OF_BLADES(130, 4), PROJECTING_BLADES(131, 4), SPIRIT_BLADES(132, 4),
	//Natures Power T4
	GROWING_POWER(133, 4), NATURES_WRATH(134, 4), WILD_MOMENTUM(135, 4),
	//Spirit Hawk T4
	EAGLE_EYE(136, 4), GO_FOR_THE_EYES(137, 4), SWIFT_SPIRIT(138, 4),

	//Gunner T1
	REARRANGE(140), GUNNERS_INTUITION(141), SPEEDY_MOVE(142), SAFE_RELOAD(143), MIND_VISION(144),
	//Gunner T2
	IN_THE_GUNFIRE(145), ANOTHER_CHANCE(146), BULLET_FOCUS(147), CAMOUFLAGE(148), LARGER_MAGAZINE(149), ELASTIC_WEAPON(150),
	//Gunner T3
	STREET_BATTLE(151, 3), HEAVY_ENHANCE(152, 3),
	//Launcher T3
	HEAVY_GUNNER(153, 3), ACC_PRACTICE(154, 3), RECOIL_PRACTICE(155, 3), DRUM_MAGAZINE(156, 3),
	//Ranger T3
	QUICK_RELOAD(157, 3), RECOIL_CONTROL(158, 3), ELEMENTAL_BULLET(159, 3), OUTLAW_OF_BARRENLAND(160, 3),
	//RifleMan T3
	SILENCER(161, 3), ONLY_ONE_SHOT(162, 3), EVASIVE_MOVE(163, 3), SURPRISE_BULLET(164, 3),
	//Riot T4
	HASTE_MOVE(165, 4), SHOT_CONCENTRATION(166,4), ROUND_PRESERVE(167, 4),
	//ReinforcedArmor T4
	BAYONET(168, 4), TACTICAL_SIGHT(169, 4), PLATE_ADD(170, 4),
	//FirstAidKit T4
	ADDITIONAL_MEDS(171,4), THERAPEUTIC_BANDAGE(172, 4), FASTER_HEALING(173,4),

	//Samurai T1
	CRITICAL_MEAL(175), MASTERS_INTUITION(176), UNEXPECTED_SLASH(177), FLOW_AWAY(178), ADRENALINE_SURGE(179),
	//Samurai T2
	FOCUSING_MEAL(180), CRITICAL_UPGRADE(181), MAGICAL_TRANSFERENCE(182), PARRY(183), DETECTION(184), POWERFUL_CRIT(185),
	//Samurai T3
	DEEP_SCAR(186, 3), FAST_LEAD(187, 3),
	//Slasher T3
	CONTINUOUS_ATTACK(188, 3), SLASHING_PRACTICE(189, 3), SERIAL_MOMENTUM(190, 3), ARCANE_ATTACK(191, 3),
	//Master T3
	DONG_MIND_EYES(192, 3), DONG_SHEATHING(193, 3), JUNG_DETECTION(194, 3), JUNG_FOCUSING(195, 3),
	//Slayer T3
	AFTERIMAGE(196, 3), ENERGY_DRAIN(197, 3), FTL(198, 3), IMAGE_OF_DEMON(199, 3),
	//Awake T4
	AWAKE_LIMIT(200, 4), AWAKE_DURATION(201, 4), INSURANCE(202, 4),
	//ShadowBlade T4
	DOUBLE_BLADE_PRACTICE(203, 4), CRITICAL_SHADOW(204, 4), HERBAL_SHADOW(205, 4),
	//Kunai T4
	KUNAI_OF_DOOM(206, 4), MYSTICAL_KUNAI(207, 4), POISONED_KUNAI(208, 4),

	//Planter T1
	FLOWER_BERRY(210), ADVENTURERS_INTUITION(211), SWING(212), NATURES_PROTECTION(213), SEED_EATING(214),
	//Planter T2
	NATURAL_MEAL(215), HERBAL_HEALING(216), SPROUT(217), VINE(218), MASS_PRODUCTION(219), NEUROTOXIN(220),
	//Planter T3
	BLOOMING_WEAPON(221, 3), FARMER(222, 3),
	//TreasureHunter T3
	TAKEDOWN(223, 3), DETECTOR(224, 3), GOLD_SHIELD(225, 3), GOLD_MINER(226, 3),
	//Adventurer T3
	JUNGLE_ADVENTURE(227, 3), SHADOW(228, 3), VINE_WHIP(229, 3), THORNY_VINE(230, 3),
	//Researcher T3
	SHOCK_DRAIN(231, 3), DEW_MAKING(232, 3), BIO_ENERGY(233, 3), CLINICAL_TRIAL(234, 3),
	//Sprout T4
	JUNGLE(235, 4), FOREST(236, 4), REGROWTH(237, 4),
	//TreasureMap T4
	LONG_LUCK(238, 4), FORESIGHT(239, 4), GOLD_HUNTER(240, 4),
	//Root T4
	POISONOUS_ROOT(241, 4), ROOT_SPREAD(242, 4), ROOT_ARMOR(243, 4),

	//Knight T1
	ON_ALERT(245), KNIGHTS_INTUITION(246), BATTLE_STIM(247), ACTIVE_BARRIER(248), WAR_CRY(249),
	//Knight T2
	IMPREGNABLE_MEAL(250), SAFE_HEALING(251), DEFENSE_STANCE(252), CROSS_SLASH(253), ENDURING(254), BLOCKING(255),
	//Knight T3
	CRAFTMANS_SKILLS(256, 3), TACKLE(257, 3),
	//Weaponmaster T3
	WEAPON_AUGMENT(258, 3), SWORD_N_SHIELD(259, 3), FIRE_WEAPON(260, 3), EARTHQUAKE(261, 3),
	//Fortress T3
	IMPREGNABLE_WALL(262, 3), COUNTER_MOMENTUM(263, 3), SHIELD_SLAM(264, 3), PREPARATION(265, 3),
	//Crusader T3
	NAME_OF_LIGHT(266, 3), HOLY_SHIELD(267, 3), DEADS_BLESS(268, 3), BLESSING_SCROLLS(269, 3),
	//HolyShield T4
	BUFFER_BARRIER(270, 4), HOLY_LIGHT(271, 4), BLESS(272, 4),
	//StimPack T4
	BURDEN_RELIEF(273, 4), LASTING_PACK(274, 4), TIME_STOP(275, 4),
	//UnstableAnkh T4
	BLESSED_ANKH(276, 4), ANKH_ENHANCE(277, 4), COMPLETE_ANKH(278, 4),

	//Nurse T1
	HEALING_MEAL(280), DOCTORS_INTUITION(281), INNER_MIRROR(282), CRITICAL_SHIELD(283), HEAL_AMP(284),
	//Nurse T2
	CHALLENGING_MEAL(285), POTION_SPREAD(286), HEALAREA(287), ANGEL(288), MEDICAL_SUPPORT(289), WINNERS_FLAG(290),
	//Nurse T3
	POWERFUL_BOND(291, 3), CHARISMA(292,3),
	//Medic T3
	PROMOTION(293, 3), HEALING_SHIELD(294, 3), HEAL_ENHANCE(295, 3), COMP_RECOVER(296, 3),
	//Angel T3
	APPEASE(297, 3), ANGEL_AND_DEVIL(298, 3), AREA_OF_LIGHT(299, 3), BLESS_ENHANCE(300, 3),
	//Surgeon T3
	SCALPEL(301, 3), DEFIBRILLATOR(302, 3), DEATH_DIAGNOSIS(303, 3), FIRST_AID(304, 3),
	//HealGenerator T4
	AREA_AMP(305, 4), SHIELD_GEN(306, 4), DURABLE_GEN(307, 4),
	//AngelWing T4
	LIGHT_LIKE_FEATHER(308, 4), ANGELS_BLESS(309, 4), HEALING_WING(310, 4),
	//GammaRayEmmit T4
	TRANSMOG_BIAS(311, 4), IMPRINTING_EFFECT(312, 4), SHEEP_TRANSMOG(313, 4),

	//universal T4
	HEROIC_ENERGY(34, 4), //See icon() and title() for special logic for this one
	//Ratmogrify T4
	RATSISTANCE(321, 4), RATLOMACY(322, 4), RATFORCEMENTS(323, 4),
	//universal T3
	ATK_SPEED_ENHANCE(315, 4), DEF_ENHANCE(316, 4), ACC_ENHANCE(317, 4), EVA_ENHANCE(318, 4), DEW_ENHANCE(319, 4),
	BETTER_CHOICE(320, 3);

	public static class ImprovisedProjectileCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.15f, 0.2f, 0.5f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 50); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class LethalMomentumTracker extends FlavourBuff{};
	public static class StrikingWaveTracker extends FlavourBuff{};
	public static class WandPreservationCounter extends CounterBuff{{revivePersists = true;}};
	public static class EmpoweredStrikeTracker extends FlavourBuff{};
	public static class BountyHunterTracker extends FlavourBuff{};
	public static class SingularStrikeTracker extends Buff{};
	public static class RejuvenatingStepsCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0f, 0.35f, 0.15f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / (15 - 5*Dungeon.hero.pointsInTalent(REJUVENATING_STEPS))); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class RejuvenatingStepsFurrow extends CounterBuff{{revivePersists = true;}};
	public static class SeerShotCooldown extends FlavourBuff{
		public int icon() { return target.buff(RevealedArea.class) != null ? BuffIndicator.NONE : BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.7f, 0.4f, 0.7f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 20); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class SpiritBladesTracker extends FlavourBuff{};
	public static class ChaseCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.4f, 0.4f, 0.8f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / (15)); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class ChainCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.2f, 0.5f, 0.8f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 10); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class LethalCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.8f, 0.1f, 0.1f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 5); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class ReloadCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.3f, 0.3f, 0.3f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 6); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class TakeDownCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(1f, 2f, 0.25f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 20); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class StreetBattleCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight( 0xB3B3B3 ); }
		public float iconFadePercent() { return Math.max(0, 1-visualcooldown() / (40-10*Dungeon.hero.pointsInTalent(Talent.STREET_BATTLE))); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class PushbackCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.3f, 0.3f, 0.3f); }
		public float iconFadePercent() { return Math.max(0, 1-visualcooldown() / (30-5*Dungeon.hero.pointsInTalent(Talent.PUSHBACK))); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class QuickSwapCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0xB3B3B3); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 5); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};


	int icon;
	int maxPoints;

	// tiers 1/2/3/4 start at levels 2/7/13/21
	public static int[] tierLevelThresholds = new int[]{0, 2, 7, 13, 21, 31};

	Talent( int icon ){
		this(icon, 2);
	}

	Talent( int icon, int maxPoints ){
		this.icon = icon;
		this.maxPoints = maxPoints;
	}

	public int icon(){
		if (this == HEROIC_ENERGY){
			if (Ratmogrify.useRatroicEnergy){
				return 324;
			}
			HeroClass cls = Dungeon.hero != null ? Dungeon.hero.heroClass : GamesInProgress.selectedClass;
			switch (cls){
				case WARRIOR: default:
					return 34;
				case MAGE:
					return 69;
				case ROGUE:
					return 104;
				case HUNTRESS:
					return 139;
				case GUNNER:
					return 174;
				case SAMURAI:
					return 209;
				case PLANTER:
					return 244;
				case KNIGHT:
					return 279;
				case NURSE:
					return 314;
			}
		} else {
			return icon;
		}
	}

	public int maxPoints(){
		return maxPoints;
	}

	public String title(){
		if (this == HEROIC_ENERGY && Ratmogrify.useRatroicEnergy){
			return Messages.get(this, name() + ".rat_title");
		}
		return Messages.get(this, name() + ".title");
	}

	public String desc(){
		return Messages.get(this, name() + ".desc");
	}

	public static void onTalentUpgraded( Hero hero, Talent talent){
		if (talent == MAX_HEALTH) {
			hero.updateHT(true);
		}
		if (talent == ADRENALINE_SURGE) {
			if (hero.pointsInTalent(ADRENALINE_SURGE) == 1) Buff.affect(hero, AdrenalineSurge.class).reset(1, AdrenalineSurge.DURATION);
			else 											Buff.affect(hero, AdrenalineSurge.class).reset(2, AdrenalineSurge.DURATION);;
		}
		if (talent == NATURES_BOUNTY){
			if ( hero.pointsInTalent(NATURES_BOUNTY) == 1) Buff.count(hero, NatureBerriesAvailable.class, 4);
			else                                           Buff.count(hero, NatureBerriesAvailable.class, 2);
		}
		if (talent == LARGER_MAGAZINE) {
			updateQuickslot();
		}
		if (talent == DRUM_MAGAZINE) {
			updateQuickslot();
		}

		if (talent == ARMSMASTERS_INTUITION && hero.pointsInTalent(ARMSMASTERS_INTUITION) == 2){
			if (hero.belongings.weapon() != null) hero.belongings.weapon().identify();
			if (hero.belongings.armor() != null)  hero.belongings.armor.identify();
		}
		if (talent == THIEFS_INTUITION && hero.pointsInTalent(THIEFS_INTUITION) == 2){
			if (hero.belongings.ring instanceof Ring) hero.belongings.ring.identify();
			if (hero.belongings.misc instanceof Ring) hero.belongings.misc.identify();
			for (Item item : Dungeon.hero.belongings){
				if (item instanceof Ring){
					((Ring) item).setKnown();
				}
			}
		}
		if (talent == THIEFS_INTUITION && hero.pointsInTalent(THIEFS_INTUITION) == 1){
			if (hero.belongings.ring instanceof Ring) hero.belongings.ring.setKnown();
			if (hero.belongings.misc instanceof Ring) ((Ring) hero.belongings.misc).setKnown();
		}

		if (talent == LIGHT_CLOAK && hero.pointsInTalent(LIGHT_CLOAK) == 1){
			for (Item item : Dungeon.hero.belongings.backpack){
				if (item instanceof CloakOfShadows){
					if (hero.buff(LostInventory.class) == null || item.keptThoughLostInvent) {
						((CloakOfShadows) item).activate(Dungeon.hero);
					}
				}
			}
		}

		if (talent == HEIGHTENED_SENSES || talent == FARSIGHT){
			Dungeon.observe();
		}

		if (talent == BETTER_CHOICE && hero.pointsInTalent(BETTER_CHOICE) == 1){
			StoneOfEnchantment stone = new StoneOfEnchantment();
			if (stone.doPickUp( Dungeon.hero )) {
				GLog.i( Messages.get(Dungeon.hero, "you_now_have", stone.name() ));
			} else {
				Dungeon.level.drop( stone, Dungeon.hero.pos ).sprite.drop();
			}
		}

		if (talent == BETTER_CHOICE && hero.pointsInTalent(BETTER_CHOICE) == 2){
			ScrollOfEnchantment enchantment = new ScrollOfEnchantment();
			if (enchantment.doPickUp( Dungeon.hero )) {
				GLog.i( Messages.get(Dungeon.hero, "you_now_have", enchantment.name() ));
			} else {
				Dungeon.level.drop( enchantment, Dungeon.hero.pos ).sprite.drop();
			}
		}

		if (talent == BETTER_CHOICE && hero.pointsInTalent(BETTER_CHOICE) == 3){
			ScrollOfUpgrade scl = new ScrollOfUpgrade();
			if (scl.doPickUp( Dungeon.hero )) {
				GLog.i( Messages.get(Dungeon.hero, "you_now_have", scl.name() ));
			} else {
				Dungeon.level.drop( scl, Dungeon.hero.pos ).sprite.drop();
			}
		}

		if (talent == DOCTORS_INTUITION && hero.pointsInTalent(DOCTORS_INTUITION) == 1) {
			for (int i = 0 ; i < 2 ; i++) {
				HashSet<Class<? extends Potion>> potions = Potion.getUnknown();
				Potion p = Reflection.newInstance(Random.element(potions));
				if (p == null) {
					break;
				} else {
					p.identify();
					potions.remove(p.getClass());
				}
			}
		}

		if (talent == DOCTORS_INTUITION && hero.pointsInTalent(DOCTORS_INTUITION) == 2) {
			for (int i = 0 ; i < 3 ; i++) {
				HashSet<Class<? extends Potion>> potions = Potion.getUnknown();
				Potion p = Reflection.newInstance(Random.element(potions));
				if (p == null) {
					break;
				} else {
					p.identify();
					potions.remove(p.getClass());
				}
			}
		}
	}

	public static class CachedRationsDropped extends CounterBuff{{revivePersists = true;}};
	public static class NatureBerriesAvailable extends CounterBuff{{revivePersists = true;}};

	public static void onFoodEaten( Hero hero, float foodVal, Item foodSource ){
		if (hero.hasTalent(HEARTY_MEAL)){
			//3/5 HP healed, when hero is below 25% health
			if (hero.HP <= hero.HT/4) {
				hero.HP = Math.min(hero.HP + 1 + 2 * hero.pointsInTalent(HEARTY_MEAL), hero.HT);
				hero.sprite.emitter().burst(Speck.factory(Speck.HEALING), 1+hero.pointsInTalent(HEARTY_MEAL));
			//2/3 HP healed, when hero is below 50% health
			} else if (hero.HP <= hero.HT/2){
				hero.HP = Math.min(hero.HP + 1 + hero.pointsInTalent(HEARTY_MEAL), hero.HT);
				hero.sprite.emitter().burst(Speck.factory(Speck.HEALING), hero.pointsInTalent(HEARTY_MEAL));
			}
		}
		if (hero.hasTalent(IRON_STOMACH)){
			if (hero.cooldown() > 0) {
				Buff.affect(hero, WarriorFoodImmunity.class, hero.cooldown());
			}
		}
		if (hero.hasTalent(EMPOWERING_MEAL)){
			//2/3 bonus wand damage for next 3 zaps
			Buff.affect( hero, WandEmpower.class).set(1 + hero.pointsInTalent(EMPOWERING_MEAL), 3);
			ScrollOfRecharging.charge( hero );
		}
		if (hero.hasTalent(ENERGIZING_MEAL)){
			//5/8 turns of recharging
			Buff.prolong( hero, Recharging.class, 2 + 3*(hero.pointsInTalent(ENERGIZING_MEAL)) );
			ScrollOfRecharging.charge( hero );
		}
		if (hero.hasTalent(MYSTICAL_MEAL)){
			//3/5 turns of recharging
			ArtifactRecharge buff = Buff.affect( hero, ArtifactRecharge.class);
			if (buff.left() < 1 + 2*(hero.pointsInTalent(MYSTICAL_MEAL))){
				Buff.affect( hero, ArtifactRecharge.class).set(1 + 2*(hero.pointsInTalent(MYSTICAL_MEAL))).ignoreHornOfPlenty = foodSource instanceof HornOfPlenty;
			}
			ScrollOfRecharging.charge( hero );
		}
		if (hero.hasTalent(INVIGORATING_MEAL)){
			//effectively 1/2 turns of haste
			Buff.prolong( hero, Haste.class, 0.67f+hero.pointsInTalent(INVIGORATING_MEAL));
		}
		if (hero.hasTalent(REARRANGE)){
			//effectively 5/10 turns of ExtraBullet
			Buff.prolong( hero, ExtraBullet.class, 5f*hero.pointsInTalent(REARRANGE));
		}
		if (hero.hasTalent(IN_THE_GUNFIRE)) {
			//effectively 1/2 turns of infiniteBullet
			Buff.prolong( hero, InfiniteBullet.class, 0.001f+hero.pointsInTalent(IN_THE_GUNFIRE));
		}
		if (hero.hasTalent(Talent.CRITICAL_MEAL)) {
			Buff.prolong( hero, CritBonus.class, 10f).set(5f * hero.pointsInTalent(Talent.CRITICAL_MEAL));
		}
		if (hero.hasTalent(Talent.FOCUSING_MEAL)) {
			Buff.prolong( hero, Adrenaline.class, 1f + 2f*hero.pointsInTalent(FOCUSING_MEAL));
		}
		if (hero.hasTalent(Talent.NATURAL_MEAL)) {
			if (hero.pointsInTalent(Talent.NATURAL_MEAL) == 1) {
				for (int i : PathFinder.NEIGHBOURS4) {
					int c = Dungeon.level.map[hero.pos + i];
					if ( c == Terrain.EMPTY || c == Terrain.EMPTY_DECO
							|| c == Terrain.EMBERS || c == Terrain.GRASS){
						Level.set(hero.pos + i, Terrain.HIGH_GRASS);
						GameScene.updateMap(hero.pos + i);
						CellEmitter.get( hero.pos + i ).burst( LeafParticle.LEVEL_SPECIFIC, 4 );
					}
				};
			} else {
				for (int i : PathFinder.NEIGHBOURS8) {
					int c = Dungeon.level.map[hero.pos + i];
					if ( c == Terrain.EMPTY || c == Terrain.EMPTY_DECO
							|| c == Terrain.EMBERS || c == Terrain.GRASS){
						Level.set(hero.pos + i, Terrain.HIGH_GRASS);
						GameScene.updateMap(hero.pos + i);
						CellEmitter.get( hero.pos + i ).burst( LeafParticle.LEVEL_SPECIFIC, 4 );
					}
				};
			}

		}
		if (hero.hasTalent(Talent.IMPREGNABLE_MEAL)) {
			Buff.affect(hero, ArmorEmpower.class).set(hero.pointsInTalent(Talent.IMPREGNABLE_MEAL), 5f);
		}
		if (hero.hasTalent(Talent.HEALING_MEAL)) {
			Buff.affect(hero, HealingArea.class).setup(hero.pos, 1+2*hero.pointsInTalent(Talent.HEALING_MEAL), 1, true);
		}
		if (hero.hasTalent(Talent.CHALLENGING_MEAL)) {
			Buff.affect(hero, ScrollOfChallenge.ChallengeArena.class).setup(hero.pos, 5*hero.pointsInTalent(Talent.CHALLENGING_MEAL));
		}
	}

	public static class WarriorFoodImmunity extends FlavourBuff{
		{ actPriority = HERO_PRIO+1; }
	}

	public static float itemIDSpeedFactor( Hero hero, Item item ){
		// 1.75x/2.5x speed with huntress talent
		float factor = 1f + hero.pointsInTalent(SURVIVALISTS_INTUITION)*0.75f;

		// 2x/instant for Warrior (see onItemEquipped)
		if (item instanceof MeleeWeapon || item instanceof Armor){
			factor *= 1f + hero.pointsInTalent(ARMSMASTERS_INTUITION);
		}
		// 3x/instant for mage (see Wand.wandUsed())
		if (item instanceof Wand){
			factor *= 1f + 2*hero.pointsInTalent(SCHOLARS_INTUITION);
		}
		// 2x/instant for rogue (see onItemEqupped), also id's type on equip/on pickup
		if (item instanceof Ring){
			factor *= 1f + hero.pointsInTalent(THIEFS_INTUITION);
		}

		// 3x/instant for Gunner (see onItemEquipped)
		if (item instanceof CrudePistol
				||item instanceof CrudePistolAP
				||item instanceof CrudePistolHP
				||item instanceof Pistol
				||item instanceof PistolAP
				||item instanceof PistolHP
				||item instanceof GoldenPistol
				||item instanceof GoldenPistolAP
				||item instanceof GoldenPistolHP
				||item instanceof Handgun
				||item instanceof HandgunAP
				||item instanceof HandgunHP
				||item instanceof Magnum
				||item instanceof MagnumAP
				||item instanceof MagnumHP
				||item instanceof DualPistol
				||item instanceof DualPistolAP
				||item instanceof DualPistolHP
				||item instanceof SubMachinegun
				||item instanceof SubMachinegunAP
				||item instanceof SubMachinegunHP
				||item instanceof AssultRifle
				||item instanceof AssultRifleAP
				||item instanceof AssultRifleHP
				||item instanceof HeavyMachinegun
				||item instanceof HeavyMachinegunAP
				||item instanceof HeavyMachinegunHP
				||item instanceof HuntingRifle
				||item instanceof HuntingRifleAP
				||item instanceof HuntingRifleHP
				||item instanceof SniperRifle
				||item instanceof SniperRifleAP
				||item instanceof SniperRifleHP
				||item instanceof ShotGun
				||item instanceof ShotGunAP
				||item instanceof ShotGunHP
				||item instanceof KSG
				||item instanceof KSGAP
				||item instanceof KSGHP
				||item instanceof MiniGun
				||item instanceof MiniGunAP
				||item instanceof MiniGunHP
				||item instanceof TacticalHandgun
				||item instanceof TacticalHandgunAP
				||item instanceof TacticalHandgunHP
				||item instanceof AntimaterRifle
				||item instanceof AntimaterRifleAP
				||item instanceof AntimaterRifleHP
				||item instanceof AutoHandgun
				||item instanceof AutoHandgunAP
				||item instanceof AutoHandgunHP
				||item instanceof AutoRifle
				||item instanceof AutoRifleAP
				||item instanceof AutoRifleHP
				||item instanceof MarksmanRifle
				||item instanceof MarksmanRifleAP
				||item instanceof MarksmanRifleHP
				||item instanceof RPG7
				||item instanceof RocketLauncher) {
			factor *= 1f + 2f*hero.pointsInTalent(GUNNERS_INTUITION);
		}

		// 3x/instant for Knight (see onItemEquipped)
		if (item instanceof Armor) {
			factor *= 1f + 2f*hero.pointsInTalent(KNIGHTS_INTUITION);
		}
		return factor;
	}

	public static void onHealingPotionUsed( Hero hero ){
		if (hero.hasTalent(RESTORED_WILLPOWER)){
			BrokenSeal.WarriorShield shield = hero.buff(BrokenSeal.WarriorShield.class);
			if (shield != null){
				int shieldToGive = Math.round(shield.maxShield() * 0.33f*(1+hero.pointsInTalent(RESTORED_WILLPOWER)));
				shield.supercharge(shieldToGive);
			}
		}
		if (hero.hasTalent(RESTORED_NATURE)){
			ArrayList<Integer> grassCells = new ArrayList<>();
			for (int i : PathFinder.NEIGHBOURS8){
				grassCells.add(hero.pos+i);
			}
			Random.shuffle(grassCells);
			for (int cell : grassCells){
				Char ch = Actor.findChar(cell);
				if (ch != null && ch.alignment == Char.Alignment.ENEMY){
					Buff.affect(ch, Roots.class, 1f + hero.pointsInTalent(RESTORED_NATURE));
				}
				if (Dungeon.level.map[cell] == Terrain.EMPTY ||
						Dungeon.level.map[cell] == Terrain.EMBERS ||
						Dungeon.level.map[cell] == Terrain.EMPTY_DECO){
					Level.set(cell, Terrain.GRASS);
					GameScene.updateMap(cell);
				}
				CellEmitter.get(cell).burst(LeafParticle.LEVEL_SPECIFIC, 4);
			}
			if (hero.pointsInTalent(RESTORED_NATURE) == 1){
				grassCells.remove(0);
				grassCells.remove(0);
				grassCells.remove(0);
			}
			for (int cell : grassCells){
				int t = Dungeon.level.map[cell];
				if ((t == Terrain.EMPTY || t == Terrain.EMPTY_DECO || t == Terrain.EMBERS
						|| t == Terrain.GRASS || t == Terrain.FURROWED_GRASS)
						&& Dungeon.level.plants.get(cell) == null){
					Level.set(cell, Terrain.HIGH_GRASS);
					GameScene.updateMap(cell);
				}
			}
			Dungeon.observe();
		}
		if (hero.hasTalent(Talent.HERBAL_HEALING)) {
			Buff.affect(hero, Sungrass.Health.class).boost(10*hero.pointsInTalent(Talent.HERBAL_HEALING));
		}
		if (hero.hasTalent(Talent.SAFE_HEALING)) {
			Buff.affect(hero, Barrier.class).setShield(10*hero.pointsInTalent(Talent.SAFE_HEALING));
		}
		if (hero.hasTalent(Talent.POTION_SPREAD) && !Dungeon.isChallenged(Challenges.NO_HEALING)) {
			Buff.affect(hero, HealingArea.class).setup(hero.pos, Math.round(((0.8f * hero.HT + 14)/3)*(1+hero.pointsInTalent(Talent.POTION_SPREAD))), 2, true);
		}
	}

	private static int upgradeUsed = 0;
	public static void onUpgradeScrollUsed( Hero hero ){
		if (hero.pointsInTalent(ANOTHER_CHANCE) == 2) {
			upgradeUsed ++;
		}
		if (hero.hasTalent(ENERGIZING_UPGRADE)){
			MagesStaff staff = hero.belongings.getItem(MagesStaff.class);
			if (staff != null){
				staff.gainCharge(2 + 2*hero.pointsInTalent(ENERGIZING_UPGRADE), true);
				ScrollOfRecharging.charge( Dungeon.hero );
				SpellSprite.show( hero, SpellSprite.CHARGE );
			}
		}
		if (hero.hasTalent(MYSTICAL_UPGRADE)){
			CloakOfShadows cloak = hero.belongings.getItem(CloakOfShadows.class);
			if (cloak != null){
				cloak.overCharge(1 + hero.pointsInTalent(MYSTICAL_UPGRADE));
				ScrollOfRecharging.charge( Dungeon.hero );
				SpellSprite.show( hero, SpellSprite.CHARGE );
			}
		}
		if (hero.hasTalent(ANOTHER_CHANCE)) {
			ScrollOfUpgrade scl = new ScrollOfUpgrade();
			StoneOfEnchantment enchantment = new StoneOfEnchantment();
			if (hero.pointsInTalent(Talent.ANOTHER_CHANCE) >= 1 && Random.Int(5) == 0) {
				if (enchantment.doPickUp( Dungeon.hero )) {
					GLog.i( Messages.get(Dungeon.hero, "you_now_have", enchantment.name() ));
				} else {
					Dungeon.level.drop( enchantment, Dungeon.hero.pos ).sprite.drop();
				}
			}
			if (hero.pointsInTalent(ANOTHER_CHANCE) == 2 && (Random.Int(10) == 0 || upgradeUsed == 10)) {
				if (scl.doPickUp( Dungeon.hero )) {
					GLog.i( Messages.get(Dungeon.hero, "you_now_have", scl.name() ));
				} else {
					Dungeon.level.drop( scl, Dungeon.hero.pos ).sprite.drop();
				}
				upgradeUsed = 0;
			}
		}

		if (hero.hasTalent(CRITICAL_UPGRADE)) {
		 	Buff.prolong(hero, CertainCrit.class, 5*hero.pointsInTalent(CRITICAL_UPGRADE));
		}
	}

	public static void onArtifactUsed( Hero hero ){
		if (hero.hasTalent(ENHANCED_RINGS)){
			Buff.prolong(hero, EnhancedRings.class, 3f*hero.pointsInTalent(ENHANCED_RINGS));
		}
	}

	public static void onItemEquipped( Hero hero, Item item ){
		if (hero.pointsInTalent(ARMSMASTERS_INTUITION) == 2 && (item instanceof Weapon || item instanceof Armor)){
			item.identify();
		}
		if (hero.hasTalent(THIEFS_INTUITION) && item instanceof Ring){
			if (hero.pointsInTalent(THIEFS_INTUITION) == 2){
				item.identify();
			} else {
				((Ring) item).setKnown();
			}
		}
		if (hero.pointsInTalent(GUNNERS_INTUITION) == 2 &&
				(item instanceof CrudePistol
						||item instanceof CrudePistolAP
						||item instanceof CrudePistolHP
						||item instanceof Pistol
						||item instanceof PistolAP
						||item instanceof PistolHP
						||item instanceof GoldenPistol
						||item instanceof GoldenPistolAP
						||item instanceof GoldenPistolHP
						||item instanceof Handgun
						||item instanceof HandgunAP
						||item instanceof HandgunHP
						||item instanceof Magnum
						||item instanceof MagnumAP
						||item instanceof MagnumHP
						||item instanceof DualPistol
						||item instanceof DualPistolAP
						||item instanceof DualPistolHP
						||item instanceof SubMachinegun
						||item instanceof SubMachinegunAP
						||item instanceof SubMachinegunHP
						||item instanceof AssultRifle
						||item instanceof AssultRifleAP
						||item instanceof AssultRifleHP
						||item instanceof HeavyMachinegun
						||item instanceof HeavyMachinegunAP
						||item instanceof HeavyMachinegunHP
						||item instanceof HuntingRifle
						||item instanceof HuntingRifleAP
						||item instanceof HuntingRifleHP
						||item instanceof SniperRifle
						||item instanceof SniperRifleAP
						||item instanceof SniperRifleHP
						||item instanceof ShotGun
						||item instanceof ShotGunAP
						||item instanceof ShotGunHP
						||item instanceof KSG
						||item instanceof KSGAP
						||item instanceof KSGHP
						||item instanceof MiniGun
						||item instanceof MiniGunAP
						||item instanceof MiniGunHP
						||item instanceof TacticalHandgun
						||item instanceof TacticalHandgunAP
						||item instanceof TacticalHandgunHP
						||item instanceof AntimaterRifle
						||item instanceof AntimaterRifleAP
						||item instanceof AntimaterRifleHP
						||item instanceof RPG7
						||item instanceof RocketLauncher
						||item instanceof FlameThrower
						||item instanceof FlameThrowerAP
						||item instanceof FlameThrowerHP
						||item instanceof PlasmaCannon
						||item instanceof PlasmaCannonAP
						||item instanceof PlasmaCannonHP
						||item instanceof GrenadeLauncher
						||item instanceof GrenadeLauncherAP
						||item instanceof GrenadeLauncherHP
						||item instanceof SleepGun
						||item instanceof FrostGun
						||item instanceof ParalysisGun
						||item instanceof AutoHandgun
						||item instanceof AutoHandgunAP
						||item instanceof AutoHandgunHP
						||item instanceof AutoRifle
						||item instanceof AutoRifleAP
						||item instanceof AutoRifleHP
						||item instanceof MarksmanRifle
						||item instanceof MarksmanRifleAP
						||item instanceof MarksmanRifleHP
				)
		){
			item.identify();
		}
		if (hero.pointsInTalent(MASTERS_INTUITION) >= 1 && item instanceof Weapon) {
			item.identify();
		}
		if (hero.pointsInTalent(KNIGHTS_INTUITION) == 2 && (item instanceof Armor)){
			item.identify();
		}
	}

	public static void onItemCollected( Hero hero, Item item ){
		if (hero.pointsInTalent(THIEFS_INTUITION) == 2){
			if (item instanceof Ring) ((Ring) item).setKnown();
		}
		if (hero.pointsInTalent(MASTERS_INTUITION) == 2) {
			if (item instanceof Weapon) {
				item.identify();
			}
		}
	}

	//note that IDing can happen in alchemy scene, so be careful with VFX here
	public static void onItemIdentified( Hero hero, Item item ){
		if (hero.hasTalent(TEST_SUBJECT)){
			//heal for 2/3 HP
			hero.HP = Math.min(hero.HP + 1 + hero.pointsInTalent(TEST_SUBJECT), hero.HT);
			if (hero.sprite != null) {
				Emitter e = hero.sprite.emitter();
				if (e != null) e.burst(Speck.factory(Speck.HEALING), hero.pointsInTalent(TEST_SUBJECT));
			}
		}
		if (hero.hasTalent(TESTED_HYPOTHESIS)){
			//2/3 turns of wand recharging
			Buff.affect(hero, Recharging.class, 1f + hero.pointsInTalent(TESTED_HYPOTHESIS));
			ScrollOfRecharging.charge(hero);
		}
	}

	public static int onAttackProc( Hero hero, Char enemy, int dmg ){
		if (hero.hasTalent(Talent.SUCKER_PUNCH)
				&& enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)
				&& enemy.buff(SuckerPunchTracker.class) == null){
			dmg += Random.IntRange(hero.pointsInTalent(Talent.SUCKER_PUNCH) , 2);
			Buff.affect(enemy, SuckerPunchTracker.class);
		}

		if (hero.hasTalent(Talent.INCISIVE_BLADE)
				&& enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)
		) {
			if (hero.pointsInTalent(Talent.INCISIVE_BLADE) == 1) {
				dmg += Random.IntRange(0, 5);
			}
			if (hero.pointsInTalent(Talent.INCISIVE_BLADE) == 2) {
				dmg += Random.IntRange(3, 10);
			}
			if (hero.pointsInTalent(Talent.INCISIVE_BLADE) == 3) {
				dmg += Random.IntRange(5, 15);
			}
		}

		if (hero.hasTalent(Talent.FOLLOWUP_STRIKE)) {
			if (hero.belongings.weapon() instanceof MissileWeapon) {
				Buff.affect(enemy, FollowupStrikeTracker.class);
			} else if (enemy.buff(FollowupStrikeTracker.class) != null){
				dmg += 1 + hero.pointsInTalent(FOLLOWUP_STRIKE);
				if (!(enemy instanceof Mob) || !((Mob) enemy).surprisedBy(hero)){
					Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG, 0.75f, 1.2f);
				}
				if (hero.belongings.weapon == null && hero.subClass == HeroSubClass.FIGHTER) {
					Buff.affect( enemy, Paralysis.class, 1f );
				}
				enemy.buff(FollowupStrikeTracker.class).detach();
			}
		}

		if (hero.hasTalent(Talent.UNEXPECTED_SLASH)
				&& enemy.buff(UnexpectedSlashTracker.class) == null){
			dmg += hero.pointsInTalent(Talent.SUCKER_PUNCH);
			Buff.affect(enemy, UnexpectedSlashTracker.class);
		}

		if (hero.hasTalent(Talent.SPEEDY_MOVE) && enemy instanceof Mob && enemy.buff(ShootTheHeartTracker.class) == null){
			Buff.affect(enemy, ShootTheHeartTracker.class);
			Buff.prolong(hero, Haste.class, 1f + hero.pointsInTalent(Talent.SPEEDY_MOVE));
		}

		if (hero.hasTalent(Talent.WAR_CRY) && enemy instanceof Mob && enemy.buff(WarCryTracker.class) == null) {
			Buff.affect(enemy, WarCryTracker.class);
			Buff.prolong(hero, Adrenaline.class, 1+hero.pointsInTalent(Talent.WAR_CRY));
		}

		return dmg;
	}

	public static class SuckerPunchTracker extends Buff{};
	public static class FollowupStrikeTracker extends Buff{};
	public static class UnexpectedSlashTracker extends Buff{};
	public static class ShootTheHeartTracker extends Buff{};
	public static class WarCryTracker extends Buff{};

	public static final int MAX_TALENT_TIERS = 4;

	public static void initClassTalents( Hero hero ){
		initClassTalents( hero.heroClass, hero.talents, hero.metamorphedTalents );
	}

	public static void initClassTalents( HeroClass cls, ArrayList<LinkedHashMap<Talent, Integer>> talents){
		initClassTalents( cls, talents, new LinkedHashMap<>());
	}

	public static void initClassTalents( HeroClass cls, ArrayList<LinkedHashMap<Talent, Integer>> talents, LinkedHashMap<Talent, Talent> replacements ){
		while (talents.size() < MAX_TALENT_TIERS){
			talents.add(new LinkedHashMap<>());
		}

		ArrayList<Talent> tierTalents = new ArrayList<>();

		//tier 1
		switch (cls){
			case WARRIOR: default:
				Collections.addAll(tierTalents, HEARTY_MEAL, ARMSMASTERS_INTUITION, TEST_SUBJECT, IRON_WILL, MAX_HEALTH);
				break;
			case MAGE:
				Collections.addAll(tierTalents, EMPOWERING_MEAL, SCHOLARS_INTUITION, TESTED_HYPOTHESIS, BACKUP_BARRIER, CHARGE_PRESERVE);
				break;
			case ROGUE:
				Collections.addAll(tierTalents, CACHED_RATIONS, THIEFS_INTUITION, SUCKER_PUNCH, PROTECTIVE_SHADOWS, EMERGENCY_ESCAPE);
				break;
			case HUNTRESS:
				Collections.addAll(tierTalents, NATURES_BOUNTY, SURVIVALISTS_INTUITION, FOLLOWUP_STRIKE, NATURES_AID, WATER_FRIENDLY);
				break;
			case GUNNER:
				Collections.addAll(tierTalents,	REARRANGE, GUNNERS_INTUITION, SPEEDY_MOVE, SAFE_RELOAD, MIND_VISION);
				break;
			case SAMURAI:
				Collections.addAll(tierTalents,	CRITICAL_MEAL, MASTERS_INTUITION, UNEXPECTED_SLASH, FLOW_AWAY, ADRENALINE_SURGE);
				break;
			case PLANTER:
				Collections.addAll(tierTalents,	FLOWER_BERRY, ADVENTURERS_INTUITION, SWING, NATURES_PROTECTION, SEED_EATING);
				break;
			case KNIGHT:
				Collections.addAll(tierTalents,	ON_ALERT, KNIGHTS_INTUITION, BATTLE_STIM, ACTIVE_BARRIER, WAR_CRY);
				break;
			case NURSE:
				Collections.addAll(tierTalents,	HEALING_MEAL, DOCTORS_INTUITION, INNER_MIRROR, CRITICAL_SHIELD, HEAL_AMP);
				break;
		}
		for (Talent talent : tierTalents){
			if (replacements.containsKey(talent)){
				talent = replacements.get(talent);
			}
			talents.get(0).put(talent, 0);
		}
		tierTalents.clear();

		//tier 2
		switch (cls){
			case WARRIOR: default:
				Collections.addAll(tierTalents, IRON_STOMACH, RESTORED_WILLPOWER, RUNIC_TRANSFERENCE, LETHAL_MOMENTUM, IMPROVISED_PROJECTILES, DESTRUCTIVE_ATK);
				break;
			case MAGE:
				Collections.addAll(tierTalents, ENERGIZING_MEAL, ENERGIZING_UPGRADE, WAND_PRESERVATION, ARCANE_VISION, SHIELD_BATTERY, FASTER_CHARGER);
				break;
			case ROGUE:
				Collections.addAll(tierTalents, MYSTICAL_MEAL, MYSTICAL_UPGRADE, WIDE_SEARCH, SILENT_STEPS, ROGUES_FORESIGHT, MOVESPEED_ENHANCE);
				break;
			case HUNTRESS:
				Collections.addAll(tierTalents, INVIGORATING_MEAL, RESTORED_NATURE, REJUVENATING_STEPS, HEIGHTENED_SENSES, DURABLE_PROJECTILES, ADDED_MEAL);
				break;
			case GUNNER:
				Collections.addAll(tierTalents,	IN_THE_GUNFIRE, ANOTHER_CHANCE, BULLET_FOCUS, CAMOUFLAGE, LARGER_MAGAZINE, ELASTIC_WEAPON);
				break;
			case SAMURAI:
				Collections.addAll(tierTalents,	FOCUSING_MEAL, CRITICAL_UPGRADE, MAGICAL_TRANSFERENCE, PARRY, DETECTION, POWERFUL_CRIT);
				break;
			case PLANTER:
				Collections.addAll(tierTalents,	NATURAL_MEAL, HERBAL_HEALING, SPROUT, VINE, MASS_PRODUCTION, NEUROTOXIN);
				break;
			case KNIGHT:
				Collections.addAll(tierTalents,	IMPREGNABLE_MEAL, SAFE_HEALING, DEFENSE_STANCE, CROSS_SLASH, ENDURING, BLOCKING);
				break;
			case NURSE:
				Collections.addAll(tierTalents,	CHALLENGING_MEAL, POTION_SPREAD, HEALAREA, ANGEL, MEDICAL_SUPPORT, WINNERS_FLAG);
				break;
		}
		for (Talent talent : tierTalents){
			if (replacements.containsKey(talent)){
				talent = replacements.get(talent);
			}
			talents.get(1).put(talent, 0);
		}
		tierTalents.clear();

		//tier 3
		switch (cls){
			case WARRIOR: default:
				Collections.addAll(tierTalents, HOLD_FAST, STRONGMAN);
				break;
			case MAGE:
				Collections.addAll(tierTalents, EMPOWERING_SCROLLS, ALLY_WARP);
				break;
			case ROGUE:
				Collections.addAll(tierTalents, ENHANCED_RINGS, LIGHT_CLOAK);
				break;
			case HUNTRESS:
				Collections.addAll(tierTalents, POINT_BLANK, SEER_SHOT);
				break;
			case GUNNER:
				Collections.addAll(tierTalents, STREET_BATTLE, HEAVY_ENHANCE);
				break;
			case SAMURAI:
				Collections.addAll(tierTalents,	DEEP_SCAR, FAST_LEAD);
				break;
			case PLANTER:
				Collections.addAll(tierTalents,	BLOOMING_WEAPON, FARMER);
				break;
			case KNIGHT:
				Collections.addAll(tierTalents,	CRAFTMANS_SKILLS, TACKLE);
				break;
			case NURSE:
				Collections.addAll(tierTalents,	POWERFUL_BOND, CHARISMA);
				break;
		}
		for (Talent talent : tierTalents){
			if (replacements.containsKey(talent)){
				talent = replacements.get(talent);
			}
			talents.get(2).put(talent, 0);
		}
		tierTalents.clear();

		//tier4
		//TBD
	}

	public static void initSubclassTalents( Hero hero ){
		initSubclassTalents( hero.subClass, hero.talents );
	}

	public static void initSubclassTalents( HeroSubClass cls, ArrayList<LinkedHashMap<Talent, Integer>> talents ){
		ArrayList<Talent> tierTalents = new ArrayList<>();
		if (cls == HeroSubClass.NONE) return;

		while (talents.size() < MAX_TALENT_TIERS){
			talents.add(new LinkedHashMap<>());
		}

		//tier 3
		switch (cls){
			case BERSERKER: default:
				Collections.addAll(tierTalents, ENDLESS_RAGE, BERSERKING_STAMINA, ENRAGED_CATALYST, LETHAL_RAGE, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case GLADIATOR:
				Collections.addAll(tierTalents, CLEAVE, LETHAL_DEFENSE, ENHANCED_COMBO, QUICK_SWAP, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case VETERAN:
				Collections.addAll(tierTalents, ARM_VETERAN, MARTIAL_ARTS, ENHANCED_FOCUSING, PUSHBACK, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case BATTLEMAGE:
				Collections.addAll(tierTalents, EMPOWERED_STRIKE, MYSTICAL_CHARGE, EXCESS_CHARGE, MANA_ENHANCE, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case WARLOCK:
				Collections.addAll(tierTalents, SOUL_EATER, SOUL_SIPHON, NECROMANCERS_MINIONS, MADNESS, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case ENGINEER:
				Collections.addAll(tierTalents, AMMO_PRESERVE, CONNECTING_CHARGER, HIGH_VOLT, STATIC_ENERGY, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case ASSASSIN:
				Collections.addAll(tierTalents, ENHANCED_LETHALITY, ASSASSINS_REACH, BOUNTY_HUNTER, ENERGY_DRAW, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case FREERUNNER:
				Collections.addAll(tierTalents, EVASIVE_ARMOR, PROJECTILE_MOMENTUM, SPEEDY_STEALTH, QUICK_PREP, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case CHASER:
				Collections.addAll(tierTalents, INCISIVE_BLADE ,LETHAL_SURPRISE , CHAIN_CLOCK, IMAGE_OF_HORROR, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case SNIPER:
				Collections.addAll(tierTalents, FARSIGHT, SHARED_ENCHANTMENT, SHARED_UPGRADES, VISION_ARROW, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case WARDEN:
				Collections.addAll(tierTalents, DURABLE_TIPS, BARKSKIN, SHIELDING_DEW, DENSE_GRASS, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case FIGHTER:
				Collections.addAll(tierTalents, SWIFT_MOVEMENT, JAW_STRIKE, RING_KNUCKLE, SINGULAR_STRIKE, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case LAUNCHER:
				Collections.addAll(tierTalents, HEAVY_GUNNER, ACC_PRACTICE, RECOIL_PRACTICE, DRUM_MAGAZINE, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case RANGER:
				Collections.addAll(tierTalents, QUICK_RELOAD, RECOIL_CONTROL, ELEMENTAL_BULLET, OUTLAW_OF_BARRENLAND, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case RIFLEMAN:
				Collections.addAll(tierTalents, SILENCER, ONLY_ONE_SHOT, EVASIVE_MOVE, SURPRISE_BULLET, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case SLASHER:
				Collections.addAll(tierTalents, CONTINUOUS_ATTACK, SLASHING_PRACTICE, SERIAL_MOMENTUM, ARCANE_ATTACK, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case MASTER:
				Collections.addAll(tierTalents, DONG_MIND_EYES, DONG_SHEATHING, JUNG_DETECTION, JUNG_FOCUSING, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case SLAYER:
				Collections.addAll(tierTalents, AFTERIMAGE, ENERGY_DRAIN, FTL, IMAGE_OF_DEMON, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case TREASUREHUNTER:
				Collections.addAll(tierTalents, TAKEDOWN, DETECTOR, GOLD_SHIELD, GOLD_MINER, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case ADVENTURER:
				Collections.addAll(tierTalents, JUNGLE_ADVENTURE, SHADOW, VINE_WHIP, THORNY_VINE, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case RESEARCHER:
				Collections.addAll(tierTalents, SHOCK_DRAIN, DEW_MAKING, BIO_ENERGY, CLINICAL_TRIAL, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case WEAPONMASTER:
				Collections.addAll(tierTalents, WEAPON_AUGMENT, SWORD_N_SHIELD, FIRE_WEAPON, EARTHQUAKE, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case FORTRESS:
				Collections.addAll(tierTalents, IMPREGNABLE_WALL, COUNTER_MOMENTUM, SHIELD_SLAM, PREPARATION, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case CRUSADER:
				Collections.addAll(tierTalents, NAME_OF_LIGHT, HOLY_SHIELD, DEADS_BLESS, BLESSING_SCROLLS, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case MEDIC:
				Collections.addAll(tierTalents, PROMOTION, HEALING_SHIELD, HEAL_ENHANCE, COMP_RECOVER, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case ANGEL:
				Collections.addAll(tierTalents, APPEASE, ANGEL_AND_DEVIL, AREA_OF_LIGHT, BLESS_ENHANCE, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
			case SURGEON:
				Collections.addAll(tierTalents, SCALPEL, DEFIBRILLATOR, DEATH_DIAGNOSIS, FIRST_AID, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE, BETTER_CHOICE);
				break;
		}
		for (Talent talent : tierTalents){
			talents.get(2).put(talent, 0);
		}
		tierTalents.clear();

	}

	public static void initArmorTalents( Hero hero ){
		initArmorTalents( hero.armorAbility, hero.talents);
	}

	public static void initArmorTalents(ArmorAbility abil, ArrayList<LinkedHashMap<Talent, Integer>> talents ){
		if (abil == null) return;

		while (talents.size() < MAX_TALENT_TIERS){
			talents.add(new LinkedHashMap<>());
		}

		for (Talent t : abil.talents()){
			talents.get(3).put(t, 0);
		}
	}

	private static final String TALENT_TIER = "talents_tier_";

	public static void storeTalentsInBundle( Bundle bundle, Hero hero ){
		for (int i = 0; i < MAX_TALENT_TIERS; i++){
			LinkedHashMap<Talent, Integer> tier = hero.talents.get(i);
			Bundle tierBundle = new Bundle();

			for (Talent talent : tier.keySet()){
				if (tier.get(talent) > 0){
					tierBundle.put(talent.name(), tier.get(talent));
				}
				if (tierBundle.contains(talent.name())){
					tier.put(talent, Math.min(tierBundle.getInt(talent.name()), talent.maxPoints()));
				}
			}
			bundle.put(TALENT_TIER+(i+1), tierBundle);
		}

		Bundle replacementsBundle = new Bundle();
		for (Talent t : hero.metamorphedTalents.keySet()){
			replacementsBundle.put(t.name(), hero.metamorphedTalents.get(t));
		}
		bundle.put("replacements", replacementsBundle);
	}

	public static void restoreTalentsFromBundle( Bundle bundle, Hero hero ){
		//TODO restore replacements
		if (bundle.contains("replacements")){
			Bundle replacements = bundle.getBundle("replacements");
			for (String key : replacements.getKeys()){
				hero.metamorphedTalents.put(Talent.valueOf(key), replacements.getEnum(key, Talent.class));
			}
		}

		if (hero.heroClass != null)     initClassTalents(hero);
		if (hero.subClass != null)      initSubclassTalents(hero);
		if (hero.armorAbility != null)  initArmorTalents(hero);

		for (int i = 0; i < MAX_TALENT_TIERS; i++){
			LinkedHashMap<Talent, Integer> tier = hero.talents.get(i);
			Bundle tierBundle = bundle.contains(TALENT_TIER+(i+1)) ? bundle.getBundle(TALENT_TIER+(i+1)) : null;

			if (tierBundle != null){
				for (Talent talent : tier.keySet()){
					if (tierBundle.contains(talent.name())){
						tier.put(talent, Math.min(tierBundle.getInt(talent.name()), talent.maxPoints()));
					}
				}
			}
		}
	}

}
