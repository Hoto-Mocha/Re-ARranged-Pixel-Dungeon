package com.shatteredpixel.shatteredpixeldungeon.items.remains;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ShatteredPillbox extends RemainsItem {
    {
        image = ItemSpriteSheet.BROKEN_PILLBOX;
    }

    @Override
    protected void doEffect(Hero hero) {
        for (int i = 0; i < 3; i++) {
            Item result = Generator.randomUsingDefaults(Generator.Category.POSITIVE_PILL);
            if (!result.collect()) {
                Dungeon.level.drop(result, hero.pos);
            }
        }
    }
}
