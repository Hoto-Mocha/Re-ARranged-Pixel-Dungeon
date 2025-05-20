package com.shatteredpixel.shatteredpixeldungeon.items.remains;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.BulletItem;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class SpareMagazine extends RemainsItem {
    {
        image = ItemSpriteSheet.SPARE_MAG;
    }

    @Override
    protected void doEffect(Hero hero) {
        BulletItem bulletItem = new BulletItem();
        int quantity = Random.IntRange(10, 20);
        bulletItem.quantity(quantity);
        bulletItem.doPickUp(hero);
    }
}
