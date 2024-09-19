package com.shatteredpixel.shatteredpixeldungeon.items.spellbook;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfDisintegration;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class BookOfDisintegration extends SpellBook {

    {
        image = ItemSpriteSheet.BOOK_OF_DISINTEGRATION;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_READ)) {
            if (hero.buff(SpellBookCoolDown.class) != null) {
                GLog.w(Messages.get(this, "cooldown"));
            } else {
                Buff.affect(hero, SpellBookCoolDown.class).set(100);
                readEffect();
            }
        }
    }

    @Override
    public void readEffect() {
        Buff.affect(Dungeon.hero, ReachBuff.class).set(Math.round(10*(1 + 0.5f*Dungeon.hero.pointsInTalent(Talent.SPELL_ENHANCE))), 1+Dungeon.hero.lvl/6);
    }

    @Override
    public String info() {
        String info = super.info();
        if (Dungeon.hero != null && Dungeon.hero.buff(SpellBookCoolDown.class) == null) {
            info += "\n\n" + Messages.get(this, "time", Math.round(10*(1 + 0.5f*Dungeon.hero.pointsInTalent(Talent.SPELL_ENHANCE))), 1+Dungeon.hero.lvl/6);
        }
        return info;
    }

    public static class ReachBuff extends Buff {
        private int maxDuration;
        private int left;
        private int reach;

        public int icon() {
            return BuffIndicator.WEAPON;
        }

        public void tintIcon(Image icon) {
            icon.hardlight(0x5A00B2);
        }

        public float iconFadePercent() {
            return Math.max(0, (maxDuration - left) / maxDuration);
        }

        public void set(int duration, int lvl) {
            maxDuration = duration;
            left = maxDuration;
            reach = lvl;
        }

        public int getUpgrade() {
            return reach;
        }

        private static final String MAX_DURATION = "maxDuration";
        private static final String LEFT = "left";
        private static final String REACH = "reach";
        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put( MAX_DURATION, maxDuration );
            bundle.put( LEFT, left );
            bundle.put( REACH, reach );
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            maxDuration = bundle.getInt( MAX_DURATION );
            left = bundle.getInt( LEFT );
            reach = bundle.getInt( REACH );
        }

        @Override
        public String iconTextDisplay() {
            return Integer.toString(left);
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", 1+reach, left);
        }

        @Override
        public boolean act() {
            left--;
            if (left <= 0) {
                detach();
            }
            spend(TICK);
            return true;
        }
    }
}
