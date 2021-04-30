package it.unibs.fp.utilities;

public class Tag {
    private String tagName;
    private String tagValue;
    private String tagAttribute;
    private String attributeValue;

    public Tag(String tagName, String tagValue) {
        this.tagName = tagName;
        this.tagValue = tagValue;
    }

    public Tag(String tagName, String tagAttribute, String attributeValue) {
        this.tagName = tagName;
        this.tagAttribute = tagAttribute;
        this.attributeValue = attributeValue;
    }

    public Tag(String tagName, String tagValue, String tagAttribute, String attributeValue) {
        this.tagName = tagName;
        this.tagValue = tagValue;
        this.tagAttribute = tagAttribute;
        this.tagValue = attributeValue;
    }

    public String getTagName() {
        return tagName;
    }
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagValue() {
        return tagValue;
    }
    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public String getTagAttribute() {
        return tagAttribute;
    }

    public String getAttributeValue() {
        return attributeValue;
    }
}
