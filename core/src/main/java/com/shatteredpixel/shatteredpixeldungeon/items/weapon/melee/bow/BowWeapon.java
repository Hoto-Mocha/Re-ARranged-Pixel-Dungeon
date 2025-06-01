package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.bow;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.ArrowItem;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Elastic;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.DisposableMissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BowWeapon extends MeleeWeapon {

    public static final String AC_SHOOT		= "SHOOT";

    {
        defaultAction = AC_SHOOT;
        usesTargeting = true;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (isEquipped( hero )) {
            actions.add(AC_SHOOT);
        }
        return actions;
    }

    @Override
    public int max(int lvl) {
        return 2+tier()
                +lvl;
    }

    public int arrowMin() {
        return arrowMin(this.buffedLvl());
    }

    public int arrowMin(int lvl) {
        return tier()
                +lvl;
    }

    public int arrowMax(int lvl) {
        return 3*(tier()+1) +
                lvl*(tier()+1);
    }

    public int arrowMax() {
        return arrowMax(this.buffedLvl());
    }

    public int arrowDamage() {
        int damage = Random.NormalIntRange(arrowMin(), arrowMax());

        damage = augment.damageFactor(damage);  //증강에 따라 변화하는 효과
        return damage;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        //적과 근접한 상태에서 공격 시 40% 확률로, 혹은 기습 공격 시 적을 2타일 밀쳐냄
        if (Dungeon.level.adjacent(attacker.pos, defender.pos)
                && ((defender instanceof Mob && ((Mob) defender).surprisedBy(attacker)) || Random.Float() < 0.4f)) {
            pushEnemy(this, attacker, defender, 2);
        }
        return super.proc(attacker, defender, damage);
    }

    //arrows will copy all procs of the bow, but excludes the pushing effect
    public int arrowProc(Char attacker, Char defender, int damage) {
        return super.proc(attacker, defender, damage);
    }

    @Override
    public String info() {
        String info = super.info();
        //근접 무기의 설명을 모두 가져옴, 여기에서 할 것은 근접 무기의 설명에 추가로 생기는 문장을 더하는 것
        if (levelKnown) { //감정되어 있을 때
            info += "\n\n" + Messages.get(this, "bow_desc",
                    augment.damageFactor(arrowMin(buffedLvl())), augment.damageFactor(arrowMax(buffedLvl())));
        } else { //감정되어 있지 않을 때
            info += "\n\n" + Messages.get(this, "bow_typical_desc",
                    augment.damageFactor(arrowMin(0)), augment.damageFactor(arrowMax(0)));
        }

        return info;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_SHOOT)) {
            if (!isEquipped( hero )) {
                usesTargeting = false;
                GLog.w(Messages.get(this, "not_equipped"));
            } else {
                if (Dungeon.bullet <= 0) {
                    usesTargeting = false;
                    GLog.w(Messages.get(this, "no_arrow"));
                } else {
                    usesTargeting = true;
                    curUser = hero;
                    curItem = this;
                    GameScene.selectCell(shooter);
                }
            }
        }
    }

    protected void duelistAbility( Hero hero, Integer target ){
        hero.sprite.operate(hero.pos);
        hero.spendAndNext(0);
        Buff.affect(hero, PenetrationShotBuff.class);
        Sample.INSTANCE.play(Assets.Sounds.MISS);
    }

    @Override
    public String abilityInfo() {
        int lvl = levelKnown ? buffedLvl() : 0;
        int min = arrowMin(lvl) + lvl + Math.round(arrowMin(lvl)*(7-tier())*0.1f);
        int max = arrowMax(lvl) + lvl + Math.round(arrowMax(lvl)*(7-tier())*0.1f);
        if (levelKnown){
            return Messages.get(this, "ability_desc", augment.damageFactor(min), augment.damageFactor(max));
        } else {
            return Messages.get(this, "typical_ability_desc", augment.damageFactor(min), augment.damageFactor(max));
        }
    }

    public Arrow knockArrow(){
        return new Arrow();
    }

    public class Arrow extends DisposableMissileWeapon {
        {
            image = ItemSpriteSheet.NORMAL_ARROW;
            tier = BowWeapon.this.tier();
            hitSound = Assets.Sounds.HIT_ARROW;
        }

        @Override
        public int proc(Char attacker, Char defender, int damage) {
            return BowWeapon.this.arrowProc(attacker, defender, damage);
        }

        @Override
        public int buffedLvl(){
            return BowWeapon.this.buffedLvl();
        }

        @Override
        public int damageRoll(Char owner) {
            int damage = arrowDamage();
            if (owner instanceof Hero) {
                Hero hero = (Hero)owner;
                Char enemy = hero.enemy();
                if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
                    //deals 50% toward max to max on surprise, instead of min to max.
                    int diff = arrowMax() - arrowMin();
                    damage = augment.damageFactor(Hero.heroDamageIntRange(
                            arrowMin() + Math.round(diff*(3/(3f+tier))), //75%, 60%, 50%, 43%, 38% toward max
                            arrowMax()));
                    int exStr = hero.STR() - STRReq();
                    if (exStr > 0) {
                        damage += Hero.heroDamageIntRange(0, exStr);
                    }
                }

                if (hero.buff(PenetrationShotBuff.class) != null) {
                    damage = hero.buff(PenetrationShotBuff.class).proc(damage, this.buffedLvl(), this.tier);
                    hero.buff(PenetrationShotBuff.class).detach();
                    Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
                }
            }
            return damage;
        }

        @Override
        public boolean hasEnchant(Class<? extends Enchantment> type, Char owner) {
            return BowWeapon.this.hasEnchant(type, owner);
        }

        @Override
        public float delayFactor(Char owner) {
            return BowWeapon.this.delayFactor(owner);
        }

        @Override
        public float accuracyFactor(Char owner, Char target) {
            return BowWeapon.this.accuracyFactor(owner, target);
        }

        @Override
        public int STRReq() {
            return BowWeapon.this.STRReq();
        }

        @Override
        protected void onThrow(int cell) {
            Char enemy = Actor.findChar( cell );
            if (enemy != null && !(enemy instanceof Hero)) {
                if (curUser.shoot( enemy, this )) {
                    if (Random.Float() < 0.25f) {
                        if (enemy.isAlive()) {
                            Buff.affect(enemy, ArrowAttached.class).hit();
                        } else {
                            dropArrow(cell);
                        }
                    }
                } else {
                    if (Random.Float() < 0.25f) {
                        dropArrow(cell);
                    }
                }
            }

            if (enemy == null || enemy instanceof Hero) {
                if (Random.Float() < 0.25f) {
                    dropArrow(cell);
                }
            }

            onShoot();
        }

        public void dropArrow(int cell) {
            Dungeon.level.drop(new ArrowItem(), cell).sprite.drop(cell);
        }

        public void onShoot() {
            Dungeon.bullet--;

            updateQuickslot();
        }
    }

    private CellSelector.Listener shooter = new CellSelector.Listener() {
        @Override
        public void onSelect( Integer target ) {
            if (target != null) {
                if (target == curUser.pos) {
                    return;
                } else {
                    knockArrow().cast(curUser, target);
                }
            }
        }
        @Override
        public String prompt() {
            return Messages.get(SpiritBow.class, "prompt");
        }
    };

    public static class ArrowAttached extends Buff {
        {
            type = buffType.NEUTRAL;
        }

        public static final float DURATION = 50f;

        int arrows = 0;
        float left;

        public void hit() {
            arrows++;
            left = DURATION;

            if (arrows >= 10) {
                Badges.validateHedgehog();
            }
        }

        @Override
        public boolean act() {
            left-=TICK;
            spend(TICK);
            if (left <= 0) {
                detach();
            }
            return true;
        }

        private final String ARROWS = "arrows";
        private final String LEFT = "left";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(ARROWS, arrows);
            bundle.put(LEFT, left);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            arrows = bundle.getInt(ARROWS);
            left = bundle.getFloat(LEFT);
        }

        @Override
        public int icon() {
            return BuffIndicator.ARROW_ATTACHED;
        }

        @Override
        public float iconFadePercent() {
            return (DURATION-left)/DURATION;
        }

        @Override
        public void detach() {
            super.detach();
            Dungeon.level.drop(new ArrowItem().quantity(arrows), target.pos).sprite.drop(target.pos);
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", arrows, left);
        }
    }

    public static void pushEnemy(Weapon weapon, Char attacker, Char defender, int power) {
        //trace a ballistica to our target (which will also extend past them
        Ballistica trajectory = new Ballistica(attacker.pos, defender.pos, Ballistica.STOP_TARGET);
        //trim it to just be the part that goes past them
        trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size()-1), Ballistica.PROJECTILE);
        //knock them back along that ballistica
        WandOfBlastWave.throwChar(defender,
                trajectory,
                power,
                false,
                false,
                BowWeapon.class);
    }

    public static class PenetrationShotBuff extends Buff {
        {
            type = buffType.NEUTRAL;
        }

        @Override
        public int icon() {
            return BuffIndicator.DUEL_BOW;
        }

        public int proc(int damage, int lvl, int tier) {
            return damage + lvl + Math.round(damage*(7-tier)*0.1f);
        }
    }
}
