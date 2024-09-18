package dev.unnm3d.redischat.api.objects;

public enum KnownChatEntities {
    GENERAL_CHANNEL("public"),
    VOID_CHAT("void"),
    SERVER_SENDER("*Server*"),
    CHANNEL_PREFIX("#"),
    DISCORD_PREFIX("@"),
    PERMISSION_PREFIX("~"),
    STAFFCHAT_CHANNEL_NAME("staffchat"),
    ALL_PLAYERS("-ALL-"),
    ;

    private final String keyName;

    /**
     * @param keyName the name of the key
     */
    KnownChatEntities(final String keyName) {
        this.keyName = keyName;
    }

    @Override
    public String toString() {
        return keyName;
    }
}
