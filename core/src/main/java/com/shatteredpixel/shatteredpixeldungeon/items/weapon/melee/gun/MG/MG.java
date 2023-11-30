package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.MG;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
public class MG extends Gun {

    {
        max_round = 4;
        round = max_round;
        shotPerShoot = 3;
        shootingAccuracy = 0.7f;
    }

    @Override
    public int bulletMax(int lvl) {
        return 2 * (tier+2) +
                Math.round(0.5f * lvl * (tier+2)) + //2강 당 3/4/5/6/7 증가
                RingOfSharpshooting.levelDamageBonus(hero);
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
