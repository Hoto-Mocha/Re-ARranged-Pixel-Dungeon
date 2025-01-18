package com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers;

import static com.shatteredpixel.shatteredpixeldungeon.levels.TempleNewLevel.CHAMBER_HEIGHT;
import static com.shatteredpixel.shatteredpixeldungeon.levels.TempleNewLevel.CHAMBER_WIDTH;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.CrystalKey;
import com.shatteredpixel.shatteredpixeldungeon.levels.TempleLastLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special.MagicalFireRoom;
import com.watabou.utils.Point;

import java.util.ArrayList;

public class SentryMazeChamber extends Chamber {

    {
        isBuildWithStructure = true;
    }

    @Override
    public int[] roomStructure() {
        return new int[] {
                //this is made by sandbox pd
                1, 1, 4, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 4, 1, 1, 1, 1, 4, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 13, 1, 1, 4, 0, 4, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 4, 4, 13, 0, 1, 1, 25, 0, 0, 1, 0, 1, 0, 1, 0, 0, 25, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 14, 14, 14, 14, 14, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 14, 11, 31, 11, 14, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 14, 31, 11, 31, 14, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 14, 11, 31, 11, 14, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 14, 14, 14, 14, 14, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 14, 0, 14, 14, 14, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 14, 0, 14, 0, 14, 14, 0, 0, 0, 0, 25, 1, 1, 1, 0, 1, 0, 14, 0, 14, 26, 0, 14, 0, 4, 4, 4, 1, 1, 0, 0, 0, 1, 0, 14, 14, 14, 0, 4, 14, 4, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 14, 0, 0, 4, 1, 1, 1, 1, 4, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 4, 1, 1
        };
    }

    @Override
    public void build() {
        super.build();

        int eternalFireCell = level.pointToCell(new Point(center.x-6, center.y+7));
        int cell1 = level.pointToCell(new Point(center.x-CHAMBER_WIDTH/2, center.y+CHAMBER_HEIGHT/2));
        int cell2 = level.pointToCell(new Point(center.x+CHAMBER_WIDTH/2, center.y+CHAMBER_HEIGHT/2));
        int cell3 = level.pointToCell(new Point(center.x+CHAMBER_WIDTH/2, center.y-CHAMBER_HEIGHT/2));
        int cell4 = level.pointToCell(new Point(center.x-CHAMBER_WIDTH/2, center.y-CHAMBER_HEIGHT/2));

        Blob.seed(eternalFireCell, 1, MagicalFireRoom.EternalFire.class, level);

        level.drop(new CrystalKey(Dungeon.depth), cell1).type = Heap.Type.CHEST;
        level.drop(new CrystalKey(Dungeon.depth), cell2).type = Heap.Type.CHEST;
        level.drop(new CrystalKey(Dungeon.depth), cell3).type = Heap.Type.CHEST;
        level.drop(new CrystalKey(Dungeon.depth), cell4).type = Heap.Type.CHEST;

        ArrayList<Point> sentryPos = new ArrayList<>();
        sentryPos.add(new Point(center.x-1, center.y-1));
        sentryPos.add(new Point(center.x+1, center.y-1));
        sentryPos.add(new Point(center.x-1, center.y+1));
        sentryPos.add(new Point(center.x+1, center.y+1));

        int dangerDist = 10;
        for (Point p : sentryPos) {
            int sentryCell = level.pointToCell(p);
            TempleLastLevel.Sentry sentry = new TempleLastLevel.Sentry();
            sentry.pos = sentryCell;
            sentry.initialChargeDelay = sentry.curChargeDelay = dangerDist / 3f + 0.1f;
            level.mobs.add( sentry );
        }
    }
}