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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Evolution;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.UpgradeDust;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeKatana;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.NormalKatana;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;
import java.util.Arrays;

public class SharpKatana extends MeleeWeapon implements AlchemyWeapon {

    {
        image = ItemSpriteSheet.SHARP_KATANA;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1.3f;

        tier = 6;
    }

    @Override
    public boolean doPickUp( Hero hero, int pos ) {
        Statistics.katanaObtained = true;
        Badges.validateSamuraiUnlock();
        return super.doPickUp(hero, pos);
    }

    @Override
    public int min(int lvl) {
        return 1;
    }

    @Override
    public int max(int lvl) {
        return  5*(tier+2) +
                lvl*(tier+2);
    }

    @Override
    public String targetingPrompt() {
        return Messages.get(this, "prompt");
    }

    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        NormalKatana.flashSlashAbility(hero, target, 0.35f, this);
    }

    @Override
    public String abilityInfo() {
        if (levelKnown){
            return Messages.get(this, "ability_desc", Messages.decimalFormat("#.##", 0.35f));
        } else {
            return Messages.get(this, "typical_ability_desc", Messages.decimalFormat("#.##", 0.35f));
        }
    }

    @Override
    public ArrayList<Class<?extends Item>> weaponRecipe() {
        return new ArrayList<>(Arrays.asList(LargeKatana.class, UpgradeDust.class, Evolution.class));
    }

    @Override
    public String discoverHint() {
        return AlchemyWeapon.hintString(weaponRecipe());
    }

}
