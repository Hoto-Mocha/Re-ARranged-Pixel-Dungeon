package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.PotionBandolier;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfInvisibility;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfParalyticGas;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfPurity;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.AquaBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.BlizzardBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.Brew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.CausticBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.InfernalBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.ShockingBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.UnstableBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.Elixir;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.ExoticPotion;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfCorrosiveGas;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfDivineInspiration;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfDragonsBreath;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfEarthenArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfMagicalSight;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfMastery;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfShielding;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfShroudingFog;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfSnapFreeze;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfStamina;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfStormClouds;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class ArrowBag extends Item {
    {
        image = ItemSpriteSheet.ARROW_BAG;
        levelKnown = true;
        unique = true;
        bones = false;

        defaultAction = AC_INJECT;
    }

    private static final String AC_INJECT = "INJECT";

    private static final String TXT_STATUS	= "%d";

    public Potion potion;
    public int uses;

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add(AC_INJECT);
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals(AC_INJECT)) {
            GameScene.selectItem(itemSelector);
        }
    }

    private static final String POTION	    = "potion";
    private static final String USES	    = "uses";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);

        bundle.put( POTION, potion );
        bundle.put( USES, uses );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        potion = (Potion)bundle.get( POTION );
        uses = bundle.getInt( USES );
    }

    @Override
    public ItemSprite.Glowing glowing() {
        if (potion != null) {
            return potion.potionGlowing();
        } else {
            return null;
        }
    }

    public int proc(Hero hero, Char enemy, int damage) {
        float dmg = damage;
        if (potion != null) {
            potion.potionProc(hero, enemy, dmg);
            uses--;
            dmg *= (1.1f);
        }

        if (uses <= 0) {
            potion = null;
            updateQuickslot();
        }

        return Math.round(dmg);
    }

    @Override
    public String desc() {
        String desc = super.desc();

        if (potion != null) {
            desc += "\n\n" + Messages.get(this, "potion_desc", potion.name(), uses);
            desc += "\n";

            if (potion instanceof ExoticPotion) {
                if (potion instanceof PotionOfShielding) {
                    desc += Messages.get(this, "desc_shielding");
                }
                if (potion instanceof PotionOfMagicalSight) {
                    desc += Messages.get(this, "desc_magicalsight");
                }
                if (potion instanceof PotionOfSnapFreeze) {
                    desc += Messages.get(this, "desc_snapfreeze");
                }
                if (potion instanceof PotionOfDragonsBreath) {
                    desc += Messages.get(this, "desc_dragonsbreath");
                }
                if (potion instanceof PotionOfCorrosiveGas) {
                    desc += Messages.get(this, "desc_corrosivegas");
                }
                if (potion instanceof PotionOfStamina) {
                    desc += Messages.get(this, "desc_stamina");
                }
                if (potion instanceof PotionOfShroudingFog) {
                    desc += Messages.get(this, "desc_shroudingfog");
                }
                if (potion instanceof PotionOfStormClouds) {
                    desc += Messages.get(this, "desc_stormclouds");
                }
                if (potion instanceof PotionOfEarthenArmor) {
                    desc += Messages.get(this, "desc_earthenarmor");
                }
                if (potion instanceof PotionOfCleansing) {
                    desc += Messages.get(this, "desc_cleansing");
                }
                if (potion instanceof PotionOfDivineInspiration) {
                    desc += Messages.get(this, "desc_divineinspiration");
                }
            } else if (potion instanceof Brew) {
                if (potion instanceof AquaBrew) {
                    desc += Messages.get(this, "desc_aqua");
                }
                if (potion instanceof BlizzardBrew) {
                    desc += Messages.get(this, "desc_blizzard");
                }
                if (potion instanceof CausticBrew) {
                    desc += Messages.get(this, "desc_caustic");
                }
                if (potion instanceof InfernalBrew) {
                    desc += Messages.get(this, "desc_infernal");
                }
                if (potion instanceof ShockingBrew) {
                    desc += Messages.get(this, "desc_shocking");
                }
                if (potion instanceof UnstableBrew) {
                    desc += Messages.get(this, "desc_unstable");
                }
            } else {
                if (potion instanceof PotionOfHealing) {
                    desc += Messages.get(this, "desc_healing");
                }
                if (potion instanceof PotionOfMindVision) {
                    desc += Messages.get(this, "desc_mindvision");
                }
                if (potion instanceof PotionOfFrost) {
                    desc += Messages.get(this, "desc_frost");
                }
                if (potion instanceof PotionOfLiquidFlame) {
                    desc += Messages.get(this, "desc_liquidflame");
                }
                if (potion instanceof PotionOfToxicGas) {
                    desc += Messages.get(this, "desc_toxicgas");
                }
                if (potion instanceof PotionOfHaste) {
                    desc += Messages.get(this, "desc_haste");
                }
                if (potion instanceof PotionOfInvisibility) {
                    desc += Messages.get(this, "desc_invisibility");
                }
                if (potion instanceof PotionOfLevitation) {
                    desc += Messages.get(this, "desc_levitation");
                }
                if (potion instanceof PotionOfParalyticGas) {
                    desc += Messages.get(this, "desc_paralyticgas");
                }
                if (potion instanceof PotionOfPurity) {
                    desc += Messages.get(this, "desc_purity");
                }
                if (potion instanceof PotionOfExperience) {
                    desc += Messages.get(this, "desc_experience");
                }
            }
        }
        return desc;
    }

    protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

        @Override
        public String textPrompt() {
            return Messages.get(ArrowBag.class, "select_title");
        }

        @Override
        public Class<? extends Bag> preferredBag() {
            return PotionBandolier.class;
        }

        @Override
        public boolean itemSelectable(Item item) {
            return item.isIdentified()
                    && item instanceof Potion
                    && !(item instanceof Elixir)
                    && !((item instanceof ExoticPotion || item instanceof Brew)
                        && Dungeon.hero.subClass != HeroSubClass.BOWMASTER);
        }

        @Override
        public void onSelect(Item item) {
            if (item == null) return;

            item.detach(Dungeon.hero.belongings.backpack);
            if (item instanceof PotionOfStrength || item instanceof PotionOfMastery) {
                upgrade();
            } else {
                potion = (Potion)item;
                uses = Math.max(1, Math.round(((Potion)item).talentFactor()*(15 + 3 * (level() + Dungeon.hero.pointsInTalent(Talent.BOTTLE_EXPANSION)))));
            }

            Dungeon.hero.sprite.operate(Dungeon.hero.pos);
            Sample.INSTANCE.play(Assets.Sounds.DRINK);

            updateQuickslot();
        }
    };

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

    @Override
    public String status() {
        return Messages.format( TXT_STATUS, uses );
    }
}
