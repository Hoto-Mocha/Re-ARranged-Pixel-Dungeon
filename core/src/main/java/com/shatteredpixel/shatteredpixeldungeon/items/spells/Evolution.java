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
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Transmuting;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FrostGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncherAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GrenadeLauncherHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ParalysisGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SleepGun;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

public class Evolution extends InventorySpell {

    {
        image = ItemSpriteSheet.EVOLUTION;

        unique = true;
    }

    @Override
    protected boolean usableOnItem(Item item) {
        return (item instanceof MeleeWeapon
                && (!(item instanceof GrenadeLauncher))
                && (!(item instanceof GrenadeLauncherAP))
                && (!(item instanceof GrenadeLauncherHP))
                && (!(item instanceof SleepGun))
                && (!(item instanceof FrostGun))
                && (!(item instanceof ParalysisGun)));
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
        if (item instanceof MeleeWeapon) {
            return changeWeapon((Weapon) item);
        } else {
            return null;
        }
    }

    private static Weapon changeWeapon( Weapon w ) {

        Weapon n;
        Generator.Category c;
        if (((MeleeWeapon)w).tier == 7) {
            if (Dungeon.hero.hasTalent(Talent.TRANSMUTATION_CONTROL)) {
                c = Generator.gunTiers[((MeleeWeapon)w).tier - 3];
            } else {
                c = Generator.wepTiers[((MeleeWeapon)w).tier - 3];
            }
        } else if (((MeleeWeapon)w).tier == 6) {
            if (Dungeon.hero.hasTalent(Talent.TRANSMUTATION_CONTROL)) {
                c = Generator.gunTiers[((MeleeWeapon)w).tier - 2];
            } else {
                c = Generator.wepTiers[((MeleeWeapon)w).tier - 2];
            }
        } else if (((MeleeWeapon)w).tier == 5){
            if (Dungeon.hero.hasTalent(Talent.TRANSMUTATION_CONTROL)) {
                c = Generator.gunTiers[((MeleeWeapon)w).tier - 1];
            } else {
                c = Generator.wepTiers[((MeleeWeapon)w).tier - 1];
            }
        } else {
            if (Dungeon.hero.hasTalent(Talent.TRANSMUTATION_CONTROL)) {
                c = Generator.gunTiers[((MeleeWeapon)w).tier];
            } else {
                c = Generator.wepTiers[((MeleeWeapon)w).tier];
            }
        }

        do {
            n = (Weapon) Reflection.newInstance(c.classes[Random.chances(c.probs)]);
        } while (Challenges.isItemBlocked(n) || n.getClass() == w.getClass());

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
        if (Dungeon.isChallenged(Challenges.DURABILITY)) {
            n.fix();
        }

        return n;

    }

    @Override
    public int value() {
        //prices of ingredients, divided by output quantity
        return Math.round(quantity * (90));
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {
        {
            inputs =  new Class[]{ScrollOfTransmutation.class, ArcaneCatalyst.class};
            inQuantity = new int[]{1, 1};

            cost = 3;

            output = Evolution.class;
            outQuantity = 1;
        }
    }
}
