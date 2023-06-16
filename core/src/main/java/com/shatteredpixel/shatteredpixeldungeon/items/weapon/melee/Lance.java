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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LanceBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Door;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class Lance extends MeleeWeapon {

    {
        image = ItemSpriteSheet.LANCE;
        hitSound = Assets.Sounds.HIT_STAB;
        hitSoundPitch = 1.2f;

        tier = 6;
        RCH = 2;
    }

    @Override
    public int max(int lvl) {
        return  4*(tier) +    //24 base
                lvl*(tier);     //scaling +6 per +1
    }

    @Override
    public String targetingPrompt() {
        return Messages.get(this, "prompt");
    }

    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        dashAbility(hero, target, this);
    }

    public static void dashAbility(Hero hero, Integer target, MeleeWeapon wep){
        if (target == null || target == -1){
            return;
        }

        int range = 3;

        if (Dungeon.level.distance(hero.pos, target) > range){
            GLog.w(Messages.get(MeleeWeapon.class, "ability_bad_position"));
            return;
        }

        Ballistica dash = new Ballistica(hero.pos, target, Ballistica.PROJECTILE);

        if (!dash.collisionPos.equals(target)
                || Actor.findChar(target) != null
                || (Dungeon.level.solid[target] && !Dungeon.level.passable[target])){
            GLog.w(Messages.get(MeleeWeapon.class, "ability_bad_position"));
            return;
        }
        wep.beforeAbilityUsed(hero, null);

        Buff.affect(hero, LanceBuff.class).setDamageFactor(2*(1+Dungeon.level.distance(hero.pos, target)), hero.belongings.secondWep == wep);

        hero.busy();
        Sample.INSTANCE.play(Assets.Sounds.MISS);
        hero.sprite.emitter().start(Speck.factory(Speck.JET), 0.01f, Math.round(4 + 2*Dungeon.level.trueDistance(hero.pos, target)));
        hero.sprite.jump(hero.pos, target, 0, 0.1f, new Callback() {
            @Override
            public void call() {
                if (Dungeon.level.map[hero.pos] == Terrain.OPEN_DOOR) {
                    Door.leave( hero.pos );
                }
                hero.pos = target;
                Dungeon.level.occupyCell(hero);
                hero.next();
            }
        });
        wep.afterAbilityUsed(hero);
    }
}
