package com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers;

import static com.shatteredpixel.shatteredpixeldungeon.levels.TempleNewLevel.CHAMBER_HEIGHT;
import static com.shatteredpixel.shatteredpixeldungeon.levels.TempleNewLevel.CHAMBER_WIDTH;

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;

import java.util.ArrayList;

public class Chamber {
    //these variable doesn't need to be saved(with storeInBundle()), because these are only used when generating the level
    public Level level;
    public Point center;
    public Rect innerRoom;
    private Rect outerRoom;
    public Point topDoor;
    public Point bottomDoor;
    public Point leftDoor;
    public Point rightDoor;
    private ArrayList<Integer> innerRoomPos = new ArrayList<>();
    public boolean isBuildWithStructure = false; //make this false if you want to make room with Painter

    public void set(Level level, int left, int top, int right, int bottom, Point center) {
        this.level      = level;
        this.center     = center;
        this.outerRoom  = new Rect( //this includes the wall
                left,
                top,
                right,
                bottom);
        this.innerRoom = new Rect( //this excludes the wall
                left+1,
                top+1,
                right-1,
                bottom-1);

        Point leftTopPoint = new Point(center.x-(CHAMBER_WIDTH/2-1), center.y-(CHAMBER_HEIGHT/2));
        for (int y = 0; y < CHAMBER_HEIGHT; y++) {
            for (int x = 0; x < CHAMBER_WIDTH; x++) {
                Point p = new Point((leftTopPoint.x-1)+x, leftTopPoint.y+y);
                innerRoomPos.add(this.level.pointToCell(p));
            }
        }

        this.topDoor    = new Point(center.x, center.y - CHAMBER_HEIGHT/2);
        this.bottomDoor = new Point(center.x, center.y + CHAMBER_HEIGHT/2);
        this.leftDoor   = new Point(center.x - CHAMBER_WIDTH/2, center.y);
        this.rightDoor  = new Point(center.x + CHAMBER_WIDTH/2, center.y);
    }

    public int[] roomStructure() {
        return new int[] {}; //must be overridden if isBuildWithStructure is true
    }

    public void build() {
        Painter.fill(level, outerRoom, Terrain.WALL);
        Painter.fill(level, outerRoom, 1, Terrain.EMPTY);
        Painter.set(level, center, Terrain.PEDESTAL);

            /*
                if the build does not work well, check the roomStructure() is correctly overridden
                if roomStructure() is too short, it will make uncompleted room
                if roomStructure() is too long, it will throw ArrayIndexOutOfBoundsException
            */
        if (isBuildWithStructure) {
            int index = 0;
            try {
                for (int pos : innerRoomPos) {
                    if (roomStructure()[index] != -1) level.map[pos] = roomStructure()[index];
                    index++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Integer> innerRoomPos() {
        return this.innerRoomPos;
    }

    //returns random pos in the room
    public ArrayList<Integer> randomRoomPos(int num) {
        ArrayList<Integer> result = new ArrayList<>();
        while (result.size() < num) {
            int randomResult = Random.element(innerRoomPos);
            if (!result.contains(randomResult)) {
                result.add(randomResult);
            }
        }

        return result;
    }

    //returns random pos in the room
    public ArrayList<Integer> randomRoomPos(int num, ArrayList<Integer> exception) {
        ArrayList<Integer> result = new ArrayList<>();
        while (result.size() < num) {
            int randomResult = Random.element(innerRoomPos);
            if (!result.contains(randomResult) && !exception.contains(randomResult)) {
                result.add(randomResult);
            }
        }

        return result;
    }

    //This returns the position of the array that is separated by the x, y coordinates from the center.
    public ArrayList<Integer> customOffsetArray(int[][] offsets) {
        ArrayList<Integer> resultArray = new ArrayList<>();

        for (int[] offset : offsets) {
            resultArray.add(level.pointToCell(new Point(center.x + offset[0], center.y + offset[1])));
        }

        return resultArray;
    }

    public Point doorPoint(int direction) {
        //direction: 0=top, 1=left, 2=bottom, 3=right, other=top (counterclockwise)
        switch (direction) {
            default: case 0:
                return new Point(center.x, center.y-CHAMBER_HEIGHT/2);
            case 1:
                return new Point(center.x-CHAMBER_WIDTH/2, center.y);
            case 2:
                return new Point(center.x, center.y+CHAMBER_HEIGHT/2);
            case 3:
                return new Point(center.x+CHAMBER_WIDTH/2, center.y);
        }
    }
}
