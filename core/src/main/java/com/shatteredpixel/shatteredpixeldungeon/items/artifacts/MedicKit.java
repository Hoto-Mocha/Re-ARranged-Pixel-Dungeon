package com.shatteredpixel.shatteredpixeldungeon.items.artifacts;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
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
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class MedicKit extends Artifact {
    {
        image = ItemSpriteSheet.FIRST_AID_KIT;

        levelCap = 10;

        defaultAction = AC_USE;
    }

    public static final String AC_USE = "USE";
    public static final String AC_ADD = "ADD";

    public static ArrayList<String> immuneList = new ArrayList<>();
    static {
        immuneList.add(Messages.get(Cripple.class, "name"));
        immuneList.add(Messages.get(Ooze.class, "name"));
        immuneList.add(Messages.get(Blindness.class, "name"));
        immuneList.add(Messages.get(Chill.class, "name"));
        immuneList.add(Messages.get(Vertigo.class, "name"));
        immuneList.add(Messages.get(Corrosion.class, "name"));
        immuneList.add(Messages.get(Weakness.class, "name"));
        immuneList.add(Messages.get(Poison.class, "name"));
        immuneList.add(Messages.get(Bleeding.class, "name"));
        immuneList.add(Messages.get(Paralysis.class, "name"));
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        if (isEquipped( hero )
                && level() < levelCap
                && !cursed) {
            actions.add(AC_ADD);
        }
        if (isEquipped( hero )
                && charge >= 1
                && !cursed) {
            actions.add(AC_USE);
        }
        return actions;
    }

    private int healAmt() {
        int healing = Math.round(charge * (this.level()+1) * 0.5f);
        if (Dungeon.isChallenged(Challenges.NO_HEALING)) healing = Math.round(healing * 0.33f);
        return healing;
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
        if (healAmt() < 1) {
            GLog.w(Messages.get(this, "no_charge"));
            return;
        }
        Healing healing = Buff.affect(hero, Healing.class);
        healing.setHeal(healAmt(), 0.25f, 0);
        healing.applyVialEffect();
        hero.sprite.operate(hero.pos);
        hero.spendAndNext(Actor.TICK);
        Sample.INSTANCE.play(Assets.Sounds.DRINK);
        charge = 0;
        updateQuickslot();
    }

    @Override
    public String info() {
        String info = super.info();

        if (cursed && cursedKnown && !isEquipped( Dungeon.hero )) {
            info += "\n\n" + Messages.get(Artifact.class, "curse_known");
        } else if (!isIdentified() && cursedKnown && !isEquipped( Dungeon.hero)) {
            info += "\n\n" + Messages.get(Artifact.class, "not_cursed");
        }

        if (!cursed && isEquipped( Dungeon.hero )) {
            info += "\n\n" + Messages.get(this, "use_desc", healAmt());
        }

        return info;
    }

    @Override
    public String status() {
        return Messages.format( "%d", healAmt() );
    }

    private String inventoryTitle(){
        return Messages.get(this, "inv_title");
    }

    private void onUpgrade(int upgrades, Item item) {
        if (upgrades > 0) {
            if (this.level() + upgrades > levelCap) {
                upgrades = levelCap - this.level();
            }
            GLog.p(Messages.get(MedicKit.class, "upgrade"));

            MedicKit.this.upgrade(upgrades);
            if (Dungeon.hero.buff(MedicKitBuff.class) != null) {
                Dungeon.hero.buff(MedicKitBuff.class).updateImmunity();
            }
            item.detach(Dungeon.hero.belongings.backpack);
            Dungeon.hero.sprite.operate(Dungeon.hero.pos);
            Dungeon.hero.spendAndNext(Actor.TICK);
            Sample.INSTANCE.play(Assets.Sounds.UNLOCK);
        }
    }

    public void charge(Hero hero, float amount) {
        float chargeAmt = amount * RingOfEnergy.artifactChargeMultiplier(hero);
        if (charge + chargeAmt >= hero.HT) {
            chargeAmt = hero.HT-charge; //can't charge over the hero's max HP
        }
        if (cursed) chargeAmt = 0;

        partialCharge += chargeAmt;
        while (partialCharge >= 1) {
            charge++;
            partialCharge--;
        }
        updateQuickslot();
    }

    private String immuneList() {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < this.level(); i++) {
            if (i == 0) {
                list.append(immuneList.get(i));
            } else {
                list.append(", ").append(immuneList.get(i));
            }
        }
        return list.toString();
    }

    @Override
    public String desc() {
        String desc = super.desc();

        if (isEquipped( Dungeon.hero )){
            if (cursed) {
                desc += "\n\n";
                desc += Messages.get(this, "desc_cursed");
            } else {
                if (!immuneList().isEmpty()) {
                    desc += "\n\n";
                    desc += Messages.get(this, "desc_equipped", immuneList());
                }
            }
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
            return (item instanceof PillOfAwakening
                    || item instanceof PotionOfStrength
                    || item instanceof ElixirOfMight
                    || item instanceof ElixirOfTalent)
                    && item.isIdentified();
        }

        @Override
        public void onSelect( Item item ) {
            if (itemSelectable(item)) {
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

            onUpgrade(upgrade, item);
        }
    };

    @Override
    public int value() {
        return 0;
    }

    @Override
    protected ArtifactBuff passiveBuff() {
        return new MedicKitBuff();
    }

    public class MedicKitBuff extends ArtifactBuff {
        {
            updateImmunity();
        }

        @Override
        public boolean act() {
            cleanse();
            return super.act();
        }

        private void cleanse() {
            Buff debuff;
            switch (MedicKit.this.level()) {
                case 10:
                    if ((debuff = Dungeon.hero.buff(Paralysis.class)) != null) debuff.detach();
                case 9:
                    if ((debuff = Dungeon.hero.buff(Bleeding.class)) != null) debuff.detach();
                case 8:
                    if ((debuff = Dungeon.hero.buff(Poison.class)) != null) debuff.detach();
                case 7:
                    if ((debuff = Dungeon.hero.buff(Weakness.class)) != null) debuff.detach();
                case 6:
                    if ((debuff = Dungeon.hero.buff(Corrosion.class)) != null) debuff.detach();
                case 5:
                    if ((debuff = Dungeon.hero.buff(Vertigo.class)) != null) debuff.detach();
                case 4:
                    if ((debuff = Dungeon.hero.buff(Chill.class)) != null) debuff.detach();
                case 3:
                    if ((debuff = Dungeon.hero.buff(Blindness.class)) != null) debuff.detach();
                case 2:
                    if ((debuff = Dungeon.hero.buff(Ooze.class)) != null) debuff.detach();
                case 1:
                    if ((debuff = Dungeon.hero.buff(Cripple.class)) != null) debuff.detach();
                case 0: default:
                    break;
            }
        }

        private void updateImmunity() {
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