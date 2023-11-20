package org.cmdutils.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import org.cmdutils.util.ClassMap;
import org.cmdutils.util.Mcfw;
import org.cmdutils.util.McfwFilterType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(at = @At("HEAD"), method = "send(Lnet/minecraft/network/packet/Packet;)V", cancellable = true)
    public void send(Packet<?> packet, CallbackInfo ci) {
        if (Mcfw.enabled) {
            String name = ClassMap.getMappedName(packet.getClass());
            McfwFilterType type = Mcfw.filteredPackets.get(name);
            if (type == null) {
                type = Mcfw.filteredPackets.get("all");
            }
            if (type != null) {
                if (type == McfwFilterType.DELAYED) {
                    Mcfw.delayedPackets.add(packet);
                }
                ci.cancel();
            }
        }
    }
}
