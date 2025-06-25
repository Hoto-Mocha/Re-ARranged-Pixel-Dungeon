package com.shatteredpixel.shatteredpixeldungeon.items.trinkets;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class GrindStone extends Trinket {

    {
        image = ItemSpriteSheet.GRINDSTONE;
    }

    @Override
    protected int upgradeEnergyCost() {
        //4 -> 6(10) -> 8(18) -> 10(28)
        return 4+2*level();
    }

    @Override
    public String statsDesc() {
        if (isIdentified()){
            return Messages.get(this, "stats_desc", drMin(buffedLvl()), drMax(buffedLvl()));
        } else {
            return Messages.get(this, "typical_stats_desc", drMin(0), drMax(0));
        }
    }

    public static int drMin(int level) {
        return 1+level;
    }

    public static int drMax(int level) {
        return 4*(1+level);
    }

    public static int drRoll(){
        return drRoll(trinketLevel(GrindStone.class));
    }

    public static int drRoll(int level) {
        if (level <= -1){
            return 0;
        } else {
            return Random.NormalIntRange(drMin(level), drMax(level));
        }
    }
}
