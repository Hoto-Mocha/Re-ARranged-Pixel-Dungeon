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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndCombo;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class ShieldCoolDown extends Buff {

    private float coolDown = 0f;
    private float maxCoolDown = hero.lvl*10f;

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
        if (hero.hasTalent(Talent.CROSS_SLASH) && Random.Int(10) < hero.pointsInTalent(Talent.CROSS_SLASH)) {
            coolDown -= 2*upgrade;
        } else {
            coolDown -= upgrade;
        }
        BuffIndicator.refreshHero(); //refresh the buff visually on-hit
    }

    public void set() {
        coolDown = maxCoolDown * (1 - 0.1f * hero.pointsInTalent(Talent.ON_ALERT));
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
