package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

//This is same with RevealedArea.class, but this contains an array of positions. Not a position.
public class RevealedLargeArea extends FlavourBuff{
    {
        type = Buff.buffType.POSITIVE;
    }

    public int[] pos;
    public int depth, branch;

    @Override
    public void detach() {
        for (int p : pos) {
            GameScene.updateFog(p, 2);
        }
        super.detach();
    }

    @Override
    public int icon() {
        return BuffIndicator.MIND_VISION;
    }

    @Override
    public void tintIcon(Image icon) {
        icon.hardlight(0, 1, 1);
    }

    @Override
    public float iconFadePercent() {
        float max = 5* Dungeon.hero.pointsInTalent(Talent.SEER_SHOT);
        return Math.max(0, (max-visualcooldown()) / max);
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", (int)visualcooldown());
    }

    private static final String BRANCH = "branch";
    private static final String DEPTH = "depth";
    private static final String POS = "pos";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(DEPTH, depth);
        bundle.put(BRANCH, branch);
        bundle.put(POS, pos);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        depth = bundle.getInt(DEPTH);
        branch = bundle.getInt(BRANCH);
        pos = bundle.getIntArray(POS);
    }
}
