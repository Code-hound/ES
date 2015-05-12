
package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.ADD;
import pt.tecnico.bubbledocs.domain.BinaryFunction;
import pt.tecnico.bubbledocs.domain.Cell;
import pt.tecnico.bubbledocs.domain.DIV;
import pt.tecnico.bubbledocs.domain.FunctionArguments;
import pt.tecnico.bubbledocs.domain.Literal;
import pt.tecnico.bubbledocs.domain.MUL;
import pt.tecnico.bubbledocs.domain.Reference;
import pt.tecnico.bubbledocs.domain.SUB;
import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.exception.CellNotInSpreadSheetException;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.InvalidValueException;
import pt.tecnico.bubbledocs.exception.ProtectedCellException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

/*
 * ASSIGN REFERENCE CELL
 * 
 * Atribui referencia a uma celula.
 * 
 * @author: Joao Pedro Zeferino
 * @author: Francisco Maria Calisto
 * 
 */

public class AssignBinaryFunctionToCellService extends BubbleDocsService {

	private String userToken;
	private int docId;
	private String cellId;
	private String auxBinaryFunction;
	private String docname;
	private String result;
	private Literal arg1L;
	private Literal arg2L;
	private Reference arg1R;
	private Reference arg2R;
	
	public AssignBinaryFunctionToCellService(String userToken, int docId, 
			String cellId, String auxBinaryFunction) {
		this.userToken = userToken;
		this.docId = docId;
		this.cellId = cellId;
		this.auxBinaryFunction = auxBinaryFunction;
	}

	@Override
	protected void dispatch(){// throws BubbleDocsException{
		//FunctionArguments arg1;
		//FunctionArguments arg2;
		String username = resetUserLastAccess(userToken);
		
		if (username == null)
			throw new UserNotInSessionException(username);
		
		SpreadSheet sheet = getSpreadSheet(docId);
		this.docname = sheet.getSpreadSheetName();

		if (!canBeWrittenBy(sheet, username)) {
			throw new InvalidAccessException(username, this.docname, "WRITE");
		}
		
		//cell = celula cujo conteudo vai passar a ter a funcao binaria
		String[] rowAndColumnCell = cellId.split(";");
		int rowCell    = Integer.parseInt(rowAndColumnCell[0]);
		int columnCell = Integer.parseInt(rowAndColumnCell[1]);
		
//Example:=ADD(2,3;1) 
//binaryFuntion=ADD
		String binaryFunction = auxBinaryFunction.substring(1,4);
				
//auxOperator=2,3;1)
		String auxOperator = auxBinaryFunction.substring(5);

		//retirar os agr's 1 e 2
		String delims = "[,)]+";
		String[] parseOpRef = auxOperator.split(delims);
		String auxArg1 = parseOpRef[0];
		String auxArg2 = parseOpRef[1];
				
		//nao necessario so se verifica no calc value
		int rowSpreadSheet    = sheet.getNumberRows();
		int columnSpreadSheet = sheet.getNumberColumns();
		// testa se a celula referenciada existe nas dimensoes da spreadsheet
		if (!(rowCell    >= 0) ||
			!(rowCell    <= rowSpreadSheet) ||
			!(columnCell >= 0) ||
			!(columnCell <= columnSpreadSheet))
			throw new CellNotInSpreadSheetException(rowCell, columnCell, docId);
	
		
		//criar o literal ou referencia para o auxArg1
		if (auxArg1.contains(";")){			
			String delims1 = "[;]";
			String[] parseOpRef1 = auxArg1.split(delims1);
			int rowCellReference1 = Integer.parseInt(parseOpRef1[0]);
			int columnCellReference1 = Integer.parseInt(parseOpRef1[1]);
			this.arg1R = new Reference
					(sheet, rowCellReference1, columnCellReference1);
		}
		else{
			this.arg1L = new Literal(Integer.parseInt((auxArg1)));
		}	
		
		
		//criar o literal ou referencia para o arg2
		if (auxArg2.contains(";")){			
			String delims2 = "[;]";
			String[] parseOpRef2 = auxArg2.split(delims2);
			int rowCellReference2 = Integer.parseInt(parseOpRef2[0]);
			int columnCellReference2 = Integer.parseInt(parseOpRef2[1]);
			this.arg2R = new Reference
					(sheet, rowCellReference2, columnCellReference2);	
		}
		else{
			this.arg2L = new Literal(Integer.parseInt((auxArg2)));
		}
		
		
		//verifica se a cellId esta' protected para fazer assign
		Reference referenceCellId = new Reference
				(sheet, rowCell, columnCell);
		if (referenceCellId.getCellReference().getProtect())
			throw new ProtectedCellException(rowCell, columnCell);
	
		switch (binaryFunction) {
		case "ADD":
			ADD binaryFuncA;
			//verifica se o arg1 e' literal se falhar o if e' pq arg1 e' referencia
			if (this.arg1L != null){
				//verifica se o arg2 e' literal se falhar o if e' pq arg2 e' referencia
				if (this.arg2L != null){
					binaryFuncA = new ADD(this.arg1L,this.arg2L);}
				else{
					binaryFuncA = new ADD(this.arg1L,this.arg2R);}}
			else{
				if (this.arg2L != null){
					binaryFuncA = new ADD(this.arg1R,this.arg2L);}
				else{
					binaryFuncA = new ADD(this.arg1R,this.arg2R);}
			}
			sheet.addContent(binaryFuncA, rowCell, columnCell);
			this.result = ""+binaryFuncA.getContentValue();
			return;
			
		case "SUB":
			SUB binaryFuncS;
			//EXPLICAÇÃO IGUAL AO DO ADD
			if (this.arg1L != null){
				if (this.arg2L != null){
					binaryFuncS = new SUB(this.arg1L,this.arg2L);}
				else{
					binaryFuncS = new SUB(this.arg1L,this.arg2R);}}
			else{
				if (this.arg2L != null){
					binaryFuncS = new SUB(this.arg1R,this.arg2L);}
				else{
					binaryFuncS = new SUB(this.arg1R,this.arg2R);}
			}
			sheet.addContent(binaryFuncS, rowCell, columnCell);
			this.result = ""+binaryFuncS.getContentValue();
			return;
			
		case "MUL":
			MUL binaryFuncM;
			//EXPLICAÇÃO IGUAL AO DO ADD
			if (this.arg1L != null){
				if (this.arg2L != null){
					binaryFuncM = new MUL(this.arg1L,this.arg2L);}
				else{
					binaryFuncM = new MUL(this.arg1L,this.arg2R);}}
			else{
				if (this.arg2L != null){
					binaryFuncM = new MUL(this.arg1R,this.arg2L);}
				else{
					binaryFuncM = new MUL(this.arg1R,this.arg2R);}
			}
			sheet.addContent(binaryFuncM, rowCell, columnCell);
			this.result = ""+binaryFuncM.getContentValue();
			return;
			
		case "DIV":
			DIV binaryFuncD;
			//EXPLICAÇÃO IGUAL AO DO ADD
			if (this.arg1L != null){
				if (this.arg2L != null){
					binaryFuncD = new DIV(this.arg1L,this.arg2L);}
				else{
					binaryFuncD = new DIV(this.arg1L,this.arg2R);}}
			else{
				if (this.arg2L != null){
					binaryFuncD = new DIV(this.arg1R,this.arg2L);}
				else{
					binaryFuncD = new DIV(this.arg1R,this.arg2R);}
			}
			sheet.addContent(binaryFuncD, rowCell, columnCell);
			this.result = ""+binaryFuncD.getContentValue();
			return; 
		default:
			throw new InvalidValueException();
		}
	}

	public final String getResult() {
		return result+"";
	}
	
}
