package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroAction;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.watabou.utils.Callback;

public class CounterAttackDef extends Buff {
    { actPriority = MOB_PRIO+1;}

    public Char enemy;

    @Override
    public boolean act() {
        if (Dungeon.hero.attack(enemy)) {
            target.sprite.attack(enemy.pos, new Callback() {
                @Override
                public void call() {
                    next();
                }
            });
        }
        detach();
        return false;
    }
}