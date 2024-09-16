package com.shatteredpixel.shatteredpixeldungeon.items.trinkets;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Blacksmith;
import com.shatteredpixel.shatteredpixeldungeon.items.GunSmithingTool;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.levels.CavesLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTitledMessage;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Necklace extends Trinket {

    String AC_INSERT = "insert";

    private static final LinkedHashMap<Integer, Integer> gems = new LinkedHashMap<Integer, Integer>() {
        {
            put(ItemSpriteSheet.RING_GARNET     , ItemSpriteSheet.NECKLACE_GARNET);
            put(ItemSpriteSheet.RING_RUBY       , ItemSpriteSheet.NECKLACE_RUBY);
            put(ItemSpriteSheet.RING_TOPAZ      , ItemSpriteSheet.NECKLACE_TOPAZ);
            put(ItemSpriteSheet.RING_EMERALD    , ItemSpriteSheet.NECKLACE_EMERALD);
            put(ItemSpriteSheet.RING_ONYX       , ItemSpriteSheet.NECKLACE_ONYX);
            put(ItemSpriteSheet.RING_OPAL       , ItemSpriteSheet.NECKLACE_OPAL);
            put(ItemSpriteSheet.RING_TOURMALINE , ItemSpriteSheet.NECKLACE_TOURMALINE);
            put(ItemSpriteSheet.RING_SAPPHIRE   , ItemSpriteSheet.NECKLACE_SAPPHIRE);
            put(ItemSpriteSheet.RING_AMETHYST   , ItemSpriteSheet.NECKLACE_AMETHYST);
            put(ItemSpriteSheet.RING_QUARTZ     , ItemSpriteSheet.NECKLACE_QUARTZ);
            put(ItemSpriteSheet.RING_AGATE      , ItemSpriteSheet.NECKLACE_AGATE);
            put(ItemSpriteSheet.RING_DIAMOND    , ItemSpriteSheet.NECKLACE_DIAMOND);
        }
    };

    public static Ring.RingBuff buff;
    private Ring ring;

    {
        image = ItemSpriteSheet.NECKLACE;
    }

    @Override
    protected int upgradeEnergyCost() {
        return 15+8*level();
    }

    @Override
    public String desc() {
        if (ring != null) {
            return Messages.get(this, "desc_ring");
        } else {
            return Messages.get(this, "desc");
        }
    }

    @Override
    public String statsDesc() {
        if (ring == null) {
            return Messages.get(this, "stats_desc");
        }
        if (isIdentified()) {
            return Messages.get(this, "stats_desc_ring", buffedLvl(), ring.trueName(), buffedRing(ring).statsInfo());
        } else {
            return Messages.get(this, "typical_stats_desc_ring", buffedLvl(), ring.trueName(), buffedRing(ring).statsInfo());
        }
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_INSERT);
        return actions;
    }
    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_INSERT)) {
            GameScene.selectItem(itemSelector);
        }
    }

    public Ring buffedRing(Ring r) {
        r.level(buffedLvl());
        return r;
    }

    private static final String IMAGE	= "image";
    private static final String RING	= "ring";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(IMAGE, image());
        bundle.put(RING, ring);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        image = bundle.getInt(IMAGE);
        ring = (Ring) bundle.get(RING);
    }

    @Override
    public boolean collect(Bag container) {
        Hero hero = Dungeon.hero;
        if (hero != null) {
            if (buff != null){
                buff.detach();
                hero.removeNecklaceRing();
                hero.updateHT(false);
                buff = null;
            }
            if (ring != null) {
                buff = buffedRing(ring).buff();
                buff.attachTo( hero );
                hero.setNecklaceRing(ring);
                hero.updateHT(false);
            }
        }
        return super.collect(container);
    }

    protected void onThrow( int cell ) {
        Hero hero = Dungeon.hero;
        if (buff != null){
            buff.detach();
            hero.removeNecklaceRing();
            hero.updateHT(false);
            buff = null;
        }
        super.onThrow(cell);
    }

    @Override
    protected void onDetach() {
        Hero hero = Dungeon.hero;
        if (buff != null){
            buff.detach();
            hero.removeNecklaceRing();
            buff = null;
        }
        hero.updateHT(false);
        super.onDetach();
    }

    protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

        @Override
        public String textPrompt() {
            return Messages.get(Necklace.class, "inv_title");
        }

        @Override
        public Class<? extends Bag> preferredBag() {
            return Belongings.Backpack.class;
        }

        @Override
        public boolean itemSelectable(Item item) {
            return item instanceof Ring && item.isIdentified() && !item.cursed && !item.isEquipped(Dungeon.hero);
        }

        @Override
        public void onSelect( Item item ) {
            if (item != null) {
                GameScene.show(new WndInsert(Necklace.this, (Ring) item));
            }
        }
    };

    public static class WndInsert extends WndOptions {

        Necklace necklace;
        Ring ring;

        public WndInsert(Necklace necklace, Ring ring){
            super(new ItemSprite(necklace),
                    Messages.titleCase(new Necklace().name()),
                    Messages.get(Necklace.class, "insert_desc", ring.trueName()),
                    Messages.get(Necklace.class, "insert_ok"),
                    Messages.get(Necklace.class, "insert_cancel"));
            this.necklace = necklace;
            this.ring = ring;
        }

        @Override
        protected void onSelect(int index) {
            if (index == 0) {
                Hero hero = Dungeon.hero;
                //반지 삽입 메커니즘
                if (Necklace.buff != null){
                    Necklace.buff.detach();
                    hero.removeNecklaceRing();
                    Necklace.buff = null;
                }
                necklace.ring = ring;
                Necklace.buff = necklace.buffedRing(ring).buff();
                Necklace.buff.attachTo( hero );
                hero.setNecklaceRing(ring);
                hero.updateHT(false);
                ring.detach(hero.belongings.backpack);
                necklace.image = Necklace.gems.get(ring.image());
                updateQuickslot();
                //이펙트
                evoke(hero);
                Sample.INSTANCE.play(Assets.Sounds.EVOKE);
                hero.spendAndNext(Actor.TICK);
                hero.sprite.operate(hero.pos);
            }

            hide();
        }

    }
}
