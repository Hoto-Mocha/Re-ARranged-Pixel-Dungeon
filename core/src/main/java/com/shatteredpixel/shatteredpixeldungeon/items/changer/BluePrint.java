package com.shatteredpixel.shatteredpixeldungeon.items.changer;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Transmuting;
import com.shatteredpixel.shatteredpixeldungeon.items.ArcaneResin;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.ArcaneBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Firebomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Flashbang;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.FrostBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.HolyBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Noisemaker;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.RegrowthBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.ShockBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.ShrapnelBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.WoollyBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfInvisibility;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.GooBlob;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.MetalShard;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ExoticScroll;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Evolution;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.UpgradeDust;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssassinsBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Flail;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gauntlet;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Glaive;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Greatshield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RoundShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RunicBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Spear;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Whip;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.AR_T6;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.AssassinsSpear;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.ChainFlail;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.ChainWhip;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.ForceGlove;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.HG_T6;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.Lance;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.LanceNShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.ObsidianShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.SR_T6;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.SpearNShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.TacticalShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.TrueRunicBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.UnformedBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.AR.AR_T5;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.HG.HG_T5;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.SR.SR_T5;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ForceCube;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class BluePrint extends Item {

    private static final String AC_USE		= "USE";

    {
        image = ItemSpriteSheet.BLUEPRINT;
        defaultAction = AC_USE;
        stackable = false;

        unique = true;
        bones = false;
    }

    private MeleeWeapon newWeapon;

    public BluePrint(MeleeWeapon wep) {
        this.newWeapon = wep;
    }

    public BluePrint() {}

    private static final String NEW_WEAPON	= "newWeapon";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( NEW_WEAPON, newWeapon );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        newWeapon = (MeleeWeapon) bundle.get( NEW_WEAPON );
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_USE);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_USE)) {
            GameScene.selectItem( itemSelector );
        }
    }

    private Item changeItem( Item item ){
        if (item instanceof MeleeWeapon) {
            return changeWeapon((MeleeWeapon) item);
        } else {
            return null;
        }
    }

    private MeleeWeapon changeWeapon(MeleeWeapon wep) {
        MeleeWeapon result = this.newWeapon;

        result.level(0);
        result.quantity(1);
        int level = wep.trueLevel();
        if (level > 0) {
            result.upgrade( level );
        } else if (level < 0) {
            result.degrade( -level );
        }

        if (wep instanceof Gun && result instanceof Gun) {  //재료와 결과물 모두 총기일 경우 총기 개조 효과가 유지됨
            ((Gun) result).barrelMod = ((Gun) wep).barrelMod;
            ((Gun) result).magazineMod = ((Gun) wep).magazineMod;
            ((Gun) result).bulletMod = ((Gun) wep).bulletMod;
            ((Gun) result).weightMod = ((Gun) wep).weightMod;
            ((Gun) result).attachMod = ((Gun) wep).attachMod;
            ((Gun) result).enchantMod = ((Gun) wep).enchantMod;
        }

        result.enchantment = wep.enchantment;
        result.curseInfusionBonus = wep.curseInfusionBonus;
        result.masteryPotionBonus = wep.masteryPotionBonus;
        result.levelKnown = wep.levelKnown;
        result.cursedKnown = wep.cursedKnown;
        result.cursed = wep.cursed;
        result.augment = wep.augment;
        result.enchantHardened = wep.enchantHardened;

        return result;

    }
    
    private String inventoryTitle(){
        return Messages.get(this, "inv_title");
    }

    @Override
    public String desc() {
        String desc = super.desc();
        if (this.newWeapon != null) {
            desc += "\n\n" + Messages.get(this, "item_desc",
                    newWeapon.tier,
                    newWeapon.trueName(),
                    Math.min(100, 100-20*(newWeapon.tier-1)),
                    Math.min(100, 100-20*(newWeapon.tier-2)),
                    Math.min(100, 100-20*(newWeapon.tier-3)),
                    Math.min(100, 100-20*(newWeapon.tier-4)),
                    Math.min(100, 100-20*(newWeapon.tier-5)));
        }

        return desc;
    }

    protected void onItemSelected(Item item) {
        Item result = null;

        if (Random.Float() < 1-0.2f*(((MeleeWeapon)changeItem(item)).tier-((MeleeWeapon)item).tier))

        if (result == null){
            //This shouldn't ever trigger
            GLog.n( Messages.get(this, "nothing") );
        } else {
            if (result != item) {
                int slot = Dungeon.quickslot.getSlot(item);
                if (item.isEquipped(Dungeon.hero)) {
                    item.cursed = false; //to allow it to be unequipped
                    if (item instanceof KindOfWeapon && Dungeon.hero.belongings.secondWep() == item){
                        ((EquipableItem) item).doUnequip(Dungeon.hero, false);
                        ((KindOfWeapon) result).equipSecondary(Dungeon.hero);
                    } else {
                        ((EquipableItem) item).doUnequip(Dungeon.hero, false);
                        ((EquipableItem) result).doEquip(Dungeon.hero);
                    }
                    Dungeon.hero.spend(-Dungeon.hero.cooldown()); //cancel equip/unequip time
                } else {
                    item.detach(Dungeon.hero.belongings.backpack);
                    if (!result.collect()) {
                        Dungeon.level.drop(result, curUser.pos).sprite.drop();
                    } else if (Dungeon.hero.belongings.getSimilar(result) != null){
                        result = Dungeon.hero.belongings.getSimilar(result);
                    }
                }
                if (slot != -1
                        && result.defaultAction() != null
                        && !Dungeon.quickslot.isNonePlaceholder(slot)
                        && Dungeon.hero.belongings.contains(result)){
                    Dungeon.quickslot.setSlot(slot, result);
                }
            }
            if (result.isIdentified()){
                Catalog.setSeen(result.getClass());
            }
            Sample.INSTANCE.play(Assets.Sounds.READ);
            Dungeon.hero.spendAndNext(Actor.TICK);
            Transmuting.show(curUser, item, result);
            curUser.sprite.emitter().start(Speck.factory(Speck.CHANGE), 0.2f, 10);
            GLog.p( Messages.get(this, "morph") );
            detach(Dungeon.hero.belongings.backpack);
        }
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public int value() {
        return -1;
    }

    protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

        @Override
        public String textPrompt() {
            return inventoryTitle();
        }

        @Override
        public Class<? extends Bag> preferredBag() {
            return Belongings.Backpack.class;
        }

        @Override
        public boolean itemSelectable(Item item) {
            return item instanceof MeleeWeapon;
        }

        @Override
        public void onSelect( Item item ) {

            //FIXME this safety check shouldn't be necessary
            //it would be better to eliminate the curItem static variable.
            if (!(curItem instanceof BluePrint)){
                return;
            }

            if (item != null && itemSelectable(item)) {
                onItemSelected(item);
            }
        }
    };

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe {

        public static final ArrayList<ArrayList<Class<?extends Item>>> validIngredients = new ArrayList<>(); //레시피마다 재료 배열을 만들어 이곳에 저장한다.
        static {
            validIngredients.add( new ArrayList<>(Arrays.asList(RunicBlade.class,       UpgradeDust.class, Evolution.class)) );
            validIngredients.add( new ArrayList<>(Arrays.asList(Glaive.class,           UpgradeDust.class, Evolution.class)) );
            validIngredients.add( new ArrayList<>(Arrays.asList(Greatshield.class,      UpgradeDust.class, Evolution.class)) );
            validIngredients.add( new ArrayList<>(Arrays.asList(Whip.class,             UpgradeDust.class, Evolution.class)) );
            validIngredients.add( new ArrayList<>(Arrays.asList(Lance.class, ObsidianShield.class,         Evolution.class)) );
            validIngredients.add( new ArrayList<>(Arrays.asList(ChainWhip.class, Flail.class,              Evolution.class)) );
            validIngredients.add( new ArrayList<>(Arrays.asList(AssassinsBlade.class,   UpgradeDust.class, Evolution.class)) );
            validIngredients.add( new ArrayList<>(Arrays.asList(AR_T5.class,            UpgradeDust.class, Evolution.class)) );
            validIngredients.add( new ArrayList<>(Arrays.asList(SR_T5.class,            UpgradeDust.class, Evolution.class)) );
            validIngredients.add( new ArrayList<>(Arrays.asList(HG_T5.class,            UpgradeDust.class, Evolution.class)) );
            validIngredients.add( new ArrayList<>(Arrays.asList(Spear.class, RoundShield.class,            Evolution.class)) );
            validIngredients.add( new ArrayList<>(Arrays.asList(HG_T6.class, ObsidianShield.class,         Evolution.class)) );
            validIngredients.add( new ArrayList<>(Arrays.asList(Glaive.class, AssassinsBlade.class,        Evolution.class)) );
            validIngredients.add( new ArrayList<>(Arrays.asList(Gauntlet.class, ForceCube.class,           Evolution.class)) );
        }

        public static final LinkedHashMap<Integer, MeleeWeapon> indexToOutput = new LinkedHashMap<>(); //재료 배열의 인덱스를 넣으면 근접 무기를 반환한다.
        static {
            indexToOutput.put( 0, new TrueRunicBlade() );
            indexToOutput.put( 1, new Lance() );
            indexToOutput.put( 2, new ObsidianShield() );
            indexToOutput.put( 3, new ChainWhip() );
            indexToOutput.put( 4, new LanceNShield() );
            indexToOutput.put( 5, new ChainFlail() );
            indexToOutput.put( 6, new UnformedBlade() );
            indexToOutput.put( 7, new AR_T6() );
            indexToOutput.put( 8, new SR_T6() );
            indexToOutput.put( 9, new HG_T6() );
            indexToOutput.put( 10, new SpearNShield() );
            indexToOutput.put( 11, new TacticalShield() );
            indexToOutput.put( 12, new AssassinsSpear() );
            indexToOutput.put( 13, new ForceGlove() );
        }

        public static final LinkedHashMap<Integer, Integer> costs = new LinkedHashMap<>(); //재료 배열의 인덱스를 넣으면 연금술 에너지 필요량을 반환한다.
        static {
            costs.put( 0, 0 );
            costs.put( 1, 0 );
            costs.put( 2, 0 );
            costs.put( 3, 0 );
            costs.put( 4, 5 );
            costs.put( 5, 5 );
            costs.put( 6, 0 );
            costs.put( 7, 0 );
            costs.put( 8, 0 );
            costs.put( 9, 0 );
            costs.put( 10, 5 );
            costs.put( 11, 5 );
            costs.put( 12, 5 );
            costs.put( 13, 5 );
        }

        public ArrayList<Class<?extends Item>> ingredientToArray(ArrayList<Item> ingredients) { //연금술 솥에 넣은 아이템들의 클래스를 배열로 만든다.

            ArrayList<Class<?extends Item>> ingredientsClassList = new ArrayList<>();

            for (Item i : ingredients) {
                ingredientsClassList.add(i.getClass());
            }

            return ingredientsClassList;
        }

        @Override
        public boolean testIngredients(ArrayList<Item> ingredients) {
            boolean valid = false;

            ArrayList<Class<?extends Item>> ingredientsClassList = ingredientToArray(ingredients);

            for (ArrayList<Class<?extends Item>> a : validIngredients) {
                if (a.containsAll(ingredientsClassList)) {    //위에서 만든 배열에 포함된 클래스를 모두 포함하고 있는 validIngredients가 존재할 경우 연금 가능
                    valid = true;
                }
            }

            return valid;
        }

        @Override
        public int cost(ArrayList<Item> ingredients) {
            int cost = 0;

            ArrayList<Class<?extends Item>> ingredientsClassList = ingredientToArray(ingredients);

            for (ArrayList<Class<?extends Item>> a : validIngredients) {
                if (a.containsAll(ingredientsClassList)) {    //validIngredients에 포함된 어떤 배열이 위에서 만든 배열의 요소를 모두 포함할 경우 그 주소값을 가져와 costs의 키로 이용한다.
                    int index = validIngredients.indexOf(a);
                    cost = costs.get(index);
                }
            }

            return cost;
        }

        @Override
        public Item brew(ArrayList<Item> ingredients) {
            Item result = null;

            ArrayList<Class<?extends Item>> ingredientsClassList = ingredientToArray(ingredients);

            for (ArrayList<Class<?extends Item>> a : validIngredients) {
                if (a.containsAll(ingredientsClassList)) {    //validIngredients에 포함된 어떤 배열이 위에서 만든 배열의 요소를 모두 포함할 경우 그 주소값을 가져와 indexToOutput의 키로 이용한다.
                    int index = validIngredients.indexOf(a);
                    result = new BluePrint(indexToOutput.get(index));
                }
            }

            for (Item i : ingredients) {
                i.quantity(i.quantity()-1);
            }

            return result;
        }

        @Override
        public Item sampleOutput(ArrayList<Item> ingredients) {
            Item result = null;

            ArrayList<Class<?extends Item>> ingredientsClassList = ingredientToArray(ingredients);

            for (ArrayList<Class<?extends Item>> a : validIngredients) {
                if (a.containsAll(ingredientsClassList)) {    //validIngredients에 포함된 어떤 배열이 위에서 만든 배열의 요소를 모두 포함할 경우 그 주소값을 가져와 indexToOutput의 키로 이용한다.
                    int index = validIngredients.indexOf(a);
                    result = new BluePrint(indexToOutput.get(index));
                }
            }

            return result;
        }
    }

}
