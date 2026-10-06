public interface SmsGateway {
    public int transmit(String msisdn, byte[] payload);
}
