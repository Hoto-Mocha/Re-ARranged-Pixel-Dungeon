package com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers;

import static com.shatteredpixel.shatteredpixeldungeon.levels.TempleNewLevel.CHAMBER_HEIGHT;
import static com.shatteredpixel.shatteredpixeldungeon.levels.TempleNewLevel.CHAMBER_WIDTH;

import com.shatteredpixel.shatteredpixeldungeon.levels.TempleLastLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.watabou.utils.Point;

import java.util.ArrayList;

public class SentryChamber extends Chamber {

    {
        isBuildWithStructure = true;
    }

    @Override
    public int[] roomStructure() {
        int n = -1;
        int F = Terrain.EMPTY;
        int W = Terrain.WALL;
        int E = Terrain.EMPTY_SP;
        int S = Terrain.STATUE_SP;
        int P = Terrain.PEDESTAL;
        int M = Terrain.EMBERS;
        return new int[] {
                W, W, W, W, P, P, P, W, F, W, P, P, P, W, W, W, W,
                W, W, W, W, S, S, S, W, F, W, S, S, S, W, W, W, W,
                W, W, E, E, E, E, E, E, E, E, E, E, E, E, E, W, W,
                W, W, E, E, E, E, E, E, E, E, E, E, E, E, E, W, W,
                P, S, E, E, E, E, E, E, E, E, E, E, E, E, E, S, P,
                P, S, E, E, E, E, E, S, S, S, E, E, E, E, E, S, P,
                P, S, E, E, E, E, E, E, E, E, E, E, E, E, E, S, P,
                W, W, E, E, E, S, E, M, M, M, E, S, E, E, E, W, W,
                F, F, E, E, E, S, E, M, P, M, E, S, E, E, E, F, F,
                W, W, E, E, E, S, E, M, M, M, E, S, E, E, E, W, W,
                P, S, E, E, E, E, E, E, E, E, E, E, E, E, E, S, P,
                P, S, E, E, E, E, E, S, S, S, E, E, E, E, E, S, P,
                P, S, E, E, E, E, E, E, E, E, E, E, E, E, E, S, P,
                W, W, E, E, E, E, E, E, E, E, E, E, E, E, E, W, W,
                W, W, E, E, E, E, E, E, E, E, E, E, E, E, E, W, W,
                W, W, W, W, S, S, S, W, F, W, S, S, S, W, W, W, W,
                W, W, W, W, P, P, P, W, F, W, P, P, P, W, W, W, W,
        };
    }

    @Override
    public void build() {
        super.build();
        ArrayList<Point> sentryPoint = new ArrayList<>();

        int[] offsets = {-4, -3, -2, 2, 3, 4};
        for (int offset : offsets) {
            // 상단 및 하단 센트리 위치
            sentryPoint.add(new Point(center.x + offset, center.y - CHAMBER_HEIGHT / 2));
            sentryPoint.add(new Point(center.x + offset, center.y + CHAMBER_HEIGHT / 2));

            // 좌측 및 우측 센트리 위치
            sentryPoint.add(new Point(center.x - CHAMBER_WIDTH / 2, center.y + offset));
            sentryPoint.add(new Point(center.x + CHAMBER_WIDTH / 2, center.y + offset));
        }

        for (Point p : sentryPoint) {
            int sentryCell = level.pointToCell(p);
            TempleLastLevel.Sentry sentry = new TempleLastLevel.Sentry();
            sentry.pos = sentryCell;
            sentry.initialChargeDelay = sentry.curChargeDelay = 3f + 0.1f;
            level.mobs.add( sentry );
        }

    }
}