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
import static com.watabou.utils.Random.NormalFloat;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PointF;

public class Demonization extends Buff implements ActionIndicator.Action {

    {
        actPriority = BUFF_PRIO - 1;
        announced = false;
    }

    public enum State{
        NORMAL, DEMONATED
    }
    public Demonization.State state = Demonization.State.NORMAL;

    @Override
    public boolean act() {
        if (state == State.DEMONATED) {
            int damage = 1;
            if (hero.HP > damage) {
                if (hero.buff(BlobImmunity.class) == null) {
                    target.damage( damage, this ); //deals no damage when hero has blobimmunity buff
                }
            } else {
                doAction();
            }
        }
        spend( TICK );
        return true;
    }

    public int evasionBonus( int heroLvl, int excessArmorStr ){
        if (state == State.DEMONATED) {
            return heroLvl/2 + excessArmorStr*Dungeon.hero.pointsInTalent(Talent.AFTERIMAGE);
        } else {
            return 0;
        }
    }

    public boolean isDemonated() {
        if (state == State.NORMAL) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void detach() {
        super.detach();
        ActionIndicator.clearAction(this);
    }

    @Override
    public int icon() {
        if (state == State.NORMAL) {
            return BuffIndicator.WEAPON;
        } else {
            return BuffIndicator.BERSERK;
        }
    }

    @Override
    public void tintIcon(Image icon) {
        if (state == State.NORMAL) {
            icon.hardlight(0xB2E9FF);
        } else {
            icon.hardlight(0xFFFF00);
        }
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        String desc;
        if (state == State.NORMAL) {
            desc = Messages.get(this, "off_state");
        } else {
            desc = Messages.get(this, "on_state");
        }
        return desc;
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
    public Image actionIcon() {
        Image icon;
        if (state == State.NORMAL) {
            if (((Hero)target).belongings.weapon() != null){
                icon = new ItemSprite(((Hero)target).belongings.weapon().image, null);
            } else {
                icon = new ItemSprite(new Item(){ {image = ItemSpriteSheet.WEAPON_HOLDER; }});
            }

            icon.tint(0xFFFF0000);
        } else {
            icon = new ItemSprite(new Item(){ {image = ItemSpriteSheet.SHEATH; }});
        }

        return icon;
    }

    @Override
    public void doAction() {
        if (state == State.NORMAL) {
            if (hero.buff(DemonizationCoolDown.class) == null) {
                state = State.DEMONATED;
                hero.sprite.showStatus( CharSprite.NEUTRAL, Messages.get(this, "name") );
                BuffIndicator.refreshHero();
                ActionIndicator.updateIcon();
                Sample.INSTANCE.play( Assets.Sounds.CHALLENGE );
                hero.sprite.centerEmitter().start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
                //GameScene.flash(0xFF0000);
                if (hero.hasTalent(Talent.FTL)) {
                    Buff.prolong(hero, EvasiveMove.class, hero.pointsInTalent(Talent.FTL) + 0.0001f);
                }
                hero.spendAndNext(Actor.TICK);
            } else {
                GLog.w(Messages.get(this, "not_prepared"));
            }
        } else {
            state = State.NORMAL;
            Buff.affect(hero, DemonizationCoolDown.class, DemonizationCoolDown.DURATION);
            Buff.affect(hero, Vulnerable.class, 5f);
            BuffIndicator.refreshHero();
            ActionIndicator.updateIcon();
            hero.spendAndNext(Actor.TICK);
        }
    }

    public void indicate() {
        ActionIndicator.setAction( this );
    }
}
