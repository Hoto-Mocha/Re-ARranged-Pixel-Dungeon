package com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow;

import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Blindweed;
import com.shatteredpixel.shatteredpixeldungeon.plants.Firebloom;
import com.shatteredpixel.shatteredpixeldungeon.plants.Icecap;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sorrowmoss;
import com.shatteredpixel.shatteredpixeldungeon.plants.Stormvine;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.CheckBox;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class NaturesBow extends SpiritBow {

    public static final String AC_CHOOSE		= "CHOOSE";

    {
        image = ItemSpriteSheet.NATURE_BOW;
    }

    private void activatePlant(Plant plant, Char defender) {
        plant.pos = defender.pos;
        plant.activate( defender.isAlive() ? defender : null );
    }

    private boolean blindweedAct    = true;
    private boolean fireblossomAct  = true;
    private boolean icecapAct       = true;
    private boolean sorrowmossAct   = true;
    private boolean stormvineAct    = true;

    private static final String BLINDWEED_ACT = "blindweedAct";
    private static final String FIREBLOSSOM_ACT = "fireblossomAct";
    private static final String ICECAP_ACT = "icecapAct";
    private static final String SORROWMOSS_ACT = "sorrowmossAct";
    private static final String STORMVINE_ACT = "stormvineAct";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(BLINDWEED_ACT,   blindweedAct  );
        bundle.put(FIREBLOSSOM_ACT, fireblossomAct);
        bundle.put(ICECAP_ACT,      icecapAct     );
        bundle.put(SORROWMOSS_ACT,  sorrowmossAct );
        bundle.put(STORMVINE_ACT,   stormvineAct  );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        blindweedAct = bundle.getBoolean(BLINDWEED_ACT);
        fireblossomAct = bundle.getBoolean(FIREBLOSSOM_ACT);
        icecapAct = bundle.getBoolean(ICECAP_ACT);
        sorrowmossAct = bundle.getBoolean(SORROWMOSS_ACT);
        stormvineAct = bundle.getBoolean(STORMVINE_ACT);
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_CHOOSE);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_CHOOSE)) {
            choosePlant();
        }
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        int dmg = super.proc(attacker, defender, damage);

        switch (Random.Int(15)) {
            default:
                break;
            case 0:
                Buff.affect(defender, Roots.class, 3f);
                if (!blindweedAct) break;
                activatePlant(new Blindweed(), defender);
                break;
            case 1:
                Buff.affect(defender, Roots.class, 3f);
                if (!fireblossomAct) break;
                activatePlant(new Firebloom(), defender);
                break;
            case 2:
                Buff.affect(defender, Roots.class, 3f);
                if (!icecapAct) break;
                activatePlant(new Icecap(), defender);
                break;
            case 3:
                Buff.affect(defender, Roots.class, 3f);
                if (!sorrowmossAct) break;
                activatePlant(new Sorrowmoss(), defender);
                break;
            case 4:
                Buff.affect(defender, Roots.class, 3f);
                if (!stormvineAct) break;
                activatePlant(new Stormvine(), defender);
                break;
        }
        return dmg;
    }

    @Override
    public SpiritArrow knockArrow(){
        return new NatureArrow();
    }

    public class NatureArrow extends SpiritArrow {
        {
            image = ItemSpriteSheet.ARROW_NATURE;
        }
    }

    public void choosePlant() {
        final int WIDTH_P	    = 150;
        final int BTN_HEIGHT	= 16;
        final float GAP         = 1;

        ShatteredPixelDungeon.scene().addToFront(new Window(){
            RenderedTextBlock title;
            RenderedTextBlock uiDesc;
            CheckBox bilndweed;
            CheckBox fireblossom;
            CheckBox icecap;
            CheckBox sorrowmoss;
            CheckBox stormvine;


            {
                title = PixelScene.renderTextBlock(Messages.get(NaturesBow.class, "ui_title"), 9);
                title.hardlight(TITLE_COLOR);
                add(title);

                //설명 문구
                uiDesc = PixelScene.renderTextBlock(Messages.get(NaturesBow.class, "ui_desc"), 6);
                add(uiDesc);

                //체크박스 버튼
                bilndweed = new CheckBox(Messages.get(Blindweed.class, "name")) {
                    @Override
                    protected void onClick() {
                        checked(switchPlantAct(0));
                    }
                };
                bilndweed.checked(blindweedAct);
                add(bilndweed);

                fireblossom = new CheckBox(Messages.get(Firebloom.class, "name")) {
                    @Override
                    protected void onClick() {
                        checked(switchPlantAct(1));
                    }
                };
                fireblossom.checked(fireblossomAct);
                add(fireblossom);

                icecap = new CheckBox(Messages.get(Icecap.class, "name")) {
                    @Override
                    protected void onClick() {
                        checked(switchPlantAct(2));
                    }
                };
                icecap.checked(icecapAct);
                add(icecap);

                sorrowmoss = new CheckBox(Messages.get(Sorrowmoss.class, "name")) {
                    @Override
                    protected void onClick() {
                        checked(switchPlantAct(3));
                    }
                };
                sorrowmoss.checked(sorrowmossAct);
                add(sorrowmoss);

                stormvine = new CheckBox(Messages.get(Stormvine.class, "name")) {
                    @Override
                    protected void onClick() {
                        checked(switchPlantAct(4));
                    }
                };
                stormvine.checked(stormvineAct);
                add(stormvine);

                //layout
                resize(WIDTH_P, 0);
                int btnWidth = width;
                title.setPos((width - title.width())/2, GAP);

                uiDesc.setPos(0, title.bottom()+2*GAP);
                uiDesc.maxWidth(btnWidth);

                bilndweed.setRect(0, uiDesc.bottom()+2*GAP, btnWidth, BTN_HEIGHT);
                fireblossom.setRect(0, bilndweed.bottom()+2*GAP, btnWidth, BTN_HEIGHT);
                icecap.setRect(0, fireblossom.bottom()+2*GAP, btnWidth, BTN_HEIGHT);
                sorrowmoss.setRect(0, icecap.bottom()+2*GAP, btnWidth, BTN_HEIGHT);
                stormvine.setRect(0, sorrowmoss.bottom()+2*GAP, btnWidth, BTN_HEIGHT);

                resize(WIDTH_P, (int)stormvine.bottom());
            }
        });
    }

    public boolean switchPlantAct(int index) {
        switch (index) {
            default: case 0:
                blindweedAct = !blindweedAct;
                return blindweedAct;
            case 1:
                fireblossomAct = !fireblossomAct;
                return fireblossomAct;
            case 2:
                icecapAct = !icecapAct;
                return icecapAct;
            case 3:
                sorrowmossAct = !sorrowmossAct;
                return sorrowmossAct;
            case 4:
                stormvineAct = !stormvineAct;
                return stormvineAct;
        }
    }

}
