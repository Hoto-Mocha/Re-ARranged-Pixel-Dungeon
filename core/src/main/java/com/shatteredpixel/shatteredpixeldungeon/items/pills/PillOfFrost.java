package com.shatteredpixel.shatteredpixeldungeon.items.pills;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Freezing;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PillOfFrost extends Pill {
    {
        image = ItemSpriteSheet.PILL_FROST;
    }

    @Override
    public void useEffect(Char appliedCh) {
        super.useEffect(appliedCh);
        GameScene.add(Blob.seed(appliedCh.pos, 5, Freezing.class));
    }
}
