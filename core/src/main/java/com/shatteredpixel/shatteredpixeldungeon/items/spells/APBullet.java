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

package com.shatteredpixel.shatteredpixeldungeon.items.spells;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Cartridge;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.StunGun;
import com.shatteredpixel.shatteredpixeldungeon.items.StunGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.StunGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAugmentation;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Carbine;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CarbineAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrower;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrowerAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FrostGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncherAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncherHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Handgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSG;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSGAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Magnum;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ParalysisGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannonAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Revolver;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RevolverAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SleepGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000AP;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class APBullet extends InventorySpell {

    {
        image = ItemSpriteSheet.AP_BULLET;

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

                || item instanceof GrenadeLauncher
                || item instanceof GrenadeLauncherHP

                || item instanceof SleepGun
                || item instanceof ParalysisGun

                || item instanceof StunGun
                || item instanceof StunGunHP;
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
        } else if (item instanceof StunGun || item instanceof StunGunHP){
            return new StunGunAP();
        } else {
            return null;
        }
    }

    private static Weapon changeWeapon( Weapon w ) {

        Weapon n;

        if (w instanceof CrudePistol) {
            n = new CrudePistolAP();
        } else if (w instanceof Pistol) {
            n = new PistolAP();
        } else if (w instanceof GoldenPistol) {
            n = new GoldenPistolAP();
        } else if (w instanceof Handgun) {
            n = new HandgunAP();
        } else if (w instanceof Magnum) {
            n = new MagnumAP();
        } else if (w instanceof TacticalHandgun) {
            n = new TacticalHandgunAP();
        } else if (w instanceof AutoHandgun) {
            n = new AutoHandgunAP();
        } else if (w instanceof DualPistol) {
            n = new DualPistolAP();
        } else if (w instanceof SubMachinegun) {
            n = new SubMachinegunAP();
        } else if (w instanceof AssultRifle) {
            n = new AssultRifleAP();
        } else if (w instanceof HeavyMachinegun) {
            n = new HeavyMachinegunAP();
        } else if (w instanceof MiniGun) {
            n = new MiniGunAP();
        } else if (w instanceof AutoRifle) {
            n = new AutoRifleAP();
        } else if (w instanceof Revolver) {
            n = new RevolverAP();
        } else if (w instanceof HuntingRifle) {
            n = new HuntingRifleAP();
        } else if (w instanceof Carbine) {
            n = new CarbineAP();
        } else if (w instanceof SniperRifle) {
            n = new SniperRifleAP();
        } else if (w instanceof AntimaterRifle) {
            n = new AntimaterRifleAP();
        } else if (w instanceof MarksmanRifle) {
            n = new MarksmanRifleAP();
        } else if (w instanceof WA2000) {
            n = new WA2000AP();
        } else if (w instanceof ShotGun) {
            n = new ShotGunAP();
        } else if (w instanceof KSG) {
            n = new KSGAP();
        } else if (w instanceof FlameThrower) {
            n = new FlameThrowerAP();
        } else if (w instanceof PlasmaCannon) {
            n = new PlasmaCannonAP();
        } else if (w instanceof GrenadeLauncher || w instanceof GrenadeLauncherHP || w instanceof GrenadeLauncherAP){
            n = new GrenadeLauncherAP();
        } else { //(w instanceof SleepGun || w instanceof ParalysisGun)
            n = new FrostGun();
        }

        //int level = w.level();
        //if (w.curseInfusionBonus) level -= 1 + level/6;
        //if (level > 0) {
        //    n.upgrade( level );
        //} else if (level < 0) {
        //    n.degrade( -level );
        //}

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
    public int value() {
        //prices of ingredients, divided by output quantity
        return Math.round(quantity * 100);
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe{
        {
            inputs =  new Class[]{Cartridge.class, LiquidMetal.class, StoneOfAugmentation.class};
            inQuantity = new int[]{1, 30, 1};

            cost = 3;

            output = APBullet.class;
            outQuantity = 1;
        }
    }
}