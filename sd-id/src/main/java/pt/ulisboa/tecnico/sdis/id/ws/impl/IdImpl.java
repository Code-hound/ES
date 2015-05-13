// classes generated from WSDL
package pt.ulisboa.tecnico.sdis.id.ws.impl;

import pt.ulisboa.tecnico.sdis.id.ws.crypto.SymCrypto;
import pt.ulisboa.tecnico.sdis.id.ws.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Hashtable;
import java.awt.RenderingHints.Key;
import java.io.IOException;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.jws.*;
import javax.naming.*;
import javax.naming.directory.*;
import javax.xml.ws.Endpoint;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import java.lang.reflect.Array;

import javax.security.auth.login.*;
import javax.security.auth.Subject;

import pt.ulisboa.tecnico.sdis.id.ws.*;
import ws.handler.HeaderHandler;
import ws.uddi.UDDINaming;

/*
 * Interface methods:
 * -createUser
 * -renewPassword
 * -removeUser
 * -requestAuthentication
 */

@SuppressWarnings("unused")
@WebService(
    endpointInterface="pt.ulisboa.tecnico.sdis.id.ws.SDId", 
    wsdlLocation="SD-ID.1_1.wsdl",
    name="SdId",
    portName="SDIdImplPort",
    targetNamespace="urn:pt:ulisboa:tecnico:sdis:id:ws",
    serviceName="SDId"
)
@HandlerChain(
		file="/handler-chain.xml"
)

public class IdImpl implements SDId {
	
	// Endpoint management

    private String uddiURL = null;
    private String wsName = null;
    private String wsURL = null;

    private Endpoint endpoint = null;
    private UDDINaming uddiNaming = null;
	
    private static String idUsername;
    private static String idPassword;
    private static String idEmail;
    
    private String[] args;

    private ArrayList<String[]> listData = new ArrayList<String[]>();
    
    /** output option **/
    private boolean verbose = true;
    
    /*
     * 
     * Integracao da chave no proj
     * 
     * @since 1.1
     *
     */
    @Resource
    private WebServiceContext webServiceContext;
    
    public static void main(String[] args) throws Exception {
    	// check args
        if (args.length < 2) {
            System.err.println("args: (r/w) (key-file)");
            return;
        }
        
        final String mode = args[0];
        final String keyPath = args[1];

        if (mode.startsWith("w")) {
        	System.out.println("Generate and save keys");
        	write(keyPath);
        } else {
        	System.out.println("Load keys");
        	read(keyPath);
        }
        
        System.out.println("Done.");
    }
    
    public static void write(String keyPath) throws Exception {
        // get a TripleDES private key
        System.out.println("Generating TripleDES key ..." );
        KeyGenerator keyGen = KeyGenerator.getInstance("TripleDES");
        keyGen.init(168);
        SecretKey key = keyGen.generateKey();
        System.out.println( "Finish generating TripleDES key" );
        byte[] encoded = key.getEncoded();
        System.out.println("Key:");
        System.out.println(printHexBinary(encoded));

        System.out.println("Writing key to '" + keyPath + "' ..." );

        FileOutputStream fos = new FileOutputStream(keyPath);
        fos.write(encoded);
        fos.close();
    }

    private static char[] printHexBinary(byte[] encoded) {
		// TODO Auto-generated method stub
		return null;
	}

	public static SecretKey read(String keyPath) throws Exception {
        System.out.println("Reading key from file " + keyPath + " ...");
        FileInputStream fis = new FileInputStream(keyPath);
        byte[] encoded = new byte[fis.available()];
        fis.read(encoded);
        fis.close();

		DESKeySpec keySpec = new DESKeySpec(encoded);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("TripleDES");
		SecretKey key = keyFactory.generateSecret(keySpec);
		return key;
    }

    public boolean isVerbose() {
        return verbose;
    }
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    /** constructor with provided UDDI location, WS name, and WS URL */
    public IdImpl(String uddiURL, String wsName, String wsURL) {
        this.uddiURL = uddiURL;
        this.wsName = wsName;
        this.wsURL = wsURL;
    }

    /** constructor with provided web service URL */
    public IdImpl(String wsURL) {
        if (wsURL == null)
            throw new NullPointerException("Web Service URL cannot be null!");
        this.wsURL = wsURL;
    }
    
    public void start() throws Exception {
        try {
            // publish endpoint
            endpoint = Endpoint.create(this);
            if (verbose) {
                System.out.printf("Starting %s%n", wsURL);
            }
            endpoint.publish(wsURL);
        } catch(Exception e) {
            endpoint = null;
            if (verbose) {
                System.out.printf("Caught exception when starting: %s%n", e);
                e.printStackTrace();
            }
            throw e;
        }
        try {
			// publish to UDDI
			if (uddiURL != null) {
			    if (verbose) {
				    System.out.printf("Publishing '%s' to UDDI at %s%n", wsName, uddiURL);
				}
				uddiNaming = new UDDINaming(uddiURL);
				uddiNaming.rebind(wsName, wsURL);
			}
        } catch(Exception e) {
            uddiNaming = null;
            if (verbose) {
                System.out.printf("Caught exception when binding to UDDI: %s%n", e);
                e.printStackTrace();
            }
            throw e;
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
            if (verbose) {
                System.out.printf("Caught i/o exception when awaiting requests: %s%n", e);
            }
        }
    }
    
    public void stop() {
        try {
            if (endpoint != null) {
                // stop endpoint
                endpoint.stop();
                if (verbose) {
                    System.out.printf("Stopped %s%n", wsURL);
                }
            }
        } catch(Exception e) {
            if (verbose) {
                System.out.printf("Caught exception when stopping: %s%n", e);
            }
        }
		try {
			if (uddiNaming != null) {
				// delete from UDDI
				uddiNaming.unbind(wsName);
				if (verbose) {
				    System.out.printf("Deleted '%s' from UDDI%n", wsName);
				}
			}
		} catch (Exception e) {
		    if (verbose) {
			    System.out.printf("Caught exception when unbinding: %s%n", e);
			}
		}
    }
    
    /**
     * 
     * SD-ID IMPLEMENTATION
     *
     * Construtor da classe.
     * 
     * @param idUsername o username do utilizador com permissoes necessarias.
     * @param idPassord a password desse utilizador.
     * @param idEmail o email desse utilizador.
     * 
     * @author: Francisco Maria Calisto
     * @author: Joao Pedro Zeferino
     * 
     */
    
    public static Map<String, byte[]> users = new LinkedHashMap<String, byte[]>();

    
    /*
     * From WSDL: <!-- Creates a new user. A new random password is generated
     * for the new user, which should be delivered to the user through some
     * secure channel. Faults: user id already exists, email address already
     * registered to other user, invalid email address -->
     */
    
    public void createUser(String userId, String emailAddress)
			throws EmailAlreadyExists_Exception,
			       InvalidEmail_Exception,
			       InvalidUser_Exception,
			       UserAlreadyExists_Exception {
    	MessageContext messageContext = webServiceContext.getMessageContext();
    	if (userId == null || userId.length() == 0)
			throw new InvalidUser_Exception("Invalid username.", new InvalidUser());
    	
	    String[] auxEmail = emailAddress.split("@");
	    if(auxEmail.length!=2 || auxEmail[0]==null || auxEmail[1]==null)
	    	throw new InvalidEmail_Exception("Invalid email", new InvalidEmail());
			String auxEmail1 = auxEmail[0];
			String auxEmail2 = auxEmail[1];
		//if(auxEmail1.equals("") || auxEmail2.equals(""))
		
	    //verifica se o user id ou emailAdress ja existem
        for(int i = 0; i < listData.size(); i++) {
    		if(userId.equals(listData.get(i)[0])) {
    			throw new UserAlreadyExists_Exception
    			("Invalid user ID", new UserAlreadyExists());
    		}
    		if(emailAddress.equals(listData.get(i)[1])) {
    			throw new EmailAlreadyExists_Exception
    			("Invalid email", new EmailAlreadyExists());
    		}
    	}
    	String[] listUser = new String[3];
    	listUser[0]=userId;
    	listUser[1]=emailAddress;
    	listData.add(listUser);
    	//byte[] passwordEncoded = null;
    	String passwordEncoded = null;
    	String password = "";
    	String password64 = "";
    	try{
    		SymCrypto cryptographer = new SymCrypto();
    		PasswordGenerator passGen = new PasswordGenerator();
    		for (int i=0; i<listData.size(); i++) {
    			if(userId.equals(listData.get(i)[0])) {
    				listData.get(i)[2] = passGen.getNewPassword();
    				password = listData.get(i)[2];
    				passwordEncoded = cryptographer.encrypt(listData.get(i)[2]);
    				break;
    			}
    		}
    		messageContext.put(HeaderHandler.getPasswordProperty(), 
    				passwordEncoded);
    	} catch (Exception e) {
			System.out.println("Failed");
		}
    }

	
	/*
     * From WSDL: <!-- Renews the user's password, replacing the previous
     * password with a new randomly-generated password. The new password is
     * delivered to the user through some secure channel. Faults: user id is not
     * associated with any registered user. -->
     */
	
	public void renewPassword(String userId)
			throws UserDoesNotExist_Exception {
		
		String oldPass;
		PasswordGenerator passGen = new PasswordGenerator();
		for (int i=0; i<listData.size(); i++) {
			if(userId.equals(listData.get(i)[0])) {
				listData.get(i)[2] = passGen.getNewPassword();
				//oldPass = listData.get(i)[2];
				//listData.get(i)[2] = oldPass + oldPass.substring(3,4);
				//System.out.println(listData.get(i)[2]);
				return;
			}
		}
		throw new UserDoesNotExist_Exception
			("User does not exist", new UserDoesNotExist());
	}

	
	/*
     * From WSDL: <!-- Removes the user from the system. Faults: user id is not
     * registered in the system -->
     */
	
	public void removeUser(String userId)
			throws UserDoesNotExist_Exception {
		// TODO Auto-generated method stub
			
		for(int i = 0; i < listData.size(); i++) {
			if(userId.equals(listData.get(i)[0])) {
				listData.remove(i);
				return ;
			}
		}
		throw new UserDoesNotExist_Exception
			("User does not exist", new UserDoesNotExist());
	}

	
   /*
    * From WSDL: <!-- Requests user authentication. A correct request should be
    * issued by a client representing the user indicated in the userId
    * argument. A user authentication request carries a second argument of type
    * byte[] and returns credentials that are necessary to complete
    * authentication, also as byte[]. The exact definition of what information
    * the arguments and return carry is determined by the actual authentication
    * protocol being used. Fault: Authentication request failed on the server
    * side. -->
    */
    
	public byte[] requestAuthentication(String userId, byte[] reserved)
			throws AuthReqFailed_Exception {
		
		for(int i = 0; i < listData.size(); i++) {
			if(userId.equals(listData.get(i)[0]) 
						&& reserved.toString().equals(listData.get(i)[2])) {
					byte [] response = new byte[1];
					response[0] = '1';
					
					return response;
			}
		}
		
		
		
		throw new AuthReqFailed_Exception("Autentication Failed", new AuthReqFailed());
	}
	
	
	// for testing

    static void reset() {
        users.clear();
        // as specified in:
        // http://disciplinas.tecnico.ulisboa.pt/leic-sod/2014-2015/labs/proj/test.html
        users.put("alice", "Aaa1".getBytes());
        users.put("bruno", "Bbb2".getBytes());
        users.put("carla", "Ccc3".getBytes());
        users.put("duarte", "Ddd4".getBytes());
        users.put("eduardo", "Eee5".getBytes());
    }

	/**
	 * 
	 * The application must supply a PrivilegedAction that is to be run
	 * inside a Subject.doAs() or Subject.doAsPrivileged().
	 * 
	 */
	
	public IdImpl(String[] origArgs) {
		this.args = (String[])origArgs.clone();
	}
	
	public Object run() {
		performIdImpl(args);
		return null;
	}
	
	private static void performIdImpl(String[] args) {
		String dn;
		
		// Set up environment for creating initial context
		Hashtable<String, String> env = new Hashtable<String, String>(11); //TODO
		
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		
		// Must use fully qualified hostname
		env.put(Context.PROVIDER_URL, 
		        "ldap://ldap.jnditutorial.org:389/o=JndiTutorial");
	
		// Request the use of the "SD-ID" mechanism
		// Authenticate by using already established Kerberos credentials
		env.put(Context.SECURITY_AUTHENTICATION, "SD-ID");
		
		// Optional first argument is comma-separated list of auth, auth-int, 
		// auth-conf
		if (args.length > 0) {
		    env.put("javax.security.sasl.qop", args[0]);
		    dn = args[1];
		} else {
		    dn = "";
		}
	    
		try {
		    /* Create initial context */
		    DirContext ctx = new InitialDirContext(env);
	
		    System.out.println(ctx.getAttributes(dn));
	
		    // do something useful with ctx
	
		    // Close the context when we're done
		    ctx.close();
		} catch (NamingException e) {
		    e.printStackTrace();
		}
	}
	public static String getIdUsername() {
		return idUsername;
	}
	public static void setIdUsername(String idUsername) {
		IdImpl.idUsername = idUsername;
	}
	public static String getIdPassword() {
		return idPassword;
	}
	public static void setIdPassword(String idPassword) {
		IdImpl.idPassword = idPassword;
	}
	public static String getIdEmail() {
		return idEmail;
	}
	public static void setIdEmail(String idEmail) {
		IdImpl.idEmail = idEmail;
	}
	
	/*
	 * 
	 * This is a Java Web Service that supports asynchronous operations.
     *
     * The code is identical to other contract-first Web Services.
     *
     * The service is defined by the Java code with annotations
     * (code-first approach, also called bottom-up approach).
     *
     * The service runs in a standalone HTTP server.
	 * 
	 */
	
	public String echo(String name) throws EchoException {
		
		// print current thread and object instance executing the operation
        System.out.printf("%s %s>%n    ", Thread.currentThread(), this);
        System.out.printf("echo(%s)%n", name);
        
        // very complicated task. Takes a long time...
        nap(3);

        if (name.length() == 0) {
            throw new EchoException("Name is too short!");
        } else {
            return "Echo " + name;
        }
		
	}
	
	public String fastEcho(String name) throws EchoException {
        // print current thread and object instance executing the operation
        System.out.printf("%s %s>%n    ", Thread.currentThread(), this);
        System.out.printf("fastEcho(%s)%n", name);

        if (name.length() == 0) {
            throw new EchoException("Name is too short!");
        } else {
            return "Fast echo " + name;
        }
    }
	
	private void nap(int seconds) {
        try {
            System.out.printf("%s %s>%n    ", Thread.currentThread(), this);
            System.out.printf("Sleeping for %d seconds...%n", seconds);

            Thread.sleep(seconds*1000);

            System.out.printf("%s %s>%n    ", Thread.currentThread(), this);
            System.out.printf("Woke up!%n");

        } catch(InterruptedException e) {
            System.out.printf("%s %s>%n    ", Thread.currentThread(), this);
            System.out.printf("Caught exception: %s%n", e);
        }
    }
	
}
