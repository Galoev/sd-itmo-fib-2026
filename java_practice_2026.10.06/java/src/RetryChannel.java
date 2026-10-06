public class RetryChannel implements Channel {

    private final Channel inner;
    private final AppConfig config;

    public RetryChannel(Channel inner, AppConfig config) {
        this.inner = inner;
        this.config = config;
    }

    @Override
    public boolean send(Message message) {
        int retries = message.retries == null ? config.maxRetries : message.retries;
        
        for (int attempt = 0; attempt < retries + 1; attempt++) {
            if (config.logEnabled) {
                System.out.println("[log] telegram, попытка " + (attempt + 1) + ", кому " + message.to);
            }
            
            if (inner.send(message)) {
                return true;
            }
        }
        return false;
    }

    
}
