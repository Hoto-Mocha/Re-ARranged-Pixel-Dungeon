package com.shatteredpixel.shatteredpixeldungeon.actors.buffs.chargearea;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
public class RechargeArea extends Buff {

    private static final float DURATION = 5;
    int floor;
    int left = 0;
    int position;
    Emitter emitter;

    {
        type = buffType.POSITIVE;
    }

    public void setup(int pos){
        position = pos;

        if (target != null) {
            fx(false);
            fx(true);
        }

        left = (int) DURATION;
        floor = Dungeon.depth;
    }

    private static final String POSITION = "position";
    private static final String LEFT = "left";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);

        bundle.put(POSITION, position);
        bundle.put(LEFT, left);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        position = bundle.getInt(POSITION);

        left = bundle.getInt(LEFT);
    }

    @Override
    public void detach() {
        emitter.on = false;
        super.detach();
    }
}
