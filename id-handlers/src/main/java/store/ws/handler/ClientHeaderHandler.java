package store.ws.handler;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.MessageContext.Scope;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 *  This SOAPHandler shows how to set/get values from headers in
 *  inbound/outbound SOAP messages.
 *
 *  A header is created in an outbound message and is read on an
 *  inbound message.
 *
 *  The value that is read from the header
 *  is placed in a SOAP message context property
 *  that can be accessed by other handlers or by the application.
 */
public class ClientHeaderHandler implements SOAPHandler<SOAPMessageContext> {

    private static final String SERVER_PROPERTY = "ID";
    private static final String CLIENT_PROPERTY = "Time";
    private static final String TICKET_PROPERTY = "ticket";
    private static final String HEADER_NAME = "ID-header";
    private static final String CLIENT_NAME = "clientID";
    private static final String SERVER_NAME = "serverID";
    private static final String TICKET_NAME = "ticket";
    private static final String NAMESPACE = "pt.tecnico.ulisboa.essd";
    //private static String NAMESPACE_SERVER = "http://ws.store.wsdl";
    
    //
    // Handler interface methods
    //
    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext smc) {
        //System.out.println("AddHeaderHandler: Handling message.");

        Boolean outboundElement = (Boolean) smc
                .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        try {
            if (outboundElement.booleanValue()) {
                System.out.println("Writing header in outbound SOAP message...");

                // get SOAP envelope
                String clientID = (String) smc.get(CLIENT_PROPERTY);
                String serverID = (String) smc.get(SERVER_PROPERTY);
                
                SOAPMessage msg = smc.getMessage();
                SOAPPart sp = msg.getSOAPPart();
                SOAPEnvelope se = sp.getEnvelope();

                // add header
                SOAPHeader sh = se.getHeader();
                if (sh == null)
                    sh = se.addHeader();

                // add header element (name, namespace prefix, namespace)
                Name header = se.createName(HEADER_NAME, "d", NAMESPACE);
                SOAPHeaderElement headerElement = sh.addHeaderElement(header);
                
                // add ID element with client's identifier
                Name client = se.createName(CLIENT_NAME, "d", NAMESPACE);
                SOAPElement clientElement = headerElement.addChildElement(client);
                clientElement.addTextNode(clientID);
                
                // add ID element with server's identifier
                Name server = se.createName(SERVER_NAME, "d", NAMESPACE);
                SOAPElement serverElement = headerElement.addChildElement(server);
                serverElement.addTextNode(serverID);
                
            } else {
            	System.out.println("Reading header in inbound SOAP message...");

                // get SOAP envelope header
                SOAPMessage msg = smc.getMessage();
                SOAPPart sp = msg.getSOAPPart();
                SOAPEnvelope se = sp.getEnvelope();
                SOAPHeader sh = se.getHeader();

                // check header
                if (sh == null) {
                    System.out.println("Header not found. Also");
                    return true;
                }

                // get first header element
                Name name = se.createName(HEADER_NAME, "d", NAMESPACE);
                Iterator<SOAPHeaderElement> headerIterator = sh.getChildElements(name);
                
                // check header element
                if (!headerIterator.hasNext()) {
                    System.out.println("Header element not found.");
                    return true;
                }
                
                SOAPElement header = (SOAPElement) headerIterator.next();
                
                Name ticketName = se.createName(TICKET_NAME, "d", NAMESPACE);
                Iterator<SOAPElement> ticketIterator = header.getChildElements(ticketName);
                if (!ticketIterator.hasNext()) {
                	System.out.println("NO element of type "+TICKET_NAME);
                	return true;
                }
                String ticketValue = ticketIterator.next().getValue();
                smc.put(CLIENT_PROPERTY, ticketValue);
                smc.setScope(TICKET_PROPERTY, Scope.APPLICATION);
                /*
                Name timestamp = se.createName(TIME_NAME, "d", NAMESPACE);
                Iterator<SOAPElement> timeIterator = header.getChildElements(timestamp);
                if (!timeIterator.hasNext()) {
                	System.out.println("NO element of type "+TIME_NAME);
                	return true;
                }
                String timeValue = timeIterator.next().getValue();
                //System.out.println("[HANDLER] Timestamp: "+timeValue+"   Client ID: " + IDValue);
                smc.put(TIME_PROPERTY, timeValue);
                smc.setScope(TIME_PROPERTY, Scope.APPLICATION);
                //System.out.println("Put everything on properties");
                 * 
                 */
            }
        } catch (Exception e) {
            System.out.print("Caught exception in handleMessage: ");
            System.out.println(e);
            System.out.println("Continue normal processing...");
        }

        return true;
    }

    public boolean handleFault(SOAPMessageContext smc) {
        System.out.println("Ignoring fault message...");
        return true;
    }

    public void close(MessageContext messageContext) {
    }
    
    public static String getClientProperty() {
    	return CLIENT_PROPERTY;
    }
    
    public static String getServerProperty() {
    	return SERVER_PROPERTY;
    }
    
    public static String getTicketProperty() {
    	return TICKET_PROPERTY;
    }
}