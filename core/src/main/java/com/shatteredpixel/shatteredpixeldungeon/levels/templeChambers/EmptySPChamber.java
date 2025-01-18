package com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers;

import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;

public class EmptySPChamber extends Chamber {

    {
        isBuildWithStructure = false;
    }

    @Override
    public void build() {
        super.build();
        Painter.fill(level, innerRoom, Terrain.EMPTY_SP);
        Painter.set(level, center, Terrain.PEDESTAL);
    }
}