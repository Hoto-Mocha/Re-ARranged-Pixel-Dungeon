package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RevealedArea;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;

public class StanceCooldown extends FlavourBuff {
    public int icon() { return BuffIndicator.TIME; }
    public void tintIcon(Image icon) { icon.hardlight(0.8f, 0.2f, 0.2f); }
    public float iconFadePercent() { return Math.max(0, visualcooldown() / (10)); }
    public String toString() { return Messages.get(this, "name"); }
    public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
};