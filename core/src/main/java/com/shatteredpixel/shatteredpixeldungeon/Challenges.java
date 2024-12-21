/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Acidic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Albino;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Bandit;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Bat;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Brute;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.CausticSlime;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Crab;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM100;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM200;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM201;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Elemental;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Eye;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Ghoul;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Gnoll;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Golem;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Guard;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Medic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Monk;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Necromancer;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Rat;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Researcher;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.SWAT;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Scorpio;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Senior;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Shaman;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Skeleton;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Slime;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Snake;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Soldier;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.SpectralNecromancer;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Spinner;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Succubus;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Suppression;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Swarm;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Tank;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Thief;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Warlock;
import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;

import java.util.ArrayList;

public class Challenges {

	//Some of these internal IDs are outdated and don't represent what these challenges do
	public static final int NO_FOOD				= 1;
	public static final int NO_ARMOR			= 2;
	public static final int NO_HEALING			= 4;
	public static final int NO_HERBALISM		= 8;
	public static final int SWARM_INTELLIGENCE	= 16;
	public static final int DARKNESS			= 32;
	public static final int NO_SCROLLS		    = 64;
	public static final int CHAMPION_ENEMIES	= 128;
	public static final int STRONGER_BOSSES 	= 256;
	public static final int SUPERMAN			= 512;
	public static final int PYRO				= 1024;
	public static final int CURSED_DUNGEON		= 2048;
	public static final int FATIGUE				= 4096;
	public static final int MUTATION			= 8192;

	public static final int MAX_VALUE           = 16383;

	public static final String[] NAME_IDS = {
			"champion_enemies",
			"stronger_bosses",
			"no_food",
			"no_armor",
			"no_healing",
			"no_herbalism",
			"swarm_intelligence",
			"darkness",
			"no_scrolls",
			"superman",
			"pyro",
			"cursed_dungeon",
			"fatigue",
			"mutation"
	};

	public static final int[] MASKS = {
			CHAMPION_ENEMIES, STRONGER_BOSSES, NO_FOOD, NO_ARMOR, NO_HEALING, NO_HERBALISM, SWARM_INTELLIGENCE, DARKNESS, NO_SCROLLS, SUPERMAN, PYRO, CURSED_DUNGEON, FATIGUE, MUTATION
	};

	public static int activeChallenges(){
		int chCount = 0;
		for (int ch : Challenges.MASKS){
			if ((Dungeon.challenges & ch) != 0) chCount++;
		}
		return chCount;
	}

	public static boolean isItemBlocked( Item item ){

		if (Dungeon.isChallenged(NO_HERBALISM) && item instanceof Dewdrop){
			return true;
		}

		return false;

	}

	public static final ArrayList<Class<? extends Mob>> enemyListR1 = new ArrayList<>();
	static {
		enemyListR1.add(Rat.class);
		enemyListR1.add(Albino.class);
		enemyListR1.add(Snake.class);
		enemyListR1.add(Gnoll.class);
		enemyListR1.add(Swarm.class);
		enemyListR1.add(Crab.class);
		enemyListR1.add(Slime.class);
		enemyListR1.add(CausticSlime.class);
	}

	public static final ArrayList<Class<? extends Mob>> enemyListR2 = new ArrayList<>();
	static {
		enemyListR2.add(Skeleton.class);
		enemyListR2.add(Thief.class);
		enemyListR2.add(Bandit.class);
		enemyListR2.add(DM100.class);
		enemyListR2.add(Guard.class);
		enemyListR2.add(Necromancer.class);
		enemyListR2.add(SpectralNecromancer.class);
	}

	public static final ArrayList<Class<? extends Mob>> enemyListR3 = new ArrayList<>();
	static {
		enemyListR3.add(Bat.class);
		enemyListR3.add(Brute.class);
		enemyListR3.add(Shaman.RedShaman.class);
		enemyListR3.add(Shaman.BlueShaman.class);
		enemyListR3.add(Shaman.PurpleShaman.class);
		enemyListR3.add(Spinner.class);
		enemyListR3.add(DM200.class);
		enemyListR3.add(DM201.class);
	}

	public static final ArrayList<Class<? extends Mob>> enemyListR4 = new ArrayList<>();
	static {
		enemyListR4.add(Elemental.FireElemental.class);
		enemyListR4.add(Elemental.FrostElemental.class);
		enemyListR4.add(Elemental.ShockElemental.class);
		enemyListR4.add(Elemental.ChaosElemental.class);
		enemyListR4.add(Warlock.class);
		enemyListR4.add(Ghoul.class);
		enemyListR4.add(Monk.class);
		enemyListR4.add(Senior.class);
		enemyListR4.add(Golem.class);
	}

	public static final ArrayList<Class<? extends Mob>> enemyListR5 = new ArrayList<>();
	static {
		enemyListR5.add(Succubus.class);
		enemyListR5.add(Eye.class);
		enemyListR5.add(Scorpio.class);
		enemyListR5.add(Acidic.class);
	}

	public static final ArrayList<Class<? extends Mob>> enemyListR6 = new ArrayList<>();
	static {
		enemyListR6.add(Soldier.class);
		enemyListR6.add(SWAT.class);
		enemyListR6.add(Researcher.class);
		enemyListR6.add(Medic.class);
		enemyListR6.add(Suppression.class);
		enemyListR6.add(Tank.class);
	}

}