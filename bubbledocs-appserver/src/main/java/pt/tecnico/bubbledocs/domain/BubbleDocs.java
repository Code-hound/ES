package pt.tecnico.bubbledocs.domain;

public class BubbleDocs extends BubbleDocs_Base {
    
    public BubbleDocs() {
        super();
        addUsers(new User("root", "root", "rootroot"));
    }
    
}
