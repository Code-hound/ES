package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class User extends User_Base {
    
	public User(String userName,String name, String password) {
        super();
        set_username(userName);
        set_name(name);
        set_password(password);
    }
   
    public User createUser(String userName,String name, String password){
    	//if(!get_username().equals("root"))
    		//throws InvalidAccessException
    	return new User(userName, name, password);	
    }

    public String toString(){
    	return "UserName: "+get_username() +" Name: "+get_name()+" Password: "+ get_password();
    }
   
    public Element exportToXML(){
    	Element element = new Element("user");
    	element.setAttribute("username",get_username());
    	element.setAttribute("name",get_name());
    	element.setAttribute("password",get_password());
    	
    	return element;
    }
}
