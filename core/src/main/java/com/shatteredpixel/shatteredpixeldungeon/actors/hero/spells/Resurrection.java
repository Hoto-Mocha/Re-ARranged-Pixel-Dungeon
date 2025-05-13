package com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HolyTome;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;

public class Resurrection extends ClericSpell {

    public static final Resurrection INSTANCE = new Resurrection();

    @Override
    public int icon(){
        return HeroIcon.RESURRECTION;
    }

    @Override
    public float chargeUse(Hero hero) {
        return 3;
    }

    @Override
    public boolean canCast(Hero hero) {
        return hero.buff(ResurrectionCooldown.class) == null && hero.buff(ResurrectionBuff.class) == null;
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", Dungeon.hero.pointsInTalent(Talent.RESURRECTION)+2) + "\n\n" + Messages.get(this, "charge_cost", (int)chargeUse(Dungeon.hero));
    }

    @Override
    public void onCast(HolyTome tome, Hero hero) {
        Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
        new Flare( 6, 32 ).color(0xFFFF00, true).show( hero.sprite, 1.2f );

        hero.busy();
        hero.spendAndNext(Actor.TICK);
        hero.sprite.operate(hero.pos);

        Buff.affect(hero, ResurrectionBuff.class, Dungeon.hero.pointsInTalent(Talent.RESURRECTION)+2);

        onSpellCast(tome, hero);
    }

    public static class ResurrectionBuff extends FlavourBuff {
        {
            type = buffType.POSITIVE;

            announced = true;
        }

        @Override
        public int icon() {
            return BuffIndicator.RESSURRECT;
        }

        @Override
        public float iconFadePercent() {
            float maxDuration = Dungeon.hero.pointsInTalent(Talent.RESURRECTION)+2;

            return Math.max(0, (maxDuration - visualcooldown()) / maxDuration);
        }
    }

    public static class ResurrectionCooldown extends FlavourBuff {
        {
            type = buffType.NEUTRAL;

            announced = false;
        }

        public static final float DURATION = 300f;

        @Override
        public int icon() {
            return BuffIndicator.TIME;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.tint(0x002157);
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, (DURATION - visualcooldown()) / DURATION);
        }
    }
}
