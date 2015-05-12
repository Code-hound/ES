package pt.ulisboa.tecnico.sdis.store.cli.test;

import store.cli.StoreClient;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class StoreClientMock extends MockUp<StoreClient> {
	
	@Mocked
	String[] endpointAddresses;
	
	@Mock
	private void uddiLookup() {
		this.endpointAddresses = new String[]{
				"http://localhost:8080/store-ws/endpoint",
				"http://localhost:8079/store-ws/endpoint",
				"http://localhost:8080/store-ws/endpoint"};
	}
	
	
}
