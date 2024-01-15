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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.watabou.noosa.audio.Sample;

public class FirstAidKit extends ArmorAbility {

    {
        baseChargeUse = 50f;
    }

    @Override
    protected void activate(ClassArmor armor, Hero hero, Integer target) {

        Buff.affect(hero, Healing.class).setHeal((int) 20 + 5 * hero.pointsInTalent(Talent.ADDITIONAL_MEDS), 0, 1 + hero.pointsInTalent(Talent.FASTER_HEALING));
        if (hero.pointsInTalent(Talent.THERAPEUTIC_BANDAGE) >= 1) {
            Buff.detach( hero, Vertigo.class);
            Buff.detach( hero, Weakness.class );
        }
        if (hero.pointsInTalent(Talent.THERAPEUTIC_BANDAGE) >= 2) {
            Buff.detach( hero, Slow.class );
            Buff.detach( hero, Vulnerable.class );
        }
        if (hero.pointsInTalent(Talent.THERAPEUTIC_BANDAGE) >= 3) {
            Buff.detach( hero, Blindness.class );
            Buff.detach( hero, Cripple.class );
        }
        if (hero.pointsInTalent(Talent.THERAPEUTIC_BANDAGE) == 4) {
            Buff.detach( hero, Poison.class );
            Buff.detach( hero, Bleeding.class );
        }
        hero.sprite.operate(hero.pos);
        Sample.INSTANCE.play(Assets.Sounds.UNLOCK);
        hero.sprite.emitter().burst(LeafParticle.GENERAL, 10);

        armor.charge -= chargeUse(hero);
        armor.updateQuickslot();
        Invisibility.dispel();
        hero.spendAndNext(Actor.TICK);
    }

    @Override
    public int icon() {
        return HeroIcon.FIRSTAIDKIT;
    }

    @Override
    public Talent[] talents() {
        return new Talent[]{Talent.ADDITIONAL_MEDS, Talent.THERAPEUTIC_BANDAGE, Talent.FASTER_HEALING, Talent.HEROIC_ENERGY};
    }
}


