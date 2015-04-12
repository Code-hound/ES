package store.ws;

import javax.xml.ws.Endpoint;

// To confirm correct service publication:
// http://localhost:8080/sd-store-ws/endpoint?WSDL

public class StoreMain {
	public static void main (String[] args) {
		if (args.length < 1) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s url%n", StoreMain.class.getName());
            return;
        }

        String url = args[0];
        Endpoint endpoint = null;
        try {
            endpoint = Endpoint.create(new StoreImpl());

            // publish endpoint
            System.out.printf("Starting %s%n", url);
            endpoint.publish(url);

            // wait
            System.out.println("Awaiting connections");
            System.out.println("Press enter to shutdown");
            System.in.read();

        } catch(Exception e) {
            System.out.printf("Caught exception: %s%n", e);
            e.printStackTrace();

        } finally {
            try {
                if (endpoint != null) {
                    // stop endpoint
                    endpoint.stop();
                    System.out.printf("Stopped %s%n", url);
                }
            } catch(Exception e) {
                System.out.printf("Caught exception when stopping: %s%n", e);
            }
        }

    }
}
