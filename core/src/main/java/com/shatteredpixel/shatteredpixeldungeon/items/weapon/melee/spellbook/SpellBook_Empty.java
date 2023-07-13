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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.spellbook;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArcaneArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Awareness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EvasiveMove;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpellBookCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.effects.Enchanting;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Transmuting;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.EnergyParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SpellBook_Empty extends SpellBook {

	public static final String AC_INFUSE		= "INFUSE";
	public static final String AC_TRANSMUTE		= "TRANSMUTE";

	{
		image = ItemSpriteSheet.EMPTY_SPELLBOOK;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;

		tier = 3;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (Dungeon.hero.subClass == HeroSubClass.WIZARD) {
			actions.add(AC_INFUSE);
			actions.add(AC_TRANSMUTE);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_INFUSE)) {
			if (this.enchantment != null) {
				GLog.w( Messages.get(this, "already_infused") );
				return;
			} else {
				this.enchant();
				GLog.p( Messages.get(this, "infuse") );

				curUser.sprite.emitter().start( Speck.factory( Speck.LIGHT ), 0.1f, 5 );
				Enchanting.show( curUser, this );

				curUser.spend( 1f );
				curUser.busy();
				curUser.sprite.operate(curUser.pos);

				Sample.INSTANCE.play( Assets.Sounds.READ );
				Invisibility.dispel();
			}
		}
		if (action.equals(AC_TRANSMUTE)) {
			Item result = changeWeapon(this);
			if (result != this) {
				int slot = Dungeon.quickslot.getSlot(this);
				if (this.isEquipped(Dungeon.hero)) {
					this.cursed = false; //to allow it to be unequipped
					if (Dungeon.hero.belongings.secondWep() == this){
						((EquipableItem) this).doUnequip(Dungeon.hero, false);
						((KindOfWeapon) result).equipSecondary(Dungeon.hero);
					} else {
						((EquipableItem) this).doUnequip(Dungeon.hero, false);
						((EquipableItem) result).doEquip(Dungeon.hero);
					}
					Dungeon.hero.spend(-Dungeon.hero.cooldown()); //cancel equip/unequip time
				} else {
					this.detach(Dungeon.hero.belongings.backpack);
					if (!result.collect()) {
						Dungeon.level.drop(result, curUser.pos).sprite.drop();
					} else if (Dungeon.hero.belongings.getSimilar(result) != null){
						result = Dungeon.hero.belongings.getSimilar(result);
					}
				}
				if (slot != -1
						&& result.defaultAction() != null
						&& !Dungeon.quickslot.isNonePlaceholder(slot)
						&& Dungeon.hero.belongings.contains(result)){
					Dungeon.quickslot.setSlot(slot, result);
				}
			}
			if (result.isIdentified()){
				Catalog.setSeen(result.getClass());
			}

			Transmuting.show(curUser, this, result);
			curUser.sprite.emitter().start(Speck.factory(Speck.CHANGE), 0.2f, 10);
			GLog.p( Messages.get(this, "transmute") );
		}
		if (action.equals(AC_READ)) {
			if (hero.buff(SpellBookCoolDown.class) != null) {
				return;
			} else if (!isIdentified()) {
				return;
			}
			readEffect(hero, true);
		}
	}

	@Override
	public void readEffect(Hero hero, boolean busy) {
		needAnimation = busy;

		switch(Random.Int( 10 )){
			case 0: default:
				Buff.affect(hero, Recharging.class, 20f+buffedLvl());
				GLog.p( Messages.get(this, "recharge") );
				break;
			case 1:
				Buff.affect(hero, ArtifactRecharge.class).set(10f+buffedLvl());
				GLog.p( Messages.get(this, "artifact") );
				break;
			case 2:
				Buff.affect(hero, Bless.class, 20f+buffedLvl());
				GLog.p( Messages.get(this, "bless") );
				break;
			case 3:
				Buff.affect(hero, Adrenaline.class, 10f+buffedLvl());
				GLog.p( Messages.get(this, "adrenaline") );
				break;
			case 4:
				Buff.affect(hero, Barrier.class).setShield(Dungeon.depth+buffedLvl()+5);
				GLog.p( Messages.get(this, "barrier") );
				break;
			case 5:
				Buff.affect(hero, Barkskin.class).set(Dungeon.depth/2+buffedLvl()+5, 1);
				GLog.p( Messages.get(this, "barkskin") );
				break;
			case 6:
				Buff.affect(hero, Invisibility.class, 10f+buffedLvl());
				GLog.p( Messages.get(this, "invisibility") );
				break;
			case 7:
				Buff.affect(hero, ArcaneArmor.class).set(Dungeon.depth/2+buffedLvl()+5, 1);
				GLog.p( Messages.get(this, "arcane_armor") );
				break;
			case 8:
				Buff.affect(hero, Awareness.class, 2f);
				GLog.p( Messages.get(this, "awareness") );
				break;
			case 9:
				Buff.affect(hero, EvasiveMove.class, 3f);
				GLog.p( Messages.get(this, "evasive_move") );
				break;
		}
		Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
		Emitter e = hero.sprite.centerEmitter();
		if (e != null) e.burst(EnergyParticle.FACTORY, 15);

		if (needAnimation) {
			readAnimation();
		}
	}

	private static Weapon changeWeapon(Weapon w ) {
		Weapon n;
		n = (Weapon)Generator.randomUsingDefaults(Generator.Category.SPELLBOOK);

		int level = w.level();
		if (w.curseInfusionBonus) level--;
		if (level > 0) {
			n.upgrade( level );
		} else if (level < 0) {
			n.degrade( -level );
		}

		n.enchantment = w.enchantment;
		n.curseInfusionBonus = w.curseInfusionBonus;
		n.masteryPotionBonus = w.masteryPotionBonus;
		n.levelKnown = w.levelKnown;
		n.cursedKnown = w.cursedKnown;
		n.cursed = w.cursed;
		n.augment = w.augment;
		n.isUpgraded = w.isUpgraded;
		return n;
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{WandOfMagicMissile.class};
			inQuantity = new int[]{1};

			cost = 5;

			output = SpellBook_Empty.class;
			outQuantity = 1;
		}
	}

}
