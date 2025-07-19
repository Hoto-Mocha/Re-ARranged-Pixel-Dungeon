package com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.archer;

import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class Hunt extends ArmorAbility {

    @Override
    protected void activate(ClassArmor armor, Hero hero, Integer target) {
        if (target == null) return;

        if (target == hero.pos) {
            GLog.w(Messages.get(this, "self_target"));
            return;
        }

        Char ch = Actor.findChar(target);
        if (ch == null) {
            GLog.w(Messages.get(this, "no_target"));
            return;
        }

        if (ch.alignment != Char.Alignment.ENEMY) {
            GLog.w(Messages.get(this, "no_enemy"));
            return;
        }

        int duration = 10;
        duration += 5*hero.pointsInTalent(Talent.CHASE);

        Buff.prolong(ch, Terror.class, duration);
        Buff.prolong(ch, Vulnerable.class, duration);

        if (hero.hasTalent(Talent.CHILL_BACK)) {
            Buff.prolong(ch, Chill.class, 3*hero.pointsInTalent(Talent.CHILL_BACK));
        }

        if (hero.hasTalent(Talent.CANNOT_ESCAPE)) {
            Buff.affect(hero, Haste.class, 2*hero.pointsInTalent(Talent.CANNOT_ESCAPE));
        }

        armor.charge -= chargeUse(hero);
        Item.updateQuickslot();
    }

    @Override
    public int icon() {
        return HeroIcon.HUNT;
    }

    public String targetingPrompt(){
        return Messages.get(SpiritBow.class, "prompt");
    }

    @Override
    public Talent[] talents() {
        return new Talent[]{Talent.CHASE, Talent.CHILL_BACK, Talent.CANNOT_ESCAPE, Talent.HEROIC_ENERGY};
    }
}
