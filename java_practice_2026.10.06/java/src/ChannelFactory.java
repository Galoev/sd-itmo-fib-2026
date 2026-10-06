public class ChannelFactory {
    private final AppConfig config;
    private final SmsGateway smsGateway;

    public ChannelFactory(AppConfig config) {
        this.config = config;
        this.smsGateway = new LegacySmsGateway();
    }
    public Channel create(String channelType) {
        if (channelType.equals("email")) {
            return new RetryChannel(new EmailChannel(config), config);
        } else if (channelType.equals("sms")) {
            return new RetryChannel(new SmsChannel(config, smsGateway), config);
        } else if (channelType.equals("telegram")) {
            return new RetryChannel(new TelegramChannel(config), config);
        } else {
            throw new IllegalArgumentException("Неизвестный канал: " + channelType);
        }
    }
}
