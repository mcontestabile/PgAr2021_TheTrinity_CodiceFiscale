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
    private final String PERSONS_FILE = "inputPersone.xml";
    private final String FISCAL_CODES_FILE = "codiciFiscali.xml";
    private final String TOWNS_FILE = "comuni.xml";
    private final String OUTPUT_FILE = "codiciPersone.xml";

    private ArrayList<Person> persons = new ArrayList<>();
    private ArrayList<FiscalCode> fiscalCodes = new ArrayList<>();
    private ArrayList<Town> towns = new ArrayList<>();
    private ArrayList<FiscalCode> invalid = new ArrayList<>();
    private ArrayList<FiscalCode> unmatched = new ArrayList<>();

    public void startXMLManaging() {
        parseXMLFiles();
        associateFiscalCodesAndTowns();
        fillInvalidAndUnmatched();
        writeOutputFile();
    }

    /**
     * Parsing phase, we get the iput dates that
     * allows to generate the Fiscal Codes and
     * to compare the fc, made by FiscalCode class,
     * with the ones in the .xml file codiciFiscali.xml.
     */
    private void parseXMLFiles() {
        try {
            XMLParser xmlParserP = new XMLParser(PERSONS_FILE);
            persons = xmlParserP.parseXML(Person.class);

            XMLParser xmlParserFC = new XMLParser(FISCAL_CODES_FILE);
            fiscalCodes = xmlParserFC.parseXML(FiscalCode.class);

            XMLParser xmlParserTw = new XMLParser(TOWNS_FILE);
            towns = xmlParserTw.parseXML(Town.class);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void associateFiscalCodesAndTowns() {
        HashMap<String, String> townsHashMap = new HashMap<>();
        for (Town t : towns) {
            townsHashMap.put(t.getName(), t.getCode());
        }
        FiscalCodeUtils fiscalCodeUtils;
        for (Person p : persons) {
            p.setTown(new Town(p.getTownString(), townsHashMap.get(p.getTownString())));
            fiscalCodeUtils = new FiscalCodeUtils(p);
            FiscalCode code = fiscalCodeUtils.getFiscalCode();
            if (fiscalCodes.contains(code))
                p.setFiscalCode(fiscalCodes.remove(fiscalCodes.indexOf(code)).getFiscalCode());
        }
    }

    /**
     * It calculates invalid and non-matching fiscal codes
     */
    private void fillInvalidAndUnmatched() {
        FiscalCodeUtils fiscalCodeUtils = new FiscalCodeUtils();
        for (FiscalCode fiscalCode : fiscalCodes) {
            if (fiscalCodeUtils.checkFiscalCode(fiscalCode.getFiscalCode())) unmatched.add(fiscalCode);
            else invalid.add(fiscalCode);
        }
    }

    /**
     * Writing the output of .xml file.
     */
    private void writeOutputFile() {
        XMLWriter xmlWriter = new XMLWriter(OUTPUT_FILE);
        xmlWriter.writeOpeningTagXML("output");
        xmlWriter.writeArrayListXML(persons, "persone", "numero", persons.size() + "");
        xmlWriter.writeOpeningTagXML("codici");
        xmlWriter.writeArrayListXML(invalid, "invalidi", "numero", invalid.size() + "");
        xmlWriter.writeArrayListXML(unmatched, "spaiati", "numero", unmatched.size() + "");
        xmlWriter.writeClosingTagXML(false);
        xmlWriter.writeClosingTagXML(true);
    }
}