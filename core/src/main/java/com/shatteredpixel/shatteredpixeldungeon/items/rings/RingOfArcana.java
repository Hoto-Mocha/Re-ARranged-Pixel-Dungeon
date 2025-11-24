/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2025 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items.rings;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AllyBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PoisonParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Blocking;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Blooming;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Kinetic;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Lucky;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Shocking;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class RingOfArcana extends Ring {

	{
		icon = ItemSpriteSheet.Icons.RING_ARCANA;
		buffClass = Arcana.class;
	}

	public String statsInfo() {
		if (isIdentified()){
			String info = Messages.get(this, "stats",
					Messages.decimalFormat("#.##", 100f * (Math.pow(1.175f, soloBuffedBonus()) - 1f)));
			if (isEquipped(Dungeon.hero) && soloBuffedBonus() != combinedBuffedBonus(Dungeon.hero)){
				info += "\n\n" + Messages.get(this, "combined_stats",
						Messages.decimalFormat("#.##", 100f * (Math.pow(1.175f, combinedBuffedBonus(Dungeon.hero)) - 1f)));
			}
			return info;
		} else {
			return Messages.get(this, "typical_stats", Messages.decimalFormat("#.##", 17.5f));
		}
	}

	public String upgradeStat1(int level){
		if (cursed) level = Math.min(-1, level-3);
		return Messages.decimalFormat("#.##", 100f * (Math.pow(1.175f, level+1)-1f)) + "%";
	}

	@Override
	public RingBuff buff() {
		return new Arcana();
	}

	public static float enchantPowerMultiplier(Char target ){
		return (float)Math.pow(1.175f, getBuffedBonus(target, Arcana.class));
	}

	public class Arcana extends RingBuff {
	}

    @Override
    public int onHit(Hero hero, Char enemy, int damage) {
        int level = (int)Math.ceil(hero.lvl / 5f);
        float powerMulti = Math.max(1f, RingOfArcana.enchantPowerMultiplier(hero));
        float procChance;
        switch (Random.Int(15)) {
            case 0: default:
                //Blazing Effect
                procChance = (level+1f)/(level+3f) * powerMulti;
                if (Random.Float() < procChance) {
                    if (enemy.buff(Burning.class) != null){
                        Buff.affect(enemy, Burning.class).reignite(enemy, 8f);
                        int burnDamage = Random.NormalIntRange( 1, 3 + Dungeon.scalingDepth()/4 );
                        enemy.damage( Math.round(burnDamage * 0.67f * powerMulti), hero );
                    } else {
                        Buff.affect(enemy, Burning.class).reignite(enemy, 8f);
                    }

                    enemy.sprite.emitter().burst( FlameParticle.FACTORY, level + 1 );
                }
                return damage;
            case 1:
                //Blocking Effect
                procChance = (level+4f)/(level+40f) * powerMulti;

                if (Random.Float() < procChance){
                    float powerLeft = Math.max(1f, procChance);

                    Blocking.BlockBuff b = Buff.affect(hero, Blocking.BlockBuff.class);
                    b.setShield(Math.round(powerLeft * (2 + level)));
                    hero.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 5);
                }
                return damage;
            case 2:
                //Blooming Effect
                procChance = (level+1f)/(level+3f) * powerMulti;
                if (Random.Float() < procChance) {
                    float plants = (1f + 0.1f*level) * powerMulti;
                    if (Random.Float() < plants%1){
                        plants = (float)Math.ceil(plants);
                    } else {
                        plants = (float)Math.floor(plants);
                    }

                    Blooming blooming = new Blooming();
                    if (blooming.plantGrass(enemy.pos)){
                        plants--;
                        if (plants <= 0){
                            return damage;
                        }
                    }

                    ArrayList<Integer> positions = new ArrayList<>();
                    for (int i : PathFinder.NEIGHBOURS8){
                        if (enemy.pos + i != hero.pos) {
                            positions.add(enemy.pos + i);
                        }
                    }
                    Random.shuffle( positions );

                    //The attacker's position is always lowest priority
                    if (Dungeon.level.adjacent(hero.pos, enemy.pos)){
                        positions.add(hero.pos);
                    }

                    for (int i : positions){
                        if (blooming.plantGrass(i)){
                            plants--;
                            if (plants <= 0) {
                                return damage;
                            }
                        }
                    }
                }
                return damage;
            case 3:
                //Chilling Effect
                procChance = (level+1f)/(level+4f) * powerMulti;
                if (Random.Float() < procChance) {
                    float durationToAdd = 3f * powerMulti;
                    Chill existing = enemy.buff(Chill.class);
                    if (existing != null){
                        durationToAdd = Math.min(durationToAdd, (6f*powerMulti)-existing.cooldown());
                    }

                    Buff.affect( enemy, Chill.class, durationToAdd );
                    Splash.at( enemy.sprite.center(), 0xFFB2D6FF, 5);
                }
                return damage;
            case 4:
                //Kinetic Effect, See RingOfForce.damageRoll too
                int conservedDamage = 0;
                if (hero.buff(Kinetic.ConservedDamage.class) != null) {
                    conservedDamage = hero.buff(Kinetic.ConservedDamage.class).damageBonus();
                    hero.buff(Kinetic.ConservedDamage.class).detach();
                }

                Buff.affect(hero, Kinetic.KineticTracker.class).conservedDamage = conservedDamage;
                return damage;
            case 5:
                //Corrupting Effect
                procChance = (level+5f)/(level+25f) * powerMulti;
                if (damage >= enemy.HP
                        && Random.Float() < procChance
                        && !enemy.isImmune(Corruption.class)
                        && enemy.buff(Corruption.class) == null
                        && enemy instanceof Mob
                        && enemy.isAlive()){
                    Corruption.corruptionHeal(enemy);

                    AllyBuff.affectAndLoot((Mob)enemy, hero, Corruption.class);

                    float powerLeft = Math.max(1f, procChance);
                    if (powerLeft > 1.1f){
                        //1 turn of adrenaline for each 20% above 100% proc rate
                        Buff.affect(enemy, Adrenaline.class, Math.round(5*(powerLeft-1f)));
                    }

                    return 0;
                }
                return damage;
            case 6:
                //Elastic Effect
                procChance = (level+1f)/(level+5f) * powerMulti;
                if (Random.Float() < procChance) {
                    //trace a ballistica to our target (which will also extend past them
                    Ballistica trajectory = new Ballistica(hero.pos, enemy.pos, Ballistica.STOP_TARGET);
                    //trim it to just be the part that goes past them
                    trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size()-1), Ballistica.PROJECTILE);
                    //knock them back along that ballistica
                    WandOfBlastWave.throwChar(enemy,
                            trajectory,
                            Math.round(2 * powerMulti),
                            true,
                            true,
                            hero);
                }
                return damage;
            case 7:
                //Grim Effect
                if (enemy.isImmune(Grim.class)) {
                    return damage;
                }
                level = Math.max( 0, level );
                //scales from 0 - 50% based on how low hp the enemy is, plus 0-5% per level
                float maxChance = 0.5f + .05f*level;
                maxChance *= powerMulti;

                //we defer logic using an actor here so we can know the true final damage
                //see Char.damage
                Buff.affect(enemy, Grim.GrimTracker.class).maxChance = maxChance;

                if (enemy.buff(Grim.GrimTracker.class) != null){
                    enemy.buff(Grim.GrimTracker.class).qualifiesForBadge = true;
                }
                return damage;
            case 8:
                //Lucky Effect
                procChance = (level+4f)/(level+40f) * powerMulti;
                if (enemy.HP <= damage && Random.Float() < procChance){

                    float powerLeft = Math.max(1f, procChance);

                    //default is -5: 80% common, 20% uncommon, 0% rare
                    //ring level increases by 1 for each 20% above 100% proc rate
                    Buff.affect(enemy, Lucky.LuckProc.class).ringLevel = -10 + Math.round(5*powerLeft);
                }
                return damage;
            case 9:
                //Shocking Effect
                procChance = (level+1f)/(level+4f) * powerMulti;
                if (Random.Float() < procChance) {
                    ArrayList<Lightning.Arc> arcs = new ArrayList<>();
                    ArrayList<Char> affected = new ArrayList<>();
                    affected.clear();
                    arcs.clear();

                    Shocking.arc(hero, enemy, 2, affected, arcs);

                    affected.remove(enemy); //defender isn't hurt by lightning
                    for (Char ch : affected) {
                        if (ch.alignment != hero.alignment) {
                            ch.damage(Math.round(damage * 0.4f * powerMulti), hero);
                        }
                    }
                }
                return damage;
            case 10:
                //Vampiric Effect
                float missingPercent = (hero.HT - hero.HP) / (float)hero.HT;
                float healChance = 0.05f + .25f*missingPercent;

                healChance *= powerMulti;

                if (Random.Float() < healChance){

                    float powerLeft = Math.max(1f, healChance);

                    //heals for 50% of damage dealt
                    int healAmt = Math.round(damage * 0.5f * powerLeft);
                    hero.heal(healAmt);
                }
                return damage;
            case 11:
                //Eldritch Effect
                Buff.prolong( enemy, Terror.class, 10f*powerMulti + 5f ).object = hero.id();
                return damage;
            case 12:
                //Stunning Effect
                Buff.prolong( enemy, Paralysis.class, 1f * powerMulti );
                enemy.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 12 );
                return damage;
            case 13:
                //Venomous Effect
                Buff.affect( enemy, Poison.class ).extend( (level/2f + 1) * powerMulti );
                CellEmitter.center(enemy.pos).burst( PoisonParticle.SPLASH, 5 );
                return damage;
            case 14:
                //Vorpal Effect
                Buff.affect(enemy, Bleeding.class).set((damage/10f) * powerMulti);
                Splash.at( enemy.sprite.center(), -PointF.PI / 2, PointF.PI / 6, enemy.sprite.blood(), 10 );
                return damage;
        }
    }
}
