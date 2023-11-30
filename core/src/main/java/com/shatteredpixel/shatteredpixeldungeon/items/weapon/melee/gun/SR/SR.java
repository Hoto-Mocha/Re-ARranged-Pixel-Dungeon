package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.SR;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
public class SR extends Gun {

    {
        max_round = 2;
        round = max_round;
        reload_time = 3f;
        shootingAccuracy = 1.5f;
    }

    @Override
    public int bulletMax(int lvl) {
        return 4 * (tier+2) +
                lvl * (tier+2) +
                RingOfSharpshooting.levelDamageBonus(hero);
    }

    @Override
    public Bullet knockBullet(){
        return new SRBullet();
    }

    public class SRBullet extends Bullet {
        {
            image = ItemSpriteSheet.SNIPER_BULLET;
        }
    }
}
