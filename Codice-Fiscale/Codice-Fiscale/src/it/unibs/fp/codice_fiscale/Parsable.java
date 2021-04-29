package it.unibs.fp.codice_fiscale;

import java.util.HashMap;
import java.util.function.Consumer;

public abstract class Parsable {
    protected final HashMap<String, Consumer<String>> methods = new HashMap<>();

    public void setAttribute(String tag, String value) {
        Consumer<String> method = methods.get(tag);
        method.accept(value);
    }

    public boolean containsAttribute(String tag) {
        return methods.get(tag) != null;
    }

    public abstract String getStartString();
}