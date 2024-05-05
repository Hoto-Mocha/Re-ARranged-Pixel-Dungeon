package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

public class MortarSprite extends MobSprite {

    private int cellToAttack;

    public MortarSprite() {
        super();

        texture( Assets.Sprites.MORTAR);

        TextureFilm frames = new TextureFilm( texture, 12, 15 );

        idle = new Animation( 20, true );
        idle.frames( frames, 0 );

        die = new Animation( 20, false );
        die.frames( frames, 0 );

        attack = new Animation( 10, false );
        attack.frames( frames, 0, 1, 2, 3, 0);

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
        cellToAttack = cell;

        turnTo( ch.pos, cell );
        play( zap );

        ((MissileSprite)parent.recycle( MissileSprite.class )).
                reset( this, cellToAttack, new MineShot(), new Callback() {
                    @Override
                    public void call() {
                        ch.onAttackComplete();
                    }
                } );
    }

    public class MineShot extends Item {
        {
            image = ItemSpriteSheet.ROCKET;
        }
    }
}
