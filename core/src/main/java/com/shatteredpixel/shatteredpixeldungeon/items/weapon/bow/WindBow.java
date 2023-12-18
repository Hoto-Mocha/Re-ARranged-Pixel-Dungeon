package com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class WindBow extends SpiritBow {

    {
        image = ItemSpriteSheet.WIND_BOW;
    }

    @Override
    public int max(int lvl) {
        int dmg = 6 + (int)(Dungeon.hero.lvl/2.5f)
                + 2*RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
                + (curseInfusionBonus ? 2 + Dungeon.hero.lvl/15 : 0);
        return Math.max(min(lvl), Math.round(dmg*0.7f));
    }

    @Override
    public int STRReq(int lvl) {
        return super.STRReq(lvl)-2;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        int dmg = super.proc(attacker, defender, damage);

        return dmg;
    }

    @Override
    public SpiritArrow knockArrow(){
        return new WindArrow();
    }

    public class WindArrow extends SpiritArrow {
        {
            image = ItemSpriteSheet.ARROW_WIND;
        }

        @Override
        protected void onThrow( int cell ) {
            Char enemy = Actor.findChar( cell );
            if (enemy == null || enemy == curUser) {
                parent = null;
                Splash.at( cell, 0xCCFFFFFF, 1 );
            } else {
                for (int i = 0; i < 1+ Random.Int(3); i++) { //hits 1~3 times in a row
                    if (!enemy.isAlive()) break;
                    if (!curUser.shoot( enemy, this )) {
                        Splash.at(cell, 0xCCFFFFFF, 1+Random.Int(2));
                    }
                }
                if (sniperSpecial && WindBow.this.augment != Augment.SPEED) sniperSpecial = false;
            }
        }
    }

}
