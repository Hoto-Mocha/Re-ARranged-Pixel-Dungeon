package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class ArrowEmpower extends Buff {
    {
        type = buffType.NEUTRAL;
    }

    private float duration;
    private int charge = 0;

    private final int MAX_CHARGE = 4;

    @Override
    public int icon() {
        return BuffIndicator.ARROW_EMPOWER;
    }

    @Override
    public float iconFadePercent() {
        return super.iconFadePercent();
    }

    @Override
    public String iconTextDisplay() {
        return Integer.toString(charge);
    }

    private String descString() {
        return 2*charge + "+" + Messages.decimalFormat("#.##", (float)Math.pow(1.1f, charge)*100) + "%%";
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", descString());
    }

    public void shoot() {
        this.duration = 1;
        this.charge = Math.min(MAX_CHARGE, charge+1);
    }

    public void shoot(int charge) {
        this.duration = 1;
        this.charge = Math.min(MAX_CHARGE, this.charge+charge);
    }

    public int proc(int damage) {
        return Math.round(damage * (float)Math.pow(1.1f, charge) + 2*charge);
    }

    @Override
    public boolean act() {

        duration -= TICK;
        if (duration <= 0) {
            detach();
        }
        spend(TICK);

        return true;
    }

    private static final String DURATION = "duration";
    private static final String CHARGE   = "charge";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);

        bundle.put(DURATION, duration);
        bundle.put(CHARGE, charge);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        duration = bundle.getFloat(DURATION);
        charge = bundle.getInt(CHARGE);
    }
}
