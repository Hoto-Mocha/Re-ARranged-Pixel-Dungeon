package com.shatteredpixel.shatteredpixeldungeon.items.remains;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class HoledPouch extends RemainsItem {
    {
        image = ItemSpriteSheet.HOLED_POUCH;
    }

    @Override
    protected void doEffect(Hero hero) {
        for (int i = 0; i < 3; i++) {
            Item result = Generator.randomUsingDefaults(Generator.Category.SEED);
            if (!result.collect()) {
                Dungeon.level.drop(result, hero.pos);
            }
        }
    }
}
