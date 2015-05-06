package pt.ulisboa.tecnico.sdis.store.cli.test;

import javax.xml.registry.JAXRException;

import org.junit.After;
import org.junit.Before;

import store.cli.StoreClientException;
import store.cli.StoreFrontend;

public class StoreFrontendRealTest {
	
	private static final String uddiURL = "http://localhost:8081";
	private static final String wsName = "SD-Store";
	private static final int multiplicity = 3;
	private static StoreFrontend frontend;
	
	@Before
	public void setup() 
			throws JAXRException, StoreClientException {
		frontend = new StoreFrontend(uddiURL, wsName, multiplicity);
	}
	
	@After
	public void teardown() {
		
	}
}
