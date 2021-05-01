package it.unibs.fp.codice_fiscale;

import it.unibs.fp.utilities.XMLParser;
import it.unibs.fp.utilities.XMLWriter;

import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class controls the entire Fiscal Code program.
 * We have the parsing of the XML, the generation of
 * the fiscal code and the writing of the output file
 * .xml codiciPersone.xml.
 */
public class ManageXML {
    /**
     * Parsing phase, we get the iput dates that
     * allows to generate the Fiscal Codes and
     * to compare the fc, made by FiscalCode class,
     * with the ones in the .xml file codiciFiscali.xml.
     */

    /**
     * Writing the output of .xml file.
     */
    public void fiscalCodeGestion() {
        ArrayList<Person> persons = null;
        ArrayList<FiscalCode> codes = null;
        ArrayList<Town> towns = null;
        HashMap<String, String> townsHashMap = new HashMap<>();
        try {
            XMLParser xmlParserP = new XMLParser("inputPersone.xml");
            persons = xmlParserP.parseXML(Person.class);

            // calcola codici invalidi
            // calcola codici spaiati
            XMLParser xmlParserFC = new XMLParser("codiciFiscali.xml");
            codes = xmlParserFC.parseXML(FiscalCode.class);

            XMLParser xmlParserTw = new XMLParser("comuni.xml");
            towns = xmlParserTw.parseXML(Town.class);
            for (Town t : towns) {
                townsHashMap.put(t.getName(), t.getCode());
            }
        } catch (XMLStreamException e) {
            System.out.println(e.getMessage());
        }

        FiscalCodeUtils fiscalCodeUtils;
        for (Person p : persons) {
            p.setTown(new Town(p.getTownString(), townsHashMap.get(p.getTownString())));
            fiscalCodeUtils = new FiscalCodeUtils(p);
            System.out.println(fiscalCodeUtils.getFiscalCode());

        }

        XMLWriter xmlWriter = new XMLWriter("codiciPersone.xml");
        xmlWriter.writeOpeningTagXML("output");
        xmlWriter.writeArrayListXML(persons, "persone", "numero", ((Integer)persons.size()).toString());

        xmlWriter.writeOpeningTagXML("codici");
        xmlWriter.writeArrayListXML(codes, "invalidi", "numero", ((Integer)codes.size()).toString());
        xmlWriter.writeArrayListXML(codes, "spaiati", "numero", ((Integer)codes.size()).toString());
        xmlWriter.writeClosingTagXML(false);

        xmlWriter.writeClosingTagXML(true);


    }
}