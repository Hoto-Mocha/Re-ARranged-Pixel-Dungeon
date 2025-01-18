package com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers;

import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;

public class TestChamber extends Chamber {

    {
        isBuildWithStructure = true;
    }

    @Override
    public int[] roomStructure() {
        int n = -1;
        int E = Terrain.EMPTY;
        int S = Terrain.STATUE;
        return new int[] {
                E,S,E,S,E,S,E,
                S,E,S,E,S,E,S,
                E,S,E,S,E,S,E,
                S,E,S,n,S,E,S,
                E,S,E,S,E,S,E,
                S,E,S,E,S,E,S,
                E,S,E,S,E,S,E
        };
    }

    @Override
    public void build() {
        super.build();
    }
}