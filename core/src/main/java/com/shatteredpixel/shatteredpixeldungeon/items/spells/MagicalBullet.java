/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2022 Evan Debenham
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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.GunEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WeaponEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Cartridge;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

import java.text.DecimalFormat;

public class MagicalBullet extends Spell {
	
	{
		image = ItemSpriteSheet.MAGICAL_BULLET;
	}
	
	@Override
	protected void onCast(Hero hero) {
		if (hero.heroClass == HeroClass.GUNNER) {
			GunEmpower buff = hero.buff(GunEmpower.class);
			if (buff != null) {
				Buff.affect(hero, GunEmpower.class).levelUp();
			} else {
				Buff.affect(hero, GunEmpower.class).reset(1, GunEmpower.DURATION);
			}
		} else {
			Buff.affect(hero, WeaponEmpower.class).set(1, 30);
		}

		hero.sprite.operate(hero.pos);
		Sample.INSTANCE.play( Assets.Sounds.EVOKE );
		CellEmitter.center( hero.pos ).burst( Speck.factory( Speck.STAR ), 7 );
		if (hero.heroClass == HeroClass.GUNNER) {
			GLog.p(Messages.get(this, "empower"));
		} else {
			GLog.p(Messages.get(this, "empower_else"));
		}
		
		detach( curUser.belongings.backpack );
		updateQuickslot();
		hero.spendAndNext( 1f );
	}

	@Override
	public String info() {
		String info = desc();

		if (hero.heroClass == HeroClass.GUNNER) {
			info += "\n\n" + Messages.get( this, "desc_gunner", new DecimalFormat("#").format(Math.ceil(hero.lvl/5f)));
		} else {
			info += "\n\n" + Messages.get( this, "desc_else");
		}

		return info;
	}
	
	@Override
	public int value() {
		//prices of ingredients
		return Math.round(quantity * 25);
	}
	
	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {
		
		{
			inputs =  new Class[]{ArcaneCatalyst.class, Cartridge.class};
			inQuantity = new int[]{1, 1};
			
			cost = 2;
			
			output = MagicalBullet.class;
			outQuantity = 1;
		}
		
	}
}
