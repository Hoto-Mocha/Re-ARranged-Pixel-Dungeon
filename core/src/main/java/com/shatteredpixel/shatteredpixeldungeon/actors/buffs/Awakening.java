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
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

public class Awakening extends Buff implements ActionIndicator.Action {

    {
        actPriority = BUFF_PRIO - 1;
        announced = false;
    }

    public enum State{
        OFF, ON
    }
    public State state = State.OFF;

    @Override
    public boolean act() {
        if (isAwaken()) {
            int damage = 1;
            if (hero.HP > damage) {
                if (hero.buff(BlobImmunity.class) == null) {
                    hero.damage( damage, this ); //deals no damage when hero has blobimmunity buff
                }
            } else {
                doAction();
            }
        }
        spend( TICK );
        return true;
    }

    public int evasionBonus( int heroLvl, int excessArmorStr ){
        if (isAwaken()) {
            return heroLvl/2 + excessArmorStr*Dungeon.hero.pointsInTalent(Talent.AFTERIMAGE);
        } else {
            return 0;
        }
    }

    public boolean isAwaken() {
        return state == State.ON;
    }

    @Override
    public void detach() {
        super.detach();
        ActionIndicator.clearAction(this);
    }

    @Override
    public int icon() {
        if (isAwaken()) {
            return BuffIndicator.DEMON_ON;
        } else {
            return BuffIndicator.DEMON_OFF;
        }
    }

//    @Override
//    public void tintIcon(Image icon) {
//        super.tintIcon(icon);
//    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        if (isAwaken()) {
            return Messages.get(this, "desc_on");
        } else {
            return Messages.get(this, "desc_off");
        }
    }

    private static final String STATE = "state";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(STATE, state);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        state = bundle.getEnum(STATE, State.class);
        ActionIndicator.setAction(this);
    }

    @Override
    public String actionName() {
        return Messages.get(this, "action_name");
    }

    @Override
    public int actionIcon() {
        if (isAwaken()) {
            return HeroIcon.AWAKEN_OFF;
        } else {
            return HeroIcon.AWAKEN_ON;
        }
    }

    @Override
    public int indicatorColor() {
        if (isAwaken()) {
            return 0x26058C;
        } else {
            return 0xC21313;
        }
    }

    @Override
    public void doAction() {
        if (!isAwaken()) {
            if (hero.buff(AwakeningCooldown.class) == null) {
                state = State.ON;
                hero.sprite.showStatus( CharSprite.NEUTRAL, Messages.get(this, "name") );
                BuffIndicator.refreshHero();
                ActionIndicator.refresh();
                Sample.INSTANCE.play( Assets.Sounds.CHALLENGE );
                hero.sprite.centerEmitter().start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
                //GameScene.flash(0xFF0000);
                if (hero.hasTalent(Talent.FASTER_THAN_LIGHT)) {
                    Buff.prolong(hero, EvasiveMove.class, hero.pointsInTalent(Talent.FASTER_THAN_LIGHT) + 0.0001f);
                }
                hero.spendAndNext(Actor.TICK);
            } else {
                GLog.w(Messages.get(this, "not_prepared"));
            }
        } else {
            state = State.OFF;
            Buff.affect(hero, AwakeningCooldown.class, AwakeningCooldown.DURATION * (float)Math.pow(0.8f, hero.pointsInTalent(Talent.QUICK_RECOVER)));
            Buff.affect(hero, Vulnerable.class, 5f);
            BuffIndicator.refreshHero();
            ActionIndicator.refresh();
            hero.spendAndNext(Actor.TICK);
        }
    }

    public void indicate() {
        ActionIndicator.setAction( this );
    }

    public static class AwakeningCooldown extends FlavourBuff {

        {
            type = buffType.NEUTRAL;
            announced = false;
        }

        public static final float DURATION	= 30f;

        @Override
        public int icon() {
            return BuffIndicator.TIME;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0x99992E);
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, (DURATION - visualcooldown()) / DURATION);
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

