package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;

public abstract class DisposableMissileWeapon extends MissileWeapon {
    @Override
    public Item split(int amount) {
        return this;
    }
}
