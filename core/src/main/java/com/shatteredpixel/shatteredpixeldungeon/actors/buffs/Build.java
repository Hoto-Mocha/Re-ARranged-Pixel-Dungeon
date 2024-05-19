package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.Barricade;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.Cannon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.MachineGun;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.Mortar;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.WatchTower;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Pickaxe;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MinersTool;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBuild;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Point;

public class Build extends Buff implements ActionIndicator.Action {

    {
        revivePersists = true;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        ActionIndicator.setAction(this);
    }

    @Override
    public boolean attachTo(Char target) {
        if (super.attachTo(target)){
            if (hero != null) {
                if (hero.subClass == HeroSubClass.ENGINEER) {
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
            ActionIndicator.clearAction(this);
        }
    }

    @Override
    public String actionName() {
        return Messages.get(this, "action_name");
    }

    @Override
    public int actionIcon() {
        return HeroIcon.BUILD;
    }

    @Override
    public int indicatorColor() {
        return 0xB3B3B3;
    }

    @Override
    public void doAction() {
        GameScene.show(new WndBuild(this));
    }

    public enum Building {
        WALL        (20),
        FLOOR       (0),
        BARRICADE   (8),
        WIRE        (8),
        WATCHTOWER  (12),
        CANNON      (20),
        MACHINEGUN  (16),
        MORTAR      (20);

        public int metalReq;

        Building(int metalReq){
            this.metalReq = metalReq;
        }

        public String title(){
            return Messages.get(this, name() + ".name");
        }

        public String desc(){
            switch (this){
                default:
                    return Messages.get(this, name() + ".desc");
            }
        }
    }

    public boolean canBuild(Building building, int metalAmt){
        boolean haveMetal = metalAmt >= building.metalReq;
        switch (building) {
            case WALL:
            case FLOOR:
                return haveMetal;
            case BARRICADE:
                return haveMetal && hero.hasTalent(Talent.BARRICADE);
            case WIRE:
                return haveMetal && hero.hasTalent(Talent.WIRE);
            case WATCHTOWER:
                return haveMetal && hero.hasTalent(Talent.WATCHTOWER);
            case CANNON:
                return haveMetal && hero.hasTalent(Talent.CANNON);
            case MACHINEGUN:
                return haveMetal && hero.hasTalent(Talent.MACHINEGUN);
            case MORTAR:
                return haveMetal && hero.hasTalent(Talent.MORTAR);
        }
        return false;
    }

    public static Building beingBuilt;

    public void build(Building building) {
        beingBuilt = building;
        GameScene.selectCell(builder);
    }

    public boolean isBuildable(int target) {
        return Dungeon.level.passable[target]
                && Dungeon.level.map[target] != Terrain.ENTRANCE
                && Dungeon.level.map[target] != Terrain.UNLOCKED_EXIT
                && Dungeon.level.map[target] != Terrain.DOOR
                && Dungeon.level.map[target] != Terrain.OPEN_DOOR
                && Dungeon.level.map[target] != Terrain.EXIT
                && Actor.findChar(target) == null;
    }


    private CellSelector.Listener builder = new CellSelector.Listener() {
        @Override
        public void onSelect(Integer target) {
            if (target != null) {
                int metalUse = beingBuilt.metalReq;
                Level level = Dungeon.level;
                Trap t;
                Point coordinate = level.cellToPoint(target);
                int x = coordinate.x;
                int y = coordinate.y;
                if (x == 0 || x == level.width()-1 || y == 0 || y == level.height()-1) {
                    GLog.w(Messages.get(Build.class, "cant_edge"));
                    return;
                }
                if (!level.adjacent(hero.pos, target)) {
                    GLog.w(Messages.get(Build.class, "cant_build"));
                    return;
                }
                switch (beingBuilt) {
                    case WALL:
                        if (isBuildable(target)) {
                            buildEffect(target);
                            //나무 벽 건설
                            Level.set(target, Terrain.BARRICADE);
                            GameScene.updateMap(target);
                            Dungeon.observe();
                        } else {
                            if (level.map[target] == Terrain.BARRICADE) {
                                //액체 금속 회수
                                LiquidMetal metal = new LiquidMetal();
                                metal.quantity(metalUse/2);
                                if (metal.doPickUp( Dungeon.hero )) {
                                    GLog.i( Messages.get(Dungeon.hero, "you_now_have", metal.name() ));
                                    hero.spend(-1);
                                } else {
                                    Dungeon.level.drop( metal, Dungeon.hero.pos ).sprite.drop();
                                }
                                Sample.INSTANCE.play(Assets.Sounds.ROCKS);
                                if (Dungeon.level.heroFOV[ target ]){
                                    CellEmitter.get( target - Dungeon.level.width() ).start(Speck.factory(Speck.ROCK), 0.07f, 10);
                                }

                                //나무 벽 철거
                                Level.set(target, Terrain.EMPTY);
                                GameScene.updateMap(target);
                                Dungeon.observe();
                            } else {
                                GLog.w(Messages.get(Build.class, "invalid"));
                            }
                            return;
                        }
                        break;
                    case FLOOR:
                        if (level.map[target] == Terrain.CHASM) {
                            buildEffect(target);
                            //바닥 건설
                            Level.set(target, Terrain.EMPTY);
                            GameScene.updateMap(target);
                            Dungeon.observe();
                            level.addVisuals();
                        } else {
                            if (Dungeon.depth % 5 == 0 || Dungeon.depth == 31 || Dungeon.branch != 0) {
                                GLog.w(Messages.get(Build.class, "cant_regular"));
                                return;
                            }
                            if (isBuildable(target)) {
                                hero.sprite.operate(target);
                                hero.spend(3f);
                                hero.buff(Hunger.class).affectHunger(-3);
                                spendTurn();

                                //아이템을 떨어뜨림
                                Heap heap = Dungeon.level.heaps.get(target);
                                if (heap != null && heap.type != Heap.Type.FOR_SALE
                                        && heap.type != Heap.Type.LOCKED_CHEST
                                        && heap.type != Heap.Type.CRYSTAL_CHEST) {
                                    for (Item item : heap.items) {
                                        Dungeon.dropToChasm(item);
                                    }
                                    heap.sprite.kill();
                                    GameScene.discard(heap);
                                    Dungeon.level.heaps.remove(target);
                                }
                                if (heap != null && (heap.type == Heap.Type.FOR_SALE
                                        || heap.type == Heap.Type.LOCKED_CHEST
                                        || heap.type == Heap.Type.CRYSTAL_CHEST)) {
                                    GLog.w(Messages.get(Build.class, "cannot_mine"));
                                    return;
                                }

                                //캐릭터를 떨어뜨림, 하지만 애초에 캐릭터가 있는 곳에는 건설이 불가능(isBuildable() 참고)하기 때문에 비활성화
//                                        Char ch = Actor.findChar(target);
//
//                                        if (ch != null && !ch.flying) {
//                                            if (ch == Dungeon.hero) {
//                                                Chasm.heroFall(target);
//                                            } else {
//                                                Chasm.mobFall((Mob) ch);
//                                            }
//                                        }

                                Sample.INSTANCE.play(Assets.Sounds.ROCKS);
                                if (Dungeon.level.heroFOV[ target ]){
                                    CellEmitter.get( target ).start(Speck.factory(Speck.ROCK), 0.07f, 10);
                                }

                                //바닥 철거
                                Level.set(target, Terrain.CHASM);
                                GameScene.updateMap(target);
                                Dungeon.observe();
                                level.addVisuals();
                            } else {
                                GLog.w(Messages.get(Build.class, "invalid"));
                                return;
                            }
                        }
                        break;
                    case BARRICADE:
                        if (!isBuildable(target)) {
                            GLog.w(Messages.get(Build.class, "invalid"));
                            return;
                        }
                        buildEffect(target);

                        //모래 바리케이드 건설
                        Barricade barricade = new Barricade();
                        barricade.pos = target;
                        GameScene.add(barricade);

                        if ((t = level.traps.get(barricade.pos)) != null && t.active){
                            if (t.disarmedByActivation) t.disarm();
                            t.reveal();
                            t.activate();
                        }
                        level.occupyCell(barricade);

                        GameScene.updateMap(target);
                        if (hero.pointsInTalent(Talent.BARRICADE) > 2) {
                            metalUse /= 2;
                        }
                        break;
                    case WIRE:
                        if (!isBuildable(target)) {
                            GLog.w(Messages.get(Build.class, "invalid"));
                            return;
                        }
                        buildEffect(target);

                        //와이어 함정 건설
                        Level.set(target, Terrain.WIRE);
                        GameScene.updateMap(target);

                        if (hero.pointsInTalent(Talent.WIRE) > 2) {
                            metalUse /= 2;
                        }
                        break;
                    case WATCHTOWER:
                        if (!isBuildable(target)) {
                            GLog.w(Messages.get(Build.class, "invalid"));
                            return;
                        }
                        buildEffect(target);

                        //감시 타워 건설
                        WatchTower tower = new WatchTower();
                        tower.pos = target;
                        GameScene.add(tower);

                        if ((t = level.traps.get(tower.pos)) != null && t.active){
                            if (t.disarmedByActivation) t.disarm();
                            t.reveal();
                            t.activate();
                        }
                        level.occupyCell(tower);

                        GameScene.updateMap(target);

                        if (hero.pointsInTalent(Talent.WATCHTOWER) > 2) {
                            metalUse /= 2;
                        }
                        break;
                    case CANNON:
                        if (!isBuildable(target)) {
                            GLog.w(Messages.get(Build.class, "invalid"));
                            return;
                        }
                        buildEffect(target);

                        //야포 건설
                        Cannon cannon = new Cannon();
                        cannon.pos = target;
                        GameScene.add(cannon);

                        if ((t = level.traps.get(cannon.pos)) != null && t.active){
                            if (t.disarmedByActivation) t.disarm();
                            t.reveal();
                            t.activate();
                        }
                        level.occupyCell(cannon);

                        GameScene.updateMap(target);
                        break;
                    case MACHINEGUN:
                        if (!isBuildable(target)) {
                            GLog.w(Messages.get(Build.class, "invalid"));
                            return;
                        }
                        buildEffect(target);

                        //거치형 기관총 건설
                        MachineGun gun = new MachineGun();
                        gun.pos = target;
                        GameScene.add(gun);

                        if ((t = level.traps.get(gun.pos)) != null && t.active){
                            if (t.disarmedByActivation) t.disarm();
                            t.reveal();
                            t.activate();
                        }
                        level.occupyCell(gun);

                        GameScene.updateMap(target);
                        break;
                    case MORTAR:
                        if (!isBuildable(target)) {
                            GLog.w(Messages.get(Build.class, "invalid"));
                            return;
                        }
                        buildEffect(target);

                        //박격포 건설
                        Mortar mortar = new Mortar();
                        mortar.pos = target;
                        GameScene.add(mortar);

                        if ((t = level.traps.get(mortar.pos)) != null && t.active){
                            if (t.disarmedByActivation) t.disarm();
                            t.reveal();
                            t.activate();
                        }
                        level.occupyCell(mortar);

                        GameScene.updateMap(target);
                        break;
                }
                LiquidMetal metal = hero.belongings.getItem(LiquidMetal.class);
                if (metal != null) {
                    metal.quantity(metal.quantity() - metalUse);
                    if (metal.quantity() <= 0) {
                        metal.detach(hero.belongings.backpack);
                    }
                }
                Item.updateQuickslot();
            }
        }

        @Override
        public String prompt() {
            return Messages.get(Build.class, "prompt");
        }
    };

    public void buildEffect(int target) {
        CellEmitter.center( target ).burst( Speck.factory( Speck.STAR ), 7 );
        Sample.INSTANCE.play( Assets.Sounds.EVOKE );
        hero.sprite.operate(target);
        spendTurn();
    }

    public void spendTurn() {
        float turn = Actor.TICK*2;
        float hungerUse = 6-turn;
        if (hero.belongings.getItem(Pickaxe.class) != null || hero.belongings.getItem(MinersTool.class) != null) {
            turn *= 0.5f;
            hungerUse *= 0.5f;
        }
        hero.buff(Hunger.class).affectHunger(-hungerUse);
        hero.spendAndNext(turn);
    }

}
