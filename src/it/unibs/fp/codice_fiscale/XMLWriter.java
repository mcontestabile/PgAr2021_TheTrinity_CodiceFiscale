package it.unibs.fp.codice_fiscale;

import it.unibs.fp.utilities.Tag;
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
            xmlWriter.writeStartElement(openingTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeClosingTagXML(boolean closeDocument) {
        try {
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
        ArrayList<Tag> elements;
        Tag startTag;
        boolean singleAttribute = false;
        try {
            startTag = obj.getStartTag();
            xmlWriter.writeStartElement(startTag.getTagName());

            if (startTag.getTagAttribute() != null) {
                xmlWriter.writeAttribute(startTag.getTagAttribute(), startTag.getAttributeValue());
            }

            elements = obj.getAttributesToWrite();

            for (Tag t : elements) {
                if (t.getTagName().equals(startTag.getTagName()) && elements.size() == 1)
                    singleAttribute = true;
                if (!singleAttribute) xmlWriter.writeStartElement(t.getTagName());
                xmlWriter.writeCharacters(t.getTagValue());
                if (!singleAttribute) xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T extends Writable> void writeArrayListXML(ArrayList<T> objList, String arrayName, String arrayAttribute, String attributeValue, String openingTag) {
        try {
            if (openingTag != null) writeOpeningTagXML(openingTag);
            if (arrayName != null) {
                xmlWriter.writeStartElement(arrayName);
                if (arrayAttribute != null) xmlWriter.writeAttribute(arrayAttribute, attributeValue);
            }

            for (T obj : objList)
                writeObjectXML(obj);

            if (arrayName != null) xmlWriter.writeEndElement();
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
}