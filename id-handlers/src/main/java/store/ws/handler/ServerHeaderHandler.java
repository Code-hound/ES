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

public class ServerHeaderHandler /*implements SOAPHandler<SOAPMessageContext>*/ {
    public static final String VALUE_PROPERTY = "value";
    public static final String TIME_PROPERTY = "time";
	private static final String TIME_REQUEST_PROPERTY = null;
	private static final String ID_REQUEST_PROPERTY = null;
    private static String HEADER_NAME = "Store-header";
    private static String ID_NAME = "clientID";
    private static String TIME_NAME = "messageTime";
    private static String NAMESPACE = "pt.tecnico.ulisboa.essd";
    //private static String NAMESPACE_SERVER = "http://ws.store.wsdl";
    
    //
    // Handler interface methods
    //
    /*
    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext smc) {
        System.out.println("AddHeaderHandler: Handling message.");

        Boolean outboundElement = (Boolean) smc
                .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        try {
            if (outboundElement.booleanValue()) {
                System.out.println("Writing header in outbound SOAP message...");

                // get SOAP envelope
                String valueToInsert = (String) smc.get(VALUE_PROPERTY);
                String messageTime = (String) smc.get(TIME_PROPERTY);
                //System.out.println("I'm to write "+propertyValue);
                SOAPMessage msg = smc.getMessage();
                SOAPPart sp = msg.getSOAPPart();
                SOAPEnvelope se = sp.getEnvelope();

                // add header
                SOAPHeader sh = se.getHeader();
                if (sh == null)
                    sh = se.addHeader();
                
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
                Name name = se.createName(HeaderHandler.getHeaderName(), "d", 
                		HeaderHandler.getNamespace());
                Iterator<SOAPHeaderElement> headerIterator = sh.getChildElements(name);
                
                // check header element
                if (!headerIterator.hasNext()) {
                    System.out.println("Header element not found.");
                    return true;
                }
                
                SOAPElement header = (SOAPElement) headerIterator.next();
                
                Name ID = se.createName(HeaderHandler.getIDName(), "d", HeaderHandler.getNamespace());
                Iterator<SOAPElement> IDIterator = header.getChildElements(ID);
                if (!IDIterator.hasNext()) {
                	System.out.println("NO element of type "+HeaderHandler.getIDName());
                	return true;
                }
                String IDValue = IDIterator.next().getValue();
                System.out.println("Client ID: " + IDValue);
                
                Name timestamp = se.createName(HeaderHandler.getTimeName(), "d", HeaderHandler.getNamespace());
                Iterator<SOAPElement> timeIterator = header.getChildElements(timestamp);
                if (!timeIterator.hasNext()) {
                	System.out.println("NO element of type "+HeaderHandler.getTimeName());
                	return true;
                }
                String timeValue = timeIterator.next().getValue();
                System.out.println("Timestamp: "+timeValue);
                
                // put header in a property context
                smc.put(ID_REQUEST_PROPERTY, IDValue);
                smc.put(TIME_REQUEST_PROPERTY, timestamp);
                // set property scope to application client/server class can access it
                //smc.setScope(VALUE_PROPERTY, Scope.APPLICATION);

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
    */
}
