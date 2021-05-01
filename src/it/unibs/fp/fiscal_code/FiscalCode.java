package it.unibs.fp.fiscal_code;

import it.unibs.fp.interfaces.Parsable;
import it.unibs.fp.utilities.XMLTag;
import it.unibs.fp.interfaces.Writable;
import java.util.ArrayList;

public class FiscalCode implements Parsable, Writable {
    private String fiscalCode;
    public static final String START_STRING = "codice";
    private static final ArrayList<String> ATTRIBUTE_STRINGS = new ArrayList<>();

    /*
     * The static keyword is used to create methods that will exist independently
     * of any instances created for the class. Static methods do not use any instance
     * variables of any object of the class they are defined in.
     */
    static {
        ATTRIBUTE_STRINGS.add("codice");
    }

    public FiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public FiscalCode() {
        setters.put("codice", this::setFiscalCode);
    }

    /**
     * @return a string, that is fiscalCode.
     */
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
        return ATTRIBUTE_STRINGS;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof FiscalCode && ((FiscalCode)obj).getFiscalCode().equals(fiscalCode));
    }
}