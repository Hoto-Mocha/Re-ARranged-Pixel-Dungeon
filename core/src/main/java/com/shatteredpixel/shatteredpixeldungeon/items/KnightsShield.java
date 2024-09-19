package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.curses.Bulk;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Flow;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Swiftness;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfArcana;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;

public class KnightsShield extends Item {

    {
        image = ItemSpriteSheet.KNIGHT_SHIELD;
        levelKnown = true;

        bones = false;
        unique = true;
    }

    public Armor.Glyph glyph;
    public boolean curseInfusionBonus = false;

    private static final String GLYPH			= "glyph";
    private static final String CURSE_INFUSION_BONUS = "curse_infusion_bonus";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( GLYPH, glyph );
        bundle.put( CURSE_INFUSION_BONUS, curseInfusionBonus );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        glyph = (Armor.Glyph) bundle.get(GLYPH);
        curseInfusionBonus = bundle.getBoolean( CURSE_INFUSION_BONUS );
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return glyph != null ? glyph.glowing() : null;
    }

    @Override
    public String name() {
        return glyph != null ? glyph.name( super.name() ) : super.name();
    }

    @Override
    public String info() {
        String info = Messages.get(this, "desc");
        if (Dungeon.hero != null) {
            info += "\n\n" + Messages.get(this, "stats_desc", this.defenseFactor());
        }
        if (glyph != null) {
            info += "\n\n" +  Messages.capitalize(Messages.get(Armor.class, "inscribed", glyph.name()));
            info += " " + glyph.desc();
        }
        return info;
    }

    public int proc( Char attacker, Char defender, int damage ) {
        if (glyph != null && defender.buff(MagicImmune.class) == null) {
            Armor armor = new Armor(0);
            armor.level(this.level());
            damage = glyph.proc( armor, attacker, defender, damage );
        }

        return damage;
    }

    public float speedFactor( Char owner, float speed ){
        if (hasGlyph(Swiftness.class, owner)) {
            boolean enemyNear = false;
            //for each enemy, check if they are adjacent, or within 2 tiles and an adjacent cell is open
            for (Char ch : Actor.chars()){
                if ( Dungeon.level.distance(ch.pos, owner.pos) <= 2 && owner.alignment != ch.alignment && ch.alignment != Char.Alignment.NEUTRAL){
                    if (Dungeon.level.adjacent(ch.pos, owner.pos)){
                        enemyNear = true;
                        break;
                    } else {
                        for (int i : PathFinder.NEIGHBOURS8){
                            if (Dungeon.level.adjacent(owner.pos+i, ch.pos) && !Dungeon.level.solid[owner.pos+i]){
                                enemyNear = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (!enemyNear) speed *= (1.2f + 0.04f * buffedLvl()) * glyph.procChanceMultiplier(owner);
        } else if (hasGlyph(Flow.class, owner) && Dungeon.level.water[owner.pos]){
            speed *= (2f + 0.5f*buffedLvl()) * glyph.procChanceMultiplier(owner);
        }

        if (hasGlyph(Bulk.class, owner) &&
                (Dungeon.level.map[owner.pos] == Terrain.DOOR
                        || Dungeon.level.map[owner.pos] == Terrain.OPEN_DOOR )) {
            speed /= 3f * RingOfArcana.enchantPowerMultiplier(owner);
        }

        return speed;
    }

    public boolean hasGlyph(Class<?extends Armor.Glyph> type, Char owner) {
        return glyph != null && glyph.getClass() == type && owner.buff(MagicImmune.class) == null;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public int level() {
        int level = Dungeon.hero == null ? 0 : Dungeon.hero.lvl/5;
        if (curseInfusionBonus) level += 1 + level/6;
        return level;
    }

    @Override
    public int buffedLvl() {
        //level isn't affected by buffs/debuffs
        return level();
    }

    public int defenseFactor() {
        int additionalArmor = 2*(1+buffedLvl());
        if (Dungeon.hero != null && Dungeon.hero.hasTalent(Talent.HARD_SHIELD)) {
            additionalArmor += 2*Dungeon.hero.pointsInTalent(Talent.HARD_SHIELD);
        }
        return additionalArmor;
    }

    //these are not used to process specific glyph effects, so magic immune doesn't affect them
    public boolean hasGoodGlyph(){
        return glyph != null && !glyph.curse();
    }

    public void inscribe( boolean isCurse ) {
        if (isCurse) {
            if (this.glyph != null){
                //if we are freshly applying curse infusion, don't replace an existing curse
                if (this.hasGoodGlyph() || this.curseInfusionBonus) {
                    this.glyph = Armor.Glyph.randomCurse(this.glyph.getClass());
                }
            } else {
                this.glyph = Armor.Glyph.randomCurse();
            }
            this.curseInfusionBonus = true;
        } else {
            Class<? extends Armor.Glyph> oldGlyphClass = this.glyph != null ? this.glyph.getClass() : null;
            Armor.Glyph gl = Armor.Glyph.random( oldGlyphClass );
            this.glyph = gl;
            this.curseInfusionBonus = false;
        }
        updateQuickslot();
    }
}
