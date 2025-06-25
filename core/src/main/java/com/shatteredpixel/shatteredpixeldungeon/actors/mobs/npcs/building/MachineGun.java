package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.CorrosiveGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM201;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAccuracy;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.DisposableMissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MachineGunSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class MachineGun extends Building {
    {
        HP = HT = 20;

        alignment = Alignment.ALLY;

        spriteClass = MachineGunSprite.class;

        //before other mobs
        actPriority = MOB_PRIO + 1;

        properties.add(Property.IMMOVABLE);
        properties.add(Property.INORGANIC);

        state = WANDERING;
        viewDistance = 4;
    }

    public void beckon( int cell ) {

        notice();

        if (state != HUNTING && state != FLEEING) {
            state = WANDERING;
        }
        target = cell;
    }

    @Override
    public int attackSkill( Char target ) {
        if (Dungeon.hero != null) {
            //same base attack skill as hero, benefits from accuracy ring
            return (int)((9 + Dungeon.hero.lvl) * RingOfAccuracy.accuracyMultiplier(Dungeon.hero));
        } else {
            return 0;
        }
    }

    @Override
    public int damageRoll() {
        if (Dungeon.hero.pointsInTalent(Talent.MACHINEGUN) > 1) {
            return Random.NormalIntRange(6, 6+Math.round(Dungeon.scalingDepth()*2f));
        } else {
            return Random.NormalIntRange(4, 4+Math.round(Dungeon.scalingDepth()*1.33f));
        }
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(0, 5);
    }

    @Override
    public boolean canInteract(Char c) {
        return true;
    }

    @Override
    public boolean interact( Char c ) {
        if (c != Dungeon.hero){
            return true;
        }
        Game.runOnRenderThread(new Callback() {
            @Override
            public void call() {
                GameScene.show(new WndOptions( sprite(),
                        Messages.get(MachineGun.this, "dismiss_title"),
                        Messages.get(MachineGun.this, "dismiss_body"),
                        Messages.get(MachineGun.this, "dismiss_confirm"),
                        Messages.get(MachineGun.this, "dismiss_cancel") ){
                    @Override
                    protected void onSelect(int index) {
                        if (index == 0){
                            die(null);
                        }
                    }
                });
            }
        });
        return true;
    }

    @Override
    protected boolean act() {
        viewDistance = 4 + (Dungeon.hero.pointsInTalent(Talent.MACHINEGUN) == 3 ? 2 : 0);
        return super.act();
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        Ballistica aim = new Ballistica(this.pos, enemy.pos, Ballistica.DASH); //ignores chars
        return aim.collisionPos == enemy.pos;
    }

    @Override
    protected boolean getCloser(int target) {
        return false;
    }

    @Override
    protected boolean getFurther(int target) {
        return false;
    }
}
