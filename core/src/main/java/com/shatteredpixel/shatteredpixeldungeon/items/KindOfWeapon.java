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

package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CertainCrit;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CritBonus;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Demonization;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Flurry;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Iaido;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.IntervalWeaponUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Jung;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SerialAttack;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sheathing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WeaponEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfVorpal;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.spellbook.SpellBook;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.spellbook.SpellBook_Disintegration;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.watabou.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

abstract public class  KindOfWeapon extends EquipableItem {
	
	protected static final float TIME_TO_EQUIP = 1f;

	protected String hitSound = Assets.Sounds.HIT;
	protected float hitSoundPitch = 1f;

	public boolean gun = false;
	public boolean handGun = false;
	public boolean machineGun = false;
	public boolean sniperGun = false;
	public boolean shotGun = false;
	public boolean rocketGun = false;
	public boolean heavyGun = false;
	public boolean bullet = false;
	public boolean handGunBullet = false;
	public boolean machineGunBullet = false;
	public boolean sniperGunBullet = false;
	public boolean shotGunBullet = false;
	public boolean rocketGunBullet = false;

	@Override
	public void execute(Hero hero, String action) {
		if (hero.subClass == HeroSubClass.CHAMPION && action.equals(AC_EQUIP)){
			usesTargeting = false;
			String primaryName = Messages.titleCase(hero.belongings.weapon != null ? hero.belongings.weapon.trueName() : Messages.get(KindOfWeapon.class, "empty"));
			String secondaryName = Messages.titleCase(hero.belongings.secondWep != null ? hero.belongings.secondWep.trueName() : Messages.get(KindOfWeapon.class, "empty"));
			if (primaryName.length() > 18) primaryName = primaryName.substring(0, 15) + "...";
			if (secondaryName.length() > 18) secondaryName = secondaryName.substring(0, 15) + "...";
			GameScene.show(new WndOptions(
					new ItemSprite(this),
					Messages.titleCase(name()),
					Messages.get(KindOfWeapon.class, "which_equip_msg"),
					Messages.get(KindOfWeapon.class, "which_equip_primary", primaryName),
					Messages.get(KindOfWeapon.class, "which_equip_secondary", secondaryName)
			){
				@Override
				protected void onSelect(int index) {
					super.onSelect(index);
					if (index == 0 || index == 1){
						//In addition to equipping itself, item reassigns itself to the quickslot
						//This is a special case as the item is being removed from inventory, but is staying with the hero.
						int slot = Dungeon.quickslot.getSlot( KindOfWeapon.this );
						slotOfUnequipped = -1;
						if (index == 0) {
							doEquip(hero);
						} else {
							equipSecondary(hero);
						}
						if (slot != -1) {
							Dungeon.quickslot.setSlot( slot, KindOfWeapon.this );
							updateQuickslot();
						//if this item wasn't quickslotted, but the item it is replacing as equipped was
						//then also have the item occupy the unequipped item's quickslot
						} else if (slotOfUnequipped != -1 && defaultAction() != null) {
							Dungeon.quickslot.setSlot( slotOfUnequipped, KindOfWeapon.this );
							updateQuickslot();
						}
					}
				}
			});
		} else if (this instanceof SpellBook && hero.subClass == HeroSubClass.WIZARD && action.equals(AC_EQUIP)){
			usesTargeting = false;
			String primaryName = Messages.titleCase(hero.belongings.weapon != null ? hero.belongings.weapon.trueName() : Messages.get(KindOfWeapon.class, "empty"));
			String secondaryName = Messages.titleCase(hero.belongings.secondWep != null ? hero.belongings.secondWep.trueName() : Messages.get(KindOfWeapon.class, "empty"));
			if (primaryName.length() > 18) primaryName = primaryName.substring(0, 15) + "...";
			if (secondaryName.length() > 18) secondaryName = secondaryName.substring(0, 15) + "...";
			GameScene.show(new WndOptions(
					new ItemSprite(this),
					Messages.titleCase(name()),
					Messages.get(KindOfWeapon.class, "which_equip_msg_spellbook"),
					Messages.get(KindOfWeapon.class, "which_equip_primary", primaryName),
					Messages.get(KindOfWeapon.class, "which_equip_secondary", secondaryName)
			){
				@Override
				protected void onSelect(int index) {
					super.onSelect(index);
					if (index == 0 || index == 1){
						//In addition to equipping itself, item reassigns itself to the quickslot
						//This is a special case as the item is being removed from inventory, but is staying with the hero.
						int slot = Dungeon.quickslot.getSlot( KindOfWeapon.this );
						slotOfUnequipped = -1;
						if (index == 0) {
							doEquip(hero);
						} else {
							equipSecondary(hero);
						}
						if (slot != -1) {
							Dungeon.quickslot.setSlot( slot, KindOfWeapon.this );
							updateQuickslot();
							//if this item wasn't quickslotted, but the item it is replacing as equipped was
							//then also have the item occupy the unequipped item's quickslot
						} else if (slotOfUnequipped != -1 && defaultAction() != null) {
							Dungeon.quickslot.setSlot( slotOfUnequipped, KindOfWeapon.this );
							updateQuickslot();
						}
					}
				}
			});
		} else {
			super.execute(hero, action);
		}
	}

	@Override
	public boolean isEquipped( Hero hero ) {
		return hero.belongings.weapon() == this || hero.belongings.secondWep() == this;
	}
	
	@Override
	public boolean doEquip( Hero hero ) {
		boolean wasInInv = hero.belongings.contains(this);
		detachAll( hero.belongings.backpack );
		
		if (hero.belongings.weapon == null || hero.belongings.weapon.doUnequip( hero, true )) {
			
			hero.belongings.weapon = this;
			activate( hero );
			Talent.onItemEquipped(hero, this);
			Badges.validateDuelistUnlock();
			ActionIndicator.refresh();
			updateQuickslot();

			cursedKnown = true;
			if (cursed) {
				equipCursed( hero );
				GLog.n( Messages.get(KindOfWeapon.class, "equip_cursed") );
			}

			if (wasInInv && hero.hasTalent(Talent.SWIFT_EQUIP)) {
				if (hero.buff(Talent.SwiftEquipCooldown.class) == null){
					hero.spendAndNext(-hero.cooldown());
					Buff.affect(hero, Talent.SwiftEquipCooldown.class, 19f)
							.secondUse = hero.pointsInTalent(Talent.SWIFT_EQUIP) == 2;
					GLog.i(Messages.get(this, "swift_equip"));
				} else if (hero.buff(Talent.SwiftEquipCooldown.class).hasSecondUse()) {
					hero.spendAndNext(-hero.cooldown());
					hero.buff(Talent.SwiftEquipCooldown.class).secondUse = false;
					GLog.i(Messages.get(this, "swift_equip"));
				} else {
					hero.spendAndNext(TIME_TO_EQUIP);
				}
			} else if (hero.subClass == HeroSubClass.WEAPONMASTER) {
				//do nothing
			} else if (hero.hasTalent(Talent.QUICK_SWAP) && hero.buff(Talent.QuickSwapCooldown.class) == null) {
				if (hero.hasTalent(Talent.QUICK_SWAP)) {
					Buff.affect( hero, WeaponEmpower.class).set( hero.pointsInTalent(Talent.QUICK_SWAP), 1f);
					Buff.affect( hero, Talent.QuickSwapCooldown.class, 5f);
				}
			} else {
				hero.spendAndNext( TIME_TO_EQUIP );
			}
			return true;
			
		} else {
			collect( hero.belongings.backpack );
			return false;
		}
	}

	public boolean equipSecondary( Hero hero ){
		boolean wasInInv = hero.belongings.contains(this);
		detachAll( hero.belongings.backpack );

		if (hero.belongings.secondWep == null || hero.belongings.secondWep.doUnequip( hero, true )) {

			hero.belongings.secondWep = this;
			activate( hero );
			Talent.onItemEquipped(hero, this);
			Badges.validateDuelistUnlock();
			ActionIndicator.refresh();
			updateQuickslot();

			cursedKnown = true;
			if (cursed) {
				equipCursed( hero );
				GLog.n( Messages.get(KindOfWeapon.class, "equip_cursed") );
			}

			if (wasInInv && hero.hasTalent(Talent.SWIFT_EQUIP)) {
				if (hero.buff(Talent.SwiftEquipCooldown.class) == null){
					hero.spendAndNext(-hero.cooldown());
					Buff.affect(hero, Talent.SwiftEquipCooldown.class, 19f)
							.secondUse = hero.pointsInTalent(Talent.SWIFT_EQUIP) == 2;
					GLog.i(Messages.get(this, "swift_equip"));
				} else if (hero.buff(Talent.SwiftEquipCooldown.class).hasSecondUse()) {
					hero.spendAndNext(-hero.cooldown());
					hero.buff(Talent.SwiftEquipCooldown.class).secondUse = false;
					GLog.i(Messages.get(this, "swift_equip"));
				} else {
					hero.spendAndNext(TIME_TO_EQUIP);
				}
			} else {
				hero.spendAndNext(TIME_TO_EQUIP);
			}
			return true;

		} else {

			collect( hero.belongings.backpack );
			return false;
		}
	}

	@Override
	public boolean doUnequip( Hero hero, boolean collect, boolean single ) {
		boolean second = hero.belongings.secondWep == this;

		if (second){
			//do this first so that the item can go to a full inventory
			hero.belongings.secondWep = null;
		}

		if (super.doUnequip( hero, collect, single )) {

			if (!second){
				hero.belongings.weapon = null;
			}
			return true;

		} else {

			if (second){
				hero.belongings.secondWep = this;
			}
			return false;

		}
	}

	public int min(){
		return min(buffedLvl());
	}

	public int max(){
		return max(buffedLvl());
	}

	public int STRReq() {
		return STRReq();
	}

	abstract public int min(int lvl);
	abstract public int max(int lvl);

	public int critChance() {
		int critChance = 0;

		Demonization demonization = hero.buff(Demonization.class);
		if (demonization != null && demonization.isDemonated()) {
			critChance += (int)((hero.defenseSkill(hero) - (hero.lvl+5))/2);
		}

		if (hero.heroClass == HeroClass.SAMURAI && hero.belongings.weapon != null) {
			critChance += 2 * (hero.STR() - hero.belongings.weapon.STRReq());
			critChance += hero.lvl;
		}

		if (hero.buff(Talent.SurpriseStabTracker.class) != null) {
			critChance += 5 * hero.pointsInTalent(Talent.SURPRISE_STAB);
			hero.buff(Talent.SurpriseStabTracker.class).detach();
		}

		if (hero.buff(Sheathing.class) != null) {
			if (hero.buff(Iaido.class) != null) {
				critChance *= 1.2f + 0.3f * hero.buff(Iaido.class).getCount();
			} else {
				critChance *= 1.2f;
			}
		}

		if (hero.buff(Jung.class) != null) {
			critChance *= 2f + 0.5f * hero.pointsInTalent(Talent.JUNG_DETECTION);
		}

		if (hero.buff(CritBonus.class) != null) {
			critChance += hero.buff(CritBonus.class).level();
		}

		if (hero.buff(IntervalWeaponUpgrade.class) != null) {
			critChance += 10 * hero.buff(IntervalWeaponUpgrade.class).boost();
		}

		if (this instanceof MissileWeapon && hero.hasTalent(Talent.CRITICAL_THROW)) {
			critChance += 25 * hero.pointsInTalent(Talent.CRITICAL_THROW);
		}

		if (hero.buff(CertainCrit.class) != null) {
			critChance = 100;
		}

		critChance += Math.round(100*RingOfVorpal.vorpalProc( hero ));

		critChance = Math.min(critChance, 100);

		return critChance;
	}

	public int damageRoll(Char owner) {
		if (owner == hero && hero.belongings.weapon() instanceof MeleeWeapon) {
			if (hero.buff(Talent.DetactiveSlashingTracker.class) != null && hero.subClass == HeroSubClass.SLASHER && hero.pointsInTalent(Talent.DETECTIVE_SLASHING) > 2) {
				Buff.affect(hero, SerialAttack.class).maxHit();
			}
			if ( critChance() > 0 ) {
				if (Random.Int(100) < critChance()) {
					Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
					hero.sprite.showStatus(CharSprite.NEUTRAL, "!");
					if (hero.buff(Iaido.class) != null && hero.pointsInTalent(Talent.SLASHING) > 1) {
						if (Random.Int(7) < hero.buff(Iaido.class).getCount()) {
							Buff.affect(hero, IntervalWeaponUpgrade.class).levelUp();
							Item.updateQuickslot();
						}
					}
					Demonization demonization = owner.buff(Demonization.class);
					if (demonization != null && demonization.isDemonated() && hero.hasTalent(Talent.ENERGY_DRAIN)) {
						int pointUsed = hero.pointsInTalent(Talent.ENERGY_DRAIN);
						if (hero.buff(Barrier.class) == null || hero.buff(Barrier.class).shielding() < (10 * pointUsed - pointUsed)) {
							Buff.affect(hero, Barrier.class).incShield(pointUsed);
						} else {
							Buff.affect(hero, Barrier.class).setShield(10 * pointUsed);
						}
					}
					if (hero.buff(Flurry.class) != null) {
						int healAmt = 2;
						hero.heal(healAmt);
					}
					if (hero.pointsInTalent(Talent.JUNG_QUICK_DRAW) > 1 && hero.buff(Jung.class) != null && hero.buff(Sheathing.class) != null) {
						Buff.prolong(hero, Adrenaline.class, 4f);
					}
					if (hero.pointsInTalent(Talent.JUNG_QUICK_DRAW) > 2 && hero.buff(Jung.class) != null && hero.buff(Sheathing.class) != null) {
						Buff.affect(hero, Talent.JungQuickDrawTracker.class);
					}
					int damageBonus = 0;
					if (hero.hasTalent(Talent.JUNG_QUICK_DRAW) && hero.buff(Jung.class) != null && hero.buff(Sheathing.class) != null) {
						damageBonus += Random.NormalIntRange(0, 20);
					}
					return Random.NormalIntRange(Math.round(0.75f * max()), max()) + damageBonus;
				} else {
					return Random.NormalIntRange( min(), max() );
				}
			} else {
				if (Random.Int(100) < -critChance()) {
					hero.sprite.showStatus(CharSprite.NEUTRAL, "?");
					return Random.NormalIntRange(min(), Math.round(0.5f * max()));
				} else {
					return Random.NormalIntRange( min(), max() );
				}
			}
		} else {
			return Random.NormalIntRange( min(), max() );
		}
	}
	
	public float accuracyFactor( Char owner, Char target ) {
		return 1f;
	}
	
	public float delayFactor( Char owner ) {
		return 1f;
	}

	public int reachFactor( Char owner ){
		return 1;
	}
	
	public boolean canReach( Char owner, int target){
		int reach = reachFactor(owner);

		SpellBook_Disintegration.ReachBuff buff = hero.buff(SpellBook_Disintegration.ReachBuff.class);
		if (buff != null) {
			reach += 1+buff.getUpgrade();
		}
		if (Dungeon.level.distance( owner.pos, target ) > reach){
			return false;
		} else {
			boolean[] passable = BArray.not(Dungeon.level.solid, null);
			for (Char ch : Actor.chars()) {
				if (ch != owner) passable[ch.pos] = false;
			}
			
			PathFinder.buildDistanceMap(target, passable, reach);
			
			return PathFinder.distance[owner.pos] <= reach;
		}
	}

	public int defenseFactor( Char owner ) {
		return 0;
	}
	
	public int proc( Char attacker, Char defender, int damage ) {
		return damage;
	}

	public void hitSound( float pitch ){
		Sample.INSTANCE.play(hitSound, 1, pitch * hitSoundPitch);
	}
	
}
