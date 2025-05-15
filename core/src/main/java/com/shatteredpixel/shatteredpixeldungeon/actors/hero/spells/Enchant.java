package com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Enchanting;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HolyTome;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class Enchant extends InventoryClericSpell {

    public static final Enchant INSTANCE = new Enchant();

    @Override
    public int icon(){
        return HeroIcon.ENCHANT;
    }

    @Override
    protected boolean usableOnItem(Item item) {
        return item.isIdentified() && !item.cursed && (item instanceof Weapon || item instanceof Armor);
    }

    @Override
    public float chargeUse(Hero hero) {
        return 7-hero.pointsInTalent(Talent.ENCHANT);
    }

    @Override
    protected void onItemSelected(HolyTome tome, Hero hero, Item item) {
        hero.busy();

        if (item instanceof Weapon) {
            ((Weapon)item).enchant();
        } else {
            ((Armor)item).inscribe();
        }

        hero.sprite.emitter().start( Speck.factory( Speck.LIGHT ), 0.1f, 5 );
        Enchanting.show( hero, item );

        if (item instanceof Weapon) {
            GLog.p(Messages.get(StoneOfEnchantment.class, "weapon"));
        } else {
            GLog.p(Messages.get(StoneOfEnchantment.class, "armor"));
        }
        hero.sprite.operate(hero.pos);
        hero.spendAndNext(Actor.TICK);
        Sample.INSTANCE.play(Assets.Sounds.READ);

        onSpellCast(tome, hero);
    }
}
