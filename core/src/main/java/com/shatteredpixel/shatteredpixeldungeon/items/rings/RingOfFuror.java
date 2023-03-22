/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items.rings;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AttackSpeedBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Flurry;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SerialAttack;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Surgery;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.samurai.ShadowBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DoubleDagger;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class RingOfFuror extends Ring {

    {
        icon = ItemSpriteSheet.Icons.RING_FUROR;
    }

    public String statsInfo() {
        if (isIdentified()) {
            return Messages.get(this, "stats", Messages.decimalFormat("#.##", 100f * (Math.pow(1.0905f, soloBuffedBonus()) - 1f)));
        } else {
            return Messages.get(this, "typical_stats", Messages.decimalFormat("#.##", 9.05f));
        }
    }

    @Override
    protected RingBuff buff() {
        return new Furor();
    }

    public static float attackSpeedMultiplier(Char target) {
        float speedBonus = (float) Math.pow(1.0905, getBuffedBonus(target, Furor.class));
        if (hero.buff(Adrenaline.class) != null) {
            speedBonus *= 1.5f;
        }
        if (hero.buff(ShadowBlade.shadowBladeTracker.class) != null) {
            speedBonus *= 2f + 0.05f * hero.pointsInTalent(Talent.DOUBLE_BLADE_PRACTICE);
        }
        if (hero.buff(Flurry.class) != null) {
            speedBonus *= 2f;
        }
        if (hero.hasTalent(Talent.ATK_SPEED_ENHANCE)) {
            speedBonus *= 1f + 0.05f * hero.pointsInTalent(Talent.ATK_SPEED_ENHANCE);
        }
        if (hero.buff(AttackSpeedBuff.class) != null) {
            speedBonus *= 1f + 0.05f * hero.buff(AttackSpeedBuff.class).getCount();
        }
        if (hero.hasTalent(Talent.SLASHING_PRACTICE) && hero.buff(SerialAttack.class) != null) {
            speedBonus *= 1f + 0.02f * hero.pointsInTalent(Talent.SLASHING_PRACTICE) * hero.buff(SerialAttack.class).getCount();
        }
        if (hero.hasTalent(Talent.CROSS_SLASH) && hero.heroClass != HeroClass.KNIGHT) {
            speedBonus *= 1f + 0.1f * hero.pointsInTalent(Talent.CROSS_SLASH);
        }
        if (hero.buff(Surgery.class) != null && hero.hasTalent(Talent.HASTY_HANDS)) {
            speedBonus *= 1 + 0.01f * hero.buff(Surgery.class).getCount() * hero.pointsInTalent(Talent.HASTY_HANDS);
        }
        if (hero.hasTalent(Talent.LESS_RESIST)) {
            int aEnc = hero.belongings.armor.STRReq() - hero.STR();
            if (aEnc < 0) {
                speedBonus *= 1 + 0.05f * hero.pointsInTalent(Talent.LESS_RESIST) * (-aEnc);
            }
        }
        if (hero.buff(DoubleDagger.ReverseBlade.class) != null) {
			speedBonus *= 3;
        }
        speedBonus *= RingOfRush.rushSpeedMultiplier(hero);
        return speedBonus;
    }

    public class Furor extends RingBuff {
    }
}
