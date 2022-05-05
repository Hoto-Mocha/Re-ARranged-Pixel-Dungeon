package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EvasiveMove;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.HealingArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PrismaticGuard;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.MirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.PrismaticImage;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PoisonParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class HandMirror extends Item {

	{
		image = ItemSpriteSheet.HAND_MIRROR;

		defaultAction = AC_USE;
		usesTargeting = true;

		bones = false;
		unique = true;
	}

	private static final String AC_USE = "USE";

	@Override
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_USE );
		return actions;
	}

	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals(AC_USE)) {
			curUser = hero;
			curItem = this;
			if (hero.buff(HandMirrorCooldown.class) != null) {
				GLog.w(Messages.get(this, "cooldown"));
			} else {
				boolean found = false;
				for (Mob m : Dungeon.level.mobs.toArray(new Mob[0])){
					if (m instanceof PrismaticImage){
						found = true;
						m.HP = Math.min(m.HP+m.HT/10, m.HT);
						m.sprite.emitter().burst(Speck.factory(Speck.HEALING), 2);
					}
				}
				if (!found) {
					PrismaticGuard pg = hero.buff(PrismaticGuard.class);
					if (pg != null) {
						if (pg.HP() == pg.maxHP()) {
							spawnImages(curUser, 1);
						} else {
							int healAmt = pg.maxHP()/10;
							healAmt = Math.min( healAmt, pg.maxHP() - pg.HP() );
							Buff.affect(hero, PrismaticGuard.class).extend( healAmt ); //heals the buff's hp
						}
					} else {
						Buff.affect(hero, PrismaticGuard.class).set( (10 + (int)Math.floor(hero.lvl * 2.5f))/10 ); //affects a new buff to hero
					}
				}
				curUser.sprite.operate(curUser.pos);
				Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
				curUser.spendAndNext(Actor.TICK);
				Buff.affect(curUser, HandMirrorCooldown.class, HandMirrorCooldown.DURATION);
			}
		}
	}

	public void spawnImages( Hero hero, int nImages ){

		ArrayList<Integer> respawnPoints = new ArrayList<>();

		for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
			int p = hero.pos + PathFinder.NEIGHBOURS8[i];
			if (Actor.findChar( p ) == null && Dungeon.level.passable[p]) {
				respawnPoints.add( p );
			}
		}

		while (nImages > 0 && respawnPoints.size() > 0) {
			int index = Random.index( respawnPoints );

			MirrorImage mob = new MirrorImage();
			mob.duplicate( hero );
			Buff.affect(mob, EvasiveMove.class, (buffedLvl() >= 10) ? 4f : 3f);
			Buff.affect(mob, Haste.class, (buffedLvl() >= 10) ? 4f : 3f);
			if(buffedLvl() >= 10) {
				Buff.affect(mob, Barkskin.class).set(this.buffedLvl(), 1);
			}
			GameScene.add( mob );
			ScrollOfTeleportation.appear( mob, respawnPoints.get( index ) );

			respawnPoints.remove( index );
			nImages--;
		}
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int value() {
		return -1;
	}

	public static class HandMirrorCooldown extends FlavourBuff {

		{
			type = buffType.NEUTRAL;
			announced = false;
		}

		public static final float DURATION = 100f;

		@Override
		public int icon() {
			return BuffIndicator.TIME;
		}

		@Override
		public void tintIcon(Image icon) {
			icon.hardlight(0xDFFF40);
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, (DURATION - visualcooldown()) / DURATION);
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc", dispTurns());
		}
	}
}
