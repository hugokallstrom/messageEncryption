package client;

import org.restlet.data.Reference;
import proto.Messages;
import java.io.IOException;

public class ClientMain {

    private static Client client;
    private static String[] arguments;
    private static ArgumentValidator validator;

    public static void main(String[] args) {
        arguments = args;
        try {
            client = new Client();
            validator = new ArgumentValidator();
            if(checkArgs())
                runCommand();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkArgs() {
        return arguments.length >= 1;
    }

    private static void runCommand() throws IOException {
        Reference uri;
        switch(arguments[0]) {
            case "POST":
                validator.validatePostArguments(arguments);
                Messages.AMessage message = createMessageFromArgs();
                uri = client.getPostReference();
                client.post(uri, message);
                break;
            case "GET":
                validator.validateGetArguments(arguments);
                uri = client.getGetReference(arguments[1]);
                client.get(uri);
                break;
            case "SEARCH":
                validator.validateSearchArguments(arguments);
                uri = client.getSearchReference(arguments[1]);
                client.get(uri);
                break;
            default: validator.printUsageInstr();
                break;
        }
    }

    private static Messages.AMessage createMessageFromArgs() {
        ProtoMessage protoMessage = new ProtoMessage();
        protoMessage.setSender(arguments[1]);
        protoMessage.setTopic(arguments[2]);
        protoMessage.setContent(arguments[3]);
        protoMessage.setRecipient(arguments[4]);
        return protoMessage.getMessage();
    }

    // When catching illegalargument from creating message
    private static void printArgumentError() {
        System.out.println("Argument lengths: ");
        System.out.println("Sender: 1-255 characters");
        System.out.println("Topic: 1-255 characters");
        System.out.println("Content: 0-65536 characters");
        System.out.println("Recipient: 1-255 characters");
        System.exit(1);
    }
}
