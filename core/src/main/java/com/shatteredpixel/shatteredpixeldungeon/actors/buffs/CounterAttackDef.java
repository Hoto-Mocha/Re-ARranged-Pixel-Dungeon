package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class CounterAttackDef extends Buff {
    { actPriority = MOB_PRIO+1;}

    public Char enemy;

    @Override
    public boolean act() {
        target.sprite.attack(enemy.pos, new Callback() {
            @Override
            public void call() {
                AttackIndicator.target(enemy);
                if (hero.attack(enemy)){
                    Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
                }

                next();
            }
        });
        detach();
        return false;
    }
}