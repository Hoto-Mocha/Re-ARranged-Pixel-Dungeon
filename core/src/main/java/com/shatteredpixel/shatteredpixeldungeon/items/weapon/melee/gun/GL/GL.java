package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.GL;

import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class GL extends Gun {

    {
        max_round = 2;
        round = max_round;
        explode = true;
    }

    @Override
    public int baseBulletMax(int lvl) {
        return 6 * (tier() + 1) +
                lvl * (tier() + 1);
    }

    @Override
    public Bullet knockBullet(){
        return new GLBullet();
    }

    public class GLBullet extends Bullet {
        {
            image = ItemSpriteSheet.GRENADE_RED;
        }
    }

}
