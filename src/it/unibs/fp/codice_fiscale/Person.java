package it.unibs.fp.codice_fiscale;

import it.unibs.fp.utilities.Parsable;
import it.unibs.fp.utilities.Tag;
import it.unibs.fp.utilities.Writable;

import java.util.ArrayList;

public class Person implements Parsable, Writable {
    private String name;
    private String surname;
    private String gender;
    private String town; // poi da modificare con un oggetto di tipo Town
    private String dateOfBirth; // eventualmente da modificare con un oggetto di tipo Date
    private String id;
    public static final String START_STRING = "persona";

    private static final ArrayList<String> attributeStrings = new ArrayList<>();

    static {
        attributeStrings.add("nome");
        attributeStrings.add("cognome");
        attributeStrings.add("sesso");
        attributeStrings.add("comune_nascita");
        attributeStrings.add("data_nascita");
    }

    public Person() {
        methods.put(attributeStrings.get(0), this::setName);
        methods.put(attributeStrings.get(1), this::setSurname);
        methods.put(attributeStrings.get(2), this::setGender);
        methods.put(attributeStrings.get(3), this::setTown);
        methods.put(attributeStrings.get(4), this::setDateOfBirth);
        methods.put("id", this::setId);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTown() {
        return town;
    }
    public void setTown(String town) {
        this.town = town;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setId(String id) {
        this.id = id;
    }

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
    public Tag getStartTag() {
        return new Tag(START_STRING, "id", id);
    }

    @Override
    public ArrayList<String> getStringsToWrite() {
        return attributeStrings;
    }
}
