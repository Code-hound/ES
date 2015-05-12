package pt.ulisboa.tecnico.sdis.store.cli.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.integration.junit4.JMockit;
import static mockit.Deencapsulation.*;
import pt.ulisboa.tecnico.sdis.store.ws.DocUserPair;
import pt.ulisboa.tecnico.sdis.store.ws.SDStore;
import pt.ulisboa.tecnico.sdis.store.ws.SDStore_Service;
import store.cli.StoreFrontend;
import store.ws.handler.HeaderHandler;

@RunWith(JMockit.class)
public class StoreFrontendMockTest {
	
	private static String goodContent = "When I was young coach called me The Tiger";
	private static String badContent = "I always had a knack for the danger";
	private static String oldTime;
	private static String newTime;
	ArrayList<SDStore> endpoints = new ArrayList<SDStore>();
	
	@Before
	public void setup() {
		newTime = new DateTime().toString();
		oldTime = new DateTime(newTime).minusMinutes(5).toString();
	}
	
	@Test
	public void mockFrontend_OneWrongValues(
			@Injectable final Map<String, Object> requestContext,
			@Mocked final SDStore endpoint,
			@Mocked final DocUserPair pair)
			throws Exception {
		
		endpoints.add(endpoint);
		endpoints.add(endpoint);
		endpoints.add(endpoint);
		StoreFrontendMock frontend = new StoreFrontendMock();
		frontend.setEndpoints(endpoints);
		
		new NonStrictExpectations(frontend) {{
			endpoint.load(pair); 
				returns (badContent.getBytes(), goodContent.getBytes(), goodContent.getBytes());
			frontend.getTime();
				returns(oldTime, newTime, newTime);
			frontend.getID();
				returns("1", "1", "1");
		 }};
	}
	
	@Test
	public void mockFrontend_TwoWrongValues(
			@Injectable final Map<String, Object> requestContext,
			@Mocked final SDStore endpoint,
			@Mocked final DocUserPair pair)
			throws Exception {
		
		endpoints.add(endpoint);
		endpoints.add(endpoint);
		endpoints.add(endpoint);
		StoreFrontendMock frontend = new StoreFrontendMock();
		frontend.setEndpoints(endpoints);
		
		 new NonStrictExpectations(frontend) {{
			endpoint.load(pair); 
				returns (badContent.getBytes(), goodContent.getBytes(), badContent.getBytes());
			frontend.getTime();
				returns(oldTime, newTime, oldTime);
			frontend.getID();
				returns("1", "1", "1");
		 }};
		 
		 byte[] loadedContent = frontend.load(pair);
		 assertEquals(new String(goodContent), new String(loadedContent));
	}
}
