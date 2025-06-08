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

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AllyBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Daze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Enduring;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EnhancedRings;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EnhancedRingsCombo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EvasiveMove;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LostInventory;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PinCushion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells.SpiritForm;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PoisonParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.ItemStatusHandler;
import com.shatteredpixel.shatteredpixeldungeon.items.KindofMisc;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.TelekineticGrab;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Blocking;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Blooming;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Kinetic;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Lucky;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Shocking;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.items.trinkets.ShardOfOblivion;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Ring extends KindofMisc {
	
	protected Buff buff;
	protected Class<? extends RingBuff> buffClass;

	private static final LinkedHashMap<String, Integer> gems = new LinkedHashMap<String, Integer>() {
		{
			put("garnet",ItemSpriteSheet.RING_GARNET);
			put("ruby",ItemSpriteSheet.RING_RUBY);
			put("topaz",ItemSpriteSheet.RING_TOPAZ);
			put("emerald",ItemSpriteSheet.RING_EMERALD);
			put("onyx",ItemSpriteSheet.RING_ONYX);
			put("opal",ItemSpriteSheet.RING_OPAL);
			put("tourmaline",ItemSpriteSheet.RING_TOURMALINE);
			put("sapphire",ItemSpriteSheet.RING_SAPPHIRE);
			put("amethyst",ItemSpriteSheet.RING_AMETHYST);
			put("quartz",ItemSpriteSheet.RING_QUARTZ);
			put("agate",ItemSpriteSheet.RING_AGATE);
			put("diamond",ItemSpriteSheet.RING_DIAMOND);
		}
	};

	public static final int RING_ACCURACY  		= 0;
	public static final int RING_ARCANA  		= 1;
	public static final int RING_ELEMENTS  		= 2;
	public static final int RING_ENERGY  		= 3;
	public static final int RING_EVASION  		= 4;
	public static final int RING_FORCE  		= 5;
	public static final int RING_FUROR  		= 6;
	public static final int RING_HASTE  		= 7;
	public static final int RING_MIGHT  		= 8;
	public static final int RING_SHARPSHOOTING  = 9;
	public static final int RING_TENACITY  		= 10;
	public static final int RING_WEALTH			= 11;

	public static final HashMap<Class<?extends Ring>, Integer> ringTypes = new HashMap<>();
	static {
		ringTypes.put(RingOfAccuracy.class,			RING_ACCURACY		);
		ringTypes.put(RingOfArcana.class,			RING_ARCANA  		);
		ringTypes.put(RingOfElements.class,			RING_ELEMENTS  		);
		ringTypes.put(RingOfEnergy.class,			RING_ENERGY  		);
		ringTypes.put(RingOfEvasion.class,			RING_EVASION  		);
		ringTypes.put(RingOfForce.class,			RING_FORCE  		);
		ringTypes.put(RingOfFuror.class,			RING_FUROR  		);
		ringTypes.put(RingOfHaste.class,			RING_HASTE  		);
		ringTypes.put(RingOfMight.class,			RING_MIGHT  		);
		ringTypes.put(RingOfSharpshooting.class,	RING_SHARPSHOOTING  );
		ringTypes.put(RingOfTenacity.class, 		RING_TENACITY  		);
		ringTypes.put(RingOfWealth.class,			RING_WEALTH			);
	}
	
	private static ItemStatusHandler<Ring> handler;
	
	private String gem;
	
	//rings cannot be 'used' like other equipment, so they ID purely based on exp
	private float levelsToID = 1;
	
	@SuppressWarnings("unchecked")
	public static void initGems() {
		handler = new ItemStatusHandler<>( (Class<? extends Ring>[])Generator.Category.RING.classes, gems );
	}

	public static void clearGems(){
		handler = null;
	}
	
	public static void save( Bundle bundle ) {
		handler.save( bundle );
	}

	public static void saveSelectively( Bundle bundle, ArrayList<Item> items ) {
		handler.saveSelectively( bundle, items );
	}
	
	@SuppressWarnings("unchecked")
	public static void restore( Bundle bundle ) {
		handler = new ItemStatusHandler<>( (Class<? extends Ring>[])Generator.Category.RING.classes, gems, bundle );
	}
	
	public Ring() {
		super();
		reset();
	}

	//anonymous rings are always IDed, do not affect ID status,
	//and their sprite is replaced by a placeholder if they are not known,
	//useful for items that appear in UIs, or which are only spawned for their effects
	protected boolean anonymous = false;
	public void anonymize(){
		if (!isKnown()) image = ItemSpriteSheet.RING_HOLDER;
		anonymous = true;
	}
	
	public void reset() {
		super.reset();
		levelsToID = 1;
		if (handler != null && handler.contains(this)){
			image = handler.image(this);
			gem = handler.label(this);
		} else {
			image = ItemSpriteSheet.RING_GARNET;
			gem = "garnet";
		}
	}
	
	public void activate( Char ch ) {
		if (buff != null){
			buff.detach();
			buff = null;
		}
		buff = buff();
		buff.attachTo( ch );
	}

	@Override
	public boolean doUnequip( Hero hero, boolean collect, boolean single ) {
		if (super.doUnequip( hero, collect, single )) {

			if (buff != null) {
				buff.detach();
				buff = null;
			}

			return true;

		} else {

			return false;

		}
	}
	
	public boolean isKnown() {
		return anonymous || (handler != null && handler.isKnown( this ));
	}
	
	public void setKnown() {
		if (!anonymous) {
			if (!isKnown()) {
				handler.know(this);
			}

			if (Dungeon.hero.isAlive()) {
				Catalog.setSeen(getClass());
				Statistics.itemTypesDiscovered.add(getClass());
			}
		}
	}
	
	@Override
	public String name() {
		return isKnown() ? super.name() : Messages.get(Ring.class, gem);
	}

	@Override
	public String desc() {
		return isKnown() ? super.desc() : Messages.get(this, "unknown_desc");
	}
	
	@Override
	public String info(){
		//skip custom notes if anonymized and un-Ided
		String desc;
		if (anonymous && (handler == null || !handler.isKnown( this ))){
			desc = desc();

		} else {
			desc = super.info();
		}

		if (cursed && isEquipped( Dungeon.hero )) {
			desc += "\n\n" + Messages.get(Ring.class, "cursed_worn");
			
		} else if (cursed && cursedKnown) {
			desc += "\n\n" + Messages.get(Ring.class, "curse_known");
			
		} else if (!isIdentified() && cursedKnown){
			desc += "\n\n" + Messages.get(Ring.class, "not_cursed");
			
		}
		
		if (isKnown()) {
			desc += "\n\n" + statsInfo();

			if (hero != null && hero.hasTalent(Talent.MYSTICAL_PUNCH)) {
				desc += "\n\n" + Messages.get(this, "special_effect");
			}
		}
		
		return desc;
	}
	
	public String statsInfo(){
		return "";
	}

	public String upgradeStat1(int level){
		return null;
	}

	public String upgradeStat2(int level){
		return null;
	}

	public String upgradeStat3(int level){
		return null;
	}
	
	@Override
	public Item upgrade() {
		super.upgrade();
		
		if (Random.Int(3) == 0) {
			cursed = false;
		}
		
		return this;
	}
	
	@Override
	public boolean isIdentified() {
		return super.isIdentified() && isKnown();
	}
	
	@Override
	public Item identify( boolean byHero ) {
		setKnown();
		levelsToID = 0;
		return super.identify(byHero);
	}

	public void setIDReady(){
		levelsToID = -1;
	}

	public boolean readyToIdentify(){
		return !isIdentified() && levelsToID <= 0;
	}
	
	@Override
	public Item random() {
		//+0: 66.67% (2/3)
		//+1: 26.67% (4/15)
		//+2: 6.67%  (1/15)
		int n = 0;
		if (Random.Int(3) == 0) {
			n++;
			if (Random.Int(5) == 0){
				n++;
			}
		}
		level(n);
		
		//30% chance to be cursed
		if (Random.Float() < 0.3f) {
			cursed = true;
		}
		
		return this;
	}
	
	public static HashSet<Class<? extends Ring>> getKnown() {
		return handler.known();
	}
	
	public static HashSet<Class<? extends Ring>> getUnknown() {
		return handler.unknown();
	}
	
	public static boolean allKnown() {
		return handler != null && handler.known().size() == Generator.Category.RING.classes.length;
	}
	
	@Override
	public int value() {
		int price = 75;
		if (cursed && cursedKnown) {
			price /= 2;
		}
		if (levelKnown) {
			if (level() > 0) {
				price *= (level() + 1);
			} else if (level() < 0) {
				price /= (1 - level());
			}
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}
	
	public RingBuff buff() {
		return null;
	}

	private static final String LEVELS_TO_ID    = "levels_to_ID";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( LEVELS_TO_ID, levelsToID );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		levelsToID = bundle.getFloat( LEVELS_TO_ID );
	}
	
	public void onHeroGainExp( float levelPercent, Hero hero ){
		if (isIdentified() || !isEquipped(hero)) return;
		levelPercent *= Talent.itemIDSpeedFactor(hero, this);
		//becomes IDed after 1 level
		levelsToID -= levelPercent;
		if (levelsToID <= 0){
			if (ShardOfOblivion.passiveIDDisabled()){
				if (levelsToID > -1){
					GLog.p(Messages.get(ShardOfOblivion.class, "identify_ready"), name());
				}
				setIDReady();
			} else {
				identify();
				GLog.p(Messages.get(Ring.class, "identify"));
				Badges.validateItemLevelAquired(this);
			}
		}
	}

	@Override
	public int buffedLvl() {
		int lvl = super.buffedLvl();
		if (Dungeon.hero.buff(EnhancedRings.class) != null){
			lvl++;
		}
		EnhancedRingsCombo enhancedRingsCombo = Dungeon.hero.buff(EnhancedRingsCombo.class);
		if (enhancedRingsCombo != null){
			lvl += enhancedRingsCombo.getCombo();
		}
		return lvl;
	}

	public static int getBonus(Char target, Class<?extends RingBuff> type){
		if (target.buff(MagicImmune.class) != null) return 0;
		int bonus = 0;
		for (RingBuff buff : target.buffs(type)) {
			bonus += buff.level();
		}
		SpiritForm.SpiritFormBuff spiritForm = target.buff(SpiritForm.SpiritFormBuff.class);
		if (bonus == 0
				&& spiritForm != null
				&& spiritForm.ring() != null
				&& spiritForm.ring().buffClass == type){
			bonus += spiritForm.ring().soloBonus();
		}
		return bonus;
	}

	public static int getBuffedBonus(Char target, Class<?extends RingBuff> type){
		if (target.buff(MagicImmune.class) != null) return 0;
		int bonus = 0;
		for (RingBuff buff : target.buffs(type)) {
			bonus += buff.buffedLvl();
		}
		if (bonus == 0
				&& target.buff(SpiritForm.SpiritFormBuff.class) != null
				&& target.buff(SpiritForm.SpiritFormBuff.class).ring() != null
				&& target.buff(SpiritForm.SpiritFormBuff.class).ring().buffClass == type){
			bonus += target.buff(SpiritForm.SpiritFormBuff.class).ring().soloBuffedBonus();
		}
		return bonus;
	}

	//just used for ring descriptions
	public int soloBonus(){
		if (cursed){
			return Math.min( 0, Ring.this.level()-2 );
		} else {
			return Ring.this.buffedLvl()+1;
		}
	}

	//just used for ring descriptions
	public int soloBuffedBonus(){
		if (cursed){
			return Math.min( 0, Ring.this.buffedLvl()-2 );
		} else {
			return Ring.this.buffedLvl()+1;
		}
	}

	//just used for ring descriptions
	public int combinedBonus(Hero hero){
		int bonus = 0;
		if (hero.belongings.ring() != null && hero.belongings.ring().getClass() == getClass()){
			bonus += hero.belongings.ring().soloBonus();
		}
		if (hero.belongings.misc() != null && hero.belongings.misc().getClass() == getClass()){
			bonus += ((Ring)hero.belongings.misc()).soloBonus();
		}
		return bonus;
	}

	//just used for ring descriptions
	public int combinedBuffedBonus(Hero hero){
		int bonus = 0;
		if (hero.belongings.ring() != null && hero.belongings.ring().getClass() == getClass()){
			bonus += hero.belongings.ring().soloBuffedBonus();
		}
		if (hero.belongings.misc() != null && hero.belongings.misc().getClass() == getClass()){
			bonus += ((Ring)hero.belongings.misc()).soloBuffedBonus();
		}
		return bonus;
	}

	public static float onHit (Hero hero, Char enemy, int damage, int ring) {
		float damageMulti = 1;
		switch (ring) {
			case RING_ACCURACY:	//명중률에 비례한 확률과 데미지 배율로 강력한 일격을 날림. 영웅의 명중이 적 회피의 2배를 넘으면 100% 확률로 작동하며, 명중/회피*2(최소 1, 최대 2)의 데미지 배율이 적용된다.
				float heroAcc = hero.attackSkill(enemy);
				float enemyEva = enemy.defenseSkill(hero)*2; //적의 회피에 2배의 보정이 붙음
				if (Random.Float() < heroAcc/enemyEva) {
					damageMulti = GameMath.gate(1, heroAcc/enemyEva, 2);
					Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
					if (Random.Float() < 0.1f) {
						Buff.affect(enemy, Paralysis.class, 1f);
					}
					if (Random.Float() < 0.1f) {
						Buff.affect(enemy, Daze.class, 2f);
					}
				}
				return damageMulti;
			case RING_ARCANA:	//무작위 무기 마법 효과 발동. 무기의 레벨은 영웅 레벨/5(올림)으로 가정하여 성능을 계산한다.
				int level = (int)Math.ceil(hero.lvl / 5f);
				float powerMulti = Math.max(1f, RingOfArcana.enchantPowerMultiplier(hero));
				float procChance = 0;
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
						break;
					case 1:
						//Blocking Effect
						procChance = (level+4f)/(level+40f) * powerMulti;

						if (Random.Float() < procChance){
							float powerLeft = Math.max(1f, procChance);

							Blocking.BlockBuff b = Buff.affect(hero, Blocking.BlockBuff.class);
							b.setShield(Math.round(powerLeft * (2 + level)));
							hero.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 5);
						}
						break;
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
									break;
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
										break;
									}
								}
							}
						}
						break;
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
						break;
					case 4:
						//Kinetic Effect, See RingOfForce.damageRoll too
						int conservedDamage = 0;
						if (hero.buff(Kinetic.ConservedDamage.class) != null) {
							conservedDamage = hero.buff(Kinetic.ConservedDamage.class).damageBonus();
							hero.buff(Kinetic.ConservedDamage.class).detach();
						}

						Buff.affect(hero, Kinetic.KineticTracker.class).conservedDamage = conservedDamage;
						break;
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
						break;
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
						break;
					case 7:
						//Grim Effect
						if (enemy.isImmune(Grim.class)) {
							return damageMulti;
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
						break;
					case 8:
						//Lucky Effect
						procChance = (level+4f)/(level+40f) * powerMulti;
						if (enemy.HP <= damage && Random.Float() < procChance){

							float powerLeft = Math.max(1f, procChance);

							//default is -5: 80% common, 20% uncommon, 0% rare
							//ring level increases by 1 for each 20% above 100% proc rate
							Buff.affect(enemy, Lucky.LuckProc.class).ringLevel = -10 + Math.round(5*powerLeft);
						}
						break;
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
						break;
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
						break;
					case 11:
						//Eldritch Effect
						Buff.prolong( enemy, Terror.class, 10f*powerMulti + 5f ).object = hero.id();
						break;
					case 12:
						//Stunning Effect
						Buff.prolong( enemy, Paralysis.class, 1f * powerMulti );
						enemy.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 12 );
						break;
					case 13:
						//Venomous Effect
						Buff.affect( enemy, Poison.class ).extend( (level/2f + 1) * powerMulti );
						CellEmitter.center(enemy.pos).burst( PoisonParticle.SPLASH, 5 );
						break;
					case 14:
						//Vorpal Effect
						Buff.affect(enemy, Bleeding.class).set((damage/10f) * powerMulti);
						Splash.at( enemy.sprite.center(), -PointF.PI / 2, PointF.PI / 6, enemy.sprite.blood(), 10 );
						break;
				}
				return damageMulti;
			case RING_ELEMENTS:	//20% 확률로 가진 디버프 전부 제거
				if (Random.Float() < 0.2f) {
					for (Buff buff : hero.buffs()) {
						if (buff.type == Buff.buffType.NEGATIVE
								&& !(buff instanceof AllyBuff)
								&& !(buff instanceof LostInventory)){
							buff.detach();
						}
						hero.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 12 );
					}
				}
				return damageMulti;
			case RING_ENERGY:	//25% 확률로 0.5턴의 유물 충전 효과를 얻음
				if (Random.Float() < 0.25f) {
					for (Buff b : hero.buffs()){
						if (b instanceof Artifact.ArtifactBuff && !((Artifact.ArtifactBuff) b).isCursed() ) {
							((Artifact.ArtifactBuff) b).charge(hero, 0.5f);
						}
					}
					ScrollOfRecharging.charge(hero);
				}
				return damageMulti;
			case RING_EVASION:	//회피율에 비례한 확률로 1턴의 회피 기동을 얻음. 영웅의 회피가 적 명중의 4배를 넘으면 100% 확률로 작동한다.
				float enemyAcc = enemy.attackSkill(hero)*4;	//적의 명중률에 4배의 보정이 붙음
				int heroEva = hero.defenseSkill(enemy);
				if (Random.Float() < heroEva/enemyAcc) {
					Buff.prolong(hero, EvasiveMove.class, 1.0001f);
				}
				return damageMulti;
			case RING_FORCE:	//25% 확률로 마비 1턴, 멍해짐, 불구, 현기증, 약화 3턴 중 하나를 걸음
				switch (Random.Int(20)) {
					case 0:
						Buff.affect(enemy, Paralysis.class, 1f);
						Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
						break;
					case 1:
						Buff.affect(enemy, Daze.class, 3f);
						Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
						break;
					case 2:
						Buff.affect(enemy, Cripple.class, 3f);
						Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
						break;
					case 3:
						Buff.affect(enemy, Vertigo.class, 3f);
						Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
						break;
					case 4:
						Buff.affect(enemy, Weakness.class, 3f);
						Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
						break;
					default:
						break;
				}
				return damageMulti;
			case RING_FUROR:	//20% 확률로 공격에 턴 소모 X
				if (Random.Float() < 0.2f) {
					hero.spend(-hero.attackDelay());	//공격에 소모하는 턴 X
				}
				return damageMulti;
			case RING_HASTE:	//20% 확률로 2턴의 신속을 얻음
				if (Random.Float() < 0.2f) {
					Buff.affect(hero, Haste.class, 2f);
				}
				return damageMulti;
			case RING_MIGHT:	//공격력 (현재 힘-10)*5%배
				return damageMulti*(1+0.05f*(hero.STR()-10));
			case RING_SHARPSHOOTING:	//대상에게 박힌 투척 무기 중 하나를 가져오고 내구도를 1만큼 수리함
				if (enemy.buff(PinCushion.class) != null) {
					Item item = enemy.buff(PinCushion.class).grabOne();
					if (item instanceof MissileWeapon) {
						((MissileWeapon) item).repair(1);
					}
					if (item.doPickUp(hero, enemy.pos)) {
						hero.spend(-Item.TIME_TO_PICK_UP); //casting the spell already takes a turn
						GLog.i( Messages.capitalize(Messages.get(hero, "you_now_have", item.name())) );
					} else {
						GLog.w(Messages.get(TelekineticGrab.class, "cant_grab"));
						Dungeon.level.drop(item, enemy.pos).sprite.drop();
					}
				}
				return damageMulti;
			case RING_TENACITY: //이번 턴에 체력이 1밑으로 내려가지 않음
				Buff.prolong(hero, Enduring.class, 1.0001f);
				return damageMulti;
			case RING_WEALTH:	//1%*(드랍 확률 증가 효과) 확률로 무작위 흔한 등급 보상이 떨어짐
				if (Random.Float() < 0.01f*RingOfWealth.dropChanceMultiplier(hero)) {
					Item prize = RingOfWealth.genConsumableDrop(-10);
					Dungeon.level.drop(prize, enemy.pos).sprite.drop();
					RingOfWealth.showFlareForBonusDrop(enemy.sprite);
				}
				return damageMulti;
		}

		return damageMulti;
	}


	public class RingBuff extends Buff {

		@Override
		public boolean attachTo( Char target ) {
			if (super.attachTo( target )) {
				//if we're loading in and the hero has partially spent a turn, delay for 1 turn
				if (target instanceof Hero && Dungeon.hero == null && cooldown() == 0 && target.cooldown() > 0) {
					spend(TICK);
				}
				return true;
			}
			return false;
		}

		@Override
		public boolean act() {
			spend( TICK );
			return true;
		}

		public int level(){
			return Ring.this.soloBonus();
		}

		public int buffedLvl(){
			return Ring.this.soloBuffedBonus();
		}

	}
}
