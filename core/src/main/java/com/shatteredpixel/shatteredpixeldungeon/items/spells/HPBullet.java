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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Carbine;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CarbineAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CarbineHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrower;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrowerAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrowerHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FrostGun;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Revolver;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RevolverAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RevolverHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Magnum;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ParalysisGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannonAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannonHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSG;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSGAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSGHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SleepGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000AP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000HP;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class HPBullet extends InventorySpell {

    {
        image = ItemSpriteSheet.HP_BULLET;

        unique = true;
        bones = false;
    }

    @Override
    protected boolean usableOnItem(Item item) {
        return item instanceof CrudePistol
                || item instanceof CrudePistolAP
                || item instanceof Pistol
                || item instanceof PistolAP
                || item instanceof GoldenPistol
                || item instanceof GoldenPistolAP
                || item instanceof Handgun
                || item instanceof HandgunAP
                || item instanceof Magnum
                || item instanceof MagnumAP
                || item instanceof TacticalHandgun
                || item instanceof TacticalHandgunAP
                || item instanceof AutoHandgun
                || item instanceof AutoHandgunAP

                || item instanceof DualPistol
                || item instanceof DualPistolAP
                || item instanceof SubMachinegun
                || item instanceof SubMachinegunAP
                || item instanceof AssultRifle
                || item instanceof AssultRifleAP
                || item instanceof HeavyMachinegun
                || item instanceof HeavyMachinegunAP
                || item instanceof MiniGun
                || item instanceof MiniGunAP
                || item instanceof AutoRifle
                || item instanceof AutoRifleAP

                || item instanceof Revolver
                || item instanceof RevolverAP
                || item instanceof HuntingRifle
                || item instanceof HuntingRifleAP
                || item instanceof Carbine
                || item instanceof CarbineAP
                || item instanceof SniperRifle
                || item instanceof SniperRifleAP
                || item instanceof AntimaterRifle
                || item instanceof AntimaterRifleAP
                || item instanceof MarksmanRifle
                || item instanceof MarksmanRifleAP
                || item instanceof WA2000
                || item instanceof WA2000AP

                || item instanceof ShotGun
                || item instanceof ShotGunAP
                || item instanceof KSG
                || item instanceof KSGAP

                || item instanceof FlameThrower
                || item instanceof FlameThrowerAP
                || item instanceof PlasmaCannon
                || item instanceof PlasmaCannonAP

                || item instanceof GrenadeLauncher
                || item instanceof GrenadeLauncherAP

                || item instanceof SleepGun
                || item instanceof FrostGun

                || item instanceof StunGun
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
            if (item.isEquipped(hero)){
                item.cursed = false; //to allow it to be unequipped
                ((EquipableItem)item).doUnequip(hero, false);
                ((EquipableItem)result).doEquip(hero);
            } else {
                item.detach(hero.belongings.backpack);
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
        } else if (item instanceof StunGun || item instanceof StunGunAP){
            return new StunGunHP();
        } else {
            return null;
        }
    }

    private static Weapon changeWeapon( Weapon w ) {

        Weapon n;

        if (w instanceof CrudePistol || w instanceof CrudePistolAP) {
            n = new CrudePistolHP();
        } else if (w instanceof Pistol || w instanceof PistolAP) {
            n = new PistolHP();
        } else if (w instanceof GoldenPistol || w instanceof GoldenPistolAP) {
            n = new GoldenPistolHP();
        } else if (w instanceof Handgun || w instanceof HandgunAP) {
            n = new HandgunHP();
        } else if (w instanceof Magnum || w instanceof MagnumAP) {
            n = new MagnumHP();
        } else if (w instanceof TacticalHandgun || w instanceof TacticalHandgunAP) {
            n = new TacticalHandgunHP();
        } else if (w instanceof AutoHandgun || w instanceof AutoHandgunAP) {
            n = new AutoHandgunHP();
        } else if (w instanceof DualPistol || w instanceof DualPistolAP) {
            n = new DualPistolHP();
        } else if (w instanceof SubMachinegun || w instanceof SubMachinegunAP) {
            n = new SubMachinegunHP();
        } else if (w instanceof AssultRifle || w instanceof AssultRifleAP) {
            n = new AssultRifleHP();
        } else if (w instanceof HeavyMachinegun || w instanceof HeavyMachinegunAP) {
            n = new HeavyMachinegunHP();
        } else if (w instanceof MiniGun || w instanceof MiniGunAP) {
            n = new MiniGunHP();
        } else if (w instanceof AutoRifle || w instanceof AutoRifleAP) {
            n = new AutoRifleHP();
        } else if (w instanceof Revolver || w instanceof RevolverAP) {
            n = new RevolverHP();
        } else if (w instanceof HuntingRifle || w instanceof HuntingRifleAP) {
            n = new HuntingRifleHP();
        } else if (w instanceof Carbine || w instanceof CarbineAP) {
            n = new CarbineHP();
        } else if (w instanceof SniperRifle || w instanceof SniperRifleAP) {
            n = new SniperRifleHP();
        } else if (w instanceof AntimaterRifle || w instanceof AntimaterRifleAP) {
            n = new AntimaterRifleHP();
        } else if (w instanceof MarksmanRifle || w instanceof MarksmanRifleAP) {
            n = new MarksmanRifleHP();
        } else if (w instanceof WA2000 || w instanceof WA2000AP) {
            n = new WA2000HP();
        } else if (w instanceof ShotGun || w instanceof ShotGunAP) {
            n = new ShotGunHP();
        } else if (w instanceof KSG || w instanceof KSGAP) {
            n = new KSGHP();
        } else if (w instanceof FlameThrower || w instanceof FlameThrowerAP) {
            n = new FlameThrowerHP();
        } else if (w instanceof PlasmaCannon || w instanceof PlasmaCannonAP) {
            n = new PlasmaCannonHP();
        } else if (w instanceof GrenadeLauncher || w instanceof GrenadeLauncherAP){
            n = new GrenadeLauncherHP();
        } else { //(w instanceof SleepGun || w instanceof FrostGun)
            n = new ParalysisGun();
        }

        //int level = w.level();
        //if (w.curseInfusionBonus) level--;
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

        return n;

    }

    @Override
    public int value() {
        return quantity * 85;
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe{
        {
            inputs =  new Class[]{Cartridge.class, LiquidMetal.class, StoneOfAugmentation.class};
            inQuantity = new int[]{1, 15, 1};

            cost = 3;

            output = HPBullet.class;
            outQuantity = 1;
        }
    }
}