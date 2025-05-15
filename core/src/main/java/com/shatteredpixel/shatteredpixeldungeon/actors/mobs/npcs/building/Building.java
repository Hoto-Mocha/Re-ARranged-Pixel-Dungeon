package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.building;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NPC;

public class Building extends NPC {
    {
        immunities.add(Amok.class);
    }
}
