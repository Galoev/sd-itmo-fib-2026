import java.nio.charset.Charset;

public class SmsChannel implements Channel {
    private final AppConfig config;

    public SmsChannel(AppConfig config, SmsGatewayAdapter smsGatewayAdapter) {
        this.config = config;
        this.gateway = gateway;
    }


    @Override
    public boolean send(Message message) {
        StringBuilder digits = new StringBuilder();
        for (char ch : message.to.toCharArray()) {
            if (Character.isDigit(ch)) {
                digits.append(ch);
            }
        }
        byte[] payload = message.text().getBytes(Charset.forName("windows-1251"));

        int status = gateway.transmit(digits.toString(), payload);
        if (status == 200) {
            config.sentCount += 1;
            return true;
        }

        if (config.logEnabled) {
            System.out.println("[log] sms не доставлено: " + message.to);
        }
        return false;
    }
    
}
