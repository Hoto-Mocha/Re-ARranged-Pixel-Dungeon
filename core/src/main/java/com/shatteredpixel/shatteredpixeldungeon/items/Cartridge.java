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

package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.InventorySpell;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Carbine;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrower;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FrostGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncherAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncherHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Handgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSG;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Magnum;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ParalysisGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Revolver;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SleepGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class Cartridge extends InventorySpell {

    {
        image = ItemSpriteSheet.CARTRIDGE;
        stackable = true;

        unique = true;
        bones = false;
    }

    @Override
    protected boolean usableOnItem(Item item) {
        return item instanceof CrudePistol
                || item instanceof Pistol
                || item instanceof GoldenPistol
                || item instanceof Handgun
                || item instanceof Magnum
                || item instanceof TacticalHandgun
                || item instanceof AutoHandgun

                || item instanceof DualPistol
                || item instanceof SubMachinegun
                || item instanceof AssultRifle
                || item instanceof HeavyMachinegun
                || item instanceof MiniGun
                || item instanceof AutoRifle

                || item instanceof Revolver
                || item instanceof HuntingRifle
                || item instanceof Carbine
                || item instanceof SniperRifle
                || item instanceof AntimaterRifle
                || item instanceof MarksmanRifle
                || item instanceof WA2000

                || item instanceof ShotGun
                || item instanceof KSG

                || item instanceof FlameThrower
                || item instanceof PlasmaCannon

                || item instanceof GrenadeLauncherHP
                || item instanceof GrenadeLauncherAP

                || item instanceof ParalysisGun
                || item instanceof FrostGun

                || item instanceof StunGunHP
                || item instanceof StunGunAP;
    }

    @Override
    protected void onItemSelected(Item item) {

        Item result = changeItem(item);

        if (result == null){
            //This shouldn't ever trigger
            GLog.n( Messages.get(this, "nothing") );
            curItem.collect( curUser.belongings.backpack );
        } else {
            if (item.isEquipped(Dungeon.hero)){
                item.cursed = false; //to allow it to be unequipped
                ((EquipableItem)item).doUnequip(Dungeon.hero, false);
                ((EquipableItem)result).doEquip(Dungeon.hero);
            } else {
                item.detach(Dungeon.hero.belongings.backpack);
                if (!result.collect()){
                    Dungeon.level.drop(result, curUser.pos).sprite.drop();
                }
            }
            if (result.isIdentified()){
                Catalog.setSeen(result.getClass());
            }
            hero.sprite.emitter().start( Speck.factory( Speck.UP ), 0.2f, 3 );
            GLog.p( Messages.get(this, "load") );
        }

    }

    public static Item changeItem( Item item ){
        if (item instanceof MeleeWeapon) {
            return changeWeapon((Weapon) item);
        } else if (item instanceof StunGunAP || item instanceof StunGunHP){
            return new StunGun();
        } else {
            return null;
        }
    }

    private static Weapon changeWeapon( Weapon w ) {

        Weapon n;

        if (w instanceof CrudePistol) {
            n = new CrudePistol();
        } else if (w instanceof Pistol) {
            n = new Pistol();
        } else if (w instanceof GoldenPistol) {
            n = new GoldenPistol();
        } else if (w instanceof Handgun) {
            n = new HandgunAP();
        } else if (w instanceof Magnum) {
            n = new Magnum();
        } else if (w instanceof TacticalHandgun) {
            n = new TacticalHandgun();
        } else if (w instanceof AutoHandgun) {
            n = new AutoHandgun();
        } else if (w instanceof DualPistol) {
            n = new DualPistol();
        } else if (w instanceof SubMachinegun) {
            n = new SubMachinegun();
        } else if (w instanceof AssultRifle) {
            n = new AssultRifle();
        } else if (w instanceof HeavyMachinegun) {
            n = new HeavyMachinegun();
        } else if (w instanceof MiniGun) {
            n = new MiniGun();
        } else if (w instanceof AutoRifle) {
            n = new AutoRifle();
        } else if (w instanceof Revolver) {
            n = new Revolver();
        } else if (w instanceof HuntingRifle) {
            n = new HuntingRifle();
        } else if (w instanceof Carbine) {
            n = new Carbine();
        } else if (w instanceof SniperRifle) {
            n = new SniperRifle();
        } else if (w instanceof AntimaterRifle) {
            n = new AntimaterRifle();
        } else if (w instanceof MarksmanRifle) {
            n = new MarksmanRifle();
        } else if (w instanceof WA2000) {
            n = new WA2000();
        } else if (w instanceof ShotGun) {
            n = new ShotGun();
        } else if (w instanceof KSG) {
            n = new KSG();
        } else if (w instanceof FlameThrower) {
            n = new FlameThrower();
        } else if (w instanceof PlasmaCannon) {
            n = new PlasmaCannon();
        } else if (w instanceof GrenadeLauncherHP || w instanceof GrenadeLauncherAP){
            n = new GrenadeLauncher();
        } else { //(w instanceof FrostGun || w instanceof ParalysisGun)
            n = new SleepGun();
        }

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

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public int value() {
        return quantity * 40;
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe{
        {
            inputs =  new Class[]{LiquidMetal.class};
            inQuantity = new int[]{40};

            cost = 3;

            output = Cartridge.class;
            outQuantity = 1;
        }
    }}
