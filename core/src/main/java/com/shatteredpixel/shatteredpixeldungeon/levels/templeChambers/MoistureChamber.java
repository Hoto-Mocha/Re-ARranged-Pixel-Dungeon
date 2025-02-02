package com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.StormCloud;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ThunderCloud;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.utils.Point;

public class MoistureChamber extends Chamber {
    {
        isBuildWithStructure = false;
    }

    @Override
    public void build() {
        super.build();

        Painter.fill(level, innerRoom, Terrain.WATER);
        Painter.fill(level, innerRoom, 1, Terrain.BOOKSHELF);
        Painter.fill(level, innerRoom, 3, Terrain.WATER);
        Painter.fill(level, innerRoom, 5, Terrain.BOOKSHELF);
        Painter.fill(level, innerRoom, 7, Terrain.WATER);
        Painter.drawLine(level, center, topDoor, Terrain.WATER);
        Painter.drawLine(level, center, bottomDoor, Terrain.WATER);
        Painter.drawLine(level, center, leftDoor, Terrain.WATER);
        Painter.drawLine(level, center, rightDoor, Terrain.WATER);
        Painter.set(level, center, Terrain.PEDESTAL);

        for (Point p : innerRoom.getPoints()){
            int cell = level.pointToCell(p);
            if (level.map[cell] == Terrain.WATER) {
                //as if gas has been spreading in the room for a while
                Blob.seed(cell, 30, StormCloud.class, level);
                Blob.seed(cell, 12, StormCloudSeed.class, level);
            }
        }
    }

    public static class StormCloudSeed extends Blob {

        @Override
        protected void evolve() {
            int cell;
            ThunderCloud gas = (ThunderCloud) Dungeon.level.blobs.get(ThunderCloud.class);
            for (int i=area.top-1; i <= area.bottom; i++) {
                for (int j = area.left-1; j <= area.right; j++) {
                    cell = j + i* Dungeon.level.width();
                    if (Dungeon.level.insideMap(cell)) {
                        if (Dungeon.level.map[cell] != Terrain.WATER){
                            off[cell] = 0;
                            continue;
                        }

                        off[cell] = cur[cell];
                        volume += off[cell];

                        if (gas == null || gas.volume == 0){
                            GameScene.add(Blob.seed(cell, off[cell], ThunderCloud.class));
                        } else if (gas.cur[cell] <= 9*off[cell]){
                            GameScene.add(Blob.seed(cell, off[cell], ThunderCloud.class));
                        }
                    }
                }
            }
        }

    }
}
