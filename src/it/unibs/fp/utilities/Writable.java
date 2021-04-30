package it.unibs.fp.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public interface Writable {
    HashMap<String, Supplier<String>> getters = new HashMap<>();

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