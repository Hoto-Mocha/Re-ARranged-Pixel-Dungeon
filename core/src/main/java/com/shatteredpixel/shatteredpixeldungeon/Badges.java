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

package com.shatteredpixel.shatteredpixeldungeon;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.MagicalHolster;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.PotionBandolier;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.ScrollHolder;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.VelvetPouch;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.remains.RemainsItem;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.journal.Bestiary;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.journal.Document;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class Badges {

	public enum BadgeType {
		HIDDEN, //internal badges used for data tracking
		LOCAL,  //unlocked on a per-run basis and added to overall player profile
		GLOBAL, //unlocked for the save profile only, usually over multiple runs
		JOURNAL //profile-based and also tied to the journal, which means they even unlock in seeded runs
	}

	public enum Badge {
		MASTERY_WARRIOR,
		MASTERY_MAGE,
		MASTERY_ROGUE,
		MASTERY_HUNTRESS,
		MASTERY_DUELIST,
		MASTERY_CLERIC,
		MASTERY_GUNNER,
		MASTERY_SAMURAI,
		MASTERY_ADVENTURER,
		MASTERY_KNIGHT,
		MASTERY_MEDIC,
		FOUND_RATMOGRIFY,

		//bronze
		UNLOCK_MAGE                 ( 1 ),
		UNLOCK_ROGUE                ( 2 ),
		UNLOCK_HUNTRESS             ( 3 ),
		UNLOCK_DUELIST              ( 4 ),
		UNLOCK_CLERIC               ( 5 ),
		MONSTERS_SLAIN_1            ( 6 ),
		MONSTERS_SLAIN_2            ( 7 ),
		GOLD_COLLECTED_1            ( 8 ),
		GOLD_COLLECTED_2            ( 9 ),
		ITEM_LEVEL_1                ( 10 ),
		LEVEL_REACHED_1             ( 11 ),
		STRENGTH_ATTAINED_1         ( 12 ),
		FOOD_EATEN_1                ( 13 ),
		ITEMS_CRAFTED_1             ( 14 ),
		BOSS_SLAIN_1                ( 15 ),
		CATALOG_ONE_EQUIPMENT       ( 16, BadgeType.JOURNAL ),
		DEATH_FROM_FIRE             ( 17 ),
		DEATH_FROM_POISON           ( 18 ),
		DEATH_FROM_GAS              ( 19 ),
		DEATH_FROM_HUNGER           ( 20 ),
		DEATH_FROM_FALLING          ( 21 ),
		RESEARCHER_1                ( 22, BadgeType.JOURNAL ),
		GAMES_PLAYED_1              ( 23, BadgeType.GLOBAL ),
		HIGH_SCORE_1                ( 24 ),

		//silver
		NO_MONSTERS_SLAIN           ( 32 ),
		BOSS_SLAIN_REMAINS          ( 33 ),
		MONSTERS_SLAIN_3            ( 34 ),
		MONSTERS_SLAIN_4            ( 35 ),
		GOLD_COLLECTED_3            ( 36 ),
		GOLD_COLLECTED_4            ( 37 ),
		ITEM_LEVEL_2                ( 38 ),
		ITEM_LEVEL_3                ( 39 ),
		LEVEL_REACHED_2             ( 40 ),
		LEVEL_REACHED_3             ( 41 ),
		STRENGTH_ATTAINED_2         ( 42 ),
		STRENGTH_ATTAINED_3         ( 43 ),
		FOOD_EATEN_2                ( 44 ),
		FOOD_EATEN_3                ( 45 ),
		ITEMS_CRAFTED_2             ( 46 ),
		ITEMS_CRAFTED_3             ( 47 ),
		BOSS_SLAIN_2                ( 48 ),
		BOSS_SLAIN_3                ( 49 ),
		ALL_POTIONS_IDENTIFIED      , //still exists internally for pre-2.5 saves
		ALL_PILLS_IDENTIFIED      	, //still exists internally for pre-2.5 saves
		ALL_SCROLLS_IDENTIFIED      , //still exists internally for pre-2.5 saves
		CATALOG_POTIONS_SCROLLS     ( 50 ),
		DEATH_FROM_ENEMY_MAGIC      ( 51 ),
		DEATH_FROM_FRIENDLY_MAGIC   ( 52 ),
		DEATH_FROM_SACRIFICE        ( 53 ),
		BOSS_SLAIN_1_WARRIOR,
		BOSS_SLAIN_1_MAGE,
		BOSS_SLAIN_1_ROGUE,
		BOSS_SLAIN_1_HUNTRESS,
		BOSS_SLAIN_1_DUELIST,
		BOSS_SLAIN_1_CLERIC,
		BOSS_SLAIN_1_GUNNER,
		BOSS_SLAIN_1_SAMURAI,
		BOSS_SLAIN_1_ADVENTURER,
		BOSS_SLAIN_1_KNIGHT,
		BOSS_SLAIN_1_MEDIC,
		BOSS_SLAIN_1_ALL_CLASSES    ( 54, BadgeType.GLOBAL ),
		RESEARCHER_2                ( 55, BadgeType.JOURNAL ),
		GAMES_PLAYED_2              ( 56, BadgeType.GLOBAL ),
		HIGH_SCORE_2                ( 57 ),

		//gold
		PIRANHAS                    ( 64 ),
		GRIM_WEAPON                 ( 65 ),
		BAG_BOUGHT_VELVET_POUCH,
		BAG_BOUGHT_SCROLL_HOLDER,
		BAG_BOUGHT_POTION_BANDOLIER,
		BAG_BOUGHT_MAGICAL_HOLSTER,
		ALL_BAGS_BOUGHT             ( 66 ),
		MASTERY_COMBO               ( 67 ),
		MONSTERS_SLAIN_5            ( 68 ),
		GOLD_COLLECTED_5            ( 69 ),
		ITEM_LEVEL_4                ( 70 ),
		LEVEL_REACHED_4             ( 71 ),
		STRENGTH_ATTAINED_4         ( 72 ),
		STRENGTH_ATTAINED_5         ( 73 ),
		FOOD_EATEN_4                ( 74 ),
		FOOD_EATEN_5                ( 75 ),
		ITEMS_CRAFTED_4             ( 76 ),
		ITEMS_CRAFTED_5             ( 77 ),
		BOSS_SLAIN_4                ( 78 ),
		ALL_RINGS_IDENTIFIED        , //still exists internally for pre-2.5 saves
		ALL_ARTIFACTS_IDENTIFIED    , //still exists internally for pre-2.5 saves
		ALL_RARE_ENEMIES            ( 79, BadgeType.JOURNAL ),
		DEATH_FROM_GRIM_TRAP        ( 80 ), //also disintegration traps
		VICTORY                     ( 81 ),
		BOSS_CHALLENGE_1            ( 82 ),
		BOSS_CHALLENGE_2            ( 83 ),
		RESEARCHER_3                ( 84, BadgeType.JOURNAL ),
		GAMES_PLAYED_3              ( 85, BadgeType.GLOBAL ),
		HIGH_SCORE_3                ( 86 ),

		//platinum
		ITEM_LEVEL_5                ( 96 ),
		LEVEL_REACHED_5             ( 97 ),
		HAPPY_END                   ( 98 ),
		HAPPY_END_REMAINS           ( 99 ),
		RODNEY                      ( 100, BadgeType.JOURNAL ),
		ALL_WEAPONS_IDENTIFIED      , //still exists internally for pre-2.5 saves
		ALL_ARMOR_IDENTIFIED        , //still exists internally for pre-2.5 saves
		ALL_WANDS_IDENTIFIED        , //still exists internally for pre-2.5 saves
		ALL_SPELLBOOKS_IDENTIFIED   , //still exists internally for pre-2.5 saves
		ALL_ITEMS_IDENTIFIED        , //still exists internally for pre-2.5 saves
		VICTORY_WARRIOR,
		VICTORY_MAGE,
		VICTORY_ROGUE,
		VICTORY_HUNTRESS,
		VICTORY_DUELIST,
		VICTORY_CLERIC,
		VICTORY_GUNNER,
		VICTORY_SAMURAI,
		VICTORY_ADVENTURER,
		VICTORY_KNIGHT,
		VICTORY_MEDIC,
		VICTORY_ALL_CLASSES         ( 101, BadgeType.GLOBAL ),
		DEATH_FROM_ALL              ( 102, BadgeType.GLOBAL ),
		BOSS_SLAIN_3_BERSERKER,
		BOSS_SLAIN_3_GLADIATOR,
		BOSS_SLAIN_3_VETERAN,

		BOSS_SLAIN_3_BATTLEMAGE,
		BOSS_SLAIN_3_WARLOCK,
		BOSS_SLAIN_3_WIZARD,

		BOSS_SLAIN_3_ASSASSIN,
		BOSS_SLAIN_3_FREERUNNER,
		BOSS_SLAIN_3_CHASER,

		BOSS_SLAIN_3_SNIPER,
		BOSS_SLAIN_3_WARDEN,
		BOSS_SLAIN_3_FIGHTER,

		BOSS_SLAIN_3_CHAMPION,
		BOSS_SLAIN_3_MONK,
		BOSS_SLAIN_3_FENCER,

		BOSS_SLAIN_3_PRIEST,
		BOSS_SLAIN_3_PALADIN,
		BOSS_SLAIN_3_ENCHANTER,

		BOSS_SLAIN_3_OUTLAW,
		BOSS_SLAIN_3_GUNSLINGER,
		BOSS_SLAIN_3_SPECIALIST,

		BOSS_SLAIN_3_SLASHER,
		BOSS_SLAIN_3_MASTER,
		BOSS_SLAIN_3_SLAYER,

		BOSS_SLAIN_3_ENGINEER,
		BOSS_SLAIN_3_EXPLORER,
		BOSS_SLAIN_3_RESEARCHER,

		BOSS_SLAIN_3_DEATHKNIGHT,
		BOSS_SLAIN_3_HORSEMAN,
		BOSS_SLAIN_3_CRUSADER,

		BOSS_SLAIN_3_SAVIOR,
		BOSS_SLAIN_3_THERAPIST,
		BOSS_SLAIN_3_MEDICALOFFICER,
		BOSS_SLAIN_3_ALL_SUBCLASSES ( 103, BadgeType.GLOBAL ),
		BOSS_CHALLENGE_3            ( 104 ),
		BOSS_CHALLENGE_4            ( 105 ),
		RESEARCHER_4                ( 106, BadgeType.JOURNAL ),
		GAMES_PLAYED_4              ( 107, BadgeType.GLOBAL ),
		HIGH_SCORE_4                ( 108 ),
		CHAMPION_1                  ( 109 ),

		//diamond
		BOSS_CHALLENGE_5            ( 120 ),
		RESEARCHER_5                ( 121, BadgeType.JOURNAL ),
		GAMES_PLAYED_5              ( 122, BadgeType.GLOBAL ),
		HIGH_SCORE_5                ( 123 ),
		CHAMPION_2                  ( 124 ),
		CHAMPION_3                  ( 125 ),

		UNLOCK_GUNNER				(136),
		UNLOCK_SAMURAI				(137),
		UNLOCK_ADVENTURER			(138),
		UNLOCK_KNIGHT				(139),
		UNLOCK_MEDIC				(140),

		HEDGEHOG					(141);

		public boolean meta;

		public int image;
		public BadgeType type;

		Badge(){
			this(-1, BadgeType.HIDDEN);
		}

		Badge( int image ) {
			this( image, BadgeType.LOCAL );
		}

		Badge( int image, BadgeType type ) {
			this.image = image;
			this.type = type;
		}

		public String title(){
			return Messages.get(this, name()+".title");
		}

		public String desc(){
			return Messages.get(this, name()+".desc");
		}
	}
	
	private static HashSet<Badge> global;
	private static HashSet<Badge> local = new HashSet<>();
	
	private static boolean saveNeeded = false;

	public static void reset() {
		local.clear();
		loadGlobal();
	}
	
	public static final String BADGES_FILE	= "badges.dat";
	private static final String BADGES		= "badges";
	
	private static final HashSet<String> removedBadges = new HashSet<>();
	static{
		//no removed badges currently
	}

	private static final HashMap<String, String> renamedBadges = new HashMap<>();
	static{
		//no renamed badges currently
	}

	public static HashSet<Badge> restore( Bundle bundle ) {
		HashSet<Badge> badges = new HashSet<>();
		if (bundle == null) return badges;
		
		String[] names = bundle.getStringArray( BADGES );
		if (names == null) return badges;

		for (int i=0; i < names.length; i++) {
			try {
				if (renamedBadges.containsKey(names[i])){
					names[i] = renamedBadges.get(names[i]);
				}
				if (!removedBadges.contains(names[i])){
					badges.add( Badge.valueOf( names[i] ) );
				}
			} catch (Exception e) {
				ShatteredPixelDungeon.reportException(e);
			}
		}

		addReplacedBadges(badges);
	
		return badges;
	}
	
	public static void store( Bundle bundle, HashSet<Badge> badges ) {
		addReplacedBadges(badges);

		int count = 0;
		String names[] = new String[badges.size()];
		
		for (Badge badge:badges) {
			names[count++] = badge.name();
		}
		bundle.put( BADGES, names );
	}
	
	public static void loadLocal( Bundle bundle ) {
		local = restore( bundle );
	}
	
	public static void saveLocal( Bundle bundle ) {
		store( bundle, local );
	}
	
	public static void loadGlobal() {
		if (global == null) {
			try {
				Bundle bundle = FileUtils.bundleFromFile( BADGES_FILE );
				global = restore( bundle );

			} catch (IOException e) {
				global = new HashSet<>();
			}
		}
	}

	public static void saveGlobal(){
		saveGlobal(false);
	}

	public static void saveGlobal(boolean force) {
		if (saveNeeded || force) {
			
			Bundle bundle = new Bundle();
			store( bundle, global );
			
			try {
				FileUtils.bundleToFile(BADGES_FILE, bundle);
				saveNeeded = false;
			} catch (IOException e) {
				ShatteredPixelDungeon.reportException(e);
			}
		}
	}

	public static int totalUnlocked(boolean global){
		if (global) return Badges.global.size();
		else        return Badges.local.size();
	}

	public static void validateMonstersSlain() {
		Badge badge = null;
		
		if (!local.contains( Badge.MONSTERS_SLAIN_1 ) && Statistics.enemiesSlain >= 10) {
			badge = Badge.MONSTERS_SLAIN_1;
			local.add( badge );
		}
		if (!local.contains( Badge.MONSTERS_SLAIN_2 ) && Statistics.enemiesSlain >= 50) {
			if (badge != null) unlock(badge);
			badge = Badge.MONSTERS_SLAIN_2;
			local.add( badge );
		}
		if (!local.contains( Badge.MONSTERS_SLAIN_3 ) && Statistics.enemiesSlain >= 100) {
			if (badge != null) unlock(badge);
			badge = Badge.MONSTERS_SLAIN_3;
			local.add( badge );
		}
		if (!local.contains( Badge.MONSTERS_SLAIN_4 ) && Statistics.enemiesSlain >= 250) {
			if (badge != null) unlock(badge);
			badge = Badge.MONSTERS_SLAIN_4;
			local.add( badge );
		}
		if (!local.contains( Badge.MONSTERS_SLAIN_5 ) && Statistics.enemiesSlain >= 500) {
			if (badge != null) unlock(badge);
			badge = Badge.MONSTERS_SLAIN_5;
			local.add( badge );
		}
		
		displayBadge( badge );
	}
	
	public static void validateGoldCollected() {
		Badge badge = null;
		
		if (!local.contains( Badge.GOLD_COLLECTED_1 ) && Statistics.goldCollected >= 250) {
			if (badge != null) unlock(badge);
			badge = Badge.GOLD_COLLECTED_1;
			local.add( badge );
		}
		if (!local.contains( Badge.GOLD_COLLECTED_2 ) && Statistics.goldCollected >= 1000) {
			if (badge != null) unlock(badge);
			badge = Badge.GOLD_COLLECTED_2;
			local.add( badge );
		}
		if (!local.contains( Badge.GOLD_COLLECTED_3 ) && Statistics.goldCollected >= 2500) {
			if (badge != null) unlock(badge);
			badge = Badge.GOLD_COLLECTED_3;
			local.add( badge );
		}
		if (!local.contains( Badge.GOLD_COLLECTED_4 ) && Statistics.goldCollected >= 7500) {
			if (badge != null) unlock(badge);
			badge = Badge.GOLD_COLLECTED_4;
			local.add( badge );
		}
		if (!local.contains( Badge.GOLD_COLLECTED_5 ) && Statistics.goldCollected >= 15_000) {
			if (badge != null) unlock(badge);
			badge = Badge.GOLD_COLLECTED_5;
			local.add( badge );
		}
		
		displayBadge( badge );
	}
	
	public static void validateLevelReached() {
		Badge badge = null;
		
		if (!local.contains( Badge.LEVEL_REACHED_1 ) && Dungeon.hero.lvl >= 6) {
			badge = Badge.LEVEL_REACHED_1;
			local.add( badge );
		}
		if (!local.contains( Badge.LEVEL_REACHED_2 ) && Dungeon.hero.lvl >= 12) {
			if (badge != null) unlock(badge);
			badge = Badge.LEVEL_REACHED_2;
			local.add( badge );
		}
		if (!local.contains( Badge.LEVEL_REACHED_3 ) && Dungeon.hero.lvl >= 18) {
			if (badge != null) unlock(badge);
			badge = Badge.LEVEL_REACHED_3;
			local.add( badge );
		}
		if (!local.contains( Badge.LEVEL_REACHED_4 ) && Dungeon.hero.lvl >= 24) {
			if (badge != null) unlock(badge);
			badge = Badge.LEVEL_REACHED_4;
			local.add( badge );
		}
		if (!local.contains( Badge.LEVEL_REACHED_5 ) && Dungeon.hero.lvl >= 30) {
			if (badge != null) unlock(badge);
			badge = Badge.LEVEL_REACHED_5;
			local.add( badge );
		}
		
		displayBadge( badge );
	}
	
	public static void validateStrengthAttained() {
		Badge badge = null;
		
		if (!local.contains( Badge.STRENGTH_ATTAINED_1 ) && Dungeon.hero.STR >= 12) {
			badge = Badge.STRENGTH_ATTAINED_1;
			local.add( badge );
		}
		if (!local.contains( Badge.STRENGTH_ATTAINED_2 ) && Dungeon.hero.STR >= 14) {
			if (badge != null) unlock(badge);
			badge = Badge.STRENGTH_ATTAINED_2;
			local.add( badge );
		}
		if (!local.contains( Badge.STRENGTH_ATTAINED_3 ) && Dungeon.hero.STR >= 16) {
			if (badge != null) unlock(badge);
			badge = Badge.STRENGTH_ATTAINED_3;
			local.add( badge );
		}
		if (!local.contains( Badge.STRENGTH_ATTAINED_4 ) && Dungeon.hero.STR >= 18) {
			if (badge != null) unlock(badge);
			badge = Badge.STRENGTH_ATTAINED_4;
			local.add( badge );
		}
		if (!local.contains( Badge.STRENGTH_ATTAINED_5 ) && Dungeon.hero.STR >= 20) {
			if (badge != null) unlock(badge);
			badge = Badge.STRENGTH_ATTAINED_5;
			local.add( badge );
		}
		
		displayBadge( badge );
	}
	
	public static void validateFoodEaten() {
		Badge badge = null;
		
		if (!local.contains( Badge.FOOD_EATEN_1 ) && Statistics.foodEaten >= 10) {
			badge = Badge.FOOD_EATEN_1;
			local.add( badge );
		}
		if (!local.contains( Badge.FOOD_EATEN_2 ) && Statistics.foodEaten >= 20) {
			if (badge != null) unlock(badge);
			badge = Badge.FOOD_EATEN_2;
			local.add( badge );
		}
		if (!local.contains( Badge.FOOD_EATEN_3 ) && Statistics.foodEaten >= 30) {
			if (badge != null) unlock(badge);
			badge = Badge.FOOD_EATEN_3;
			local.add( badge );
		}
		if (!local.contains( Badge.FOOD_EATEN_4 ) && Statistics.foodEaten >= 40) {
			if (badge != null) unlock(badge);
			badge = Badge.FOOD_EATEN_4;
			local.add( badge );
		}
		if (!local.contains( Badge.FOOD_EATEN_5 ) && Statistics.foodEaten >= 50) {
			if (badge != null) unlock(badge);
			badge = Badge.FOOD_EATEN_5;
			local.add( badge );
		}
		
		displayBadge( badge );
	}
	
	public static void validateItemsCrafted() {
		Badge badge = null;
		
		if (!local.contains( Badge.ITEMS_CRAFTED_1 ) && Statistics.itemsCrafted >= 3) {
			badge = Badge.ITEMS_CRAFTED_1;
			local.add( badge );
		}
		if (!local.contains( Badge.ITEMS_CRAFTED_2 ) && Statistics.itemsCrafted >= 8) {
			if (badge != null) unlock(badge);
			badge = Badge.ITEMS_CRAFTED_2;
			local.add( badge );
		}
		if (!local.contains( Badge.ITEMS_CRAFTED_3 ) && Statistics.itemsCrafted >= 15) {
			if (badge != null) unlock(badge);
			badge = Badge.ITEMS_CRAFTED_3;
			local.add( badge );
		}
		if (!local.contains( Badge.ITEMS_CRAFTED_4 ) && Statistics.itemsCrafted >= 24) {
			if (badge != null) unlock(badge);
			badge = Badge.ITEMS_CRAFTED_4;
			local.add( badge );
		}
		if (!local.contains( Badge.ITEMS_CRAFTED_5 ) && Statistics.itemsCrafted >= 35) {
			if (badge != null) unlock(badge);
			badge = Badge.ITEMS_CRAFTED_5;
			local.add( badge );
		}
		
		displayBadge( badge );
	}
	
	public static void validatePiranhasKilled() {
		Badge badge = null;
		
		if (!local.contains( Badge.PIRANHAS ) && Statistics.piranhasKilled >= 6) {
			badge = Badge.PIRANHAS;
			local.add( badge );
		}
		
		displayBadge( badge );
	}
	
	public static void validateItemLevelAquired( Item item ) {
		
		// This method should be called:
		// 1) When an item is obtained (Item.collect)
		// 2) When an item is upgraded (ScrollOfUpgrade, ScrollOfWeaponUpgrade, ShortSword, WandOfMagicMissile)
		// 3) When an item is identified

		// Note that artifacts should never trigger this badge as they are alternatively upgraded
		if (!item.levelKnown || item instanceof Artifact) {
			return;
		}

		if (item instanceof MeleeWeapon){
			validateDuelistUnlock();
		}
		
		Badge badge = null;
		if (!local.contains( Badge.ITEM_LEVEL_1 ) && item.level() >= 3) {
			badge = Badge.ITEM_LEVEL_1;
			local.add( badge );
		}
		if (!local.contains( Badge.ITEM_LEVEL_2 ) && item.level() >= 6) {
			if (badge != null) unlock(badge);
			badge = Badge.ITEM_LEVEL_2;
			local.add( badge );
		}
		if (!local.contains( Badge.ITEM_LEVEL_3 ) && item.level() >= 9) {
			if (badge != null) unlock(badge);
			badge = Badge.ITEM_LEVEL_3;
			local.add( badge );
		}
		if (!local.contains( Badge.ITEM_LEVEL_4 ) && item.level() >= 12) {
			if (badge != null) unlock(badge);
			badge = Badge.ITEM_LEVEL_4;
			local.add( badge );
		}
		if (!local.contains( Badge.ITEM_LEVEL_5 ) && item.level() >= 15) {
			if (badge != null) unlock(badge);
			badge = Badge.ITEM_LEVEL_5;
			local.add( badge );
		}
		
		displayBadge( badge );
	}
	
	public static void validateAllBagsBought( Item bag ) {
		
		Badge badge = null;
		if (bag instanceof VelvetPouch) {
			badge = Badge.BAG_BOUGHT_VELVET_POUCH;
		} else if (bag instanceof ScrollHolder) {
			badge = Badge.BAG_BOUGHT_SCROLL_HOLDER;
		} else if (bag instanceof PotionBandolier) {
			badge = Badge.BAG_BOUGHT_POTION_BANDOLIER;
		} else if (bag instanceof MagicalHolster) {
			badge = Badge.BAG_BOUGHT_MAGICAL_HOLSTER;
		}
		
		if (badge != null) {
			
			local.add( badge );
			
			if (!local.contains( Badge.ALL_BAGS_BOUGHT ) &&
				local.contains( Badge.BAG_BOUGHT_VELVET_POUCH ) &&
				local.contains( Badge.BAG_BOUGHT_SCROLL_HOLDER ) &&
				local.contains( Badge.BAG_BOUGHT_POTION_BANDOLIER ) &&
				local.contains( Badge.BAG_BOUGHT_MAGICAL_HOLSTER )) {
						
					badge = Badge.ALL_BAGS_BOUGHT;
					local.add( badge );
					displayBadge( badge );
			}
		}
	}

	//several badges all tie into catalog completion
	public static void validateCatalogBadges(){

		int totalSeen = 0;
		int totalThings = 0;

		for (Catalog cat : Catalog.values()){
			totalSeen += cat.totalSeen();
			totalThings += cat.totalItems();
		}

		for (Bestiary cat : Bestiary.values()){
			totalSeen += cat.totalSeen();
			totalThings += cat.totalEntities();
		}

		for (Document doc : Document.values()){
			if (!doc.isLoreDoc()) {
				for (String page : doc.pageNames()){
					if (doc.isPageFound(page)) totalSeen++;
					totalThings++;
				}
			}
		}

		//overall unlock badges
		Badge badge = null;
		if (totalSeen >= 40) {
			badge = Badge.RESEARCHER_1;
		}
		if (totalSeen >= 80) {
			unlock(badge);
			badge = Badge.RESEARCHER_2;
		}
		if (totalSeen >= 160) {
			unlock(badge);
			badge = Badge.RESEARCHER_3;
		}
		if (totalSeen >= 320) {
			unlock(badge);
			badge = Badge.RESEARCHER_4;
		}
		if (totalSeen == totalThings) {
			unlock(badge);
			badge = Badge.RESEARCHER_5;
		}
		displayBadge( badge );

		//specific task badges

		boolean qualified = true;
		for (Catalog cat : Catalog.equipmentCatalogs) {
			if (cat != Catalog.ENCHANTMENTS && cat != Catalog.GLYPHS) {
				if (cat.totalSeen() == 0) {
					qualified = false;
					break;
				}
			}
		}
		if (qualified) {
			displayBadge(Badge.CATALOG_ONE_EQUIPMENT);
		}

		//doesn't actually use catalogs, but triggers at the same time effectively
		if (!local.contains(Badge.CATALOG_POTIONS_SCROLLS)
				&& Potion.allKnown() && Scroll.allKnown()
				&& Dungeon.hero != null && Dungeon.hero.isAlive()){
			local.add(Badge.CATALOG_POTIONS_SCROLLS);
			displayBadge(Badge.CATALOG_POTIONS_SCROLLS);
		}

		if (Bestiary.RARE.totalEntities() == Bestiary.RARE.totalSeen()){
			displayBadge(Badge.ALL_RARE_ENEMIES);
		}

		if (Document.HALLS_KING.isPageRead(Document.KING_ATTRITION)){
			displayBadge(Badge.RODNEY);
		}

	}
	
	public static void validateDeathFromFire() {
		Badge badge = Badge.DEATH_FROM_FIRE;
		local.add( badge );
		displayBadge( badge );
		
		validateDeathFromAll();
	}
	
	public static void validateDeathFromPoison() {
		Badge badge = Badge.DEATH_FROM_POISON;
		local.add( badge );
		displayBadge( badge );
		
		validateDeathFromAll();
	}
	
	public static void validateDeathFromGas() {
		Badge badge = Badge.DEATH_FROM_GAS;
		local.add( badge );
		displayBadge( badge );
		
		validateDeathFromAll();
	}
	
	public static void validateDeathFromHunger() {
		Badge badge = Badge.DEATH_FROM_HUNGER;
		local.add( badge );
		displayBadge( badge );
		
		validateDeathFromAll();
	}

	public static void validateDeathFromFalling() {
		Badge badge = Badge.DEATH_FROM_FALLING;
		local.add( badge );
		displayBadge( badge );

		validateDeathFromAll();
	}

	public static void validateDeathFromEnemyMagic() {
		Badge badge = Badge.DEATH_FROM_ENEMY_MAGIC;
		local.add( badge );
		displayBadge( badge );

		validateDeathFromAll();
	}
	
	public static void validateDeathFromFriendlyMagic() {
		Badge badge = Badge.DEATH_FROM_FRIENDLY_MAGIC;
		local.add( badge );
		displayBadge( badge );

		validateDeathFromAll();
	}

	public static void validateDeathFromSacrifice() {
		Badge badge = Badge.DEATH_FROM_SACRIFICE;
		local.add( badge );
		displayBadge( badge );

		validateDeathFromAll();
	}

	public static void validateDeathFromGrimOrDisintTrap() {
		Badge badge = Badge.DEATH_FROM_GRIM_TRAP;
		local.add( badge );
		displayBadge( badge );

		validateDeathFromAll();
	}
	
	private static void validateDeathFromAll() {
		if (isUnlocked( Badge.DEATH_FROM_FIRE ) &&
				isUnlocked( Badge.DEATH_FROM_POISON ) &&
				isUnlocked( Badge.DEATH_FROM_GAS ) &&
				isUnlocked( Badge.DEATH_FROM_HUNGER) &&
				isUnlocked( Badge.DEATH_FROM_FALLING) &&
				isUnlocked( Badge.DEATH_FROM_ENEMY_MAGIC) &&
				isUnlocked( Badge.DEATH_FROM_FRIENDLY_MAGIC) &&
				isUnlocked( Badge.DEATH_FROM_SACRIFICE) &&
				isUnlocked( Badge.DEATH_FROM_GRIM_TRAP)) {

			Badge badge = Badge.DEATH_FROM_ALL;
			if (!isUnlocked( badge )) {
				displayBadge( badge );
			}
		}
	}

	private static LinkedHashMap<HeroClass, Badge> firstBossClassBadges = new LinkedHashMap<>();
	static {
		firstBossClassBadges.put(HeroClass.WARRIOR, Badge.BOSS_SLAIN_1_WARRIOR);
		firstBossClassBadges.put(HeroClass.MAGE, Badge.BOSS_SLAIN_1_MAGE);
		firstBossClassBadges.put(HeroClass.ROGUE, Badge.BOSS_SLAIN_1_ROGUE);
		firstBossClassBadges.put(HeroClass.HUNTRESS, Badge.BOSS_SLAIN_1_HUNTRESS);
		firstBossClassBadges.put(HeroClass.DUELIST, Badge.BOSS_SLAIN_1_DUELIST);
		firstBossClassBadges.put(HeroClass.CLERIC, Badge.BOSS_SLAIN_1_CLERIC);
		firstBossClassBadges.put(HeroClass.GUNNER, Badge.BOSS_SLAIN_1_GUNNER);
		firstBossClassBadges.put(HeroClass.SAMURAI, Badge.BOSS_SLAIN_1_SAMURAI);
		firstBossClassBadges.put(HeroClass.ADVENTURER, Badge.BOSS_SLAIN_1_ADVENTURER);
		firstBossClassBadges.put(HeroClass.KNIGHT, Badge.BOSS_SLAIN_1_KNIGHT);
		firstBossClassBadges.put(HeroClass.MEDIC, Badge.BOSS_SLAIN_1_MEDIC);
	}

	private static LinkedHashMap<HeroClass, Badge> victoryClassBadges = new LinkedHashMap<>();
	static {
		victoryClassBadges.put(HeroClass.WARRIOR, Badge.VICTORY_WARRIOR);
		victoryClassBadges.put(HeroClass.MAGE, Badge.VICTORY_MAGE);
		victoryClassBadges.put(HeroClass.ROGUE, Badge.VICTORY_ROGUE);
		victoryClassBadges.put(HeroClass.HUNTRESS, Badge.VICTORY_HUNTRESS);
		victoryClassBadges.put(HeroClass.DUELIST, Badge.VICTORY_DUELIST);
		victoryClassBadges.put(HeroClass.CLERIC, Badge.VICTORY_CLERIC);
		victoryClassBadges.put(HeroClass.GUNNER, Badge.VICTORY_GUNNER);
		victoryClassBadges.put(HeroClass.SAMURAI, Badge.VICTORY_SAMURAI);
		victoryClassBadges.put(HeroClass.ADVENTURER, Badge.VICTORY_ADVENTURER);
		victoryClassBadges.put(HeroClass.KNIGHT, Badge.VICTORY_KNIGHT);
		victoryClassBadges.put(HeroClass.MEDIC, Badge.VICTORY_MEDIC);
	}

	private static LinkedHashMap<HeroSubClass, Badge> thirdBossSubclassBadges = new LinkedHashMap<>();
	static {
		thirdBossSubclassBadges.put(HeroSubClass.BERSERKER, Badge.BOSS_SLAIN_3_BERSERKER);
		thirdBossSubclassBadges.put(HeroSubClass.GLADIATOR, Badge.BOSS_SLAIN_3_GLADIATOR);
		thirdBossSubclassBadges.put(HeroSubClass.VETERAN, Badge.BOSS_SLAIN_3_VETERAN);
		thirdBossSubclassBadges.put(HeroSubClass.BATTLEMAGE, Badge.BOSS_SLAIN_3_BATTLEMAGE);
		thirdBossSubclassBadges.put(HeroSubClass.WARLOCK, Badge.BOSS_SLAIN_3_WARLOCK);
		thirdBossSubclassBadges.put(HeroSubClass.WIZARD, Badge.BOSS_SLAIN_3_WIZARD);
		thirdBossSubclassBadges.put(HeroSubClass.ASSASSIN, Badge.BOSS_SLAIN_3_ASSASSIN);
		thirdBossSubclassBadges.put(HeroSubClass.FREERUNNER, Badge.BOSS_SLAIN_3_FREERUNNER);
		thirdBossSubclassBadges.put(HeroSubClass.CHASER, Badge.BOSS_SLAIN_3_CHASER);
		thirdBossSubclassBadges.put(HeroSubClass.SNIPER, Badge.BOSS_SLAIN_3_SNIPER);
		thirdBossSubclassBadges.put(HeroSubClass.WARDEN, Badge.BOSS_SLAIN_3_WARDEN);
		thirdBossSubclassBadges.put(HeroSubClass.FIGHTER, Badge.BOSS_SLAIN_3_FIGHTER);
		thirdBossSubclassBadges.put(HeroSubClass.CHAMPION, Badge.BOSS_SLAIN_3_CHAMPION);
		thirdBossSubclassBadges.put(HeroSubClass.MONK, Badge.BOSS_SLAIN_3_MONK);
		thirdBossSubclassBadges.put(HeroSubClass.FENCER, Badge.BOSS_SLAIN_3_FENCER);
		thirdBossSubclassBadges.put(HeroSubClass.PRIEST, Badge.BOSS_SLAIN_3_PRIEST);
		thirdBossSubclassBadges.put(HeroSubClass.PALADIN, Badge.BOSS_SLAIN_3_PALADIN);
		thirdBossSubclassBadges.put(HeroSubClass.ENCHANTER, Badge.BOSS_SLAIN_3_ENCHANTER);
		thirdBossSubclassBadges.put(HeroSubClass.OUTLAW, Badge.BOSS_SLAIN_3_OUTLAW);
		thirdBossSubclassBadges.put(HeroSubClass.GUNSLINGER, Badge.BOSS_SLAIN_3_GUNSLINGER);
		thirdBossSubclassBadges.put(HeroSubClass.SPECIALIST, Badge.BOSS_SLAIN_3_SPECIALIST);
		thirdBossSubclassBadges.put(HeroSubClass.SLASHER, Badge.BOSS_SLAIN_3_SLASHER);
		thirdBossSubclassBadges.put(HeroSubClass.MASTER, Badge.BOSS_SLAIN_3_MASTER);
		thirdBossSubclassBadges.put(HeroSubClass.SLAYER, Badge.BOSS_SLAIN_3_SLAYER);
		thirdBossSubclassBadges.put(HeroSubClass.ENGINEER, Badge.BOSS_SLAIN_3_ENGINEER);
		thirdBossSubclassBadges.put(HeroSubClass.EXPLORER, Badge.BOSS_SLAIN_3_EXPLORER);
		thirdBossSubclassBadges.put(HeroSubClass.RESEARCHER, Badge.BOSS_SLAIN_3_RESEARCHER);
		thirdBossSubclassBadges.put(HeroSubClass.DEATHKNIGHT, Badge.BOSS_SLAIN_3_DEATHKNIGHT);
		thirdBossSubclassBadges.put(HeroSubClass.HORSEMAN, Badge.BOSS_SLAIN_3_HORSEMAN);
		thirdBossSubclassBadges.put(HeroSubClass.CRUSADER, Badge.BOSS_SLAIN_3_CRUSADER);
		thirdBossSubclassBadges.put(HeroSubClass.SAVIOR, Badge.BOSS_SLAIN_3_SAVIOR);
		thirdBossSubclassBadges.put(HeroSubClass.THERAPIST, Badge.BOSS_SLAIN_3_THERAPIST);
		thirdBossSubclassBadges.put(HeroSubClass.MEDICALOFFICER, Badge.BOSS_SLAIN_3_MEDICALOFFICER);
	}
	
	public static void validateBossSlain() {
		Badge badge = null;
		switch (Dungeon.depth) {
		case 5:
			badge = Badge.BOSS_SLAIN_1;
			break;
		case 10:
			badge = Badge.BOSS_SLAIN_2;
			break;
		case 15:
			badge = Badge.BOSS_SLAIN_3;
			break;
		case 20:
			badge = Badge.BOSS_SLAIN_4;
			break;
		}
		
		if (badge != null) {
			local.add( badge );
			displayBadge( badge );
			
			if (badge == Badge.BOSS_SLAIN_1) {
				badge = firstBossClassBadges.get(Dungeon.hero.heroClass);
				if (badge == null) return;
				local.add( badge );
				unlock(badge);

				boolean allUnlocked = true;
				for (Badge b : firstBossClassBadges.values()){
					if (!isUnlocked(b)){
						allUnlocked = false;
						break;
					}
				}
				if (allUnlocked) {
					
					badge = Badge.BOSS_SLAIN_1_ALL_CLASSES;
					if (!isUnlocked( badge )) {
						displayBadge( badge );
					}
				}
			} else if (badge == Badge.BOSS_SLAIN_3) {

				badge = thirdBossSubclassBadges.get(Dungeon.hero.subClass);
				if (badge == null) return;
				local.add( badge );
				unlock(badge);

				boolean allUnlocked = true;
				for (Badge b : thirdBossSubclassBadges.values()){
					if (!isUnlocked(b)){
						allUnlocked = false;
						break;
					}
				}
				if (allUnlocked) {
					badge = Badge.BOSS_SLAIN_3_ALL_SUBCLASSES;
					if (!isUnlocked( badge )) {
						displayBadge( badge );
					}
				}
			}

			if (Statistics.qualifiedForBossRemainsBadge && Dungeon.hero.belongings.getItem(RemainsItem.class) != null){
				badge = Badge.BOSS_SLAIN_REMAINS;
				local.add( badge );
				displayBadge( badge );
			}

		}
	}

	public static void validateBossChallengeCompleted(){
		Badge badge = null;
		switch (Dungeon.depth) {
			case 5:
				badge = Badge.BOSS_CHALLENGE_1;
				break;
			case 10:
				badge = Badge.BOSS_CHALLENGE_2;
				break;
			case 15:
				badge = Badge.BOSS_CHALLENGE_3;
				break;
			case 20:
				badge = Badge.BOSS_CHALLENGE_4;
				break;
			case 25:
				badge = Badge.BOSS_CHALLENGE_5;
				break;
		}

		if (badge != null) {
			local.add(badge);
			displayBadge(badge);
		}
	}
	
	public static void validateMastery() {
		
		Badge badge = null;
		switch (Dungeon.hero.heroClass) {
			case WARRIOR:
				badge = Badge.MASTERY_WARRIOR;
				break;
			case MAGE:
				badge = Badge.MASTERY_MAGE;
				break;
			case ROGUE:
				badge = Badge.MASTERY_ROGUE;
				break;
			case HUNTRESS:
				badge = Badge.MASTERY_HUNTRESS;
				break;
			case DUELIST:
				badge = Badge.MASTERY_DUELIST;
				break;
			case CLERIC:
				badge = Badge.MASTERY_CLERIC;
				break;
			case GUNNER:
				badge = Badge.MASTERY_GUNNER;
				break;
			case SAMURAI:
				badge = Badge.MASTERY_SAMURAI;
				break;
			case ADVENTURER:
				badge = Badge.MASTERY_ADVENTURER;
				break;
			case KNIGHT:
				badge = Badge.MASTERY_KNIGHT;
				break;
			case MEDIC:
				badge = Badge.MASTERY_MEDIC;
				break;
		}
		
		unlock(badge);
	}

	public static void validateRatmogrify(){
		unlock(Badge.FOUND_RATMOGRIFY);
	}
	
	public static void validateMageUnlock(){
		if (Statistics.upgradesUsed >= 1 && !isUnlocked(Badge.UNLOCK_MAGE)){
			displayBadge( Badge.UNLOCK_MAGE );
		}
	}
	
	public static void validateRogueUnlock(){
		if (Statistics.sneakAttacks >= 10 && !isUnlocked(Badge.UNLOCK_ROGUE)){
			displayBadge( Badge.UNLOCK_ROGUE );
		}
	}
	
	public static void validateHuntressUnlock(){
		if (Statistics.thrownAttacks >= 10 && !isUnlocked(Badge.UNLOCK_HUNTRESS)){
			displayBadge( Badge.UNLOCK_HUNTRESS );
		}
	}

	public static void validateDuelistUnlock(){
		if (!isUnlocked(Badge.UNLOCK_DUELIST) && Dungeon.hero != null
				&& Dungeon.hero.belongings.weapon instanceof MeleeWeapon
				&& ((MeleeWeapon) Dungeon.hero.belongings.weapon).tier >= 2
				&& ((MeleeWeapon) Dungeon.hero.belongings.weapon).STRReq() <= Dungeon.hero.STR()){

			if (Dungeon.hero.belongings.weapon.isIdentified() &&
					((MeleeWeapon) Dungeon.hero.belongings.weapon).STRReq() <= Dungeon.hero.STR()) {
				displayBadge(Badge.UNLOCK_DUELIST);

			} else if (!Dungeon.hero.belongings.weapon.isIdentified() &&
					((MeleeWeapon) Dungeon.hero.belongings.weapon).STRReq(0) <= Dungeon.hero.STR()){
				displayBadge(Badge.UNLOCK_DUELIST);
			}
		}
	}

	public static void validateClericUnlock(){
		if (!isUnlocked(Badge.UNLOCK_CLERIC)){
			displayBadge( Badge.UNLOCK_CLERIC );
		}
	}

	public static void validateGunnerUnlock() {
		if (Statistics.gunModified && !isUnlocked(Badge.UNLOCK_GUNNER)) {
			displayBadge(Badge.UNLOCK_GUNNER);
		}
	}

	public static void validateSamuraiUnlock() {
		if (Statistics.katanaObtained && !isUnlocked(Badge.UNLOCK_SAMURAI)) {
			displayBadge(Badge.UNLOCK_SAMURAI);
		}
	}

	public static void validateAdventurerUnlock() {
		if (Statistics.plantTriggered >= 10 && !isUnlocked(Badge.UNLOCK_ADVENTURER)) {
			displayBadge(Badge.UNLOCK_ADVENTURER);
		}
	}

	public static void validateKnightUnlock() {
		if (Statistics.plateObtained && !isUnlocked(Badge.UNLOCK_KNIGHT)) {
			displayBadge(Badge.UNLOCK_KNIGHT);
		}
	}

	public static void validateMedicUnlock() {
		if (Statistics.pillsMade > 4 && !isUnlocked(Badge.UNLOCK_MEDIC)) {
			displayBadge(Badge.UNLOCK_MEDIC);
		}
	}
	
	public static void validateMasteryCombo( int n ) {
		if (!local.contains( Badge.MASTERY_COMBO ) && n == 10) {
			Badge badge = Badge.MASTERY_COMBO;
			local.add( badge );
			displayBadge( badge );
		}
	}
	
	public static void validateVictory() {

		Badge badge = Badge.VICTORY;
		local.add( badge );
		displayBadge( badge );

		badge = victoryClassBadges.get(Dungeon.hero.heroClass);
		if (badge == null) return;
		local.add( badge );
		unlock(badge);

		boolean allUnlocked = true;
		for (Badge b : victoryClassBadges.values()){
			if (!isUnlocked(b)){
				allUnlocked = false;
				break;
			}
		}
		if (allUnlocked){
			badge = Badge.VICTORY_ALL_CLASSES;
			displayBadge( badge );
		}
	}

	public static void validateNoKilling() {
		if (!local.contains( Badge.NO_MONSTERS_SLAIN ) && Statistics.completedWithNoKilling) {
			Badge badge = Badge.NO_MONSTERS_SLAIN;
			local.add( badge );
			displayBadge( badge );
			Statistics.completedWithNoKilling = false;
		}
	}
	
	public static void validateGrimWeapon() {
		if (!local.contains( Badge.GRIM_WEAPON )) {
			Badge badge = Badge.GRIM_WEAPON;
			local.add( badge );
			displayBadge( badge );
		}
	}
	
	public static void validateGamesPlayed() {
		Badge badge = null;
		if (Rankings.INSTANCE.totalNumber >= 10 || Rankings.INSTANCE.wonNumber >= 1) {
			badge = Badge.GAMES_PLAYED_1;
		}
		if (Rankings.INSTANCE.totalNumber >= 25 || Rankings.INSTANCE.wonNumber >= 3) {
			unlock(badge);
			badge = Badge.GAMES_PLAYED_2;
		}
		if (Rankings.INSTANCE.totalNumber >= 50 || Rankings.INSTANCE.wonNumber >= 5) {
			unlock(badge);
			badge = Badge.GAMES_PLAYED_3;
		}
		if (Rankings.INSTANCE.totalNumber >= 200 || Rankings.INSTANCE.wonNumber >= 10) {
			unlock(badge);
			badge = Badge.GAMES_PLAYED_4;
		}
		if (Rankings.INSTANCE.totalNumber >= 1000 || Rankings.INSTANCE.wonNumber >= 25) {
			unlock(badge);
			badge = Badge.GAMES_PLAYED_5;
		}
		
		displayBadge( badge );
	}

	public static void validateHighScore( int score ){
		Badge badge = null;
		if (score >= 5000) {
			badge = Badge.HIGH_SCORE_1;
			local.add( badge );
		}
		if (score >= 25_000) {
			unlock(badge);
			badge = Badge.HIGH_SCORE_2;
			local.add( badge );
		}
		if (score >= 100_000) {
			unlock(badge);
			badge = Badge.HIGH_SCORE_3;
			local.add( badge );
		}
		if (score >= 250_000) {
			unlock(badge);
			badge = Badge.HIGH_SCORE_4;
			local.add( badge );
		}
		if (score >= 1_000_000) {
			unlock(badge);
			badge = Badge.HIGH_SCORE_5;
			local.add( badge );
		}

		displayBadge( badge );
	}
	
	public static void validateHappyEnd() {
		local.add( Badge.HAPPY_END );
		displayBadge( Badge.HAPPY_END );

		if( Dungeon.hero.belongings.getItem(RemainsItem.class) != null ){
			local.add( Badge.HAPPY_END_REMAINS );
			displayBadge( Badge.HAPPY_END_REMAINS );
		}
	}

	public static void validateChampion( int challenges ) {
		if (challenges == 0) return;
		Badge badge = null;
		if (challenges >= 1) {
			badge = Badge.CHAMPION_1;
		}
		if (challenges >= 3){
			unlock(badge);
			badge = Badge.CHAMPION_2;
		}
		if (challenges >= 6){
			unlock(badge);
			badge = Badge.CHAMPION_3;
		}
		local.add(badge);
		displayBadge( badge );
	}

	public static void validateHedgehog() {
		if (!local.contains( Badge.HEDGEHOG )) {
			Badge badge = Badge.HEDGEHOG;
			local.add( badge );
			displayBadge( badge );
		}
	}
	
	private static void displayBadge( Badge badge ) {

		if (badge == null || (badge.type != BadgeType.JOURNAL && !Dungeon.customSeedText.isEmpty())) {
			return;
		}
		
		if (isUnlocked( badge )) {
			
			if (badge.type == BadgeType.LOCAL) {
				GLog.h( Messages.get(Badges.class, "endorsed", badge.title()) );
				GLog.newLine();
			}
			
		} else {
			
			unlock(badge);
			
			GLog.h( Messages.get(Badges.class, "new", badge.title() + " (" + badge.desc() + ")") );
			GLog.newLine();
			PixelScene.showBadge( badge );
		}
	}
	
	public static boolean isUnlocked( Badge badge ) {
		return global.contains( badge );
	}
	
	public static HashSet<Badge> allUnlocked(){
		loadGlobal();
		return new HashSet<>(global);
	}
	
	public static void disown( Badge badge ) {
		loadGlobal();
		global.remove( badge );
		saveNeeded = true;
	}
	
	public static void unlock( Badge badge ){
		if (!isUnlocked(badge) && (badge.type == BadgeType.JOURNAL || Dungeon.customSeedText.isEmpty())){
			global.add( badge );
			saveNeeded = true;
		}
	}

	public static List<Badge> filterReplacedBadges( boolean global ) {

		ArrayList<Badge> badges = new ArrayList<>(global ? Badges.global : Badges.local);

		Iterator<Badge> iterator = badges.iterator();
		while (iterator.hasNext()) {
			Badge badge = iterator.next();
			if ((!global && badge.type != BadgeType.LOCAL) || badge.type == BadgeType.HIDDEN) {
				iterator.remove();
			}
		}

		Collections.sort(badges);

		return filterReplacedBadges(badges);

	}

	//only show the highest unlocked and the lowest locked
	private static final Badge[][] tierBadgeReplacements = new Badge[][]{
			{Badge.MONSTERS_SLAIN_1, Badge.MONSTERS_SLAIN_2, Badge.MONSTERS_SLAIN_3, Badge.MONSTERS_SLAIN_4, Badge.MONSTERS_SLAIN_5},
			{Badge.GOLD_COLLECTED_1, Badge.GOLD_COLLECTED_2, Badge.GOLD_COLLECTED_3, Badge.GOLD_COLLECTED_4, Badge.GOLD_COLLECTED_5},
			{Badge.ITEM_LEVEL_1, Badge.ITEM_LEVEL_2, Badge.ITEM_LEVEL_3, Badge.ITEM_LEVEL_4, Badge.ITEM_LEVEL_5},
			{Badge.LEVEL_REACHED_1, Badge.LEVEL_REACHED_2, Badge.LEVEL_REACHED_3, Badge.LEVEL_REACHED_4, Badge.LEVEL_REACHED_5},
			{Badge.STRENGTH_ATTAINED_1, Badge.STRENGTH_ATTAINED_2, Badge.STRENGTH_ATTAINED_3, Badge.STRENGTH_ATTAINED_4, Badge.STRENGTH_ATTAINED_5},
			{Badge.FOOD_EATEN_1, Badge.FOOD_EATEN_2, Badge.FOOD_EATEN_3, Badge.FOOD_EATEN_4, Badge.FOOD_EATEN_5},
			{Badge.ITEMS_CRAFTED_1, Badge.ITEMS_CRAFTED_2, Badge.ITEMS_CRAFTED_3, Badge.ITEMS_CRAFTED_4, Badge.ITEMS_CRAFTED_5},
			{Badge.BOSS_SLAIN_1, Badge.BOSS_SLAIN_2, Badge.BOSS_SLAIN_3, Badge.BOSS_SLAIN_4},
			{Badge.RESEARCHER_1, Badge.RESEARCHER_2, Badge.RESEARCHER_3, Badge.RESEARCHER_4, Badge.RESEARCHER_5},
			{Badge.HIGH_SCORE_1, Badge.HIGH_SCORE_2, Badge.HIGH_SCORE_3, Badge.HIGH_SCORE_4, Badge.HIGH_SCORE_5},
			{Badge.GAMES_PLAYED_1, Badge.GAMES_PLAYED_2, Badge.GAMES_PLAYED_3, Badge.GAMES_PLAYED_4, Badge.GAMES_PLAYED_5},
			{Badge.CHAMPION_1, Badge.CHAMPION_2, Badge.CHAMPION_3}
	};

	//don't show the later badge if the earlier one isn't unlocked
	private static final Badge[][] prerequisiteBadges = new Badge[][]{
			{Badge.BOSS_SLAIN_1, Badge.BOSS_CHALLENGE_1},
			{Badge.BOSS_SLAIN_2, Badge.BOSS_CHALLENGE_2},
			{Badge.BOSS_SLAIN_3, Badge.BOSS_CHALLENGE_3},
			{Badge.BOSS_SLAIN_4, Badge.BOSS_CHALLENGE_4},
			{Badge.VICTORY,      Badge.BOSS_CHALLENGE_5},
	};

	//If the summary badge is unlocked, don't show the component badges
	private static final Badge[][] summaryBadgeReplacements = new Badge[][]{
			{Badge.DEATH_FROM_FIRE, Badge.DEATH_FROM_ALL},
			{Badge.DEATH_FROM_GAS, Badge.DEATH_FROM_ALL},
			{Badge.DEATH_FROM_HUNGER, Badge.DEATH_FROM_ALL},
			{Badge.DEATH_FROM_POISON, Badge.DEATH_FROM_ALL},
			{Badge.DEATH_FROM_FALLING, Badge.DEATH_FROM_ALL},
			{Badge.DEATH_FROM_ENEMY_MAGIC, Badge.DEATH_FROM_ALL},
			{Badge.DEATH_FROM_FRIENDLY_MAGIC, Badge.DEATH_FROM_ALL},
			{Badge.DEATH_FROM_SACRIFICE, Badge.DEATH_FROM_ALL},
			{Badge.DEATH_FROM_GRIM_TRAP, Badge.DEATH_FROM_ALL},

			{Badge.ALL_WEAPONS_IDENTIFIED, Badge.ALL_ITEMS_IDENTIFIED},
			{Badge.ALL_ARMOR_IDENTIFIED, Badge.ALL_ITEMS_IDENTIFIED},
			{Badge.ALL_WANDS_IDENTIFIED, Badge.ALL_ITEMS_IDENTIFIED},
			{Badge.ALL_SPELLBOOKS_IDENTIFIED, Badge.ALL_ITEMS_IDENTIFIED},
			{Badge.ALL_RINGS_IDENTIFIED, Badge.ALL_ITEMS_IDENTIFIED},
			{Badge.ALL_ARTIFACTS_IDENTIFIED, Badge.ALL_ITEMS_IDENTIFIED},
			{Badge.ALL_POTIONS_IDENTIFIED, Badge.ALL_ITEMS_IDENTIFIED},
			{Badge.ALL_PILLS_IDENTIFIED, Badge.ALL_ITEMS_IDENTIFIED},
			{Badge.ALL_SCROLLS_IDENTIFIED, Badge.ALL_ITEMS_IDENTIFIED}
	};
	
	public static List<Badge> filterReplacedBadges( List<Badge> badges ) {

		for (Badge[] tierReplace : tierBadgeReplacements){
			leaveBest( badges, tierReplace );
		}

		for (Badge[] metaReplace : summaryBadgeReplacements){
			leaveBest( badges, metaReplace );
		}
		
		return badges;
	}
	
	private static void leaveBest( Collection<Badge> list, Badge...badges ) {
		for (int i=badges.length-1; i > 0; i--) {
			if (list.contains( badges[i])) {
				for (int j=0; j < i; j++) {
					list.remove( badges[j] );
				}
				break;
			}
		}
	}

	public static List<Badge> filterBadgesWithoutPrerequisites(List<Badges.Badge> badges ) {

		for (Badge[] prereqReplace : prerequisiteBadges){
			leaveWorst( badges, prereqReplace );
		}

		for (Badge[] tierReplace : tierBadgeReplacements){
			leaveWorst( badges, tierReplace );
		}

		Collections.sort( badges );

		return badges;
	}

	private static void leaveWorst( Collection<Badge> list, Badge...badges ) {
		for (int i=0; i < badges.length; i++) {
			if (list.contains( badges[i])) {
				for (int j=i+1; j < badges.length; j++) {
					list.remove( badges[j] );
				}
				break;
			}
		}
	}

	public static Collection<Badge> addReplacedBadges(Collection<Badges.Badge> badges ) {

		for (Badge[] tierReplace : tierBadgeReplacements){
			addLower( badges, tierReplace );
		}

		for (Badge[] metaReplace : summaryBadgeReplacements){
			addLower( badges, metaReplace );
		}

		return badges;
	}

	private static void addLower( Collection<Badge> list, Badge...badges ) {
		for (int i=badges.length-1; i > 0; i--) {
			if (list.contains( badges[i])) {
				for (int j=0; j < i; j++) {
					list.add( badges[j] );
				}
				break;
			}
		}
	}

	//used for badges with completion progress that would otherwise be hard to track
	public static String showCompletionProgress( Badge badge ){
		if (isUnlocked(badge)) return null;

		String result = "\n";

		if (badge == Badge.BOSS_SLAIN_1_ALL_CLASSES){
			for (HeroClass cls : HeroClass.values()){
				result += "\n";
				if (isUnlocked(firstBossClassBadges.get(cls)))  result += "_" + Messages.titleCase(cls.title()) + "_";
				else                                            result += Messages.titleCase(cls.title());
			}

			return result;

		} else if (badge == Badge.VICTORY_ALL_CLASSES) {

			for (HeroClass cls : HeroClass.values()){
				result += "\n";
				if (isUnlocked(victoryClassBadges.get(cls)))    result += "_" + Messages.titleCase(cls.title()) + "_";
				else                                            result += Messages.titleCase(cls.title());
			}

			return result;

		} else if (badge == Badge.BOSS_SLAIN_3_ALL_SUBCLASSES){

			for (HeroSubClass cls : HeroSubClass.values()){
				if (cls == HeroSubClass.NONE) continue;
				result += "\n";
				if (isUnlocked(thirdBossSubclassBadges.get(cls)))   result += "_" + Messages.titleCase(cls.title()) + "_";
				else                                                result += Messages.titleCase(cls.title()) ;
			}

			return result;
		}

		return null;
	}
}
