package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.alchemy;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.HolyBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Bible;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.HeavyBoomerang;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MissileSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

public class Cross extends MissileWeapon {
    {
        image = ItemSpriteSheet.CROSS;
        hitSound = Assets.Sounds.HIT_CRUSH;
        hitSoundPitch = 1f;

        tier = 5;
        sticky = false;

        baseUses = 1000;
    }

    @Override
    public int max(int lvl) {
        return  6 * tier +  //30 base
                tier * lvl; //+5 per level
    }

    boolean circleBackhit = false;

    @Override
    protected float adjacentAccFactor(Char owner, Char target) {
        if (circleBackhit){
            circleBackhit = false;
            return 1.5f;
        }
        return super.adjacentAccFactor(owner, target);
    }

    @Override
    protected void rangedHit(Char enemy, int cell) {
        decrementDurability();
        if (durability > 0){
            Buff.append(Dungeon.hero, CircleBack.class).setup(this, cell, Dungeon.hero.pos, Dungeon.depth);
        }
    }

    @Override
    protected void rangedMiss(int cell) {
        parent = null;
        Buff.append(Dungeon.hero, CircleBack.class).setup(this, cell, Dungeon.hero.pos, Dungeon.depth);
    }

    public static class CircleBack extends Buff {

        {
            revivePersists = true;
        }

        private Cross cross;
        private int thrownPos;
        private int returnPos;
        private int returnDepth;

        private int left;

        public void setup( Cross cross, int thrownPos, int returnPos, int returnDepth){
            this.cross = cross;
            this.thrownPos = thrownPos;
            this.returnPos = returnPos;
            this.returnDepth = returnDepth;
            left = 3;
        }

        public int returnPos(){
            return returnPos;
        }

        public MissileWeapon cancel(){
            detach();
            return cross;
        }

        public int activeDepth(){
            return returnDepth;
        }

        @Override
        public boolean act() {
            if (returnDepth == Dungeon.depth){
                left--;
                if (left <= 0){
                    final Char returnTarget = Actor.findChar(returnPos);
                    final Char target = this.target;
                    MissileSprite visual = ((MissileSprite) Dungeon.hero.sprite.parent.recycle(MissileSprite.class));
                    visual.reset( thrownPos,
                            returnPos,
                            cross,
                            new Callback() {
                                @Override
                                public void call() {
                                    if (returnTarget == target){
                                        if (target instanceof Hero && cross.doPickUp((Hero) target)) {
                                            //grabbing the boomerang takes no time
                                            ((Hero) target).spend(-TIME_TO_PICK_UP);
                                        } else {
                                            Dungeon.level.drop(cross, returnPos).sprite.drop();
                                        }

                                    } else if (returnTarget != null){
                                        cross.circleBackhit = true;
                                        if (((Hero)target).shoot( returnTarget, cross)) {
                                            cross.decrementDurability();
                                        }
                                        if (cross.durability > 0) {
                                            Dungeon.level.drop(cross, returnPos).sprite.drop();
                                        }

                                    } else {
                                        Dungeon.level.drop(cross, returnPos).sprite.drop();
                                    }
                                    Cross.CircleBack.this.next();
                                }
                            });
                    visual.alpha(0f);
                    float duration = Dungeon.level.trueDistance(thrownPos, returnPos) / 20f;
                    target.sprite.parent.add(new AlphaTweener(visual, 1f, duration));
                    detach();
                    return false;
                }
            }
            spend( TICK );
            return true;
        }

        private static final String CROSS = "cross";
        private static final String THROWN_POS = "thrown_pos";
        private static final String RETURN_POS = "return_pos";
        private static final String RETURN_DEPTH = "return_depth";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(CROSS, cross);
            bundle.put(THROWN_POS, thrownPos);
            bundle.put(RETURN_POS, returnPos);
            bundle.put(RETURN_DEPTH, returnDepth);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            cross = (Cross) bundle.get(CROSS);
            thrownPos = bundle.getInt(THROWN_POS);
            returnPos = bundle.getInt(RETURN_POS);
            returnDepth = bundle.getInt(RETURN_DEPTH);
        }
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (defender.properties().contains(Char.Property.DEMONIC) || defender.properties().contains(Char.Property.UNDEAD)){
            defender.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10 );
            Sample.INSTANCE.play(Assets.Sounds.BURNING);

            damage *= 1.33f; //deals more damage to the demons and the undeads
        }
        return super.proc(attacker, defender, damage);
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {
        {
            inputs =  new Class[]{Bible.class, HolyBomb.class, HeavyBoomerang.class};
            inQuantity = new int[]{1, 1, 1};

            cost = 20;

            output = Cross.class;
            outQuantity = 1;
        }
    }
}
