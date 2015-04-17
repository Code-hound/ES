package id.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Random;

import javax.jws.*;

import java.io.IOException;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
     * 
     * NÃO UTILIZAR BASE DE DADOS, eliminar a complexidade do IdImpl pois não é preciso ser persistente,
     * os objectos devem ser guardados aqui, num vector (sugerido pelo prof),
     * cada posição vai ter um User com nome, senha e email.
     * 
     * Criar função ---populate--- com dados de teste indicados a seguir, armazenando-os no vector criado:
     * 			http://disciplinas.tecnico.ulisboa.pt/leic-sod/2014-2015/labs/proj/test.html
     */
    
    private void checkConnection() {
        while(conn == null)
            try {
                Class.forName(dbDriver);
                conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            } catch (SQLException e) {
                System.out.println("IdImpl error: couldn't establish connection to database - " + e);
                printSQLExceptions(e);
            } catch (ClassNotFoundException e) {
                System.out.println("IdImpl error: couldn't load sql driver - " + e);
                e.printStackTrace();
                break;
            }
    }

    public String sayHello(String name) {
        return "Hello " + name + "!";
    }

	public void createUser(String userId, String emailAddress)
			throws EmailAlreadyExists_Exception,
			       InvalidEmail_Exception,
			       InvalidUser_Exception,
			       UserAlreadyExists_Exception {
		// TODO Auto-generated method stub
		// Gera senha alfanumerica e armazena em String.
		// Apresenta a senha na consola de serviço.
		
		String id = "";
		String userIdVazio = ""; // FIXME
		String userIdNull = null; // FIXME
		
		// Make sure you're connected. NAO É NECESSARIO!!!
		// Apenas adicionar o User da criaçao no vector e gerar a sua senha (pode ser a letra inicial do nome repetida 3 vezes e um numero inteiro)
		// Assim, seguiamos o exemplo dos dados de teste. Alice -> senha aaa1
        
		checkConnection(); // TODELETE
        
        try {
        	conn.setAutoCommit(false);
        	
        	// Find out if the user exists in the DB
        	String sqlCheckerUser = "SELECT username FROM user WHERE username = ?;"; // FIXME
        	pstmt = conn.prepareStatement(sqlCheckUser);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if(userId.equals(userIdVazio) || userId.equals(userIdNull)) {
            	InvalidUser iu = new InvalidUser();
                String errorMsg = String.format("O utilizador %s não é válido.", userId);
                iu.setMessage(errorMsg);
                iu.setUserId(userId);
                throw new InvalidUser_Exception(errorMsg, iu);
            }
            
            if(!rs.next() != null) {
            	UserAlreadyExists uae = new UserAlreadyExists();
                String errorMsg = String.format("O utilizador %s já existe na base de dados.", userId);
                iu.setMessage(errorMsg);
                iu.setUserId(userId);
                throw new UserAlreadyExists_Exception(errorMsg, uae);
            }
            
            if(!rs.next() != null) {
            	EmailAlreadyExists eae = new EmailAlreadyExists();
                String errorMsg = String.format("O E-Mail %s já existe na base de dados.", emailAddress);
                iu.setMessage(errorMsg);
                iu.setEmailAddress(emailAddress);
                throw new UserAlreadyExists_Exception(errorMsg, eae);
            }
            
            // FALTA O PARSER
            
            /*
        	 * ?PERGUNTA?
        	 * 
        	 * Como implementar o Parser? 
        	 * RETIRAR BASE DE DADOS
        	 * 
        	 */
            
            if(pstmt != null) {
            	pstmt.close();
            }
            
            // Insert a new check into the system
            String sqlEmmitCheck = "INSERT INTO check (userId,emailAddress) VALUE (?,?,?);";
            pstmt = conn.prepareStatement(sqlEmmitCheck, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, userId);
            pstmt.setString(2, emailAddress);
            
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = String.valueOf(rs.getString(1));
            }
            
            
            
            conn.commit();
        } catch (SQLException e) {
        	System.out.println("IdImpl.createUser() error: couldn't execute query - " + e);
            try {
                /* undo all changes to database */
                System.out.println("IdImpl.createUser(): Rollback update");
                conn.rollback();
            } catch (SQLException e2) {
                System.out.println("IdImpl.createUser(): SQL exception while attempting rollback " + e2);
            }
            
            printSQLExceptions(e);
        } finally {
            try {
                if(pstmt != null)
                    pstmt.close();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("IdImpl.createUser() error attempting to wind down transaction: " + e);
                printSQLExceptions(e);
            }
        }
        
        return id;
    }

	public void renewPassword(String userId) throws UserDoesNotExist_Exception {
		// TODO Auto-generated method stub
		// Apresenta nova senha na consola de serviço.
		
		String id = "";
		private int min = 0;
		private int max = 1000;
		
		// Make sure you're connected. RETIRAR
        checkConnection();
        
        // CARE
        
        /*
    	 * ?PERGUNTA?
    	 * 
    	 * Como implementar o random para este caso?
    	 * 
    	 * A senha para Alice, de acordo com os dados de teste, é aaa1.
    	 * Para renovar podemos ir buscar a senha que corresponde ao userId Alice e adicionar 1 à String, ficando aaa11.
    	 * Somar para ficar aaa2 acho mais complicado, uma vez que a senha é armazenada como String.
    	 * 
    	 */
        
        Random rand = new Random();
        int randomNum = rend.nextInt((max - min) + 1) + min;
        
        try {
        	conn.setAutoCommit(false);
        	
        	// Find out if the user exists in the DB
        	String sqlCheckerUser = "SELECT username FROM user WHERE username = ?;"; // FIXME
        	pstmt = conn.prepareStatement(sqlCheckUser);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if(!rs.next() != null) {
            	UserDoesNotExist udne = new UserDoesNotExist();
                String errorMsg = String.format("O utilizador %s não existe na base de dados.", userId);
                iu.setMessage(errorMsg);
                iu.setUserId(userId);
                throw new UserDoesNotExist_Exception(errorMsg, udne);
            }
            
            if(pstmt != null) {
            	pstmt.close();
            }
            
            // Insert a new check into the system
            // FIXME
            String sqlEmmitCheck = "INSERT INTO check (userId) VALUE (?,?,?);";
            pstmt = conn.prepareStatement(sqlEmmitCheck, Statement.RETURN_GENERATED_KEYS);
            
            /*
        	 * ?PERGUNTA?
        	 * 
        	 * Saber se a pass e actualizada assim? cc como e?
        	 *	retirar base de dados
        	 * 
        	 */
            
            pstmt.setString(1, userId + "randomNum");
            
            /*
        	 * 
        	 * 
	    	 * A senha para Alice, de acordo com os dados de teste, é aaa1.
	    	 * Para renovar podemos ir buscar a senha que corresponde ao userId Alice e adicionar 1 à String, ficando aaa11.
	    	 * Somar para ficar aaa2 acho mais complicado, uma vez que a senha é armazenada como String.
	    	 * depois é só imprimir, sim.
        	 * 
        	 */
            
            System.out.println(pstmt.setString(1, userId + "randomNum"));
            
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = String.valueOf(rs.getString(1));
            }
            
            conn.commit();
        } catch (SQLException e) {
        	System.out.println("IdImpl.createUser() error: couldn't execute query - " + e);
            try {
                /* undo all changes to database */
                System.out.println("IdImpl.createUser(): Rollback update");
                conn.rollback();
            } catch (SQLException e2) {
                System.out.println("IdImpl.createUser(): SQL exception while attempting rollback " + e2);
            }
            
            printSQLExceptions(e);
        } finally {
            try {
                if(pstmt != null)
                    pstmt.close();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("IdImpl.createUser() error attempting to wind down transaction: " + e);
                printSQLExceptions(e);
            }
        }
        
        return id;
	}
	
	/*
	 * ?PERGUNTA?
	 * 
	 * Como e que e que devemos apagar? Algo tipo deleteUser de ES?
	 * Vector de objectos User, buscar pelo userId... user=null
	 * 
	 */

	public void removeUser(String userId) throws UserDoesNotExist_Exception {
		// TODO Auto-generated method stub
		
	}

	public byte[] requestAuthentication(String userId, byte[] reserved)
			throws AuthReqFailed_Exception {
		// TODO Auto-generated method stub
		// Confirma correcção dos argumentos
		// Retorna array com 1 byte, valor '(byte) 1'
		// Exception se o utilizador não existe ou password incorrecta.
		
		/*
    	 * ?PERGUNTA?
    	 * 
    	 * Ir buscar ao vector de Users pelo userId e ver se a password corresponde. Nao é preciso buscar nada
    	 * (porque ES ainda nao está ligado a SD)
    	 * 
    	 */
		
		String userpath = getUserPath(userId);
    	// File file = new File(userpath);
    	// float fileSize = file.length();
    	
    	//WARNING: casting float as int may not be safe
    	
    	byte[] content = new byte[(int)fileSize];
    	FileInputStream reader;
    	BufferedInputStream bufferedReader = null;
    	
    	try {
    		AuthReqFailed udne = new AuthReqFailed();
            String errorMsg = String.format("Falha com o user %s ao requisitar autenticação.", userId);
            iu.setMessage(errorMsg);
            iu.setUserId(userId);    		
    		
    		reader = new FileInputStream(file);
    		bufferedReader = new BufferedInputStream(reader);
    		bufferedReader.read(content);
    		bufferedReader.close();
    		
    		throw new AuthReqFailed_Exception(errorMsg, udne);
    	} catch (IOException ex) {
    		System.out.println("Failed IO");
    	}
    	
    	return content;
	}
	
	
	
	// -----> EXTRA <-----
	
	/*
	 * ?PERGUNTA?
	 * 
	 * Perguntar se o que se segue e necessario? remover BD
	 * 
	 */
	
	/*
     * printSQLExceptions - imprime para o ecra informacao detalhada de quaisquer excepcoes de SQL que tenham
     * ocorrido e que ainda nao tenham sido reportados.
     */
    private void printSQLExceptions(SQLException e) {
        /* SQLException specific information */
        while (e != null) {
            System.out.println("SQLException detailed information");
            System.out.println("Message: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("ErrorCode: " + e.getErrorCode());
            e = e.getNextException();
        }
    }

    
    System.out.println("Ola");
}
