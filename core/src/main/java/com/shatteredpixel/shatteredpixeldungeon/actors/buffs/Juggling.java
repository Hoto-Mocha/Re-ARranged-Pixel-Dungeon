package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Juggling extends Buff implements ActionIndicator.Action {
    {
        type = buffType.NEUTRAL;
    }

    Queue<MissileWeapon> weapons = new LinkedList<>();

    @Override
    public int icon() {
        return BuffIndicator.JUGGLING;
    }

    private int maxWeapons(Hero hero) {
        return 3 + hero.pointsInTalent(Talent.SKILLFUL_JUGGLING);
    }

    public void juggle(Hero hero, MissileWeapon wep) {
        weapons.offer(wep);
        if (weapons.size() > (maxWeapons(hero))) {
            MissileWeapon polled = weapons.poll();
            if (polled != null) {
                polled.doPickUp(hero, hero.pos);
                GLog.i(Messages.get(hero, "you_now_have", polled.name()));
            }
            hero.spend(-1);
        }
        hero.sprite.zap(hero.pos);
        Sample.INSTANCE.play(Assets.Sounds.MISS);

        ActionIndicator.setAction(this);
    }

    @Override
    public void detach() {
        for (MissileWeapon weapon : weapons) {
            if (weapon != null) Dungeon.level.drop(weapon, target.pos);
        }
        ActionIndicator.clearAction();

        super.detach();
    }

    @Override
    public boolean act() {

        if (weapons.isEmpty()) {
            detach();
        }

        spend(TICK);

        return true;
    }

    @Override
    public String desc() {
        StringBuilder sb = new StringBuilder();
        Iterator<MissileWeapon> iterator = weapons.iterator();
        while (iterator.hasNext()) {
            MissileWeapon weapon = iterator.next();

            sb.append(weapon.name());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        return Messages.get(this, "desc", sb.toString());
    }

    @Override
    public String actionName() {
        return Messages.get(this, "action_name");
    }

    @Override
    public int actionIcon() {
        return HeroIcon.JUGGLING;
    }

    @Override
    public int indicatorColor() {
        return 0xB3B3B3;
    }

    @Override
    public void doAction() {
        GameScene.selectCell(listener);
    }

    private CellSelector.Listener listener = new CellSelector.Listener() {

        @Override
        public void onSelect(Integer cell) {
            if (cell != null) {
                while (!weapons.isEmpty()) {
                    MissileWeapon weapon = weapons.poll();
                    if (weapon != null && weapon.STRReq() <= Dungeon.hero.STR()) {
                        weapon.cast(Dungeon.hero, cell, false, weapons.isEmpty() ? 1 : 0);
                    } else { //현재 힘보다 무거운 무기를 저글링 중일 때 던지지 않고 바닥에 떨어뜨림
                        Dungeon.level.drop(weapon, Dungeon.hero.pos);
                        if (weapons.isEmpty()) { //마지막 무기가 무거운 경우 이전에 던진 투척에는 턴을 소모하지 않으므로 여기에서 턴을 소모함
                            Dungeon.hero.spendAndNext(1);
                        }
                    }
                }

                detach();
            }
        }

        @Override
        public String prompt() {
            return Messages.get(SpiritBow.class, "prompt");
        }
    };
}
