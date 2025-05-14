package com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HolyTome;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class HolyMantle extends TargetedClericSpell {
    public static final HolyMantle INSTANCE = new HolyMantle();

    @Override
    public int icon(){
        return HeroIcon.HOLY_MANTLE;
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", 25*(1+Dungeon.hero.pointsInTalent(Talent.HOLY_MANTLE))) + "\n\n" + Messages.get(this, "charge_cost", (int)chargeUse(Dungeon.hero));
    }

    @Override
    protected void onTargetSelected(HolyTome tome, Hero hero, Integer target) {
        if (target == null) return;

        if (!Dungeon.level.heroFOV[target]) {
            GLog.w(Messages.get(ClericSpell.class, "not_in_fov"));
            return;
        }

        Char ch = Actor.findChar(target);
        if (ch == null) {
            GLog.w(Messages.get(ClericSpell.class, "no_target"));
            return;
        }

        Sample.INSTANCE.play(Assets.Sounds.READ);

        hero.busy();
        hero.spendAndNext(Actor.TICK);
        hero.sprite.operate(hero.pos);

        Buff.affect(ch, HolyMantleBuff.class);

        onSpellCast(tome, hero);
    }

    public static class HolyMantleBuff extends Buff {
        {
            type = buffType.POSITIVE;
            announced = true;
        }

        @Override
        public int icon() {
            return BuffIndicator.HOLY_MANTLE;
        }

        public static float DamageReduction(float damage) {
            return Math.max(0, damage * (1 - 0.25f * (1+Dungeon.hero.pointsInTalent(Talent.HOLY_MANTLE))));
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", 25*(1+Dungeon.hero.pointsInTalent(Talent.HOLY_MANTLE)));
        }
    }
}
