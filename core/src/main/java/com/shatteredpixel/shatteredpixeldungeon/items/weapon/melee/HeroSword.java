package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public class HeroSword extends MeleeWeapon {

    {
        image = ItemSpriteSheet.HERO_SWORD;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1f;

        unique = true;
        bones = false;
    }

    public MeleeWeapon usedWep;

    int ability = -1;

    public static final int NO_ABILITY          = -1;
    public static final int SNEAK               = 0;
    public static final int HEAVY_BLOW          = 1;
    public static final int COMBO_STRIKE        = 2;
    public static final int RETRIBUTION         = 3;
    public static final int CLEAVE              = 4;
    public static final int DEFENSIVE_STANCE    = 5;
    public static final int RUNIC_SLASH         = 6;
    public static final int SWORD_DANCE         = 7;
    public static final int HARVEST             = 8;
    public static final int LUNGE               = 9;
    public static final int ANGELIZE            = 10;
    public static final int REVERSE_GRIP        = 11;
    public static final int PARRY               = 12;
    public static final int FLASH_SLASH         = 13;
    public static final int LASH                = 14;
    public static final int GUARD               = 15;

    public HeroSword() {}

    public HeroSword(int ability, MeleeWeapon wep) {
        this.tier = wep.tier;
        this.DLY = wep.DLY;
        this.RCH = wep.RCH;
        this.ability = ability;
        this.usedWep = wep;
    }

    private static final String USED_WEAPON	= "usedWep";
    private static final String ABILITY	= "ability";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( USED_WEAPON, usedWep );
        bundle.put( ABILITY, ability );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        usedWep = (MeleeWeapon) bundle.get( USED_WEAPON );
        ability = bundle.getInt( ABILITY );
    }

    @Override
    public int min(int lvl) {
        return usedWep.min(lvl);
    }

    @Override
    public int max(int lvl) {
        return usedWep.max(lvl);
    }

    @Override
    public int STRReq(int lvl) {
        return usedWep.STRReq(lvl);
    }

    @Override
    public int defenseFactor( Char owner ) {
        return usedWep.defenseFactor(owner);
    }

    @Override
    public int reachFactor(Char owner) {
        int reach = super.reachFactor(owner);
        reach += usedWep.reachFactor(owner);

        return reach - 1;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public int value() {
        return -1;
    }

    @Override
    public String info() {
        String info = super.info();

        info += "\n\n" + Messages.get(this, "properties", usedWep.name());

        return info;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        usedWep.proc(attacker, defender, damage);
        return super.proc( attacker, defender, damage );
    }

    @Override
    protected int baseChargeUse(Hero hero, Char target){
        if (ability == CLEAVE) {
            if (hero.buff(Sword.CleaveTracker.class) != null) {
                return 0;
            } else {
                return 1;
            }
        }
        return 1;
    }

    @Override
    public String targetingPrompt() {
        switch (ability) {
            case NO_ABILITY: case DEFENSIVE_STANCE: case SWORD_DANCE: case ANGELIZE: case REVERSE_GRIP: case PARRY: case LASH: case GUARD: default:
                return null;
            case SNEAK: case HEAVY_BLOW: case COMBO_STRIKE: case RETRIBUTION: case CLEAVE: case RUNIC_SLASH: case HARVEST: case LUNGE: case FLASH_SLASH:
                return Messages.get(this, "prompt");
        }
    }

    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        int dmgBoost;
        switch (ability) {
            case NO_ABILITY: default:
                break;
            case SNEAK: //단검 능력
                Dagger.sneakAbility(hero, target, 6, 2+buffedLvl(), this);
                break;
            case HEAVY_BLOW: //손도끼 능력
                dmgBoost = augment.damageFactor(Math.round(0.45f*max(buffedLvl())));
                Mace.heavyBlowAbility(hero, target, 1, dmgBoost, this);
                break;
            case COMBO_STRIKE: //징 박힌 장갑 능력
                dmgBoost = augment.damageFactor(Math.round(0.45f*max(buffedLvl())));
                Sai.comboStrikeAbility(hero, target, 0, dmgBoost, this);
                break;
            case RETRIBUTION: //특대 도끼 능력
                if (hero.HP / (float)hero.HT >= 0.5f){
                    GLog.w(Messages.get(this, "ability_cant_use"));
                    return;
                }

                if (target == null) {
                    return;
                }

                Char enemy = Actor.findChar(target);
                if (enemy == null || enemy == hero || hero.isCharmedBy(enemy) || !Dungeon.level.heroFOV[target]) {
                    GLog.w(Messages.get(this, "ability_no_target"));
                    return;
                }

                hero.belongings.abilityWeapon = this;
                if (!hero.canAttack(enemy)){
                    GLog.w(Messages.get(this, "ability_target_range"));
                    hero.belongings.abilityWeapon = null;
                    return;
                }
                hero.belongings.abilityWeapon = null;

                hero.sprite.attack(enemy.pos, new Callback() {
                    @Override
                    public void call() {
                        beforeAbilityUsed(hero, enemy);
                        AttackIndicator.target(enemy);

                        //roughly +50% base damage, +50% scaling
                        int dmgBoost = augment.damageFactor(Math.round(0.5f*max(buffedLvl())));

                        if (hero.attack(enemy, 1, dmgBoost, Char.INFINITE_ACCURACY)){
                            Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
                        }

                        Invisibility.dispel();
                        if (!enemy.isAlive()){
                            hero.next();
                            onAbilityKill(hero, enemy);
                        } else {
                            hero.spendAndNext(hero.attackDelay());
                        }
                        afterAbilityUsed(hero);
                    }
                });
                break;
            case CLEAVE: //검 능력
                dmgBoost = augment.damageFactor(Math.round(0.67f*max(buffedLvl())));
                Sword.cleaveAbility(hero, target, 1, dmgBoost, this);
                break;
            case DEFENSIVE_STANCE: //육척봉 능력
                beforeAbilityUsed(hero, null);
                //1 turn less as using the ability is instant
                Buff.prolong(hero, Quarterstaff.DefensiveStance.class, 3 + buffedLvl());
                hero.sprite.operate(hero.pos);
                hero.next();
                afterAbilityUsed(hero);
                break;
            case RUNIC_SLASH: //룬 검 능력
                if (target == null) {
                    return;
                }

                Char enemy2 = Actor.findChar(target);
                if (enemy2 == null || enemy2 == hero || hero.isCharmedBy(enemy2) || !Dungeon.level.heroFOV[target]) {
                    GLog.w(Messages.get(this, "ability_no_target"));
                    return;
                }

                //we apply here because of projecting
                RunicBlade.RunicSlashTracker tracker = Buff.affect(hero, RunicBlade.RunicSlashTracker.class);
                tracker.boost = 2f + 0.50f*buffedLvl();
                hero.belongings.abilityWeapon = this;
                if (!hero.canAttack(enemy2)){
                    GLog.w(Messages.get(this, "ability_target_range"));
                    tracker.detach();
                    hero.belongings.abilityWeapon = null;
                    return;
                }
                hero.belongings.abilityWeapon = null;

                hero.sprite.attack(enemy2.pos, new Callback() {
                    @Override
                    public void call() {
                        beforeAbilityUsed(hero, enemy2);
                        AttackIndicator.target(enemy2);
                        if (hero.attack(enemy2, 1f, 0, Char.INFINITE_ACCURACY)){
                            Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
                            if (!enemy2.isAlive()){
                                onAbilityKill(hero, enemy2);
                            }
                        }
                        tracker.detach();
                        Invisibility.dispel();
                        hero.spendAndNext(hero.attackDelay());
                        afterAbilityUsed(hero);
                    }
                });
                break;
            case SWORD_DANCE: //시미타 능력
                beforeAbilityUsed(hero, null);
                //1 turn less as using the ability is instant
                Buff.prolong(hero, Scimitar.SwordDance.class, 3+buffedLvl());
                hero.sprite.operate(hero.pos);
                hero.next();
                afterAbilityUsed(hero);
                break;
            case HARVEST: //손낫 능력
                //replaces damage with 50% avg dmg of bleed
                int bleedAmt = augment.damageFactor(Math.round(0.5f*max(buffedLvl())));
                Sickle.harvestAbility(hero, target, 0f, bleedAmt, this);
                break;
            case LUNGE: //레이피어 능력
                //roughly +67% damage
                dmgBoost = augment.damageFactor(Math.round(0.67f*max(buffedLvl())));
                Rapier.lungeAbility(hero, target, 1, dmgBoost, this);
                break;
            case ANGELIZE: //성서 능력
                Bible.angelAbility(hero, 5+buffedLvl(), this);
                break;
            case REVERSE_GRIP: //쌍단검 능력
                beforeAbilityUsed(hero, null);
                //1 turn less as using the ability is instant
                Buff.prolong(hero, DualDagger.ReverseBlade.class, 5+buffedLvl());
                Sample.INSTANCE.play(Assets.Sounds.MISS);
                hero.sprite.emitter().burst( Speck.factory( Speck.JET ), 20);
                hero.next();
                afterAbilityUsed(hero);
                break;
            case PARRY: //쌍절곤 능력
                beforeAbilityUsed(hero, null);
                Invisibility.dispel();
                Buff.affect(hero, Nunchaku.ParryTracker.class, Actor.TICK);
                hero.spendAndNext(Actor.TICK);
                hero.busy();
                afterAbilityUsed(hero);
                break;
            case FLASH_SLASH: //낡은 도 능력
                NormalKatana.flashSlashAbility(hero, target, 0.6f, this);
                break;
            case LASH: //채찍 능력
                ArrayList<Char> targets = new ArrayList<>();
                Char closest = null;

                hero.belongings.abilityWeapon = this;
                for (Char ch : Actor.chars()){
                    if (ch.alignment == Char.Alignment.ENEMY
                            && !hero.isCharmedBy(ch)
                            && Dungeon.level.heroFOV[ch.pos]
                            && hero.canAttack(ch)){
                        targets.add(ch);
                        if (closest == null || Dungeon.level.trueDistance(hero.pos, closest.pos) > Dungeon.level.trueDistance(hero.pos, ch.pos)){
                            closest = ch;
                        }
                    }
                }
                hero.belongings.abilityWeapon = null;

                if (targets.isEmpty()) {
                    GLog.w(Messages.get(this, "ability_no_target"));
                    return;
                }

                throwSound();
                Char finalClosest = closest;
                hero.sprite.attack(hero.pos, new Callback() {
                    @Override
                    public void call() {
                        beforeAbilityUsed(hero, finalClosest);
                        //roughly +20% base damage
                        int dmgBoost = augment.damageFactor(Math.round(0.2f*max(buffedLvl())));
                        for (Char ch : targets) {
                            hero.attack(ch, 1, dmgBoost, Char.INFINITE_ACCURACY);
                            if (!ch.isAlive()){
                                onAbilityKill(hero, ch);
                            }
                        }
                        Invisibility.dispel();
                        hero.spendAndNext(hero.attackDelay());
                        afterAbilityUsed(hero);
                    }
                });
                break;
            case GUARD: //원형 방패 능력
                RoundShield.guardAbility(hero, 5+buffedLvl(), this);
                break;
        }
    }

    @Override
    public String abilityName() {
        MeleeWeapon wep;
        switch (ability) {
            case NO_ABILITY: default:
                wep = new MeleeWeapon();
                break;
            case SNEAK:
                wep = new Dagger();
                break;
            case HEAVY_BLOW:
                wep = new HandAxe();
                break;
            case COMBO_STRIKE:
                wep = new Gloves();
                break;
            case RETRIBUTION:
                wep = new Greataxe();
                break;
            case CLEAVE:
                wep = new WornShortsword();
                break;
            case DEFENSIVE_STANCE:
                wep = new Quarterstaff();
                break;
            case RUNIC_SLASH:
                wep = new RunicBlade();
                break;
            case SWORD_DANCE:
                wep = new Scimitar();
                break;
            case HARVEST:
                wep = new Sickle();
                break;
            case LUNGE:
                wep = new Rapier();
                break;
            case ANGELIZE:
                wep = new Bible();
                break;
            case REVERSE_GRIP:
                wep = new DualDagger();
                break;
            case PARRY:
                wep = new Nunchaku();
                break;
            case FLASH_SLASH:
                wep = new WornKatana();
                break;
            case LASH:
                wep = new Whip();
                break;
            case GUARD:
                wep = new RoundShield();
                break;
        }
        return wep.abilityName();
    }

    @Override
    public String abilityInfo() {
        String prefix = Messages.get(this, "prefix");
        MeleeWeapon wep;
        switch (ability) {
            case NO_ABILITY: default:
                wep = new MeleeWeapon() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case SNEAK:
                wep = new Dagger() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case HEAVY_BLOW:
                wep = new HandAxe() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case COMBO_STRIKE:
                wep = new Gloves() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case RETRIBUTION:
                wep = new Greataxe() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case CLEAVE:
                wep = new WornShortsword() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case DEFENSIVE_STANCE:
                wep = new Quarterstaff() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case RUNIC_SLASH:
                wep = new RunicBlade() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case SWORD_DANCE:
                wep = new Scimitar() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case HARVEST:
                wep = new Sickle() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case LUNGE:
                wep = new Rapier() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case ANGELIZE:
                wep = new Bible() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case REVERSE_GRIP:
                wep = new DualDagger() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case PARRY:
                wep = new Nunchaku() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case FLASH_SLASH:
                wep = new WornKatana() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case LASH:
                wep = new Whip() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
            case GUARD:
                wep = new RoundShield() {
                    @Override
                    public int max(int lvl) {
                        return HeroSword.this.max(lvl);
                    }
                };
                break;
        }
        if (levelKnown) {
            wep.identify().level(buffedLvl());
        }
        return prefix + " " + wep.abilityInfo();
    }

}
