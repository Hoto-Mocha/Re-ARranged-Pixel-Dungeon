package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MortarSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Mortar extends Building {
    {
        HP = HT = 20;

        state = PASSIVE;

        alignment = Alignment.ALLY;

        spriteClass = MortarSprite.class;

        actPriority = HERO_PRIO+1;

        properties.add(Property.IMMOVABLE);
        properties.add(Property.INORGANIC);
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(0, 5);
    }

//    @Override
//    protected boolean act() {
//        for (Char ch : Actor.chars()) {
//            if (ch instanceof Mob) {
//                Mob enemy = (Mob)ch;
//                if (enemy.targetingChar() == this
//                        && enemy.fieldOfView != null
//                        && enemy.fieldOfView[Dungeon.hero.pos]) {
//                    enemy.aggro(Dungeon.hero);
//                }
//            }
//        }
//
//        return super.act();
//    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public boolean canInteract(Char c) {
        return Dungeon.level.adjacent(this.pos, c.pos);
    }

    @Override
    public boolean interact( Char c ) {
        if (c != Dungeon.hero){
            return true;
        }
        Game.runOnRenderThread(new Callback() {
            @Override
            public void call() {
                int bulletReq = Dungeon.hero.pointsInTalent(Talent.MORTAR) > 2 ? 5 : 10;
                if (Dungeon.bullet < bulletReq) {
                    GLog.w(Messages.get(Mortar.class, "no_bullet"));
                    return;
                }
                GameScene.selectCell(shooter);
            }
        });
        return true;
    }

    public int cell = -1;

    @Override
    public void onAttackComplete() {
        if (cell == -1) return;

        ArrayList<Char> affected = new ArrayList<>();

        if (Dungeon.level.heroFOV[cell]) {
            CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
            Sample.INSTANCE.play(Assets.Sounds.BLAST);
        }

        boolean terrainAffected = false;
        for (int n : PathFinder.NEIGHBOURS9) {
            int c = cell + n;
            if (c >= 0 && c < Dungeon.level.length()) {
                if (Dungeon.level.heroFOV[c]) {
                    CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
                }

                if (Dungeon.level.flamable[c]) {
                    Dungeon.level.destroy(c);
                    GameScene.updateMap(c);
                    terrainAffected = true;
                }

                //destroys items / triggers bombs caught in the blast.
                Heap heap = Dungeon.level.heaps.get(c);
                if (heap != null)
                    heap.explode();

                Char ch = Actor.findChar(c);
                if (ch != null) {
                    affected.add(ch);
                }
            }
        }

        for (Char ch : affected){
            //if they have already been killed by another bomb
            if( !ch.isAlive() ){
                continue;
            }

            int dmg = Random.NormalIntRange(20, Dungeon.hero.pointsInTalent(Talent.CANNON) > 1 ? 150 : 100);

            //those not at the center of the blast take less damage
            if (ch.pos != cell){
                dmg = Math.round(dmg*0.67f);
            }

            dmg -= ch.drRoll();

            if (dmg > 0) {
                ch.damage(dmg, this);
            }

            if (ch == Dungeon.hero && !ch.isAlive()) {
                Badges.validateDeathFromFriendlyMagic();
                GLog.n(Messages.get(Gun.class, "ondeath"));
                Dungeon.fail(this);
            }
        }

        if (terrainAffected) {
            Dungeon.observe();
        }

        cell = -1; //초기화

        next();
        Dungeon.hero.spendAndNext(Actor.TICK*3);
    }

    private CellSelector.Listener shooter = new CellSelector.Listener() {
        @Override
        public void onSelect(Integer target) {
            if (target != null) {
                int bulletReq = Dungeon.hero.pointsInTalent(Talent.MORTAR) > 2 ? 3 : 6;
                if (target == Mortar.this.pos) return;
                cell = target;
                Mortar.this.sprite.zap(target);
                Dungeon.hero.sprite.zap(Mortar.this.pos);
                CellEmitter.get(Mortar.this.pos).burst(SmokeParticle.FACTORY, 4);
                CellEmitter.center(Mortar.this.pos).burst(BlastParticle.FACTORY, 4);
                Sample.INSTANCE.play(Assets.Sounds.HIT_CRUSH, 0.7f, 0.3f);
                Sample.INSTANCE.play(Assets.Sounds.HIT_CRUSH, 0.7f, 0.2f);
                Dungeon.bullet -= bulletReq;
                Item.updateQuickslot();
            }
        }

        @Override
        public String prompt() {
            return Messages.get(SpiritBow.class, "prompt");
        }
    };
}
