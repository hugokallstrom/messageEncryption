package server;

import com.google.protobuf.InvalidProtocolBufferException;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.*;
import proto.Messages;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by hugo on 11/4/14.
 * Called when retrieving messages from a specific recipient.
 */
public class IdResource extends BaseResource {

    String id;
    byte[] messageList;

    public IdResource(Context context, Request request, Response response) {
        super(context, request, response);

        this.id = (String) getRequest().getAttributes().get("id");

        try {
            messageList = searchId(id);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (messageList != null) {
            getVariants().add(new Variant(MediaType.APPLICATION_OCTET_STREAM));
            setAvailable(true);
        } else {
            setAvailable(false);
        }

    }

    private byte[] searchId(String id) throws IOException {

        for(String recipient : getByteMessages().keySet()) {
            for(byte[] byteMessage : getByteMessages().get(recipient)) {
                Messages.AMessage message = Messages.AMessage.parseFrom(byteMessage);
                if(message.getId().contains(id)) {
                    Messages.MessageList.Builder builder = Messages.MessageList.newBuilder();
                    return builder.addMessages(message.toBuilder()).build().toByteArray();
                }
            }
        }
        return null;
    }

    /**
     * Returns a listing of all registered messages to the specific recipient.
     */
    @Override
    public Representation represent(Variant variant) throws ResourceException {
        // Generate the right representation according to its media type.
        if (MediaType.APPLICATION_OCTET_STREAM.equals(variant.getMediaType())) {
            if(messageList != null) {
                Representation representation = new InputRepresentation(new ByteArrayInputStream(messageList), MediaType.APPLICATION_OCTET_STREAM);
                return representation;
            }
        }
        return null;
    }
}

