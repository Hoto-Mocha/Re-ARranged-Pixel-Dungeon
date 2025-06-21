package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.MachineGun;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class MachineGunSprite extends MobSprite {

    private int cellToAttack;

    public MachineGunSprite() {
        super();

        texture( Assets.Sprites.MACHINEGUN );

        TextureFilm frames = new TextureFilm( texture, 16, 11 );

        idle = new Animation( 20, true );
        idle.frames( frames, 0 );

        die = new Animation( 20, false );
        die.frames( frames, 0 );

        attack = new Animation( 15, false );
        attack.frames( frames, 1, 2, 3, 4, 3, 4, 0 );

        zap = attack.clone();

        play( idle );
    }

    @Override
    public void attack( int cell ) {
        cellToAttack = cell;
        zap(cell);

        CellEmitter.get(ch.pos).burst(SmokeParticle.FACTORY, 2);
        CellEmitter.center(ch.pos).burst(BlastParticle.FACTORY, 2);
        Sample.INSTANCE.play( Assets.Sounds.HIT_CRUSH, 1, Random.Float(0.33f, 0.66f) );
        ((MissileSprite)parent.recycle( MissileSprite.class )).
                reset( this, cellToAttack, new MachineGunBullet(), new Callback() {

                    @Override
                    public void call() {
                        ch.onAttackComplete();
                    }
                } );
    }

    @Override
    public void onComplete(Animation anim) {
        if (anim == zap) {
            idle();
        } else {
            super.onComplete(anim);
        }
    }

    public static class MachineGunBullet extends Item {
        {
            image = ItemSpriteSheet.SINGLE_BULLET;
        }
    }
}
