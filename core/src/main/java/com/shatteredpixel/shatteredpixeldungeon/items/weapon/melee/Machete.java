package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.Rope;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Berry;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Machete extends MeleeWeapon {
    public static final String AC_SLASH	= "SLASH";

    {
        defaultAction = AC_SLASH;

        image = ItemSpriteSheet.MACHETE;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1.1f;

        tier = 1;
        DLY = 1.5f; //0.67x speed
        RCH = 2; //2x range

        unique = true;
        bones = false;
    }

    @Override
    public int reachFactor(Char owner) {
        int reach = super.reachFactor(owner);

        reach += Dungeon.hero.pointsInTalent(Talent.LONG_MACHETE);

        return reach;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_SLASH);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_SLASH)) {
            usesTargeting = true;
            curUser = hero;
            curItem = this;
            GameScene.selectCell(slasher);
        }
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        Buff.affect(defender, Bleeding.class).set(1+Math.round(Machete.this.buffedLvl()/2f));
        return super.proc( attacker, defender, damage );
    }

    @Override
    public int max(int lvl) {
        return  5*(tier+2) +
                lvl*(tier+2);
    }

    public void slash(int pos) {
        Hero hero = Dungeon.hero;
        if (pos == hero.pos) {
            GLog.w((Messages.get(this, "cannot_target")));
            return;
        }

//        if (Dungeon.level.map[pos] != Terrain.FURROWED_GRASS && Dungeon.level.map[pos] != Terrain.HIGH_GRASS) {
//            GLog.w(Messages.get(this, "no_grass"));
//            return;
//        }

        curUser.spendAndNext(Actor.TICK);
        Sample.INSTANCE.play(Assets.Sounds.HIT_SLASH, 2, Random.Float(0.9f, 1.3f));
        curUser.sprite.zap(pos);

        Ballistica aim = new Ballistica(curUser.pos, pos, Ballistica.STOP_TARGET);
        int dist = aim.dist;
        ConeAOE cone = new ConeAOE(aim,
                dist,
                90,
                Ballistica.STOP_TARGET | Ballistica.STOP_SOLID | Ballistica.IGNORE_SOFT_SOLID);

        int count = 0;
        for (int cell : cone.cells){
            if (Dungeon.level.map[cell] == Terrain.HIGH_GRASS || Dungeon.level.map[cell] == Terrain.FURROWED_GRASS) {
                if (Dungeon.level.map[cell] == Terrain.HIGH_GRASS) {
                    Dungeon.level.pressCell(cell);
                }
                Level.set(cell, Terrain.GRASS);
                GameScene.updateMap(cell);
                CellEmitter.get(cell).burst( LeafParticle.LEVEL_SPECIFIC, 4 );
                float chance = 0.5f;
                if (hero.hasTalent(Talent.ROPE_COLLECTOR)) {
                    chance *= 1+0.2f * hero.pointsInTalent(Talent.ROPE_COLLECTOR);
                }
                if (Random.Float() < chance) {
                    count ++;
                }

                //berries try to drop on floors 2/3/4/6/7/8, to a max of 4/6
                if (hero.hasTalent(Talent.HARVEST_BERRY)){
                    int berriesAvailable = 2 + 2*hero.pointsInTalent(Talent.HARVEST_BERRY);

                    Talent.HarvestBerriesDropped dropped = Buff.affect(hero, Talent.HarvestBerriesDropped.class);
                    berriesAvailable -= dropped.count();

                    if (berriesAvailable > 0) {
                        int targetFloor = 2 + 2 * hero.pointsInTalent(Talent.HARVEST_BERRY);
                        targetFloor -= berriesAvailable;
                        targetFloor += (targetFloor >= 5) ? 3 : 2;

                        //If we're behind: 1/10, if we're on page: 1/30, if we're ahead: 1/90
                        boolean droppingBerry = false;
                        if (Dungeon.depth > targetFloor) droppingBerry = Random.Int(10) == 0;
                        else if (Dungeon.depth == targetFloor) droppingBerry = Random.Int(30) == 0;
                        else if (Dungeon.depth < targetFloor) droppingBerry = Random.Int(90) == 0;

                        if (droppingBerry) {
                            dropped.countUp(1);
                            Dungeon.level.drop(new Berry(), cell).sprite.drop();
                        }
                    }

                }
            }
        }
        if (count > 0) {
            Rope rope = new Rope();
            rope.quantity(count);
            if (rope.doPickUp( Dungeon.hero )) {
                GLog.i( Messages.get(Dungeon.hero, "you_now_have", rope.name() ));
                hero.spend(-1);
            } else {
                Dungeon.level.drop( rope, Dungeon.hero.pos ).sprite.drop();
            }
        }
    }

    private CellSelector.Listener slasher = new CellSelector.Listener() {
        @Override
        public void onSelect( Integer target ) {
            if (target != null) {
                Char ch = Actor.findChar(target);
                Hero hero = Dungeon.hero;
                if (ch != null && ch.alignment == Char.Alignment.ENEMY) {
                    KindOfWeapon herosWeapon = hero.belongings.weapon; //기존에 사용하던 무기를 저장
                    hero.belongings.weapon = Machete.this; //공격에 사용할 무기를 마체테로 변경

                    if (!hero.canAttack(ch)) {
                        GLog.w(Messages.get(Machete.this, "cannot_reach"));
                    } else {
                        hero.sprite.zap(ch.pos);
                        hero.busy();
                        hero.spendAndNext(hero.attackDelay());
                        hero.attack(ch, 1, 0, 1);
                        Invisibility.dispel();
                    }

                    hero.belongings.weapon = herosWeapon; //영웅의 무기를 원래 무기로 되돌림
                } else {
                    if (Dungeon.level.distance(hero.pos, target) > Machete.this.reachFactor(hero)) {
                        GLog.w(Messages.get(Machete.this, "cannot_reach"));
                        return;
                    }
                    slash(target);
                }

            }
        }
        @Override
        public String prompt() {
            return Messages.get(SpiritBow.class, "prompt");
        }
    };
}
