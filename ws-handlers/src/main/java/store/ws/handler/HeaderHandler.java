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
public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

    private static final String ID_PROPERTY = "ID";
    private static final String TIME_PROPERTY = "Time";
    private static final String HEADER_NAME = "Store-header";
    private static final String ID_NAME = "clientID";
    private static final String TIME_NAME = "messageTime";
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
                String valueToInsert = (String) smc.get(ID_PROPERTY);
                String messageTime = (String) smc.get(TIME_PROPERTY);
                
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
                
                // add ID element with value
                Name ID = se.createName(ID_NAME, "d", NAMESPACE);
                SOAPElement IDElement = headerElement.addChildElement(ID);
                IDElement.addTextNode(valueToInsert);
                
                // add time element with timestamp
                Name time = se.createName(TIME_NAME, "d", NAMESPACE);
                SOAPElement timeElement = headerElement.addChildElement(time);
                timeElement.addTextNode(messageTime);
                
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
                
                Name ID = se.createName(ID_NAME, "d", NAMESPACE);
                Iterator<SOAPElement> IDIterator = header.getChildElements(ID);
                if (!IDIterator.hasNext()) {
                	System.out.println("NO element of type "+ID_NAME);
                	return true;
                }
                String IDValue = IDIterator.next().getValue();
                smc.put(ID_PROPERTY, IDValue);
                smc.setScope(ID_PROPERTY, Scope.APPLICATION);
                
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
    /*
    public static String getHeaderName() {
    	return HEADER_NAME;
    }
    
    public static String getNamespace() {
    	return NAMESPACE;
    }
    */
    public static String getIDProperty() {
    	return ID_PROPERTY;
    }
    
    public static String getTimeProperty() {
    	return TIME_PROPERTY;
    }
}