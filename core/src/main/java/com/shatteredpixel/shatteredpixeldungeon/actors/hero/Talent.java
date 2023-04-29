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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;
import static com.shatteredpixel.shatteredpixeldungeon.items.Item.updateQuickslot;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArmorEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CertainCrit;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CounterBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EnhancedRings;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ExtraBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.HealingArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.InfiniteBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LostInventory;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PhysicalEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RevealedArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sheathing;
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
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClothArmor;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gloves;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public enum Talent {

	//Warrior T1
	HEARTY_MEAL						(0),
	VETERANS_INTUITION				(1),
	TEST_SUBJECT					(2),
	IRON_WILL						(3),
	MAX_HEALTH						(4),
	//Warrior T2
	IRON_STOMACH					(5),
	RESTORED_WILLPOWER				(6),
	RUNIC_TRANSFERENCE				(7),
	LETHAL_MOMENTUM					(8),
	IMPROVISED_PROJECTILES			(9),
	PARRY							(10),
	//Warrior T3
	HOLD_FAST						(11, 3),
	STRONGMAN						(12, 3),
	//Berserker T3
	ENDLESS_RAGE					(13, 3),
	DEATHLESS_FURY					(14, 3),
	ENRAGED_CATALYST				(15, 3),
	LETHAL_RAGE						(16, 3),
	MAX_RAGE						(17, 3),
	ENDURANCE						(18, 3),
	//Gladiator T3
	CLEAVE							(19, 3),
	LETHAL_DEFENSE					(20, 3),
	ENHANCED_COMBO					(21, 3),
	QUICK_SWAP						(22, 3),
	OFFENSIVE_DEFENSE				(23, 3),
	SKILL_ENHANCE					(24, 3),
	//Veteran T3
	ARM_VETERAN						(25, 3),
	MARTIAL_ARTS					(26, 3),
	ENHANCED_FOCUSING				(27, 3),
	PUSHBACK						(28, 3),
	FOCUS_UPGRADE					(29, 3),
	BARRIER_FORMATION				(30, 3),
	//Heroic Leap T4
	BODY_SLAM						(31, 4),
	IMPACT_WAVE						(32, 4),
	DOUBLE_JUMP						(33, 4),
	//Shockwave T4
	EXPANDING_WAVE					(34, 4),
	STRIKING_WAVE					(35, 4),
	SHOCK_FORCE						(36, 4),
	//Endure T4
	SUSTAINED_RETRIBUTION			(37, 4),
	SHRUG_IT_OFF					(38, 4),
	EVEN_THE_ODDS					(39, 4),

	//Mage T1
	EMPOWERING_MEAL					(44),
	SCHOLARS_INTUITION				(45),
	TESTED_HYPOTHESIS				(46),
	BACKUP_BARRIER					(47),
	CHARGE_PRESERVE					(48),
	//Mage T2
	ENERGIZING_MEAL					(49),
	ENERGIZING_UPGRADE				(50),
	WAND_PRESERVATION				(51),
	ARCANE_VISION					(52),
	SHIELD_BATTERY					(53),
	FASTER_CHARGER					(54),
	//Mage T3
	EMPOWERING_SCROLLS				(55, 3),
	ALLY_WARP						(56, 3),
	//Battlemage T3
	EMPOWERED_STRIKE				(57, 3),
	MYSTICAL_CHARGE					(58, 3),
	EXCESS_CHARGE					(59, 3),
	BATTLE_MAGIC					(60, 3),
	MAGIC_RUSH						(61, 3),
	MAGICAL_CIRCLE					(62, 3),
	//Warlock T3
	SOUL_EATER						(63, 3),
	SOUL_SIPHON						(64, 3),
	NECROMANCERS_MINIONS			(65, 3),
	MADNESS							(66, 3),
	ENHANCED_MARK					(67, 3),
	MARK_OF_WEAKNESS				(68, 3),
	//Engineer T3
	AMMO_PRESERVE					(69, 3),
	CONNECTING_CHARGER				(70, 3),
	HIGH_VOLT						(71, 3),
	STATIC_ENERGY					(72, 3),
	BATTERY_CHARGE					(73, 3),
	ELECTRIC_BULLET					(74, 3),
	//Elemental Blast T4
	BLAST_RADIUS					(75, 4),
	ELEMENTAL_POWER					(76, 4),
	REACTIVE_BARRIER				(77, 4),
	//Wild Magic T4
	WILD_POWER						(78, 4),
	FIRE_EVERYTHING					(79, 4),
	CONSERVED_MAGIC					(80, 4),
	//Warp Beacon T4
	TELEFRAG						(81, 4),
	REMOTE_BEACON					(82, 4),
	LONGRANGE_WARP					(83, 4),

	//Rogue T1
	CACHED_RATIONS					(88),
	THIEFS_INTUITION				(89),
	SUCKER_PUNCH					(90),
	PROTECTIVE_SHADOWS				(91),
	EMERGENCY_ESCAPE				(92),
	//Rogue T2
	MYSTICAL_MEAL					(93),
	MYSTICAL_UPGRADE				(94),
	WIDE_SEARCH						(95),
	SILENT_STEPS					(96),
	ROGUES_FORESIGHT				(97),
	MOVESPEED_ENHANCE				(98),
	//Rogue T3
	ENHANCED_RINGS					(99, 3),
	LIGHT_CLOAK						(100, 3),
	//Assassin T3
	ENHANCED_LETHALITY				(101, 3),
	ASSASSINS_REACH					(102, 3),
	BOUNTY_HUNTER					(103, 3),
	ENERGY_DRAW						(104, 3),
	PERFECT_ASSASSIN				(105, 3),
	CAUTIOUS_PREP					(106, 3),
	//Freerunner T3
	EVASIVE_ARMOR					(107, 3),
	PROJECTILE_MOMENTUM				(108, 3),
	SPEEDY_STEALTH					(109, 3),
	QUICK_PREP						(110, 3),
	OVERCOMING						(111, 3),
	MOMENTARY_FOCUSING				(112, 3),
	//Chaser T3
	POISONOUS_BLADE					(113, 3),
	LETHAL_SURPRISE					(114, 3),
	CHAIN_CLOCK						(115, 3),
	SOUL_COLLECT					(116, 3),
	TRAIL_TRACKING					(117, 3),
	MASTER_OF_CLOAKING				(118, 3),
	//Smoke Bomb T4
	HASTY_RETREAT					(119, 4),
	BODY_REPLACEMENT				(120, 4),
	SHADOW_STEP						(121, 4),
	//Death Mark T4
	FEAR_THE_REAPER					(122, 4),
	DEATHLY_DURABILITY				(123, 4),
	DOUBLE_MARK						(124, 4),
	//Shadow Clone T4
	SHADOW_BLADE					(125, 4),
	CLONED_ARMOR					(126, 4),
	PERFECT_COPY					(127, 4),

	//Huntress T1
	NATURES_BOUNTY					(132),
	SURVIVALISTS_INTUITION			(133),
	FOLLOWUP_STRIKE					(134),
	NATURES_AID						(135),
	WATER_FRIENDLY					(136),
	//Huntress T2
	INVIGORATING_MEAL				(137),
	RESTORED_NATURE					(138),
	REJUVENATING_STEPS				(139),
	HEIGHTENED_SENSES				(140),
	DURABLE_PROJECTILES				(141),
	ADDED_MEAL						(142),
	//Huntress T3
	POINT_BLANK						(143, 3),
	SEER_SHOT						(144, 3),
	//Sniper T3
	FARSIGHT						(145, 3),
	SHARED_ENCHANTMENT				(146, 3),
	SHARED_UPGRADES					(147, 3),
	KICK							(148, 3),
	SHOOTING_EYES					(149, 3),
	TARGET_SPOTTING					(150, 3),
	//Warden T3
	DURABLE_TIPS					(151, 3),
	BARKSKIN						(152, 3),
	SHIELDING_DEW					(153, 3),
	DENSE_GRASS						(154, 3),
	ATTRACTION						(155, 3),
	WITHDRAW_TRAP					(156, 3),
	//Fighter T3
	SWIFT_MOVEMENT					(157, 3),
	LESS_RESIST						(158, 3),
	RING_KNUCKLE					(159, 3),
	MYSTICAL_PUNCH					(160, 3),
	QUICK_STEP						(161, 3),
	COUNTER_ATTACK					(162, 3),
	//Spectral Blades T4
	FAN_OF_BLADES					(163, 4),
	PROJECTING_BLADES				(164, 4),
	SPIRIT_BLADES					(165, 4),
	//Natures Power T4
	GROWING_POWER					(166, 4),
	NATURES_WRATH					(167, 4),
	WILD_MOMENTUM					(168, 4),
	//Spirit Hawk T4
	EAGLE_EYE						(169, 4),
	GO_FOR_THE_EYES					(170, 4),
	SWIFT_SPIRIT					(171, 4),

	//Duelist T1
	STRENGTHENING_MEAL				(176),
	ADVENTURERS_INTUITION			(177),
	PATIENT_STRIKE					(178),
	AGGRESSIVE_BARRIER				(179),
	SKILLED_HAND					(180),
	//Duelist T2
	FOCUSED_MEAL					(181),
	RESTORED_AGILITY				(182),
	WEAPON_RECHARGING				(183),
	LETHAL_HASTE					(184),
	SWIFT_EQUIP						(185),
	ACCUMULATION					(186),
	//Duelist T3
	LIGHTWEIGHT_CHARGE				(187, 3),
	DEADLY_FOLLOWUP					(188, 3),
	//Champion T3
	SECONDARY_CHARGE				(189, 3),
	TWIN_UPGRADES					(190, 3),
	COMBINED_LETHALITY				(191, 3),
	FASTER_CHARGE					(192, 3),
	QUICK_FOLLOWUP					(193, 3),
	MOMENTARY_UPGRADE				(194, 3),
	//Monk T3
	UNENCUMBERED_SPIRIT				(195, 3),
	MONASTIC_VIGOR					(196, 3),
	COMBINED_ENERGY					(197, 3),
	RESTORED_ENERGY					(198, 3),
	ENERGY_BARRIER					(199, 3),
	IRON_PUNCH						(200, 3),
	///Fencer T3
	CLAM_STEPS						(201, 3),
	CRITICAL_MOMENTUM				(202, 3),
	KINETIC_MOVEMENT				(203, 3),
	AGGRESSIVE_MOVEMENT				(204, 3),
	UNENCUMBERED_MOVEMENT			(205, 3),
	SOULIZE							(206, 3),
	//Challenge T4
	CLOSE_THE_GAP					(207, 4),
	INVIGORATING_VICTORY			(208, 4),
	ELIMINATION_MATCH				(209, 4),
	//Elemental Strike T4
	ELEMENTAL_REACH					(210, 4),
	STRIKING_FORCE					(211, 4),
	DIRECTED_POWER					(212, 4),
	//Feint T4
	FEIGNED_RETREAT					(213, 4),
	EXPOSE_WEAKNESS					(214, 4),
	COUNTER_ABILITY					(215, 4),

	//??? T1
	//???							(220),
	//???							(221),
	//???							(222),
	//???							(223),
	//???							(224),
	//??? T2
	//???							(225),
	//???							(226),
	//???							(227),
	//???							(228),
	//???							(229),
	//???							(230),
	//??? T3
	//???							(231, 3),
	//???							(232, 3),
	//??? T3
	//???							(233, 3),
	//???							(234, 3),
	//???							(235, 3),
	//???							(236, 3),
	//???							(237, 3),
	//???							(238, 3),
	//??? T3
	//???							(239, 3),
	//???							(240, 3),
	//???							(241, 3),
	//???							(242, 3),
	//???							(243, 3),
	//???							(244, 3),
	//??? T3
	//???							(245, 3),
	//???							(246, 3),
	//???							(247, 3),
	//???							(248, 3),
	//???							(249, 3),
	//???							(250, 3),
	//??? T4
	//???							(251, 4),
	//???							(252, 4),
	//???							(253, 4),
	//??? T4
	//???							(254, 4),
	//???							(255, 4),
	//???							(256, 4),
	//??? T4
	//???							(257, 4),
	//???							(258, 4),
	//???							(259, 4),

	//Gunner T1
	REARRANGE						(264),
	GUNNERS_INTUITION				(265),
	SPEEDY_MOVE						(266),
	SAFE_RELOAD						(267),
	MIND_VISION						(268),
	//Gunner T2
	IN_THE_GUNFIRE					(269),
	ANOTHER_CHANCE					(270),
	BULLET_FOCUS					(271),
	CAMOUFLAGE						(272),
	LARGER_MAGAZINE					(273),
	TRANSMUTATION_CONTROL			(274),
	//Gunner T3
	STREET_BATTLE					(275, 3),
	FAST_RELOAD						(276, 3),
	//Marshal T3
	JUSTICE_BULLET					(277, 3),
	INTIMIDATION					(278, 3),
	SEARCH							(279, 3),
	COVER							(280, 3),
	SURRENDER						(281, 3),
	INVEST_END						(282, 3),
	//Gunslinger T3
	QUICK_RELOAD					(283, 3),
	MOVING_SHOT						(284, 3),
	ELEMENTAL_BULLET				(285, 3),
	MYSTICAL_THROW					(286, 3),
	SOUL_BULLET						(287, 3),
	LIGHT_MOVEMENT					(288, 3),
	//RifleMan T3
	SILENCER						(289, 3),
	SKILLFUL_RUNNER					(290, 3),
	STEALTH							(291, 3),
	INTO_THE_SHADOW					(292, 3),
	RANGED_SNIPING					(293, 3),
	TELESCOPE						(294, 3),
	//Riot T4
	HASTE_MOVE						(295, 4),
	SHOT_CONCENTRATION				(296, 4),
	ROUND_PRESERVE					(297, 4),
	//ReinforcedArmor T4
	BAYONET							(298, 4),
	TACTICAL_SIGHT					(299, 4),
	PLATE_ADD						(300, 4),
	//FirstAidKit T4
	ADDITIONAL_MEDS					(301,4),
	THERAPEUTIC_BANDAGE				(302, 4),
	FASTER_HEALING					(303,4),

	//Samurai T1
	SURPRISE_STAB					(308),
	MASTERS_INTUITION				(309),
	UNEXPECTED_SLASH				(310),
	FLOW_AWAY						(311),
	ADRENALINE_SURGE				(312),
	//Samurai T2
	FOCUSING_MEAL					(313),
	CRITICAL_UPGRADE				(314),
	MAGICAL_TRANSFERENCE			(315),
	EYE_OF_DRAGON					(316),
	DETECTION						(317),
	CRITICAL_THROW					(318),
	//Samurai T3
	DEEP_SCAR						(319, 3),
	FAST_LEAD						(320, 3),
	//Slasher T3
	CONTINUOUS_ATTACK				(321, 3),
	SLASHING_PRACTICE				(322, 3),
	SERIAL_MOMENTUM					(323, 3),
	ARCANE_ATTACK					(324, 3),
	SLASHING						(325, 3),
	DETECTIVE_SLASHING				(326, 3),
	//Master T3
	DONG_MIND_EYES					(327, 3),
	DONG_SHEATHING					(328, 3),
	DONG_POLISHING					(329, 3),
	JUNG_DETECTION					(330, 3),
	JUNG_QUICK_DRAW					(331, 3),
	JUNG_INCISIVE_BLADE				(332, 3),
	//Slayer T3
	AFTERIMAGE						(333, 3),
	ENERGY_DRAIN					(334, 3),
	FTL								(335, 3),
	QUICK_RECOVER					(336, 3),
	HASTE_RECOVER					(337, 3),
	FLURRY							(338, 3),
	//Awake T4
	AWAKE_LIMIT						(339, 4),
	AWAKE_DURATION					(340, 4),
	INSURANCE						(341, 4),
	//ShadowBlade T4
	DOUBLE_BLADE_PRACTICE			(342, 4),
	CRITICAL_SHADOW					(343, 4),
	HERBAL_SHADOW					(344, 4),
	//Kunai T4
	KUNAI_OF_DOOM					(345, 4),
	MYSTICAL_KUNAI					(346, 4),
	CORROSIVE_KUNAI					(347, 4),

	//Planter T1
	SUDDEN_GROWTH					(352),
	SAFE_POTION						(353),
	ROOT							(354),
	PLANT_SHIELD					(355),
	KNOWLEDGE_HERB					(356),
	//Planter T2
	NATURAL_MEAL					(357),
	HERBAL_DEW						(358),
	SPROUT							(359),
	FIREWATCH						(360),
	FLOWER_BED						(361),
	WEAK_POISON						(362),
	//Planter T3
	BLOOMING_WEAPON					(363, 3),
	FARMER							(364, 3),
	//TreasureHunter T3
	TAKEDOWN						(365, 3),
	DETECTOR						(366, 3),
	GOLD_SHIELD						(367, 3),
	GOLD_MINER						(368, 3),
	FINDING_TREASURE				(369, 3),
	HIDDEN_STASH					(370, 3),
	//Adventurer T3
	JUNGLE_ADVENTURE				(371, 3),
	SHADOW							(372, 3),
	VINE_WHIP						(373, 3),
	THORNY_VINE						(374, 3),
	SNARE							(375, 3),
	SHARP_INTUITION					(376, 3),
	//Researcher T3
	ALIVE_GRASS						(377, 3),
	DEW_MAKING						(378, 3),
	BIO_ENERGY						(379, 3),
	ROOT_PLANT						(380, 3),
	BIOLOGY_PROJECT					(381, 3),
	CHEMICAL						(382, 3),
	//Sprout T4
	JUNGLE							(383, 4),
	FOREST							(384, 4),
	REGROWTH						(385, 4),
	//TreasureMap T4
	LONG_LUCK						(386, 4),
	FORESIGHT						(387, 4),
	GOLD_HUNTER						(388, 4),
	//Root T4
	POISONOUS_ROOT					(389, 4),
	ROOT_SPREAD						(390, 4),
	ROOT_ARMOR						(391, 4),

	//Knight T1
	ON_ALERT						(396),
	KNIGHTS_INTUITION				(397),
	ARMOR_ENHANCE					(398),
	ACTIVE_BARRIER					(399),
	WAR_CRY							(400),
	//Knight T2
	IMPREGNABLE_MEAL				(401),
	SAFE_HEALING					(402),
	DEFENSE_STANCE					(403),
	FAITH							(404),
	CHIVALRY						(405),
	ARMOR_ADAPTION					(406),
	//Knight T3
	CRAFTMANS_SKILLS				(407, 3),
	TACKLE							(408, 3),
	//Weaponmaster T3
	CLASH							(409, 3),
	MYSTICAL_POWER					(410, 3),
	ABSOLUTE_ZERO					(411, 3),
	EARTHQUAKE						(412, 3),
	SPEAR_N_SHIELD					(413, 3),
	UPGRADE_SHARE					(414, 3),
	//Fortress T3
	IMPREGNABLE_WALL				(415, 3),
	COUNTER_MOMENTUM				(416, 3),
	SHIELD_SLAM						(417, 3),
	PREPARATION						(418, 3),
	FORTRESS						(419, 3),
	MYSTICAL_COUNTER				(420, 3),
	//Crusader T3
	DIVINE_SHIELD					(421, 3),
	DEADS_BLESS						(422, 3),
	ARMOR_BLESSING					(423, 3),
	BLESSED_TALENT					(424, 3),
	MYSTICAL_VEIL					(425, 3),
	SHIELD_OF_LIGHT					(426, 3),
	//HolyShield T4
	BUFFER_BARRIER					(427, 4),
	HOLY_LIGHT						(428, 4),
	BLESS							(429, 4),
	//StimPack T4
	BURDEN_RELIEF					(430, 4),
	LASTING_PACK					(431, 4),
	TIME_STOP						(432, 4),
	//UnstableAnkh T4
	BLESSED_ANKH					(433, 4),
	ANKH_ENHANCE					(434, 4),
	COMPLETE_ANKH					(435, 4),

	//Nurse T1
	HEALING_MEAL					(440),
	DOCTORS_INTUITION				(441),
	INNER_MIRROR					(442),
	CRITICAL_SHIELD					(443),
	HEAL_AMP						(444),
	//Nurse T2
	CHALLENGING_MEAL				(445),
	POTION_SPREAD					(446),
	HEALAREA						(447),
	ANGEL							(448),
	MEDICAL_SUPPORT					(449),
	WINNERS_FLAG					(450),
	//Nurse T3
	POWERFUL_BOND					(451, 3),
	CHARISMA						(452, 3),
	//Medic T3
	PROMOTION						(453, 3),
	HEALING_SHIELD					(454, 3),
	HEAL_ENHANCE					(455, 3),
	COMP_RECOVER					(456, 3),
	IMMUNE_SYSTEM					(457, 3),
	HIGHER_HEAL						(458, 3),
	//Angel T3
	APPEASE							(459, 3),
	ANGEL_AND_DEVIL					(460, 3),
	AREA_OF_LIGHT					(461, 3),
	BLESS_ENHANCE					(462, 3),
	PERSUASION						(463, 3),
	HOLY_PROTECTION					(464, 3),
	//Surgeon T3
	SCALPEL							(465, 3),
	DEFIBRILLATOR					(466, 3),
	DEATH_DIAGNOSIS					(467, 3),
	FIRST_AID						(468, 3),
	DISINFECTION					(469, 3),
	HASTY_HANDS						(470, 3),
	//HealGenerator T4
	AREA_AMP						(471, 4),
	SHIELD_GEN						(472, 4),
	DURABLE_GEN						(473, 4),
	//AngelWing T4
	LIGHT_LIKE_FEATHER				(474, 4),
	ANGELS_BLESS					(475, 4),
	HEALING_WING					(476, 4),
	//GammaRayEmmit T4
	TRANSMOG_BIAS					(477, 4),
	IMPRINTING_EFFECT				(478, 4),
	SHEEP_TRANSMOG					(479, 4),

	//universal T4
	HEROIC_ENERGY					(43, 4), //See icon() and title() for special logic for this one
	//Ratmogrify T4
	RATSISTANCE						(568, 4),
	RATLOMACY						(569, 4),
	RATFORCEMENTS					(570, 4),
	//universal T3
	ATK_SPEED_ENHANCE				(528, 4),
	ACC_ENHANCE						(529, 4),
	EVA_ENHANCE						(530, 4),
	BETTER_CHOICE					(531, 3);

	public static class ImprovisedProjectileCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.15f, 0.2f, 0.5f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 50); }
	};
	public static class LethalMomentumTracker extends FlavourBuff{};
	public static class StrikingWaveTracker extends FlavourBuff{};
	public static class WandPreservationCounter extends CounterBuff{{revivePersists = true;}};
	public static class EmpoweredStrikeTracker extends FlavourBuff{};

	public static class SkilledHandTracker extends Buff{};
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
	public static class DetactiveSlashingTracker extends Buff{};
	public static class IncisiveBladeTracker extends Buff{};
	public static class ActiveBarrierTracker extends Buff{};
	public static class SurpriseStabTracker extends Buff{};
	public static class JungQuickDrawTracker extends Buff{};
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
	public static class SkillfulRunnerCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight( 0xFFFF00 ); }
		public float iconFadePercent() { return Math.max(0, 1-visualcooldown() / 30); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class IntoTheShadowCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight( 0x7FA9D2 ); }
		public float iconFadePercent() { return Math.max(0, 1-visualcooldown() / 15); }
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
	public static class QuickFollowupCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0x651F66); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 10); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class QuickFollowupTracker extends FlavourBuff{};

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
	public static class FlowAwayCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0xF27318); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / (70 - 20 * hero.pointsInTalent(Talent.FLOW_AWAY))); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class PatientStrikeTracker extends FlavourBuff{};
	public static class AggressiveBarrierCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.35f, 0f, 0.7f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 50); }
	};;
	public static class RestoredAgilityTracker extends FlavourBuff{};
	public static class LethalHasteCooldown extends FlavourBuff{
		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.35f, 0f, 0.7f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 100); }
	};
	public static class SwiftEquipCooldown extends FlavourBuff{
		public boolean secondUse;
		public boolean hasSecondUse(){
			return secondUse && cooldown() > 14f;
		}

		public int icon() { return BuffIndicator.TIME; }
		public void tintIcon(Image icon) {
			if (hasSecondUse()) icon.hardlight(0.85f, 0f, 1.0f);
			else                icon.hardlight(0.35f, 0f, 0.7f);
		}
		public float iconFadePercent() { return GameMath.gate(0, visualcooldown() / 20f, 1); }

		private static final String SECOND_USE = "second_use";
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(SECOND_USE, secondUse);
		}
		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			secondUse = bundle.getBoolean(SECOND_USE);
		}
	};
	public static class DeadlyFollowupTracker extends FlavourBuff{};
	public static class CombinedLethalityAbilityTracker extends FlavourBuff{
		public MeleeWeapon weapon;
	};
	public static class CombinedLethalityTriggerTracker extends FlavourBuff{
		{ type = buffType.POSITIVE; }
		public int icon() { return BuffIndicator.CORRUPT; }
		public void tintIcon(Image icon) { icon.hardlight(0.6f, 0.15f, 0.6f); }
	};
	public static class CombinedEnergyAbilityTracker extends FlavourBuff{
		public int energySpent = -1;
		public boolean wepAbilUsed = false;
	}
	public static class AgressiveMovementAbilityTracker extends FlavourBuff{
		public boolean wepAbilUsed = false;
	}
	public static class CounterAbilityTacker extends FlavourBuff{};

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
				return 571;
			}
			HeroClass cls = Dungeon.hero != null ? Dungeon.hero.heroClass : GamesInProgress.selectedClass;
			switch (cls){
				case WARRIOR: default:
					return 43;
				case MAGE:
					return 87;
				case ROGUE:
					return 131;
				case HUNTRESS:
					return 175;
				case DUELIST:
					return 219;
				//	case ???:
				//		return 263;
				case GUNNER:
					return 307;
				case SAMURAI:
					return 351;
				case PLANTER:
					return 395;
				case KNIGHT:
					return 439;
				case NURSE:
					return 483;
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
		if (talent == PARRY) {
			Buff.affect(hero, ParryTracker.class);
		}
		if (talent == NATURES_BOUNTY){
			if ( hero.pointsInTalent(NATURES_BOUNTY) == 1) Buff.count(hero, NatureBerriesAvailable.class, 4);
			else                                           Buff.count(hero, NatureBerriesAvailable.class, 2);
		}
		if (talent == ACCUMULATION) {
			updateQuickslot();
		}
		if (talent == LARGER_MAGAZINE) {
			updateQuickslot();
		}

		if (talent == VETERANS_INTUITION && hero.pointsInTalent(VETERANS_INTUITION) == 2){
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
			if (hero.belongings.weapon != null && hero.belongings.weapon.gun)
				hero.belongings.weapon.identify();
		}
		if (talent == MASTERS_INTUITION && hero.pointsInTalent(MASTERS_INTUITION) == 1){
			if (hero.belongings.weapon() != null) hero.belongings.weapon().identify();
		}
		if (talent == KNIGHTS_INTUITION && hero.pointsInTalent(KNIGHTS_INTUITION) == 2){
			if (hero.belongings.armor() != null)  hero.belongings.armor.identify();
		}
		if (talent == ADVENTURERS_INTUITION && hero.pointsInTalent(ADVENTURERS_INTUITION) == 2){
			if (hero.belongings.weapon() != null) hero.belongings.weapon().identify();
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

		if (talent == COMP_RECOVER && hero.buff(HealingArea.class) != null) {
			hero.buff(HealingArea.class).attachTo(hero);
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
			for (int i = 0; i < 3; i++) {
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
		if (talent == SECONDARY_CHARGE || talent == TWIN_UPGRADES){
			Item.updateQuickslot();
		}

		if (talent == UNENCUMBERED_SPIRIT && hero.pointsInTalent(talent) == 3){
			Item toGive = new ClothArmor().identify();
			if (!toGive.collect()){
				Dungeon.level.drop(toGive, hero.pos).sprite.drop();
			}
			toGive = new Gloves().identify();
			if (!toGive.collect()){
				Dungeon.level.drop(toGive, hero.pos).sprite.drop();
			}
		}
	}
	public static class CachedRationsDropped extends CounterBuff{{revivePersists = true;}};
	public static class NatureBerriesAvailable extends CounterBuff{{revivePersists = true;}}; //for pre-1.3.0 saves
	public static class NatureBerriesDropped extends CounterBuff{{revivePersists = true;}};

	public static void onFoodEaten( Hero hero, float foodVal, Item foodSource ) {
		if (hero.hasTalent(HEARTY_MEAL)) {
			//3/5 HP healed, when hero is below 25% health
			if (hero.HP <= hero.HT / 4) {
				hero.HP = Math.min(hero.HP + 1 + 2 * hero.pointsInTalent(HEARTY_MEAL), hero.HT);
				hero.sprite.emitter().burst(Speck.factory(Speck.HEALING), 1 + hero.pointsInTalent(HEARTY_MEAL));
				//2/3 HP healed, when hero is below 50% health
			} else if (hero.HP <= hero.HT / 2) {
				hero.HP = Math.min(hero.HP + 1 + hero.pointsInTalent(HEARTY_MEAL), hero.HT);
				hero.sprite.emitter().burst(Speck.factory(Speck.HEALING), hero.pointsInTalent(HEARTY_MEAL));
			}
		}
		if (hero.hasTalent(IRON_STOMACH)) {
			if (hero.cooldown() > 0) {
				Buff.affect(hero, WarriorFoodImmunity.class, hero.cooldown());
			}
		}
		if (hero.hasTalent(EMPOWERING_MEAL)) {
			//2/3 bonus wand damage for next 3 zaps
			Buff.affect(hero, WandEmpower.class).set(1 + hero.pointsInTalent(EMPOWERING_MEAL), 3);
			ScrollOfRecharging.charge(hero);
		}
		if (hero.hasTalent(ENERGIZING_MEAL)) {
			//5/8 turns of recharging
			Buff.prolong(hero, Recharging.class, 2 + 3 * (hero.pointsInTalent(ENERGIZING_MEAL)));
			ScrollOfRecharging.charge(hero);
			SpellSprite.show(hero, SpellSprite.CHARGE);
		}
		if (hero.hasTalent(MYSTICAL_MEAL)) {
			//3/5 turns of recharging
			ArtifactRecharge buff = Buff.affect(hero, ArtifactRecharge.class);
			if (buff.left() < 1 + 2 * (hero.pointsInTalent(MYSTICAL_MEAL))) {
				Buff.affect(hero, ArtifactRecharge.class).set(1 + 2 * (hero.pointsInTalent(MYSTICAL_MEAL))).ignoreHornOfPlenty = foodSource instanceof HornOfPlenty;
			}
			ScrollOfRecharging.charge(hero);
			SpellSprite.show(hero, SpellSprite.CHARGE, 0, 1, 1);
		}
		if (hero.hasTalent(INVIGORATING_MEAL)) {
			//effectively 1/2 turns of haste
			Buff.prolong(hero, Haste.class, 0.67f + hero.pointsInTalent(INVIGORATING_MEAL));
		}
		if (hero.hasTalent(REARRANGE)) {
			//effectively 5/10 turns of ExtraBullet
			Buff.prolong(hero, ExtraBullet.class, 5f * hero.pointsInTalent(REARRANGE));
		}
		if (hero.hasTalent(IN_THE_GUNFIRE)) {
			//effectively 1/2 turns of infiniteBullet
			Buff.prolong(hero, InfiniteBullet.class, 0.001f + hero.pointsInTalent(IN_THE_GUNFIRE));
		}
		if (hero.hasTalent(Talent.FOCUSING_MEAL)) {
			Buff.prolong(hero, Adrenaline.class, 1f + 2f * hero.pointsInTalent(FOCUSING_MEAL));
		}
		if (hero.hasTalent(Talent.NATURAL_MEAL)) {
			if (hero.pointsInTalent(Talent.NATURAL_MEAL) == 1) {
				for (int i : PathFinder.NEIGHBOURS4) {
					int c = Dungeon.level.map[hero.pos + i];
					if (c == Terrain.EMPTY || c == Terrain.EMPTY_DECO
							|| c == Terrain.EMBERS || c == Terrain.GRASS) {
						Level.set(hero.pos + i, Terrain.HIGH_GRASS);
						GameScene.updateMap(hero.pos + i);
						CellEmitter.get(hero.pos + i).burst(LeafParticle.LEVEL_SPECIFIC, 4);
					}
				}
				;
			} else {
				for (int i : PathFinder.NEIGHBOURS8) {
					int c = Dungeon.level.map[hero.pos + i];
					if (c == Terrain.EMPTY || c == Terrain.EMPTY_DECO
							|| c == Terrain.EMBERS || c == Terrain.GRASS) {
						Level.set(hero.pos + i, Terrain.HIGH_GRASS);
						GameScene.updateMap(hero.pos + i);
						CellEmitter.get(hero.pos + i).burst(LeafParticle.LEVEL_SPECIFIC, 4);
					}
				}
				;
			}

		}
		if (hero.hasTalent(Talent.IMPREGNABLE_MEAL)) {
			Buff.affect(hero, ArmorEmpower.class).set(hero.pointsInTalent(Talent.IMPREGNABLE_MEAL), 10f);
		}
		if (hero.hasTalent(Talent.HEALING_MEAL)) {
			Buff.affect(hero, HealingArea.class).setup(hero.pos, 10 * hero.pointsInTalent(Talent.HEALING_MEAL), 1, true);
		}
		if (hero.hasTalent(Talent.CHALLENGING_MEAL)) {
			Buff.affect(hero, ScrollOfChallenge.ChallengeArena.class).setup(hero.pos, 10 * hero.pointsInTalent(Talent.CHALLENGING_MEAL));
		}
		if (hero.hasTalent(STRENGTHENING_MEAL)){
			//3 bonus physical damage for next 2/3 attacks
			Buff.affect( hero, PhysicalEmpower.class).set(3, 1 + hero.pointsInTalent(STRENGTHENING_MEAL));
		}
		if (hero.hasTalent(FOCUSED_MEAL)) {
			if (hero.heroClass == HeroClass.DUELIST) {
				//1/1.5 charge for the duelist
				Buff.affect(hero, MeleeWeapon.Charger.class).gainCharge(0.5f * (hero.pointsInTalent(FOCUSED_MEAL) + 1));
			} else {
				// lvl/3 / lvl/2 bonus dmg on next hit for other classes
				Buff.affect(hero, PhysicalEmpower.class).set(Math.round(hero.lvl / (4f - hero.pointsInTalent(FOCUSED_MEAL))), 1);
			}
		}
	}

	public static class WarriorFoodImmunity extends FlavourBuff{
		{ actPriority = HERO_PRIO+1; }
	}

	public static float itemIDSpeedFactor( Hero hero, Item item ){
		// 1.75x/2.5x speed with Huntress talent
		float factor = 1f + 0.75f*hero.pointsInTalent(SURVIVALISTS_INTUITION);

		// Affected by both Warrior(1.75x/2.5x) and Duelist(2.5x/inst.) talents
		if (item instanceof MeleeWeapon){
			factor *= 1f + 1.5f*hero.pointsInTalent(ADVENTURERS_INTUITION); //instant at +2 (see onItemEquipped)
			factor *= 1f + 0.75f*hero.pointsInTalent(VETERANS_INTUITION);
		}
		// Affected by both Warrior(2.5x/inst.) and Duelist(1.75x/2.5x) talents
		if (item instanceof Armor){
			factor *= 1f + 0.75f*hero.pointsInTalent(ADVENTURERS_INTUITION);
			factor *= 1f + hero.pointsInTalent(VETERANS_INTUITION); //instant at +2 (see onItemEquipped)
		}
		// 3x/instant for Mage (see Wand.wandUsed())
		if (item instanceof Wand){
			factor *= 1f + 2.0f*hero.pointsInTalent(SCHOLARS_INTUITION);
		}
		// 2x/instant for Rogue (see onItemEqupped), also id's type on equip/on pickup
		if (item instanceof Ring){
			factor *= 1f + hero.pointsInTalent(THIEFS_INTUITION);
		}
		return factor;
	}

	public static void onHealingPotionUsed( Hero hero ) {
		if (hero.hasTalent(RESTORED_WILLPOWER)) {
			if (hero.heroClass == HeroClass.WARRIOR) {
				BrokenSeal.WarriorShield shield = hero.buff(BrokenSeal.WarriorShield.class);
				if (shield != null) {
					int shieldToGive = Math.round(shield.maxShield() * 0.33f * (1 + hero.pointsInTalent(RESTORED_WILLPOWER)));
					shield.supercharge(shieldToGive);
				}
			} else {
				int shieldToGive = Math.round(hero.HT * (0.025f * (1 + hero.pointsInTalent(RESTORED_WILLPOWER))));
				Buff.affect(hero, Barrier.class).setShield(shieldToGive);
			}
		}
		if (hero.hasTalent(RESTORED_NATURE)) {
			ArrayList<Integer> grassCells = new ArrayList<>();
			for (int i : PathFinder.NEIGHBOURS8) {
				grassCells.add(hero.pos + i);
			}
			Random.shuffle(grassCells);
			for (int cell : grassCells) {
				Char ch = Actor.findChar(cell);
				if (ch != null && ch.alignment == Char.Alignment.ENEMY) {
					Buff.affect(ch, Roots.class, 1f + hero.pointsInTalent(RESTORED_NATURE));
				}
				if (Dungeon.level.map[cell] == Terrain.EMPTY ||
						Dungeon.level.map[cell] == Terrain.EMBERS ||
						Dungeon.level.map[cell] == Terrain.EMPTY_DECO) {
					Level.set(cell, Terrain.GRASS);
					GameScene.updateMap(cell);
				}
				CellEmitter.get(cell).burst(LeafParticle.LEVEL_SPECIFIC, 4);
			}
			if (hero.pointsInTalent(RESTORED_NATURE) == 1) {
				grassCells.remove(0);
				grassCells.remove(0);
				grassCells.remove(0);
			}
			for (int cell : grassCells) {
				int t = Dungeon.level.map[cell];
				if ((t == Terrain.EMPTY || t == Terrain.EMPTY_DECO || t == Terrain.EMBERS
						|| t == Terrain.GRASS || t == Terrain.FURROWED_GRASS)
						&& Dungeon.level.plants.get(cell) == null) {
					Level.set(cell, Terrain.HIGH_GRASS);
					GameScene.updateMap(cell);
				}
			}
			Dungeon.observe();
		}
		if (hero.hasTalent(Talent.SAFE_HEALING)) {
			Buff.affect(hero, Barrier.class).setShield(10 * hero.pointsInTalent(Talent.SAFE_HEALING));
		}
		if (hero.hasTalent(Talent.POTION_SPREAD) && !Dungeon.isChallenged(Challenges.NO_HEALING)) {
			Buff.affect(hero, HealingArea.class).setup(hero.pos, Math.round(((0.8f * hero.HT + 14) / 3) * (1 + hero.pointsInTalent(Talent.POTION_SPREAD))), 2, true);
			if (hero.hasTalent(RESTORED_AGILITY)) {
				Buff.prolong(hero, RestoredAgilityTracker.class, hero.cooldown());
			}
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
		if (hero.pointsInTalent(VETERANS_INTUITION) == 2 && item instanceof Armor){
			item.identify();
		}
		if (hero.hasTalent(THIEFS_INTUITION) && item instanceof Ring){
			if (hero.pointsInTalent(THIEFS_INTUITION) == 2){
				item.identify();
			} else {
				((Ring) item).setKnown();
			}
		}
		if (hero.hasTalent(GUNNERS_INTUITION) && item instanceof Weapon) {
			if (((Weapon) item).gun) {
				item.identify();
			}
		}
		if (hero.hasTalent(MASTERS_INTUITION) && item instanceof Weapon) {
			item.identify();
		}
		if (hero.hasTalent(KNIGHTS_INTUITION) && (item instanceof Armor)) {
			item.identify();
		}
		if (hero.pointsInTalent(ADVENTURERS_INTUITION) == 2 && item instanceof Weapon){
			item.identify();
		}
	}

	public static void onItemCollected( Hero hero, Item item ){
		if (hero.pointsInTalent(GUNNERS_INTUITION) == 2 && item instanceof Weapon){
			if (((Weapon) item).gun) item.identify();
		}
		if (hero.pointsInTalent(THIEFS_INTUITION) == 2){
			if (item instanceof Ring) ((Ring) item).setKnown();
		}
		if (hero.pointsInTalent(MASTERS_INTUITION) == 2) {
			if (item instanceof Weapon) {
				item.identify();
			}
		}
		if (hero.pointsInTalent(KNIGHTS_INTUITION) == 2) {
			if (item instanceof Armor) {
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

		if (hero.hasTalent(Talent.POISONOUS_BLADE)
				&& enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)
		) {
			Buff.affect(enemy, Poison.class).set(2+hero.pointsInTalent(Talent.POISONOUS_BLADE));
		}

		if (hero.hasTalent(Talent.FOLLOWUP_STRIKE)) {
			if (hero.belongings.attackingWeapon() instanceof MissileWeapon) {
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

		if (hero.hasTalent(Talent.UNEXPECTED_SLASH) && hero.buff(Sheathing.class) != null){
			dmg += hero.pointsInTalent(Talent.UNEXPECTED_SLASH);
		}

		if (hero.hasTalent(Talent.UNEXPECTED_SLASH) && hero.heroClass != HeroClass.SAMURAI) {
			dmg += hero.pointsInTalent(Talent.UNEXPECTED_SLASH);
		}

		if (hero.hasTalent(Talent.SPEEDY_MOVE) && enemy instanceof Mob && enemy.buff(ShootTheHeartTracker.class) == null){
			Buff.affect(enemy, ShootTheHeartTracker.class);
			Buff.prolong(hero, Haste.class, 1f + hero.pointsInTalent(Talent.SPEEDY_MOVE));
		}

		if (hero.hasTalent(Talent.WAR_CRY) && enemy instanceof Mob && enemy.buff(WarCryTracker.class) == null) {
			Buff.affect(enemy, WarCryTracker.class);
			Buff.prolong(hero, Adrenaline.class, 1 + hero.pointsInTalent(Talent.WAR_CRY));
		}
		if (hero.buff(Talent.SpiritBladesTracker.class) != null
				&& Random.Int(10) < 3*hero.pointsInTalent(Talent.SPIRIT_BLADES)){
			SpiritBow bow = hero.belongings.getItem(SpiritBow.class);
			if (bow != null) dmg = bow.proc( hero, enemy, dmg );
			hero.buff(Talent.SpiritBladesTracker.class).detach();
		}

		if (hero.hasTalent(PATIENT_STRIKE)){
			if (hero.buff(PatientStrikeTracker.class) != null
					&& !(hero.belongings.attackingWeapon() instanceof MissileWeapon)){
				hero.buff(PatientStrikeTracker.class).detach();
				dmg += Random.IntRange(hero.pointsInTalent(Talent.PATIENT_STRIKE), 2);
				if (!(enemy instanceof Mob) || !((Mob) enemy).surprisedBy(hero)){
					Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG, 0.75f, 1.2f);
				}
			}
		}

		if (hero.hasTalent(DEADLY_FOLLOWUP)) {
			if (hero.belongings.attackingWeapon() instanceof MissileWeapon) {
				if (!(hero.belongings.attackingWeapon() instanceof SpiritBow.SpiritArrow)) {
					Buff.prolong(enemy, DeadlyFollowupTracker.class, 5f);
				}
			} else if (enemy.buff(DeadlyFollowupTracker.class) != null){
				dmg = Math.round(dmg * (1.0f + .08f*hero.pointsInTalent(DEADLY_FOLLOWUP)));
				if (!(enemy instanceof Mob) || !((Mob) enemy).surprisedBy(hero)){
					Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG, 0.75f, 1.2f);
				}
			}
		}

		return dmg;
	}

	public static class SuckerPunchTracker extends Buff{};
	public static class FollowupStrikeTracker extends Buff{};
	public static class ShootTheHeartTracker extends Buff{};
	public static class WarCryTracker extends Buff{};
	public static class ParryCooldown extends Buff{
		float cooldown;
		public void set() {
			cooldown = 90-20*hero.pointsInTalent(Talent.PARRY)+1;
		}
		@Override
		public boolean act() {
			cooldown -= Actor.TICK;
			if (cooldown <= 0) {
				Buff.affect(target, ParryTracker.class);
				detach();
			}
			spend(Actor.TICK);
			return true;
		}
	};
	public static class ParryTracker extends Buff{
		{
			type = buffType.NEUTRAL;
			announced = true;
		}

		@Override
		public int icon() {
			return BuffIndicator.PARRY;
		}

		@Override
		public boolean act() {
			spend(Actor.TICK);
			return true;
		}

		@Override
		public void detach() {
			super.detach();
			Buff.affect(target, ParryCooldown.class).set();
		}
	};
	public static class RiposteTracker extends Buff{
		{ actPriority = VFX_PRIO;}

		public Char enemy;

		@Override
		public boolean act() {
			target.sprite.attack(enemy.pos, new Callback() {
				@Override
				public void call() {
					AttackIndicator.target(enemy);
					if (hero.attack(enemy, 1f, 0, 1)){
						Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
					}

					next();
				}
			});
			detach();
			return false;
		}
	}

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
				Collections.addAll(tierTalents, HEARTY_MEAL, VETERANS_INTUITION, TEST_SUBJECT, IRON_WILL, MAX_HEALTH);
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
				Collections.addAll(tierTalents,	SURPRISE_STAB, MASTERS_INTUITION, UNEXPECTED_SLASH, FLOW_AWAY, ADRENALINE_SURGE);
				break;
			case PLANTER:
				Collections.addAll(tierTalents,	SUDDEN_GROWTH, SAFE_POTION, ROOT, KNOWLEDGE_HERB, PLANT_SHIELD);
				break;
			case KNIGHT:
				Collections.addAll(tierTalents,	ON_ALERT, KNIGHTS_INTUITION, ARMOR_ENHANCE, ACTIVE_BARRIER, WAR_CRY);
				break;
			case NURSE:
				Collections.addAll(tierTalents,	HEALING_MEAL, DOCTORS_INTUITION, INNER_MIRROR, CRITICAL_SHIELD, HEAL_AMP);
				break;
			case DUELIST:
				Collections.addAll(tierTalents, STRENGTHENING_MEAL, ADVENTURERS_INTUITION, PATIENT_STRIKE, AGGRESSIVE_BARRIER, SKILLED_HAND);
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
				Collections.addAll(tierTalents, IRON_STOMACH, RESTORED_WILLPOWER, RUNIC_TRANSFERENCE, LETHAL_MOMENTUM, IMPROVISED_PROJECTILES, PARRY);
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
			case DUELIST:
				Collections.addAll(tierTalents, FOCUSED_MEAL, RESTORED_AGILITY, WEAPON_RECHARGING, LETHAL_HASTE, SWIFT_EQUIP, ACCUMULATION);
				break;
			case GUNNER:
				Collections.addAll(tierTalents,	IN_THE_GUNFIRE, ANOTHER_CHANCE, BULLET_FOCUS, CAMOUFLAGE, LARGER_MAGAZINE, TRANSMUTATION_CONTROL);
				break;
			case SAMURAI:
				Collections.addAll(tierTalents,	FOCUSING_MEAL, CRITICAL_UPGRADE, MAGICAL_TRANSFERENCE, EYE_OF_DRAGON, DETECTION, CRITICAL_THROW);
				break;
			case PLANTER:
				Collections.addAll(tierTalents,	NATURAL_MEAL, HERBAL_DEW, SPROUT, FIREWATCH, FLOWER_BED, WEAK_POISON);
				break;
			case KNIGHT:
				Collections.addAll(tierTalents,	IMPREGNABLE_MEAL, SAFE_HEALING, DEFENSE_STANCE, FAITH, CHIVALRY, ARMOR_ADAPTION);
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
			case DUELIST:
				Collections.addAll(tierTalents, LIGHTWEIGHT_CHARGE, DEADLY_FOLLOWUP);
				break;
			case GUNNER:
				Collections.addAll(tierTalents, STREET_BATTLE, FAST_RELOAD);
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
			case CHAMPION:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, SECONDARY_CHARGE, TWIN_UPGRADES, COMBINED_LETHALITY, FASTER_CHARGE, QUICK_FOLLOWUP, MOMENTARY_UPGRADE);
				break;
			case MONK:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, UNENCUMBERED_SPIRIT, MONASTIC_VIGOR, COMBINED_ENERGY, RESTORED_ENERGY, ENERGY_BARRIER, IRON_PUNCH);
				break;
			case FENCER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, CLAM_STEPS, CRITICAL_MOMENTUM, KINETIC_MOVEMENT, AGGRESSIVE_MOVEMENT, UNENCUMBERED_MOVEMENT, SOULIZE);
				break;
			case MARSHAL:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, JUSTICE_BULLET, INTIMIDATION, SEARCH, COVER, SURRENDER, INVEST_END);
				break;
			case GUNSLINGER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, QUICK_RELOAD, MOVING_SHOT, ELEMENTAL_BULLET, MYSTICAL_THROW, SOUL_BULLET, LIGHT_MOVEMENT);
				break;
			case SPECIALIST:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, SILENCER, SKILLFUL_RUNNER, STEALTH, INTO_THE_SHADOW, RANGED_SNIPING, TELESCOPE);
				break;
			case SLASHER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, CONTINUOUS_ATTACK, SLASHING_PRACTICE, SERIAL_MOMENTUM, ARCANE_ATTACK, SLASHING, DETECTIVE_SLASHING);
				break;
			case MASTER:
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, DONG_MIND_EYES, DONG_SHEATHING, DONG_POLISHING, JUNG_DETECTION, JUNG_QUICK_DRAW, JUNG_INCISIVE_BLADE);
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
				Collections.addAll(tierTalents, ATK_SPEED_ENHANCE, ACC_ENHANCE, EVA_ENHANCE, BETTER_CHOICE, DIVINE_SHIELD, DEADS_BLESS, ARMOR_BLESSING, BLESSED_TALENT, MYSTICAL_VEIL, SHIELD_OF_LIGHT);
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

	private static final HashMap<String, String> renamedTalents = new HashMap<>();
	static{
		//v2.0.0
		renamedTalents.put("ARMSMASTERS_INTUITION",     "VETERANS_INTUITION");
		//v2.0.0 BETA
		renamedTalents.put("LIGHTLY_ARMED",             "UNENCUMBERED_SPIRIT");
	}

	public static void restoreTalentsFromBundle( Bundle bundle, Hero hero ){
		if (bundle.contains("replacements")){
			Bundle replacements = bundle.getBundle("replacements");
			for (String key : replacements.getKeys()){
				String value = replacements.getString(key);
				if (renamedTalents.containsKey(key)) key = renamedTalents.get(key);
				if (renamedTalents.containsKey(value)) value = renamedTalents.get(value);
				hero.metamorphedTalents.put(Talent.valueOf(key), Talent.valueOf(value));
			}
		}

		if (hero.heroClass != null)     initClassTalents(hero);
		if (hero.subClass != null)      initSubclassTalents(hero);
		if (hero.armorAbility != null)  initArmorTalents(hero);

		for (int i = 0; i < MAX_TALENT_TIERS; i++){
			LinkedHashMap<Talent, Integer> tier = hero.talents.get(i);
			Bundle tierBundle = bundle.contains(TALENT_TIER+(i+1)) ? bundle.getBundle(TALENT_TIER+(i+1)) : null;

			if (tierBundle != null){
				for (String tName : tierBundle.getKeys()){
					int points = tierBundle.getInt(tName);
					if (renamedTalents.containsKey(tName)) tName = renamedTalents.get(tName);
					try {
						Talent talent = Talent.valueOf(tName);
						if (tier.containsKey(talent)) {
							tier.put(talent, Math.min(points, talent.maxPoints()));
						}
					} catch (Exception e){
						ShatteredPixelDungeon.reportException(e);
					}
				}
			}
		}
	}

}
