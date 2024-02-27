package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Preparation;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroAction;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NPC;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.GL.GL;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.BArray;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class Sheath extends Item {

    public static final String AC_USE	= "USE";

    {
        image = ItemSpriteSheet.SHEATH;
        defaultAction = AC_USE;

        unique = true;
        bones = false;
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_USE);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals( AC_USE )) {
            if (hero.belongings.weapon instanceof MeleeWeapon) {
                if (hero.buff(Sheathing.class) != null) {
                    hero.buff(Sheathing.class).detach();
                } else {
                    Buff.affect(hero, Sheathing.class);
                }
                hero.spendAndNext(Actor.TICK);
            } else {
                GLog.w(Messages.get(this, "no_weapon"));
            }
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

    public static boolean isFlashSlash() {
        return hero.subClass == HeroSubClass.MASTER &&
                    hero.buff(Sheathing.class) != null &&
                    hero.buff(FlashSlashCooldown.class) == null &&
                    hero.buff(DashAttackTracker.class) == null;
    }

    public static class Sheathing extends Buff implements ActionIndicator.Action {
        {
            type = buffType.POSITIVE;
            announced = true;
        }

        public int pos = -1;

        @Override
        public boolean attachTo(Char target) {
            if (super.attachTo(target)){
                if (hero != null) {
                    Dungeon.observe();
                    if (hero.subClass == HeroSubClass.MASTER && hero.buff(DashAttackCooldown.class) == null) {
                        ActionIndicator.setAction(this);
                    }
                }
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void detach() {
            super.detach();
            if (hero != null) {
                Dungeon.observe();
                GameScene.updateFog();
                ActionIndicator.clearAction(this);
            }
        }

        @Override
        public boolean act() {
            if (hero.subClass == HeroSubClass.MASTER) {
                if (hero.buff(DashAttackCooldown.class) == null) {
                    ActionIndicator.setAction(this);
                } else {
                    ActionIndicator.clearAction(this);
                }
            }

            if (pos == -1) pos = target.pos;
            if (pos != target.pos || !(hero.belongings.weapon instanceof MeleeWeapon)) {
                detach();
            } else {
                spend(TICK);
            }
            return true;
        }

        @Override
        public String actionName() {
            return Messages.get(this, "action");
        }

        @Override
        public int actionIcon() {
            return HeroIcon.PREPARATION;
        }

        @Override
        public int indicatorColor() {
            return 0x171717;
        }

        @Override
        public void doAction() {
            GameScene.selectCell(attack);
        }

        private static final String POS = "pos";
        private static final String CAN_DASH = "canDash";
        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put( POS, pos );
            bundle.put( CAN_DASH, (hero.subClass == HeroSubClass.MASTER && hero.buff(DashAttackCooldown.class) == null) );
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            pos = bundle.getInt( POS );

            if (bundle.getBoolean(CAN_DASH)) {
                ActionIndicator.setAction(this);
            }
        }

        @Override
        public int icon() {
            return BuffIndicator.SHEATHING;
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc");
        }

        public int blinkDistance(){
            return 500;
        }

        private CellSelector.Listener attack = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell == null) return;
                final Char enemy = Actor.findChar( cell );
                if (enemy != null) {
                    if (Dungeon.hero.isCharmedBy(enemy) || enemy instanceof NPC || enemy == Dungeon.hero) {
                        GLog.w(Messages.get(Sheathing.class, "no_target"));
                    } else {
                        //just attack them then!
                        if (Dungeon.hero.canAttack(enemy)){
                            Dungeon.hero.curAction = new HeroAction.Attack( enemy );
                            Dungeon.hero.next();
                            return;
                        }

                        PathFinder.buildDistanceMap(Dungeon.hero.pos,BArray.or(Dungeon.level.passable, Dungeon.level.avoid, null), blinkDistance());
                        int dest = -1;
                        for (int i : PathFinder.NEIGHBOURS8){
                            //cannot blink into a cell that's occupied or impassable, only over them
                            if (Actor.findChar(cell+i) != null)     continue;
                            if (!Dungeon.level.passable[cell+i] && !(target.flying && Dungeon.level.avoid[cell+i])) {
                                continue;
                            }

                            if (dest == -1 || PathFinder.distance[dest] > PathFinder.distance[cell+i]){
                                dest = cell+i;
                                //if two cells have the same pathfinder distance, prioritize the one with the closest true distance to the hero
                            } else if (PathFinder.distance[dest] == PathFinder.distance[cell+i]){
                                if (Dungeon.level.trueDistance(Dungeon.hero.pos, dest) > Dungeon.level.trueDistance(Dungeon.hero.pos, cell+i)){
                                    dest = cell+i;
                                }
                            }
                        }

                        if (dest == -1 || PathFinder.distance[dest] == Integer.MAX_VALUE || Dungeon.hero.rooted) {
                            GLog.w(Messages.get(Sheathing.class, "cannot_dash"));
                            if (Dungeon.hero.rooted) PixelScene.shake( 1, 1f );
                            return;
                        }

                        Buff.affect(hero, DashAttackTracker.class);

                        Dungeon.hero.pos = dest;
                        Dungeon.level.occupyCell(Dungeon.hero);
                        //prevents the hero from being interrupted by seeing new enemies
                        Dungeon.observe();
                        GameScene.updateFog();
                        Dungeon.hero.checkVisibleMobs();

                        Dungeon.hero.sprite.place( Dungeon.hero.pos );
                        Dungeon.hero.sprite.turnTo( Dungeon.hero.pos, cell);
                        CellEmitter.get( Dungeon.hero.pos ).burst( Speck.factory( Speck.WOOL ), 6 );
                        Sample.INSTANCE.play( Assets.Sounds.PUFF );

                        Dungeon.hero.curAction = new HeroAction.Attack( enemy );
                        Dungeon.hero.next();
                    }
                } else {
                    int dest;
                    PathFinder.buildDistanceMap(Dungeon.hero.pos,BArray.or(Dungeon.level.passable, Dungeon.level.avoid, null), blinkDistance());
                    if (!Dungeon.level.passable[cell] && !(target.flying && Dungeon.level.avoid[cell])) {
                        GLog.w(Messages.get(Sheathing.class, "cannot_dash"));
                        return;
                    } else {
                        dest = cell;
                    }
                    if (PathFinder.distance[dest] == Integer.MAX_VALUE || Dungeon.hero.rooted) {
                        GLog.w(Messages.get(Sheathing.class, "cannot_dash"));
                        if (Dungeon.hero.rooted) PixelScene.shake( 1, 1f );
                        return;
                    }

                    Dungeon.hero.pos = dest;
                    Dungeon.level.occupyCell(Dungeon.hero);
                    //prevents the hero from being interrupted by seeing new enemies
                    Dungeon.observe();
                    GameScene.updateFog();
                    Dungeon.hero.checkVisibleMobs();

                    Dungeon.hero.sprite.place( Dungeon.hero.pos );
                    Dungeon.hero.sprite.turnTo( Dungeon.hero.pos, cell);
                    CellEmitter.get( Dungeon.hero.pos ).burst( Speck.factory( Speck.WOOL ), 6 );
                    Sample.INSTANCE.play( Assets.Sounds.PUFF );

                    Dungeon.hero.spendAndNext(Actor.TICK);

                    GLog.w(Messages.get(Sheathing.class, "no_target"));
                    Buff.prolong(hero, DashAttackCooldown.class, (100-10*hero.pointsInTalent(Talent.DYNAMIC_PREPARATION)));
                    if (hero.buff(DashAttackAcceleration.class) != null) {
                        hero.buff(DashAttackAcceleration.class).detach();
                    }
                    ActionIndicator.clearAction(Sheathing.this);
                }
            }

            @Override
            public String prompt() {
                return Messages.get(SpiritBow.class, "prompt");
            }
        };

    }

    public static class CriticalAttack extends FlavourBuff {}

    public static class CertainCrit extends Buff {
        {
            type = buffType.POSITIVE;
            announced = true;
        }

        private int hitAmount = 0;

        public void set(int amount) {
            this.hitAmount = amount;
        }

        public void hit() {
            this.hitAmount--;
            if (this.hitAmount <= 0) {
                detach();
            }
        }

        public String iconTextDisplay(){
            return String.valueOf(hitAmount);
        }

        @Override
        public int icon() {
            return BuffIndicator.CRITICAL;
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", hitAmount);
        }
    }

    public static class FlashSlashCooldown extends FlavourBuff{
        public int icon() { return BuffIndicator.TIME; }
        public void tintIcon(Image icon) { icon.hardlight(0x586EDB); }
        public float iconFadePercent() { return Math.max(0, visualcooldown() / 30); }
    };

    public static class DashAttackCooldown extends FlavourBuff{
        public int icon() { return BuffIndicator.TIME; }
        public void tintIcon(Image icon) { icon.hardlight(0xFF7F00); }
        public float iconFadePercent() { return Math.max(0, visualcooldown() / 100); }
        @Override
        public void detach() {
            super.detach();
        }
    };

    public static class DashAttackTracker extends Buff {}

    public static class DashAttackVision extends FlavourBuff {}

    public static class DashAttackAcceleration extends FlavourBuff {
        {
            type = buffType.POSITIVE;
            announced = false;
        }

        public static final float DURATION = 10f;

        float dmgMulti = 1;

        public void hit() {
            dmgMulti += 0.05f;
            dmgMulti = Math.min(dmgMulti, 1+0.25f*hero.pointsInTalent(Talent.ACCELERATION));
        }

        public float getDmgMulti() {
            return dmgMulti;
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, (DURATION - visualcooldown()) / DURATION);
        }

        private static final String MULTI = "dmgMulti";
        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put( MULTI, dmgMulti );
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            dmgMulti = bundle.getFloat( MULTI );
        }

        @Override
        public int icon() {
            return BuffIndicator.CRITICAL;
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", Messages.decimalFormat("#", dmgMulti*100));
        }
    }
}
