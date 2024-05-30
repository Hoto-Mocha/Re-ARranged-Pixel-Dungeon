package com.shatteredpixel.shatteredpixeldungeon.items.trinkets;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;
import static com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping.discover;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Awareness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.text.DecimalFormat;

public class CrystalBall extends Trinket {
    {
        image = ItemSpriteSheet.CRYSTAL_BALL;
    }

    @Override
    protected int upgradeEnergyCost() {
        //6 -> 8(14) -> 10(24) -> 12(36)
        return 6+2*level();
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", new DecimalFormat("#.##").format(100*itemVisionChance(buffedLvl())), new DecimalFormat("#.##").format(100*mappingChance(buffedLvl())));
    }

    public static boolean[] visited = new boolean[32];

    public static float itemVisionChance(){
        return itemVisionChance(trinketLevel(CrystalBall.class));
    }

    public static float itemVisionChance( int level ){
        if (level == -1){
            return 0f;
        } else {
//            if (DeviceCompat.isDebug()) return 1;
//            else
            return 0.125f + 0.125f*level;
        }
    }

    public static float mappingChance(){
        return mappingChance(trinketLevel(CrystalBall.class));
    }

    public static float mappingChance( int level ){
        if (level == -1){
            return 0f;
        } else {
//            if (DeviceCompat.isDebug()) return 1;
//            else
                return 0.02f + 0.02f*level;
        }
    }

    public static void onSwitchLevel() {
        if (trinketLevel(CrystalBall.class) != -1) { //수정구를 가지고 있을 때에만 작동함
            if (Dungeon.hero != null && Dungeon.hero.buff(Awareness.class) != null) {
                Dungeon.hero.buff(Awareness.class).detach();
            }
        }
        if (Dungeon.branch == 0 && !CrystalBall.visited[Dungeon.depth]) { //브랜치 층에서는 작동하지 않음
            if (Random.Float() < CrystalBall.itemVisionChance()) {
                Buff.affect(hero, Awareness.class, Awareness.DURATION);
            }
            if (Random.Float() < CrystalBall.mappingChance()) {
                int length = Dungeon.level.length();
                int[] map = Dungeon.level.map;
                boolean[] mapped = Dungeon.level.mapped;
                boolean[] discoverable = Dungeon.level.discoverable;

                boolean noticed = false;

                for (int i=0; i < length; i++) {

                    int terr = map[i];

                    if (discoverable[i]) {

                        mapped[i] = true;
                        if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

                            Dungeon.level.discover( i );

                            if (Dungeon.level.heroFOV[i]) {
                                GameScene.discoverTile( i, terr );
                                discover( i );

                                noticed = true;
                            }
                        }
                    }
                }
                GameScene.updateFog();

                if (noticed) {
                    Sample.INSTANCE.play( Assets.Sounds.SECRET );
                }

                SpellSprite.show( Dungeon.hero, SpellSprite.MAP );
            }
            CrystalBall.visited[Dungeon.depth] = true;
        }
    }
}
