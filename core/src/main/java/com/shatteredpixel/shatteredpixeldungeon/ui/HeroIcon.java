/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2025 Evan Debenham
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
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells.ClericSpell;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;

//icons for hero subclasses and abilities atm, maybe add classes?
public class HeroIcon extends Image {

	private static TextureFilm film;
	private static final int SIZE = 16;

	//transparent icon
	public static final int NONE    = 127;

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

	public static final int PRIEST		= 20;
	public static final int PALADIN		= 21;
	public static final int ENCHANTER	= 22;
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

	public static final int ENGINEER 	= 32;
	public static final int EXPLORER	= 33;
	public static final int RESEARCHER	= 34;
//	public static final int 			= 35;

	public static final int DEATHKNIGHT = 36;
	public static final int HORSEMAN	= 37;
	public static final int CRUSADER	= 38;
//	public static final int 			= 39;

	public static final int SAVIOR		= 40;
	public static final int THERAPIST	= 41;
	public static final int MEDICALOFFICER	= 42;
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

	public static final int ASCENDED_FORM	= 68;
	public static final int TRINITY      	= 69;
	public static final int POWER_OF_MANY	= 70;
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

	public static final int SPROUT			= 80;
	public static final int TREASUREMAP		= 81;
	public static final int ROOT	    	= 82;
//	public static final int 				= 83;

	public static final int HOLY_SHIELD		= 84;
	public static final int STIMPACK		= 85;
	public static final int UNSTABLE_ANKH	= 86;
//	public static final int 				= 87;

	public static final int HEAL_GENERATOR	= 88;
	public static final int ANGEL_WING		= 89;
	public static final int GAMMA_EMMIT		= 90;
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

	public static final int BUILD			= 132;
//	public static final int 				= 133;
//	public static final int 				= 134;
//	public static final int 				= 135;

//	public static final int 				= 136;
	public static final int RIDE			= 137;
	public static final int PRAY			= 138;
//	public static final int 				= 139;

	public static final int FIRST_AID		= 140;
//	public static final int 				= 141;
	public static final int COMMAND			= 142;
//	public static final int 				= 143;

//cleric spells
	public static final int GUIDING_LIGHT   = 144;
	public static final int HOLY_WEAPON     = 145;
	public static final int HOLY_WARD       = 146;
	public static final int HOLY_INTUITION  = 147;
	public static final int SHIELD_OF_LIGHT = 148;
	public static final int RECALL_GLYPH    = 149;
	public static final int SUNRAY          = 150;
	public static final int DIVINE_SENSE    = 151;
	public static final int BLESS           = 152;
	public static final int CLEANSE         = 153;
	public static final int RADIANCE        = 154;
	public static final int HOLY_LANCE      = 155;
	public static final int HALLOWED_GROUND = 156;
	public static final int MNEMONIC_PRAYER = 157;
	public static final int SMITE           = 158;
	public static final int LAY_ON_HANDS    = 159;
	public static final int AURA_OF_PROTECTION = 160;
	public static final int WALL_OF_LIGHT   = 161;
	public static final int DIVINE_INTERVENTION = 162;
	public static final int JUDGEMENT       = 163;
	public static final int FLASH           = 164;
	public static final int BODY_FORM       = 165;
	public static final int MIND_FORM       = 166;
	public static final int SPIRIT_FORM     = 167;
	public static final int BEAMING_RAY     = 168;
	public static final int LIFE_LINK       = 169;
	public static final int STASIS          = 170;
//cleric new spells
	public static final int DIVINE_BLAST	= 171;
	public static final int DIVINE_RAY	    = 172;
	public static final int HOLY_BOMB	    = 173;
	public static final int RESURRECTION    = 174;
	public static final int HOLY_MANTLE	    = 175;
	public static final int POWER_OF_LIFE   = 176;
	public static final int INDUCE_AGGRO    = 177;
	public static final int SPELL_BURST = 178;
	public static final int TIME_AMP	    = 179;
	public static final int WEAKENING_HEX   = 180;
	public static final int STUN		    = 181;
	public static final int THUNDER_IMBUE	= 182;
	public static final int ARCANE_ARMOR 	= 183;
	public static final int ENCHANT		    = 184;

	//all cleric spells have a separate icon with no background for the action indicator
	public static final int SPELL_ACTION_OFFSET      = 48;

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

	public HeroIcon(ClericSpell spell){
		super( Assets.Interfaces.HERO_ICONS );
		if (film == null){
			film = new TextureFilm(texture, SIZE, SIZE);
		}
		frame(film.get(spell.icon()));
	}

}
