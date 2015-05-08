package pt.ulisboa.tecnico.sdis.store.cli.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.registry.JAXRException;

import pt.ulisboa.tecnico.sdis.store.ws.CapacityExceeded_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocAlreadyExists;
import pt.ulisboa.tecnico.sdis.store.ws.DocAlreadyExists_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocDoesNotExist_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocUserPair;
import pt.ulisboa.tecnico.sdis.store.ws.SDStore;
import pt.ulisboa.tecnico.sdis.store.ws.SDStore_Service;
import pt.ulisboa.tecnico.sdis.store.ws.UserDoesNotExist_Exception;
import store.cli.StoreClientException;
import store.cli.StoreClient;

import org.junit.After;
import org.junit.Before;

import store.cli.StoreClientException;

public class StoreClientRealTest {
	
	private static final String uddiURL = "http://localhost:8081";
	private static final String wsName = "SD-Store";
	private static final int multiplicity = 3;
	private static StoreClient client;
	
	private static final String USERNAME = "orwell";
	private static final String DOC_ID1 = "1984";
	private static final String DOC_ID2 = "Animal Farm";
	private static final String DOC_ID3 = "Homage To Catalonia";
	private static final String DOC_ID4 = "Coming Up For Air";
	private static final String CONTENT = 
			"Word had gone round during the day that old Major, the prize Middle White"
			+ " boar, had had a strange dream on the previous night and wished to "
			+ "communicate it to the other animals.";
	
	@Before
	public void setup() 
			throws JAXRException, StoreClientException {
		client = new StoreClient(uddiURL, wsName, multiplicity);
	}
	
	@After
	public void teardown() {
		
	}
	
	@Test
	public void printAddresses() {
		
	}
}
