package com.shatteredpixel.shatteredpixeldungeon.items.spellbook;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.BulletItem;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class BookOfBlast extends SpellBook {

    {
        image = ItemSpriteSheet.BOOK_OF_BLAST;
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
            if (ch != null && ch.alignment == Char.Alignment.ENEMY) {
                //trace a ballistica to our target (which will also extend past them
                Ballistica trajectory = new Ballistica(Dungeon.hero.pos, ch.pos, Ballistica.STOP_TARGET);
                //trim it to just be the part that goes past them
                trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
                //knock them back along that ballistica
                WandOfBlastWave.throwChar(ch, trajectory, Math.round((2+Dungeon.hero.lvl/5f)*(1+0.5f*Dungeon.hero.pointsInTalent(Talent.SPELL_ENHANCE))), false, true, Dungeon.hero);
            }
            if (Dungeon.level.map[cell] == Terrain.DOOR) {
                Level.set(cell, Terrain.OPEN_DOOR);
                Dungeon.observe();
            }
        }
        Sample.INSTANCE.play(Assets.Sounds.BLAST);
        WandOfBlastWave.BlastWave.blast(Dungeon.hero.pos);
    }

    @Override
    public String info() {
        String info = super.info();
        if (Dungeon.hero != null && Dungeon.hero.buff(SpellBookCoolDown.class) == null) {
            info += "\n\n" + Messages.get(this, "time",
                    Math.round((2+Dungeon.hero.lvl/5f)*(1+0.5f*Dungeon.hero.pointsInTalent(Talent.SPELL_ENHANCE))));
        }
        return info;
    }
}
