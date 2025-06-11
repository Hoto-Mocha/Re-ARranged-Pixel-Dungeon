package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.HG;

import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
public class HG extends Gun {

    {
        max_round = 4;
        round = max_round;
        shootingSpeed = 0.5f;
        reload_time = 1f;
        adjacentShootingAccuracy = 2f;
    }

    @Override
    public int baseBulletMax(int lvl) {
        return 2 * (tier() + 1) +
                lvl * (tier() + 1);
    }

    @Override
    public Bullet knockBullet(){
        return new HGBullet();
    }

    public class HGBullet extends Bullet {
        {
            image = ItemSpriteSheet.SINGLE_BULLET;
        }
    }
}
