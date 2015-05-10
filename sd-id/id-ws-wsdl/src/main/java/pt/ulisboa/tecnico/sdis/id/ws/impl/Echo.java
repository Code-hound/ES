package pt.ulisboa.tecnico.sdis.id.ws.impl;

import javax.jws.*;

@WebService
public interface Echo {

	@WebMethod String echo(String name) throws EchoException;
    @WebMethod String fastEcho(String name) throws EchoException;
	
}
