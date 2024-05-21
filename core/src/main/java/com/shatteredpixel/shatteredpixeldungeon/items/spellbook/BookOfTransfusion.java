package com.shatteredpixel.shatteredpixeldungeon.items.spellbook;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfTransfusion;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

import java.text.DecimalFormat;

public class BookOfTransfusion extends SpellBook {

    {
        image = ItemSpriteSheet.BOOK_OF_TRANSFUSION;
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
        vampBuffEffect(3+hero.lvl/5);
    }

    @Override
    public String info() {
        String info = super.info();
        if (Dungeon.hero.buff(SpellBookCoolDown.class) == null) {
            info += "\n\n" + Messages.get(this, "time",
                    3+hero.lvl/5,
                    Math.round(5+Dungeon.scalingDepth()/2f),
                    new DecimalFormat("#.##").format(100*(0.2f+0.005f*hero.lvl)*(1+0.5f*hero.pointsInTalent(Talent.SPELL_ENHANCE))));
        }
        return info;
    }

    public static void vampBuffEffect(int radius) {
        Ballistica aim;
        //The direction of the aim only matters if it goes outside the map
        //So we try to aim in the cardinal direction that has the most space
        int x = Dungeon.hero.pos % Dungeon.level.width();
        int y = Dungeon.hero.pos / Dungeon.level.width();

        if (Math.max(x, Dungeon.level.width()-x) >= Math.max(y, Dungeon.level.height()-y)){
            if (x > Dungeon.level.width()/2){
                aim = new Ballistica(Dungeon.hero.pos, Dungeon.hero.pos - 1, Ballistica.WONT_STOP);
            } else {
                aim = new Ballistica(Dungeon.hero.pos, Dungeon.hero.pos + 1, Ballistica.WONT_STOP);
            }
        } else {
            if (y > Dungeon.level.height()/2){
                aim = new Ballistica(Dungeon.hero.pos, Dungeon.hero.pos - Dungeon.level.width(), Ballistica.WONT_STOP);
            } else {
                aim = new Ballistica(Dungeon.hero.pos, Dungeon.hero.pos + Dungeon.level.width(), Ballistica.WONT_STOP);
            }
        }
        int aoeSize = radius;
        int projectileProps = Ballistica.STOP_SOLID | Ballistica.STOP_TARGET;

        ConeAOE aoe = new ConeAOE(aim, aoeSize, 360, projectileProps);

        for (Ballistica ray : aoe.outerRays){
            ((MagicMissile)Dungeon.hero.sprite.parent.recycle( MagicMissile.class )).reset(
                    MagicMissile.BLOOD_CONE,
                    Dungeon.hero.sprite,
                    ray.path.get(ray.dist),
                    null
            );
        }

        //cast a ray 2/3 the way, and do effects
        ((MagicMissile)Dungeon.hero.sprite.parent.recycle( MagicMissile.class )).reset(
                MagicMissile.BLOOD_CONE,
                Dungeon.hero.sprite,
                aim.path.get(Math.min(aoeSize / 2, aim.path.size()-1)),
                new Callback() {
                    @Override
                    public void call() {
                        for (int cell : aoe.cells) {
                            Char mob = Actor.findChar(cell);
                            if (mob != null && mob.alignment != Char.Alignment.ALLY && mob instanceof Mob){
                                Buff.affect(mob, VampiricMark.class).set(Math.round(5+Dungeon.scalingDepth()/2f));
                                ((Mob) mob).beckon(Dungeon.hero.pos);
                            }
                        }
                    }
                }
        );

        Sample.INSTANCE.play( Assets.Sounds.CHARGEUP );

    }

    public static class VampiricMark extends Buff {
        {
            type = buffType.NEGATIVE;
            announced = true;
        }

        public static final float DURATION	= 30f;

        protected float left;

        private static final String LEFT	= "left";

        @Override
        public void storeInBundle( Bundle bundle ) {
            super.storeInBundle( bundle );
            bundle.put( LEFT, left );

        }

        @Override
        public void restoreFromBundle( Bundle bundle ) {
            super.restoreFromBundle( bundle );
            left = bundle.getFloat( LEFT );
        }

        public void set( float duration ) {
            this.left = duration;
        }

        @Override
        public boolean act() {

            spend(TICK);
            left -= TICK;
            if (left <= 0){
                detach();
            }

            return true;
        }

        public static void proc(Char attacker, int damage){
            attacker.heal(Math.round(damage*(0.2f+0.005f*hero.lvl)*(1+0.5f*hero.pointsInTalent(Talent.SPELL_ENHANCE))));
        }

        @Override
        public int icon() {
            return BuffIndicator.INVERT_MARK;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0xFF0000);
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, (DURATION - left) / DURATION);
        }

        @Override
        public String iconTextDisplay() {
            return Integer.toString((int)left);
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", dispTurns(left));
        }
    }
}
