package com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ConfusionGas;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special.ToxicGasRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;

import java.util.ArrayList;

public class ConfusionChamber extends Chamber {
    {
        isBuildWithStructure = true;
    }

    @Override
    public int[] roomStructure() {
        return new int[] {
                1, 1, 1, 1, 1, 1, 1, 25, 1, 25, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 5, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 25, 25, 25, 1, 1, 1, 1, 1, 1, 1, 25, 4, 1, 1, 1, 1, 25, 14, 14, 14, 25, 1, 1, 1, 1, 4, 25, 1, 5, 1, 1, 1, 1, 25, 14, 11, 14, 25, 1, 1, 1, 1, 5, 1, 25, 4, 1, 1, 1, 1, 25, 14, 14, 14, 25, 1, 1, 1, 1, 4, 25, 1, 1, 1, 1, 1, 1, 1, 25, 25, 25, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 5, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 25, 1, 25, 1, 1, 1, 1, 1, 1, 1
        };
    }

    @Override
    public void build() {
        super.build();

        int[][] offsets = {
                //top
                {-1, -8}, {0, -8}, {1, -8},
                {-1, -7}, {0, -7}, {1, -7},
                //bottom
                {-1, 7}, {0, 7}, {1, 7},
                {-1, 8}, {0, 8}, {1, 8},
                //left
                {-8, -1}, {-8, 0}, {-8, 1},
                {-7, -1}, {-7, 0}, {-7, 1},
                //right
                {7, -1}, {7, 0}, {7, 1},
                {8, -1}, {8, 0}, {8, 1},
                //center
                            {-1, -2},   {0, -2},    {1, -2},
                {-2, -1},   {-1, -1},   {0, -1},    {1, -1},    {2, -1},
                {-2, 0},    {-1, 0},    {0, 0},     {1, 0},     {2, 0},
                {-2, 1},    {-1, 1},    {0, 1},     {1, 1},     {2, 1},
                            {-1, 2},    {0, 2},     {1, 2},
        };

        ArrayList<Integer> exception = new ArrayList<>(customOffsetArray(offsets));

        final int TRAP_NUM = 60;
        for (int pos : randomRoomPos(TRAP_NUM, exception)) {
            level.setTrap(new ConfusionVent().reveal(), pos);
            Blob.seed(pos, 20, ConfusionGasSeed.class, level);
            Painter.set(level, pos, Terrain.INACTIVE_TRAP);
        }
    }

    public static class ConfusionGasSeed extends Blob {

        @Override
        protected void evolve() {
            int cell;
            ConfusionGas gas = (ConfusionGas) Dungeon.level.blobs.get(ConfusionGas.class);
            for (int i=area.top-1; i <= area.bottom; i++) {
                for (int j = area.left-1; j <= area.right; j++) {
                    cell = j + i* Dungeon.level.width();
                    if (Dungeon.level.insideMap(cell)) {
                        if (Dungeon.level.map[cell] != Terrain.INACTIVE_TRAP){
                            off[cell] = 0;
                            continue;
                        }

                        off[cell] = cur[cell];
                        volume += off[cell];

                        if (gas == null || gas.volume == 0){
                            GameScene.add(Blob.seed(cell, off[cell], ConfusionGas.class));
                        } else if (gas.cur[cell] <= 9*off[cell]){
                            GameScene.add(Blob.seed(cell, off[cell], ConfusionGas.class));
                        }
                    }
                }
            }
        }

    }

    public static class ConfusionVent extends Trap {

        {
            color = BLACK;
            shape = GRILL;

            canBeHidden = false;
            active = false;
        }

        @Override
        public void activate() {
            //does nothing, this trap is just decoration and is always deactivated
        }

    }
}
