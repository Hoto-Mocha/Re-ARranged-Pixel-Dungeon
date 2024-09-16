package com.shatteredpixel.shatteredpixeldungeon.items.pills;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;
import java.util.HashSet;

public class Pill extends Item {

    public static final String AC_USE = "USE";

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
        }
    }

    public void apply( Hero hero ) {
        detach( hero.belongings.backpack );

        hero.spend( TIME_TO_USE );
        hero.busy();

        Sample.INSTANCE.play( Assets.Sounds.DRINK );

        hero.sprite.operate( hero.pos );
        useEffect(hero);
    }

    public void useEffect(Char appliedCh) { //pills can be applied to the enemies
//        TODO: 간호사 기능 추가
//        if (curUser.heroClass == HeroClass.NURSE) {
//            if (Random.Float() < talentChance){
//                Talent.onPotionUsed(curUser, appliedCh.pos, talentFactor);
//            }
//        }
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
