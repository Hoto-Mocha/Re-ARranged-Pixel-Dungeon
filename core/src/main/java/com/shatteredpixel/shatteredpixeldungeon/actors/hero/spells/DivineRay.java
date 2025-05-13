package com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RevealedArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RevealedLargeArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlessingParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.EnergyParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.YellowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HolyTome;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.tweeners.Tweener;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.List;

public class DivineRay extends TargetedClericSpell {

    public static final DivineRay INSTANCE = new DivineRay();

    @Override
    public int icon(){
        return HeroIcon.DIVINE_RAY;
    }

    @Override
    public float chargeUse(Hero hero) {
        return 3;
    }

    @Override
    public int targetingFlags(){
        return Ballistica.STOP_TARGET;
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", 4*Dungeon.hero.pointsInTalent(Talent.DIVINE_RAY)) + "\n\n" + Messages.get(this, "charge_cost", (int)chargeUse(Dungeon.hero));
    }

    @Override
    protected void onTargetSelected(HolyTome tome, Hero hero, Integer target) {
        if (target == null) return;

        if (target == hero.pos) {
            GLog.w(Messages.get(this, "cannot_self"));
        }

        float delay = 1.2f;
        hero.busy();
        int targetCell = target;
        Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
        hero.sprite.parent.add(new Tweener(hero.sprite.parent, delay) { //delay초 후에 작동하도록 설정한 Tweener
            @Override
            protected void updateValues(float progress) { //시간이 지남에 따라 실행되는 함수
                if (Math.floor(100*progress % 10f) == 0 && progress < 1f) { // 0~1초 사이에서 0.1초 마다 실행
                    Emitter e = hero.sprite.centerEmitter();
                    if (e != null) e.burst(YellowParticle.FACTORY, 1);
                }
            }

            @Override
            protected void onComplete() { //시간이 다 지나면 실행되는 함수
                super.onComplete();
                shootLaser(hero, targetCell);
                onSpellCast(tome, hero);
            }
        });
    }

    public void shootLaser(Hero hero, int target) {
        int maxDistance = 4*hero.pointsInTalent(Talent.DIVINE_RAY);

        Ballistica beam = new Ballistica(hero.pos, target, Ballistica.WONT_STOP);
        ArrayList<Char> affected = new ArrayList<>();

        affectMap(beam.subPath(1, maxDistance));



        for (int c : beam.subPath(1, maxDistance)) {
            Char ch;

            if ((ch = Actor.findChar( c )) != null) {
                if ((ch instanceof Mob && ((Mob) ch).state == ((Mob) ch).PASSIVE
                        && !(Dungeon.level.mapped[c] || Dungeon.level.visited[c])) || (ch instanceof Hero)){
                    //avoid harming undiscovered passive chars
                } else {
                    affected.add(ch);
                }
            }

            CellEmitter.center( c ).burst( YellowParticle.BURST, 4 );
        }

        RevealedLargeArea a = Buff.prolong(hero, RevealedLargeArea.class, 3);
        //광선이 지나간 위치를 가지는 List를 int[]로 변환해서 저장. List는 번들에 넣어 저장할 수 없기 때문.
        List<Integer> list = beam.subPath(1, maxDistance);
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        a.pos = array;
        a.depth = Dungeon.depth;
        a.branch = Dungeon.branch;

        Dungeon.observe();

        for (Char ch : affected) {
            int minDamage = (Char.hasProp(ch, Char.Property.UNDEAD) || Char.hasProp(ch, Char.Property.DEMONIC)) ? 16 : 8;
            int damage = Random.NormalIntRange(minDamage, 16);
            if (ch.alignment == Char.Alignment.ENEMY) {
                ch.damage( damage, this );
                ch.sprite.centerEmitter().burst( YellowParticle.BURST, 4 );
                ch.sprite.flash();
            } else if (ch.alignment == Char.Alignment.ALLY) {
                int excessDamage = Math.max(0, damage - (ch.HT - ch.HP));
                ch.heal(damage);
                if (excessDamage > 0) {
                    Buff.affect(ch, Barrier.class).setShield(excessDamage);
                    ch.sprite.showStatusWithIcon( CharSprite.POSITIVE, Integer.toString(excessDamage), FloatingText.SHIELDING );
                }
            } else {
                //ignore neutral
            }
        }

        hero.sprite.zap(target);
        int cell = beam.path.get(Math.min(beam.dist, maxDistance));
        hero.sprite.parent.add(new Beam.YellowRay(hero.sprite.center(), DungeonTilemap.raisedTileCenterToWorld( cell ), 2));

        hero.spendAndNext(Actor.TICK);
        Item.updateQuickslot();
    }

    private void affectMap(List<Integer> path){
        boolean noticed = false;
        for (int c : path){
            if (!Dungeon.level.insideMap(c)){
                continue;
            }
            for (int n : PathFinder.NEIGHBOURS9){
                int cell = c+n;

                if (Dungeon.level.discoverable[cell])
                    Dungeon.level.mapped[cell] = true;

                int terr = Dungeon.level.map[cell];
                if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

                    Dungeon.level.discover( cell );

                    GameScene.discoverTile( cell, terr );
                    ScrollOfMagicMapping.discover(cell);

                    noticed = true;
                }
            }
        }
        if (noticed)
            Sample.INSTANCE.play( Assets.Sounds.SECRET );

        GameScene.updateFog();
    }
}
