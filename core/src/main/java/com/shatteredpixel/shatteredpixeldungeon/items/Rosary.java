package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Rosary extends Item {
    {
        image = ItemSpriteSheet.ROSARY;
        levelKnown = true;

        bones = false;
        unique = true;
    }

    public void onPray(Hero hero) {
        Buff.affect(hero, Bless.class, 2f);
        if (hero.buff(Barrier.class) == null) {
            Buff.affect(hero, Barrier.class).setShield(1+this.buffedLvl());
        } else {
            Buff.affect(hero, Barrier.class).incShield(1+this.buffedLvl());
        }
    }

    @Override
    public int level() {
        int level = Dungeon.hero == null ? 0 : Dungeon.hero.lvl/5;
        return level;
    }

    @Override
    public int buffedLvl() {
        //level isn't affected by buffs/debuffs
        return level();
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", 1+this.buffedLvl());
    }
}
