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

package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.ArcaneResin;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.Recipe;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Blandfruit;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MeatPie;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Pasty;
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
import com.shatteredpixel.shatteredpixeldungeon.items.Cartridge;
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
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
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
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.scenes.AlchemyScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndInfoItem;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.PointerArea;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Arrays;

public class QuickRecipe extends Component {
	
	private ArrayList<Item> ingredients;
	
	private ArrayList<ItemSlot> inputs;
	private QuickRecipe.arrow arrow;
	private ItemSlot output;
	
	public QuickRecipe(Recipe.SimpleRecipe r){
		this(r, r.getIngredients(), r.sampleOutput(null));
	}
	
	public QuickRecipe(Recipe r, ArrayList<Item> inputs, final Item output) {
		
		ingredients = inputs;
		int cost = r.cost(inputs);
		boolean hasInputs = true;
		this.inputs = new ArrayList<>();
		for (final Item in : inputs) {
			anonymize(in);
			ItemSlot curr;
			curr = new ItemSlot(in) {
				{
					hotArea.blockLevel = PointerArea.NEVER_BLOCK;
				}

				@Override
				protected void onClick() {
					ShatteredPixelDungeon.scene().addToFront(new WndInfoItem(in));
				}
			};
			
			ArrayList<Item> similar = Dungeon.hero.belongings.getAllSimilar(in);
			int quantity = 0;
			for (Item sim : similar) {
				//if we are looking for a specific item, it must be IDed
				if (sim.getClass() != in.getClass() || sim.isIdentified()) quantity += sim.quantity();
			}
			
			if (quantity < in.quantity()) {
				curr.sprite.alpha(0.3f);
				hasInputs = false;
			}
			curr.showExtraInfo(false);
			add(curr);
			this.inputs.add(curr);
		}
		
		if (cost > 0) {
			arrow = new arrow(Icons.get(Icons.ARROW), cost);
			arrow.hardlightText(0x44CCFF);
		} else {
			arrow = new arrow(Icons.get(Icons.ARROW));
		}
		if (hasInputs) {
			arrow.icon.tint(1, 1, 0, 1);
			if (!(ShatteredPixelDungeon.scene() instanceof AlchemyScene)) {
				arrow.enable(false);
			}
		} else {
			arrow.icon.color(0, 0, 0);
			arrow.enable(false);
		}
		add(arrow);
		
		anonymize(output);
		this.output = new ItemSlot(output){
			@Override
			protected void onClick() {
				ShatteredPixelDungeon.scene().addToFront(new WndInfoItem(output));
			}
		};
		if (!hasInputs){
			this.output.sprite.alpha(0.3f);
		}
		this.output.showExtraInfo(false);
		add(this.output);
		
		layout();
	}
	
	@Override
	protected void layout() {
		
		height = 16;
		width = 0;

		int padding = inputs.size() == 1 ? 8 : 0;

		for (ItemSlot item : inputs){
			item.setRect(x + width + padding, y, 16, 16);
			width += 16 + padding;
		}
		
		arrow.setRect(x + width, y, 14, 16);
		width += 14;
		
		output.setRect(x + width, y, 16, 16);
		width += 16;

		width += padding;
	}
	
	//used to ensure that un-IDed items are not spoiled
	private void anonymize(Item item){
		if (item instanceof Potion){
			((Potion) item).anonymize();
		} else if (item instanceof Scroll){
			((Scroll) item).anonymize();
		}
	}
	
	public class arrow extends IconButton {
		
		BitmapText text;
		
		public arrow(){
			super();
		}
		
		public arrow( Image icon ){
			super( icon );
		}
		
		public arrow( Image icon, int count ){
			super( icon );
			hotArea.blockLevel = PointerArea.NEVER_BLOCK;

			text = new BitmapText( Integer.toString(count), PixelScene.pixelFont);
			text.measure();
			add(text);
		}
		
		@Override
		protected void layout() {
			super.layout();
			
			if (text != null){
				text.x = x;
				text.y = y;
				PixelScene.align(text);
			}
		}
		
		@Override
		protected void onPointerUp() {
			icon.brightness(1f);
		}

		@Override
		protected void onClick() {
			super.onClick();
			
			//find the window this is inside of and close it
			Group parent = this.parent;
			while (parent != null){
				if (parent instanceof Window){
					((Window) parent).hide();
					break;
				} else {
					parent = parent.parent;
				}
			}
			
			((AlchemyScene)ShatteredPixelDungeon.scene()).populate(ingredients, Dungeon.hero.belongings);
		}
		
		public void hardlightText(int color ){
			if (text != null) text.hardlight(color);
		}
	}
	
	//gets recipes for a particular alchemy guide page
	//a null entry indicates a break in section
	public static ArrayList<QuickRecipe> getRecipes( int pageIdx ){
		ArrayList<QuickRecipe> result = new ArrayList<>();
		switch (pageIdx){
			case 0: default:
				result.add(new QuickRecipe( new Potion.SeedToPotion(), new ArrayList<>(Arrays.asList(new Plant.Seed.PlaceHolder().quantity(3))), new WndBag.Placeholder(ItemSpriteSheet.POTION_HOLDER){
					@Override
					public String name() {
						return Messages.get(Potion.SeedToPotion.class, "name");
					}

					@Override
					public String info() {
						return "";
					}
				}));
				return result;
			case 1:
				Recipe r = new Scroll.ScrollToStone();
				for (Class<?> cls : Generator.Category.SCROLL.classes){
					Scroll scroll = (Scroll) Reflection.newInstance(cls);
					if (!scroll.isKnown()) scroll.anonymize();
					ArrayList<Item> in = new ArrayList<Item>(Arrays.asList(scroll));
					result.add(new QuickRecipe( r, in, r.sampleOutput(in)));
				}
				return result;
			case 2:
				result.add(new QuickRecipe( new StewedMeat.oneMeat() ));
				result.add(new QuickRecipe( new StewedMeat.twoMeat() ));
				result.add(new QuickRecipe( new StewedMeat.threeMeat() ));
				result.add(null);
				result.add(new QuickRecipe( new MeatPie.Recipe(),
						new ArrayList<Item>(Arrays.asList(new Pasty(), new Food(), new MysteryMeat.PlaceHolder())),
						new MeatPie()));
				result.add(null);
				result.add(new QuickRecipe( new Blandfruit.CookFruit(),
						new ArrayList<>(Arrays.asList(new Blandfruit(), new Plant.Seed.PlaceHolder())),
						new Blandfruit(){

							public String name(){
								return Messages.get(Blandfruit.class, "cooked");
							}
							
							@Override
							public String info() {
								return "";
							}
						}));
				return result;
			case 3:
				r = new ExoticPotion.PotionToExotic();
				for (Class<?> cls : Generator.Category.POTION.classes){
					Potion pot = (Potion) Reflection.newInstance(cls);
					ArrayList<Item> in = new ArrayList<>(Arrays.asList(pot));
					result.add(new QuickRecipe( r, in, r.sampleOutput(in)));
				}
				return result;
			case 4:
				r = new ExoticScroll.ScrollToExotic();
				for (Class<?> cls : Generator.Category.SCROLL.classes){
					Scroll scroll = (Scroll) Reflection.newInstance(cls);
					ArrayList<Item> in = new ArrayList<>(Arrays.asList(scroll));
					result.add(new QuickRecipe( r, in, r.sampleOutput(in)));
				}
				return result;
			case 5:
				r = new Bomb.EnhanceBomb();
				int i = 0;
				for (Class<?> cls : Bomb.EnhanceBomb.validIngredients.keySet()){
					if (i == 2){
						result.add(null);
						i = 0;
					}
					Item item = (Item) Reflection.newInstance(cls);
					ArrayList<Item> in = new ArrayList<>(Arrays.asList(new Bomb(), item));
					result.add(new QuickRecipe( r, in, r.sampleOutput(in)));
					i++;
				}
				return result;
			case 6:
				result.add(new QuickRecipe( new LiquidMetal.Recipe(),
						new ArrayList<Item>(Arrays.asList(new MissileWeapon.PlaceHolder())),
						new LiquidMetal()));
				result.add(new QuickRecipe( new LiquidMetal.Recipe(),
						new ArrayList<Item>(Arrays.asList(new MissileWeapon.PlaceHolder().quantity(2))),
						new LiquidMetal()));
				result.add(new QuickRecipe( new LiquidMetal.Recipe(),
						new ArrayList<Item>(Arrays.asList(new MissileWeapon.PlaceHolder().quantity(3))),
						new LiquidMetal()));
				result.add(null);
				result.add(null);
				result.add(new QuickRecipe( new ArcaneResin.Recipe(),
						new ArrayList<Item>(Arrays.asList(new Wand.PlaceHolder())),
						new ArcaneResin()));
				//result.add(null);
				//result.add(null);
				//result.add(new QuickRecipe(new Scrap.Recipe()));
				//result.add(null);
				//result.add(new QuickRecipe(new BrassScrap.Recipe()));
				result.add(null);
				result.add(null);
				result.add(new QuickRecipe(new Cartridge.Recipe()));
				result.add(new QuickRecipe(new GunSmithingTool.Recipe()));
				result.add(null);
				result.add(new QuickRecipe(new APBullet.Recipe()));
				result.add(new QuickRecipe(new HPBullet.Recipe()));
				result.add(null);
				result.add(new QuickRecipe(new StableAPBullet.Recipe()));
				result.add(new QuickRecipe(new StableHPBullet.Recipe()));
				result.add(null);
				result.add(null);
				result.add(new QuickRecipe(new Pistol.Recipe()));
				result.add(new QuickRecipe(new GoldenPistol.Recipe()));
				result.add(null);
				result.add(new QuickRecipe(new AutoHandgun.Recipe1()));
				result.add(null);
				result.add(new QuickRecipe(new AutoRifle.Recipe1()));
				result.add(null);
				result.add(new QuickRecipe(new MarksmanRifle.Recipe1()));
				result.add(new QuickRecipe(new WA2000.Recipe1()));
				result.add(null);
				result.add(null);
				result.add(new QuickRecipe(new SpearNShield.Recipe()));
				result.add(new QuickRecipe(new LanceNShield.Recipe()));
				result.add(null);
				result.add(new QuickRecipe(new ChainFlail.Recipe()));
				result.add(new QuickRecipe(new UnholyBible.Recipe()));
				result.add(new QuickRecipe(new CursedSword.Recipe()));
				result.add(new QuickRecipe(new Ballista.Recipe()));
				result.add(null);
				result.add(new QuickRecipe(new FlameScimitar.Recipe()));
				result.add(new QuickRecipe(new FrostScimitar.Recipe()));
				result.add(null);
				result.add(new QuickRecipe(new PoisonScimitar.Recipe()));
				result.add(new QuickRecipe(new ElectroScimitar.Recipe()));
				result.add(null);
				result.add(new QuickRecipe(new TacticalShield.Recipe1()));
				result.add(new QuickRecipe(new ForceGlove.Recipe()));
				result.add(new QuickRecipe(new AssassinsSpear.Recipe()));
				result.add(new QuickRecipe(new HolySword.Recipe()));
				result.add(new QuickRecipe(new DoubleGreatSword.Recipe()));
				result.add(new QuickRecipe(new ChaosSword.Recipe()));
				result.add(new QuickRecipe(new Cross.Recipe()));
				result.add(null);
				result.add(null);
				result.add(new QuickRecipe(new SpellBook_Empty.Recipe()));
				result.add(null);
				result.add(null);
				result.add(new QuickRecipe(new SpellBook_Fire.Recipe()));
				result.add(new QuickRecipe(new SpellBook_Frost.Recipe()));
				result.add(new QuickRecipe(new SpellBook_Lightning.Recipe()));
				result.add(null);
				result.add(new QuickRecipe(new SpellBook_Disintegration.Recipe()));
				result.add(new QuickRecipe(new SpellBook_Blast.Recipe()));
				result.add(new QuickRecipe(new SpellBook_Corrosion.Recipe()));
				result.add(null);
				result.add(new QuickRecipe(new SpellBook_Prismatic.Recipe()));
				result.add(new QuickRecipe(new SpellBook_Regrowth.Recipe()));
				result.add(new QuickRecipe(new SpellBook_Transfusion.Recipe()));
				result.add(null);
				result.add(new QuickRecipe(new SpellBook_Corruption.Recipe()));
				result.add(new QuickRecipe(new SpellBook_Warding.Recipe()));
				result.add(new QuickRecipe(new SpellBook_Earth.Recipe()));
				result.add(null);
				result.add(null);
				result.add(new QuickRecipe(new MissileButton.Recipe()));
				return result;
			case 7:
				result.add(new QuickRecipe(new AlchemicalCatalyst.Recipe(), new ArrayList<>(Arrays.asList(new Potion.PlaceHolder(), new Plant.Seed.PlaceHolder())), new AlchemicalCatalyst()));
				result.add(new QuickRecipe(new AlchemicalCatalyst.Recipe(), new ArrayList<>(Arrays.asList(new Potion.PlaceHolder(), new Runestone.PlaceHolder())), new AlchemicalCatalyst()));
				result.add(null);
				result.add(null);
				result.add(new QuickRecipe(new ArcaneCatalyst.Recipe(), new ArrayList<>(Arrays.asList(new Scroll.PlaceHolder(), new Runestone.PlaceHolder())), new ArcaneCatalyst()));
				result.add(new QuickRecipe(new ArcaneCatalyst.Recipe(), new ArrayList<>(Arrays.asList(new Scroll.PlaceHolder(), new Plant.Seed.PlaceHolder())), new ArcaneCatalyst()));
				return result;
			case 8:
				result.add(new QuickRecipe(new CausticBrew.Recipe()));
				result.add(new QuickRecipe(new BlizzardBrew.Recipe()));
				result.add(new QuickRecipe(new InfernalBrew.Recipe()));
				result.add(new QuickRecipe(new ShockingBrew.Recipe()));
				result.add(null);
				result.add(null);
				result.add(new QuickRecipe(new ElixirOfHoneyedHealing.Recipe()));
				result.add(new QuickRecipe(new ElixirOfAquaticRejuvenation.Recipe()));
				result.add(new QuickRecipe(new ElixirOfMight.Recipe()));
				result.add(new QuickRecipe(new ElixirOfDragonsBlood.Recipe()));
				result.add(new QuickRecipe(new ElixirOfIcyTouch.Recipe()));
				result.add(new QuickRecipe(new ElixirOfToxicEssence.Recipe()));
				result.add(new QuickRecipe(new ElixirOfArcaneArmor.Recipe()));
				result.add(new QuickRecipe(new ElixirOfTalent.Recipe()));
				result.add(new QuickRecipe(new ElixirOfHealth.Recipe()));
				result.add(null);
				result.add(null);
				result.add(new QuickRecipe(new PotionOfWeaponEnhance.Recipe()));
				result.add(new QuickRecipe(new PotionOfArmorEnhance.Recipe()));
				return result;
			case 9:
				result.add(new QuickRecipe(new TelekineticGrab.Recipe()));
				result.add(new QuickRecipe(new PhaseShift.Recipe()));
				result.add(new QuickRecipe(new WildEnergy.Recipe()));
				result.add(new QuickRecipe(new BeaconOfReturning.Recipe()));
				result.add(new QuickRecipe(new SummonElemental.Recipe()));
				result.add(new QuickRecipe(new FireImbueSpell.Recipe()));
				result.add(null);
				result.add(new QuickRecipe(new AquaBlast.Recipe()));
				result.add(new QuickRecipe(new ReclaimTrap.Recipe()));
				result.add(new QuickRecipe(new FeatherFall.Recipe()));
				result.add(null);
				result.add(new QuickRecipe(new Alchemize.Recipe()));
				result.add(new QuickRecipe(new MagicalInfusion.Recipe()));
				result.add(new QuickRecipe(new MagicalInfusion.Recipe2()));
				result.add(new QuickRecipe(new CurseInfusion.Recipe()));
				result.add(new QuickRecipe(new Recycle.Recipe()));
				result.add(new QuickRecipe(new Evolution.Recipe()));
				result.add(null);
				result.add(new QuickRecipe(new AdvancedEvolution.Recipe()));
				result.add(new QuickRecipe(new Xray.Recipe()));
				return result;
		}
	}
	
}
