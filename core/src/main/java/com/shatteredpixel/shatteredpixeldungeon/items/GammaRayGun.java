package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AllyBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LostInventory;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RadioactiveMutation;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SaviorAllyBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.StimPack;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PoisonParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class GammaRayGun extends Item {

    {
        image = ItemSpriteSheet.GAMMA_RAY_GUN;

        defaultAction = AC_USE;
        usesTargeting = true;

        bones = false;
        unique = true;
    }

    private static final String AC_USE = "USE";

    private float powerMulti() {
        float multi = 1f;
        if (hero.hasTalent(Talent.HIGH_POWER)) {
            multi += 0.25f * hero.pointsInTalent(Talent.HIGH_POWER);
        }
        return multi;
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add(AC_USE);
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals(AC_USE)) {
            usesTargeting = true;
            curUser = hero;
            curItem = this;
            GameScene.selectCell(shooter);
        }
    }

    private CellSelector.Listener shooter = new CellSelector.Listener() {
        @Override
        public void onSelect( Integer target ) {
            if (target != null) {
                if (target == curUser.pos) {
                    GLog.w(Messages.get(this, "cannot_self"));
                    return;
                } else {
                    Ballistica beam = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
                    Char ch = Actor.findChar(beam.collisionPos);
                    if (ch != null) {
                        if (ch.alignment == Char.Alignment.ENEMY) {
                            Buff.affect(ch, Poison.class).set( Math.round((3f + 2f*Dungeon.depth / 3f) * powerMulti()) );
                            if (Dungeon.level.heroFOV[ch.pos]) {
                                CellEmitter.center( ch.pos ).burst( PoisonParticle.SPLASH, 3 );
                            }
                            if (curUser.hasTalent(Talent.RADIATION)) {
                                Buff.affect(ch, RadioactiveMutation.class).set(6-curUser.pointsInTalent(Talent.RADIATION));
                            }
                            if (curUser.subClass == HeroSubClass.SAVIOR) {
                                int allyNumber = 0;
                                for (Char mob : Actor.chars()) {
                                    if (mob.buff(SaviorAllyBuff.class) != null) {
                                        allyNumber++;
                                    }
                                }
                                if (ch instanceof Mob
                                        && allyNumber < 2 + hero.pointsInTalent(Talent.RECRUIT)
                                        && !ch.isImmune(SaviorAllyBuff.class)
                                        && Random.Float() < (ch.HT - ch.HP + 5*(1+hero.pointsInTalent(Talent.APPEASE))) / (float)ch.HT) {
                                    
                                    // 아군으로 만드는 버프
                                    AllyBuff.affectAndLoot((Mob)ch, curUser, SaviorAllyBuff.class);
                                    
                                    // 디버프 제거
                                    for (Buff b : ch.buffs()){
                                        if (b.type == Buff.buffType.NEGATIVE
                                                && !(b instanceof AllyBuff)
                                                && !(b instanceof LostInventory)){
                                            b.detach();
                                        }
                                        if (b instanceof Hunger){
                                            ((Hunger) b).satisfy(Hunger.STARVING);
                                        }
                                    }
                                    
                                    // 구원자 3-8 특성
                                    if (hero.hasTalent(Talent.DELAYED_HEALING)) {
                                        Buff.affect(ch, Healing.class).setHeal((int)(0.2f*hero.pointsInTalent(Talent.DELAYED_HEALING))*ch.HT, 0, 1);
                                    }
                                }
                            }
                        }
                        if (ch.alignment == Char.Alignment.ALLY && (ch != curUser)) {
                            int healAmt = Math.round((5f+curUser.lvl/2f)*powerMulti());
                            // 아군 회복
                            ch.heal(healAmt);

                            if (hero.hasTalent(Talent.ADRENALINE)) {
                                Buff.prolong(ch, Adrenaline.class, 3*hero.pointsInTalent(Talent.ADRENALINE));
                                Buff.affect(ch, Poison.class).set(3*hero.pointsInTalent(Talent.ADRENALINE));
                            }

                            if (hero.hasTalent(Talent.STIMPACK)) {
                                Buff.prolong(ch, StimPack.class, hero.pointsInTalent(Talent.STIMPACK));
                            }
                        }
                    }
                    curUser.sprite.zap(target);
                    curUser.sprite.parent.add( new Beam.GreenRay(curUser.sprite.center(), DungeonTilemap.raisedTileCenterToWorld(beam.collisionPos)) );
                }
                if (curUser.buff(GammaRayCooldown.class) != null) {
                    if (curUser.buff(Poison.class) != null) {
                        Buff.affect(curUser, Poison.class).extend(5+Math.round(curUser.lvl/2f));
                    } else {
                        Buff.affect(curUser, Poison.class).set(5+Math.round(curUser.lvl/2f));
                    }
                    BuffIndicator.refreshHero();
                    CellEmitter.center( curUser.pos ).burst( PoisonParticle.SPLASH, 3 );
                }
                if (Random.Float() < 0.33f) {
                    Buff.affect(hero, GammaRayCooldown.class).set(Random.NormalIntRange(3, 5) + hero.pointsInTalent(Talent.HIGH_POWER));
                }
                hero.spendAndNext(Actor.TICK);
            }
        }
        @Override
        public String prompt() {
            return Messages.get(SpiritBow.class, "prompt");
        }
    };

    public static class GammaRayCooldown extends Buff {

        int duration;

        {
            type = buffType.NEUTRAL;
        }

        public void set(int time) {
            duration = time;
        }

        @Override
        public boolean act() {
            duration-=TICK;
            spend(TICK);
            if (duration <= 0) {
                detach();
            }
            return true;
        }

        private static final String DURATION  = "duration";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(DURATION, duration);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            duration = bundle.getInt( DURATION );
        }
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public int value() {
        return -1;
    }
}