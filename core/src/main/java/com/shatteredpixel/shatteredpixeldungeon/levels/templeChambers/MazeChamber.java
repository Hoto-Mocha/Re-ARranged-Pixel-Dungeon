package com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers;

import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class MazeChamber extends Chamber {
    {
        isBuildWithStructure = false;
    }

    @Override
    public void build() {
        super.build();

        Painter.fill(level, innerRoom, Terrain.BOOKSHELF);
        Painter.fill(level, innerRoom, 1, Terrain.EMPTY_SP);
        for (int i = 2; i <= 7; i++){
            Painter.fill(level, innerRoom, i, (i % 2 == 0) ? Terrain.WALL : Terrain.FURROWED_GRASS);
            if (i % 2 == 0) {
                Painter.set(level, randomBridge(i), Terrain.DOOR);
            }
        }
        Painter.set(level, center, Terrain.PEDESTAL);

        int[][] offsets = {
                {-5, -8}, {-1, -8}, {0, -8}, {1, -8}, {5, -8}, //top edge
                {-8, -5}, {-8, -1}, {-8, 0}, {-8, 1}, {-8, 5}, //left edge
                {-5, 8}, {-1, 8}, {0, 8}, {1, 8}, {5, 8}, //bottom edge
                {8, -5}, {8, -1}, {8, 0}, {8, 1}, {8, 5}, //right edge
        };

        for (int pos : customOffsetArray(offsets)) {
            Painter.set(level, pos, Terrain.EMPTY_SP);
        }
    }

    public Point randomBridge(int m) {
        int x;
        int y;
        int n = m+1; //모서리에 다리가 생기는 것을 방지하기 위함
        int left = innerRoom.left;
        int top = innerRoom.top;
        int right = innerRoom.right-1;
        int bottom = innerRoom.bottom-1;
        switch (Random.Int(4)) {
            case 0: default:
                x = left + m;
                y = Random.IntRange( top + n, bottom - n );
                break;
            case 1:
                x = right - m;
                y = Random.IntRange( top + n, bottom - n );
                break;
            case 2:
                x = Random.IntRange( left + n, right - n );
                y = top + m;
                break;
            case 3:
                x = Random.IntRange( left + n, right - n );
                y = bottom - m;
                break;
        }

        return new Point(x, y);
    }
}