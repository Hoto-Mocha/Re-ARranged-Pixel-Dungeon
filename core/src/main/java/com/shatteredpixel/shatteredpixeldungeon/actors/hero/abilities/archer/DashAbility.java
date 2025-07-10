package com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.archer;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;

public class DashAbility extends ArmorAbility {

    @Override
    protected void activate(ClassArmor armor, Hero hero, Integer target) {

    }

    @Override
    public int icon() {
        return HeroIcon.DASH;
    }

    @Override
    public Talent[] talents() {
        return new Talent[]{Talent.LONGRANGE_DASH, Talent.DUST_SPREAD, Talent.KINETIC_DASH, Talent.HEROIC_ENERGY};
    }
}
