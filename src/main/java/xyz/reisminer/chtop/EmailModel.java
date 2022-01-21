package xyz.reisminer.chtop;

public class EmailModel {

    public String receiver;
    public String sender;
    public String subject;
    public String message;
    public String key = Token.EMAILSENDER_KEY;

    public EmailModel(String to, String from, String subjekt, String msg) {
        receiver = to;
        sender = from;
        subject = subjekt;
        message = msg;
    }

    @Override
    public String toString() {
        return "EmailModel{" +
                "receiver='" + receiver + '\'' +
                ", sender='" + sender + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
