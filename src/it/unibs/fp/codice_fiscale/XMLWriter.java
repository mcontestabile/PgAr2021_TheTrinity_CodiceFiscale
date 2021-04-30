package it.unibs.fp.codice_fiscale;

import it.unibs.fp.utilities.XMLTag;
import it.unibs.fp.utilities.Writable;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class XMLWriter {
    private XMLStreamWriter xmlWriter = null;
    public final static String ENCODING = "utf-8";
    public final static String VERSION = "1.0";
    public static int tabLevel = 0;
    public static final int INCREMENT_LEVEL = 1;
    public static final int SAME_LEVEL = 0;
    public static final int DECREMENT_LEVEL = -1;

    public XMLWriter(String fileName) {
        try {
            XMLOutputFactory xmlFactory = XMLOutputFactory.newInstance();
            xmlWriter = xmlFactory.createXMLStreamWriter(new FileOutputStream(fileName), ENCODING);
            xmlWriter.writeStartDocument(ENCODING, VERSION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeOpeningTagXML(String openingTag) {
        try {
            writeTabs(INCREMENT_LEVEL);
            xmlWriter.writeStartElement(openingTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeClosingTagXML(boolean closeDocument) {
        try {
            writeTabs(DECREMENT_LEVEL);
            xmlWriter.writeEndElement();
            if (closeDocument) {
                xmlWriter.writeEndDocument();
                xmlWriter.flush();
                xmlWriter.close();
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public <T extends Writable> void writeObjectXML(T obj) {
        ArrayList<XMLTag> elements;
        XMLTag startXMLTag;
        boolean singleAttribute = false;
        try {
            startXMLTag = obj.getStartTag();

            writeTabs(!singleAttribute ? INCREMENT_LEVEL : SAME_LEVEL);
            xmlWriter.writeStartElement(startXMLTag.getTagName());

            if (startXMLTag.getTagAttribute() != null) {
                xmlWriter.writeAttribute(startXMLTag.getTagAttribute(), startXMLTag.getAttributeValue());
            }

            elements = obj.getAttributesToWrite();

            for (XMLTag t : elements) {
                if (t.getTagName().equals(startXMLTag.getTagName()) && elements.size() == 1)
                    singleAttribute = true;
                if (!singleAttribute) {
                    writeTabs(SAME_LEVEL);
                    xmlWriter.writeStartElement(t.getTagName());
                }
                xmlWriter.writeCharacters(t.getTagValue());
                if (!singleAttribute) xmlWriter.writeEndElement();
            }
            if(!singleAttribute) writeTabs(DECREMENT_LEVEL);
            else tabLevel--;
            xmlWriter.writeEndElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T extends Writable> void writeArrayListXML(ArrayList<T> objList, String arrayName, String arrayAttribute, String attributeValue, String openingTag) {
        try {
            if (openingTag != null) writeOpeningTagXML(openingTag);
            if (arrayName != null) {
                writeTabs(INCREMENT_LEVEL);
                xmlWriter.writeStartElement(arrayName);
                if (arrayAttribute != null) xmlWriter.writeAttribute(arrayAttribute, attributeValue);
            }

            for (T obj : objList)
                writeObjectXML(obj);

            if (arrayName != null) {
                writeTabs(DECREMENT_LEVEL);
                xmlWriter.writeEndElement();
            }
            if (openingTag != null) writeClosingTagXML(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T extends Writable> void writeArrayListXML(ArrayList<T> objList, String arrayName, String arrayAttribute, String attributeValue) {
        writeArrayListXML(objList, arrayName, arrayAttribute, attributeValue, null);
    }

    public <T extends Writable> void writeArrayListXML(ArrayList<T> objList) {
        writeArrayListXML(objList, null, null, null);
    }

    public void writeTabs(int changeLevel) throws XMLStreamException {
        if (changeLevel == -1) tabLevel--;
        xmlWriter.writeCharacters("\n");
        for (int i=0; i<tabLevel; i++) {
            xmlWriter.writeCharacters("\t");
        }
        if (changeLevel == 1) tabLevel++;
    }
}