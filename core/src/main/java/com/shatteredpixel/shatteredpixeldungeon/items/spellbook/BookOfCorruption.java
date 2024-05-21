package com.shatteredpixel.shatteredpixeldungeon.items.spellbook;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AllyBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NPC;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorruption;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.text.DecimalFormat;

public class BookOfCorruption extends SpellBook {

    {
        image = ItemSpriteSheet.BOOK_OF_CORRUPTION;
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
        for (int i : PathFinder.NEIGHBOURS8) {
            int cell = Dungeon.hero.pos+i;
            Char ch = Actor.findChar(cell);
            if (ch != null && ch.alignment == Char.Alignment.ENEMY && ch instanceof Mob && !(ch instanceof NPC)) {
                if (Random.Float()+0.01f*(10+Dungeon.hero.lvl/2f)*(1+0.5f*Dungeon.hero.pointsInTalent(Talent.SPELL_ENHANCE)) < (1-ch.HP/(float)ch.HT)) {
                    if (!ch.isImmune(Corruption.class)){
                        Corruption.corruptionHeal(ch);

                        AllyBuff.affectAndLoot((Mob)ch, curUser, Corruption.class);
                    } else {
                        Buff.affect(ch, Doom.class);
                    }
                }
            }
            CellEmitter.get(cell).burst(ShadowParticle.UP, 6);
        }
        Sample.INSTANCE.play(Assets.Sounds.MIMIC);
    }

    @Override
    public String info() {
        String info = super.info();
        if (Dungeon.hero.buff(SpellBookCoolDown.class) == null) {
            info += "\n\n" + Messages.get(this, "time",
                    new DecimalFormat("#.#").format(100*(0.01f*(10+Dungeon.hero.lvl/2f)*(1+0.5f*Dungeon.hero.pointsInTalent(Talent.SPELL_ENHANCE)))));
        }
        return info;
    }
}
