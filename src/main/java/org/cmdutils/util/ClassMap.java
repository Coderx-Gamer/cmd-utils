package org.cmdutils.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ClassMap {
    public static final Map<String, String> PACKET_MAP = new HashMap<>();

    public static String formatClassName(String name) {
        String[] parts = name.isEmpty() ? new String[]{name} : name.split(Pattern.quote("."));
        return parts[parts.length - 1];
    }

    public static String getMappedName(Class<?> clazz) {
        String className = formatClassName(clazz.getName());
        String packetName = PACKET_MAP.get(className);
        return packetName == null ? className.replace("C2SPacket", "") : packetName;
    }

    static {
        PACKET_MAP.put("class_2793", "TeleportConfirm");
        PACKET_MAP.put("class_2795", "QueryBlockNbt");
        PACKET_MAP.put("class_2797", "ChatMessage");
        PACKET_MAP.put("class_2799", "ClientStatus");
        PACKET_MAP.put("class_2803", "ClientOptions");
        PACKET_MAP.put("class_2805", "RequestCommandCompletions");
        PACKET_MAP.put("class_2811", "ButtonClick");
        PACKET_MAP.put("class_2813", "ClickSlot");
        PACKET_MAP.put("class_2815", "CloseHandledScreen");
        PACKET_MAP.put("class_2817", "CustomPayload");
        PACKET_MAP.put("class_2820", "BookUpdate");
        PACKET_MAP.put("class_2822", "QueryEntityNbt");
        PACKET_MAP.put("class_2824", "PlayerInteractEntity");
        PACKET_MAP.put("class_2824$class_5909", "PlayerInteractEntity$InteractHandler");
        PACKET_MAP.put("class_2824$class_5910", "PlayerInteractEntity$InteractAtHandler");
        PACKET_MAP.put("class_2827", "KeepAlive");
        PACKET_MAP.put("class_2828$class_2829", "PlayerMove$PositionAndOnGround");
        PACKET_MAP.put("class_2828$class_2830", "PlayerMove$Full");
        PACKET_MAP.put("class_2828$class_2831", "PlayerMove$LookAndOnGround");
        PACKET_MAP.put("class_2828$class_5911", "PlayerMove$OnGroundOnly");
        PACKET_MAP.put("class_2833", "VehicleMove");
        PACKET_MAP.put("class_2836", "BoatPaddleState");
        PACKET_MAP.put("class_2838", "PickFromInventory");
        PACKET_MAP.put("class_2840", "CraftRequest");
        PACKET_MAP.put("class_2842", "UpdatePlayerAbilities");
        PACKET_MAP.put("class_2846", "PlayerAction");
        PACKET_MAP.put("class_2848", "ClientCommand");
        PACKET_MAP.put("class_2851", "PlayerInput");
        PACKET_MAP.put("class_2853", "RecipeBookData");
        PACKET_MAP.put("class_2855", "RenameItem");
        PACKET_MAP.put("class_2856", "ResourcePackStatus");
        PACKET_MAP.put("class_2859", "AdvancementTab");
        PACKET_MAP.put("class_2863", "SelectMerchantTrade");
        PACKET_MAP.put("class_2866", "UpdateBeacon");
        PACKET_MAP.put("class_2868", "UpdateSelectedSlot");
        PACKET_MAP.put("class_2870", "UpdateCommandBlock");
        PACKET_MAP.put("class_2871", "UpdateCommandBlockMinecart");
        PACKET_MAP.put("class_2873", "CreativeInventoryAction");
        PACKET_MAP.put("class_2875", "UpdateStructureBlock");
        PACKET_MAP.put("class_2877", "UpdateSign");
        PACKET_MAP.put("class_2879", "HandSwing");
        PACKET_MAP.put("class_2884", "SpectatorTeleport");
        PACKET_MAP.put("class_2885", "PlayerInteractBlock");
        PACKET_MAP.put("class_2886", "PlayerInteractItem");
        PACKET_MAP.put("class_2889", "Handshake");
        PACKET_MAP.put("class_2913", "LoginQueryResponse");
        PACKET_MAP.put("class_2915", "LoginHello");
        PACKET_MAP.put("class_2917", "LoginKey");
        PACKET_MAP.put("class_2935", "QueryPing");
        PACKET_MAP.put("class_2937", "QueryRequest");
        PACKET_MAP.put("class_3753", "UpdateJigsaw");
        PACKET_MAP.put("class_4210", "UpdateDifficulty");
        PACKET_MAP.put("class_4211", "UpdateDifficultyLock");
        PACKET_MAP.put("class_5194", "JigsawGenerating");
        PACKET_MAP.put("class_5427", "RecipeCategoryOptions");
        PACKET_MAP.put("class_6374", "CommonPong");
        PACKET_MAP.put("class_7472", "CommandExecution");
        PACKET_MAP.put("class_7640", "MessageAcknowledgment");
        PACKET_MAP.put("class_7861", "PlayerSession");
        PACKET_MAP.put("class_8590", "AcknowledgeChunks");
        PACKET_MAP.put("class_8591", "AcknowledgeReconfiguration");
        PACKET_MAP.put("class_8593", "EnterConfiguration");
        PACKET_MAP.put("class_8736", "Ready");
    }
}
