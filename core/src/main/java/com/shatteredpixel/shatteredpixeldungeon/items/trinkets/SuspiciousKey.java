package com.shatteredpixel.shatteredpixeldungeon.items.trinkets;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class SuspiciousKey extends Trinket {
    public static int[] additionalRoomsThisRun_0 = new int[7]; //레벨 별로 이번 게임에서 추가로 생성할 방의 개수를 배열로 생성한다.
    public static int[] additionalRoomsThisRun_1 = new int[7];
    public static int[] additionalRoomsThisRun_2 = new int[7];
    public static int[] additionalRoomsThisRun_3 = new int[7];
    public static int[] additionalRoomsThisRun = new int[7]; //이번 게임에서 강화 수치에 따라 추가로 생성할 방의 개수
    public static boolean[] roomsAdded = new boolean[7]; //additionalRoomsThisRun에 값을 넣었는지 계층별로 확인

    {
        image = ItemSpriteSheet.SUSPICIOUS_KEY;
    }

    @Override
    protected int upgradeEnergyCost() {
        return 8+3*level();
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", Messages.decimalFormat("#.#", secretRoomChance(buffedLvl())), (int)(100*doorHideChance(buffedLvl())));
    }

    public static void initForRun() { //던전을 생성할 때 배열의 값을 결정한다.
        for (int i = 0; i < additionalRoomsThisRun_0.length; i++) {
            additionalRoomsThisRun_0[i] = (int) secretRoomChance(0);
            if (Random.Float() < secretRoomChance(0) % 1f) {
                additionalRoomsThisRun_0[i] += 1;
            }
        }
        for (int i = 0; i < additionalRoomsThisRun_1.length; i++) {
            additionalRoomsThisRun_1[i] = (int) secretRoomChance(1);
            if (Random.Float() < secretRoomChance(1) % 1f) {
                additionalRoomsThisRun_1[i] += 1;
            }
        }
        for (int i = 0; i < additionalRoomsThisRun_2.length; i++) {
            additionalRoomsThisRun_2[i] = (int) secretRoomChance(2);
            if (Random.Float() < secretRoomChance(2) % 1f) {
                additionalRoomsThisRun_2[i] += 1;
            }
        }
        for (int i = 0; i < additionalRoomsThisRun_3.length; i++) {
            additionalRoomsThisRun_3[i] = (int) secretRoomChance(3);
            if (Random.Float() < secretRoomChance(3) % 1f) {
                additionalRoomsThisRun_3[i] += 1;
            }
        }
    }

    public static void secretUse(int region, int used) { //비밀방을 추가로 생성했을 경우 그만큼 차감한다.
        int level = trinketLevel(SuspiciousKey.class);
        if (level == -1) {
            return;
        }
        additionalRoomsThisRun[region] -= used;
    }

    public static int additionalSecretRoom(int depth) { //depth에 해당하는 계층에서의 추가 비밀방 개수를 반환한다.
        int region = depth/5;
        region = Math.min(region, 6);
        int level = trinketLevel(SuspiciousKey.class);
        if (level == -1) {
            return 0;
        }
        if (!roomsAdded[region]) { //이전에 이 계층에 대하여 이미 추가 비밀방 개수를 결정했다면, 이 계층에서는 그 숫자를 계속 사용한다.
            switch (level) { //현재 강화 수치에 따라 현재 계층에서 추가로 생성할 방의 개수를 결정
                case 0:
                    additionalRoomsThisRun[region] = additionalRoomsThisRun_0[region];
                    break;
                case 1:
                    additionalRoomsThisRun[region] = additionalRoomsThisRun_1[region];
                    break;
                case 2:
                    additionalRoomsThisRun[region] = additionalRoomsThisRun_2[region];
                    break;
                case 3:
                    additionalRoomsThisRun[region] = additionalRoomsThisRun_3[region];
                    break;
            }
            //현재 계층에서 추가로 생성할 방의 개수를 결정했다면 더 이상 이 계층에 대해서 추가로 생성할 방의 개수를 결정하지 않는다.
            //조건을 추가하지 않을 경우, 계층이 끝나기 전에 장신구를 강화하면 새로운 비밀방을 만들어 낼 수 있게 되어 버린다.
            roomsAdded[region] = true;
        }
        return additionalRoomsThisRun[region];
    }

    public static float secretRoomChance(int level){ //강화 수치별로 한 계층에 몇 개의 추가 비밀방을 생성할지 결정
        switch (level) {
            default:
                return 0;
            case 0:
                return 0.6f;
            case 1:
                return 1.2f;
            case 2:
                return 1.8f;
            case 3:
                return 2.4f;
        }
    }

    public static float doorHideChance() {
        return doorHideChance(trinketLevel(SuspiciousKey.class));
    }

    public static float doorHideChance( int level ){
        if (level == -1){
            return 0f;
        } else {
//            if (DeviceCompat.isDebug()) return 1;
//            else
            return 0.1f + 0.1f*level;
        }
    }

    private static final String LEVEL_0	= "level0";
    private static final String LEVEL_1	= "level1";
    private static final String LEVEL_2	= "level2";
    private static final String LEVEL_3	= "level3";
    private static final String THIS_RUN= "thisRun";
    private static final String ADDED	= "added";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(LEVEL_0, additionalRoomsThisRun_0);
        bundle.put(LEVEL_1, additionalRoomsThisRun_1);
        bundle.put(LEVEL_2, additionalRoomsThisRun_2);
        bundle.put(LEVEL_3, additionalRoomsThisRun_3);
        bundle.put(THIS_RUN, additionalRoomsThisRun);
        bundle.put(ADDED, roomsAdded);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        additionalRoomsThisRun_0 = bundle.getIntArray(LEVEL_0);
        additionalRoomsThisRun_1 = bundle.getIntArray(LEVEL_1);
        additionalRoomsThisRun_2 = bundle.getIntArray(LEVEL_2);
        additionalRoomsThisRun_3 = bundle.getIntArray(LEVEL_3);
        additionalRoomsThisRun = bundle.getIntArray(THIS_RUN);
        roomsAdded = bundle.getBooleanArray(ADDED);
    }
}
