package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

public class GodFury extends Buff {

    {
        type = buffType.NEUTRAL;
    }

    @Override
    public int icon() { return BuffIndicator.GODFURY; }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }
}
