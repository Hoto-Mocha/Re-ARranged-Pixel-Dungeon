package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

public class CounterAttack extends FlavourBuff {
    { actPriority = HERO_PRIO+1;}

    @Override
    public boolean act() {
        spend(TICK);
        detach();
        return true;
    }

}