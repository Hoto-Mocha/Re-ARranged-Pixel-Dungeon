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

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

import java.text.DecimalFormat;
public class LargeSwordBuff extends Buff {

    {
        type = buffType.NEUTRAL;
        announced = false;
    }

    float damageFactor = 1;
    float accuracyFactor = 1;
    float defenseFactor = 0.90f;
    float maxDamage;
    float maxAccuracy;
    int turn = 0;

    public void setDamageFactor(float lvl) {
        turn++;
        maxDamage = 1.80f + 0.20f * (lvl + 1);
        maxAccuracy = 1.40f + 0.10f * (lvl + 1);
        damageFactor += 0.20f;
        accuracyFactor += 0.10f;
        defenseFactor = (float)Math.pow(0.90f, lvl + 1);

        if (damageFactor > maxDamage) {
            damageFactor = maxDamage;
        }
    }

    public float getDefenseFactor() {
        return defenseFactor;
    }

    public float getDamageFactor() {
        return damageFactor;
    }

    public float getAccuracyFactor() {
        return accuracyFactor;
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
        if (damageFactor <= 1.2f){
            icon.hardlight(0.5f, 1, 0);
        } else if (damageFactor <= 1.4f) {
            icon.hardlight(1, 1, 0);
        } else if (damageFactor <= 1.6f){
            icon.hardlight(1, 0.67f, 0);
        } else if (damageFactor <= 1.8f){
            icon.hardlight(1, 0.33f, 0);
        } else {
            icon.hardlight(1, 0, 0);
        }
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, 1 - (damageFactor - 1) / (maxDamage - 1));
    }

    public int pos = -1;

    @Override
    public boolean act() {
        if (pos == -1) pos = target.pos;
        if (pos != target.pos) {
            detach();
        }
        spend(TICK);
        return true;
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc",
                turn,
                new DecimalFormat("#").format((getDamageFactor() - 1) * 100),
                new DecimalFormat("#").format((maxDamage - 1) * 100),
                new DecimalFormat("#").format((getAccuracyFactor() - 1) * 100),
                new DecimalFormat("#").format((maxAccuracy - 1) * 100),
                new DecimalFormat("#.##").format(((1 - defenseFactor) * 100)));
    }

    private static final String DAMAGE_FACTOR = "damageFactor";
    private static final String ACCURACY_FACTOR = "accuracyFactor";
    private static final String MAX_DAMAGE = "maxDamage";
    private static final String MAX_ACCURACY = "maxAccuracy";
    private static final String DEFENSE_FACTOR = "defenseFactor";
    private static final String TURN = "turn";
    private static final String POS = "pos";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(DAMAGE_FACTOR, damageFactor);
        bundle.put(ACCURACY_FACTOR, accuracyFactor);
        bundle.put(MAX_DAMAGE, maxDamage);
        bundle.put(MAX_ACCURACY, maxAccuracy);
        bundle.put(DEFENSE_FACTOR, defenseFactor);
        bundle.put(TURN, turn);
        bundle.put(POS, pos);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        damageFactor = bundle.getFloat(DAMAGE_FACTOR);
        accuracyFactor = bundle.getFloat(ACCURACY_FACTOR);
        maxDamage = bundle.getFloat(MAX_DAMAGE);
        maxAccuracy = bundle.getFloat(MAX_ACCURACY);
        defenseFactor = bundle.getFloat(DEFENSE_FACTOR);
        turn = bundle.getInt(TURN);
        pos = bundle.getInt(POS);
    }
}

