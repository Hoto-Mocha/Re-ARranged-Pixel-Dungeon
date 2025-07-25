package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.quick;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

import java.util.ArrayList;

public class QuickWeapon extends MeleeWeapon {

    public static final String AC_SLASH	= "SLASH";

    {
        defaultAction = AC_SLASH;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.remove(AC_EQUIP);
        actions.add(AC_SLASH);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_SLASH)) {
            usesTargeting = true;
            curUser = hero;
            curItem = this;
            GameScene.selectCell(slasher);
        }
    }

    private CellSelector.Listener slasher = new CellSelector.Listener() {
        @Override
        public void onSelect( Integer target ) {
            if (target != null) {
                Char ch = Actor.findChar(target);
                Hero hero = Dungeon.hero;
                if (ch != null && ch.alignment == Char.Alignment.ENEMY) {
                    KindOfWeapon herosWeapon = hero.belongings.weapon; //기존에 사용하던 무기를 저장
                    hero.belongings.weapon = QuickWeapon.this; //공격에 사용할 무기를 이 무기로 변경

                    if (!hero.canAttack(ch)) {
                        GLog.w(Messages.get(QuickWeapon.class, "cannot_reach"));
                    } else {
                        hero.sprite.zap(ch.pos);
                        hero.busy();
                        hero.spendAndNext(hero.attackDelay());
                        hero.attack(ch, 1, 0, 1);
                        Invisibility.dispel();
                    }

                    hero.belongings.weapon = herosWeapon; //영웅의 무기를 원래 무기로 되돌림
                } else {
                    GLog.w(Messages.get(QuickWeapon.class, "no_enemy"));
                }
            }
        }
        @Override
        public String prompt() {
            return Messages.get(SpiritBow.class, "prompt");
        }
    };
}
