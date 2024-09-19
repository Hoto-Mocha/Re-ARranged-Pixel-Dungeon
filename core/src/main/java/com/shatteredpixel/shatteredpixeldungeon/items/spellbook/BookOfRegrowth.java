package com.shatteredpixel.shatteredpixeldungeon.items.spellbook;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.MirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfRegrowth;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.BArray;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BookOfRegrowth extends SpellBook {

    {
        image = ItemSpriteSheet.BOOK_OF_REGROWTH;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_READ)) {
            if (hero.buff(SpellBookCoolDown.class) != null) {
                GLog.w(Messages.get(this, "cooldown"));
            } else {
                Buff.affect(hero, SpellBookCoolDown.class).set(100);
                readEffect();
            }
        }
    }

    @Override
    public void readEffect() {
        if (spawnPlants(hero.pos, (0.1f+0.01f*hero.lvl)*(1+0.5f*hero.pointsInTalent(Talent.SPELL_ENHANCE)), 5)) {
            GLog.p(Messages.get(this, "plant"));
            Dungeon.observe();
        }
    }

    @Override
    public String info() {
        String info = super.info();
        if (Dungeon.hero != null && Dungeon.hero.buff(SpellBookCoolDown.class) == null) {
            info += "\n\n" + Messages.get(this, "time",
                    5,
                    new DecimalFormat("#.##").format(100*((0.1f+0.01f*hero.lvl)*(1+0.5f*hero.pointsInTalent(Talent.SPELL_ENHANCE)))));
        }
        return info;
    }

    public static boolean spawnPlants( int centerPos, float rate, int distance ){
        rate = Math.min(rate, 1);
        ArrayList<Integer> plantPoints = new ArrayList<>();
        PathFinder.buildDistanceMap( centerPos, BArray.or( Dungeon.level.passable, Dungeon.level.avoid, null ), distance );
        for (int i = 0; i < PathFinder.distance.length; i++) {
            int terr = Dungeon.level.map[i];
            if (PathFinder.distance[i] < Integer.MAX_VALUE
                    && Dungeon.level.map[i] != Terrain.CHASM
                    && (terr == Terrain.EMPTY || terr == Terrain.EMBERS || terr == Terrain.EMPTY_DECO ||
                    terr == Terrain.GRASS || terr == Terrain.HIGH_GRASS || terr == Terrain.FURROWED_GRASS)) {
                plantPoints.add(i);
            }
        }
        Random.shuffle(plantPoints);

        int spawned = 0;
        int amount = Math.round(plantPoints.size()*rate);
        for (int pos : plantPoints) {
            if (amount <= 0) {
                break;
            }

            if (Random.Float() < 0.25f) {
                Dungeon.level.plant((Plant.Seed) Generator.randomUsingDefaults(Generator.Category.SEED), pos);
            } else {
                Level.set(pos, Terrain.HIGH_GRASS);
                GameScene.updateMap( pos );
            }
            CellEmitter.get( pos ).burst( LeafParticle.GENERAL, 6 );

            amount--;
            spawned++;
        }

        return spawned > 0;
    }
}
