package client;

import proto.Messages;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by hugo on 11/4/14.
 * Representation of a messageList. Contains attributes for sender, recipient,
 * topic, time stamp and content. One constructor is used on the client side
 * for automatic time stamp and the other on the server side to insert the time stamp.
 */
public class Message {
    private String sender;
    private String recipient;
    private String topic;
    private String timeStamp;
    private String content;
    private String id;

    public Message(String sender) {
        super();
        this.sender = sender;
        generateId();
        generateTimeStamp();
    }


    public Message() {

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) { this.timeStamp = timeStamp; }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void generateTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd h:mm:ss a");
        this.timeStamp = sdf.format(date);
    }

    private void generateId() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Messages.AMessage toProto() {
        Message tempMessage = new Message(sender);
        Messages.AMessage protoMessage = Messages.AMessage.newBuilder()
                .setSender(sender)
                .setTopic(topic)
                .setContent(content)
                .setRecipient(recipient)
                .setTimeStamp(tempMessage.getTimeStamp())
                .build();

        return protoMessage;
    }
}
