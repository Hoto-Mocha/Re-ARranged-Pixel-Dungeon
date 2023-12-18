package com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.plants.Blindweed;
import com.shatteredpixel.shatteredpixeldungeon.plants.Firebloom;
import com.shatteredpixel.shatteredpixeldungeon.plants.Icecap;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sorrowmoss;
import com.shatteredpixel.shatteredpixeldungeon.plants.Stormvine;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

public class NaturesBow extends SpiritBow {

    {
        image = ItemSpriteSheet.NATURE_BOW;
    }

    private static Class[] harmfulPlants = new Class[]{
            Blindweed.class, Firebloom.class, Icecap.class, Sorrowmoss.class,  Stormvine.class
    };

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        int dmg = super.proc(attacker, defender, damage);

        if (Random.Int(3) == 0) {
            Plant plant = (Plant) Reflection.newInstance(Random.element(harmfulPlants));
            plant.pos = defender.pos;
            plant.activate( defender.isAlive() ? defender : null );
            Buff.affect(defender, Roots.class, 3f);
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

}
