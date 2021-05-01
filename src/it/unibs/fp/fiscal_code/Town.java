package it.unibs.fp.fiscal_code;

import it.unibs.fp.interfaces.Parsable;

/**
 * This Class contains the informations of each
 * town into the file .xml comuni.xml. Those informations
 * are the town's name and the code, which can be found
 * in the Codice Belfiore. This code is made of
 * one char and three int.
 */
public class Town implements Parsable {
    private String name;
    private String code;
    public static final String START_STRING = "comune";

    public Town(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * This method sets the parameter with the attributes
     * contained in the file .xml inputPersone.xml.
     */
    public Town() {
        setters.put("nome", this::setName);
        setters.put("codice", this::setCode);
    }

    /**
     * @return a string that is name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name, initialization.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return a string that is code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code, initialization.
     */
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getStartString() {
        return START_STRING;
    }
}