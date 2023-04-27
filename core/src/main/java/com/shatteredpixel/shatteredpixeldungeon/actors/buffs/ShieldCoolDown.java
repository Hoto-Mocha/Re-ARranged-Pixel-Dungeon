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

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class ShieldCoolDown extends Buff {

    private float coolDown = 0f;
    private float maxCoolDown = 0f;

    @Override
    public int icon() {
        return BuffIndicator.TIME;
    }

    @Override
    public void tintIcon(Image icon) {
        icon.hardlight(0x99999);
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (maxCoolDown - coolDown)/ maxCoolDown);
    }

    public float getCoolDown() {
        return coolDown;
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    public void hit( int upgrade ) {
        coolDown -= upgrade;
        BuffIndicator.refreshHero(); //refresh the buff visually on-hit
    }

    public void use(int amount) {
        coolDown -= amount;
        BuffIndicator.refreshHero();
    }

    public void set() {
        maxCoolDown = 10f * hero.lvl;
        coolDown = 10f * hero.lvl;
        coolDown *= Math.pow(0.9f, hero.pointsInTalent(Talent.ON_ALERT));
        coolDown *= Math.pow(0.8f, hero.pointsInTalent(Talent.FORTRESS));
    }

    public void extend(float amount) {
        coolDown += amount;
    }

    @Override
    public void detach() {
        super.detach();
    }

    @Override
    public boolean act() {
        coolDown-=TICK;
        spend(TICK);
        if (coolDown <= 0) {
            detach();
        }
        return true;
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", dispTurns(coolDown));
    }

    private static final String MAXCOOLDOWN = "maxCoolDown";
    private static final String COOLDOWN  = "cooldown";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(MAXCOOLDOWN, maxCoolDown);
        bundle.put(COOLDOWN, coolDown);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        maxCoolDown = bundle.getInt( MAXCOOLDOWN );
        coolDown = bundle.getFloat( COOLDOWN );
    }

}
