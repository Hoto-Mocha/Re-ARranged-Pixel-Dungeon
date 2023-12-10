package com.shatteredpixel.shatteredpixeldungeon.items.spellbook;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NPC;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfPrismaticLight;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

import java.text.DecimalFormat;

public class BookOfLight extends SpellBook {

    {
        image = ItemSpriteSheet.BOOK_OF_LIGHT;
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
        Buff.affect(Dungeon.hero, Light.class, 50*(1 + 0.5f * Dungeon.hero.pointsInTalent(Talent.SPELL_ENHANCE)));
        GameScene.flash(0xFFFFFF);
        Sample.INSTANCE.play(Assets.Sounds.BLAST);
        for (Mob m : Dungeon.level.mobs) {
            if (!(m instanceof NPC) && Dungeon.level.heroFOV[m.pos]) {
                Buff.affect(m, Blindness.class, (2+(int)Math.floor(Dungeon.hero.lvl/6f)*(1+0.5f*Dungeon.hero.pointsInTalent(Talent.SPELL_ENHANCE))));
            }
        }
    }

    @Override
    public String info() {
        String info = super.info();
        if (Dungeon.hero.buff(SpellBookCoolDown.class) == null) {
            info += "\n\n" + Messages.get(this, "time",
                    new DecimalFormat("#").format(50*(1 + 0.5f * Dungeon.hero.pointsInTalent(Talent.SPELL_ENHANCE))),
                    new DecimalFormat("#").format((2+(int)Math.floor(Dungeon.hero.lvl/6f)*(1+0.5f*Dungeon.hero.pointsInTalent(Talent.SPELL_ENHANCE)))));
        }
        return info;
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{WandOfPrismaticLight.class};
            inQuantity = new int[]{1};

            cost = 5;

            output = BookOfLight.class;
            outQuantity = 1;
        }

    }
}
