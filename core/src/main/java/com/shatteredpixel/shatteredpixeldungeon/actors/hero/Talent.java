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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RevealedArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WandEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WeaponEmpower;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSG;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSGAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSGHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Magnum;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunHP;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000AP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000HP;
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
import com.watabou.utils.GameMath;
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
	ENDLESS_RAGE(13, 3), DEATHLESS_FURY(14, 3), ENRAGED_CATALYST(15, 3), LETHAL_RAGE(16, 3), MAX_RAGE(17, 3), ENDURANCE(18, 3),
	//Gladiator T3
	CLEAVE(19, 3), LETHAL_DEFENSE(20, 3), ENHANCED_COMBO(21, 3), QUICK_SWAP(22, 3), OFFENSIVE_DEFENSE(23, 3), SKILL_ENHANCE(24, 3),
	//Veteran T3
	ARM_VETERAN(25, 3), MARTIAL_ARTS(26, 3), ENHANCED_FOCUSING(27, 3), PUSHBACK(28, 3), FOCUS_UPGRADE(29, 3), BARRIER_FORMATION(30, 3),
	//Heroic Leap T4
	BODY_SLAM(31, 4), IMPACT_WAVE(32, 4), DOUBLE_JUMP(33, 4),
	//Shockwave T4
	EXPANDING_WAVE(34, 4), STRIKING_WAVE(35, 4), SHOCK_FORCE(36, 4),
	//Endure T4
	SUSTAINED_RETRIBUTION(37, 4), SHRUG_IT_OFF(38, 4), EVEN_THE_ODDS(39, 4),

	//Mage T1
	EMPOWERING_MEAL(41), SCHOLARS_INTUITION(42), TESTED_HYPOTHESIS(43), BACKUP_BARRIER(44), CHARGE_PRESERVE(45),
	//Mage T2
	ENERGIZING_MEAL(46), ENERGIZING_UPGRADE(47), WAND_PRESERVATION(48), ARCANE_VISION(49), SHIELD_BATTERY(50), FASTER_CHARGER(51),
	//Mage T3
	EMPOWERING_SCROLLS(52, 3), ALLY_WARP(53, 3),
	//Battlemage T3
	EMPOWERED_STRIKE(54, 3), MYSTICAL_CHARGE(55, 3), EXCESS_CHARGE(56, 3), BATTLE_MAGIC(57, 3), MAGIC_RUSH(58, 3), MAGICAL_CIRCLE(59, 3),
	//Warlock T3
	SOUL_EATER(60, 3), SOUL_SIPHON(61, 3), NECROMANCERS_MINIONS(62, 3), MADNESS(63, 3), ENHANCED_MARK(64, 3), MARK_OF_WEAKNESS(65, 3),
	//Engineer T3
	AMMO_PRESERVE(66, 3), CONNECTING_CHARGER(67, 3), HIGH_VOLT(68, 3), STATIC_ENERGY(69, 3), BATTERY_CHARGE(70, 3), ELECTRIC_BULLET(71, 3),
	//Elemental Blast T4
	BLAST_RADIUS(72, 4), ELEMENTAL_POWER(73, 4), REACTIVE_BARRIER(74, 4),
	//Wild Magic T4
	WILD_POWER(75, 4), FIRE_EVERYTHING(76, 4), CONSERVED_MAGIC(77, 4),
	//Warp Beacon T4
	TELEFRAG(78, 4), REMOTE_BEACON(79, 4), LONGRANGE_WARP(80, 4),

	//Rogue T1
	CACHED_RATIONS(82), THIEFS_INTUITION(83), SUCKER_PUNCH(84), PROTECTIVE_SHADOWS(85), EMERGENCY_ESCAPE(86),
	//Rogue T2
	MYSTICAL_MEAL(87), MYSTICAL_UPGRADE(88), WIDE_SEARCH(89), SILENT_STEPS(90), ROGUES_FORESIGHT(91), MOVESPEED_ENHANCE(92),
	//Rogue T3
	ENHANCED_RINGS(93, 3), LIGHT_CLOAK(94, 3),
	//Assassin T3
	ENHANCED_LETHALITY(95, 3), ASSASSINS_REACH(96, 3), BOUNTY_HUNTER(97, 3), ENERGY_DRAW(98, 3), PERFECT_ASSASSIN(99, 3), CAUTIOUS_PREP(100, 3),
	//Freerunner T3
	EVASIVE_ARMOR(101, 3), PROJECTILE_MOMENTUM(102, 3), SPEEDY_STEALTH(103, 3), QUICK_PREP(104, 3), OVERCOMING(105, 3), MOMENTARY_FOCUSING(106, 3),
	//Chaser T3
	POISONOUS_BLADE(107, 3), LETHAL_SURPRISE(108, 3), CHAIN_CLOCK(109, 3), SOUL_COLLECT(110, 3), TRAIL_TRACKING(111, 3), MASTER_OF_CLOAKING(112, 3),
	//Smoke Bomb T4
	HASTY_RETREAT(113, 4), BODY_REPLACEMENT(114, 4), SHADOW_STEP(115, 4),
	//Death Mark T4
	FEAR_THE_REAPER(116, 4), DEATHLY_DURABILITY(117, 4), DOUBLE_MARK(118, 4),
	//Shadow Clone T4
	SHADOW_BLADE(119, 4), CLONED_ARMOR(120, 4), PERFECT_COPY(121, 4),

	//Huntress T1
	NATURES_BOUNTY(123), SURVIVALISTS_INTUITION(124), FOLLOWUP_STRIKE(125), NATURES_AID(126), WATER_FRIENDLY(127),
	//Huntress T2
	INVIGORATING_MEAL(128), RESTORED_NATURE(129), REJUVENATING_STEPS(130), HEIGHTENED_SENSES(131), DURABLE_PROJECTILES(132), ADDED_MEAL(133),
	//Huntress T3
	POINT_BLANK(134, 3), SEER_SHOT(135, 3),
	//Sniper T3
	FARSIGHT(136, 3), SHARED_ENCHANTMENT(137, 3), SHARED_UPGRADES(138, 3), KICK(139, 3), SHOOTING_EYES(140, 3), TARGET_SPOTTING(141, 3),
	//Warden T3
	DURABLE_TIPS(142, 3), BARKSKIN(143, 3), SHIELDING_DEW(144, 3), DENSE_GRASS(145, 3), ATTRACTION(146, 3), WITHDRAW_TRAP(147, 3),
	//Fighter T3
	SWIFT_MOVEMENT(148, 3), LESS_RESIST(149, 3), RING_KNUCKLE(150, 3), MYSTICAL_PUNCH(151, 3), QUICK_STEP(152, 3), COUNTER_ATTACK(153, 3),
	//Spectral Blades T4
	FAN_OF_BLADES(154, 4), PROJECTING_BLADES(155, 4), SPIRIT_BLADES(156, 4),
	//Natures Power T4
	GROWING_POWER(157, 4), NATURES_WRATH(158, 4), WILD_MOMENTUM(159, 4),
	//Spirit Hawk T4
	EAGLE_EYE(160, 4), GO_FOR_THE_EYES(161, 4), SWIFT_SPIRIT(162, 4),

	//Gunner T1
	REARRANGE(164), GUNNERS_INTUITION(165), SPEEDY_MOVE(166), SAFE_RELOAD(167), MIND_VISION(168),
	//Gunner T2
	IN_THE_GUNFIRE(169), ANOTHER_CHANCE(170), BULLET_FOCUS(171), CAMOUFLAGE(172), LARGER_MAGAZINE(173), ELASTIC_WEAPON(174),
	//Gunner T3
	STREET_BATTLE(175, 3), HEAVY_ENHANCE(176, 3),
	//Launcher T3
	HEAVY_GUNNER(177, 3), ACC_PRACTICE(178, 3), RECOIL_PRACTICE(179, 3), DRUM_MAGAZINE(180, 3), AMMO_SAVE(181, 3), CONCENTRATE_SHOOTING(182, 3),
	//Ranger T3
	QUICK_RELOAD(183, 3), RECOIL_CONTROL(184, 3), ELEMENTAL_BULLET(185, 3), OUTLAW_OF_BARRENLAND(186, 3), ROLLING(187, 3), SPECIAL_TRAINING(188, 3),
	//RifleMan T3
	SILENCER(189, 3), ONLY_ONE_SHOT(190, 3), EVASIVE_MOVE(191, 3), SURPRISE_BULLET(192, 3), RANGED_SNIPING(193, 3), TELESCOPE(194, 3),
	//Riot T4
	HASTE_MOVE(195, 4), SHOT_CONCENTRATION(196, 4), ROUND_PRESERVE(197, 4),
	//ReinforcedArmor T4
	BAYONET(198, 4), TACTICAL_SIGHT(199, 4), PLATE_ADD(200, 4),
	//FirstAidKit T4
	ADDITIONAL_MEDS(201,4), THERAPEUTIC_BANDAGE(202, 4), FASTER_HEALING(203,4),

	//Samurai T1
	CRITICAL_MEAL(205), MASTERS_INTUITION(206), UNEXPECTED_SLASH(207), FLOW_AWAY(208), ADRENALINE_SURGE(209),
	//Samurai T2
	FOCUSING_MEAL(210), CRITICAL_UPGRADE(211), MAGICAL_TRANSFERENCE(212), PARRY(213), DETECTION(214), POWERFUL_CRIT(215),
	//Samurai T3
	DEEP_SCAR(216, 3), FAST_LEAD(217, 3),
	//Slasher T3
	CONTINUOUS_ATTACK(218, 3), SLASHING_PRACTICE(219, 3), SERIAL_MOMENTUM(220, 3), ARCANE_ATTACK(221, 3), SLASHING(222, 3), DETECTIVE_SLASHING(223, 3),
	//Master T3
	DONG_MIND_EYES(224, 3), DONG_SHEATHING(225, 3), DONG_POLISHING(226, 3), JUNG_DETECTION(227, 3), JUNG_FOCUSING(228, 3), JUNG_INCISIVE_BLADE(229, 3),
	//Slayer T3
	AFTERIMAGE(230, 3), ENERGY_DRAIN(231, 3), FTL(232, 3), QUICK_RECOVER(233, 3), HASTE_RECOVER(234, 3), FLURRY(235, 3),
	//Awake T4
	AWAKE_LIMIT(236, 4), AWAKE_DURATION(237, 4), INSURANCE(238, 4),
	//ShadowBlade T4
	DOUBLE_BLADE_PRACTICE(239, 4), CRITICAL_SHADOW(240, 4), HERBAL_SHADOW(241, 4),
	//Kunai T4
	KUNAI_OF_DOOM(242, 4), MYSTICAL_KUNAI(243, 4), POISONED_KUNAI(244, 4),

	//Planter T1
	SUDDEN_GROWTH(246), SAFE_POTION(247), ROOT(248), KNOWLEDGE_HERB(249), GRAVEL_THROW(250),
	//Planter T2
	NATURAL_MEAL(251), HERBAL_DEW(252), SPROUT(253), FIREWATCH(254), FLOWER_BED(255), WEAK_POISON(256),
	//Planter T3
	BLOOMING_WEAPON(257, 3), FARMER(258, 3),
	//TreasureHunter T3
	TAKEDOWN(259, 3), DETECTOR(260, 3), GOLD_SHIELD(261, 3), GOLD_MINER(262, 3), FINDING_TREASURE(263, 3), HIDDEN_STASH(264, 3),
	//Adventurer T3
	JUNGLE_ADVENTURE(265, 3), SHADOW(266, 3), VINE_WHIP(267, 3), THORNY_VINE(268, 3), SNARE(269, 3), SHARP_INTUITION(270, 3),
	//Researcher T3
	ALIVE_GRASS(271, 3), DEW_MAKING(272, 3), BIO_ENERGY(273, 3), ROOT_PLANT(274, 3), BIOLOGY_PROJECT(275, 3), CHEMICAL(276, 3),
	//Sprout T4
	JUNGLE(277, 4), FOREST(278, 4), REGROWTH(279, 4),
	//TreasureMap T4
	LONG_LUCK(280, 4), FORESIGHT(281, 4), GOLD_HUNTER(282, 4),
	//Root T4
	POISONOUS_ROOT(283, 4), ROOT_SPREAD(284, 4), ROOT_ARMOR(285, 4),

	//Knight T1
	ON_ALERT(287), KNIGHTS_INTUITION(288), ARMOR_ENHANCE(289), ACTIVE_BARRIER(290), WAR_CRY(291),
	//Knight T2
	IMPREGNABLE_MEAL(292), SAFE_HEALING(293), DEFENSE_STANCE(294), CROSS_SLASH(295), ENDURING(296), BLOCKING(297),
	//Knight T3
	CRAFTMANS_SKILLS(298, 3), TACKLE(299, 3),
	//Weaponmaster T3
	CLASH(300, 3), MYSTICAL_POWER(301, 3), ABSOLUTE_ZERO(302, 3), EARTHQUAKE(303, 3), SPEAR_N_SHIELD(304, 3), UPGRADE_SHARE(305, 3),
	//Fortress T3
	IMPREGNABLE_WALL(306, 3), COUNTER_MOMENTUM(307, 3), SHIELD_SLAM(308, 3), PREPARATION(309, 3), FORTRESS(310, 3), MYSTICAL_COUNTER(311, 3),
	//Crusader T3
	NAME_OF_LIGHT(312, 3), HOLY_SHIELD(313, 3), DEADS_BLESS(314, 3), BLESSING_SCROLLS(315, 3), DIVINE_SHIELD(316, 3), PROTECTION_OF_LIGHT(317, 3),
	//HolyShield T4
	BUFFER_BARRIER(318, 4), HOLY_LIGHT(319, 4), BLESS(320, 4),
	//StimPack T4
	BURDEN_RELIEF(321, 4), LASTING_PACK(322, 4), TIME_STOP(323, 4),
	//UnstableAnkh T4
	BLESSED_ANKH(324, 4), ANKH_ENHANCE(325, 4), COMPLETE_ANKH(326, 4),

	//Nurse T1
	HEALING_MEAL(328), DOCTORS_INTUITION(329), INNER_MIRROR(330), CRITICAL_SHIELD(331), HEAL_AMP(332),
	//Nurse T2
	CHALLENGING_MEAL(333), POTION_SPREAD(334), HEALAREA(335), ANGEL(336), MEDICAL_SUPPORT(337), WINNERS_FLAG(338),
	//Nurse T3
	POWERFUL_BOND(339, 3), CHARISMA(340, 3),
	//Medic T3
	PROMOTION(341, 3), HEALING_SHIELD(342, 3), HEAL_ENHANCE(343, 3), COMP_RECOVER(344, 3), IMMUNE_SYSTEM(345, 3), HIGHER_HEAL(346, 3),
	//Angel T3
	APPEASE(347, 3), ANGEL_AND_DEVIL(348, 3), AREA_OF_LIGHT(349, 3), BLESS_ENHANCE(350, 3), PERSUASION(351, 3), HOLY_PROTECTION(352, 3),
	//Surgeon T3
	SCALPEL(353, 3), DEFIBRILLATOR(354, 3), DEATH_DIAGNOSIS(355, 3), FIRST_AID(356, 3), DISINFECTION(357, 3), HASTY_HANDS(358, 3),
	//HealGenerator T4
	AREA_AMP(359, 4), SHIELD_GEN(360, 4), DURABLE_GEN(361, 4),
	//AngelWing T4
	LIGHT_LIKE_FEATHER(362, 4), ANGELS_BLESS(363, 4), HEALING_WING(364, 4),
	//GammaRayEmmit T4
	TRANSMOG_BIAS(365, 4), IMPRINTING_EFFECT(366, 4), SHEEP_TRANSMOG(367, 4),

	//universal T4
	HEROIC_ENERGY(40, 4), //See icon() and title() for special logic for this one
	//Ratmogrify T4
	RATSISTANCE(373, 4), RATLOMACY(374, 4), RATFORCEMENTS(375, 4),
	//universal T3
	ATK_SPEED_ENHANCE(369, 4), ACC_ENHANCE(370, 4), EVA_ENHANCE(371, 4), BETTER_CHOICE(372, 3);

	public static class ImprovisedProjectileCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.15f, 0.2f, 0.5f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 50); }
	};
	public static class LethalMomentumTracker extends FlavourBuff{};
	public static class StrikingWaveTracker extends FlavourBuff{};
	public static class WandPreservationCounter extends CounterBuff{{revivePersists = true;}};
	public static class EmpoweredStrikeTracker extends FlavourBuff{};
	public static class ProtectiveShadowsTracker extends Buff {
		float barrierInc = 0.5f;

		@Override
		public boolean act() {
			//barrier every 2/1 turns, to a max of 3/5
			if (((Hero)target).hasTalent(Talent.PROTECTIVE_SHADOWS) && target.invisible > 0){
				Barrier barrier = Buff.affect(target, Barrier.class);
				if (barrier.shielding() < 1 + 2*((Hero)target).pointsInTalent(Talent.PROTECTIVE_SHADOWS)) {
					barrierInc += 0.5f * ((Hero) target).pointsInTalent(Talent.PROTECTIVE_SHADOWS);
				}
				if (barrierInc >= 1){
					barrierInc = 0;
					barrier.incShield(1);
				} else {
					barrier.incShield(0); //resets barrier decay
				}
			} else {
				detach();
			}
			spend( TICK );
			return true;
		}

		private static final String BARRIER_INC = "barrier_inc";
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put( BARRIER_INC, barrierInc);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			barrierInc = bundle.getFloat( BARRIER_INC );
		}
	}
	public static class BountyHunterTracker extends FlavourBuff{};
	//public static class SingularStrikeTracker extends Buff{};
	public static class CriticalUpgradeTracker extends Buff{};
	public static class ShootingEyesTracker extends Buff{};
	public static class MysticalPunchTracker extends Buff{};
	public static class CounterAttackTracker extends Buff{};
	public static class RollingTracker extends Buff{};
	public static class DetactiveSlashingTracker extends Buff{};
	public static class IncisiveBladeTracker extends Buff{};
	public static class ActiveBarrierTracker extends Buff{};
	public static class RejuvenatingStepsCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0f, 0.35f, 0.15f); }
		public float iconFadePercent() { return GameMath.gate(0, visualcooldown() / (15 - 5*Dungeon.hero.pointsInTalent(REJUVENATING_STEPS)), 1); }
	};
	public static class RejuvenatingStepsFurrow extends CounterBuff{{revivePersists = true;}};
	public static class SeerShotCooldown extends FlavourBuff{
		public int icon() { return target.buff(RevealedArea.class) != null ? BuffIndicator.NONE : BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.7f, 0.4f, 0.7f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 20); }
	};
	public static class SpiritBladesTracker extends FlavourBuff{};
	public static class ChaseCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.4f, 0.4f, 0.8f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / (15)); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
		public void spendTime() { spend(-1f); }
	};
	public static class ChainCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.2f, 0.5f, 0.8f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 10); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
		public void spendTime() { spend(-1f); }
	};
	public static class LethalCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.8f, 0.1f, 0.1f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 5); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
		public void spendTime() { spend(-1f); }
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
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 15); }
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
	public static class KickCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0xF27318); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 10); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class MaxRageCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0xFF3333); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 50); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class DetectiveSlashingCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0x002157); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 5); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class SnareCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0x002157); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 10 * (4 - Dungeon.hero.pointsInTalent(Talent.SNARE))); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class UpgradeShareCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0x00D800); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / (3 * Dungeon.hero.pointsInTalent(Talent.UPGRADE_SHARE))); }
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
				return 376;
			}
			HeroClass cls = Dungeon.hero != null ? Dungeon.hero.heroClass : GamesInProgress.selectedClass;
			switch (cls){
				case WARRIOR: default:
					return 40;
				case MAGE:
					return 81;
				case ROGUE:
					return 122;
				case HUNTRESS:
					return 163;
				case GUNNER:
					return 204;
				case SAMURAI:
					return 245;
				case PLANTER:
					return 286;
				case KNIGHT:
					return 327;
				case NURSE:
					return 368;
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

	public final String desc(){
		return desc(false);
	}

	public String desc(boolean metamorphed){
		if (metamorphed){
			String metaDesc = Messages.get(this, name() + ".meta_desc");
			if (!metaDesc.equals(Messages.NO_TEXT_FOUND)){
				return Messages.get(this, name() + ".desc") + "\n\n" + metaDesc;
			}
		}
		return Messages.get(this, name() + ".desc");
	}

	public static void onTalentUpgraded( Hero hero, Talent talent){
		//for metamorphosis
		if (talent == IRON_WILL && hero.heroClass != HeroClass.WARRIOR){
			Buff.affect(hero, BrokenSeal.WarriorShield.class);
	}
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
		if (talent == GUNNERS_INTUITION && hero.pointsInTalent(GUNNERS_INTUITION) == 2){
			if (hero.belongings.weapon() != null &&
					(hero.belongings.weapon() instanceof CrudePistol
						|| hero.belongings.weapon() instanceof CrudePistolAP
						|| hero.belongings.weapon() instanceof CrudePistolHP
						|| hero.belongings.weapon() instanceof Pistol
						|| hero.belongings.weapon() instanceof PistolAP
						|| hero.belongings.weapon() instanceof PistolHP
						|| hero.belongings.weapon() instanceof GoldenPistol
						|| hero.belongings.weapon() instanceof GoldenPistolAP
						|| hero.belongings.weapon() instanceof GoldenPistolHP
						|| hero.belongings.weapon() instanceof Handgun
						|| hero.belongings.weapon() instanceof HandgunAP
						|| hero.belongings.weapon() instanceof HandgunHP
						|| hero.belongings.weapon() instanceof Magnum
						|| hero.belongings.weapon() instanceof MagnumAP
						|| hero.belongings.weapon() instanceof MagnumHP
						|| hero.belongings.weapon() instanceof TacticalHandgun
						|| hero.belongings.weapon() instanceof TacticalHandgunAP
						|| hero.belongings.weapon() instanceof TacticalHandgunHP
						|| hero.belongings.weapon() instanceof AutoHandgun
						|| hero.belongings.weapon() instanceof AutoHandgunAP
						|| hero.belongings.weapon() instanceof AutoHandgunHP
						|| hero.belongings.weapon() instanceof DualPistol
						|| hero.belongings.weapon() instanceof DualPistolAP
						|| hero.belongings.weapon() instanceof DualPistolHP
						|| hero.belongings.weapon() instanceof SubMachinegun
						|| hero.belongings.weapon() instanceof SubMachinegunAP
						|| hero.belongings.weapon() instanceof SubMachinegunHP
						|| hero.belongings.weapon() instanceof AssultRifle
						|| hero.belongings.weapon() instanceof AssultRifleAP
						|| hero.belongings.weapon() instanceof AssultRifleHP
						|| hero.belongings.weapon() instanceof HeavyMachinegun
						|| hero.belongings.weapon() instanceof HeavyMachinegunAP
						|| hero.belongings.weapon() instanceof HeavyMachinegunHP
						|| hero.belongings.weapon() instanceof MiniGun
						|| hero.belongings.weapon() instanceof MiniGunAP
						|| hero.belongings.weapon() instanceof MiniGunHP
						|| hero.belongings.weapon() instanceof AutoRifle
						|| hero.belongings.weapon() instanceof AutoRifleAP
						|| hero.belongings.weapon() instanceof AutoRifleHP
						|| hero.belongings.weapon() instanceof Revolver
						|| hero.belongings.weapon() instanceof RevolverAP
						|| hero.belongings.weapon() instanceof RevolverHP
						|| hero.belongings.weapon() instanceof HuntingRifle
						|| hero.belongings.weapon() instanceof HuntingRifleAP
						|| hero.belongings.weapon() instanceof HuntingRifleHP
						|| hero.belongings.weapon() instanceof Carbine
						|| hero.belongings.weapon() instanceof CarbineAP
						|| hero.belongings.weapon() instanceof CarbineHP
						|| hero.belongings.weapon() instanceof SniperRifle
						|| hero.belongings.weapon() instanceof SniperRifleAP
						|| hero.belongings.weapon() instanceof SniperRifleHP
						|| hero.belongings.weapon() instanceof AntimaterRifle
						|| hero.belongings.weapon() instanceof AntimaterRifleAP
						|| hero.belongings.weapon() instanceof AntimaterRifleHP
						|| hero.belongings.weapon() instanceof MarksmanRifle
						|| hero.belongings.weapon() instanceof MarksmanRifleAP
						|| hero.belongings.weapon() instanceof MarksmanRifleHP
						|| hero.belongings.weapon() instanceof WA2000
						|| hero.belongings.weapon() instanceof WA2000AP
						|| hero.belongings.weapon() instanceof WA2000HP
						|| hero.belongings.weapon() instanceof ShotGun
						|| hero.belongings.weapon() instanceof ShotGunAP
						|| hero.belongings.weapon() instanceof ShotGunHP
						|| hero.belongings.weapon() instanceof KSG
						|| hero.belongings.weapon() instanceof KSGAP
						|| hero.belongings.weapon() instanceof KSGHP
						|| hero.belongings.weapon() instanceof FlameThrower
						|| hero.belongings.weapon() instanceof FlameThrowerAP
						|| hero.belongings.weapon() instanceof FlameThrowerHP
						|| hero.belongings.weapon() instanceof PlasmaCannon
						|| hero.belongings.weapon() instanceof PlasmaCannonAP
						|| hero.belongings.weapon() instanceof PlasmaCannonHP
						|| hero.belongings.weapon() instanceof RPG7
						|| hero.belongings.weapon() instanceof RocketLauncher)) hero.belongings.weapon().identify();
		}
		if (talent == MASTERS_INTUITION && hero.pointsInTalent(MASTERS_INTUITION) == 1){
			if (hero.belongings.weapon() != null) hero.belongings.weapon().identify();
		}
		if (talent == KNIGHTS_INTUITION && hero.pointsInTalent(KNIGHTS_INTUITION) == 2){
			if (hero.belongings.armor() != null)  hero.belongings.armor.identify();
		}

		if (talent == LIGHT_CLOAK && hero.heroClass == HeroClass.ROGUE){
			for (Item item : Dungeon.hero.belongings.backpack){
				if (item instanceof CloakOfShadows){
					if (hero.buff(LostInventory.class) == null || item.keptThoughLostInvent) {
						((CloakOfShadows) item).activate(Dungeon.hero);
					}
				}
			}
		}

		if (talent == HEIGHTENED_SENSES || talent == FARSIGHT || talent == TELESCOPE || talent == JUNGLE_ADVENTURE){
			Dungeon.observe();
		}

		if (talent == BETTER_CHOICE && hero.pointsInTalent(BETTER_CHOICE) == 1){
			StoneOfEnchantment stone = new StoneOfEnchantment();
			if (stone.doPickUp( Dungeon.hero )) {
				GLog.i( Messages.get(Dungeon.hero, "you_now_have", stone.name() ));
				hero.spend(-1);
			} else {
				Dungeon.level.drop( stone, Dungeon.hero.pos ).sprite.drop();
			}
		}

		if (talent == BETTER_CHOICE && hero.pointsInTalent(BETTER_CHOICE) == 2){
			ScrollOfEnchantment enchantment = new ScrollOfEnchantment();
			if (enchantment.doPickUp( Dungeon.hero )) {
				GLog.i( Messages.get(Dungeon.hero, "you_now_have", enchantment.name() ));
				hero.spend(-1);
			} else {
				Dungeon.level.drop( enchantment, Dungeon.hero.pos ).sprite.drop();
			}
		}

		if (talent == BETTER_CHOICE && hero.pointsInTalent(BETTER_CHOICE) == 3){
			ScrollOfUpgrade scl = new ScrollOfUpgrade();
			if (scl.doPickUp( Dungeon.hero )) {
				GLog.i( Messages.get(Dungeon.hero, "you_now_have", scl.name() ));
				hero.spend(-1);
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
	public static class NatureBerriesAvailable extends CounterBuff{{revivePersists = true;}}; //for pre-1.3.0 saves
	public static class NatureBerriesDropped extends CounterBuff{{revivePersists = true;}};

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
			SpellSprite.show(hero, SpellSprite.CHARGE);
		}
		if (hero.hasTalent(MYSTICAL_MEAL)){
			//3/5 turns of recharging
			ArtifactRecharge buff = Buff.affect( hero, ArtifactRecharge.class);
			if (buff.left() < 1 + 2*(hero.pointsInTalent(MYSTICAL_MEAL))){
				Buff.affect( hero, ArtifactRecharge.class).set(1 + 2*(hero.pointsInTalent(MYSTICAL_MEAL))).ignoreHornOfPlenty = foodSource instanceof HornOfPlenty;
			}
			ScrollOfRecharging.charge( hero );
			SpellSprite.show(hero, SpellSprite.CHARGE, 0, 1, 1);
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
			if (hero.heroClass == HeroClass.SAMURAI) Buff.prolong( hero, CritBonus.class, 10f).set(5f * hero.pointsInTalent(Talent.CRITICAL_MEAL));
			else Buff.affect( hero, WeaponEmpower.class).set(hero.pointsInTalent(Talent.CRITICAL_MEAL), 10);
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
			Buff.affect(hero, ArmorEmpower.class).set(hero.pointsInTalent(Talent.IMPREGNABLE_MEAL), 10f);
		}
		if (hero.hasTalent(Talent.HEALING_MEAL)) {
			Buff.affect(hero, HealingArea.class).setup(hero.pos, 10*hero.pointsInTalent(Talent.HEALING_MEAL), 1, true);
		}
		if (hero.hasTalent(Talent.CHALLENGING_MEAL)) {
			Buff.affect(hero, ScrollOfChallenge.ChallengeArena.class).setup(hero.pos, 10*hero.pointsInTalent(Talent.CHALLENGING_MEAL));
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
				|| item instanceof CrudePistolAP
				|| item instanceof CrudePistolHP
				|| item instanceof Pistol
				|| item instanceof PistolAP
				|| item instanceof PistolHP
				|| item instanceof GoldenPistol
				|| item instanceof GoldenPistolAP
				|| item instanceof GoldenPistolHP
				|| item instanceof Handgun
				|| item instanceof HandgunAP
				|| item instanceof HandgunHP
				|| item instanceof Magnum
				|| item instanceof MagnumAP
				|| item instanceof MagnumHP
				|| item instanceof TacticalHandgun
				|| item instanceof TacticalHandgunAP
				|| item instanceof TacticalHandgunHP
				|| item instanceof AutoHandgun
				|| item instanceof AutoHandgunAP
				|| item instanceof AutoHandgunHP

				|| item instanceof DualPistol
				|| item instanceof DualPistolAP
				|| item instanceof DualPistolHP
				|| item instanceof SubMachinegun
				|| item instanceof SubMachinegunAP
				|| item instanceof SubMachinegunHP
				|| item instanceof AssultRifle
				|| item instanceof AssultRifleAP
				|| item instanceof AssultRifleHP
				|| item instanceof HeavyMachinegun
				|| item instanceof HeavyMachinegunAP
				|| item instanceof HeavyMachinegunHP
				|| item instanceof MiniGun
				|| item instanceof MiniGunAP
				|| item instanceof MiniGunHP
				|| item instanceof AutoRifle
				|| item instanceof AutoRifleAP
				|| item instanceof AutoRifleHP

				|| item instanceof Revolver
				|| item instanceof RevolverAP
				|| item instanceof RevolverHP
				|| item instanceof HuntingRifle
				|| item instanceof HuntingRifleAP
				|| item instanceof HuntingRifleHP
				|| item instanceof Carbine
				|| item instanceof CarbineAP
				|| item instanceof CarbineHP
				|| item instanceof SniperRifle
				|| item instanceof SniperRifleAP
				|| item instanceof SniperRifleHP
				|| item instanceof AntimaterRifle
				|| item instanceof AntimaterRifleAP
				|| item instanceof AntimaterRifleHP
				|| item instanceof MarksmanRifle
				|| item instanceof MarksmanRifleAP
				|| item instanceof MarksmanRifleHP
				|| item instanceof WA2000
				|| item instanceof WA2000AP
				|| item instanceof WA2000HP

				|| item instanceof ShotGun
				|| item instanceof ShotGunAP
				|| item instanceof ShotGunHP
				|| item instanceof KSG
				|| item instanceof KSGAP
				|| item instanceof KSGHP

				|| item instanceof FlameThrower
				|| item instanceof FlameThrowerAP
				|| item instanceof FlameThrowerHP
				|| item instanceof PlasmaCannon
				|| item instanceof PlasmaCannonAP
				|| item instanceof PlasmaCannonHP

				|| item instanceof RPG7
				|| item instanceof RocketLauncher) {
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
			if (hero.heroClass == HeroClass.WARRIOR) {
				BrokenSeal.WarriorShield shield = hero.buff(BrokenSeal.WarriorShield.class);
				if (shield != null) {
					int shieldToGive = Math.round(shield.maxShield() * 0.33f * (1 + hero.pointsInTalent(RESTORED_WILLPOWER)));
					shield.supercharge(shieldToGive);
				}
			} else {
				int shieldToGive = Math.round( hero.HT * (0.025f * (1+hero.pointsInTalent(RESTORED_WILLPOWER))));
				Buff.affect(hero, Barrier.class).setShield(shieldToGive);
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
			if (hero.heroClass == HeroClass.MAGE) {
				MagesStaff staff = hero.belongings.getItem(MagesStaff.class);
				if (staff != null) {
					staff.gainCharge(2 + 2 * hero.pointsInTalent(ENERGIZING_UPGRADE), true);
					ScrollOfRecharging.charge(Dungeon.hero);
					SpellSprite.show(hero, SpellSprite.CHARGE);
				}
			} else {
				Buff.affect(hero, Recharging.class, 4 + 8 * hero.pointsInTalent(ENERGIZING_UPGRADE));
			}
		}
		if (hero.hasTalent(MYSTICAL_UPGRADE)){
			if (hero.heroClass == HeroClass.ROGUE) {
				CloakOfShadows cloak = hero.belongings.getItem(CloakOfShadows.class);
				if (cloak != null) {
					cloak.overCharge(1 + hero.pointsInTalent(MYSTICAL_UPGRADE));
					ScrollOfRecharging.charge(Dungeon.hero);
					SpellSprite.show(hero, SpellSprite.CHARGE, 0, 1, 1);
				}
			} else {
				Buff.affect(hero, ArtifactRecharge.class).set( 2 + 4*hero.pointsInTalent(MYSTICAL_UPGRADE) ).ignoreHornOfPlenty = false;
				ScrollOfRecharging.charge(Dungeon.hero);
				SpellSprite.show(hero, SpellSprite.CHARGE, 0, 1, 1);
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
		 	if (hero.heroClass == HeroClass.SAMURAI) Buff.prolong(hero, CertainCrit.class, 5*hero.pointsInTalent(CRITICAL_UPGRADE));
			else Buff.affect( hero, CriticalUpgradeTracker.class);
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
						|| item instanceof CrudePistolAP
						|| item instanceof CrudePistolHP
						|| item instanceof Pistol
						|| item instanceof PistolAP
						|| item instanceof PistolHP
						|| item instanceof GoldenPistol
						|| item instanceof GoldenPistolAP
						|| item instanceof GoldenPistolHP
						|| item instanceof Handgun
						|| item instanceof HandgunAP
						|| item instanceof HandgunHP
						|| item instanceof Magnum
						|| item instanceof MagnumAP
						|| item instanceof MagnumHP
						|| item instanceof TacticalHandgun
						|| item instanceof TacticalHandgunAP
						|| item instanceof TacticalHandgunHP
						|| item instanceof AutoHandgun
						|| item instanceof AutoHandgunAP
						|| item instanceof AutoHandgunHP

						|| item instanceof DualPistol
						|| item instanceof DualPistolAP
						|| item instanceof DualPistolHP
						|| item instanceof SubMachinegun
						|| item instanceof SubMachinegunAP
						|| item instanceof SubMachinegunHP
						|| item instanceof AssultRifle
						|| item instanceof AssultRifleAP
						|| item instanceof AssultRifleHP
						|| item instanceof HeavyMachinegun
						|| item instanceof HeavyMachinegunAP
						|| item instanceof HeavyMachinegunHP
						|| item instanceof MiniGun
						|| item instanceof MiniGunAP
						|| item instanceof MiniGunHP
						|| item instanceof AutoRifle
						|| item instanceof AutoRifleAP
						|| item instanceof AutoRifleHP

						|| item instanceof Revolver
						|| item instanceof RevolverAP
						|| item instanceof RevolverHP
						|| item instanceof HuntingRifle
						|| item instanceof HuntingRifleAP
						|| item instanceof HuntingRifleHP
						|| item instanceof Carbine
						|| item instanceof CarbineAP
						|| item instanceof CarbineHP
						|| item instanceof SniperRifle
						|| item instanceof SniperRifleAP
						|| item instanceof SniperRifleHP
						|| item instanceof AntimaterRifle
						|| item instanceof AntimaterRifleAP
						|| item instanceof AntimaterRifleHP
						|| item instanceof MarksmanRifle
						|| item instanceof MarksmanRifleAP
						|| item instanceof MarksmanRifleHP
						|| item instanceof WA2000
						|| item instanceof WA2000AP
						|| item instanceof WA2000HP

						|| item instanceof ShotGun
						|| item instanceof ShotGunAP
						|| item instanceof ShotGunHP
						|| item instanceof KSG
						|| item instanceof KSGAP
						|| item instanceof KSGHP

						|| item instanceof FlameThrower
						|| item instanceof FlameThrowerAP
						|| item instanceof FlameThrowerHP
						|| item instanceof PlasmaCannon
						|| item instanceof PlasmaCannonAP
						|| item instanceof PlasmaCannonHP

						|| item instanceof RPG7
						|| item instanceof RocketLauncher)
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

		if (hero.hasTalent(Talent.POWERFUL_CRIT)
				&& hero.heroClass != HeroClass.SAMURAI
				&& enemy.buff(PowerfulCritTracker.class) == null){
			dmg += 1+2*hero.pointsInTalent(Talent.POWERFUL_CRIT);
			Buff.affect(enemy, PowerfulCritTracker.class);
		}

		if (hero.hasTalent(Talent.POISONOUS_BLADE)
				&& enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)
		) {
			Buff.affect(enemy, Poison.class).set(2+hero.pointsInTalent(Talent.POISONOUS_BLADE));
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
	public static class PowerfulCritTracker extends Buff{};
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
				Collections.addAll(tierTalents,	SUDDEN_GROWTH, SAFE_POTION, ROOT, KNOWLEDGE_HERB, GRAVEL_THROW);
				break;
			case KNIGHT:
				Collections.addAll(tierTalents,	ON_ALERT, KNIGHTS_INTUITION, ARMOR_ENHANCE, ACTIVE_BARRIER, WAR_CRY);
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
				Collections.addAll(tierTalents,	NATURAL_MEAL, HERBAL_DEW, SPROUT, FIREWATCH, FLOWER_BED, WEAK_POISON);
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
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, ENDLESS_RAGE, DEATHLESS_FURY, ENRAGED_CATALYST, LETHAL_RAGE, MAX_RAGE, ENDURANCE);
				break;
			case GLADIATOR:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, CLEAVE, LETHAL_DEFENSE, ENHANCED_COMBO, QUICK_SWAP, OFFENSIVE_DEFENSE, SKILL_ENHANCE);
				break;
			case VETERAN:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, ARM_VETERAN, MARTIAL_ARTS, ENHANCED_FOCUSING, PUSHBACK, FOCUS_UPGRADE, BARRIER_FORMATION);
				break;
			case BATTLEMAGE:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, EMPOWERED_STRIKE, MYSTICAL_CHARGE, EXCESS_CHARGE, BATTLE_MAGIC, MAGIC_RUSH, MAGICAL_CIRCLE);
				break;
			case WARLOCK:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, SOUL_EATER, SOUL_SIPHON, NECROMANCERS_MINIONS, MADNESS, ENHANCED_MARK, MARK_OF_WEAKNESS);
				break;
			case ENGINEER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, AMMO_PRESERVE, CONNECTING_CHARGER, HIGH_VOLT, STATIC_ENERGY, BATTERY_CHARGE, ELECTRIC_BULLET);
				break;
			case ASSASSIN:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, ENHANCED_LETHALITY, ASSASSINS_REACH, BOUNTY_HUNTER, ENERGY_DRAW, PERFECT_ASSASSIN, CAUTIOUS_PREP);
				break;
			case FREERUNNER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, EVASIVE_ARMOR, PROJECTILE_MOMENTUM, SPEEDY_STEALTH, QUICK_PREP, OVERCOMING, MOMENTARY_FOCUSING);
				break;
			case CHASER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, POISONOUS_BLADE ,LETHAL_SURPRISE , CHAIN_CLOCK, SOUL_COLLECT, TRAIL_TRACKING, MASTER_OF_CLOAKING);
				break;
			case SNIPER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, FARSIGHT, SHARED_ENCHANTMENT, SHARED_UPGRADES, KICK, SHOOTING_EYES, TARGET_SPOTTING);
				break;
			case WARDEN:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, DURABLE_TIPS, BARKSKIN, SHIELDING_DEW, DENSE_GRASS, ATTRACTION, WITHDRAW_TRAP);
				break;
			case FIGHTER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, SWIFT_MOVEMENT, LESS_RESIST, RING_KNUCKLE, MYSTICAL_PUNCH, QUICK_STEP, COUNTER_ATTACK);
				break;
			case LAUNCHER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, HEAVY_GUNNER, ACC_PRACTICE, RECOIL_PRACTICE, DRUM_MAGAZINE, AMMO_SAVE, CONCENTRATE_SHOOTING);
				break;
			case RANGER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, QUICK_RELOAD, RECOIL_CONTROL, ELEMENTAL_BULLET, OUTLAW_OF_BARRENLAND, ROLLING, SPECIAL_TRAINING);
				break;
			case RIFLEMAN:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, SILENCER, ONLY_ONE_SHOT, EVASIVE_MOVE, SURPRISE_BULLET, RANGED_SNIPING, TELESCOPE);
				break;
			case SLASHER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, CONTINUOUS_ATTACK, SLASHING_PRACTICE, SERIAL_MOMENTUM, ARCANE_ATTACK, SLASHING, DETECTIVE_SLASHING);
				break;
			case MASTER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, DONG_MIND_EYES, DONG_SHEATHING, DONG_POLISHING, JUNG_DETECTION, JUNG_FOCUSING, JUNG_INCISIVE_BLADE);
				break;
			case SLAYER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, AFTERIMAGE, ENERGY_DRAIN, FTL, QUICK_RECOVER, HASTE_RECOVER, FLURRY);
				break;
			case TREASUREHUNTER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, TAKEDOWN, DETECTOR, GOLD_SHIELD, GOLD_MINER, FINDING_TREASURE, HIDDEN_STASH);
				break;
			case ADVENTURER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, JUNGLE_ADVENTURE, SHADOW, VINE_WHIP, THORNY_VINE, SNARE, SHARP_INTUITION);
				break;
			case RESEARCHER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, ALIVE_GRASS, DEW_MAKING, BIO_ENERGY, ROOT_PLANT, BIOLOGY_PROJECT, CHEMICAL);
				break;
			case WEAPONMASTER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, CLASH, MYSTICAL_POWER, ABSOLUTE_ZERO, EARTHQUAKE, SPEAR_N_SHIELD, UPGRADE_SHARE);
				break;
			case FORTRESS:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, IMPREGNABLE_WALL, COUNTER_MOMENTUM, SHIELD_SLAM, PREPARATION, FORTRESS, MYSTICAL_COUNTER);
				break;
			case CRUSADER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, NAME_OF_LIGHT, HOLY_SHIELD, DEADS_BLESS, BLESSING_SCROLLS, DIVINE_SHIELD, PROTECTION_OF_LIGHT);
				break;
			case MEDIC:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, PROMOTION, HEALING_SHIELD, HEAL_ENHANCE, COMP_RECOVER, IMMUNE_SYSTEM, HIGHER_HEAL);
				break;
			case ANGEL:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, APPEASE, ANGEL_AND_DEVIL, AREA_OF_LIGHT, BLESS_ENHANCE, PERSUASION, HOLY_PROTECTION);
				break;
			case SURGEON:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, SCALPEL, DEFIBRILLATOR, DEATH_DIAGNOSIS, FIRST_AID, DISINFECTION, HASTY_HANDS);
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
