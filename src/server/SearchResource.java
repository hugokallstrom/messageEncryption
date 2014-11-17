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
import java.util.ArrayList;

public class SearchResource extends BaseResource {

    String keyword;
    ArrayList<byte[]> results;

    public SearchResource(Context context, Request request, Response response) {
        super(context, request, response);
        try {
            this.keyword = (String) getRequest().getAttributes().get("keyword");
            results = searchMess(keyword);
            checkResults();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void checkResults() {
        if (results != null) {
            getVariants().add(new Variant(MediaType.APPLICATION_OCTET_STREAM));
            setAvailable(true);
        } else {
            setAvailable(false);
        }
    }

    /**
     * Retrieves all messages from all recipients and
     * matches with specified keyword.
     * @param keyword user chosen keyword
     * @return matched messages
     */
    private ArrayList<byte[]> searchMess(String keyword) throws InvalidProtocolBufferException {

        ArrayList<byte[]> result = new ArrayList<byte[]>();

        for(String recipient : getByteMessages().keySet()) {
            for(byte[] byteMessage : getByteMessages().get(recipient)) {
                Messages.AMessage message = Messages.AMessage.parseFrom(byteMessage);
                if(message.getContent().contains(keyword)) {
                    result.add(byteMessage);
                }
            }
        }
        return result;
    }

    @Override
    public Representation represent(Variant variant) throws ResourceException {
        if (MediaType.APPLICATION_OCTET_STREAM.equals(variant.getMediaType())) {
            try {
                if (results != null) {
                    byte[] bytes = createMessageList();
                    Representation representation = new InputRepresentation(new ByteArrayInputStream(bytes), MediaType.APPLICATION_OCTET_STREAM);
                    return representation;
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private byte[] createMessageList() throws InvalidProtocolBufferException {
        Messages.MessageList.Builder builder = Messages.MessageList.newBuilder();
        for (byte[] byteMessage : results) {
            Messages.AMessage message = Messages.AMessage.parseFrom(byteMessage);
            builder.addMessages(message.toBuilder());
        }
        byte[] bytes = builder.build().toByteArray();
        return bytes;
    }
}
