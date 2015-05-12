package example.soap;

import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;


public class NewSOAP {

    public static void main(String[] args) {

        try {
            // create SOAP message
            MessageFactory mf = MessageFactory.newInstance();
            SOAPMessage soapMessage = mf.createMessage();

            // access SOAP body
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
            SOAPBody soapBody = soapEnvelope.getBody();

            // create new SOAP body element
            Name name = soapEnvelope.createName("HelloWorld", "hw", "http://www.hello.com/");
            SOAPElement element = soapBody.addChildElement(name);
            element.addTextNode( "hello text message" );

            // print SOAP message
            soapMessage.writeTo(System.out);
            System.out.println();

        } catch (Exception e) {
            // print error information
            System.out.printf("Caught exception in main method: %s%n", e);
        }

        System.out.println("Finished");
    }

}
