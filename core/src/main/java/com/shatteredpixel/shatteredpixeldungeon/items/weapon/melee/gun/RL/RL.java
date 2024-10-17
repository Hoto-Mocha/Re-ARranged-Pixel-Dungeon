package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.RL;

import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class RL extends Gun {

    {
        max_round = 2;
        round = max_round;
        explode = true;
    }

    @Override
    public int baseBulletMax(int lvl) {
        return 4 * (tier() + 2) +
                lvl * (tier() + 2);
    }

    @Override
    public int bulletUse() {
        return Math.max(0, (maxRound()-round)*3);
    }

    @Override
    public Bullet knockBullet(){
        return new RLBullet();
    }

    public class RLBullet extends Bullet {
        {
            image = ItemSpriteSheet.ROCKET;
        }
    }

}
