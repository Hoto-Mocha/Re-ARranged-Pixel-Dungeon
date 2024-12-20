package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.DirectableAlly;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.Building;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.Cannon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.MachineGun;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building.Mortar;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.GL.GL;
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
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndCommand;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.Visual;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Command extends Buff implements ActionIndicator.Action {
    {
        type = buffType.POSITIVE;
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
        if (this.charge++ > 6) {
            this.charge = 6;
        }
        ActionIndicator.setAction( this );
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
            case STAY:
                return hero.hasTalent(Talent.STAY_CMD);
            case ATTACK:
                return hero.hasTalent(Talent.ATTACK_CMD);
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
            case STAY:
                break;
            case ATTACK:
                break;
            case CLOSE_AIR_SUPPORT:
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
            SupportAlly newAlly = new SupportAlly();

            newAlly.pos = Random.element(spawnPoints);

            GameScene.add(newAlly, 1f);
            Dungeon.level.occupyCell(newAlly);

            Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
            CellEmitter.get(newAlly.pos).start( Speck.factory(Speck.LIGHT), 0.2f, 3 );

            hero.spendAndNext(1f);
            hero.sprite.operate(hero.pos);

            useCharge(CommandMove.RECRUIT);
        } else
            GLog.i( Messages.get(this, "no_space") );
    }

    public void cmdMedicalSupport() {
        ArrayList<SupportAlly> allies = new ArrayList<>();
        for (Char ch : Actor.chars()) {
            if (ch instanceof SupportAlly && ch.HP < ch.HT && Dungeon.level.heroFOV[ch.pos]) {
                allies.add((SupportAlly) ch);
            }
        }
        if (!allies.isEmpty()) {
            for (SupportAlly ally : allies) {
                Buff.affect(ally, Healing.class).setHeal(Math.round(ally.HT*0.2f), 0, (int)Math.ceil(Dungeon.scalingDepth()/5f));
            }

            Sample.INSTANCE.play(Assets.Sounds.BEACON);

            hero.spendAndNext(1f);
            hero.sprite.operate(hero.pos);

            useCharge(CommandMove.MEDICAL_SUPPORT);
        } else {
            GLog.w(Messages.get(this, "no_allies"));
        }
    }

    public void cmdMove() {
        GameScene.selectCell( cmdMoveCellSelector );
    }

    public void cmdStimpack() {
        ArrayList<SupportAlly> allies = new ArrayList<>();
        for (Char ch : Actor.chars()) {
            if (ch instanceof SupportAlly) {
                if (!Dungeon.level.heroFOV[ch.pos]) {
                    continue;
                }
                allies.add((SupportAlly) ch);
            }
        }

        if (!allies.isEmpty()) {

            for (SupportAlly ally : allies) {
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

    /* --- 명령 수행 메서드 --- */
    
    //명령에 필요한 명령권 수
    public static int commandChargeReq(CommandMove command) {
        switch (command) {
            default:
                return command.chargeReq;
            case RECRUIT:
                int allies = 0;
                for (Char ch : Actor.chars()) {
                    if (ch instanceof SupportAlly) {
                        allies++;
                    }
                }
                return allies+1;
            case MOVE:
                return hero.pointsInTalent(Talent.MOVE_CMD) < 2 ? 1 : 0; //특성 레벨이 2 이상이면 명령권을 소모하지 않음
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
        STAY                (1),
        ATTACK              (1),
        CLOSE_AIR_SUPPORT   (1);

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
                case STAY:
                    talentLv = Math.max(1, hero.pointsInTalent(Talent.STAY_CMD));
                    return Messages.get(this, name() + ".desc", 5*talentLv, Messages.decimalFormat("#.#", (1+0.5f*talentLv)));
                case ATTACK:
                    talentLv = Math.max(1, hero.pointsInTalent(Talent.ATTACK_CMD));
                    return Messages.get(this, name() + ".desc", talentLv);
                case CLOSE_AIR_SUPPORT:
                    talentLv = Math.max(1, hero.pointsInTalent(Talent.CAS_CMD));
                    return Messages.get(this, name() + ".desc", talentLv, talentLv);
            }
        }
    }

    /* --- 명령에 필요한 타일 선택기 --- */

    private CellSelector.Listener cmdMoveCellSelector = new CellSelector.Listener() {
        @Override
        public void onSelect(Integer target) {
            if (target != null) {
                ArrayList<SupportAlly> allies = new ArrayList<>();
                for (Char ch : Actor.chars()) {
                    if (ch instanceof SupportAlly) {
                        if (hero.pointsInTalent(Talent.MOVE_CMD) < 3 && !Dungeon.level.heroFOV[ch.pos]) {
                            continue;
                        }
                        allies.add((SupportAlly) ch);
                    }
                }

                if (!allies.isEmpty()) {
                    for (SupportAlly ally : allies) {
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
            }
        }

        @Override
        public String prompt() {
            return Messages.get(Command.class, "prompt");
        }
    };

    private CellSelector.Listener cmdAttackCellSelector = new CellSelector.Listener() {
        @Override
        public void onSelect(Integer target) {
            if (target != null) {

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

            }
        }

        @Override
        public String prompt() {
            return Messages.get(Command.class, "prompt");
        }
    };

    /* --- 명령에 필요한 타일 선택기 --- */

    //아군 군인
    public static class SupportAlly extends DirectableAlly {

        {
            spriteClass = SupportSprite.class;

            HP = HT = hero.HT/4;

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
            return Random.NormalIntRange(3*region, 10*region);
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

    public static class SupportSprite extends MobSprite {

        private int cellToAttack;

        public SupportSprite() {
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

    public static class SupportShot extends Item {
        {
            image = ItemSpriteSheet.SINGLE_BULLET;
        }
    }
}
