package com._741741.mysticcrowncompat.compat;

import net.neoforged.fml.ModList;

/**
 * 管理可选模组兼容,防止未安装的模组类被触发加载。
 * <p>
 * CURIOS_LOADED 在类加载时即确定(与 Travellers-Set-Plus 一致),
 * 避免在构造函数阶段读取时仍为 false 导致 Curios 能力注册被跳过。
 */
public class CompatManager {
    public static final boolean CURIOS_LOADED = ModList.get().isLoaded("curios");
}
