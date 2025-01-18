package com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Piranha;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;

public class PiranhaPoolChamber extends Chamber {

    {
        isBuildWithStructure = false;
    }

    @Override
    public void build() {
        super.build();

        Painter.fill(level, innerRoom, 1, Terrain.EMPTY_SP);
        Painter.fill(level, innerRoom, 2, Terrain.WATER);
        for (int i = 0; i < 4; i++) {
            Painter.drawLine(level, doorPoint(i), center, Terrain.EMPTY_SP);
        }

        Painter.set(level, center, Terrain.PEDESTAL);

        Piranha piranha1 = Piranha.random();
        piranha1.pos = level.pointToCell(new Point(center.x-2, center.y-2));
        level.mobs.add(piranha1);

        Piranha piranha2 = Piranha.random();
        piranha2.pos = level.pointToCell(new Point(center.x+2, center.y-2));
        level.mobs.add(piranha2);

        Piranha piranha3 = Piranha.random();
        piranha3.pos = level.pointToCell(new Point(center.x-2, center.y+2));
        level.mobs.add(piranha3);

        Piranha piranha4 = Piranha.random();
        piranha4.pos = level.pointToCell(new Point(center.x+2, center.y+2));
        level.mobs.add(piranha4);
    }
}