package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class SpellEnhance extends Buff {
    {
        type = buffType.POSITIVE;
    }

    private int left;

    public void reset(){
        left = Dungeon.hero.pointsInTalent(Talent.SPELL_ENHANCE);
        Item.updateQuickslot();
    }

    public void use(){
        left--;
        if (left <= 0){
            detach();
        }
    }

    @Override
    public void detach() {
        super.detach();
        Item.updateQuickslot();
    }

    @Override
    public int icon() {
        return BuffIndicator.UPGRADE;
    }

    @Override
    public void tintIcon(Image icon) {
        icon.hardlight(0x00B300);
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (3f - left) / 3f);
    }

    @Override
    public String iconTextDisplay() {
        return Integer.toString(left);
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", left);
    }

    private static final String LEFT = "left";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(LEFT, left);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        left = bundle.getInt(LEFT);
    }
}
