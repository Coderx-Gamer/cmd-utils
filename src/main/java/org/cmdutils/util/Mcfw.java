package org.cmdutils.util;

import net.minecraft.network.packet.Packet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mcfw {
    public static Map<String, McfwFilterType> filteredPackets = new HashMap<>();
    public static List<Packet<?>> delayedPackets = new ArrayList<>();

    public static boolean enabled = false;
}
