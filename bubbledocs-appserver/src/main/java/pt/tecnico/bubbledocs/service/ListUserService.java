package pt.tecnico.bubbledocs.service;

import java.util.ArrayList;
import java.util.List;

import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.BubbleDocs;

/*
 * A ANALISAR <------------- TODO
 * 
 * Talvez esta parte e este ficheiros de lista
 * nao sejam necessarios para o nosso projecto
 * 
 * @author: Francisco Maria Calisto
 * 
 */

public class ListUserService extends BubbleDocsService {
	
	private List<String> registeredUser;
	
	public ListUserService() {
		//NTODO <-- VAZIO
	}
	
	public final void dispatch() {
		BubbleDocs bd = getBubbleDocs();
		registeredUser = new ArrayList<>();
		
		for (User u : bd.getUsersSet()) {
			registeredUser.add(u.get_username());
		}
		
	}
	
	public final List<String> getResult() {
		return registeredUser;
	}
	
}