package com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Degradation;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HolyTome;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PointF;

public class WeakeningHex extends TargetedClericSpell {
    public static final WeakeningHex INSTANCE = new WeakeningHex();

    @Override
    public int icon(){
        return HeroIcon.WEAKENING_HEX;
    }

    @Override
    public String desc() {
        StringBuilder s = new StringBuilder();
        s.append(new Hex().name());
        if (Dungeon.hero.pointsInTalent(Talent.WEAKENING_HEX) >= 2) {
            s.append(", ").append(new Weakness().name());
        }
        if (Dungeon.hero.pointsInTalent(Talent.WEAKENING_HEX) >= 3) {
            s.append(", ").append(new Vulnerable().name());
        }
        return Messages.get(this, "desc", s.toString())+ "\n\n" + Messages.get(this, "charge_cost", (int)chargeUse(Dungeon.hero));
    }

    private float duration() {
        return 5f;
    }

    @Override
    public float chargeUse(Hero hero) {
        return 2;
    }

    @Override
    protected void onTargetSelected(HolyTome tome, Hero hero, Integer target) {
        if (target == null) return;

        Char ch = Actor.findChar(target);
        if (ch == null) {
            GLog.w(Messages.get(ClericSpell.class, "no_target"));
            return;
        }

        hero.busy();
        hero.spendAndNext(Actor.TICK);
        hero.sprite.operate(hero.pos);

        Sample.INSTANCE.play(Assets.Sounds.READ);

        Buff.affect(ch, Hex.class, duration());
        if (Dungeon.hero.pointsInTalent(Talent.WEAKENING_HEX) >= 2) {
            Buff.affect(ch, Weakness.class, duration());
        }
        if (Dungeon.hero.pointsInTalent(Talent.WEAKENING_HEX) >= 3) {
            Buff.affect(ch, Vulnerable.class, duration());
        }

        ch.sprite.parent.add( Degradation.weapon( new PointF(Dungeon.level.cellToPoint(ch.pos))) );

        Sample.INSTANCE.play(Assets.Sounds.DEGRADE);

        onSpellCast(tome, hero);
    }
}
