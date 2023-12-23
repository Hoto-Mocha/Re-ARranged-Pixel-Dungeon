package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;

import java.util.ArrayList;

public class WeaponCatalyst extends Item {

    {


        stackable = true;

        defaultAction = AC_APPLY;

        bones = true;
    }

    private static final String AC_APPLY = "APPLY";

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_APPLY );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals(AC_APPLY)) {
            curUser = hero;

            detach(hero.belongings.backpack);
        }
    }


}
