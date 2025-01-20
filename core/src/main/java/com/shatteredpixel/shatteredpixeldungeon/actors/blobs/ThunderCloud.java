package com.shatteredpixel.shatteredpixeldungeon.actors.blobs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.BlobEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ThunderCloud extends Blob {

    @Override
    protected void evolve() {
        super.evolve();

        int cell;

        Fire fire = (Fire) Dungeon.level.blobs.get(Fire.class);
        for (int i = area.left; i < area.right; i++){
            for (int j = area.top; j < area.bottom; j++){
                cell = i + j*Dungeon.level.width();
                if (cur[cell] > 0) {
                    Dungeon.level.setCellToWater(true, cell);
                    if (fire != null){
                        fire.clear(cell);
                    }

                    if (Random.Float() < 0.15f) { //15% chance to make thunder
                        if (Dungeon.level.heroFOV[cell]) {
                            PointF cellPoint = DungeonTilemap.tileCenterToWorld(cell);

                            final float length = 6f;

                            float x = cellPoint.x;
                            float y = cellPoint.y-5f;

                            PointF from;
                            PointF to;
                            if (Random.Float() < 0.5f) {
                                from = new PointF(x-length, y-Random.Float(-length, length));
                                to = new PointF(x+length, y+Random.Float(-length, length));
                            } else {
                                from = new PointF(x-Random.Float(-length, length), y-length);
                                to = new PointF(x+Random.Float(-length, length), y+length);
                            }

                            ArrayList<Lightning.Arc> arcs = new ArrayList<>();
                            arcs.add(new Lightning.Arc(from, to));
                            Dungeon.hero.sprite.parent.add(new Lightning(arcs, null));
                            Sample.INSTANCE.play(Assets.Sounds.LIGHTNING, 0.7f, 1f);
                        }

                        Char ch = Actor.findChar(cell);
                        if (ch != null && !ch.isImmune(Electricity.class)) {
                            ch.damage(Random.NormalIntRange(5 + Dungeon.scalingDepth() / 4, 10 + Dungeon.scalingDepth() / 4), new Electricity()); //same with shocking dart
                        }
                    }

                    //fiery enemies take damage as if they are in toxic gas
                    Char ch = Actor.findChar(cell);
                    if (ch != null
                            && !ch.isImmune(getClass())
                            && Char.hasProp(ch, Char.Property.FIERY)){
                        ch.damage(1 + Dungeon.scalingDepth()/5, this);
                    }
                }
            }
        }
    }

    @Override
    public void use( BlobEmitter emitter ) {
        super.use( emitter );
        emitter.pour( Speck.factory( Speck.THUNDER_STORM ), 0.15f );
    }

    @Override
    public String tileDesc() {
        return Messages.get(this, "desc");
    }

}
