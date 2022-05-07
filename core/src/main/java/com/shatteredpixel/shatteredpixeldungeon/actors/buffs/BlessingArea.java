package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlessingParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.HealingParticle;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ShadowCaster;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Point;

import java.util.ArrayList;

public class BlessingArea extends Buff {

    private ArrayList<Integer> areaPositions = new ArrayList<>();
    private ArrayList<Emitter> areaEmitters = new ArrayList<>();
    private ArrayList<Char> targets = new ArrayList<>();

    private static final float DURATION = 20;
    int left = 0;

    {
        type = buffType.POSITIVE;
    }

    @Override
    public int icon() {
        return BuffIndicator.BLESS;
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

    public void setup(int pos, int duration, int dist){ //dist's default value is 1

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

    @Override
    public boolean act() {

        for (int i : areaPositions) {
            Char ch = Actor.findChar(i);
            if (ch != null && ch.alignment == Char.Alignment.ALLY) {
                targets.add(ch);
            }
        }

        left--;
        BuffIndicator.refreshHero();
        if (left <= 0){
            detach();
        }

        for (Char ally : targets) {
            Buff.affect(ally, Bless.class, 3f);
        }

        targets.clear();

        spend(TICK);
        return true;
    }

    @Override
    public void fx(boolean on) {
        if (on){
            for (int i : areaPositions){
                Emitter e = CellEmitter.get(i);
                e.pour(BlessingParticle.FACTORY, 0.05f);
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

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);

        int[] values = new int[areaPositions.size()];
        for (int i = 0; i < values.length; i ++)
            values[i] = areaPositions.get(i);
        bundle.put(AREA_POSITIONS, values);

        bundle.put(LEFT, left);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        int[] values = bundle.getIntArray( AREA_POSITIONS );
        for (int value : values) {
            areaPositions.add(value);
        }

        left = bundle.getInt(LEFT);
    }
}