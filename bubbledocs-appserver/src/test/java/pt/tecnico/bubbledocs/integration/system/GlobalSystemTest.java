package pt.tecnico.bubbledocs.integration.system;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pt.tecnico.bubbledocs.integration.*;
import pt.tecnico.bubbledocs.integration.component.BubbleDocsIntegratorTest;

public class GlobalSystemTest extends BubbleDocsIntegratorTest {
	
	private String rootToken;
	private String userToken;
	private String PASSWORD;
	private String USERNAME = "andre32";
	private String NAME = "Andre Rodrigues";
	private String EMAIL = "andre@gmail.com";
	
	@Before
	public void setup() {
		rootToken = addUserToSession("root");
	}
	
	@After
	public void teardown() {
		removeUserFromSession(rootToken);
	}
	
	@Test
	public void success() {
		CreateUserIntegrator integratorCreateUser = 
				new CreateUserIntegrator(rootToken, USERNAME, EMAIL, NAME);
		integratorCreateUser.execute();
		PASSWORD = integratorCreateUser.getPassword();
		LoginUserIntegrator integratorLogin = 
				new LoginUserIntegrator(USERNAME, PASSWORD);
		
		CreateSpreadSheetIntegrator integratorcreateSpreadSheet = 
				new CreateSpreadSheetIntegrator(userToken, NAME, 10, 7);
	}
}
