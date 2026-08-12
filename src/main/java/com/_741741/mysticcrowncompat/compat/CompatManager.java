package com._741741.mysticcrowncompat.compat;

import net.neoforged.fml.ModList;

public class CompatManager {
    public static boolean CURIOS_LOADED = false;

    public static void init() {
        CURIOS_LOADED = ModList.get().isLoaded("curios");
    }
}
