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
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.NaturesPower;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class ReinforcedArmor extends ArmorAbility {

    {
        baseChargeUse = 25f;
    }

    @Override
    protected void activate(ClassArmor armor, Hero hero, Integer target) {

        Buff.prolong(hero, reinforcedArmorTracker.class, reinforcedArmorTracker.DURATION);
        hero.sprite.operate(hero.pos);
        Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
        //hero.sprite.emitter().burst(LeafParticle.GENERAL, 10);

        armor.charge -= chargeUse(hero);
        armor.updateQuickslot();
        Invisibility.dispel();
        hero.spendAndNext(Actor.TICK);

    }

    @Override
    public int icon() {
        return HeroIcon.REINFORCEDARMOR;
    }

    @Override
    public Talent[] talents() {
        return new Talent[]{Talent.BAYONET, Talent.TACTICAL_SIGHT, Talent.PLATE_ADD, Talent.HEROIC_ENERGY};
    }

    public static class reinforcedArmorTracker extends FlavourBuff {

        public static final float DURATION = 20f;

        @Override
        public int icon() {
            return BuffIndicator.PLATE;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(1f, 1f, 1f);
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, (DURATION - visualcooldown()) / DURATION);
        }

        public int blockingRoll(){
            return Random.NormalIntRange(5 + 5 * Dungeon.hero.pointsInTalent(Talent.PLATE_ADD), 20 + 5 * Dungeon.hero.pointsInTalent(Talent.PLATE_ADD));
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", dispTurns());
        }
    }
}


