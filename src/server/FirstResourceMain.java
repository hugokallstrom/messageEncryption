package server;

import org.restlet.Component;
import org.restlet.data.Protocol;

public class FirstResourceMain {

    public static void main(String[] args) {
        try {

            Component component = new Component();
            component.getServers().add(Protocol.HTTP, 8182);
            component.getDefaultHost().attach(new FirstResourceApplication());
            component.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

