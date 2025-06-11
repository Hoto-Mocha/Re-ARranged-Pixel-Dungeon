package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.SG;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class SG extends Gun {

    {
        max_round = 2;
        round = max_round;
        shotPerShoot = 5;
        shootingAccuracy = 1f;
        adjacentShootingAccuracy = 3f;
        spread = true;
    }

    @Override
    public int bulletUse() {
        return maxRound()-round;
    }

    @Override
    public int baseBulletMax(int lvl) {
        return  (tier()+1) +
                Math.round(0.5f * lvl * (tier()+1)); //2강 당 2/3/4/5/6 증가
    }

    @Override
    public Bullet knockBullet(){
        return new SGBullet();
    }

    public class SGBullet extends Bullet {
        {
            image = ItemSpriteSheet.TRIPLE_BULLET;
        }

        @Override
        protected float adjacentAccFactor(Char owner, Char target) {
            return super.adjacentAccFactor(owner, target)*3f;
        }
    }
}
