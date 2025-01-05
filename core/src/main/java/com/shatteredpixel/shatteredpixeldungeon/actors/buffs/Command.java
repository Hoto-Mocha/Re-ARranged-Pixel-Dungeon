package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.DirectableAlly;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.Building;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.Cannon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.MachineGun;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.Mortar;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.CursedWand;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MissileSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MobSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.ui.TargetHealthIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndCommand;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.Visual;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.Tweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Command extends Buff implements ActionIndicator.Action {
    {
        type = buffType.POSITIVE;
        revivePersists = true;
    }

    int charge = 0;

    public static final String CHARGE = "charge";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(CHARGE, charge);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        charge = bundle.getInt(CHARGE);
        ActionIndicator.setAction(this);
    }

    @Override
    public String actionName() {
        return Messages.get(this, "action_name");
    }

    @Override
    public int actionIcon() {
        return HeroIcon.COMMAND;
    }

    @Override
    public boolean attachTo(Char target) {
        if (super.attachTo(target)){
            if (hero != null) {
                ActionIndicator.setAction(this);
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
    public Visual secondaryVisual() {
        BitmapText txt = new BitmapText(PixelScene.pixelFont);
        txt.text( Integer.toString(this.charge) );
        txt.hardlight(CharSprite.POSITIVE);
        txt.measure();
        return txt;
    }

    @Override
    public int indicatorColor() {
        return 0x1F1F1F;
    }

    @Override
    public void doAction() {
        GameScene.show(new WndCommand(this));
    }

    //적을 처치하면 명령권을 얻는 메서드. 최대 6까지 스택 가능
    public void kill() {
        kill(false);
    }

    public void kill(boolean overcharge) {
        if (overcharge) { //최대치를 넘어서도 될 경우 최대치 적용 X
            this.charge++;
            ActionIndicator.setAction( this );
        } else {
            if (this.charge <= 6) { //현재 충전량이 6 이하인 경우에만 최대치를 적용. 이미 최대치를 넘어선 경우 충전량에 변화는 없다.
                this.charge = Math.min(++this.charge, 6);
            }
            ActionIndicator.setAction( this );
        }
    }

    //명령을 사용할 수 있는지 여부
    public boolean canUse(CommandMove command) {
        if (charge < commandChargeReq(command)) return false;
        switch (command) {
            case RECRUIT: case MEDICAL_SUPPORT: default:
                return true;
            case MOVE:
                return hero.hasTalent(Talent.MOVE_CMD);
            case STIMPACK:
                return hero.hasTalent(Talent.STIMPACK_CMD);
            case ENGINEER:
                return hero.hasTalent(Talent.ENGINEER_CMD);
            case PROMOTE:
                return hero.hasTalent(Talent.PROMOTE_CMD);
            case EXPLOSION:
                return hero.hasTalent(Talent.EXPLOSION_CMD);
            case CLOSE_AIR_SUPPORT:
                return hero.hasTalent(Talent.CAS_CMD);
        }
    }

    //입력받은 명령에 따라서 다른 명령을 실행하는 메서드
    public void useCommand(CommandMove command) {
        switch (command) {
            case RECRUIT:
                cmdRecruit();
                break;
            case MEDICAL_SUPPORT:
                cmdMedicalSupport();
                break;
            case MOVE:
                cmdMove();
                break;
            case STIMPACK:
                cmdStimpack();
                break;
            case ENGINEER:
                cmdEnginner();
                break;
            case PROMOTE:
                cmdPromote();
                break;
            case EXPLOSION:
                cmdExplosion();
                break;
            case CLOSE_AIR_SUPPORT:
                cmdCAS();
                break;
        }
    }

    /* --- 명령 수행 메서드 --- */
    
    public void cmdRecruit() {
        Hero hero = (Hero) target;
        ArrayList<Integer> spawnPoints = new ArrayList<>();
        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
            int p = hero.pos + PathFinder.NEIGHBOURS8[i];
            if (Actor.findChar(p) == null && Dungeon.level.passable[p]) {
                spawnPoints.add(p);
            }
        }

        if (!spawnPoints.isEmpty()) {
            useCharge(CommandMove.RECRUIT);

            SupportSoldier newAlly = new SupportSoldier();

            newAlly.pos = Random.element(spawnPoints);

            GameScene.add(newAlly, 1f);
            Dungeon.level.occupyCell(newAlly);

            Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
            CellEmitter.get(newAlly.pos).start( Speck.factory(Speck.LIGHT), 0.2f, 3 );

            hero.spendAndNext(1f);
            hero.sprite.operate(hero.pos);
        } else
            GLog.i( Messages.get(this, "no_space") );
    }

    public void cmdMedicalSupport() {
        ArrayList<SupportSoldier> allies = new ArrayList<>();
        for (Char ch : Actor.chars()) {
            if (ch instanceof SupportSoldier && ch.HP < ch.HT && Dungeon.level.heroFOV[ch.pos]) {
                allies.add((SupportSoldier) ch);
            }
        }
        if (!allies.isEmpty()) {
            for (SupportSoldier ally : allies) {
                Buff.affect(ally, Healing.class).setHeal(Math.round(ally.HT*0.2f), 0, (int)Math.ceil(Dungeon.scalingDepth()/5f));
            }

            Sample.INSTANCE.play(Assets.Sounds.BEACON);

            hero.spendAndNext(1f);
            hero.sprite.operate(hero.pos);

            useCharge(CommandMove.MEDICAL_SUPPORT);
        } else {
            GLog.w(Messages.get(this, "no_allies_to_cmd"));
        }
    }

    public void cmdMove() {
        GameScene.selectCell( cmdMoveCellSelector );
    }

    public void cmdStimpack() {
        ArrayList<SupportSoldier> allies = new ArrayList<>();
        for (Char ch : Actor.chars()) {
            if (ch instanceof SupportSoldier) {
                if (!Dungeon.level.heroFOV[ch.pos]) {
                    continue;
                }
                allies.add((SupportSoldier) ch);
            }
        }

        if (!allies.isEmpty()) {

            for (SupportSoldier ally : allies) {
                Buff.affect(ally, StimPack.class, 5*hero.pointsInTalent(Talent.STIMPACK_CMD));
            }

            useCharge(CommandMove.STIMPACK);

            Sample.INSTANCE.play(Assets.Sounds.BEACON);
            hero.spendAndNext(1f);
            hero.sprite.operate(hero.pos);
        } else {
            GLog.w(Messages.get(Command.class, "no_allies_to_cmd"));
        }
    }

    public void cmdEnginner() {
        GameScene.selectCell( cmdEngineerCellSelector );
    }

    public void cmdPromote() {
        GameScene.selectCell( cmdPromoteCellSelector );
    }

    public void cmdExplosion() {
        GameScene.selectCell( cmdExplosionCellSelector );
    }

    public void cmdCAS() {
        GameScene.selectCell( cmdCASCellSelector );
    }

    /* --- 명령 수행 메서드 --- */
    
    //명령에 필요한 명령권 수
    public static int commandChargeReq(CommandMove command) {
        switch (command) {
            default:
                return command.chargeReq;
            case RECRUIT:
                int allies = 0;
                for (Char ch : Actor.chars()) {
                    if (ch instanceof SupportSoldier) {
                        allies++;
                    }
                }
                return allies+1;
            case MOVE:
                return hero.pointsInTalent(Talent.MOVE_CMD) < 2 ? 1 : 0; //특성 레벨이 2 이상이면 명령권을 소모하지 않음
            case PROMOTE:
                return hero.pointsInTalent(Talent.PROMOTE_CMD) > 2 ? 2 : 3; //특성 레벨이 3이면 명령권 소모량 -1
        }
    }

    //명령권 사용 메서드
    public void useCharge(CommandMove command) {
        charge -= commandChargeReq(command);
        ActionIndicator.setAction(this);
    }

    public enum CommandMove {
        RECRUIT             (1),
        MEDICAL_SUPPORT     (1),
        MOVE                (1),
        STIMPACK            (1),
        ENGINEER            (1),
        PROMOTE             (3),
        EXPLOSION           (5),
        CLOSE_AIR_SUPPORT   (3);

        public int chargeReq;

        CommandMove(int chargeReq){
            this.chargeReq = chargeReq;
        }

        public String title(){
            return Messages.get(this, name() + ".name");
        }

        public String desc(){
            int talentLv = 0; //개별 특성의 레벨을 나타냄. 변수가 특성의 레벨을 포함할 경우에, 특성을 가지고 있지 않으면 특성 레벨이 1일 때의 설명을 출력
            switch (this){
                default:
                    return Messages.get(this, name() + ".desc");
                case MOVE:
                    return Messages.get(this, name() + ".desc" + (hero.pointsInTalent(Talent.MOVE_CMD) == 3 ? 2 : 1)); //특성 레벨이 3이면 desc2를, 아니라면 desc1을 출력
                case STIMPACK:
                    talentLv = Math.max(1, hero.pointsInTalent(Talent.STIMPACK_CMD));
                    return Messages.get(this, name() + ".desc", 5*talentLv);
                case ENGINEER:
                    talentLv = Math.max(1, hero.pointsInTalent(Talent.ENGINEER_CMD));
                    return Messages.get(this, name() + ".desc" + talentLv);
                case PROMOTE:
                    talentLv = Math.max(1, hero.pointsInTalent(Talent.PROMOTE_CMD));
                    return Messages.get(this, name() + ".desc", 5*talentLv, Messages.decimalFormat("#.#", (1+0.5f*talentLv)));
                case EXPLOSION:
                    talentLv = Math.max(1, hero.pointsInTalent(Talent.EXPLOSION_CMD));
                    return Messages.get(this, name() + ".desc", talentLv*3);
                case CLOSE_AIR_SUPPORT:
                    talentLv = Math.max(1, hero.pointsInTalent(Talent.CAS_CMD));
                    return Messages.get(this, name() + ".desc", 2+3*talentLv);
            }
        }
    }

    /* --- 명령에 필요한 타일 선택기 --- */

    private CellSelector.Listener cmdMoveCellSelector = new CellSelector.Listener() {
        @Override
        public void onSelect(Integer target) {
            if (target != null) {
                ArrayList<SupportSoldier> allies = new ArrayList<>();
                for (Char ch : Actor.chars()) {
                    if (ch instanceof SupportSoldier) {
                        if (hero.pointsInTalent(Talent.MOVE_CMD) < 3 && !Dungeon.level.heroFOV[ch.pos]) {
                            continue;
                        }
                        allies.add((SupportSoldier) ch);
                    }
                }

                if (!allies.isEmpty()) {
                    for (SupportSoldier ally : allies) {
                        ally.directTocell(target);
                    }

                    useCharge(CommandMove.MOVE);

                    Sample.INSTANCE.play(Assets.Sounds.BEACON);
                    hero.spendAndNext(1f);
                    hero.sprite.zap(target);
                } else {
                    GLog.w(Messages.get(Command.class, "no_allies_to_cmd"));
                }
            }
        }

        @Override
        public String prompt() {
            return Messages.get(Command.class, "prompt");
        }
    };

    private CellSelector.Listener cmdEngineerCellSelector = new CellSelector.Listener() {
        @Override
        public void onSelect(Integer target) {
            if (target != null) {
                if (Dungeon.level.heroFOV[target]) {
                    if (Build.isBuildable(target, true)) {
                        Trap t;
                        Building b;
                        //특성 레벨에 따라 다른 빌딩을 건설
                        switch (hero.pointsInTalent(Talent.ENGINEER_CMD)) {
                            case 1: default:
                                b = new MachineGun(); //거치형 기관총
                                break;
                            case 2:
                                b = new Cannon(); //야포
                                break;
                            case 3:
                                b = new Mortar(); //박격포
                                break;
                        }
                        //빌딩 건설 부분
                        b.pos = target;
                        GameScene.add(b);
                        if ((t = Dungeon.level.traps.get(b.pos)) != null && t.active){
                            if (t.disarmedByActivation) t.disarm();
                            t.reveal();
                            t.activate();
                        }
                        Dungeon.level.occupyCell(b);
                        GameScene.updateMap(target);

                        hero.spendAndNext(1f);
                        hero.sprite.zap(target);

                        CellEmitter.center( target ).burst( Speck.factory( Speck.STAR ), 7 );
                        Sample.INSTANCE.play( Assets.Sounds.EVOKE );

                        useCharge(CommandMove.ENGINEER);
                    } else {
                        GLog.w(Messages.get(Build.class, "invalid"));
                    }
                } else {
                    GLog.w(Messages.get(Command.class, "bad_target"));
                }
            }
        }

        @Override
        public String prompt() {
            return Messages.get(Command.class, "prompt");
        }
    };

    private CellSelector.Listener cmdPromoteCellSelector = new CellSelector.Listener() {
        @Override
        public void onSelect(Integer target) {
            if (target != null) {
                if (Dungeon.level.heroFOV[target]) {
                    Char ch = Actor.findChar(target);
                    if (ch instanceof SupportSoldier) {
                        SupportSoldier newAlly;
                        switch (Random.Int(3)) {
                            case 0: default:
                                newAlly = new SupportSniper();
                                break;
                            case 1:
                                newAlly = new SupportShielder();
                                break;
                            case 2:
                                newAlly = new SupportBomber();
                                break;
                        }
                        float enemyHPPercent = (ch.HP)/(float)ch.HT; //적이 변하고 나서 체력 비율이 그대로일 수 있도록 현재 적의 체력 비율을 확인
                        newAlly.pos = ch.pos;

                        Actor.remove( ch );
                        ch.sprite.killAndErase();
                        Dungeon.level.mobs.remove(ch);

                        if (hero.pointsInTalent(Talent.PROMOTE_CMD) > 1) {
                            newAlly.HP = newAlly.HT;
                        } else {
                            newAlly.HP = Math.max(1, (int)Math.ceil(newAlly.HT * enemyHPPercent)); //체력 비율은 올림 처리, 적어도 1의 체력을 보유하고 있어야 함
                        }

                        GameScene.add(newAlly);

                        TargetHealthIndicator.instance.target(null);
                        CellEmitter.get(newAlly.pos).burst(Speck.factory(Speck.WOOL), 4);
                        Sample.INSTANCE.play(Assets.Sounds.PUFF);

                        Dungeon.level.occupyCell(newAlly);

                        hero.spendAndNext(1f);
                        hero.sprite.operate(hero.pos);
                        Sample.INSTANCE.play(Assets.Sounds.BEACON);

                        useCharge(CommandMove.PROMOTE);
                    } else {
                        GLog.w(Messages.get(Command.class, "no_allies_to_cmd"));
                    }
                } else {
                    GLog.w(Messages.get(Command.class, "bad_target"));
                }
            }
        }

        @Override
        public String prompt() {
            return Messages.get(Command.class, "prompt");
        }
    };

    private CellSelector.Listener cmdExplosionCellSelector = new CellSelector.Listener() {
        @Override
        public void onSelect(Integer target) {
            if (target != null) {
                if (Dungeon.level.heroFOV[target]) {
                    Ballistica aim = new Ballistica(hero.pos, target, Ballistica.STOP_TARGET);
                    new Explosive().effect(null, hero, aim, false);
                    hero.sprite.parent.add(new TargetedCell(target, 0xFF0000));

                    hero.sprite.operate(hero.pos);
                    hero.spendAndNext(1f);

                    Sample.INSTANCE.play(Assets.Sounds.BEACON);

                    useCharge(CommandMove.EXPLOSION);
                } else {
                    GLog.w(Messages.get(Command.class, "bad_target"));
                }
            }
        }

        @Override
        public String prompt() {
            return Messages.get(Command.class, "prompt");
        }
    };

    private CellSelector.Listener cmdCASCellSelector = new CellSelector.Listener() {
        @Override
        public void onSelect(Integer target) {
            if (target != null) {
                Ballistica aim = new Ballistica(hero.pos, target, Ballistica.DASH);
                float delay = 0f;
                for (int cell : aim.subPath(2, Math.min((2+3*hero.pointsInTalent(Talent.CAS_CMD)), aim.dist))) { //경로의 3번째 타일부터 5/8/11타일 까지가 범위. 경로의 끝을 벗어나지 않음.
                    float finalDelay = delay; //폭발 이펙트 지연 시간. 0에서 시작해 루프 한 번을 돌 때마다 0.05가 추가된다.
                    Actor.add(new Actor() { //폭발 이펙트를 발생시키는 액터 추가

                        {
                            actPriority = VFX_PRIO;
                        }

                        @Override
                        protected boolean act() { //액터가 행동하면 지연 시간을 가진 Tweener를 추가하고 사라진다.
                            hero.sprite.parent.add(new Tweener(hero.sprite.parent, finalDelay) { //finalDelay초 후에 폭발 이펙트가 작동하도록 설정한 Tweener
                                @Override
                                protected void updateValues(float progress) { //시간이 지남에 따라 실행되는 함수
                                    hero.spendAndNext(0); //아직 터지지 않았을 경우 영웅은 행동할 수 없다.
                                }

                                @Override
                                protected void onComplete() { //시간이 다 지나면 실행되는 함수
                                    super.onComplete();
                                    new CASBomb().explode(cell); //폭발
                                }
                            });
                            Actor.remove(this);
                            spend(0);
                            return true;
                        }
                    });
                    delay += 0.05f; //0.05초마다 1번 터진다.
                }

                hero.spendAndNext(1f);
                hero.sprite.operate(hero.pos);
                Sample.INSTANCE.play(Assets.Sounds.BEACON);

                useCharge(CommandMove.CLOSE_AIR_SUPPORT);
            }
        }

        @Override
        public String prompt() {
            return Messages.get(Command.class, "prompt");
        }
    };

    //아군 군인
    public static class SupportSoldier extends DirectableAlly {

        {
            spriteClass = SupportSoldirSprite.class;

            if (hero != null) {
                HP = HT = hero.HT/4;
            }

            viewDistance = Light.DISTANCE;

            immunities.add(AllyBuff.class);
        }

        @Override
        protected boolean canAttack( Char enemy ) {
            Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
            return Dungeon.level.adjacent( pos, enemy.pos ) || attack.collisionPos == enemy.pos;
        }

        @Override
        public int defenseSkill(Char target) {
            return 4+hero.lvl; //same with hero's base defenseSkill
        }

        @Override
        public int attackSkill(Char target) {
            return 9+hero.lvl; //same with hero's base attackSkill
        }

        @Override
        public int drRoll() {
            int region = (int)Math.ceil(Dungeon.scalingDepth()/5f);
            return Random.NormalIntRange(region, 5*region);
        }

        @Override
        public int damageRoll() {
            int region = (int)Math.ceil(Dungeon.scalingDepth()/5f);
            return Random.NormalIntRange(2*region, 8*region);
        }

        @Override
        public int attackProc(Char enemy, int damage) {
            damage = super.attackProc( enemy, damage );

            return damage;
        }

        @Override
        protected boolean act() {
            boolean result = super.act();
            if (enemy == null && !movingToDefendPos && defendingPos == -1) {
                followHero();
            }
            return result;
        }

        @Override
        public void die(Object cause) {
            super.die(cause);
        }

        @Override
        protected void spend(float time) {
            super.spend(time);
        }

        @Override
        public void destroy() {
            super.destroy();
            Dungeon.observe();
            GameScene.updateFog();
        }
    }

    public static class SupportSoldirSprite extends MobSprite {

        private int cellToAttack;

        public SupportSoldirSprite() {
            super();

            texture( Assets.Sprites.SUPPORT_FORCE );

            TextureFilm frames = new TextureFilm( texture, 12, 15 );

            idle = new Animation( 1, true );
            idle.frames( frames, 0, 0, 0, 1, 0, 0, 1, 1 );

            run = new Animation( 20, true );
            run.frames( frames, 2, 3, 4, 5, 6, 7 );

            attack = new Animation( 15, false );
            attack.frames( frames, 8, 9, 10, 0 );

            zap = attack.clone();

            die = new Animation( 20, false );
            die.frames( frames, 11, 12, 13, 14, 15, 14);

            play( idle );
        }

        @Override
        public void attack( int cell ) {
            if (!Dungeon.level.adjacent( cell, ch.pos )) {

                cellToAttack = cell;
                turnTo( ch.pos , cell );
                play( zap );

            } else {

                super.attack( cell );

            }
        }

        @Override
        public void onComplete( Animation anim ) {
            if (anim == zap) {
                idle();
                CellEmitter.get(ch.pos).burst(SmokeParticle.FACTORY, 2);
                CellEmitter.center(ch.pos).burst(BlastParticle.FACTORY, 2);
                Sample.INSTANCE.play( Assets.Sounds.HIT_CRUSH, 1, Random.Float(0.33f, 0.66f) );
                ((MissileSprite)parent.recycle( MissileSprite.class )).
                        reset( this, cellToAttack, new SupportShot(), new Callback() {
                            @Override
                            public void call() {
                                ch.onAttackComplete();
                            }
                        } );
            } else {
                super.onComplete( anim );
            }
        }
    }

    //아군 저격수
    public static class SupportSniper extends SupportSoldier {

        {
            spriteClass = SupportSniperSprite.class;

            if (hero != null) {
                HP = HT = hero.HT / 8; //less HT
            }

            viewDistance = Light.DISTANCE+3; //longer viewDistance

            immunities.add(AllyBuff.class);
        }

        @Override
        public int attackSkill(Char target) {
            return (9+hero.lvl)*3; //x3 of hero's base attackSkill
        }

        @Override
        public int drRoll() {
            int region = (int)Math.ceil(Dungeon.scalingDepth()/5f);
            return Random.NormalIntRange(region, 2*region); //weaker dr
        }

        @Override
        public int damageRoll() {
            int region = (int)Math.ceil(Dungeon.scalingDepth()/5f);
            return Random.NormalIntRange(4*region, 12*region); //stronger damage
        }
    }

    public static class SupportSniperSprite extends SupportSoldirSprite {

        public SupportSniperSprite() {
            super();

            int offset = 21;

            texture( Assets.Sprites.SUPPORT_FORCE );

            TextureFilm frames = new TextureFilm( texture, 12, 15 );

            idle = new Animation( 1, true );
            idle.frames( frames, offset+0, offset+0, offset+0, offset+1, offset+0, offset+0, offset+1, offset+1 );

            run = new Animation( 20, true );
            run.frames( frames, offset+2, offset+3, offset+4, offset+5, offset+6, offset+7 );

            attack = new Animation( 15, false );
            attack.frames( frames, offset+8, offset+9, offset+10, offset+0 );

            zap = attack.clone();

            die = new Animation( 20, false );
            die.frames( frames, offset+11, offset+12, offset+13, offset+14, offset+15, offset+14);

            play( idle );
        }
    }

    //아군 방패수
    public static class SupportShielder extends SupportSoldier {

        {
            spriteClass = SupportShielderSprite.class;

            if (hero != null) {
                HP = HT = hero.HT/3; //higher HT
            }

            viewDistance = Light.DISTANCE;

            immunities.add(AllyBuff.class);
        }

        @Override
        protected boolean canAttack( Char enemy ) {
            return Dungeon.level.adjacent( pos, enemy.pos );
        }

        @Override
        public int drRoll() {
            int region = (int)Math.ceil(Dungeon.scalingDepth()/5f);
            return Random.NormalIntRange(2*region, 6*region); //higher dr
        }

        @Override
        public int damageRoll() {
            int region = (int)Math.ceil(Dungeon.scalingDepth()/5f);
            return Random.NormalIntRange(region, 5*region); //less damage
        }

        @Override
        public int attackProc(Char enemy, int damage) {
            damage = super.attackProc( enemy, damage );
            if (Random.Float() < 0.1f) { //10% chance of inflicting short Paralysis
                Buff.affect(enemy, Paralysis.class, 2f);
            }
            this.aggro(enemy); //aggros the enemy to themselves
            return damage;
        }
    }

    public static class SupportShielderSprite extends SupportSoldirSprite {

        public SupportShielderSprite() {
            super();

            int offset = 42;

            texture( Assets.Sprites.SUPPORT_FORCE );

            TextureFilm frames = new TextureFilm( texture, 12, 15 );

            idle = new Animation( 1, true );
            idle.frames( frames, offset+0, offset+0, offset+0, offset+1, offset+0, offset+0, offset+1, offset+1 );

            run = new Animation( 20, true );
            run.frames( frames, offset+2, offset+3, offset+4, offset+5, offset+6, offset+7 );

            attack = new Animation( 15, false );
            attack.frames( frames, offset+8, offset+9, offset+10, offset+0 );

            zap = attack.clone();

            die = new Animation( 20, false );
            die.frames( frames, offset+11, offset+12, offset+13, offset+14, offset+15, offset+14);

            play( idle );
        }
    }

    //아군 폭격수
    public static class SupportBomber extends SupportSoldier {

        {
            spriteClass = SupportBomberSprite.class;

            if (hero != null) {
                HP = HT = hero.HT/4;
            }

            viewDistance = Light.DISTANCE;

            immunities.add(AllyBuff.class);
        }

        @Override
        public int attackSkill(Char target) {
            return Math.round((9+hero.lvl)*0.67f); //2/3 of hero's base attackSkill
        }

        @Override
        public int damageRoll() {
            int region = (int)Math.ceil(Dungeon.scalingDepth()/5f);
            return Random.NormalIntRange(3*region, 10*region);
        }

        @Override
        public void onAttackComplete() {
            super.onAttackComplete();

            if (!Dungeon.level.adjacent(enemy.pos, this.pos)) {
                for (int i : PathFinder.NEIGHBOURS8) {
                    int cell = enemy.pos + i;
                    Char ch;
                    if ((ch = Actor.findChar(cell)) != null) {
                        if (ch.alignment == Alignment.ENEMY) {
                            this.attack(ch);
                        }
                    }
                }
                for (int i : PathFinder.NEIGHBOURS9) {
                    int cell = enemy.pos + i;
                    if (Dungeon.level.heroFOV[cell]) {
                        CellEmitter.get(cell).burst(SmokeParticle.FACTORY, 4);
                        CellEmitter.center(enemy.pos).burst(BlastParticle.FACTORY, 4);
                    }
                    if (Dungeon.level.flamable[cell]) {
                        Dungeon.level.destroy(cell);
                        GameScene.updateMap(cell);
                    }
                }
            }
        }
    }

    public static class SupportBomberSprite extends SupportSoldirSprite {

        public SupportBomberSprite() {
            super();

            int offset = 63;

            texture( Assets.Sprites.SUPPORT_FORCE );

            TextureFilm frames = new TextureFilm( texture, 12, 15 );

            idle = new Animation( 1, true );
            idle.frames( frames, offset+0, offset+0, offset+0, offset+1, offset+0, offset+0, offset+1, offset+1 );

            run = new Animation( 20, true );
            run.frames( frames, offset+2, offset+3, offset+4, offset+5, offset+6, offset+7 );

            attack = new Animation( 15, false );
            attack.frames( frames, offset+8, offset+9, offset+10, offset+0 );

            zap = attack.clone();

            die = new Animation( 20, false );
            die.frames( frames, offset+11, offset+12, offset+13, offset+14, offset+15, offset+14);

            play( idle );
        }
    }

    //아군 군인 총알
    public static class SupportShot extends Item {
        {
            image = ItemSpriteSheet.SINGLE_BULLET;
        }
    }

    //폭발물 설치 메커니즘. 저주 완드의 그것과 같다.
    public static class Explosive extends CursedWand.SuperNova {
        @Override
        public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
            ExplosiveTracker nova = Buff.append(Dungeon.hero, ExplosiveTracker.class);
            nova.pos = bolt.collisionPos;
            nova.harmsAllies = !positiveOnly;

            return true;
        }
    }

    public static class CASBomb extends Bomb {
        { //이 곳에서 폭발의 데미지를 조절 가능하다.
            minDamage = 4 + Dungeon.scalingDepth();
            maxDamage = 12 + 3*Dungeon.scalingDepth();
        }
    }
}
