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

package com.shatteredpixel.shatteredpixeldungeon.actors;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.challenges;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Electricity;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AllyBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArcaneArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Berserk;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChampionEnemy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ExtraBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Dread;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Fury;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LanceBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LanceGuard;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LifeLink;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LostInventory;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Momentum;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Preparation;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SerialAttack;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ShieldBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SnipersMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpearGuard;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Speed;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Swing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.ReinforcedArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.gunner.Riot;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.planter.TreasureMap;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.DeathMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.samurai.Awake;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.samurai.ShadowBlade;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.Endure;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Elemental;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.AntiMagic;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Potential;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfElements;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfChallenge;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLightning;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.PoisonBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Blazing;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Blocking;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Shocking;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.BeamSaber;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrower;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrowerAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrowerHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncherAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncherHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Handgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.IronHammer;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Lance;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Magnum;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannonAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannonHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RPG7;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RocketLauncher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SPAS;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SPASAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SPASHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Shovel;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Spade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.ShockingDart;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Door;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.GrimTrap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.Arrays;
import java.util.HashSet;

public abstract class Char extends Actor {
	
	public int pos = 0;
	
	public CharSprite sprite;
	
	public int HT;
	public int HP;
	
	protected float baseSpeed	= 1;
	protected PathFinder.Path path;

	public int paralysed	    = 0;
	public boolean rooted		= false;
	public boolean flying		= false;
	public int invisible		= 0;
	
	//these are relative to the hero
	public enum Alignment{
		ENEMY,
		NEUTRAL,
		ALLY
	}
	public Alignment alignment;
	
	public int viewDistance	= 8;
	
	public boolean[] fieldOfView = null;
	
	private HashSet<Buff> buffs = new HashSet<>();
	
	@Override
	protected boolean act() {
		if (fieldOfView == null || fieldOfView.length != Dungeon.level.length()){
			fieldOfView = new boolean[Dungeon.level.length()];
		}
		Dungeon.level.updateFieldOfView( this, fieldOfView );

		//throw any items that are on top of an immovable char
		if (properties().contains(Property.IMMOVABLE)){
			throwItems();
		}
		return false;
	}

	protected void throwItems(){
		Heap heap = Dungeon.level.heaps.get( pos );
		if (heap != null && heap.type == Heap.Type.HEAP) {
			int n;
			do {
				n = pos + PathFinder.NEIGHBOURS8[Random.Int( 8 )];
			} while (!Dungeon.level.passable[n] && !Dungeon.level.avoid[n]);
			Dungeon.level.drop( heap.pickUp(), n ).sprite.drop( pos );
		}
	}

	public String name(){
		return Messages.get(this, "name");
	}

	public boolean canInteract(Char c){
		if (Dungeon.level.adjacent( pos, c.pos )){
			return true;
		} else if (c instanceof Hero
				&& alignment == Alignment.ALLY
				&& Dungeon.level.distance(pos, c.pos) <= 2*Dungeon.hero.pointsInTalent(Talent.ALLY_WARP)){
			return true;
		} else {
			return false;
		}
	}
	
	//swaps places by default
	public boolean interact(Char c){

		//don't allow char to swap onto hazard unless they're flying
		//you can swap onto a hazard though, as you're not the one instigating the swap
		if (!Dungeon.level.passable[pos] && !c.flying){
			return true;
		}

		//can't swap into a space without room
		if (properties().contains(Property.LARGE) && !Dungeon.level.openSpace[c.pos]
			|| c.properties().contains(Property.LARGE) && !Dungeon.level.openSpace[pos]){
			return true;
		}

		int curPos = pos;

		//warp instantly with allies in this case
		if (c == Dungeon.hero && Dungeon.hero.hasTalent(Talent.ALLY_WARP)){
			PathFinder.buildDistanceMap(c.pos, BArray.or(Dungeon.level.passable, Dungeon.level.avoid, null));
			if (PathFinder.distance[pos] == Integer.MAX_VALUE){
				return true;
			}
			ScrollOfTeleportation.appear(this, c.pos);
			ScrollOfTeleportation.appear(c, curPos);
			Dungeon.observe();
			GameScene.updateFog();
			return true;
		}

		//can't swap places if one char has restricted movement
		if (rooted || c.rooted || buff(Vertigo.class) != null || c.buff(Vertigo.class) != null){
			return true;
		}

		moveSprite( pos, c.pos );
		move( c.pos );
		
		c.sprite.move( c.pos, curPos );
		c.move( curPos );
		
		c.spend( 1 / c.speed() );

		if (c == Dungeon.hero){
			if (Dungeon.hero.subClass == HeroSubClass.FREERUNNER){
				Buff.affect(Dungeon.hero, Momentum.class).gainStack();
			}

			Dungeon.hero.busy();
		}

		if (c == Dungeon.hero){
			if (Dungeon.hero.subClass == HeroSubClass.RANGER && Random.Int(50) == 0){
				Buff.affect(Dungeon.hero, Haste.class,5);
			}

			Dungeon.hero.busy();
		}
		
		return true;
	}
	
	protected boolean moveSprite( int from, int to ) {
		
		if (sprite.isVisible() && (Dungeon.level.heroFOV[from] || Dungeon.level.heroFOV[to])) {
			sprite.move( from, to );
			return true;
		} else {
			sprite.turnTo(from, to);
			sprite.place( to );
			return true;
		}
	}

	public void hitSound( float pitch ){
		Sample.INSTANCE.play(Assets.Sounds.HIT, 1, pitch);
	}

	public boolean blockSound( float pitch ) {
		return false;
	}
	
	protected static final String POS       = "pos";
	protected static final String TAG_HP    = "HP";
	protected static final String TAG_HT    = "HT";
	protected static final String TAG_SHLD  = "SHLD";
	protected static final String BUFFS	    = "buffs";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		
		super.storeInBundle( bundle );
		
		bundle.put( POS, pos );
		bundle.put( TAG_HP, HP );
		bundle.put( TAG_HT, HT );
		bundle.put( BUFFS, buffs );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		
		super.restoreFromBundle( bundle );
		
		pos = bundle.getInt( POS );
		HP = bundle.getInt( TAG_HP );
		HT = bundle.getInt( TAG_HT );
		
		for (Bundlable b : bundle.getCollection( BUFFS )) {
			if (b != null) {
				((Buff)b).attachTo( this );
			}
		}
	}

	final public boolean attack( Char enemy ){
		if (hero.buff(Awake.awakeTracker.class) != null) {
			return attack(enemy, 1.2f+0.2f*hero.pointsInTalent(Talent.AWAKE_LIMIT), 0f, 1f);
		} else
		return attack(enemy, 1f, 0f, 1f);
	}
	
	public boolean attack( Char enemy, float dmgMulti, float dmgBonus, float accMulti ) {

		if (enemy == null) return false;
		
		boolean visibleFight = Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[enemy.pos];

		if (enemy.isInvulnerable(getClass())) {

			if (visibleFight) {
				enemy.sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "invulnerable") );

				Sample.INSTANCE.play(Assets.Sounds.HIT_PARRY, 1f, Random.Float(0.96f, 1.05f));
			}

			return false;

		} else if (hit( this, enemy, accMulti )) {
			
			int dr = enemy.drRoll();

			Barkskin bark = enemy.buff(Barkskin.class);
			if (bark != null)   dr += Random.NormalIntRange( 0 , bark.level() );

			Blocking.BlockBuff block = enemy.buff(Blocking.BlockBuff.class);
			if (block != null)  dr += block.blockingRoll();

			ReinforcedArmor.reinforcedArmorTracker rearmor = enemy.buff(ReinforcedArmor.reinforcedArmorTracker.class);
			if (rearmor != null)  dr += rearmor.blockingRoll();
			
			if (this instanceof Hero){
				Hero h = (Hero)this;
				if (h.belongings.weapon() instanceof MissileWeapon
						&& h.subClass == HeroSubClass.SNIPER
						&& !Dungeon.level.adjacent(h.pos, enemy.pos)){
					dr = 0;
				}
				if (hero.buff(ShadowBlade.shadowBladeTracker.class) != null && Random.Int(2) == 0) {
					if (hero.hasTalent(Talent.CRITICAL_SHADOW)) {
						dmgBonus += Random.NormalIntRange(0, 5*hero.pointsInTalent(Talent.CRITICAL_SHADOW));
					}
					if (hero.hasTalent(Talent.HERBAL_SHADOW)) {
						int healAmt = 2 * hero.pointsInTalent(Talent.HERBAL_SHADOW);
						healAmt = Math.min( healAmt, hero.HT - hero.HP );
						if (healAmt > 0 && hero.isAlive()) {
							hero.HP += healAmt;
							hero.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
							hero.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
						}
					}
					dr = 0;
				}
				else if (h.belongings.weapon() instanceof CrudePistolAP.Bullet
						|| h.belongings.weapon() instanceof PistolAP.Bullet
						|| h.belongings.weapon() instanceof GoldenPistolAP.Bullet
						|| h.belongings.weapon() instanceof HandgunAP.Bullet
						|| h.belongings.weapon() instanceof MagnumAP.Bullet
						|| h.belongings.weapon() instanceof HuntingRifleAP.Bullet
						|| h.belongings.weapon() instanceof SniperRifleAP.Bullet
						|| h.belongings.weapon() instanceof DualPistolAP.Bullet
						|| h.belongings.weapon() instanceof SubMachinegunAP.Bullet
						|| h.belongings.weapon() instanceof AssultRifleAP.Bullet
						|| h.belongings.weapon() instanceof HeavyMachinegunAP.Bullet
						|| h.belongings.weapon() instanceof ShotGunAP.Bullet
						|| h.belongings.weapon() instanceof SPASAP.Bullet
						|| h.belongings.weapon() instanceof MiniGunAP.Bullet
						|| h.belongings.weapon() instanceof LargeHandgunAP.Bullet
						|| h.belongings.weapon() instanceof AntimaterRifleAP.Bullet
				) {
					dr = 0;
				} else if (h.belongings.weapon() instanceof CrudePistolHP.Bullet
						|| h.belongings.weapon() instanceof PistolHP.Bullet
						|| h.belongings.weapon() instanceof GoldenPistolHP.Bullet
						|| h.belongings.weapon() instanceof HandgunHP.Bullet
						|| h.belongings.weapon() instanceof MagnumHP.Bullet
						|| h.belongings.weapon() instanceof HuntingRifleHP.Bullet
						|| h.belongings.weapon() instanceof SniperRifleHP.Bullet
						|| h.belongings.weapon() instanceof DualPistolHP.Bullet
						|| h.belongings.weapon() instanceof SubMachinegunHP.Bullet
						|| h.belongings.weapon() instanceof AssultRifleHP.Bullet
						|| h.belongings.weapon() instanceof HeavyMachinegunHP.Bullet
						|| h.belongings.weapon() instanceof MiniGunHP.Bullet
						|| h.belongings.weapon() instanceof LargeHandgunHP.Bullet
						|| h.belongings.weapon() instanceof AntimaterRifleHP.Bullet
				) {
					dr *= 3;
				} else if (h.belongings.weapon instanceof ShotGunHP.Bullet
						 ||h.belongings.weapon instanceof SPASHP.Bullet) {
					dr *= 2;
				}
			}
			
			int dmg;
			Preparation prep = buff(Preparation.class);
			if (prep != null){
				dmg = prep.damageRoll(this);
				if (this == Dungeon.hero && Dungeon.hero.hasTalent(Talent.BOUNTY_HUNTER)) {
					Buff.affect(Dungeon.hero, Talent.BountyHunterTracker.class, 0.0f);
				}
			} else {
				dmg = damageRoll();
			}

			dmg = Math.round(dmg*dmgMulti);

			Berserk berserk = buff(Berserk.class);
			if (berserk != null) dmg = berserk.damageFactor(dmg);

			if (Dungeon.isChallenged(Challenges.SUPERMAN) && this instanceof Hero) {
				dmg *= 3f;
			}

			if (this instanceof Hero) {
				if (Dungeon.hero.belongings.weapon() instanceof AntimaterRifle.Bullet || Dungeon.hero.belongings.weapon() instanceof AntimaterRifleAP.Bullet || Dungeon.hero.belongings.weapon() instanceof AntimaterRifleHP.Bullet ) {
					int distance = Dungeon.level.distance(hero.pos, enemy.pos) - 1;
					float multiplier = Math.min(3f, 1.2f * (float)Math.pow(1.125f, distance));
					dmg = Math.round(dmg * multiplier);
				}
			}

			if (this instanceof Hero && hero.buff(Swing.class) != null) {
				dmg += 2;
			}

			if (this instanceof Hero && hero.buff(TreasureMap.GoldTracker.class) != null) {
				dmg *= 1 + 0.1f * hero.pointsInTalent(Talent.GOLD_HUNTER);
			}

			if (buff( Fury.class ) != null) {
				dmg *= 1.5f;
			}

			if (this instanceof Hero && hero.buff(LanceBuff.class) != null && hero.belongings.weapon() instanceof Lance) {
				dmg *= 1f + hero.buff(LanceBuff.class).getDamageFactor();
				Buff.detach(this, LanceBuff.class);
			}

			if (this instanceof Hero) {
				if (hero.buff(ShadowBlade.shadowBladeTracker.class) != null) {
					dmg *= 0.5f;
				}
			}

			if (this instanceof Hero && hero.hasTalent(Talent.VINE_WHIP) && hero.belongings.weapon instanceof MeleeWeapon) {
				dmg *= 1-0.2f*hero.pointsInTalent(Talent.VINE_WHIP);
			}

			if (this instanceof Hero && hero.belongings.weapon instanceof IronHammer && Random.Int(20) == 0) {
				Buff.affect(enemy, Paralysis.class, 1f);
			}

			if (this instanceof Hero && hero.belongings.weapon instanceof BeamSaber && Random.Int(40) == 0) {
				Buff.affect(enemy, Blindness.class, 2f);
			}

			if (this instanceof Hero
					&& hero.hasTalent(Talent.TAKEDOWN)
					&& hero.buff(Talent.TakeDownCooldown.class) == null
					&& (hero.belongings.weapon() instanceof Shovel || hero.belongings.weapon() instanceof Spade)) {
				Buff.affect(hero, Talent.TakeDownCooldown.class, 20f);
			}

			if (this instanceof Hero) {
				if (hero.subClass == HeroSubClass.SLASHER) {
					if (hero.buff(SerialAttack.class) != null) {
						dmg *= 1f + 0.05f * hero.buff(SerialAttack.class).getCount();
						Buff.affect(hero, SerialAttack.class).hit(enemy);
					} else {
						dmg *= 1f;
						Buff.affect(hero, SerialAttack.class).hit(enemy);
					}
				}
			}

			if (this instanceof Hero) {
				if (Random.Int(3) < hero.pointsInTalent(Talent.DESTRUCTIVE_BULLET) && hero.buff(Talent.DestructiveCooldown.class) == null) {
					if (Dungeon.hero.belongings.weapon() instanceof CrudePistol.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof CrudePistolAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof CrudePistolHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof Pistol.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof PistolAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof PistolHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof GoldenPistol.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof Handgun.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof HandgunAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof HandgunHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof Magnum.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof MagnumAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof MagnumHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof LargeHandgun.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunHP.Bullet
					) {
						Buff.prolong(enemy, Vulnerable.class, 5f);
						Buff.affect(hero, Talent.DestructiveCooldown.class, 20f);
					}
				}
			}

			if (this instanceof Hero){
				Hero h = (Hero)this;
				if (h.belongings.weapon() instanceof CrudePistolAP.Bullet
				 || h.belongings.weapon() instanceof PistolAP.Bullet
				 || h.belongings.weapon() instanceof GoldenPistolAP.Bullet
				 || h.belongings.weapon() instanceof HandgunAP.Bullet
				 || h.belongings.weapon() instanceof MagnumAP.Bullet
				 || h.belongings.weapon() instanceof HuntingRifleAP.Bullet
				 || h.belongings.weapon() instanceof SniperRifleAP.Bullet
				 || h.belongings.weapon() instanceof DualPistolAP.Bullet
				 || h.belongings.weapon() instanceof SubMachinegunAP.Bullet
				 || h.belongings.weapon() instanceof AssultRifleAP.Bullet
				 || h.belongings.weapon() instanceof HeavyMachinegunAP.Bullet
				 || h.belongings.weapon() instanceof MiniGunAP.Bullet
				 || h.belongings.weapon() instanceof LargeHandgunAP.Bullet
				 || h.belongings.weapon() instanceof AntimaterRifleAP.Bullet
				) {
					dmg *= 0.90f;
				} else if (h.belongings.weapon() instanceof CrudePistolHP.Bullet
					    || h.belongings.weapon() instanceof PistolHP.Bullet
					    || h.belongings.weapon() instanceof GoldenPistolHP.Bullet
					    || h.belongings.weapon() instanceof HandgunHP.Bullet
					    || h.belongings.weapon() instanceof MagnumHP.Bullet
					    || h.belongings.weapon() instanceof HuntingRifleHP.Bullet
					    || h.belongings.weapon() instanceof SniperRifleHP.Bullet
					    || h.belongings.weapon() instanceof DualPistolHP.Bullet
					    || h.belongings.weapon() instanceof SubMachinegunHP.Bullet
					    || h.belongings.weapon() instanceof AssultRifleHP.Bullet
					    || h.belongings.weapon() instanceof HeavyMachinegunHP.Bullet
						|| h.belongings.weapon() instanceof MiniGunHP.Bullet
						|| h.belongings.weapon() instanceof LargeHandgunHP.Bullet
						|| h.belongings.weapon() instanceof AntimaterRifleHP.Bullet
				) {
					dmg *= 1.25f;
				}
			}

			if (this instanceof Hero) {
				float heroHPPercent = ((float)hero.HP / (float)hero.HT);
				if (Dungeon.hero.belongings.weapon() instanceof LargeHandgun.Bullet
						||Dungeon.hero.belongings.weapon() instanceof LargeHandgunAP.Bullet
						||Dungeon.hero.belongings.weapon() instanceof LargeHandgunHP.Bullet ) {
					dmg *= GameMath.gate(0.125f, 2*heroHPPercent, 1.5f); //0%~6.25% HP : 0.125x, scales defend on Hero health, 75%~100% HP : 1.5x
				}
			}


			if (this instanceof Hero) {
				if (Dungeon.hero.buff(ExtraBullet.class) != null) {
					if (Dungeon.hero.belongings.weapon() instanceof CrudePistolAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof PistolAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof HandgunAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof MagnumAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof HuntingRifleAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof SniperRifleAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof DualPistolAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof SubMachinegunAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof AssultRifleAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegunAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof MiniGunAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifleAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof CrudePistolHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof PistolHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof HandgunHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof MagnumHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof HuntingRifleHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof SniperRifleHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof DualPistolHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof SubMachinegunHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof AssultRifleHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegunHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof MiniGunHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifleHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof CrudePistol.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof Pistol.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof GoldenPistol.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof Handgun.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof Magnum.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof HuntingRifle.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof SniperRifle.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof DualPistol.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof SubMachinegun.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof AssultRifle.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegun.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof MiniGun.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof LargeHandgun.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifle.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof RocketLauncher.Rocket
							|| Dungeon.hero.belongings.weapon() instanceof RPG7.Rocket
					) {
						dmg += 3;
					}

				}
			}

			if (Dungeon.hero.buff(Riot.riotTracker.class) != null) {
				if (Dungeon.hero.belongings.weapon() instanceof CrudePistolAP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof PistolAP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolAP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof HandgunAP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof MagnumAP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof HuntingRifleAP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof SniperRifleAP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof DualPistolAP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof SubMachinegunAP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof AssultRifleAP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegunAP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof MiniGunAP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunAP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifleAP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof CrudePistolHP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof PistolHP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolHP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof HandgunHP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof MagnumHP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof HuntingRifleHP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof SniperRifleHP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof DualPistolHP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof SubMachinegunHP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof AssultRifleHP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegunHP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof MiniGunHP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunHP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifleHP.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof CrudePistol.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof Pistol.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof GoldenPistol.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof Handgun.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof Magnum.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof HuntingRifle.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof SniperRifle.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof DualPistol.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof SubMachinegun.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof AssultRifle.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegun.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof MiniGun.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof LargeHandgun.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifle.Bullet
						|| Dungeon.hero.belongings.weapon() instanceof RocketLauncher.Rocket
						|| Dungeon.hero.belongings.weapon() instanceof RPG7.Rocket
				) {
					dmg *= 0.5f;
				}

			}
			if (this instanceof Hero) {
				if (Dungeon.hero.hasTalent(Talent.MELEE_ENHANCE)) {
					if (Dungeon.hero.belongings.weapon() instanceof CrudePistolAP
							|| Dungeon.hero.belongings.weapon() instanceof PistolAP
							|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolAP
							|| Dungeon.hero.belongings.weapon() instanceof HandgunAP
							|| Dungeon.hero.belongings.weapon() instanceof MagnumAP
							|| Dungeon.hero.belongings.weapon() instanceof HuntingRifleAP
							|| Dungeon.hero.belongings.weapon() instanceof SniperRifleAP
							|| Dungeon.hero.belongings.weapon() instanceof DualPistolAP
							|| Dungeon.hero.belongings.weapon() instanceof SubMachinegunAP
							|| Dungeon.hero.belongings.weapon() instanceof AssultRifleAP
							|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegunAP
							|| Dungeon.hero.belongings.weapon() instanceof MiniGunAP
							|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunAP
							|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifleAP
							|| Dungeon.hero.belongings.weapon() instanceof CrudePistolHP
							|| Dungeon.hero.belongings.weapon() instanceof PistolHP
							|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolHP
							|| Dungeon.hero.belongings.weapon() instanceof HandgunHP
							|| Dungeon.hero.belongings.weapon() instanceof MagnumHP
							|| Dungeon.hero.belongings.weapon() instanceof HuntingRifleHP
							|| Dungeon.hero.belongings.weapon() instanceof SniperRifleHP
							|| Dungeon.hero.belongings.weapon() instanceof DualPistolHP
							|| Dungeon.hero.belongings.weapon() instanceof SubMachinegunHP
							|| Dungeon.hero.belongings.weapon() instanceof AssultRifleHP
							|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegunHP
							|| Dungeon.hero.belongings.weapon() instanceof MiniGunHP
							|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunHP
							|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifleHP
							|| Dungeon.hero.belongings.weapon() instanceof CrudePistol
							|| Dungeon.hero.belongings.weapon() instanceof Pistol
							|| Dungeon.hero.belongings.weapon() instanceof GoldenPistol
							|| Dungeon.hero.belongings.weapon() instanceof Handgun
							|| Dungeon.hero.belongings.weapon() instanceof Magnum
							|| Dungeon.hero.belongings.weapon() instanceof HuntingRifle
							|| Dungeon.hero.belongings.weapon() instanceof SniperRifle
							|| Dungeon.hero.belongings.weapon() instanceof DualPistol
							|| Dungeon.hero.belongings.weapon() instanceof SubMachinegun
							|| Dungeon.hero.belongings.weapon() instanceof AssultRifle
							|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegun
							|| Dungeon.hero.belongings.weapon() instanceof MiniGun
							|| Dungeon.hero.belongings.weapon() instanceof LargeHandgun
							|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifle
							|| Dungeon.hero.belongings.weapon() instanceof RocketLauncher
							|| Dungeon.hero.belongings.weapon() instanceof RPG7
							|| Dungeon.hero.belongings.weapon() instanceof FlameThrower
							|| Dungeon.hero.belongings.weapon() instanceof FlameThrowerAP
							|| Dungeon.hero.belongings.weapon() instanceof FlameThrowerHP
					) {
						dmg += 5*Dungeon.hero.pointsInTalent(Talent.MELEE_ENHANCE);
					}
				}
			}

			if (this instanceof Hero) {
				if (hero.heroClass == HeroClass.GUNNER) {
					if (Dungeon.hero.belongings.weapon() instanceof CrudePistolAP
							|| Dungeon.hero.belongings.weapon() instanceof PistolAP
							|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolAP
							|| Dungeon.hero.belongings.weapon() instanceof HandgunAP
							|| Dungeon.hero.belongings.weapon() instanceof MagnumAP
							|| Dungeon.hero.belongings.weapon() instanceof HuntingRifleAP
							|| Dungeon.hero.belongings.weapon() instanceof SniperRifleAP
							|| Dungeon.hero.belongings.weapon() instanceof DualPistolAP
							|| Dungeon.hero.belongings.weapon() instanceof SubMachinegunAP
							|| Dungeon.hero.belongings.weapon() instanceof AssultRifleAP
							|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegunAP
							|| Dungeon.hero.belongings.weapon() instanceof MiniGunAP
							|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunAP
							|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifleAP
							|| Dungeon.hero.belongings.weapon() instanceof CrudePistolHP
							|| Dungeon.hero.belongings.weapon() instanceof PistolHP
							|| Dungeon.hero.belongings.weapon() instanceof GoldenPistolHP
							|| Dungeon.hero.belongings.weapon() instanceof HandgunHP
							|| Dungeon.hero.belongings.weapon() instanceof MagnumHP
							|| Dungeon.hero.belongings.weapon() instanceof HuntingRifleHP
							|| Dungeon.hero.belongings.weapon() instanceof SniperRifleHP
							|| Dungeon.hero.belongings.weapon() instanceof DualPistolHP
							|| Dungeon.hero.belongings.weapon() instanceof SubMachinegunHP
							|| Dungeon.hero.belongings.weapon() instanceof AssultRifleHP
							|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegunHP
							|| Dungeon.hero.belongings.weapon() instanceof MiniGunHP
							|| Dungeon.hero.belongings.weapon() instanceof LargeHandgunHP
							|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifleHP
							|| Dungeon.hero.belongings.weapon() instanceof CrudePistol
							|| Dungeon.hero.belongings.weapon() instanceof Pistol
							|| Dungeon.hero.belongings.weapon() instanceof GoldenPistol
							|| Dungeon.hero.belongings.weapon() instanceof Handgun
							|| Dungeon.hero.belongings.weapon() instanceof Magnum
							|| Dungeon.hero.belongings.weapon() instanceof HuntingRifle
							|| Dungeon.hero.belongings.weapon() instanceof SniperRifle
							|| Dungeon.hero.belongings.weapon() instanceof DualPistol
							|| Dungeon.hero.belongings.weapon() instanceof SubMachinegun
							|| Dungeon.hero.belongings.weapon() instanceof AssultRifle
							|| Dungeon.hero.belongings.weapon() instanceof HeavyMachinegun
							|| Dungeon.hero.belongings.weapon() instanceof MiniGun
							|| Dungeon.hero.belongings.weapon() instanceof LargeHandgun
							|| Dungeon.hero.belongings.weapon() instanceof AntimaterRifle
							|| Dungeon.hero.belongings.weapon() instanceof RocketLauncher
							|| Dungeon.hero.belongings.weapon() instanceof RPG7
							|| Dungeon.hero.belongings.weapon() instanceof FlameThrower
							|| Dungeon.hero.belongings.weapon() instanceof FlameThrowerAP
							|| Dungeon.hero.belongings.weapon() instanceof FlameThrowerHP
							|| Dungeon.hero.belongings.weapon() instanceof PlasmaCannon
							|| Dungeon.hero.belongings.weapon() instanceof PlasmaCannonAP
							|| Dungeon.hero.belongings.weapon() instanceof PlasmaCannonHP
					) {
						dmg += Random.NormalIntRange(0, hero.belongings.weapon.buffedLvl());
					}
				}
			}

			if (this instanceof Hero) {
				if (Dungeon.hero.hasTalent(Talent.HEAVY_ENHANCE)) {
					if (Dungeon.hero.belongings.weapon() instanceof RocketLauncher.Rocket
							|| Dungeon.hero.belongings.weapon() instanceof RPG7.Rocket
							|| Dungeon.hero.belongings.weapon() instanceof ShotGun.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof ShotGunAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof ShotGunHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof SPAS.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof SPASAP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof SPASHP.Bullet
							|| Dungeon.hero.belongings.weapon() instanceof GrenadeLauncher.Rocket
							|| Dungeon.hero.belongings.weapon() instanceof GrenadeLauncherAP.Rocket
							|| Dungeon.hero.belongings.weapon() instanceof GrenadeLauncherHP.Rocket
					) {
						dmg *= 1f + 0.05f*Dungeon.hero.pointsInTalent(Talent.HEAVY_ENHANCE);
					}
				}
			}

			if (Dungeon.hero.hasTalent(Talent.ONLY_ONE_SHOT)) {
				if (Dungeon.hero.belongings.weapon() instanceof HuntingRifle.Bullet
				 || Dungeon.hero.belongings.weapon() instanceof HuntingRifleAP.Bullet
				 || Dungeon.hero.belongings.weapon() instanceof HuntingRifleHP.Bullet
				 || Dungeon.hero.belongings.weapon() instanceof SniperRifle.Bullet
				 || Dungeon.hero.belongings.weapon() instanceof SniperRifleAP.Bullet
				 || Dungeon.hero.belongings.weapon() instanceof SniperRifleHP.Bullet
				 || Dungeon.hero.belongings.weapon() instanceof AntimaterRifle.Bullet
				 || Dungeon.hero.belongings.weapon() instanceof AntimaterRifleAP.Bullet
				 || Dungeon.hero.belongings.weapon() instanceof AntimaterRifleHP.Bullet
				) {
					dmg *= 1f + 0.1f*Dungeon.hero.pointsInTalent(Talent.ONLY_ONE_SHOT);
				}
			}

			if ((Dungeon.hero.buff(SpearGuard.class) != null) || (Dungeon.hero.buff(LanceGuard.class) != null)) {
				dmg *= 0.4f;
			}

			dmg += dmgBonus;

			//friendly endure
			Endure.EndureTracker endure = buff(Endure.EndureTracker.class);
			if (endure != null) dmg = endure.damageFactor(dmg);

			//enemy endure
			endure = enemy.buff(Endure.EndureTracker.class);
			if (endure != null){
				dmg = endure.adjustDamageTaken(dmg);
			}

			if (enemy.buff(ScrollOfChallenge.ChallengeArena.class) != null){
				dmg *= 0.67f;
			}
			
			int effectiveDamage = enemy.defenseProc( this, dmg );
			effectiveDamage = Math.max( effectiveDamage - dr, 0 );
			
			if ( enemy.buff( Vulnerable.class ) != null){
				effectiveDamage *= 1.33f;
			}
			
			effectiveDamage = attackProc( enemy, effectiveDamage );
			
			if (visibleFight) {
				if (effectiveDamage > 0 || !enemy.blockSound(Random.Float(0.96f, 1.05f))) {
					hitSound(Random.Float(0.87f, 1.15f));
				}
			}

			// If the enemy is already dead, interrupt the attack.
			// This matters as defence procs can sometimes inflict self-damage, such as armor glyphs.
			if (!enemy.isAlive()){
				return true;
			}

			enemy.damage( effectiveDamage, this );

			if (buff(FireImbue.class) != null)  buff(FireImbue.class).proc(enemy);
			if (buff(FrostImbue.class) != null) buff(FrostImbue.class).proc(enemy);

			if (enemy.isAlive() && prep != null && prep.canKO(enemy)){
				enemy.HP = 0;
				if (!enemy.isAlive()) {
					enemy.die(this);
				} else {
					//helps with triggering any on-damage effects that need to activate
					enemy.damage(-1, this);
					DeathMark.processFearTheReaper(enemy);
				}
				enemy.sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Preparation.class, "assassinated"));
			}

			enemy.sprite.bloodBurstA( sprite.center(), effectiveDamage );
			enemy.sprite.flash();

			if (!enemy.isAlive() && visibleFight) {
				if (enemy == Dungeon.hero) {
					
					if (this == Dungeon.hero) {
						return true;
					}

					Dungeon.fail( getClass() );
					GLog.n( Messages.capitalize(Messages.get(Char.class, "kill", name())) );
					
				} else if (this == Dungeon.hero) {
					GLog.i( Messages.capitalize(Messages.get(Char.class, "defeat", enemy.name())) );
				}
			}
			
			return true;
			
		} else {

			enemy.sprite.showStatus( CharSprite.NEUTRAL, enemy.defenseVerb() );
			if (visibleFight) {
				//TODO enemy.defenseSound? currently miss plays for monks/crab even when they parry
				Sample.INSTANCE.play(Assets.Sounds.MISS);
			}
			
			return false;
			
		}
	}

	public static int INFINITE_ACCURACY = 1_000_000;
	public static int INFINITE_EVASION = 1_000_000;

	final public static boolean hit( Char attacker, Char defender, boolean magic ) {
		return hit(attacker, defender, magic ? 2f : 1f);
	}

	public static boolean hit( Char attacker, Char defender, float accMulti ) {
		float acuStat = attacker.attackSkill( defender );
		float defStat = defender.defenseSkill( attacker );

		//if accuracy or evasion are large enough, treat them as infinite.
		//note that infinite evasion beats infinite accuracy
		if (defStat >= INFINITE_EVASION){
			return false;
		} else if (acuStat >= INFINITE_ACCURACY){
			return true;
		}

		float acuRoll = Random.Float( acuStat );
		if (attacker.buff(Bless.class) != null) acuRoll *= 1.25f;
		if (attacker.buff(  Hex.class) != null) acuRoll *= 0.8f;
		for (ChampionEnemy buff : attacker.buffs(ChampionEnemy.class)){
			acuRoll *= buff.evasionAndAccuracyFactor();
		}
		
		float defRoll = Random.Float( defStat );
		if (defender.buff(Bless.class) != null) defRoll *= 1.25f;
		if (defender.buff(  Hex.class) != null) defRoll *= 0.8f;
		for (ChampionEnemy buff : defender.buffs(ChampionEnemy.class)){
			defRoll *= buff.evasionAndAccuracyFactor();
		}
		
		return (acuRoll * accMulti) >= defRoll;
	}
	
	public int attackSkill( Char target ) {
		return 0;
	}
	
	public int defenseSkill( Char enemy ) {
		return 0;
	}
	
	public String defenseVerb() {
		return Messages.get(this, "def_verb");
	}
	
	public int drRoll() {
		return 0;
	}
	
	public int damageRoll() {
		return 1;
	}
	
	//TODO it would be nice to have a pre-armor and post-armor proc.
	// atm attack is always post-armor and defence is already pre-armor
	
	public int attackProc( Char enemy, int damage ) {
		if ( buff(Weakness.class) != null ){
			damage *= 0.67f;
		}
		for (ChampionEnemy buff : buffs(ChampionEnemy.class)){
			damage *= buff.meleeDamageFactor();
			buff.onAttackProc( enemy );
		}
		return damage;
	}
	
	public int defenseProc( Char enemy, int damage ) {
		return damage;
	}
	
	public float speed() {
		float speed = baseSpeed;
		if ( buff( Cripple.class ) != null ) speed /= 2f;
		if ( buff( Stamina.class ) != null) speed *= 1.5f;
		if ( buff( Adrenaline.class ) != null) speed *= 2f;
		if ( buff( Haste.class ) != null) speed *= 3f;
		if ( buff( Dread.class ) != null) speed *= 2f;
		return speed;
	}
	
	//used so that buffs(Shieldbuff.class) isn't called every time unnecessarily
	private int cachedShield = 0;
	public boolean needsShieldUpdate = true;
	
	public int shielding(){
		if (!needsShieldUpdate){
			return cachedShield;
		}
		
		cachedShield = 0;
		for (ShieldBuff s : buffs(ShieldBuff.class)){
			cachedShield += s.shielding();
		}
		needsShieldUpdate = false;
		return cachedShield;
	}
	
	public void damage( int dmg, Object src ) {
		
		if (!isAlive() || dmg < 0) {
			return;
		}

		if(isInvulnerable(src.getClass())){
			sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "invulnerable"));
			return;
		}

		for (ChampionEnemy buff : buffs(ChampionEnemy.class)){
			dmg = (int) Math.ceil(dmg * buff.damageTakenFactor());
		}

		if (!(src instanceof LifeLink) && buff(LifeLink.class) != null){
			HashSet<LifeLink> links = buffs(LifeLink.class);
			for (LifeLink link : links.toArray(new LifeLink[0])){
				if (Actor.findById(link.object) == null){
					links.remove(link);
					link.detach();
				}
			}
			dmg = (int)Math.ceil(dmg / (float)(links.size()+1));
			for (LifeLink link : links){
				Char ch = (Char)Actor.findById(link.object);
				ch.damage(dmg, link);
				if (!ch.isAlive()){
					link.detach();
				}
			}
		}

		Terror t = buff(Terror.class);
		if (t != null){
			t.recover();
		}
		Dread d = buff(Dread.class);
		if (d != null){
			d.recover();
		}
		Charm c = buff(Charm.class);
		if (c != null){
			c.recover(src);
		}
		if (this.buff(Frost.class) != null){
			Buff.detach( this, Frost.class );
		}
		if (this.buff(MagicalSleep.class) != null){
			Buff.detach(this, MagicalSleep.class);
		}
		if (this.buff(Doom.class) != null && !isImmune(Doom.class)){
			dmg *= 2;
		}
		if (alignment != Alignment.ALLY && this.buff(DeathMark.DeathMarkTracker.class) != null){
			dmg *= 1.25f;
		}
		
		Class<?> srcClass = src.getClass();
		if (isImmune( srcClass )) {
			dmg = 0;
		} else {
			dmg = Math.round( dmg * resist( srcClass ));
		}
		
		//TODO improve this when I have proper damage source logic
		if (AntiMagic.RESISTS.contains(src.getClass()) && buff(ArcaneArmor.class) != null){
			dmg -= Random.NormalIntRange(0, buff(ArcaneArmor.class).level());
			if (dmg < 0) dmg = 0;
		}
		
		if (buff( Paralysis.class ) != null) {
			buff( Paralysis.class ).processDamage(dmg);
		}

		Endure.EndureTracker endure = buff(Endure.EndureTracker.class);
		if (endure != null){
			dmg = endure.enforceDamagetakenLimit(dmg);
		}

		int shielded = dmg;
		//FIXME: when I add proper damage properties, should add an IGNORES_SHIELDS property to use here.
		if (!(src instanceof Hunger)){
			for (ShieldBuff s : buffs(ShieldBuff.class)){
				dmg = s.absorbDamage(dmg);
				if (dmg == 0) break;
			}
		}
		shielded -= dmg;
		HP -= dmg;
		
		if (sprite != null) {
			sprite.showStatus(HP > HT / 2 ?
							CharSprite.WARNING :
							CharSprite.NEGATIVE,
					Integer.toString(dmg + shielded));
		}

		if (HP < 0) HP = 0;

		if (!isAlive()) {
			die( src );
		} else if (HP == 0 && buff(DeathMark.DeathMarkTracker.class) != null){
			DeathMark.processFearTheReaper(this);
		}
	}
	
	public void destroy() {
		HP = 0;
		Actor.remove( this );

		for (Char ch : Actor.chars().toArray(new Char[0])){
			if (ch.buff(Charm.class) != null && ch.buff(Charm.class).object == id()){
				ch.buff(Charm.class).detach();
			}
			if (ch.buff(Dread.class) != null && ch.buff(Dread.class).object == id()){
				ch.buff(Dread.class).detach();
			}
			if (ch.buff(Terror.class) != null && ch.buff(Terror.class).object == id()){
				ch.buff(Terror.class).detach();
			}
			if (ch.buff(SnipersMark.class) != null && ch.buff(SnipersMark.class).object == id()){
				ch.buff(SnipersMark.class).detach();
			}
		}
	}
	
	public void die( Object src ) {
		destroy();
		if (src != Chasm.class) sprite.die();
	}

	//we cache this info to prevent having to call buff(...) in isAlive.
	//This is relevant because we call isAlive during drawing, which has both performance
	//and thread coordination implications
	public boolean deathMarked = false;
	
	public boolean isAlive() {
		return HP > 0 || deathMarked;
	}
	
	@Override
	protected void spend( float time ) {
		
		float timeScale = 1f;
		if (buff( Slow.class ) != null) {
			timeScale *= 0.5f;
			//slowed and chilled do not stack
		} else if (buff( Chill.class ) != null) {
			timeScale *= buff( Chill.class ).speedFactor();
		}
		if (buff( Speed.class ) != null) {
			timeScale *= 2.0f;
		}
		
		super.spend( time / timeScale );
	}
	
	public synchronized HashSet<Buff> buffs() {
		return new HashSet<>(buffs);
	}
	
	@SuppressWarnings("unchecked")
	//returns all buffs assignable from the given buff class
	public synchronized <T extends Buff> HashSet<T> buffs( Class<T> c ) {
		HashSet<T> filtered = new HashSet<>();
		for (Buff b : buffs) {
			if (c.isInstance( b )) {
				filtered.add( (T)b );
			}
		}
		return filtered;
	}

	@SuppressWarnings("unchecked")
	//returns an instance of the specific buff class, if it exists. Not just assignable
	public synchronized  <T extends Buff> T buff( Class<T> c ) {
		for (Buff b : buffs) {
			if (b.getClass() == c) {
				return (T)b;
			}
		}
		return null;
	}

	public synchronized boolean isCharmedBy( Char ch ) {
		int chID = ch.id();
		for (Buff b : buffs) {
			if (b instanceof Charm && ((Charm)b).object == chID) {
				return true;
			}
		}
		return false;
	}

	public synchronized void add( Buff buff ) {

		if (buff(PotionOfCleansing.Cleanse.class) != null) { //cleansing buff
			if (buff.type == Buff.buffType.NEGATIVE
					&& !(buff instanceof AllyBuff)
					&& !(buff instanceof LostInventory)){
				return;
			}
		}

		buffs.add( buff );
		if (Actor.chars().contains(this)) Actor.add( buff );

		if (sprite != null && buff.announced)
			switch(buff.type){
				case POSITIVE:
					sprite.showStatus(CharSprite.POSITIVE, buff.toString());
					break;
				case NEGATIVE:
					sprite.showStatus(CharSprite.NEGATIVE, buff.toString());
					break;
				case NEUTRAL: default:
					sprite.showStatus(CharSprite.NEUTRAL, buff.toString());
					break;
			}

	}
	
	public synchronized void remove( Buff buff ) {
		
		buffs.remove( buff );
		Actor.remove( buff );

	}
	
	public synchronized void remove( Class<? extends Buff> buffClass ) {
		for (Buff buff : buffs( buffClass )) {
			remove( buff );
		}
	}
	
	@Override
	protected synchronized void onRemove() {
		for (Buff buff : buffs.toArray(new Buff[buffs.size()])) {
			buff.detach();
		}
	}
	
	public synchronized void updateSpriteState() {
		for (Buff buff:buffs) {
			buff.fx( true );
		}
	}
	
	public float stealth() {
		return 0;
	}

	public final void move( int step ) {
		move( step, true );
	}

	//travelling may be false when a character is moving instantaneously, such as via teleportation
	public void move( int step, boolean travelling ) {

		if (travelling && Dungeon.level.adjacent( step, pos ) && buff( Vertigo.class ) != null) {
			sprite.interruptMotion();
			int newPos = pos + PathFinder.NEIGHBOURS8[Random.Int( 8 )];
			if (!(Dungeon.level.passable[newPos] || Dungeon.level.avoid[newPos])
					|| (properties().contains(Property.LARGE) && !Dungeon.level.openSpace[newPos])
					|| Actor.findChar( newPos ) != null)
				return;
			else {
				sprite.move(pos, newPos);
				step = newPos;
			}
		}

		if (Dungeon.level.map[pos] == Terrain.OPEN_DOOR) {
			Door.leave( pos );
		}

		pos = step;
		
		if (this != Dungeon.hero) {
			sprite.visible = Dungeon.level.heroFOV[pos];
		}
		
		Dungeon.level.occupyCell(this );
	}
	
	public int distance( Char other ) {
		return Dungeon.level.distance( pos, other.pos );
	}
	
	public void onMotionComplete() {
		//Does nothing by default
		//The main actor thread already accounts for motion,
		// so calling next() here isn't necessary (see Actor.process)
	}
	
	public void onAttackComplete() {
		next();
	}
	
	public void onOperateComplete() {
		next();
	}
	
	protected final HashSet<Class> resistances = new HashSet<>();
	
	//returns percent effectiveness after resistances
	//TODO currently resistances reduce effectiveness by a static 50%, and do not stack.
	public float resist( Class effect ){
		HashSet<Class> resists = new HashSet<>(resistances);
		for (Property p : properties()){
			resists.addAll(p.resistances());
		}
		for (Buff b : buffs()){
			resists.addAll(b.resistances());
		}
		
		float result = 1f;
		for (Class c : resists){
			if (c.isAssignableFrom(effect)){
				result *= 0.5f;
			}
		}
		return result * RingOfElements.resist(this, effect);
	}
	
	protected final HashSet<Class> immunities = new HashSet<>();
	
	public boolean isImmune(Class effect ){
		HashSet<Class> immunes = new HashSet<>(immunities);
		for (Property p : properties()){
			immunes.addAll(p.immunities());
		}
		for (Buff b : buffs()){
			immunes.addAll(b.immunities());
		}
		
		for (Class c : immunes){
			if (c.isAssignableFrom(effect)){
				return true;
			}
		}
		return false;
	}

	//similar to isImmune, but only factors in damage.
	//Is used in AI decision-making
	public boolean isInvulnerable( Class effect ){
		return false;
	}

	protected HashSet<Property> properties = new HashSet<>();

	public HashSet<Property> properties() {
		HashSet<Property> props = new HashSet<>(properties);
		//TODO any more of these and we should make it a property of the buff, like with resistances/immunities
		if (buff(ChampionEnemy.Giant.class) != null) {
			props.add(Property.LARGE);
		}
		return props;
	}

	public enum Property{
		BOSS ( new HashSet<Class>( Arrays.asList(Grim.class, GrimTrap.class, ScrollOfRetribution.class, ScrollOfPsionicBlast.class)),
				new HashSet<Class>( Arrays.asList(AllyBuff.class, Dread.class) )),
		MINIBOSS ( new HashSet<Class>(),
				new HashSet<Class>( Arrays.asList(AllyBuff.class, Dread.class) )),
		UNDEAD,
		DEMONIC,
		INORGANIC ( new HashSet<Class>(),
				new HashSet<Class>( Arrays.asList(Bleeding.class, ToxicGas.class, Poison.class) )),
		FIERY ( new HashSet<Class>( Arrays.asList(WandOfFireblast.class, Elemental.FireElemental.class)),
				new HashSet<Class>( Arrays.asList(Burning.class, Blazing.class))),
		ICY ( new HashSet<Class>( Arrays.asList(WandOfFrost.class, Elemental.FrostElemental.class)),
				new HashSet<Class>( Arrays.asList(Frost.class, Chill.class))),
		ACIDIC ( new HashSet<Class>( Arrays.asList(Corrosion.class)),
				new HashSet<Class>( Arrays.asList(Ooze.class))),
		ELECTRIC ( new HashSet<Class>( Arrays.asList(WandOfLightning.class, Shocking.class, Potential.class, Electricity.class, ShockingDart.class, Elemental.ShockElemental.class )),
				new HashSet<Class>()),
		LARGE,
		IMMOVABLE;
		
		private HashSet<Class> resistances;
		private HashSet<Class> immunities;
		
		Property(){
			this(new HashSet<Class>(), new HashSet<Class>());
		}
		
		Property( HashSet<Class> resistances, HashSet<Class> immunities){
			this.resistances = resistances;
			this.immunities = immunities;
		}
		
		public HashSet<Class> resistances(){
			return new HashSet<>(resistances);
		}
		
		public HashSet<Class> immunities(){
			return new HashSet<>(immunities);
		}

	}

	public static boolean hasProp( Char ch, Property p){
		return (ch != null && ch.properties().contains(p));
	}
}
