package com.shatteredpixel.shatteredpixeldungeon.effects.particles;

import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;

public class AirSupportParticle extends PixelParticle {
    public static Emitter.Factory factory(Callback callback) {
        return new Emitter.Factory() {
            @Override
            public void emit(Emitter emitter, int index, float x, float y) {
                ((AirSupportParticle)emitter.recycle( AirSupportParticle.class )).reset( x, y, callback );
            }

            @Override
            public boolean lightMode() {
                return true;
            }
        };
    }

    public AirSupportParticle() {
        super();
        color(0xFF0000);
    }

    protected final float SPEED_MULTI = 3f;

    PointF startPoint;
    PointF endPoint;
    PointF delta;

    boolean shoot; //폭격 여부
    Callback callback = null;

    public void reset(float x, float y, Callback callback) {
        revive();

        endPoint = new PointF(x, y);
        startPoint = new PointF(x-40, y-80);
        delta = new PointF(endPoint.x - startPoint.x, endPoint.y - startPoint.y);
        this.x = startPoint.x;
        this.y = startPoint.y;

        this.shoot = false;
        this.callback = callback;

        left = lifespan = 1/ SPEED_MULTI;

        //종시에 초기 속도의 3배가 되는 공식
//        float speedX = (delta.x)/2f*SPEED_MULTI;
//        float speedY = (delta.y)/2f*SPEED_MULTI;
//
//        float accX = (delta.x)*SPEED_MULTI*SPEED_MULTI;
//        float accY = (delta.y)*SPEED_MULTI*SPEED_MULTI;

        //등속도 운동 공식
        float speedX = (delta.x)*SPEED_MULTI;
        float speedY = (delta.y)*SPEED_MULTI;

        float accX = 0;
        float accY = 0;

        acc.set( accX, accY );
        speed.set( speedX, speedY );
    }

    @Override
    public void update() {
        super.update();
        am = Math.min(1, ((lifespan-left)*5)/(lifespan)); //0~(lifespan/5)초에서 투명도가 0~1이 되도록 조절. 나머지는 항상 1
        float firstSize = 2;
        float sizeAdd = 4f;
        size(firstSize*(1+sizeAdd*(1-(left/lifespan)))); //처음에는 firstSize이었다가 끝에는 firstSize+sizeAdd가 됨

        if (!shoot && left <= 0) {
            shoot = true;

            this.callback.call();
        }
    }
}
