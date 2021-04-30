package it.unibs.fp.interfaces;

import it.unibs.fp.utilities.XMLTag;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * This Interface allows the parsing of the .xml file.
 */
public interface Parsable {
    HashMap<String, Consumer<String>> setters = new HashMap<>();

    default void setAttribute(XMLTag XMLTag) {
        Consumer<String> method = setters.get(XMLTag.getTagName());
        if (method != null) method.accept(XMLTag.getTagValue());
        if (XMLTag.getAttributeValue() != null) {
            method = setters.get(XMLTag.getTagAttribute());
            if (method != null) method.accept(XMLTag.getAttributeValue());
        }
    }

    default boolean containsAttribute(String tag) {
        return setters.get(tag) != null;
    }

    String getStartString();
}