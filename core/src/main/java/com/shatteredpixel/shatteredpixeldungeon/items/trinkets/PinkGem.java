package com.shatteredpixel.shatteredpixeldungeon.items.trinkets;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class PinkGem extends Trinket {
    {
        image = ItemSpriteSheet.PINK_GEM;
    }

    @Override
    protected int upgradeEnergyCost() {
        return 6+2*level();
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", (int)(100*dropChance(buffedLvl())), ringLevel(buffedLvl()), Messages.decimalFormat("#.#", dropChanceMultiplier(buffedLvl())));
    }

    public static void proc(Char enemy) {
        if (trinketLevel(PinkGem.class) != -1 && Random.Float() < dropChance()) {
            Buff.affect(enemy, PinkGem.LuckProc.class);
        }
    }

    public static float dropChance() {
        return PinkGem.dropChance(trinketLevel(PinkGem.class));
    }

    public static float dropChance(int level) {
        switch (level) {
            default:
                return 0;
            case 0:
                return 0.1f;
            case 1:
                return 0.25f;
            case 2:
                return 0.35f;
            case 3:
                return 0.5f;
        }
    }

    public static int ringLevel() {
        return PinkGem.ringLevel(trinketLevel(PinkGem.class));
    }

    public static int ringLevel(int level) {
        switch (level) {
            case 0: default:
                return 0; //60% common, 30% uncommon, 10% rare
            case 1:
                return 3; //48% common, 36% uncommon, 16% rare
            case 2:
                return 6; //36% common, 42% uncommon, 22% rare
            case 3:
                return 9; //24% common, 48% uncommon, 28% rare
        }
    }

    public static float dropChanceMultiplier() {
        return PinkGem.dropChanceMultiplier(trinketLevel(PinkGem.class));
    }

    public static float dropChanceMultiplier(int level) {
        switch (level) {
            default:
                return 1;
            case 0:
                return 0.8f;
            case 1:
                return 0.5f;
            case 2:
                return 0.3f;
            case 3:
                return 0f;
        }
    }

    //used to keep track of whether a luck proc is incoming. see Mob.die()
    public static class LuckProc extends Buff {

        public int ringLevel = PinkGem.ringLevel();

        @Override
        public boolean act() {
            detach();
            return true;
        }

        public Item genLoot(){
            detach();
            return RingOfWealth.genConsumableDrop(ringLevel);
        }
    }
}
