package com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.archer;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.DisposableMissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Snipe extends ArmorAbility {
    {
        baseChargeUse = 50;
    }

    @Override
    protected void activate(ClassArmor armor, Hero hero, Integer target) {
        if (target == null) return;

        if (target == hero.pos) {
            GLog.w(Messages.get(this, "self_target"));
            return;
        }

        if (Dungeon.level.solid[target] || !Dungeon.level.passable[target]) {
            GLog.w(Messages.get(this, "bad_position"));
            return;
        }

        Ballistica b = new Ballistica(hero.pos, target, Ballistica.STOP_TARGET);
        if (!canReach(b, hero.pointsInTalent(Talent.ACCURATE_SNIPE))) { //도달할 수 있으면 true, 없으면 false
            GLog.w(Messages.get(this, "bad_position"));
            return;
        }

        armor.charge -= chargeUse(hero);
        Item.updateQuickslot();

        hero.sprite.zap(target);
        knockArrow().cast(hero, target);
    }

    @Override
    public int icon() {
        return HeroIcon.SNIPE;
    }

    public String targetingPrompt(){
        return Messages.get(SpiritBow.class, "prompt");
    }

    private boolean canReach(Ballistica path, int wallPenetration){
        for (int cell : path.subPath(0, path.dist)){
            if (Dungeon.level.solid[cell]){
                wallPenetration--;
                if (wallPenetration < 0){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Talent[] talents() {
        return new Talent[]{Talent.ENHANCED_ARROW, Talent.ACCURATE_SNIPE, Talent.KNOCKING_ARROW, Talent.HEROIC_ENERGY};
    }

    public SnipeArrow knockArrow() {
        return new SnipeArrow();
    }

    public static class SnipeArrow extends DisposableMissileWeapon {
        {
            image = ItemSpriteSheet.NORMAL_ARROW;
        }

        @Override
        public int min() {
            return 10 + 5 * curUser.pointsInTalent(Talent.ENHANCED_ARROW);
        }

        @Override
        public int min(int lvl) {
            return min();
        }

        @Override
        public int max() {
            return 40 + 10 * curUser.pointsInTalent(Talent.ENHANCED_ARROW);
        }

        @Override
        public int max(int lvl) {
            return max();
        }

        @Override
        public int proc(Char attacker, Char defender, int damage) {
            int dmg = super.proc(attacker, defender, damage);

            if (Random.Float() < 0.25f*curUser.pointsInTalent(Talent.KNOCKING_ARROW)) {
                Buff.affect(defender, Paralysis.class, 10);
            } else {
                Buff.affect(defender, Cripple.class, 10);
            }

            //딱 붙어 있으면 0
            int distance = Dungeon.level.distance(attacker.pos, defender.pos)-1;
            float multi = Math.min(2.5f, (float) Math.pow(1.1f, distance));
            dmg = Math.round(dmg*multi);
            return dmg;
        }

        @Override
        public float accuracyFactor(Char owner, Char target) {
            float ACC = super.accuracyFactor(owner, target);

            ACC *= 1f + 0.5f*curUser.pointsInTalent(Talent.ACCURATE_SNIPE);

            return ACC;
        }

        @Override
        protected void onThrow(int cell) {
            Char ch = Actor.findChar(cell);
            if (ch == null) return;
            curUser.shoot(ch, this);
        }

        @Override
        public int throwPos(Hero user, int dst) {
            return dst;
        }

        @Override
        public void throwSound() {
            Sample.INSTANCE.play( Assets.Sounds.ATK_SPIRITBOW, 1, Random.Float(0.87f, 1.15f) );
        }
    }
}
