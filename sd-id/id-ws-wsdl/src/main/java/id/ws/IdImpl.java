package id.ws;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

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

import javax.jws.*;

import pt.ulisboa.tecnico.sdis.id.ws.*; // classes generated from WSDL

/*
 * Interface methods:
 * -createUser
 * -renewPassword
 * -removeUser
 * -requestAuthentication
 */

@WebService(
    endpointInterface="pt.ulisboa.tecnico.sdis.id.ws.SDId", 
    wsdlLocation="SD-ID.1_1.wsdl",
    name="SdId", // MESMO NOME D POM.XML done
    portName="SDIdImplPort",
    targetNamespace="urn:pt:ulisboa:tecnico:sdis:id:ws",
    serviceName="SDId"
)

public class IdImpl implements SDId {
	
    private String idUsername;
    private String idPassword;
    private String idEmail;
    
    public ArrayList<ArrayList<String>> listData = new ArrayList<ArrayList<String>>();
    
    
    
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
     * 
     */    
    
    public IdImpl(String idUsername, String idPassword, String idEmail) {
        this.idUsername = idUsername;
        this.idPassword = idPassword;
        this.idEmail = idEmail;
        
        // FIXME
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
     * 
     * Fiquei sem ter a certeza se a funcao checkConnection() e para apagar
     * 
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
		String str = userId + "";
		String userLetter1 = str.substring(0,1);
		int userIndex = listData().size() + 1;
	    String userPassword = userLetter1.toUpperCase() + userLetter1 + userLetter1 + userIndex;
	    
	    String[] auxEmail = emailAddress.split("@");
		String auxEmail1 = String.parseString(auxEmail[0]);
		String auxEmail2 = String.parseString(auxEmail[1]);
		
		if(auxEmail1 != "" || auxEmail1 != "")
			throw new InvalidEmail_Exception;
			
		if(userId != "" || userId != null)
			throw new InvalidUser_Exception;		
		
	    public ArrayList<String> listUser = new ArrayList<String>();
	    
	    //verifica se o user id ou emailAdress ja existem
        for(i = 0; i < listData().size; i++) {
    		
    		if(listUser[i][0] == userId) {
    			throw new UserAlreadyExists_Exception;
    		}
    		
    		if(listUser[i][1] == emailAdress) {
    			throw new EmailAlreadyExists_Exception;
    		}
    		
    	}
    	
    	listUser.add(userId);
    	listUser.add(emailId);
    	listUser.add(userPassword);
    	
    	listData.add(listUser);
	    
		// Make sure you're connected. NAO É NECESSARIO!!!
		// Apenas adicionar o User da criaçao no vector e gerar a sua senha (pode ser a letra inicial do nome repetida 3 vezes e um numero inteiro)
		// Assim, seguiamos o exemplo dos dados de teste. alice -> senha Aaa1
        
        return id;
    }
	
	
	/*		throws UserDoesNotExist_Exception {
		User user = getUsername(userId);
		List<String> usersList = new ArrayList<String>();
		
		for(User user : user) {
			usersList.add(user.getName());
		}
		
		return usersList;
	}*/
	
	/*
	public List<String> listPassword(String userPassword)
			throws UserDoesNotExist_Exception {
		List<String> passList = new ArrayList<String>();
		
		private int userCont;
		
		for(i = 0; ) {
			passList.add(user.getPassword());
		}
		
		return usersList;
	}
	*/

	public void renewPassword(String userId)
			throws UserDoesNotExist_Exception {
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
	
	private String getUsername(String user) {
		return (String) username;
	}
}
