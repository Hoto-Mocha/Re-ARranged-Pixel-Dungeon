package com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.archer;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;

public class Hunt extends ArmorAbility {

    @Override
    protected void activate(ClassArmor armor, Hero hero, Integer target) {

    }

    @Override
    public int icon() {
        return HeroIcon.HUNT;
    }

    @Override
    public Talent[] talents() {
        return new Talent[]{Talent.CHASE, Talent.CHILL_BACK, Talent.CANNOT_ESCAPE, Talent.HEROIC_ENERGY};
    }
}
