package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.changer.OldAmulet;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.CrystalKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.Key;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;

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
                Rect chamber = new Rect(
                        left,
                        top,
                        right,
                        bottom);
                Painter.fill(this, chamber, Terrain.WALL);
                Painter.fill(this, chamber, 1, Terrain.EMPTY_SP);
                Painter.set(this, center, Terrain.PEDESTAL);

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
}
