package it.unibs.fp.codice_fiscale;

import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;

public class Menu {
    public void fiscalCodeGestion() {
        ArrayList<Person> persons = null;
        ArrayList<FiscalCode> codes = null;
        ArrayList<Town> towns = null;

        try {
            XMLParser xmlParserP = new XMLParser("inputPersone.xml");
            persons = xmlParserP.parseXML(Person.class);

            XMLParser xmlParserFC = new XMLParser("codiciFiscali.xml");
            codes = xmlParserFC.parseXML(FiscalCode.class);

            XMLParser xmlParserTw = new XMLParser("comuni.xml");
            towns = xmlParserTw.parseXML(Town.class);
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
