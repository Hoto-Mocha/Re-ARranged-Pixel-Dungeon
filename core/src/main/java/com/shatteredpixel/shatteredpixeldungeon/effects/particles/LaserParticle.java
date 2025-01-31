package com.shatteredpixel.shatteredpixeldungeon.effects.particles;

import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class LaserParticle extends PixelParticle {
    public static final Emitter.Factory FACTORY = new Emitter.Factory() {
        @Override
        public void emit( Emitter emitter, int index, float x, float y ) {
            ((LaserParticle)emitter.recycle( LaserParticle.class )).reset( x, y );
        }
        @Override
        public boolean lightMode() {
            return true;
        }
    };

    public static final Emitter.Factory BURST = new Emitter.Factory() {
        @Override
        public void emit( Emitter emitter, int index, float x, float y ) {
            ((LaserParticle)emitter.recycle( LaserParticle.class )).resetBurst( x, y );
        }
        @Override
        public boolean lightMode() {
            return true;
        }
    };

    public LaserParticle() {
        super();

        lifespan = 1f;
        color( 0x5CDAFF );

        speed.polar( Random.Float( PointF.PI2 ), Random.Float( 24, 32 ) );
    }

    public void reset( float x, float y ) {
        revive();

        left = lifespan;

        this.x = x - speed.x * lifespan;
        this.y = y - speed.y * lifespan;
    }

    @Override
    public void update() {
        super.update();

        float p = left / lifespan;
        am = p < 0.5f ? p * p * 4 : (1 - p) * 2;
        size( Random.Float( 5 * left / lifespan ) );
    }

    public void resetBurst( float x, float y ) {
        revive();

        this.x = x;
        this.y = y;

        speed.polar( Random.Float( PointF.PI2 ), Random.Float( 16, 32 ) );

        left = lifespan;
    }
}
