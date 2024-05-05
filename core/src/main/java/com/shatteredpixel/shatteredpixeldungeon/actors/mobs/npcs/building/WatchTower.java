package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.WatchTowerSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class WatchTower extends Building {
    {
        HP = HT = 15;

        state = PASSIVE;

        alignment = Alignment.ALLY;

        spriteClass = WatchTowerSprite.class;

        actPriority = HERO_PRIO+1;

        properties.add(Property.IMMOVABLE);
        properties.add(Property.INORGANIC);

        updateFOV();
    }

    public void updateFOV() {
        viewDistance = 4;
        if (Dungeon.hero.pointsInTalent(Talent.WATCHTOWER) > 1) {
            viewDistance = 6;
        }

        Dungeon.observe();
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(0, 5);
    }

    @Override
    protected boolean act() {
        for (Char ch : Actor.chars()) {
            if (ch instanceof Mob) {
                Mob enemy = (Mob)ch;
                if (!(enemy.targetingChar() instanceof WatchTower)
                        && enemy.fieldOfView != null
                        && enemy.fieldOfView[this.pos]) {
                    enemy.aggro(this);
                }
            }
        }

        return super.act();
    }

    @Override
    public void destroy() {
        super.destroy();
        Dungeon.observe();
        GameScene.updateFog(pos, viewDistance+1);
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
                GameScene.show(new WndOptions( sprite(),
                        Messages.get(Building.class, "dismiss_title"),
                        Messages.get(Building.class, "dismiss_body"),
                        Messages.get(Building.class, "dismiss_confirm"),
                        Messages.get(Building.class, "dismiss_cancel") ){
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
}
