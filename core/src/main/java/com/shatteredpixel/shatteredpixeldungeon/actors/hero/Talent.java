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

import static com.shatteredpixel.shatteredpixeldungeon.items.Item.updateQuickslot;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CounterBuff;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RPG7;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Languages;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public enum Talent {

	//Warrior T1
	HEARTY_MEAL(0), ARMSMASTERS_INTUITION(1), TEST_SUBJECT(2), IRON_WILL(3),
	//Warrior T2
	IRON_STOMACH(4), RESTORED_WILLPOWER(5), RUNIC_TRANSFERENCE(6), LETHAL_MOMENTUM(7), IMPROVISED_PROJECTILES(8),
	//Warrior T3
	HOLD_FAST(9, 3), STRONGMAN(10, 3),
	//Berserker T3
	ENDLESS_RAGE(11, 3), BERSERKING_STAMINA(12, 3), ENRAGED_CATALYST(13, 3),
	//Gladiator T3
	CLEAVE(14, 3), LETHAL_DEFENSE(15, 3), ENHANCED_COMBO(16, 3),
	//Veteran T3
	ARM_VETERAN(128, 3), MARTIAL_ARTS(129, 3), ENHANCED_FOCUSING(130, 3),
	//Heroic Leap T4
	BODY_SLAM(17, 4), IMPACT_WAVE(18, 4), DOUBLE_JUMP(19, 4),
	//Shockwave T4
	EXPANDING_WAVE(20, 4), STRIKING_WAVE(21, 4), SHOCK_FORCE(22, 4),
	//Endure T4
	SUSTAINED_RETRIBUTION(23, 4), SHRUG_IT_OFF(24, 4), EVEN_THE_ODDS(25, 4),

	//Mage T1
	EMPOWERING_MEAL(32), SCHOLARS_INTUITION(33), TESTED_HYPOTHESIS(34), BACKUP_BARRIER(35),
	//Mage T2
	ENERGIZING_MEAL(36), ENERGIZING_UPGRADE(37), WAND_PRESERVATION(38), ARCANE_VISION(39), SHIELD_BATTERY(40),
	//Mage T3
	EMPOWERING_SCROLLS(41, 3), ALLY_WARP(42, 3),
	//Battlemage T3
	EMPOWERED_STRIKE(43, 3), MYSTICAL_CHARGE(44, 3), EXCESS_CHARGE(45, 3),
	//Warlock T3
	SOUL_EATER(46, 3), SOUL_SIPHON(47, 3), NECROMANCERS_MINIONS(48, 3),
	//engineer T3
	AMMO_PRESERVE(137, 3), CONNECTING_CHARGER(138, 3), FASTER_CHARGER(139, 3),
	//Elemental Blast T4
	BLAST_RADIUS(49, 4), ELEMENTAL_POWER(50, 4), REACTIVE_BARRIER(51, 4),
	//Wild Magic T4
	WILD_POWER(52, 4), FIRE_EVERYTHING(53, 4), CONSERVED_MAGIC(54, 4),
	//Warp Beacon T4
	TELEFRAG(55, 4), REMOTE_BEACON(56, 4), LONGRANGE_WARP(57, 4),

	//Rogue T1
	CACHED_RATIONS(64), THIEFS_INTUITION(65), SUCKER_PUNCH(66), PROTECTIVE_SHADOWS(67),
	//Rogue T2
	MYSTICAL_MEAL(68), MYSTICAL_UPGRADE(69), WIDE_SEARCH(70), SILENT_STEPS(71), ROGUES_FORESIGHT(72),
	//Rogue T3
	ENHANCED_RINGS(73, 3), LIGHT_CLOAK(74, 3),
	//Assassin T3
	ENHANCED_LETHALITY(75, 3), ASSASSINS_REACH(76, 3), BOUNTY_HUNTER(77, 3),
	//Freerunner T3
	EVASIVE_ARMOR(78, 3), PROJECTILE_MOMENTUM(79, 3), SPEEDY_STEALTH(80, 3),
	//Chaser T3
	INCISIVE_BLADE(131, 3), LETHAL_SURPRISE(132, 3), CHAIN_CLOCK(133, 3),
	//Smoke Bomb T4
	HASTY_RETREAT(81, 4), BODY_REPLACEMENT(82, 4), SHADOW_STEP(83, 4),
	//Death Mark T4
	FEAR_THE_REAPER(84, 4), DEATHLY_DURABILITY(85, 4), DOUBLE_MARK(86, 4),
	//Shadow Clone T4
	SHADOW_BLADE(87, 4), CLONED_ARMOR(88, 4), PERFECT_COPY(89, 4),

	//Huntress T1
	NATURES_BOUNTY(96), SURVIVALISTS_INTUITION(97), FOLLOWUP_STRIKE(98), NATURES_AID(99),
	//Huntress T2
	INVIGORATING_MEAL(100), RESTORED_NATURE(101), REJUVENATING_STEPS(102), HEIGHTENED_SENSES(103), DURABLE_PROJECTILES(104),
	//Huntress T3
	POINT_BLANK(105, 3), SEER_SHOT(106, 3),
	//Sniper T3
	FARSIGHT(107, 3), SHARED_ENCHANTMENT(108, 3), SHARED_UPGRADES(109, 3),
	//Warden T3
	DURABLE_TIPS(110, 3), BARKSKIN(111, 3), SHIELDING_DEW(112, 3),
	//fighter T3
	SKILLS_PRACTICE(134, 3), MIND_PRACTICE(135, 3), VITAL_ATTACK(136, 3),
	//Spectral Blades T4
	FAN_OF_BLADES(113, 4), PROJECTING_BLADES(114, 4), SPIRIT_BLADES(115, 4),
	//Natures Power T4
	GROWING_POWER(116, 4), NATURES_WRATH(117, 4), WILD_MOMENTUM(118, 4),
	//Spirit Hawk T4
	EAGLE_EYE(119, 4), GO_FOR_THE_EYES(120, 4), SWIFT_SPIRIT(121, 4),

	//Gunner T1
	REARRANGE(160), GUNNERS_INTUITION(161), SHOOT_THE_HEART(162), SAFE_RELOAD(163),
	//Gunner T2
	IN_THE_GUNFIRE(164), ANOTHER_CHANCE(165), CHOICE_N_FOCUS(166), BLACKSMITH(167), LARGER_MAGAZINE(168),
	//Gunner T3
	MELEE_ENHANCE(169, 3), BOOM_ENHANCE(170, 3),
	//Launcher T3
	MACHINEGUN_MASTER(171, 3), ACC_PRACTICE(172, 2), REBOUND_PRACTICE(173, 3),
	//Ranger T3
	HANDGUN_MASTER(174, 3), BETTER_FOCUS(175, 3), REBOUND_CONTROL(176, 3),
	//RifleMan T3
	RIFLE_MASTER(140, 3), ALL_FOR_ONE(141, 3), EVASIVE_MOVE(142, 3),
	//Riot T4
	RIOT_1(177, 4), RIOT_2(178,4), RIOT_3(179, 4),
	//ArtillerySupport T4
	ARTILLERY_1(180, 4), ARTILLERY_2(181, 4), ARTILLERY_3(182, 4),
	//Napalm T4
	NAPALM_1(183,4), NAPALM_2(184, 4), NAPALM_3(185,4),

	//universal T4
	HEROIC_ENERGY(26, 4), //See icon() and title() for special logic for this one
	//Ratmogrify T4
	RATSISTANCE(124, 4), RATLOMACY(125, 4), RATFORCEMENTS(126, 4),

	//universal T3
	BETTER_CHOICE(123, 3);

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
		public int icon() { return target.buff(RevealedArea.class) != null ? BuffIndicator.NONE : BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.4f, 0.4f, 0.8f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / (15)); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class ChainCooldown extends FlavourBuff{
		public int icon() { return target.buff(RevealedArea.class) != null ? BuffIndicator.NONE : BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.2f, 0.5f, 0.8f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 10); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class LethalCooldown extends FlavourBuff{
		public int icon() { return target.buff(RevealedArea.class) != null ? BuffIndicator.NONE : BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.8f, 0.1f, 0.1f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 5); }
		public String toString() { return Messages.get(this, "name"); }
		public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
	};
	public static class ReloadCooldown extends FlavourBuff{
		public int icon() { return target.buff(RevealedArea.class) != null ? BuffIndicator.NONE : BuffIndicator.TIME; }
		public void tintIcon(Image icon) { icon.hardlight(0.3f, 0.3f, 0.3f); }
		public float iconFadePercent() { return Math.max(0, visualcooldown() / 6); }
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
				return 127;
			}
			HeroClass cls = Dungeon.hero != null ? Dungeon.hero.heroClass : GamesInProgress.selectedClass;
			switch (cls){
				case WARRIOR: default:
					return 26;
				case MAGE:
					return 58;
				case ROGUE:
					return 90;
				case HUNTRESS:
					return 122;
				case GUNNER:
					return 185;
			}
		} else {
			return icon;
		}
	}

	public int maxPoints(){
		return maxPoints;
	}

	public String title(){
		//TODO translate this
		if (this == HEROIC_ENERGY &&
				Messages.lang() == Languages.ENGLISH
				&& Dungeon.hero != null
				&& Dungeon.hero.armorAbility instanceof Ratmogrify){
			return "ratroic energy";
		}
		return Messages.get(this, name() + ".title");
	}

	public String desc(){
		return Messages.get(this, name() + ".desc");
	}

	public static void onTalentUpgraded( Hero hero, Talent talent){
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
			ScrollOfUpgrade scl = new ScrollOfUpgrade();
			if (scl.doPickUp( Dungeon.hero )) {
				GLog.i( Messages.get(Dungeon.hero, "you_now_have", scl.name() ));
			} else {
				Dungeon.level.drop( scl, Dungeon.hero.pos ).sprite.drop();
			}
		}

		if (talent == BETTER_CHOICE && hero.pointsInTalent(BETTER_CHOICE) == 2){
			ScrollOfUpgrade scl = new ScrollOfUpgrade();
			if (scl.doPickUp( Dungeon.hero )) {
				GLog.i( Messages.get(Dungeon.hero, "you_now_have", scl.name() ));
			} else {
				Dungeon.level.drop( scl, Dungeon.hero.pos ).sprite.drop();
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
			//effectively 1/2 turns of ExtraBullet
			Buff.prolong( hero, InfiniteBullet.class, 0.67f+hero.pointsInTalent(INVIGORATING_MEAL));
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
		) {
			factor *= 1f + hero.pointsInTalent(GUNNERS_INTUITION);
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
	}

	public static void onUpgradeScrollUsed( Hero hero ){
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
			if (hero.pointsInTalent(Talent.ANOTHER_CHANCE) >= 1 && Random.Int(19) == 0) {
				if (enchantment.doPickUp( Dungeon.hero )) {
					GLog.i( Messages.get(Dungeon.hero, "you_now_have", enchantment.name() ));
				} else {
					Dungeon.level.drop( enchantment, Dungeon.hero.pos ).sprite.drop();
				}
			}
			if (hero.pointsInTalent(Talent.ANOTHER_CHANCE) == 2 && Random.Int(19) == 0) {
				if (scl.doPickUp( Dungeon.hero )) {
					GLog.i( Messages.get(Dungeon.hero, "you_now_have", scl.name() ));
				} else {
					Dungeon.level.drop( scl, Dungeon.hero.pos ).sprite.drop();
				}
			}
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
						||item instanceof RocketLauncher)
		){
			item.identify();
		}
	}

	public static void onItemCollected( Hero hero, Item item ){
		if (hero.pointsInTalent(THIEFS_INTUITION) == 2){
			if (item instanceof Ring) ((Ring) item).setKnown();
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

		if (Dungeon.hero.belongings.weapon instanceof CrudePistol.Bullet
				|| Dungeon.hero.belongings.weapon instanceof CrudePistolAP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof CrudePistolHP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof Pistol.Bullet
				|| Dungeon.hero.belongings.weapon instanceof PistolAP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof PistolHP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof GoldenPistol.Bullet
				|| Dungeon.hero.belongings.weapon instanceof GoldenPistolAP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof GoldenPistolHP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof Handgun.Bullet
				|| Dungeon.hero.belongings.weapon instanceof HandgunAP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof HandgunHP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof Magnum.Bullet
				|| Dungeon.hero.belongings.weapon instanceof MagnumAP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof MagnumHP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof DualPistol.Bullet
				|| Dungeon.hero.belongings.weapon instanceof DualPistolAP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof DualPistolHP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof SubMachinegun.Bullet
				|| Dungeon.hero.belongings.weapon instanceof SubMachinegunAP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof SubMachinegunHP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof AssultRifle.Bullet
				|| Dungeon.hero.belongings.weapon instanceof AssultRifleAP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof AssultRifleHP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof HeavyMachinegun.Bullet
				|| Dungeon.hero.belongings.weapon instanceof HeavyMachinegunAP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof HeavyMachinegunHP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof HuntingRifle.Bullet
				|| Dungeon.hero.belongings.weapon instanceof HuntingRifleAP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof HuntingRifleHP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof SniperRifle.Bullet
				|| Dungeon.hero.belongings.weapon instanceof SniperRifleAP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof SniperRifleHP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof ShotGun.Bullet
				|| Dungeon.hero.belongings.weapon instanceof ShotGunAP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof ShotGunHP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof RocketLauncher.Rocket
				|| Dungeon.hero.belongings.weapon instanceof MiniGun.Bullet
				|| Dungeon.hero.belongings.weapon instanceof MiniGunAP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof MiniGunHP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof LargeHandgun.Bullet
				|| Dungeon.hero.belongings.weapon instanceof LargeHandgunAP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof LargeHandgunHP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof AntimaterRifle.Bullet
				|| Dungeon.hero.belongings.weapon instanceof AntimaterRifleAP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof AntimaterRifleHP.Bullet
				|| Dungeon.hero.belongings.weapon instanceof RPG7.Rocket
		){
			if (hero.hasTalent(Talent.SHOOT_THE_HEART) && enemy instanceof Mob && enemy.buff(ShootTheHeartTracker.class) == null){
				dmg += 1 + hero.pointsInTalent(Talent.SHOOT_THE_HEART);
				Buff.affect(enemy, ShootTheHeartTracker.class);
			}
		}

		return dmg;
	}

	public static class SuckerPunchTracker extends Buff{};
	public static class FollowupStrikeTracker extends Buff{};
	public static class ShootTheHeartTracker extends Buff{};

	public static final int MAX_TALENT_TIERS = 4;

	public static void initClassTalents( Hero hero ){
		initClassTalents( hero.heroClass, hero.talents );
	}

	public static void initClassTalents( HeroClass cls, ArrayList<LinkedHashMap<Talent, Integer>> talents ){
		while (talents.size() < MAX_TALENT_TIERS){
			talents.add(new LinkedHashMap<>());
		}

		ArrayList<Talent> tierTalents = new ArrayList<>();

		//tier 1
		switch (cls){
			case WARRIOR: default:
				Collections.addAll(tierTalents, HEARTY_MEAL, ARMSMASTERS_INTUITION, TEST_SUBJECT, IRON_WILL);
				break;
			case MAGE:
				Collections.addAll(tierTalents, EMPOWERING_MEAL, SCHOLARS_INTUITION, TESTED_HYPOTHESIS, BACKUP_BARRIER);
				break;
			case ROGUE:
				Collections.addAll(tierTalents, CACHED_RATIONS, THIEFS_INTUITION, SUCKER_PUNCH, PROTECTIVE_SHADOWS);
				break;
			case HUNTRESS:
				Collections.addAll(tierTalents, NATURES_BOUNTY, SURVIVALISTS_INTUITION, FOLLOWUP_STRIKE, NATURES_AID);
				break;
			case GUNNER:
				Collections.addAll(tierTalents,	REARRANGE, GUNNERS_INTUITION, SHOOT_THE_HEART, SAFE_RELOAD);
				break;
		}
		for (Talent talent : tierTalents){
			talents.get(0).put(talent, 0);
		}
		tierTalents.clear();

		//tier 2
		switch (cls){
			case WARRIOR: default:
				Collections.addAll(tierTalents, IRON_STOMACH, RESTORED_WILLPOWER, RUNIC_TRANSFERENCE, LETHAL_MOMENTUM, IMPROVISED_PROJECTILES);
				break;
			case MAGE:
				Collections.addAll(tierTalents, ENERGIZING_MEAL, ENERGIZING_UPGRADE, WAND_PRESERVATION, ARCANE_VISION, SHIELD_BATTERY);
				break;
			case ROGUE:
				Collections.addAll(tierTalents, MYSTICAL_MEAL, MYSTICAL_UPGRADE, WIDE_SEARCH, SILENT_STEPS, ROGUES_FORESIGHT);
				break;
			case HUNTRESS:
				Collections.addAll(tierTalents, INVIGORATING_MEAL, RESTORED_NATURE, REJUVENATING_STEPS, HEIGHTENED_SENSES, DURABLE_PROJECTILES);
				break;
			case GUNNER:
				Collections.addAll(tierTalents,	IN_THE_GUNFIRE, ANOTHER_CHANCE, CHOICE_N_FOCUS, BLACKSMITH, LARGER_MAGAZINE);
				break;
		}
		for (Talent talent : tierTalents){
			talents.get(1).put(talent, 0);
		}
		tierTalents.clear();

		//tier 3
		switch (cls){
			case WARRIOR: default:
				Collections.addAll(tierTalents, HOLD_FAST, STRONGMAN, BETTER_CHOICE);
				break;
			case MAGE:
				Collections.addAll(tierTalents, EMPOWERING_SCROLLS, ALLY_WARP, BETTER_CHOICE);
				break;
			case ROGUE:
				Collections.addAll(tierTalents, ENHANCED_RINGS, LIGHT_CLOAK, BETTER_CHOICE);
				break;
			case HUNTRESS:
				Collections.addAll(tierTalents, POINT_BLANK, SEER_SHOT, BETTER_CHOICE);
				break;
			case GUNNER:
				Collections.addAll(tierTalents, MELEE_ENHANCE, BOOM_ENHANCE);
				break;
		}
		for (Talent talent : tierTalents){
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
		if (cls == HeroSubClass.NONE) return;

		while (talents.size() < MAX_TALENT_TIERS){
			talents.add(new LinkedHashMap<>());
		}

		ArrayList<Talent> tierTalents = new ArrayList<>();

		//tier 3
		switch (cls){
			case BERSERKER: default:
				Collections.addAll(tierTalents, ENDLESS_RAGE, BERSERKING_STAMINA, ENRAGED_CATALYST);
				break;
			case GLADIATOR:
				Collections.addAll(tierTalents, CLEAVE, LETHAL_DEFENSE, ENHANCED_COMBO);
				break;
			case VETERAN:
				Collections.addAll(tierTalents, ARM_VETERAN, MARTIAL_ARTS, ENHANCED_FOCUSING);
				break;
			case BATTLEMAGE:
				Collections.addAll(tierTalents, EMPOWERED_STRIKE, MYSTICAL_CHARGE, EXCESS_CHARGE);
				break;
			case WARLOCK:
				Collections.addAll(tierTalents, SOUL_EATER, SOUL_SIPHON, NECROMANCERS_MINIONS);
				break;
			case ENGINEER:
				Collections.addAll(tierTalents, AMMO_PRESERVE, CONNECTING_CHARGER, FASTER_CHARGER);
				break;
			case ASSASSIN:
				Collections.addAll(tierTalents, ENHANCED_LETHALITY, ASSASSINS_REACH, BOUNTY_HUNTER);
				break;
			case FREERUNNER:
				Collections.addAll(tierTalents, EVASIVE_ARMOR, PROJECTILE_MOMENTUM, SPEEDY_STEALTH);
				break;
			case CHASER:
				Collections.addAll(tierTalents, INCISIVE_BLADE ,LETHAL_SURPRISE , CHAIN_CLOCK);
				break;
			case SNIPER:
				Collections.addAll(tierTalents, FARSIGHT, SHARED_ENCHANTMENT, SHARED_UPGRADES);
				break;
			case WARDEN:
				Collections.addAll(tierTalents, DURABLE_TIPS, BARKSKIN, SHIELDING_DEW);
				break;
			case FIGHTER:
				Collections.addAll(tierTalents, SKILLS_PRACTICE, MIND_PRACTICE, VITAL_ATTACK);
				break;
			case LAUNCHER:
				Collections.addAll(tierTalents, MACHINEGUN_MASTER, ACC_PRACTICE, REBOUND_PRACTICE);
				break;
			case RANGER:
				Collections.addAll(tierTalents, HANDGUN_MASTER, BETTER_FOCUS, REBOUND_CONTROL);
				break;
			case RIFLEMAN:
				Collections.addAll(tierTalents, RIFLE_MASTER, ALL_FOR_ONE, EVASIVE_MOVE);
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
	}

	public static void restoreTalentsFromBundle( Bundle bundle, Hero hero ){
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
