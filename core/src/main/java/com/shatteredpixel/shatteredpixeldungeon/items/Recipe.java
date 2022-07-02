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

package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Blandfruit;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MeatPie;
import com.shatteredpixel.shatteredpixeldungeon.items.food.StewedMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.AlchemicalCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfArmorEnhance;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfWeaponEnhance;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.BlizzardBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.CausticBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.InfernalBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.ShockingBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfAquaticRejuvenation;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfArcaneArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfDragonsBlood;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfHealth;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfIcyTouch;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfTalent;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfToxicEssence;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.ExoticPotion;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Pickaxe;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ExoticScroll;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.APBullet;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.AdvancedEvolution;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Alchemize;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.AquaBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ArcaneCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.BeaconOfReturning;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.CurseInfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Evolution;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.FeatherFall;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.FireImbueSpell;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.GunSmithingTool;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.HPBullet;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.MagicalInfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.MagicalPorter;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.PhaseShift;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ReclaimTrap;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Recycle;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ScrollOfExtract;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.StableAPBullet;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.StableHPBullet;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.SummonElemental;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.TelekineticGrab;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.WildEnergy;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Xray;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssassinsSpear;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Ballista;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ChainFlail;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ChaosSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CursedSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DoubleGreatSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ElectroScimitar;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameScimitar;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ForceGlove;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FrostScimitar;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HolySword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LanceNShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MissileButton;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PoisonScimitar;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpearNShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Blast;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Corruption;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Disintegration;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Earth;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Empty;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Fire;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Frost;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Lightning;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Prismatic;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Regrowth;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Transfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SpellBook_Warding;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.UnholyBible;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Cross;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public abstract class Recipe {
	
	public abstract boolean testIngredients(ArrayList<Item> ingredients);
	
	public abstract int cost(ArrayList<Item> ingredients);
	
	public abstract Item brew(ArrayList<Item> ingredients);
	
	public abstract Item sampleOutput(ArrayList<Item> ingredients);
	
	//subclass for the common situation of a recipe with static inputs and outputs
	public static abstract class SimpleRecipe extends Recipe {
		
		//*** These elements must be filled in by subclasses
		protected Class<?extends Item>[] inputs; //each class should be unique
		protected int[] inQuantity;
		
		protected int cost;
		
		protected Class<?extends Item> output;
		protected int outQuantity;
		//***
		
		//gets a simple list of items based on inputs
		public ArrayList<Item> getIngredients() {
			ArrayList<Item> result = new ArrayList<>();
			for (int i = 0; i < inputs.length; i++) {
				Item ingredient = Reflection.newInstance(inputs[i]);
				ingredient.quantity(inQuantity[i]);
				result.add(ingredient);
			}
			return result;
		}
		
		@Override
		public final boolean testIngredients(ArrayList<Item> ingredients) {
			
			int[] needed = inQuantity.clone();
			
			for (Item ingredient : ingredients){
				if (!ingredient.isIdentified()) return false;
				for (int i = 0; i < inputs.length; i++){
					if (ingredient.getClass() == inputs[i]){
						needed[i] -= ingredient.quantity();
						break;
					}
				}
			}
			
			for (int i : needed){
				if (i > 0){
					return false;
				}
			}
			
			return true;
		}
		
		public final int cost(ArrayList<Item> ingredients){
			return cost;
		}
		
		@Override
		public final Item brew(ArrayList<Item> ingredients) {
			if (!testIngredients(ingredients)) return null;
			
			int[] needed = inQuantity.clone();
			
			for (Item ingredient : ingredients){
				for (int i = 0; i < inputs.length; i++) {
					if (ingredient.getClass() == inputs[i] && needed[i] > 0) {
						if (needed[i] <= ingredient.quantity()) {
							ingredient.quantity(ingredient.quantity() - needed[i]);
							needed[i] = 0;
						} else {
							needed[i] -= ingredient.quantity();
							ingredient.quantity(0);
						}
					}
				}
			}
			
			//sample output and real output are identical in this case.
			return sampleOutput(null);
		}
		
		//ingredients are ignored, as output doesn't vary
		public final Item sampleOutput(ArrayList<Item> ingredients){
			try {
				Item result = Reflection.newInstance(output);
				result.quantity(outQuantity);
				return result;
			} catch (Exception e) {
				ShatteredPixelDungeon.reportException( e );
				return null;
			}
		}
	}
	
	
	//*******
	// Static members
	//*******

	private static Recipe[] variableRecipes = new Recipe[]{
			new LiquidMetal.Recipe()
	};
	
	private static Recipe[] oneIngredientRecipes = new Recipe[]{
		new Scroll.ScrollToStone(),
		new ExoticPotion.PotionToExotic(),
		new ExoticScroll.ScrollToExotic(),
		new ArcaneResin.Recipe(),
		new Alchemize.Recipe(),
		new Cartridge.Recipe(),
		new StewedMeat.oneMeat(),
		new SpellBook_Empty.Recipe()
	};

	private static Recipe[] twoIngredientRecipes = new Recipe[]{
		new Blandfruit.CookFruit(),
		new Bomb.EnhanceBomb(),
		new AlchemicalCatalyst.Recipe(),
		new ArcaneCatalyst.Recipe(),
		new ElixirOfArcaneArmor.Recipe(),
		new ElixirOfAquaticRejuvenation.Recipe(),
		new ElixirOfDragonsBlood.Recipe(),
		new ElixirOfIcyTouch.Recipe(),
		new ElixirOfMight.Recipe(),
		new ElixirOfHoneyedHealing.Recipe(),
		new ElixirOfToxicEssence.Recipe(),
		new BlizzardBrew.Recipe(),
		new InfernalBrew.Recipe(),
		new ShockingBrew.Recipe(),
		new CausticBrew.Recipe(),
		new AquaBlast.Recipe(),
		new BeaconOfReturning.Recipe(),
		new CurseInfusion.Recipe(),
		new FeatherFall.Recipe(),
		new MagicalInfusion.Recipe(),
		new MagicalInfusion.Recipe2(),
		new MagicalPorter.Recipe(),
		new PhaseShift.Recipe(),
		new ReclaimTrap.Recipe(),
		new Recycle.Recipe(),
		new WildEnergy.Recipe(),
		new TelekineticGrab.Recipe(),
		new SummonElemental.Recipe(),
		new StewedMeat.twoMeat(),
		new Evolution.Recipe(),
		new Pistol.Recipe(),
		new GoldenPistol.Recipe(),
		new MissileButton.Recipe(),
		new StableAPBullet.Recipe(),
		new StableHPBullet.Recipe(),
		new SpellBook_Blast.Recipe(),
		new SpellBook_Corrosion.Recipe(),
		new SpellBook_Corruption.Recipe(),
		new SpellBook_Disintegration.Recipe(),
		new SpellBook_Earth.Recipe(),
		new SpellBook_Fire.Recipe(),
		new SpellBook_Frost.Recipe(),
		new SpellBook_Lightning.Recipe(),
		new SpellBook_Prismatic.Recipe(),
		new SpellBook_Regrowth.Recipe(),
		new SpellBook_Transfusion.Recipe(),
		new SpellBook_Warding.Recipe(),
		new PotionOfWeaponEnhance.Recipe(),
		new PotionOfArmorEnhance.Recipe(),
		new GunSmithingTool.Recipe()
	};
	
	private static Recipe[] threeIngredientRecipes = new Recipe[]{
		new Potion.SeedToPotion(),
		new StewedMeat.threeMeat(),
		new MeatPie.Recipe(),
		new ElixirOfTalent.Recipe(),
		new SpearNShield.Recipe(),
		new LanceNShield.Recipe(),
		//new Scrap.Recipe(),
		//new BrassScrap.Recipe(),
		new APBullet.Recipe(),
		new HPBullet.Recipe(),
		new AdvancedEvolution.Recipe(),
		new ChainFlail.Recipe(),
		new FlameScimitar.Recipe(),
		new FrostScimitar.Recipe(),
		new PoisonScimitar.Recipe(),
		new ElectroScimitar.Recipe(),
		new UnholyBible.Recipe(),
		new CursedSword.Recipe(),
		new Ballista.Recipe(),
		new TacticalShield.Recipe1(),
		new TacticalShield.Recipe2(),
		new TacticalShield.Recipe3(),
		new ForceGlove.Recipe(),
		new HolySword.Recipe(),
		new Cross.Recipe(),
		new ElixirOfHealth.Recipe(),
		new AutoHandgun.Recipe1(),
		new AutoHandgun.Recipe2(),
		new AutoHandgun.Recipe3(),
		new AutoRifle.Recipe1(),
		new AutoRifle.Recipe2(),
		new AutoRifle.Recipe3(),
		new MarksmanRifle.Recipe1(),
		new MarksmanRifle.Recipe2(),
		new MarksmanRifle.Recipe3(),
		new WA2000.Recipe1(),
		new WA2000.Recipe2(),
		new WA2000.Recipe3(),
		new FireImbueSpell.Recipe(),
		new AssassinsSpear.Recipe(),
		new Xray.Recipe(),
		new DoubleGreatSword.Recipe(),
		new ChaosSword.Recipe()
	};
	
	public static ArrayList<Recipe> findRecipes(ArrayList<Item> ingredients){

		ArrayList<Recipe> result = new ArrayList<>();

		for (Recipe recipe : variableRecipes){
			if (recipe.testIngredients(ingredients)){
				result.add(recipe);
			}
		}

		if (ingredients.size() == 1){
			for (Recipe recipe : oneIngredientRecipes){
				if (recipe.testIngredients(ingredients)){
					result.add(recipe);
				}
			}
			
		} else if (ingredients.size() == 2){
			for (Recipe recipe : twoIngredientRecipes){
				if (recipe.testIngredients(ingredients)){
					result.add(recipe);
				}
			}
			
		} else if (ingredients.size() == 3){
			for (Recipe recipe : threeIngredientRecipes){
				if (recipe.testIngredients(ingredients)){
					result.add(recipe);
				}
			}
		}
		
		return result;
	}

	public static boolean usableInRecipe(Item item){
		if (item instanceof EquipableItem){
			//only weapons and wands allowed among equipment items
			return item.isIdentified() && !item.cursed && (item instanceof MissileWeapon || item instanceof MeleeWeapon || item instanceof Pickaxe) && !item.isEquipped( hero );
		} else if (item instanceof Wand) {
			return item.isIdentified() && !item.cursed;
		} else {
			//other items can be unidentified, but not cursed
			return !item.cursed;
		}
	}
}