package it.unibs.fp.utilities;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * This Interface allows the parsing of the .xml file.
 */
public interface Parsable {
    HashMap<String, Consumer<String>> methods = new HashMap<>();

    default void setAttribute(XMLTag XMLTag) {
        Consumer<String> method = methods.get(XMLTag.getTagName());
        if (method != null) method.accept(XMLTag.getTagValue());
        if (XMLTag.getAttributeValue() != null) {
            method = methods.get(XMLTag.getTagAttribute());
            if (method != null) method.accept(XMLTag.getAttributeValue());
        }
    }

    default boolean containsAttribute(String tag) {
        return methods.get(tag) != null;
    }

    String getStartString();
}