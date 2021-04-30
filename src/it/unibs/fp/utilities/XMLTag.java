package it.unibs.fp.utilities;

/**
 * This is the XMLTag's Class. We had to create it in order
 * to build the tags that are used in the Interfaces Parsable and
 * Writable. This Class is useful because, while we have to write
 * the output XML and to focus on the main task of this program,
 * the fiscal code's validation, we always need the tags, which
 * split out each part of fiscal code.
 */
public class XMLTag {
    private String tagName;
    private String tagValue;
    private String tagAttribute;
    private String attributeValue;

    /**
     * @param tagName name of the XMLTag
     * Constructor, it allows the creation of an object, that is XMLTag.
     */
    public XMLTag(String tagName) {
        this.tagName = tagName;
    }

    /**
     * @param tagName  name of the XMLTag
     * @param tagValue  value of the XMLTag
     * Constructor, it allows the creation of an object, that is XMLTag.
     */
    public XMLTag(String tagName, String tagValue) {
        this.tagName = tagName;
        this.tagValue = tagValue;
    }

    /**
     * @param tagName  name of the XMLTag
     * @param tagAttribute  attribute name of the XMLTag
     * @param attributeValue attribute value of the XMLTag
     * Constructor, it allows the creation of an object, that is XMLTag.
     */
    public XMLTag(String tagName, String tagAttribute, String attributeValue) {
        this.tagName = tagName;
        this.tagAttribute = tagAttribute;
        this.attributeValue = attributeValue;
    }

    /**
     * @param tagName name of the XMLTag
     * @param tagValue value name of the XMLTag
     * @param tagAttribute attribute name of the XMLTag
     * @param attributeValue attribute value of the XMLTag
     * Constructor, it allows the creation of an object, that is XMLTag.
     */
    public XMLTag(String tagName, String tagValue, String tagAttribute, String attributeValue) {
        this.tagName = tagName;
        this.tagValue = tagValue;
        this.tagAttribute = tagAttribute;
        this.tagValue = attributeValue;
    }

    /**
     * @return a string that is tagName.
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * @param tagName, initialization.
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * @return a string that is tagValue.
     */
    public String getTagValue() {
        return tagValue;
    }

    /**
     * @param tagValue, initialization.
     */
    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    /**
     * @return a string that is tagAttribute.
     */
    public String getTagAttribute() {
        return tagAttribute;
    }

    /**
     * @return a string that is getAttributeValue.
     */
    public String getAttributeValue() {
        return attributeValue;
    }
}