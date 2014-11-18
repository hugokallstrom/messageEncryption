package server;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.restlet.Application;
import org.restlet.Router;
import org.restlet.Restlet;

public class MessageApplication extends Application {

    private final Map<String, ArrayList<byte[]>> byteMessages;

    public MessageApplication() {
        byteMessages = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized Restlet createRoot() {
        Router router = new Router(getContext());
        router.attach("/messages", MessageResource.class);
        router.attach("/messages/{id}", IdResource.class);
        router.attach("/messages/s/{keyword}", SearchResource.class);
        return router;
    }

    public Map<String, ArrayList<byte[]>> getByteMessages() {
        return byteMessages;
    }

}
