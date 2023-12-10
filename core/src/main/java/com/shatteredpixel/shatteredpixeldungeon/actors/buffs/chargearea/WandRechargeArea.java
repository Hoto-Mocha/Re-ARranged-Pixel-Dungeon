package com.shatteredpixel.shatteredpixeldungeon.actors.buffs.chargearea;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlessingParticle;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.particles.Emitter;

public class WandRechargeArea extends RechargeArea {

    @Override
    public boolean act() {

        if (position == target.pos){
            Buff.affect(target, Recharging.class, 10f);
            detach();
        }

        if (Dungeon.depth != floor) {
            detach();
        }

        left--;
        BuffIndicator.refreshHero();
        if (left <= 0){
            detach();
        }

        spend(TICK);
        return true;
    }

    @Override
    public void fx(boolean on) {
        Emitter e = CellEmitter.get(position);
        if (on){
            e.pour(BlessingParticle.FACTORY, 0.05f);
            emitter = e;
        } else {
            emitter.on = false;
        }
    }
}
