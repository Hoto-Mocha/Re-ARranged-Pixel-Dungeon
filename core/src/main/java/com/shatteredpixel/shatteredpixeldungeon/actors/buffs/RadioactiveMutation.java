package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PoisonParticle;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

public class RadioactiveMutation extends Buff implements Hero.Doom {
	
	protected float time;
	protected int maxTime;

	private static final String TIME = "time";
	private static final String MAX_TIME = "maxTime";

	{
		type = buffType.NEGATIVE;
		announced = true;
	}
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put(TIME, time);
		bundle.put(MAX_TIME, maxTime);
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		time = bundle.getFloat(TIME);
		maxTime = bundle.getInt(MAX_TIME);
	}
	
	public void set( int maxTime ) {
		this.time = maxTime;
		this.maxTime = maxTime;
	}
	
	@Override
	public int icon() {
		return BuffIndicator.RADIOACTIVE;
	}

	public String iconTextDisplay(){
		return Integer.toString((int) time);
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns(time));
	}

	@Override
	public boolean attachTo(Char target) {
		if (super.attachTo(target) && target.sprite != null){
			CellEmitter.center(target.pos).burst( PoisonParticle.SPLASH, 5 );
			return true;
		} else
			return false;
	}

	@Override
	public boolean act() {
		if (!target.isAlive()) {
			detach();
		}

		if ((time -= TICK) == 0) {
			if (target != null && target.isAlive()) {
				target.damage( 1, this );
			}
			time = maxTime;
		}

		spend( TICK );
		
		return true;
	}

	@Override
	public void onDeath() {
		Dungeon.fail( this );
		GLog.n( Messages.get(this, "ondeath") );
	}
}
