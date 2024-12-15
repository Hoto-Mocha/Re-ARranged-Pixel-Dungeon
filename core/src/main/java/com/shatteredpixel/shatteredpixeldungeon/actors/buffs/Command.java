package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndCombo;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndCommand;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Visual;
import com.watabou.utils.Bundle;

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

    public void kill() {
        if (this.charge++ > 6) {
            this.charge = 6;
        }
    }

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

    public void useCommand(CommandMove command) {
        switch (command) {
            case RECRUIT:
                break;
            case MEDICAL_SUPPORT:
                break;
            case MOVE:
                break;
            case STIMPACK:
                break;
            case ENGINEER:
                break;
            case STAY:
                break;
            case ATTACK:
                break;
            case CLOSE_AIR_SUPPORT:
                break;
        }
    }

    public static int commandChargeReq(CommandMove command) {
        switch (command) {
            default:
                return command.chargeReq;
            case MOVE:
                return hero.pointsInTalent(Talent.MOVE_CMD) < 3 ? 1 : 0; //특성 레벨이 3이면 명령권을 소모하지 않음
        }
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

    private CellSelector.Listener cmdMoveCellSelector = new CellSelector.Listener() {
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

    private CellSelector.Listener cmdEngineerCellSelector = new CellSelector.Listener() {
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
}
