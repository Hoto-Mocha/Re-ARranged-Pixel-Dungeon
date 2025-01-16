/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Door;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MimicSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class TempleEbonyMimic extends Mimic {

    {
        spriteClass = MimicSprite.Ebony.class;
    }

    @Override
    public String name() {
        if (alignment == Alignment.NEUTRAL){
            return Messages.get(EbonyMimic.class, "hidden_name");
        } else {
            return super.name();
        }
    }

    @Override
    public String description() {
        if (alignment == Alignment.NEUTRAL){
            return Messages.get(EbonyMimic.class, "hidden_desc");
        } else {
            return super.description();
        }
    }

    @Override
    public boolean stealthy() {
        return true;
    }

    public void stopHiding(){
        state = HUNTING;
        if (sprite != null) sprite.idle();
        if (Actor.chars().contains(this) && Dungeon.level.heroFOV[pos]) {
            enemy = Dungeon.hero;
            target = Dungeon.hero.pos;
            GLog.w(Messages.get(EbonyMimic.class, "reveal") );
            CellEmitter.get(pos).burst(Speck.factory(Speck.STAR), 10);
            Sample.INSTANCE.play(Assets.Sounds.MIMIC, 1, 0.85f);
        }
        if (Actor.chars().contains(this) && Dungeon.level.map[pos] == Terrain.DOOR){
            Door.enter( pos );
        }
    }

    @Override
    public int damageRoll() {
        if (alignment == Alignment.NEUTRAL){
            return Math.round(super.damageRoll()*1.75f); //BIG damage on surprise
        } else {
            return super.damageRoll();
        }
    }

    @Override
    public void setLevel(int level) {
        super.setLevel(Math.round(level*1.25f));
    }

    @Override
    protected void generatePrize( boolean useDecks ) {
        return;
    }

}
