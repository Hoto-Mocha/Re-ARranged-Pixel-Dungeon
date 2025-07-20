package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.bow;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BowMasterSkill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CounterBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.GreaterHaste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SharpShooterBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.ArrowBag;
import com.shatteredpixel.shatteredpixeldungeon.items.ArrowItem;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.DisposableMissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
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
        if (Dungeon.hero != null) {
            return tier()
                    + lvl
                    + RingOfSharpshooting.levelDamageBonus(Dungeon.hero);
        } else {
            return tier()
                    + lvl;
        }

    }

    public int arrowMax(int lvl) {
        if (Dungeon.hero != null) {
            return 4*(tier()+1)
                    + lvl*(tier()+1)
                    + RingOfSharpshooting.levelDamageBonus(Dungeon.hero);
        } else {
            return 4*(tier()+1) +
                    lvl*(tier()+1);
        }
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
            pushEnemy(this, attacker, defender, 2 + Dungeon.hero.pointsInTalent(Talent.PUSHBACK));
        }
        return super.proc(attacker, defender, damage);
    }

    @Override
    public boolean doPickUp(Hero hero, int pos) {
        Statistics.bowObtained = true;
        Badges.validateArcherUnlock();
        return super.doPickUp(hero, pos);
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
        if (hero.buff(PenetrationShotBuff.class) != null) {
            GLog.w(Messages.get(this, "already_used"));
            return;
        }

        beforeAbilityUsed(hero, null);

        hero.sprite.operate(hero.pos);
        hero.spendAndNext(0);
        Buff.affect(hero, PenetrationShotBuff.class);
        Sample.INSTANCE.play(Assets.Sounds.MISS);

        afterAbilityUsed(hero);
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
        Arrow arrow = new Arrow();
        arrow.reset(this);
        return arrow;
    }

    @Override
    public int targetingPos(Hero user, int dst) {
        return knockArrow().targetingPos(user, dst);
    }

    public static class Arrow extends DisposableMissileWeapon {
        {
            image = ItemSpriteSheet.NORMAL_ARROW;
            hitSound = Assets.Sounds.HIT_ARROW;
        }

        @Override
        public int min() {
            return arrowFrom.arrowMin();
        }

        @Override
        public int min(int lvl) {
            return arrowFrom.arrowMin(lvl);
        }

        @Override
        public int max() {
            return arrowFrom.arrowMax();
        }

        @Override
        public int max(int lvl) {
            return arrowFrom.arrowMax(lvl);
        }

        private BowWeapon arrowFrom;
        public boolean useBullet = true;
        public boolean isBurst = false;

        private static final String ARROW_FROM = "arrowFrom";
        private static final String USE_BULLET = "useBullet";
        private static final String IS_BURST = "isBurst";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);

            bundle.put(ARROW_FROM, arrowFrom);
            bundle.put(USE_BULLET, useBullet);
            bundle.put(IS_BURST, isBurst);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);

            arrowFrom = (BowWeapon) bundle.get(ARROW_FROM);
            useBullet = bundle.getBoolean(USE_BULLET);
            isBurst = bundle.getBoolean(IS_BURST);
        }

        public void reset(BowWeapon bow) {
            arrowFrom = bow;
            this.tier = bow.tier();
        }

        @Override
        public int proc(Char attacker, Char defender, int damage) {
            if (attacker == Dungeon.hero && Dungeon.hero.belongings.getItem(ArrowBag.class) != null) {
                ArrowBag arrowBag = Dungeon.hero.belongings.getItem(ArrowBag.class);
                damage = arrowBag.proc((Hero) attacker, defender, damage);
            }
            if (attacker == Dungeon.hero && BowMasterSkill.isFastShot((Hero) attacker)) {
                damage = Math.round(damage * BowMasterSkill.fastShotDamageMultiplier((Hero) attacker));
            }
            if (attacker == Dungeon.hero && attacker.buff(BowMasterSkill.class) != null) {
                damage = attacker.buff(BowMasterSkill.class).proc(damage);
            }

            return super.proc(attacker, defender, damage);
        }

        @Override
        public int buffedLvl(){
            return arrowFrom.buffedLvl();
        }

        @Override
        public int damageRoll(Char owner) {
            int damage = arrowFrom.arrowDamage();
            if (owner instanceof Hero) {
                Hero hero = (Hero)owner;
                Char enemy = hero.attackTarget();
                if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
                    //deals 50% toward max to max on surprise, instead of min to max.
                    int diff = arrowFrom.arrowMax() - arrowFrom.arrowMin();
                    damage = augment.damageFactor(Hero.heroDamageIntRange(
                            arrowFrom.arrowMin() + Math.round(diff*(3/(3f+tier))), //75%, 60%, 50%, 43%, 38% toward max
                            arrowFrom.arrowMax()));
                    int exStr = hero.STR() - STRReq();
                    if (exStr > 0) {
                        damage += Hero.heroDamageIntRange(0, exStr);
                    }
                }

                if (hero.buff(PenetrationShotBuff.class) != null) {
                    damage = hero.buff(PenetrationShotBuff.class).proc(damage, this.buffedLvl(), this.tier);
                    Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
                }

                if (hero.buff(BowFatigue.class) != null) {
                    damage = hero.buff(BowFatigue.class).damage(damage);
                }
            }
            return damage;
        }

        @Override
        public boolean hasEnchant(Class<? extends Enchantment> type, Char owner) {
            return arrowFrom.hasEnchant(type, owner);
        }

        @Override
        public float delayFactor(Char owner) {
            if (owner == Dungeon.hero && BowMasterSkill.isFastShot((Hero)owner)) {
                return 0;
            }
            return arrowFrom.delayFactor(owner);
        }

        @Override
        public float accuracyFactor(Char owner, Char target) {
            float ACC = arrowFrom.accuracyFactor(owner, target);
            if (owner == Dungeon.hero && owner.buff(BowMasterSkill.class) != null && owner.buff(BowMasterSkill.class).isPowerShot()) {
                return Hero.INFINITE_ACCURACY;
            }

            if (isBurst && owner instanceof Hero && ((Hero) owner).hasTalent(Talent.BULLSEYE)) {
                switch (((Hero) owner).pointsInTalent(Talent.BULLSEYE)) {
                    case 3:
                        return Hero.INFINITE_ACCURACY;
                    case 2:
                        ACC *= 5;
                        break;
                    case 1: default:
                        ACC *= 2;
                        break;
                }
            }

            return ACC;
        }

        @Override
        public int STRReq() {
            return arrowFrom.STRReq();
        }

        private float arrowPinChance() {
            float chance = 0.25f + 0.125f*Dungeon.hero.pointsInTalent(Talent.DEXTERITY);
            if (Dungeon.hero.hasTalent(Talent.PERFECT_SHOT) && isBurst) {
                chance = Math.max(chance, Dungeon.hero.pointsInTalent(Talent.PERFECT_SHOT)/3f);
            }
            return chance;
        }

        @Override
        protected void onThrow(int cell) {
            Char enemy = Actor.findChar( cell );
            if (enemy != null && !(enemy instanceof Hero)) {
                if (curUser.shoot( enemy, this )) {
                    if (Random.Float() < arrowPinChance()) {
                        if (enemy.isAlive()) {
                            Buff.affect(enemy, ArrowAttached.class).hit();
                        } else {
                            dropArrow(cell);
                        }
                    }
                    if (!enemy.isAlive() && isBurst && Dungeon.hero.hasTalent(Talent.HURRICANE)) {
                        Buff.affect(Dungeon.hero, GreaterHaste.class).set(1+Dungeon.hero.pointsInTalent(Talent.HURRICANE));
                    }
                } else {
                    if (Random.Float() < arrowPinChance()) {
                        dropArrow(cell);
                    }
                }
            }

            if (enemy == null || enemy instanceof Hero) {
                if (Random.Float() < arrowPinChance()) {
                    dropArrow(cell);
                }
            }

            SharpShooterBuff.rangedLethal(enemy, isBurst, this);

            onShoot();
        }

        public void dropArrow(int cell) {
            Dungeon.level.drop(new ArrowItem(), cell).sprite.drop(cell);
        }

        public void onShoot() {
            if (useBullet) Dungeon.bullet--;

            if (Dungeon.hero.buff(PenetrationShotBuff.class) != null) {
                Dungeon.hero.buff(PenetrationShotBuff.class).detach();
            }
            if (Dungeon.hero.subClass != HeroSubClass.BOWMASTER) {
                Buff.affect(Dungeon.hero, BowFatigue.class).countUp(1);
            }
            if (Dungeon.hero.subClass == HeroSubClass.BOWMASTER) {
                Buff.affect(Dungeon.hero, BowMasterSkill.class).shoot();
            }
            if (Dungeon.hero.hasTalent(Talent.SPECTRE_ARROW)) {
                if (Random.Float() < Dungeon.hero.pointsInTalent(Talent.SPECTRE_ARROW)/6f && useBullet) {
                    Dungeon.bullet++;
                }
            }

            updateQuickslot();
        }

        @Override
        public void throwSound() {
            Sample.INSTANCE.play( Assets.Sounds.ATK_SPIRITBOW, 1, Random.Float(0.87f, 1.15f) );
        }

        @Override
        public void cast(final Hero user, int dst) {
            super.cast(user, dst);
        }
    }

    private CellSelector.Listener shooter = new CellSelector.Listener() {
        @Override
        public void onSelect( Integer target ) {
            if (target != null) {
                if (target == curUser.pos) {
                    if (Dungeon.hero.heroClass == HeroClass.DUELIST && Dungeon.hero.buff(Charger.class) != null) {
                        Charger charger = Dungeon.hero.buff(Charger.class);
                        if (charger.charges >= 1) {
                            duelistAbility(Dungeon.hero, target);
                        } else {
                            GLog.w(Messages.get(MeleeWeapon.class, "ability_no_charge"));
                        }
                    }
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

    public static class BowFatigue extends CounterBuff {
        {
            type = buffType.NEGATIVE;
        }

        float duration;
        final float MAX_DURATION = 3f;

        @Override
        public boolean act() {
            duration -= TICK;
            if (duration <= 0) {
                detach();
            }

            spend(TICK);

            return true;
        }

        @Override
        public int icon() {
            return BuffIndicator.WEAKNESS;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0x999999);
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", (int)count(), Messages.decimalFormat("#.##", duration));
        }

        public static final String DURATION = "duration";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);

            bundle.put(DURATION, duration);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);

            duration = bundle.getFloat(DURATION);
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, ((MAX_DURATION-1) - duration) / (MAX_DURATION-1));
        }

        @Override
        public void countUp(float inc) {
            duration = MAX_DURATION;
            super.countUp(inc);
        }

        @Override
        public String iconTextDisplay() {
            return (int)count()+"";
        }

        public int damage(int damage) {
            if (this.count() > 4) {
                int exceed = (int)this.count() - 4;
                damage = Math.round(damage * (float)Math.pow(0.9f, exceed));
            }
            return damage;
        }
    }
}
