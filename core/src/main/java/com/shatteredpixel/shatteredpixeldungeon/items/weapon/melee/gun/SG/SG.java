package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.SG;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class SG extends Gun {
    //근접 이외에 탄환이 맞지 않음. Hero.attackSkill()참고
    //탄환 기습 불가. Hero.canSurpriseAttack()참고

    {
        max_round = 2;
        round = max_round;
        shotPerShoot = 5;
        shootingAccuracy = 3f;  //투척무기는 근접 시 0.5배 보정이 기본으로 있기 때문에 배율이 높음
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        if (action.equals(AC_RELOAD)) {
            reload();
        }
        super.execute(hero, action);
    }

    @Override
    public int bulletMax(int lvl) {
        return  (tier+1) +
                Math.round(0.5f * lvl * (tier+1)) + //2강 당 2/3/4/5/6 증가
                RingOfSharpshooting.levelDamageBonus(Dungeon.hero);
    }

    @Override
    public Bullet knockBullet(){
        return new SGBullet();
    }

    public class SGBullet extends Bullet {
        {
            image = ItemSpriteSheet.TRIPLE_BULLET;
        }
    }
}
