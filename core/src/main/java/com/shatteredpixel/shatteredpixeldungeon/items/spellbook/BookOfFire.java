package com.shatteredpixel.shatteredpixeldungeon.items.spellbook;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class BookOfFire extends SpellBook {

    {
        image = ItemSpriteSheet.BOOK_OF_FIRE;
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
        Buff.affect(Dungeon.hero, FireImbue.class).set(5+Math.round(Dungeon.hero.lvl/2f*(1 + 0.5f*Dungeon.hero.pointsInTalent(Talent.SPELL_ENHANCE))));
    }

    @Override
    public String info() {
        String info = super.info();
        if (Dungeon.hero != null && Dungeon.hero.buff(SpellBookCoolDown.class) == null) {
            info += "\n\n" + Messages.get(this, "time",
                    5+Math.round(Dungeon.hero.lvl/2f*(1 + 0.5f*Dungeon.hero.pointsInTalent(Talent.SPELL_ENHANCE))));
        }
        return info;
    }
}
