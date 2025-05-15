package com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfBlink;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FlashingTrap;
import com.watabou.utils.Reflection;

public class WarpStoneChamber extends Chamber {
    {
        isBuildWithStructure = true;
    }

    @Override
    public int[] roomStructure() {
        return new int[] {
                //this is made by sandbox pd
                25, 26, 25, 25, 11, 25, 25, 26, 14, 25, 25, 25, 25, 25, 25, 25, 25, 25, 18, 18, 18, 18, 18, 18, 18, 11, 25, 18, 18, 26, 18, 18, 18, 26, 25, 18, 18, 18, 25, 25, 25, 25, 14, 25, 18, 18, 18, 11, 18, 18, 25, 25, 18, 11, 18, 18, 18, 25, 14, 25, 25, 18, 18, 18, 18, 18, 18, 25, 25, 26, 18, 18, 25, 11, 25, 26, 18, 25, 18, 18, 25, 18, 25, 18, 11, 25, 18, 18, 18, 18, 18, 18, 18, 18, 25, 18, 18, 11, 18, 25, 18, 25, 25, 18, 18, 18, 18, 18, 18, 18, 18, 25, 18, 18, 25, 25, 25, 18, 25, 25, 25, 25, 25, 25, 25, 25, 25, 14, 25, 18, 18, 26, 14, 25, 18, 25, 14, 11, 14, 25, 18, 18, 18, 14, 11, 14, 18, 18, 18, 25, 14, 11, 14, 26, 18, 25, 14, 26, 18, 18, 25, 14, 25, 25, 25, 25, 25, 25, 25, 25, 25, 18, 25, 25, 25, 18, 18, 25, 18, 18, 18, 18, 18, 18, 18, 18, 25, 25, 18, 25, 18, 11, 18, 18, 25, 18, 18, 18, 18, 18, 18, 18, 18, 25, 11, 18, 25, 18, 25, 18, 18, 25, 18, 26, 25, 11, 25, 18, 18, 26, 25, 25, 18, 18, 18, 18, 18, 18, 25, 25, 14, 25, 18, 18, 18, 11, 18, 25, 25, 18, 18, 11, 18, 18, 18, 25, 14, 25, 25, 25, 25, 18, 18, 18, 25, 26, 18, 18, 18, 26, 18, 18, 25, 11, 18, 18, 18, 18, 18, 18, 18, 25, 25, 25, 25, 25, 25, 25, 25, 25, 14, 26, 25, 25, 11, 25, 25, 26, 25
        };
    }

    @Override
    public void build() {
        super.build();

        int[][] offsets = {
                {-3, -4}, {-6, -5}, {-4, -8},
                {4, -3}, {5, -6}, {8, -4},
                {3, 4}, {6, 5}, {4, 8},
                {-4, 3}, {-5, 6}, {-8, 4},
                {0, 0}, {0, -7}, {7, 0}, {0, 7}, {-7, 0},
        };

        for (int pos : customOffsetArray(offsets)) {
            level.drop(new StoneOfBlink(), pos);
        }


        for (int cell : innerRoomPos()) {
            if (level.map[cell] == Terrain.TRAP) {
                level.setTrap(Reflection.newInstance(FlashingTrap.class).reveal(), cell);
            }
        }

    }
}