package com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.archer;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Door;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.BArray;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

import java.util.HashSet;

public class DashAbility extends ArmorAbility {

    {
        baseChargeUse = 25;
    }

    @Override
    public float chargeUse(Hero hero) {
        if (hero.buff(KineticDashTracker.class) != null){
            //reduced charge use by 10%/21%/33%/46%
            return (float)(super.chargeUse(hero) * Math.pow(0.9, hero.pointsInTalent(Talent.KINETIC_DASH)));
        } else {
            return super.chargeUse(hero);
        }
    }

    @Override
    protected void activate(ClassArmor armor, Hero hero, Integer target) {
        if (target == null) {
            return;
        }

        if (Actor.findChar(target) == hero){
            GLog.w(Messages.get(this, "self_target"));
            return;
        }

        if (Actor.findChar(target) != null) {
            GLog.w(Messages.get(this, "there_is_something"));
            return;
        }


        if (hero.rooted){
            GLog.w( Messages.get(this, "rooted") );
            return;
        }

        int range = 4;

        range += 2*hero.pointsInTalent(Talent.LONGRANGE_DASH);

        if (Dungeon.level.distance(hero.pos, target) > range){
            GLog.w(Messages.get(this, "bad_position"));
            return;
        }

        PathFinder.buildDistanceMap(target, BArray.or(Dungeon.level.passable, Dungeon.level.avoid, null));
        if (PathFinder.distance[hero.pos] == Integer.MAX_VALUE){
            GLog.w( Messages.get(this, "bad_position") );
            return;
        }

        Ballistica dash = new Ballistica(hero.pos, target, Ballistica.DASH);

        if (!dash.collisionPos.equals(target)
                || Actor.findChar(target) != null
                || (Dungeon.level.solid[target] && !Dungeon.level.passable[target])
                || Dungeon.level.map[target] == Terrain.CHASM){
            GLog.w(Messages.get(this, "bad_position"));
            return;
        }

        hero.busy();
        Sample.INSTANCE.play(Assets.Sounds.MISS);
        hero.sprite.emitter().start(Speck.factory(Speck.JET), 0.01f, Math.round(4 + 2* Dungeon.level.trueDistance(hero.pos, target)));
        hero.sprite.jump(hero.pos, target, 0, 0.1f, new Callback() {
            @Override
            public void call() {
                if (Dungeon.level.map[hero.pos] == Terrain.OPEN_DOOR) {
                    Door.leave( hero.pos );
                }
                hero.pos = target;
                Dungeon.level.occupyCell(hero);

                if (hero.hasTalent(Talent.DUST_SPREAD)) {
                    for (int cell : dash.subPath(1, dash.dist)) {
                        Char ch = Actor.findChar(cell);
                        if (ch == null) continue;
                        if (ch.alignment != Char.Alignment.ENEMY) continue;

                        Buff.prolong(ch, Blindness.class, 2*hero.pointsInTalent(Talent.DUST_SPREAD));
                    }
                }

                armor.charge -= chargeUse(hero);
                Item.updateQuickslot();

                if (hero.hasTalent(Talent.KINETIC_DASH)) {
                    Buff.affect(hero, KineticDashTracker.class);
                }

                hero.next();
            }
        });
    }

    @Override
    public int icon() {
        return HeroIcon.DASH;
    }

    public String targetingPrompt(){
        return Messages.get(SpiritBow.class, "prompt");
    }

    @Override
    public Talent[] talents() {
        return new Talent[]{Talent.LONGRANGE_DASH, Talent.DUST_SPREAD, Talent.KINETIC_DASH, Talent.HEROIC_ENERGY};
    }

    public static class KineticDashTracker extends FlavourBuff {}
}
