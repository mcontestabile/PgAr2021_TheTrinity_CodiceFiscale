package it.unibs.fp.utilities;

import it.unibs.fp.interfaces.Writable;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * This Interface allows the process of
 * writing of the .xml file.
 */
public class XMLWriter {
    private XMLStreamWriter xmlWriter = null;
    public final static String ENCODING = "utf-8";
    public final static String VERSION = "1.0";
    public static int tabLevel = 0;
    public static final int INCREMENT_LEVEL = 1;
    public static final int SAME_LEVEL = 0;
    public static final int DECREMENT_LEVEL = -1;

    /**
     * it generates a file .xml that has a name
     * that is a String. The name is given
     * by the user. As the exercise's text
     * says, we have to call it outputPersone.
     * @param fileName contains the xml output name.
     */
    public XMLWriter(String fileName) {
        try {
            XMLOutputFactory xmlFactory = XMLOutputFactory.newInstance();
            xmlWriter = xmlFactory.createXMLStreamWriter(new FileOutputStream(fileName), ENCODING);
            xmlWriter.writeStartDocument(ENCODING, VERSION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param openingTag,  it generates the opening tag of the
     *                     fileName.xml.
     */
    public void writeOpeningTagXML(String openingTag) {
        try {
            writeTabs(INCREMENT_LEVEL);
            xmlWriter.writeStartElement(openingTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this method closes an open tag.
     * @param closeDocument,  if true, it close document
     *                        of the fileName.xml.
     */
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

    /**
     * This method writes the Object that we need in
     * the file .xml fileName.xml.
     * @param obj represents a generic object.
     * @param <T> represents a generic type.
     */
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

    /**
     * This method writes the ArrayList that we need in
     * the file .xml fileName.xml, considering the parameters
     * we would like to add in the ArrayList.
     * @param objList  represents a generic object ArrayList.
     * @param arrayName represents the tag name of the array.
     * @param arrayAttribute represents the array's attributes.
     * @param attributeValue represents the array's attributes.
     * @param openingTag document's opening tag.
     * @param <T> represents a generic type.
     */
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

    /**
     * This method writes the ArrayList that we need in
     * the file .xml fileName.xml, considering the parameters
     * we would like to add in teh ArrayList.
     */
    public <T extends Writable> void writeArrayListXML(ArrayList<T> objList, String arrayName, String arrayAttribute, String attributeValue) {
        writeArrayListXML(objList, arrayName, arrayAttribute, attributeValue, null);
    }

    /**
     * This method writes the ArrayList that we need in
     * the file .xml fileName.xml, considering the parameters
     * we would like to add in teh ArrayList.
     */
    public <T extends Writable> void writeArrayListXML(ArrayList<T> objList) {
        writeArrayListXML(objList, null, null, null);
    }

    /**
     *
     * @param changeLevel if 1, it increases tabLevel (number of tabs)
     *                    if -1, it decreases tabLevel
     * @throws XMLStreamException managed by others methods.
     */
    public void writeTabs(int changeLevel) throws XMLStreamException {
        if (changeLevel == -1) tabLevel--;
        xmlWriter.writeCharacters("\n");
        for (int i=0; i<tabLevel; i++) {
            xmlWriter.writeCharacters("\t");
        }
        if (changeLevel == 1) tabLevel++;
    }
}