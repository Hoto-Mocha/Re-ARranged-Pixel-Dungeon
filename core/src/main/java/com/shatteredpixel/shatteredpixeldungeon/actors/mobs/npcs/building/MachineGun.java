package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Build;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CannonSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MachineGunSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MachineGun extends Building {
    {
        HP = HT = 20;

        state = PASSIVE;

        alignment = Alignment.ALLY;

        spriteClass = MachineGunSprite.class;

        actPriority = HERO_PRIO-1;

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
                int bulletReq = Dungeon.hero.pointsInTalent(Talent.MACHINEGUN) > 2 ? 1 : 2;
                if (Dungeon.bullet < bulletReq) {
                    GLog.w(Messages.get(MachineGun.class, "no_bullet"));
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

        CellEmitter.get(cell).burst(SmokeParticle.FACTORY, 4);
        CellEmitter.center(cell).burst(BlastParticle.FACTORY, 4);

        Char ch = Actor.findChar(cell);

        if (ch != null) {
            for (int i = 0; i < 3; i++) {
                Dungeon.hero.shoot(ch, new MachineGunBullet());
            }

            if (ch == Dungeon.hero && !ch.isAlive()) {
                Badges.validateDeathFromFriendlyMagic();
                GLog.n(Messages.get(Gun.class, "ondeath"));
                Dungeon.fail(this);
            }
        }

        cell = -1; //초기화

        next();
        Dungeon.hero.spendAndNext(Actor.TICK);
    }

    public static class MachineGunBullet extends MissileWeapon {
        {
            image = ItemSpriteSheet.TRIPLE_BULLET;
        }

        @Override
        public int damageRoll(Char owner) {
            return Random.NormalIntRange(5, Dungeon.hero.pointsInTalent(Talent.MACHINEGUN) > 1 ? 30 : 15);
        }
    }

    private CellSelector.Listener shooter = new CellSelector.Listener() {
        @Override
        public void onSelect(Integer target) {
            if (target != null) {
                int bulletReq = Dungeon.hero.pointsInTalent(Talent.MACHINEGUN) > 2 ? 1 : 2;
                if (target == MachineGun.this.pos) return;
                final Ballistica aim = new Ballistica(MachineGun.this.pos, target, Ballistica.PROJECTILE);
                cell = aim.collisionPos;
                MachineGun.this.sprite.zap(target);
                Dungeon.hero.sprite.zap(MachineGun.this.pos);
                CellEmitter.get(MachineGun.this.pos).burst(SmokeParticle.FACTORY, 4);
                CellEmitter.center(MachineGun.this.pos).burst(BlastParticle.FACTORY, 4);
                Sample.INSTANCE.play( Assets.Sounds.HIT_CRUSH, 1, Random.Float(0.33f, 0.66f) );
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
