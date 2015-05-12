package pt.ulisboa.tecnico.sdis.id.ws.impl;

import java.io.IOException;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;


@WebService(targetNamespace = "urn:pt:ulisboa:tecnico:ws:test")
public class TestControl {

	// Endpoint management

    private String wsURL = null;
    private Endpoint endpoint = null;

    /** output option **/
    private boolean verbose = true;

    public boolean isVerbose() {
        return verbose;
    }
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }


    public TestControl(String wsURL) {
        if (wsURL == null)
            throw new NullPointerException("Web Service URL cannot be null!");
        this.wsURL = wsURL;
    }

    public void start() {
        try {
            // publish endpoint
            endpoint = Endpoint.create(this);
            if (verbose)
                System.out.printf("Starting %s%n", wsURL);
            endpoint.publish(wsURL);

        } catch(Exception e) {
            if (verbose) {
                System.out.printf("Caught exception when starting: %s%n", e);
                e.printStackTrace();
            }

        }
    }

    public void awaitConnections() {
        if (verbose) {
            System.out.println("Awaiting connections");
            System.out.println("Press enter to shutdown");
        }
        try {
            System.in.read();
        } catch(IOException e) {
            if (verbose)
                System.out.printf("Caught i/o exception when awaiting requests: %s%n", e);
        }
    }

    public void stop() {
        try {
            if (endpoint != null) {
                // stop endpoint
                endpoint.stop();
                if (verbose)
                    System.out.printf("Stopped %s%n", wsURL);
            }
        } catch(Exception e) {
            if (verbose)
                System.out.printf("Caught exception when stopping: %s%n", e);
        }
    }


	// test control implementation

	public void reset() {
		System.out.println("Resetting data for tests...");
		IdImpl.reset();
	}

	public void terminate() {
		System.out.println("Received shutdown command. Shutting the server down...");
		System.exit(0);
	}

}
