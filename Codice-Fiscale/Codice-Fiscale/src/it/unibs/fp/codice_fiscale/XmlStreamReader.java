package it.unibs.fp.codice_fiscale;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

/**
 * This is a simple XML stream reader example that is fairly much self
 * contained.
 */
public class XmlStreamReader {
    XMLInputFactory xmlif = null;
    XMLStreamReader xmlr = null;

    try {
        xmlif = XMLInputFactory.newInstance();
        xmlr = xmlif.createXMLStreamReader(filename, new FileInputStream(filename));
    } catch (Exception e) {
        System.out.println("Errore nell'inizializzazione del reader:");
        System.out.println(e.getMessage());
    }
}