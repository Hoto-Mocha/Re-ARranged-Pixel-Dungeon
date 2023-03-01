/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2022 Evan Debenham
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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ElectroBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EvasionEnhance;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.AmmoBelt;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfReload;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class NormalGun extends Gun {
    {
        //max_round = 3;
        //if (magazine) {
        //    max_round += 1;
        //}
        //if (Dungeon.hero.hasTalent(Talent.LARGER_MAGAZINE)) {
        //    max_round += Dungeon.hero.pointsInTalent(Talent.LARGER_MAGAZINE);
        //}
        reload_time = 2f * RingOfReload.reloadMultiplier(Dungeon.hero);
    }

    @Override
    public int Bulletmin(int lvl) {
        return 2 * tier +
                lvl      +
                RingOfSharpshooting.levelDamageBonus(hero);
    }

    @Override
    public int Bulletmax(int lvl) {
        return 4 * (tier+1)   +
                lvl * (tier+1)  +
                RingOfSharpshooting.levelDamageBonus(hero);
    }

    @Override
    public String status() {
        max_round = (magazine) ? 4 : 3;
        return Messages.format(TXT_STATUS, round, max_round);
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
                    if (hero.hasTalent(Talent.ELEMENTAL_BULLET)) {
                        elementReload();
                    } else {
                        reload();
                    }
                } else {
                    reload_time = 2f * RingOfReload.reloadMultiplier(Dungeon.hero);
                    usesTargeting = true;
                    curUser = hero;
                    curItem = this;
                    GameScene.selectCell(shooter);
                }
            }
        }
        if (action.equals(AC_RELOAD)) {
            if (round == max_round){
                GLog.w(Messages.get(this, "already_loaded"));
            } else if (round == 0 && hero.hasTalent(Talent.ELEMENTAL_BULLET)){
                elementReload();
            } else {
                reload();
            }
        }
    }

    @Override
    public void reload() {
        Buff.detach(hero, FrostBullet.class);
        Buff.detach(hero, FireBullet.class);
        Buff.detach(hero, ElectroBullet.class);
        if (Dungeon.hero.hasTalent(Talent.LARGER_MAGAZINE)) {
            max_round += Dungeon.hero.pointsInTalent(Talent.LARGER_MAGAZINE);
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

        super.reload();
    }

    public CellSelector.Listener shooter = new CellSelector.Listener() {
        @Override
        public void onSelect( Integer target ) {
            AmmoBelt.OverHeat overHeat = hero.buff(AmmoBelt.OverHeat.class);
            if (target != null) {
                if (overHeat != null && Random.Float() < AmmoBelt.OverHeat.chance) {
                    usesTargeting = false;
                    GLog.w(Messages.get(Gun.class, "failed"));
                    curUser.spendAndNext(Actor.TICK);
                } else {
                    if (target == curUser.pos) {
                        reload();
                    } else {
                        knockBullet().cast(curUser, target);
                    }
                }
            }
        }
        @Override
        public String prompt() {
            return Messages.get(SpiritBow.class, "prompt");
        }
    };
}
