package it.unibs.fp.interfaces;

import it.unibs.fp.utilities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * This Interface allows the writing of the .xml file.
 */
public interface Writable {
    HashMap<String, Supplier<String>> getters = new HashMap<>();

    /**
     * We are setting the attributes of the output .xml file.
     * @return the tags we want to put in the output .xml file.
     */
    default ArrayList<XMLTag> getAttributesToWrite() {
        setGetters();
        ArrayList<XMLTag> XMLTags = new ArrayList<>();
        for (String name : getStringsToWrite()) {
            Supplier<String> getter = getters.get(name);
            XMLTags.add(new XMLTag(name, getter.get()));
        }
        return XMLTags;
    }

    void setGetters();
    XMLTag getStartTag();
    ArrayList<String> getStringsToWrite();
}