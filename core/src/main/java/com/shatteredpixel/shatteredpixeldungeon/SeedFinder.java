package com.shatteredpixel.shatteredpixeldungeon;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.ArmoredStatue;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.CrystalMimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.GoldenMimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Statue;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Imp;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Wandmaker;
import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.items.EnergyCrystal;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap.Type;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.CrystalKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.GoldenKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.CeremonialCandle;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.CorpseDust;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Embers;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Pickaxe;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.DungeonSeed;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SeedFinder {
	enum Condition {ANY, ALL}
	enum FINDING {STOP,CONTINUE}

	public static FINDING findingStatus = FINDING.STOP;

	public static class Options {
		public static int floors;
		public static Condition condition;
		public static long seed;
	}

	static class HeapItem {
		public Item item;
		public Heap heap;

		public HeapItem(Item item, Heap heap) {
			this.item = item;
			this.heap = heap;
		}
	}

	List<Class<? extends Item>> blacklist;
	ArrayList<String> itemList;

	private void addTextItems(String caption, ArrayList<HeapItem> items, StringBuilder builder) {
		if (!items.isEmpty()) {
			builder.append(caption).append(":\n");

			for (HeapItem item : items) {
				Item i = item.item;
				Heap h = item.heap;

				if (((i instanceof Armor && ((Armor) i).hasGoodGlyph()) ||
					(i instanceof Weapon && ((Weapon) i).hasGoodEnchant()) ||
					(i instanceof Ring) || (i instanceof Wand)) && i.cursed)
					builder.append("- " + Messages.get(this, "cursed")).append(i.title().toLowerCase());

				else
					builder.append("- ").append(i.title().toLowerCase());

				if (h.type != Type.HEAP)
					builder.append(" (").append(h.title().toLowerCase()).append(")");

				builder.append("\n");
			}

			builder.append("\n");
		}
	}

	private void addTextQuest(String caption, ArrayList<Item> items, StringBuilder builder) {
		if (!items.isEmpty()) {
			builder.append(caption).append(":\n");

			for (Item i : items) {
				if (i.cursed)
					builder.append("- " + Messages.get(this, "cursed")).append(i.title().toLowerCase()).append("\n");

				else
					builder.append("- ").append(i.title().toLowerCase()).append("\n");
			}

			builder.append("\n");
		}
	}



	public void findSeed(boolean stop){
		if(!stop){
			findingStatus = FINDING.STOP;
		}
	}

	public String findSeed(String[] wanted, int floor) {
		itemList = new ArrayList<>(Arrays.asList(wanted));

		String seedDigits = Integer.toString(Random.Int(500000));
		findingStatus = FINDING.CONTINUE;
		Options.condition = Condition.ALL;

		String result="NONE";

		for (int i = Random.Int(9999999); i < DungeonSeed.TOTAL_SEEDS && findingStatus == FINDING.CONTINUE ; i++) {
			if (testSeedALL(seedDigits + i, floor)) {
				result = logSeedItems(seedDigits + i, floor);
				break;
			}
		}
		return result;
	}

	private ArrayList<Heap> getMobDrops(Level l) {
		ArrayList<Heap> heaps = new ArrayList<>();

		for (Mob m : l.mobs) {
			if (m instanceof Statue) {
				Heap h = new Heap();
				h.items = new LinkedList<>();
				h.items.add(((Statue) m).weapon.identify());
				h.type = Type.STATUE;
				heaps.add(h);
			}

			else if (m instanceof ArmoredStatue) {
				Heap h = new Heap();
				h.items = new LinkedList<>();
				h.items.add(((ArmoredStatue) m).armor.identify());
				h.items.add(((ArmoredStatue) m).weapon.identify());
				h.type = Type.STATUE;
				heaps.add(h);
			}

			else if (m instanceof Mimic) {
				Heap h = new Heap();
				h.items = new LinkedList<>();

				for (Item item : ((Mimic) m).items)
					h.items.add(item.identify());

				if (m instanceof GoldenMimic) h.type = Type.GOLDEN_MIMIC;
				else if (m instanceof CrystalMimic) h.type = Type.CRYSTAL_MIMIC;
				else h.type = Type.MIMIC;
				heaps.add(h);
			}
		}

		return heaps;
	}

	private boolean testSeed(String seed, int floors) {
		SPDSettings.customSeed(seed);
		GamesInProgress.selectedClass = HeroClass.WARRIOR;
		Dungeon.init();

		boolean[] itemsFound = new boolean[itemList.size()];

		for (int i = 0; i < floors; i++) {
			Level l = Dungeon.newLevel();

			ArrayList<Heap> heaps = new ArrayList<>(l.heaps.valueList());
			heaps.addAll(getMobDrops(l));
			
			if(Ghost.Quest.armor != null){
				for (int j = 0; j < itemList.size(); j++) {
					if (Ghost.Quest.armor.identify().title().toLowerCase().replaceAll(" ","").contains(itemList.get(j).replaceAll(" ",""))) {
						if (!itemsFound[j]) {
							itemsFound[j] = true;
							break;
						}
					}
				}
			}
			if(Wandmaker.Quest.wand1 != null){
				for (int j = 0; j < itemList.size(); j++) {
					if (Wandmaker.Quest.wand1.identify().title().toLowerCase().replaceAll(" ","").contains(itemList.get(j).replaceAll(" ","")) || Wandmaker.Quest.wand2.identify().title().toLowerCase().replaceAll(" ","").contains(itemList.get(j).replaceAll(" ",""))) {
						if (!itemsFound[j]) {
							itemsFound[j] = true;
							break;
						}
					}	
					if(Wandmaker.Quest.type == 1 && Messages.get(this, "corpsedust").contains(itemList.get(j).replaceAll(" ",""))){
						if (!itemsFound[j]) {
							itemsFound[j] = true;
							break;
						}
					}else if(Wandmaker.Quest.type == 2 && Messages.get(this, "embers").contains(itemList.get(j).replaceAll(" ",""))){
						if (!itemsFound[j]) {
							itemsFound[j] = true;
							break;
						}
					}else if(Wandmaker.Quest.type == 3 && Messages.get(this, "rotberry").contains(itemList.get(j).replaceAll(" ",""))){
						if (!itemsFound[j]) {
							itemsFound[j] = true;
							break;
						}
					}
				}
			}
			if(Imp.Quest.reward != null){
				for (int j = 0; j < itemList.size(); j++) {
					if (Imp.Quest.reward.identify().title().toLowerCase().replaceAll(" ","").contains(itemList.get(j).replaceAll(" ",""))) {
						if (!itemsFound[j]) {
							itemsFound[j] = true;
							break;
						}
					}
				}
			}

			for (Heap h : heaps) {
				for (Item item : h.items) {
					item.identify();

					for (int j = 0; j < itemList.size(); j++) {
						if (item.title().toLowerCase().replaceAll(" ","").contains(itemList.get(j).replaceAll(" ",""))) {
							if (!itemsFound[j]) {
								itemsFound[j] = true;
								break;
							}
						}
					}
				}
			}

			Dungeon.depth++;
		}

		if (Options.condition == Condition.ANY) {
			for (int i = 0; i < itemList.size(); i++) {
				if (itemsFound[i])
					return true;
			}

			return false;
		}

		else {
			for (int i = 0; i < itemList.size(); i++) {
				if (!itemsFound[i])
					return false;
			}

			return true;
		}
	}

	private boolean testSeedALL(String seed, int floors) {
		SPDSettings.customSeed(seed);
		GamesInProgress.selectedClass = HeroClass.WARRIOR;
		Dungeon.init();

		boolean[] itemsFound = new boolean[itemList.size()];
		Arrays.fill(itemsFound, false);

		for (int i = 0; i < floors; i++) {
			Level l = Dungeon.newLevel();

			ArrayList<Heap> heaps = new ArrayList<>(l.heaps.valueList());
			heaps.addAll(getMobDrops(l));

			if(Ghost.Quest.armor != null){
				for (int j = 0; j < itemList.size(); j++) {
					String wantingItem = itemList.get(j);
					boolean precise = wantingItem.startsWith("\"")&&wantingItem.endsWith("\"");
					if(precise){
						wantingItem = wantingItem.replaceAll(" ", "");
					}else{
						wantingItem = wantingItem.replaceAll("\"","");
					}
					if (!precise&&Ghost.Quest.armor.identify().title().toLowerCase().replaceAll(" ","").contains(wantingItem) || precise&& Ghost.Quest.armor.identify().title().toLowerCase().equals(wantingItem)) {
						if (!itemsFound[j]) {
							itemsFound[j] = true;
							break;
						}
					}
				}
			}
			if(Wandmaker.Quest.wand1 != null){
				for (int j = 0; j < itemList.size(); j++) {
					String wantingItem = itemList.get(j);
					String wand1 = Wandmaker.Quest.wand1.identify().title().toLowerCase();
					String wand2 = Wandmaker.Quest.wand2.identify().title().toLowerCase();
					boolean precise = wantingItem.startsWith("\"")&&wantingItem.endsWith("\"");
					if(precise){
						wantingItem = wantingItem.replaceAll("\"","");
						if (wand1.equals(wantingItem) || wand2.equals(wantingItem)) {
							if (!itemsFound[j]) {
								itemsFound[j] = true;
								break;
							}
						}
					}else{
						wantingItem = wantingItem.replaceAll(" ", "");
						wand1 = wand1.replaceAll(" ","");
						wand2 = wand2.replaceAll(" ","");
						if (wand1.contains(wantingItem) || wand2.contains(wantingItem)) {
							if (!itemsFound[j]) {
								itemsFound[j] = true;
								break;
							}
						}
					}
					if(Wandmaker.Quest.type == 1 && Messages.get(this, "corpsedust").contains(wantingItem.replaceAll(" ",""))){
						if (!itemsFound[j]) {
							itemsFound[j] = true;
							break;
						}
					}else if(Wandmaker.Quest.type == 2 && Messages.get(this, "embers").contains(wantingItem.replaceAll(" ",""))){
						if (!itemsFound[j]) {
							itemsFound[j] = true;
							break;
						}
					}else if(Wandmaker.Quest.type == 3 && Messages.get(this, "rotberry").contains(wantingItem.replaceAll(" ",""))){
						if (!itemsFound[j]) {
							itemsFound[j] = true;
							break;
						}
					}
				}
			}
			if(Imp.Quest.reward != null){
				for (int j = 0; j < itemList.size(); j++) {
					String wantingItem = itemList.get(j);
					boolean precise = wantingItem.startsWith("\"")&&wantingItem.endsWith("\"");
					String ring = Imp.Quest.reward.identify().title().toLowerCase();
					if (!precise&&ring.replaceAll(" ","").contains(wantingItem.replaceAll(" ",""))
					||
					precise&& ring.equals(wantingItem)) {
						if (!itemsFound[j]) {
							itemsFound[j] = true;
							break;
						}
					}
				}
			}

			for (Heap h : heaps) {
				for (Item item : h.items) {
					item.identify();
					String itemName = item.title().toLowerCase();

					for (int j = 0; j < itemList.size(); j++) {
						String wantingItem = itemList.get(j);
						boolean precise = wantingItem.startsWith("\"")&&wantingItem.endsWith("\"");
						if (!precise&&itemName.replaceAll(" ","").contains(wantingItem.replaceAll(" ",""))
						|| precise&& itemName.equals(wantingItem.replaceAll("\"", ""))) {
							if (!itemsFound[j]) {
								itemsFound[j] = true;
								break;
							}
						}
					}
				}
			}
			if(areAllTrue(itemsFound)){
				return true;
			}
			Dungeon.depth++;
		}
		return false;
	}

	private static boolean areAllTrue(boolean[] array) {
		for(boolean b : array) if(!b) return false;
		return true;
	}

	public String logSeedItems(String seed, int floors) {
		
		SPDSettings.customSeed(seed);
		GamesInProgress.selectedClass = HeroClass.WARRIOR;
		Dungeon.init();
		StringBuilder result = new StringBuilder(Messages.get(this, "seed") + DungeonSeed.convertToCode(Dungeon.seed) + " (" + Dungeon.seed + ") " + Messages.get(this, "items") + ":\n\n");

		blacklist = Arrays.asList(Gold.class, Dewdrop.class, IronKey.class, GoldenKey.class, CrystalKey.class, EnergyCrystal.class,
								  CorpseDust.class, Embers.class, CeremonialCandle.class, Pickaxe.class);


		for (int i = 0; i < floors; i++) {
			result.append("\n_----- ").append(Long.toString(Dungeon.depth)).append(" ").append(Messages.get(this, "floor") + " -----_\n\n");

			Level l = Dungeon.newLevel();
			ArrayList<Heap> heaps = new ArrayList<>(l.heaps.valueList());
			StringBuilder builder = new StringBuilder();
			ArrayList<HeapItem> scrolls = new ArrayList<>();
			ArrayList<HeapItem> potions = new ArrayList<>();
			ArrayList<HeapItem> equipment = new ArrayList<>();
			ArrayList<HeapItem> rings = new ArrayList<>();
			ArrayList<HeapItem> artifacts = new ArrayList<>();
			ArrayList<HeapItem> wands = new ArrayList<>();
			ArrayList<HeapItem> others = new ArrayList<>();
			ArrayList<HeapItem> forSales = new ArrayList<>();

			// list quest rewards
			if (Ghost.Quest.armor != null) {
				ArrayList<Item> rewards = new ArrayList<>();
				rewards.add(Ghost.Quest.armor.identify());
				rewards.add(Ghost.Quest.weapon.identify());
				Ghost.Quest.complete();

				addTextQuest("[ " + Messages.get(this, "sad_ghost_reward") + " ]", rewards, builder);
			}

			if (Wandmaker.Quest.wand1 != null) {
				ArrayList<Item> rewards = new ArrayList<>();
				rewards.add(Wandmaker.Quest.wand1.identify());
				rewards.add(Wandmaker.Quest.wand2.identify());
				Wandmaker.Quest.complete();

				builder.append("[ " + Messages.get(this, "wandmaker_need") +" ]:\n ");


				switch (Wandmaker.Quest.type) {
					case 1: default:
						builder.append(Messages.get(this, "corpsedust") + "\n\n");
						break;
					case 2:
						builder.append(Messages.get(this, "embers") + "\n\n");
						break;
					case 3:
						builder.append(Messages.get(this, "rotberry") + "\n\n");
				}

				addTextQuest("[ "+ Messages.get(this, "wandmaker_reward") +" ]", rewards, builder);
			}

			if (Imp.Quest.reward != null) {
				ArrayList<Item> rewards = new ArrayList<>();
				rewards.add(Imp.Quest.reward.identify());
				Imp.Quest.complete();

				addTextQuest("[ "+ Messages.get(this, "imp_reward") +" ]", rewards, builder);
			}

			heaps.addAll(getMobDrops(l));

			// list items
			for (Heap h : heaps) {
				for (Item item : h.items) {
					item.identify();

					if (h.type == Type.FOR_SALE) forSales.add(new HeapItem(item, h));
					else if (blacklist.contains(item.getClass())) continue;
					else if (item instanceof Scroll) scrolls.add(new HeapItem(item, h));
					else if (item instanceof Potion) potions.add(new HeapItem(item, h));
					else if (item instanceof MeleeWeapon || item instanceof Armor) equipment.add(new HeapItem(item, h));
					else if (item instanceof Ring) rings.add(new HeapItem(item, h));
					else if (item instanceof Artifact) artifacts.add(new HeapItem(item, h));
					else if (item instanceof Wand) wands.add(new HeapItem(item, h));
					else others.add(new HeapItem(item, h));
				}
			}

			addTextItems("[ "+ Messages.get(this, "scrolls") +" ]", scrolls, builder);
			addTextItems("[ "+ Messages.get(this, "potions") +" ]", potions, builder);
			addTextItems("[ "+ Messages.get(this, "equipment") +" ]", equipment, builder);
			addTextItems("[ "+ Messages.get(this, "rings") +" ]", rings, builder);
			addTextItems("[ "+ Messages.get(this, "artifacts") +" ]", artifacts, builder);
			addTextItems("[ "+ Messages.get(this, "wands") +" ]", wands, builder);
			addTextItems("[ "+ Messages.get(this, "for_sales") +" ]", forSales, builder);
			addTextItems("[ "+ Messages.get(this, "others") +" ]", others, builder);

			result.append("\n").append(builder);

			Dungeon.depth++;
		}
		return result.toString();
    }

}
