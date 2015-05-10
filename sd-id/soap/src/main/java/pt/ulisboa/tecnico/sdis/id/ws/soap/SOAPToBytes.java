package example.soap;

import java.io.*;
import javax.xml.soap.*;
import javax.xml.namespace.QName;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;


public class SOAPToBytes {

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
            byte[] messageByteArray = SOAPMessageToByteArray(soapMessage);

            // process message as a byte array
            // ...

            // create a new SOAP Message from the byte array
            SOAPMessage newMsg = byteArrayToSOAPMessage(messageByteArray);

            // print SOAP message
            soapMessage.writeTo(System.out);
            System.out.println();

        } catch (Exception e) {
            // print error information
            System.out.printf("Caught exception in main method: %s%n", e);
        }

        System.out.println("Finished");
    }

    private static byte[] SOAPMessageToByteArray(SOAPMessage msg) throws Exception {
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        byte[] msgByteArray = null;

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        Source source = msg.getSOAPPart().getContent();
        Result result = new StreamResult(byteOutStream);
        transformer.transform(source, result);

        msgByteArray = byteOutStream.toByteArray();
        return msgByteArray;
    }

    private static byte[] SOAPMessageToByteArrayAlternativeImplementation(SOAPMessage msg) throws Exception {
    //                                          -------------------------
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        byte[] msgByteArray = null;

        // assuming no attachments exist, msg.write() dumps only the SOAP envelope in SOAP part
        msg.writeTo(byteOutStream);
        msgByteArray = byteOutStream.toByteArray();

        return msgByteArray;
    }

    private static SOAPMessage byteArrayToSOAPMessage(byte[] msg) throws Exception {
        ByteArrayInputStream byteInStream = new ByteArrayInputStream(msg);
        StreamSource source = new StreamSource(byteInStream);
        SOAPMessage newMsg = null;

        MessageFactory mf = MessageFactory.newInstance();
        newMsg = mf.createMessage();
        SOAPPart soapPart = newMsg.getSOAPPart();
        soapPart.setContent(source);

        return newMsg;
    }

}
