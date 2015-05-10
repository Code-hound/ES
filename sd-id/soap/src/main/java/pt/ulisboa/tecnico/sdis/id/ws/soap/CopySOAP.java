package example.soap;

import java.util.Iterator;

import javax.xml.namespace.QName;

import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPException;


public class CopySOAP {

    public static void main(String[] args) {

        try {

            SOAPMessage soapMessage1 = null;
            SOAPMessage soapMessage2 = null;

            {
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
                System.out.println("SOAP Message 1");
                soapMessage.writeTo(System.out);
                System.out.println();

                soapMessage1 = soapMessage;
            }

            {
                // create SOAP message
                MessageFactory mf = MessageFactory.newInstance();
                SOAPMessage soapMessage = mf.createMessage();

                // access SOAP body
                SOAPPart soapPart = soapMessage.getSOAPPart();
                SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
                SOAPBody soapBody = soapEnvelope.getBody();

                // create new SOAP body element
                Name name = soapEnvelope.createName("HelloWorld2", "hw2", "http://www.hello2.com/");
                SOAPElement element = soapBody.addChildElement(name);
                element.addTextNode( "hello text message 2" );

                // print SOAP message
                System.out.println("SOAP Message 2");
                soapMessage.writeTo(System.out);
                System.out.println();

                soapMessage2 = soapMessage;
            }

            // copy SOAPElement from SOAP message 1 to 2

            SOAPBody soapBody1 = soapMessage1.getSOAPPart().getEnvelope().getBody();
            SOAPBody soapBody2 = soapMessage2.getSOAPPart().getEnvelope().getBody();
            org.w3c.dom.Document doc2 = soapBody2.getOwnerDocument();

            Iterator it1 = soapBody1.getChildElements();
            while(it1.hasNext()) {
                SOAPElement element1 = (SOAPElement) it1.next();
                org.w3c.dom.Node importedNode = doc2.importNode(element1, true);
                soapBody2.appendChild(importedNode);
            }

            // print SOAP message
            System.out.println("SOAP Message 2");
            soapMessage2.writeTo(System.out);
            System.out.println();




        } catch (Exception e) {
            // print error information
            System.out.printf("Caught exception in main method: %s%n", e);
        }

        System.out.println("Finished");
    }

}
