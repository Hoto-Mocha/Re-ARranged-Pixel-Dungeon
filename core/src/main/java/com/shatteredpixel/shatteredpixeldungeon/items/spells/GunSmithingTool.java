/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Cartridge;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Carbine;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CarbineAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CarbineHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Handgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Magnum;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSG;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSGAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KSGHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Revolver;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RevolverAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RevolverHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TacticalHandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoHandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AutoRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MarksmanRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000AP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WA2000HP;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.IconTitle;
import com.watabou.noosa.audio.Sample;

public class GunSmithingTool extends InventorySpell {

    {
        image = ItemSpriteSheet.KIT;

        unique = true;
        bones = false;
    }

    @Override
    protected boolean usableOnItem(Item item) {
        return item instanceof CrudePistol
                || item instanceof CrudePistolAP
                || item instanceof CrudePistolHP
                || item instanceof Pistol
                || item instanceof PistolAP
                || item instanceof PistolHP
                || item instanceof GoldenPistol
                || item instanceof GoldenPistolAP
                || item instanceof GoldenPistolHP
                || item instanceof Handgun
                || item instanceof HandgunAP
                || item instanceof HandgunHP
                || item instanceof Magnum
                || item instanceof MagnumAP
                || item instanceof MagnumHP
                || item instanceof TacticalHandgun
                || item instanceof TacticalHandgunAP
                || item instanceof TacticalHandgunHP
                || item instanceof AutoHandgun
                || item instanceof AutoHandgunAP
                || item instanceof AutoHandgunHP

                || item instanceof DualPistol
                || item instanceof DualPistolAP
                || item instanceof DualPistolHP
                || item instanceof SubMachinegun
                || item instanceof SubMachinegunAP
                || item instanceof SubMachinegunHP
                || item instanceof AssultRifle
                || item instanceof AssultRifleAP
                || item instanceof AssultRifleHP
                || item instanceof HeavyMachinegun
                || item instanceof HeavyMachinegunAP
                || item instanceof HeavyMachinegunHP
                || item instanceof MiniGun
                || item instanceof MiniGunAP
                || item instanceof MiniGunHP
                || item instanceof AutoRifle
                || item instanceof AutoRifleAP
                || item instanceof AutoRifleHP

                || item instanceof Revolver
                || item instanceof RevolverAP
                || item instanceof RevolverHP
                || item instanceof HuntingRifle
                || item instanceof HuntingRifleAP
                || item instanceof HuntingRifleHP
                || item instanceof Carbine
                || item instanceof CarbineAP
                || item instanceof CarbineHP
                || item instanceof SniperRifle
                || item instanceof SniperRifleAP
                || item instanceof SniperRifleHP
                || item instanceof AntimaterRifle
                || item instanceof AntimaterRifleAP
                || item instanceof AntimaterRifleHP
                || item instanceof MarksmanRifle
                || item instanceof MarksmanRifleAP
                || item instanceof MarksmanRifleHP
                || item instanceof WA2000
                || item instanceof WA2000AP
                || item instanceof WA2000HP

                || item instanceof ShotGun
                || item instanceof ShotGunAP
                || item instanceof ShotGunHP
                || item instanceof KSG
                || item instanceof KSGAP
                || item instanceof KSGHP;
    }

    @Override
    protected void onItemSelected(Item item) {

        GameScene.show(new WndSmithing(item));

    }

    public class WndSmithing extends Window {

        private static final int WIDTH			= 120;
        private static final int MARGIN 		= 2;
        private static final int BUTTON_WIDTH	= WIDTH - MARGIN * 2;
        private static final int BUTTON_HEIGHT	= 15;

        private void smith() {
            GLog.p(Messages.get(GunSmithingTool.class, "modification"));
            Sample.INSTANCE.play( Assets.Sounds.EVOKE );
            curUser.spend( 1f );
            curUser.busy();
            (curUser.sprite).operate( curUser.pos );
            CellEmitter.center( curUser.pos ).burst( Speck.factory( Speck.STAR ), 7 );
            updateQuickslot();
        }

        public WndSmithing( final Item toSmith ) {
            super();

            IconTitle titlebar = new IconTitle( toSmith );
            titlebar.setRect( 0, 0, WIDTH, 0 );
            add( titlebar );

            RenderedTextBlock tfMesage = PixelScene.renderTextBlock( Messages.get(this, "choice"), 8 );
            tfMesage.maxWidth(WIDTH - MARGIN * 2);
            tfMesage.setPos(MARGIN, titlebar.bottom() + MARGIN);
            add( tfMesage );

            float pos = tfMesage.top() + tfMesage.height();

            if (toSmith instanceof CrudePistol){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistol)toSmith).silencer = true;
                        ((CrudePistol)toSmith).short_barrel = false;
                        ((CrudePistol)toSmith).long_barrel = false;
                        ((CrudePistol)toSmith).magazine = false;
                        ((CrudePistol)toSmith).light = false;
                        ((CrudePistol)toSmith).heavy = false;
                        ((CrudePistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistol)toSmith).silencer = false;
                        ((CrudePistol)toSmith).short_barrel = true;
                        ((CrudePistol)toSmith).long_barrel = false;
                        ((CrudePistol)toSmith).magazine = false;
                        ((CrudePistol)toSmith).light = false;
                        ((CrudePistol)toSmith).heavy = false;
                        ((CrudePistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistol)toSmith).silencer = false;
                        ((CrudePistol)toSmith).short_barrel = false;
                        ((CrudePistol)toSmith).long_barrel = true;
                        ((CrudePistol)toSmith).magazine = false;
                        ((CrudePistol)toSmith).light = false;
                        ((CrudePistol)toSmith).heavy = false;
                        ((CrudePistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistol)toSmith).silencer = false;
                        ((CrudePistol)toSmith).short_barrel = false;
                        ((CrudePistol)toSmith).long_barrel = false;
                        ((CrudePistol)toSmith).magazine = true;
                        ((CrudePistol)toSmith).light = false;
                        ((CrudePistol)toSmith).heavy = false;
                        ((CrudePistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistol)toSmith).silencer = false;
                        ((CrudePistol)toSmith).short_barrel = false;
                        ((CrudePistol)toSmith).long_barrel = false;
                        ((CrudePistol)toSmith).magazine = false;
                        ((CrudePistol)toSmith).light = true;
                        ((CrudePistol)toSmith).heavy = false;
                        ((CrudePistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistol)toSmith).silencer = false;
                        ((CrudePistol)toSmith).short_barrel = false;
                        ((CrudePistol)toSmith).long_barrel = false;
                        ((CrudePistol)toSmith).magazine = false;
                        ((CrudePistol)toSmith).light = false;
                        ((CrudePistol)toSmith).heavy = true;
                        ((CrudePistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistol)toSmith).silencer = false;
                        ((CrudePistol)toSmith).short_barrel = false;
                        ((CrudePistol)toSmith).long_barrel = false;
                        ((CrudePistol)toSmith).magazine = false;
                        ((CrudePistol)toSmith).light = false;
                        ((CrudePistol)toSmith).heavy = false;
                        ((CrudePistol)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof CrudePistolAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistolAP)toSmith).silencer = true;
                        ((CrudePistolAP)toSmith).short_barrel = false;
                        ((CrudePistolAP)toSmith).long_barrel = false;
                        ((CrudePistolAP)toSmith).magazine = false;
                        ((CrudePistolAP)toSmith).light = false;
                        ((CrudePistolAP)toSmith).heavy = false;
                        ((CrudePistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistolAP)toSmith).silencer = false;
                        ((CrudePistolAP)toSmith).short_barrel = true;
                        ((CrudePistolAP)toSmith).long_barrel = false;
                        ((CrudePistolAP)toSmith).magazine = false;
                        ((CrudePistolAP)toSmith).light = false;
                        ((CrudePistolAP)toSmith).heavy = false;
                        ((CrudePistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistolAP)toSmith).silencer = false;
                        ((CrudePistolAP)toSmith).short_barrel = false;
                        ((CrudePistolAP)toSmith).long_barrel = true;
                        ((CrudePistolAP)toSmith).magazine = false;
                        ((CrudePistolAP)toSmith).light = false;
                        ((CrudePistolAP)toSmith).heavy = false;
                        ((CrudePistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistolAP)toSmith).silencer = false;
                        ((CrudePistolAP)toSmith).short_barrel = false;
                        ((CrudePistolAP)toSmith).long_barrel = false;
                        ((CrudePistolAP)toSmith).magazine = true;
                        ((CrudePistolAP)toSmith).light = false;
                        ((CrudePistolAP)toSmith).heavy = false;
                        ((CrudePistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistolAP)toSmith).silencer = false;
                        ((CrudePistolAP)toSmith).short_barrel = false;
                        ((CrudePistolAP)toSmith).long_barrel = false;
                        ((CrudePistolAP)toSmith).magazine = false;
                        ((CrudePistolAP)toSmith).light = true;
                        ((CrudePistolAP)toSmith).heavy = false;
                        ((CrudePistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistolAP)toSmith).silencer = false;
                        ((CrudePistolAP)toSmith).short_barrel = false;
                        ((CrudePistolAP)toSmith).long_barrel = false;
                        ((CrudePistolAP)toSmith).magazine = false;
                        ((CrudePistolAP)toSmith).light = false;
                        ((CrudePistolAP)toSmith).heavy = true;
                        ((CrudePistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistolAP)toSmith).silencer = false;
                        ((CrudePistolAP)toSmith).short_barrel = false;
                        ((CrudePistolAP)toSmith).long_barrel = false;
                        ((CrudePistolAP)toSmith).magazine = false;
                        ((CrudePistolAP)toSmith).light = false;
                        ((CrudePistolAP)toSmith).heavy = false;
                        ((CrudePistolAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof CrudePistolHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistolHP)toSmith).silencer = true;
                        ((CrudePistolHP)toSmith).short_barrel = false;
                        ((CrudePistolHP)toSmith).long_barrel = false;
                        ((CrudePistolHP)toSmith).magazine = false;
                        ((CrudePistolHP)toSmith).light = false;
                        ((CrudePistolHP)toSmith).heavy = false;
                        ((CrudePistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistolHP)toSmith).silencer = false;
                        ((CrudePistolHP)toSmith).short_barrel = true;
                        ((CrudePistolHP)toSmith).long_barrel = false;
                        ((CrudePistolHP)toSmith).magazine = false;
                        ((CrudePistolHP)toSmith).light = false;
                        ((CrudePistolHP)toSmith).heavy = false;
                        ((CrudePistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistolHP)toSmith).silencer = false;
                        ((CrudePistolHP)toSmith).short_barrel = false;
                        ((CrudePistolHP)toSmith).long_barrel = true;
                        ((CrudePistolHP)toSmith).magazine = false;
                        ((CrudePistolHP)toSmith).light = false;
                        ((CrudePistolHP)toSmith).heavy = false;
                        ((CrudePistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistolHP)toSmith).silencer = false;
                        ((CrudePistolHP)toSmith).short_barrel = false;
                        ((CrudePistolHP)toSmith).long_barrel = false;
                        ((CrudePistolHP)toSmith).magazine = true;
                        ((CrudePistolHP)toSmith).light = false;
                        ((CrudePistolHP)toSmith).heavy = false;
                        ((CrudePistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistolHP)toSmith).silencer = false;
                        ((CrudePistolHP)toSmith).short_barrel = false;
                        ((CrudePistolHP)toSmith).long_barrel = false;
                        ((CrudePistolHP)toSmith).magazine = false;
                        ((CrudePistolHP)toSmith).light = true;
                        ((CrudePistolHP)toSmith).heavy = false;
                        ((CrudePistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistolHP)toSmith).silencer = false;
                        ((CrudePistolHP)toSmith).short_barrel = false;
                        ((CrudePistolHP)toSmith).long_barrel = false;
                        ((CrudePistolHP)toSmith).magazine = false;
                        ((CrudePistolHP)toSmith).light = false;
                        ((CrudePistolHP)toSmith).heavy = true;
                        ((CrudePistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CrudePistolHP)toSmith).silencer = false;
                        ((CrudePistolHP)toSmith).short_barrel = false;
                        ((CrudePistolHP)toSmith).long_barrel = false;
                        ((CrudePistolHP)toSmith).magazine = false;
                        ((CrudePistolHP)toSmith).light = false;
                        ((CrudePistolHP)toSmith).heavy = false;
                        ((CrudePistolHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof Pistol){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Pistol)toSmith).silencer = true;
                        ((Pistol)toSmith).short_barrel = false;
                        ((Pistol)toSmith).long_barrel = false;
                        ((Pistol)toSmith).magazine = false;
                        ((Pistol)toSmith).light = false;
                        ((Pistol)toSmith).heavy = false;
                        ((Pistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Pistol)toSmith).silencer = false;
                        ((Pistol)toSmith).short_barrel = true;
                        ((Pistol)toSmith).long_barrel = false;
                        ((Pistol)toSmith).magazine = false;
                        ((Pistol)toSmith).light = false;
                        ((Pistol)toSmith).heavy = false;
                        ((Pistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Pistol)toSmith).silencer = false;
                        ((Pistol)toSmith).short_barrel = false;
                        ((Pistol)toSmith).long_barrel = true;
                        ((Pistol)toSmith).magazine = false;
                        ((Pistol)toSmith).light = false;
                        ((Pistol)toSmith).heavy = false;
                        ((Pistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Pistol)toSmith).silencer = false;
                        ((Pistol)toSmith).short_barrel = false;
                        ((Pistol)toSmith).long_barrel = false;
                        ((Pistol)toSmith).magazine = true;
                        ((Pistol)toSmith).light = false;
                        ((Pistol)toSmith).heavy = false;
                        ((Pistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Pistol)toSmith).silencer = false;
                        ((Pistol)toSmith).short_barrel = false;
                        ((Pistol)toSmith).long_barrel = false;
                        ((Pistol)toSmith).magazine = false;
                        ((Pistol)toSmith).light = true;
                        ((Pistol)toSmith).heavy = false;
                        ((Pistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Pistol)toSmith).silencer = false;
                        ((Pistol)toSmith).short_barrel = false;
                        ((Pistol)toSmith).long_barrel = false;
                        ((Pistol)toSmith).magazine = false;
                        ((Pistol)toSmith).light = false;
                        ((Pistol)toSmith).heavy = true;
                        ((Pistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Pistol)toSmith).silencer = false;
                        ((Pistol)toSmith).short_barrel = false;
                        ((Pistol)toSmith).long_barrel = false;
                        ((Pistol)toSmith).magazine = false;
                        ((Pistol)toSmith).light = false;
                        ((Pistol)toSmith).heavy = false;
                        ((Pistol)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof PistolAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((PistolAP)toSmith).silencer = true;
                        ((PistolAP)toSmith).short_barrel = false;
                        ((PistolAP)toSmith).long_barrel = false;
                        ((PistolAP)toSmith).magazine = false;
                        ((PistolAP)toSmith).light = false;
                        ((PistolAP)toSmith).heavy = false;
                        ((PistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((PistolAP)toSmith).silencer = false;
                        ((PistolAP)toSmith).short_barrel = true;
                        ((PistolAP)toSmith).long_barrel = false;
                        ((PistolAP)toSmith).magazine = false;
                        ((PistolAP)toSmith).light = false;
                        ((PistolAP)toSmith).heavy = false;
                        ((PistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((PistolAP)toSmith).silencer = false;
                        ((PistolAP)toSmith).short_barrel = false;
                        ((PistolAP)toSmith).long_barrel = true;
                        ((PistolAP)toSmith).magazine = false;
                        ((PistolAP)toSmith).light = false;
                        ((PistolAP)toSmith).heavy = false;
                        ((PistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((PistolAP)toSmith).silencer = false;
                        ((PistolAP)toSmith).short_barrel = false;
                        ((PistolAP)toSmith).long_barrel = false;
                        ((PistolAP)toSmith).magazine = true;
                        ((PistolAP)toSmith).light = false;
                        ((PistolAP)toSmith).heavy = false;
                        ((PistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((PistolAP)toSmith).silencer = false;
                        ((PistolAP)toSmith).short_barrel = false;
                        ((PistolAP)toSmith).long_barrel = false;
                        ((PistolAP)toSmith).magazine = false;
                        ((PistolAP)toSmith).light = true;
                        ((PistolAP)toSmith).heavy = false;
                        ((PistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((PistolAP)toSmith).silencer = false;
                        ((PistolAP)toSmith).short_barrel = false;
                        ((PistolAP)toSmith).long_barrel = false;
                        ((PistolAP)toSmith).magazine = false;
                        ((PistolAP)toSmith).light = false;
                        ((PistolAP)toSmith).heavy = true;
                        ((PistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((PistolAP)toSmith).silencer = false;
                        ((PistolAP)toSmith).short_barrel = false;
                        ((PistolAP)toSmith).long_barrel = false;
                        ((PistolAP)toSmith).magazine = false;
                        ((PistolAP)toSmith).light = false;
                        ((PistolAP)toSmith).heavy = false;
                        ((PistolAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof PistolHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((PistolHP)toSmith).silencer = true;
                        ((PistolHP)toSmith).short_barrel = false;
                        ((PistolHP)toSmith).long_barrel = false;
                        ((PistolHP)toSmith).magazine = false;
                        ((PistolHP)toSmith).light = false;
                        ((PistolHP)toSmith).heavy = false;
                        ((PistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((PistolHP)toSmith).silencer = false;
                        ((PistolHP)toSmith).short_barrel = true;
                        ((PistolHP)toSmith).long_barrel = false;
                        ((PistolHP)toSmith).magazine = false;
                        ((PistolHP)toSmith).light = false;
                        ((PistolHP)toSmith).heavy = false;
                        ((PistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((PistolHP)toSmith).silencer = false;
                        ((PistolHP)toSmith).short_barrel = false;
                        ((PistolHP)toSmith).long_barrel = true;
                        ((PistolHP)toSmith).magazine = false;
                        ((PistolHP)toSmith).light = false;
                        ((PistolHP)toSmith).heavy = false;
                        ((PistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((PistolHP)toSmith).silencer = false;
                        ((PistolHP)toSmith).short_barrel = false;
                        ((PistolHP)toSmith).long_barrel = false;
                        ((PistolHP)toSmith).magazine = true;
                        ((PistolHP)toSmith).light = false;
                        ((PistolHP)toSmith).heavy = false;
                        ((PistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((PistolHP)toSmith).silencer = false;
                        ((PistolHP)toSmith).short_barrel = false;
                        ((PistolHP)toSmith).long_barrel = false;
                        ((PistolHP)toSmith).magazine = false;
                        ((PistolHP)toSmith).light = true;
                        ((PistolHP)toSmith).heavy = false;
                        ((PistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((PistolHP)toSmith).silencer = false;
                        ((PistolHP)toSmith).short_barrel = false;
                        ((PistolHP)toSmith).long_barrel = false;
                        ((PistolHP)toSmith).magazine = false;
                        ((PistolHP)toSmith).light = false;
                        ((PistolHP)toSmith).heavy = true;
                        ((PistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((PistolHP)toSmith).silencer = false;
                        ((PistolHP)toSmith).short_barrel = false;
                        ((PistolHP)toSmith).long_barrel = false;
                        ((PistolHP)toSmith).magazine = false;
                        ((PistolHP)toSmith).light = false;
                        ((PistolHP)toSmith).heavy = false;
                        ((PistolHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof GoldenPistol){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistol)toSmith).silencer = true;
                        ((GoldenPistol)toSmith).short_barrel = false;
                        ((GoldenPistol)toSmith).long_barrel = false;
                        ((GoldenPistol)toSmith).magazine = false;
                        ((GoldenPistol)toSmith).light = false;
                        ((GoldenPistol)toSmith).heavy = false;
                        ((GoldenPistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistol)toSmith).silencer = false;
                        ((GoldenPistol)toSmith).short_barrel = true;
                        ((GoldenPistol)toSmith).long_barrel = false;
                        ((GoldenPistol)toSmith).magazine = false;
                        ((GoldenPistol)toSmith).light = false;
                        ((GoldenPistol)toSmith).heavy = false;
                        ((GoldenPistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistol)toSmith).silencer = false;
                        ((GoldenPistol)toSmith).short_barrel = false;
                        ((GoldenPistol)toSmith).long_barrel = true;
                        ((GoldenPistol)toSmith).magazine = false;
                        ((GoldenPistol)toSmith).light = false;
                        ((GoldenPistol)toSmith).heavy = false;
                        ((GoldenPistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistol)toSmith).silencer = false;
                        ((GoldenPistol)toSmith).short_barrel = false;
                        ((GoldenPistol)toSmith).long_barrel = false;
                        ((GoldenPistol)toSmith).magazine = true;
                        ((GoldenPistol)toSmith).light = false;
                        ((GoldenPistol)toSmith).heavy = false;
                        ((GoldenPistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistol)toSmith).silencer = false;
                        ((GoldenPistol)toSmith).short_barrel = false;
                        ((GoldenPistol)toSmith).long_barrel = false;
                        ((GoldenPistol)toSmith).magazine = false;
                        ((GoldenPistol)toSmith).light = true;
                        ((GoldenPistol)toSmith).heavy = false;
                        ((GoldenPistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistol)toSmith).silencer = false;
                        ((GoldenPistol)toSmith).short_barrel = false;
                        ((GoldenPistol)toSmith).long_barrel = false;
                        ((GoldenPistol)toSmith).magazine = false;
                        ((GoldenPistol)toSmith).light = false;
                        ((GoldenPistol)toSmith).heavy = true;
                        ((GoldenPistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistol)toSmith).silencer = false;
                        ((GoldenPistol)toSmith).short_barrel = false;
                        ((GoldenPistol)toSmith).long_barrel = false;
                        ((GoldenPistol)toSmith).magazine = false;
                        ((GoldenPistol)toSmith).light = false;
                        ((GoldenPistol)toSmith).heavy = false;
                        ((GoldenPistol)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof GoldenPistolAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistolAP)toSmith).silencer = true;
                        ((GoldenPistolAP)toSmith).short_barrel = false;
                        ((GoldenPistolAP)toSmith).long_barrel = false;
                        ((GoldenPistolAP)toSmith).magazine = false;
                        ((GoldenPistolAP)toSmith).light = false;
                        ((GoldenPistolAP)toSmith).heavy = false;
                        ((GoldenPistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistolAP)toSmith).silencer = false;
                        ((GoldenPistolAP)toSmith).short_barrel = true;
                        ((GoldenPistolAP)toSmith).long_barrel = false;
                        ((GoldenPistolAP)toSmith).magazine = false;
                        ((GoldenPistolAP)toSmith).light = false;
                        ((GoldenPistolAP)toSmith).heavy = false;
                        ((GoldenPistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistolAP)toSmith).silencer = false;
                        ((GoldenPistolAP)toSmith).short_barrel = false;
                        ((GoldenPistolAP)toSmith).long_barrel = true;
                        ((GoldenPistolAP)toSmith).magazine = false;
                        ((GoldenPistolAP)toSmith).light = false;
                        ((GoldenPistolAP)toSmith).heavy = false;
                        ((GoldenPistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistolAP)toSmith).silencer = false;
                        ((GoldenPistolAP)toSmith).short_barrel = false;
                        ((GoldenPistolAP)toSmith).long_barrel = false;
                        ((GoldenPistolAP)toSmith).magazine = true;
                        ((GoldenPistolAP)toSmith).light = false;
                        ((GoldenPistolAP)toSmith).heavy = false;
                        ((GoldenPistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistolAP)toSmith).silencer = false;
                        ((GoldenPistolAP)toSmith).short_barrel = false;
                        ((GoldenPistolAP)toSmith).long_barrel = false;
                        ((GoldenPistolAP)toSmith).magazine = false;
                        ((GoldenPistolAP)toSmith).light = true;
                        ((GoldenPistolAP)toSmith).heavy = false;
                        ((GoldenPistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistolAP)toSmith).silencer = false;
                        ((GoldenPistolAP)toSmith).short_barrel = false;
                        ((GoldenPistolAP)toSmith).long_barrel = false;
                        ((GoldenPistolAP)toSmith).magazine = false;
                        ((GoldenPistolAP)toSmith).light = false;
                        ((GoldenPistolAP)toSmith).heavy = true;
                        ((GoldenPistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistolAP)toSmith).silencer = false;
                        ((GoldenPistolAP)toSmith).short_barrel = false;
                        ((GoldenPistolAP)toSmith).long_barrel = false;
                        ((GoldenPistolAP)toSmith).magazine = false;
                        ((GoldenPistolAP)toSmith).light = false;
                        ((GoldenPistolAP)toSmith).heavy = false;
                        ((GoldenPistolAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof GoldenPistolHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistolHP)toSmith).silencer = true;
                        ((GoldenPistolHP)toSmith).short_barrel = false;
                        ((GoldenPistolHP)toSmith).long_barrel = false;
                        ((GoldenPistolHP)toSmith).magazine = false;
                        ((GoldenPistolHP)toSmith).light = false;
                        ((GoldenPistolHP)toSmith).heavy = false;
                        ((GoldenPistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistolHP)toSmith).silencer = false;
                        ((GoldenPistolHP)toSmith).short_barrel = true;
                        ((GoldenPistolHP)toSmith).long_barrel = false;
                        ((GoldenPistolHP)toSmith).magazine = false;
                        ((GoldenPistolHP)toSmith).light = false;
                        ((GoldenPistolHP)toSmith).heavy = false;
                        ((GoldenPistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistolHP)toSmith).silencer = false;
                        ((GoldenPistolHP)toSmith).short_barrel = false;
                        ((GoldenPistolHP)toSmith).long_barrel = true;
                        ((GoldenPistolHP)toSmith).magazine = false;
                        ((GoldenPistolHP)toSmith).light = false;
                        ((GoldenPistolHP)toSmith).heavy = false;
                        ((GoldenPistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistolHP)toSmith).silencer = false;
                        ((GoldenPistolHP)toSmith).short_barrel = false;
                        ((GoldenPistolHP)toSmith).long_barrel = false;
                        ((GoldenPistolHP)toSmith).magazine = true;
                        ((GoldenPistolHP)toSmith).light = false;
                        ((GoldenPistolHP)toSmith).heavy = false;
                        ((GoldenPistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistolHP)toSmith).silencer = false;
                        ((GoldenPistolHP)toSmith).short_barrel = false;
                        ((GoldenPistolHP)toSmith).long_barrel = false;
                        ((GoldenPistolHP)toSmith).magazine = false;
                        ((GoldenPistolHP)toSmith).light = true;
                        ((GoldenPistolHP)toSmith).heavy = false;
                        ((GoldenPistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistolHP)toSmith).silencer = false;
                        ((GoldenPistolHP)toSmith).short_barrel = false;
                        ((GoldenPistolHP)toSmith).long_barrel = false;
                        ((GoldenPistolHP)toSmith).magazine = false;
                        ((GoldenPistolHP)toSmith).light = false;
                        ((GoldenPistolHP)toSmith).heavy = true;
                        ((GoldenPistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((GoldenPistolHP)toSmith).silencer = false;
                        ((GoldenPistolHP)toSmith).short_barrel = false;
                        ((GoldenPistolHP)toSmith).long_barrel = false;
                        ((GoldenPistolHP)toSmith).magazine = false;
                        ((GoldenPistolHP)toSmith).light = false;
                        ((GoldenPistolHP)toSmith).heavy = false;
                        ((GoldenPistolHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof Handgun){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Handgun)toSmith).silencer = true;
                        ((Handgun)toSmith).short_barrel = false;
                        ((Handgun)toSmith).long_barrel = false;
                        ((Handgun)toSmith).magazine = false;
                        ((Handgun)toSmith).light = false;
                        ((Handgun)toSmith).heavy = false;
                        ((Handgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Handgun)toSmith).silencer = false;
                        ((Handgun)toSmith).short_barrel = true;
                        ((Handgun)toSmith).long_barrel = false;
                        ((Handgun)toSmith).magazine = false;
                        ((Handgun)toSmith).light = false;
                        ((Handgun)toSmith).heavy = false;
                        ((Handgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Handgun)toSmith).silencer = false;
                        ((Handgun)toSmith).short_barrel = false;
                        ((Handgun)toSmith).long_barrel = true;
                        ((Handgun)toSmith).magazine = false;
                        ((Handgun)toSmith).light = false;
                        ((Handgun)toSmith).heavy = false;
                        ((Handgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Handgun)toSmith).silencer = false;
                        ((Handgun)toSmith).short_barrel = false;
                        ((Handgun)toSmith).long_barrel = false;
                        ((Handgun)toSmith).magazine = true;
                        ((Handgun)toSmith).light = false;
                        ((Handgun)toSmith).heavy = false;
                        ((Handgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Handgun)toSmith).silencer = false;
                        ((Handgun)toSmith).short_barrel = false;
                        ((Handgun)toSmith).long_barrel = false;
                        ((Handgun)toSmith).magazine = false;
                        ((Handgun)toSmith).light = true;
                        ((Handgun)toSmith).heavy = false;
                        ((Handgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Handgun)toSmith).silencer = false;
                        ((Handgun)toSmith).short_barrel = false;
                        ((Handgun)toSmith).long_barrel = false;
                        ((Handgun)toSmith).magazine = false;
                        ((Handgun)toSmith).light = false;
                        ((Handgun)toSmith).heavy = true;
                        ((Handgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Handgun)toSmith).silencer = false;
                        ((Handgun)toSmith).short_barrel = false;
                        ((Handgun)toSmith).long_barrel = false;
                        ((Handgun)toSmith).magazine = false;
                        ((Handgun)toSmith).light = false;
                        ((Handgun)toSmith).heavy = false;
                        ((Handgun)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof HandgunAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HandgunAP)toSmith).silencer = true;
                        ((HandgunAP)toSmith).short_barrel = false;
                        ((HandgunAP)toSmith).long_barrel = false;
                        ((HandgunAP)toSmith).magazine = false;
                        ((HandgunAP)toSmith).light = false;
                        ((HandgunAP)toSmith).heavy = false;
                        ((HandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HandgunAP)toSmith).silencer = false;
                        ((HandgunAP)toSmith).short_barrel = true;
                        ((HandgunAP)toSmith).long_barrel = false;
                        ((HandgunAP)toSmith).magazine = false;
                        ((HandgunAP)toSmith).light = false;
                        ((HandgunAP)toSmith).heavy = false;
                        ((HandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HandgunAP)toSmith).silencer = false;
                        ((HandgunAP)toSmith).short_barrel = false;
                        ((HandgunAP)toSmith).long_barrel = true;
                        ((HandgunAP)toSmith).magazine = false;
                        ((HandgunAP)toSmith).light = false;
                        ((HandgunAP)toSmith).heavy = false;
                        ((HandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HandgunAP)toSmith).silencer = false;
                        ((HandgunAP)toSmith).short_barrel = false;
                        ((HandgunAP)toSmith).long_barrel = false;
                        ((HandgunAP)toSmith).magazine = true;
                        ((HandgunAP)toSmith).light = false;
                        ((HandgunAP)toSmith).heavy = false;
                        ((HandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HandgunAP)toSmith).silencer = false;
                        ((HandgunAP)toSmith).short_barrel = false;
                        ((HandgunAP)toSmith).long_barrel = false;
                        ((HandgunAP)toSmith).magazine = false;
                        ((HandgunAP)toSmith).light = true;
                        ((HandgunAP)toSmith).heavy = false;
                        ((HandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HandgunAP)toSmith).silencer = false;
                        ((HandgunAP)toSmith).short_barrel = false;
                        ((HandgunAP)toSmith).long_barrel = false;
                        ((HandgunAP)toSmith).magazine = false;
                        ((HandgunAP)toSmith).light = false;
                        ((HandgunAP)toSmith).heavy = true;
                        ((HandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HandgunAP)toSmith).silencer = false;
                        ((HandgunAP)toSmith).short_barrel = false;
                        ((HandgunAP)toSmith).long_barrel = false;
                        ((HandgunAP)toSmith).magazine = false;
                        ((HandgunAP)toSmith).light = false;
                        ((HandgunAP)toSmith).heavy = false;
                        ((HandgunAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof HandgunHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HandgunHP)toSmith).silencer = true;
                        ((HandgunHP)toSmith).short_barrel = false;
                        ((HandgunHP)toSmith).long_barrel = false;
                        ((HandgunHP)toSmith).magazine = false;
                        ((HandgunHP)toSmith).light = false;
                        ((HandgunHP)toSmith).heavy = false;
                        ((HandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HandgunHP)toSmith).silencer = false;
                        ((HandgunHP)toSmith).short_barrel = true;
                        ((HandgunHP)toSmith).long_barrel = false;
                        ((HandgunHP)toSmith).magazine = false;
                        ((HandgunHP)toSmith).light = false;
                        ((HandgunHP)toSmith).heavy = false;
                        ((HandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HandgunHP)toSmith).silencer = false;
                        ((HandgunHP)toSmith).short_barrel = false;
                        ((HandgunHP)toSmith).long_barrel = true;
                        ((HandgunHP)toSmith).magazine = false;
                        ((HandgunHP)toSmith).light = false;
                        ((HandgunHP)toSmith).heavy = false;
                        ((HandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HandgunHP)toSmith).silencer = false;
                        ((HandgunHP)toSmith).short_barrel = false;
                        ((HandgunHP)toSmith).long_barrel = false;
                        ((HandgunHP)toSmith).magazine = true;
                        ((HandgunHP)toSmith).light = false;
                        ((HandgunHP)toSmith).heavy = false;
                        ((HandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HandgunHP)toSmith).silencer = false;
                        ((HandgunHP)toSmith).short_barrel = false;
                        ((HandgunHP)toSmith).long_barrel = false;
                        ((HandgunHP)toSmith).magazine = false;
                        ((HandgunHP)toSmith).light = true;
                        ((HandgunHP)toSmith).heavy = false;
                        ((HandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HandgunHP)toSmith).silencer = false;
                        ((HandgunHP)toSmith).short_barrel = false;
                        ((HandgunHP)toSmith).long_barrel = false;
                        ((HandgunHP)toSmith).magazine = false;
                        ((HandgunHP)toSmith).light = false;
                        ((HandgunHP)toSmith).heavy = true;
                        ((HandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HandgunHP)toSmith).silencer = false;
                        ((HandgunHP)toSmith).short_barrel = false;
                        ((HandgunHP)toSmith).long_barrel = false;
                        ((HandgunHP)toSmith).magazine = false;
                        ((HandgunHP)toSmith).light = false;
                        ((HandgunHP)toSmith).heavy = false;
                        ((HandgunHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof Magnum){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Magnum)toSmith).silencer = true;
                        ((Magnum)toSmith).short_barrel = false;
                        ((Magnum)toSmith).long_barrel = false;
                        ((Magnum)toSmith).magazine = false;
                        ((Magnum)toSmith).light = false;
                        ((Magnum)toSmith).heavy = false;
                        ((Magnum)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Magnum)toSmith).silencer = false;
                        ((Magnum)toSmith).short_barrel = true;
                        ((Magnum)toSmith).long_barrel = false;
                        ((Magnum)toSmith).magazine = false;
                        ((Magnum)toSmith).light = false;
                        ((Magnum)toSmith).heavy = false;
                        ((Magnum)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Magnum)toSmith).silencer = false;
                        ((Magnum)toSmith).short_barrel = false;
                        ((Magnum)toSmith).long_barrel = true;
                        ((Magnum)toSmith).magazine = false;
                        ((Magnum)toSmith).light = false;
                        ((Magnum)toSmith).heavy = false;
                        ((Magnum)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Magnum)toSmith).silencer = false;
                        ((Magnum)toSmith).short_barrel = false;
                        ((Magnum)toSmith).long_barrel = false;
                        ((Magnum)toSmith).magazine = true;
                        ((Magnum)toSmith).light = false;
                        ((Magnum)toSmith).heavy = false;
                        ((Magnum)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Magnum)toSmith).silencer = false;
                        ((Magnum)toSmith).short_barrel = false;
                        ((Magnum)toSmith).long_barrel = false;
                        ((Magnum)toSmith).magazine = false;
                        ((Magnum)toSmith).light = true;
                        ((Magnum)toSmith).heavy = false;
                        ((Magnum)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Magnum)toSmith).silencer = false;
                        ((Magnum)toSmith).short_barrel = false;
                        ((Magnum)toSmith).long_barrel = false;
                        ((Magnum)toSmith).magazine = false;
                        ((Magnum)toSmith).light = false;
                        ((Magnum)toSmith).heavy = true;
                        ((Magnum)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Magnum)toSmith).silencer = false;
                        ((Magnum)toSmith).short_barrel = false;
                        ((Magnum)toSmith).long_barrel = false;
                        ((Magnum)toSmith).magazine = false;
                        ((Magnum)toSmith).light = false;
                        ((Magnum)toSmith).heavy = false;
                        ((Magnum)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof MagnumAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MagnumAP)toSmith).silencer = true;
                        ((MagnumAP)toSmith).short_barrel = false;
                        ((MagnumAP)toSmith).long_barrel = false;
                        ((MagnumAP)toSmith).magazine = false;
                        ((MagnumAP)toSmith).light = false;
                        ((MagnumAP)toSmith).heavy = false;
                        ((MagnumAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MagnumAP)toSmith).silencer = false;
                        ((MagnumAP)toSmith).short_barrel = true;
                        ((MagnumAP)toSmith).long_barrel = false;
                        ((MagnumAP)toSmith).magazine = false;
                        ((MagnumAP)toSmith).light = false;
                        ((MagnumAP)toSmith).heavy = false;
                        ((MagnumAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MagnumAP)toSmith).silencer = false;
                        ((MagnumAP)toSmith).short_barrel = false;
                        ((MagnumAP)toSmith).long_barrel = true;
                        ((MagnumAP)toSmith).magazine = false;
                        ((MagnumAP)toSmith).light = false;
                        ((MagnumAP)toSmith).heavy = false;
                        ((MagnumAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MagnumAP)toSmith).silencer = false;
                        ((MagnumAP)toSmith).short_barrel = false;
                        ((MagnumAP)toSmith).long_barrel = false;
                        ((MagnumAP)toSmith).magazine = true;
                        ((MagnumAP)toSmith).light = false;
                        ((MagnumAP)toSmith).heavy = false;
                        ((MagnumAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MagnumAP)toSmith).silencer = false;
                        ((MagnumAP)toSmith).short_barrel = false;
                        ((MagnumAP)toSmith).long_barrel = false;
                        ((MagnumAP)toSmith).magazine = false;
                        ((MagnumAP)toSmith).light = true;
                        ((MagnumAP)toSmith).heavy = false;
                        ((MagnumAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MagnumAP)toSmith).silencer = false;
                        ((MagnumAP)toSmith).short_barrel = false;
                        ((MagnumAP)toSmith).long_barrel = false;
                        ((MagnumAP)toSmith).magazine = false;
                        ((MagnumAP)toSmith).light = false;
                        ((MagnumAP)toSmith).heavy = true;
                        ((MagnumAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MagnumAP)toSmith).silencer = false;
                        ((MagnumAP)toSmith).short_barrel = false;
                        ((MagnumAP)toSmith).long_barrel = false;
                        ((MagnumAP)toSmith).magazine = false;
                        ((MagnumAP)toSmith).light = false;
                        ((MagnumAP)toSmith).heavy = false;
                        ((MagnumAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof MagnumHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MagnumHP)toSmith).silencer = true;
                        ((MagnumHP)toSmith).short_barrel = false;
                        ((MagnumHP)toSmith).long_barrel = false;
                        ((MagnumHP)toSmith).magazine = false;
                        ((MagnumHP)toSmith).light = false;
                        ((MagnumHP)toSmith).heavy = false;
                        ((MagnumHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MagnumHP)toSmith).silencer = false;
                        ((MagnumHP)toSmith).short_barrel = true;
                        ((MagnumHP)toSmith).long_barrel = false;
                        ((MagnumHP)toSmith).magazine = false;
                        ((MagnumHP)toSmith).light = false;
                        ((MagnumHP)toSmith).heavy = false;
                        ((MagnumHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MagnumHP)toSmith).silencer = false;
                        ((MagnumHP)toSmith).short_barrel = false;
                        ((MagnumHP)toSmith).long_barrel = true;
                        ((MagnumHP)toSmith).magazine = false;
                        ((MagnumHP)toSmith).light = false;
                        ((MagnumHP)toSmith).heavy = false;
                        ((MagnumHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MagnumHP)toSmith).silencer = false;
                        ((MagnumHP)toSmith).short_barrel = false;
                        ((MagnumHP)toSmith).long_barrel = false;
                        ((MagnumHP)toSmith).magazine = true;
                        ((MagnumHP)toSmith).light = false;
                        ((MagnumHP)toSmith).heavy = false;
                        ((MagnumHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MagnumHP)toSmith).silencer = false;
                        ((MagnumHP)toSmith).short_barrel = false;
                        ((MagnumHP)toSmith).long_barrel = false;
                        ((MagnumHP)toSmith).magazine = false;
                        ((MagnumHP)toSmith).light = true;
                        ((MagnumHP)toSmith).heavy = false;
                        ((MagnumHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MagnumHP)toSmith).silencer = false;
                        ((MagnumHP)toSmith).short_barrel = false;
                        ((MagnumHP)toSmith).long_barrel = false;
                        ((MagnumHP)toSmith).magazine = false;
                        ((MagnumHP)toSmith).light = false;
                        ((MagnumHP)toSmith).heavy = true;
                        ((MagnumHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MagnumHP)toSmith).silencer = false;
                        ((MagnumHP)toSmith).short_barrel = false;
                        ((MagnumHP)toSmith).long_barrel = false;
                        ((MagnumHP)toSmith).magazine = false;
                        ((MagnumHP)toSmith).light = false;
                        ((MagnumHP)toSmith).heavy = false;
                        ((MagnumHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof TacticalHandgun){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgun)toSmith).silencer = true;
                        ((TacticalHandgun)toSmith).short_barrel = false;
                        ((TacticalHandgun)toSmith).long_barrel = false;
                        ((TacticalHandgun)toSmith).magazine = false;
                        ((TacticalHandgun)toSmith).light = false;
                        ((TacticalHandgun)toSmith).heavy = false;
                        ((TacticalHandgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgun)toSmith).silencer = false;
                        ((TacticalHandgun)toSmith).short_barrel = true;
                        ((TacticalHandgun)toSmith).long_barrel = false;
                        ((TacticalHandgun)toSmith).magazine = false;
                        ((TacticalHandgun)toSmith).light = false;
                        ((TacticalHandgun)toSmith).heavy = false;
                        ((TacticalHandgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgun)toSmith).silencer = false;
                        ((TacticalHandgun)toSmith).short_barrel = false;
                        ((TacticalHandgun)toSmith).long_barrel = true;
                        ((TacticalHandgun)toSmith).magazine = false;
                        ((TacticalHandgun)toSmith).light = false;
                        ((TacticalHandgun)toSmith).heavy = false;
                        ((TacticalHandgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgun)toSmith).silencer = false;
                        ((TacticalHandgun)toSmith).short_barrel = false;
                        ((TacticalHandgun)toSmith).long_barrel = false;
                        ((TacticalHandgun)toSmith).magazine = true;
                        ((TacticalHandgun)toSmith).light = false;
                        ((TacticalHandgun)toSmith).heavy = false;
                        ((TacticalHandgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgun)toSmith).silencer = false;
                        ((TacticalHandgun)toSmith).short_barrel = false;
                        ((TacticalHandgun)toSmith).long_barrel = false;
                        ((TacticalHandgun)toSmith).magazine = false;
                        ((TacticalHandgun)toSmith).light = true;
                        ((TacticalHandgun)toSmith).heavy = false;
                        ((TacticalHandgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgun)toSmith).silencer = false;
                        ((TacticalHandgun)toSmith).short_barrel = false;
                        ((TacticalHandgun)toSmith).long_barrel = false;
                        ((TacticalHandgun)toSmith).magazine = false;
                        ((TacticalHandgun)toSmith).light = false;
                        ((TacticalHandgun)toSmith).heavy = true;
                        ((TacticalHandgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgun)toSmith).silencer = false;
                        ((TacticalHandgun)toSmith).short_barrel = false;
                        ((TacticalHandgun)toSmith).long_barrel = false;
                        ((TacticalHandgun)toSmith).magazine = false;
                        ((TacticalHandgun)toSmith).light = false;
                        ((TacticalHandgun)toSmith).heavy = false;
                        ((TacticalHandgun)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof TacticalHandgunAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgunAP)toSmith).silencer = true;
                        ((TacticalHandgunAP)toSmith).short_barrel = false;
                        ((TacticalHandgunAP)toSmith).long_barrel = false;
                        ((TacticalHandgunAP)toSmith).magazine = false;
                        ((TacticalHandgunAP)toSmith).light = false;
                        ((TacticalHandgunAP)toSmith).heavy = false;
                        ((TacticalHandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgunAP)toSmith).silencer = false;
                        ((TacticalHandgunAP)toSmith).short_barrel = true;
                        ((TacticalHandgunAP)toSmith).long_barrel = false;
                        ((TacticalHandgunAP)toSmith).magazine = false;
                        ((TacticalHandgunAP)toSmith).light = false;
                        ((TacticalHandgunAP)toSmith).heavy = false;
                        ((TacticalHandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgunAP)toSmith).silencer = false;
                        ((TacticalHandgunAP)toSmith).short_barrel = false;
                        ((TacticalHandgunAP)toSmith).long_barrel = true;
                        ((TacticalHandgunAP)toSmith).magazine = false;
                        ((TacticalHandgunAP)toSmith).light = false;
                        ((TacticalHandgunAP)toSmith).heavy = false;
                        ((TacticalHandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgunAP)toSmith).silencer = false;
                        ((TacticalHandgunAP)toSmith).short_barrel = false;
                        ((TacticalHandgunAP)toSmith).long_barrel = false;
                        ((TacticalHandgunAP)toSmith).magazine = true;
                        ((TacticalHandgunAP)toSmith).light = false;
                        ((TacticalHandgunAP)toSmith).heavy = false;
                        ((TacticalHandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgunAP)toSmith).silencer = false;
                        ((TacticalHandgunAP)toSmith).short_barrel = false;
                        ((TacticalHandgunAP)toSmith).long_barrel = false;
                        ((TacticalHandgunAP)toSmith).magazine = false;
                        ((TacticalHandgunAP)toSmith).light = true;
                        ((TacticalHandgunAP)toSmith).heavy = false;
                        ((TacticalHandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgunAP)toSmith).silencer = false;
                        ((TacticalHandgunAP)toSmith).short_barrel = false;
                        ((TacticalHandgunAP)toSmith).long_barrel = false;
                        ((TacticalHandgunAP)toSmith).magazine = false;
                        ((TacticalHandgunAP)toSmith).light = false;
                        ((TacticalHandgunAP)toSmith).heavy = true;
                        ((TacticalHandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgunAP)toSmith).silencer = false;
                        ((TacticalHandgunAP)toSmith).short_barrel = false;
                        ((TacticalHandgunAP)toSmith).long_barrel = false;
                        ((TacticalHandgunAP)toSmith).magazine = false;
                        ((TacticalHandgunAP)toSmith).light = false;
                        ((TacticalHandgunAP)toSmith).heavy = false;
                        ((TacticalHandgunAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof TacticalHandgunHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgunHP)toSmith).silencer = true;
                        ((TacticalHandgunHP)toSmith).short_barrel = false;
                        ((TacticalHandgunHP)toSmith).long_barrel = false;
                        ((TacticalHandgunHP)toSmith).magazine = false;
                        ((TacticalHandgunHP)toSmith).light = false;
                        ((TacticalHandgunHP)toSmith).heavy = false;
                        ((TacticalHandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgunHP)toSmith).silencer = false;
                        ((TacticalHandgunHP)toSmith).short_barrel = true;
                        ((TacticalHandgunHP)toSmith).long_barrel = false;
                        ((TacticalHandgunHP)toSmith).magazine = false;
                        ((TacticalHandgunHP)toSmith).light = false;
                        ((TacticalHandgunHP)toSmith).heavy = false;
                        ((TacticalHandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgunHP)toSmith).silencer = false;
                        ((TacticalHandgunHP)toSmith).short_barrel = false;
                        ((TacticalHandgunHP)toSmith).long_barrel = true;
                        ((TacticalHandgunHP)toSmith).magazine = false;
                        ((TacticalHandgunHP)toSmith).light = false;
                        ((TacticalHandgunHP)toSmith).heavy = false;
                        ((TacticalHandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgunHP)toSmith).silencer = false;
                        ((TacticalHandgunHP)toSmith).short_barrel = false;
                        ((TacticalHandgunHP)toSmith).long_barrel = false;
                        ((TacticalHandgunHP)toSmith).magazine = true;
                        ((TacticalHandgunHP)toSmith).light = false;
                        ((TacticalHandgunHP)toSmith).heavy = false;
                        ((TacticalHandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgunHP)toSmith).silencer = false;
                        ((TacticalHandgunHP)toSmith).short_barrel = false;
                        ((TacticalHandgunHP)toSmith).long_barrel = false;
                        ((TacticalHandgunHP)toSmith).magazine = false;
                        ((TacticalHandgunHP)toSmith).light = true;
                        ((TacticalHandgunHP)toSmith).heavy = false;
                        ((TacticalHandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgunHP)toSmith).silencer = false;
                        ((TacticalHandgunHP)toSmith).short_barrel = false;
                        ((TacticalHandgunHP)toSmith).long_barrel = false;
                        ((TacticalHandgunHP)toSmith).magazine = false;
                        ((TacticalHandgunHP)toSmith).light = false;
                        ((TacticalHandgunHP)toSmith).heavy = true;
                        ((TacticalHandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((TacticalHandgunHP)toSmith).silencer = false;
                        ((TacticalHandgunHP)toSmith).short_barrel = false;
                        ((TacticalHandgunHP)toSmith).long_barrel = false;
                        ((TacticalHandgunHP)toSmith).magazine = false;
                        ((TacticalHandgunHP)toSmith).light = false;
                        ((TacticalHandgunHP)toSmith).heavy = false;
                        ((TacticalHandgunHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof AutoHandgun){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgun)toSmith).silencer = true;
                        ((AutoHandgun)toSmith).short_barrel = false;
                        ((AutoHandgun)toSmith).long_barrel = false;
                        ((AutoHandgun)toSmith).magazine = false;
                        ((AutoHandgun)toSmith).light = false;
                        ((AutoHandgun)toSmith).heavy = false;
                        ((AutoHandgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgun)toSmith).silencer = false;
                        ((AutoHandgun)toSmith).short_barrel = true;
                        ((AutoHandgun)toSmith).long_barrel = false;
                        ((AutoHandgun)toSmith).magazine = false;
                        ((AutoHandgun)toSmith).light = false;
                        ((AutoHandgun)toSmith).heavy = false;
                        ((AutoHandgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgun)toSmith).silencer = false;
                        ((AutoHandgun)toSmith).short_barrel = false;
                        ((AutoHandgun)toSmith).long_barrel = true;
                        ((AutoHandgun)toSmith).magazine = false;
                        ((AutoHandgun)toSmith).light = false;
                        ((AutoHandgun)toSmith).heavy = false;
                        ((AutoHandgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgun)toSmith).silencer = false;
                        ((AutoHandgun)toSmith).short_barrel = false;
                        ((AutoHandgun)toSmith).long_barrel = false;
                        ((AutoHandgun)toSmith).magazine = true;
                        ((AutoHandgun)toSmith).light = false;
                        ((AutoHandgun)toSmith).heavy = false;
                        ((AutoHandgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgun)toSmith).silencer = false;
                        ((AutoHandgun)toSmith).short_barrel = false;
                        ((AutoHandgun)toSmith).long_barrel = false;
                        ((AutoHandgun)toSmith).magazine = false;
                        ((AutoHandgun)toSmith).light = true;
                        ((AutoHandgun)toSmith).heavy = false;
                        ((AutoHandgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgun)toSmith).silencer = false;
                        ((AutoHandgun)toSmith).short_barrel = false;
                        ((AutoHandgun)toSmith).long_barrel = false;
                        ((AutoHandgun)toSmith).magazine = false;
                        ((AutoHandgun)toSmith).light = false;
                        ((AutoHandgun)toSmith).heavy = true;
                        ((AutoHandgun)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgun)toSmith).silencer = false;
                        ((AutoHandgun)toSmith).short_barrel = false;
                        ((AutoHandgun)toSmith).long_barrel = false;
                        ((AutoHandgun)toSmith).magazine = false;
                        ((AutoHandgun)toSmith).light = false;
                        ((AutoHandgun)toSmith).heavy = false;
                        ((AutoHandgun)toSmith).flash = true;
                        smith();
                        updateQuickslot();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof AutoHandgunAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgunAP)toSmith).silencer = true;
                        ((AutoHandgunAP)toSmith).short_barrel = false;
                        ((AutoHandgunAP)toSmith).long_barrel = false;
                        ((AutoHandgunAP)toSmith).magazine = false;
                        ((AutoHandgunAP)toSmith).light = false;
                        ((AutoHandgunAP)toSmith).heavy = false;
                        ((AutoHandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgunAP)toSmith).silencer = false;
                        ((AutoHandgunAP)toSmith).short_barrel = true;
                        ((AutoHandgunAP)toSmith).long_barrel = false;
                        ((AutoHandgunAP)toSmith).magazine = false;
                        ((AutoHandgunAP)toSmith).light = false;
                        ((AutoHandgunAP)toSmith).heavy = false;
                        ((AutoHandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgunAP)toSmith).silencer = false;
                        ((AutoHandgunAP)toSmith).short_barrel = false;
                        ((AutoHandgunAP)toSmith).long_barrel = true;
                        ((AutoHandgunAP)toSmith).magazine = false;
                        ((AutoHandgunAP)toSmith).light = false;
                        ((AutoHandgunAP)toSmith).heavy = false;
                        ((AutoHandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgunAP)toSmith).silencer = false;
                        ((AutoHandgunAP)toSmith).short_barrel = false;
                        ((AutoHandgunAP)toSmith).long_barrel = false;
                        ((AutoHandgunAP)toSmith).magazine = true;
                        ((AutoHandgunAP)toSmith).light = false;
                        ((AutoHandgunAP)toSmith).heavy = false;
                        ((AutoHandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgunAP)toSmith).silencer = false;
                        ((AutoHandgunAP)toSmith).short_barrel = false;
                        ((AutoHandgunAP)toSmith).long_barrel = false;
                        ((AutoHandgunAP)toSmith).magazine = false;
                        ((AutoHandgunAP)toSmith).light = true;
                        ((AutoHandgunAP)toSmith).heavy = false;
                        ((AutoHandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgunAP)toSmith).silencer = false;
                        ((AutoHandgunAP)toSmith).short_barrel = false;
                        ((AutoHandgunAP)toSmith).long_barrel = false;
                        ((AutoHandgunAP)toSmith).magazine = false;
                        ((AutoHandgunAP)toSmith).light = false;
                        ((AutoHandgunAP)toSmith).heavy = true;
                        ((AutoHandgunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgunAP)toSmith).silencer = false;
                        ((AutoHandgunAP)toSmith).short_barrel = false;
                        ((AutoHandgunAP)toSmith).long_barrel = false;
                        ((AutoHandgunAP)toSmith).magazine = false;
                        ((AutoHandgunAP)toSmith).light = false;
                        ((AutoHandgunAP)toSmith).heavy = false;
                        ((AutoHandgunAP)toSmith).flash = true;
                        smith();
                        updateQuickslot();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof AutoHandgunHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgunHP)toSmith).silencer = true;
                        ((AutoHandgunHP)toSmith).short_barrel = false;
                        ((AutoHandgunHP)toSmith).long_barrel = false;
                        ((AutoHandgunHP)toSmith).magazine = false;
                        ((AutoHandgunHP)toSmith).light = false;
                        ((AutoHandgunHP)toSmith).heavy = false;
                        ((AutoHandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgunHP)toSmith).silencer = false;
                        ((AutoHandgunHP)toSmith).short_barrel = true;
                        ((AutoHandgunHP)toSmith).long_barrel = false;
                        ((AutoHandgunHP)toSmith).magazine = false;
                        ((AutoHandgunHP)toSmith).light = false;
                        ((AutoHandgunHP)toSmith).heavy = false;
                        ((AutoHandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgunHP)toSmith).silencer = false;
                        ((AutoHandgunHP)toSmith).short_barrel = false;
                        ((AutoHandgunHP)toSmith).long_barrel = true;
                        ((AutoHandgunHP)toSmith).magazine = false;
                        ((AutoHandgunHP)toSmith).light = false;
                        ((AutoHandgunHP)toSmith).heavy = false;
                        ((AutoHandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgunHP)toSmith).silencer = false;
                        ((AutoHandgunHP)toSmith).short_barrel = false;
                        ((AutoHandgunHP)toSmith).long_barrel = false;
                        ((AutoHandgunHP)toSmith).magazine = true;
                        ((AutoHandgunHP)toSmith).light = false;
                        ((AutoHandgunHP)toSmith).heavy = false;
                        ((AutoHandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgunHP)toSmith).silencer = false;
                        ((AutoHandgunHP)toSmith).short_barrel = false;
                        ((AutoHandgunHP)toSmith).long_barrel = false;
                        ((AutoHandgunHP)toSmith).magazine = false;
                        ((AutoHandgunHP)toSmith).light = true;
                        ((AutoHandgunHP)toSmith).heavy = false;
                        ((AutoHandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgunHP)toSmith).silencer = false;
                        ((AutoHandgunHP)toSmith).short_barrel = false;
                        ((AutoHandgunHP)toSmith).long_barrel = false;
                        ((AutoHandgunHP)toSmith).magazine = false;
                        ((AutoHandgunHP)toSmith).light = false;
                        ((AutoHandgunHP)toSmith).heavy = true;
                        ((AutoHandgunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoHandgunHP)toSmith).silencer = false;
                        ((AutoHandgunHP)toSmith).short_barrel = false;
                        ((AutoHandgunHP)toSmith).long_barrel = false;
                        ((AutoHandgunHP)toSmith).magazine = false;
                        ((AutoHandgunHP)toSmith).light = false;
                        ((AutoHandgunHP)toSmith).heavy = false;
                        ((AutoHandgunHP)toSmith).flash = true;
                        smith();
                        updateQuickslot();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof DualPistol){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistol)toSmith).silencer = true;
                        ((DualPistol)toSmith).short_barrel = false;
                        ((DualPistol)toSmith).long_barrel = false;
                        ((DualPistol)toSmith).magazine = false;
                        ((DualPistol)toSmith).light = false;
                        ((DualPistol)toSmith).heavy = false;
                        ((DualPistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistol)toSmith).silencer = false;
                        ((DualPistol)toSmith).short_barrel = true;
                        ((DualPistol)toSmith).long_barrel = false;
                        ((DualPistol)toSmith).magazine = false;
                        ((DualPistol)toSmith).light = false;
                        ((DualPistol)toSmith).heavy = false;
                        ((DualPistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistol)toSmith).silencer = false;
                        ((DualPistol)toSmith).short_barrel = false;
                        ((DualPistol)toSmith).long_barrel = true;
                        ((DualPistol)toSmith).magazine = false;
                        ((DualPistol)toSmith).light = false;
                        ((DualPistol)toSmith).heavy = false;
                        ((DualPistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistol)toSmith).silencer = false;
                        ((DualPistol)toSmith).short_barrel = false;
                        ((DualPistol)toSmith).long_barrel = false;
                        ((DualPistol)toSmith).magazine = true;
                        ((DualPistol)toSmith).light = false;
                        ((DualPistol)toSmith).heavy = false;
                        ((DualPistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistol)toSmith).silencer = false;
                        ((DualPistol)toSmith).short_barrel = false;
                        ((DualPistol)toSmith).long_barrel = false;
                        ((DualPistol)toSmith).magazine = false;
                        ((DualPistol)toSmith).light = true;
                        ((DualPistol)toSmith).heavy = false;
                        ((DualPistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistol)toSmith).silencer = false;
                        ((DualPistol)toSmith).short_barrel = false;
                        ((DualPistol)toSmith).long_barrel = false;
                        ((DualPistol)toSmith).magazine = false;
                        ((DualPistol)toSmith).light = false;
                        ((DualPistol)toSmith).heavy = true;
                        ((DualPistol)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistol)toSmith).silencer = false;
                        ((DualPistol)toSmith).short_barrel = false;
                        ((DualPistol)toSmith).long_barrel = false;
                        ((DualPistol)toSmith).magazine = false;
                        ((DualPistol)toSmith).light = false;
                        ((DualPistol)toSmith).heavy = false;
                        ((DualPistol)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof DualPistolAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistolAP)toSmith).silencer = true;
                        ((DualPistolAP)toSmith).short_barrel = false;
                        ((DualPistolAP)toSmith).long_barrel = false;
                        ((DualPistolAP)toSmith).magazine = false;
                        ((DualPistolAP)toSmith).light = false;
                        ((DualPistolAP)toSmith).heavy = false;
                        ((DualPistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistolAP)toSmith).silencer = false;
                        ((DualPistolAP)toSmith).short_barrel = true;
                        ((DualPistolAP)toSmith).long_barrel = false;
                        ((DualPistolAP)toSmith).magazine = false;
                        ((DualPistolAP)toSmith).light = false;
                        ((DualPistolAP)toSmith).heavy = false;
                        ((DualPistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistolAP)toSmith).silencer = false;
                        ((DualPistolAP)toSmith).short_barrel = false;
                        ((DualPistolAP)toSmith).long_barrel = true;
                        ((DualPistolAP)toSmith).magazine = false;
                        ((DualPistolAP)toSmith).light = false;
                        ((DualPistolAP)toSmith).heavy = false;
                        ((DualPistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistolAP)toSmith).silencer = false;
                        ((DualPistolAP)toSmith).short_barrel = false;
                        ((DualPistolAP)toSmith).long_barrel = false;
                        ((DualPistolAP)toSmith).magazine = true;
                        ((DualPistolAP)toSmith).light = false;
                        ((DualPistolAP)toSmith).heavy = false;
                        ((DualPistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistolAP)toSmith).silencer = false;
                        ((DualPistolAP)toSmith).short_barrel = false;
                        ((DualPistolAP)toSmith).long_barrel = false;
                        ((DualPistolAP)toSmith).magazine = false;
                        ((DualPistolAP)toSmith).light = true;
                        ((DualPistolAP)toSmith).heavy = false;
                        ((DualPistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistolAP)toSmith).silencer = false;
                        ((DualPistolAP)toSmith).short_barrel = false;
                        ((DualPistolAP)toSmith).long_barrel = false;
                        ((DualPistolAP)toSmith).magazine = false;
                        ((DualPistolAP)toSmith).light = false;
                        ((DualPistolAP)toSmith).heavy = true;
                        ((DualPistolAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistolAP)toSmith).silencer = false;
                        ((DualPistolAP)toSmith).short_barrel = false;
                        ((DualPistolAP)toSmith).long_barrel = false;
                        ((DualPistolAP)toSmith).magazine = false;
                        ((DualPistolAP)toSmith).light = false;
                        ((DualPistolAP)toSmith).heavy = false;
                        ((DualPistolAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof DualPistolHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistolHP)toSmith).silencer = true;
                        ((DualPistolHP)toSmith).short_barrel = false;
                        ((DualPistolHP)toSmith).long_barrel = false;
                        ((DualPistolHP)toSmith).magazine = false;
                        ((DualPistolHP)toSmith).light = false;
                        ((DualPistolHP)toSmith).heavy = false;
                        ((DualPistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistolHP)toSmith).silencer = false;
                        ((DualPistolHP)toSmith).short_barrel = true;
                        ((DualPistolHP)toSmith).long_barrel = false;
                        ((DualPistolHP)toSmith).magazine = false;
                        ((DualPistolHP)toSmith).light = false;
                        ((DualPistolHP)toSmith).heavy = false;
                        ((DualPistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistolHP)toSmith).silencer = false;
                        ((DualPistolHP)toSmith).short_barrel = false;
                        ((DualPistolHP)toSmith).long_barrel = true;
                        ((DualPistolHP)toSmith).magazine = false;
                        ((DualPistolHP)toSmith).light = false;
                        ((DualPistolHP)toSmith).heavy = false;
                        ((DualPistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistolHP)toSmith).silencer = false;
                        ((DualPistolHP)toSmith).short_barrel = false;
                        ((DualPistolHP)toSmith).long_barrel = false;
                        ((DualPistolHP)toSmith).magazine = true;
                        ((DualPistolHP)toSmith).light = false;
                        ((DualPistolHP)toSmith).heavy = false;
                        ((DualPistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistolHP)toSmith).silencer = false;
                        ((DualPistolHP)toSmith).short_barrel = false;
                        ((DualPistolHP)toSmith).long_barrel = false;
                        ((DualPistolHP)toSmith).magazine = false;
                        ((DualPistolHP)toSmith).light = true;
                        ((DualPistolHP)toSmith).heavy = false;
                        ((DualPistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistolHP)toSmith).silencer = false;
                        ((DualPistolHP)toSmith).short_barrel = false;
                        ((DualPistolHP)toSmith).long_barrel = false;
                        ((DualPistolHP)toSmith).magazine = false;
                        ((DualPistolHP)toSmith).light = false;
                        ((DualPistolHP)toSmith).heavy = true;
                        ((DualPistolHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((DualPistolHP)toSmith).silencer = false;
                        ((DualPistolHP)toSmith).short_barrel = false;
                        ((DualPistolHP)toSmith).long_barrel = false;
                        ((DualPistolHP)toSmith).magazine = false;
                        ((DualPistolHP)toSmith).light = false;
                        ((DualPistolHP)toSmith).heavy = false;
                        ((DualPistolHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof SubMachinegun){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegun)toSmith).silencer = true;
                        ((SubMachinegun)toSmith).short_barrel = false;
                        ((SubMachinegun)toSmith).long_barrel = false;
                        ((SubMachinegun)toSmith).magazine = false;
                        ((SubMachinegun)toSmith).light = false;
                        ((SubMachinegun)toSmith).heavy = false;
                        ((SubMachinegun)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegun)toSmith).silencer = false;
                        ((SubMachinegun)toSmith).short_barrel = true;
                        ((SubMachinegun)toSmith).long_barrel = false;
                        ((SubMachinegun)toSmith).magazine = false;
                        ((SubMachinegun)toSmith).light = false;
                        ((SubMachinegun)toSmith).heavy = false;
                        ((SubMachinegun)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegun)toSmith).silencer = false;
                        ((SubMachinegun)toSmith).short_barrel = false;
                        ((SubMachinegun)toSmith).long_barrel = true;
                        ((SubMachinegun)toSmith).magazine = false;
                        ((SubMachinegun)toSmith).light = false;
                        ((SubMachinegun)toSmith).heavy = false;
                        ((SubMachinegun)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegun)toSmith).silencer = false;
                        ((SubMachinegun)toSmith).short_barrel = false;
                        ((SubMachinegun)toSmith).long_barrel = false;
                        ((SubMachinegun)toSmith).magazine = true;
                        ((SubMachinegun)toSmith).light = false;
                        ((SubMachinegun)toSmith).heavy = false;
                        ((SubMachinegun)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegun)toSmith).silencer = false;
                        ((SubMachinegun)toSmith).short_barrel = false;
                        ((SubMachinegun)toSmith).long_barrel = false;
                        ((SubMachinegun)toSmith).magazine = false;
                        ((SubMachinegun)toSmith).light = true;
                        ((SubMachinegun)toSmith).heavy = false;
                        ((SubMachinegun)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegun)toSmith).silencer = false;
                        ((SubMachinegun)toSmith).short_barrel = false;
                        ((SubMachinegun)toSmith).long_barrel = false;
                        ((SubMachinegun)toSmith).magazine = false;
                        ((SubMachinegun)toSmith).light = false;
                        ((SubMachinegun)toSmith).heavy = true;
                        ((SubMachinegun)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegun)toSmith).silencer = false;
                        ((SubMachinegun)toSmith).short_barrel = false;
                        ((SubMachinegun)toSmith).long_barrel = false;
                        ((SubMachinegun)toSmith).magazine = false;
                        ((SubMachinegun)toSmith).light = false;
                        ((SubMachinegun)toSmith).heavy = false;
                        ((SubMachinegun)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof SubMachinegunAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegunAP)toSmith).silencer = true;
                        ((SubMachinegunAP)toSmith).short_barrel = false;
                        ((SubMachinegunAP)toSmith).long_barrel = false;
                        ((SubMachinegunAP)toSmith).magazine = false;
                        ((SubMachinegunAP)toSmith).light = false;
                        ((SubMachinegunAP)toSmith).heavy = false;
                        ((SubMachinegunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegunAP)toSmith).silencer = false;
                        ((SubMachinegunAP)toSmith).short_barrel = true;
                        ((SubMachinegunAP)toSmith).long_barrel = false;
                        ((SubMachinegunAP)toSmith).magazine = false;
                        ((SubMachinegunAP)toSmith).light = false;
                        ((SubMachinegunAP)toSmith).heavy = false;
                        ((SubMachinegunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegunAP)toSmith).silencer = false;
                        ((SubMachinegunAP)toSmith).short_barrel = false;
                        ((SubMachinegunAP)toSmith).long_barrel = true;
                        ((SubMachinegunAP)toSmith).magazine = false;
                        ((SubMachinegunAP)toSmith).light = false;
                        ((SubMachinegunAP)toSmith).heavy = false;
                        ((SubMachinegunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegunAP)toSmith).silencer = false;
                        ((SubMachinegunAP)toSmith).short_barrel = false;
                        ((SubMachinegunAP)toSmith).long_barrel = false;
                        ((SubMachinegunAP)toSmith).magazine = true;
                        ((SubMachinegunAP)toSmith).light = false;
                        ((SubMachinegunAP)toSmith).heavy = false;
                        ((SubMachinegunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegunAP)toSmith).silencer = false;
                        ((SubMachinegunAP)toSmith).short_barrel = false;
                        ((SubMachinegunAP)toSmith).long_barrel = false;
                        ((SubMachinegunAP)toSmith).magazine = false;
                        ((SubMachinegunAP)toSmith).light = true;
                        ((SubMachinegunAP)toSmith).heavy = false;
                        ((SubMachinegunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegunAP)toSmith).silencer = false;
                        ((SubMachinegunAP)toSmith).short_barrel = false;
                        ((SubMachinegunAP)toSmith).long_barrel = false;
                        ((SubMachinegunAP)toSmith).magazine = false;
                        ((SubMachinegunAP)toSmith).light = false;
                        ((SubMachinegunAP)toSmith).heavy = true;
                        ((SubMachinegunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegunAP)toSmith).silencer = false;
                        ((SubMachinegunAP)toSmith).short_barrel = false;
                        ((SubMachinegunAP)toSmith).long_barrel = false;
                        ((SubMachinegunAP)toSmith).magazine = false;
                        ((SubMachinegunAP)toSmith).light = false;
                        ((SubMachinegunAP)toSmith).heavy = false;
                        ((SubMachinegunAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof SubMachinegunHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegunHP)toSmith).silencer = true;
                        ((SubMachinegunHP)toSmith).short_barrel = false;
                        ((SubMachinegunHP)toSmith).long_barrel = false;
                        ((SubMachinegunHP)toSmith).magazine = false;
                        ((SubMachinegunHP)toSmith).light = false;
                        ((SubMachinegunHP)toSmith).heavy = false;
                        ((SubMachinegunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegunHP)toSmith).silencer = false;
                        ((SubMachinegunHP)toSmith).short_barrel = true;
                        ((SubMachinegunHP)toSmith).long_barrel = false;
                        ((SubMachinegunHP)toSmith).magazine = false;
                        ((SubMachinegunHP)toSmith).light = false;
                        ((SubMachinegunHP)toSmith).heavy = false;
                        ((SubMachinegunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegunHP)toSmith).silencer = false;
                        ((SubMachinegunHP)toSmith).short_barrel = false;
                        ((SubMachinegunHP)toSmith).long_barrel = true;
                        ((SubMachinegunHP)toSmith).magazine = false;
                        ((SubMachinegunHP)toSmith).light = false;
                        ((SubMachinegunHP)toSmith).heavy = false;
                        ((SubMachinegunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegunHP)toSmith).silencer = false;
                        ((SubMachinegunHP)toSmith).short_barrel = false;
                        ((SubMachinegunHP)toSmith).long_barrel = false;
                        ((SubMachinegunHP)toSmith).magazine = true;
                        ((SubMachinegunHP)toSmith).light = false;
                        ((SubMachinegunHP)toSmith).heavy = false;
                        ((SubMachinegunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegunHP)toSmith).silencer = false;
                        ((SubMachinegunHP)toSmith).short_barrel = false;
                        ((SubMachinegunHP)toSmith).long_barrel = false;
                        ((SubMachinegunHP)toSmith).magazine = false;
                        ((SubMachinegunHP)toSmith).light = true;
                        ((SubMachinegunHP)toSmith).heavy = false;
                        ((SubMachinegunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegunHP)toSmith).silencer = false;
                        ((SubMachinegunHP)toSmith).short_barrel = false;
                        ((SubMachinegunHP)toSmith).long_barrel = false;
                        ((SubMachinegunHP)toSmith).magazine = false;
                        ((SubMachinegunHP)toSmith).light = false;
                        ((SubMachinegunHP)toSmith).heavy = true;
                        ((SubMachinegunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SubMachinegunHP)toSmith).silencer = false;
                        ((SubMachinegunHP)toSmith).short_barrel = false;
                        ((SubMachinegunHP)toSmith).long_barrel = false;
                        ((SubMachinegunHP)toSmith).magazine = false;
                        ((SubMachinegunHP)toSmith).light = false;
                        ((SubMachinegunHP)toSmith).heavy = false;
                        ((SubMachinegunHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof AssultRifle){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifle)toSmith).silencer = true;
                        ((AssultRifle)toSmith).short_barrel = false;
                        ((AssultRifle)toSmith).long_barrel = false;
                        ((AssultRifle)toSmith).magazine = false;
                        ((AssultRifle)toSmith).light = false;
                        ((AssultRifle)toSmith).heavy = false;
                        ((AssultRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifle)toSmith).silencer = false;
                        ((AssultRifle)toSmith).short_barrel = true;
                        ((AssultRifle)toSmith).long_barrel = false;
                        ((AssultRifle)toSmith).magazine = false;
                        ((AssultRifle)toSmith).light = false;
                        ((AssultRifle)toSmith).heavy = false;
                        ((AssultRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifle)toSmith).silencer = false;
                        ((AssultRifle)toSmith).short_barrel = false;
                        ((AssultRifle)toSmith).long_barrel = true;
                        ((AssultRifle)toSmith).magazine = false;
                        ((AssultRifle)toSmith).light = false;
                        ((AssultRifle)toSmith).heavy = false;
                        ((AssultRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifle)toSmith).silencer = false;
                        ((AssultRifle)toSmith).short_barrel = false;
                        ((AssultRifle)toSmith).long_barrel = false;
                        ((AssultRifle)toSmith).magazine = true;
                        ((AssultRifle)toSmith).light = false;
                        ((AssultRifle)toSmith).heavy = false;
                        ((AssultRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifle)toSmith).silencer = false;
                        ((AssultRifle)toSmith).short_barrel = false;
                        ((AssultRifle)toSmith).long_barrel = false;
                        ((AssultRifle)toSmith).magazine = false;
                        ((AssultRifle)toSmith).light = true;
                        ((AssultRifle)toSmith).heavy = false;
                        ((AssultRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifle)toSmith).silencer = false;
                        ((AssultRifle)toSmith).short_barrel = false;
                        ((AssultRifle)toSmith).long_barrel = false;
                        ((AssultRifle)toSmith).magazine = false;
                        ((AssultRifle)toSmith).light = false;
                        ((AssultRifle)toSmith).heavy = true;
                        ((AssultRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifle)toSmith).silencer = false;
                        ((AssultRifle)toSmith).short_barrel = false;
                        ((AssultRifle)toSmith).long_barrel = false;
                        ((AssultRifle)toSmith).magazine = false;
                        ((AssultRifle)toSmith).light = false;
                        ((AssultRifle)toSmith).heavy = false;
                        ((AssultRifle)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof AssultRifleAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifleAP)toSmith).silencer = true;
                        ((AssultRifleAP)toSmith).short_barrel = false;
                        ((AssultRifleAP)toSmith).long_barrel = false;
                        ((AssultRifleAP)toSmith).magazine = false;
                        ((AssultRifleAP)toSmith).light = false;
                        ((AssultRifleAP)toSmith).heavy = false;
                        ((AssultRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifleAP)toSmith).silencer = false;
                        ((AssultRifleAP)toSmith).short_barrel = true;
                        ((AssultRifleAP)toSmith).long_barrel = false;
                        ((AssultRifleAP)toSmith).magazine = false;
                        ((AssultRifleAP)toSmith).light = false;
                        ((AssultRifleAP)toSmith).heavy = false;
                        ((AssultRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifleAP)toSmith).silencer = false;
                        ((AssultRifleAP)toSmith).short_barrel = false;
                        ((AssultRifleAP)toSmith).long_barrel = true;
                        ((AssultRifleAP)toSmith).magazine = false;
                        ((AssultRifleAP)toSmith).light = false;
                        ((AssultRifleAP)toSmith).heavy = false;
                        ((AssultRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifleAP)toSmith).silencer = false;
                        ((AssultRifleAP)toSmith).short_barrel = false;
                        ((AssultRifleAP)toSmith).long_barrel = false;
                        ((AssultRifleAP)toSmith).magazine = true;
                        ((AssultRifleAP)toSmith).light = false;
                        ((AssultRifleAP)toSmith).heavy = false;
                        ((AssultRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifleAP)toSmith).silencer = false;
                        ((AssultRifleAP)toSmith).short_barrel = false;
                        ((AssultRifleAP)toSmith).long_barrel = false;
                        ((AssultRifleAP)toSmith).magazine = false;
                        ((AssultRifleAP)toSmith).light = true;
                        ((AssultRifleAP)toSmith).heavy = false;
                        ((AssultRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifleAP)toSmith).silencer = false;
                        ((AssultRifleAP)toSmith).short_barrel = false;
                        ((AssultRifleAP)toSmith).long_barrel = false;
                        ((AssultRifleAP)toSmith).magazine = false;
                        ((AssultRifleAP)toSmith).light = false;
                        ((AssultRifleAP)toSmith).heavy = true;
                        ((AssultRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifleAP)toSmith).silencer = false;
                        ((AssultRifleAP)toSmith).short_barrel = false;
                        ((AssultRifleAP)toSmith).long_barrel = false;
                        ((AssultRifleAP)toSmith).magazine = false;
                        ((AssultRifleAP)toSmith).light = false;
                        ((AssultRifleAP)toSmith).heavy = false;
                        ((AssultRifleAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof AssultRifleHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifleHP)toSmith).silencer = true;
                        ((AssultRifleHP)toSmith).short_barrel = false;
                        ((AssultRifleHP)toSmith).long_barrel = false;
                        ((AssultRifleHP)toSmith).magazine = false;
                        ((AssultRifleHP)toSmith).light = false;
                        ((AssultRifleHP)toSmith).heavy = false;
                        ((AssultRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifleHP)toSmith).silencer = false;
                        ((AssultRifleHP)toSmith).short_barrel = true;
                        ((AssultRifleHP)toSmith).long_barrel = false;
                        ((AssultRifleHP)toSmith).magazine = false;
                        ((AssultRifleHP)toSmith).light = false;
                        ((AssultRifleHP)toSmith).heavy = false;
                        ((AssultRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifleHP)toSmith).silencer = false;
                        ((AssultRifleHP)toSmith).short_barrel = false;
                        ((AssultRifleHP)toSmith).long_barrel = true;
                        ((AssultRifleHP)toSmith).magazine = false;
                        ((AssultRifleHP)toSmith).light = false;
                        ((AssultRifleHP)toSmith).heavy = false;
                        ((AssultRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifleHP)toSmith).silencer = false;
                        ((AssultRifleHP)toSmith).short_barrel = false;
                        ((AssultRifleHP)toSmith).long_barrel = false;
                        ((AssultRifleHP)toSmith).magazine = true;
                        ((AssultRifleHP)toSmith).light = false;
                        ((AssultRifleHP)toSmith).heavy = false;
                        ((AssultRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifleHP)toSmith).silencer = false;
                        ((AssultRifleHP)toSmith).short_barrel = false;
                        ((AssultRifleHP)toSmith).long_barrel = false;
                        ((AssultRifleHP)toSmith).magazine = false;
                        ((AssultRifleHP)toSmith).light = true;
                        ((AssultRifleHP)toSmith).heavy = false;
                        ((AssultRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifleHP)toSmith).silencer = false;
                        ((AssultRifleHP)toSmith).short_barrel = false;
                        ((AssultRifleHP)toSmith).long_barrel = false;
                        ((AssultRifleHP)toSmith).magazine = false;
                        ((AssultRifleHP)toSmith).light = false;
                        ((AssultRifleHP)toSmith).heavy = true;
                        ((AssultRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AssultRifleHP)toSmith).silencer = false;
                        ((AssultRifleHP)toSmith).short_barrel = false;
                        ((AssultRifleHP)toSmith).long_barrel = false;
                        ((AssultRifleHP)toSmith).magazine = false;
                        ((AssultRifleHP)toSmith).light = false;
                        ((AssultRifleHP)toSmith).heavy = false;
                        ((AssultRifleHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof HeavyMachinegun){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegun)toSmith).silencer = true;
                        ((HeavyMachinegun)toSmith).short_barrel = false;
                        ((HeavyMachinegun)toSmith).long_barrel = false;
                        ((HeavyMachinegun)toSmith).magazine = false;
                        ((HeavyMachinegun)toSmith).light = false;
                        ((HeavyMachinegun)toSmith).heavy = false;
                        ((HeavyMachinegun)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegun)toSmith).silencer = false;
                        ((HeavyMachinegun)toSmith).short_barrel = true;
                        ((HeavyMachinegun)toSmith).long_barrel = false;
                        ((HeavyMachinegun)toSmith).magazine = false;
                        ((HeavyMachinegun)toSmith).light = false;
                        ((HeavyMachinegun)toSmith).heavy = false;
                        ((HeavyMachinegun)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegun)toSmith).silencer = false;
                        ((HeavyMachinegun)toSmith).short_barrel = false;
                        ((HeavyMachinegun)toSmith).long_barrel = true;
                        ((HeavyMachinegun)toSmith).magazine = false;
                        ((HeavyMachinegun)toSmith).light = false;
                        ((HeavyMachinegun)toSmith).heavy = false;
                        ((HeavyMachinegun)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegun)toSmith).silencer = false;
                        ((HeavyMachinegun)toSmith).short_barrel = false;
                        ((HeavyMachinegun)toSmith).long_barrel = false;
                        ((HeavyMachinegun)toSmith).magazine = true;
                        ((HeavyMachinegun)toSmith).light = false;
                        ((HeavyMachinegun)toSmith).heavy = false;
                        ((HeavyMachinegun)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegun)toSmith).silencer = false;
                        ((HeavyMachinegun)toSmith).short_barrel = false;
                        ((HeavyMachinegun)toSmith).long_barrel = false;
                        ((HeavyMachinegun)toSmith).magazine = false;
                        ((HeavyMachinegun)toSmith).light = true;
                        ((HeavyMachinegun)toSmith).heavy = false;
                        ((HeavyMachinegun)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegun)toSmith).silencer = false;
                        ((HeavyMachinegun)toSmith).short_barrel = false;
                        ((HeavyMachinegun)toSmith).long_barrel = false;
                        ((HeavyMachinegun)toSmith).magazine = false;
                        ((HeavyMachinegun)toSmith).light = false;
                        ((HeavyMachinegun)toSmith).heavy = true;
                        ((HeavyMachinegun)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegun)toSmith).silencer = false;
                        ((HeavyMachinegun)toSmith).short_barrel = false;
                        ((HeavyMachinegun)toSmith).long_barrel = false;
                        ((HeavyMachinegun)toSmith).magazine = false;
                        ((HeavyMachinegun)toSmith).light = false;
                        ((HeavyMachinegun)toSmith).heavy = false;
                        ((HeavyMachinegun)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof HeavyMachinegunAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegunAP)toSmith).silencer = true;
                        ((HeavyMachinegunAP)toSmith).short_barrel = false;
                        ((HeavyMachinegunAP)toSmith).long_barrel = false;
                        ((HeavyMachinegunAP)toSmith).magazine = false;
                        ((HeavyMachinegunAP)toSmith).light = false;
                        ((HeavyMachinegunAP)toSmith).heavy = false;
                        ((HeavyMachinegunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegunAP)toSmith).silencer = false;
                        ((HeavyMachinegunAP)toSmith).short_barrel = true;
                        ((HeavyMachinegunAP)toSmith).long_barrel = false;
                        ((HeavyMachinegunAP)toSmith).magazine = false;
                        ((HeavyMachinegunAP)toSmith).light = false;
                        ((HeavyMachinegunAP)toSmith).heavy = false;
                        ((HeavyMachinegunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegunAP)toSmith).silencer = false;
                        ((HeavyMachinegunAP)toSmith).short_barrel = false;
                        ((HeavyMachinegunAP)toSmith).long_barrel = true;
                        ((HeavyMachinegunAP)toSmith).magazine = false;
                        ((HeavyMachinegunAP)toSmith).light = false;
                        ((HeavyMachinegunAP)toSmith).heavy = false;
                        ((HeavyMachinegunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegunAP)toSmith).silencer = false;
                        ((HeavyMachinegunAP)toSmith).short_barrel = false;
                        ((HeavyMachinegunAP)toSmith).long_barrel = false;
                        ((HeavyMachinegunAP)toSmith).magazine = true;
                        ((HeavyMachinegunAP)toSmith).light = false;
                        ((HeavyMachinegunAP)toSmith).heavy = false;
                        ((HeavyMachinegunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegunAP)toSmith).silencer = false;
                        ((HeavyMachinegunAP)toSmith).short_barrel = false;
                        ((HeavyMachinegunAP)toSmith).long_barrel = false;
                        ((HeavyMachinegunAP)toSmith).magazine = false;
                        ((HeavyMachinegunAP)toSmith).light = true;
                        ((HeavyMachinegunAP)toSmith).heavy = false;
                        ((HeavyMachinegunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegunAP)toSmith).silencer = false;
                        ((HeavyMachinegunAP)toSmith).short_barrel = false;
                        ((HeavyMachinegunAP)toSmith).long_barrel = false;
                        ((HeavyMachinegunAP)toSmith).magazine = false;
                        ((HeavyMachinegunAP)toSmith).light = false;
                        ((HeavyMachinegunAP)toSmith).heavy = true;
                        ((HeavyMachinegunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegunAP)toSmith).silencer = false;
                        ((HeavyMachinegunAP)toSmith).short_barrel = false;
                        ((HeavyMachinegunAP)toSmith).long_barrel = false;
                        ((HeavyMachinegunAP)toSmith).magazine = false;
                        ((HeavyMachinegunAP)toSmith).light = false;
                        ((HeavyMachinegunAP)toSmith).heavy = false;
                        ((HeavyMachinegunAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof HeavyMachinegunHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegunHP)toSmith).silencer = true;
                        ((HeavyMachinegunHP)toSmith).short_barrel = false;
                        ((HeavyMachinegunHP)toSmith).long_barrel = false;
                        ((HeavyMachinegunHP)toSmith).magazine = false;
                        ((HeavyMachinegunHP)toSmith).light = false;
                        ((HeavyMachinegunHP)toSmith).heavy = false;
                        ((HeavyMachinegunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegunHP)toSmith).silencer = false;
                        ((HeavyMachinegunHP)toSmith).short_barrel = true;
                        ((HeavyMachinegunHP)toSmith).long_barrel = false;
                        ((HeavyMachinegunHP)toSmith).magazine = false;
                        ((HeavyMachinegunHP)toSmith).light = false;
                        ((HeavyMachinegunHP)toSmith).heavy = false;
                        ((HeavyMachinegunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegunHP)toSmith).silencer = false;
                        ((HeavyMachinegunHP)toSmith).short_barrel = false;
                        ((HeavyMachinegunHP)toSmith).long_barrel = true;
                        ((HeavyMachinegunHP)toSmith).magazine = false;
                        ((HeavyMachinegunHP)toSmith).light = false;
                        ((HeavyMachinegunHP)toSmith).heavy = false;
                        ((HeavyMachinegunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegunHP)toSmith).silencer = false;
                        ((HeavyMachinegunHP)toSmith).short_barrel = false;
                        ((HeavyMachinegunHP)toSmith).long_barrel = false;
                        ((HeavyMachinegunHP)toSmith).magazine = true;
                        ((HeavyMachinegunHP)toSmith).light = false;
                        ((HeavyMachinegunHP)toSmith).heavy = false;
                        ((HeavyMachinegunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegunHP)toSmith).silencer = false;
                        ((HeavyMachinegunHP)toSmith).short_barrel = false;
                        ((HeavyMachinegunHP)toSmith).long_barrel = false;
                        ((HeavyMachinegunHP)toSmith).magazine = false;
                        ((HeavyMachinegunHP)toSmith).light = true;
                        ((HeavyMachinegunHP)toSmith).heavy = false;
                        ((HeavyMachinegunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegunHP)toSmith).silencer = false;
                        ((HeavyMachinegunHP)toSmith).short_barrel = false;
                        ((HeavyMachinegunHP)toSmith).long_barrel = false;
                        ((HeavyMachinegunHP)toSmith).magazine = false;
                        ((HeavyMachinegunHP)toSmith).light = false;
                        ((HeavyMachinegunHP)toSmith).heavy = true;
                        ((HeavyMachinegunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HeavyMachinegunHP)toSmith).silencer = false;
                        ((HeavyMachinegunHP)toSmith).short_barrel = false;
                        ((HeavyMachinegunHP)toSmith).long_barrel = false;
                        ((HeavyMachinegunHP)toSmith).magazine = false;
                        ((HeavyMachinegunHP)toSmith).light = false;
                        ((HeavyMachinegunHP)toSmith).heavy = false;
                        ((HeavyMachinegunHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof MiniGun){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGun)toSmith).silencer = true;
                        ((MiniGun)toSmith).short_barrel = false;
                        ((MiniGun)toSmith).long_barrel = false;
                        ((MiniGun)toSmith).magazine = false;
                        ((MiniGun)toSmith).light = false;
                        ((MiniGun)toSmith).heavy = false;
                        ((MiniGun)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGun)toSmith).silencer = false;
                        ((MiniGun)toSmith).short_barrel = true;
                        ((MiniGun)toSmith).long_barrel = false;
                        ((MiniGun)toSmith).magazine = false;
                        ((MiniGun)toSmith).light = false;
                        ((MiniGun)toSmith).heavy = false;
                        ((MiniGun)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGun)toSmith).silencer = false;
                        ((MiniGun)toSmith).short_barrel = false;
                        ((MiniGun)toSmith).long_barrel = true;
                        ((MiniGun)toSmith).magazine = false;
                        ((MiniGun)toSmith).light = false;
                        ((MiniGun)toSmith).heavy = false;
                        ((MiniGun)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGun)toSmith).silencer = false;
                        ((MiniGun)toSmith).short_barrel = false;
                        ((MiniGun)toSmith).long_barrel = false;
                        ((MiniGun)toSmith).magazine = true;
                        ((MiniGun)toSmith).light = false;
                        ((MiniGun)toSmith).heavy = false;
                        ((MiniGun)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGun)toSmith).silencer = false;
                        ((MiniGun)toSmith).short_barrel = false;
                        ((MiniGun)toSmith).long_barrel = false;
                        ((MiniGun)toSmith).magazine = false;
                        ((MiniGun)toSmith).light = true;
                        ((MiniGun)toSmith).heavy = false;
                        ((MiniGun)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGun)toSmith).silencer = false;
                        ((MiniGun)toSmith).short_barrel = false;
                        ((MiniGun)toSmith).long_barrel = false;
                        ((MiniGun)toSmith).magazine = false;
                        ((MiniGun)toSmith).light = false;
                        ((MiniGun)toSmith).heavy = true;
                        ((MiniGun)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGun)toSmith).silencer = false;
                        ((MiniGun)toSmith).short_barrel = false;
                        ((MiniGun)toSmith).long_barrel = false;
                        ((MiniGun)toSmith).magazine = false;
                        ((MiniGun)toSmith).light = false;
                        ((MiniGun)toSmith).heavy = false;
                        ((MiniGun)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof MiniGunAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGunAP)toSmith).silencer = true;
                        ((MiniGunAP)toSmith).short_barrel = false;
                        ((MiniGunAP)toSmith).long_barrel = false;
                        ((MiniGunAP)toSmith).magazine = false;
                        ((MiniGunAP)toSmith).light = false;
                        ((MiniGunAP)toSmith).heavy = false;
                        ((MiniGunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGunAP)toSmith).silencer = false;
                        ((MiniGunAP)toSmith).short_barrel = true;
                        ((MiniGunAP)toSmith).long_barrel = false;
                        ((MiniGunAP)toSmith).magazine = false;
                        ((MiniGunAP)toSmith).light = false;
                        ((MiniGunAP)toSmith).heavy = false;
                        ((MiniGunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGunAP)toSmith).silencer = false;
                        ((MiniGunAP)toSmith).short_barrel = false;
                        ((MiniGunAP)toSmith).long_barrel = true;
                        ((MiniGunAP)toSmith).magazine = false;
                        ((MiniGunAP)toSmith).light = false;
                        ((MiniGunAP)toSmith).heavy = false;
                        ((MiniGunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGunAP)toSmith).silencer = false;
                        ((MiniGunAP)toSmith).short_barrel = false;
                        ((MiniGunAP)toSmith).long_barrel = false;
                        ((MiniGunAP)toSmith).magazine = true;
                        ((MiniGunAP)toSmith).light = false;
                        ((MiniGunAP)toSmith).heavy = false;
                        ((MiniGunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGunAP)toSmith).silencer = false;
                        ((MiniGunAP)toSmith).short_barrel = false;
                        ((MiniGunAP)toSmith).long_barrel = false;
                        ((MiniGunAP)toSmith).magazine = false;
                        ((MiniGunAP)toSmith).light = true;
                        ((MiniGunAP)toSmith).heavy = false;
                        ((MiniGunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGunAP)toSmith).silencer = false;
                        ((MiniGunAP)toSmith).short_barrel = false;
                        ((MiniGunAP)toSmith).long_barrel = false;
                        ((MiniGunAP)toSmith).magazine = false;
                        ((MiniGunAP)toSmith).light = false;
                        ((MiniGunAP)toSmith).heavy = true;
                        ((MiniGunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGunAP)toSmith).silencer = false;
                        ((MiniGunAP)toSmith).short_barrel = false;
                        ((MiniGunAP)toSmith).long_barrel = false;
                        ((MiniGunAP)toSmith).magazine = false;
                        ((MiniGunAP)toSmith).light = false;
                        ((MiniGunAP)toSmith).heavy = false;
                        ((MiniGunAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof MiniGunHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGunHP)toSmith).silencer = true;
                        ((MiniGunHP)toSmith).short_barrel = false;
                        ((MiniGunHP)toSmith).long_barrel = false;
                        ((MiniGunHP)toSmith).magazine = false;
                        ((MiniGunHP)toSmith).light = false;
                        ((MiniGunHP)toSmith).heavy = false;
                        ((MiniGunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGunHP)toSmith).silencer = false;
                        ((MiniGunHP)toSmith).short_barrel = true;
                        ((MiniGunHP)toSmith).long_barrel = false;
                        ((MiniGunHP)toSmith).magazine = false;
                        ((MiniGunHP)toSmith).light = false;
                        ((MiniGunHP)toSmith).heavy = false;
                        ((MiniGunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGunHP)toSmith).silencer = false;
                        ((MiniGunHP)toSmith).short_barrel = false;
                        ((MiniGunHP)toSmith).long_barrel = true;
                        ((MiniGunHP)toSmith).magazine = false;
                        ((MiniGunHP)toSmith).light = false;
                        ((MiniGunHP)toSmith).heavy = false;
                        ((MiniGunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGunHP)toSmith).silencer = false;
                        ((MiniGunHP)toSmith).short_barrel = false;
                        ((MiniGunHP)toSmith).long_barrel = false;
                        ((MiniGunHP)toSmith).magazine = true;
                        ((MiniGunHP)toSmith).light = false;
                        ((MiniGunHP)toSmith).heavy = false;
                        ((MiniGunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGunHP)toSmith).silencer = false;
                        ((MiniGunHP)toSmith).short_barrel = false;
                        ((MiniGunHP)toSmith).long_barrel = false;
                        ((MiniGunHP)toSmith).magazine = false;
                        ((MiniGunHP)toSmith).light = true;
                        ((MiniGunHP)toSmith).heavy = false;
                        ((MiniGunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGunHP)toSmith).silencer = false;
                        ((MiniGunHP)toSmith).short_barrel = false;
                        ((MiniGunHP)toSmith).long_barrel = false;
                        ((MiniGunHP)toSmith).magazine = false;
                        ((MiniGunHP)toSmith).light = false;
                        ((MiniGunHP)toSmith).heavy = true;
                        ((MiniGunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MiniGunHP)toSmith).silencer = false;
                        ((MiniGunHP)toSmith).short_barrel = false;
                        ((MiniGunHP)toSmith).long_barrel = false;
                        ((MiniGunHP)toSmith).magazine = false;
                        ((MiniGunHP)toSmith).light = false;
                        ((MiniGunHP)toSmith).heavy = false;
                        ((MiniGunHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof AutoRifle){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifle)toSmith).silencer = true;
                        ((AutoRifle)toSmith).short_barrel = false;
                        ((AutoRifle)toSmith).long_barrel = false;
                        ((AutoRifle)toSmith).magazine = false;
                        ((AutoRifle)toSmith).light = false;
                        ((AutoRifle)toSmith).heavy = false;
                        ((AutoRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifle)toSmith).silencer = false;
                        ((AutoRifle)toSmith).short_barrel = true;
                        ((AutoRifle)toSmith).long_barrel = false;
                        ((AutoRifle)toSmith).magazine = false;
                        ((AutoRifle)toSmith).light = false;
                        ((AutoRifle)toSmith).heavy = false;
                        ((AutoRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifle)toSmith).silencer = false;
                        ((AutoRifle)toSmith).short_barrel = false;
                        ((AutoRifle)toSmith).long_barrel = true;
                        ((AutoRifle)toSmith).magazine = false;
                        ((AutoRifle)toSmith).light = false;
                        ((AutoRifle)toSmith).heavy = false;
                        ((AutoRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifle)toSmith).silencer = false;
                        ((AutoRifle)toSmith).short_barrel = false;
                        ((AutoRifle)toSmith).long_barrel = false;
                        ((AutoRifle)toSmith).magazine = true;
                        ((AutoRifle)toSmith).light = false;
                        ((AutoRifle)toSmith).heavy = false;
                        ((AutoRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifle)toSmith).silencer = false;
                        ((AutoRifle)toSmith).short_barrel = false;
                        ((AutoRifle)toSmith).long_barrel = false;
                        ((AutoRifle)toSmith).magazine = false;
                        ((AutoRifle)toSmith).light = true;
                        ((AutoRifle)toSmith).heavy = false;
                        ((AutoRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifle)toSmith).silencer = false;
                        ((AutoRifle)toSmith).short_barrel = false;
                        ((AutoRifle)toSmith).long_barrel = false;
                        ((AutoRifle)toSmith).magazine = false;
                        ((AutoRifle)toSmith).light = false;
                        ((AutoRifle)toSmith).heavy = true;
                        ((AutoRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifle)toSmith).silencer = false;
                        ((AutoRifle)toSmith).short_barrel = false;
                        ((AutoRifle)toSmith).long_barrel = false;
                        ((AutoRifle)toSmith).magazine = false;
                        ((AutoRifle)toSmith).light = false;
                        ((AutoRifle)toSmith).heavy = false;
                        ((AutoRifle)toSmith).flash = true;
                        smith();
                        updateQuickslot();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof AutoRifleAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifleAP)toSmith).silencer = true;
                        ((AutoRifleAP)toSmith).short_barrel = false;
                        ((AutoRifleAP)toSmith).long_barrel = false;
                        ((AutoRifleAP)toSmith).magazine = false;
                        ((AutoRifleAP)toSmith).light = false;
                        ((AutoRifleAP)toSmith).heavy = false;
                        ((AutoRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifleAP)toSmith).silencer = false;
                        ((AutoRifleAP)toSmith).short_barrel = true;
                        ((AutoRifleAP)toSmith).long_barrel = false;
                        ((AutoRifleAP)toSmith).magazine = false;
                        ((AutoRifleAP)toSmith).light = false;
                        ((AutoRifleAP)toSmith).heavy = false;
                        ((AutoRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifleAP)toSmith).silencer = false;
                        ((AutoRifleAP)toSmith).short_barrel = false;
                        ((AutoRifleAP)toSmith).long_barrel = true;
                        ((AutoRifleAP)toSmith).magazine = false;
                        ((AutoRifleAP)toSmith).light = false;
                        ((AutoRifleAP)toSmith).heavy = false;
                        ((AutoRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifleAP)toSmith).silencer = false;
                        ((AutoRifleAP)toSmith).short_barrel = false;
                        ((AutoRifleAP)toSmith).long_barrel = false;
                        ((AutoRifleAP)toSmith).magazine = true;
                        ((AutoRifleAP)toSmith).light = false;
                        ((AutoRifleAP)toSmith).heavy = false;
                        ((AutoRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifleAP)toSmith).silencer = false;
                        ((AutoRifleAP)toSmith).short_barrel = false;
                        ((AutoRifleAP)toSmith).long_barrel = false;
                        ((AutoRifleAP)toSmith).magazine = false;
                        ((AutoRifleAP)toSmith).light = true;
                        ((AutoRifleAP)toSmith).heavy = false;
                        ((AutoRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifleAP)toSmith).silencer = false;
                        ((AutoRifleAP)toSmith).short_barrel = false;
                        ((AutoRifleAP)toSmith).long_barrel = false;
                        ((AutoRifleAP)toSmith).magazine = false;
                        ((AutoRifleAP)toSmith).light = false;
                        ((AutoRifleAP)toSmith).heavy = true;
                        ((AutoRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifleAP)toSmith).silencer = false;
                        ((AutoRifleAP)toSmith).short_barrel = false;
                        ((AutoRifleAP)toSmith).long_barrel = false;
                        ((AutoRifleAP)toSmith).magazine = false;
                        ((AutoRifleAP)toSmith).light = false;
                        ((AutoRifleAP)toSmith).heavy = false;
                        ((AutoRifleAP)toSmith).flash = true;
                        smith();
                        updateQuickslot();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof AutoRifleHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifleHP)toSmith).silencer = true;
                        ((AutoRifleHP)toSmith).short_barrel = false;
                        ((AutoRifleHP)toSmith).long_barrel = false;
                        ((AutoRifleHP)toSmith).magazine = false;
                        ((AutoRifleHP)toSmith).light = false;
                        ((AutoRifleHP)toSmith).heavy = false;
                        ((AutoRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifleHP)toSmith).silencer = false;
                        ((AutoRifleHP)toSmith).short_barrel = true;
                        ((AutoRifleHP)toSmith).long_barrel = false;
                        ((AutoRifleHP)toSmith).magazine = false;
                        ((AutoRifleHP)toSmith).light = false;
                        ((AutoRifleHP)toSmith).heavy = false;
                        ((AutoRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifleHP)toSmith).silencer = false;
                        ((AutoRifleHP)toSmith).short_barrel = false;
                        ((AutoRifleHP)toSmith).long_barrel = true;
                        ((AutoRifleHP)toSmith).magazine = false;
                        ((AutoRifleHP)toSmith).light = false;
                        ((AutoRifleHP)toSmith).heavy = false;
                        ((AutoRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifleHP)toSmith).silencer = false;
                        ((AutoRifleHP)toSmith).short_barrel = false;
                        ((AutoRifleHP)toSmith).long_barrel = false;
                        ((AutoRifleHP)toSmith).magazine = true;
                        ((AutoRifleHP)toSmith).light = false;
                        ((AutoRifleHP)toSmith).heavy = false;
                        ((AutoRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifleHP)toSmith).silencer = false;
                        ((AutoRifleHP)toSmith).short_barrel = false;
                        ((AutoRifleHP)toSmith).long_barrel = false;
                        ((AutoRifleHP)toSmith).magazine = false;
                        ((AutoRifleHP)toSmith).light = true;
                        ((AutoRifleHP)toSmith).heavy = false;
                        ((AutoRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifleHP)toSmith).silencer = false;
                        ((AutoRifleHP)toSmith).short_barrel = false;
                        ((AutoRifleHP)toSmith).long_barrel = false;
                        ((AutoRifleHP)toSmith).magazine = false;
                        ((AutoRifleHP)toSmith).light = false;
                        ((AutoRifleHP)toSmith).heavy = true;
                        ((AutoRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AutoRifleHP)toSmith).silencer = false;
                        ((AutoRifleHP)toSmith).short_barrel = false;
                        ((AutoRifleHP)toSmith).long_barrel = false;
                        ((AutoRifleHP)toSmith).magazine = false;
                        ((AutoRifleHP)toSmith).light = false;
                        ((AutoRifleHP)toSmith).heavy = false;
                        ((AutoRifleHP)toSmith).flash = true;
                        smith();
                        updateQuickslot();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof Revolver){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Revolver)toSmith).silencer = true;
                        ((Revolver)toSmith).short_barrel = false;
                        ((Revolver)toSmith).long_barrel = false;
                        ((Revolver)toSmith).magazine = false;
                        ((Revolver)toSmith).light = false;
                        ((Revolver)toSmith).heavy = false;
                        ((Revolver)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Revolver)toSmith).silencer = false;
                        ((Revolver)toSmith).short_barrel = true;
                        ((Revolver)toSmith).long_barrel = false;
                        ((Revolver)toSmith).magazine = false;
                        ((Revolver)toSmith).light = false;
                        ((Revolver)toSmith).heavy = false;
                        ((Revolver)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Revolver)toSmith).silencer = false;
                        ((Revolver)toSmith).short_barrel = false;
                        ((Revolver)toSmith).long_barrel = true;
                        ((Revolver)toSmith).magazine = false;
                        ((Revolver)toSmith).light = false;
                        ((Revolver)toSmith).heavy = false;
                        ((Revolver)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Revolver)toSmith).silencer = false;
                        ((Revolver)toSmith).short_barrel = false;
                        ((Revolver)toSmith).long_barrel = false;
                        ((Revolver)toSmith).magazine = true;
                        ((Revolver)toSmith).light = false;
                        ((Revolver)toSmith).heavy = false;
                        ((Revolver)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Revolver)toSmith).silencer = false;
                        ((Revolver)toSmith).short_barrel = false;
                        ((Revolver)toSmith).long_barrel = false;
                        ((Revolver)toSmith).magazine = false;
                        ((Revolver)toSmith).light = true;
                        ((Revolver)toSmith).heavy = false;
                        ((Revolver)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Revolver)toSmith).silencer = false;
                        ((Revolver)toSmith).short_barrel = false;
                        ((Revolver)toSmith).long_barrel = false;
                        ((Revolver)toSmith).magazine = false;
                        ((Revolver)toSmith).light = false;
                        ((Revolver)toSmith).heavy = true;
                        ((Revolver)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Revolver)toSmith).silencer = false;
                        ((Revolver)toSmith).short_barrel = false;
                        ((Revolver)toSmith).long_barrel = false;
                        ((Revolver)toSmith).magazine = false;
                        ((Revolver)toSmith).light = false;
                        ((Revolver)toSmith).heavy = false;
                        ((Revolver)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof RevolverAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((RevolverAP)toSmith).silencer = true;
                        ((RevolverAP)toSmith).short_barrel = false;
                        ((RevolverAP)toSmith).long_barrel = false;
                        ((RevolverAP)toSmith).magazine = false;
                        ((RevolverAP)toSmith).light = false;
                        ((RevolverAP)toSmith).heavy = false;
                        ((RevolverAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((RevolverAP)toSmith).silencer = false;
                        ((RevolverAP)toSmith).short_barrel = true;
                        ((RevolverAP)toSmith).long_barrel = false;
                        ((RevolverAP)toSmith).magazine = false;
                        ((RevolverAP)toSmith).light = false;
                        ((RevolverAP)toSmith).heavy = false;
                        ((RevolverAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((RevolverAP)toSmith).silencer = false;
                        ((RevolverAP)toSmith).short_barrel = false;
                        ((RevolverAP)toSmith).long_barrel = true;
                        ((RevolverAP)toSmith).magazine = false;
                        ((RevolverAP)toSmith).light = false;
                        ((RevolverAP)toSmith).heavy = false;
                        ((RevolverAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((RevolverAP)toSmith).silencer = false;
                        ((RevolverAP)toSmith).short_barrel = false;
                        ((RevolverAP)toSmith).long_barrel = false;
                        ((RevolverAP)toSmith).magazine = true;
                        ((RevolverAP)toSmith).light = false;
                        ((RevolverAP)toSmith).heavy = false;
                        ((RevolverAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((RevolverAP)toSmith).silencer = false;
                        ((RevolverAP)toSmith).short_barrel = false;
                        ((RevolverAP)toSmith).long_barrel = false;
                        ((RevolverAP)toSmith).magazine = false;
                        ((RevolverAP)toSmith).light = true;
                        ((RevolverAP)toSmith).heavy = false;
                        ((RevolverAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((RevolverAP)toSmith).silencer = false;
                        ((RevolverAP)toSmith).short_barrel = false;
                        ((RevolverAP)toSmith).long_barrel = false;
                        ((RevolverAP)toSmith).magazine = false;
                        ((RevolverAP)toSmith).light = false;
                        ((RevolverAP)toSmith).heavy = true;
                        ((RevolverAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((RevolverAP)toSmith).silencer = false;
                        ((RevolverAP)toSmith).short_barrel = false;
                        ((RevolverAP)toSmith).long_barrel = false;
                        ((RevolverAP)toSmith).magazine = false;
                        ((RevolverAP)toSmith).light = false;
                        ((RevolverAP)toSmith).heavy = false;
                        ((RevolverAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof RevolverHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((RevolverHP)toSmith).silencer = true;
                        ((RevolverHP)toSmith).short_barrel = false;
                        ((RevolverHP)toSmith).long_barrel = false;
                        ((RevolverHP)toSmith).magazine = false;
                        ((RevolverHP)toSmith).light = false;
                        ((RevolverHP)toSmith).heavy = false;
                        ((RevolverHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((RevolverHP)toSmith).silencer = false;
                        ((RevolverHP)toSmith).short_barrel = true;
                        ((RevolverHP)toSmith).long_barrel = false;
                        ((RevolverHP)toSmith).magazine = false;
                        ((RevolverHP)toSmith).light = false;
                        ((RevolverHP)toSmith).heavy = false;
                        ((RevolverHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((RevolverHP)toSmith).silencer = false;
                        ((RevolverHP)toSmith).short_barrel = false;
                        ((RevolverHP)toSmith).long_barrel = true;
                        ((RevolverHP)toSmith).magazine = false;
                        ((RevolverHP)toSmith).light = false;
                        ((RevolverHP)toSmith).heavy = false;
                        ((RevolverHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((RevolverHP)toSmith).silencer = false;
                        ((RevolverHP)toSmith).short_barrel = false;
                        ((RevolverHP)toSmith).long_barrel = false;
                        ((RevolverHP)toSmith).magazine = true;
                        ((RevolverHP)toSmith).light = false;
                        ((RevolverHP)toSmith).heavy = false;
                        ((RevolverHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((RevolverHP)toSmith).silencer = false;
                        ((RevolverHP)toSmith).short_barrel = false;
                        ((RevolverHP)toSmith).long_barrel = false;
                        ((RevolverHP)toSmith).magazine = false;
                        ((RevolverHP)toSmith).light = true;
                        ((RevolverHP)toSmith).heavy = false;
                        ((RevolverHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((RevolverHP)toSmith).silencer = false;
                        ((RevolverHP)toSmith).short_barrel = false;
                        ((RevolverHP)toSmith).long_barrel = false;
                        ((RevolverHP)toSmith).magazine = false;
                        ((RevolverHP)toSmith).light = false;
                        ((RevolverHP)toSmith).heavy = true;
                        ((RevolverHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((RevolverHP)toSmith).silencer = false;
                        ((RevolverHP)toSmith).short_barrel = false;
                        ((RevolverHP)toSmith).long_barrel = false;
                        ((RevolverHP)toSmith).magazine = false;
                        ((RevolverHP)toSmith).light = false;
                        ((RevolverHP)toSmith).heavy = false;
                        ((RevolverHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof HuntingRifle){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifle)toSmith).silencer = true;
                        ((HuntingRifle)toSmith).short_barrel = false;
                        ((HuntingRifle)toSmith).long_barrel = false;
                        ((HuntingRifle)toSmith).magazine = false;
                        ((HuntingRifle)toSmith).light = false;
                        ((HuntingRifle)toSmith).heavy = false;
                        ((HuntingRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifle)toSmith).silencer = false;
                        ((HuntingRifle)toSmith).short_barrel = true;
                        ((HuntingRifle)toSmith).long_barrel = false;
                        ((HuntingRifle)toSmith).magazine = false;
                        ((HuntingRifle)toSmith).light = false;
                        ((HuntingRifle)toSmith).heavy = false;
                        ((HuntingRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifle)toSmith).silencer = false;
                        ((HuntingRifle)toSmith).short_barrel = false;
                        ((HuntingRifle)toSmith).long_barrel = true;
                        ((HuntingRifle)toSmith).magazine = false;
                        ((HuntingRifle)toSmith).light = false;
                        ((HuntingRifle)toSmith).heavy = false;
                        ((HuntingRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifle)toSmith).silencer = false;
                        ((HuntingRifle)toSmith).short_barrel = false;
                        ((HuntingRifle)toSmith).long_barrel = false;
                        ((HuntingRifle)toSmith).magazine = true;
                        ((HuntingRifle)toSmith).light = false;
                        ((HuntingRifle)toSmith).heavy = false;
                        ((HuntingRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifle)toSmith).silencer = false;
                        ((HuntingRifle)toSmith).short_barrel = false;
                        ((HuntingRifle)toSmith).long_barrel = false;
                        ((HuntingRifle)toSmith).magazine = false;
                        ((HuntingRifle)toSmith).light = true;
                        ((HuntingRifle)toSmith).heavy = false;
                        ((HuntingRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifle)toSmith).silencer = false;
                        ((HuntingRifle)toSmith).short_barrel = false;
                        ((HuntingRifle)toSmith).long_barrel = false;
                        ((HuntingRifle)toSmith).magazine = false;
                        ((HuntingRifle)toSmith).light = false;
                        ((HuntingRifle)toSmith).heavy = true;
                        ((HuntingRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifle)toSmith).silencer = false;
                        ((HuntingRifle)toSmith).short_barrel = false;
                        ((HuntingRifle)toSmith).long_barrel = false;
                        ((HuntingRifle)toSmith).magazine = false;
                        ((HuntingRifle)toSmith).light = false;
                        ((HuntingRifle)toSmith).heavy = false;
                        ((HuntingRifle)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof HuntingRifleAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifleAP)toSmith).silencer = true;
                        ((HuntingRifleAP)toSmith).short_barrel = false;
                        ((HuntingRifleAP)toSmith).long_barrel = false;
                        ((HuntingRifleAP)toSmith).magazine = false;
                        ((HuntingRifleAP)toSmith).light = false;
                        ((HuntingRifleAP)toSmith).heavy = false;
                        ((HuntingRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifleAP)toSmith).silencer = false;
                        ((HuntingRifleAP)toSmith).short_barrel = true;
                        ((HuntingRifleAP)toSmith).long_barrel = false;
                        ((HuntingRifleAP)toSmith).magazine = false;
                        ((HuntingRifleAP)toSmith).light = false;
                        ((HuntingRifleAP)toSmith).heavy = false;
                        ((HuntingRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifleAP)toSmith).silencer = false;
                        ((HuntingRifleAP)toSmith).short_barrel = false;
                        ((HuntingRifleAP)toSmith).long_barrel = true;
                        ((HuntingRifleAP)toSmith).magazine = false;
                        ((HuntingRifleAP)toSmith).light = false;
                        ((HuntingRifleAP)toSmith).heavy = false;
                        ((HuntingRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifleAP)toSmith).silencer = false;
                        ((HuntingRifleAP)toSmith).short_barrel = false;
                        ((HuntingRifleAP)toSmith).long_barrel = false;
                        ((HuntingRifleAP)toSmith).magazine = true;
                        ((HuntingRifleAP)toSmith).light = false;
                        ((HuntingRifleAP)toSmith).heavy = false;
                        ((HuntingRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifleAP)toSmith).silencer = false;
                        ((HuntingRifleAP)toSmith).short_barrel = false;
                        ((HuntingRifleAP)toSmith).long_barrel = false;
                        ((HuntingRifleAP)toSmith).magazine = false;
                        ((HuntingRifleAP)toSmith).light = true;
                        ((HuntingRifleAP)toSmith).heavy = false;
                        ((HuntingRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifleAP)toSmith).silencer = false;
                        ((HuntingRifleAP)toSmith).short_barrel = false;
                        ((HuntingRifleAP)toSmith).long_barrel = false;
                        ((HuntingRifleAP)toSmith).magazine = false;
                        ((HuntingRifleAP)toSmith).light = false;
                        ((HuntingRifleAP)toSmith).heavy = true;
                        ((HuntingRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifleAP)toSmith).silencer = false;
                        ((HuntingRifleAP)toSmith).short_barrel = false;
                        ((HuntingRifleAP)toSmith).long_barrel = false;
                        ((HuntingRifleAP)toSmith).magazine = false;
                        ((HuntingRifleAP)toSmith).light = false;
                        ((HuntingRifleAP)toSmith).heavy = false;
                        ((HuntingRifleAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof HuntingRifleHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifleHP)toSmith).silencer = true;
                        ((HuntingRifleHP)toSmith).short_barrel = false;
                        ((HuntingRifleHP)toSmith).long_barrel = false;
                        ((HuntingRifleHP)toSmith).magazine = false;
                        ((HuntingRifleHP)toSmith).light = false;
                        ((HuntingRifleHP)toSmith).heavy = false;
                        ((HuntingRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifleHP)toSmith).silencer = false;
                        ((HuntingRifleHP)toSmith).short_barrel = true;
                        ((HuntingRifleHP)toSmith).long_barrel = false;
                        ((HuntingRifleHP)toSmith).magazine = false;
                        ((HuntingRifleHP)toSmith).light = false;
                        ((HuntingRifleHP)toSmith).heavy = false;
                        ((HuntingRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifleHP)toSmith).silencer = false;
                        ((HuntingRifleHP)toSmith).short_barrel = false;
                        ((HuntingRifleHP)toSmith).long_barrel = true;
                        ((HuntingRifleHP)toSmith).magazine = false;
                        ((HuntingRifleHP)toSmith).light = false;
                        ((HuntingRifleHP)toSmith).heavy = false;
                        ((HuntingRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifleHP)toSmith).silencer = false;
                        ((HuntingRifleHP)toSmith).short_barrel = false;
                        ((HuntingRifleHP)toSmith).long_barrel = false;
                        ((HuntingRifleHP)toSmith).magazine = true;
                        ((HuntingRifleHP)toSmith).light = false;
                        ((HuntingRifleHP)toSmith).heavy = false;
                        ((HuntingRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifleHP)toSmith).silencer = false;
                        ((HuntingRifleHP)toSmith).short_barrel = false;
                        ((HuntingRifleHP)toSmith).long_barrel = false;
                        ((HuntingRifleHP)toSmith).magazine = false;
                        ((HuntingRifleHP)toSmith).light = true;
                        ((HuntingRifleHP)toSmith).heavy = false;
                        ((HuntingRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifleHP)toSmith).silencer = false;
                        ((HuntingRifleHP)toSmith).short_barrel = false;
                        ((HuntingRifleHP)toSmith).long_barrel = false;
                        ((HuntingRifleHP)toSmith).magazine = false;
                        ((HuntingRifleHP)toSmith).light = false;
                        ((HuntingRifleHP)toSmith).heavy = true;
                        ((HuntingRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((HuntingRifleHP)toSmith).silencer = false;
                        ((HuntingRifleHP)toSmith).short_barrel = false;
                        ((HuntingRifleHP)toSmith).long_barrel = false;
                        ((HuntingRifleHP)toSmith).magazine = false;
                        ((HuntingRifleHP)toSmith).light = false;
                        ((HuntingRifleHP)toSmith).heavy = false;
                        ((HuntingRifleHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof Carbine){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Carbine)toSmith).silencer = true;
                        ((Carbine)toSmith).short_barrel = false;
                        ((Carbine)toSmith).long_barrel = false;
                        ((Carbine)toSmith).magazine = false;
                        ((Carbine)toSmith).light = false;
                        ((Carbine)toSmith).heavy = false;
                        ((Carbine)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Carbine)toSmith).silencer = false;
                        ((Carbine)toSmith).short_barrel = true;
                        ((Carbine)toSmith).long_barrel = false;
                        ((Carbine)toSmith).magazine = false;
                        ((Carbine)toSmith).light = false;
                        ((Carbine)toSmith).heavy = false;
                        ((Carbine)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Carbine)toSmith).silencer = false;
                        ((Carbine)toSmith).short_barrel = false;
                        ((Carbine)toSmith).long_barrel = true;
                        ((Carbine)toSmith).magazine = false;
                        ((Carbine)toSmith).light = false;
                        ((Carbine)toSmith).heavy = false;
                        ((Carbine)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Carbine)toSmith).silencer = false;
                        ((Carbine)toSmith).short_barrel = false;
                        ((Carbine)toSmith).long_barrel = false;
                        ((Carbine)toSmith).magazine = true;
                        ((Carbine)toSmith).light = false;
                        ((Carbine)toSmith).heavy = false;
                        ((Carbine)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Carbine)toSmith).silencer = false;
                        ((Carbine)toSmith).short_barrel = false;
                        ((Carbine)toSmith).long_barrel = false;
                        ((Carbine)toSmith).magazine = false;
                        ((Carbine)toSmith).light = true;
                        ((Carbine)toSmith).heavy = false;
                        ((Carbine)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Carbine)toSmith).silencer = false;
                        ((Carbine)toSmith).short_barrel = false;
                        ((Carbine)toSmith).long_barrel = false;
                        ((Carbine)toSmith).magazine = false;
                        ((Carbine)toSmith).light = false;
                        ((Carbine)toSmith).heavy = true;
                        ((Carbine)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((Carbine)toSmith).silencer = false;
                        ((Carbine)toSmith).short_barrel = false;
                        ((Carbine)toSmith).long_barrel = false;
                        ((Carbine)toSmith).magazine = false;
                        ((Carbine)toSmith).light = false;
                        ((Carbine)toSmith).heavy = false;
                        ((Carbine)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof CarbineAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CarbineAP)toSmith).silencer = true;
                        ((CarbineAP)toSmith).short_barrel = false;
                        ((CarbineAP)toSmith).long_barrel = false;
                        ((CarbineAP)toSmith).magazine = false;
                        ((CarbineAP)toSmith).light = false;
                        ((CarbineAP)toSmith).heavy = false;
                        ((CarbineAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CarbineAP)toSmith).silencer = false;
                        ((CarbineAP)toSmith).short_barrel = true;
                        ((CarbineAP)toSmith).long_barrel = false;
                        ((CarbineAP)toSmith).magazine = false;
                        ((CarbineAP)toSmith).light = false;
                        ((CarbineAP)toSmith).heavy = false;
                        ((CarbineAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CarbineAP)toSmith).silencer = false;
                        ((CarbineAP)toSmith).short_barrel = false;
                        ((CarbineAP)toSmith).long_barrel = true;
                        ((CarbineAP)toSmith).magazine = false;
                        ((CarbineAP)toSmith).light = false;
                        ((CarbineAP)toSmith).heavy = false;
                        ((CarbineAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CarbineAP)toSmith).silencer = false;
                        ((CarbineAP)toSmith).short_barrel = false;
                        ((CarbineAP)toSmith).long_barrel = false;
                        ((CarbineAP)toSmith).magazine = true;
                        ((CarbineAP)toSmith).light = false;
                        ((CarbineAP)toSmith).heavy = false;
                        ((CarbineAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CarbineAP)toSmith).silencer = false;
                        ((CarbineAP)toSmith).short_barrel = false;
                        ((CarbineAP)toSmith).long_barrel = false;
                        ((CarbineAP)toSmith).magazine = false;
                        ((CarbineAP)toSmith).light = true;
                        ((CarbineAP)toSmith).heavy = false;
                        ((CarbineAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CarbineAP)toSmith).silencer = false;
                        ((CarbineAP)toSmith).short_barrel = false;
                        ((CarbineAP)toSmith).long_barrel = false;
                        ((CarbineAP)toSmith).magazine = false;
                        ((CarbineAP)toSmith).light = false;
                        ((CarbineAP)toSmith).heavy = true;
                        ((CarbineAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CarbineAP)toSmith).silencer = false;
                        ((CarbineAP)toSmith).short_barrel = false;
                        ((CarbineAP)toSmith).long_barrel = false;
                        ((CarbineAP)toSmith).magazine = false;
                        ((CarbineAP)toSmith).light = false;
                        ((CarbineAP)toSmith).heavy = false;
                        ((CarbineAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof CarbineHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CarbineHP)toSmith).silencer = true;
                        ((CarbineHP)toSmith).short_barrel = false;
                        ((CarbineHP)toSmith).long_barrel = false;
                        ((CarbineHP)toSmith).magazine = false;
                        ((CarbineHP)toSmith).light = false;
                        ((CarbineHP)toSmith).heavy = false;
                        ((CarbineHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CarbineHP)toSmith).silencer = false;
                        ((CarbineHP)toSmith).short_barrel = true;
                        ((CarbineHP)toSmith).long_barrel = false;
                        ((CarbineHP)toSmith).magazine = false;
                        ((CarbineHP)toSmith).light = false;
                        ((CarbineHP)toSmith).heavy = false;
                        ((CarbineHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CarbineHP)toSmith).silencer = false;
                        ((CarbineHP)toSmith).short_barrel = false;
                        ((CarbineHP)toSmith).long_barrel = true;
                        ((CarbineHP)toSmith).magazine = false;
                        ((CarbineHP)toSmith).light = false;
                        ((CarbineHP)toSmith).heavy = false;
                        ((CarbineHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CarbineHP)toSmith).silencer = false;
                        ((CarbineHP)toSmith).short_barrel = false;
                        ((CarbineHP)toSmith).long_barrel = false;
                        ((CarbineHP)toSmith).magazine = true;
                        ((CarbineHP)toSmith).light = false;
                        ((CarbineHP)toSmith).heavy = false;
                        ((CarbineHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CarbineHP)toSmith).silencer = false;
                        ((CarbineHP)toSmith).short_barrel = false;
                        ((CarbineHP)toSmith).long_barrel = false;
                        ((CarbineHP)toSmith).magazine = false;
                        ((CarbineHP)toSmith).light = true;
                        ((CarbineHP)toSmith).heavy = false;
                        ((CarbineHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CarbineHP)toSmith).silencer = false;
                        ((CarbineHP)toSmith).short_barrel = false;
                        ((CarbineHP)toSmith).long_barrel = false;
                        ((CarbineHP)toSmith).magazine = false;
                        ((CarbineHP)toSmith).light = false;
                        ((CarbineHP)toSmith).heavy = true;
                        ((CarbineHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((CarbineHP)toSmith).silencer = false;
                        ((CarbineHP)toSmith).short_barrel = false;
                        ((CarbineHP)toSmith).long_barrel = false;
                        ((CarbineHP)toSmith).magazine = false;
                        ((CarbineHP)toSmith).light = false;
                        ((CarbineHP)toSmith).heavy = false;
                        ((CarbineHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof SniperRifle){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifle)toSmith).silencer = true;
                        ((SniperRifle)toSmith).short_barrel = false;
                        ((SniperRifle)toSmith).long_barrel = false;
                        ((SniperRifle)toSmith).magazine = false;
                        ((SniperRifle)toSmith).light = false;
                        ((SniperRifle)toSmith).heavy = false;
                        ((SniperRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifle)toSmith).silencer = false;
                        ((SniperRifle)toSmith).short_barrel = true;
                        ((SniperRifle)toSmith).long_barrel = false;
                        ((SniperRifle)toSmith).magazine = false;
                        ((SniperRifle)toSmith).light = false;
                        ((SniperRifle)toSmith).heavy = false;
                        ((SniperRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifle)toSmith).silencer = false;
                        ((SniperRifle)toSmith).short_barrel = false;
                        ((SniperRifle)toSmith).long_barrel = true;
                        ((SniperRifle)toSmith).magazine = false;
                        ((SniperRifle)toSmith).light = false;
                        ((SniperRifle)toSmith).heavy = false;
                        ((SniperRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifle)toSmith).silencer = false;
                        ((SniperRifle)toSmith).short_barrel = false;
                        ((SniperRifle)toSmith).long_barrel = false;
                        ((SniperRifle)toSmith).magazine = true;
                        ((SniperRifle)toSmith).light = false;
                        ((SniperRifle)toSmith).heavy = false;
                        ((SniperRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifle)toSmith).silencer = false;
                        ((SniperRifle)toSmith).short_barrel = false;
                        ((SniperRifle)toSmith).long_barrel = false;
                        ((SniperRifle)toSmith).magazine = false;
                        ((SniperRifle)toSmith).light = true;
                        ((SniperRifle)toSmith).heavy = false;
                        ((SniperRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifle)toSmith).silencer = false;
                        ((SniperRifle)toSmith).short_barrel = false;
                        ((SniperRifle)toSmith).long_barrel = false;
                        ((SniperRifle)toSmith).magazine = false;
                        ((SniperRifle)toSmith).light = false;
                        ((SniperRifle)toSmith).heavy = true;
                        ((SniperRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifle)toSmith).silencer = false;
                        ((SniperRifle)toSmith).short_barrel = false;
                        ((SniperRifle)toSmith).long_barrel = false;
                        ((SniperRifle)toSmith).magazine = false;
                        ((SniperRifle)toSmith).light = false;
                        ((SniperRifle)toSmith).heavy = false;
                        ((SniperRifle)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof SniperRifleAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifleAP)toSmith).silencer = true;
                        ((SniperRifleAP)toSmith).short_barrel = false;
                        ((SniperRifleAP)toSmith).long_barrel = false;
                        ((SniperRifleAP)toSmith).magazine = false;
                        ((SniperRifleAP)toSmith).light = false;
                        ((SniperRifleAP)toSmith).heavy = false;
                        ((SniperRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifleAP)toSmith).silencer = false;
                        ((SniperRifleAP)toSmith).short_barrel = true;
                        ((SniperRifleAP)toSmith).long_barrel = false;
                        ((SniperRifleAP)toSmith).magazine = false;
                        ((SniperRifleAP)toSmith).light = false;
                        ((SniperRifleAP)toSmith).heavy = false;
                        ((SniperRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifleAP)toSmith).silencer = false;
                        ((SniperRifleAP)toSmith).short_barrel = false;
                        ((SniperRifleAP)toSmith).long_barrel = true;
                        ((SniperRifleAP)toSmith).magazine = false;
                        ((SniperRifleAP)toSmith).light = false;
                        ((SniperRifleAP)toSmith).heavy = false;
                        ((SniperRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifleAP)toSmith).silencer = false;
                        ((SniperRifleAP)toSmith).short_barrel = false;
                        ((SniperRifleAP)toSmith).long_barrel = false;
                        ((SniperRifleAP)toSmith).magazine = true;
                        ((SniperRifleAP)toSmith).light = false;
                        ((SniperRifleAP)toSmith).heavy = false;
                        ((SniperRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifleAP)toSmith).silencer = false;
                        ((SniperRifleAP)toSmith).short_barrel = false;
                        ((SniperRifleAP)toSmith).long_barrel = false;
                        ((SniperRifleAP)toSmith).magazine = false;
                        ((SniperRifleAP)toSmith).light = true;
                        ((SniperRifleAP)toSmith).heavy = false;
                        ((SniperRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifleAP)toSmith).silencer = false;
                        ((SniperRifleAP)toSmith).short_barrel = false;
                        ((SniperRifleAP)toSmith).long_barrel = false;
                        ((SniperRifleAP)toSmith).magazine = false;
                        ((SniperRifleAP)toSmith).light = false;
                        ((SniperRifleAP)toSmith).heavy = true;
                        ((SniperRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifleAP)toSmith).silencer = false;
                        ((SniperRifleAP)toSmith).short_barrel = false;
                        ((SniperRifleAP)toSmith).long_barrel = false;
                        ((SniperRifleAP)toSmith).magazine = false;
                        ((SniperRifleAP)toSmith).light = false;
                        ((SniperRifleAP)toSmith).heavy = false;
                        ((SniperRifleAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof SniperRifleHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifleHP)toSmith).silencer = true;
                        ((SniperRifleHP)toSmith).short_barrel = false;
                        ((SniperRifleHP)toSmith).long_barrel = false;
                        ((SniperRifleHP)toSmith).magazine = false;
                        ((SniperRifleHP)toSmith).light = false;
                        ((SniperRifleHP)toSmith).heavy = false;
                        ((SniperRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifleHP)toSmith).silencer = false;
                        ((SniperRifleHP)toSmith).short_barrel = true;
                        ((SniperRifleHP)toSmith).long_barrel = false;
                        ((SniperRifleHP)toSmith).magazine = false;
                        ((SniperRifleHP)toSmith).light = false;
                        ((SniperRifleHP)toSmith).heavy = false;
                        ((SniperRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifleHP)toSmith).silencer = false;
                        ((SniperRifleHP)toSmith).short_barrel = false;
                        ((SniperRifleHP)toSmith).long_barrel = true;
                        ((SniperRifleHP)toSmith).magazine = false;
                        ((SniperRifleHP)toSmith).light = false;
                        ((SniperRifleHP)toSmith).heavy = false;
                        ((SniperRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifleHP)toSmith).silencer = false;
                        ((SniperRifleHP)toSmith).short_barrel = false;
                        ((SniperRifleHP)toSmith).long_barrel = false;
                        ((SniperRifleHP)toSmith).magazine = true;
                        ((SniperRifleHP)toSmith).light = false;
                        ((SniperRifleHP)toSmith).heavy = false;
                        ((SniperRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifleHP)toSmith).silencer = false;
                        ((SniperRifleHP)toSmith).short_barrel = false;
                        ((SniperRifleHP)toSmith).long_barrel = false;
                        ((SniperRifleHP)toSmith).magazine = false;
                        ((SniperRifleHP)toSmith).light = true;
                        ((SniperRifleHP)toSmith).heavy = false;
                        ((SniperRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifleHP)toSmith).silencer = false;
                        ((SniperRifleHP)toSmith).short_barrel = false;
                        ((SniperRifleHP)toSmith).long_barrel = false;
                        ((SniperRifleHP)toSmith).magazine = false;
                        ((SniperRifleHP)toSmith).light = false;
                        ((SniperRifleHP)toSmith).heavy = true;
                        ((SniperRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((SniperRifleHP)toSmith).silencer = false;
                        ((SniperRifleHP)toSmith).short_barrel = false;
                        ((SniperRifleHP)toSmith).long_barrel = false;
                        ((SniperRifleHP)toSmith).magazine = false;
                        ((SniperRifleHP)toSmith).light = false;
                        ((SniperRifleHP)toSmith).heavy = false;
                        ((SniperRifleHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof AntimaterRifle){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifle)toSmith).silencer = true;
                        ((AntimaterRifle)toSmith).short_barrel = false;
                        ((AntimaterRifle)toSmith).long_barrel = false;
                        ((AntimaterRifle)toSmith).magazine = false;
                        ((AntimaterRifle)toSmith).light = false;
                        ((AntimaterRifle)toSmith).heavy = false;
                        ((AntimaterRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifle)toSmith).silencer = false;
                        ((AntimaterRifle)toSmith).short_barrel = true;
                        ((AntimaterRifle)toSmith).long_barrel = false;
                        ((AntimaterRifle)toSmith).magazine = false;
                        ((AntimaterRifle)toSmith).light = false;
                        ((AntimaterRifle)toSmith).heavy = false;
                        ((AntimaterRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifle)toSmith).silencer = false;
                        ((AntimaterRifle)toSmith).short_barrel = false;
                        ((AntimaterRifle)toSmith).long_barrel = true;
                        ((AntimaterRifle)toSmith).magazine = false;
                        ((AntimaterRifle)toSmith).light = false;
                        ((AntimaterRifle)toSmith).heavy = false;
                        ((AntimaterRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifle)toSmith).silencer = false;
                        ((AntimaterRifle)toSmith).short_barrel = false;
                        ((AntimaterRifle)toSmith).long_barrel = false;
                        ((AntimaterRifle)toSmith).magazine = true;
                        ((AntimaterRifle)toSmith).light = false;
                        ((AntimaterRifle)toSmith).heavy = false;
                        ((AntimaterRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifle)toSmith).silencer = false;
                        ((AntimaterRifle)toSmith).short_barrel = false;
                        ((AntimaterRifle)toSmith).long_barrel = false;
                        ((AntimaterRifle)toSmith).magazine = false;
                        ((AntimaterRifle)toSmith).light = true;
                        ((AntimaterRifle)toSmith).heavy = false;
                        ((AntimaterRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifle)toSmith).silencer = false;
                        ((AntimaterRifle)toSmith).short_barrel = false;
                        ((AntimaterRifle)toSmith).long_barrel = false;
                        ((AntimaterRifle)toSmith).magazine = false;
                        ((AntimaterRifle)toSmith).light = false;
                        ((AntimaterRifle)toSmith).heavy = true;
                        ((AntimaterRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifle)toSmith).silencer = false;
                        ((AntimaterRifle)toSmith).short_barrel = false;
                        ((AntimaterRifle)toSmith).long_barrel = false;
                        ((AntimaterRifle)toSmith).magazine = false;
                        ((AntimaterRifle)toSmith).light = false;
                        ((AntimaterRifle)toSmith).heavy = false;
                        ((AntimaterRifle)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof AntimaterRifleAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifleAP)toSmith).silencer = true;
                        ((AntimaterRifleAP)toSmith).short_barrel = false;
                        ((AntimaterRifleAP)toSmith).long_barrel = false;
                        ((AntimaterRifleAP)toSmith).magazine = false;
                        ((AntimaterRifleAP)toSmith).light = false;
                        ((AntimaterRifleAP)toSmith).heavy = false;
                        ((AntimaterRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifleAP)toSmith).silencer = false;
                        ((AntimaterRifleAP)toSmith).short_barrel = true;
                        ((AntimaterRifleAP)toSmith).long_barrel = false;
                        ((AntimaterRifleAP)toSmith).magazine = false;
                        ((AntimaterRifleAP)toSmith).light = false;
                        ((AntimaterRifleAP)toSmith).heavy = false;
                        ((AntimaterRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifleAP)toSmith).silencer = false;
                        ((AntimaterRifleAP)toSmith).short_barrel = false;
                        ((AntimaterRifleAP)toSmith).long_barrel = true;
                        ((AntimaterRifleAP)toSmith).magazine = false;
                        ((AntimaterRifleAP)toSmith).light = false;
                        ((AntimaterRifleAP)toSmith).heavy = false;
                        ((AntimaterRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifleAP)toSmith).silencer = false;
                        ((AntimaterRifleAP)toSmith).short_barrel = false;
                        ((AntimaterRifleAP)toSmith).long_barrel = false;
                        ((AntimaterRifleAP)toSmith).magazine = true;
                        ((AntimaterRifleAP)toSmith).light = false;
                        ((AntimaterRifleAP)toSmith).heavy = false;
                        ((AntimaterRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifleAP)toSmith).silencer = false;
                        ((AntimaterRifleAP)toSmith).short_barrel = false;
                        ((AntimaterRifleAP)toSmith).long_barrel = false;
                        ((AntimaterRifleAP)toSmith).magazine = false;
                        ((AntimaterRifleAP)toSmith).light = true;
                        ((AntimaterRifleAP)toSmith).heavy = false;
                        ((AntimaterRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifleAP)toSmith).silencer = false;
                        ((AntimaterRifleAP)toSmith).short_barrel = false;
                        ((AntimaterRifleAP)toSmith).long_barrel = false;
                        ((AntimaterRifleAP)toSmith).magazine = false;
                        ((AntimaterRifleAP)toSmith).light = false;
                        ((AntimaterRifleAP)toSmith).heavy = true;
                        ((AntimaterRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifleAP)toSmith).silencer = false;
                        ((AntimaterRifleAP)toSmith).short_barrel = false;
                        ((AntimaterRifleAP)toSmith).long_barrel = false;
                        ((AntimaterRifleAP)toSmith).magazine = false;
                        ((AntimaterRifleAP)toSmith).light = false;
                        ((AntimaterRifleAP)toSmith).heavy = false;
                        ((AntimaterRifleAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof AntimaterRifleHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifleHP)toSmith).silencer = true;
                        ((AntimaterRifleHP)toSmith).short_barrel = false;
                        ((AntimaterRifleHP)toSmith).long_barrel = false;
                        ((AntimaterRifleHP)toSmith).magazine = false;
                        ((AntimaterRifleHP)toSmith).light = false;
                        ((AntimaterRifleHP)toSmith).heavy = false;
                        ((AntimaterRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifleHP)toSmith).silencer = false;
                        ((AntimaterRifleHP)toSmith).short_barrel = true;
                        ((AntimaterRifleHP)toSmith).long_barrel = false;
                        ((AntimaterRifleHP)toSmith).magazine = false;
                        ((AntimaterRifleHP)toSmith).light = false;
                        ((AntimaterRifleHP)toSmith).heavy = false;
                        ((AntimaterRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifleHP)toSmith).silencer = false;
                        ((AntimaterRifleHP)toSmith).short_barrel = false;
                        ((AntimaterRifleHP)toSmith).long_barrel = true;
                        ((AntimaterRifleHP)toSmith).magazine = false;
                        ((AntimaterRifleHP)toSmith).light = false;
                        ((AntimaterRifleHP)toSmith).heavy = false;
                        ((AntimaterRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifleHP)toSmith).silencer = false;
                        ((AntimaterRifleHP)toSmith).short_barrel = false;
                        ((AntimaterRifleHP)toSmith).long_barrel = false;
                        ((AntimaterRifleHP)toSmith).magazine = true;
                        ((AntimaterRifleHP)toSmith).light = false;
                        ((AntimaterRifleHP)toSmith).heavy = false;
                        ((AntimaterRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifleHP)toSmith).silencer = false;
                        ((AntimaterRifleHP)toSmith).short_barrel = false;
                        ((AntimaterRifleHP)toSmith).long_barrel = false;
                        ((AntimaterRifleHP)toSmith).magazine = false;
                        ((AntimaterRifleHP)toSmith).light = true;
                        ((AntimaterRifleHP)toSmith).heavy = false;
                        ((AntimaterRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifleHP)toSmith).silencer = false;
                        ((AntimaterRifleHP)toSmith).short_barrel = false;
                        ((AntimaterRifleHP)toSmith).long_barrel = false;
                        ((AntimaterRifleHP)toSmith).magazine = false;
                        ((AntimaterRifleHP)toSmith).light = false;
                        ((AntimaterRifleHP)toSmith).heavy = true;
                        ((AntimaterRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((AntimaterRifleHP)toSmith).silencer = false;
                        ((AntimaterRifleHP)toSmith).short_barrel = false;
                        ((AntimaterRifleHP)toSmith).long_barrel = false;
                        ((AntimaterRifleHP)toSmith).magazine = false;
                        ((AntimaterRifleHP)toSmith).light = false;
                        ((AntimaterRifleHP)toSmith).heavy = false;
                        ((AntimaterRifleHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof MarksmanRifle){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifle)toSmith).silencer = true;
                        ((MarksmanRifle)toSmith).short_barrel = false;
                        ((MarksmanRifle)toSmith).long_barrel = false;
                        ((MarksmanRifle)toSmith).magazine = false;
                        ((MarksmanRifle)toSmith).light = false;
                        ((MarksmanRifle)toSmith).heavy = false;
                        ((MarksmanRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifle)toSmith).silencer = false;
                        ((MarksmanRifle)toSmith).short_barrel = true;
                        ((MarksmanRifle)toSmith).long_barrel = false;
                        ((MarksmanRifle)toSmith).magazine = false;
                        ((MarksmanRifle)toSmith).light = false;
                        ((MarksmanRifle)toSmith).heavy = false;
                        ((MarksmanRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifle)toSmith).silencer = false;
                        ((MarksmanRifle)toSmith).short_barrel = false;
                        ((MarksmanRifle)toSmith).long_barrel = true;
                        ((MarksmanRifle)toSmith).magazine = false;
                        ((MarksmanRifle)toSmith).light = false;
                        ((MarksmanRifle)toSmith).heavy = false;
                        ((MarksmanRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifle)toSmith).silencer = false;
                        ((MarksmanRifle)toSmith).short_barrel = false;
                        ((MarksmanRifle)toSmith).long_barrel = false;
                        ((MarksmanRifle)toSmith).magazine = true;
                        ((MarksmanRifle)toSmith).light = false;
                        ((MarksmanRifle)toSmith).heavy = false;
                        ((MarksmanRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifle)toSmith).silencer = false;
                        ((MarksmanRifle)toSmith).short_barrel = false;
                        ((MarksmanRifle)toSmith).long_barrel = false;
                        ((MarksmanRifle)toSmith).magazine = false;
                        ((MarksmanRifle)toSmith).light = true;
                        ((MarksmanRifle)toSmith).heavy = false;
                        ((MarksmanRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifle)toSmith).silencer = false;
                        ((MarksmanRifle)toSmith).short_barrel = false;
                        ((MarksmanRifle)toSmith).long_barrel = false;
                        ((MarksmanRifle)toSmith).magazine = false;
                        ((MarksmanRifle)toSmith).light = false;
                        ((MarksmanRifle)toSmith).heavy = true;
                        ((MarksmanRifle)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifle)toSmith).silencer = false;
                        ((MarksmanRifle)toSmith).short_barrel = false;
                        ((MarksmanRifle)toSmith).long_barrel = false;
                        ((MarksmanRifle)toSmith).magazine = false;
                        ((MarksmanRifle)toSmith).light = false;
                        ((MarksmanRifle)toSmith).heavy = false;
                        ((MarksmanRifle)toSmith).flash = true;
                        smith();
                        updateQuickslot();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof MarksmanRifleAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifleAP)toSmith).silencer = true;
                        ((MarksmanRifleAP)toSmith).short_barrel = false;
                        ((MarksmanRifleAP)toSmith).long_barrel = false;
                        ((MarksmanRifleAP)toSmith).magazine = false;
                        ((MarksmanRifleAP)toSmith).light = false;
                        ((MarksmanRifleAP)toSmith).heavy = false;
                        ((MarksmanRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifleAP)toSmith).silencer = false;
                        ((MarksmanRifleAP)toSmith).short_barrel = true;
                        ((MarksmanRifleAP)toSmith).long_barrel = false;
                        ((MarksmanRifleAP)toSmith).magazine = false;
                        ((MarksmanRifleAP)toSmith).light = false;
                        ((MarksmanRifleAP)toSmith).heavy = false;
                        ((MarksmanRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifleAP)toSmith).silencer = false;
                        ((MarksmanRifleAP)toSmith).short_barrel = false;
                        ((MarksmanRifleAP)toSmith).long_barrel = true;
                        ((MarksmanRifleAP)toSmith).magazine = false;
                        ((MarksmanRifleAP)toSmith).light = false;
                        ((MarksmanRifleAP)toSmith).heavy = false;
                        ((MarksmanRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifleAP)toSmith).silencer = false;
                        ((MarksmanRifleAP)toSmith).short_barrel = false;
                        ((MarksmanRifleAP)toSmith).long_barrel = false;
                        ((MarksmanRifleAP)toSmith).magazine = true;
                        ((MarksmanRifleAP)toSmith).light = false;
                        ((MarksmanRifleAP)toSmith).heavy = false;
                        ((MarksmanRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifleAP)toSmith).silencer = false;
                        ((MarksmanRifleAP)toSmith).short_barrel = false;
                        ((MarksmanRifleAP)toSmith).long_barrel = false;
                        ((MarksmanRifleAP)toSmith).magazine = false;
                        ((MarksmanRifleAP)toSmith).light = true;
                        ((MarksmanRifleAP)toSmith).heavy = false;
                        ((MarksmanRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifleAP)toSmith).silencer = false;
                        ((MarksmanRifleAP)toSmith).short_barrel = false;
                        ((MarksmanRifleAP)toSmith).long_barrel = false;
                        ((MarksmanRifleAP)toSmith).magazine = false;
                        ((MarksmanRifleAP)toSmith).light = false;
                        ((MarksmanRifleAP)toSmith).heavy = true;
                        ((MarksmanRifleAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifleAP)toSmith).silencer = false;
                        ((MarksmanRifleAP)toSmith).short_barrel = false;
                        ((MarksmanRifleAP)toSmith).long_barrel = false;
                        ((MarksmanRifleAP)toSmith).magazine = false;
                        ((MarksmanRifleAP)toSmith).light = false;
                        ((MarksmanRifleAP)toSmith).heavy = false;
                        ((MarksmanRifleAP)toSmith).flash = true;
                        smith();
                        updateQuickslot();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof MarksmanRifleHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifleHP)toSmith).silencer = true;
                        ((MarksmanRifleHP)toSmith).short_barrel = false;
                        ((MarksmanRifleHP)toSmith).long_barrel = false;
                        ((MarksmanRifleHP)toSmith).magazine = false;
                        ((MarksmanRifleHP)toSmith).light = false;
                        ((MarksmanRifleHP)toSmith).heavy = false;
                        ((MarksmanRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifleHP)toSmith).silencer = false;
                        ((MarksmanRifleHP)toSmith).short_barrel = true;
                        ((MarksmanRifleHP)toSmith).long_barrel = false;
                        ((MarksmanRifleHP)toSmith).magazine = false;
                        ((MarksmanRifleHP)toSmith).light = false;
                        ((MarksmanRifleHP)toSmith).heavy = false;
                        ((MarksmanRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifleHP)toSmith).silencer = false;
                        ((MarksmanRifleHP)toSmith).short_barrel = false;
                        ((MarksmanRifleHP)toSmith).long_barrel = true;
                        ((MarksmanRifleHP)toSmith).magazine = false;
                        ((MarksmanRifleHP)toSmith).light = false;
                        ((MarksmanRifleHP)toSmith).heavy = false;
                        ((MarksmanRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifleHP)toSmith).silencer = false;
                        ((MarksmanRifleHP)toSmith).short_barrel = false;
                        ((MarksmanRifleHP)toSmith).long_barrel = false;
                        ((MarksmanRifleHP)toSmith).magazine = true;
                        ((MarksmanRifleHP)toSmith).light = false;
                        ((MarksmanRifleHP)toSmith).heavy = false;
                        ((MarksmanRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifleHP)toSmith).silencer = false;
                        ((MarksmanRifleHP)toSmith).short_barrel = false;
                        ((MarksmanRifleHP)toSmith).long_barrel = false;
                        ((MarksmanRifleHP)toSmith).magazine = false;
                        ((MarksmanRifleHP)toSmith).light = true;
                        ((MarksmanRifleHP)toSmith).heavy = false;
                        ((MarksmanRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifleHP)toSmith).silencer = false;
                        ((MarksmanRifleHP)toSmith).short_barrel = false;
                        ((MarksmanRifleHP)toSmith).long_barrel = false;
                        ((MarksmanRifleHP)toSmith).magazine = false;
                        ((MarksmanRifleHP)toSmith).light = false;
                        ((MarksmanRifleHP)toSmith).heavy = true;
                        ((MarksmanRifleHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((MarksmanRifleHP)toSmith).silencer = false;
                        ((MarksmanRifleHP)toSmith).short_barrel = false;
                        ((MarksmanRifleHP)toSmith).long_barrel = false;
                        ((MarksmanRifleHP)toSmith).magazine = false;
                        ((MarksmanRifleHP)toSmith).light = false;
                        ((MarksmanRifleHP)toSmith).heavy = false;
                        ((MarksmanRifleHP)toSmith).flash = true;
                        smith();
                        updateQuickslot();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof WA2000){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000)toSmith).silencer = true;
                        ((WA2000)toSmith).short_barrel = false;
                        ((WA2000)toSmith).long_barrel = false;
                        ((WA2000)toSmith).magazine = false;
                        ((WA2000)toSmith).light = false;
                        ((WA2000)toSmith).heavy = false;
                        ((WA2000)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000)toSmith).silencer = false;
                        ((WA2000)toSmith).short_barrel = true;
                        ((WA2000)toSmith).long_barrel = false;
                        ((WA2000)toSmith).magazine = false;
                        ((WA2000)toSmith).light = false;
                        ((WA2000)toSmith).heavy = false;
                        ((WA2000)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000)toSmith).silencer = false;
                        ((WA2000)toSmith).short_barrel = false;
                        ((WA2000)toSmith).long_barrel = true;
                        ((WA2000)toSmith).magazine = false;
                        ((WA2000)toSmith).light = false;
                        ((WA2000)toSmith).heavy = false;
                        ((WA2000)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000)toSmith).silencer = false;
                        ((WA2000)toSmith).short_barrel = false;
                        ((WA2000)toSmith).long_barrel = false;
                        ((WA2000)toSmith).magazine = true;
                        ((WA2000)toSmith).light = false;
                        ((WA2000)toSmith).heavy = false;
                        ((WA2000)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000)toSmith).silencer = false;
                        ((WA2000)toSmith).short_barrel = false;
                        ((WA2000)toSmith).long_barrel = false;
                        ((WA2000)toSmith).magazine = false;
                        ((WA2000)toSmith).light = true;
                        ((WA2000)toSmith).heavy = false;
                        ((WA2000)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000)toSmith).silencer = false;
                        ((WA2000)toSmith).short_barrel = false;
                        ((WA2000)toSmith).long_barrel = false;
                        ((WA2000)toSmith).magazine = false;
                        ((WA2000)toSmith).light = false;
                        ((WA2000)toSmith).heavy = true;
                        ((WA2000)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000)toSmith).silencer = false;
                        ((WA2000)toSmith).short_barrel = false;
                        ((WA2000)toSmith).long_barrel = false;
                        ((WA2000)toSmith).magazine = false;
                        ((WA2000)toSmith).light = false;
                        ((WA2000)toSmith).heavy = false;
                        ((WA2000)toSmith).flash = true;
                        smith();
                        updateQuickslot();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof WA2000AP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000AP)toSmith).silencer = true;
                        ((WA2000AP)toSmith).short_barrel = false;
                        ((WA2000AP)toSmith).long_barrel = false;
                        ((WA2000AP)toSmith).magazine = false;
                        ((WA2000AP)toSmith).light = false;
                        ((WA2000AP)toSmith).heavy = false;
                        ((WA2000AP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000AP)toSmith).silencer = false;
                        ((WA2000AP)toSmith).short_barrel = true;
                        ((WA2000AP)toSmith).long_barrel = false;
                        ((WA2000AP)toSmith).magazine = false;
                        ((WA2000AP)toSmith).light = false;
                        ((WA2000AP)toSmith).heavy = false;
                        ((WA2000AP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000AP)toSmith).silencer = false;
                        ((WA2000AP)toSmith).short_barrel = false;
                        ((WA2000AP)toSmith).long_barrel = true;
                        ((WA2000AP)toSmith).magazine = false;
                        ((WA2000AP)toSmith).light = false;
                        ((WA2000AP)toSmith).heavy = false;
                        ((WA2000AP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000AP)toSmith).silencer = false;
                        ((WA2000AP)toSmith).short_barrel = false;
                        ((WA2000AP)toSmith).long_barrel = false;
                        ((WA2000AP)toSmith).magazine = true;
                        ((WA2000AP)toSmith).light = false;
                        ((WA2000AP)toSmith).heavy = false;
                        ((WA2000AP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000AP)toSmith).silencer = false;
                        ((WA2000AP)toSmith).short_barrel = false;
                        ((WA2000AP)toSmith).long_barrel = false;
                        ((WA2000AP)toSmith).magazine = false;
                        ((WA2000AP)toSmith).light = true;
                        ((WA2000AP)toSmith).heavy = false;
                        ((WA2000AP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000AP)toSmith).silencer = false;
                        ((WA2000AP)toSmith).short_barrel = false;
                        ((WA2000AP)toSmith).long_barrel = false;
                        ((WA2000AP)toSmith).magazine = false;
                        ((WA2000AP)toSmith).light = false;
                        ((WA2000AP)toSmith).heavy = true;
                        ((WA2000AP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000AP)toSmith).silencer = false;
                        ((WA2000AP)toSmith).short_barrel = false;
                        ((WA2000AP)toSmith).long_barrel = false;
                        ((WA2000AP)toSmith).magazine = false;
                        ((WA2000AP)toSmith).light = false;
                        ((WA2000AP)toSmith).heavy = false;
                        ((WA2000AP)toSmith).flash = true;
                        smith();
                        updateQuickslot();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof WA2000HP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000HP)toSmith).silencer = true;
                        ((WA2000HP)toSmith).short_barrel = false;
                        ((WA2000HP)toSmith).long_barrel = false;
                        ((WA2000HP)toSmith).magazine = false;
                        ((WA2000HP)toSmith).light = false;
                        ((WA2000HP)toSmith).heavy = false;
                        ((WA2000HP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000HP)toSmith).silencer = false;
                        ((WA2000HP)toSmith).short_barrel = true;
                        ((WA2000HP)toSmith).long_barrel = false;
                        ((WA2000HP)toSmith).magazine = false;
                        ((WA2000HP)toSmith).light = false;
                        ((WA2000HP)toSmith).heavy = false;
                        ((WA2000HP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000HP)toSmith).silencer = false;
                        ((WA2000HP)toSmith).short_barrel = false;
                        ((WA2000HP)toSmith).long_barrel = true;
                        ((WA2000HP)toSmith).magazine = false;
                        ((WA2000HP)toSmith).light = false;
                        ((WA2000HP)toSmith).heavy = false;
                        ((WA2000HP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000HP)toSmith).silencer = false;
                        ((WA2000HP)toSmith).short_barrel = false;
                        ((WA2000HP)toSmith).long_barrel = false;
                        ((WA2000HP)toSmith).magazine = true;
                        ((WA2000HP)toSmith).light = false;
                        ((WA2000HP)toSmith).heavy = false;
                        ((WA2000HP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000HP)toSmith).silencer = false;
                        ((WA2000HP)toSmith).short_barrel = false;
                        ((WA2000HP)toSmith).long_barrel = false;
                        ((WA2000HP)toSmith).magazine = false;
                        ((WA2000HP)toSmith).light = true;
                        ((WA2000HP)toSmith).heavy = false;
                        ((WA2000HP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000HP)toSmith).silencer = false;
                        ((WA2000HP)toSmith).short_barrel = false;
                        ((WA2000HP)toSmith).long_barrel = false;
                        ((WA2000HP)toSmith).magazine = false;
                        ((WA2000HP)toSmith).light = false;
                        ((WA2000HP)toSmith).heavy = true;
                        ((WA2000HP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((WA2000HP)toSmith).silencer = false;
                        ((WA2000HP)toSmith).short_barrel = false;
                        ((WA2000HP)toSmith).long_barrel = false;
                        ((WA2000HP)toSmith).magazine = false;
                        ((WA2000HP)toSmith).light = false;
                        ((WA2000HP)toSmith).heavy = false;
                        ((WA2000HP)toSmith).flash = true;
                        smith();
                        updateQuickslot();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof ShotGun){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGun)toSmith).silencer = true;
                        ((ShotGun)toSmith).short_barrel = false;
                        ((ShotGun)toSmith).long_barrel = false;
                        ((ShotGun)toSmith).magazine = false;
                        ((ShotGun)toSmith).light = false;
                        ((ShotGun)toSmith).heavy = false;
                        ((ShotGun)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGun)toSmith).silencer = false;
                        ((ShotGun)toSmith).short_barrel = true;
                        ((ShotGun)toSmith).long_barrel = false;
                        ((ShotGun)toSmith).magazine = false;
                        ((ShotGun)toSmith).light = false;
                        ((ShotGun)toSmith).heavy = false;
                        ((ShotGun)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGun)toSmith).silencer = false;
                        ((ShotGun)toSmith).short_barrel = false;
                        ((ShotGun)toSmith).long_barrel = true;
                        ((ShotGun)toSmith).magazine = false;
                        ((ShotGun)toSmith).light = false;
                        ((ShotGun)toSmith).heavy = false;
                        ((ShotGun)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGun)toSmith).silencer = false;
                        ((ShotGun)toSmith).short_barrel = false;
                        ((ShotGun)toSmith).long_barrel = false;
                        ((ShotGun)toSmith).magazine = true;
                        ((ShotGun)toSmith).light = false;
                        ((ShotGun)toSmith).heavy = false;
                        ((ShotGun)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGun)toSmith).silencer = false;
                        ((ShotGun)toSmith).short_barrel = false;
                        ((ShotGun)toSmith).long_barrel = false;
                        ((ShotGun)toSmith).magazine = false;
                        ((ShotGun)toSmith).light = true;
                        ((ShotGun)toSmith).heavy = false;
                        ((ShotGun)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGun)toSmith).silencer = false;
                        ((ShotGun)toSmith).short_barrel = false;
                        ((ShotGun)toSmith).long_barrel = false;
                        ((ShotGun)toSmith).magazine = false;
                        ((ShotGun)toSmith).light = false;
                        ((ShotGun)toSmith).heavy = true;
                        ((ShotGun)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGun)toSmith).silencer = false;
                        ((ShotGun)toSmith).short_barrel = false;
                        ((ShotGun)toSmith).long_barrel = false;
                        ((ShotGun)toSmith).magazine = false;
                        ((ShotGun)toSmith).light = false;
                        ((ShotGun)toSmith).heavy = false;
                        ((ShotGun)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof ShotGunAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGunAP)toSmith).silencer = true;
                        ((ShotGunAP)toSmith).short_barrel = false;
                        ((ShotGunAP)toSmith).long_barrel = false;
                        ((ShotGunAP)toSmith).magazine = false;
                        ((ShotGunAP)toSmith).light = false;
                        ((ShotGunAP)toSmith).heavy = false;
                        ((ShotGunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGunAP)toSmith).silencer = false;
                        ((ShotGunAP)toSmith).short_barrel = true;
                        ((ShotGunAP)toSmith).long_barrel = false;
                        ((ShotGunAP)toSmith).magazine = false;
                        ((ShotGunAP)toSmith).light = false;
                        ((ShotGunAP)toSmith).heavy = false;
                        ((ShotGunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGunAP)toSmith).silencer = false;
                        ((ShotGunAP)toSmith).short_barrel = false;
                        ((ShotGunAP)toSmith).long_barrel = true;
                        ((ShotGunAP)toSmith).magazine = false;
                        ((ShotGunAP)toSmith).light = false;
                        ((ShotGunAP)toSmith).heavy = false;
                        ((ShotGunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGunAP)toSmith).silencer = false;
                        ((ShotGunAP)toSmith).short_barrel = false;
                        ((ShotGunAP)toSmith).long_barrel = false;
                        ((ShotGunAP)toSmith).magazine = true;
                        ((ShotGunAP)toSmith).light = false;
                        ((ShotGunAP)toSmith).heavy = false;
                        ((ShotGunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGunAP)toSmith).silencer = false;
                        ((ShotGunAP)toSmith).short_barrel = false;
                        ((ShotGunAP)toSmith).long_barrel = false;
                        ((ShotGunAP)toSmith).magazine = false;
                        ((ShotGunAP)toSmith).light = true;
                        ((ShotGunAP)toSmith).heavy = false;
                        ((ShotGunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGunAP)toSmith).silencer = false;
                        ((ShotGunAP)toSmith).short_barrel = false;
                        ((ShotGunAP)toSmith).long_barrel = false;
                        ((ShotGunAP)toSmith).magazine = false;
                        ((ShotGunAP)toSmith).light = false;
                        ((ShotGunAP)toSmith).heavy = true;
                        ((ShotGunAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGunAP)toSmith).silencer = false;
                        ((ShotGunAP)toSmith).short_barrel = false;
                        ((ShotGunAP)toSmith).long_barrel = false;
                        ((ShotGunAP)toSmith).magazine = false;
                        ((ShotGunAP)toSmith).light = false;
                        ((ShotGunAP)toSmith).heavy = false;
                        ((ShotGunAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof ShotGunHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGunHP)toSmith).silencer = true;
                        ((ShotGunHP)toSmith).short_barrel = false;
                        ((ShotGunHP)toSmith).long_barrel = false;
                        ((ShotGunHP)toSmith).magazine = false;
                        ((ShotGunHP)toSmith).light = false;
                        ((ShotGunHP)toSmith).heavy = false;
                        ((ShotGunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGunHP)toSmith).silencer = false;
                        ((ShotGunHP)toSmith).short_barrel = true;
                        ((ShotGunHP)toSmith).long_barrel = false;
                        ((ShotGunHP)toSmith).magazine = false;
                        ((ShotGunHP)toSmith).light = false;
                        ((ShotGunHP)toSmith).heavy = false;
                        ((ShotGunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGunHP)toSmith).silencer = false;
                        ((ShotGunHP)toSmith).short_barrel = false;
                        ((ShotGunHP)toSmith).long_barrel = true;
                        ((ShotGunHP)toSmith).magazine = false;
                        ((ShotGunHP)toSmith).light = false;
                        ((ShotGunHP)toSmith).heavy = false;
                        ((ShotGunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGunHP)toSmith).silencer = false;
                        ((ShotGunHP)toSmith).short_barrel = false;
                        ((ShotGunHP)toSmith).long_barrel = false;
                        ((ShotGunHP)toSmith).magazine = true;
                        ((ShotGunHP)toSmith).light = false;
                        ((ShotGunHP)toSmith).heavy = false;
                        ((ShotGunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGunHP)toSmith).silencer = false;
                        ((ShotGunHP)toSmith).short_barrel = false;
                        ((ShotGunHP)toSmith).long_barrel = false;
                        ((ShotGunHP)toSmith).magazine = false;
                        ((ShotGunHP)toSmith).light = true;
                        ((ShotGunHP)toSmith).heavy = false;
                        ((ShotGunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGunHP)toSmith).silencer = false;
                        ((ShotGunHP)toSmith).short_barrel = false;
                        ((ShotGunHP)toSmith).long_barrel = false;
                        ((ShotGunHP)toSmith).magazine = false;
                        ((ShotGunHP)toSmith).light = false;
                        ((ShotGunHP)toSmith).heavy = true;
                        ((ShotGunHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((ShotGunHP)toSmith).silencer = false;
                        ((ShotGunHP)toSmith).short_barrel = false;
                        ((ShotGunHP)toSmith).long_barrel = false;
                        ((ShotGunHP)toSmith).magazine = false;
                        ((ShotGunHP)toSmith).light = false;
                        ((ShotGunHP)toSmith).heavy = false;
                        ((ShotGunHP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof KSG){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSG)toSmith).silencer = true;
                        ((KSG)toSmith).short_barrel = false;
                        ((KSG)toSmith).long_barrel = false;
                        ((KSG)toSmith).magazine = false;
                        ((KSG)toSmith).light = false;
                        ((KSG)toSmith).heavy = false;
                        ((KSG)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSG)toSmith).silencer = false;
                        ((KSG)toSmith).short_barrel = true;
                        ((KSG)toSmith).long_barrel = false;
                        ((KSG)toSmith).magazine = false;
                        ((KSG)toSmith).light = false;
                        ((KSG)toSmith).heavy = false;
                        ((KSG)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSG)toSmith).silencer = false;
                        ((KSG)toSmith).short_barrel = false;
                        ((KSG)toSmith).long_barrel = true;
                        ((KSG)toSmith).magazine = false;
                        ((KSG)toSmith).light = false;
                        ((KSG)toSmith).heavy = false;
                        ((KSG)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSG)toSmith).silencer = false;
                        ((KSG)toSmith).short_barrel = false;
                        ((KSG)toSmith).long_barrel = false;
                        ((KSG)toSmith).magazine = true;
                        ((KSG)toSmith).light = false;
                        ((KSG)toSmith).heavy = false;
                        ((KSG)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSG)toSmith).silencer = false;
                        ((KSG)toSmith).short_barrel = false;
                        ((KSG)toSmith).long_barrel = false;
                        ((KSG)toSmith).magazine = false;
                        ((KSG)toSmith).light = true;
                        ((KSG)toSmith).heavy = false;
                        ((KSG)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSG)toSmith).silencer = false;
                        ((KSG)toSmith).short_barrel = false;
                        ((KSG)toSmith).long_barrel = false;
                        ((KSG)toSmith).magazine = false;
                        ((KSG)toSmith).light = false;
                        ((KSG)toSmith).heavy = true;
                        ((KSG)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSG)toSmith).silencer = false;
                        ((KSG)toSmith).short_barrel = false;
                        ((KSG)toSmith).long_barrel = false;
                        ((KSG)toSmith).magazine = false;
                        ((KSG)toSmith).light = false;
                        ((KSG)toSmith).heavy = false;
                        ((KSG)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof KSGAP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSGAP)toSmith).silencer = true;
                        ((KSGAP)toSmith).short_barrel = false;
                        ((KSGAP)toSmith).long_barrel = false;
                        ((KSGAP)toSmith).magazine = false;
                        ((KSGAP)toSmith).light = false;
                        ((KSGAP)toSmith).heavy = false;
                        ((KSGAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSGAP)toSmith).silencer = false;
                        ((KSGAP)toSmith).short_barrel = true;
                        ((KSGAP)toSmith).long_barrel = false;
                        ((KSGAP)toSmith).magazine = false;
                        ((KSGAP)toSmith).light = false;
                        ((KSGAP)toSmith).heavy = false;
                        ((KSGAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSGAP)toSmith).silencer = false;
                        ((KSGAP)toSmith).short_barrel = false;
                        ((KSGAP)toSmith).long_barrel = true;
                        ((KSGAP)toSmith).magazine = false;
                        ((KSGAP)toSmith).light = false;
                        ((KSGAP)toSmith).heavy = false;
                        ((KSGAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSGAP)toSmith).silencer = false;
                        ((KSGAP)toSmith).short_barrel = false;
                        ((KSGAP)toSmith).long_barrel = false;
                        ((KSGAP)toSmith).magazine = true;
                        ((KSGAP)toSmith).light = false;
                        ((KSGAP)toSmith).heavy = false;
                        ((KSGAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSGAP)toSmith).silencer = false;
                        ((KSGAP)toSmith).short_barrel = false;
                        ((KSGAP)toSmith).long_barrel = false;
                        ((KSGAP)toSmith).magazine = false;
                        ((KSGAP)toSmith).light = true;
                        ((KSGAP)toSmith).heavy = false;
                        ((KSGAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSGAP)toSmith).silencer = false;
                        ((KSGAP)toSmith).short_barrel = false;
                        ((KSGAP)toSmith).long_barrel = false;
                        ((KSGAP)toSmith).magazine = false;
                        ((KSGAP)toSmith).light = false;
                        ((KSGAP)toSmith).heavy = true;
                        ((KSGAP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSGAP)toSmith).silencer = false;
                        ((KSGAP)toSmith).short_barrel = false;
                        ((KSGAP)toSmith).long_barrel = false;
                        ((KSGAP)toSmith).magazine = false;
                        ((KSGAP)toSmith).light = false;
                        ((KSGAP)toSmith).heavy = false;
                        ((KSGAP)toSmith).flash = true;
                        smith();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            } else if (toSmith instanceof KSGHP){
                RedButton btnSilencer = new RedButton( Messages.get(this, "silencer") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSGHP)toSmith).silencer = true;
                        ((KSGHP)toSmith).short_barrel = false;
                        ((KSGHP)toSmith).long_barrel = false;
                        ((KSGHP)toSmith).magazine = false;
                        ((KSGHP)toSmith).light = false;
                        ((KSGHP)toSmith).heavy = false;
                        ((KSGHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnSilencer.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnSilencer );

                pos = btnSilencer.bottom();
                //End of button

                RedButton btnShortBarrel = new RedButton( Messages.get(this, "short_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSGHP)toSmith).silencer = false;
                        ((KSGHP)toSmith).short_barrel = true;
                        ((KSGHP)toSmith).long_barrel = false;
                        ((KSGHP)toSmith).magazine = false;
                        ((KSGHP)toSmith).light = false;
                        ((KSGHP)toSmith).heavy = false;
                        ((KSGHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnShortBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnShortBarrel );

                pos = btnShortBarrel.bottom();
                //End of button

                RedButton btnLongBarrel = new RedButton( Messages.get(this, "long_barrel") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSGHP)toSmith).silencer = false;
                        ((KSGHP)toSmith).short_barrel = false;
                        ((KSGHP)toSmith).long_barrel = true;
                        ((KSGHP)toSmith).magazine = false;
                        ((KSGHP)toSmith).light = false;
                        ((KSGHP)toSmith).heavy = false;
                        ((KSGHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLongBarrel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLongBarrel );

                pos = btnLongBarrel.bottom();
                //End of button

                RedButton btnMagazine = new RedButton( Messages.get(this, "magazine") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSGHP)toSmith).silencer = false;
                        ((KSGHP)toSmith).short_barrel = false;
                        ((KSGHP)toSmith).long_barrel = false;
                        ((KSGHP)toSmith).magazine = true;
                        ((KSGHP)toSmith).light = false;
                        ((KSGHP)toSmith).heavy = false;
                        ((KSGHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnMagazine.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnMagazine );

                pos = btnMagazine.bottom();
                //End of button

                RedButton btnLight = new RedButton( Messages.get(this, "light") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSGHP)toSmith).silencer = false;
                        ((KSGHP)toSmith).short_barrel = false;
                        ((KSGHP)toSmith).long_barrel = false;
                        ((KSGHP)toSmith).magazine = false;
                        ((KSGHP)toSmith).light = true;
                        ((KSGHP)toSmith).heavy = false;
                        ((KSGHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnLight.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnLight );

                pos = btnLight.bottom();
                //End of button

                RedButton btnHeavy = new RedButton( Messages.get(this, "heavy") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSGHP)toSmith).silencer = false;
                        ((KSGHP)toSmith).short_barrel = false;
                        ((KSGHP)toSmith).long_barrel = false;
                        ((KSGHP)toSmith).magazine = false;
                        ((KSGHP)toSmith).light = false;
                        ((KSGHP)toSmith).heavy = true;
                        ((KSGHP)toSmith).flash = false;
                        smith();
                    }
                };
                btnHeavy.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnHeavy );

                pos = btnHeavy.bottom();
                //End of button

                RedButton btnFlash = new RedButton( Messages.get(this, "flash") ) {
                    @Override
                    protected void onClick() {
                        hide();
                        ((KSGHP)toSmith).silencer = false;
                        ((KSGHP)toSmith).short_barrel = false;
                        ((KSGHP)toSmith).long_barrel = false;
                        ((KSGHP)toSmith).magazine = false;
                        ((KSGHP)toSmith).light = false;
                        ((KSGHP)toSmith).heavy = false;
                        ((KSGHP)toSmith).flash = true;
                        smith();
                        updateQuickslot();
                    }
                };
                btnFlash.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
                add( btnFlash );

                pos = btnFlash.bottom();
                //End of button
            }

            RedButton btnCancel = new RedButton( Messages.get(this, "cancel") ) {
                @Override
                protected void onClick() {
                    hide();
                    GunSmithingTool.this.collect();
                }
            };
            btnCancel.setRect( MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT );
            add( btnCancel );

            resize( WIDTH, (int)btnCancel.bottom() + MARGIN );
        }

        @Override
        public void onBackPressed() {
            GunSmithingTool.this.collect();
            super.onBackPressed();
        }
    }

    @Override
    public int value() {
        return quantity * (40+10);
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe{
        {
            inputs =  new Class[]{Cartridge.class, LiquidMetal.class};
            inQuantity = new int[]{1, 20};

            cost = 5;

            output = GunSmithingTool.class;
            outQuantity = 1;
        }
    }
}