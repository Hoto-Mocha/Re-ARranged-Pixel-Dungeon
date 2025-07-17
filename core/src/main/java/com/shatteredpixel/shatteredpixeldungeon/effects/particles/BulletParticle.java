package com.shatteredpixel.shatteredpixeldungeon.effects.particles;

import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class BulletParticle extends PixelParticle.Shrinking {
    //픽셀 파티클 정리
    //speed = PointF 클래스의 인스턴스로, x, y 좌표로 이동하는 초기 속도
    //acc = PointF 클래스의 인스턴스로, x, y 좌표로 이동하는 가속도. 시간이 지남에 따라 초기 속도에 가속도*(lifespan에 대해 지난 시간이 가지는 비율)이 더해진다.
    //예를 들어, 가속도가 (10, 10)이고, 속도가 (1, 3)일 때 lifespan이 2(초)면 1초가 흘렀을 때 속도는 6, 8이 되며, 2초가 되었을 때 속도는 11, 13이 된다.
    //x값이 양수면 오른쪽으로, y값이 양수면 아래쪽으로 향한다.
    //x, y값은 상대좌표다. 애초에 좌표가 속도이기 때문

    public static Emitter.Factory factory(PointF destination) {
        return new Emitter.Factory() {
            @Override
            public void emit(Emitter emitter, int index, float x, float y) {
                ((BulletParticle)emitter.recycle( BulletParticle.class )).reset( x, y, destination );
            }
            @Override
            public boolean lightMode() {
                return true;
            }
        };
    }

    private final int randomSpeedRange = 1;
    private final float speedMulti = 3f + Random.Float(-randomSpeedRange, randomSpeedRange);

    public void reset( float x, float y, PointF destination ) {
        // 속도와 벡터의 특징을 생각해 보자.
        // 1. 속도는 크기와 방향을 가지고 있는 벡터이다.
        // 2. 벡터의 시작점을 옮겨도 벡터는 변하지 않는다.
        // 절대좌표계에서, 만약 A(1,3)에서 B(5,6)으로 가는 벡터가 있을 때, 이 벡터 AB는 어떻게 되는가?
        // AB(5-1,6-3) = AB(4,3)이 된다.
        // 즉 A(1,3)에서 B(5,6)으로 가는 벡터는 (0,0)에서 (4,3)으로 가는 벡터와 똑같다는 것이다.
        // 파티클도 똑같다. 지금 이 메서드의 A(x,y)는 파티클이 시작하는 지점의 절대좌표이고, B(destination.x,destination.y)는 파티클이 이동하는 목표지점의 절대좌표이다.
        // 그렇다면 속도를 어떻게 정의해야 하는가?
        // AB(destination.x - x,destination.y - y)인 것이다.

        // 만약 속도를 더 빠르게 하고 싶다면 벡터에 그 배율(이하 n)을 나누어 주면 된다. 그렇게 하면 방향은 같되 크기는 n배가 된다.
        // 다만 시간이 그대로일 경우 파티클의 이동 거리는 원점에서 목표한 지점까지의 거리의 n배만큼 더 가게 된다.
        // 왜냐하면 1초에 (x,y)까지 도달하는 벡터가 속도, 여기에서의 speed이기 때문이다. 1초에 (nx,ny)까지 도달하는 게 되지 않겠는가?
        // 따라서 시간을 n으로 나눔으로써 1/n초에 (nx,ny)/n = (x,y)까지 도달하게 만들 수 있다.
        revive();

        int randomCellRange = 8;

        color( 0xEE7722 );

        this.x = x;
        this.y = y;

        left = lifespan = 1/speedMulti;

        size = Random.Float(1, 3);

        PointF randomDestination = new PointF(destination.x + Random.Float(-randomCellRange, randomCellRange), destination.y + Random.Float(-randomCellRange, randomCellRange));

        float speedX = (randomDestination.x - this.x)*speedMulti*1.1f; //초기 속도 1.1배 보정
        float speedY = (randomDestination.y - this.y)*speedMulti*1.1f; //초기 속도 1.1배 보정

        // 예시: 초기 속도와 최종 속도가 똑같은 공식
        // acc.set( 0, 0 );
        // speed.set( speedX, speedY );

        //도착 지점에서 속도가 0이 되는 공식
        float accX = -2*(randomDestination.x - this.x)*speedMulti*speedMulti;
        float accY = -2*(randomDestination.y - this.y)*speedMulti*speedMulti;

        acc.set( accX, accY );
        speed.set( 2*speedX, 2*speedY );
    }

    @Override
    public void update() {
        super.update();
        am = left/lifespan;
    }
}