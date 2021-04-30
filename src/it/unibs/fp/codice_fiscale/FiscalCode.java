package it.unibs.fp.codice_fiscale;

import it.unibs.fp.utilities.Parsable;
import it.unibs.fp.utilities.XMLTag;
import it.unibs.fp.utilities.Writable;

import java.util.ArrayList;

public class FiscalCode implements Parsable, Writable {
    private String fiscalCode;
    public static final String START_STRING = "codice";

    private static final ArrayList<String> attributeStrings = new ArrayList<>();

    static {
        attributeStrings.add("codice");
    }

    public FiscalCode() {
        methods.put("codice", this::setFiscalCode);
    }

    public String getFiscalCode() {
        return fiscalCode;
    }
    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    @Override
    public String toString() {
        return "FiscalCode{" +
                "fiscalCode='" + fiscalCode + '\'' +
                '}';
    }

    @Override
    public String getStartString() {
        return START_STRING;
    }

    @Override
    public void setGetters() {
        getters.put("codice", this::getFiscalCode);
    }

    @Override
    public XMLTag getStartTag() {
        return new XMLTag(START_STRING);
    }

    @Override
    public ArrayList<String> getStringsToWrite() {
        return attributeStrings;
    }
}
