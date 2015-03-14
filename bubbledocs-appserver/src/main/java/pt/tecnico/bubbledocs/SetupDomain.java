package pt.tecnico.bubbledocs;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;

import pt.ist.fenixframework.Config;

import pt.tecnico.bubbledocs.domain.BubbleDocs;

public class SetupDomain {

    @Atomic
    public static void main(String[] args){
    	populateDomain();
    }
    
    static void populateDomain(){
    	BubbleDocs bubbledocs = BubbleDocs.getInstance();
	
		try{
		    System.out.println("Error! Business rule violated!"); // <-- ATENCAO
		}catch (pt.tecnico.BUBBLEDOCS.exception.NameAlreadyExistsException nae){
		    System.out.println("Could not add two equals <var1> to the same <var2>"); // <-- ATENCAO
		}
    }
}
