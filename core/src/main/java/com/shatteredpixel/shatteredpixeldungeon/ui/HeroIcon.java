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
	public static final int NONE    = 119;

	//subclasses
	public static final int BERSERKER   = 0;
	public static final int GLADIATOR   = 1;
	public static final int VETERAN		= 2;
//	public static final int 			= 3;

	public static final int BATTLEMAGE  = 4;
	public static final int WARLOCK     = 5;
	public static final int WIZARD		= 6;
//	public static final int 			= 7;

	public static final int ASSASSIN    = 8;
	public static final int FREERUNNER  = 9;
	public static final int CHASER		= 10;
//	public static final int 			= 11;

	public static final int SNIPER      = 12;
	public static final int WARDEN      = 13;
	public static final int FIGHTER		= 14;
//	public static final int 			= 15;

	public static final int CHAMPION    = 16;
	public static final int MONK        = 17;
	public static final int FENCER		= 18;
//	public static final int 			= 19;

//	public static final int 			= 20;
//	public static final int 			= 21;
//	public static final int 			= 22;
//	public static final int 			= 23;

	//new classes
	public static final int OUTLAW		= 24;
	public static final int GUNSLINGER	= 25;
	public static final int SPECIALIST	= 26;
//	public static final int 			= 27;

	public static final int SLASHER		= 28;
	public static final int MASTER		= 29;
	public static final int SLAYER		= 30;
//	public static final int 			= 31;

//	public static final int 			= 32;
//	public static final int 			= 33;
//	public static final int 			= 34;
//	public static final int 			= 35;

//	public static final int 			= 36;
//	public static final int 			= 37;
//	public static final int 			= 38;
//	public static final int 			= 39;

//	public static final int 			= 40;
//	public static final int 			= 41;
//	public static final int 			= 42;
//	public static final int 			= 43;

//	public static final int 			= 44;
//	public static final int 			= 45;
//	public static final int 			= 46;
//	public static final int 			= 47;

	//abilities
	public static final int HEROIC_LEAP     = 48;
	public static final int SHOCKWAVE       = 49;
	public static final int ENDURE          = 50;
//	public static final int 				= 51;

	public static final int ELEMENTAL_BLAST = 52;
	public static final int WILD_MAGIC      = 53;
	public static final int WARP_BEACON     = 54;
//	public static final int 				= 55;

	public static final int SMOKE_BOMB      = 56;
	public static final int DEATH_MARK      = 57;
	public static final int SHADOW_CLONE    = 58;
//	public static final int 				= 59;

	public static final int SPECTRAL_BLADES = 60;
	public static final int NATURES_POWER   = 61;
	public static final int SPIRIT_HAWK     = 62;
//	public static final int 				= 63;

	public static final int CHALLENGE       = 64;
	public static final int ELEMENTAL_STRIKE= 65;
	public static final int FEINT           = 66;
//	public static final int 				= 67;

//	public static final int 				= 68;
//	public static final int 				= 69;
//	public static final int 				= 70;
//	public static final int 				= 71;

	//new abilities
	public static final int RIOT			= 72;
	public static final int REINFORCEDARMOR	= 73;
	public static final int FIRSTAIDKIT		= 74;
//	public static final int 				= 75;

	public static final int AWAKE			= 76;
	public static final int SHADOW_BLADE	= 77;
	public static final int KUNAI			= 78;
//	public static final int 				= 79;

//	public static final int 				= 80;
//	public static final int 				= 81;
//	public static final int 				= 82;
//	public static final int 				= 83;

//	public static final int 				= 84;
//	public static final int 				= 85;
//	public static final int 				= 86;
//	public static final int 				= 87;

//	public static final int 				= 88;
//	public static final int 				= 89;
//	public static final int 				= 90;
//	public static final int 				= 91;

//	public static final int 				= 92;
//	public static final int 				= 93;
//	public static final int 				= 94;
//	public static final int 				= 95;

	public static final int RATMOGRIFY      = 96;

	//action indicator visuals
	public static final int BERSERK         = 104;
	public static final int COMBO           = 105;
	public static final int TACKLE          = 106;
//	public static final int					= 107;

	public static final int	MAGIC_COMBO		= 108;
//	public static final int					= 109;
//	public static final int					= 110;
//	public static final int					= 111;

	public static final int PREPARATION     = 112;
	public static final int MOMENTUM        = 113;
//	public static final int					= 114;
//	public static final int					= 115;

	public static final int SNIPERS_MARK    = 116;
//	public static final int					= 117;
//	public static final int					= 118;
//	public static final int					= 119;

	public static final int WEAPON_SWAP     = 120;
	public static final int MONK_ABILITIES  = 121;
//	public static final int 				= 122;
//	public static final int 				= 123;

//	public static final int 				= 124;
//	public static final int 				= 125;
//	public static final int 				= 126;
//	public static final int 				= 127;

	public static final int SWORD_AURA		= 128;
	public static final int AWAKEN_OFF		= 129;
	public static final int AWAKEN_ON		= 130;
//	public static final int 				= 131;

//	public static final int 				= 132;
//	public static final int 				= 133;
//	public static final int 				= 134;
//	public static final int 				= 135;

//	public static final int 				= 136;
//	public static final int 				= 137;
//	public static final int 				= 138;
//	public static final int 				= 139;

//	public static final int 				= 140;
//	public static final int 				= 141;
//	public static final int 				= 142;
//	public static final int 				= 143;

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

	public HeroIcon(ActionIndicator.Action action){
		super( Assets.Interfaces.HERO_ICONS );
		if (film == null){
			film = new TextureFilm(texture, SIZE, SIZE);
		}
		frame(film.get(action.actionIcon()));
	}

}
