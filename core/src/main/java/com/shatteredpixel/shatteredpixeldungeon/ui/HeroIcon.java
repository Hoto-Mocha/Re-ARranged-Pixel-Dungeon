/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;

//icons for hero subclasses and abilities atm, maybe add classes?
public class HeroIcon extends Image {

	private static TextureFilm film;
	private static final int SIZE = 16;

	//transparent icon
	public static final int NONE    = 31;

	//subclasses
	public static final int BERSERKER   	= 0;
	public static final int GLADIATOR   	= 1;
	public static final int VETERAN     	= 2;
	//public static final int ???        	= 3;
	public static final int BATTLEMAGE  	= 4;
	public static final int WARLOCK     	= 5;
	public static final int ENGINEER    	= 6;
	//public static final int ???        	= 7;
	public static final int ASSASSIN    	= 8;
	public static final int FREERUNNER  	= 9;
	public static final int CHASER      	= 10;
	//public static final int ???        	= 11;
	public static final int SNIPER      	= 12;
	public static final int WARDEN      	= 13;
	public static final int FIGHTER     	= 14;
	//public static final int ???        	= 15;
	public static final int CHAMPION    	= 16;
	public static final int MONK        	= 17;
	public static final int FENCER       	= 18;
	//public static final int ???        	= 19;
	//public static final int ???		    = 20;
	//public static final int ???		    = 21;
	//public static final int ???        	= 22;
	//public static final int ???        	= 23;
	public static final int MARSHAL	    	= 24;
	public static final int GUNSLINGER     	= 25;
	public static final int SPECIALIST    	= 26;
	//public static final int ???        	= 27;
	public static final int SLASHER     	= 28;
	public static final int MASTER      	= 29;
	public static final int SLAYER  	    = 30;
	//public static final int ???        	= 31;
	public static final int TREASUREHUNTER	= 32;
	public static final int ADVENTURER		= 33;
	public static final int RESEARCHER		= 34;
	//public static final int ???        	= 35;
	public static final int WEAPONMASTER	= 36;
	public static final int FORTRESS		= 37;
	public static final int CRUSADER		= 38;
	//public static final int ???        	= 39;
	public static final int MEDIC			= 40;
	public static final int ANGEL			= 41;
	public static final int SURGEON			= 42;
	//public static final int ???        	= 43;

	//abilities
	public static final int HEROIC_LEAP     = 48;
	public static final int SHOCKWAVE       = 49;
	public static final int ENDURE          = 50;
	//public static final int ???        	= 51;
	public static final int ELEMENTAL_BLAST = 52;
	public static final int WILD_MAGIC      = 53;
	public static final int WARP_BEACON     = 54;
	//public static final int ???        	= 55;
	public static final int SMOKE_BOMB      = 56;
	public static final int DEATH_MARK      = 57;
	public static final int SHADOW_CLONE    = 58;
	//public static final int ???        	= 59;
	public static final int SPECTRAL_BLADES = 60;
	public static final int NATURES_POWER   = 61;
	public static final int SPIRIT_HAWK     = 62;
	//public static final int ???        	= 63;
	public static final int CHALLENGE       = 64;
	public static final int ELEMENTAL_STRIKE= 65;
	public static final int FEINT           = 66;
	//public static final int ???        	= 67;
	//public static final int ???        	= 68;
	//public static final int ???        	= 69;
	//public static final int ???        	= 70;
	//public static final int ???        	= 71;
	public static final int RIOT            = 72;
	public static final int REINFORCEDARMOR = 73;
	public static final int FIRSTAIDKIT     = 74;
	//public static final int ???        	= 75;
	public static final int AWAKE			= 76;
	public static final int SHADOW_BLADE	= 77;
	public static final int KUNAI			= 78;
	//public static final int ???        	= 79;
	public static final int SPROUT			= 80;
	public static final int TREASUREMAP		= 81;
	public static final int ROOT			= 82;
	//public static final int ???        	= 83;
	public static final int HOLY_SHIELD		= 84;
	public static final int STIMPACK		= 85;
	public static final int UNSTABLE_ANKH	= 86;
	//public static final int ???        	= 87;
	public static final int HEAL_GENERATOR	= 88;
	public static final int ANGEL_WING		= 89;
	public static final int GAMMA_EMMIT		= 90;
	//public static final int ???        	= 91;
	//public static final int ???        	= 92;
	//public static final int ???        	= 93;
	//public static final int ???        	= 94;
	//public static final int ???        	= 95;
	public static final int RATMOGRIFY      = 96;

	public HeroIcon(HeroSubClass subCls){
		super( Assets.Interfaces.HERO_ICONS );
		if (film == null){
			film = new TextureFilm(texture, SIZE, SIZE);
		}
		frame(film.get(subCls.icon()));
	}

	public HeroIcon(ArmorAbility abil){
		super( Assets.Interfaces.HERO_ICONS );
		if (film == null){
			film = new TextureFilm(texture, SIZE, SIZE);
		}
		frame(film.get(abil.icon()));
	}

}
