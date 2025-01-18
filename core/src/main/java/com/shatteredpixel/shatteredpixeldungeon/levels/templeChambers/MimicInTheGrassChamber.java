package com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.TempleEbonyMimic;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;

import java.util.ArrayList;

public class MimicInTheGrassChamber extends Chamber {
        /*
            this room contains full of furrowed grass and 12 of hidden ebony mimic
        */

    {
        isBuildWithStructure = false;
    }

    @Override
    public void build() {
        super.build();

        Painter.fill(level, innerRoom, Terrain.FURROWED_GRASS);
        Painter.set(level, center, Terrain.PEDESTAL);

        ArrayList<Integer> randomPos = randomRoomPos(32);
        for (int pos : randomPos) {
            level.mobs.add(Mimic.spawnAt(pos, TempleEbonyMimic.class, false));
        }
    }
}