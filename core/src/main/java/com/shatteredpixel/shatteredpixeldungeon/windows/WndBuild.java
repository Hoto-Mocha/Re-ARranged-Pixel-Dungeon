package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Build;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.Rope;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;

public class WndBuild extends Window {

    private static final int WIDTH_P = 120;
    private static final int WIDTH_L = 160;

    private static final int MARGIN  = 2;

    public WndBuild(Build build) {
        super();

        int width = PixelScene.landscape() ? WIDTH_L : WIDTH_P;

        float pos = MARGIN;
        RenderedTextBlock title = PixelScene.renderTextBlock(Messages.titleCase(Messages.get(this, "title")), 9);
        title.hardlight(TITLE_COLOR);
        title.setPos((width-title.width())/2, pos);
        title.maxWidth(width - MARGIN * 2);
        add(title);

        pos = title.bottom() + 3*MARGIN;

        Rope rope = Dungeon.hero.belongings.getItem(Rope.class);
        int ropeAmt = 0;
        if (rope != null) {
            ropeAmt = rope.quantity();
        }

        for (Build.Building building : Build.Building.values()) {
            int ropeUse = building.ropeReq;
            if (building == Build.Building.BARRICADE && Dungeon.hero.pointsInTalent(Talent.BARRICADE) > 2) {
                ropeUse /= 2;
            }
            if (building == Build.Building.WIRE && Dungeon.hero.pointsInTalent(Talent.WIRE) > 2) {
                ropeUse /= 2;
            }
            if (building == Build.Building.WATCHTOWER && Dungeon.hero.pointsInTalent(Talent.WATCHTOWER) > 2) {
                ropeUse /= 2;
            }
            String text = "_" + Messages.titleCase(building.title()) + " " + Messages.get(this, "rope_req", ropeUse) + ":_ " + building.desc();
            RedButton moveBtn = new RedButton(text, 6){
                @Override
                protected void onClick() {
                    super.onClick();
                    hide();
                    build.build(building);
                }
            };
            moveBtn.leftJustify = true;
            moveBtn.multiline = true;
            moveBtn.setSize(width, moveBtn.reqHeight());
            moveBtn.setRect(0, pos, width, moveBtn.reqHeight());
            moveBtn.enable(build.canBuild(building, ropeAmt));
            add(moveBtn);
            pos = moveBtn.bottom() + MARGIN;
        }

        resize(width, (int)pos);
    }

}
