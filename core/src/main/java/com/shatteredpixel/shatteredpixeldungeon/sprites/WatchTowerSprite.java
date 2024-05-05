package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class WatchTowerSprite extends MobSprite {
    public WatchTowerSprite() {
        super();

        texture( Assets.Sprites.WATCHTOWER );

        TextureFilm frames = new TextureFilm( texture, 9, 11 );

        idle = new Animation( 20, true );
        idle.frames( frames, 0 );

        die = new Animation( 20, false );
        die.frames( frames, 0 );

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

    @Override
    public void turnTo(int from, int to) {
        //do nothing
    }
}
