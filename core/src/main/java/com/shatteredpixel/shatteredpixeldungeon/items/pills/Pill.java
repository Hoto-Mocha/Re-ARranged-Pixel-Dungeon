package com.shatteredpixel.shatteredpixeldungeon.items.pills;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.InventoryStone;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndUseItem;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;
import java.util.HashSet;

public class Pill extends Item {

    public static final String AC_USE = "USE";

    //used internally for potions that can be drunk or thrown
    public static final String AC_CHOOSE = "CHOOSE";

    private static final float TIME_TO_USE = 1f;

    protected boolean enemyAlternative = false; //this should be true if the pill has different effects on heroes and enemies.

    {
        stackable = true;
        defaultAction = AC_USE;
    }

    //affects how strongly on-potion talents trigger from this potion
    protected float talentFactor = 0.5f;
    //the chance (0-1) of whether on-potion talents trigger from this potion
    protected float talentChance = 0.5f;

    protected static final HashSet<Class<?extends Pill>> dangerousPills = new HashSet<>();
    static {
        dangerousPills.add(PillOfParalysis.class);
        dangerousPills.add(PillOfFlame.class);
        dangerousPills.add(PillOfFrost.class);
        dangerousPills.add(PillOfToxin.class);
    }


    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_USE );
        return actions;
    }

    @Override
    public String defaultAction() {
        if (Dungeon.hero.heroClass == HeroClass.MEDIC && dangerousPills.contains(this.getClass())){
            return AC_CHOOSE;
        } else {
            return AC_USE;
        }
    }

    @Override
    public void execute( Hero hero, String action ) {
        super.execute(hero, action);
        if (action.equals(AC_USE)) {
            if (dangerousPills.contains(getClass())) {
                GameScene.show(
                     new WndOptions(new ItemSprite(this),
                             Messages.get(Pill.class, "harmful"),
                             Messages.get(Pill.class, "sure_take"),
                             Messages.get(Pill.class, "yes"), Messages.get(Pill.class, "no") ) {
                         @Override
                         protected void onSelect(int index) {
                             if (index == 0) {
                                 apply( hero );
                             }
                         }
                     }
                );
            } else {
                apply(hero);
            }
            return;
        }
        if (action.equals(AC_CHOOSE)) {
            GameScene.show(new WndUseItem(null, this) );
            return;
        }
    }

    public void apply( Hero hero ) {
        detach( hero.belongings.backpack );

        if (curUser.heroClass == HeroClass.MEDIC) {
            hero.spendAndNext( 0 );
        } else {
            hero.spendAndNext( TIME_TO_USE );
        }

        Sample.INSTANCE.play( Assets.Sounds.DRINK );

        hero.sprite.operate( hero.pos );
        useEffect(hero);
        Catalog.countUse(getClass());
    }

    @Override
    protected void onThrow(int cell) {
        if (curUser.heroClass == HeroClass.MEDIC) {
            if (Dungeon.level.pit[cell] || Actor.findChar(cell) == null || cell == curUser.pos){
                super.onThrow( cell );
            } else {
                useEffect(Actor.findChar(cell));
                Invisibility.dispel();
            }
        } else {
            super.onThrow(cell);
        }
    }

    public void useEffect(Char appliedCh) { //pills can be applied to the enemies
        Catalog.countUse(getClass());
    };

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public String desc() {
        String desc = super.desc();
//        TODO: 간호사 기능 추가
//        if (curUser.heroClass == HeroClass.NURSE && enemyAlternative) {
//            desc += "\n\n" + Messages.get(this, "enemy_desc");
//        }
        return desc;
    }

    @Override
    public int value() {
        //potions' value/2 by default
        return 15 * quantity;
    }

    @Override
    public int energyVal() {
        //potions' energyVal/2 by default
        return 3 * quantity;
    }

    public static class PlaceHolder extends Pill {

        {
            image = ItemSpriteSheet.PILLS_HOLDER;
        }

        @Override
        public void useEffect(Char appliedCh) {
            //does nothing
        }

        @Override
        public boolean isSimilar(Item item) {
            return item instanceof Pill;
        }

        @Override
        public String info() {
            return "";
        }
    }
}
