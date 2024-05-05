package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class EnhancedMachete extends Machete {
    {
        defaultAction = AC_SLASH;

        image = ItemSpriteSheet.ENHANCED_MACHETE;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1.1f;

        tier = 5;
        RCH = 2;

        unique = true;
        bones = false;
    }
}
