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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ShovelDigCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Lucky;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Spade extends Shovel {

    public static final String AC_DIG	= "DIG";

    {
        defaultAction = AC_DIG;

        image = ItemSpriteSheet.SPADE;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1.3f;

        tier = 5;

        unique = true;
        bones = false;
    }

    @Override
    public void Dig() {
        curUser.spend(Actor.TICK);
        curUser.busy();
        Sample.INSTANCE.play(Assets.Sounds.TRAMPLE, 2, 1.1f);
        curUser.sprite.operate(curUser.pos);

        GLog.i(Messages.get(this, "dig"));

        int flowers = (Random.Int(1) < hero.pointsInTalent(Talent.FLOWER_BED)) ? 1 : 0;

        if (hero.subClass == HeroSubClass.RESEARCHER) {
            ArrayList<Integer> positions = new ArrayList<>();
            for (int i : PathFinder.NEIGHBOURS25) {
                positions.add(i);
            }
            Random.shuffle( positions );
            for (int i : positions) {
                int c = Dungeon.level.map[hero.pos + i];
                if ( c == Terrain.EMPTY || c == Terrain.EMPTY_DECO
                        || c == Terrain.EMBERS || c == Terrain.GRASS
                        || c == Terrain.WATER || c == Terrain.FURROWED_GRASS){
                    if (flowers > 0) {
                        Dungeon.level.plant((Plant.Seed) Generator.randomUsingDefaults(Generator.Category.SEED), hero.pos);
                        flowers--;
                    } else {
                        if (Random.Int(8) < 1+hero.pointsInTalent(Talent.ALIVE_GRASS)) {
                            Level.set(hero.pos + i, Terrain.HIGH_GRASS);
                        } else {
                            Level.set(hero.pos + i, Terrain.FURROWED_GRASS);
                        }
                    }
                    Char enemy = Actor.findChar( hero.pos + i );
                    if (enemy instanceof Mob && hero.hasTalent(Talent.ROOT)) {
                        Buff.affect(enemy, Roots.class, 1+hero.pointsInTalent(Talent.ROOT));
                    }
                    GameScene.updateMap(hero.pos + i);
                    CellEmitter.get( hero.pos + i ).burst( LeafParticle.LEVEL_SPECIFIC, 4 );
                }
            }
        } else {
            ArrayList<Integer> positions = new ArrayList<>();
            for (int i : PathFinder.NEIGHBOURS9) {
                positions.add(i);
            }
            Random.shuffle( positions );
            for (int i : positions) {
                int c = Dungeon.level.map[hero.pos + i];
                if ( c == Terrain.EMPTY || c == Terrain.EMPTY_DECO
                        || c == Terrain.EMBERS || c == Terrain.GRASS){
                    if (flowers > 0) {
                        Dungeon.level.plant((Plant.Seed) Generator.randomUsingDefaults(Generator.Category.SEED), hero.pos + i);
                        flowers--;
                    } else {
                        Level.set(hero.pos + i, Terrain.FURROWED_GRASS);
                    }
                    Char enemy = Actor.findChar( hero.pos + i );
                    if (enemy instanceof Mob && hero.hasTalent(Talent.ROOT)) {
                        Buff.affect(enemy, Roots.class, 1+hero.pointsInTalent(Talent.ROOT));
                    }
                    GameScene.updateMap(hero.pos + i);
                    CellEmitter.get( hero.pos + i ).burst( LeafParticle.LEVEL_SPECIFIC, 4 );
                }
            }
        }
        if (Random.Int(10) < hero.pointsInTalent(Talent.DETECTOR)) {
            Dungeon.level.drop(Lucky.genLoot(), hero.pos).sprite.drop();
            Lucky.showFlare(hero.sprite);
        }
        Buff.affect(hero, ShovelDigCoolDown.class, Math.max(20-2*buffedLvl(), 5));
        if (hero.hasTalent(Talent.GRAVEL_THROW)) {
            Buff.affect(hero, CrippleTracker.class, 1+hero.pointsInTalent(Talent.GRAVEL_THROW));
        }
    }
}
