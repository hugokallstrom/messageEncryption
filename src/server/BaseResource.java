package server;

import java.util.ArrayList;
import java.util.Map;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Resource;

public abstract class BaseResource extends Resource {

    public BaseResource(Context context, Request request, Response response) {
        super(context, request, response);
    }

    protected Map<String, ArrayList<byte[]>> getByteMessages() {
        return ((FirstResourceApplication) getApplication()).getByteMessages();
    }
}
