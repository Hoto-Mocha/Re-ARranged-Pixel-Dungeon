package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;

public class SurgeryUse extends Buff implements ActionIndicator.Action {

    {
        type = buffType.NEUTRAL;
    }

    @Override
    public boolean attachTo(Char target) {
        ActionIndicator.setAction(this);
        return super.attachTo(target);
    }

    @Override
    public void detach() {
        super.detach();
        ActionIndicator.clearAction(this);
    }

    @Override
    public boolean act() {
        if (Dungeon.hero.buff(Surgery.class) == null) {
            detach();
        }
        spend(TICK);
        return true;
    }

    @Override
    public String actionName() {
        return Messages.get(this, "action_name");
    }

    @Override
    public int actionIcon() {
        return HeroIcon.DEATH_CERTIFICATE;
    }

    @Override
    public int indicatorColor() {
        return 0xFFFFFF;
    }

    @Override
    public void doAction() {
        Buff.affect(Dungeon.hero, SurgeryTracker.class);
        Dungeon.hero.buff(Surgery.class).detach();
        ((HeroSprite)Dungeon.hero.sprite).read();
        detach();
    }
}
