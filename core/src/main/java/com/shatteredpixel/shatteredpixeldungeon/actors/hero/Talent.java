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

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;
import static com.shatteredpixel.shatteredpixeldungeon.items.Item.updateQuickslot;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AdrenalineSurge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArmorEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CertainCrit;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CounterBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CritBonus;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EnhancedRings;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ExtraBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.InfiniteBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LostInventory;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RevealedArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
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
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgunHP;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SPAS;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SPASAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SPASHP;
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
import com.shatteredpixel.shatteredpixeldungeon.messages.Languages;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sungrass;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public enum Talent {

	//Warrior T1
	HEARTY_MEAL(0), ARMSMASTERS_INTUITION(1), TEST_SUBJECT(2), IRON_WILL(3), MAX_HEALTH(4),
	//Warrior T2
	IRON_STOMACH(5), RESTORED_WILLPOWER(6), RUNIC_TRANSFERENCE(7), LETHAL_MOMENTUM(8), IMPROVISED_PROJECTILES(9), DESTRUCTIVE_ATK(10),
	//Warrior T3
	HOLD_FAST(11, 3), STRONGMAN(12, 3),
	//Berserker T3
	ENDLESS_RAGE(13, 3), BERSERKING_STAMINA(14, 3), ENRAGED_CATALYST(15, 3),
	//Gladiator T3
	CLEAVE(16, 3), LETHAL_DEFENSE(17, 3), ENHANCED_COMBO(18, 3),
	//Veteran T3
	ARM_VETERAN(19, 3), MARTIAL_ARTS(20, 3), ENHANCED_FOCUSING(21, 3),
	//Heroic Leap T4
	BODY_SLAM(22, 4), IMPACT_WAVE(23, 4), DOUBLE_JUMP(24, 4),
	//Shockwave T4
	EXPANDING_WAVE(25, 4), STRIKING_WAVE(26, 4), SHOCK_FORCE(27, 4),
	//Endure T4
	SUSTAINED_RETRIBUTION(28, 4), SHRUG_IT_OFF(29, 4), EVEN_THE_ODDS(30, 4),

	//Mage T1
	EMPOWERING_MEAL(32), SCHOLARS_INTUITION(33), TESTED_HYPOTHESIS(34), BACKUP_BARRIER(35), CHARGE_PRESERVE(36),
	//Mage T2
	ENERGIZING_MEAL(37), ENERGIZING_UPGRADE(38), WAND_PRESERVATION(39), ARCANE_VISION(40), SHIELD_BATTERY(41), FASTER_CHARGER(42),
	//Mage T3
	EMPOWERING_SCROLLS(43, 3), ALLY_WARP(44, 3),
	//Battlemage T3
	EMPOWERED_STRIKE(45, 3), MYSTICAL_CHARGE(46, 3), EXCESS_CHARGE(47, 3),
	//Warlock T3
	SOUL_EATER(48, 3), SOUL_SIPHON(49, 3), NECROMANCERS_MINIONS(50, 3),
	//Engineer T3
	AMMO_PRESERVE(51, 3), CONNECTING_CHARGER(52, 3), HIGH_VOLT(53, 3),
	//Elemental Blast T4
	BLAST_RADIUS(54, 4), ELEMENTAL_POWER(55, 4), REACTIVE_BARRIER(56, 4),
	//Wild Magic T4
	WILD_POWER(57, 4), FIRE_EVERYTHING(58, 4), CONSERVED_MAGIC(59, 4),
	//Warp Beacon T4
	TELEFRAG(60, 4), REMOTE_BEACON(61, 4), LONGRANGE_WARP(62, 4),

	//Rogue T1
	CACHED_RATIONS(64), THIEFS_INTUITION(65), SUCKER_PUNCH(66), PROTECTIVE_SHADOWS(67), EMERGENCY_ESCAPE(68),
	//Rogue T2
	MYSTICAL_MEAL(69), MYSTICAL_UPGRADE(70), WIDE_SEARCH(71), SILENT_STEPS(72), ROGUES_FORESIGHT(73), MOVESPEED_ENHANCE(74),
	//Rogue T3
	ENHANCED_RINGS(75, 3), LIGHT_CLOAK(76, 3),
	//Assassin T3
	ENHANCED_LETHALITY(77, 3), ASSASSINS_REACH(78, 3), BOUNTY_HUNTER(79, 3),
	//Freerunner T3
	EVASIVE_ARMOR(80, 3), PROJECTILE_MOMENTUM(81, 3), SPEEDY_STEALTH(82, 3),
	//Chaser T3
	INCISIVE_BLADE(83, 3), LETHAL_SURPRISE(84, 3), CHAIN_CLOCK(85, 3),
	//Smoke Bomb T4
	HASTY_RETREAT(86, 4), BODY_REPLACEMENT(87, 4), SHADOW_STEP(88, 4),
	//Death Mark T4
	FEAR_THE_REAPER(89, 4), DEATHLY_DURABILITY(90, 4), DOUBLE_MARK(91, 4),
	//Shadow Clone T4
	SHADOW_BLADE(92, 4), CLONED_ARMOR(93, 4), PERFECT_COPY(94, 4),

	//Huntress T1
	NATURES_BOUNTY(96), SURVIVALISTS_INTUITION(97), FOLLOWUP_STRIKE(98), NATURES_AID(99), WATER_FRIENDLY(100),
	//Huntress T2
	INVIGORATING_MEAL(101), RESTORED_NATURE(102), REJUVENATING_STEPS(103), HEIGHTENED_SENSES(104), DURABLE_PROJECTILES(105), ADDED_MEAL(106),
	//Huntress T3
	POINT_BLANK(107, 3), SEER_SHOT(108, 3),
	//Sniper T3
	FARSIGHT(109, 3), SHARED_ENCHANTMENT(110, 3), SHARED_UPGRADES(111, 3),
	//Warden T3
	DURABLE_TIPS(112, 3), BARKSKIN(113, 3), SHIELDING_DEW(114, 3),
	//Fighter T3
	SKILLS_PRACTICE(115, 3), MIND_PRACTICE(116, 3), VITAL_ATTACK(117, 3),
	//Spectral Blades T4
	FAN_OF_BLADES(118, 4), PROJECTING_BLADES(119, 4), SPIRIT_BLADES(120, 4),
	//Natures Power T4
	GROWING_POWER(121, 4), NATURES_WRATH(122, 4), WILD_MOMENTUM(123, 4),
	//Spirit Hawk T4
	EAGLE_EYE(124, 4), GO_FOR_THE_EYES(125, 4), SWIFT_SPIRIT(126, 4),

	//Gunner T1
	REARRANGE(128), GUNNERS_INTUITION(129), SPEEDY_MOVE(130), SAFE_RELOAD(131), MIND_VISION(132),
	//Gunner T2
	IN_THE_GUNFIRE(133), ANOTHER_CHANCE(134), BULLET_FOCUS(135), CAMOUFLAGE(136), LARGER_MAGAZINE(137), ELASTIC_WEAPON(138),
	//Gunner T3
	MELEE_ENHANCE(139, 3), HEAVY_ENHANCE(140, 3),
	//Launcher T3
	HEAVY_GUNNER(141, 3), ACC_PRACTICE(142, 3), RECOIL_PRACTICE(143, 3),
	//Ranger T3
	HANDGUN_MASTER(144, 3), RECOIL_CONTROL(145, 3), ELEMENTAL_BULLET(146, 3),
	//RifleMan T3
	RIFLE_MASTER(147, 3), ONLY_ONE_SHOT(148, 3), EVASIVE_MOVE(149, 3),
	//Riot T4
	HASTE_MOVE(150, 4), SHOT_CONCENTRATION(151,4), ROUND_PRESERVE(152, 4),
	//ReinforcedArmor T4
	BAYONET(153, 4), TACTICAL_SIGHT(154, 4), PLATE_ADD(155, 4),
	//FirstAidKit T4
	ADDITIONAL_MEDS(156,4), THERAPEUTIC_BANDAGE(157, 4), FASTER_HEALING(158,4),

	//Samurai T1
	CRITICAL_MEAL(160), MASTERS_INTUITION(161), UNEXPECTED_SLASH(162), FLOW_AWAY(163), ADRENALINE_SURGE(164),
	//Samurai T2
	FOCUSING_MEAL(165), CRITICAL_UPGRADE(166), MAGICAL_TRANSFERENCE(167), PARRY(168), DETECTION(169), POWERFUL_CRIT(170),
	//Samurai T3
	DEEP_SCAR(171, 3), FAST_LEAD(172, 3),
	//Slasher T3
	CONTINUOUS_ATTACK(173, 3), SLASHING_PRACTICE(174, 3), SERIAL_MOMENTUM(175, 3),
	//Master T3
	DONG_MIND_EYES(176, 3), JUNG_DETECTION(177, 3), MIND_FOCUS(178, 3),
	//Slayer T3
	AFTERIMAGE(179, 3), ENERGY_DRAIN(180, 3), FTL(181, 3),
	//Awake T4
	AWAKE_LIMIT(182, 4), AWAKE_DURATION(183, 4), INSURANCE(184, 4),
	//ShadowBlade T4
	DOUBLE_BLADE_PRACTICE(185, 4), CRITICAL_SHADOW(186, 4),HERBAL_SHADOW(187, 4),
	//Kunai T4
	KUNAI_OF_DOOM(188, 4), MYSTICAL_KUNAI(189, 4), POISONED_KUNAI(190, 4),

	//Planter T1
	FLOWER_BERRY(192), ADVENTURERS_INTUITION(193), SWING(194), NATURES_PROTECTION(195), SEED_EATING(196),
	//Planter T2
	NATURAL_MEAL(197), HERBAL_HEALING(198), SPROUT(199), VINE(200), MASS_PRODUCTION(201), NEUROTOXIN(202),
	//Planter T3
	BLOOMING_WEAPON(203, 3), FARMER(204, 3),
	//TreasureHunter T3
	TAKEDOWN(205, 3), DETECTOR(206, 3), GOLD_SHIELD(207, 3),
	//Adventurer T3
	JUNGLE_ADVENTURE(208, 3), SHADOW(209, 3), VINE_WHIP(210, 3),
	//Researcher T3
	SHOCK_DRAIN(211, 3), DEW_MAKING(212, 3), BIO_ENERGY(213, 3),
	//Sprout T4
	JUNGLE(214, 4), FOREST(215, 4), REGROWTH(216, 4),
	//TreasureMap T4
	LONG_LUCK(217, 4), FORESIGHT(218, 4), GOLD_HUNTER(219, 4),
	//Root T4
	POISONOUS_ROOT(220, 4), ROOT_SPREAD(221, 4), ROOT_ARMOR(222, 4),

	//Knight T1
	ON_ALERT(224), KNIGHTS_INTUITION(225), BATTLE_STIM(226), ACTIVE_BARRIER(227), WAR_CRY(228),
	//Knight T2
	IMPREGNABLE_MEAL(229), SAFE_HEALING(230), DEFENSE_STANCE(231), CROSS_SLASH(232), ENDURING(233), BLOCKING(234),
	//Knight T3
	CRAFTMANS_SKILLS(235, 3), TACKLE(236, 3),
	//Weaponmaster T3
	WEAPON_AUGMENT(237, 3), SWORD_N_SHIELD(238, 3), FIRE_WEAPON(239, 3),
	//Fortress T3
	IMPREGNABLE_WALL(240, 3), FIND_WEAKNESS(241, 3), SHIELD_SLAM(242, 3),
	//Crusader T3
	NAME_OF_LIGHT(243, 3), HOLY_SHIELD(244, 3), DEADS_BLESS(245, 3),
	//HolyShield T4
	BUFFER_BARRIER(246, 4), HOLY_LIGHT(247, 4), BLESS(248, 4),
	//StimPack T4
	BURDEN_RELIEF(249, 4), LASTING_PACK(250, 4), TIME_STOP(251, 4),
	//UnstableAnkh T4
	BLESSED_ANKH(252, 4), ANKH_ENHANCE(253, 4), COMPLETE_ANKH(254, 4),
	//universal T4
	HEROIC_ENERGY(31, 4), //See icon() and title() for special logic for this one
	//Ratmogrify T4
	RATSISTANCE(262, 4), RATLOMACY(263, 4), RATFORCEMENTS(264, 4),
	//universal T3
	ATK_SPEED_ENHANCE(256, 4), DEF_ENHANCE(257, 4), ACC_ENHANCE(258, 4), EVA_ENHANCE(259, 4), DEW_ENHANCE(260, 4),
	BETTER_CHOICE(261, 3);

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
			if (Dungeon.hero != null && Dungeon.hero.armorAbility instanceof Ratmogrify){
				return 233;
			}
			HeroClass cls = Dungeon.hero != null ? Dungeon.hero.heroClass : GamesInProgress.selectedClass;
			switch (cls){
				case WARRIOR: default:
					return 31;
				case MAGE:
					return 63;
				case ROGUE:
					return 95;
				case HUNTRESS:
					return 127;
				case GUNNER:
					return 159;
				case SAMURAI:
					return 191;
				case PLANTER:
					return 223;
				case KNIGHT:
					return 255;
			}
		} else {
			return icon;
		}
	}

	public int maxPoints(){
		return maxPoints;
	}

	public String title(){
		if (this == HEROIC_ENERGY
				&& Dungeon.hero != null
				&& Dungeon.hero.armorAbility instanceof Ratmogrify){
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
			Buff.affect( hero, ArtifactRecharge.class).set(1 + 2*(hero.pointsInTalent(MYSTICAL_MEAL))).ignoreHornOfPlenty = foodSource instanceof HornOfPlenty;
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
			Buff.prolong( hero, Bless.class, 1f + 2f*hero.pointsInTalent(FOCUSING_MEAL));
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
				||item instanceof SPAS
				||item instanceof SPASAP
				||item instanceof SPASHP
				||item instanceof MiniGun
				||item instanceof MiniGunAP
				||item instanceof MiniGunHP
				||item instanceof LargeHandgun
				||item instanceof LargeHandgunAP
				||item instanceof LargeHandgunHP
				||item instanceof AntimaterRifle
				||item instanceof AntimaterRifleAP
				||item instanceof AntimaterRifleHP
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
	}

	private static int upgradeUsed = 0;
	public static void onUpgradeScrollUsed( Hero hero ){
		if (hero.pointsInTalent(ANOTHER_CHANCE) == 2) {
			upgradeUsed ++;
		}
		if (hero.hasTalent(ENERGIZING_UPGRADE)){
			MagesStaff staff = hero.belongings.getItem(MagesStaff.class);
			if (staff != null){
				staff.gainCharge(1 + 2*hero.pointsInTalent(ENERGIZING_UPGRADE), true);
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
						||item instanceof SPAS
						||item instanceof SPASAP
						||item instanceof SPASHP
						||item instanceof MiniGun
						||item instanceof MiniGunAP
						||item instanceof MiniGunHP
						||item instanceof LargeHandgun
						||item instanceof LargeHandgunAP
						||item instanceof LargeHandgunHP
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
			Emitter e = hero.sprite.emitter();
			if (e != null) e.burst(Speck.factory(Speck.HEALING), hero.pointsInTalent(TEST_SUBJECT));
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
				Collections.addAll(tierTalents, MELEE_ENHANCE, HEAVY_ENHANCE);
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
				Collections.addAll(tierTalents, BETTER_CHOICE, ENDLESS_RAGE, BERSERKING_STAMINA, ENRAGED_CATALYST, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case GLADIATOR:
				Collections.addAll(tierTalents, BETTER_CHOICE, CLEAVE, LETHAL_DEFENSE, ENHANCED_COMBO, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case VETERAN:
				Collections.addAll(tierTalents, BETTER_CHOICE, ARM_VETERAN, MARTIAL_ARTS, ENHANCED_FOCUSING, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case BATTLEMAGE:
				Collections.addAll(tierTalents, BETTER_CHOICE, EMPOWERED_STRIKE, MYSTICAL_CHARGE, EXCESS_CHARGE, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case WARLOCK:
				Collections.addAll(tierTalents, BETTER_CHOICE, SOUL_EATER, SOUL_SIPHON, NECROMANCERS_MINIONS, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case ENGINEER:
				Collections.addAll(tierTalents, BETTER_CHOICE, AMMO_PRESERVE, CONNECTING_CHARGER, HIGH_VOLT, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case ASSASSIN:
				Collections.addAll(tierTalents, BETTER_CHOICE, ENHANCED_LETHALITY, ASSASSINS_REACH, BOUNTY_HUNTER, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case FREERUNNER:
				Collections.addAll(tierTalents, BETTER_CHOICE, EVASIVE_ARMOR, PROJECTILE_MOMENTUM, SPEEDY_STEALTH, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case CHASER:
				Collections.addAll(tierTalents, BETTER_CHOICE, INCISIVE_BLADE ,LETHAL_SURPRISE , CHAIN_CLOCK, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case SNIPER:
				Collections.addAll(tierTalents, BETTER_CHOICE, FARSIGHT, SHARED_ENCHANTMENT, SHARED_UPGRADES, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case WARDEN:
				Collections.addAll(tierTalents, BETTER_CHOICE, DURABLE_TIPS, BARKSKIN, SHIELDING_DEW, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case FIGHTER:
				Collections.addAll(tierTalents, BETTER_CHOICE, SKILLS_PRACTICE, MIND_PRACTICE, VITAL_ATTACK, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case LAUNCHER:
				Collections.addAll(tierTalents, BETTER_CHOICE, HEAVY_GUNNER, ACC_PRACTICE, RECOIL_PRACTICE, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case RANGER:
				Collections.addAll(tierTalents, BETTER_CHOICE, HANDGUN_MASTER, RECOIL_CONTROL, ELEMENTAL_BULLET, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case RIFLEMAN:
				Collections.addAll(tierTalents, BETTER_CHOICE, RIFLE_MASTER, ONLY_ONE_SHOT, EVASIVE_MOVE, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case SLASHER:
				Collections.addAll(tierTalents, BETTER_CHOICE, CONTINUOUS_ATTACK, SLASHING_PRACTICE, SERIAL_MOMENTUM, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case MASTER:
				Collections.addAll(tierTalents, BETTER_CHOICE, DONG_MIND_EYES, JUNG_DETECTION, MIND_FOCUS, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case SLAYER:
				Collections.addAll(tierTalents, BETTER_CHOICE, AFTERIMAGE, ENERGY_DRAIN, FTL, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case TREASUREHUNTER:
				Collections.addAll(tierTalents, BETTER_CHOICE, TAKEDOWN, DETECTOR, GOLD_SHIELD, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case ADVENTURER:
				Collections.addAll(tierTalents, BETTER_CHOICE, JUNGLE_ADVENTURE, SHADOW, VINE_WHIP, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case RESEARCHER:
				Collections.addAll(tierTalents, BETTER_CHOICE, SHOCK_DRAIN, DEW_MAKING, BIO_ENERGY, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case WEAPONMASTER:
				Collections.addAll(tierTalents, BETTER_CHOICE, WEAPON_AUGMENT, SWORD_N_SHIELD, FIRE_WEAPON, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case FORTRESS:
				Collections.addAll(tierTalents, BETTER_CHOICE, IMPREGNABLE_WALL, FIND_WEAKNESS, SHIELD_SLAM, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
				break;
			case CRUSADER:
				Collections.addAll(tierTalents, BETTER_CHOICE, NAME_OF_LIGHT, HOLY_SHIELD, DEADS_BLESS, ATK_SPEED_ENHANCE, DEF_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, DEW_ENHANCE);
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
			//pre-0.9.1 saves
			if (tierBundle == null && i == 0 && bundle.contains("talents")){
				tierBundle = bundle.getBundle("talents");
			}

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
