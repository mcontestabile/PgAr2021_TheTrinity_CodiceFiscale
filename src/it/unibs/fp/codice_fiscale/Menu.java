package it.unibs.fp.codice_fiscale;

import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;

public class Menu {
    public void fiscalCodeGestion() {
        XMLParser xmlParserP = new XMLParser("inputPersone.xml");
        ArrayList<Person> persons = null;
        try {
            persons = xmlParserP.parseXML(Person.class);
            for (Person p : persons)
                System.out.println(p.toString());
        } catch (XMLStreamException e) {
            System.out.println(e.getMessage());
        }

        XMLParser xmlParserFC = new XMLParser("codiciFiscali.xml");
        ArrayList<FiscalCode> codes = null;
        try {
            codes = xmlParserFC.parseXML(FiscalCode.class);
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

        XMLWriter xmlWriter = new XMLWriter("codiciPersone.xml");
        xmlWriter.writeOpeningTagXML("output");
        xmlWriter.writeArrayListXML(persons, "persone", "numero", ((Integer)persons.size()).toString());



        xmlWriter.writeArrayListXML(codes, "codici", "numero", ((Integer)codes.size()).toString());

        xmlWriter.writeClosingTagXML(true);


    }
}
