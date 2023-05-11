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
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.StunGun;
import com.shatteredpixel.shatteredpixeldungeon.items.StunGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.StunGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Carbine;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CarbineHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrower;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameThrowerHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FrostGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncherAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncherHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Handgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSG;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSGHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Magnum;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ParalysisGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PlasmaCannonHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Revolver;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RevolverHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SleepGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000HP;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class StableHPBullet extends InventorySpell {

    {
        image = ItemSpriteSheet.HP_BULLET;

        unique = true;
        bones = false;
    }

    private static final ItemSprite.Glowing WHITE = new ItemSprite.Glowing( 0xFFFFFF );

    @Override
    public ItemSprite.Glowing glowing() {
        return WHITE;
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

        if (w instanceof CrudePistol) {
            n = new CrudePistolHP();
        } else if (w instanceof Pistol) {
            n = new PistolHP();
        } else if (w instanceof GoldenPistol) {
            n = new GoldenPistolHP();
        } else if (w instanceof Handgun) {
            n = new HandgunHP();
        } else if (w instanceof Magnum) {
            n = new MagnumHP();
        } else if (w instanceof TacticalHandgun) {
            n = new TacticalHandgunHP();
        } else if (w instanceof AutoHandgun) {
            n = new AutoHandgunHP();
        } else if (w instanceof DualPistol) {
            n = new DualPistolHP();
        } else if (w instanceof SubMachinegun) {
            n = new SubMachinegunHP();
        } else if (w instanceof AssultRifle) {
            n = new AssultRifleHP();
        } else if (w instanceof HeavyMachinegun) {
            n = new HeavyMachinegunHP();
        } else if (w instanceof MiniGun) {
            n = new MiniGunHP();
        } else if (w instanceof AutoRifle) {
            n = new AutoRifleHP();
        } else if (w instanceof Revolver) {
            n = new RevolverHP();
        } else if (w instanceof HuntingRifle) {
            n = new HuntingRifleHP();
        } else if (w instanceof Carbine) {
            n = new CarbineHP();
        } else if (w instanceof SniperRifle) {
            n = new SniperRifleHP();
        } else if (w instanceof AntimaterRifle) {
            n = new AntimaterRifleHP();
        } else if (w instanceof MarksmanRifle) {
            n = new MarksmanRifleHP();
        } else if (w instanceof WA2000) {
            n = new WA2000HP();
        } else if (w instanceof ShotGun) {
            n = new ShotGunHP();
        } else if (w instanceof KSG) {
            n = new KSGHP();
        } else if (w instanceof FlameThrower) {
            n = new FlameThrowerHP();
        } else if (w instanceof PlasmaCannon) {
            n = new PlasmaCannonHP();
        } else if (w instanceof GrenadeLauncher || w instanceof GrenadeLauncherAP || w instanceof GrenadeLauncherHP){
            n = new GrenadeLauncherHP();
        } else { //(w instanceof SleepGun || w instanceof FrostGun)
            n = new ParalysisGun();
        }

        int level = w.level();
        if (w.curseInfusionBonus) level -= 1 + level/6;
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
    public int value() {
        return quantity * 175;
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe{
        {
            inputs =  new Class[]{HPBullet.class, Evolution.class};
            inQuantity = new int[]{1, 1};

            cost = 15;

            output = StableHPBullet.class;
            outQuantity = 1;
        }
    }
}