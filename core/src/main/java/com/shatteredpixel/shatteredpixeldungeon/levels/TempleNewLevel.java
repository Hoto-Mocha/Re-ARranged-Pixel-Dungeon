package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.changer.OldAmulet;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers.AlchemyChamber;
import com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers.Chamber;
import com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers.ConfusionChamber;
import com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers.MazeChamber;
import com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers.MimicInTheGrassChamber;
import com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers.MineFieldChamber;
import com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers.MoistureChamber;
import com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers.PiranhaPoolChamber;
import com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers.SentryChamber;
import com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers.SentryMazeChamber;
import com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers.SpearGnollChamber;
import com.shatteredpixel.shatteredpixeldungeon.levels.templeChambers.WarpStoneChamber;
import com.watabou.utils.Point;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;
import com.watabou.utils.Reflection;

public class TempleNewLevel extends Level {

    {
        color1 = 0x64bb4c;
        color2 = 0x569545;
    }

    public static final int PEDESTAL_WALL_OFFSET = 3; //the distance of between top wall and amulet cell
    public static final int PEDESTAL_CHAMBER_OFFSET = 3; //the distance of between amulet cell and chambers' top wall
    public static final int ENTRANCE_WALL_OFFSET = 2; //the distance of between entrance cell and bottom wall
    public static final int ENTRANCE_CHAMBER_OFFSET = 3; //needs to have enough distance for keyCell()
    public static final int CHAMBER_X_NUM      = 2; // chamber's horizontal number, at least 1
    public static final int CHAMBER_Y_NUM      = 6; // chamber's vertical number, at least 1
    public static final int CHAMBER_WIDTH      = 17; // chamber's horizontal length(excluding wall), at least 1, and must be odd
    public static final int CHAMBER_HEIGHT     = 17; // chamber's vertical length(excluding wall), at least 1, and must be odd
    public static final int CHAMBER_FIRST_X    = 0; //cannot be changed
    public static final int CHAMBER_FIRST_Y    = PEDESTAL_WALL_OFFSET + PEDESTAL_CHAMBER_OFFSET + 2;
    public static final int WIDTH = 1+CHAMBER_X_NUM*(CHAMBER_WIDTH+1); //cannot be changed
    public static final int HEIGHT = ENTRANCE_CHAMBER_OFFSET + ENTRANCE_WALL_OFFSET + CHAMBER_FIRST_Y + CHAMBER_Y_NUM * (CHAMBER_HEIGHT+1) +3;
    Rect rect = new Rect(0, 0, WIDTH, HEIGHT);

    private int entranceCell() {
        return (int)(WIDTH/2f)+WIDTH*(HEIGHT-4);
    }

    private Point entrancePoint() {
        return this.cellToPoint(entranceCell());
    }

    private int amuletCell() {
        return (int)(WIDTH/2f)+WIDTH*(PEDESTAL_WALL_OFFSET+1);
    }

    private Point amuletPoint() {
        return this.cellToPoint(amuletCell());
    }

    //더 이상 입구 위에 열쇠를 두지 않음
//    private int keyCell() {
//        return entranceCell()-WIDTH*2;
//    }
//
//    private Point keyPoint() {
//        return this.cellToPoint(keyCell());
//    }

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

        transitions.add(new LevelTransition(this,
                amuletCell(),
                LevelTransition.Type.BRANCH_EXIT,
                Dungeon.depth+1,
                2,
                LevelTransition.Type.BRANCH_ENTRANCE));

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
            SentryMazeChamber.class,
            WarpStoneChamber.class,
            SpearGnollChamber.class,
            MazeChamber.class,
            ConfusionChamber.class,
            MoistureChamber.class,
    };
    float[] deck = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; //the sum needs to be equal or less than CHAMBER_NUM_X * CHAMBER_NUM_Y

    private void createChamber(Level level, int left, int top, int right, int bottom, Point center) {
        int index = Random.chances(deck); //picks random index from deck
        if (index == -1) return;
        deck[index] -= 1; //makes the index's chance to 0

        Class<?> chamberClass;
        try { //deck's length can be longer than chamberClasses's length, so I put try-catch here
            chamberClass = chamberClasses[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            chamberClass = Chamber.class;
        }
        Chamber finalChamber = (Chamber) Reflection.newInstance(chamberClass); //finally makes a new instance of the chamber
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

        Painter.set(this, amuletPoint(), Terrain.EXIT);

//        Painter.set(this, keyPoint(), Terrain.PEDESTAL);

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

                int door = Terrain.DOOR; //방의 4면에 있는 문 타일 지정

                //door placing logic
                int doorX;
                //horizontally placing doors
                doorX = center.x-Math.round(CHAMBER_WIDTH/2f);
                if (doorX != 0 && doorX != WIDTH-1) { //doesn't place a door on the edge of map
                    Painter.set(this, doorX, center.y, door);
                }
                doorX = center.x+Math.round(CHAMBER_WIDTH/2f);
                if (doorX != 0 && doorX != WIDTH-1) { //doesn't place a door on the edge of map
                    Painter.set(this, doorX, center.y, door);
                }
                //vertically placing doors
                Painter.set(this, center.x, center.y-Math.round(CHAMBER_HEIGHT/2f), door);
                Painter.set(this, center.x, center.y+Math.round(CHAMBER_HEIGHT/2f), door);

                //reward placing logic
                int remains = this.pointToCell(center);

//                Item mainLoot = null;
//                do {
//                    switch (Random.Int(3)){
//                        case 0:
//                            mainLoot = Generator.randomUsingDefaults(Generator.Category.RING);
//                            break;
//                        case 1:
//                            mainLoot = Generator.randomUsingDefaults(Generator.Category.ARTIFACT);
//                            break;
//                        case 2:
//                            mainLoot = Generator.randomUsingDefaults(Random.oneOf(
//                                    Generator.Category.WEAPON,
//                                    Generator.Category.ARMOR));
//                            break;
//                    }
//                } while ( mainLoot == null || Challenges.isItemBlocked(mainLoot));
//                this.drop(mainLoot, remains).setHauntedIfCursed().type = Heap.Type.SKELETON;

                int n = Random.IntRange( 1, 2 );
                for (int i=0; i < n; i++) {
                    this.drop( prize( this ), remains ).setHauntedIfCursed().type = Heap.Type.SKELETON;
                }
            }
        }

        //key item placing logic
        this.drop(new OldAmulet(), amuletCell());

        Painter.set(this, entrancePoint(), Terrain.ENTRANCE);
    }

    private static Item prize( Level level ) {
        return Generator.randomUsingDefaults( Random.oneOf(
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
}
