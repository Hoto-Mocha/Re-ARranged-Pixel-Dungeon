package com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArcaneArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HolyTome;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.watabou.noosa.audio.Sample;

public class ArcaneArmorSpell extends ClericSpell {
    public static final ArcaneArmorSpell INSTANCE = new ArcaneArmorSpell();

    @Override
    public int icon(){
        return HeroIcon.ARCANE_ARMOR;
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", interval()) + "\n\n" + Messages.get(this, "charge_cost", (int)chargeUse(Dungeon.hero));
    }

    private int interval() {
        return 10*Dungeon.hero.pointsInTalent(Talent.ARCANE_ARMOR);
    }

    @Override
    public void onCast(HolyTome tome, Hero hero) {
        hero.busy();
        hero.sprite.operate(hero.pos);
        hero.spendAndNext(Actor.TICK);

        Sample.INSTANCE.play(Assets.Sounds.READ);

        Buff.affect(hero, ArcaneArmor.class).add(1, interval());

        onSpellCast(tome, hero);
    }
}
