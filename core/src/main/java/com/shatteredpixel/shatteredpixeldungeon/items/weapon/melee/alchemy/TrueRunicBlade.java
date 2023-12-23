package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Piranha;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RunicBlade;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class TrueRunicBlade extends AlchemyWeapon {

    {
        image = ItemSpriteSheet.TRUE_RUNIC_BLADE;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1f;

        tier = 5;
    }

    @Override
    public int max(int lvl) {
        return  5*(tier) +
                Math.round(lvl*(tier+4)); //same as Runic Blade
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        Buff.affect(attacker, TrueRunicSlashTracker.class);
        return super.proc(attacker, defender, damage);
    }

    @Override
    public String targetingPrompt() {
        return Messages.get(this, "prompt");
    }

    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        if (target == null) {
            return;
        }

        Char enemy = Actor.findChar(target);
        if (enemy == null || enemy == hero || hero.isCharmedBy(enemy) || !Dungeon.level.heroFOV[target]) {
            GLog.w(Messages.get(RunicBlade.class, "ability_no_target"));
            return;
        }

        //we apply here because of projecting
        RunicBlade.RunicSlashTracker tracker = Buff.affect(hero, RunicBlade.RunicSlashTracker.class);
        hero.belongings.abilityWeapon = this;
        if (!hero.canAttack(enemy)){
            GLog.w(Messages.get(RunicBlade.class, "ability_bad_position"));
            tracker.detach();
            hero.belongings.abilityWeapon = null;
            return;
        }
        hero.belongings.abilityWeapon = null;

        hero.sprite.attack(enemy.pos, new Callback() {
            @Override
            public void call() {
                beforeAbilityUsed(hero, enemy);
                AttackIndicator.target(enemy);
                if (hero.attack(enemy, 1f, 0, Char.INFINITE_ACCURACY)){
                    Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
                    if (!enemy.isAlive()){
                        onAbilityKill(hero, enemy);
                    }
                }
                tracker.detach();
                Invisibility.dispel();
                hero.spendAndNext(hero.attackDelay());
                afterAbilityUsed(hero);
            }
        });
    }

    public static class TrueRunicSlashTracker extends FlavourBuff{};
}
