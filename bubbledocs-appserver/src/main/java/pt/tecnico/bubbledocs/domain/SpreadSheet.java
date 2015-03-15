package pt.tecnico.bubbledocs.domain;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

public class SpreadSheet extends SpreadSheet_Base {
    
    public SpreadSheet(User user, int id, String name, LocalDate date, int rows, int columns) {
    	super();
        setUser(user);
        set_id(id);
        set_spreadSheetName(name);
        set_date(date);
        
        if(getCellsSet().isEmpty())
        	getCellsSet().add(new Cell(1,1));
        for(int i=2; i == rows; i++){
        	for(int j =2; j == columns;j++){
        		getCellsSet().add(new Cell(i,j));
        	}
        }
        
    }
    
    public List<User> getReadOnlyUser(){
    	List<User> users = new ArrayList<User>();
    	for(Access a : getDocAccessSet()){
    		if(a.get_permission() == 2){
    			users.add(a.getUser());
    		}
    	}
    	return users;
    }
    
    public List<User> getReadWriteUserOnly(){
    	List<User> users = new ArrayList<User>();
    	/*User u = getCreator();
    	users.add(u);
    	*/
    	for(Access a : getDocAccessSet()){
    		if(a.get_permission() == 3){
    			users.add(a.getUser());
    		}
    	}
    	return users;
    }
    
    public List<User> getAccessUsers(){
    	List<User> users = new ArrayList<User>();
    	for(Access a : getDocAccessSet()){
    			users.add(a.getUser());
    	}
    	return users;
    }

    public void addContent(int row, int column, Content c){
    	for(Cell cell : getCellsSet()){
    		if(cell.get_cellRow() == row && cell.get_cellColumn()== column){
    			//try{
    			cell.setContent(c);
    			//}catch(ProtectedCellException e)
    		}
    	}
    }

    public void removeContent(int row, int column){
    	for(Cell cell : getCellsSet()){
    		if(cell.get_cellRow() == row && cell.get_cellColumn()== column){
    			//try{
    			cell.setContent(null);
    			//}catch(ProtectedCellException e)
    		}
    	}
    }
    
    public String getCellDescription(int row, int column){
    	String description="";
    	for(Cell cell : getCellsSet()){
    		if(cell.get_cellRow() == row && cell.get_cellColumn()== column){
    			//try{
    			description+=row+";"+column;
    			description+=cell.toString();
    			//}catch(ProtectedCellException e)
    		}
    	}
    	return description;
    }

    
    
}
