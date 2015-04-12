package pt.tecnico.bubbledocs.exception;

public class UnavailableServiceException extends BubbleDocsException
{

	/*
	 * UNAVAILABLE SERVICE EXCEPTION
	 * 
	 * Caso ocorra um erro na invocacao ao servico externo.
	 * 
	 * NOTA: alterar o servico de criacao de um user por forma a utilizar o servico
	 * externo SD-ID via IDRemoteServices.
	 * 
	 * @author: Francisco Maria Calisto
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public UnavailableServiceException() {
		super();
	}

	@Override
	public String getMessage() {
		return "Unavailable service, try again.";
	}

}