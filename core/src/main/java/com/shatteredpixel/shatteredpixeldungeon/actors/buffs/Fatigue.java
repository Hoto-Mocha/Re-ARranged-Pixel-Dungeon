package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Fatigue extends Buff {

    int duration = 0;

    {
        type = buffType.NEGATIVE;
    }

    @Override
    public int icon() {
        return BuffIndicator.CORRUPT;
    }

    @Override
    public void tintIcon(Image icon) {
        switch (duration/2) {
            case 0:
            default:
                icon.hardlight(0f, 1f, 0f);
                break;
            case 1:
                icon.hardlight(0.6f, 1f, 0f);
                break;
            case 2:
                icon.hardlight(1f, 1f, 0f);
                break;
            case 3:
                icon.hardlight(1f, 0.6f, 0f);
                break;
            case 4:
                icon.hardlight(1f, 0f, 0f);
                break;
        }
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc");
    }

    public void hit(boolean attack) {
        if (duration == 0) {
            duration ++;
        }
        if (attack) {
            duration += 2;
        } else {
            duration ++;
        }

        if (duration > 9) {
            switch (Random.NormalIntRange(0, 6)) {
                case 0: default:
                    Buff.affect(target, Paralysis.class, Paralysis.DURATION/5f);
                    break;
                case 1:
                    Buff.affect(target, Cripple.class, Cripple.DURATION/2f);
                    break;
                case 2:
                    Buff.affect(target, Weakness.class, Weakness.DURATION/4f);
                    break;
                case 3:
                    Buff.affect(target, Blindness.class, Blindness.DURATION/2f);
                    break;
                case 4:
                    Buff.affect(target, Daze.class, Daze.DURATION);
                    break;
                case 5:
                    Buff.affect(target, Vulnerable.class, Vulnerable.DURATION/4f);
                    break;
                case 6:
                    Buff.affect(target, Slow.class, Slow.DURATION/2f);
                    break;
            }
            duration -= Random.NormalIntRange(5, 7);
        }

        BuffIndicator.refreshHero();
    }

    @Override
    public boolean act() {
        duration--;
        if (duration <= 0) {
            detach();
        }
        spend(TICK);
        return true;
    }

    private static final String DURATION = "duration";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( DURATION, duration );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        duration = bundle.getInt( DURATION );
    }
}
