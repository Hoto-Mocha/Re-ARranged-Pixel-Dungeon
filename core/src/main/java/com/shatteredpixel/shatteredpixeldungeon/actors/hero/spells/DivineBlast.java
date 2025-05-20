package com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HolyTome;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.TenguDartTrap;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

public class DivineBlast extends TargetedClericSpell {

    public static final DivineBlast INSTANCE = new DivineBlast();

    @Override
    public int icon(){
        return HeroIcon.DIVINE_BLAST;
    }

    @Override
    public float chargeUse(Hero hero) {
        return 2;
    }

    @Override
    public int targetingFlags(){
        return -1; //no targeting
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", Math.max(1, Dungeon.hero.pointsInTalent(Talent.DIVINE_BLAST))) + "\n\n" + Messages.get(this, "charge_cost", (int)chargeUse(Dungeon.hero));
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

        Sample.INSTANCE.play(Assets.Sounds.READ);
        hero.sprite.operate(target, new Callback() {
            @Override
            public void call() {
                hero.sprite.idle();
                WandOfBlastWave.BlastWave.blast(target);
                //presses all tiles in the AOE first, with the exception of tengu dart traps
                for (int i : PathFinder.NEIGHBOURS9){
                    if (!(Dungeon.level.traps.get(target+i) instanceof TenguDartTrap)) {
                        Dungeon.level.pressCell(target + i);
                    }
                }

                //throws other chars around the center.
                for (int i  : PathFinder.NEIGHBOURS8){
                    Char ch = Actor.findChar(target + i);

                    if (ch != null){
                        //do not push chars that are dieing over a pit, or that move due to the damage
                        if ((ch.isAlive() || ch.flying || !Dungeon.level.pit[ch.pos])
                                && ch.pos == target + i) {
                            Ballistica trajectory = new Ballistica(ch.pos, ch.pos + i, Ballistica.MAGIC_BOLT);
                            int strength = hero.pointsInTalent(Talent.DIVINE_BLAST);
                            WandOfBlastWave.throwChar(ch, trajectory, strength, false, true, this);
                        }
                    }
                }

                //paralyzes the center target
                Char ch = Actor.findChar(target);
                if (ch != null && ch.alignment == Char.Alignment.ENEMY && !ch.isImmune(Paralysis.class)){
                    Buff.affect(ch, Paralysis.class, 2);
                }

                hero.spend( 1f );
                hero.next();
                onSpellCast(tome, hero);
            }
        });
    }
}
