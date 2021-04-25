package it.unibs.fp.codice_fiscale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

/*
 * XML stream reader, permette di leggere il file inputPersone.xml
 * e creare gli attributi che useremo nel codice CodiceFiscale per
 * generare il codice fiscale delle persone inserite nell'XML.
 *
 * Passaggi richiesti per questo processo:
 * 1. Get the Document Builder.
 * 2. Get document.
 * 3. Normalize the XML structure.
 * 4. Get all the element by the tag name.
 * (Fonte: l'Indiano (<3) che ha il canale "Coding Simplified" su YouTube,
 *  video pubblicato il 10 Luglio 2020).
 */

public class XMLParser {

    public void parseTheXMLInputDati (){
        // 1. Get the Document Builder.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 2. Get document.
            Document document = builder.parse(new File("inputPersone.xml"));

            // 3. Normalize the XML structure.
            document.getDocumentElement().normalize();

            // 4. Get all the element by the tag name.
            /*
             * Cio' che caratterizza il file xml è che ha i tag di
             * apertura e chiusura "persone", che sono i fulcro
             * del documento. Tuttavia, a noi interessano anche le
             * sottocategorie che permettono la generazione del cf
             * (genere, luogo di nascita...). Queste le recuperiamo
             * attraverso il ciclo for.
             */

            NodeList peopleList = document.getElementsByTagName("persona");
            for (int i = 0; i < peopleList.getLength(); i++) {
                Node person = peopleList.item(i);

            /* Questo if ci permette di identificare i singoli attributi
            +  della persona. ELEMENT_NODE = 1 (Fonte: la classe Node.java),
            *  ovvero il nodo è <code>Attr</code>
             */
                if (person.getNodeType() == Node.ELEMENT_NODE) {
                    // Inzio a prendere le singole persone.
                    Element personElement = (Element) person;
                    System.out.println("Persona numero " + personElement.getAttribute("id"));

                    // Prendo i nodi figli, ovvero i dati anagrafici.
                    NodeList personInformations = person.getChildNodes();
                    for (int c = 0; c < personInformations.getLength(); c++) {
                        Node informations = personInformations.item(c);
                        if (informations.getNodeType() == Node.ELEMENT_NODE) {
                            Element informationElement = (Element) informations;
                            //Otterremo i dati anagrafici della persona i
                            System.out.println("          " + informationElement.getTagName() + " : " + informationElement.getAttribute("value"));
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException exception) {
            exception.printStackTrace();
        }

        System.out.println();
        System.out.println();
    }

    public void parseTheXMLcodiciFiscali () {
        // 1. Get the Document Builder.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 2. Get document.
            Document document = builder.parse(new File("codiciFiscali.xml"));

            // 3. Normalize the XML structure.
            document.getDocumentElement().normalize();

            // 4. Get all the element by the tag name.
            /*
             * A differenza del precedente parser, qui non abbiamo i
             * nodi figli, quindi l'operazione è molto più semplice.
             */

            NodeList fiscalCodesList = document.getElementsByTagName("persona");
            for (int i = 0; i < fiscalCodesList.getLength(); i++) {
                Node fiscalCode = fiscalCodesList.item(i);
                Element fc = (Element) fiscalCode;
                System.out.println("          " + fc.getTagName() + fc.getAttribute("codice"));

            /* Questo if ci permette di identificare i singoli attributi
            +  della persona. ELEMENT_NODE = 1 (Fonte: la classe Node.java),
            *  ovvero il nodo è <code>Attr</code>

                if (fiscalCode.getNodeType() == Node.ELEMENT_NODE) {
                    // Inzio a prendere in singoli codici dell'XML.
                    Element fc = (Element) fiscalCode;
                    System.out.println("Codice : " + fc.getAttribute("codice"));
                } */
            }
        } catch (ParserConfigurationException | SAXException | IOException exception) {
            exception.printStackTrace();
        }
    }
}
// Codice slide lezione 4
/*XMLInputFactory xmlif = null;
    XMLStreamReader xmlr = null;

    try {
        xmlif = XMLInputFactory.newInstance();
        xmlr = xmlif.createXMLStreamReader(inputPersone, new FileInputStream(inputPersone));
    } catch (Exception e) {
        System.out.println("Errore nell'inizializzazione del reader:");
        System.out.println(e.getMessage());
    }

    while (xmlr.hasNext()) {
        switch (xmlr.getEventType()) {
            case XMLStreamConstants.START_DOCUMENT ->
                    System.out.println("Start Read Doc " + filename);
                    break;

            case XMLStreamConstants.START_ELEMENT ->
                    System.out.println("Tag " + xmlr.getLocalName());
                    for (int i = 0; i < xmlr.getAttributeCount(); i++)
                    System.out.printf(" => attributo %s->%s%n", xmlr.getAttributeLocalName(i), xmlr.getAttributeValue(i));
                    break;

            case XMLStreamConstants.END_ELEMENT ->
                    System.out.println("END-Tag " + xmlr.getLocalName());
                    break;

            case XMLStreamConstants.COMMENT ->
                System.out.println("// commento " + xmlr.getText());
                break;

            case XMLStreamConstants.CHARACTERS ->
                if (xmlr.getText().trim().length() > 0)
                    System.out.println("-> " + xmlr.getText());
                 break;
        }
        xmlr.next();
    }

    XMLOutputFactory xmlof = null;
    XMLStreamWriter xmlw = null;

    try {
        xmlof = XMLOutputFactory.newInstance();
        xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(inputPersone), “utf-8”); xmlw.writeStartDocument(“utf-8”, “1.0”);
    } catch (Exception e) {
        System.out.println("Errore nell'inizializzazione del writer:"); System.out.println(e.getMessage());
    }*/