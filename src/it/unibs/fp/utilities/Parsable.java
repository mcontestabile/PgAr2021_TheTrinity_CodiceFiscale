package it.unibs.fp.utilities;

import java.util.HashMap;
import java.util.function.Consumer;

public interface Parsable {
    final HashMap<String, Consumer<String>> methods = new HashMap<>();

    default public void setAttribute(Tag tag) {
        Consumer<String> method = methods.get(tag.getTagName());
        if (method != null) method.accept(tag.getTagValue());
        if (tag.getAttributeValue() != null) {
            method = methods.get(tag.getTagAttribute());
            if (method != null) method.accept(tag.getAttributeValue());
        }
    }

    default public boolean containsAttribute(String tag) {
        return methods.get(tag) != null;
    }

    public String getStartString();
}