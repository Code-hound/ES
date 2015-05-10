package example.soap;

import java.io.ByteArrayInputStream;

import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPException;


public class SetSOAPMessageContent {

    public static void main(String[] args) {

        try {
            //
            // create SOAP message
            //
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();

            // access SOAP envelope
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope soapEnvelope = soapPart.getEnvelope();

            // access SOAP header
            SOAPHeader soapHeader = soapEnvelope.getHeader();
            if(soapHeader == null) {
                // header is optional
                soapHeader = soapEnvelope.addHeader();
            }

            // access SOAP body
            SOAPBody soapBody = soapEnvelope.getBody();

            // create new SOAP header element
            {
                Name name = soapEnvelope.createName("HelloWorldHeader", "hwe", "http://www.hello.com/headers");
                SOAPElement element = soapHeader.addChildElement(name);
                element.addTextNode( "hello header text" );
            }

            // create new SOAP body element
            {
                Name name = soapEnvelope.createName("HelloWorld", "hw", "http://www.hello.com/");
                SOAPElement element = soapBody.addChildElement(name);
                element.addTextNode( "hello text" );
            }

            // print SOAP message
            System.out.println("Message (before content change):");
            soapMessage.writeTo(System.out);
            System.out.println();

            //
            // Create new message with same *contents*
            //
            SOAPMessage oldMessage = soapMessage;

            String newMessageContents = "<soap-env:Envelope xmlns:soap-env=\"htt" +
                "p://schemas.xmlsoap.org/soap/envelope/\"><soap-env:Header><hwe:" +
                "HelloWorldHeader xmlns:hwe=\"http://www.hello.com/headers\">CHA" +
                "NGED hello header text</hwe:HelloWorldHeader></soap-env:Header>" +
                "<soap-enV:Body><hw:HelloWorld xmlns:hw=\"http://www.hello.com/\"" +
                ">CHANGED hello text</hw:HelloWorld></soap-env:Body></soap-env:E" +
                "nvelope>";
            byte[] byteArray = newMessageContents.getBytes();
       			ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);

            // create new message with same mime headers as old message and input bytes as changed content
            SOAPMessage newMessage = messageFactory.createMessage(oldMessage.getMimeHeaders(), bais);

            // replace contents of old message with new message's
            oldMessage.getSOAPPart().setContent(newMessage.getSOAPPart().getContent());


            // print SOAP message again
            System.out.println("Message (after content change):");
            soapMessage.writeTo(System.out);
            System.out.println();


        } catch (Exception e) {
            // print error information
            System.out.printf("Caught exception in main method: %s%n", e);
        }

        System.out.println("Finished");
    }

}
