package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.nurse.AngelWing;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.HealingParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ShadowCaster;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class HealingArea extends Buff implements ActionIndicator.Action {

    private ArrayList<Integer> areaPositions = new ArrayList<>();
    private ArrayList<Emitter> areaEmitters = new ArrayList<>();
    private ArrayList<Char> targets = new ArrayList<>();
    private ArrayList<Char> enemy = new ArrayList<>();

    private boolean comp = false;

    private static final float DURATION = 20;
    int left = 0;

    {
        type = buffType.POSITIVE;
    }

    @Override
    public int icon() {
        return BuffIndicator.REGEN;
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (DURATION - left) / DURATION);
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
        return Messages.get(this, "desc", left);
    }

    //

    @Override
    public boolean attachTo(Char target) {
        if (((Hero)target).hasTalent(Talent.COMP_RECOVER)) {
            ActionIndicator.setAction(this);
            comp = true;
        }
        return super.attachTo(target);
    }

    @Override
    public void detach() {
        super.detach();
        ActionIndicator.clearAction(this);
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
        int duration = left;
        int healAmt = Math.round(duration/10f*hero.pointsInTalent(Talent.COMP_RECOVER));
        hero.heal(healAmt);
        detach();
        hero.sprite.operate(hero.pos);
        detach();
    }

    //

    public void setup(int pos, int duration, int dist, boolean talent){ //dist's default value is 1

        if (Dungeon.depth == 5 || Dungeon.depth == 10 || Dungeon.depth == 20){
            dist = 1; //smaller boss areas
        } else {

            boolean[] visibleCells = new boolean[Dungeon.level.length()];
            Point c = Dungeon.level.cellToPoint(pos);
            ShadowCaster.castShadow(c.x, c.y, visibleCells, Dungeon.level.losBlocking, 8);
            int count=0;
            for (boolean b : visibleCells){
                if (b) count++;
            }

            if (count < 30){
                dist += 0;
            } else if (count >= 100) {
                dist += 2;
            } else {
                dist += 1;
            }

            if (talent && hero.hasTalent(Talent.HEALAREA)) {
                dist += hero.pointsInTalent(Talent.HEALAREA);
            }
        }

        PathFinder.buildDistanceMap( pos, BArray.or( Dungeon.level.passable, Dungeon.level.avoid, null ), dist );
        for (int i = 0; i < PathFinder.distance.length; i++) {
            if (PathFinder.distance[i] < Integer.MAX_VALUE && !areaPositions.contains(i)) {
                areaPositions.add(i);
            }
        }
        if (target != null) {
            fx(false);
            fx(true);
        }

        left = duration;

    }

    public void extend(int duration) {
        left += duration;
    }

    @Override
    public boolean act() {

        for (int i : areaPositions) {
            Char ch = Actor.findChar(i);
            if (ch != null) {
                if (ch.alignment == Char.Alignment.ALLY && !(ch instanceof Hero)) {
                    targets.add(ch);
                } else if (ch.alignment == Char.Alignment.ENEMY) {
                    enemy.add(ch);
                }
            }
        }

        if (!areaPositions.contains(target.pos)){
            detach();
        }

        left--;
        BuffIndicator.refreshHero();
        if (left <= 0){
            detach();
        }

        for (Char ally : targets) {
            int healAmt = 1;
            if (hero.hasTalent(Talent.HEAL_AMP)) {
                if (Random.Int(4) < hero.pointsInTalent(Talent.HEAL_AMP)) {
                    healAmt *= 2;
                }
            }
            if (hero.buff(AngelWing.AngelWingBuff.class) != null && hero.hasTalent(Talent.HEALING_WING)) {
                healAmt *= 1+ hero.pointsInTalent(Talent.HEALING_WING);
            }
            ally.heal(healAmt);

            if (hero.hasTalent(Talent.PROMOTION)) {
                if (hero.pointsInTalent(Talent.PROMOTION) >= 1) {
                    Buff.prolong(ally, Adrenaline.class, 2);
                }
                if (hero.pointsInTalent(Talent.PROMOTION) >= 2) {
                    Buff.prolong(ally, Haste.class, 2);
                }
                if (hero.pointsInTalent(Talent.PROMOTION) == 3) {
                    if (ally.buff(Barrier.class) != null) {
                        Buff.affect(ally, Barrier.class).incShield(healAmt+1);
                    } else {
                        Buff.affect(ally, Barrier.class).setShield(healAmt+1);
                    }
                }
            }
        }

        for (Char ch : enemy) {
            if (hero.hasTalent(Talent.ANGEL_AND_DEVIL)) {
                if (Random.Int(6) < hero.pointsInTalent(Talent.ANGEL_AND_DEVIL)) {
                    int dmg = (hero.hasTalent(Talent.ANGEL)) ? 2 : 1;
                    ch.damage(dmg, hero);
                    ch.sprite.emitter().start( ShadowParticle.UP, 0.05f, 4 );
                    Sample.INSTANCE.play(Assets.Sounds.BURNING, 0.7f);

                }
            }
        }
        enemy.clear();
        targets.clear();

        spend(TICK);
        return true;
    }

    @Override
    public void fx(boolean on) {
        if (on){
            for (int i : areaPositions){
                Emitter e = CellEmitter.get(i);
                e.pour(HealingParticle.FACTORY, 0.05f);
                areaEmitters.add(e);
            }
        } else {
            for (Emitter e : areaEmitters){
                e.on = false;
            }
            areaEmitters.clear();
        }
    }

    private static final String AREA_POSITIONS = "area_positions";
    private static final String LEFT = "left";
    private static final String COMP = "comp";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);

        int[] values = new int[areaPositions.size()];
        for (int i = 0; i < values.length; i ++)
            values[i] = areaPositions.get(i);
        bundle.put(AREA_POSITIONS, values);

        bundle.put(LEFT, left);
        bundle.put(COMP, comp);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        int[] values = bundle.getIntArray( AREA_POSITIONS );
        for (int value : values) {
            areaPositions.add(value);
        }

        left = bundle.getInt(LEFT);
        comp = bundle.getBoolean(COMP);
        if (comp) {
            ActionIndicator.setAction(this);
        }
    }
}