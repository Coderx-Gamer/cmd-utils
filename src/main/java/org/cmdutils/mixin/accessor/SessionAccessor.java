package org.cmdutils.mixin.accessor;

import net.minecraft.client.session.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.UUID;

@Mixin(Session.class)
public interface SessionAccessor {
    @Accessor
    String getUsername();

    @Accessor
    UUID getUuid();

    @Accessor
    String getAccessToken();

    @Accessor
    Session.AccountType getAccountType();

    @Accessor
    @Mutable
    void setUsername(String username);

    @Accessor
    @Mutable
    void setUuid(UUID uuid);

    @Accessor
    @Mutable
    void setAccessToken(String accessToken);

    @Accessor
    @Mutable
    void setAccountType(Session.AccountType accountType);
}
