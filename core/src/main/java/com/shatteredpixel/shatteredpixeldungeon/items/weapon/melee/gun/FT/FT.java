package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.FT;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public class FT extends Gun {

    {
        max_round = 2;
        round = max_round;
        shootingAccuracy = 1.5f;
    }

    @Override
    public int baseBulletMax(int lvl) {
        return 3 * (tier() + 1) +
                lvl * (tier() + 1);
    }

    @Override
    public int bulletUse() {
        return Math.max(0, (maxRound()-round)*2);
    }

    @Override
    public Bullet knockBullet(){
        return new FTBullet();
    }

    public class FTBullet extends Bullet {
        {
            hitSound = Assets.Sounds.BURNING;
            image = ItemSpriteSheet.NO_BULLET;
        }

        @Override
        protected void onThrow(int cell) {
            if (cell != curUser.pos) {
                Ballistica aim = new Ballistica(curUser.pos, cell, Ballistica.WONT_STOP);
                int maxDist = FT.this.tier + 1;
                int dist = Math.min(aim.dist, maxDist);
                ConeAOE cone = new ConeAOE(aim,
                        dist,
                        30,
                        Ballistica.STOP_TARGET | Ballistica.STOP_SOLID | Ballistica.IGNORE_SOFT_SOLID);
                //cast to cells at the tip, rather than all cells, better performance.
                for (Ballistica ray : cone.outerRays){
                    ((MagicMissile)curUser.sprite.parent.recycle( MagicMissile.class )).reset(
                            MagicMissile.FIRE_CONE,
                            curUser.sprite,
                            ray.path.get(ray.dist),
                            null
                    );
                }
                ArrayList<Char> chars = new ArrayList<>();
                for (int cells : cone.cells){
                    //knock doors open
                    if (Dungeon.level.map[cells] == Terrain.DOOR){
                        Level.set(cells, Terrain.OPEN_DOOR);
                        GameScene.updateMap(cells);
                    }

                    //only ignite cells directly near caster if they are flammable
                    if (!(Dungeon.level.adjacent(curUser.pos, cells) && !Dungeon.level.flamable[cells])) {
                        GameScene.add(Blob.seed(cells, 2, Fire.class));
                    }

                    Char ch = Actor.findChar(cells);
                    if (ch != null && ch.alignment != hero.alignment){
                        chars.add(ch);
                    }
                }
                for (Char ch : chars) {
                    for (int i=0; i<shotPerShoot(); i++) {
                        curUser.shoot(ch, this);
                    }
                    if (ch == hero && !ch.isAlive()) {
                        Dungeon.fail(getClass());
                        Badges.validateDeathFromFriendlyMagic();
                        GLog.n(Messages.get(this, "ondeath"));
                    }
                }

                //final zap at 2/3 distance, for timing of the actual effect
                MagicMissile.boltFromChar(curUser.sprite.parent,
                        MagicMissile.FIRE_CONE,
                        curUser.sprite,
                        cone.coreRay.path.get(dist * 2 / 3),
                        new Callback() {
                            @Override
                            public void call() {
                            }
                        });
            }

            Invisibility.dispel();
            onShoot();
        }

        @Override
        public void throwSound() {
            Sample.INSTANCE.play(Assets.Sounds.BURNING, 1f);
        }
    }
}
