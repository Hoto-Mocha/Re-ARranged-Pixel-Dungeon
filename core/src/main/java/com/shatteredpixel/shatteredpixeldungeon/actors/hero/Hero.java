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

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.level;
import static com.shatteredpixel.shatteredpixeldungeon.items.Item.updateQuickslot;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Bones;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.SacrificialFire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AdrenalineSurge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AnkhInvulnerability;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AscensionChallenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Awareness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Berserk;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cloaking;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Combo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Awakening;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Drowsy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Enduring;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EnhancedRingsCombo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EvasiveMove;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Fatigue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Foresight;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.GhostSpawner;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.HoldFast;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Levitation;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LostInventory;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalCombo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MindVision;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Momentum;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MonkEnergy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PhysicalEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PinCushion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Regeneration;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RouletteOfDeath;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SnipersMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SwordAura;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Tackle;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.adventurer.TreasureMap;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.Challenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.ElementalStrike;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.ReinforcedArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.Riot;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.NaturesPower;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.samurai.ShadowBlade;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.Endure;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Monk;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Snake;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.CheckedCell;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.EnergyParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap.Type;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.Rope;
import com.shatteredpixel.shatteredpixeldungeon.items.Sheath;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
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
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.MasterThievesArmband;
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
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfTalent;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfDivineInspiration;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.DarkGold;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Pickaxe;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
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
import com.shatteredpixel.shatteredpixeldungeon.items.spellbook.BookOfDisintegration;
import com.shatteredpixel.shatteredpixeldungeon.items.spellbook.SpellBook;
import com.shatteredpixel.shatteredpixeldungeon.items.trinkets.BrokenMagnifyingGlass;
import com.shatteredpixel.shatteredpixeldungeon.items.trinkets.PinkGem;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLivingEarth;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Blooming;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Lucky;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Bible;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualDagger;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Flail;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Nunchaku;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Quarterstaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RoundShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Sai;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Scimitar;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.ChainFlail;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.Lance;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.LanceNShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.UnholyBible;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.SG.SG;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.journal.Document;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.MiningLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ShadowCaster;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.AlchemyScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.StatusPane;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndHero;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndResurrect;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTradeItem;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.tweeners.Delayer;
import com.watabou.utils.BArray;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Point;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

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
	public boolean damageInterrupt = true;
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
	// for enemies we know we aren't seeing normally, resulting in better performance
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

		HT = (Dungeon.isChallenged(Challenges.SUPERMAN)) ? 10 : 20 + 5 * (lvl-1) + HTBoost;
		if (this.hasTalent(Talent.MAX_HEALTH)) {
			HT += 5*this.pointsInTalent(Talent.MAX_HEALTH);
		}
		float multiplier = RingOfMight.HTMultiplier(this);
		HT = Math.round(multiplier * HT);
		
		if (buff(ElixirOfMight.HTBoost.class) != null){
			HT += buff(ElixirOfMight.HTBoost.class).boost();
		}

		if (buff(ElixirOfTalent.ElixirOfTalentHTBoost.class) != null){
			HT += buff(ElixirOfTalent.ElixirOfTalentHTBoost.class).boost();
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

	public void onSTRGained() {

	}

	public void onSTRLost() {

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
		int bonusPoints = 0;
		if (lvl < (Talent.tierLevelThresholds[tier]-1)
				|| (tier == 3 && subClass == HeroSubClass.NONE)
				|| (tier == 4 && armorAbility == null)) {
			return 0;
		} else if (buff(PotionOfDivineInspiration.DivineInspirationTracker.class) != null
					&& buff(PotionOfDivineInspiration.DivineInspirationTracker.class).isBoosted(tier)) {
			bonusPoints += 2;
		}
		if (tier == 3 && buff(ElixirOfTalent.BonusTalentTracker.class) != null) {
			bonusPoints += 4;
		}
		return bonusPoints;
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
		if (!RingOfForce.fightingUnarmed(this)) {
			belongings.attackingWeapon().hitSound(pitch);
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
		Armor armor = belongings.armor();
		if (armor instanceof ClassArmor){
			return 6;
		} else if (armor != null){
			return armor.tier;
		} else {
			return 0;
		}
	}
	
	public boolean shoot( Char enemy, MissileWeapon wep ) {

		this.enemy = enemy;
		boolean wasEnemy = enemy.alignment == Alignment.ENEMY
				|| (enemy instanceof Mimic && enemy.alignment == Alignment.NEUTRAL);

		//temporarily set the hero's weapon to the missile weapon being used
		//TODO improve this!
		belongings.thrownWeapon = wep;
		boolean hit = attack( enemy );
		Invisibility.dispel();
		belongings.thrownWeapon = null;

		if (hit && subClass == HeroSubClass.GLADIATOR && wasEnemy){
			Buff.affect( this, Combo.class ).hit( enemy );
		}

		if (hit && heroClass == HeroClass.DUELIST && wasEnemy){
			Buff.affect( this, Sai.ComboStrikeTracker.class).addHit();
		}

		return hit;
	}
	
	@Override
	public int attackSkill( Char target ) {
		KindOfWeapon wep = belongings.attackingWeapon();
		
		float accuracy = 1;
		accuracy *= RingOfAccuracy.accuracyMultiplier( this );

		if (Dungeon.isChallenged(Challenges.SUPERMAN)) {
			accuracy *= 2;
		}
		
		if (wep instanceof MissileWeapon && !(wep instanceof Gun.Bullet)){ //총탄을 제외한 투척 무기의 정확성
			if (Dungeon.level.adjacent( pos, target.pos )) {
				accuracy *= (0.5f + 0.2f*pointsInTalent(Talent.POINT_BLANK));
			} else {
				accuracy *= 1.5f;
			}
		}

		if (wep instanceof Gun.Bullet) {	//총탄의 정확성
			if (Dungeon.level.adjacent( pos, target.pos )) {
				if (wep instanceof SG.SGBullet) {
					accuracy *= 10f; //산탄총은 기본적으로 0.2배의 명중률 보정이 있으며, 이를 10배함으로써 2배의 명중률을 가짐
				} else {
					accuracy *= (0.5f + 0.2f*pointsInTalent(Talent.POINT_BLANK));
				}
			}
			if (hero.hasTalent(Talent.INEVITABLE_DEATH) && hero.buff(RouletteOfDeath.class) != null && hero.buff(RouletteOfDeath.class).timeToDeath()) {
				accuracy *= 1 + hero.pointsInTalent(Talent.INEVITABLE_DEATH);
			}
		}

		if (hero.subClass == HeroSubClass.GUNSLINGER && hero.justMoved && wep instanceof MissileWeapon) {
			accuracy *= 0.25f*(1+0.5f*hero.pointsInTalent(Talent.MOVING_SHOT));
		}

		if (buff(Scimitar.SwordDance.class) != null){
			accuracy *= 1.50f;
		}

		if (hero.hasTalent(Talent.ACC_ENHANCE)) {
			accuracy *= 1 + 0.05f * hero.pointsInTalent(Talent.ACC_ENHANCE);
		}

		if (hero.buff(LargeSword.LargeSwordBuff.class) != null) {
			accuracy *= hero.buff(LargeSword.LargeSwordBuff.class).getAccuracyFactor();
		}

		if (hero.buff(Sheath.Sheathing.class) != null) {
			accuracy *= 1.2f;
		}

		if (hero.buff(UnholyBible.Demon.class) != null) {
			accuracy = INFINITE_ACCURACY;
		}

		if (hero.buff(MeleeWeapon.DashAttack.class) != null) {
			accuracy = INFINITE_ACCURACY;
		}

		if (hero.subClass == HeroSubClass.MASTER &&
				hero.buff(Sheath.Sheathing.class) != null &&
				hero.buff(Sheath.FlashSlashCooldown.class) == null &&
				hero.buff(Sheath.DashAttackTracker.class) == null) {
			accuracy = INFINITE_ACCURACY;
		}

		if (!RingOfForce.fightingUnarmed(this)) {
			return (int)(attackSkill * accuracy * wep.accuracyFactor( this, target ));
		} else {
			return (int)(attackSkill * accuracy);
		}
	}
	
	@Override
	public int defenseSkill( Char enemy ) {

		if (enemy != null) {
			if (buff(Combo.ParryTracker.class) != null){
				if (canAttack(enemy) && !isCharmedBy(enemy)){
					Buff.affect(this, Combo.RiposteTracker.class).enemy = enemy;
				}
				return INFINITE_EVASION;
			}

			if (buff(RoundShield.GuardTracker.class) != null){
				return INFINITE_EVASION;
			}

			if (buff(Talent.ParryTracker.class) != null){
				if (canAttack(enemy) && !isCharmedBy(enemy)){
					Buff.affect(this, Talent.RiposteTracker.class).enemy = enemy;
				}
				return INFINITE_EVASION;
			}

			if (buff(Nunchaku.ParryTracker.class) != null){
				if (canAttack(enemy) && !isCharmedBy(enemy)){
					Buff.affect(this, Nunchaku.RiposteTracker.class).enemy = enemy;
				}
				return INFINITE_EVASION;
			}
		}
		
		float evasion = defenseSkill;
		
		evasion *= RingOfEvasion.evasionMultiplier( this );

		if (hero.hasTalent(Talent.SWIFT_MOVEMENT)) {
			evasion += hero.STR()-10;
		}

		if (Dungeon.isChallenged(Challenges.SUPERMAN)) {
			evasion *= 3;
		}

		if (buff(Talent.RestoredAgilityTracker.class) != null){
			if (pointsInTalent(Talent.LIQUID_AGILITY) == 1){
				evasion *= 4f;
			} else if (pointsInTalent(Talent.LIQUID_AGILITY) == 2){
				return INFINITE_EVASION;
			}
		}

		if (buff(Quarterstaff.DefensiveStance.class) != null){
			evasion *= 3;
		}

		if (hero.hasTalent(Talent.EVA_ENHANCE)) {
			evasion *= 1 + 0.05f * hero.pointsInTalent(Talent.EVA_ENHANCE);
		}

		if (hero.buff(UnholyBible.Demon.class) != null) {
			evasion /= 2;
		}
		
		if (paralysed > 0) {
			evasion /= 2;
		}

		if (belongings.armor() != null) {
			evasion = belongings.armor().evasionFactor(this, evasion);
		}

		return Math.round(evasion);
	}

	@Override
	public String defenseVerb() {
		Combo.ParryTracker parry = buff(Combo.ParryTracker.class);
		if (parry != null){
			parry.parried = true;
			if (buff(Combo.class).getComboCount() < 9 || pointsInTalent(Talent.ENHANCED_COMBO) < 2){
				parry.detach();
			}
			return Messages.get(Monk.class, "parried");
		}

		if (buff(RoundShield.GuardTracker.class) != null){
			buff(RoundShield.GuardTracker.class).hasBlocked = true;
			BuffIndicator.refreshHero();
			Sample.INSTANCE.play(Assets.Sounds.HIT_PARRY, 1, Random.Float(0.96f, 1.05f));
			return Messages.get(RoundShield.GuardTracker.class, "guarded");
		}

		if (buff(MonkEnergy.MonkAbility.Focus.FocusActivation.class) != null){
			buff(MonkEnergy.MonkAbility.Focus.FocusActivation.class).detach();
			if (sprite != null && sprite.visible) {
				Sample.INSTANCE.play(Assets.Sounds.HIT_PARRY, 1, Random.Float(0.96f, 1.05f));
			}
			return Messages.get(Monk.class, "parried");
		}

		Talent.ParryTracker parryTracker = buff(Talent.ParryTracker.class);
		if (hasTalent(Talent.PARRY)) {
			if (parryTracker != null) {
				parryTracker.detach();
				return Messages.get(Monk.class, "parried");
			}
		}

		Nunchaku.ParryTracker nunchakuParry = buff(Nunchaku.ParryTracker.class);
		if (nunchakuParry != null){
			nunchakuParry.parried = true;
			if (sprite != null && sprite.visible) {
				Sample.INSTANCE.play(Assets.Sounds.HIT_PARRY, 1, Random.Float(0.96f, 1.05f));
			}
			return Messages.get(Monk.class, "parried");
		}

		RouletteOfDeath roulette = buff(RouletteOfDeath.class);
		if (hasTalent(Talent.HONORABLE_SHOT) && roulette != null && roulette.overHalf()) {
			Buff.prolong(hero, Talent.HonorableShotTracker.class, 1f);
		}

		Awakening awakening = buff(Awakening.class);
		if (awakening != null && awakening.isAwaken() && hasTalent(Talent.HASTE_EVASION)) {
			Buff.prolong(hero, Haste.class, 1+hero.pointsInTalent(Talent.HASTE_EVASION));
		}

		return super.defenseVerb();
	}

	@Override
	public int drRoll() {
		int dr = super.drRoll();

		if (belongings.armor() != null) {
			int armDr = Char.combatRoll( belongings.armor().DRMin(), belongings.armor().DRMax());
			if (STR() < belongings.armor().STRReq()){
				armDr -= 2*(belongings.armor().STRReq() - STR());
			}
			if (armDr > 0) dr += armDr;
		}
		if (belongings.weapon() != null && !RingOfForce.fightingUnarmed(this))  {
			int wepDr = Char.combatRoll( 0 , belongings.weapon().defenseFactor( this ) );
			if (STR() < ((Weapon)belongings.weapon()).STRReq()){
				wepDr -= 2*(((Weapon)belongings.weapon()).STRReq() - STR());
			}
			if (wepDr > 0) dr += wepDr;
		}

		if (buff(HoldFast.class) != null){
			dr += buff(HoldFast.class).armorBonus();
		}

		if (hero.hasTalent(Talent.PARRING)) {
			dr += Random.NormalIntRange(0, 1+hero.pointsInTalent(Talent.PARRING));
		}

		ReinforcedArmor.ReinforcedArmorTracker reArmor = hero.buff(ReinforcedArmor.ReinforcedArmorTracker.class);
		if (reArmor != null)  dr += reArmor.blockingRoll();
		
		return dr;
	}
	
	@Override
	public int damageRoll() {
		KindOfWeapon wep = belongings.attackingWeapon();
		int dmg;

		if (!RingOfForce.fightingUnarmed(this)) {
			dmg = wep.damageRoll( this );

			if (!(wep instanceof MissileWeapon)) dmg += RingOfForce.armedDamageBonus(this);
		} else {
			dmg = RingOfForce.damageRoll(this);
			if (RingOfForce.unarmedGetsWeaponAugment(this)){
				dmg = ((Weapon)belongings.attackingWeapon()).augment.damageFactor(dmg);
			}
		}

		PhysicalEmpower emp = buff(PhysicalEmpower.class);
		if (emp != null){
			dmg += emp.dmgBoost;
			emp.left--;
			if (emp.left <= 0) {
				emp.detach();
			}
			Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG, 0.75f, 1.2f);
		}

		if (heroClass != HeroClass.DUELIST
				&& hasTalent(Talent.WEAPON_RECHARGING)
				&& (buff(Recharging.class) != null || buff(ArtifactRecharge.class) != null)){
			dmg = Math.round(dmg * 1.025f + (.025f*pointsInTalent(Talent.WEAPON_RECHARGING)));
		}

		if (dmg < 0) dmg = 0;
		return dmg;
	}
	
	@Override
	public float speed() {

		float speed = super.speed();

		speed *= RingOfHaste.speedMultiplier(this);
		
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

		if (hero.hasTalent(Talent.MOVESPEED_ENHANCE)) {
			speed *= 1 + 0.1*hero.pointsInTalent(Talent.MOVESPEED_ENHANCE);
		}

		if (subClass == HeroSubClass.MONK && buff(MonkEnergy.class) != null && buff(MonkEnergy.class).harmonized(this)) {
			speed *= 1.5f;
		}

		if (hero.buff(ReinforcedArmor.ReinforcedArmorTracker.class) != null && hero.hasTalent(Talent.PLATE_ADD)) {
			speed *= (1 - hero.pointsInTalent(Talent.PLATE_ADD)/8f);
		}

		if (hero.buff(Riot.RiotTracker.class) != null && hero.hasTalent(Talent.HASTE_MOVE)) {
			speed *= 1f + 0.25f * hero.pointsInTalent(Talent.HASTE_MOVE);
		}

		if (level.map[hero.pos] == Terrain.FURROWED_GRASS && hero.hasTalent(Talent.JUNGLE_EXPLORE)) {
			speed *= Math.pow(1.2f, hero.pointsInTalent(Talent.JUNGLE_EXPLORE));
		}

		speed = AscensionChallenge.modifyHeroSpeed(speed);
		
		return speed;
		
	}

	@Override
	public boolean canSurpriseAttack(){
		KindOfWeapon w = belongings.attackingWeapon();
		if (!(w instanceof Weapon))             return true;
		if (RingOfForce.fightingUnarmed(this))  return true;
		if (STR() < ((Weapon)w).STRReq())       return false;
		if (w instanceof Flail)                 return false;
		if (w instanceof ChainFlail)            return false;
		if (w instanceof SG.SGBullet)           return false;

		return super.canSurpriseAttack();
	}

	public boolean canAttack(Char enemy){
		if (enemy == null || pos == enemy.pos || !Actor.chars().contains(enemy)) {
			return false;
		}

		//can always attack adjacent enemies
		if (Dungeon.level.adjacent(pos, enemy.pos)) {
			return true;
		}

		KindOfWeapon wep = Dungeon.hero.belongings.attackingWeapon();

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

		if (buff(Talent.CounterAttackTracker.class) != null && hero.belongings.weapon == null) {
			buff(Talent.CounterAttackTracker.class).detach();
			return 0;
		}

		float delay = 1f;

		if ( buff(Adrenaline.class) != null) delay /= 1.5f;

		if (!RingOfForce.fightingUnarmed(this)) {
			
			return delay * belongings.attackingWeapon().delayFactor( this );
			
		} else {
			//Normally putting furor speed on unarmed attacks would be unnecessary
			//But there's going to be that one guy who gets a furor+force ring combo
			//This is for that one guy, you shall get your fists of fury!
			float speed = RingOfFuror.attackSpeedMultiplier(this);

			if (hero.hasTalent(Talent.LESS_RESIST)) {
				int aEnc = hero.belongings.armor.STRReq() - hero.STR();
				if (aEnc < 0) {
					speed *= 1 + 0.05f * hero.pointsInTalent(Talent.LESS_RESIST) * (-aEnc);
				}
			}

			if (hero.hasTalent(Talent.QUICK_FOLLOWUP) && hero.buff(Talent.QuickFollowupTracker.class) != null) {
				speed *= 1+hero.pointsInTalent(Talent.QUICK_FOLLOWUP)/3f;
			}

			if (hero.subClass == HeroSubClass.MONK && hero.buff(MonkEnergy.class) != null && hero.buff(MonkEnergy.class).harmonized(hero)) {
				speed *= 1.5f;
			}

			if (hero.hasTalent(Talent.ATK_SPEED_ENHANCE)) {
				speed *= 1 + 0.05f * hero.pointsInTalent(Talent.ATK_SPEED_ENHANCE);
			}

			Awakening awakening = hero.buff(Awakening.class);
			if (awakening != null && awakening.isAwaken()) {
				speed *= 2f;
			}

			if (hero.buff(DualDagger.ReverseBlade.class) != null) {
				speed *= 2f;
			}

			if (hero.buff(ShadowBlade.shadowBladeTracker.class) != null) {
				speed *= 2f + 0.05f * hero.pointsInTalent(Talent.DOUBLE_BLADE_PRACTICE);
			}

			//ditto for furor + sword dance!
			if (buff(Scimitar.SwordDance.class) != null) {
				speed += 0.6f;
			}

			//and augments + brawler's stance! My goodness, so many options now compared to 2014!
			if (RingOfForce.unarmedGetsWeaponAugment(this)){
				delay = ((Weapon)belongings.weapon).augment.delayFactor(delay);
			}

			return delay/speed;
		}
	}

	@Override
	public void spend( float time ) {
		super.spend(time);
	}

	@Override
	public void spendConstant(float time) {
		justMoved = false;
		super.spendConstant(time);
	}

	public void spendAndNextConstant(float time ) {
		busy();
		spendConstant( time );
		next();
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
		BuffIndicator.refreshBoss();
		
		if (paralysed > 0) {
			
			curAction = null;
			
			spendAndNext( TICK );
			return false;
		}
		
		boolean actResult;
		if (curAction == null) {
			
			if (resting) {
				spendConstant( TIME_TO_REST );
				next();
			} else {
				ready();
			}

			//if we just loaded into a level and have a search buff, make sure to process them
			if(Actor.now() == 0){
				if (buff(Foresight.class) != null){
					search(false);
				} else if (buff(TalismanOfForesight.Foresight.class) != null){
					buff(TalismanOfForesight.Foresight.class).checkAwareness();
				}
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
				
			} else if (curAction instanceof HeroAction.Mine) {
				actResult = actMine( (HeroAction.Mine)curAction );

			}else if (curAction instanceof HeroAction.LvlTransition) {
				actResult = actTransition( (HeroAction.LvlTransition)curAction );
				
			} else if (curAction instanceof HeroAction.Attack) {
				actResult = actAttack( (HeroAction.Attack)curAction );
				
			} else if (curAction instanceof HeroAction.Alchemy) {
				actResult = actAlchemy( (HeroAction.Alchemy)curAction );
				
			} else {
				actResult = false;
			}
		}
		
		if(hasTalent(Talent.BARKSKIN) && Dungeon.level.map[pos] == Terrain.FURROWED_GRASS){
			Barkskin.conditionallyAppend(this, (lvl*pointsInTalent(Talent.BARKSKIN))/2, 1 );
		}

		if (hasTalent(Talent.PARRY) && buff(Talent.ParryCooldown.class) == null){
			Buff.affect(this, Talent.ParryTracker.class);
		}

		if (Dungeon.isChallenged(Challenges.CURSED_DUNGEON) && hero.buff(GhostSpawner.class) == null) {
			Buff.affect(hero, GhostSpawner.class);
		}

		if (hero.subClass == HeroSubClass.SLAYER && hero.buff(Awakening.class) == null) {
			Buff.affect(hero, Awakening.class).indicate();
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
		waitOrPickup = false;
		ready = true;
		canSelfTrample = true;

		AttackIndicator.updateState();
		
		GameScene.ready();
	}
	
	public void interrupt() {
		if (isAlive() && curAction != null &&
			((curAction instanceof HeroAction.Move && curAction.dst != pos) ||
			(curAction instanceof HeroAction.LvlTransition))) {
			lastAction = curAction;
		}
		curAction = null;
		GameScene.resetKeyHold();
		resting = false;
	}
	
	public void resume() {
		curAction = lastAction;
		lastAction = null;
		damageInterrupt = false;
		next();
	}

	private boolean canSelfTrample = false;
	public boolean canSelfTrample(){
		return canSelfTrample && !rooted && !flying &&
				//standing in high grass
				(Dungeon.level.map[pos] == Terrain.HIGH_GRASS ||
				//standing in furrowed grass and not huntress
				((heroClass != HeroClass.HUNTRESS && hero.subClass != HeroSubClass.SPECIALIST) && Dungeon.level.map[pos] == Terrain.FURROWED_GRASS) ||
				//standing on a plant
				Dungeon.level.plants.get(pos) != null);
	}
	
	private boolean actMove( HeroAction.Move action ) {

		if (getCloser( action.dst )) {
			canSelfTrample = false;
			return true;

		//Hero moves in place if there is grass to trample
		} else if (pos == action.dst && canSelfTrample()){
			canSelfTrample = false;
			Dungeon.level.pressCell(pos);
			spendAndNext( 1 / speed() );
			return false;
		} else {
			ready();
			return false;
		}
	}
	
	private boolean actInteract( HeroAction.Interact action ) {
		
		Char ch = action.ch;

		if (ch.isAlive() && ch.canInteract(this)) {
			
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

	//used to keep track if the wait/pickup action was used
	// so that the hero spends a turn even if the fail to pick up an item
	public boolean waitOrPickup = false;

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
							|| item instanceof Key
							|| item instanceof Guidebook) {
						//Do Nothing
					} else if (item instanceof DarkGold) {
						DarkGold existing = belongings.getItem(DarkGold.class);
						if (existing != null){
							if (existing.quantity() >= 40) {
								GLog.p(Messages.get(DarkGold.class, "you_now_have", existing.quantity()));
							} else {
								GLog.i(Messages.get(DarkGold.class, "you_now_have", existing.quantity()));
							}
						}
					} else {

						//TODO make all unique items important? or just POS / SOU?
						boolean important = item.unique && item.isIdentified() &&
								(item instanceof Scroll || item instanceof Potion);
						if (important) {
							GLog.p( Messages.capitalize(Messages.get(this, "you_now_have", item.name())) );
						} else {
							GLog.i( Messages.capitalize(Messages.get(this, "you_now_have", item.name())) );
						}
					}
					
					curAction = null;
				} else {

					if (waitOrPickup) {
						spendAndNextConstant(TIME_TO_REST);
					}

					//allow the hero to move between levels even if they can't collect the item
					if (Dungeon.level.getTransition(pos) != null){
						throwItems();
					} else {
						heap.sprite.drop();
					}

					if (item instanceof Dewdrop
							|| item instanceof TimekeepersHourglass.sandBag
							|| item instanceof DriedRose.Petal
							|| item instanceof Key) {
						//Do Nothing
					} else {
						GLog.newLine();
						GLog.n(Messages.capitalize(Messages.get(this, "you_cant_have", item.name())));
					}

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
			path = null;
			
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
					PixelScene.shake( 1, 0.5f );
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
			path = null;
			
			boolean hasKey = false;
			int door = Dungeon.level.map[doorCell];
			
			if (door == Terrain.LOCKED_DOOR
					&& Notes.keyCount(new IronKey(Dungeon.depth)) > 0) {
				
				hasKey = true;
				
			} else if (door == Terrain.CRYSTAL_DOOR
					&& Notes.keyCount(new CrystalKey(Dungeon.depth, Dungeon.branch)) > 0) {

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

	private boolean actMine(HeroAction.Mine action){
		if (Dungeon.level.adjacent(pos, action.dst)){
			path = null;
			if ((Dungeon.level.map[action.dst] == Terrain.WALL
					|| Dungeon.level.map[action.dst] == Terrain.WALL_DECO
					|| Dungeon.level.map[action.dst] == Terrain.MINE_CRYSTAL
					|| Dungeon.level.map[action.dst] == Terrain.MINE_BOULDER)
				&& Dungeon.level.insideMap(action.dst)){
				sprite.attack(action.dst, new Callback() {
					@Override
					public void call() {

						boolean crystalAdjacent = false;
						for (int i : PathFinder.NEIGHBOURS8) {
							if (Dungeon.level.map[action.dst + i] == Terrain.MINE_CRYSTAL){
								crystalAdjacent = true;
								break;
							}
						}

						//1 hunger spent total
						if (Dungeon.level.map[action.dst] == Terrain.WALL_DECO){
							DarkGold gold = new DarkGold();
							if (gold.doPickUp( Dungeon.hero )) {
								DarkGold existing = Dungeon.hero.belongings.getItem(DarkGold.class);
								if (existing != null && existing.quantity()%5 == 0){
									if (existing.quantity() >= 40) {
										GLog.p(Messages.get(DarkGold.class, "you_now_have", existing.quantity()));
									} else {
										GLog.i(Messages.get(DarkGold.class, "you_now_have", existing.quantity()));
									}
								}
								spend(-Actor.TICK); //picking up the gold doesn't spend a turn here
							} else {
								Dungeon.level.drop( gold, pos ).sprite.drop();
							}
							PixelScene.shake(0.5f, 0.5f);
							CellEmitter.center( action.dst ).burst( Speck.factory( Speck.STAR ), 7 );
							Sample.INSTANCE.play( Assets.Sounds.EVOKE );
							Level.set( action.dst, Terrain.EMPTY_DECO );

							//mining gold doesn't break crystals
							crystalAdjacent = false;

						//4 hunger spent total
						} else if (Dungeon.level.map[action.dst] == Terrain.WALL){
							buff(Hunger.class).affectHunger(-3);
							PixelScene.shake(0.5f, 0.5f);
							CellEmitter.get( action.dst ).burst( Speck.factory( Speck.ROCK ), 2 );
							Sample.INSTANCE.play( Assets.Sounds.MINE );
							Level.set( action.dst, Terrain.EMPTY_DECO );

						//1 hunger spent total
						} else if (Dungeon.level.map[action.dst] == Terrain.MINE_CRYSTAL){
							Splash.at(action.dst, 0xFFFFFF, 5);
							Sample.INSTANCE.play( Assets.Sounds.SHATTER );
							Level.set( action.dst, Terrain.EMPTY );

						//1 hunger spent total
						} else if (Dungeon.level.map[action.dst] == Terrain.MINE_BOULDER){
							Splash.at(action.dst, 0x555555, 5);
							Sample.INSTANCE.play( Assets.Sounds.MINE, 0.6f );
							Level.set( action.dst, Terrain.EMPTY_DECO );
						}

						for (int i : PathFinder.NEIGHBOURS9) {
							Dungeon.level.discoverable[action.dst + i] = true;
						}
						for (int i : PathFinder.NEIGHBOURS9) {
							GameScene.updateMap( action.dst+i );
						}

						if (crystalAdjacent){
							sprite.parent.add(new Delayer(0.2f){
								@Override
								protected void onComplete() {
									boolean broke = false;
									for (int i : PathFinder.NEIGHBOURS8) {
										if (Dungeon.level.map[action.dst+i] == Terrain.MINE_CRYSTAL){
											Splash.at(action.dst+i, 0xFFFFFF, 5);
											Level.set( action.dst+i, Terrain.EMPTY );
											broke = true;
										}
									}
									if (broke){
										Sample.INSTANCE.play( Assets.Sounds.SHATTER );
									}

									for (int i : PathFinder.NEIGHBOURS9) {
										GameScene.updateMap( action.dst+i );
									}
									spendAndNext(TICK);
									ready();
								}
							});
						} else {
							spendAndNext(TICK);
							ready();
						}

						Dungeon.observe();
					}
				});
			} else {
				ready();
			}
			return false;
		} else if (getCloser( action.dst )) {

			return true;

		} else {
			ready();
			return false;
		}
	}
	
	private boolean actTransition(HeroAction.LvlTransition action ) {
		int stairs = action.dst;
		LevelTransition transition = Dungeon.level.getTransition(stairs);

		if (rooted) {
			PixelScene.shake(1, 1f);
			ready();
			return false;

		} else if (!Dungeon.level.locked && transition != null && transition.inside(pos)) {

			if (Dungeon.level.activateTransition(this, transition)){
				curAction = null;
			} else {
				ready();
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

		if (isCharmedBy( enemy )){
			GLog.w( Messages.get(Charm.class, "cant_attack"));
			ready();
			return false;
		}

		if (enemy.isAlive() && canAttack( enemy ) && enemy.invisible == 0) {

			if (heroClass != HeroClass.DUELIST
					&& hasTalent(Talent.AGGRESSIVE_BARRIER)
					&& buff(Talent.AggressiveBarrierCooldown.class) == null
					&& (HP / (float)HT) < 0.20f*(1+pointsInTalent(Talent.AGGRESSIVE_BARRIER))){
				Buff.affect(this, Barrier.class).setShield(3);
				sprite.showStatusWithIcon(CharSprite.POSITIVE, "3", FloatingText.SHIELDING);
				Buff.affect(this, Talent.AggressiveBarrierCooldown.class, 50f);

			}

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
		spendAndNextConstant( TIME_TO_REST );
		if (hasTalent(Talent.HOLD_FAST)){
			Buff.affect(this, HoldFast.class).pos = pos;
		}
		if (hasTalent(Talent.PATIENT_STRIKE)){
			Buff.affect(Dungeon.hero, Talent.PatientStrikeTracker.class).pos = Dungeon.hero.pos;
		}
		if (!fullRest) {
			if (sprite != null) {
				sprite.showStatus(CharSprite.DEFAULT, Messages.get(this, "wait"));
			}

			if (belongings.weapon instanceof LargeSword || belongings.secondWep instanceof LargeSword){
				Buff.affect(this, LargeSword.LargeSwordBuff.class).setDamageFactor(belongings.weapon.buffedLvl(), (belongings.secondWep instanceof LargeSword));
				if (hero.sprite != null) {
					Emitter e = hero.sprite.centerEmitter();
					if (e != null) e.burst(EnergyParticle.FACTORY, 15);
				}
			}

			if (Dungeon.hero.subClass == HeroSubClass.CHASER
					&& hero.buff(Talent.ChaseCooldown.class) == null
					&& hero.buff(Invisibility.class) == null
					&& hero.buff(CloakOfShadows.cloakStealth.class) == null ) {
				if (hero.hasTalent(Talent.MASTER_OF_CLOAKING)) {
					Buff.affect(Dungeon.hero, Invisibility.class, 6f);
				} else {
					Buff.affect(Dungeon.hero, Invisibility.class, 5f);
				}
				if (hero.pointsInTalent(Talent.MASTER_OF_CLOAKING) > 1) {
					Buff.affect(Dungeon.hero, Talent.ChaseCooldown.class, 10f);
				} else {
					Buff.affect(Dungeon.hero, Talent.ChaseCooldown.class, 15f);
				}
			}

			if (Dungeon.level.map[pos] == Terrain.FURROWED_GRASS && hero.subClass == HeroSubClass.SPECIALIST) {
				boolean adjacentMob = false;
				for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
					if (level.adjacent(hero.pos, mob.pos)) {
						adjacentMob = true;
						break;
					}
				}
				if (hero.pointsInTalent(Talent.STEALTH_MASTER) > 1) {
					adjacentMob = false;
				}
				if (!adjacentMob && hero.hasTalent(Talent.INTO_THE_SHADOW) && hero.buff(Talent.IntoTheShadowCooldown.class) == null) {
					Buff.affect(this, Invisibility.class, 3f*hero.pointsInTalent(Talent.INTO_THE_SHADOW));
					Buff.affect(this, Talent.IntoTheShadowCooldown.class, 15);
				} else {
					Buff.affect(this, Cloaking.class);
				}
			}
		}
		resting = fullRest;
	}

	public float critChance(final Char enemy, final Weapon wep) {
		float chance = 0;

		if (heroClass == HeroClass.SAMURAI) {
			chance = 0.01f;
			chance += 0.01f * (lvl - 1);
			chance += Math.max(0, (0.02f + 0.005f*pointsInTalent(Talent.WEAPON_MASTERY)) * (STR() - wep.STRReq()));
		}

		if (buff(Sheath.CertainCrit.class) != null) {
			chance += 1f;
		}

		if (hasTalent(Talent.BASIC_PRACTICE)) {
			chance += 0.02f * pointsInTalent(Talent.BASIC_PRACTICE);
		}

		if (wep instanceof MissileWeapon && hasTalent(Talent.CRITICAL_THROW)) {
			chance += 0.25f * pointsInTalent(Talent.CRITICAL_THROW);
		}

		Awakening awakening = buff(Awakening.class);
		if (awakening != null && awakening.isAwaken()) {
			if (hasTalent(Talent.ACCELERATED_LETHALITY)) {
				chance += 0.1f*pointsInTalent(Talent.ACCELERATED_LETHALITY);
			}
			chance += Math.max(0, 0.01f*(defenseSkill(enemy) - (4 + lvl)));
		}

		if (enemy != null) {
			if (hasTalent(Talent.UNEXPECTED_SLASH) && enemy.buff(Talent.UnexpectedSlashTracker.class) == null) {
				chance += 0.1f*pointsInTalent(Talent.UNEXPECTED_SLASH);
				Buff.affect(enemy, Talent.UnexpectedSlashTracker.class);
			}
		}

		if (buff(Sheath.Sheathing.class) != null) {
			if (subClass == HeroSubClass.MASTER &&
					buff(Sheath.FlashSlashCooldown.class) == null &&
					buff(Sheath.DashAttackTracker.class) == null) {
				switch (pointsInTalent(Talent.ENHANCED_CRIT)) {
					case 0: default:
						chance *= 1.5f;
						break;
					case 1:
						chance *= 1.6f;
						break;
					case 2:
						chance *= 1.75f;
						break;
					case 3:
						chance *= 2f;
						break;
				}
				spend(-attackDelay());
			} else {
				chance *= 1.2f;
			}
			chance += 0.05f;
		}

		return GameMath.gate(0, chance, 2);
	}

	public int criticalDamage(int damage, Weapon wep, Char enemy) {
		int max = wep.max();
		float multi = 1f+Math.max(0, critChance(enemy, wep)-1);
		int bonusDamage = 0;

		damage = (int)(max * 0.75f + damage * 0.25f);

		multi += 0.05f * pointsInTalent(Talent.LETHAL_POWER);

		return Math.round(damage * multi) + bonusDamage;
	}

	@Override
	public int attackProc( final Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );

		KindOfWeapon wep;
		if (RingOfForce.fightingUnarmed(this) && !RingOfForce.unarmedGetsWeaponEnchantment(this)){
			wep = null;
		} else {
			wep = belongings.attackingWeapon();
		}

		if (hero.buff(MeleeWeapon.DashAttack.class) != null) {
			damage *= hero.buff(MeleeWeapon.DashAttack.class).getDmgMulti();
			hero.buff(MeleeWeapon.DashAttack.class).detach();
		}

		if (Dungeon.isChallenged(Challenges.SUPERMAN)) {
			damage *= 3f;
		}

		if (wep != null) damage = wep.proc( this, enemy, damage );

		damage = Talent.onAttackProc( this, enemy, damage );

		if (wep instanceof Weapon) {
			if (Random.Float() < critChance(enemy, (Weapon)wep)) {
				damage = criticalDamage(damage, (Weapon)wep, enemy);

				Buff.affect(enemy, Sheath.CriticalAttack.class);

				if (Sheath.isFlashSlash()) {
					damage *= 1 + 0.15f * hero.pointsInTalent(Talent.POWERFUL_SLASH);
				}

				Awakening awakening = hero.buff(Awakening.class);
				if (awakening != null && awakening.isAwaken()) {
					spend(-hero.attackDelay());
					if (hero.hasTalent(Talent.STABLE_BARRIER)) {
						int shield = 1;
						int maxShield = Math.round(hero.HT * 0.2f * hero.pointsInTalent(Talent.STABLE_BARRIER));
						int curShield = 0;
						if (hero.buff(Barrier.class) != null) curShield = hero.buff(Barrier.class).shielding();
						shield = Math.min(shield, maxShield-curShield);
						if (shield > 0) {
							Buff.affect(hero, Barrier.class).incShield(shield);
							hero.sprite.showStatus( CharSprite.POSITIVE, Messages.get(Dewdrop.class, "shield", shield) );
						}
					}
				}
			} else {
				if (Sheath.isFlashSlash()) {
					Buff.prolong(hero, Sheath.FlashSlashCooldown.class, (30-5*hero.pointsInTalent(Talent.STATIC_PREPARATION))-1);
				}
			}
		}

		if (hero.buff(Sheath.DashAttackTracker.class) != null) {
			if (hero.hasTalent(Talent.ACCELERATION)) {
				Buff.prolong(hero, Sheath.DashAttackAcceleration.class, Sheath.DashAttackAcceleration.DURATION).hit();
				Sheath.DashAttackAcceleration buff = hero.buff(Sheath.DashAttackAcceleration.class);
				if (buff != null) {
					damage *= buff.getDmgMulti();
				}
			}
			if (hero.buff(Sheath.DashAttackAcceleration.class) != null) {
				damage *= hero.buff(Sheath.DashAttackAcceleration.class).getDmgMulti();
			}
			hero.buff(Sheath.DashAttackTracker.class).detach();
		}
		
		switch (subClass) {
			case SNIPER:
				if (wep instanceof MissileWeapon && !(wep instanceof SpiritBow.SpiritArrow) && enemy != this) {
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
				if (hero.hasTalent(Talent.KICK)
						&& enemy.buff(PinCushion.class) != null
						&& level.adjacent(hero.pos, enemy.pos)
						&& hero.buff(Talent.KickCooldown.class) == null) {
					Item item = enemy.buff(PinCushion.class).grabOne();
					if (item.doPickUp(hero, enemy.pos)){
						hero.spend(-1); //attacking enemy already takes a turn
					} else {
						GLog.w(Messages.get(this, "cant_grab"));
						Dungeon.level.drop(item, enemy.pos).sprite.drop();
					}
					Ballistica trajectory = new Ballistica(hero.pos, enemy.pos, Ballistica.STOP_TARGET);
					trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
					int dist = hero.pointsInTalent(Talent.KICK);
					WandOfBlastWave.throwChar(enemy, trajectory, dist, true, false ,hero.getClass());
					Buff.affect(hero, Talent.KickCooldown.class, 10f);
				}
				if (wep instanceof MissileWeapon
						&& hero.hasTalent(Talent.SHOOTING_EYES)
						&& enemy.buff(Talent.ShootingEyesTracker.class) == null) {
					if (Random.Float() < hero.pointsInTalent(Talent.SHOOTING_EYES)/3f) {
						Buff.affect(enemy, Blindness.class, 2f);
					}
					Buff.affect(enemy, Talent.ShootingEyesTracker.class);
				}
				if (wep instanceof MissileWeapon
						&& hero.hasTalent(Talent.TARGET_SPOTTING)
						&& hero.buff(SnipersMark.class) != null
						&& hero.buff(SnipersMark.class).object == enemy.id()) {
					damage *= 1+0.1f*hero.pointsInTalent(Talent.TARGET_SPOTTING);
				}
				break;
			case FIGHTER:
				if (wep == null && Random.Int(3) < hero.pointsInTalent(Talent.QUICK_STEP)) {
					Buff.prolong(hero, Talent.QuickStep.class, 1.0001f);
				}
				if (wep == null && hero.hasTalent(Talent.RING_KNUCKLE) && hero.buff(RingOfForce.Force.class) == null) {
					Buff.prolong(hero, EnhancedRingsCombo.class, (Dungeon.hero.pointsInTalent(Talent.RING_KNUCKLE) >= 2) ? 2f : 1f).hit();
					hero.updateHT(false);
					updateQuickslot();
				}
				if (wep == null && Random.Float() < hero.pointsInTalent(Talent.MYSTICAL_PUNCH)/3f) {
					if (hero.belongings.ring != null) {
						damage *= Ring.onHit(hero, enemy, damage, Ring.ringTypes.get(hero.belongings.ring.getClass()));
					}
					if (hero.belongings.misc instanceof Ring) {
						damage *= Ring.onHit(hero, enemy, damage, Ring.ringTypes.get(hero.belongings.misc.getClass()));
					}
				}
				break;

			case CHAMPION:
				if (hero.belongings.weapon != null && hero.belongings.secondWep != null
						&& hero.pointsInTalent(Talent.TWIN_SWORD) > 1
						&& hero.belongings.weapon.getClass() == hero.belongings.secondWep.getClass()) {
					KindOfWeapon other = hero.belongings.secondWep;
					if (hero.belongings.secondWep == wep)  {
						other = hero.belongings.weapon;
					}
					damage = other.proc( this, enemy, damage );
				}
				break;
			case VETERAN:
				if (level.adjacent(enemy.pos, pos) && hero.buff(Tackle.TackleTracker.class) == null) {
					Actor.add(new Actor() {

						{
							actPriority = VFX_PRIO;
						}

						@Override
						protected boolean act() {
							if (enemy.isAlive()) {
								Buff.prolong(Hero.this, Tackle.class, 1).set(enemy.id());
							}
							Actor.remove(this);
							return true;
						}
					});
				}
				break;
			case OUTLAW:
				if (wep instanceof Gun.Bullet) {
					if (hero.hasTalent(Talent.HEADSHOT) && Random.Float() < 0.01f*hero.pointsInTalent(Talent.HEADSHOT)) {
						if (!Char.hasProp(enemy, Property.BOSS) && !Char.hasProp(enemy, Property.MINIBOSS) && enemy.alignment == Alignment.ENEMY) {
							damage = enemy.HP;
						} else {
							damage *= 1.2f;
						}
						enemy.sprite.emitter().burst( ShadowParticle.UP, 5 );
					}

					if (hero.buff(Talent.HonorableShotTracker.class) != null
							&& (enemy.HP/(float)enemy.HT) <= 0.4f*hero.pointsInTalent(Talent.HONORABLE_SHOT)/3f) {
						if (!Char.hasProp(enemy, Property.BOSS) && !Char.hasProp(enemy, Property.MINIBOSS)) {
							damage = enemy.HP;
						} else {
							damage *= 1.2f;
						}
						enemy.sprite.emitter().burst( ShadowParticle.UP, 5 );
						hero.buff(Talent.HonorableShotTracker.class).detach();
					}

					if (hero.buff(RouletteOfDeath.class) == null || !hero.buff(RouletteOfDeath.class).timeToDeath()) {
						Buff.affect(this, RouletteOfDeath.class).hit();
					} else {
						if (!Char.hasProp(enemy, Property.BOSS) && !Char.hasProp(enemy, Property.MINIBOSS)) {
							damage = enemy.HP;
							if (hero.belongings.weapon instanceof Gun) {
								((Gun)hero.belongings.weapon).quickReload();
							}
							if (hero.hasTalent(Talent.BULLET_TIME)) {
								for (Char ch : Actor.chars()) {
									if (level.heroFOV[ch.pos] && ch != hero && ch.alignment == Alignment.ENEMY) {
										Buff.affect(ch, Slow.class, 4*hero.pointsInTalent(Talent.BULLET_TIME));
									}
								}
							}
						} else {
							damage *= 1.2f;
						}
						enemy.sprite.emitter().burst( ShadowParticle.UP, 5 );
						hero.buff(RouletteOfDeath.class).detach();
					}
				}
				break;
			default:
		}

		if (hero.hasTalent(Talent.WATER_FRIENDLY) && Dungeon.level.map[hero.pos] == Terrain.WATER) {
			damage += Random.NormalIntRange(1, hero.pointsInTalent(Talent.WATER_FRIENDLY));
			Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
		}

		if (hero.buff(Talent.SkilledHandTracker.class) != null) {
			damage += 1+hero.pointsInTalent(Talent.SKILLED_HAND);
			hero.buff(Talent.SkilledHandTracker.class).detach();
			Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
		}

		if (Random.Float() < hero.pointsInTalent(Talent.MADNESS)/10f) {
			Buff.prolong(enemy, Amok.class, 3f);
		}

		SpellBook.SpellBookCoolDown spellBookCoolDown = buff(SpellBook.SpellBookCoolDown.class);
		if (hero.hasTalent(Talent.BRIG_BOOST) && spellBookCoolDown != null) {
			spellBookCoolDown.hit(hero.pointsInTalent(Talent.BRIG_BOOST));
		}

		if (hero.buff(Bible.Angel.class) != null) {
			hero.heal(Math.max(Math.round(0.2f*damage), 1));
		}

		if (hero.buff(UnholyBible.Demon.class) != null) {
			damage *= 1.33f;
		}

		if (hero.buff(DualDagger.ReverseBlade.class) != null) {
			damage *= 0.5f;
			Buff.affect(enemy, Bleeding.class).add(Random.NormalIntRange(1, 3));
			if (enemy.sprite.visible) {
				Splash.at( enemy.sprite.center(), -PointF.PI / 2, PointF.PI / 6,
						enemy.sprite.blood(), Math.min( 10 * Random.NormalIntRange(1, 3) / enemy.HT, 10 ) );
			}
		}

		if (hero.buff(Awakening.class) != null && hero.buff(Awakening.class).isAwaken()) {
			damage *= 0.5f;
		}

		if (hero.buff(ShadowBlade.shadowBladeTracker.class) != null) {
			damage *= 0.5f;
		}

		if (hero.hasTalent(Talent.BIOLOGY_PROJECT)) {
			if (!(enemy.properties().contains(Property.INORGANIC) || enemy.properties().contains(Property.UNDEAD))){
				enemy.sprite.emitter().start( ShadowParticle.UP, 0.05f, 3 );
				Sample.INSTANCE.play(Assets.Sounds.BURNING);

				damage *= Math.pow(1.1f, hero.pointsInTalent(Talent.BIOLOGY_PROJECT));
			}
		}

		if (hero.belongings.attackingWeapon() instanceof MeleeWeapon && hero.heroClass != HeroClass.ADVENTURER && hero.hasTalent(Talent.LONG_MACHETE)) {
			int dist = level.distance(hero.pos, enemy.pos)-1;
			dist = Math.min(dist, hero.pointsInTalent(Talent.LONG_MACHETE));
			damage = (int)Math.round(damage * Math.pow(0.8f, dist));
		}

		//damage addition
		if (hero.hasTalent(Talent.NATURE_FRIENDLY) && (level.map[this.pos] == Terrain.HIGH_GRASS || level.map[this.pos] == Terrain.FURROWED_GRASS)) {
			damage += Random.Int(1, hero.pointsInTalent(Talent.NATURE_FRIENDLY));
		}

		if (hero.buff(TreasureMap.GoldTracker.class) != null) {
			damage *= 1 + 0.1f * hero.pointsInTalent(Talent.GOLD_HUNTER);
			hero.buff(TreasureMap.GoldTracker.class).detach();
		}

		//attacking procs
		if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
			if (hero.hasTalent(Talent.POISONOUS_BLADE)) {
				Buff.affect(enemy, Poison.class).set(2+hero.pointsInTalent(Talent.POISONOUS_BLADE));
			}
			if (hero.hasTalent(Talent.SOUL_COLLECT) && damage >= enemy.HP) {
				int healAmt = 3*hero.pointsInTalent(Talent.SOUL_COLLECT);
				healAmt = Math.min( healAmt, hero.HT - hero.HP );
				if (healAmt > 0 && hero.isAlive()) {
					hero.HP += healAmt;
					hero.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 2 );
					hero.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
				}
			}
			if (hero.hasTalent(Talent.TRAIL_TRACKING) && damage >= enemy.HP) {
				Buff.affect(hero, MindVision.class, hero.pointsInTalent(Talent.TRAIL_TRACKING));
			}

			if (hero.pointsInTalent(Talent.MASTER_OF_CLOAKING) == 3) {
				if (hero.buff(Talent.ChaseCooldown.class) != null) {
					hero.buff(Talent.ChaseCooldown.class).spendTime();
				}
				if (hero.buff(Talent.ChainCooldown.class) != null) {
					hero.buff(Talent.ChainCooldown.class).spendTime();
				}
				if (hero.buff(Talent.LethalCooldown.class) != null) {
					hero.buff(Talent.LethalCooldown.class).spendTime();
				}
			}
		}

		if (hero.hasTalent(Talent.BAYONET) && hero.buff(ReinforcedArmor.ReinforcedArmorTracker.class) != null){
			if (wep instanceof Gun) {
				Buff.affect( enemy, Bleeding.class ).set( 4 + hero.pointsInTalent(Talent.BAYONET));
			}
		}

		if (Dungeon.isChallenged(Challenges.PYRO)) {
			Buff.affect(enemy, Burning.class).reignite(enemy, 8f);
		}

		if (Dungeon.isChallenged(Challenges.FATIGUE)) {
			Buff.affect(this, Fatigue.class).hit(true);
		}

		if (hero.subClass == HeroSubClass.SLASHER) {
			Buff.affect(hero, SwordAura.class).hit(damage);
		}

		if (hero.hasTalent(Talent.BLOOMING_WEAPON)
				&& Random.Float() < 0.05f*hero.pointsInTalent(Talent.BLOOMING_WEAPON)) {
			boolean secondPlant = Random.Float() <= 0.33f;
			ArrayList<Integer> positions = new ArrayList<>();
			Blooming blooming = new Blooming();
			for (int i : PathFinder.NEIGHBOURS8){
				positions.add(i);
			}
			Random.shuffle( positions );
			for (int i : positions){
				if (blooming.plantGrass(enemy.pos + i)) {
					if (secondPlant) secondPlant = false;
					else return damage;
				}
			}
		}

		if (hero.subClass == HeroSubClass.RESEARCHER && Random.Float() < 0.2f) {
			Buff.affect(enemy, Ooze.class).set(Ooze.DURATION/4f * (1+0.5f*hero.pointsInTalent(Talent.POWERFUL_ACID)));
		}

		if (buff(TreasureMap.LuckTracker.class) != null
				&& enemy.HP <= damage) {
			Buff.affect(enemy, Lucky.LuckProc.class);
		}

		if (buff(Sheath.CertainCrit.class) != null) {
			buff(Sheath.CertainCrit.class).hit();
		}

		PinkGem.proc(enemy);
		
		return damage;
	}
	
	@Override
	public int defenseProc( Char enemy, int damage ) {
		
		if (damage > 0 && subClass == HeroSubClass.BERSERKER){
			Berserk berserk = Buff.affect(this, Berserk.class);
			berserk.damage(damage);
		}

		if (hero.subClass == HeroSubClass.GLADIATOR && Random.Float() < hero.pointsInTalent(Talent.OFFENSIVE_DEFENSE)/3f) {
			Combo combo = Buff.affect(this, Combo.class);
			combo.hit(enemy);
		}
		
		if (belongings.armor() != null) {
			damage = belongings.armor().proc( enemy, this, damage );
		}

		WandOfLivingEarth.RockArmor rockArmor = buff(WandOfLivingEarth.RockArmor.class);
		if (rockArmor != null) {
			damage = rockArmor.absorb(damage);
		}

		if (hero.hasTalent(Talent.EMERGENCY_ESCAPE) && Random.Float() < hero.pointsInTalent(Talent.EMERGENCY_ESCAPE)/50f) {
			Buff.prolong(this, Invisibility.class, 3f);
		}

		if (hero.hasTalent(Talent.OVERCOMING)) {
			Momentum momentum = buff(Momentum.class);
			if (momentum != null && momentum.freerunning()) {
				Buff.affect(this, Haste.class, 2f);
				if (hero.pointsInTalent(Talent.OVERCOMING) > 1) Buff.affect(this, Adrenaline.class, 2f);
				if (hero.pointsInTalent(Talent.OVERCOMING) > 2) Buff.affect(this, EvasiveMove.class, 2f);
			}
		}

		if (Dungeon.isChallenged(Challenges.FATIGUE)) {
			Buff.affect(this, Fatigue.class).hit(false);
		}
		
		return super.defenseProc( enemy, damage );
	}
	
	@Override
	public void damage( int dmg, Object src ) {
		if (buff(TimekeepersHourglass.timeStasis.class) != null)
			return;

		//regular damage interrupt, triggers on any damage except specific mild DOT effects
		// unless the player recently hit 'continue moving', in which case this is ignored
		if (!(src instanceof Hunger || src instanceof Viscosity.DeferedDamage) && damageInterrupt) {
			interrupt();
		}

		if (this.buff(Drowsy.class) != null){
			Buff.detach(this, Drowsy.class);
			GLog.w( Messages.get(this, "pain_resist") );
		}

		Endure.EndureTracker endure = buff(Endure.EndureTracker.class);
		if (!(src instanceof Char)){
			//reduce damage here if it isn't coming from a character (if it is we already reduced it)
			if (endure != null){
				dmg = Math.round(endure.adjustDamageTaken(dmg));
			}
			//the same also applies to challenge scroll damage reduction
			if (buff(ScrollOfChallenge.ChallengeArena.class) != null){
				dmg *= 0.67f;
			}
			//and to monk meditate damage reduction
			if (buff(MonkEnergy.MonkAbility.Meditate.MeditateResistance.class) != null){
				dmg *= 0.2f;
			}
		}

		CapeOfThorns.Thorns thorns = buff( CapeOfThorns.Thorns.class );
		if (thorns != null) {
			dmg = thorns.proc(dmg, (src instanceof Char ? (Char)src : null),  this);
		}

		dmg = (int)Math.ceil(dmg * RingOfTenacity.damageMultiplier( this ));

		LargeSword.LargeSwordBuff largeSwordBuff = hero.buff(LargeSword.LargeSwordBuff.class);
		if (largeSwordBuff != null) {
			dmg = (int)Math.ceil(dmg * largeSwordBuff.getDefenseFactor());
		}

		//TODO improve this when I have proper damage source logic
		if (belongings.armor() != null && belongings.armor().hasGlyph(AntiMagic.class, this)
				&& AntiMagic.RESISTS.contains(src.getClass())){
			dmg -= AntiMagic.drRoll(this, belongings.armor().buffedLvl());
			dmg = Math.max(dmg, 0);
		}

		if (buff(Talent.WarriorFoodImmunity.class) != null){
			if (pointsInTalent(Talent.IRON_STOMACH) == 1)       dmg = Math.round(dmg*0.25f);
			else if (pointsInTalent(Talent.IRON_STOMACH) == 2)  dmg = Math.round(dmg*0.00f);
		}

		if (buff(Tackle.SuperArmorTracker.class) != null) {
			switch (pointsInTalent(Talent.SUPER_ARMOR)) {
				case 1:
					dmg = Math.round(dmg*0.67f);
					break;
				case 2:
					dmg = Math.round(dmg*0.33f);
					break;
				case 3:
					dmg = Math.round(dmg*0.00f);
					break;
				case 0: default:
					break;
			}
		}

		int preHP = HP + shielding();
		if (src instanceof Hunger) preHP -= shielding();
		super.damage( dmg, src );
		int postHP = HP + shielding();
		if (src instanceof Hunger) postHP -= shielding();
		int effectiveDamage = preHP - postHP;

		if (effectiveDamage <= 0) return;

		if (buff(Challenge.DuelParticipant.class) != null){
			buff(Challenge.DuelParticipant.class).addDamage(effectiveDamage);
		}

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
				//hero gets interrupted on taking serious damage, regardless of any other factor
				interrupt();
				damageInterrupt = true;
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

				//only do a simple check for mind visioned enemies, better performance
				if ((!mindVisionEnemies.contains(m) && QuickSlotButton.autoAim(m) != -1)
						|| (mindVisionEnemies.contains(m) && new Ballistica( pos, m.pos, Ballistica.PROJECTILE ).collisionPos == m.pos)) {
					if (target == null) {
						target = m;
					} else if (distance(target) > distance(m)) {
						target = m;
					}
					if (m instanceof Snake && Dungeon.level.distance(m.pos, pos) <= 4
							&& !Document.ADVENTURERS_GUIDE.isPageRead(Document.GUIDE_EXAMINING)){
						GLog.p(Messages.get(Guidebook.class, "hint"));
						GameScene.flashForDocument(Document.ADVENTURERS_GUIDE, Document.GUIDE_EXAMINING);
						//we set to read here to prevent this message popping up a bunch
						Document.ADVENTURERS_GUIDE.readPage(Document.GUIDE_EXAMINING);
					}
				}
			}
		}

		Char lastTarget = QuickSlotButton.lastTarget;
		if (target != null && (lastTarget == null ||
							!lastTarget.isAlive() || !lastTarget.isActive() ||
							lastTarget.alignment == Alignment.ALLY ||
							!fieldOfView[lastTarget.pos])){
			QuickSlotButton.target(target);
		}
		
		if (newMob) {
			if (resting){
				Dungeon.observe();
			}
			interrupt();
		}

		visibleEnemies = visible;
	}
	
	public int visibleEnemies() {
		return visibleEnemies.size();
	}
	
	public Mob visibleEnemy( int index ) {
		return visibleEnemies.get(index % visibleEnemies.size());
	}

	public ArrayList<Mob> getVisibleEnemies(){
		return new ArrayList<>(visibleEnemies);
	}
	
	private boolean walkingToVisibleTrapInFog = false;
	
	//FIXME this is a fairly crude way to track this, really it would be nice to have a short
	//history of hero actions
	public boolean justMoved = false;
	
	private boolean getCloser( final int target ) {

		if (target == pos)
			return false;

		if (rooted) {
			PixelScene.shake( 1, 1f );
			return false;
		}
		
		int step = -1;
		
		if (Dungeon.level.adjacent( pos, target )) {

			path = null;

			if (Actor.findChar( target ) == null) {
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

			float delay = 1 / speed();

			if (Dungeon.level.pit[step] && !Dungeon.level.solid[step]
					&& (!flying || buff(Levitation.class) != null && buff(Levitation.class).detachesWithinDelay(delay))){
				if (!Chasm.jumpConfirmed){
					Chasm.heroJump(this);
					interrupt();
				} else {
					flying = false;
					remove(buff(Levitation.class)); //directly remove to prevent cell pressing
					Chasm.heroFall(target);
				}
				canSelfTrample = false;
				return false;
			}

			if ((hero.belongings.weapon instanceof Lance ||
					hero.belongings.secondWep instanceof Lance ||
					(hero.belongings.weapon instanceof LanceNShield && ((LanceNShield)hero.belongings.weapon).stance) ||
					(hero.belongings.secondWep instanceof LanceNShield && ((LanceNShield)hero.belongings.secondWep).stance))) {
				Buff.affect(this, Lance.LanceBuff.class).setDamageFactor(1 + (hero.speed()), hero.belongings.secondWep instanceof Lance || (hero.belongings.secondWep instanceof LanceNShield && ((LanceNShield) hero.belongings.secondWep).stance));
			}

			if (subClass == HeroSubClass.FREERUNNER){
				Buff.affect(this, Momentum.class).gainStack();
			}

			if (hero.buff(Talent.RollingTracker.class) != null && hero.belongings.weapon instanceof Gun && Dungeon.bullet > 1) {
				Dungeon.bullet --;
				((Gun)hero.belongings.weapon).manualReload();
				hero.buff(Talent.RollingTracker.class).detach();
			}

			if (hero.hasTalent(Talent.QUICK_RELOAD) && hero.belongings.weapon instanceof Gun && Random.Float() < 0.03f * hero.pointsInTalent(Talent.QUICK_RELOAD) && Dungeon.bullet > 1) {
				Dungeon.bullet --;
				((Gun)hero.belongings.weapon).manualReload();
			}

			if (hero.hasTalent(Talent.MIND_VISION) && Random.Float() < 0.01f*hero.pointsInTalent(Talent.MIND_VISION)) {
				Buff.affect(this, MindVision.class, 1f);
			}
			
			sprite.move(pos, step);
			move(step);

			if (buff(Talent.QuickStep.class) != null) {
				spend(-delay);
				buff(Talent.QuickStep.class).detach();
			}

			spend( delay );
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

			if (((Mob) ch).heroShouldInteract()) {
				curAction = new HeroAction.Interact( ch );
			} else {
				curAction = new HeroAction.Attack( ch );
			}

		//TODO perhaps only trigger this if hero is already adjacent? reducing mistaps
		} else if (Dungeon.level instanceof MiningLevel &&
					belongings.getItem(Pickaxe.class) != null &&
				(Dungeon.level.map[cell] == Terrain.WALL
						|| Dungeon.level.map[cell] == Terrain.WALL_DECO
						|| Dungeon.level.map[cell] == Terrain.MINE_CRYSTAL
						|| Dungeon.level.map[cell] == Terrain.MINE_BOULDER)){

			curAction = new HeroAction.Mine( cell );

		} else if (heap != null
				//moving to an item doesn't auto-pickup when enemies are near...
				&& (visibleEnemies.size() == 0 || cell == pos ||
				//...but only for standard heaps. Chests and similar open as normal.
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
			
		} else if (Dungeon.level.map[cell] == Terrain.LOCKED_DOOR || Dungeon.level.map[cell] == Terrain.CRYSTAL_DOOR || Dungeon.level.map[cell] == Terrain.LOCKED_EXIT) {
			
			curAction = new HeroAction.Unlock( cell );
			
		} else if (Dungeon.level.getTransition(cell) != null
				//moving to a transition doesn't automatically trigger it when enemies are near
				&& (visibleEnemies.size() == 0 || cell == pos)
				&& !Dungeon.level.locked
				&& !Dungeon.level.plants.containsKey(cell)
				&& (Dungeon.depth < 31 || Dungeon.level.getTransition(cell).type == LevelTransition.Type.REGULAR_ENTRANCE) ) {

			curAction = new HeroAction.LvlTransition( cell );
			
		}  else {
			
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

		//xp granted by ascension challenge is only for on-exp gain effects
		if (source != AscensionChallenge.class) {
			this.exp += exp;
		}
		float percent = exp/(float)maxExp();

		EtherealChains.chainsRecharge chains = buff(EtherealChains.chainsRecharge.class);
		if (chains != null) chains.gainExp(percent);

		HornOfPlenty.hornRecharge horn = buff(HornOfPlenty.hornRecharge.class);
		if (horn != null) horn.gainCharge(percent);
		
		AlchemistsToolkit.kitEnergy kit = buff(AlchemistsToolkit.kitEnergy.class);
		if (kit != null) kit.gainCharge(percent);

		MasterThievesArmband.Thievery armband = buff(MasterThievesArmband.Thievery.class);
		if (armband != null) armband.gainCharge(percent);
		
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
			if (buff(ElementalStrike.ElementalStrikeFurrowCounter.class) != null){
				buff(ElementalStrike.ElementalStrikeFurrowCounter.class).countDown(percent*20f);
				if (buff(ElementalStrike.ElementalStrikeFurrowCounter.class).count() <= 0){
					buff(ElementalStrike.ElementalStrikeFurrowCounter.class).detach();
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
			
			Item.updateQuickslot();
			
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
	public boolean add( Buff buff ) {

		if (buff(TimekeepersHourglass.timeStasis.class) != null) {
			return false;
		}

		boolean added = super.add( buff );

		if (sprite != null && added) {
			String msg = buff.heroMessage();
			if (msg != null){
				GLog.w(msg);
			}

			if (buff instanceof Paralysis || buff instanceof Vertigo) {
				interrupt();
			}

		}
		
		BuffIndicator.refreshHero();

		return added;
	}
	
	@Override
	public boolean remove( Buff buff ) {
		if (super.remove( buff )) {
			BuffIndicator.refreshHero();
			return true;
		}
		return false;
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

		if (buff(Enduring.class) != null) {
			this.HP = 1;
			return;
		}
		
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

				if (cause instanceof Hero.Doom) {
					((Hero.Doom)cause).onDeath();
				}

				SacrificialFire.Marked sacMark = buff(SacrificialFire.Marked.class);
				if (sacMark != null){
					sacMark.detach();
				}

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

		Game.runOnRenderThread(new Callback() {
			@Override
			public void call() {
				GameScene.gameOver();
				Sample.INSTANCE.play( Assets.Sounds.DEATH );
			}
		});

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

		if (enemy == null){
			curAction = null;
			super.onAttackComplete();
			return;
		}
		
		AttackIndicator.target(enemy);
		boolean wasEnemy = enemy.alignment == Alignment.ENEMY
				|| (enemy instanceof Mimic && enemy.alignment == Alignment.NEUTRAL);

		boolean hit = attack( enemy );
		
		Invisibility.dispel();
		spend( attackDelay() );

		if (hit && subClass == HeroSubClass.GLADIATOR && wasEnemy){
			Buff.affect( this, Combo.class ).hit( enemy );
		}

		if (hit && subClass == HeroSubClass.BATTLEMAGE && hero.belongings.attackingWeapon() instanceof MagesStaff && hero.hasTalent(Talent.BATTLE_MAGIC) && wasEnemy) {
			Buff.affect( this, MagicalCombo.class).hit( enemy );
		}

		if (hero.subClass == HeroSubClass.CHASER
				&& hero.hasTalent(Talent.CHAIN_CLOCK)
				&& ((Mob) enemy).surprisedBy(hero)
				&& hero.buff(Talent.ChainCooldown.class) == null){
			Buff.affect( this, Invisibility.class, 1f * hero.pointsInTalent(Talent.CHAIN_CLOCK));
			Buff.affect( this, Haste.class, 1f * hero.pointsInTalent(Talent.CHAIN_CLOCK));
			Buff.affect( this, Talent.ChainCooldown.class, 10f);
			Sample.INSTANCE.play( Assets.Sounds.MELD );
		}

		if (hero.subClass == HeroSubClass.CHASER
				&& hero.hasTalent(Talent.LETHAL_SURPRISE)
				&& ((Mob) enemy).surprisedBy(hero)
				&& !enemy.isAlive()
				&& hero.buff(Talent.LethalCooldown.class) == null) {
			if (hero.pointsInTalent(Talent.LETHAL_SURPRISE) >= 1) {
				for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
					if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos] && mob.state != mob.SLEEPING) {
						Buff.affect( mob, Vulnerable.class, 1f);
					}
				}
				Buff.affect(hero, Talent.LethalCooldown.class, 5f);
			}
			if (hero.pointsInTalent(Talent.LETHAL_SURPRISE) >= 2) {
				for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
					if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos] && mob.state != mob.SLEEPING) {
						Buff.affect( mob, Paralysis.class, 1f);
					}
				}
			}
			if (hero.pointsInTalent(Talent.LETHAL_SURPRISE) == 3) {
				Buff.affect(hero, Swiftthistle.TimeBubble.class).twoTurns();
			}
		}

		if (hit && heroClass == HeroClass.DUELIST && wasEnemy){
			Buff.affect( this, Sai.ComboStrikeTracker.class).addHit();
		}

		if (!hit && hero.belongings.weapon == null && hero.subClass == HeroSubClass.FIGHTER && Random.Int(5) == 0 && hero.pointsInTalent(Talent.SWIFT_MOVEMENT) > 1) {
			Buff.prolong(hero, EvasiveMove.class, 0.9999f);
		}

		if (hero.buff(Sheath.Sheathing.class) != null) {
			hero.buff(Sheath.Sheathing.class).detach();
			if (!enemy.isAlive() && Random.Float() < hero.pointsInTalent(Talent.QUICK_SHEATHING)/3f) {
				Buff.affect(hero, Sheath.Sheathing.class);
			}
		}
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
				} else if (door == Terrain.CRYSTAL_DOOR) {
					hasKey = Notes.remove(new CrystalKey(Dungeon.depth, Dungeon.branch));
					if (hasKey) {
						Level.set(doorCell, Terrain.EMPTY);
						Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
						CellEmitter.get( doorCell ).start( Speck.factory( Speck.DISCOVER ), 0.025f, 20 );
					}
				} else {
					hasKey = Notes.remove(new SkeletonKey(Dungeon.depth));
					if (hasKey) Level.set(doorCell, Terrain.UNLOCKED_EXIT);
				}
				
				if (hasKey) {
					GameScene.updateKeyDisplay();
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

		if (!ready) {
			super.onOperateComplete();
		}
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
		return super.isInvulnerable(effect) || buff(AnkhInvulnerability.class) != null;
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
							chance = BrokenMagnifyingGlass.trapNoticeChance();
							
						//unintentional door detection scales from 20% at floor 0 to 0% at floor 20
						} else {
							chance = BrokenMagnifyingGlass.doorNoticeChance();
						}

						//don't want to let the player search though hidden doors in tutorial
						if (SPDSettings.intro()){
							chance = 0;
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

		if (talisman != null){
			talisman.checkAwareness();
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
			} else if (i instanceof CloakOfShadows && i.keptThroughLostInventory() && hasTalent(Talent.LIGHT_CLOAK)){
				((CloakOfShadows) i).activate(this);
			} else if (i instanceof Wand && i.keptThroughLostInventory()){
				if (holster != null && holster.contains(i)){
					((Wand) i).charge(this, MagicalHolster.HOLSTER_SCALE_FACTOR);
				} else {
					((Wand) i).charge(this);
				}
			} else if (i instanceof MagesStaff && i.keptThroughLostInventory()){
				((MagesStaff) i).applyWandChargeBuff(this);
			}
		}

		updateHT(false);
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
