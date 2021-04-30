package it.unibs.fp.codice_fiscale;

import it.unibs.fp.utilities.Parsable;
import it.unibs.fp.utilities.XMLTag;
import it.unibs.fp.utilities.Writable;

import java.util.ArrayList;

/**
 * This Class implements Parsable and Writable, because we
 * need the input dates (using Parsable Interface), but
 * also to write the dates in the output file
 * (using Writable Interface). Person is also important,
 * because is used in FiscalCode Class, because each
 * person has its personal fiscal code, so each personal
 * information is given by Person Class.
 */
public class Person implements Parsable, Writable {
    private String name;
    private String surname;
    private String gender;
    private String town; // poi da modificare con un oggetto di tipo Town
    private String dateOfBirth; // eventualmente da modificare con un oggetto di tipo Date
    private String id;
    public static final String START_STRING = "persona";

    private static final ArrayList<String> attributeStrings = new ArrayList<>();

    /*
     * The keyword static is used to create methods that will exist independently
     * of any instances created for the class. Static methods do not use
     * any instance variables of any object of the class they are defined in.
     */
    static {
        attributeStrings.add("nome");
        attributeStrings.add("cognome");
        attributeStrings.add("sesso");
        attributeStrings.add("comune_nascita");
        attributeStrings.add("data_nascita");
    }

    /**
     * This method allows the setting of each personal
     * information of each person in the file .xml
     * inputPersone.xml.
     */
    public Person() {
        methods.put(attributeStrings.get(0), this::setName);
        methods.put(attributeStrings.get(1), this::setSurname);
        methods.put(attributeStrings.get(2), this::setGender);
        methods.put(attributeStrings.get(3), this::setTown);
        methods.put(attributeStrings.get(4), this::setDateOfBirth);
        methods.put("id", this::setId);
    }

    /**
     * @return a string that is name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name, initialization.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return a string that is surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname, initialization.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return a string that is gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender, initialization.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return a string that is town.
     */
    public String getTown() {
        return town;
    }

    /**
     * @param town, initialization.
     */
    public void setTown(String town) {
        this.town = town;
    }

    /**
     * @return a string that is date of birth.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth, initialization.
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @param id, initialization.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method sets the parameter with the attributes
     * contained in the file .xml inputPersone.xml.
     */
    public void setGetters() {
        getters.put(attributeStrings.get(0), this::getName);
        getters.put(attributeStrings.get(1), this::getSurname);
        getters.put(attributeStrings.get(2), this::getGender);
        getters.put(attributeStrings.get(3), this::getTown);
        getters.put(attributeStrings.get(4), this::getDateOfBirth);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", town='" + town + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public String getStartString() {
        return START_STRING;
    }

    @Override
    public XMLTag getStartTag() {
        return new XMLTag(START_STRING, "id", id);
    }

    @Override
    public ArrayList<String> getStringsToWrite() {
        return attributeStrings;
    }
}
