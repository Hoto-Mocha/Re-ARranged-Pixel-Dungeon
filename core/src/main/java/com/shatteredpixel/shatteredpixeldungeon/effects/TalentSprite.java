package com.shatteredpixel.shatteredpixeldungeon.effects;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.ui.TalentIcon;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;

public class TalentSprite extends Component {

    private enum Phase {
        FADE_IN, STATIC, FADE_OUT
    }

    private static final float FADE_IN_TIME		= 0.2f;
    private static final float STATIC_TIME		= 0.4f;
    private static final float FADE_OUT_TIME	= 0.4f;

    private static final float ALPHA	= 0.6f;

    Image sprite;

    private Char target;

    private TalentSprite.Phase phase;
    private float duration;
    private float passed;

    public TalentSprite(Talent talent ){
        sprite = new TalentIcon(talent);
        sprite.originToCenter();
        add(sprite);

        sprite.alpha(0f);

        phase = TalentSprite.Phase.FADE_IN;
        duration = FADE_IN_TIME;
        passed = 0;
    }

    @Override
    public void update() {
        super.update();

        if (passed == 0) {
            sprite.x = target.sprite.center().x - sprite.width() / 2;
            sprite.y = target.sprite.y - sprite.height();
        }

        switch (phase) {
            case FADE_IN:
                sprite.alpha( passed / duration * ALPHA );
                sprite.scale.set( passed / duration );
                break;
            case STATIC:
                break;
            case FADE_OUT:
                sprite.alpha( 1 - passed / duration * ALPHA );
                sprite.scale.set( 1 + passed / duration );
                break;
        }

        if ((passed += Game.elapsed) > duration) {
            switch (phase) {
                case FADE_IN:
                    phase = Phase.STATIC;
                    duration = STATIC_TIME;
                    break;
                case STATIC:
                    phase = Phase.FADE_OUT;
                    duration = FADE_OUT_TIME;
                    break;
                case FADE_OUT:
                    kill();
                    break;
            }

            passed = 0;
        }
    }

    public static void show( Char ch, Talent talent ) {

        if (!ch.sprite.visible) {
            return;
        }

        TalentSprite sprite = new TalentSprite( talent );
        sprite.target = ch;
        ch.sprite.parent.add( sprite );
    }
}
