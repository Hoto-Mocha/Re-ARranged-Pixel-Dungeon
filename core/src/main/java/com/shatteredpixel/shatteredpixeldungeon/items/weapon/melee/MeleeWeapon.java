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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.GreaterHaste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MonkEnergy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Regeneration;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells.HolyWeapon;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.EnergyCrystal;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.Sheath;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfForce;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.bow.BowWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Door;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIcon;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.Visual;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.BArray;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MeleeWeapon extends Weapon {

	public static String AC_ABILITY = "ABILITY";
	public static String AC_SCRAP = "SCRAP";

	@Override
	public void activate(Char ch) {
		super.activate(ch);
		if (ch instanceof Hero && ((Hero) ch).heroClass == HeroClass.DUELIST){
			Buff.affect(ch, Charger.class);
		}
	}

	@Override
	public String defaultAction() {
		if (Dungeon.hero != null && (Dungeon.hero.heroClass == HeroClass.DUELIST
				|| Dungeon.hero.hasTalent(Talent.SWIFT_EQUIP)) && !(this instanceof Gun) && !(this instanceof BowWeapon)){
			return AC_ABILITY;
		} else {
			return super.defaultAction();
		}
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && hero.heroClass == HeroClass.DUELIST){
			actions.add(AC_ABILITY);
		}
		if (!isEquipped(hero) && (hero.heroClass == HeroClass.GUNNER || hero.heroClass == HeroClass.ARCHER)) {
			actions.add(AC_SCRAP);
		}
		return actions;
	}

	@Override
	public String actionName(String action, Hero hero) {
		if (action.equals(AC_ABILITY)){
			return abilityName();
		} else {
			return super.actionName(action, hero);
		}
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);

		if (action.equals(AC_UNEQUIP) && hero.buff(Sheath.Sheathing.class) != null) {
			hero.buff(Sheath.Sheathing.class).detach();
		}
		if (action.equals(AC_ABILITY)){
			usesTargeting = false;
			if (!isEquipped(hero)) {
				if (hero.hasTalent(Talent.SWIFT_EQUIP)){
					if (hero.buff(Talent.SwiftEquipCooldown.class) == null
						|| hero.buff(Talent.SwiftEquipCooldown.class).hasSecondUse()){
						execute(hero, AC_EQUIP);
					} else if (hero.heroClass == HeroClass.DUELIST) {
						GLog.w(Messages.get(this, "ability_need_equip"));
					}
				} else if (hero.heroClass == HeroClass.DUELIST) {
					GLog.w(Messages.get(this, "ability_need_equip"));
				}
			} else if (hero.heroClass != HeroClass.DUELIST){
				//do nothing
			} else if (STRReq() > hero.STR()){
				GLog.w(Messages.get(this, "ability_low_str"));
			} else if ((Buff.affect(hero, Charger.class).charges + Buff.affect(hero, Charger.class).partialCharge) < abilityChargeUse(hero, null)) {
				GLog.w(Messages.get(this, "ability_no_charge"));
			} else {

				if (targetingPrompt() == null){
					duelistAbility(hero, hero.pos);
					updateQuickslot();
				} else {
					usesTargeting = useTargeting();
					GameScene.selectCell(new CellSelector.Listener() {
						@Override
						public void onSelect(Integer cell) {
							if (cell != null) {
								duelistAbility(hero, cell);
								updateQuickslot();
							}
						}

						@Override
						public String prompt() {
							return targetingPrompt();
						}
					});
				}
			}
		}
		if (action.equals(AC_SCRAP)) {
			if (hero.heroClass == HeroClass.GUNNER) {
				Game.runOnRenderThread(new Callback() {
					@Override
					public void call() {
						int level = MeleeWeapon.this.isIdentified() ? MeleeWeapon.this.level() : 0;
						final int amount = Math.round(5 * (MeleeWeapon.this.tier+1) * (float)Math.pow(2, Math.min(3, level)));
						GameScene.show(
								new WndOptions( new ItemSprite(MeleeWeapon.this),
										Messages.get(MeleeWeapon.class, "scrap_title"),
										Messages.get(MeleeWeapon.class, "scrap_desc", amount),
										Messages.get(MeleeWeapon.class, "scrap_yes"),
										Messages.get(MeleeWeapon.class, "scrap_no") ) {

									private float elapsed = 0f;

									@Override
									public synchronized void update() {
										super.update();
										elapsed += Game.elapsed;
									}

									@Override
									public void hide() {
										if (elapsed > 0.2f){
											super.hide();
										}
									}

									@Override
									protected void onSelect( int index ) {
										if (index == 0 && elapsed > 0.2f) {
											LiquidMetal metal = new LiquidMetal();

											metal.quantity(amount);
											if (!metal.doPickUp(hero)) {
												Dungeon.level.drop( metal, hero.pos ).sprite.drop();
											}

											EnergyCrystal crystal = new EnergyCrystal();
											crystal.quantity(Random.IntRange(1, 2));
											crystal.doPickUp(hero);

											MeleeWeapon.this.detach(hero.belongings.backpack);

											hero.sprite.operate(hero.pos);
											GLog.p(Messages.get(MeleeWeapon.class, "scrap", amount));
											Sample.INSTANCE.play(Assets.Sounds.UNLOCK);

											updateQuickslot();
										}
									}
								}
						);
					}
				});
			} else if (hero.heroClass == HeroClass.ARCHER) {
				detach(hero.belongings.backpack);
				hero.sprite.operate(hero.pos);
				Sample.INSTANCE.play(Assets.Sounds.EVOKE);
				CellEmitter.center( curUser.pos ).burst( Speck.factory( Speck.STAR ), 7 );
				GLog.p(Messages.get(this, "scrap_archer"));
				MissileWeapon result = Generator.randomMissile(true);
				if (!result.doPickUp(hero, hero.pos)) {
					GLog.i(Messages.get(Dungeon.hero, "you_now_have", result.name()));
					hero.spend(-1);
					Dungeon.level.drop(result, hero.pos).sprite.drop();
				}
				updateQuickslot();
			}
		}
	}

	//leave null for no targeting
	public String targetingPrompt(){
		return null;
	}

	public boolean useTargeting(){
		return targetingPrompt() != null;
	}

	@Override
	public int targetingPos(Hero user, int dst) {
		return dst; //weapon abilities do not use projectile logic, no autoaim
	}

	protected void duelistAbility( Hero hero, Integer target ){
		//do nothing by default
	}

	public void beforeAbilityUsed(Hero hero, Char target){
		hero.belongings.abilityWeapon = this;
		Charger charger = Buff.affect(hero, Charger.class);

		charger.partialCharge -= abilityChargeUse(hero, target);
		while (charger.partialCharge < 0 && charger.charges > 0) {
			charger.charges--;
			charger.partialCharge++;
		}

		if (hero.heroClass == HeroClass.DUELIST
				&& hero.hasTalent(Talent.AGGRESSIVE_BARRIER)
				&& (hero.HP / (float)hero.HT) <= 0.5f){
			int shieldAmt = 1 + 2*hero.pointsInTalent(Talent.AGGRESSIVE_BARRIER);
			Buff.affect(hero, Barrier.class).setShield(shieldAmt);
			hero.sprite.showStatusWithIcon(CharSprite.POSITIVE, Integer.toString(shieldAmt), FloatingText.SHIELDING);
		}

		if (hero.hasTalent(Talent.SKILLED_HAND)) {
			Buff.affect(hero, Talent.SkilledHandTracker.class);
		}

		updateQuickslot();
	}

	public void afterAbilityUsed( Hero hero ){
		hero.belongings.abilityWeapon = null;
		if (hero.hasTalent(Talent.PRECISE_ASSAULT)){
			Buff.prolong(hero, Talent.PreciseAssaultTracker.class, hero.cooldown()+4f);
		}
		if (hero.hasTalent(Talent.VARIED_CHARGE)){
			Talent.VariedChargeTracker tracker = hero.buff(Talent.VariedChargeTracker.class);
			if (tracker == null || tracker.weapon == getClass() || tracker.weapon == null){
				Buff.affect(hero, Talent.VariedChargeTracker.class).weapon = getClass();
			} else {
				tracker.detach();
				Charger charger = Buff.affect(hero, Charger.class);
				charger.gainCharge(hero.pointsInTalent(Talent.VARIED_CHARGE) / 6f);
				ScrollOfRecharging.charge(hero);
			}
		}
		if (hero.hasTalent(Talent.COMBINED_LETHALITY)) {
			Talent.CombinedLethalityAbilityTracker tracker = hero.buff(Talent.CombinedLethalityAbilityTracker.class);
			if (tracker == null || tracker.weapon == this || tracker.weapon == null){
				Buff.affect(hero, Talent.CombinedLethalityAbilityTracker.class, hero.cooldown()).weapon = this;
			} else {
				//we triggered the talent, so remove the tracker
				tracker.detach();
			}
		}
		if (hero.hasTalent(Talent.COMBINED_ENERGY)){
			Talent.CombinedEnergyAbilityTracker tracker = hero.buff(Talent.CombinedEnergyAbilityTracker.class);
			if (tracker == null || !tracker.monkAbilused){
				Buff.prolong(hero, Talent.CombinedEnergyAbilityTracker.class, 5f).wepAbilUsed = true;
			} else {
				tracker.wepAbilUsed = true;
				Buff.affect(hero, MonkEnergy.class).processCombinedEnergy(tracker);
			}
		}
		if (hero.hasTalent(Talent.AGGRESSIVE_MOVEMENT)) {
			Talent.AgressiveMovementAbilityTracker tracker = hero.buff(Talent.AgressiveMovementAbilityTracker.class);
			if (tracker == null){
				Buff.prolong(hero, Talent.AgressiveMovementAbilityTracker.class, hero.cooldown()).wepAbilUsed = true;
			} else {
				tracker.wepAbilUsed = true;
			}
		}
		if (hero.buff(Talent.CounterAbilityTacker.class) != null){
			Charger charger = Buff.affect(hero, Charger.class);
			charger.gainCharge(hero.pointsInTalent(Talent.COUNTER_ABILITY)*0.375f);
			hero.buff(Talent.CounterAbilityTacker.class).detach();
		}
	}

	public static void onAbilityKill( Hero hero, Char killed ){
		if (killed.alignment == Char.Alignment.ENEMY && hero.hasTalent(Talent.LETHAL_HASTE)){
			//effectively 3/5 turns of greater haste
			Buff.affect(hero, GreaterHaste.class).set(2 + 2*hero.pointsInTalent(Talent.LETHAL_HASTE));
		}
	}

	protected int baseChargeUse(Hero hero, Char target){
		return 1; //abilities use 1 charge by default
	}

	public final float abilityChargeUse(Hero hero, Char target){
		return baseChargeUse(hero, target);
	}

	public int tier;

	@Override
	public int min(int lvl) {
		return  tier +  //base
				lvl;    //level scaling
	}

	@Override
	public int max(int lvl) {
		return  5*(tier+1) +    //base
				lvl*(tier+1);   //level scaling
	}

	public int STRReq(int lvl){
		int req = STRReq(tier, lvl);
		if (masteryPotionBonus){
			req -= 2;
		}
		return req;
	}

	private static boolean evaluatingTwinUpgrades = false;
	@Override
	public int buffedLvl() {
		if (!evaluatingTwinUpgrades && Dungeon.hero != null && isEquipped(Dungeon.hero) && Dungeon.hero.hasTalent(Talent.TWIN_UPGRADES)){
			KindOfWeapon other = null;
			if (Dungeon.hero.belongings.weapon() != this) other = Dungeon.hero.belongings.weapon();
			if (Dungeon.hero.belongings.secondWep() != this) other = Dungeon.hero.belongings.secondWep();

			if (other instanceof MeleeWeapon) {
				evaluatingTwinUpgrades = true;
				int otherLevel = other.buffedLvl();
				evaluatingTwinUpgrades = false;

				//weaker weapon needs to be 2/1/0 tiers lower, based on talent level
				if ((tier + (3 - Dungeon.hero.pointsInTalent(Talent.TWIN_UPGRADES))) <= ((MeleeWeapon) other).tier
						&& otherLevel > super.buffedLvl()) {
					return otherLevel;
				}

			}
		}
		return super.buffedLvl();
	}

	@Override
	public int damageRoll(Char owner) {
		int damage = augment.damageFactor(super.damageRoll( owner ));

		if (owner instanceof Hero) {
			int exStr = ((Hero)owner).STR() - STRReq();
			if (exStr > 0) {
				damage += Hero.heroDamageIntRange( 0, exStr );
			}
		}
		return damage;
	}

	public int tier() {
		return this.tier;
	}
	
	@Override
	public String info() {

		String info = super.info();

		if (levelKnown) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_known", tier(), augment.damageFactor(min()), augment.damageFactor(max()), STRReq());
			if (Dungeon.hero != null) {
				if (STRReq() > Dungeon.hero.STR()) {
					info += " " + Messages.get(Weapon.class, "too_heavy");
				} else if (Dungeon.hero.STR() > STRReq()) {
					info += " " + Messages.get(Weapon.class, "excess_str", Dungeon.hero.STR() - STRReq());
				}
			}
		} else {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_unknown", tier(), min(0), max(0), STRReq(0));
			if (Dungeon.hero != null && STRReq(0) > Dungeon.hero.STR()) {
				info += " " + Messages.get(MeleeWeapon.class, "probably_too_heavy");
			}
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

		if (isEquipped(Dungeon.hero) && !hasCurseEnchant() && Dungeon.hero.buff(HolyWeapon.HolyWepBuff.class) != null
				&& (Dungeon.hero.subClass != HeroSubClass.PALADIN || enchantment == null)){
			info += "\n\n" + Messages.capitalize(Messages.get(Weapon.class, "enchanted", Messages.get(HolyWeapon.class, "ench_name", Messages.get(Enchantment.class, "enchant"))));
			info += " " + Messages.get(HolyWeapon.class, "ench_desc");
		} else if (enchantment != null && (cursedKnown || !enchantment.curse())){
			info += "\n\n" + Messages.capitalize(Messages.get(Weapon.class, "enchanted", enchantment.name()));
			if (enchantHardened) info += " " + Messages.get(Weapon.class, "enchant_hardened");
			info += " " + enchantment.desc();
		} else if (enchantHardened){
			info += "\n\n" + Messages.get(Weapon.class, "hardened_no_enchant");
		}

		if (cursed && isEquipped( Dungeon.hero )) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed");
		} else if (!isIdentified() && cursedKnown){
			if (enchantment != null && enchantment.curse()) {
				info += "\n\n" + Messages.get(Weapon.class, "weak_cursed");
			} else {
				info += "\n\n" + Messages.get(Weapon.class, "not_cursed");
			}
		}

		//the mage's staff has no ability as it can only be gained by the mage
		if (Dungeon.hero != null && Dungeon.hero.heroClass == HeroClass.DUELIST && !(this instanceof MagesStaff)){
			info += "\n\n" + abilityInfo();
		}

		if (hero != null && isEquipped(hero) && hero.critChance(this) > 0) {
			info += "\n\n" + Messages.get(Weapon.class, "critchance", Messages.decimalFormat("#.##", 100*hero.critChance(this)));
		}
		
		return info;
	}

	public String abilityName() {
		return Messages.upperCase(Messages.get(this, "ability_name"));
	}

	public String abilityDesc() {
		return Messages.get(this, "ability_desc");
	}
	
	public String statsInfo(){
		return Messages.get(this, "stats_desc");
	}

	public String abilityInfo() {
		return Messages.get(this, "ability_desc");
	}

	public String upgradeAbilityStat(int level){
		return null;
	}

	@Override
	public String status() {
		if (Dungeon.hero != null && isEquipped(Dungeon.hero)
				&& Dungeon.hero.buff(Charger.class) != null) {
			Charger buff = Dungeon.hero.buff(Charger.class);
			return buff.charges + "/" + buff.chargeCap();
		} else {
			return super.status();
		}
	}

	@Override
	public int value() {
		int price = 20 * tier;
		if (hasGoodEnchant()) {
			price *= 1.5;
		}
		if (cursedKnown && (cursed || hasCurseEnchant())) {
			price /= 2;
		}
		if (levelKnown && level() > 0) {
			price *= (level() + 1);
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}



	private static final String TIER = "tier";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(TIER, tier);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		if (bundle.getInt(TIER) != 0) {
			tier = bundle.getInt(TIER);
		}
	}

	public static class Charger extends Buff implements ActionIndicator.Action {

		public int charges = 2;
		public float partialCharge;

		@Override
		public boolean act() {
			if (charges < chargeCap()){
				if (Regeneration.regenOn()){
					//60 to 45 turns per charge
					float chargeToGain = 1/(60f-1.5f*(chargeCap()-charges));

					//40 to 30 turns per charge for champion
					if (Dungeon.hero.subClass == HeroSubClass.CHAMPION){
						chargeToGain *= 1.5f;
					}

					if (hero.hasTalent(Talent.FASTER_CHARGE)) {
						chargeToGain *= 1+hero.pointsInTalent(Talent.FASTER_CHARGE)/12f;
					}

					if (hero.hasTalent(Talent.TWIN_SWORD) && hero.belongings.weapon != null && hero.belongings.secondWep != null) {
						if (hero.belongings.weapon.getClass() == hero.belongings.secondWep.getClass()) {
							chargeToGain *= 1.25f;
						}
					}


					//50% slower charge gain with brawler's stance enabled, even if buff is inactive
					if (Dungeon.hero.buff(RingOfForce.BrawlersStance.class) != null){
						chargeToGain *= 0.50f;
					}

					partialCharge += chargeToGain;
				}

				int points = ((Hero)target).pointsInTalent(Talent.WEAPON_RECHARGING);
				if (points > 0 && target.buff(Recharging.class) != null || target.buff(ArtifactRecharge.class) != null){
					//1 every 15 turns at +1, 10 turns at +2
					partialCharge += 1/(20f - 5f*points);
				}

				if (partialCharge >= 1){
					charges++;
					partialCharge--;
					updateQuickslot();
				}
			} else {
				partialCharge = 0;
			}

			if (ActionIndicator.action != this && Dungeon.hero.subClass == HeroSubClass.CHAMPION || Dungeon.hero.subClass == HeroSubClass.FENCER) {
				ActionIndicator.setAction(this);
			}

			spend(TICK);
			return true;
		}

		@Override
		public void fx(boolean on) {
			if (on && (Dungeon.hero.subClass == HeroSubClass.CHAMPION || Dungeon.hero.subClass == HeroSubClass.FENCER)) {
				ActionIndicator.setAction(this);
			}
		}

		@Override
		public void detach() {
			super.detach();
			ActionIndicator.clearAction(this);
		}

		public int chargeCap(){
			//caps at level 19 with 8 or 10 charges
			int charge;
			if (Dungeon.hero.subClass == HeroSubClass.CHAMPION){
				charge = Math.min(10, 4 + (Dungeon.hero.lvl - 1) / 3);
			} else {
				charge = Math.min(8, 2 + (Dungeon.hero.lvl - 1) / 3);
			}
			charge += Dungeon.hero.pointsInTalent(Talent.ACCUMULATION);
			return charge;
		}

		public void gainCharge( float charge ){
			if (charges < chargeCap()) {
				partialCharge += charge;
				while (partialCharge >= 1f) {
					charges++;
					partialCharge--;
				}
				if (charges >= chargeCap()){
					partialCharge = 0;
					charges = chargeCap();
				}
				updateQuickslot();
			}
		}

		public static final String CHARGES          = "charges";
		private static final String PARTIALCHARGE   = "partialCharge";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(CHARGES, charges);
			bundle.put(PARTIALCHARGE, partialCharge);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			charges = bundle.getInt(CHARGES);
			partialCharge = bundle.getFloat(PARTIALCHARGE);
		}

		@Override
		public String actionName() {
			if (hero.subClass == HeroSubClass.FENCER) {
				return Messages.get(MeleeWeapon.class, "dash");
			} else {
				return Messages.get(MeleeWeapon.class, "swap");
			}
		}

		@Override
		public int actionIcon() {
			if (hero.subClass == HeroSubClass.CHAMPION) {
				return HeroIcon.WEAPON_SWAP;
			}
			else {
				return 0;
			}
		}

		@Override
		public Visual primaryVisual() {
			Image ico;
			if (hero.subClass == HeroSubClass.FENCER) {
				ico = new BuffIcon(BuffIndicator.HASTE, true);
				ico.hardlight(0x5A00B2);
				return ico;
			} else {
				if (Dungeon.hero.belongings.weapon == null){
					ico = new HeroIcon(this);
				} else {
					ico = new ItemSprite(Dungeon.hero.belongings.weapon);
				}
			}
			ico.width += 4; //shift slightly to the left to separate from smaller icon
			return ico;
		}

		@Override
		public Visual secondaryVisual() {
			Image ico;
			if (hero.subClass == HeroSubClass.CHAMPION) {
				if (Dungeon.hero.belongings.secondWep == null){
					ico = new HeroIcon(this);
				} else {
					ico = new ItemSprite(Dungeon.hero.belongings.secondWep);
				}
				ico.scale.set(PixelScene.align(0.51f));
				ico.brightness(0.6f);
				return ico;
			} else {
				return null;
			}
		}

		@Override
		public int indicatorColor() {
			return 0x5500BB;
		}

		@Override
		public void doAction() {
			if (Dungeon.hero.subClass != HeroSubClass.CHAMPION && Dungeon.hero.subClass != HeroSubClass.FENCER) {
				return;
			}

			if (Dungeon.hero.subClass == HeroSubClass.CHAMPION) {
				if (Dungeon.hero.belongings.secondWep == null && Dungeon.hero.belongings.backpack.items.size() >= Dungeon.hero.belongings.backpack.capacity()) {
					GLog.w(Messages.get(MeleeWeapon.class, "swap_full"));
					return;
				}

				KindOfWeapon temp = Dungeon.hero.belongings.weapon;
				Dungeon.hero.belongings.weapon = Dungeon.hero.belongings.secondWep;
				Dungeon.hero.belongings.secondWep = temp;

				Dungeon.hero.sprite.operate(Dungeon.hero.pos);
				Sample.INSTANCE.play(Assets.Sounds.UNLOCK);

				if (hero.buff(Talent.QuickFollowupTracker.class) != null) {
					hero.buff(Talent.QuickFollowupTracker.class).detach();
				}

				if (hero.buff(Talent.QuickFollowupCooldown.class) == null && hero.hasTalent(Talent.QUICK_FOLLOWUP)) {
					Buff.affect(hero, Talent.QuickFollowupTracker.class);
					Buff.affect(hero, Talent.QuickFollowupCooldown.class, 10f);
				}

				ActionIndicator.setAction(this);
				Item.updateQuickslot();
				AttackIndicator.updateState();
			} else {
				float chargeToUse = 1f;

				if (hero.buff(DashTracker.class) != null) {
					chargeToUse *= 0.5f;
				}
				if (hero.hasTalent(Talent.CLAM_STEPS)) {
					chargeToUse *= Math.pow(0.795, hero.pointsInTalent(Talent.CLAM_STEPS));
				}
				Talent.AgressiveMovementAbilityTracker tracker = hero.buff(Talent.AgressiveMovementAbilityTracker.class);
				if (tracker != null && tracker.wepAbilUsed) {
					chargeToUse *= 0.5f;
				}
				if (charges < chargeToUse) {
					GLog.w(Messages.get(MeleeWeapon.class, "ability_no_charge"));
				} else {
					GameScene.selectCell(dasher);
				}
			}
		}

		public void useCharges(float amount) {
			partialCharge -= amount;
			while (partialCharge < 0 && charges > 0) {
				charges--;
				partialCharge++;
			}
		}
	}

	private static CellSelector.Listener dasher = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer target ) {
			if (target == null || target == -1 || (!Dungeon.level.visited[target] && !Dungeon.level.mapped[target])){
				return;
			}

			//chains cannot be used to go where it is impossible to walk to
			PathFinder.buildDistanceMap(target, BArray.or(Dungeon.level.passable, Dungeon.level.avoid, null));
			if (PathFinder.distance[hero.pos] == Integer.MAX_VALUE){
				GLog.w( Messages.get(MeleeWeapon.class, "dash_bad_position") );
				return;
			}

			if (hero.rooted){
				GLog.w( Messages.get(MeleeWeapon.class, "rooted") );
				return;
			}

			int range = 2;
			if (hero.hasTalent(Talent.SOULIZE)) {
				range += 1;
			}

			if (Dungeon.level.distance(hero.pos, target) > range){
				GLog.w(Messages.get(MeleeWeapon.class, "dash_bad_position"));
				return;
			}

			Ballistica dash = new Ballistica(hero.pos, target, Ballistica.PROJECTILE);
			if (hero.pointsInTalent(Talent.SOULIZE) > 1) {
				dash = new Ballistica(hero.pos, target, Ballistica.DASH);
				if (hero.pointsInTalent(Talent.SOULIZE) > 2) {
					dash = new Ballistica(hero.pos, target, Ballistica.STOP_TARGET);
				}
			}

			if (!dash.collisionPos.equals(target)
					|| Actor.findChar(target) != null
					|| (Dungeon.level.solid[target] && !Dungeon.level.passable[target])
					|| Dungeon.level.map[target] == Terrain.CHASM){
				GLog.w(Messages.get(MeleeWeapon.class, "dash_bad_position"));
				return;
			}

			hero.busy();
			Sample.INSTANCE.play(Assets.Sounds.MISS);
			hero.sprite.emitter().start(Speck.factory(Speck.JET), 0.01f, Math.round(4 + 2*Dungeon.level.trueDistance(hero.pos, target)));
			hero.sprite.jump(hero.pos, target, 0, 0.1f, new Callback() {
				@Override
				public void call() {
					if (Dungeon.level.map[hero.pos] == Terrain.OPEN_DOOR) {
						Door.leave( hero.pos );
					}
					hero.pos = target;
					if (hero.pointsInTalent(Talent.AGGRESSIVE_MOVEMENT) > 1) {
						for (int i : PathFinder.NEIGHBOURS8){
							Char ch = Actor.findChar( target+i );
							if (ch != null && ch.alignment != Char.Alignment.ALLY){
								ch.damage(Math.round(hero.belongings.weapon.damageRoll(hero) * 0.1f), Dungeon.hero);
								if (hero.pointsInTalent(Talent.AGGRESSIVE_MOVEMENT) > 2) {
									Buff.affect(ch, Vulnerable.class, 3f);
								}
							}
						}
						Sample.INSTANCE.play(Assets.Sounds.BLAST);
						WandOfBlastWave.BlastWave.blast(target);
						Camera.main.shake(0.5f, 0.5f);
					}
					Dungeon.level.occupyCell(hero);
					hero.next();
				}
			});

			Buff.affect(hero, DashAttack.class).set();

			float chargeToUse = 1f;

			if (hero.buff(DashTracker.class) != null) {
				chargeToUse *= 0.5f;
				hero.buff(DashTracker.class).detach();
			}
			if (hero.hasTalent(Talent.CLAM_STEPS)) {
				chargeToUse *= Math.pow(0.795, hero.pointsInTalent(Talent.CLAM_STEPS));
			}
			Talent.AgressiveMovementAbilityTracker tracker = hero.buff(Talent.AgressiveMovementAbilityTracker.class);
			if (tracker != null && tracker.wepAbilUsed){
				chargeToUse *= 0.5f;
				tracker.detach();
			}
			hero.buff(Charger.class).useCharges(chargeToUse);

			if (hero.hasTalent(Talent.KINETIC_MOVEMENT)) {
				Buff.affect(hero, DashTracker.class).set();
			}

			Item.updateQuickslot();
			AttackIndicator.updateState();
		}
		@Override
		public String prompt() {
			return Messages.get(SpiritBow.class, "prompt");
		}
	};

	public static class DashAttack extends Buff {
		{
			announced = false;
			type = buffType.POSITIVE;
		}

		int duration = 0;
		float dmgMulti = 1.1f;

		public void set() {
			dmgMulti = 1.1f * (float)Math.pow(1.05, hero.pointsInTalent(Talent.CRITICAL_MOMENTUM));
		}

		public float getDmgMulti() {
			return dmgMulti;
		}

		@Override
		public boolean act() {
			duration --;
			if (duration <= 0) {
				detach();
			}
			spend(TICK);
			return true;
		}

		public static final String DURATION          = "duration";
		public static final String DMG_MULTI         = "dmgMulti";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(DURATION, duration);
			bundle.put(DMG_MULTI, dmgMulti);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			duration = bundle.getInt(DURATION);
			dmgMulti = bundle.getFloat(DMG_MULTI);
		}
	}

	public static class DashTracker extends Buff {
		{
			announced = false;
			type = buffType.POSITIVE;
		}

		float maxDuration;
		float duration;

		public void set() {
			maxDuration = hero.cooldown()+(hero.pointsInTalent(Talent.KINETIC_MOVEMENT));
			duration = maxDuration;
		}

		@Override
		public boolean act() {
			duration -= 1;
			if (duration <= 0) {
				detach();
			}
			spend(TICK);
			return true;
		}

		@Override
		public int icon() {
			return BuffIndicator.TIME;
		}

		public float iconFadePercent() {
			return Math.max(0, 1-duration / maxDuration);
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc", Messages.decimalFormat("#.##", duration));
		}

		public static final String DURATION          = "duration";
		public static final String MAX_DURATION      = "maxDuration";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(DURATION, duration);
			bundle.put(MAX_DURATION, maxDuration);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			duration = bundle.getFloat(DURATION);
			maxDuration = bundle.getFloat(MAX_DURATION);
		}
	}

	public static class PlaceHolder extends MeleeWeapon {

		{
			image = ItemSpriteSheet.WEAPON_HOLDER;
		}

		@Override
		public boolean isSimilar(Item item) {
			return item instanceof MeleeWeapon && !item.isEquipped(hero) && !cursed && item.isIdentified();
		}

		@Override
		public String info() {
			return "";
		}
	}
}
