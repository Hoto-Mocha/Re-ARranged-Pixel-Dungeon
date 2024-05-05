package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfWarding;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BarricadeSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Barricade extends Building {
    int maxDr;
    {
        if (Dungeon.hero.pointsInTalent(Talent.BARRICADE) > 1) {
            HP = HT = 30+Dungeon.depth*5;
        } else {
            HP = HT = 60+Dungeon.depth*5;
        }

        maxDr = Dungeon.hero.pointsInTalent(Talent.BARRICADE) > 1 ? 10 : 5;

        state = PASSIVE;

        alignment = Alignment.ALLY;

        spriteClass = BarricadeSprite.class;

        actPriority = MOB_PRIO-1;

        properties.add(Property.IMMOVABLE);
        properties.add(Property.INORGANIC);
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(0, maxDr);
    }

    @Override
    protected boolean act() {
        for (Char ch : Actor.chars()) {
            if (ch instanceof Mob) {
                Mob enemy = (Mob)ch;
                if (enemy.targetingChar() == this
                        && enemy.fieldOfView != null
                        && enemy.fieldOfView[Dungeon.hero.pos]) {
                    enemy.aggro(Dungeon.hero);
                }
            }
        }

        return super.act();
    }

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
