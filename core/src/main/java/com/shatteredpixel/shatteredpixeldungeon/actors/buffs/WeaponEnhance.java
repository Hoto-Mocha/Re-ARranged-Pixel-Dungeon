package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class WeaponEnhance extends Buff {

    {
        type = buffType.POSITIVE;
    }

    private int hit = 0;
    private int maxHit = 0;
    private int level = 0;

    public void set( int level, int hit ) {
        this.maxHit = hit;
        this.hit = this.maxHit;
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
        return Math.max((maxHit - hit)/(float) maxHit, 0);
    }

    public void attackProc() {
        hit--;
        if (hit <= 0) {
            detach();
        }
        BuffIndicator.refreshHero();
    }

    public int weaponLevel(int weaponLevel){
        weaponLevel += this.level;
        return weaponLevel;
    }

    @Override
    public int icon() {
        return BuffIndicator.UPGRADE;
    }

    @Override
    public void tintIcon(Image icon) {
        icon.hardlight(1, 0, 0);
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", level, hit);
    }

    private static final String HIT = "hit";
    private static final String MAX_HIT = "maxHit";
    private static final String LEVEL = "level";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(HIT, hit);
        bundle.put(MAX_HIT, maxHit);
        bundle.put(LEVEL, level);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        hit = bundle.getInt(HIT);
        maxHit = bundle.getInt(MAX_HIT);
        level = bundle.getInt(LEVEL);
    }
}
