package com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HolyTome;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.HolyBomb;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.BArray;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class HolyBombSpell extends TargetedClericSpell {

    public static final HolyBombSpell INSTANCE = new HolyBombSpell();

    @Override
    public int icon(){
        return HeroIcon.HOLY_BOMB;
    }

    @Override
    public float chargeUse(Hero hero) {
        return 4-hero.pointsInTalent(Talent.HOLY_BOMB);
    }

    @Override
    public int targetingFlags(){
        return Ballistica.STOP_TARGET;
    }

    @Override
    protected void onTargetSelected(HolyTome tome, Hero hero, Integer target) {
        if (target == null) return;

        if (!Dungeon.level.heroFOV[target]) {
            GLog.w(Messages.get(ClericSpell.class, "not_in_fov"));
            return;
        }

        hero.busy();
        hero.spendAndNext(Actor.TICK);
        hero.sprite.zap(target);

        SpelledHolyBomb bomb = new SpelledHolyBomb();
        bomb.explode(target);

        onSpellCast(tome, hero);
    }

    //same with HolyBomb.class, but explosion illuminates affected enemies
    public static class SpelledHolyBomb extends HolyBomb {
        @Override
        public void explode(int cell) {
            super.explode(cell);

            ArrayList<Char> affected = new ArrayList<>();

            PathFinder.buildDistanceMap( cell, BArray.not( Dungeon.level.solid, null ), explosionRange() );
            for (int i = 0; i < PathFinder.distance.length; i++) {
                if (PathFinder.distance[i] < Integer.MAX_VALUE) {
                    Char ch = Actor.findChar(i);
                    if (ch != null) {
                        affected.add(ch);
                    }
                }
            }

            for (Char ch : affected) {
                if (ch.alignment == Char.Alignment.ENEMY) {
                    Buff.affect(ch, GuidingLight.Illuminated.class);
                }
            }
        }
    }
}
