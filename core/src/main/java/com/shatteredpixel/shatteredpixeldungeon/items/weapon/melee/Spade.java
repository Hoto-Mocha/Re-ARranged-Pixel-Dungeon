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

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ShovelDigCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Lucky;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Spade extends MeleeWeapon {

    public static final String AC_DIG	= "DIG";

    {
        defaultAction = AC_DIG;

        image = ItemSpriteSheet.SPADE;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1.3f;

        tier = 3;

        unique = true;
        bones = false;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_DIG);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_DIG)) {

            if (hero.buff(ShovelDigCoolDown.class) != null){
                GLog.w(Messages.get(this, "not_ready"));
            } else {
                Dig();
            }
        }
    }

    public void Dig() {
        curUser.spend(Actor.TICK);
        curUser.busy();
        Sample.INSTANCE.play(Assets.Sounds.TRAMPLE, 2, 1.1f);
        curUser.sprite.operate(curUser.pos);

        GLog.i(Messages.get(this, "dig"));

        for (int i : PathFinder.NEIGHBOURS9) {
            int c = Dungeon.level.map[hero.pos + i];
            if ( c == Terrain.EMPTY || c == Terrain.EMPTY_DECO
                    || c == Terrain.EMBERS || c == Terrain.GRASS){
                Level.set(hero.pos + i, Terrain.FURROWED_GRASS);
                GameScene.updateMap(hero.pos + i);
                CellEmitter.get( hero.pos + i ).burst( LeafParticle.LEVEL_SPECIFIC, 4 );
            }
        }
        if (Random.Int(10) < hero.pointsInTalent(Talent.DETECTOR)) {
            Dungeon.level.drop(Lucky.genLoot(), hero.pos).sprite.drop();
            Lucky.showFlare(hero.sprite);
        }
        Buff.affect(hero, ShovelDigCoolDown.class, Math.max(20-2*buffedLvl(), 5));
    }

    @Override
    public int max(int lvl) {
        if (hero.hasTalent(Talent.TAKEDOWN) && hero.buff(Talent.TakeDownCooldown.class) == null) {
            return 4*(tier+1) +
                    lvl*(tier+1) +
                    10 * hero.pointsInTalent(Talent.TAKEDOWN);
        } else
        return  4*(tier+1) +
                lvl*(tier+1);
    }
}
