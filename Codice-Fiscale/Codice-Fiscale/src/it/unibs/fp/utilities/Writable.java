package it.unibs.fp.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public interface Writable {
    final HashMap<String, Supplier<String>> getters = new HashMap<>();

    default public ArrayList<Tag> getAttributesToWrite() {
        setGetters();
        ArrayList<Tag> tags = new ArrayList<>();
        for (String name : getStringsToWrite()) {
            Supplier<String> getter = getters.get(name);
            tags.add(new Tag(name, getter.get()));
        }
        return tags;
    }

    public void setGetters();
    public Tag getStartTag();
    public ArrayList<String> getStringsToWrite();
}