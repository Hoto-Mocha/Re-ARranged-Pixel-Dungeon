package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Wraith;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.CorpseDust;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class GhostSpawner extends Buff {

    int spawnPower = 0;

    {
        //not cleansed by reviving, but does check to ensure the dust is still present
        revivePersists = true;
    }

    @Override
    public boolean act() {

        spawnPower++;
        int wraiths = 1; //we include the wraith we're trying to spawn
        for (Mob mob : Dungeon.level.mobs){
            if (mob instanceof Wraith){
                wraiths++;
            }
        }

        int powerNeeded = Math.min(25, wraiths*wraiths);

        if (powerNeeded <= spawnPower){
            spawnPower -= powerNeeded;
            int pos = 0;
            //FIXME this seems like old bad code (why not more checks at least?) but corpse dust may be balanced around it
            int tries = 20;
            do{
                pos = Random.Int(Dungeon.level.length());
                tries --;
            } while (tries > 0 && (!Dungeon.level.heroFOV[pos] || Dungeon.level.solid[pos] || Actor.findChar( pos ) != null));
            if (tries > 0) {
                Wraith.spawnAt(pos);
                Sample.INSTANCE.play(Assets.Sounds.CURSED);
            }
        }

        spend(TICK);
        return true;
    }

    private static String SPAWNPOWER = "spawnpower";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( SPAWNPOWER, spawnPower );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        spawnPower = bundle.getInt( SPAWNPOWER );
    }
}