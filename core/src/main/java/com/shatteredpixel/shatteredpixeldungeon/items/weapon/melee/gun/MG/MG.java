package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.MG;

import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
public class MG extends Gun {

    {
        max_round = 4;
        round = max_round;
        shotPerShoot = 3;
        shootingAccuracy = 0.7f;
        adjacentShootingAccuracy = 0.3f;
    }

    @Override
    public int baseBulletMax(int lvl) {
        return 2 * (tier()+2) +
                Math.round(0.5f * lvl * (tier()+2)); //2강 당 3/4/5/6/7 증가
    }

    @Override
    public Bullet knockBullet(){
        return new MGBullet();
    }

    public class MGBullet extends Bullet {
        {
            image = ItemSpriteSheet.TRIPLE_BULLET;
        }
    }

}
