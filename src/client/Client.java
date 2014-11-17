package client;

import org.restlet.data.*;
import org.restlet.resource.InputRepresentation;
import org.restlet.resource.Representation;
import proto.Messages;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hugo on 11/17/14.
 */
public class Client {

    private org.restlet.Client client;
    private MessageParser parser;

    public Client() {
        client = new org.restlet.Client(Protocol.HTTP);
        parser = new MessageParser();
    }

    public void post(Reference uri, Messages.AMessage message) {
        Representation representation = createPostRepresentation(message, uri);
        Response response = client.post(uri, representation);
        checkResponse(response);
    }

    private Representation createPostRepresentation(Messages.AMessage message, Reference messageUri) {
        Request request = new Request(Method.POST, messageUri);
        byte[] bytes = message.toByteArray();
        Representation representation = new InputRepresentation(new ByteArrayInputStream(bytes), MediaType.APPLICATION_OCTET_STREAM);
        request.setEntity(representation);
        return representation;
    }

    public void get(Reference uri) throws IOException {
        Response response = client.get(uri);
        Representation representation = response.getEntity();
        InputStream inputStream = representation.getStream();
        if(checkResponse(response))
            parser.parseMessageList(inputStream);
    }

    public Reference getPostReference() {
        return new Reference("http://localhost:8182/messages");
    }

    public Reference getGetReference(String id) {
        return new Reference("http://localhost:8182/messages/" + id);
    }

    public Reference getSearchReference(String keyword) {
        return new Reference("http://localhost:8182/messages/s/" + keyword);
    }

    private boolean checkResponse(Response response) {
        if (response.getStatus().isSuccess()) {
            String identifier = response.getEntity().getIdentifier().getIdentifier();
            System.out.println(identifier);
            return true;
        } else {
            return false;
        }
    }

}
