package com.shatteredpixel.shatteredpixeldungeon.items.pills;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

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
            apply(hero);
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
