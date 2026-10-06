/*
 * Сервис уведомлений интернет-магазина.
 *
 * Отправляет уведомления о заказах на почту, по SMS и в Telegram.
 * Сеть не используется: каналы печатают, что бы они отправили.
 */

import java.util.List;
import java.util.Map;

public class Notifier {

    private final ChannelFactory factory;
    private final AppConfig config;

    public Notifier(AppConfig config) {
        factory = new ChannelFactory(config);
        this.config = config;
    }

    public boolean send(String channelType, Message message) {
        return factory.create(channelType).send(message);
    }

    /** Отправляет текст всем в группе. Группа может содержать другие группы. */
    @SuppressWarnings("unchecked")
    public static void notifyGroup(Notifier notifier, String channelType, Object group, String text) {
        if (group instanceof String) {
            Message message = new Message((String) group, "", text, null, "normal", true, null, null, false, null);
            notifier.send(channelType, message);
        } else if (group instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) group;
            System.out.println("[group] " + map.get("name"));
            for (Object member : (List<Object>) map.get("members")) {
                notifyGroup(notifier, channelType, member, text);
            }
        } else {
            throw new IllegalArgumentException("Непонятный получатель: " + group);
        }
    }
}
