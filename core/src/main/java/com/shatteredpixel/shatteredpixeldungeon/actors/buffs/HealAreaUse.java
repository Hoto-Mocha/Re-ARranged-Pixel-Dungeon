package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.watabou.noosa.Image;

public class HealAreaUse extends Buff implements ActionIndicator.Action {

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
        if (Dungeon.hero.buff(HealingArea.class) == null) {
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
    public Image actionIcon() {
        return Icons.get(Icons.HEAL);
    }

    @Override
    public void doAction() {
        int duration = hero.buff(HealingArea.class).left;
        int healAmt = Math.round(duration/5f*hero.pointsInTalent(Talent.COMP_RECOVER));
        healAmt = Math.min( healAmt, hero.HT - hero.HP );
        if (healAmt > 0 && hero.isAlive()) {
            hero.HP += healAmt;
            hero.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
            hero.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
        }
        Dungeon.hero.buff(HealingArea.class).detach();
        Dungeon.hero.sprite.operate(Dungeon.hero.pos);
        detach();
    }
}
