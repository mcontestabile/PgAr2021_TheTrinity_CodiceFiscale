package it.unibs.fp.fiscal_code;

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
        ArrayList<FiscalCode> fiscalCodes = null;
        ArrayList<Town> towns;
        HashMap<String, String> townsHashMap = new HashMap<>();
        try {
            XMLParser xmlParserP = new XMLParser("inputPersone.xml");
            persons = xmlParserP.parseXML(Person.class);

            /*
            It calculates invalid and non-matching fiscal codes
             */
            XMLParser xmlParserFC = new XMLParser("codiciFiscali.xml");
            fiscalCodes = xmlParserFC.parseXML(FiscalCode.class);

            XMLParser xmlParserTw = new XMLParser("comuni.xml");
            towns = xmlParserTw.parseXML(Town.class);
            for (Town t : towns) {
                townsHashMap.put(t.getName(), t.getCode());
            }
        } catch (XMLStreamException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<FiscalCode> personFiscalCodes = new ArrayList<>();
        FiscalCodeUtils fiscalCodeUtils;
        for (Person p : persons) {
            p.setTown(new Town(p.getTownString(), townsHashMap.get(p.getTownString())));
            fiscalCodeUtils = new FiscalCodeUtils(p);
            FiscalCode code = fiscalCodeUtils.getFiscalCode();
            if (fiscalCodes.contains(code))
                p.setFiscalCode(fiscalCodes.remove(fiscalCodes.indexOf(code)).getFiscalCode());
        }

        ArrayList<FiscalCode> invalidi = new ArrayList<>();
        ArrayList<FiscalCode> spaiati = new ArrayList<>();
        fiscalCodeUtils = new FiscalCodeUtils();
        for (FiscalCode fiscalCode : fiscalCodes) {
            if (fiscalCodeUtils.checkFiscalCode(fiscalCode.getFiscalCode())) spaiati.add(fiscalCode);
            else invalidi.add(fiscalCode);
        }

        XMLWriter xmlWriter = new XMLWriter("codiciPersone.xml");
        xmlWriter.writeOpeningTagXML("output");
        xmlWriter.writeArrayListXML(persons, "persone", "numero", persons.size() + "");

        xmlWriter.writeOpeningTagXML("codici");
        xmlWriter.writeArrayListXML(invalidi, "invalidi", "numero", invalidi.size() + "");
        xmlWriter.writeArrayListXML(spaiati, "spaiati", "numero", spaiati.size() + "");
        xmlWriter.writeClosingTagXML(false);

        xmlWriter.writeClosingTagXML(true);
    }
}
