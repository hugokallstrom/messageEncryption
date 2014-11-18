package client;

import org.restlet.data.Reference;
import org.restlet.resource.ResourceException;

import java.io.IOException;

/**
 * Created by hugo on 11/18/14.
 */
public class TestBuilder {

    Client client;
    String id;


    public TestBuilder() {
        client = new Client();

    }

    public void testPost() throws ResourceException {
        Reference reference = client.getPostReference();
        ProtoMessage message = createMessage();
        id = message.getId();
        System.out.println("Posting message with id " + message.getId());
        client.post(reference, message.getMessage());
    }

    private ProtoMessage createMessage() {
        ProtoMessage message = new ProtoMessage();
        message.setSender("Hugo");
        message.setTopic("Test Topic");
        message.setContent("Test content");
        message.setRecipient("David");
        return message;
    }

    public void testGet() throws IOException, ResourceException {
        Reference reference = client.getGetReference(id);
        System.out.println( "\n \n" + "Getting messsage with id: " + id);
        client.get(reference);
    }

    public void testSearch() throws IOException, ResourceException {
        Reference reference = client.getSearchReference("content");
        System.out.println( "\n \n" + "Searchin message with keyword content");
        client.get(reference);
    }

}

