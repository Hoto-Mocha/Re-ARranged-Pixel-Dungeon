package com.shatteredpixel.shatteredpixeldungeon.items.changer;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Transmuting;
import com.shatteredpixel.shatteredpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.KnightsShield;
import com.shatteredpixel.shatteredpixeldungeon.items.Rosary;
import com.shatteredpixel.shatteredpixeldungeon.items.Saddle;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.CorrosiveBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.GoldenBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.NaturesBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.TacticalBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.WindBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DeathSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.EnhancedMachete;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeroSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Machete;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Shovel;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Spade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTitledMessage;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class OldAmulet extends Item {

    public static final String AC_USE		= "USE";

    ArrayList<Integer> abilityList = new ArrayList<>();

    {
        image = ItemSpriteSheet.OLD_AMULET;
        defaultAction = AC_USE;
        stackable = false;

        unique = true;
        bones = false;

        while (abilityList.size() < 3) {
            int index = Random.Int(16);
            if (!abilityList.contains(index)) {
                abilityList.add(index);
            }
        }
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (hero.buff(TempleCurse.class) != null) {
            actions.remove(AC_USE);
        } else {
            actions.add(AC_USE);
        }
        return actions;
    }

    @Override
    public boolean doPickUp(Hero hero, int pos) {
        if (Dungeon.depth == 20 && Dungeon.branch == 2 && hero.buff(TempleCurse.class) == null) {
            Dungeon.templeCompleted = true;
            Buff.affect(hero, TempleCurse.class);
            PixelScene.shake(1, 0.7f);
            Sample.INSTANCE.play(Assets.Sounds.ROCKS, 0.7f, 0.5f);
            GLog.n(Messages.get(this, "shake"));
        }
        return super.doPickUp(hero, pos);
    }

    @Override
    public String desc() {
        String desc = super.desc();

        if (Dungeon.hero != null && Dungeon.hero.buff(TempleCurse.class) != null) {
            desc += "\n\n" + Messages.get(this, "cannot_use");
        }

        return desc;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_USE)) {
            if (hero.buff(TempleCurse.class) != null) {
                GLog.w(Messages.get(this, "cannot_use"));
            } else {
                GameScene.selectItem( itemSelector );
            }
        }
    }

    private static final String ABILITY_LIST_0	= "abilityList_0";
    private static final String ABILITY_LIST_1	= "abilityList_1";
    private static final String ABILITY_LIST_2	= "abilityList_2";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( ABILITY_LIST_0, abilityList.get(0) );
        bundle.put( ABILITY_LIST_1, abilityList.get(1) );
        bundle.put( ABILITY_LIST_2, abilityList.get(2) );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        abilityList.clear();
        abilityList.add(bundle.getInt(ABILITY_LIST_0));
        abilityList.add(bundle.getInt(ABILITY_LIST_1));
        abilityList.add(bundle.getInt(ABILITY_LIST_2));
    }

    private String inventoryTitle(){
        return Messages.get(this, "inv_title");
    }

    public static Item changeItem( Item item ){
        if (item instanceof SpiritBow) {
            return changeBow((SpiritBow)item);
        } else if (item instanceof Shovel) {
            return changeShovel((Shovel)item);
        } else if (item instanceof Machete) {
            return changeMachete((Machete)item);
        } else if (item instanceof KnightsShield) {
            return changeShield();
        } else {
            return null;
        }
    }

    private static SpiritBow changeBow(SpiritBow bow) {
        SpiritBow newBow;
        switch (Random.Int(5)) {
            case 0: default:
                newBow = new NaturesBow();
                break;
            case 1:
                newBow = new GoldenBow();
                break;
            case 2:
                newBow = new CorrosiveBow();
                break;
            case 3:
                newBow = new WindBow();
                break;
            case 4:
                newBow = new TacticalBow();
                break;

        }

        newBow.level(0);
        newBow.quantity(1);
        int level = bow.trueLevel();
        if (level > 0) {
            newBow.upgrade( level );
        } else if (level < 0) {
            newBow.degrade( -level );
        }

        newBow.enchantment = bow.enchantment;
        newBow.curseInfusionBonus = bow.curseInfusionBonus;
        newBow.masteryPotionBonus = bow.masteryPotionBonus;
        newBow.levelKnown = bow.levelKnown;
        newBow.cursedKnown = bow.cursedKnown;
        newBow.cursed = bow.cursed;
        newBow.augment = bow.augment;
        newBow.enchantHardened = bow.enchantHardened;

        return newBow;
    }

    private static Spade changeShovel(Shovel shovel) {
        Spade newShovel = new Spade();

        newShovel.level(0);
        newShovel.quantity(1);
        int level = shovel.trueLevel();
        if (level > 0) {
            newShovel.upgrade( level );
        } else if (level < 0) {
            newShovel.degrade( -level );
        }

        newShovel.enchantment = shovel.enchantment;
        newShovel.curseInfusionBonus = shovel.curseInfusionBonus;
        newShovel.masteryPotionBonus = shovel.masteryPotionBonus;
        newShovel.levelKnown = shovel.levelKnown;
        newShovel.cursedKnown = shovel.cursedKnown;
        newShovel.cursed = shovel.cursed;
        newShovel.augment = shovel.augment;
        newShovel.enchantHardened = shovel.enchantHardened;

        return newShovel;
    }

    private static Machete changeMachete(Machete machete) {
        EnhancedMachete newMachete = new EnhancedMachete();

        newMachete.level(0);
        newMachete.quantity(1);
        int level = machete.trueLevel();
        if (level > 0) {
            newMachete.upgrade( level );
        } else if (level < 0) {
            newMachete.degrade( -level );
        }

        newMachete.enchantment = machete.enchantment;
        newMachete.curseInfusionBonus = machete.curseInfusionBonus;
        newMachete.masteryPotionBonus = machete.masteryPotionBonus;
        newMachete.levelKnown = machete.levelKnown;
        newMachete.cursedKnown = machete.cursedKnown;
        newMachete.cursed = machete.cursed;
        newMachete.augment = machete.augment;
        newMachete.enchantHardened = machete.enchantHardened;

        return newMachete;
    }

    private static Item changeShield() {
        Item newItem;
        switch (Dungeon.hero.subClass) {
            case DEATHKNIGHT:
                newItem = new DeathSword();
                break;
            case HORSEMAN:
                newItem = new Saddle();
                break;
            case CRUSADER:
                newItem = new Rosary();
                break;
            default:
                newItem = null;
                break;
        }

        return newItem;
    }

    protected void onItemSelected(Item item) {
        Item result = changeItem(item);

        if (result == null){
            //This shouldn't ever trigger
            GLog.n( Messages.get(this, "nothing") );
            curItem.collect( curUser.belongings.backpack );
        } else {
            if (result != item) {
                int slot = Dungeon.quickslot.getSlot(item);
                if (item.isEquipped(Dungeon.hero)) {
                    item.cursed = false; //to allow it to be unequipped
                    if (item instanceof Artifact && result instanceof Ring){
                        //if we turned an equipped artifact into a ring, ring goes into inventory
                        ((EquipableItem) item).doUnequip(Dungeon.hero, false);
                        if (!result.collect()){
                            Dungeon.level.drop(result, curUser.pos).sprite.drop();
                        }
                    } else if (item instanceof KindOfWeapon && Dungeon.hero.belongings.secondWep() == item){
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
            Sample.INSTANCE.play(Assets.Sounds.EVOKE);
            CellEmitter.center( curUser.pos ).burst( Speck.factory( Speck.STAR ), 7 );
            new Flare( 6, 32 ).color(0xFFFF00, true).show( curUser.sprite, 2f );
            Dungeon.hero.spendAndNext(Actor.TICK);
            Dungeon.hero.sprite.operate(Dungeon.hero.pos);
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
            if (!item.isIdentified()) return false;
            switch (Dungeon.hero.heroClass) {
                case WARRIOR: default:
                    return item instanceof BrokenSeal;
                case MAGE:
                    return item instanceof MagesStaff;
                case ROGUE:
                    return item instanceof Ring;
                case HUNTRESS:
                    return item instanceof SpiritBow;
                case DUELIST:
                case SAMURAI:
                    return item instanceof MeleeWeapon && !(item instanceof MagesStaff) && !(item instanceof Gun);
                case GUNNER:
                    return item instanceof Gun;
                case ADVENTURER:
                    return item instanceof Machete || item instanceof Shovel;
                case KNIGHT:
                    return item instanceof KnightsShield;
            }
        }

        @Override
        public void onSelect( Item item ) {

            //FIXME this safety check shouldn't be necessary
            //it would be better to eliminate the curItem static variable.
            if (!(curItem instanceof OldAmulet)){
                return;
            }

            if (item != null && itemSelectable(item)) {
                switch (Dungeon.hero.heroClass) {
                    default:
                        onItemSelected(item);
                        break;
                    case DUELIST:
                        GameScene.show(new WndAbilitySelect((MeleeWeapon)item, abilityList.get(0), abilityList.get(1), abilityList.get(2)));
                        break;
                }
            }
        }
    };

    public static class WndAbilitySelect extends WndOptions {

        private MeleeWeapon wep;
        private ArrayList<Integer> ability = new ArrayList<>();

        public WndAbilitySelect(MeleeWeapon wep, int ability_1, int ability_2, int ability_3) {
            super(new ItemSprite(new HeroSword()),
                    Messages.titleCase(new HeroSword().name()),
                    Messages.get(HeroSword.class, "ability_select"),
                    new HeroSword(ability_1, wep).abilityName(),
                    new HeroSword(ability_2, wep).abilityName(),
                    new HeroSword(ability_3, wep).abilityName(),
                    Messages.get(HeroSword.class, "cancel"));
            ability.add(ability_1);
            ability.add(ability_2);
            ability.add(ability_3);
            this.wep = wep;
        }

        @Override
        protected void onSelect(int index) {
            if (index < 3) {
                HeroSword heroSword = new HeroSword(ability.get(index), wep);

                heroSword.level(0);
                heroSword.quantity(1);
                int level = wep.trueLevel();
                if (level > 0) {
                    heroSword.upgrade( level );
                } else if (level < 0) {
                    heroSword.degrade( -level );
                }

                heroSword.enchantment = wep.enchantment;
                heroSword.curseInfusionBonus = wep.curseInfusionBonus;
                heroSword.masteryPotionBonus = wep.masteryPotionBonus;
                heroSword.levelKnown = wep.levelKnown;
                heroSword.cursedKnown = wep.cursedKnown;
                heroSword.cursed = wep.cursed;
                heroSword.augment = wep.augment;
                heroSword.enchantHardened = wep.enchantHardened;

                int slot = Dungeon.quickslot.getSlot(wep);
                if (wep.isEquipped(Dungeon.hero)) {
                    wep.cursed = false; //to allow it to be unequipped
                    if (Dungeon.hero.belongings.secondWep() == wep){
                        wep.doUnequip(Dungeon.hero, false);
                        heroSword.equipSecondary(Dungeon.hero);
                    } else {
                        wep.doUnequip(Dungeon.hero, false);
                        heroSword.doEquip(Dungeon.hero);
                    }
                    Dungeon.hero.spend(-Dungeon.hero.cooldown()); //cancel equip/unequip time
                } else {
                    wep.detach(Dungeon.hero.belongings.backpack);
                    if (!heroSword.collect()) {
                        Dungeon.level.drop(heroSword, curUser.pos).sprite.drop();
                    } else if (Dungeon.hero.belongings.getSimilar(heroSword) != null){
                        heroSword = (HeroSword) Dungeon.hero.belongings.getSimilar(heroSword);
                    }
                }
                if (slot != -1
                        && heroSword.defaultAction() != null
                        && !Dungeon.quickslot.isNonePlaceholder(slot)
                        && Dungeon.hero.belongings.contains(heroSword)){
                    Dungeon.quickslot.setSlot(slot, heroSword);
                }
                Sample.INSTANCE.play(Assets.Sounds.EVOKE);
                CellEmitter.center( curUser.pos ).burst( Speck.factory( Speck.STAR ), 7 );
                new Flare( 6, 32 ).color(0xFFFF00, true).show( curUser.sprite, 2f );
                Dungeon.hero.spendAndNext(Actor.TICK);
                Dungeon.hero.sprite.operate(Dungeon.hero.pos);
                Transmuting.show(curUser, wep, heroSword);
                curUser.sprite.emitter().start(Speck.factory(Speck.CHANGE), 0.2f, 10);
                GLog.p( Messages.get(OldAmulet.class, "morph") );
                Dungeon.hero.belongings.getItem(OldAmulet.class).detach(Dungeon.hero.belongings.backpack);
            } else {
                hide();
            }
        }

        @Override
        protected boolean hasInfo(int index) {
            return index < 3;
        }

        @Override
        protected void onInfo( int index ) {
            HeroSword heroSword = new HeroSword(ability.get(index), wep);
            if (wep.isIdentified()) {
                heroSword.level(wep.buffedLvl());
                heroSword.identify();
            }
            GameScene.show(new WndTitledMessage(
                    Icons.get(Icons.INFO),
                    Messages.titleCase(heroSword.abilityName()),
                    heroSword.abilityInfo()));
        }

    }

    public static class TempleCurse extends Buff {
        @Override
        public int icon() {
            return BuffIndicator.AMULET;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0xC68749);
        }

        public static void beckonEnemies(){
            if (Dungeon.hero.buff(TempleCurse.class) != null) {
                for (Mob m : Dungeon.level.mobs){
                    if (m.alignment == Char.Alignment.ENEMY) {
                        m.beckon(Dungeon.hero.pos);
                    }
                }
            }
        }

        @Override
        public boolean act() {
            beckonEnemies();

            spend(TICK);
            return true;
        }

        public void saySwitch(){
            if (Dungeon.branch == 2) {
                GLog.n(Messages.get(this, "beckon"));
            } else {
                GLog.i(Messages.get(this, "escape"));
            }
        }

        public void onLevelSwitch() {
            if (Dungeon.branch == 2) {
                PixelScene.shake(1, 0.7f);
                Sample.INSTANCE.play(Assets.Sounds.ROCKS, 0.7f, 0.5f);
            }
        }
    }
}
