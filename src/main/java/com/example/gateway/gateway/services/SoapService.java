package com.example.gateway.gateway.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.webservices.client.HttpWebServiceMessageSenderBuilder;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import com.example.gateway.wsdl.*;
import org.springframework.ws.transport.WebServiceMessageSender;

import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import java.time.Duration;

/**
 * <p>
 * SoapService class.
 * </p>
 *
 * @author <a href="mailto:despacho@neftaliyagua.com">Neftalí Yagua</a>
 * @version $Id: $Id
 */
@Service
public class SoapService extends WebServiceGatewaySupport {

    Logger logger = LoggerFactory.getLogger(SoapService.class);

    /**
     * <p>
     * Middleware. Este es el intermeciario que interceptará todas las peticiones
     * </p>
     *
     * @param request a {@link java.lang.Object} object
     * @param action  a {@link java.lang.String} object
     * @return Object
     */
    public Object Middleware(Object request, String soapAction) {
        String url = "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.example.gateway.wsdl");
        this.setMarshaller(marshaller);
        this.setUnmarshaller(marshaller);

        WebServiceMessageSender sender = new HttpWebServiceMessageSenderBuilder()
                .setConnectTimeout(Duration.ofSeconds(3600))
                .setReadTimeout(Duration.ofSeconds(3600))
                .build();

        this.setMessageSender(sender);

        return getWebServiceTemplate().marshalSendAndReceive(url, request, new WebServiceMessageCallback() {

            /**
             *
             * @param message
             */
            public void doWithMessage(WebServiceMessage message) {

                ((SoapMessage) message).setSoapAction(soapAction);

                if (message instanceof SaajSoapMessage) {

                    try {

                        SOAPMessage soapMessage = ((SaajSoapMessage) message).getSaajMessage();
                        SOAPHeader header = soapMessage.getSOAPHeader();
                        /*
                        Acá van las cabeceras de authenticación que
                        dependerá del tipo de autenticación de tu proyecto,
                        para estas pruebas no requiere autenticación pero
                        es probable que tu proyecto si lo requiera.

                        SOAPHeaderElement security = header.addHeaderElement(
                                new QName("https://www.w3schools.com/xml/", "AccountHeader")
                        );

                        security.addChildElement("Token").setTextContent(username);
                        security.addChildElement("User").setTextContent(password);
                        security.addChildElement("Pass").setTextContent(token);
                        */
                        soapMessage.saveChanges();
                    } catch (Exception e) {
                        new RuntimeException(e);
                    }
                }
            }
        });
    }


    /**
     * {@summary} cambiar grados celsius a farhrenheit
     *
     * @param code a {@link java.lang.String} object
     * @return CelsiusToFahrenheitResponse
     */
    public CountryNameResponse clientCountryName(String code) {

        CountryName request = new CountryName();
        request.setSCountryISOCode(code);
        return (CountryNameResponse) this.Middleware(request, "");
    }
}

