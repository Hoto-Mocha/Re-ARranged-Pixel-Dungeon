package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping.discover;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfFeatherFall;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.ExoticPotion;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Pickaxe;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ExoticScroll;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.DistortionTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.SummoningTrap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTextInput;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTitledMessage;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Collections;
//This is from Elemental PD

public class Teleporter extends Item {

    String AC_TELEPORT = "teleport";
    String AC_RETURN = "return";
    String AC_SPAWN = "spawn";
    String AC_RANDOMSPAWN = "randomSpawn";
    String AC_GETITEM = "getItem";
    String AC_MAPPING = "mapping";

    static ArrayList<Class<?>> itemClass = new ArrayList<>();

    {
        defaultAction = AC_TELEPORT;
        image = ItemSpriteSheet.TELEPORTER;

        Collections.addAll(itemClass, Generator.Category.WEP_T1.classes);
        Collections.addAll(itemClass, Generator.Category.WEP_T2.classes);
        Collections.addAll(itemClass, Generator.Category.WEP_T3.classes);
        Collections.addAll(itemClass, Generator.Category.WEP_T4.classes);
        Collections.addAll(itemClass, Generator.Category.WEP_T5.classes);
        Collections.addAll(itemClass, Generator.Category.WEP_AL_T3.classes);
        Collections.addAll(itemClass, Generator.Category.WEP_AL_T4.classes);
        Collections.addAll(itemClass, Generator.Category.WEP_AL_T5.classes);
        Collections.addAll(itemClass, Generator.Category.WEP_AL_T6.classes);
        Collections.addAll(itemClass, Generator.Category.WEP_AL_T7.classes);
        Collections.addAll(itemClass, Generator.Category.MIS_T1.classes);
        Collections.addAll(itemClass, Generator.Category.MIS_T2.classes);
        Collections.addAll(itemClass, Generator.Category.MIS_T3.classes);
        Collections.addAll(itemClass, Generator.Category.MIS_T4.classes);
        Collections.addAll(itemClass, Generator.Category.MIS_T5.classes);
        Collections.addAll(itemClass, Generator.Category.SPELLBOOK.classes);
        Collections.addAll(itemClass, Generator.Category.ARMOR.classes);
        Collections.addAll(itemClass, Generator.Category.WAND.classes);
        Collections.addAll(itemClass, Generator.Category.RING.classes);
        Collections.addAll(itemClass, Generator.Category.ARTIFACT.classes);
        Collections.addAll(itemClass, Generator.Category.SEED.classes);
        Collections.addAll(itemClass, Generator.Category.STONE.classes);
        Collections.addAll(itemClass, Generator.Category.FOOD.classes);
        for (Class classes : Generator.Category.POTION.classes){
            itemClass.add(classes);
            itemClass.add(ExoticPotion.regToExo.get(classes));
        }
        for (Class classes : Generator.Category.SCROLL.classes){
            itemClass.add(classes);
            itemClass.add(ExoticScroll.regToExo.get(classes));
        }
        Collections.addAll(itemClass, Honeypot.class, Bomb.class, TengusMask.class, KingsCrown.class, EnergyCrystal.class, Stylus.class, Torch.class, Gold.class, BulletItem.class, BulletBelt.class, Ankh.class, LiquidMetal.class, Pickaxe.class);
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_TELEPORT);
        actions.add(AC_RETURN);
        actions.add(AC_SPAWN);
        actions.add(AC_RANDOMSPAWN);
        actions.add(AC_GETITEM);
        actions.add(AC_MAPPING);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);
        if (action.equals(AC_TELEPORT)) {
            Buff.affect(hero, ElixirOfFeatherFall.FeatherBuff.class, 3f);
            Chasm.heroFall(hero.pos);
            int length = Dungeon.level.length();
            int[] map = Dungeon.level.map;
            boolean[] mapped = Dungeon.level.mapped;
            boolean[] discoverable = Dungeon.level.discoverable;

            for (int i=0; i < length; i++) {

                int terr = map[i];

                if (discoverable[i]) {

                    mapped[i] = true;
                    if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

                        Dungeon.level.discover( i );

                        if (Dungeon.level.heroFOV[i]) {
                            GameScene.discoverTile( i, terr );
                            discover( i );
                        }
                    }
                }
            }
            GameScene.updateFog();
        }
        if (action.equals(AC_RETURN)) {
            InterlevelScene.mode = InterlevelScene.Mode.RETURN;
            InterlevelScene.returnDepth = Math.max(1, (Dungeon.depth - 1));
            InterlevelScene.returnBranch = 0;
            InterlevelScene.returnPos = -2;
            Game.switchScene( InterlevelScene.class );
        }
        if (action.equals(AC_SPAWN)) {
            SummoningTrap trap1 = new SummoningTrap();
            trap1.pos = hero.pos;
            trap1.activate();
            hero.next();
        }
        if (action.equals(AC_RANDOMSPAWN)) {
            DistortionTrap trap2 = new DistortionTrap();
            trap2.pos = hero.pos;
            trap2.activate();
            hero.next();
        }
        if (action.equals(AC_GETITEM)) {

            ShatteredPixelDungeon.scene().addToFront(new WndTextInput(Messages.get(Teleporter.class, "getitem_title"),
                    Messages.get(Teleporter.class, "getitem_desc"),
                    Messages.get(Teleporter.class, "getitem_default_item")+"\n"+
                            Messages.get(Teleporter.class, "getitem_amount")+" \n"+
                            Messages.get(Teleporter.class, "getitem_upgrade")+" \n"+
                            Messages.get(Teleporter.class, "getitem_identify")+" ",
                    100,
                    true,
                    Messages.get(Teleporter.class, "getitem_yes"),
                    Messages.get(Teleporter.class, "getitem_no")){
                @Override
                public void onSelect(boolean positive, String text) {
                    if (positive && text != "") {
                        if (text.trim().equals("help")) {
                            GameScene.show(new WndTitledMessage(
                                    Icons.get(Icons.INFO),
                                    Messages.titleCase(Messages.get(Teleporter.class, "help_title")),
                                    Messages.get(Teleporter.class, "help_desc")));
                        } else if (text.trim().equals("list")) {
                            StringBuilder itemList = new StringBuilder();
                            for (Class classes : itemClass) {
                                itemList.append(Messages.get(classes, "name")).append(", ");
                            }
                            GameScene.show(new WndTitledMessage(
                                    Icons.get(Icons.INFO),
                                    Messages.titleCase(Messages.get(Teleporter.class, "list_title")),
                                    itemList.toString()));
                        } else {
                            //받은 문장을 엔터 단위로 끊는다.
                            String[] strInput = text.split("\n");
                            if (strInput.length < 4) {
                                GLog.w(Messages.get(Teleporter.class, "too_short"));
                                hide();
                                return;
                            }

                            //아이템 이름 결정 메커니즘
                            String itemName = strInput[0]; //1번째 줄에 들어가는 문장(아이템 이름)을 저장

                            Class finalItemClass = null;
                            for (Class classes : itemClass) {
                                if (itemName.equals(Messages.get(classes, "name"))) {
                                    finalItemClass = classes;
                                    break;
                                }
                            }
                            if (finalItemClass == null) { //null check
                                GLog.w(Messages.get(Teleporter.class, "wrong_itemname_1", itemName));
                                hide();
                                return;
                            }

                            Item item = (Item) Reflection.newInstance(finalItemClass);
                            if (item == null) {
                                GLog.w(Messages.get(Teleporter.class, "wrong_itemname_2"));
                                hide();
                                return;
                            }

                            //아이템 수량 결정 메커니즘
                            String amount = strInput[1].replaceAll(Messages.get(Teleporter.class, "getitem_amount"), "").replaceAll(" ", ""); //기본 문장과 공백을 제거
                            int itemAmount = 1;
                            if (!amount.equals("")) {
                                try {
                                    itemAmount = Integer.parseInt(amount);
                                } catch (NumberFormatException e) {
                                    GLog.w(Messages.get(Teleporter.class, "wrong_amount"));
                                    hide();
                                }
                            }
                            if (!item.stackable) {
                                itemAmount = 1;
                            }

                            //아이템 강화수치 결정 메커니즘
                            String upgrade = strInput[2].replaceAll(Messages.get(Teleporter.class, "getitem_upgrade"), "").replaceAll(" ", ""); //기본 문장과 공백을 제거
                            int itemUpgrade = 0;
                            if (!upgrade.equals("")) {
                                try {
                                    itemUpgrade = Integer.parseInt(upgrade);
                                } catch (NumberFormatException e) {
                                    GLog.w(Messages.get(Teleporter.class, "wrong_upgrade"));
                                    hide();
                                }
                            }
                            if (!item.isUpgradable()) {
                                itemUpgrade = 0;
                            }

                            //아이템 감정 여부 결정 메커니즘
                            String identify = strInput[3].replaceAll(Messages.get(Teleporter.class, "getitem_identify"), "").replaceAll(" ", ""); //기본 문장과 공백을 제거
                            boolean isIdentified = Boolean.parseBoolean(identify);

                            //최종 아이템 지급
                            if (isIdentified) {
                                item.quantity(itemAmount).upgrade(itemUpgrade).identify();
                            } else {
                                item.quantity(itemAmount).upgrade(itemUpgrade);
                            }
                            if (!item.doPickUp( hero )) {
                                Dungeon.level.drop(item, hero.pos).sprite.drop();
                            }
                        }
                    } else {
                        hide();
                    }
                }
            });
        }
        if (action.equals(AC_MAPPING)) {
            int length = Dungeon.level.length();
            int[] map = Dungeon.level.map;
            boolean[] mapped = Dungeon.level.mapped;
            boolean[] discoverable = Dungeon.level.discoverable;

            boolean noticed = false;

            for (int i=0; i < length; i++) {

                int terr = map[i];

                if (discoverable[i]) {

                    mapped[i] = true;
                    if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

                        Dungeon.level.discover( i );

                        if (Dungeon.level.heroFOV[i]) {
                            GameScene.discoverTile( i, terr );
                            discover( i );

                            noticed = true;
                        }
                    }
                }
            }
            GameScene.updateFog();

            GLog.i( Messages.get(this, "layout") );
            if (noticed) {
                Sample.INSTANCE.play( Assets.Sounds.SECRET );
            }

            SpellSprite.show( curUser, SpellSprite.MAP );
        }
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

}