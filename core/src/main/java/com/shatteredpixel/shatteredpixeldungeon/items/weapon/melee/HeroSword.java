package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

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
    public int defenseFactor( Char owner ) {
        return usedWep.defenseFactor(owner);
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

        info += "\n\n" + Messages.get(this, "properties", usedWep.trueName());

        return info;
    }

    @Override
    public String abilityName() {
        return Messages.upperCase(Messages.get(this, "ability_name_" + ability));
    }

    @Override
    public String abilityDesc() {
        return Messages.get(this, "ability_desc_" + ability);
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        usedWep.proc(attacker, defender, damage);
        return super.proc( attacker, defender, damage );
    }

    @Override
    protected int baseChargeUse(Hero hero, Char target){
        switch (ability) {
            case -1: default:
                return 0;
            case 0: case 5: case 7: case 8: case 10: case 11: case 12:
                return 2;
            case 1:
                if (target == null || (target instanceof Mob && ((Mob) target).surprisedBy(hero))) {
                    return 1;
                } else {
                    return 2;
                }
            case 2: case 3: case 6: case 9: case 13:
                return 1;
            case 4:
                if (hero.buff(Sword.CleaveTracker.class) != null){
                    return 0;
                } else {
                    return 1;
                }
        }
    }

    @Override
    public String targetingPrompt() {
        switch (ability) {
            case -1: case 5: case 7: case 10: case 11: case 12: default:
                return null;
            case 0: case 1: case 2: case 3: case 4: case 6: case 8: case 9: case 13:
                return Messages.get(this, "prompt");
        }
    }

    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        switch (ability) {
            case -1: default:
                break;
            case 0: //단검 능력
                Dagger.sneakAbility(hero, target, 6, this);
                break;
            case 1: //손도끼 능력
                Mace.heavyBlowAbility(hero, target, 1.45f, this);
                break;
            case 2: //징 박힌 장갑 능력
                Sai.comboStrikeAbility(hero, target, 0.45f, this);
                break;
            case 3: //특대 도끼 능력
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
                    GLog.w(Messages.get(this, "ability_bad_position"));
                    hero.belongings.abilityWeapon = null;
                    return;
                }
                hero.belongings.abilityWeapon = null;

                hero.sprite.attack(enemy.pos, new Callback() {
                    @Override
                    public void call() {
                        beforeAbilityUsed(hero, enemy);
                        AttackIndicator.target(enemy);
                        if (hero.attack(enemy, 1.50f, 0, Char.INFINITE_ACCURACY)){
                            Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
                            if (!enemy.isAlive()){
                                onAbilityKill(hero, enemy);
                            }
                        }
                        Invisibility.dispel();
                        hero.spendAndNext(hero.attackDelay());
                        afterAbilityUsed(hero);
                    }
                });
                break;
            case 4: //검 능력
                Sword.cleaveAbility(hero, target, 1.33f, this);
                break;
            case 5: //육척봉 능력
                beforeAbilityUsed(hero, null);
                Buff.prolong(hero, Quarterstaff.DefensiveStance.class, 4f); //4 turns as using the ability is instant
                hero.sprite.operate(hero.pos);
                hero.next();
                afterAbilityUsed(hero);
                break;
            case 6: //룬 검 능력
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
                hero.belongings.abilityWeapon = this;
                if (!hero.canAttack(enemy2)){
                    GLog.w(Messages.get(this, "ability_bad_position"));
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
            case 7: //시미타 능력
                beforeAbilityUsed(hero, null);
                Buff.prolong(hero, Scimitar.SwordDance.class, 4f); //4 turns as using the ability is instant
                hero.sprite.operate(hero.pos);
                hero.next();
                afterAbilityUsed(hero);
                break;
            case 8: //손낫 능력
                Sickle.harvestAbility(hero, target, 1f, this);
                break;
            case 9: //레이피어 능력
                Rapier.lungeAbility(hero, target, 1.67f, 0, this);
                break;
            case 10: //성서 능력
                Bible.angelAbility(hero, 5, this);
                break;
            case 11: //쌍단검 능력
                beforeAbilityUsed(hero, null);
                Buff.prolong(hero, DualDagger.ReverseBlade.class, 5f); //5 turns as using the ability is instant
                Sample.INSTANCE.play(Assets.Sounds.MISS);
                hero.sprite.emitter().burst( Speck.factory( Speck.JET ), 20);
                hero.next();
                afterAbilityUsed(hero);
                break;
            case 12: //쌍절곤 능력
                beforeAbilityUsed(hero, null);
                Invisibility.dispel();
                Buff.affect(hero, Nunchaku.ParryTracker.class, Actor.TICK);
                hero.spendAndNext(Actor.TICK);
                hero.busy();
                afterAbilityUsed(hero);
                break;
            case 13: //낡은 도 능력
                NormalKatana.flashSlashAbility(hero, target, 0.6f, this);
                break;
        }
    }

}
