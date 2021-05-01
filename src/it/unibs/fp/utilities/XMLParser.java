package it.unibs.fp.utilities;

import it.unibs.fp.interfaces.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * This class allows the process of parsing
 * of the input files we have to analyse and
 * use in out Codice Fiscale program.
 */
public class XMLParser {
    private XMLStreamReader xmlReader = null;
    /**
     * This method gets the .xml file (that is fileName) we need to parse.
     * @param fileName
     */
    public XMLParser(String fileName) {
        try {
            XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
            xmlReader = xmlFactory.createXMLStreamReader(fileName, new FileInputStream(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parsing the file xml that we need to parse.
     * @param obj is a generic object.
     * @param <T> is a generic type (T stands for "Thing").
     * @return objList (the parsed list).
     * @throws XMLStreamException
     */
    public <T extends Parsable> ArrayList<T> parseXML(Class<T> obj) throws XMLStreamException {
        String elementName = null;
        XMLTag XMLTag;
        ArrayList<T> objList = new ArrayList<>();
        T t = null;

        /*
         * We create a new instance to initialize the T obj,
         * (so we can use T setters and Parsable methods) and
         * we also consider the exceptions that are possible.
         */
        try {
            t = obj.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        /*
         * While hasNext() is true, so that parsing events are possibile,
         * we parse the .xml file.
         */
        while (xmlReader.hasNext()) {
            assert t != null;
            /*
             * we made a switch, we return with xmlReader.getEventType()
             * an int which represents the event we have to deal with.
             */
            switch (xmlReader.getEventType()) {
                /* XMLStreamConstants.START_DOCUMENT = 7, we are starting the parsing. */
                case XMLStreamConstants.START_DOCUMENT -> {}

                /* XMLStreamConstants.START_ELEMENT = 1, we read an opening tag. */
                case XMLStreamConstants.START_ELEMENT -> {
                    elementName = t.containsAttribute(xmlReader.getLocalName()) ? xmlReader.getLocalName() : null;

                    /* Counting the attributes and getting them back. */
                    for (int i = 0; i < xmlReader.getAttributeCount(); i++) {
                        String name = xmlReader.getAttributeLocalName(i);
                        String value = xmlReader.getAttributeValue(i);
                        XMLTag = elementName != null ? new XMLTag(elementName, name, value) : new XMLTag(name, value);
                        t.setAttribute(XMLTag);
                    }
                }

                /* XMLStreamConstants.END_ELEMENT = 2, reading an end tag. */
                case XMLStreamConstants.END_ELEMENT -> {
                    if (t.getStartString().equals(xmlReader.getLocalName())) {
                        objList.add(t);
                        try {
                            t = obj.getDeclaredConstructor().newInstance();
                        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }

                /* XMLStreamConstants.COMMENT = 5, reading a comment. We will add comment management in the future. */
                case XMLStreamConstants.COMMENT -> {}

                /* XMLStreamConstants.CHARACTERS  = 4, reading a text inside an element. */
                case XMLStreamConstants.CHARACTERS -> {
                    if (xmlReader.getText().trim().length() > 0 && elementName != null) {
                        XMLTag = new XMLTag(elementName, xmlReader.getText());
                        t.setAttribute(XMLTag);
                    }
                }
            }

            xmlReader.next();
        }
        return objList;
    }
}