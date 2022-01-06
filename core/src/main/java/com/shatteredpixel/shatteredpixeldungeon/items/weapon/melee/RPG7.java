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
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Focusing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.InfiniteBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Momentum;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.Riot;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.APBullet;
import com.shatteredpixel.shatteredpixeldungeon.items.HPBullet;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfReload;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Evolution;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
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

import java.util.ArrayList;

public class RPG7 extends MeleeWeapon {

    public static final String AC_SHOOT		= "SHOOT";
    public static final String AC_RELOAD = "RELOAD";

    private int max_round;
    private int round;
    private float reload_time;
    private static final String TXT_STATUS = "%d/%d";

    {

        defaultAction = AC_SHOOT;
        usesTargeting = true;

        image = ItemSpriteSheet.RPG7;
        hitSound = Assets.Sounds.HIT_CRUSH;
        hitSoundPitch = 0.8f;

        tier = 6;
    }

    private static final String ROUND = "round";
    private static final String MAX_ROUND = "max_round";
    private static final String RELOAD_TIME = "reload_time";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(MAX_ROUND, max_round);
        bundle.put(ROUND, round);
        bundle.put(RELOAD_TIME, reload_time);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        max_round = bundle.getInt(MAX_ROUND);
        round = bundle.getInt(ROUND);
        reload_time = bundle.getFloat(RELOAD_TIME);
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
                    reload_time = (hero.hasTalent(Talent.HEAVY_GUNNER) && Random.Int(10) < hero.pointsInTalent(Talent.HEAVY_GUNNER)) ? 0 : 3f* RingOfReload.reloadMultiplier(Dungeon.hero);
                    reload();
                } else {
                    reload_time = (hero.hasTalent(Talent.HEAVY_GUNNER) && Random.Int(10) < hero.pointsInTalent(Talent.HEAVY_GUNNER)) ? 0 : 3f* RingOfReload.reloadMultiplier(Dungeon.hero);
                    usesTargeting = true;
                    curUser = hero;
                    curItem = this;
                    GameScene.selectCell(shooter);
                }
            }
        }
        if (action.equals(AC_RELOAD)) {
            max_round = 1;
            if (round == max_round){
                GLog.w(Messages.get(this, "already_loaded"));
            } else {
                reload();
            }
        }
    }

    public void quickReload() {
        max_round = 1;
        round = Math.max(max_round, round);
        updateQuickslot();
    }

    public void reload() {
        max_round = 1;
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


    public int getRound() { return this.round; }

    @Override
    public String status() {
        max_round = 1;
        return Messages.format(TXT_STATUS, round, max_round);
    }

    @Override
    public int STRReq(int lvl) {
        return STRReq(tier, lvl); //18 base strength req, Changeable
    }

    public int min(int lvl) {
        return 0;
    }

    public int max(int lvl) {
        return 10;
    }

    public int Bulletmin(int lvl) {
        return (tier+5) +
                lvl      +
                RingOfSharpshooting.levelDamageBonus(hero);
    }

    public int Bulletmax(int lvl) {
        return 8 * (tier+6)   +
                lvl * (tier+6) +
                RingOfSharpshooting.levelDamageBonus(hero);
    }

    @Override
    public String info() {

        max_round = 1;
        reload_time = 3f* RingOfReload.reloadMultiplier(Dungeon.hero);
        String info = desc();

        if (levelKnown) {
            info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_known", tier, augment.damageFactor(min()), augment.damageFactor(max()), STRReq());
            if (STRReq() > hero.STR()) {
                info += " " + Messages.get(Weapon.class, "too_heavy");
            } else if (hero.STR() > STRReq()){
                info += " " + Messages.get(Weapon.class, "excess_str", hero.STR() - STRReq());
            }
            info += "\n\n" + Messages.get(RPG7.class, "stats_known",
                    Bulletmin(RPG7.this.buffedLvl()),
                    Bulletmax(RPG7.this.buffedLvl()),
                    round, max_round, reload_time);
        } else {
            info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_unknown", tier, min(0), max(0), STRReq(0));
            if (STRReq(0) > hero.STR()) {
                info += " " + Messages.get(MeleeWeapon.class, "probably_too_heavy");
            }
            info += "\n\n" + Messages.get(RPG7.class, "stats_unknown",
                    Bulletmin(0),
                    Bulletmax(0),
                    round, max_round, reload_time);
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

        if (cursed && isEquipped( hero )) {
            info += "\n\n" + Messages.get(Weapon.class, "cursed_worn");
        } else if (cursedKnown && cursed) {
            info += "\n\n" + Messages.get(Weapon.class, "cursed");
        } else if (!isIdentified() && cursedKnown){
            info += "\n\n" + Messages.get(Weapon.class, "not_cursed");
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
    }                   //공격 속도

    public RPG7.Rocket knockBullet(){
        return new RPG7.Rocket();
    }
    public class Rocket extends MissileWeapon {

        {
            image = ItemSpriteSheet.ROCKET;

            hitSound = Assets.Sounds.PUFF;
            tier = 5;                                                                            //if you make something different guns, you should change this
        }

        @Override
        public int damageRoll(Char owner) {
            Hero hero = (Hero)owner;
            Char enemy = hero.enemy();
            int bulletdamage = Random.NormalIntRange(Bulletmin(RPG7.this.buffedLvl()),
                    Bulletmax(RPG7.this.buffedLvl()));

            if (owner.buff(Momentum.class) != null && owner.buff(Momentum.class).freerunning()) {
                bulletdamage = Math.round(bulletdamage * (1f + 0.15f * ((Hero) owner).pointsInTalent(Talent.PROJECTILE_MOMENTUM)));
            }

            if (owner.buff(Focusing.class) != null) {
                bulletdamage = Math.round(bulletdamage * (1.2f + 0.1f * ((Hero) owner).pointsInTalent(Talent.ARM_VETERAN)));
            }
            return bulletdamage;
        }

        @Override
        public boolean hasEnchant(Class<? extends Enchantment> type, Char owner) {
            return RPG7.this.hasEnchant(type, owner);
        }

        @Override
        public int proc(Char attacker, Char defender, int damage) {
            return RPG7.this.proc(attacker, defender, damage);
        }

        @Override
        public float delayFactor(Char user) {
            if (hero.buff(Riot.riotTracker.class) != null) {
                return RPG7.this.delayFactor(user)/2f;
            } else {
                return RPG7.this.delayFactor(user);
            }
        }

        @Override
        public int STRReq(int lvl) {
            if (RPG7.this.masteryPotionBonus) {
                return STRReq(tier, RPG7.this.buffedLvl()) - 2;
            }
            return STRReq(tier, RPG7.this.buffedLvl());
        }

        @Override
        protected void onThrow(int cell) {
            Char enemy = Actor.findChar( cell );
            ArrayList<Char> targets = new ArrayList<>();
            if (Actor.findChar(cell) != null) targets.add(Actor.findChar(cell));
            for (int i : PathFinder.NEIGHBOURS8){
                if (Actor.findChar(cell + i) != null) targets.add(Actor.findChar(cell + i));
            }
            for (Char target : targets){
                curUser.shoot(target, this);
                if (target == hero && !target.isAlive()){
                    Dungeon.fail(getClass());
                    GLog.n(Messages.get(RPG7.class, "ondeath"));
                }
            }
            CellEmitter.get(cell).burst(SmokeParticle.FACTORY, 2);
            CellEmitter.center(cell).burst(BlastParticle.FACTORY, 2);
            boolean terrainAffected = false;
            ArrayList<Char> affected = new ArrayList<>();
            for (int n : PathFinder.NEIGHBOURS9) {
                int c = cell + n;
                if (c >= 0 && c < Dungeon.level.length()) {
                    if (Dungeon.level.heroFOV[c]) {
                        CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
                        CellEmitter.center(cell).burst(BlastParticle.FACTORY, 4);
                    }
                    if (Dungeon.level.flamable[c]) {
                        Dungeon.level.destroy(c);
                        GameScene.updateMap(c);
                        terrainAffected = true;
                    }
                    Char ch = Actor.findChar(c);
                    if (ch != null) {
                        affected.add(ch);
                    }
                }
            }
            Sample.INSTANCE.play( Assets.Sounds.BLAST );
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
            updateQuickslot();
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