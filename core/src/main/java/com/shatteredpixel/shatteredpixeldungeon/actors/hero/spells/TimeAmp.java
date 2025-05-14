package com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlessingParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HolyTome;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Random;

public class TimeAmp extends TargetedClericSpell {
    public static final TimeAmp INSTANCE = new TimeAmp();

    @Override
    public int icon(){
        return HeroIcon.TIME_AMP;
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", duration(), duration() )+ "\n\n" + Messages.get(this, "charge_cost", (int)chargeUse(Dungeon.hero));
    }

    private float duration() {
        return 2f*Dungeon.hero.pointsInTalent(Talent.TIME_AMP);
    }

    @Override
    protected void onTargetSelected(HolyTome tome, Hero hero, Integer target) {
        if (target == null) {
            return;
        }

        Char ch = Actor.findChar(target);
        if (ch == null) {
            GLog.w(Messages.get(ClericSpell.class, "no_target"));
            return;
        }

        if ((ch.alignment == Char.Alignment.ENEMY && ch.buff(Slow.class) != null)
            || (ch.alignment == Char.Alignment.ALLY && ch.buff(Swiftthistle.TimeBubble.class) != null)) {
            GLog.w(Messages.get(this, "already_have"));
            return;
        }

        hero.busy();
        hero.sprite.operate(hero.pos);
        Sample.INSTANCE.play(Assets.Sounds.READ);

        onSpellCast(tome, hero);

        if (ch.alignment == Char.Alignment.ENEMY) {
            ch.sprite.emitter().burst( SlowParticle.FACTORY, 6 );
            Sample.INSTANCE.play(Assets.Sounds.DEGRADE);
            Buff.affect(ch, Slow.class, duration());
        } else if (ch.alignment == Char.Alignment.ALLY) {
            ch.sprite.emitter().burst( AccelerationParticle.FACTORY, 6 );
            Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
            Buff.affect(ch, Swiftthistle.TimeBubble.class).set(duration());
        }

        hero.spendAndNext(Actor.TICK);
    }

    public static class AccelerationParticle extends PixelParticle.Shrinking {
        public static final Emitter.Factory FACTORY = new Emitter.Factory() {
            @Override
            public void emit( Emitter emitter, int index, float x, float y ) {
                ((AccelerationParticle)emitter.recycle( AccelerationParticle.class )).reset( x, y );
            }
            @Override
            public boolean lightMode() {
                return true;
            }
        };

        public AccelerationParticle() {
            super();

            lifespan = 0.6f;

            color( 0xFFFF00 );
        }

        public void reset( float x, float y){
            revive();

            this.x = x+Random.Float(-4, 4);
            this.y = y+2;

            left = lifespan;
            size = 6;

            speed.set( 0, -16 );
            acc.set(0, -16);
        }

        @Override
        public void update() {
            super.update();

            am = 1 - left / lifespan;
        }
    }

    public static class SlowParticle extends PixelParticle.Shrinking {
        public static final Emitter.Factory FACTORY = new Emitter.Factory() {
            @Override
            public void emit( Emitter emitter, int index, float x, float y ) {
                ((SlowParticle)emitter.recycle( SlowParticle.class )).reset( x, y );
            }
            @Override
            public boolean lightMode() {
                return true;
            }
        };

        public SlowParticle() {
            super();

            lifespan = 0.6f;

            color( 0xFF8000 );
        }

        public void reset( float x, float y){
            revive();

            this.x = x+Random.Float(-4, 4);
            this.y = y+2;

            left = lifespan;
            size = 6;

            speed.set( 0, 16 );
            acc.set(0, 16);
        }

        @Override
        public void update() {
            super.update();

            am = 1 - left / lifespan;
        }
    }
}
