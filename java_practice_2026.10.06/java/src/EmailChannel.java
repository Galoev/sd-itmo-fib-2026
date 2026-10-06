public class EmailChannel implements Channel {
    private final AppConfig config;

    public EmailChannel(AppConfig config) {
        this.config = config;
    }

    @Override
    public boolean send(Message message) {
        if (config.logEnabled) {
            System.out.println("[log] email, попытка " + (attempt + 1) + ", кому " + message.to);
        }
        String header = "[email] from=" + config.senderEmail + " to=" + message.to;
        if (message.cc != null) {
            header += " cc=" + message.cc;
        }
        header += " subject=\"" + message.subject + "\" priority=" + message.priority;
        System.out.println(header);
        for (String line : message.text().split("\n", -1)) {
            System.out.println("  " + line);
        }
        config.sentCount += 1;
        return true;
    }
    
}
