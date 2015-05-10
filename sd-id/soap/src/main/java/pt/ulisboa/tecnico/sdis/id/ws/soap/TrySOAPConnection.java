package example.soap;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;


public class TrySOAPConnection {

    public static void main(String[] args) {

        try {
            //
            // create SOAP message
            //
            MessageFactory mf = MessageFactory.newInstance();
            SOAPMessage soapMessage = mf.createMessage();

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
                Name name = soapEnvelope.createName("Euro2008 WS Information", "eh", "http://euro2008.dataaccess.eu");
                SOAPElement element = soapHeader.addChildElement(name);
                element.addTextNode( "http://euro2008.dataaccess.eu/footballpoolwebservice.wso" );
            }

            // create new SOAP body element
            {

                Name name = soapEnvelope.createName("StadiumInfo", "", "http://euro2008.dataaccess.eu");
                SOAPElement element = soapBody.addChildElement(name);

                name = soapEnvelope.createName("sStadiumName");
                SOAPElement element2 = element.addChildElement(name);
                element2.addTextNode("St. Jakob-Park");
            }

            // print SOAP message
            System.out.println("Message to send:");
            soapMessage.writeTo(System.out);
            System.out.println();

            //
            //  SOAP call
            //

            // create connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // set address
            if(args.length < 1) {
                throw new Exception("Missing required argument - endpoint address - please specify it in run args");
            }
            String endpointAddress = args[0];
            System.out.println("Endpoint address: " + endpointAddress);

            // call
           SOAPMessage response = soapConnection.call(soapMessage, endpointAddress);

            // print SOAP message
           System.out.println("Resposta do Servidor");
           response.writeTo(System.out);
           System.out.println("");

            soapConnection.close();

        } catch (Exception e) {
            // print error information
            System.out.printf("Caught exception in main method: %s%n", e);
        }

        System.out.println("Finished");
    }

}
