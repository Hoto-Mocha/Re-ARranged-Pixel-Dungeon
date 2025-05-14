package com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HolyTome;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class Stun extends TargetedClericSpell {
    public static final Stun INSTANCE = new Stun();

    @Override
    public int icon(){
        return HeroIcon.STUN;
    }

    @Override
    public int targetingFlags(){
        return -1; //no targeting
    }

    private int duration() {
        return 1+Dungeon.hero.pointsInTalent(Talent.STUN);
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", duration()) + "\n\n" + Messages.get(this, "charge_cost", (int)chargeUse(Dungeon.hero));
    }

    @Override
    protected void onTargetSelected(HolyTome tome, Hero hero, Integer target) {
        if (target == null){
            return;
        }

        if (!Dungeon.level.heroFOV[target]) {
            GLog.w(Messages.get(ClericSpell.class, "not_in_fov"));
            return;
        }

        Char ch = Actor.findChar(target);
        if (ch == null) {
            GLog.w(Messages.get(ClericSpell.class, "no_target"));
            return;
        }

        hero.busy();
        hero.spendAndNext(Actor.TICK);
        hero.sprite.zap(target);

        Sample.INSTANCE.play(Assets.Sounds.READ);

        if (!ch.isImmune(Paralysis.class)){
            Sample.INSTANCE.play(Assets.Sounds.ROCKS);
            CellEmitter.get( target - Dungeon.level.width() ).start(Speck.factory(Speck.ROCK), 0.07f, 3);
            Buff.affect(ch, Paralysis.class, duration());
        } else {
            GLog.w(Messages.get(this, "immune"));
        }

        onSpellCast(tome, hero);
    }
}
