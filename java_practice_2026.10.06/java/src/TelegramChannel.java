public class TelegramChannel  implements  Channel{

    private final AppConfig config;

    public TelegramChannel(AppConfig config) {
        this.config = config;
    }

    @Override
    public boolean send(Message message) {
        String line = "[telegram] " + message.to + ": " + message.text();
        if (message.silent) {
            line += " (без звука)";
        }
        System.out.println(line);
        config.sentCount += 1;
        return true;
    }
    
}
