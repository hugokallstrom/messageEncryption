package client;


import proto.Messages;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hugo on 11/17/14.
 */
public class MessageParser {

    public MessageParser() {
    }

    public void parseMessageList(InputStream inputStream) throws IOException {
        Messages.MessageList list = Messages.MessageList.parseFrom(inputStream);
        for(int i=0; i < list.getMessagesCount(); i++) {
            printMessage(list.getMessages(i));
        }
    }

    private static void printMessage(Messages.AMessage message) {
        System.out.println("Message: ");
        System.out.println("Sender: " + message.getSender());
        System.out.println("Topic: " + message.getTopic());
        System.out.println("Content: " + message.getContent());
        System.out.println("Recipient: " + message.getRecipient());
        System.out.println("Timestamp: " + message.getTimeStamp());
        System.out.println("Id: " + message.getId());
    }

}
