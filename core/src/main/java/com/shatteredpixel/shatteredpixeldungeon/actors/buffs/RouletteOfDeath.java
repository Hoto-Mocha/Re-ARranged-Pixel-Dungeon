package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class RouletteOfDeath extends Buff {

    {
        announced = true;
        type = buffType.POSITIVE;
    }

    private int maxDuration = 0;
    private int duration = 0;
    private int count = 0;

    public void hit() {
        maxDuration = 6 + 2 * Dungeon.hero.pointsInTalent(Talent.PERFECT_FOCUSING);
        duration = maxDuration;
        count ++;
    }

    public boolean timeToDeath() {
        return count >= 6;
    }

    public boolean overHalf() {
        return count >= 3;
    }

    @Override
    public int icon() {
        switch (count) {
            case 1: default:
                return BuffIndicator.ROULETTE_1;
            case 2:
                return BuffIndicator.ROULETTE_2;
            case 3:
                return BuffIndicator.ROULETTE_3;
            case 4:
                return BuffIndicator.ROULETTE_4;
            case 5:
                return BuffIndicator.ROULETTE_5;
            case 6:
                return BuffIndicator.ROULETTE_6;
        }
    }

    @Override
    public float iconFadePercent() { return Math.max(0, duration / maxDuration); }

    @Override
    public String iconTextDisplay() {
        return Integer.toString(count);
    }

    @Override
    public boolean act() {
        duration --;
        if (duration <= 0) {
            detach();
        }
        spend(TICK);
        return true;
    }

    @Override
    public String desc(){
        return Messages.get(this, "desc", count, duration);
    }

    public static final String DURATION         = "duration";
    public static final String MAX_DURATION     = "maxDuration";
    public static final String COUNT            = "count";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(DURATION, duration);
        bundle.put(MAX_DURATION, maxDuration);
        bundle.put(COUNT, count);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        duration = bundle.getInt(DURATION);
        maxDuration = bundle.getInt(MAX_DURATION);
        count = bundle.getInt(COUNT);
    }

}
