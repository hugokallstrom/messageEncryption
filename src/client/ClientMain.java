package client;

import org.restlet.data.Reference;
import org.restlet.resource.ResourceException;
import proto.Messages;
import java.io.IOException;
import java.util.IllegalFormatCodePointException;

public class ClientMain {

    private static Client client;
    private static ArgumentValidator validator;

    public static void main(String[] args) {
        try {
            client = new Client();
            validator = new ArgumentValidator();
            checkArgs(args);
            runCommand(args);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (ResourceException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void runCommand(String[] arguments) throws IOException, ResourceException {
        Reference uri;
        switch(arguments[0]) {
            case "POST":
                validator.validatePostArguments(arguments);
                Messages.AMessage message = createMessageFromArgs(arguments);
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
            case "TEST":
                runTests();
                break;
            default: validator.printUsageInstr();
                break;
        }
    }

    private static void runTests() throws IOException, ResourceException {
        TestBuilder test = new TestBuilder();
        test.testPost();
        test.testGet();
        test.testSearch();
    }

    private static void checkArgs(String [] arguments) throws IllegalArgumentException {
        if(arguments.length < 1) {
            throw new IllegalArgumentException("Supply at least one argument");
        }
    }

    private static Messages.AMessage createMessageFromArgs(String[] arguments) {
        ProtoMessage protoMessage = new ProtoMessage();
        try {
            protoMessage.setSender(arguments[1]);
            protoMessage.setTopic(arguments[2]);
            protoMessage.setContent(arguments[3]);
            protoMessage.setRecipient(arguments[4]);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return protoMessage.getMessage();
    }

}
