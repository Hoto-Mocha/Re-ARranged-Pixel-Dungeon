package com.shatteredpixel.shatteredpixeldungeon.actors.buffs.chargearea;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;

public class MutationBuff extends Buff {
    @Override
    public boolean act() {
        detach();
        return super.act();
    }
}
