package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Chains;
import com.shatteredpixel.shatteredpixeldungeon.effects.Effects;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.EtherealChains;
import com.shatteredpixel.shatteredpixeldungeon.levels.MiningLevel;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.BArray;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class Rope extends Item {

    {
        image = ItemSpriteSheet.ROPE;

        stackable = true;

        defaultAction = AC_USE;

        bones = false;
    }

    private static final String AC_USE = "USE";

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_USE );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals(AC_USE)) {
            usesTargeting = true;
            curUser = hero;
            curItem = this;
            GameScene.selectCell(caster);
        }
    }

    private CellSelector.Listener caster = new CellSelector.Listener(){

        @Override
        public void onSelect(Integer target) {
            if (target != null && (Dungeon.level.visited[target] || Dungeon.level.mapped[target])){

                //chains cannot be used to go where it is impossible to walk to
                PathFinder.buildDistanceMap(target, BArray.or(Dungeon.level.passable, Dungeon.level.avoid, null));
                if (!(Dungeon.level instanceof MiningLevel) && PathFinder.distance[curUser.pos] == Integer.MAX_VALUE){
                    GLog.w( Messages.get(Rope.class, "cant_reach") );
                    return;
                }

                int ballistica = Ballistica.PROJECTILE;
                if (hero.pointsInTalent(Talent.ROPE_MASTER) > 2) {
                    ballistica = Ballistica.STOP_TARGET;
                }

                final Ballistica chain = new Ballistica(curUser.pos, target, ballistica);

                Char ch = Actor.findChar( chain.collisionPos );
                if (ch != null){
                    chainEnemy( chain, curUser, ch );
                } else {
                    chainLocation( chain, curUser );
                }
                onUse();
            }
        }

        @Override
        public String prompt() {
            return Messages.get(EtherealChains.class, "prompt");
        }
    };

    //pulls an enemy to a position along the chain's path, as close to the hero as possible
    private void chainEnemy( Ballistica chain, final Hero hero, final Char enemy ){
        if (enemy.properties().contains(Char.Property.IMMOVABLE)) {
            GLog.w( Messages.get(Rope.class, "cant_pull") );
            return;
        }

        int bestPos = -1;
        for (int i : chain.subPath(1, chain.dist)){
            //prefer to the earliest point on the path
            if (!Dungeon.level.solid[i]
                    && Actor.findChar(i) == null
                    && (!Char.hasProp(enemy, Char.Property.LARGE) || Dungeon.level.openSpace[i])){
                bestPos = i;
                break;
            }
        }

        boolean bind = false;
        if (bestPos == -1) {
            if (hero.hasTalent(Talent.VINE_BIND)) {
                bind = true;
            } else {
                GLog.i(Messages.get(Rope.class, "does_nothing"));
                return;
            }
        }

        final int pulledPos = bestPos;

        int ropeUse = Dungeon.level.distance(enemy.pos, pulledPos);
        if (hero.hasTalent(Talent.ROPE_MASTER)) {
            ropeUse = Math.round(ropeUse/2f);
        }
        if (bind) {
            ropeUse = 5;
        }
        if (ropeUse > this.quantity()) {
            GLog.w( Messages.get(Rope.class, "no_rope") );
            return;
        }

        this.quantity(this.quantity() - ropeUse);
        if (this.quantity() <= 0) {
            detach(Dungeon.hero.belongings.backpack);
        }

        if (bind) {
            Buff.affect(enemy, Roots.class, 3*hero.pointsInTalent(Talent.VINE_BIND));
            updateQuickslot();
            return;
        }

        throwSound();
        hero.sprite.parent.add(new Chains(hero.sprite.center(),
                enemy.sprite.center(),
                Effects.Type.VINE_ROPE,
                new Callback() {
                    public void call() {
                        Actor.add(new Pushing(enemy, enemy.pos, pulledPos, new Callback() {
                            public void call() {
                                enemy.pos = pulledPos;
                                Dungeon.level.occupyCell(enemy);
                                Dungeon.observe();
                                GameScene.updateFog();
                                float spendTime = Actor.TICK;
                                if (hero.pointsInTalent(Talent.ROPE_MASTER) > 1){
                                    spendTime = 0;
                                }
                                hero.spendAndNext(spendTime);

                                if (hero.hasTalent(Talent.LASSO)) {
                                    Buff.affect(enemy, Cripple.class, 1+hero.pointsInTalent(Talent.LASSO));
                                }

                                updateQuickslot();
                            }
                        }));
                        hero.next();
                    }
                }));
    }

    //pulls the hero along the chain to the collisionPos, if possible.
    private void chainLocation( Ballistica chain, final Hero hero ){

        //don't pull if rooted
        if (hero.rooted){
            PixelScene.shake( 1, 1f );
            GLog.w( Messages.get(Rope.class, "rooted") );
            return;
        }

        //don't pull if the collision spot is in a wall
        if (Dungeon.level.solid[chain.collisionPos]
                || !(Dungeon.level.passable[chain.collisionPos] || Dungeon.level.avoid[chain.collisionPos])){
            GLog.i( Messages.get(Rope.class, "inside_wall"));
            return;
        }

        //don't pull if there are no solid objects next to the pull location
        boolean solidFound = false;
        for (int i : PathFinder.NEIGHBOURS8){
            if (Dungeon.level.solid[chain.collisionPos + i]){
                solidFound = true;
                break;
            }
        }
        if (!solidFound){
            GLog.i( Messages.get(Rope.class, "nothing_to_grab") );
            return;
        }

        final int newHeroPos = chain.collisionPos;

        int ropeUse = Dungeon.level.distance(hero.pos, newHeroPos);
        if (ropeUse > this.quantity()){
            GLog.w( Messages.get(Rope.class, "no_rope") );
            return;
        }

        this.quantity(this.quantity() - ropeUse);
        if (this.quantity() <= 0) {
            detachAll(Dungeon.hero.belongings.backpack);
        }

        hero.busy();
        throwSound();
        hero.sprite.parent.add(new Chains(hero.sprite.center(),
                DungeonTilemap.raisedTileCenterToWorld(newHeroPos),
                Effects.Type.VINE_ROPE,
                new Callback() {
                    public void call() {
                        Actor.add(new Pushing(hero, hero.pos, newHeroPos, new Callback() {
                            public void call() {
                                hero.pos = newHeroPos;
                                Dungeon.level.occupyCell(hero);
                                hero.spendAndNext(1f);
                                Dungeon.observe();
                                GameScene.updateFog();

                                updateQuickslot();
                            }
                        }));
                        hero.next();
                    }
                }));
    }

    public void onUse() {
        if (hero.hasTalent(Talent.ROPE_REBOUND)) {
            Buff.affect(hero, Haste.class, 1+hero.pointsInTalent(Talent.ROPE_REBOUND));
        }
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
    public int value() {
        return 1*quantity;
    }

}
