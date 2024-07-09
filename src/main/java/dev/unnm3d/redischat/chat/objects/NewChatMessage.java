package dev.unnm3d.redischat.chat.objects;


import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@EqualsAndHashCode
@ToString
public class NewChatMessage implements DataSerializable {
    private final ChannelAudience sender;
    private final long timestamp;
    @Setter
    private String format;
    @Setter
    private String content;
    private final ChannelAudience receiver;


    /**
     * Creates a ChatMessageInfo as "Server"
     *
     * @param content The message content
     */
    public NewChatMessage(String content) {
        this(new ChannelAudience(), System.currentTimeMillis(), "%message%", content, ChannelAudience.publicChannelAudience());
    }

    /**
     * Creates a ChatMessageInfo as "Server"
     *
     * @param content The message content
     */
    public NewChatMessage(String content, String permissionToSee) {
        this(new ChannelAudience(), System.currentTimeMillis(), "%message%", content, ChannelAudience.publicChannelAudience(permissionToSee));
    }

    /**
     * Creates a ChatMessageInfo from a sender, formatting, message and receiver
     *
     * @param sender   The sender of the message
     * @param format   The formatting of the message
     * @param content  The message content
     * @param receiver The receiver of the message
     */
    public NewChatMessage(@NotNull ChannelAudience sender, long timestamp, @Nullable String format, @Nullable String content, @NotNull ChannelAudience receiver) {
        this.sender = sender;
        this.timestamp = timestamp;
        this.format = Strings.nullToEmpty(format);
        this.content = Strings.nullToEmpty(content);
        this.receiver = receiver;
    }

    public static NewChatMessage deserialize(String serializedMessage) {
        String[] parts = serializedMessage.split("§§;");
        if (parts.length < 1) {
            throw new IllegalArgumentException("Invalid message serialization");
        }

        return new NewChatMessage(
                ChannelAudience.deserialize(parts[0]),
                Long.parseLong(parts[1]),
                parts[2],
                parts[3],
                ChannelAudience.deserialize(parts[4])
        );
    }

    @Override
    public String serialize() {
        return sender.serialize() + "§§;" + timestamp + "§§;" + format + "§§;" + content + "§§;" + receiver.serialize();
    }
}
