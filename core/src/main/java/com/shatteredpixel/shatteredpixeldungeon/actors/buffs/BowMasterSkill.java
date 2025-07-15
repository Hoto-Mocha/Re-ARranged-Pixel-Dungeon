package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class BowMasterSkill extends Buff implements ActionIndicator.Action {
    {
        type = buffType.NEUTRAL;
    }

    private boolean shoot = false;
    private boolean moved = false;
    private boolean powerShot = false;
    private int charge = 0;
    private final int MAX_CHARGE = 4;

    private int maxCharge() {
        return MAX_CHARGE + Dungeon.hero.pointsInTalent(Talent.EXPANDED_POWER);
    }

    @Override
    public int icon() {
        return BuffIndicator.ARROW_EMPOWER;
    }

    @Override
    public void tintIcon(Image icon) {
        if (isPowerShot()) {
            icon.tint(1, 1, 1, 0.4f);
        } else {
            float tint = Math.min(1, 0.2f*(charge+1));
            icon.hardlight(tint, tint, tint);
        }
    }

    @Override
    public float iconFadePercent() {
        return Math.min(1, charge/(float)maxCharge());
    }

    @Override
    public String iconTextDisplay() {
        return Integer.toString(charge);
    }

    private String descString() {
        return 2*charge + "+" + Messages.decimalFormat("#", ((float)(Math.pow(dmgMulti(), charge)-1) + (isPowerShot() ? powerShotMulti() : 0))*100) + "%";
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", descString());
    }

    public void shoot() {
        if (isPowerShot()) {
            this.moved = false;
            this.shoot = false;
            this.powerShot = false;
            return;
        }

        this.moved = false;
        this.shoot = true;
        this.charge = Math.min(maxCharge(), charge+1);
        if (charge >= MAX_CHARGE) {
            ActionIndicator.setAction(this);
        }
    }

    public void moveCharge(int charge) {
        if (isPowerShot()) {
            this.moved = true;
            this.shoot = true;
            return;
        }
        if (charge < 0) {
            if (Random.Float() < 0.8f) {
                return;
            } else {
                charge = 0;
            }
        }

        this.moved = true;
        this.shoot = true;
        this.charge = Math.min(maxCharge(), this.charge+charge);

        if (charge >= MAX_CHARGE) {
            ActionIndicator.setAction(this);
        }
    }

    public boolean isMoved() {
        return this.moved;
    }

    public boolean isPowerShot() {
        return this.powerShot;
    }

    public float powerShotMulti() {
        return 1f;
    }

    private float dmgMulti() {
        return 1.05f;
    }

    public int proc(int damage) {
        float result = damage * (float)Math.pow(dmgMulti(), charge) + 2*charge;
        if (isPowerShot()) {
            result += damage * powerShotMulti();
        }
        return Math.round(result);
    }

    @Override
    public boolean act() {
        if (!shoot) detach();
        spend(TICK);
        shoot = false;

        return true;
    }

    @Override
    public void detach() {
        ActionIndicator.clearAction();
        super.detach();
    }

    private static final String CHARGE   = "charge";
    private static final String MOVED   = "moved";
    private static final String SHOOT   = "shoot";
    private static final String POWER_SHOT   = "powerShot";


    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);

        bundle.put(CHARGE, charge);
        bundle.put(MOVED, moved);
        bundle.put(SHOOT, shoot);
        bundle.put(POWER_SHOT, powerShot);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        charge = bundle.getInt(CHARGE);
        moved = bundle.getBoolean(MOVED);
        shoot = bundle.getBoolean(SHOOT);
        powerShot = bundle.getBoolean(POWER_SHOT);

        if (charge >= MAX_CHARGE && !isPowerShot()) {
            ActionIndicator.setAction(this);
        }
    }

    public static boolean isFastShot(Hero hero) {
        return hero.buff(BowMasterSkill.class) == null && hero.hasTalent(Talent.FASTSHOT);
    }

    public static float fastShotDamageMultiplier(Hero hero) {
        if (!hero.hasTalent(Talent.FASTSHOT)) return 1;
        else return 0.2f * hero.pointsInTalent(Talent.FASTSHOT);
    }

    public static float speedBoost(Hero hero){
        if (!hero.hasTalent(Talent.UNENCUMBERED_STEP)){
            return 1;
        }

        boolean enemyNear = false;
        //for each enemy, check if they are adjacent, or within 2 tiles and an adjacent cell is open
        for (Char ch : Actor.chars()){
            if ( Dungeon.level.distance(ch.pos, hero.pos) <= 2 && hero.alignment != ch.alignment && ch.alignment != Char.Alignment.NEUTRAL){
                if (Dungeon.level.adjacent(ch.pos, hero.pos)){
                    enemyNear = true;
                    break;
                } else {
                    for (int i : PathFinder.NEIGHBOURS8){
                        if (Dungeon.level.adjacent(hero.pos+i, ch.pos) && !Dungeon.level.solid[hero.pos+i]){
                            enemyNear = true;
                            break;
                        }
                    }
                }
            }
        }

        if (enemyNear){
            return 1;
        } else {
            return (1 + 0.1f*hero.pointsInTalent(Talent.UNENCUMBERED_STEP));
        }
    }

    @Override
    public String actionName() {
        return Messages.get(this, "action_name");
    }

    @Override
    public int actionIcon() {
        return HeroIcon.POWER_SHOT;
    }

    @Override
    public int indicatorColor() {
        return 0xCC0022;
    }

    @Override
    public void doAction() {
        ScrollOfRecharging.charge(Dungeon.hero);
        powerShot = true;
        BuffIndicator.refreshHero();
        Dungeon.hero.sprite.operate(Dungeon.hero.pos);
        Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
        ActionIndicator.clearAction();
    }

    public static void move() {
        if (hero.hasTalent(Talent.MOVING_FOCUS) && hero.buff(BowMasterSkill.class) != null && !hero.buff(BowMasterSkill.class).isMoved()) {
            hero.buff(BowMasterSkill.class).moveCharge(Math.max(-1, hero.pointsInTalent(Talent.MOVING_FOCUS)-2));
        }
    }
}
