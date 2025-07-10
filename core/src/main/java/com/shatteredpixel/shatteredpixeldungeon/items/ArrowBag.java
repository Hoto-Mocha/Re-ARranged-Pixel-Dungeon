package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.PotionBandolier;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfMastery;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class ArrowBag extends Item {
    {
        image = ItemSpriteSheet.ARROW_BAG;
        levelKnown = true;
        unique = true;
        bones = false;
    }

    private static final String AC_USE = "USE";

    public Potion potion;
    public int uses;

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add(AC_USE);
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals(AC_USE)) {
            GameScene.selectItem(itemSelector);
        }
    }

    private static final String POTION	    = "potion";
    private static final String USES	    = "uses";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);

        bundle.put( POTION, potion );
        bundle.put( USES, uses );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        potion = (Potion)bundle.get( POTION );
        uses = bundle.getInt( USES );
    }

    @Override
    public ItemSprite.Glowing glowing() {
        if (potion != null) {
            return potion.potionGlowing();
        } else {
            return null;
        }
    }

    public int proc(Hero hero, Char enemy, int damage) {
        float dmg = damage;
        if (potion != null) {
            potion.potionProc(hero, enemy, dmg);
            uses--;
            dmg *= (1f + 0.2f * level());
        }

        if (uses <= 0) {
            potion = null;
            updateQuickslot();
        }

        return Math.round(dmg);
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", uses, potion.name());
    }

    protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

        @Override
        public String textPrompt() {
            return Messages.get(Waterskin.class, "seed");
        }

        @Override
        public Class<? extends Bag> preferredBag() {
            return PotionBandolier.class;
        }

        @Override
        public boolean itemSelectable(Item item) {
            return item instanceof Potion;
        }

        @Override
        public void onSelect(Item item) {
            item.detach(Dungeon.hero.belongings.backpack);
            if (item instanceof PotionOfStrength || item instanceof PotionOfMastery) {
                upgrade();
            } else {
                potion = (Potion)item;
                uses = 8 + 4 * level();
            }

            Dungeon.hero.sprite.operate(Dungeon.hero.pos);
            Sample.INSTANCE.play(Assets.Sounds.DRINK);

            updateQuickslot();
        }
    };

    @Override
    public int value() {
        return -1;
    }


}
