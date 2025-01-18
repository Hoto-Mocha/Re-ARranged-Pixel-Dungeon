package com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers;

import static com.shatteredpixel.shatteredpixeldungeon.levels.TempleNewLevel.CHAMBER_HEIGHT;
import static com.shatteredpixel.shatteredpixeldungeon.levels.TempleNewLevel.CHAMBER_WIDTH;

import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ExplosiveTrap;
import com.watabou.utils.Point;

import java.util.ArrayList;

public class MineFieldChamber extends Chamber {

    {
        isBuildWithStructure = false;
    }

    @Override
    public void build() {
        super.build();

        ArrayList<Point> floorPoint = new ArrayList<>();

        int[] offsets = {-1, 0, 1};
        for (int offset : offsets) {
            floorPoint.add(new Point(center.x + offset, center.y - CHAMBER_HEIGHT / 2));
            floorPoint.add(new Point(center.x + offset, center.y + CHAMBER_HEIGHT / 2));
            floorPoint.add(new Point(center.x - CHAMBER_WIDTH / 2, center.y + offset));
            floorPoint.add(new Point(center.x + CHAMBER_WIDTH / 2, center.y + offset));
        }

        for (Point p : floorPoint) {
            Painter.set(level, p, Terrain.EMPTY_SP);
        }

        ArrayList<Integer> emberCellArray = randomRoomPos(100);
        for (int emberCell : emberCellArray) {
            if (!floorPoint.contains(level.cellToPoint(emberCell)) && emberCell != level.pointToCell(center)) {
                Painter.set(level, emberCell, Terrain.EMBERS);
            }
        }

        for (int trapCell : randomRoomPos(100)) {
            if (!floorPoint.contains(level.cellToPoint(trapCell)) && !emberCellArray.contains(trapCell) && trapCell != level.pointToCell(center)) {
                Painter.set(level, trapCell, Terrain.SECRET_TRAP);
                level.setTrap(new ExplosiveTrap().hide(), trapCell);
            }
        }
    }
}