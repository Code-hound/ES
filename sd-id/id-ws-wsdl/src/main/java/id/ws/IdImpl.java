package id.ws;

import java.sql.Connection;

import javax.jws.*;

import pt.ulisboa.tecnico.sdis.id.ws.*; // classes generated from WSDL

@WebService(
    endpointInterface="pt.ulisboa.tecnico.sdis.id.ws.SDId", 
    wsdlLocation="SD-ID.1_1.wsdl",
    name="SdId",//MESMO NOME D POM.XML done
    portName="SDIdImplPort",
    targetNamespace="urn:pt:ulisboa:tecnico:sdis:id:ws",
    serviceName="SDId"
)

public class IdImpl implements SDId {
	
	private Connection conn = null;
    private String dbDriver;
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
   
    /**
     * Construtor da classe.
     * 
     * @param dbDriver recebe o driver especifico que utilizaremos para comunicar com a BD da aplicacao.
     * @param dbUrl a localizacao da BD.
     * @param dbUsername o username do utilizador com permissoes necessarias para manipular a BD.
     * @param dbPassword a password desse utilizador.
     */    
    
    public IdImpl(String dbDriver, String dbUrl, String dbUsername, String dbPassword) {
        this.dbDriver = dbDriver;
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        
        checkConnection();
    }
    
    
    /*
     * checkConnection - verifica que a ligacao a base de dados se encontra aberta. Caso por alguma razao
     * se tenha fechado, tenta reestabelecer a ligacao.
     */
    
    private void checkConnection() {
        while(conn == null)
            try {
                Class.forName(dbDriver);
                conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            } catch (SQLException e) {
                System.out.println("ChequeRefeicaoImpl error: couldn't establish connection to database - " + e);
                printSQLExceptions(e);
            } catch (ClassNotFoundException e) {
                System.out.println("ChequeRefeicaoImpl error: couldn't load sql driver - " + e);
                e.printStackTrace();
                break;
            }
    }
    
    /*
     * 
     * Talvez tenha que adicionar o campo emial.
     * 
     */
    
    @Override
    public String transmit(String user, boolean endorsable) throws UserDoesNotExist_Exception {
    	String id = "";
    	
    	PrepareStatement pstmt = null;
    	
    	// Make sure you're connected
        checkConnection();
        
        try {
        	conn.setAutoCommit(false);
        	
        	// Find out if the user exists in the DB
        	String sqlCheckerUser = "SELECT name FROM username WHERE name = ?;"; // MAY FIXME
        	pstmt = conn.prepareStatement(sqlCheckUser);
            pstmt.setString(1, titular);
            ResultSet rs = pstmt.executeQuery();
            
            if(!rs.next() != null) {
            	UserDoesNotExist udne = new UserDoesNotExist();
                String errorMsg = String.format("O utilizador %s não existe na base de dados.", user);
                udne.setMessage(errorMsg);
                udne.setUser(user);
                throw new UserDoesNotExist_Exception(errorMsg, udne);
            }
            
            if(pstmt != null) {
            	pstmt.close();
            }
            
            // Insert a new check into the system
            String sqlEmmitCheck = "INSERT INTO check (user,endorsable) VALUE (?,?,?);";
            pstmt = conn.prepareStatement(sqlEmmitCheck, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user);
            pstmt.setBoolean(2, endorsable);
        }
    }

    public String sayHello(String name) {
        return "Hello " + name + "!";
    }

	public void createUser(String userId, String emailAddress)
			throws EmailAlreadyExists_Exception, InvalidEmail_Exception,
			InvalidUser_Exception, UserAlreadyExists_Exception {
		// TODO Auto-generated method stub
		// Gera senha alfanumerica e armazena em String.
		// Apresenta a senha na consola de serviço.
		
	}

	public void renewPassword(String userId) throws UserDoesNotExist_Exception {
		// TODO Auto-generated method stub
		// Apresenta nova senha na consola de serviço.
		
	}

	public void removeUser(String userId) throws UserDoesNotExist_Exception {
		// TODO Auto-generated method stub
		
	}

	public byte[] requestAuthentication(String userId, byte[] reserved)
			throws AuthReqFailed_Exception {
		// TODO Auto-generated method stub
		// Confirma correcção dos argumentos
		// Retorna array com 1 byte, valor '(byte) 1'
		// Exception se o utilizador não existe ou password incorrecta.
		return null;
	}

}
