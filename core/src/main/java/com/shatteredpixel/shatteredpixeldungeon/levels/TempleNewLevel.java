package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.EbonyMimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Piranha;
import com.shatteredpixel.shatteredpixeldungeon.items.EnergyCrystal;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.changer.OldAmulet;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ExplosiveTrap;
import com.watabou.utils.Point;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class TempleNewLevel extends Level {

    {
        color1 = 0x64bb4c;
        color2 = 0x569545;
    }

    private static final int CHAMBER_X_NUM      = 5; // chamber's horizontal number, at least 1
    private static final int CHAMBER_Y_NUM      = 5; // chamber's vertical number, at least 1
    private static final int CHAMBER_WIDTH      = 17; // chamber's horizontal length(excluding wall), at least 1, and must be odd
    private static final int CHAMBER_HEIGHT     = 17; // chamber's vertical length(excluding wall), at least 1, and must be odd
    private static final int CHAMBER_FIRST_X    = 0; //cannot be changed
    private static final int CHAMBER_FIRST_Y    = 2 /* <- this means how far the chamber is from the top pedestal*/ +4;
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

    //chamber build logic
    Class<?>[] chamberClasses = {
            MimicInTheGrassChamber.class,
            PiranhaPoolChamber.class,
            AlchemyChamber.class,
            SentryChamber.class,
            MineFieldChamber.class,
    };
    float[] deck = {1, 1, 1, 1, 1};

    private void createChamber(Level level, int left, int top, int right, int bottom, Point center) {
        int index = Random.chances(deck); //picks random index from deck
        if (index == -1) return;
        deck[index] = 0; //makes the index's chance to 0

        Class<?> chamberClass;
        try { //deck's length can be longer than chamberClasses's length, so I put try-catch here
            chamberClass = chamberClasses[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            chamberClass = Chamber.class;
        }
        Chamber finalChamber = (Chamber) Reflection.newInstance(chamberClass); //finally makes new instance of the chamber
        if (finalChamber != null) {
            finalChamber.set(level,
                    left,
                    top,
                    right,
                    bottom,
                    center);
            finalChamber.build();
        }
    }

    private void buildLevel() {
        Painter.fill(this, rect, Terrain.WALL);
        Painter.fill(this, rect, 1, Terrain.EMPTY);

        Painter.set(this, amuletPoint(), Terrain.PEDESTAL);

        Painter.set(this, keyPoint(), Terrain.PEDESTAL);

        for (int x = 0; x < CHAMBER_X_NUM; x++) {                   //chamber's x coordinate. 0~CHAMBER_X_NUM
            for (int y = 0; y < CHAMBER_Y_NUM; y++) {               //chamber's y coordinate. 0~CHAMBER_Y_NUM
                int left = CHAMBER_FIRST_X + x*(CHAMBER_WIDTH+1);   //chamber's actual left pos. chamber includes this pos
                int top = CHAMBER_FIRST_Y + y*(CHAMBER_HEIGHT+1);   //chamber's actual top pos. chamber includes this pos
                int right = left + CHAMBER_WIDTH+2;                 //chamber's actual right pos. chamber doesn't include this pos
                int bottom = top + CHAMBER_HEIGHT+2;                //chamber's actual right pos. chamber doesn't include this pos
                Point center = new Point(left + Math.round(CHAMBER_WIDTH/2f), top + Math.round(CHAMBER_HEIGHT/2f)); //chamber's center point.

                Chamber chamber = new Chamber();
                chamber.set(this, left, top, right, bottom, center);
                chamber.build();

                createChamber(this, left, top, right, bottom, center);

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
        public Point center;
        public Rect innerRoom;
        private Rect outerRoom;
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
                if roomStructure() is too short, it will make uncomplete room
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

    public static class EmptySPChamber extends Chamber {

        {
            isBuildWithStructure = false;
        }

        @Override
        public void build() {
            super.build();
            Painter.fill(level, innerRoom, Terrain.EMPTY_SP);
            Painter.set(level, center, Terrain.PEDESTAL);
        }
    }

    public static class TestChamber extends Chamber {

        {
            isBuildWithStructure = true;
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

    //actual chambers
    public static class MimicInTheGrassChamber extends Chamber {
        /*
            this room contains full of furrowed grass and 12 of hidden ebony mimic
        */

        {
            isBuildWithStructure = false;
        }

        @Override
        public void build() {
            super.build();

            Painter.fill(level, innerRoom, Terrain.FURROWED_GRASS);
            Painter.set(level, center, Terrain.PEDESTAL);

            ArrayList<Integer> randomPos = randomRoomPos(12);
            for (int pos : randomPos) {
                level.mobs.add(Mimic.spawnAt(pos, EbonyMimic.class, false));
            }
        }
    }

    public static class PiranhaPoolChamber extends Chamber {

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

    public static class AlchemyChamber extends Chamber {

        {
            isBuildWithStructure = true;
        }

        @Override
        public int[] roomStructure() {
            int n = -1;
            int E = Terrain.EMPTY_SP;
            int B = Terrain.BOOKSHELF;
            int P = Terrain.PEDESTAL;
            int A = Terrain.ALCHEMY;
            int S = Terrain.STATUE_SP;
            return new int[] {
                    E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E,
                    E, B, B, B, B, B, B, B, E, B, B, B, B, B, B, B, E,
                    E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E,
                    E, B, B, B, B, B, B, B, E, B, B, B, B, B, B, B, E,
                    E, E, E, E, E, E, E, E, E, E, P, E, E, E, P, E, E,
                    E, B, B, B, B, B, B, B, E, E, P, E, A, E, P, E, E,
                    E, E, E, E, E, E, E, E, E, E, P, E, E, E, P, E, E,
                    E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E,
                    E, E, E, E, E, E, E, E, n, E, E, E, E, E, E, E, E,
                    E, E, E, E, E, E, E, E, E, E, S, E, S, E, S, E, E,
                    E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E,
                    E, B, B, B, B, B, B, B, E, B, B, B, B, B, B, B, E,
                    E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E,
                    E, B, B, B, B, B, B, B, E, B, B, B, B, B, B, B, E,
                    E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E,
                    E, B, B, B, B, B, B, B, E, B, B, B, B, B, B, B, E,
                    E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E,
            };
        }

        @Override
        public void build() {
            super.build();
            for (int i = 2; i < 5; i++) {
                Point pedestalPos = new Point(center.x+2, center.y-i);
                if (i == 2) {
                    level.drop(Generator.randomUsingDefaults(Generator.Category.FOOD), level.pointToCell(pedestalPos));
                } else {
                    level.drop(new EnergyCrystal().quantity(Random.IntRange(6,10)), level.pointToCell(pedestalPos));
                }
            }
            for (int i = 2; i < 5; i++) {
                Point pedestalPos = new Point(center.x+6, center.y-i);
                level.drop(Generator.randomUsingDefaults(Generator.Category.POTION), level.pointToCell(pedestalPos));
            }
        }
    }

    public static class SentryChamber extends Chamber {

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

    public static class MineFieldChamber extends Chamber {

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
}
