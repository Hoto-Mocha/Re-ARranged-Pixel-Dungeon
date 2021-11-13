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

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class Focusing extends Buff {

    private int count = 0;
    private float focusTime = 0f;
    private float initialFocusTime = 5f;
    public int getCount() {
        return count;
    }

    @Override
    public int icon() {
        return BuffIndicator.MARK;
    }

    @Override
    public void tintIcon(Image icon) {
        icon.resetColor();
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (initialFocusTime - focusTime) / initialFocusTime);
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    public void hit(Char enemy) {

        count++;
        focusTime = 5f;

        if (!enemy.isAlive() || (enemy.buff(Corruption.class) != null && enemy.HP == enemy.HT)) {
            focusTime = Math.max(focusTime, 15 * ((Hero) target).pointsInTalent(Talent.CLEAVE));
        }

        initialFocusTime = focusTime;

        BuffIndicator.refreshHero(); //refresh the buff visually on-hit

    }

    public void addTime(float time) {
        focusTime += time;
    }

    @Override
    public void detach() {
        super.detach();
    }

    @Override
    public boolean act() {
        focusTime -= TICK;
        spend(TICK);
        if (focusTime <= 0) {
            detach();
        }
        return true;
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", count, dispTurns(focusTime));
    }

    private static final String COUNT = "count";
    private static final String TIME = "focusTime";
    private static final String INITIAL_TIME = "initialFocusTime";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(COUNT, count);
        bundle.put(TIME, focusTime);
        bundle.put(INITIAL_TIME, initialFocusTime);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        count = bundle.getInt(COUNT);
        focusTime = bundle.getFloat(TIME);
    }

    public Image getIcon() {
        Image icon;
        if (((Hero) target).belongings.weapon() != null) {
            icon = new ItemSprite(((Hero) target).belongings.weapon().image, null);
        } else {
            icon = new ItemSprite(new Item() {
                {
                    image = ItemSpriteSheet.WEAPON_HOLDER;
                }
            });
        }
        return icon;
    }

    public int getComboCount() {
        return count;
    }
}