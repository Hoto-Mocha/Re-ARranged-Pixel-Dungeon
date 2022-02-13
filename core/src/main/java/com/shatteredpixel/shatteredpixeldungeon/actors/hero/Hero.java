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
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Bones;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Alchemy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AdrenalineSurge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AnkhInvulnerability;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Awareness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Berserk;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Combo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Demonization;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Dong;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Drowsy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ElectroBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EvasiveMove;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Focusing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Foresight;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Jung;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LanceBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LanceGuard;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LanceGuardBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.HoldFast;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Lead;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LostInventory;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MindVision;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Momentum;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Regeneration;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SerialAttack;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Shadows;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SnipersMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpearGuard;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpearGuardBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.StanceCooldown;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.ReinforcedArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.Riot;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.NaturesPower;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.samurai.ShadowBlade;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.Endure;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Monk;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Snake;
import com.shatteredpixel.shatteredpixeldungeon.effects.CheckedCell;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap.Type;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.AntiMagic;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Brimstone;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Viscosity;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CapeOfThorns;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.EtherealChains;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HornOfPlenty;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.MagicalHolster;
import com.shatteredpixel.shatteredpixeldungeon.items.journal.Guidebook;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.CrystalKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.GoldenKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.Key;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.SkeletonKey;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfDivineInspiration;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAccuracy;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEvasion;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfForce;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfFuror;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfTenacity;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfChallenge;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLivingEarth;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.GoldenBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.NaturesBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.PoisonBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.WindBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Blocking;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Bible;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ChainFlail;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ChainWhip;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ElectroScimitar;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Flail;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameScimitar;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrower;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrowerAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrowerHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FrostScimitar;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HolySword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Lance;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LanceNShield;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ObsidianShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannonAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannonHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PoisonScimitar;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpearNShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TestWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.UnholyBible;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.journal.Document;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ShadowCaster;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Earthroot;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.AlchemyScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.SurfaceScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.StatusPane;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndHero;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndResurrect;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTradeItem;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

import sun.jvm.hotspot.opto.Block;

public class Hero extends Char {

	{
		actPriority = HERO_PRIO;
		
		alignment = Alignment.ALLY;
	}
	
	public static final int MAX_LEVEL = 30;

	public static final int STARTING_STR = 10;
	
	private static final float TIME_TO_REST		    = 1f;
	private static final float TIME_TO_SEARCH	    = 2f;
	private static final float HUNGER_FOR_SEARCH	= 6f;

	public HeroClass heroClass = HeroClass.ROGUE;
	public HeroSubClass subClass = HeroSubClass.NONE;
	public ArmorAbility armorAbility = null;
	public ArrayList<LinkedHashMap<Talent, Integer>> talents = new ArrayList<>();
	public LinkedHashMap<Talent, Talent> metamorphedTalents = new LinkedHashMap<>();
	
	private int attackSkill = 10;
	private int defenseSkill = 5;

	public boolean ready = false;
	private boolean damageInterrupt = true;
	public HeroAction curAction = null;
	public HeroAction lastAction = null;

	private Char enemy;
	
	public boolean resting = false;
	
	public Belongings belongings;
	
	public int STR;
	
	public float awareness;
	
	public int lvl = 1;
	public int exp = 0;
	
	public int HTBoost = 0;
	
	private ArrayList<Mob> visibleEnemies;

	//This list is maintained so that some logic checks can be skipped
	// for enemies we know we aren't seeing normally, resultign in better performance
	public ArrayList<Mob> mindVisionEnemies = new ArrayList<>();

	public Hero() {
		super();

		HP = HT = (Dungeon.isChallenged(Challenges.SUPERMAN)) ? 10 : 20;
		STR = STARTING_STR;
		
		belongings = new Belongings( this );
		
		visibleEnemies = new ArrayList<>();
	}
	
	public void updateHT( boolean boostHP ){
		int curHT = HT;
		
		HT = (Dungeon.isChallenged(Challenges.SUPERMAN)) ? 10 : 20 + 5*(lvl-1) + HTBoost;
		float multiplier = RingOfMight.HTMultiplier(this);
		HT = Math.round(multiplier * HT);
		
		if (buff(ElixirOfMight.HTBoost.class) != null){
			HT += buff(ElixirOfMight.HTBoost.class).boost();
		}
		
		if (boostHP){
			HP += Math.max(HT - curHT, 0);
		}
		HP = Math.min(HP, HT);
	}

	public int STR() {
		int strBonus = 0;

		strBonus += RingOfMight.strengthBonus( this );
		
		AdrenalineSurge buff = buff(AdrenalineSurge.class);
		if (buff != null){
			strBonus += buff.boost();
		}

		if (hasTalent(Talent.STRONGMAN)){
			strBonus += (int)Math.floor(STR * (0.03f + 0.05f*pointsInTalent(Talent.STRONGMAN)));
		}

		return STR + strBonus;
	}

	private static final String CLASS       = "class";
	private static final String SUBCLASS    = "subClass";
	private static final String ABILITY     = "armorAbility";

	private static final String ATTACK		= "attackSkill";
	private static final String DEFENSE		= "defenseSkill";
	private static final String STRENGTH	= "STR";
	private static final String LEVEL		= "lvl";
	private static final String EXPERIENCE	= "exp";
	private static final String HTBOOST     = "htboost";
	
	@Override
	public void storeInBundle( Bundle bundle ) {

		super.storeInBundle( bundle );

		bundle.put( CLASS, heroClass );
		bundle.put( SUBCLASS, subClass );
		bundle.put( ABILITY, armorAbility );
		Talent.storeTalentsInBundle( bundle, this );
		
		bundle.put( ATTACK, attackSkill );
		bundle.put( DEFENSE, defenseSkill );
		
		bundle.put( STRENGTH, STR );
		
		bundle.put( LEVEL, lvl );
		bundle.put( EXPERIENCE, exp );
		
		bundle.put( HTBOOST, HTBoost );

		belongings.storeInBundle( bundle );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {

		lvl = bundle.getInt( LEVEL );
		exp = bundle.getInt( EXPERIENCE );

		HTBoost = bundle.getInt(HTBOOST);

		super.restoreFromBundle( bundle );

		heroClass = bundle.getEnum( CLASS, HeroClass.class );
		subClass = bundle.getEnum( SUBCLASS, HeroSubClass.class );
		armorAbility = (ArmorAbility)bundle.get( ABILITY );
		Talent.restoreTalentsFromBundle( bundle, this );
		
		attackSkill = bundle.getInt( ATTACK );
		defenseSkill = bundle.getInt( DEFENSE );
		
		STR = bundle.getInt( STRENGTH );

		belongings.restoreFromBundle( bundle );
	}
	
	public static void preview( GamesInProgress.Info info, Bundle bundle ) {
		info.level = bundle.getInt( LEVEL );
		info.str = bundle.getInt( STRENGTH );
		info.exp = bundle.getInt( EXPERIENCE );
		info.hp = bundle.getInt( Char.TAG_HP );
		info.ht = bundle.getInt( Char.TAG_HT );
		info.shld = bundle.getInt( Char.TAG_SHLD );
		info.heroClass = bundle.getEnum( CLASS, HeroClass.class );
		info.subClass = bundle.getEnum( SUBCLASS, HeroSubClass.class );
		Belongings.preview( info, bundle );
	}

	public boolean hasTalent( Talent talent ){
		return pointsInTalent(talent) > 0;
	}

	public int pointsInTalent( Talent talent ){
		for (LinkedHashMap<Talent, Integer> tier : talents){
			for (Talent f : tier.keySet()){
				if (f == talent) return tier.get(f);
			}
		}
		return 0;
	}

	public void upgradeTalent( Talent talent ){
		for (LinkedHashMap<Talent, Integer> tier : talents){
			for (Talent f : tier.keySet()){
				if (f == talent) tier.put(talent, tier.get(talent)+1);
			}
		}
		Talent.onTalentUpgraded(this, talent);
	}

	public int talentPointsSpent(int tier){
		int total = 0;
		for (int i : talents.get(tier-1).values()){
			total += i;
		}
		return total;
	}

	public int talentPointsAvailable(int tier){
		if (lvl < (Talent.tierLevelThresholds[tier] - 1)
			|| (tier == 3 && subClass == HeroSubClass.NONE)
			|| (tier == 4 && armorAbility == null)) {
			return 0;
		} else if (lvl >= Talent.tierLevelThresholds[tier+1]){
			return Talent.tierLevelThresholds[tier+1] - Talent.tierLevelThresholds[tier] - talentPointsSpent(tier) + bonusTalentPoints(tier);
		} else {
			return 1 + lvl - Talent.tierLevelThresholds[tier] - talentPointsSpent(tier) + bonusTalentPoints(tier);
		}
	}

	public int bonusTalentPoints(int tier){
		if (lvl < (Talent.tierLevelThresholds[tier]-1)
				|| (tier == 3 && subClass == HeroSubClass.NONE)
				|| (tier == 4 && armorAbility == null)) {
			return 0;
		} else if (buff(PotionOfDivineInspiration.DivineInspirationTracker.class) != null
					&& buff(PotionOfDivineInspiration.DivineInspirationTracker.class).isBoosted(tier)) {
			return 2;
		} else {
			return 0;
		}
	}
	
	public String className() {
		return subClass == null || subClass == HeroSubClass.NONE ? heroClass.title() : subClass.title();
	}

	@Override
	public String name(){
		return className();
	}

	@Override
	public void hitSound(float pitch) {
		if ( belongings.weapon() != null ){
			belongings.weapon().hitSound(pitch);
		} else if (RingOfForce.getBuffedBonus(this, RingOfForce.Force.class) > 0) {
			//pitch deepens by 2.5% (additive) per point of strength, down to 75%
			super.hitSound( pitch * GameMath.gate( 0.75f, 1.25f - 0.025f*STR(), 1f) );
		} else {
			super.hitSound(pitch * 1.1f);
		}
	}

	@Override
	public boolean blockSound(float pitch) {
		if ( belongings.weapon() != null && belongings.weapon().defenseFactor(this) >= 4 ){
			Sample.INSTANCE.play( Assets.Sounds.HIT_PARRY, 1, pitch);
			return true;
		}
		return super.blockSound(pitch);
	}

	public void live() {
		for (Buff b : buffs()){
			if (!b.revivePersists) b.detach();
		}
		Buff.affect( this, Regeneration.class );
		Buff.affect( this, Hunger.class );
	}
	
	public int tier() {
		if (belongings.armor() instanceof ClassArmor){
			return 6;
		} else if (belongings.armor() != null){
			return belongings.armor().tier;
		} else {
			return 0;
		}
	}
	
	public boolean shoot( Char enemy, MissileWeapon wep ) {

		this.enemy = enemy;

		//temporarily set the hero's weapon to the missile weapon being used
		//TODO improve this!
		belongings.thrownWeapon = wep;
		boolean hit = attack( enemy );
		Invisibility.dispel();
		belongings.thrownWeapon = null;
		
		if (hit && subClass == HeroSubClass.GLADIATOR){
			Buff.affect( this, Combo.class ).hit( enemy );
		}

		if (hit && subClass == HeroSubClass.SLASHER){
			Buff.affect( this, SerialAttack.class ).hit( enemy );
		}

		return hit;
	}

	@Override
	public int attackSkill( Char target ) {
		KindOfWeapon wep = belongings.weapon();
		
		float accuracy = 1;
		accuracy *= RingOfAccuracy.accuracyMultiplier( this );

		if (Dungeon.isChallenged(Challenges.SUPERMAN)) {
			accuracy *= 2;
		}

		if (hero.hasTalent(Talent.ACC_ENHANCE)) {
			accuracy *= 1 + 0.2f * hero.pointsInTalent(Talent.ACC_ENHANCE);
		}

		if (hero.buff(Lead.class) != null) {
			accuracy *= 1.2f;
		}

		if (hero.buff(Jung.class) != null && hero.buff(Lead.class) != null && wep instanceof MeleeWeapon) {
			accuracy = INFINITE_ACCURACY;
		}
		
		if (wep instanceof MissileWeapon){
			if (Dungeon.level.adjacent( pos, target.pos )) {
				if (wep instanceof ShotGun.Bullet
				 || wep instanceof SPAS.Bullet
				 || wep instanceof ShotGunHP.Bullet
				 || wep instanceof SPASHP.Bullet) {
					accuracy *= (1.5f + 0.2f*pointsInTalent(Talent.POINT_BLANK));
				} else if (wep instanceof HuntingRifle.Bullet
						|| wep instanceof SniperRifle.Bullet
						|| wep instanceof AntimaterRifle.Bullet
						|| wep instanceof HuntingRifleAP.Bullet
						|| wep instanceof SniperRifleAP.Bullet
						|| wep instanceof AntimaterRifleAP.Bullet
						|| wep instanceof HuntingRifleHP.Bullet
						|| wep instanceof SniperRifleHP.Bullet
						|| wep instanceof AntimaterRifleHP.Bullet
				){
					accuracy *= 0;
				} else {
					accuracy *= (0.5f + 0.2f*pointsInTalent(Talent.POINT_BLANK));
				}

			} else {
				if (wep instanceof ShotGun.Bullet
			     || wep instanceof SPAS.Bullet
				 || wep instanceof ShotGunHP.Bullet
				 || wep instanceof SPASHP.Bullet) {
					accuracy *= 0;
				}
				else if (wep instanceof HuntingRifle.Bullet
					  || wep instanceof SniperRifle.Bullet
					  || wep instanceof AntimaterRifle.Bullet
					  || wep instanceof HuntingRifleAP.Bullet
					  || wep instanceof SniperRifleAP.Bullet
					  || wep instanceof AntimaterRifleAP.Bullet
					  || wep instanceof HuntingRifleHP.Bullet
					  || wep instanceof SniperRifleHP.Bullet
					  || wep instanceof AntimaterRifleHP.Bullet
				) {
						accuracy *= 2f;
				} else if (wep instanceof CrudePistol.Bullet
						|| wep instanceof CrudePistolAP.Bullet
						|| wep instanceof CrudePistolHP.Bullet
						|| wep instanceof Pistol.Bullet
						|| wep instanceof PistolAP.Bullet
						|| wep instanceof PistolHP.Bullet
						|| wep instanceof GoldenPistol.Bullet
						|| wep instanceof GoldenPistolAP.Bullet
						|| wep instanceof GoldenPistolHP.Bullet
						|| wep instanceof Handgun.Bullet
						|| wep instanceof HandgunAP.Bullet
						|| wep instanceof HandgunHP.Bullet
						|| wep instanceof Magnum.Bullet
						|| wep instanceof MagnumAP.Bullet
						|| wep instanceof MagnumHP.Bullet
						|| wep instanceof DualPistol.Bullet
						|| wep instanceof DualPistolAP.Bullet
						|| wep instanceof DualPistolHP.Bullet
						|| wep instanceof SubMachinegun.Bullet
						|| wep instanceof SubMachinegunAP.Bullet
						|| wep instanceof SubMachinegunHP.Bullet
						|| wep instanceof AssultRifle.Bullet
						|| wep instanceof AssultRifleAP.Bullet
						|| wep instanceof AssultRifleHP.Bullet
						|| wep instanceof HeavyMachinegun.Bullet
						|| wep instanceof HeavyMachinegunAP.Bullet
						|| wep instanceof HeavyMachinegunHP.Bullet
						|| wep instanceof RocketLauncher.Rocket
						|| wep instanceof ShotGunAP.Bullet
						|| wep instanceof SPASAP.Bullet
						|| wep instanceof MiniGun.Bullet
						|| wep instanceof MiniGunAP.Bullet
						|| wep instanceof MiniGunHP.Bullet
				) {
					accuracy *= 1f;
				} else if (wep instanceof LargeHandgun.Bullet
						|| wep instanceof LargeHandgunAP.Bullet
						|| wep instanceof LargeHandgunHP.Bullet) {
					accuracy *= 1.3f;
				} else if (wep instanceof TacticalShield.Bullet) {
					accuracy *= 0.7f;
				} else {
					accuracy *= 1.5f;
				}
			}
			if (this.hasTalent(Talent.BULLET_FOCUS)) {
				if (wep instanceof CrudePistolAP.Bullet
					|| wep instanceof PistolAP.Bullet
					|| wep instanceof GoldenPistolAP.Bullet
					|| wep instanceof HandgunAP.Bullet
					|| wep instanceof MagnumAP.Bullet
					|| wep instanceof HuntingRifleAP.Bullet
					|| wep instanceof SniperRifleAP.Bullet
					|| wep instanceof DualPistolAP.Bullet
					|| wep instanceof SubMachinegunAP.Bullet
					|| wep instanceof AssultRifleAP.Bullet
					|| wep instanceof HeavyMachinegunAP.Bullet
					|| wep instanceof MiniGunAP.Bullet
					|| wep instanceof LargeHandgunAP.Bullet
					|| wep instanceof AntimaterRifleAP.Bullet
					|| wep instanceof CrudePistolHP.Bullet
					|| wep instanceof PistolHP.Bullet
					|| wep instanceof GoldenPistolHP.Bullet
					|| wep instanceof HandgunHP.Bullet
					|| wep instanceof MagnumHP.Bullet
					|| wep instanceof HuntingRifleHP.Bullet
					|| wep instanceof SniperRifleHP.Bullet
					|| wep instanceof DualPistolHP.Bullet
					|| wep instanceof SubMachinegunHP.Bullet
					|| wep instanceof AssultRifleHP.Bullet
					|| wep instanceof HeavyMachinegunHP.Bullet
					|| wep instanceof MiniGunHP.Bullet
					|| wep instanceof LargeHandgunHP.Bullet
					|| wep instanceof AntimaterRifleHP.Bullet
					|| wep instanceof CrudePistol.Bullet
					|| wep instanceof Pistol.Bullet
					|| wep instanceof GoldenPistol.Bullet
					|| wep instanceof Handgun.Bullet
					|| wep instanceof Magnum.Bullet
					|| wep instanceof HuntingRifle.Bullet
					|| wep instanceof SniperRifle.Bullet
					|| wep instanceof DualPistol.Bullet
					|| wep instanceof SubMachinegun.Bullet
					|| wep instanceof AssultRifle.Bullet
					|| wep instanceof HeavyMachinegun.Bullet
					|| wep instanceof MiniGun.Bullet
					|| wep instanceof LargeHandgun.Bullet
					|| wep instanceof AntimaterRifle.Bullet
					|| wep instanceof RocketLauncher.Rocket
					|| wep instanceof RPG7.Rocket
					|| wep instanceof GrenadeLauncher.Rocket
					|| wep instanceof GrenadeLauncherAP.Rocket
					|| wep instanceof GrenadeLauncherHP.Rocket
					|| wep instanceof TacticalShield.Bullet
				) {
					accuracy *= 1.05f + 0.05f * hero.pointsInTalent(Talent.BULLET_FOCUS);
				}
			}
		}

		if (Dungeon.hero.hasTalent(Talent.ENHANCED_FOCUSING)) {
			accuracy *= 1f + (0.1f * hero.pointsInTalent(Talent.ENHANCED_FOCUSING));
		}

		if (Dungeon.hero.hasTalent(Talent.ACC_PRACTICE)) {
			if (wep instanceof SubMachinegun.Bullet
					|| wep instanceof SubMachinegunAP.Bullet
					|| wep instanceof SubMachinegunHP.Bullet
					|| wep instanceof AssultRifle.Bullet
					|| wep instanceof AssultRifleAP.Bullet
					|| wep instanceof AssultRifleHP.Bullet
					|| wep instanceof HeavyMachinegun.Bullet
					|| wep instanceof HeavyMachinegunAP.Bullet
					|| wep instanceof HeavyMachinegunHP.Bullet
					|| wep instanceof MiniGun.Bullet
					|| wep instanceof MiniGunAP.Bullet
					|| wep instanceof MiniGunHP.Bullet) {
				accuracy *= 1f + (0.1f * hero.pointsInTalent(Talent.ACC_PRACTICE));
			}
		}

		if (wep instanceof CrudePistol.Bullet
				|| wep instanceof CrudePistolAP.Bullet
				|| wep instanceof CrudePistolHP.Bullet
				|| wep instanceof Pistol.Bullet
				|| wep instanceof PistolAP.Bullet
				|| wep instanceof PistolHP.Bullet
				|| wep instanceof GoldenPistol.Bullet
				|| wep instanceof GoldenPistolAP.Bullet
				|| wep instanceof GoldenPistolHP.Bullet
				|| wep instanceof Handgun.Bullet
				|| wep instanceof HandgunAP.Bullet
				|| wep instanceof HandgunHP.Bullet
				|| wep instanceof Magnum.Bullet
				|| wep instanceof MagnumAP.Bullet
				|| wep instanceof MagnumHP.Bullet
				|| wep instanceof LargeHandgun.Bullet
				|| wep instanceof LargeHandgunAP.Bullet
				|| wep instanceof LargeHandgunHP.Bullet
		) {
			if (buff(FireBullet.class) != null) buff(FireBullet.class).proc(enemy);
			if (buff(FrostBullet.class) != null) buff(FrostBullet.class).proc(enemy);
			if (buff(ElectroBullet.class) != null) buff(ElectroBullet.class).proc(enemy);
		}

		if (hero.buff(Riot.riotTracker.class) != null && hero.hasTalent(Talent.SHOT_CONCENTRATION)) {
			if (wep instanceof CrudePistol.Bullet
			 || wep instanceof CrudePistolAP.Bullet
			 || wep instanceof CrudePistolHP.Bullet
			 || wep instanceof Pistol.Bullet
			 || wep instanceof PistolAP.Bullet
			 || wep instanceof PistolHP.Bullet
			 || wep instanceof GoldenPistol.Bullet
			 || wep instanceof GoldenPistolAP.Bullet
			 || wep instanceof GoldenPistolHP.Bullet
			 || wep instanceof Handgun.Bullet
			 || wep instanceof HandgunAP.Bullet
			 || wep instanceof HandgunHP.Bullet
			 || wep instanceof Magnum.Bullet
			 || wep instanceof MagnumAP.Bullet
			 || wep instanceof MagnumHP.Bullet
			 || wep instanceof LargeHandgun.Bullet
			 || wep instanceof LargeHandgunAP.Bullet
			 || wep instanceof LargeHandgunHP.Bullet
			 || wep instanceof DualPistol.Bullet
			 || wep instanceof DualPistolAP.Bullet
			 || wep instanceof DualPistolHP.Bullet
			 || wep instanceof SubMachinegun.Bullet
			 || wep instanceof SubMachinegunAP.Bullet
			 || wep instanceof SubMachinegunHP.Bullet
			 || wep instanceof AssultRifle.Bullet
			 || wep instanceof AssultRifleAP.Bullet
			 || wep instanceof AssultRifleHP.Bullet
			 || wep instanceof HeavyMachinegun.Bullet
			 || wep instanceof HeavyMachinegunAP.Bullet
			 || wep instanceof HeavyMachinegunHP.Bullet
			 || wep instanceof MiniGun.Bullet
			 || wep instanceof MiniGunAP.Bullet
			 || wep instanceof MiniGunHP.Bullet
			 || wep instanceof HuntingRifle.Bullet
			 || wep instanceof HuntingRifleAP.Bullet
			 || wep instanceof HuntingRifleHP.Bullet
			 || wep instanceof SniperRifle.Bullet
			 || wep instanceof SniperRifleAP.Bullet
			 || wep instanceof SniperRifleHP.Bullet
			 || wep instanceof AntimaterRifle.Bullet
			 || wep instanceof AntimaterRifleAP.Bullet
			 || wep instanceof AntimaterRifleHP.Bullet
			 || wep instanceof ShotGun.Bullet
			 || wep instanceof ShotGunAP.Bullet
			 || wep instanceof ShotGunHP.Bullet
			 || wep instanceof SPAS.Bullet
			 || wep instanceof SPASAP.Bullet
			 || wep instanceof SPASHP.Bullet
			 || wep instanceof RocketLauncher.Rocket
			 || wep instanceof RPG7.Rocket
			 //|| wep instanceof FlameThrower.Bullet
			 //|| wep instanceof FlameThrowerAP.Bullet
			 //|| wep instanceof FlameThrowerHP.Bullet
			 //|| wep instanceof PlasmaCannon.Bullet
			 //|| wep instanceof PlasmaCannonAP.Bullet
			 //|| wep instanceof PlasmaCannonHP.Bullet
			 || wep instanceof GrenadeLauncher.Rocket
			 || wep instanceof GrenadeLauncherAP.Rocket
			 || wep instanceof GrenadeLauncherHP.Rocket
			) {
				if (Random.Int(4) < hero.pointsInTalent(Talent.SHOT_CONCENTRATION)) {
					Riot.riotTracker riot = hero.buff(Riot.riotTracker.class);
					riot.extend();
				}
			}
		}
		
		if (wep != null) {
			return (int)(attackSkill * accuracy * wep.accuracyFactor( this ));
		} else {
			return (int)(attackSkill * accuracy);
		}
	}
	
	@Override
	public int defenseSkill( Char enemy ) {

		if (buff(Combo.ParryTracker.class) != null){
			if (canAttack(enemy)){
				Buff.affect(this, Combo.RiposteTracker.class).enemy = enemy;
			}
			return INFINITE_EVASION;
		}

		if (buff(EvasiveMove.class) != null) {
			return INFINITE_EVASION;
		}

		if (buff(LanceGuard.class) != null || buff(SpearGuard.class) != null){
			return 0;
		}
		
		float evasion = defenseSkill;
		
		evasion *= RingOfEvasion.evasionMultiplier( this );

		if (Dungeon.isChallenged(Challenges.SUPERMAN)) {
			evasion *= 3;
		}

		if (hero.hasTalent(Talent.EVA_ENHANCE)) {
			evasion *= 1 + 0.1f * hero.pointsInTalent(Talent.EVA_ENHANCE);
		}
		
		if (paralysed > 0) {
			evasion /= 2;
		}

		if (belongings.armor() != null) {
			evasion = belongings.armor().evasionFactor(this, evasion);
		}

		if (belongings.artifact() instanceof CapeOfThorns && belongings.artifact().cursed) {
			evasion /= 2;
		}

		return Math.round(evasion);
	}

	@Override
	public String defenseVerb() {
		Combo.ParryTracker parry = buff(Combo.ParryTracker.class);
		if (parry == null){
			return super.defenseVerb();
		} else {
			parry.parried = true;
			if (buff(Combo.class).getComboCount() < 9 || pointsInTalent(Talent.ENHANCED_COMBO) < 2) {
				parry.detach();
			}
			return Messages.get(Monk.class, "parried");
		}
	}


	@Override
	public int drRoll() {
		int dr = 0;

		if (belongings.armor() != null) {
			int armDr = Random.NormalIntRange( belongings.armor().DRMin(), belongings.armor().DRMax());
			if (STR() < belongings.armor().STRReq()){
				armDr -= 2*(belongings.armor().STRReq() - STR());
			}
			if (armDr > 0) dr += armDr;
		}
		if (belongings.weapon() != null)  {
			int wepDr = Random.NormalIntRange( 0 , belongings.weapon().defenseFactor( this ) );
			if (STR() < ((Weapon)belongings.weapon()).STRReq()){
				wepDr -= 2*(((Weapon)belongings.weapon()).STRReq() - STR());
			}
			if (wepDr > 0) dr += wepDr;
		}

		if (buff(HoldFast.class) != null){
			dr += Random.NormalIntRange(0, 2*pointsInTalent(Talent.HOLD_FAST));
		}

		if (buff(LanceGuard.class) != null && hero.belongings.weapon instanceof LanceNShield){
			dr += 4+hero.belongings.weapon.buffedLvl()*2;
		}

		if (buff(SpearGuard.class) != null && hero.belongings.weapon instanceof SpearNShield){
			dr += 4+hero.belongings.weapon.buffedLvl()*2;
		}

		if (buff(Lead.class) != null) {
			dr += Random.NormalIntRange(0, 5*pointsInTalent(Talent.PARRY));
		}
		
		return dr;
	}
	
	@Override
	public int damageRoll() {
		KindOfWeapon wep = belongings.weapon();
		int dmg;

		if (wep != null) {
			dmg = wep.damageRoll( this );
			if (!(wep instanceof MissileWeapon)) dmg += RingOfForce.armedDamageBonus(this);
		} else {
			dmg = RingOfForce.damageRoll(this);
		}
		if (dmg < 0) dmg = 0;
		
		return dmg;
	}
	
	@Override
	public float speed() {

		float speed = super.speed();

		speed *= RingOfHaste.speedMultiplier(this);

		if (hero.buff(ReinforcedArmor.reinforcedArmorTracker.class) != null && hero.hasTalent(Talent.PLATE_ADD)) {
			speed *= (1 - hero.pointsInTalent(Talent.PLATE_ADD)/8f);
		}

		if (hero.subClass == HeroSubClass.RANGER) {
			if (hero.belongings.weapon instanceof CrudePistol
			 || hero.belongings.weapon instanceof CrudePistolAP
			 || hero.belongings.weapon instanceof CrudePistolHP
			 || hero.belongings.weapon instanceof Pistol
			 || hero.belongings.weapon instanceof PistolAP
			 || hero.belongings.weapon instanceof PistolHP
			 || hero.belongings.weapon instanceof GoldenPistol
			 || hero.belongings.weapon instanceof GoldenPistolAP
			 || hero.belongings.weapon instanceof GoldenPistolHP
			 || hero.belongings.weapon instanceof Handgun
			 || hero.belongings.weapon instanceof HandgunAP
			 || hero.belongings.weapon instanceof HandgunHP
			 || hero.belongings.weapon instanceof Magnum
			 || hero.belongings.weapon instanceof MagnumAP
			 || hero.belongings.weapon instanceof MagnumHP
			 || hero.belongings.weapon instanceof LargeHandgun
			 || hero.belongings.weapon instanceof LargeHandgunAP
			 || hero.belongings.weapon instanceof LargeHandgunHP
			 || hero.belongings.weapon instanceof DualPistol
			 || hero.belongings.weapon instanceof DualPistolAP
			 || hero.belongings.weapon instanceof DualPistolHP
			) {
				speed *= 1.1f;
			}
		}

		if (hero.belongings.weapon instanceof TacticalShield) {
			speed *= 0.5f;
		}

		if (hero.buff(Riot.riotTracker.class) != null && hero.hasTalent(Talent.HASTE_MOVE)) {
			speed *= 1f + 0.25f * hero.pointsInTalent(Talent.HASTE_MOVE);
		}

		if ((hero.buff(SpearGuard.class) != null) || (hero.buff(LanceGuard.class) != null)) {
			speed *= 0.5f;
		}

		if (belongings.armor() != null) {
			speed = belongings.armor().speedFactor(this, speed);
		}
		
		Momentum momentum = buff(Momentum.class);
		if (momentum != null){
			((HeroSprite)sprite).sprint( momentum.freerunning() ? 1.5f : 1f );
			speed *= momentum.speedMultiplier();
		} else {
			((HeroSprite)sprite).sprint( 1f );
		}

		NaturesPower.naturesPowerTracker natStrength = buff(NaturesPower.naturesPowerTracker.class);
		if (natStrength != null){
			speed *= (2f + 0.25f*pointsInTalent(Talent.GROWING_POWER));
		}
		
		return speed;
		
	}

	public boolean canSurpriseAttack(){
		if (belongings.weapon() == null || !(belongings.weapon() instanceof Weapon))    return true;
		if (STR() < ((Weapon)belongings.weapon()).STRReq())                             return false;
		if (belongings.weapon() instanceof Flail)                                       return false;
		if (belongings.weapon() instanceof ChainFlail)                                  return false;
		//if (belongings.weapon() instanceof RocketLauncher.Rocket)					    return false;
		//if (belongings.weapon() instanceof RPG7.Rocket)				          	    return false;
		if (belongings.weapon() instanceof MiniGun.Bullet)				          	    return false;
		if (belongings.weapon() instanceof MiniGunAP.Bullet)				          	return false;
		if (belongings.weapon() instanceof MiniGunHP.Bullet)				          	return false;
		if (belongings.weapon() instanceof ShotGun.Bullet)				          	    return false;
		//if (belongings.weapon() instanceof ShotGunAP.Bullet)				          	return false;           //슬러그 샷건은 기습가능
		if (belongings.weapon() instanceof ShotGunHP.Bullet)				          	return false;
		if (belongings.weapon() instanceof SPAS.Bullet)				             	 	return false;
		//if (belongings.weapon() instanceof SPASAP.Bullet)				         	 	return false;
		if (belongings.weapon() instanceof SPASHP.Bullet)				        	  	return false;
		//if (belongings.weapon() instanceof GrenadeLauncher.Rocket)					return false;
		//if (belongings.weapon() instanceof GrenadeLauncherAP.Rocket)					return false;
		//if (belongings.weapon() instanceof GrenadeLauncherHP.Rocket)					return false;  			//폭발류도 기습 가능

		return true;
	}

	public boolean canAttack(Char enemy){
		if (enemy == null || pos == enemy.pos || !Actor.chars().contains(enemy)) {
			return false;
		}

		//can always attack adjaceadjacent enemies
		if (Dungeon.level.adjacent(pos, enemy.pos)) {
			return true;
		}

		KindOfWeapon wep = Dungeon.hero.belongings.weapon();

		if (wep != null){
			return wep.canReach(this, enemy.pos);
		} else {
			return false;
		}
	}
	
	public float attackDelay() {
		if (buff(Talent.LethalMomentumTracker.class) != null){
			buff(Talent.LethalMomentumTracker.class).detach();
			return 0;
		}

		if (buff(Lead.class) != null && buff(Jung.class) != null) {
			hero.buff(Jung.class).detach();
			Buff.affect(hero, Dong.class);
			Buff.affect(hero, StanceCooldown.class, 9f);
			buff(Dong.class).getIcon();
			return 1f;
		}

		if (belongings.weapon() != null) {

				return belongings.weapon().delayFactor( this );

		} else {
			if (hero.hasTalent(Talent.SLASHING_PRACTICE) && hero.buff(SerialAttack.class) != null) {
				return 1f / RingOfFuror.attackSpeedMultiplier(this) / (1f + 0.05f * buff(SerialAttack.class).getCount());
			} else {
				//Normally putting furor speed on unarmed attacks would be unnecessary
				//But there's going to be that one guy who gets a furor+force ring combo
				//This is for that one guy, you shall get your fists of fury!
				return 1f / RingOfFuror.attackSpeedMultiplier(this);
			}

		}
	}

	@Override
	public void spend( float time ) {
		justMoved = false;
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
		
		super.spend(time);
	}
	
	public void spendAndNext( float time ) {
		busy();
		spend( time );
		next();
	}
	
	@Override
	public boolean act() {
		
		//calls to dungeon.observe will also update hero's local FOV.
		fieldOfView = Dungeon.level.heroFOV;

		if (buff(Endure.EndureTracker.class) != null){
			buff(Endure.EndureTracker.class).endEnduring();
		}
		
		if (!ready) {
			//do a full observe (including fog update) if not resting.
			if (!resting || buff(MindVision.class) != null || buff(Awareness.class) != null) {
				Dungeon.observe();
			} else {
				//otherwise just directly re-calculate FOV
				Dungeon.level.updateFieldOfView(this, fieldOfView);
			}
		}
		
		checkVisibleMobs();
		BuffIndicator.refreshHero();
		
		if (paralysed > 0) {
			
			curAction = null;
			
			spendAndNext( TICK );
			return false;
		}
		
		boolean actResult;
		if (curAction == null) {
			
			if (resting) {
				spend( TIME_TO_REST );
				next();
			} else {
				ready();
			}
			
			actResult = false;
			
		} else {
			
			resting = false;
			
			ready = false;
			
			if (curAction instanceof HeroAction.Move) {
				actResult = actMove( (HeroAction.Move)curAction );
				
			} else if (curAction instanceof HeroAction.Interact) {
				actResult = actInteract( (HeroAction.Interact)curAction );
				
			} else if (curAction instanceof HeroAction.Buy) {
				actResult = actBuy( (HeroAction.Buy)curAction );
				
			}else if (curAction instanceof HeroAction.PickUp) {
				actResult = actPickUp( (HeroAction.PickUp)curAction );
				
			} else if (curAction instanceof HeroAction.OpenChest) {
				actResult = actOpenChest( (HeroAction.OpenChest)curAction );
				
			} else if (curAction instanceof HeroAction.Unlock) {
				actResult = actUnlock((HeroAction.Unlock) curAction);
				
			} else if (curAction instanceof HeroAction.Descend) {
				actResult = actDescend( (HeroAction.Descend)curAction );
				
			} else if (curAction instanceof HeroAction.Ascend) {
				actResult = actAscend( (HeroAction.Ascend)curAction );
				
			} else if (curAction instanceof HeroAction.Attack) {
				actResult = actAttack( (HeroAction.Attack)curAction );
				
			} else if (curAction instanceof HeroAction.Alchemy) {
				actResult = actAlchemy( (HeroAction.Alchemy)curAction );
				
			} else {
				actResult = false;
			}
		}
		
		if(hasTalent(Talent.BARKSKIN) && Dungeon.level.map[pos] == Terrain.FURROWED_GRASS){
			Buff.affect(this, Barkskin.class).set( (lvl*pointsInTalent(Talent.BARKSKIN))/2, 1 );
		}

		if(Dungeon.level.map[pos] == Terrain.FURROWED_GRASS && hero.hasTalent(Talent.SHADOW) && hero.buff(Shadows.class) != null) {
			Buff.prolong(this, Shadows.class, 1.0001f);
		}
		
		return actResult;
	}
	
	public void busy() {
		ready = false;
	}
	
	private void ready() {
		if (sprite.looping()) sprite.idle();
		curAction = null;
		damageInterrupt = true;
		ready = true;

		AttackIndicator.updateState();
		
		GameScene.ready();
	}
	
	public void interrupt() {
		if (isAlive() && curAction != null &&
			((curAction instanceof HeroAction.Move && curAction.dst != pos) ||
			(curAction instanceof HeroAction.Ascend || curAction instanceof HeroAction.Descend))) {
			lastAction = curAction;
		}
		curAction = null;
		GameScene.resetKeyHold();
	}
	
	public void resume() {
		curAction = lastAction;
		lastAction = null;
		damageInterrupt = false;
		next();
	}
	
	private boolean actMove( HeroAction.Move action ) {

		if (getCloser( action.dst )) {
			return true;

		} else {
			ready();
			return false;
		}
	}
	
	private boolean actInteract( HeroAction.Interact action ) {
		
		Char ch = action.ch;

		if (ch.canInteract(this)) {
			
			ready();
			sprite.turnTo( pos, ch.pos );
			return ch.interact(this);
			
		} else {
			
			if (fieldOfView[ch.pos] && getCloser( ch.pos )) {

				return true;

			} else {
				ready();
				return false;
			}
			
		}
	}
	
	private boolean actBuy( HeroAction.Buy action ) {
		int dst = action.dst;
		if (pos == dst) {

			ready();
			
			Heap heap = Dungeon.level.heaps.get( dst );
			if (heap != null && heap.type == Type.FOR_SALE && heap.size() == 1) {
				Game.runOnRenderThread(new Callback() {
					@Override
					public void call() {
						GameScene.show( new WndTradeItem( heap ) );
					}
				});
			}

			return false;

		} else if (getCloser( dst )) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actAlchemy( HeroAction.Alchemy action ) {
		int dst = action.dst;
		if (Dungeon.level.distance(dst, pos) <= 1) {

			ready();
			
			AlchemistsToolkit.kitEnergy kit = buff(AlchemistsToolkit.kitEnergy.class);
			if (kit != null && kit.isCursed()){
				GLog.w( Messages.get(AlchemistsToolkit.class, "cursed"));
				return false;
			}

			AlchemyScene.clearToolkit();
			ShatteredPixelDungeon.switchScene(AlchemyScene.class);
			return false;

		} else if (getCloser( dst )) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actPickUp( HeroAction.PickUp action ) {
		int dst = action.dst;
		if (pos == dst) {
			
			Heap heap = Dungeon.level.heaps.get( pos );
			if (heap != null) {
				Item item = heap.peek();
				if (item.doPickUp( this )) {
					heap.pickUp();

					if (item instanceof Dewdrop
							|| item instanceof TimekeepersHourglass.sandBag
							|| item instanceof DriedRose.Petal
							|| item instanceof Key) {
						//Do Nothing
					} else {

						//TODO make all unique items important? or just POS / SOU?
						boolean important = item.unique && item.isIdentified() &&
								(item instanceof Scroll || item instanceof Potion);
						if (important) {
							GLog.p( Messages.get(this, "you_now_have", item.name()) );
						} else {
							GLog.i( Messages.get(this, "you_now_have", item.name()) );
						}
					}
					
					curAction = null;
				} else {

					if (item instanceof Dewdrop
							|| item instanceof TimekeepersHourglass.sandBag
							|| item instanceof DriedRose.Petal
							|| item instanceof Key) {
						//Do Nothing
					} else {
						GLog.newLine();
						GLog.n(Messages.get(this, "you_cant_have", item.name()));
					}

					heap.sprite.drop();
					ready();
				}
			} else {
				ready();
			}

			return false;

		} else if (getCloser( dst )) {

			return true;

		} else {
			ready();
			return false;
		}
	}
	
	private boolean actOpenChest( HeroAction.OpenChest action ) {
		int dst = action.dst;
		if (Dungeon.level.adjacent( pos, dst ) || pos == dst) {
			
			Heap heap = Dungeon.level.heaps.get( dst );
			if (heap != null && (heap.type != Type.HEAP && heap.type != Type.FOR_SALE)) {
				
				if ((heap.type == Type.LOCKED_CHEST && Notes.keyCount(new GoldenKey(Dungeon.depth)) < 1)
					|| (heap.type == Type.CRYSTAL_CHEST && Notes.keyCount(new CrystalKey(Dungeon.depth)) < 1)){

						GLog.w( Messages.get(this, "locked_chest") );
						ready();
						return false;

				}
				
				switch (heap.type) {
				case TOMB:
					Sample.INSTANCE.play( Assets.Sounds.TOMB );
					Camera.main.shake( 1, 0.5f );
					break;
				case SKELETON:
				case REMAINS:
					break;
				default:
					Sample.INSTANCE.play( Assets.Sounds.UNLOCK );
				}
				
				sprite.operate( dst );
				
			} else {
				ready();
			}

			return false;

		} else if (getCloser( dst )) {

			return true;

		} else {
			ready();
			return false;
		}
	}
	
	private boolean actUnlock( HeroAction.Unlock action ) {
		int doorCell = action.dst;
		if (Dungeon.level.adjacent( pos, doorCell )) {
			
			boolean hasKey = false;
			int door = Dungeon.level.map[doorCell];
			
			if (door == Terrain.LOCKED_DOOR
					&& Notes.keyCount(new IronKey(Dungeon.depth)) > 0) {
				
				hasKey = true;
				
			} else if (door == Terrain.LOCKED_EXIT
					&& Notes.keyCount(new SkeletonKey(Dungeon.depth)) > 0) {

				hasKey = true;
				
			}
			
			if (hasKey) {
				
				sprite.operate( doorCell );
				
				Sample.INSTANCE.play( Assets.Sounds.UNLOCK );
				
			} else {
				GLog.w( Messages.get(this, "locked_door") );
				ready();
			}

			return false;

		} else if (getCloser( doorCell )) {

			return true;

		} else {
			ready();
			return false;
		}
	}
	
	private boolean actDescend( HeroAction.Descend action ) {
		int stairs = action.dst;

		if (rooted) {
			Camera.main.shake(1, 1f);
			ready();
			return false;
		//there can be multiple exit tiles, so descend on any of them
		//TODO this is slightly brittle, it assumes there are no disjointed sets of exit tiles
		} else if ((Dungeon.level.map[pos] == Terrain.EXIT || Dungeon.level.map[pos] == Terrain.UNLOCKED_EXIT)) {
			
			curAction = null;

			TimekeepersHourglass.timeFreeze timeFreeze = buff(TimekeepersHourglass.timeFreeze.class);
			if (timeFreeze != null) timeFreeze.disarmPressedTraps();
			Swiftthistle.TimeBubble timeBubble = buff(Swiftthistle.TimeBubble.class);
			if (timeBubble != null) timeBubble.disarmPressedTraps();
			
			InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
			Game.switchScene( InterlevelScene.class );

			return false;

		} else if (getCloser( stairs )) {

			return true;

		} else {
			ready();
			return false;
		}
	}
	
	private boolean actAscend( HeroAction.Ascend action ) {
		int stairs = action.dst;


		if (rooted){
			Camera.main.shake( 1, 1f );
			ready();
			return false;
		//there can be multiple entrance tiles, so descend on any of them
		//TODO this is slightly brittle, it assumes there are no disjointed sets of entrance tiles
		} else if (Dungeon.level.map[pos] == Terrain.ENTRANCE) {
			
			if (Dungeon.depth == 1) {
				
				if (belongings.getItem( Amulet.class ) == null) {
					Game.runOnRenderThread(new Callback() {
						@Override
						public void call() {
							GameScene.show( new WndMessage( Messages.get(Hero.this, "leave") ) );
						}
					});
					ready();
				} else {
					Badges.silentValidateHappyEnd();
					Dungeon.win( Amulet.class );
					Dungeon.deleteGame( GamesInProgress.curSlot, true );
					Game.switchScene( SurfaceScene.class );
				}
				
			} else {
				
				curAction = null;

				TimekeepersHourglass.timeFreeze timeFreeze = buff(TimekeepersHourglass.timeFreeze.class);
				if (timeFreeze != null) timeFreeze.disarmPressedTraps();
				Swiftthistle.TimeBubble timeBubble = buff(Swiftthistle.TimeBubble.class);
				if (timeBubble != null) timeBubble.disarmPressedTraps();

				InterlevelScene.mode = InterlevelScene.Mode.ASCEND;
				Game.switchScene( InterlevelScene.class );
			}

			return false;

		} else if (getCloser( stairs )) {

			return true;

		} else {
			ready();
			return false;
		}
	}
	
	private boolean actAttack( HeroAction.Attack action ) {

		enemy = action.target;

		if (enemy.isAlive() && canAttack( enemy ) && !isCharmedBy( enemy )) {
			
			sprite.attack( enemy.pos );

			return false;

		} else {

			if (fieldOfView[enemy.pos] && getCloser( enemy.pos )) {

				return true;

			} else {
				ready();
				return false;
			}

		}
	}

	public Char enemy(){
		return enemy;
	}

	public void rest( boolean fullRest ) {
		spendAndNext( TIME_TO_REST );

		if (!fullRest) {
			if (Dungeon.level.map[pos] == Terrain.FURROWED_GRASS && hero.hasTalent(Talent.SHADOW) && hero.buff(Shadows.class) == null) {
				Buff.affect(this, Shadows.class, 1.0001f);
			}
			if (hasTalent(Talent.HOLD_FAST)){
				Buff.affect(this, HoldFast.class);
			}
			if (sprite != null) {
				sprite.showStatus(CharSprite.DEFAULT, Messages.get(this, "wait"));
			}
			if (Dungeon.hero.subClass == HeroSubClass.CHASER
					&& hero.buff(Talent.ChaseCooldown.class) == null
					&& hero.buff(Invisibility.class) == null
					&& hero.buff(CloakOfShadows.cloakStealth.class) == null ) {
				Buff.affect(Dungeon.hero, Invisibility.class, 5f);
				Buff.affect(Dungeon.hero, Talent.ChaseCooldown.class, 15f);
			}
			if (hero.heroClass == HeroClass.SAMURAI) {
				if (hero.buff(Lead.class) == null && hero.hasTalent(Talent.FLOW_AWAY) && Random.Int(10) < hero.pointsInTalent(Talent.FLOW_AWAY)) {
					Buff.affect(hero, EvasiveMove.class, 0.9999f);
				}
				Buff.affect(hero, Lead.class);

				if (hero.subClass == HeroSubClass.MASTER && hero.buff(StanceCooldown.class) == null) {
					if (hero.buff(Dong.class) == null && hero.buff(Jung.class) == null) {

						Buff.affect(hero, Dong.class);

					} else if (hero.buff(Dong.class) != null) {

						hero.buff(Dong.class).detach();
						Buff.affect(hero, Jung.class);

					}
				}
			}
		}
		resting = fullRest;
	}
	
	@Override
	public int attackProc( final Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		
		KindOfWeapon wep = belongings.weapon();

		if (wep != null) damage = wep.proc( this, enemy, damage );

		if (buff(Talent.SpiritBladesTracker.class) != null
				&& Random.Int(10) < 3*pointsInTalent(Talent.SPIRIT_BLADES)){
			SpiritBow bow = belongings.getItem(SpiritBow.class);
			if (bow != null) damage = bow.proc( this, enemy, damage );
			buff(Talent.SpiritBladesTracker.class).detach();
		}

		damage = Talent.onAttackProc( this, enemy, damage );
		
		switch (subClass) {
		case SNIPER:
			if (wep instanceof MissileWeapon && !(wep instanceof SpiritBow.SpiritArrow
					|| wep instanceof WindBow.SpiritArrow
					|| wep instanceof PoisonBow.SpiritArrow
					|| wep instanceof GoldenBow.SpiritArrow
					|| wep instanceof NaturesBow.SpiritArrow) && enemy != this) {
				Actor.add(new Actor() {
					
					{
						actPriority = VFX_PRIO;
					}
					
					@Override
					protected boolean act() {
						if (enemy.isAlive()) {
							int bonusTurns = hasTalent(Talent.SHARED_UPGRADES) ? wep.buffedLvl() : 0;
							Buff.prolong(Hero.this, SnipersMark.class, SnipersMark.DURATION + bonusTurns).set(enemy.id(), bonusTurns);
						}
						Actor.remove(this);
						return true;
					}
				});
			}
			break;
		default:
		}
		
		return damage;
	}
	
	@Override
	public int defenseProc( Char enemy, int damage ) {
		
		if (damage > 0 && subClass == HeroSubClass.BERSERKER){
			Berserk berserk = Buff.affect(this, Berserk.class);
			berserk.damage(damage);
		}
		
		if (belongings.armor() != null) {
			damage = belongings.armor().proc( enemy, this, damage );
		}
		
		Earthroot.Armor armor = buff( Earthroot.Armor.class );
		if (armor != null) {
			int consumedArmor = 2*(damage-armor.absorb( damage ));
			damage = armor.absorb( damage );
			if (hero.subClass == HeroSubClass.RESEARCHER) {
				int healPercent = Math.max(1, Math.round(consumedArmor/5f));
				int healAmt = (hero.pointsInTalent(Talent.BIO_ENERGY) == 3) ? 2*healPercent : healPercent;
				healAmt = Math.min( healAmt, hero.HT - hero.HP );
				if (healAmt > 0 && hero.isAlive()) {
					hero.HP += healAmt;
					hero.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
					hero.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
				}
			}


		}

		WandOfLivingEarth.RockArmor rockArmor = buff(WandOfLivingEarth.RockArmor.class);
		if (rockArmor != null) {
			damage = rockArmor.absorb(damage);
		}

		if (hero.buff(LanceGuard.class) != null || hero.buff(SpearGuard.class) != null) {
			if (sprite != null && sprite.visible) {
				Sample.INSTANCE.play(Assets.Sounds.HIT_PARRY, 1, Random.Float(0.96f, 1.05f));
			}
		}
		
		return damage;
	}
	
	@Override
	public void damage( int dmg, Object src ) {
		if (buff(TimekeepersHourglass.timeStasis.class) != null)
			return;

		if (!(src instanceof Hunger || src instanceof Viscosity.DeferedDamage) && damageInterrupt) {
			interrupt();
			resting = false;
		}

		if (this.buff(Drowsy.class) != null){
			Buff.detach(this, Drowsy.class);
			GLog.w( Messages.get(this, "pain_resist") );
		}

		Endure.EndureTracker endure = buff(Endure.EndureTracker.class);
		if (!(src instanceof Char)){
			//reduce damage here if it isn't coming from a character (if it is we already reduced it)
			if (endure != null){
				dmg = endure.adjustDamageTaken(dmg);
			}
			//the same also applies to challenge scroll damage reduction
			if (buff(ScrollOfChallenge.ChallengeArena.class) != null){
				dmg *= 0.67f;
			}
		}

		if (Dungeon.hero.hasTalent(Talent.DEF_ENHANCE)) {
			dmg *= 1 - 0.05f * Dungeon.hero.pointsInTalent(Talent.DEF_ENHANCE);
		}

		CapeOfThorns.Thorns thorns = buff( CapeOfThorns.Thorns.class );
		if (thorns != null) {
			dmg = thorns.proc(dmg, (src instanceof Char ? (Char)src : null),  this);
		}

		dmg = (int)Math.ceil(dmg * RingOfTenacity.damageMultiplier( this ));

		//TODO improve this when I have proper damage source logic
		if (belongings.armor() != null && belongings.armor().hasGlyph(AntiMagic.class, this)
				&& AntiMagic.RESISTS.contains(src.getClass())){
			dmg -= AntiMagic.drRoll(belongings.armor().buffedLvl());
		}

		if (belongings.weapon() instanceof ObsidianShield && ObsidianShield.RESISTS.contains(src.getClass())) {
			dmg -= ObsidianShield.drRoll(belongings.weapon().buffedLvl());
		}

		if (buff(LanceGuard.class) != null && hero.belongings.weapon() instanceof LanceNShield && LanceNShield.RESISTS.contains(src.getClass())){
			dmg -= 2+hero.belongings.weapon.buffedLvl();
		}


		if (buff(Talent.WarriorFoodImmunity.class) != null){
			if (pointsInTalent(Talent.IRON_STOMACH) == 1)       dmg = Math.round(dmg*0.25f);
			else if (pointsInTalent(Talent.IRON_STOMACH) == 2)  dmg = Math.round(dmg*0.00f);
		}

		int preHP = HP + shielding();
		super.damage( dmg, src );
		int postHP = HP + shielding();
		int effectiveDamage = preHP - postHP;

		if (effectiveDamage <= 0) return;

		//flash red when hit for serious damage.
		float percentDMG = effectiveDamage / (float)preHP; //percent of current HP that was taken
		float percentHP = 1 - ((HT - postHP) / (float)HT); //percent health after damage was taken
		// The flash intensity increases primarily based on damage taken and secondarily on missing HP.
		float flashIntensity = 0.25f * (percentDMG * percentDMG) / percentHP;
		//if the intensity is very low don't flash at all
		if (flashIntensity >= 0.05f){
			flashIntensity = Math.min(1/3f, flashIntensity); //cap intensity at 1/3
			GameScene.flash( (int)(0xFF*flashIntensity) << 16 );
			if (isAlive()) {
				if (flashIntensity >= 1/6f) {
					Sample.INSTANCE.play(Assets.Sounds.HEALTH_CRITICAL, 1/3f + flashIntensity * 2f);
				} else {
					Sample.INSTANCE.play(Assets.Sounds.HEALTH_WARN, 1/3f + flashIntensity * 4f);
				}
			}
		}
	}
	
	public void checkVisibleMobs() {
		ArrayList<Mob> visible = new ArrayList<>();

		boolean newMob = false;

		Mob target = null;
		for (Mob m : Dungeon.level.mobs.toArray(new Mob[0])) {
			if (fieldOfView[ m.pos ] && m.alignment == Alignment.ENEMY) {
				visible.add(m);
				if (!visibleEnemies.contains( m )) {
					newMob = true;
				}

				if (!mindVisionEnemies.contains(m) && QuickSlotButton.autoAim(m) != -1){
					if (target == null){
						target = m;
					} else if (distance(target) > distance(m)) {
						target = m;
					}
					if (m instanceof Snake && Dungeon.level.distance(m.pos, pos) <= 4
							&& !Document.ADVENTURERS_GUIDE.isPageRead(Document.GUIDE_EXAMINING)){
						GLog.p(Messages.get(Guidebook.class, "hint"));
						GameScene.flashForDocument(Document.GUIDE_EXAMINING);
						//we set to read here to prevent this message popping up a bunch
						Document.ADVENTURERS_GUIDE.readPage(Document.GUIDE_EXAMINING);
					}
				}
			}
		}

		Char lastTarget = QuickSlotButton.lastTarget;
		if (target != null && (lastTarget == null ||
							!lastTarget.isAlive() ||
							lastTarget.alignment == Alignment.ALLY ||
							!fieldOfView[lastTarget.pos])){
			QuickSlotButton.target(target);
		}
		
		if (newMob) {
			interrupt();
			if (resting){
				Dungeon.observe();
				resting = false;
			}
		}

		visibleEnemies = visible;
	}
	
	public int visibleEnemies() {
		return visibleEnemies.size();
	}
	
	public Mob visibleEnemy( int index ) {
		return visibleEnemies.get(index % visibleEnemies.size());
	}
	
	private boolean walkingToVisibleTrapInFog = false;
	
	//FIXME this is a fairly crude way to track this, really it would be nice to have a short
	//history of hero actions
	public boolean justMoved = false;
	
	private boolean getCloser( final int target ) {

		if (target == pos)
			return false;

		if (rooted) {
			Camera.main.shake( 1, 1f );
			return false;
		}
		
		int step = -1;
		
		if (Dungeon.level.adjacent( pos, target )) {

			path = null;

			if (Actor.findChar( target ) == null) {
				if (Dungeon.level.pit[target] && !flying && !Dungeon.level.solid[target]) {
					if (!Chasm.jumpConfirmed){
						Chasm.heroJump(this);
						interrupt();
					} else {
						Chasm.heroFall(target);
					}
					return false;
				}
				if (Dungeon.level.passable[target] || Dungeon.level.avoid[target]) {
					step = target;
				}
				if (walkingToVisibleTrapInFog
						&& Dungeon.level.traps.get(target) != null
						&& Dungeon.level.traps.get(target).visible){
					return false;
				}
			}
			
		} else {

			boolean newPath = false;
			if (path == null || path.isEmpty() || !Dungeon.level.adjacent(pos, path.getFirst()))
				newPath = true;
			else if (path.getLast() != target)
				newPath = true;
			else {
				if (!Dungeon.level.passable[path.get(0)] || Actor.findChar(path.get(0)) != null) {
					newPath = true;
				}
			}

			if (newPath) {

				int len = Dungeon.level.length();
				boolean[] p = Dungeon.level.passable;
				boolean[] v = Dungeon.level.visited;
				boolean[] m = Dungeon.level.mapped;
				boolean[] passable = new boolean[len];
				for (int i = 0; i < len; i++) {
					passable[i] = p[i] && (v[i] || m[i]);
				}

				PathFinder.Path newpath = Dungeon.findPath(this, target, passable, fieldOfView, true);
				if (newpath != null && path != null && newpath.size() > 2*path.size()){
					path = null;
				} else {
					path = newpath;
				}
			}

			if (path == null) return false;
			step = path.removeFirst();

		}

		if (step != -1) {

			if (subClass == HeroSubClass.FREERUNNER){
				Buff.affect(this, Momentum.class).gainStack();
			}

			if (hero.belongings.weapon instanceof Lance) {
				Buff.affect(this, LanceBuff.class).setDamageFactor(1+(hero.speed()));
			}

			if (Dungeon.hero.subClass == HeroSubClass.RANGER && Random.Int(50) == 0){
				Buff.affect(Dungeon.hero, Haste.class,5);
			}

			if (hero.subClass == HeroSubClass.SLAYER && hero.buff(Demonization.class) == null) {
				Buff.affect(hero, Demonization.class).indicate();
			}

			float speed = speed();
			
			sprite.move(pos, step);
			move(step);

			spend( 1 / speed );
			justMoved = true;
			
			search(false);

			return true;

		} else {

			return false;
			
		}

	}
	
	public boolean handle( int cell ) {
		
		if (cell == -1) {
			return false;
		}

		if (fieldOfView == null || fieldOfView.length != Dungeon.level.length()){
			fieldOfView = new boolean[Dungeon.level.length()];
			Dungeon.level.updateFieldOfView( this, fieldOfView );
		}
		
		Char ch = Actor.findChar( cell );
		Heap heap = Dungeon.level.heaps.get( cell );
		
		if (Dungeon.level.map[cell] == Terrain.ALCHEMY && cell != pos) {
			
			curAction = new HeroAction.Alchemy( cell );
			
		} else if (fieldOfView[cell] && ch instanceof Mob) {

			if (ch.alignment != Alignment.ENEMY && ch.buff(Amok.class) == null) {
				curAction = new HeroAction.Interact( ch );
			} else {
				curAction = new HeroAction.Attack( ch );
			}

		} else if (heap != null
				//moving to an item doesn't auto-pickup when enemies are near...
				&& (visibleEnemies.size() == 0 || cell == pos ||
				//...but only for standard heaps, chests and similar open as normal.
				(heap.type != Type.HEAP && heap.type != Type.FOR_SALE))) {

			switch (heap.type) {
			case HEAP:
				curAction = new HeroAction.PickUp( cell );
				break;
			case FOR_SALE:
				curAction = heap.size() == 1 && heap.peek().value() > 0 ?
					new HeroAction.Buy( cell ) :
					new HeroAction.PickUp( cell );
				break;
			default:
				curAction = new HeroAction.OpenChest( cell );
			}
			
		} else if (Dungeon.level.map[cell] == Terrain.LOCKED_DOOR || Dungeon.level.map[cell] == Terrain.LOCKED_EXIT) {
			
			curAction = new HeroAction.Unlock( cell );
			
		} else if ((cell == Dungeon.level.exit || Dungeon.level.map[cell] == Terrain.EXIT || Dungeon.level.map[cell] == Terrain.UNLOCKED_EXIT)
				&& Dungeon.depth < 26) {
			
			curAction = new HeroAction.Descend( cell );
			
		} else if (cell == Dungeon.level.entrance || Dungeon.level.map[cell] == Terrain.ENTRANCE) {
			
			curAction = new HeroAction.Ascend( cell );
			
		} else  {
			
			if (!Dungeon.level.visited[cell] && !Dungeon.level.mapped[cell]
					&& Dungeon.level.traps.get(cell) != null && Dungeon.level.traps.get(cell).visible) {
				walkingToVisibleTrapInFog = true;
			} else {
				walkingToVisibleTrapInFog = false;
			}
			
			curAction = new HeroAction.Move( cell );
			lastAction = null;
			
		}

		return true;
	}
	
	public void earnExp( int exp, Class source ) {
		
		this.exp += exp;
		float percent = exp/(float)maxExp();

		EtherealChains.chainsRecharge chains = buff(EtherealChains.chainsRecharge.class);
		if (chains != null) chains.gainExp(percent);

		HornOfPlenty.hornRecharge horn = buff(HornOfPlenty.hornRecharge.class);
		if (horn != null) horn.gainCharge(percent);
		
		AlchemistsToolkit.kitEnergy kit = buff(AlchemistsToolkit.kitEnergy.class);
		if (kit != null) kit.gainCharge(percent);
		
		Berserk berserk = buff(Berserk.class);
		if (berserk != null) berserk.recover(percent);
		
		if (source != PotionOfExperience.class) {
			for (Item i : belongings) {
				i.onHeroGainExp(percent, this);
			}
			if (buff(Talent.RejuvenatingStepsFurrow.class) != null){
				buff(Talent.RejuvenatingStepsFurrow.class).countDown(percent*200f);
				if (buff(Talent.RejuvenatingStepsFurrow.class).count() <= 0){
					buff(Talent.RejuvenatingStepsFurrow.class).detach();
				}
			}
		}
		
		boolean levelUp = false;
		while (this.exp >= maxExp()) {
			this.exp -= maxExp();
			if (lvl < MAX_LEVEL) {
				lvl++;
				levelUp = true;
				
				if (buff(ElixirOfMight.HTBoost.class) != null){
					buff(ElixirOfMight.HTBoost.class).onLevelUp();
				}
				
				updateHT( true );
				attackSkill++;
				defenseSkill++;

			} else {
				Buff.prolong(this, Bless.class, Bless.DURATION);
				this.exp = 0;

				GLog.newLine();
				GLog.p( Messages.get(this, "level_cap"));
				Sample.INSTANCE.play( Assets.Sounds.LEVELUP );
			}
			
		}
		
		if (levelUp) {
			
			if (sprite != null) {
				GLog.newLine();
				GLog.p( Messages.get(this, "new_level") );
				sprite.showStatus( CharSprite.POSITIVE, Messages.get(Hero.class, "level_up") );
				Sample.INSTANCE.play( Assets.Sounds.LEVELUP );
				if (lvl < Talent.tierLevelThresholds[Talent.MAX_TALENT_TIERS+1]){
					GLog.newLine();
					GLog.p( Messages.get(this, "new_talent") );
					StatusPane.talentBlink = 10f;
					WndHero.lastIdx = 1;
				}
			}
			
			updateQuickslot();
			
			Badges.validateLevelReached();
		}
	}
	
	public int maxExp() {
		return maxExp( lvl );
	}
	
	public static int maxExp( int lvl ){
		return 5 + lvl * 5;
	}
	
	public boolean isStarving() {
		return Buff.affect(this, Hunger.class).isStarving();
	}
	
	@Override
	public void add( Buff buff ) {

		if (buff(TimekeepersHourglass.timeStasis.class) != null)
			return;

		super.add( buff );

		if (sprite != null && buffs().contains(buff)) {
			String msg = buff.heroMessage();
			if (msg != null){
				GLog.w(msg);
			}

			if (buff instanceof Paralysis || buff instanceof Vertigo) {
				interrupt();
			}

		}
		
		BuffIndicator.refreshHero();
	}
	
	@Override
	public void remove( Buff buff ) {
		super.remove( buff );

		BuffIndicator.refreshHero();
	}
	
	@Override
	public float stealth() {
		float stealth = super.stealth();
		
		if (belongings.armor() != null){
			stealth = belongings.armor().stealthFactor(this, stealth);
		}
		
		return stealth;
	}
	
	@Override
	public void die( Object cause ) {
		
		curAction = null;

		Ankh ankh = null;

		//look for ankhs in player inventory, prioritize ones which are blessed.
		for (Ankh i : belongings.getAllItems(Ankh.class)){
			if (ankh == null || i.isBlessed()) {
				ankh = i;
			}
		}

		if (ankh != null) {
			interrupt();
			resting = false;

			if (ankh.isBlessed()) {
				this.HP = HT / 4;

				PotionOfHealing.cure(this);
				Buff.prolong(this, AnkhInvulnerability.class, AnkhInvulnerability.DURATION);

				SpellSprite.show(this, SpellSprite.ANKH);
				GameScene.flash(0x80FFFF40);
				Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
				GLog.w(Messages.get(this, "revive"));
				Statistics.ankhsUsed++;

				ankh.detach(belongings.backpack);

				for (Char ch : Actor.chars()) {
					if (ch instanceof DriedRose.GhostHero) {
						((DriedRose.GhostHero) ch).sayAnhk();
						return;
					}
				}
			} else {

				//this is hacky, basically we want to declare that a wndResurrect exists before
				//it actually gets created. This is important so that the game knows to not
				//delete the run or submit it to rankings, because a WndResurrect is about to exist
				//this is needed because the actual creation of the window is delayed here
				WndResurrect.instance = new Object();
				Ankh finalAnkh = ankh;
				Game.runOnRenderThread(new Callback() {
					@Override
					public void call() {
						GameScene.show( new WndResurrect(finalAnkh) );
					}
				});

			}
			return;
		}
		
		Actor.fixTime();
		super.die( cause );
		reallyDie( cause );
	}
	
	public static void reallyDie( Object cause ) {
		
		int length = Dungeon.level.length();
		int[] map = Dungeon.level.map;
		boolean[] visited = Dungeon.level.visited;
		boolean[] discoverable = Dungeon.level.discoverable;
		
		for (int i=0; i < length; i++) {
			
			int terr = map[i];
			
			if (discoverable[i]) {
				
				visited[i] = true;
				if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {
					Dungeon.level.discover( i );
				}
			}
		}
		
		Bones.leave();
		
		Dungeon.observe();
		GameScene.updateFog();
				
		Dungeon.hero.belongings.identify();

		int pos = Dungeon.hero.pos;

		ArrayList<Integer> passable = new ArrayList<>();
		for (Integer ofs : PathFinder.NEIGHBOURS8) {
			int cell = pos + ofs;
			if ((Dungeon.level.passable[cell] || Dungeon.level.avoid[cell]) && Dungeon.level.heaps.get( cell ) == null) {
				passable.add( cell );
			}
		}
		Collections.shuffle( passable );

		ArrayList<Item> items = new ArrayList<>(Dungeon.hero.belongings.backpack.items);
		for (Integer cell : passable) {
			if (items.isEmpty()) {
				break;
			}

			Item item = Random.element( items );
			Dungeon.level.drop( item, cell ).sprite.drop( pos );
			items.remove( item );
		}

		for (Char c : Actor.chars()){
			if (c instanceof DriedRose.GhostHero){
				((DriedRose.GhostHero) c).sayHeroKilled();
			}
		}

		GameScene.gameOver();
		
		if (cause instanceof Hero.Doom) {
			((Hero.Doom)cause).onDeath();
		}
		
		Dungeon.deleteGame( GamesInProgress.curSlot, true );
	}

	//effectively cache this buff to prevent having to call buff(...) a bunch.
	//This is relevant because we call isAlive during drawing, which has both performance
	//and thread coordination implications if that method calls buff(...) frequently
	private Berserk berserk;

	@Override
	public boolean isAlive() {
		
		if (HP <= 0){
			if (berserk == null) berserk = buff(Berserk.class);
			return berserk != null && berserk.berserking();
		} else {
			berserk = null;
			return super.isAlive();
		}
	}

	@Override
	public void move(int step, boolean travelling) {
		boolean wasHighGrass = Dungeon.level.map[step] == Terrain.HIGH_GRASS;

		super.move( step, travelling);
		
		if (!flying && travelling) {
			if (Dungeon.level.water[pos]) {
				Sample.INSTANCE.play( Assets.Sounds.WATER, 1, Random.Float( 0.8f, 1.25f ) );
			} else if (Dungeon.level.map[pos] == Terrain.EMPTY_SP) {
				Sample.INSTANCE.play( Assets.Sounds.STURDY, 1, Random.Float( 0.96f, 1.05f ) );
			} else if (Dungeon.level.map[pos] == Terrain.GRASS
					|| Dungeon.level.map[pos] == Terrain.EMBERS
					|| Dungeon.level.map[pos] == Terrain.FURROWED_GRASS){
				if (step == pos && wasHighGrass) {
					Sample.INSTANCE.play(Assets.Sounds.TRAMPLE, 1, Random.Float( 0.96f, 1.05f ) );
				} else {
					Sample.INSTANCE.play( Assets.Sounds.GRASS, 1, Random.Float( 0.96f, 1.05f ) );
				}
			} else {
				Sample.INSTANCE.play( Assets.Sounds.STEP, 1, Random.Float( 0.96f, 1.05f ) );
			}
		}
	}
	
	@Override
	public void onAttackComplete() {

		int exStr = 0;
		if (hero.belongings.weapon != null) {
			exStr = hero.STR() - hero.belongings.weapon.STRReq();
		}
		AttackIndicator.target(enemy);
		
		boolean hit = attack( enemy );
		
		Invisibility.dispel();
		spend( attackDelay() );

		if (hero.buff(Lead.class) != null) {

			if (hit) {
				Buff.affect(enemy, Bleeding.class).set(Math.min(3+hero.pointsInTalent(Talent.DEEP_SCAR), Math.round(exStr/2f)));
			}
			if (!enemy.isAlive() && Dungeon.hero.hasTalent(Talent.FAST_LEAD) && Random.Int(3) < Dungeon.hero.pointsInTalent(Talent.FAST_LEAD)) {
				Buff.affect(hero, Lead.class);
			} else {
				hero.buff(Lead.class).detach();
			}
		}

		if (hit && hero.hasTalent(Talent.MIND_FOCUS)) {
			if (Random.Int(3) < hero.pointsInTalent(Talent.MIND_FOCUS)) {
				int healAmt = 1;
				healAmt = Math.min( healAmt, hero.HT - hero.HP );
				if (healAmt > 0 && hero.isAlive()) {
					hero.HP += healAmt;
					hero.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
					hero.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
				}
			}
		}

		if (hit && subClass == HeroSubClass.GLADIATOR){
			Buff.affect( this, Combo.class ).hit( enemy );
		}

		if (hit && subClass == HeroSubClass.VETERAN){
			if (Dungeon.hero.belongings.weapon() instanceof CrudePistol
					|| Dungeon.hero.belongings.weapon() instanceof CrudePistolAP
					|| Dungeon.hero.belongings.weapon() instanceof CrudePistolHP
					|| Dungeon.hero.belongings.weapon() instanceof Pistol
					|| Dungeon.hero.belongings.weapon() instanceof PistolAP
					|| Dungeon.hero.belongings.weapon() instanceof PistolHP
					|| Dungeon.hero.belongings.weapon() instanceof GoldenPistol
					|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolAP
					|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolHP
					|| Dungeon.hero.belongings.weapon() instanceof Handgun
					|| Dungeon.hero.belongings.weapon() instanceof HandgunAP
					|| Dungeon.hero.belongings.weapon() instanceof HandgunHP
					|| Dungeon.hero.belongings.weapon() instanceof Magnum
					|| Dungeon.hero.belongings.weapon() instanceof MagnumAP
					|| Dungeon.hero.belongings.weapon() instanceof MagnumHP
					|| Dungeon.hero.belongings.weapon() instanceof LargeHandgun
					|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunAP
					|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunHP
					|| Dungeon.hero.belongings.weapon() instanceof DualPistol
					|| Dungeon.hero.belongings.weapon() instanceof DualPistolAP
					|| Dungeon.hero.belongings.weapon() instanceof DualPistolHP
					|| Dungeon.hero.belongings.weapon() instanceof SubMachinegun
					|| Dungeon.hero.belongings.weapon() instanceof SubMachinegunAP
					|| Dungeon.hero.belongings.weapon() instanceof SubMachinegunHP
					|| Dungeon.hero.belongings.weapon() instanceof AssultRifle
					|| Dungeon.hero.belongings.weapon() instanceof AssultRifleAP
					|| Dungeon.hero.belongings.weapon() instanceof AssultRifleHP
					|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegun
					|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegunAP
					|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegunHP
					|| Dungeon.hero.belongings.weapon() instanceof MiniGun
					|| Dungeon.hero.belongings.weapon() instanceof MiniGunAP
					|| Dungeon.hero.belongings.weapon() instanceof MiniGunHP
					|| Dungeon.hero.belongings.weapon() instanceof HuntingRifle
					|| Dungeon.hero.belongings.weapon() instanceof HuntingRifleAP
					|| Dungeon.hero.belongings.weapon() instanceof HuntingRifleHP
					|| Dungeon.hero.belongings.weapon() instanceof SniperRifle
					|| Dungeon.hero.belongings.weapon() instanceof SniperRifleAP
					|| Dungeon.hero.belongings.weapon() instanceof SniperRifleHP
					|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifle
					|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifleAP
					|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifleHP
					|| Dungeon.hero.belongings.weapon() instanceof ShotGun
					|| Dungeon.hero.belongings.weapon() instanceof ShotGunAP
					|| Dungeon.hero.belongings.weapon() instanceof ShotGunHP
					|| Dungeon.hero.belongings.weapon() instanceof SPAS
					|| Dungeon.hero.belongings.weapon() instanceof SPASAP
					|| Dungeon.hero.belongings.weapon() instanceof SPASHP
					|| Dungeon.hero.belongings.weapon() instanceof RocketLauncher
					|| Dungeon.hero.belongings.weapon() instanceof RPG7
					|| Dungeon.hero.belongings.weapon() instanceof FlameThrower
					|| Dungeon.hero.belongings.weapon() instanceof FlameThrowerAP
					|| Dungeon.hero.belongings.weapon() instanceof FlameThrowerHP
					|| Dungeon.hero.belongings.weapon() instanceof PlasmaCannon
					|| Dungeon.hero.belongings.weapon() instanceof PlasmaCannonAP
					|| Dungeon.hero.belongings.weapon() instanceof PlasmaCannonHP
					|| Dungeon.hero.belongings.weapon() instanceof GrenadeLauncher
					|| Dungeon.hero.belongings.weapon() instanceof GrenadeLauncherAP
					|| Dungeon.hero.belongings.weapon() instanceof GrenadeLauncherHP
			) {
				Buff.affect( this, Focusing.class ).hit( enemy );
			}
		}

		if (hit && hero.hasTalent(Talent.BAYONET) && hero.buff(ReinforcedArmor.reinforcedArmorTracker.class) != null){
			if (Dungeon.hero.belongings.weapon() instanceof CrudePistol
					|| Dungeon.hero.belongings.weapon() instanceof CrudePistolAP
					|| Dungeon.hero.belongings.weapon() instanceof CrudePistolHP
					|| Dungeon.hero.belongings.weapon() instanceof Pistol
					|| Dungeon.hero.belongings.weapon() instanceof PistolAP
					|| Dungeon.hero.belongings.weapon() instanceof PistolHP
					|| Dungeon.hero.belongings.weapon() instanceof GoldenPistol
					|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolAP
					|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolHP
					|| Dungeon.hero.belongings.weapon() instanceof Handgun
					|| Dungeon.hero.belongings.weapon() instanceof HandgunAP
					|| Dungeon.hero.belongings.weapon() instanceof HandgunHP
					|| Dungeon.hero.belongings.weapon() instanceof Magnum
					|| Dungeon.hero.belongings.weapon() instanceof MagnumAP
					|| Dungeon.hero.belongings.weapon() instanceof MagnumHP
					|| Dungeon.hero.belongings.weapon() instanceof LargeHandgun
					|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunAP
					|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunHP
					|| Dungeon.hero.belongings.weapon() instanceof DualPistol
					|| Dungeon.hero.belongings.weapon() instanceof DualPistolAP
					|| Dungeon.hero.belongings.weapon() instanceof DualPistolHP
					|| Dungeon.hero.belongings.weapon() instanceof SubMachinegun
					|| Dungeon.hero.belongings.weapon() instanceof SubMachinegunAP
					|| Dungeon.hero.belongings.weapon() instanceof SubMachinegunHP
					|| Dungeon.hero.belongings.weapon() instanceof AssultRifle
					|| Dungeon.hero.belongings.weapon() instanceof AssultRifleAP
					|| Dungeon.hero.belongings.weapon() instanceof AssultRifleHP
					|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegun
					|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegunAP
					|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegunHP
					|| Dungeon.hero.belongings.weapon() instanceof MiniGun
					|| Dungeon.hero.belongings.weapon() instanceof MiniGunAP
					|| Dungeon.hero.belongings.weapon() instanceof MiniGunHP
					|| Dungeon.hero.belongings.weapon() instanceof HuntingRifle
					|| Dungeon.hero.belongings.weapon() instanceof HuntingRifleAP
					|| Dungeon.hero.belongings.weapon() instanceof HuntingRifleHP
					|| Dungeon.hero.belongings.weapon() instanceof SniperRifle
					|| Dungeon.hero.belongings.weapon() instanceof SniperRifleAP
					|| Dungeon.hero.belongings.weapon() instanceof SniperRifleHP
					|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifle
					|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifleAP
					|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifleHP
					|| Dungeon.hero.belongings.weapon() instanceof ShotGun
					|| Dungeon.hero.belongings.weapon() instanceof ShotGunAP
					|| Dungeon.hero.belongings.weapon() instanceof ShotGunHP
					|| Dungeon.hero.belongings.weapon() instanceof SPAS
					|| Dungeon.hero.belongings.weapon() instanceof SPASAP
					|| Dungeon.hero.belongings.weapon() instanceof SPASHP
					|| Dungeon.hero.belongings.weapon() instanceof RocketLauncher
					|| Dungeon.hero.belongings.weapon() instanceof RPG7
					|| Dungeon.hero.belongings.weapon() instanceof FlameThrower
					|| Dungeon.hero.belongings.weapon() instanceof FlameThrowerAP
					|| Dungeon.hero.belongings.weapon() instanceof FlameThrowerHP
					|| Dungeon.hero.belongings.weapon() instanceof PlasmaCannon
					|| Dungeon.hero.belongings.weapon() instanceof PlasmaCannonAP
					|| Dungeon.hero.belongings.weapon() instanceof PlasmaCannonHP
					|| Dungeon.hero.belongings.weapon() instanceof GrenadeLauncher
					|| Dungeon.hero.belongings.weapon() instanceof GrenadeLauncherAP
					|| Dungeon.hero.belongings.weapon() instanceof GrenadeLauncherHP
			) {
				Buff.affect( enemy, Bleeding.class ).set( 4 + hero.pointsInTalent(Talent.BAYONET));
			}
		}

		if (hero.subClass == HeroSubClass.CHASER && hero.hasTalent(Talent.CHAIN_CLOCK) && ((Mob) enemy).surprisedBy(hero) && hero.buff(Talent.ChainCooldown.class) == null){
			Buff.affect( this, Invisibility.class, 1f * hero.pointsInTalent(Talent.CHAIN_CLOCK));
			Buff.affect( this, Haste.class, 1f * hero.pointsInTalent(Talent.CHAIN_CLOCK));
			Buff.affect( this, Talent.ChainCooldown.class, 10f);
			Sample.INSTANCE.play( Assets.Sounds.MELD );
		}

		if (hero.subClass == HeroSubClass.CHASER && hero.hasTalent(Talent.LETHAL_SURPRISE) && ((Mob) enemy).surprisedBy(hero) && !enemy.isAlive() && hero.buff(Talent.LethalCooldown.class) == null) {
			if (hero.pointsInTalent(Talent.LETHAL_SURPRISE) >= 1) {
				for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
					if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos]) {
						Buff.affect( mob, Vulnerable.class, 1f);
					}
				}
				Buff.affect(hero, Talent.LethalCooldown.class, 5f);
			}
			if (hero.pointsInTalent(Talent.LETHAL_SURPRISE) >= 2) {
				for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
					if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos]) {
						Buff.affect( mob, Paralysis.class, 1f);
					}
				}
			}
			if (hero.pointsInTalent(Talent.LETHAL_SURPRISE) == 3) {
				Buff.affect(hero, Swiftthistle.TimeBubble.class).twoTurns();
			}
		}

		if (hero.belongings.weapon instanceof TestWeapon) {
			Buff.affect(hero, Awareness.class, 10f);
		}

		if (hit && hero.belongings.weapon instanceof ChainWhip && Random.Int(100) < 10+2*hero.belongings.weapon.buffedLvl()) {
			Buff.affect( enemy, Paralysis.class, 3f);
		}

		if (hit && (hero.belongings.weapon instanceof FlameScimitar
				 || hero.belongings.weapon instanceof FrostScimitar
				 || hero.belongings.weapon instanceof PoisonScimitar
				 || hero.belongings.weapon instanceof ElectroScimitar)){
			if (hero.belongings.weapon instanceof FlameScimitar && Random.Int(2) == 0) {
				if (enemy.buff(Burning.class) != null){
					Buff.affect(enemy, Burning.class).reignite(enemy, 8f);
					int burnDamage = Random.NormalIntRange( 1, 3 + Dungeon.depth/4 );
					enemy.damage( Math.round(burnDamage * 0.67f), this );
				} else {
					Buff.affect(enemy, Burning.class).reignite(enemy, 8f);
				}
			}

			if (hero.belongings.weapon instanceof FrostScimitar && Random.Int(2) == 0) {
				//adds 3 turns of chill per proc, with a cap of 6 turns
				float durationToAdd = 3f;
				Chill existing = enemy.buff(Chill.class);
				if (existing != null){
					durationToAdd = Math.min(durationToAdd, 6f-existing.cooldown());
				}

				Buff.affect( enemy, Chill.class, durationToAdd );
				Splash.at( enemy.sprite.center(), 0xFFB2D6FF, 5);
			}

			if (hero.belongings.weapon instanceof PoisonScimitar && Random.Int(4) <= Math.floor(hero.belongings.weapon.buffedLvl()/4f)) {
				if (enemy.buff(Poison.class) != null) {
					Buff.affect( enemy, Poison.class).extend(2);
				} else {
					Buff.affect( enemy, Poison.class).set(2);
				}
			}

			if (hero.belongings.weapon instanceof ElectroScimitar && Random.Int(10) <= Math.floor(hero.belongings.weapon.buffedLvl()/4f)) {
				Buff.affect(enemy, Paralysis.class, 2f);
				enemy.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
				enemy.sprite.flash();
				Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
			}
		}

		if (hit && hero.belongings.weapon instanceof Bible) {
			if (hero.buff(Bless.class) == null) {
				Buff.affect( this, Bless.class, 2f);
			} else if (hero.buff(PotionOfCleansing.Cleanse.class) == null) {
				Buff.affect( this, PotionOfCleansing.Cleanse.class, 2f);
			} else if (hero.buff(Adrenaline.class) == null) {
				Buff.affect( this, Adrenaline.class, 2f);
			} else {
				int healAmt = 1;
				healAmt = Math.min( healAmt, hero.HT - hero.HP );
				if (healAmt > 0 && hero.isAlive()) {
					hero.HP += healAmt;
					hero.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
					hero.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
				}
			}
		}

		if (hit && hero.belongings.weapon instanceof HolySword) {
			if (hero.STR() >= ((HolySword) hero.belongings.weapon).STRReq()) {
				int healAmt = 5+hero.belongings.weapon.buffedLvl();
				healAmt = Math.min( healAmt, hero.HT - hero.HP );
				if (healAmt > 0 && hero.isAlive()) {
					hero.HP += healAmt;
					hero.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
					hero.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
				}
			}
		}

		if (hit && hero.belongings.weapon instanceof UnholyBible) {
			switch (Random.Int(15)) {
				case 0: case 1: default:
					Buff.affect( enemy, Weakness.class, 3f );
					break;
				case 2: case 3:
					Buff.affect( enemy, Vulnerable.class, 3f );
					break;
				case 4:
					Buff.affect( enemy, Cripple.class, 3f );
					break;
				case 5:
					Buff.affect( enemy, Blindness.class, 3f );
					break;
				case 6:
					Buff.affect( enemy, Terror.class, 3f );
					break;
				case 7: case 8: case 9:
					Buff.affect( enemy, Amok.class, 3f );
					break;
				case 10: case 11:
					Buff.affect( enemy, Slow.class, 3f );
					break;
				case 12: case 13:
					Buff.affect( enemy, Hex.class, 3f );
					break;
				case 14:
					Buff.affect( enemy, Paralysis.class, 3f );
					break;
			}
			if (Random.Int(100) <= Math.min(hero.belongings.weapon.buffedLvl(), 9)) {					//1% base, +1% per lvl, max 10%
				Buff.affect( enemy, com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom.class);
			}
		}

		if (hit && hero.belongings.weapon == null && hero.subClass == HeroSubClass.FIGHTER) {
			if (hit && hero.pointsInTalent(Talent.VITAL_ATTACK) >= 1 && Random.Int(5) == 0){
				Buff.affect( enemy, Weakness.class, 3f );
			}
			if (hit && hero.pointsInTalent(Talent.VITAL_ATTACK) >= 2 && Random.Int(5) == 0){
				Buff.affect( enemy, Vulnerable.class, 3f );
			}
			if (hit && hero.pointsInTalent(Talent.VITAL_ATTACK) == 3 && Random.Int(10) == 0){
				Buff.affect( enemy, Paralysis.class, 3f );
			}
			if (hit && hero.pointsInTalent(Talent.MIND_PRACTICE) >= 1 && Random.Int(10) == 0){
				Buff.affect( this, Adrenaline.class, 3f );
			}
			if (hit && hero.pointsInTalent(Talent.MIND_PRACTICE) >= 2 && Random.Int(10) == 0){
				Buff.affect( this, Bless.class, 3f );
			}
			if (hit && hero.pointsInTalent(Talent.MIND_PRACTICE) == 3 && Random.Int(10) == 0){
				Buff.affect(this, Healing.class).setHeal((int) (3), 0, 1);
			}
		}
		if (hit && hero.subClass == HeroSubClass.ENGINEER) {
			if (Dungeon.hero.belongings.weapon() instanceof CrudePistol
					|| Dungeon.hero.belongings.weapon() instanceof CrudePistolAP
					|| Dungeon.hero.belongings.weapon() instanceof CrudePistolHP
					|| Dungeon.hero.belongings.weapon() instanceof Pistol
					|| Dungeon.hero.belongings.weapon() instanceof PistolAP
					|| Dungeon.hero.belongings.weapon() instanceof PistolHP
					|| Dungeon.hero.belongings.weapon() instanceof GoldenPistol
					|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolAP
					|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolHP
					|| Dungeon.hero.belongings.weapon() instanceof Handgun
					|| Dungeon.hero.belongings.weapon() instanceof HandgunAP
					|| Dungeon.hero.belongings.weapon() instanceof HandgunHP
					|| Dungeon.hero.belongings.weapon() instanceof Magnum
					|| Dungeon.hero.belongings.weapon() instanceof MagnumAP
					|| Dungeon.hero.belongings.weapon() instanceof MagnumHP
					|| Dungeon.hero.belongings.weapon() instanceof LargeHandgun
					|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunAP
					|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunHP
					|| Dungeon.hero.belongings.weapon() instanceof DualPistol
					|| Dungeon.hero.belongings.weapon() instanceof DualPistolAP
					|| Dungeon.hero.belongings.weapon() instanceof DualPistolHP
					|| Dungeon.hero.belongings.weapon() instanceof SubMachinegun
					|| Dungeon.hero.belongings.weapon() instanceof SubMachinegunAP
					|| Dungeon.hero.belongings.weapon() instanceof SubMachinegunHP
					|| Dungeon.hero.belongings.weapon() instanceof AssultRifle
					|| Dungeon.hero.belongings.weapon() instanceof AssultRifleAP
					|| Dungeon.hero.belongings.weapon() instanceof AssultRifleHP
					|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegun
					|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegunAP
					|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegunHP
					|| Dungeon.hero.belongings.weapon() instanceof MiniGun
					|| Dungeon.hero.belongings.weapon() instanceof MiniGunAP
					|| Dungeon.hero.belongings.weapon() instanceof MiniGunHP
					|| Dungeon.hero.belongings.weapon() instanceof HuntingRifle
					|| Dungeon.hero.belongings.weapon() instanceof HuntingRifleAP
					|| Dungeon.hero.belongings.weapon() instanceof HuntingRifleHP
					|| Dungeon.hero.belongings.weapon() instanceof SniperRifle
					|| Dungeon.hero.belongings.weapon() instanceof SniperRifleAP
					|| Dungeon.hero.belongings.weapon() instanceof SniperRifleHP
					|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifle
					|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifleAP
					|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifleHP
					|| Dungeon.hero.belongings.weapon() instanceof ShotGun
					|| Dungeon.hero.belongings.weapon() instanceof ShotGunAP
					|| Dungeon.hero.belongings.weapon() instanceof ShotGunHP
					|| Dungeon.hero.belongings.weapon() instanceof SPAS
					|| Dungeon.hero.belongings.weapon() instanceof SPASAP
					|| Dungeon.hero.belongings.weapon() instanceof SPASHP
					|| Dungeon.hero.belongings.weapon() instanceof RocketLauncher
					|| Dungeon.hero.belongings.weapon() instanceof RPG7
					|| Dungeon.hero.belongings.weapon() instanceof FlameThrower
					|| Dungeon.hero.belongings.weapon() instanceof FlameThrowerAP
					|| Dungeon.hero.belongings.weapon() instanceof FlameThrowerHP
					|| Dungeon.hero.belongings.weapon() instanceof PlasmaCannon
					|| Dungeon.hero.belongings.weapon() instanceof PlasmaCannonAP
					|| Dungeon.hero.belongings.weapon() instanceof PlasmaCannonHP
					|| Dungeon.hero.belongings.weapon() instanceof GrenadeLauncher
					|| Dungeon.hero.belongings.weapon() instanceof GrenadeLauncherAP
					|| Dungeon.hero.belongings.weapon() instanceof GrenadeLauncherHP
			) {
				if (hero.pointsInTalent(Talent.CONNECTING_CHARGER) >= 1 && Random.Int(5) == 0 && hero.pointsInTalent(Talent.CONNECTING_CHARGER) < 3) {
					Buff.affect(this, Recharging.class, 1f);
				}
				if (hero.pointsInTalent(Talent.CONNECTING_CHARGER) == 2 && Random.Int(5) == 0 && hero.pointsInTalent(Talent.CONNECTING_CHARGER) < 3) {
					Buff.affect(this, ArtifactRecharge.class).set( 1 );
				}
				if (hero.pointsInTalent(Talent.CONNECTING_CHARGER) == 3) {
					if (Random.Int(4) == 0) {
						Buff.affect(this, Recharging.class, 2f);
					}
					if (Random.Int(4) == 0) {
						Buff.affect(this, ArtifactRecharge.class).prolong( 2 );
					}
				}
				if (Random.Int(5) == 0) {
					if (Random.Int(1) == 0) {
						Buff.affect(enemy, Paralysis.class, 2f);
						enemy.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
						enemy.sprite.flash();
						Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
					}
					Buff.affect(this, Barrier.class).setShield(10);
				}
			}
		}

		//if (hit && hero.subClass == HeroSubClass.RANGER) {
		//	if (hero.belongings.weapon instanceof CrudePistol
		//		|| hero.belongings.weapon instanceof CrudePistolAP
		//		|| hero.belongings.weapon instanceof CrudePistolHP
		//		|| hero.belongings.weapon instanceof Pistol
		//		|| hero.belongings.weapon instanceof PistolAP
		//		|| hero.belongings.weapon instanceof PistolHP
		//		|| hero.belongings.weapon instanceof GoldenPistol
		//		|| hero.belongings.weapon instanceof GoldenPistolAP
		//		|| hero.belongings.weapon instanceof GoldenPistolHP
		//		|| hero.belongings.weapon instanceof Handgun
		//		|| hero.belongings.weapon instanceof HandgunAP
		//		|| hero.belongings.weapon instanceof HandgunHP
		//		|| hero.belongings.weapon instanceof Magnum
		//		|| hero.belongings.weapon instanceof MagnumAP
		//		|| hero.belongings.weapon instanceof MagnumHP
		//		|| hero.belongings.weapon instanceof LargeHandgun
		//		|| hero.belongings.weapon instanceof LargeHandgunAP
		//		|| hero.belongings.weapon instanceof LargeHandgunHP) {

		//	}
		//}

		curAction = null;

		super.onAttackComplete();
	}
	
	@Override
	public void onMotionComplete() {
		GameScene.checkKeyHold();
	}
	
	@Override
	public void onOperateComplete() {
		
		if (curAction instanceof HeroAction.Unlock) {

			int doorCell = ((HeroAction.Unlock)curAction).dst;
			int door = Dungeon.level.map[doorCell];
			
			if (Dungeon.level.distance(pos, doorCell) <= 1) {
				boolean hasKey = true;
				if (door == Terrain.LOCKED_DOOR) {
					hasKey = Notes.remove(new IronKey(Dungeon.depth));
					if (hasKey) Level.set(doorCell, Terrain.DOOR);
				} else {
					hasKey = Notes.remove(new SkeletonKey(Dungeon.depth));
					if (hasKey) Level.set(doorCell, Terrain.UNLOCKED_EXIT);
				}
				
				if (hasKey) {
					GameScene.updateKeyDisplay();
					Level.set(doorCell, door == Terrain.LOCKED_DOOR ? Terrain.DOOR : Terrain.UNLOCKED_EXIT);
					GameScene.updateMap(doorCell);
					spend(Key.TIME_TO_UNLOCK);
				}
			}
			
		} else if (curAction instanceof HeroAction.OpenChest) {
			
			Heap heap = Dungeon.level.heaps.get( ((HeroAction.OpenChest)curAction).dst );
			
			if (Dungeon.level.distance(pos, heap.pos) <= 1){
				boolean hasKey = true;
				if (heap.type == Type.SKELETON || heap.type == Type.REMAINS) {
					Sample.INSTANCE.play( Assets.Sounds.BONES );
				} else if (heap.type == Type.LOCKED_CHEST){
					hasKey = Notes.remove(new GoldenKey(Dungeon.depth));
				} else if (heap.type == Type.CRYSTAL_CHEST){
					hasKey = Notes.remove(new CrystalKey(Dungeon.depth));
				}
				
				if (hasKey) {
					GameScene.updateKeyDisplay();
					heap.open(this);
					spend(Key.TIME_TO_UNLOCK);
				}
			}
			
		}
		curAction = null;

		super.onOperateComplete();
	}

	@Override
	public boolean isImmune(Class effect) {
		if (effect == Burning.class
				&& belongings.armor() != null
				&& belongings.armor().hasGlyph(Brimstone.class, this)){
			return true;
		}
		return super.isImmune(effect);
	}

	@Override
	public boolean isInvulnerable(Class effect) {
		return buff(AnkhInvulnerability.class) != null;
	}

	public boolean search( boolean intentional ) {
		
		if (!isAlive()) return false;
		
		boolean smthFound = false;

		boolean circular = pointsInTalent(Talent.WIDE_SEARCH) == 1;
		int distance = heroClass == HeroClass.ROGUE ? 2 : 1;
		if (hasTalent(Talent.WIDE_SEARCH)) distance++;
		
		boolean foresight = buff(Foresight.class) != null;
		boolean foresightScan = foresight && !Dungeon.level.mapped[pos];

		if (foresightScan){
			Dungeon.level.mapped[pos] = true;
		}

		if (foresight) {
			distance = Foresight.DISTANCE;
			circular = true;
		}

		Point c = Dungeon.level.cellToPoint(pos);

		TalismanOfForesight.Foresight talisman = buff( TalismanOfForesight.Foresight.class );
		boolean cursed = talisman != null && talisman.isCursed();

		int[] rounding = ShadowCaster.rounding[distance];

		int left, right;
		int curr;
		for (int y = Math.max(0, c.y - distance); y <= Math.min(Dungeon.level.height()-1, c.y + distance); y++) {
			if (!circular){
				left = c.x - distance;
			} else if (rounding[Math.abs(c.y - y)] < Math.abs(c.y - y)) {
				left = c.x - rounding[Math.abs(c.y - y)];
			} else {
				left = distance;
				while (rounding[left] < rounding[Math.abs(c.y - y)]){
					left--;
				}
				left = c.x - left;
			}
			right = Math.min(Dungeon.level.width()-1, c.x + c.x - left);
			left = Math.max(0, left);
			for (curr = left + y * Dungeon.level.width(); curr <= right + y * Dungeon.level.width(); curr++){

				if ((foresight || fieldOfView[curr]) && curr != pos) {

					if ((foresight && (!Dungeon.level.mapped[curr] || foresightScan))){
						GameScene.effectOverFog(new CheckedCell(curr, foresightScan ? pos : curr));
					} else if (intentional) {
						GameScene.effectOverFog(new CheckedCell(curr, pos));
					}

					if (foresight){
						Dungeon.level.mapped[curr] = true;
					}
					
					if (Dungeon.level.secret[curr]){
						
						Trap trap = Dungeon.level.traps.get( curr );
						float chance;

						//searches aided by foresight always succeed, even if trap isn't searchable
						if (foresight){
							chance = 1f;

						//otherwise if the trap isn't searchable, searching always fails
						} else if (trap != null && !trap.canBeSearched){
							chance = 0f;

						//intentional searches always succeed against regular traps and doors
						} else if (intentional){
							chance = 1f;
						
						//unintentional searches always fail with a cursed talisman
						} else if (cursed) {
							chance = 0f;
							
						//unintentional trap detection scales from 40% at floor 0 to 30% at floor 25
						} else if (Dungeon.level.map[curr] == Terrain.SECRET_TRAP) {
							chance = (hero.hasTalent(Talent.JUNGLE_ADVENTURE)) ? 1.25f*(0.4f - (Dungeon.depth / 250f)) : 0.4f - (Dungeon.depth / 250f);
							
						//unintentional door detection scales from 20% at floor 0 to 0% at floor 20
						} else {
							chance = (hero.hasTalent(Talent.JUNGLE_ADVENTURE)) ? 1.25f*(0.2f - (Dungeon.depth / 100f)) : 0.2f - (Dungeon.depth / 100f);;
						}
						
						if (Random.Float() < chance) {
						
							int oldValue = Dungeon.level.map[curr];
							
							GameScene.discoverTile( curr, oldValue );
							
							Dungeon.level.discover( curr );
							
							ScrollOfMagicMapping.discover( curr );
							
							if (fieldOfView[curr]) smthFound = true;
	
							if (talisman != null){
								if (oldValue == Terrain.SECRET_TRAP){
									talisman.charge(2);
								} else if (oldValue == Terrain.SECRET_DOOR){
									talisman.charge(10);
								}
							}
						}
					}
				}
			}
		}
		
		if (intentional) {
			sprite.showStatus( CharSprite.DEFAULT, Messages.get(this, "search") );
			sprite.operate( pos );
			if (!Dungeon.level.locked) {
				if (cursed) {
					GLog.n(Messages.get(this, "search_distracted"));
					Buff.affect(this, Hunger.class).affectHunger(TIME_TO_SEARCH - (2 * HUNGER_FOR_SEARCH));
				} else {
					Buff.affect(this, Hunger.class).affectHunger(TIME_TO_SEARCH - HUNGER_FOR_SEARCH);
				}
			}
			spendAndNext(TIME_TO_SEARCH);
			
		}
		
		if (smthFound) {
			GLog.w( Messages.get(this, "noticed_smth") );
			Sample.INSTANCE.play( Assets.Sounds.SECRET );
			interrupt();
		}

		if (foresight){
			GameScene.updateFog(pos, Foresight.DISTANCE+1);
		}
		
		return smthFound;
	}
	
	public void resurrect() {
		HP = HT;
		live();

		MagicalHolster holster = belongings.getItem(MagicalHolster.class);

		Buff.affect(this, LostInventory.class);
		Buff.affect(this, Invisibility.class, 3f);
		//lost inventory is dropped in interlevelscene

		//activate items that persist after lost inventory
		//FIXME this is very messy, maybe it would be better to just have one buff that
		// handled all items that recharge over time?
		for (Item i : belongings){
			if (i instanceof EquipableItem && i.isEquipped(this)){
				((EquipableItem) i).activate(this);
			} else if (i instanceof CloakOfShadows && i.keptThoughLostInvent && hasTalent(Talent.LIGHT_CLOAK)){
				((CloakOfShadows) i).activate(this);
			} else if (i instanceof Wand && i.keptThoughLostInvent){
				if (holster != null && holster.contains(i)){
					((Wand) i).charge(this, MagicalHolster.HOLSTER_SCALE_FACTOR);
				} else {
					((Wand) i).charge(this);
				}
			} else if (i instanceof MagesStaff && i.keptThoughLostInvent){
				((MagesStaff) i).applyWandChargeBuff(this);
			}
		}
	}

	@Override
	public void next() {
		if (isAlive())
			super.next();
	}

	public static interface Doom {
		public void onDeath();
	}
}
