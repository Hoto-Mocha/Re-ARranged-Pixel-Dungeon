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

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Transmuting;
import com.shatteredpixel.shatteredpixeldungeon.items.ArcaneResin;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.GoldenBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.NaturesBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.PoisonBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.WindBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.BeamSaber;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gauntlet;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Glaive;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Greataxe;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Greatshield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Greatsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HugeSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.IronHammer;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Lance;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LanceNShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgunHP;
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
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SPAS;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SPASAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SPASHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SharpKatana;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Shovel;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Spade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TrueRunicBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WarHammer;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

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
        return item instanceof SniperRifle
            || item instanceof SniperRifleHP
            || item instanceof SniperRifleAP
            || item instanceof HeavyMachinegun
            || item instanceof HeavyMachinegunHP
            || item instanceof HeavyMachinegunAP
            || item instanceof Magnum
            || item instanceof MagnumAP
            || item instanceof MagnumHP
            || item instanceof ShotGun
            || item instanceof ShotGunAP
            || item instanceof ShotGunHP
            || item instanceof RocketLauncher
            || item instanceof LargeKatana
            || item instanceof Glaive
            || item instanceof Greatshield
            || item instanceof RunicBlade
            || item instanceof Shovel
            || item instanceof Greataxe
            || item instanceof WarHammer
            || item instanceof Gauntlet
            || item instanceof SpiritBow
            || item instanceof WindBow
            || item instanceof NaturesBow
            || item instanceof GoldenBow
            || item instanceof PoisonBow;

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
                || item instanceof PoisonBow ) {
            return changeWeapon((Weapon) item);
        } else {
            return null;
        }
    }

    private static Weapon changeWeapon( Weapon w ) {

        //Normal Weapon Success Rate: 90%
        //Gun Success Rate: 70%
        //Gus Success Rate for Gunner: 100%

        Weapon n;

        if (w instanceof SniperRifle
                || w instanceof SniperRifleHP
                || w instanceof SniperRifleAP) {
            if (w instanceof SniperRifle) {
                if (Dungeon.hero.heroClass == HeroClass.GUNNER || Random.Int(10) < 7) {
                    n = new AntimaterRifle();
                } else {
                    n = Generator.randomWeapon();
                }
            } else if (w instanceof SniperRifleAP) {
                if (Dungeon.hero.heroClass == HeroClass.GUNNER || Random.Int(10) < 7) {
                    n = new AntimaterRifleAP();
                } else {
                    n = Generator.randomWeapon();
                }
            } else { //w instanceof SniperRifleHP
                if (Dungeon.hero.heroClass == HeroClass.GUNNER || Random.Int(10) < 7) {
                    n = new AntimaterRifleHP();
                } else {
                    n = Generator.randomWeapon();
                }
            }
        } else if (w instanceof HeavyMachinegun
                || w instanceof HeavyMachinegunAP
                || w instanceof HeavyMachinegunHP) {
            if (w instanceof HeavyMachinegun) {
                if (Dungeon.hero.heroClass == HeroClass.GUNNER || Random.Int(10) < 7) {
                    n = new MiniGun();
                } else {
                    n = Generator.randomWeapon();
                }
            } else if (w instanceof HeavyMachinegunAP) {
                if (Dungeon.hero.heroClass == HeroClass.GUNNER || Random.Int(10) < 7) {
                    n = new MiniGunAP();
                } else {
                    n = Generator.randomWeapon();
                }
            } else { //w instanceof HeavyMachinegunHP
                if (Dungeon.hero.heroClass == HeroClass.GUNNER || Random.Int(10) < 7) {
                    n = new MiniGunHP();
                } else {
                    n = Generator.randomWeapon();
                }
            }
        } else if (w instanceof Magnum
                || w instanceof MagnumAP
                || w instanceof MagnumHP) {
            if (w instanceof Magnum) {
                if (Dungeon.hero.heroClass == HeroClass.GUNNER || Random.Int(10) < 7) {
                    n = new LargeHandgun();
                } else {
                    n = Generator.randomWeapon();
                }
            } else if (w instanceof MagnumAP) {
                if (Dungeon.hero.heroClass == HeroClass.GUNNER || Random.Int(10) < 7) {
                    n = new LargeHandgunAP();
                } else {
                    n = Generator.randomWeapon();
                }
            } else { //w instanceof MagnumHP
                if (Dungeon.hero.heroClass == HeroClass.GUNNER || Random.Int(10) < 7) {
                    n = new LargeHandgunHP();
                } else {
                    n = Generator.randomWeapon();
                }
            }
        } else if (w instanceof ShotGun
                || w instanceof ShotGunAP
                || w instanceof ShotGunHP) {
            if (w instanceof ShotGun) {
                if (Dungeon.hero.heroClass == HeroClass.GUNNER || Random.Int(10) < 7) {
                    n = new SPAS();
                } else {
                    n = Generator.randomWeapon();
                }
            } else if (w instanceof ShotGunAP) {
                if (Dungeon.hero.heroClass == HeroClass.GUNNER || Random.Int(10) < 7) {
                    n = new SPASAP();
                } else {
                    n = Generator.randomWeapon();
                }
            } else { //w instanceof ShotGunHP
                if (Dungeon.hero.heroClass == HeroClass.GUNNER || Random.Int(10) < 7) {
                    n = new SPASHP();
                } else {
                    n = Generator.randomWeapon();
                }
            }
        } else if (w instanceof RocketLauncher) {
            if (Dungeon.hero.heroClass == HeroClass.GUNNER || Random.Int(10) < 7) {
                n = new RPG7();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof LargeKatana) {
            if (Random.Int(10) < 9) {
                n = new SharpKatana();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof Glaive) {
            if (Random.Int(10) < 9) {
                n = new Lance();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof Greatshield) {
            if (Random.Int(10) < 9) {
                n = new ObsidianShield();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof RunicBlade){
            if (Random.Int(10) < 9) {
                n = new TrueRunicBlade();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof Shovel) {
            n = new Spade();
        } else if (w instanceof Greataxe) {
            if (Random.Int(10) < 9) {
                n = new HugeSword();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof WarHammer) {
            if (Random.Int(10) < 9) {
                n = new IronHammer();
            } else {
                n = Generator.randomWeapon();
            }
        } else if (w instanceof Gauntlet) {
            if (Random.Int(10) < 9) {
                n = new BeamSaber();
            } else {
                n = Generator.randomWeapon();
            }
        } else { //w instanceof SpiritBow || w instanceof WindBow || w instanceof NaturesBow || w instanceof GoldenBow || w instanceof PoisonBow
            int proc = Random.Int(8);
            if (proc <= 3) {
                n = new WindBow(); //50%
            } else if (proc == 4) {
                n = new NaturesBow(); //12.5%
            } else if (proc == 5) {
                n = new PoisonBow(); //12.5%
            } else {
                n = new GoldenBow(); //25%
            }
            n.enchantment = w.enchantment;
            n.curseInfusionBonus = w.curseInfusionBonus;
            n.masteryPotionBonus = w.masteryPotionBonus;
            n.levelKnown = w.levelKnown;
            n.cursedKnown = w.cursedKnown;
            n.cursed = w.cursed;
            n.augment = w.augment;

            return n;
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

        return n;

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
