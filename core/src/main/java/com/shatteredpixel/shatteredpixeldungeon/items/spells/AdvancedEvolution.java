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

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Transmuting;
import com.shatteredpixel.shatteredpixeldungeon.items.ArcaneResin;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.GoldenBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.NaturesBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.PoisonBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.WindBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssassinsBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.BeamSaber;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ChainWhip;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Crossbow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ExplosiveCrossbow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gauntlet;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GildedShovel;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Glaive;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Greataxe;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Greatshield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HugeSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.IronHammer;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSG;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSGAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSGHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Lance;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeKatana;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Magnum;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ObsidianShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RPG7;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RocketLauncher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RunicBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SharpKatana;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Shovel;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Spade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TrueRunicBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.UnformedBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WarHammer;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class AdvancedEvolution extends InventorySpell {

    {
        image = ItemSpriteSheet.AD_EVOLUTION;

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
        return (!(item instanceof Spade)
            && (item instanceof Weapon && ((Weapon)item).canAdvance));
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
            Transmuting.show(curUser, item, result);
            curUser.sprite.emitter().start(Speck.factory(Speck.CHANGE), 0.2f, 10);
            GLog.p( Messages.get(this, "evolve") );
        }

    }

    public static Item changeItem( Item item ){
        if (item instanceof MeleeWeapon
                || item instanceof SpiritBow
                || item instanceof WindBow
                || item instanceof NaturesBow
                || item instanceof GoldenBow
                || item instanceof PoisonBow
                || item instanceof MissileWeapon ) {
            return changeWeapon((Weapon) item);
        } else {
            return null;
        }
    }

    private static float getGunChance() {
        float gunChance = 0.7f;
        if (Dungeon.hero.heroClass == HeroClass.GUNNER) {
            gunChance = 1;
        }
        return gunChance;
    }

    private static float getWepChance() {
        float wepChance = 0.9f;
        if (Dungeon.hero.heroClass == HeroClass.DUELIST){
            wepChance = 1;
        }
        return wepChance;
    }

    private static Weapon changeWeapon( Weapon w ) {

        //Normal Weapon Success Rate: 90%
        //Normal Weapon Success Rate for Duelist: 100%
        //Gun Success Rate: 70%
        //Gus Success Rate for Gunner: 100%

        Weapon n;
        if (w instanceof SniperRifle) {
            if (w instanceof SniperRifleAP) {
                if (Random.Float() < getGunChance()) {
                    n = new AntimaterRifleAP();
                } else {
                    n = Generator.randomWeapon();
                }
            } else if (w instanceof SniperRifleHP) {
                if (Random.Float() < getGunChance()) {
                    n = new AntimaterRifleHP();
                } else {
                    n = Generator.randomWeapon();
                }
            } else {
                if (Random.Float() < getGunChance()) {
                    n = new AntimaterRifle();
                } else {
                    n = Generator.randomWeapon();
                }
            }
        } else if (w instanceof HeavyMachinegun) {
            if (w instanceof HeavyMachinegunAP) {
                if (Random.Float() < getGunChance()) {
                    n = new MiniGunAP();
                } else {
                    n = Generator.randomWeapon();
                }
            } else if (w instanceof HeavyMachinegunHP) {
                if (Random.Float() < getGunChance()) {
                    n = new MiniGunHP();
                } else {
                    n = Generator.randomWeapon();
                }
            } else {
                if (Random.Float() < getGunChance()) {
                    n = new MiniGun();
                } else {
                    n = Generator.randomWeapon();
                }
            }
        } else if (w instanceof Magnum) {
            if (w instanceof MagnumAP) {
                if (Random.Float() < getGunChance()) {
                    n = new TacticalHandgunAP();
                } else {
                    n = Generator.randomWeapon();
                }
            } else if (w instanceof MagnumHP) {
                if (Random.Float() < getGunChance()) {
                    n = new TacticalHandgunHP();
                } else {
                    n = Generator.randomWeapon();
                }
            } else {
                if (Random.Float() < getGunChance()) {
                    n = new TacticalHandgun();
                } else {
                    n = Generator.randomWeapon();
                }
            }
        } else if (w instanceof ShotGun) {
            if (w instanceof ShotGunAP) {
                if (Random.Float() < getGunChance()) {
                    n = new KSGAP();
                } else {
                    n = Generator.randomWeapon();
                }
            } else if (w instanceof ShotGunHP) {
                if (Random.Float() < getGunChance()) {
                    n = new KSGHP();
                } else {
                    n = Generator.randomWeapon();
                }
            } else {
                if (Random.Float() < getGunChance()) {
                    n = new KSG();
                } else {
                    n = Generator.randomWeapon();
                }
            }
        } else if (w instanceof RocketLauncher) {
            if (Random.Float() < getGunChance()) {
                n = new RPG7();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof LargeKatana) {
            if (Random.Float() < getWepChance()) {
                n = new SharpKatana();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof Glaive) {
            if (Random.Float() < getWepChance()) {
                n = new Lance();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof Greatshield) {
            if (Random.Float() < getWepChance()) {
                n = new ObsidianShield();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof RunicBlade){
            if (Random.Float() < getWepChance()) {
                n = new TrueRunicBlade();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof AssassinsBlade){
            if (Random.Float() < getWepChance()) {
                n = new UnformedBlade();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof GildedShovel) {
            n = new Spade();
        } else if (w instanceof Shovel) {
            n = new GildedShovel();
        } else if (w instanceof Greataxe) {
            if (Random.Float() < getWepChance()) {
                n = new HugeSword();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof WarHammer) {
            if (Random.Float() < getWepChance()) {
                n = new IronHammer();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof Gauntlet) {
            if (Random.Float() < getWepChance()) {
                n = new BeamSaber();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof SpiritBow) {
            switch (Random.Int(4)) {
                case 0:
                default:
                    n = new WindBow();
                    break;
                case 1:
                    n = new NaturesBow();
                    break;
                case 2:
                    n = new PoisonBow();
                    break;
                case 3:
                    n = new GoldenBow();
                    break;
            }
        } else if (w instanceof WindBow) {
            switch (Random.Int(3)) {
                case 0: default:
                    n = new NaturesBow();
                    break;
                case 1:
                    n = new PoisonBow();
                    break;
                case 2:
                    n = new GoldenBow();
                    break;
            }
        } else if (w instanceof NaturesBow) {
            switch (Random.Int(3)) {
                case 0: default:
                    n = new WindBow();
                    break;
                case 1:
                    n = new PoisonBow();
                    break;
                case 2:
                    n = new GoldenBow();
                    break;
            }
        } else if (w instanceof PoisonBow) {
            switch (Random.Int(3)) {
                case 0: default:
                    n = new WindBow();
                    break;
                case 1:
                    n = new NaturesBow();
                    break;
                case 2:
                    n = new GoldenBow();
                    break;
            }
        } else if (w instanceof GoldenBow) {
            switch (Random.Int(3)) {
                case 0: default:
                    n = new WindBow();
                    break;
                case 1:
                    n = new NaturesBow();
                    break;
                case 2:
                    n = new PoisonBow();
                    break;
            }
        } else if (w instanceof Crossbow) {
            if (Random.Float() < getWepChance()) {
                n = new ExplosiveCrossbow();
            } else {
                n = Generator.randomWeapon();
            }
        } else { //w instanceof Whip
            if (Random.Float() < getWepChance()) {
                n = new ChainWhip();
            } else {
                n = Generator.randomWeapon();
            }
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
        n.fix();

        return n;

    }

    @Override
    public String desc() {
        return super.desc() + "\n\n" + Messages.get(this, "chance", Messages.decimalFormat("#", 100f * getGunChance()), Messages.decimalFormat("#", 100f * getWepChance()));
    }

    @Override
    public int value() {
        //prices of ingredients, divided by output quantity
        return Math.round(quantity * 190);
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {
        {
                inputs =  new Class[]{Evolution.class, ArcaneCatalyst.class, ArcaneResin.class};
                inQuantity = new int[]{1, 1, 2};

                cost = 5;

                output = AdvancedEvolution.class;
                outQuantity = 1;
        }
    }
}
