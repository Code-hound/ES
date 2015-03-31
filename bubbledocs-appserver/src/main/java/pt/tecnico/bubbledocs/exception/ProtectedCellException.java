package pt.tecnico.bubbledocs.exception;

public class ProtectedCellException extends BubbleDocsException
{

	/*
	 *	PROTECTED CELL EXCEPTION EXCEPTION
	 *
	 *	Excepcao caso a celula esteja protegida.
	 *
	 *	@author: Luis Ribeiro Gomes
	 */

	private int row;    //row
	private int column; //column

	public ProtectedCellException(int row, int column)
	{
		this.row    = row;
		this.column = column;
	}
	
	public int getRow()
	{
		return this.row;
	}
	
	public int getColumn()
	{
		return this.column;
	}
	
	@Override
	public String getMessage()
	{
		return "Cell is protected: " + this.row + " ; " + this.column;
	}
	
}
