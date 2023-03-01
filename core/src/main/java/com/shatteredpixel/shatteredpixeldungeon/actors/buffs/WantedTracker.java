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
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NPC;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIcon;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class WantedTracker extends Buff implements ActionIndicator.Action {

    {
        actPriority = BUFF_PRIO - 1;
        announced = false;
    }

    @Override
    public boolean act() {
        spend(TICK);
        return true;
    }

    @Override
    public void detach() {
        super.detach();
        ActionIndicator.clearAction(this);
    }

    @Override
    public String actionName() {
        return Messages.get(this, "action_name");
    }

    @Override
    public Image actionIcon() {
        Image icon;
        icon = new BuffIcon(BuffIndicator.WANTED, true);
        return icon;
    }

    @Override
    public void doAction() {
        if (hero.buff(WantedCoolDown.class) == null) {
            GameScene.selectCell(wanted);
        } else {
            ActionIndicator.clearAction(this);
        }
    }

    private CellSelector.Listener wanted = new CellSelector.Listener() {

        @Override
        public void onSelect(Integer cell) {
            if (cell == null) return;
            final Char enemy = Actor.findChar( cell );
            if (enemy == null || enemy instanceof NPC || !Dungeon.level.heroFOV[cell] || enemy == hero || enemy.alignment == Char.Alignment.ALLY){
                GLog.w(Messages.get(WantedTracker.class, "no_target"));
            } else {
                WantedTracker buff = hero.buff(WantedTracker.class);
                Buff.affect(enemy, Wanted.class);
                if (hero.hasTalent(Talent.INTIMIDATION)) {
                    Buff.affect(enemy, Terror.class, 3*hero.pointsInTalent(Talent.INTIMIDATION));
                }
                if (hero.pointsInTalent(Talent.SURRENDER) > 2 && Random.Int(10) < 1 && enemy instanceof Mob){
                    if (!enemy.isImmune(ScrollOfSirensSong.Enthralled.class)){
                        AllyBuff.affectAndLoot((Mob)enemy, hero, ScrollOfSirensSong.Enthralled.class);

                    } else {
                        Buff.affect( enemy, Charm.class, Charm.DURATION ).object = hero.id();

                    }
                    enemy.sprite.centerEmitter().burst( Speck.factory( Speck.HEART ), 10 );
                    enemy.buff(Wanted.class).detach();
                }
                Buff.affect(hero, WantedCoolDown.class).set();
                buff.clearAction();
                hero.busy();
                Sample.INSTANCE.play( Assets.Sounds.READ );
                ((HeroSprite)hero.sprite).read();
                hero.sprite.centerEmitter().start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
                hero.next();
            }
        }

        @Override
        public String prompt() {
            return Messages.get(WantedTracker.class, "prompt");
        }
    };

    public void indicate() {
        ActionIndicator.setAction( this );
    }

    public void clearAction() {
        ActionIndicator.clearAction( this );
    }



    public static class Wanted extends Buff {

        {
            type = buffType.NEUTRAL;
            announced = true;
        }

        @Override
        public int icon() {
            return BuffIndicator.WANTED;
        }

        @Override
        public boolean act() {
            spend(TICK);
            return true;
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", 10+5*Dungeon.hero.pointsInTalent(Talent.JUSTICE_BULLET));
        }
    }



    public static class WantedCoolDown extends Buff {

        float duration;
        int maxDuration = 50;

        {
            type = buffType.NEUTRAL;
        }

        public void set() {
            duration = maxDuration;
        }

        public void reduce() {
            duration -= maxDuration/3f*hero.pointsInTalent(Talent.INVEST_END);
            if (duration <= 0) {
                detach();
            }
            BuffIndicator.refreshHero();
        }

        @Override
        public boolean act() {
            duration-=TICK;
            spend(TICK);
            if (duration <= 0) {
                detach();
            }
            return true;
        }

        private static final String DURATION  = "duration";
        private static final String MAX_DURATION  = "maxDuration";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(DURATION, duration);
            bundle.put(MAX_DURATION, maxDuration);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            duration = bundle.getFloat( DURATION );
            maxDuration = bundle.getInt( MAX_DURATION );
        }

        @Override
        public int icon() {
            return BuffIndicator.TIME;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0xFF2A00);
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, (maxDuration - duration) / maxDuration);
        }

        @Override
        public void detach() {
            WantedTracker buff = hero.buff(WantedTracker.class);
            buff.indicate();
            super.detach();
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", duration);
        }
    }
}

