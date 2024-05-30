package com.shatteredpixel.shatteredpixeldungeon.items.trinkets;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class BrokenMagnifyingGlass extends Trinket {
    {
        image = ItemSpriteSheet.MAGNIFYING_GLASS;
    }

    @Override
    protected int upgradeEnergyCost() {
        return 6+2*level();
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", (int)(100*doorNoticeChance(buffedLvl())), (int)(100*trapNoticeChance(buffedLvl())));
    }

    public static float trapNoticeChance() {
        return BrokenMagnifyingGlass.trapNoticeChance(trinketLevel(BrokenMagnifyingGlass.class));
    }

    public static float trapNoticeChance(int level) {
        switch (level) {
            default:
                //unintentional trap detection scales from 40% at floor 0 to 30% at floor 25
                return 0.4f - (Dungeon.depth / 250f);
            case 0:
                return 0.3f;
            case 1:
                return 0.2f;
            case 2:
                return 0.05f;
            case 3:
                return 0f;
        }
    }

    public static float doorNoticeChance() {
        return BrokenMagnifyingGlass.doorNoticeChance(trinketLevel(BrokenMagnifyingGlass.class));
    }

    public static float doorNoticeChance(int level) {
        switch (level) {
            default:
                //unintentional door detection scales from 20% at floor 0 to 0% at floor 20
                return 0.2f - (Dungeon.depth / 100f);
            case 0:
                return 0.2f;
            case 1:
                return 0.3f;
            case 2:
                return 0.7f;
            case 3:
                return 1f;
        }
    }
}
