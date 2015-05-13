package id.cli;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import pt.ulisboa.tecnico.bubbledocs.testutils.TestServerManager;

import pt.ulisboa.tecnico.sdis.id.cli.IdClient;
import pt.ulisboa.tecnico.sdis.id.cli.IDClientException;

/**
 * Integration Test suite abstract class
 */
public class AbstractIdIT {

    private static final String TEST_PROP_FILE = "/test.properties";

    private static Properties PROPS;
    protected static IdClient ID_CLIENT;

    @BeforeClass
    public static void oneTimeSetup() throws Exception {
        PROPS = new Properties();
        try {
            PROPS.load(AbstractIdIT.class.getResourceAsStream(TEST_PROP_FILE));
        } catch (IOException e) {
            final String msg = String.format(
                    "Could not load properties file {}", TEST_PROP_FILE);
            System.out.println(msg);
            throw e;
        }
        String uddiEnabled = PROPS.getProperty("uddi.enabled");
        String uddiURL = PROPS.getProperty("uddi.url");
        String wsName = PROPS.getProperty("ws.name");
        String wsURL = PROPS.getProperty("ws.url");

        if ("true".equalsIgnoreCase(uddiEnabled)) {
            ID_CLIENT = new IdClient(uddiURL, wsName);
        } else {
            ID_CLIENT = new IdClient(wsURL);
        }
        ID_CLIENT.setVerbose(true);

        System.out.println("Resetting ID Web Service state...");
        String url = ID_CLIENT.getWsURL() + "/test";
        TestServerManager.resetServerState(url);
    }

    @AfterClass
    public static void cleanup() {
        ID_CLIENT = null;
    }

}
