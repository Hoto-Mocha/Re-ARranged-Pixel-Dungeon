package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.bow.Bow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.bow.BowWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.bow.GreatBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.bow.LongBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.bow.ShortBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.bow.WornShortBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

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
        hero.spendAndNext(Math.max(0, 1f - hero.pointsInTalent(Talent.SWIFT_JUGGLING)/3f));

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

    private static final String WEAPONS = "weapons";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);

        bundle.put(WEAPONS, weapons);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        for (Bundlable item : bundle.getCollection( WEAPONS )) {
            if (item != null){
                weapons.add((MissileWeapon) item);
            }
        }
        if (weapons.isEmpty()) {
            detach();
        } else {
            ActionIndicator.setAction(this);
        }
    }

    private final CellSelector.Listener listener = new CellSelector.Listener() {

        @Override
        public void onSelect(Integer cell) {
            if (cell != null) {
                while (!weapons.isEmpty()) {
                    MissileWeapon weapon = weapons.poll();
                    if (weapon != null) {
                        if (weapon.STRReq() <= Dungeon.hero.STR()) {
                            weapon.cast(Dungeon.hero, cell, false, weapons.isEmpty() ? 1 : 0); //기존의 cast()를 사용하면 인벤토리의 투척 무기를 강제로 없애고 턴을 소모하는 문제가 있어 새로운 cast()를 정의해서 사용함
                        } else {
                            Dungeon.level.drop(weapon, Dungeon.hero.pos);
                            if (weapons.isEmpty()) { //마지막 무기가 무거운 경우 이전에 던진 투척에는 턴을 소모하지 않으므로 여기에서 턴을 소모함
                                Dungeon.hero.spendAndNext(1);
                            }
                        }
                    }
                }

                detach(); //버프를 제거한다. 만약 (그럴 일은 없지만) 저글링 중인 투척 무기가 남은 경우 바닥에 떨어뜨린다.
            }
        }

        @Override
        public String prompt() {
            return Messages.get(SpiritBow.class, "prompt");
        }
    };

    public static void kill() {
        if (Dungeon.hero.subClass == HeroSubClass.JUGGLER && Dungeon.bullet > 1 && Dungeon.hero.hasTalent(Talent.HABITUAL_HAND)) {
            BowWeapon bow;
            if (!(Dungeon.hero.belongings.weapon instanceof BowWeapon)) {
                switch (Dungeon.scalingDepth()/5+1) {
                    case 1: default:
                        bow = new WornShortBow();
                        break;
                    case 2:
                        bow = new ShortBow();
                        break;
                    case 3:
                        bow = new Bow();
                        break;
                    case 4:
                        bow = new LongBow();
                        break;
                    case 5:
                    case 6:
                        bow = new GreatBow();
                        break;
                }
            } else {
                bow = (BowWeapon) Dungeon.hero.belongings.weapon;
            }
            for (int i = 0; i < Dungeon.hero.pointsInTalent(Talent.HABITUAL_HAND); i++) {
                if (Dungeon.bullet <= 0) break;
                Buff.affect(Dungeon.hero, Juggling.class).juggle(Dungeon.hero, bow.knockArrow());
                Dungeon.bullet--;
            }
        }
    }

    public static float accuracyFactor(Hero hero) {
        if (hero.buff(Juggling.class) != null) {
            return 0.5f + 0.2f*Dungeon.hero.pointsInTalent(Talent.FOCUS_MAINTAIN);
        } else {
            return 1;
        }
    }
}
