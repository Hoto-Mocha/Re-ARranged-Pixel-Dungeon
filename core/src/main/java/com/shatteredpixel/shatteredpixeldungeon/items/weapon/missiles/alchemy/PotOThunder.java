package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.alchemy;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Electricity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ThunderImbue;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.ShockingBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ForceCube;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class PotOThunder extends MissileWeapon {
    {
        image = ItemSpriteSheet.THUNDERBOLT;
        hitSound = Assets.Sounds.LIGHTNING;
        hitSoundPitch = 1.2f;

        tier = 5;
        sticky = true;

        baseUses = 10;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        Buff.affect(defender, Paralysis.class, Random.NormalIntRange(3, 5));

        defender.damage(magicDamage(this.buffedLvl()), new Electricity());
        ThunderImbue.thunderEffect(defender.sprite);

        CharSprite s = defender.sprite;
        if (s != null && s.parent != null) {
            ArrayList<Lightning.Arc> arcs = new ArrayList<>();
            arcs.add(new Lightning.Arc(new PointF(s.x, s.y + s.height / 2), new PointF(s.x + s.width, s.y + s.height / 2)));
            arcs.add(new Lightning.Arc(new PointF(s.x + s.width / 2, s.y), new PointF(s.x + s.width / 2, s.y + s.height)));
            s.parent.add(new Lightning(arcs, null));
        }

        return super.proc(attacker, defender, damage);
    }

    @Override
    public String info() {
        String info = super.info();

        info += "\n\n" + Messages.get(this, "magic_damage", magicMin(buffedLvl()), magicMax(buffedLvl()));

        return info;
    }

    @Override
    public int min(int lvl) {
        return 10;
    }

    @Override
    public int max(int lvl) {
        return  10;
    }

    public int magicMin(int lvl) {
        return tier+lvl+RingOfSharpshooting.levelDamageBonus(Dungeon.hero);
    }

    public int magicMax(int lvl) {
        return (5+lvl+RingOfSharpshooting.levelDamageBonus(Dungeon.hero))*tier;
    }

    public int magicDamage(int lvl) { //magic damage
        return Random.NormalIntRange(magicMin(lvl), magicMax(lvl));
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {
        {
            inputs =  new Class[]{ShockingBrew.class, ScrollOfRecharging.class, ForceCube.class};
            inQuantity = new int[]{1, 1, 1};

            cost = 3;

            output = PotOThunder.class;
            outQuantity = 1;
        }
    }
}
