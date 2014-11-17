package server; /**
 * Created by hugo on 11/4/14.
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.google.protobuf.InvalidProtocolBufferException;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.*;
import proto.Messages;

/**
 *
 */
public class MessageResource extends BaseResource {

    Collection<ArrayList<byte[]>> messages;

    public MessageResource(Context context, Request request, Response response) {
        super(context, request, response);
        messages = getByteMessages().values();
        setModifiable(true);
        getVariants().add(new Variant(MediaType.APPLICATION_OCTET_STREAM));
    }

    /**
     * Handle POST requests: send a new messageList.
     */
    @Override
    public void acceptRepresentation(Representation entity)
            throws ResourceException {
        try {
            InputStream is = entity.getStream();
            Messages.AMessage message = Messages.AMessage.parseFrom(is);
            String recipient = message.getRecipient();

            if (!getByteMessages().containsKey(recipient)) {
                ArrayList<byte[]> messageList = new ArrayList<byte[]>();
                getByteMessages().put(recipient, messageList);
            }
            getByteMessages().get(recipient).add(message.toByteArray());

            getResponse().setStatus(Status.SUCCESS_CREATED);
            Representation rep = new StringRepresentation("Message created",
                    MediaType.APPLICATION_OCTET_STREAM);

            rep.setIdentifier(getRequest().getResourceRef().getIdentifier()
                    + "/" + recipient);
            getResponse().setEntity(rep);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a listing of all registered messages.
     */
    @Override
    public Representation represent(Variant variant) throws ResourceException {
        // Generate the right representation according to its media type.
        if (MediaType.APPLICATION_OCTET_STREAM.equals(variant.getMediaType())) {
            try {
                byte[] bytes = createMessageList();
                Representation representation = new InputRepresentation(new ByteArrayInputStream(bytes), MediaType.APPLICATION_OCTET_STREAM);
                return representation;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private byte[] createMessageList() throws InvalidProtocolBufferException {
        Messages.MessageList.Builder builder = Messages.MessageList.newBuilder();
        for (String recepient : getByteMessages().keySet()) {
            for (byte[] byteMessage : getByteMessages().get(recepient)) {
                Messages.AMessage protoMessage = Messages.AMessage.parseFrom(byteMessage);
                builder.addMessages(protoMessage.toBuilder());
            }
        }
        return builder.build().toByteArray();
    }
}
