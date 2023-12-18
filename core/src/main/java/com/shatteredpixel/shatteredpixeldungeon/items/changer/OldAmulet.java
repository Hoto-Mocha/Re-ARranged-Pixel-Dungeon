package com.shatteredpixel.shatteredpixeldungeon.items.changer;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AscensionChallenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Transmuting;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.CorrosiveBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.GoldenBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.NaturesBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.TacticalBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.WindBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class OldAmulet extends Item {

    public static final String AC_USE		= "USE";

    {
        image = ItemSpriteSheet.OLD_AMULET;
        defaultAction = AC_USE;
        stackable = false;

        unique = true;
        bones = false;
    }

    protected Class<?extends Bag> preferredBag = Belongings.Backpack.class;

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

        if (Dungeon.hero.buff(TempleCurse.class) != null) {
            desc += "\n\n" + Messages.get(this, "cannot_use");
        }

        return desc;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_USE)) {
            GameScene.selectItem( itemSelector );
        }
    }

    private String inventoryTitle(){
        return Messages.get(this, "inv_title");
    }

    public static Item changeItem( Item item ){
        if (item instanceof SpiritBow) {
            return changeBow((SpiritBow)item);
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
            return preferredBag;
        }

        @Override
        public boolean itemSelectable(Item item) {
            switch (Dungeon.hero.heroClass) {
                case WARRIOR: default:
                    return item instanceof BrokenSeal;
                case MAGE:
                    return item instanceof MagesStaff;
                case ROGUE:
                    return item instanceof CloakOfShadows;
                case HUNTRESS:
                    return item instanceof SpiritBow;
                case DUELIST:
                    return item instanceof MeleeWeapon;
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
                onItemSelected(item);
            }
        }
    };

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
