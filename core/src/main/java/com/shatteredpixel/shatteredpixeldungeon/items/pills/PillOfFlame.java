package com.shatteredpixel.shatteredpixeldungeon.items.pills;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PillOfFlame extends Pill {
    {
        image = ItemSpriteSheet.PILL_LIQFLAME;
    }

    @Override
    public void useEffect(Char appliedCh) {
        super.useEffect(appliedCh);
        GameScene.add(Blob.seed(appliedCh.pos, 2, Fire.class));
    }
}
