/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class SpellBookCoolDown extends Buff {

    int duration;
    int maxDuration;

    {
        type = buffType.NEUTRAL;
        announced = false;
    }

    public void set(int time) {
        maxDuration = time;
        duration = maxDuration;
    }

    public void use(int time) {
        duration -= time;
        if (duration <= 0) detach();
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

    @Override
    public int icon() {
    return BuffIndicator.TIME;
    }

    @Override
    public void tintIcon(Image icon) {
        icon.hardlight(0xD4A04F);
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (maxDuration - duration) / maxDuration);
    }

    @Override
    public String iconTextDisplay() {
        return Integer.toString(duration);
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", duration);
    }

    private static final String MAX_DURATION = "maxDuration";
    private static final String DURATION = "duration";
    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( MAX_DURATION, maxDuration );
        bundle.put( DURATION, duration );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        maxDuration = bundle.getInt( MAX_DURATION );
        duration = bundle.getInt( DURATION );
    }
}
