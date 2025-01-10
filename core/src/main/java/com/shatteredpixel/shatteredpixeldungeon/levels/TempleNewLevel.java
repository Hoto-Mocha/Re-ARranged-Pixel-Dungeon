package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.changer.OldAmulet;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;

import java.util.ArrayList;

public class TempleNewLevel extends Level {

    {
        color1 = 0x64bb4c;
        color2 = 0x569545;
    }

    private static final int CHAMBER_X_NUM = 5; // chamber's horizontal number, at least 1
    private static final int CHAMBER_Y_NUM = 5; // chamber's vertical number, at least 1
    private static final int CHAMBER_WIDTH = 7; // chamber's horizontal length(excluding wall), at least 1, and must be odd
    private static final int CHAMBER_HEIGHT = 7; // chamber's vertical length(excluding wall), at least 1, and must be odd
    private static final int CHAMBER_FIRST_X = 0; //cannot be changed
    private static final int CHAMBER_FIRST_Y = 2 /* <- this means how far the chamber is from the top pedestal*/ +4;
    private static final int WIDTH = 1+CHAMBER_X_NUM*(CHAMBER_WIDTH+1); //cannot be changed
    private static final int HEIGHT = 2 /* <- this means how far the chamber is from the bottom entrance*/ +12+CHAMBER_Y_NUM*(CHAMBER_HEIGHT+1);
    Rect rect = new Rect(0, 0, WIDTH, HEIGHT);

    private int entranceCell() {
        return (int)(WIDTH/2f)+WIDTH*(HEIGHT-4);
    }

    private Point entrancePoint() {
        return this.cellToPoint(entranceCell());
    }

    private int amuletCell() {
        return (int)(WIDTH/2f)+WIDTH*(3);
    }

    private Point amuletPoint() {
        return this.cellToPoint(amuletCell());
    }

    private int keyCell() {
        return entranceCell()-WIDTH*2;
    }

    private Point keyPoint() {
        return this.cellToPoint(keyCell());
    }

    private int centerCell() {
        return (int)(WIDTH/2f)+WIDTH*(int)(WIDTH/2f);
    }

    private Point centerPoint() {
        return this.cellToPoint(centerCell());
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_TEMPLE;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_TEMPLE;
    }

    @Override
    protected boolean build() {
        setSize(WIDTH, HEIGHT);

        transitions.add(new LevelTransition(this,
                entranceCell(),
                LevelTransition.Type.BRANCH_ENTRANCE,
                Dungeon.depth,
                0,
                LevelTransition.Type.BRANCH_EXIT));

        buildLevel();
        return true;
    }

    private void buildLevel() {
        Painter.fill(this, rect, Terrain.WALL);
        Painter.fill(this, rect, 1, Terrain.EMPTY);

        Painter.set(this, amuletPoint(), Terrain.PEDESTAL);

        Painter.set(this, keyPoint(), Terrain.PEDESTAL);

        //chamber build logic
        for (int x = 0; x < CHAMBER_X_NUM; x++) {                   //chamber's x coordinate. 0~CHAMBER_X_NUM
            for (int y = 0; y < CHAMBER_Y_NUM; y++) {               //chamber's y coordinate. 0~CHAMBER_Y_NUM
                int left = CHAMBER_FIRST_X + x*(CHAMBER_WIDTH+1);   //chamber's actual left pos. chamber includes this pos
                int right = left + CHAMBER_WIDTH+2;                 //chamber's actual right pos. chamber doesn't include this pos
                int top = CHAMBER_FIRST_Y + y*(CHAMBER_HEIGHT+1);   //chamber's actual top pos. chamber includes this pos
                int bottom = top + CHAMBER_HEIGHT+2;                //chamber's actual right pos. chamber doesn't include this pos
                Point center = new Point(left + Math.round(CHAMBER_WIDTH/2f), top + Math.round(CHAMBER_HEIGHT/2f)); //chamber's center point.
                //makes default empty room
                Chamber chamber = new Chamber(
                        this,
                        left,
                        top,
                        right,
                        bottom,
                        center);
                chamber.build();
                //fills room for other type of room
                TestChamber testChamber = new TestChamber(
                        this,
                        left,
                        top,
                        right,
                        bottom,
                        center);
                testChamber.build();

                //door placing logic
                int doorX;
                //horizontally placing doors
                doorX = center.x-Math.round(CHAMBER_WIDTH/2f);
                if (doorX != 0 && doorX != WIDTH-1) { //doesn't place a door on the edge of map
                    Painter.set(this, doorX, center.y, Terrain.LOCKED_DOOR);
                }
                doorX = center.x+Math.round(CHAMBER_WIDTH/2f);
                if (doorX != 0 && doorX != WIDTH-1) { //doesn't place a door on the edge of map
                    Painter.set(this, doorX, center.y, Terrain.LOCKED_DOOR);
                }
                //vertically placing doors
                Painter.set(this, center.x, center.y-Math.round(CHAMBER_HEIGHT/2f), Terrain.LOCKED_DOOR);
                Painter.set(this, center.x, center.y+Math.round(CHAMBER_HEIGHT/2f), Terrain.LOCKED_DOOR);

                //reward placing logic
                int remains = this.pointToCell(center);

                Item mainLoot = null;
                do {
                    switch (Random.Int(3)){
                        case 0:
                            mainLoot = Generator.random(Generator.Category.RING);
                            break;
                        case 1:
                            mainLoot = Generator.random(Generator.Category.ARTIFACT);
                            break;
                        case 2:
                            mainLoot = Generator.random(Random.oneOf(
                                    Generator.Category.WEAPON,
                                    Generator.Category.ARMOR));
                            break;
                    }
                } while ( mainLoot == null || Challenges.isItemBlocked(mainLoot));
                this.drop(mainLoot, remains).setHauntedIfCursed().type = Heap.Type.SKELETON;

                int n = Random.IntRange( 1, 2 );
                for (int i=0; i < n; i++) {
                    this.drop( prize( this ), remains ).setHauntedIfCursed();
                }

                this.drop( new IronKey( Dungeon.depth, Dungeon.branch ), remains );
            }
        }

        //key item placing logic
        this.drop(new IronKey(Dungeon.depth, Dungeon.branch), keyCell());
        this.drop(new OldAmulet(), amuletCell());

        Painter.set(this, entrancePoint(), Terrain.ENTRANCE);
    }

    private static Item prize( Level level ) {
        return Generator.random( Random.oneOf(
                Generator.Category.POTION,
                Generator.Category.SCROLL,
                Generator.Category.FOOD,
                Generator.Category.GOLD
        ) );
    }

    @Override
    protected void createMobs() {

    }

    @Override
    protected void createItems() {

    }

    public static class Chamber {
        public Level level;
        public final Point center;
        public final Rect innerRoom;
        private final Rect outerRoom;
        private ArrayList<Integer> innerRoomPos = new ArrayList<>();
        public boolean isBuildWithStructure = false; //make this false if you want to make room with Painter

        public Chamber(Level level, int left, int top, int right, int bottom, Point center) {
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
        }

        public int[] roomStructure() {
            return new int[] {}; //must be overridden if isBuildWithStructure is true
        }

        public void build() {
            Painter.fill(this.level, this.outerRoom, Terrain.WALL);
            Painter.fill(this.level, this.outerRoom, 1, Terrain.EMPTY);
            Painter.set(this.level, this.center, Terrain.PEDESTAL);

            /*
                if the build does not work well, check the roomStructure() is correctly overridden
                if roomStructure() is too short, it will make uncomplete room
                if roomStructure() is too long, it will throw ArrayIndexOutOfBoundsException
            */
            if (isBuildWithStructure) {
                int index = 0;
                try {
                    for (int pos : innerRoomPos) {
                        if (roomStructure()[index] != -1) this.level.map[pos] = roomStructure()[index];
                        index++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class EmptySPChamber extends Chamber {

        {
            this.isBuildWithStructure = false;
        }

        public EmptySPChamber(Level level, int left, int top, int right, int bottom, Point center) {
            super(level, left, top, right, bottom, center);
        }

        @Override
        public void build() {
            super.build();
            Painter.fill(this.level, this.innerRoom, Terrain.EMPTY_SP);
            Painter.set(this.level, this.center, Terrain.PEDESTAL);
        }
    }

    public static class TestChamber extends Chamber {
        {
            this.isBuildWithStructure = true;
        }

        public TestChamber(Level level, int left, int top, int right, int bottom, Point center) {
            super(level, left, top, right, bottom, center);
        }

        @Override
        public int[] roomStructure() {
            int n = -1;
            int E = Terrain.EMPTY;
            int S = Terrain.STATUE;
            return new int[] {
                    E,S,E,S,E,S,E,
                    S,E,S,E,S,E,S,
                    E,S,E,S,E,S,E,
                    S,E,S,n,S,E,S,
                    E,S,E,S,E,S,E,
                    S,E,S,E,S,E,S,
                    E,S,E,S,E,S,E
            };
        }

        @Override
        public void build() {
            super.build();
        }
    }
}
