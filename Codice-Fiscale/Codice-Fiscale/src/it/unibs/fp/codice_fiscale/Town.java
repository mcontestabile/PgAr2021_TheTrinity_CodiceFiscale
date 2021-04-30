package it.unibs.fp.codice_fiscale;

import it.unibs.fp.utilities.Parsable;
import it.unibs.fp.utilities.Tag;
import it.unibs.fp.utilities.Writable;

import java.util.ArrayList;

public class Town implements Parsable, Writable {
    private String name;
    private String code;
    public static final String START_STRING = "comune";

    public Town() {
        methods.put("nome", this::setName);
        methods.put("codice", this::setCode);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Town{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public ArrayList<Tag> getAttributesToWrite() {
        return null;
    }

    @Override
    public void setGetters() {

    }

    @Override
    public Tag getStartTag() {
        return null;
    }

    @Override
    public String getStartString() {
        return START_STRING;
    }

    @Override
    public ArrayList<String> getStringsToWrite() {
        return null;
    }
}
