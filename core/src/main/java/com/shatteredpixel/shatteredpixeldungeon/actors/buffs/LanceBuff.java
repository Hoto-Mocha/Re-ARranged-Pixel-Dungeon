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
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

import java.text.DecimalFormat;

public class LanceBuff extends Buff {

    {
        type = buffType.NEUTRAL;
        announced = false;
    }

    float damageFactor = 0;
    float maxDamage    = 2f;

    public void setDamageFactor(float amount) {
        if (damageFactor < maxDamage && (damageFactor + 0.05f * amount) < maxDamage ) {
            damageFactor += 0.05f * amount;
        } else {
            damageFactor = maxDamage + 0.05f;
        }
    }

    public float getDamageFactor() {
        return damageFactor;
    }

    public float duration() {
       return visualcooldown();
    }

    @Override
    public int icon() {
        return BuffIndicator.WEAPON;
    }

    @Override
    public void tintIcon(Image icon) {
        icon.hardlight(0.2f, 1f, 0.2f);
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (maxDamage - damageFactor / maxDamage) - 1);
    }

    @Override
    public boolean act() {
        damageFactor-=0.05f*TICK;
        spend(TICK);
        if (damageFactor <= 0) {
            detach();
        }
        return true;
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", new DecimalFormat("#.##").format(1+getDamageFactor()));
    }

    private static final String DAMAGE =        "damage";
    private static final String MAXDAMAGE =     "maxdamage";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(DAMAGE, damageFactor);
        bundle.put(MAXDAMAGE, maxDamage);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        damageFactor = bundle.getFloat(DAMAGE);
        maxDamage = bundle.getFloat(MAXDAMAGE);
    }
}
