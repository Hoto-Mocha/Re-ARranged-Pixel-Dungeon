package com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ThunderImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HolyTome;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class ThunderImbueSpell extends TargetedClericSpell {
    public static final ThunderImbueSpell INSTANCE = new ThunderImbueSpell();

    @Override
    public int icon(){
        return HeroIcon.THUNDER_IMBUE;
    }

    @Override
    public int targetingFlags(){
        return -1; //no targeting
    }

    private int duration() {
        return 10*Dungeon.hero.pointsInTalent(Talent.THUNDER_IMBUE);
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", duration()) + "\n\n" + Messages.get(this, "charge_cost", (int)chargeUse(Dungeon.hero));
    }

    @Override
    protected void onTargetSelected(HolyTome tome, Hero hero, Integer target) {
        if (target == null){
            return;
        }

        if (!Dungeon.level.heroFOV[target]) {
            GLog.w(Messages.get(ClericSpell.class, "not_in_fov"));
            return;
        }

        Char ch = Actor.findChar(target);
        if (ch == null) {
            GLog.w(Messages.get(ClericSpell.class, "no_target"));
            return;
        }

        hero.busy();
        hero.spendAndNext(Actor.TICK);
        hero.sprite.zap(target);

        Sample.INSTANCE.play(Assets.Sounds.READ);

//        //이미 버프를 가지고 있는 경우 해당 버프를 얻을 확률이 크게 감소. 만약 모든 버프를 가지고 있다면 다시 동일한 확률이 됨.
//        float[] chances = {100, 100, 100, 100};
//        if (ch.buff(FireImbue.class) != null)       chances[0] = 1;
//        if (ch.buff(FrostImbue.class) != null)      chances[1] = 1;
//        if (ch.buff(ToxicImbue.class) != null)      chances[2] = 1;
//        if (ch.buff(com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ThunderImbue.class) != null)    chances[3] = 1;
//
//        //버프는 중첩되지 않고 갱신된다.
//        switch (Random.chances(chances)) {
//            case 0: default:
//                Buff.affect(ch, FireImbue.class).set(duration());
//                break;
//            case 1:
//                Buff.prolong(ch, FrostImbue.class, duration());
//                break;
//            case 2:
//                Buff.affect(ch, ToxicImbue.class).set(duration());
//                break;
//            case 3:
//                Buff.affect(ch, com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ThunderImbue.class).set(duration());
//                break;
//        }

        Buff.affect(ch, ThunderImbue.class).set(duration());

        onSpellCast(tome, hero);
    }
}
