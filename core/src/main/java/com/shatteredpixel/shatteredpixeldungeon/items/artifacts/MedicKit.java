package com.shatteredpixel.shatteredpixeldungeon.items.artifacts;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.PotionBandolier;
import com.shatteredpixel.shatteredpixeldungeon.items.pills.PillOfAwakening;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfTalent;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEnergy;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;

import java.util.ArrayList;

public class MedicKit extends Artifact {
    {
        image = ItemSpriteSheet.FIRST_AID_KIT;

        levelCap = 10;
    }

    public static final String AC_USE = "USE";
    public static final String AC_ADD = "ADD";

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        if (isEquipped( hero )
                && level() < levelCap
                && !cursed) {
            actions.add(AC_ADD);
        }
        if (isEquipped( hero ) && charge >= 1) {
            actions.add(AC_USE);
        }
        return actions;
    }

    @Override
    public void execute(Hero hero, String action ) {
        super.execute(hero, action);

        if (action.equals(AC_ADD)){
            GameScene.selectItem( itemSelector );
        }
        if (action.equals(AC_USE)) {
            useKit(hero);
        }
    }

    private void useKit(Hero hero) {
        if (charge < 1) {
            GLog.w(Messages.get(this, "no_charge"));
            return;
        }
        int healAmt = Math.round(charge * this.level() * 0.5f);
        if (Dungeon.isChallenged(Challenges.NO_HEALING)) healAmt = Math.round(healAmt * 0.33f);
        if (healAmt > 0) {
            Healing healing = Buff.affect(hero, Healing.class);
            healing.setHeal(healAmt, 0.25f, 0);
            healing.applyVialEffect();
            hero.sprite.operate(hero.pos);
            hero.spendAndNext(Actor.TICK);

            charge = 0;
        }
    }

    @Override
    public String info() {
        String info = super.info();

        int healAmt = Math.round(charge * this.level() * 0.5f); // for desc
        if (Dungeon.isChallenged(Challenges.NO_HEALING)) healAmt = Math.round(healAmt * 0.33f); // for desc

        if (cursed && cursedKnown && !isEquipped( Dungeon.hero )) {
            info += "\n\n" + Messages.get(Artifact.class, "curse_known");
        } else if (!isIdentified() && cursedKnown && !isEquipped( Dungeon.hero)) {
            info += "\n\n" + Messages.get(Artifact.class, "not_cursed");
        }
        if (!cursed && isEquipped( Dungeon.hero )) {
            info += "\n\n" + Messages.get(this, "use_desc", healAmt);
        }
        return info;
    }

    private String inventoryTitle(){
        return Messages.get(this, "inv_title");
    }

    private void onUpgrade(int upgrades) {
        if (upgrades > 0) {
            GLog.p(Messages.get(MedicKit.class, "upgrade"));

            MedicKit.this.upgrade(upgrades);
        }
    }

    public void charge(Hero hero, float amount) {
        float chargeAmt = amount * RingOfEnergy.artifactChargeMultiplier(hero);
        if (charge >= hero.HT) {
            chargeAmt *= 0.25f; //x0.25 if current charge is over the hero's max HP
        }
        if (cursed) chargeAmt = 0;

        partialCharge += chargeAmt;
        while (partialCharge >= 1) {
            charge++;
            partialCharge--;
        }
        updateQuickslot();
    }

    @Override
    public String desc() {
        String desc = super.desc();

        if (isEquipped( Dungeon.hero )){
            desc += "\n\n";
            if (cursed)
                desc += Messages.get(this, "desc_cursed");
            else
                desc += Messages.get(this, "desc_equipped");
        }
        return desc;
    }

    protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

        @Override
        public String textPrompt() {
            return inventoryTitle();
        }

        @Override
        public Class<? extends Bag> preferredBag() {
            return PotionBandolier.class;
        }

        // PillOfAwakening: +1
        // PotionOfStrength: +2
        // ElixirOfMight: +2
        // ElixirOfTalent: +4
        @Override
        public boolean itemSelectable(Item item) {
            return item instanceof PillOfAwakening || item instanceof PotionOfStrength || item instanceof ElixirOfMight || item instanceof ElixirOfTalent;
        }

        @Override
        public void onSelect( Item item ) {
            if (item != null && itemSelectable(item)) {
                onItemSelected(item);
            }
        }

        private void onItemSelected(Item item) {
            int upgrade;
            if (item instanceof PillOfAwakening) {
                upgrade = 1;
            } else if (item instanceof PotionOfStrength || item instanceof ElixirOfMight) {
                upgrade = 2;
            } else if (item instanceof ElixirOfTalent) {
                upgrade = 4;
            } else {
                upgrade = 0;
            }

            onUpgrade(upgrade);
        }
    };

    @Override
    public int value() {
        return 0;
    }

    @Override
    protected ArtifactBuff passiveBuff() {
        return new firstAidBuff();
    }

    public class firstAidBuff extends ArtifactBuff {
        {
            switch (MedicKit.this.level()) {
                case 10:
                    immunities.add(Paralysis.class);
                case 9:
                    immunities.add(Bleeding.class);
                case 8:
                    immunities.add(Poison.class);
                case 7:
                    immunities.add(Weakness.class);
                case 6:
                    immunities.add(Corrosion.class);
                case 5:
                    immunities.add(Vertigo.class);
                case 4:
                    immunities.add(Chill.class);
                case 3:
                    immunities.add(Blindness.class);
                case 2:
                    immunities.add(Ooze.class);
                case 1:
                    immunities.add(Cripple.class);
                case 0: default:
                    break;
            }
        }
    }
}