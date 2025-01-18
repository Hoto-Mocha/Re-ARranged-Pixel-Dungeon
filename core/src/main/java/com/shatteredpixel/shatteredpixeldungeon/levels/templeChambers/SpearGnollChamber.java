package com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.GnollGuard;

public class SpearGnollChamber extends Chamber {
    {
        isBuildWithStructure = true;
    }

    @Override
    public int[] roomStructure() {
        return new int[] {
                14, 14, 14, 14, 14, 4, 14, 5, 14, 5, 14, 14, 14, 14, 14, 14, 14, 14, 0, 0, 0, 14, 4, 14, 4, 14, 4, 0, 0, 0, 0, 0, 0, 14, 14, 0, 11, 0, 14, 4, 0, 4, 26, 4, 11, 0, 11, 0, 11, 0, 14, 14, 0, 0, 0, 14, 4, 27, 4, 14, 4, 0, 0, 0, 0, 0, 0, 14, 14, 0, 11, 0, 14, 4, 14, 5, 14, 5, 14, 14, 14, 14, 14, 14, 14, 14, 0, 0, 0, 14, 4, 4, 4, 5, 4, 4, 4, 4, 4, 4, 4, 4, 14, 0, 11, 0, 14, 4, 27, 27, 15, 27, 27, 4, 14, 27, 0, 14, 14, 5, 4, 4, 4, 5, 4, 27, 15, 15, 15, 27, 4, 5, 4, 4, 4, 5, 14, 14, 26, 14, 14, 5, 15, 15, 11, 15, 15, 5, 14, 14, 26, 14, 14, 5, 4, 4, 4, 5, 4, 27, 15, 15, 15, 27, 4, 5, 4, 4, 4, 5, 14, 14, 0, 27, 14, 4, 27, 27, 15, 27, 27, 4, 14, 0, 11, 0, 14, 4, 4, 4, 4, 4, 4, 4, 4, 5, 4, 4, 4, 14, 0, 0, 0, 14, 14, 14, 14, 14, 14, 14, 14, 5, 14, 5, 14, 4, 14, 0, 11, 0, 14, 14, 0, 0, 0, 0, 0, 0, 4, 14, 4, 27, 4, 14, 0, 0, 0, 14, 14, 0, 11, 0, 11, 0, 11, 4, 26, 4, 0, 4, 14, 0, 11, 0, 14, 14, 0, 0, 0, 0, 0, 0, 4, 14, 4, 14, 4, 14, 0, 0, 0, 14, 14, 14, 14, 14, 14, 14, 14, 5, 14, 5, 14, 4, 14, 14, 14, 14, 14
        };
    }

    @Override
    public void build() {
        super.build();

        int[][] offsets = {
                {-6, -2}, {-6, -4}, {-6, -6},
                {2, -6}, {4, -6}, {6, -6},
                {6, 2}, {6, 4}, {6, 6},
                {-2, 6}, {-4, 6}, {-6, 6},
        };

        for (int pos : customOffsetArray(offsets)) {
            GnollGuard gnollGuard = new GnollGuard();
            gnollGuard.pos = pos;
            level.mobs.add(gnollGuard);
        }
    }
}