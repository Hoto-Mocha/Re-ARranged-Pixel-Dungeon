package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.HG;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
public class HG extends Gun {

    {
        max_round = 4;
        round = max_round;
        shootingSpeed = 0.5f;
        reload_time = 1f;
    }

    @Override
    public int bulletMax(int lvl) {
        return 2 * (tier + 1) +
                lvl * (tier + 1) +
                RingOfSharpshooting.levelDamageBonus(hero);
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
