package example.soap;

import java.io.*;
import javax.xml.soap.*;
import javax.xml.namespace.QName;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;


public class SOAPToDOM {

    public static void main(String[] args) {

        try {
            // create SOAP message
            MessageFactory mf = MessageFactory.newInstance();
            SOAPMessage soapMessage = mf.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
            SOAPBody soapBody = soapEnvelope.getBody();
            Name name = soapEnvelope.createName("HelloWorld", "hw", "http://www.hello.com/");
            SOAPElement element = soapBody.addChildElement(name);
            element.addTextNode( "hello text message" );

            // create a byte array from the SOAP Message
            Document document = SOAPMessageToDOMDocument(soapMessage);

            // process message as a DOM document
            // ...

            // create a new SOAP Message from the DOM document
            SOAPMessage newMsg = DOMDocumentToSOAPMessage(document);

            // print SOAP message
            soapMessage.writeTo(System.out);
            System.out.println();

        } catch (Exception e) {
            // print error information
            System.out.printf("Caught exception in main method: %s%n", e);
        }

        System.out.println("Finished");
    }

    private static Document SOAPMessageToDOMDocument(SOAPMessage msg) throws Exception {

        // SOAPPart implements org.w3c.dom.Document interface
        Document part = msg.getSOAPPart();

        return part;
    }

    private static SOAPMessage DOMDocumentToSOAPMessage(Document doc) throws Exception {
        SOAPMessage newMsg = null;

        MessageFactory mf = MessageFactory.newInstance();
        newMsg = mf.createMessage();
        SOAPPart soapPart = newMsg.getSOAPPart();
        soapPart.setContent(new DOMSource(doc));

        return newMsg;
    }

}
