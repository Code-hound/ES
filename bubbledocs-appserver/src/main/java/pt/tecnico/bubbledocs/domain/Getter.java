 package pt.tecnico.bubbledocs.domain;

public class Getter{
	public static int visit (Cell cell) {
		//try{
		Content c=cell.getContent();
		return 	c.getContentValue();
		//}catch(InvalidValueException){}
	}

	public static int visit (Literal literal) {
		return literal.get_number();
	}

	public static int visit (Reference reference) {
		Cell c = reference.getCell();
		Content cont= c.getContent();
		return cont.getContentValue();
	}

	public static int visit (ADD function) {
		int value=0;
		for(Content c : function.getArgsSet()){
			value+=c.getContentValue();
		}
		return value;
	}

	public static int visit (SUB function) {
		Content contents[]=new Content[2];

		function.getArgsSet().toArray(contents);

		return	contents[0].getContentValue() - contents[1].getContentValue();
	}

	public static int visit (MUL function) {
		int value=1;
		for(Content c : function.getArgsSet()){
			value*=c.getContentValue();
		}
		return value;
	} 

	public static int visit (DIV function) {
		Content contents[]=new Content[2];

		function.getArgsSet().toArray(contents);

		/*if(contents[1].getContentValue()==0){
		* throw new DivisionByZeroException();
		* }
		* */

		return	contents[0].getContentValue() - contents[1].getContentValue();
	}

	public static int visit (AVG function) {
		int value=0;
		//FIX ME
		return value;
	}	 

	public static int visit (PRD function) {
		int value=0;
		//FIX ME
		return value;
	}
}
