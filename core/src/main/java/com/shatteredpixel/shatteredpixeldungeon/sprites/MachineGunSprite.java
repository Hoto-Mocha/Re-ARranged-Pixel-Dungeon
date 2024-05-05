package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.MachineGun;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

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

        attack = new Animation( 10, false );
        attack.frames( frames, 1, 2, 3, 4, 3, 4, 0 );

        zap = attack.clone();

        play( idle );
    }

    @Override
    public void showAlert() {
        //do nothing
    }

    @Override
    public void showSleep() {
        //do nothing
    }

    @Override
    public void showLost() {
        //do nothing
    }

    public void zap( int cell ) {
        final Ballistica aim = new Ballistica(ch.pos, cell, Ballistica.PROJECTILE);

        cellToAttack = aim.collisionPos;

        turnTo( ch.pos, cell );
        play( zap );

        ((MissileSprite)parent.recycle( MissileSprite.class )).
                reset( this, cellToAttack, new MachineGun.MachineGunBullet(), new Callback() {
                    @Override
                    public void call() {
                        ch.onAttackComplete();
                    }
                } );
    }
}
