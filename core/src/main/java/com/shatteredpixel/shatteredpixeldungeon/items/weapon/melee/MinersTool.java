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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MinersToolCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ShovelDigCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Lucky;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MinersTool extends MeleeWeapon {

	public static final String AC_DIG	= "DIG";
	public static final String AC_MINE	= "MINE";

	{
		defaultAction = AC_MINE;

		image = ItemSpriteSheet.MINERS_TOOL;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 0.9f;

		tier = 3;

		unique = true;
		bones = false;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		int level = Math.max( 0, this.buffedLvl() );
		float procChance;
		// lvl 0 - 13%
		// lvl 1 - 22%
		// lvl 2 - 30%
		procChance = (level+1f)/(level+8f);
		if (Random.Float() < procChance) {
			Buff.prolong(defender, Vulnerable.class, 5f);
		}
		return super.proc( attacker, defender, damage );
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_DIG);
		actions.add(AC_MINE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_DIG)) {
			if (hero.buff(ShovelDigCoolDown.class) != null){
				GLog.w(Messages.get(this, "not_ready"));
			} else {
				Dig();
			}
		}
		if (action.equals(AC_MINE)) {
			if (hero.buff(MinersToolCoolDown.class) != null){
				GLog.w(Messages.get(this, "not_ready_mining"));
			} else {
				usesTargeting = false;
				curUser = hero;
				curItem = this;
				GameScene.selectCell(new CellSelector.Listener() {
					@Override
					public void onSelect( Integer cell ) {
						if (cell != null) {
							if (!(Dungeon.level.adjacent(curUser.pos, cell) || cell == curUser.pos)) {
								GLog.w(Messages.get(MinersTool.class, "too_far"));
							} else {
								if (Dungeon.level.map[cell] == Terrain.WALL_DECO) {
									goldMining(cell);
								} else if (Dungeon.level.map[cell] == Terrain.WATER) {
									filling(cell);
								} else if (Dungeon.level.map[cell] == Terrain.EMPTY
										|| Dungeon.level.map[cell] == Terrain.EMPTY_DECO
										|| Dungeon.level.map[cell] == Terrain.EMPTY_SP
										|| Dungeon.level.map[cell] == Terrain.GRASS
										|| Dungeon.level.map[cell] == Terrain.HIGH_GRASS
										|| Dungeon.level.map[cell] == Terrain.FURROWED_GRASS
										|| Dungeon.level.map[cell] == Terrain.EMBERS
										|| Dungeon.level.map[cell] == Terrain.INACTIVE_TRAP) {
									if (Dungeon.depth % 5 == 0 || Dungeon.depth == 31) {
										GLog.w(Messages.get(MinersTool.class, "cannot_mine_boss"));
									} else {
										Char ch = Actor.findChar(cell);
										//don't trigger when immovable neutral chars are exist
										if (ch != null) {
											if (!(ch.alignment == Char.Alignment.NEUTRAL || Char.hasProp(ch, Char.Property.IMMOVABLE))) {
												floorMining(cell);
											} else {
												GLog.w(Messages.get(MinersTool.class, "cannot_mine"));
											}
										} else {
											floorMining(cell);
										}
									}
								} else if (Dungeon.level.map[cell] == Terrain.STATUE
										|| Dungeon.level.map[cell] == Terrain.STATUE_SP) {
									statueMining(cell);
								} else if (Dungeon.level.map[cell] == Terrain.TRAP
										|| Dungeon.level.map[cell] == Terrain.SECRET_TRAP) {
									trapDisarming(cell);
								} else {
									GLog.w(Messages.get(MinersTool.class, "cannot_mine"));
								}
							}
						}
					}
					@Override
					public String prompt() {
						return Messages.get(MinersTool.class, "prompt");
					}
				});
			}
		}
	}

	public void Dig() {
		curUser.spend(Actor.TICK);
		curUser.busy();
		Sample.INSTANCE.play(Assets.Sounds.TRAMPLE, 2, 1.1f);
		curUser.sprite.operate(curUser.pos);

		GLog.i(Messages.get(this, "dig"));

		for (int i : PathFinder.NEIGHBOURS9) {
			int c = Dungeon.level.map[hero.pos + i];
			if ( c == Terrain.EMPTY || c == Terrain.EMPTY_DECO
					|| c == Terrain.EMBERS || c == Terrain.GRASS){
				Level.set(hero.pos + i, Terrain.FURROWED_GRASS);
				GameScene.updateMap(hero.pos + i);
				CellEmitter.get( hero.pos + i ).burst( LeafParticle.LEVEL_SPECIFIC, 4 );
			}
		}
		if (Random.Int(10) < hero.pointsInTalent(Talent.DETECTOR)) {
			Dungeon.level.drop(Lucky.genLoot(), hero.pos).sprite.drop();
			Lucky.showFlare(hero.sprite);
		}
		Buff.affect(hero, ShovelDigCoolDown.class, Math.max(20-2*buffedLvl(), 5));
	}

	@Override
	public int max(int lvl) {
		if (hero.hasTalent(Talent.TAKEDOWN) && hero.buff(Talent.TakeDownCooldown.class) == null) {
			return 4*(tier+1) +
					lvl*(tier+1) +
					10 * hero.pointsInTalent(Talent.TAKEDOWN);
		} else
			return  4*(tier+1) +
					lvl*(tier+1);
	}

	public void goldMining(int cell) {
		Item gold = new Gold().quantity((int)Math.ceil(Dungeon.depth/5f)*Random.NormalIntRange(5, 25));
		Level.set(cell, Terrain.CUSTOM_WALL);
		Dungeon.level.drop(gold, curUser.pos).sprite.drop();
		Sample.INSTANCE.play( Assets.Sounds.EVOKE );
		curUser.sprite.zap( cell );
		CellEmitter.center( cell ).burst( Speck.factory( Speck.STAR ), 10 );
		curUser.spendAndNext(3f);
		GameScene.updateMap(cell);
		curUser.busy();
		Buff.affect(curUser, MinersToolCoolDown.class, Math.max(20-2*buffedLvl(), 5));
		Invisibility.dispel();
		GLog.p(Messages.get(MinersTool.class, "gold_mining"));
	}

	public void floorMining(int cell) {
		Level.set(cell, Terrain.CHASM);

		Heap heap = Dungeon.level.heaps.get(cell);
		if (heap != null && heap.type != Heap.Type.FOR_SALE
				&& heap.type != Heap.Type.LOCKED_CHEST
				&& heap.type != Heap.Type.CRYSTAL_CHEST) {
			for (Item item : heap.items) {
				Dungeon.dropToChasm(item);
			}
			heap.sprite.kill();
			GameScene.discard(heap);
			Dungeon.level.heaps.remove(cell);
		}

		Char ch = Actor.findChar(cell);

		if (ch != null && !ch.flying) {
			if (ch == Dungeon.hero) {
				Chasm.heroFall(cell);
			} else {
				Chasm.mobFall((Mob) ch);
			}
		}
		curUser.sprite.zap( cell );
		Sample.INSTANCE.play(Assets.Sounds.ROCKS);
		CellEmitter.get(cell).start(Speck.factory(Speck.ROCK), 0.07f, 10);
		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			mob.beckon( curUser.pos );
		}
		curUser.spendAndNext(3f);
		for (int i : PathFinder.NEIGHBOURS9){
			GameScene.updateMap(cell + i);
		}
		curUser.busy();
		Buff.affect(curUser, MinersToolCoolDown.class, 50f);
		Invisibility.dispel();
		GLog.w(Messages.get(MinersTool.class, "floor_mining"));
	}

	public void filling(int cell) {
		Level.set(cell, Terrain.GRASS);
		for (int i : PathFinder.NEIGHBOURS9){
			GameScene.updateMap(curUser.pos + i);
		}
		CellEmitter.get( cell ).burst( LeafParticle.LEVEL_SPECIFIC, 4 );
		Sample.INSTANCE.play(Assets.Sounds.TRAMPLE, 2, 1.1f);
		curUser.sprite.operate(curUser.pos);
		curUser.spend(1f);
		curUser.busy();
		Buff.affect(curUser, MinersToolCoolDown.class, Math.max(20-2*buffedLvl(), 5));
		Invisibility.dispel();
		GLog.i(Messages.get(MinersTool.class, "filling"));
	}

	public void statueMining(int cell) {
		if (Random.Int(10) == 0) {
			Item stone = Generator.random(Generator.Category.STONE);
			Dungeon.level.drop(stone, cell).sprite.drop();
			GLog.p(Messages.get(MinersTool.class, "statue_mining_stone"));
		} else {
			GLog.i(Messages.get(MinersTool.class, "statue_mining"));
		}
		if (Dungeon.level.map[cell] == Terrain.STATUE) {
			Level.set(cell, Terrain.EMPTY);
		} else {
			Level.set(cell, Terrain.EMPTY_SP);
		}
		curUser.sprite.zap( cell );
		Sample.INSTANCE.play(Assets.Sounds.ROCKS);
		CellEmitter.get(cell).start(Speck.factory(Speck.ROCK), 0.07f, 5);
		curUser.spendAndNext(2f);
		for (int i : PathFinder.NEIGHBOURS9){
			GameScene.updateMap(cell + i);
		}
		curUser.busy();
		Buff.affect(curUser, MinersToolCoolDown.class, Math.max(20-2*buffedLvl(), 5));
		Invisibility.dispel();
	}

	public void trapDisarming(int cell) {
		Trap t = Dungeon.level.traps.get(cell);
		t.reveal();
		t.disarm();
		CellEmitter.get(t.pos).burst(Speck.factory(Speck.STEAM), 6);
		Sample.INSTANCE.play(Assets.Sounds.UNLOCK);
		Level.set(cell, Terrain.INACTIVE_TRAP);
		curUser.sprite.operate(curUser.pos);
		curUser.spendAndNext(2f);
		GameScene.updateMap(cell);
		curUser.busy();
		Buff.affect(curUser, MinersToolCoolDown.class, Math.max(20-2*buffedLvl(), 5));
		Invisibility.dispel();
		GLog.i(Messages.get(MinersTool.class, "trap_disarming"));
	}
}
