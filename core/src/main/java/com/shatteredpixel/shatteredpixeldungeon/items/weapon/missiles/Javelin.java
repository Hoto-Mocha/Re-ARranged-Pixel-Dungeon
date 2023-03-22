/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Ballista;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Javelin extends MissileWeapon {

	{
		image = ItemSpriteSheet.JAVELIN;
		hitSound = Assets.Sounds.HIT_STAB;
		hitSoundPitch = 1f;
		
		tier = 4;
	}

	private static Ballista bow;

	@Override
	public int min(int lvl) {
		if (bow != null) {
			return  4 * tier +
					bow.buffedLvl() +
					2*lvl;
		} else {
			return  2 * tier +
					2*lvl;
		}
	}

	@Override
	public int max(int lvl) {
		if (bow != null) {
			return  7 * tier +
					bow.buffedLvl()*(tier+1) +
					tier*lvl;
		} else {
			return  5 * tier +
					tier*lvl;
		}
	}

	private void updateBallista(){
		if (Dungeon.hero.belongings.weapon() instanceof Ballista){
			bow = (Ballista) Dungeon.hero.belongings.weapon();
		} else if (Dungeon.hero.belongings.secondWep() instanceof Ballista) {
			//player can instant swap anyway, so this is just QoL
			bow = (Ballista) Dungeon.hero.belongings.secondWep();
		} else {
			bow = null;
		}
	}

	public boolean ballistaHasEnchant( Char owner ){
		return (bow != null && bow.enchantment != null && owner.buff(MagicImmune.class) == null);
	}

	@Override
	public boolean hasEnchant(Class<? extends Enchantment> type, Char owner) {
		if (bow != null && bow.hasEnchant(type, owner)){
			return true;
		} else {
			return super.hasEnchant(type, owner);
		}
	}

	@Override
	public int throwPos(Hero user, int dst) {
		updateBallista();
		return super.throwPos(user, dst);
	}

	@Override
	protected void onThrow(int cell) {
		updateBallista();
		//we have to set this here, as on-hit effects can move the target we aim at
		chargedShotPos = cell;
		super.onThrow(cell);
	}

	@Override
	public void throwSound() {
		updateBallista();
		if (bow != null) {
			Sample.INSTANCE.play(Assets.Sounds.ATK_CROSSBOW, 1, Random.Float(0.87f, 1.15f));
		} else {
			super.throwSound();
		}
	}

	@Override
	public String info() {
		updateBallista();
		if (bow != null && !bow.isIdentified()){
			int level = bow.level();
			//temporarily sets the level of the bow to 0 for IDing purposes
			bow.level(0);
			String info = super.info();
			bow.level(level);
			return info;
		} else {
			return super.info();
		}
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		if (bow != null){
			damage = bow.proc(attacker, defender, damage);
		}
		if (!processingChargedShot) {
			processChargedShot(defender, damage);
		}
		return super.proc(attacker, defender, damage);
	}

	@Override
	public float accuracyFactor(Char owner, Char target) {
		//don't update xbow here, as dart is the active weapon atm
		if (bow != null && owner.buff(Ballista.BallistaShot.class) != null){
			return Char.INFINITE_ACCURACY;
		} else {
			return super.accuracyFactor(owner, target);
		}
	}

	private boolean processingChargedShot = false;
	private int chargedShotPos;
	protected void processChargedShot( Char target, int dmg ){
		//don't update xbow here, as dart may be the active weapon atm
		processingChargedShot = true;
		if (chargedShotPos != -1 && (bow != null && Dungeon.hero.buff(Ballista.BallistaShot.class) != null)) {
			PathFinder.buildDistanceMap(chargedShotPos, Dungeon.level.passable, 1);
			for (int i : PathFinder.NEIGHBOURS9){
				int c = chargedShotPos + i;
				if (c >= 0 && c < Dungeon.level.length()) {
					if (Dungeon.level.heroFOV[c]) {
						CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
						CellEmitter.center(chargedShotPos).burst(BlastParticle.FACTORY, 4);
					}
					if (Dungeon.level.flamable[c]) {
						Dungeon.level.destroy(c);
						GameScene.updateMap(c);
					}
				}
			}
			//necessary to clone as some on-hit effects use Pathfinder
			int[] distance = PathFinder.distance.clone();
			for (Char ch : Actor.chars()){
				if (ch == target){
					Actor.add(new Actor() {
						{ actPriority = VFX_PRIO; }
						@Override
						protected boolean act() {
							if (!ch.isAlive()){
								bow.onAbilityKill(Dungeon.hero);
							}
							Actor.remove(this);
							return true;
						}
					});
				} else if (distance[ch.pos] != Integer.MAX_VALUE){
					proc(Dungeon.hero, ch, dmg);
				}
				if (ch != target && distance[ch.pos] != Integer.MAX_VALUE){
					int damage = damageRoll(Dungeon.hero);
					damage -= ch.drRoll();
					ch.damage(damage, Dungeon.hero);
				}
			}
			Sample.INSTANCE.play(Assets.Sounds.BLAST);
		}
		chargedShotPos = -1;
		processingChargedShot = false;
	}

	@Override
	protected void decrementDurability() {
		super.decrementDurability();
		if (Dungeon.hero.buff(Ballista.BallistaShot.class) != null) {
			Dungeon.hero.buff(Ballista.BallistaShot.class).detach();
		}
	}
	
}
