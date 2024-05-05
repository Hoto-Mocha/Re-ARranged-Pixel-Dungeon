package com.shatteredpixel.shatteredpixeldungeon.levels.features;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;

public class Wire {
    private static boolean freezeTrample = false;

    public static void trample(Level level, int pos ) {

        if (freezeTrample) return;

        Char ch = Actor.findChar(pos);

        if (ch != null) {
            Buff.affect(ch, Cripple.class, Cripple.DURATION/2f);
            int bleed = Math.round(2+Dungeon.depth/3f);
            if (Dungeon.hero.pointsInTalent(Talent.WIRE) > 1) {
                bleed += 3;
            }
            Buff.affect(ch, Bleeding.class).set(bleed);
            Level.set(pos, Terrain.EMPTY);
        }

        freezeTrample = false;

        if (ShatteredPixelDungeon.scene() instanceof GameScene) {
            GameScene.updateMap(pos);
        }
    }

}
