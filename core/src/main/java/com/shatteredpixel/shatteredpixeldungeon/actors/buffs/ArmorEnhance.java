package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class ArmorEnhance extends Buff {

    {
        type = buffType.POSITIVE;
    }

    private float duration = 0f;
    private float maxDuration = 0f;
    private int level = 0;

    public void set( int level, float duration ) {
        this.maxDuration = duration;
        this.duration = this.maxDuration;
        this.level = level;
        Item.updateQuickslot();
    }

    @Override
    public void detach() {
        super.detach();
        Item.updateQuickslot();
    }

    @Override
    public float iconFadePercent() {
        return Math.max((maxDuration - duration)/maxDuration, 0);
    }

    @Override
    public boolean act() {
        if (--duration <= 0) {
            detach();
        } else {
            spend(TICK);
        }
        return true;
    }

    public int armorLevel(int armorLevel){
        armorLevel += this.level;
        return armorLevel;
    }

    @Override
    public int icon() {
        return BuffIndicator.UPGRADE;
    }

    @Override
    public void tintIcon(Image icon) {
        icon.hardlight(0, 0, 1);
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", level, duration);
    }

    private static final String DURATION = "duration";
    private static final String MAX_DURATION = "maxDuration";
    private static final String LEVEL = "level";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(DURATION, duration);
        bundle.put(MAX_DURATION, maxDuration);
        bundle.put(LEVEL, level);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        duration = bundle.getFloat(DURATION);
        maxDuration = bundle.getFloat(MAX_DURATION);
        level = bundle.getInt(LEVEL);
    }
}
