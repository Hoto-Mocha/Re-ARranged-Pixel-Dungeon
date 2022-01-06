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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Combo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Focusing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Handgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Magnum;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RPG7;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RocketLauncher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SPAS;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SPASAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SPASHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Shockwave extends ArmorAbility {

	{
		baseChargeUse = 1f;
	}

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

	@Override
	protected void activate(ClassArmor armor, Hero hero, Integer target) {
		if (target == null){
			return;
		}
		if (target == hero.pos){
			GLog.w(Messages.get(this, "self_target"));
			return;
		}
		hero.busy();

		armor.charge -= chargeUse(hero);
		Item.updateQuickslot();

		Ballistica aim = new Ballistica(hero.pos, target, Ballistica.WONT_STOP);

		int maxDist = 5 + hero.pointsInTalent(Talent.EXPANDING_WAVE);
		int dist = Math.min(aim.dist, maxDist);

		ConeAOE cone = new ConeAOE(aim,
				dist,
				60 + 15*hero.pointsInTalent(Talent.EXPANDING_WAVE),
				Ballistica.STOP_SOLID | Ballistica.STOP_TARGET);

		//cast to cells at the tip, rather than all cells, better performance.
		for (Ballistica ray : cone.outerRays){
			((MagicMissile)hero.sprite.parent.recycle( MagicMissile.class )).reset(
					MagicMissile.FORCE_CONE,
					hero.sprite,
					ray.path.get(ray.dist),
					null
			);
		}

		hero.sprite.zap(target);
		Sample.INSTANCE.play(Assets.Sounds.BLAST, 1f, 0.5f);
		Camera.main.shake(2, 0.5f);
		//final zap at 2/3 distance, for timing of the actual effect
		MagicMissile.boltFromChar(hero.sprite.parent,
				MagicMissile.FORCE_CONE,
				hero.sprite,
				cone.coreRay.path.get(dist * 2 / 3),
				new Callback() {
					@Override
					public void call() {

						for (int cell : cone.cells){

							Char ch = Actor.findChar(cell);
							if (ch != null && ch.alignment != hero.alignment){
								int scalingStr = hero.STR()-10;
								int damage = Random.NormalIntRange(5 + scalingStr, 10 + 2*scalingStr);
								damage = Math.round(damage * (1f + 0.2f*hero.pointsInTalent(Talent.SHOCK_FORCE)));
								damage -= ch.drRoll();

								if (hero.pointsInTalent(Talent.STRIKING_WAVE) == 4){
									Buff.affect(hero, Talent.StrikingWaveTracker.class, 0f);
								}

								if (Random.Int(10) < 3*hero.pointsInTalent(Talent.STRIKING_WAVE)){
									damage = hero.attackProc(ch, damage);
									ch.damage(damage, hero);
									if (hero.subClass == HeroSubClass.GLADIATOR){
										Buff.affect( hero, Combo.class ).hit( ch );
									}
									if (hero.subClass == HeroSubClass.VETERAN){
										if (hero.belongings.weapon instanceof CrudePistol
												|| hero.belongings.weapon instanceof CrudePistolAP
												|| hero.belongings.weapon instanceof CrudePistolHP
												|| hero.belongings.weapon instanceof Pistol
												|| hero.belongings.weapon instanceof PistolAP
												|| hero.belongings.weapon instanceof PistolHP
												|| hero.belongings.weapon instanceof GoldenPistol
												|| hero.belongings.weapon instanceof GoldenPistolAP
												|| hero.belongings.weapon instanceof GoldenPistolHP
												|| hero.belongings.weapon instanceof Handgun
												|| hero.belongings.weapon instanceof HandgunAP
												|| hero.belongings.weapon instanceof HandgunHP
												|| hero.belongings.weapon instanceof Magnum
												|| hero.belongings.weapon instanceof MagnumAP
												|| hero.belongings.weapon instanceof MagnumHP
												|| hero.belongings.weapon instanceof DualPistol
												|| hero.belongings.weapon instanceof DualPistolAP
												|| hero.belongings.weapon instanceof DualPistolHP
												|| hero.belongings.weapon instanceof SubMachinegun
												|| hero.belongings.weapon instanceof SubMachinegunAP
												|| hero.belongings.weapon instanceof SubMachinegunHP
												|| hero.belongings.weapon instanceof AssultRifle
												|| hero.belongings.weapon instanceof AssultRifleAP
												|| hero.belongings.weapon instanceof AssultRifleHP
												|| hero.belongings.weapon instanceof HeavyMachinegun
												|| hero.belongings.weapon instanceof HeavyMachinegunAP
												|| hero.belongings.weapon instanceof HeavyMachinegunHP
												|| hero.belongings.weapon instanceof HuntingRifle
												|| hero.belongings.weapon instanceof HuntingRifleAP
												|| hero.belongings.weapon instanceof HuntingRifleHP
												|| hero.belongings.weapon instanceof SniperRifle
												|| hero.belongings.weapon instanceof SniperRifleAP
												|| hero.belongings.weapon instanceof SniperRifleHP
												|| hero.belongings.weapon instanceof ShotGun
												|| hero.belongings.weapon instanceof ShotGunAP
												|| hero.belongings.weapon instanceof ShotGunHP
												|| hero.belongings.weapon instanceof SPAS
												|| hero.belongings.weapon instanceof SPASAP
												|| hero.belongings.weapon instanceof SPASHP
												|| hero.belongings.weapon instanceof RocketLauncher
												|| hero.belongings.weapon instanceof MiniGun
												|| hero.belongings.weapon instanceof MiniGunAP
												|| hero.belongings.weapon instanceof MiniGunHP
												|| hero.belongings.weapon instanceof LargeHandgun
												|| hero.belongings.weapon instanceof LargeHandgunAP
												|| hero.belongings.weapon instanceof LargeHandgunHP
												|| hero.belongings.weapon instanceof AntimaterRifle
												|| hero.belongings.weapon instanceof AntimaterRifleAP
												|| hero.belongings.weapon instanceof AntimaterRifleHP
												|| hero.belongings.weapon instanceof RPG7
										) {
											Buff.affect( hero, Focusing.class ).hit( ch );
										}
									}
								} else {
									ch.damage(damage, hero);
								}
								if (ch.isAlive()){
									if (Random.Int(4) < hero.pointsInTalent(Talent.SHOCK_FORCE)){
										Buff.affect(ch, Paralysis.class, 5f);
									} else {
										Buff.affect(ch, Cripple.class, 5f);
									}
								}

							}
						}

						Invisibility.dispel();
						hero.spendAndNext(Actor.TICK);

					}
				});
	}

	@Override
	public int icon() {
		return HeroIcon.SHOCKWAVE;
	}

	@Override
	public Talent[] talents() {
		return new Talent[]{Talent.EXPANDING_WAVE, Talent.STRIKING_WAVE, Talent.SHOCK_FORCE, Talent.HEROIC_ENERGY};
	}
}
