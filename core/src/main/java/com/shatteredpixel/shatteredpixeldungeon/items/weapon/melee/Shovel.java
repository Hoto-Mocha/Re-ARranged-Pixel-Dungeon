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
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfRegrowth;
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

public class Shovel extends MeleeWeapon {

    public static final String AC_DIG	= "DIG";

    {
        defaultAction = AC_DIG;

        image = ItemSpriteSheet.ADVENTURER_SHOVEL;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1.1f;

        tier = 1;

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
            dig(hero.pos);
        }
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        return super.proc( attacker, defender, damage );
    }

    @Override
    public int max(int lvl) {
        return  5*(tier+1) +
                lvl*(tier+1);
    }

    public void dig(int pos) {
        ArrayList<Integer> tiles = new ArrayList<>();
        for (int i : PathFinder.NEIGHBOURS9) {
            int tile = pos + i;
            if (Dungeon.level.map[tile] == Terrain.GRASS || (Dungeon.level.map[tile] == Terrain.WATER && hero.hasTalent(Talent.WATER_ABSORB))) {
                tiles.add(tile);
            }
        }

        if (tiles.isEmpty()) {
            GLog.w(Messages.get(this, "no_grass"));
            return;
        }

        for (int tile : tiles) {
            if (Dungeon.level.map[tile] == Terrain.GRASS) {
                if (hero.hasTalent(Talent.RAPID_GROWTH) && Random.Float() < 0.2f*hero.pointsInTalent(Talent.RAPID_GROWTH)) {
                    Level.set(tile, Terrain.FURROWED_GRASS);
                } else {
                    Level.set(tile, Terrain.EMPTY);
                }
                GameScene.updateMap(tile);
                CellEmitter.get(tile).burst( LeafParticle.LEVEL_SPECIFIC, 4 );
                if (Random.Float() < 0.1f) {
                    Dungeon.level.drop(Generator.randomUsingDefaults(Generator.Category.SEED), pos).sprite.drop(tile);
                }
            } else if (Dungeon.level.map[tile] == Terrain.WATER) {
                Level.set(tile, Terrain.EMPTY);
                GameScene.updateMap( tile );
                CellEmitter.get( tile ).burst( Speck.factory( Speck.STEAM ), 10 );
                if (Random.Float() < 0.05f*hero.pointsInTalent(Talent.WATER_ABSORB)) {
                    Dungeon.level.drop(new Dewdrop(), tile).sprite.drop(tile);
                }
            }
        }

        curUser.spend(Actor.TICK);
        curUser.busy();
        Sample.INSTANCE.play(Assets.Sounds.TRAMPLE, 2, 1.1f);
        curUser.sprite.operate(curUser.pos);
    }
}
