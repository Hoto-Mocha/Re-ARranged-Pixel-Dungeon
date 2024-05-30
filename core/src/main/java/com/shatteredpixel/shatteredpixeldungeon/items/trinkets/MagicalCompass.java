package com.shatteredpixel.shatteredpixeldungeon.items.trinkets;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class MagicalCompass extends Trinket {

    {
        image = ItemSpriteSheet.COMPASS;
    }

    @Override
    protected int upgradeEnergyCost() {
        //6 -> 8(14) -> 10(24) -> 12(36)
        return 6+2*level();
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", (int)(100*compassChance(buffedLvl())));
    }

    public static boolean[] compassed = new boolean[32];

    public static float compassChance(){
        return compassChance(trinketLevel(MagicalCompass.class));
    }

    public static float compassChance( int level ){
        if (level == -1){
            return 0f;
        } else {
//            if (DeviceCompat.isDebug()) return 1;
//            else
            return 0.2f + 0.2f*level;
        }
    }

    public static void onSwitchLevel() {
        if (Dungeon.branch == 0 && !MagicalCompass.compassed[Dungeon.depth]) { //브랜치 층에서는 작동하지 않음
            if (Random.Float() < MagicalCompass.compassChance()) {
                int len = Dungeon.level.length();
                boolean[] p = Dungeon.level.passable;
                boolean[] s = Dungeon.level.secret;
                boolean[] a = Dungeon.level.avoid;
                boolean[] passable = new boolean[len];
                for (int i = 0; i < len; i++) {
                    passable[i] = (p[i] || s[i]) && !a[i];
                }

                //TODO: boolean[] vis 부분에 passable을 넣는 것이 어떤 역할을 하는지 정확히 모르는 상태에서 진행함. 버그 발생 시 수정 필요
                PathFinder.Path path = Dungeon.findPath(Dungeon.hero, Dungeon.level.exit(), passable, passable, true);
                if (PathFinder.distance[Dungeon.level.exit()] == Integer.MAX_VALUE){
                    GLog.w(Messages.get(MagicalCompass.class, "cant_find"));
                } else {
                    int[] map = Dungeon.level.map;
                    boolean[] mapped = Dungeon.level.mapped;
                    boolean[] discoverable = Dungeon.level.discoverable;
                    for (int i : path) {
                        if (discoverable[i]) {
                            int terr = map[i];

                            mapped[i] = true;
                            if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

                                Dungeon.level.discover( i );

                                if (Dungeon.level.heroFOV[i]) {
                                    GameScene.discoverTile( i, terr );
                                    ScrollOfMagicMapping.discover( i );
                                }
                            }
                        }
                    }
                    GameScene.updateFog();
                }
            }

            MagicalCompass.compassed[Dungeon.depth] = true;
        }
    }
}
