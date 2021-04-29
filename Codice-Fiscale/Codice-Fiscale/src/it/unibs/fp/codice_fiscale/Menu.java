package it.unibs.fp.codice_fiscale;

import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;

public class Menu {
    public void fiscalCodeGestion() {
        XMLParser xmlParserP = new XMLParser("inputPersone.xml");
        try {
            ArrayList<Person> persons = xmlParserP.parseXML(Person.class);
            for (Person p : persons)
                System.out.println(p.toString());
        } catch (XMLStreamException e) {
            System.out.println(e.getMessage());
        }

        XMLParser xmlParserFC = new XMLParser("codiciFiscali.xml");
        try {
            ArrayList<FiscalCode> codes = xmlParserFC.parseXML(FiscalCode.class);
            for (FiscalCode c : codes)
                System.out.println(c.toString());
        } catch (XMLStreamException e) {
            System.out.println(e.getMessage());
        }

        XMLParser xmlParserTw = new XMLParser("comuni.xml");
        try {
            ArrayList<Town> towns = xmlParserTw.parseXML(Town.class);
            for (Town t : towns)
                System.out.println(t.toString());
        } catch (XMLStreamException e) {
            System.out.println(e.getMessage());
        }
    }
}
