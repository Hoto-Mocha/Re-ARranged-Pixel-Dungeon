package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.quick;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.InventoryScroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class PocketKnife extends QuickWeapon {
    {
        tier = 1;
        image = ItemSpriteSheet.POCKET_KNIFE;
        hitSound = Assets.Sounds.HIT_SLASH;

        bones = false;
    }

    public static final String AC_FORGE	= "FORGE";
    boolean upgraded = false;

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (upgraded) actions.add(AC_FORGE);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_FORGE)) {
            GameScene.selectItem( itemSelector );
        }
    }

    @Override
    public int max(int lvl) {
        return  4*(tier+1) +    //base
                lvl*(tier+1);   //level scaling
    }

    @Override
    public Item upgrade() {
        this.upgraded = true;
        return super.upgrade();
    }

    @Override
    public String desc() {
        String desc = super.desc();
        if (upgraded) {
            desc += "\n\n" + Messages.get(this, "upgraded");
        }
        return desc;
    }

    protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

        @Override
        public String textPrompt() {
            return Messages.get(PocketKnife.class, "select_item");
        }

        @Override
        public Class<? extends Bag> preferredBag() {
            return Belongings.Backpack.class;
        }

        @Override
        public boolean itemSelectable(Item item) {
            return item instanceof MeleeWeapon && item.isUpgradable();
        }

        @Override
        public void onSelect( Item item ) {
            if (item == null) return;

            item.upgrade();
            Sample.INSTANCE.play( Assets.Sounds.EVOKE );
            CellEmitter.center( curUser.pos ).burst( Speck.factory( Speck.STAR ), 7 );
            curUser.sprite.operate( curUser.pos );
            PocketKnife.this.detach( curUser.belongings.backpack );
        }
    };
}
