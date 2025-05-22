/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.scenes;

import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.SeedFinder;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Languages;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.Archs;
import com.shatteredpixel.shatteredpixeldungeon.ui.ExitButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.ScrollPane;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTextInput;
import com.watabou.noosa.Camera;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.ui.Component;

import java.util.Arrays;

public class SeedFindScene extends PixelScene {
	@Override
	public void create() {
		super.create();

		final float colWidth = 200;
		final float fullWidth = colWidth * (landscape() ? 2 : 1);

		int w = Camera.main.width;
		int h = Camera.main.height;

		Archs archs = new Archs();
		archs.setSize(w, h);
		add(archs);

		//darkens the arches
		add(new ColorBlock(w, h, 0x88000000));

		ScrollPane list = new ScrollPane(new Component());
		add(list);

		Component content = list.content();
		content.clear();

		ShatteredPixelDungeon.scene().addToFront(new WndTextInput(Messages.get(this, "title"), Messages.get(this, "body"), Messages.get(this, "initial_value"), 1000, true, Messages.get(this, "find"), Messages.get(HeroSelectScene.class, "custom_seed_clear")) {
			@Override
			public void onSelect(boolean positive, String text) {
				int floor = 31;
				boolean floorOption = false;
				String up_to_floor;
				if (Messages.lang() == Languages.KOREAN) {
					up_to_floor = "층까지";
				} else {
					up_to_floor = "floor end";
				}
				String strFloor;
				if (Messages.lang() == Languages.KOREAN) {
					strFloor = "층";
				} else {
					strFloor = "floor";
				}
				if (text.contains(up_to_floor)) {
					floorOption = true;
					String fl = text.split(strFloor)[0].trim();
					try {
						floor = Integer.parseInt(fl);
					} catch (
							NumberFormatException e) {
					}
				}
				if (positive && text != "") {
					String[] itemList = floorOption ? Arrays.copyOfRange(text.split("\n"), 1, text.split("\n").length) : text.split("\n");

					Component content = list.content();
					content.clear();

					CreditsBlock txt = new CreditsBlock(true, Window.TITLE_COLOR, new SeedFinder().findSeed(itemList, floor));
					txt.setRect((Camera.main.width - colWidth)/2f, 12, colWidth, 0);
					content.add(txt);

					content.setSize( fullWidth, txt.bottom()+10 );

					list.setRect( 0, 0, w, h );
					list.scrollTo(0, 0);

				} else {
					SPDSettings.customSeed("");
					ShatteredPixelDungeon.switchNoFade(HeroSelectScene.class);
				}
			}
		});

		ExitButton btnExit = new ExitButton() {
			@Override
			protected void onClick() {
				if (Game.scene() instanceof TitleScene) {
					Game.instance.finish();
				} else {
					ShatteredPixelDungeon.switchNoFade( HeroSelectScene.class );
				}
			}
		};
		btnExit.setPos(Camera.main.width - btnExit.width(), 0);
		add(btnExit);

		//fadeIn();
	}

	@Override
	protected void onBackPressed() {
		ShatteredPixelDungeon.switchScene(HeroSelectScene.class);
	}

	private void addLine(float y, Group content) {
		ColorBlock line = new ColorBlock(Camera.main.width, 1, 0xFF333333);
		line.y = y;
		content.add(line);
	}


	public static class CreditsBlock extends Component {

		boolean large;

		RenderedTextBlock body;

		public CreditsBlock(boolean large, int highlight, String body) {
			super();

			this.large = large;

			this.body = PixelScene.renderTextBlock(body, 6);
			if (highlight != -1)
				this.body.setHightlighting(true, highlight);
			if (large)
				this.body.align(RenderedTextBlock.CENTER_ALIGN);
			add(this.body);
		}

		@Override
		protected void layout() {
			super.layout();

			float topY = top();

			if (large){
				body.maxWidth((int)width());
				body.setPos( x + (width() - body.width())/2f, topY);
			} else {
				topY += 1;
				body.maxWidth((int)width());
				body.setPos( x, topY);
			}

			topY += body.height();

			height = Math.max(height, topY - top());
		}
	}
}