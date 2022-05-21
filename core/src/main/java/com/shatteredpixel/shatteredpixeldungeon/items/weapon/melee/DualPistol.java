/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ElectroBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Focusing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.InfiniteBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Momentum;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.Riot;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfReload;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.GoldenBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.NaturesBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.PoisonBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.WindBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DualPistol extends MeleeWeapon {

    public static final String AC_SHOOT		= "SHOOT";
    public static final String AC_RELOAD = "RELOAD";

    public int max_round;
    public int round = 0;
    public boolean silencer = false;
    public boolean short_barrel = false;
    public boolean long_barrel = false;
    public boolean magazine = false;
    public boolean light = false;
    public boolean heavy = false;
    public boolean flash = false;
    public float reload_time;
    private static final String TXT_STATUS = "%d/%d";

    {

        defaultAction = AC_SHOOT;
        usesTargeting = true;

        image = ItemSpriteSheet.DUAL_PISTOL;
        hitSound = Assets.Sounds.HIT_CRUSH;
        hitSoundPitch = 0.8f;

        tier = 2;
    }

    private static final String ROUND = "round";
    private static final String MAX_ROUND = "max_round";
    private static final String RELOAD_TIME = "reload_time";
    private static final String SILENCER = "silencer";
    private static final String SHORT_BARREL = "short_barrel";
    private static final String LONG_BARREL = "long_barrel";
    private static final String MAGAZINE = "magazine";
    private static final String LIGHT = "light";
    private static final String HEAVY = "heavy";
    private static final String FLASH = "flash";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(MAX_ROUND, max_round);
        bundle.put(ROUND, round);
        bundle.put(RELOAD_TIME, reload_time);
        bundle.put(SILENCER, silencer);
        bundle.put(SHORT_BARREL, short_barrel);
        bundle.put(LONG_BARREL, long_barrel);
        bundle.put(MAGAZINE, magazine);
        bundle.put(LIGHT, light);
        bundle.put(HEAVY, heavy);
        bundle.put(FLASH, flash);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        max_round = bundle.getInt(MAX_ROUND);
        round = bundle.getInt(ROUND);
        reload_time = bundle.getFloat(RELOAD_TIME);
        silencer = bundle.getBoolean(SILENCER);
        short_barrel = bundle.getBoolean(SHORT_BARREL);
        long_barrel = bundle.getBoolean(LONG_BARREL);
        magazine = bundle.getBoolean(MAGAZINE);
        light = bundle.getBoolean(LIGHT);
        heavy = bundle.getBoolean(HEAVY);
        flash = bundle.getBoolean(FLASH);
    }




    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (isEquipped( hero )) {
            actions.add(AC_SHOOT);
            actions.add(AC_RELOAD);
        }
        return actions;
    }



    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_SHOOT)) {

            if (!isEquipped( hero )) {
                usesTargeting = false;
                GLog.w(Messages.get(this, "not_equipped"));
            } else {
                if (round <= 0) {
                    reload_time = (hero.hasTalent(Talent.HEAVY_GUNNER) && Random.Int(10) < hero.pointsInTalent(Talent.HEAVY_GUNNER)) ? 0 : 2f* RingOfReload.reloadMultiplier(Dungeon.hero);
                    if (hero.hasTalent(Talent.ELEMENTAL_BULLET)) {
                        elementReload();
                    } else {
                        reload();
                    }
                } else {
                    reload_time = (hero.hasTalent(Talent.HEAVY_GUNNER) && Random.Int(10) < hero.pointsInTalent(Talent.HEAVY_GUNNER)) ? 0 : 2f* RingOfReload.reloadMultiplier(Dungeon.hero);
                    usesTargeting = true;
                    curUser = hero;
                    curItem = this;
                    GameScene.selectCell(shooter);
                }
            }
        }
        if (action.equals(AC_RELOAD)) {
            max_round = (magazine) ? 10 : 8;
            if (Dungeon.hero.hasTalent(Talent.LARGER_MAGAZINE)) {
                max_round += 2f * Dungeon.hero.pointsInTalent(Talent.LARGER_MAGAZINE);
            }
            if (Dungeon.hero.hasTalent(Talent.DRUM_MAGAZINE)) {
                max_round += 2f * Dungeon.hero.pointsInTalent(Talent.DRUM_MAGAZINE);
            }
            if (round == max_round){
                GLog.w(Messages.get(this, "already_loaded"));
            } else if (round == 0 && hero.hasTalent(Talent.ELEMENTAL_BULLET)){
                elementReload();
            } else {
                reload();
            }
        }
    }

    public void reload() {
        max_round = (magazine) ? 10 : 8;
        if (Dungeon.hero.hasTalent(Talent.LARGER_MAGAZINE)) {
            max_round += 2f * Dungeon.hero.pointsInTalent(Talent.LARGER_MAGAZINE);
        }
        if (Dungeon.hero.hasTalent(Talent.DRUM_MAGAZINE)) {
            max_round += 2f * Dungeon.hero.pointsInTalent(Talent.DRUM_MAGAZINE);
        }
        curUser.spend(reload_time);
        curUser.busy();
        Sample.INSTANCE.play(Assets.Sounds.UNLOCK, 2, 1.1f);
        curUser.sprite.operate(curUser.pos);
        round = Math.max(max_round, round);

        GLog.i(Messages.get(this, "reloading"));

        if (Dungeon.hero.hasTalent(Talent.SAFE_RELOAD) && Dungeon.hero.buff(Talent.ReloadCooldown.class) == null) {
            Buff.affect(hero, Barrier.class).setShield(1+2*hero.pointsInTalent(Talent.SAFE_RELOAD));
            Buff.affect(hero, Talent.ReloadCooldown.class, 5f);
        }

        updateQuickslot();
    }

    public void elementReload() {
        Buff.detach(hero, FrostBullet.class);
        Buff.detach(hero, FireBullet.class);
        Buff.detach(hero, ElectroBullet.class);
        max_round = 4;
        if (Dungeon.hero.hasTalent(Talent.LARGER_MAGAZINE)) {
            max_round += 2f * Dungeon.hero.pointsInTalent(Talent.LARGER_MAGAZINE);
        }
        if (Dungeon.hero.hasTalent(Talent.DRUM_MAGAZINE)) {
            max_round += 2f * Dungeon.hero.pointsInTalent(Talent.DRUM_MAGAZINE);
        }

        curUser.spend(reload_time);
        curUser.busy();
        Sample.INSTANCE.play(Assets.Sounds.UNLOCK, 2, 1.1f);
        curUser.sprite.operate(curUser.pos);
        round = Math.max(max_round, round);

        GLog.i(Messages.get(this, "reloading"));

        if (Dungeon.hero.hasTalent(Talent.SAFE_RELOAD) && Dungeon.hero.buff(Talent.ReloadCooldown.class) == null) {
            Buff.affect(hero, Barrier.class).setShield(1+2*hero.pointsInTalent(Talent.SAFE_RELOAD));
            Buff.affect(hero, Talent.ReloadCooldown.class, 5f);
        }

        int chance = Random.Int(6);
        if (Dungeon.hero.pointsInTalent(Talent.ELEMENTAL_BULLET) >= 1 && chance == 0) {
            Buff.affect(hero, FrostBullet.class, 100f);
        }
        if (Dungeon.hero.pointsInTalent(Talent.ELEMENTAL_BULLET) >= 2 && chance == 1) {
            Buff.affect(hero, FireBullet.class, 100f);
        }
        if (Dungeon.hero.pointsInTalent(Talent.ELEMENTAL_BULLET) == 3 && chance == 2) {
            Buff.affect(hero, ElectroBullet.class, 100f);
        }

        updateQuickslot();
    }

    public int getRound() { return this.round; }

    @Override
    public String status() {
        max_round = (magazine) ? 10 : 8;
        if (Dungeon.hero.hasTalent(Talent.LARGER_MAGAZINE)) {
            max_round += 2f * Dungeon.hero.pointsInTalent(Talent.LARGER_MAGAZINE);
        }
        if (Dungeon.hero.hasTalent(Talent.DRUM_MAGAZINE)) {
            max_round += 2f * Dungeon.hero.pointsInTalent(Talent.DRUM_MAGAZINE);
        }

        return Messages.format(TXT_STATUS, round, max_round);
    }

    @Override
    public int STRReq(int lvl) {
        int needSTR = STRReq(tier, lvl);
        if (heavy) {
            needSTR += 2;
        }
        if (light) {
            needSTR -= 2;
        }
        return needSTR;
    }

    public int min(int lvl) {
        return tier +
               lvl;
    }

    public int max(int lvl) {
        return 3 * (tier + 1) +
               lvl;
    }

    public int Bulletmin(int lvl) {
        return tier +
                lvl +
                RingOfSharpshooting.levelDamageBonus(Dungeon.hero);
    }

    public int Bulletmax(int lvl) {
        return 2 * (tier)   +
                lvl * (tier) +
                RingOfSharpshooting.levelDamageBonus(Dungeon.hero);
    }

    @Override
    public String info() {

        max_round = (magazine) ? 10 : 8;
        if (Dungeon.hero.hasTalent(Talent.LARGER_MAGAZINE)) {
            max_round += 2f * Dungeon.hero.pointsInTalent(Talent.LARGER_MAGAZINE);
        }
        if (Dungeon.hero.hasTalent(Talent.DRUM_MAGAZINE)) {
            max_round += 2f * Dungeon.hero.pointsInTalent(Talent.DRUM_MAGAZINE);
        }
        reload_time = 2f* RingOfReload.reloadMultiplier(Dungeon.hero);
        String info = desc();

        if (levelKnown) {
            info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_known", tier, augment.damageFactor(min()), augment.damageFactor(max()), STRReq());
            if (STRReq() > Dungeon.hero.STR()) {
                info += " " + Messages.get(Weapon.class, "too_heavy");
            } else if (Dungeon.hero.STR() > STRReq()){
                info += " " + Messages.get(Weapon.class, "excess_str", Dungeon.hero.STR() - STRReq());
            }
            info += "\n\n" + Messages.get(DualPistol.class, "stats_known",
                    Bulletmin(DualPistol.this.buffedLvl()),
                    Bulletmax(DualPistol.this.buffedLvl()),
                    round, max_round, new DecimalFormat("#.##").format(reload_time));
        } else {
            info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_unknown", tier, min(0), max(0), STRReq(0));
            if (STRReq(0) > Dungeon.hero.STR()) {
                info += " " + Messages.get(MeleeWeapon.class, "probably_too_heavy");
            }
            info += "\n\n" + Messages.get(DualPistol.class, "stats_unknown",
                    Bulletmin(0),
                    Bulletmax(0),
                    round, max_round, new DecimalFormat("#.##").format(reload_time));
        }

        String statsInfo = statsInfo();
        if (!statsInfo.equals("")) info += "\n\n" + statsInfo;

        switch (augment) {
            case SPEED:
                info += " " + Messages.get(Weapon.class, "faster");
                break;
            case DAMAGE:
                info += " " + Messages.get(Weapon.class, "stronger");
                break;
            case NONE:
        }

        if (enchantment != null && (cursedKnown || !enchantment.curse())){
            info += "\n\n" + Messages.get(Weapon.class, "enchanted", enchantment.name());
            info += " " + Messages.get(enchantment, "desc");
        }

        if (cursed && isEquipped( Dungeon.hero )) {
            info += "\n\n" + Messages.get(Weapon.class, "cursed_worn");
        } else if (cursedKnown && cursed) {
            info += "\n\n" + Messages.get(Weapon.class, "cursed");
        } else if (!isIdentified() && cursedKnown){
            info += "\n\n" + Messages.get(Weapon.class, "not_cursed");
        }

        if (silencer) {
            info += "\n\n" + Messages.get(CrudePistol.class, "silencer");
        }
        if (short_barrel) {
            info += "\n\n" + Messages.get(CrudePistol.class, "short_barrel");
        }
        if (long_barrel) {
            info += "\n\n" + Messages.get(CrudePistol.class, "long_barrel");
        }
        if (magazine) {
            info += "\n\n" + Messages.get(CrudePistol.class, "magazine");
        }
        if (light) {
            info += "\n\n" + Messages.get(CrudePistol.class, "light");
        }
        if (heavy) {
            info += "\n\n" + Messages.get(CrudePistol.class, "heavy");
        }
        if (flash) {
            info += "\n\n" + Messages.get(CrudePistol.class, "flash");
        }

        if (Dungeon.isChallenged(Challenges.DURABILITY) && levelKnown) {
            info += "\n\n" + Messages.get(Item.class, "durability_weapon", durability(), maxDurability());
        }

        return info;
    }

    @Override
    public int targetingPos(Hero user, int dst) {
        return knockBullet().targetingPos(user, dst);
    }

    private int targetPos;

    @Override
    public int damageRoll(Char owner) {
        int damage = augment.damageFactor(super.damageRoll(owner));

        if (owner instanceof Hero) {
            int exStr = ((Hero)owner).STR() - STRReq();
            if (exStr > 0) {
                damage += Random.IntRange( 0, exStr );
            }
        }

        return damage;
    }                           //초과 힘에 따른 추가 데미지

    @Override
    protected float baseDelay(Char owner) {
        float delay = augment.delayFactor(this.DLY);
        if (owner instanceof Hero) {
            int encumbrance = STRReq() - ((Hero)owner).STR();
            if (encumbrance > 0){
                delay *= Math.pow( 1.2, encumbrance );
            }
        }
        if (Dungeon.hero.hasTalent(Talent.MARTIAL_ARTS)) {
            delay -= 0.1f * Dungeon.hero.pointsInTalent(Talent.MARTIAL_ARTS);
        }
        return delay;
    }

    public DualPistol.Bullet knockBullet(){
        return new DualPistol.Bullet();
    }
    public class Bullet extends MissileWeapon {

        {
            image = ItemSpriteSheet.DUAL_BULLET;

            hitSound = Assets.Sounds.PUFF;
            tier = 2;
        }

        @Override
        public int buffedLvl(){
            return DualPistol.this.buffedLvl();
        }

        @Override
        public int damageRoll(Char owner) {
            Hero hero = (Hero)owner;
            Char enemy = hero.enemy();
            int bulletdamage = Random.NormalIntRange(Bulletmin(DualPistol.this.buffedLvl()),
                    Bulletmax(DualPistol.this.buffedLvl()));

            if (owner.buff(Momentum.class) != null && owner.buff(Momentum.class).freerunning()) {
                bulletdamage = Math.round(bulletdamage * (1f + 0.15f * ((Hero) owner).pointsInTalent(Talent.PROJECTILE_MOMENTUM)));
            }

            if (owner.buff(Focusing.class) != null) {
                bulletdamage = Math.round(bulletdamage * (1.10f + 0.05f * ((Hero) owner).pointsInTalent(Talent.ARM_VETERAN)));
            }
            return bulletdamage;
        }

        @Override
        public boolean hasEnchant(Class<? extends Enchantment> type, Char owner) {
            return DualPistol.this.hasEnchant(type, owner);
        }

        @Override
        public int proc(Char attacker, Char defender, int damage) {
            SpiritBow bow = hero.belongings.getItem(SpiritBow.class);
            WindBow bow2 = hero.belongings.getItem(WindBow.class);
            GoldenBow bow3 = hero.belongings.getItem(GoldenBow.class);
            NaturesBow bow4 = hero.belongings.getItem(NaturesBow.class);
            PoisonBow bow5 = hero.belongings.getItem(PoisonBow.class);
            if (DualPistol.this.enchantment == null
                    && Random.Int(3) < hero.pointsInTalent(Talent.SHARED_ENCHANTMENT)
                    && hero.buff(MagicImmune.class) == null
                    && bow != null
                    && bow.enchantment != null) {
                return bow.enchantment.proc(this, attacker, defender, damage);
            } else if (DualPistol.this.enchantment == null
                    && Random.Int(3) < hero.pointsInTalent(Talent.SHARED_ENCHANTMENT)
                    && hero.buff(MagicImmune.class) == null
                    && bow2 != null
                    && bow2.enchantment != null) {
                return bow2.enchantment.proc(this, attacker, defender, damage);
            } else if (DualPistol.this.enchantment == null
                    && Random.Int(3) < hero.pointsInTalent(Talent.SHARED_ENCHANTMENT)
                    && hero.buff(MagicImmune.class) == null
                    && bow3 != null
                    && bow3.enchantment != null) {
                return bow3.enchantment.proc(this, attacker, defender, damage);
            } else if (DualPistol.this.enchantment == null
                    && Random.Int(3) < hero.pointsInTalent(Talent.SHARED_ENCHANTMENT)
                    && hero.buff(MagicImmune.class) == null
                    && bow4 != null
                    && bow4.enchantment != null) {
                return bow4.enchantment.proc(this, attacker, defender, damage);
            } else if (DualPistol.this.enchantment == null
                    && Random.Int(3) < hero.pointsInTalent(Talent.SHARED_ENCHANTMENT)
                    && hero.buff(MagicImmune.class) == null
                    && bow5 != null
                    && bow5.enchantment != null) {
                return bow5.enchantment.proc(this, attacker, defender, damage);
            } else {
                return DualPistol.this.proc(attacker, defender, damage);
            }
        }

        @Override
        public float delayFactor(Char user) {
            if (hero.hasTalent(Talent.RECOIL_CONTROL)) {
                if (hero.buff(Riot.riotTracker.class) != null) {
                    return DualPistol.this.delayFactor(user)/(2f + 2f * hero.pointsInTalent(Talent.RECOIL_CONTROL)/3f);
                } else {
                    return DualPistol.this.delayFactor(user)/(1f + hero.pointsInTalent(Talent.RECOIL_CONTROL)/3f);
                }
            } else {
                if (hero.buff(Riot.riotTracker.class) != null) {
                    return DualPistol.this.delayFactor(user)/2f;
                } else {
                        return DualPistol.this.delayFactor(user);
                }
            }
        }

        @Override
        public float accuracyFactor(Char user) {
            float accFactor = super.accuracyFactor(user);
            if (Dungeon.hero.hasTalent(Talent.ENHANCED_FOCUSING)) {
                accFactor += 0.1f * Dungeon.hero.pointsInTalent(Talent.ENHANCED_FOCUSING);
            }
            return accFactor;
        }

        @Override
        public int STRReq(int lvl) {
            if (DualPistol.this.masteryPotionBonus) {
                return STRReq(tier, DualPistol.this.buffedLvl()) - 2;
            }
            return STRReq(tier, DualPistol.this.buffedLvl());
        }

        @Override
        protected void onThrow( int cell ) {
            if (hero.hasTalent(Talent.RECOIL_PRACTICE) && Random.Int(3) <= hero.pointsInTalent(Talent.RECOIL_PRACTICE)-1) {
                for (int i=1; i<=3; i++) {                                                           //i<=n에서 n이 반복하는 횟수, 즉 발사 횟수
                    if (round <= 0) {
                        break;
                    }
                    Char enemy = Actor.findChar(cell);
                    if (enemy == null || enemy == curUser) {
                        parent = null;
                        CellEmitter.get(cell).burst(SmokeParticle.FACTORY, 2);
                        CellEmitter.center(cell).burst(BlastParticle.FACTORY, 2);
                    } else {
                        if (!curUser.shoot(enemy, this)) {
                            CellEmitter.get(cell).burst(SmokeParticle.FACTORY, 2);
                            CellEmitter.center(cell).burst(BlastParticle.FACTORY, 2);
                        }
                    }
                    if (hero.buff(InfiniteBullet.class) != null) {
                        //round preserves
                    } else if (hero.buff(Riot.riotTracker.class) != null && Random.Int(10) <= hero.pointsInTalent(Talent.ROUND_PRESERVE)-1) {
                        //round preserves
                    } else {
                        if (hero.subClass == HeroSubClass.LAUNCHER && Random.Int(10) == 0) {
                            //round preserves
                        } else {
                            round --;
                        }
                    }
                }
            } else {
                for (int i=1; i<=2; i++) {                                                           //i<=n에서 n이 반복하는 횟수, 즉 발사 횟수
                    if (round <= 0) {
                        break;
                    }
                    Char enemy = Actor.findChar(cell);
                    if (enemy == null || enemy == curUser) {
                        parent = null;
                        CellEmitter.get(cell).burst(SmokeParticle.FACTORY, 2);
                        CellEmitter.center(cell).burst(BlastParticle.FACTORY, 2);
                    } else {
                        if (!curUser.shoot(enemy, this)) {
                            CellEmitter.get(cell).burst(SmokeParticle.FACTORY, 2);
                            CellEmitter.center(cell).burst(BlastParticle.FACTORY, 2);
                        }
                    }

                    if (hero.buff(InfiniteBullet.class) != null) {
                        //round preserves
                    } else if (hero.buff(Riot.riotTracker.class) != null && Random.Int(10) <= hero.pointsInTalent(Talent.ROUND_PRESERVE)-1) {
                        //round preserves
                    } else {
                        if (hero.subClass == HeroSubClass.LAUNCHER && Random.Int(10) == 0) {
                            //round preserves
                        } else {
                            round --;
                        }
                    }
                }
            }
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if (mob.paralysed <= 0
                        && Dungeon.level.distance(curUser.pos, mob.pos) <= 4
                        && mob.state != mob.HUNTING
                        && !silencer) {
                    mob.beckon( curUser.pos );
                }
            }
            updateQuickslot();
            if (Dungeon.isChallenged(Challenges.DURABILITY)) {
                DualPistol.this.use();
            }
        }

        @Override
        public void throwSound() {
            Sample.INSTANCE.play( Assets.Sounds.HIT_CRUSH, 1, Random.Float(0.33f, 0.66f) );
        }

        @Override
        public void cast(final Hero user, final int dst) {
            super.cast(user, dst);
        }
    }

    private CellSelector.Listener shooter = new CellSelector.Listener() {
        @Override
        public void onSelect( Integer target ) {
            if (target != null) {
                if (target == curUser.pos) {
                    reload();
                } else {
                    knockBullet().cast(curUser, target);
                }
            }
        }
        @Override
        public String prompt() {
            return Messages.get(SpiritBow.class, "prompt");
        }
    };
}