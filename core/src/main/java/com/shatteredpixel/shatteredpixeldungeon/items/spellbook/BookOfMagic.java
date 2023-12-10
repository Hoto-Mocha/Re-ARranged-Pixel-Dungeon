package com.shatteredpixel.shatteredpixeldungeon.items.spellbook;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class BookOfMagic extends SpellBook {

    {
        image = ItemSpriteSheet.BOOK_OF_MAGIC;
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
        Buff.affect(Dungeon.hero, Recharging.class, 5+Math.round(Dungeon.hero.lvl/2f*(1 + 0.5f*Dungeon.hero.pointsInTalent(Talent.SPELL_ENHANCE))));
    }

    @Override
    public String info() {
        String info = super.info();
        if (Dungeon.hero.buff(SpellBookCoolDown.class) == null) {
            info += "\n\n" + Messages.get(this, "time",
                    5+Math.round(Dungeon.hero.lvl/2f*(1 + 0.5f*Dungeon.hero.pointsInTalent(Talent.SPELL_ENHANCE))));
        }
        return info;
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{WandOfMagicMissile.class};
            inQuantity = new int[]{1};

            cost = 5;

            output = BookOfMagic.class;
            outQuantity = 1;
        }

    }
}
