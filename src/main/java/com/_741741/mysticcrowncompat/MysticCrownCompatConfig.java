package com._741741.mysticcrowncompat;

import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class MysticCrownCompatConfig {
    public static boolean enableCuriosIntegration;

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder()
        .comment("Mystic Crown Compat Config")
        .push("general");

    private static final ModConfigSpec.BooleanValue ENABLE_CURIOS = BUILDER
        .comment("Enable Curios integration for Mystic Crown (requires Curios installed)")
        .define("enableCuriosIntegration", true);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static void refresh() {
        enableCuriosIntegration = ENABLE_CURIOS.get();
    }

    public static void onLoad(ModConfigEvent event) {
        if (event.getConfig().getSpec() == SPEC) {
            refresh();
        }
    }
}
