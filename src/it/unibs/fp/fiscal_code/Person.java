package it.unibs.fp.fiscal_code;

import it.unibs.fp.interfaces.Parsable;
import it.unibs.fp.utilities.XMLTag;
import it.unibs.fp.interfaces.Writable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    private Town town;
    private Date dateOfBirth;
    private String id;
    private String fiscalCode = "ASSENTE";
    public static final String START_STRING = "persona";
    private static final ArrayList<String> ATTRIBUTE_STRINGS = new ArrayList<>();

    /*
     * The keyword static is used to create methods that will exist independently
     * of any instances created for the class. Static methods do not use
     * any instance variables of any object of the class they are defined in.
     */
    static {
        ATTRIBUTE_STRINGS.add("nome");
        ATTRIBUTE_STRINGS.add("cognome");
        ATTRIBUTE_STRINGS.add("sesso");
        ATTRIBUTE_STRINGS.add("comune_nascita");
        ATTRIBUTE_STRINGS.add("data_nascita");
        ATTRIBUTE_STRINGS.add("codice_fiscale");
    }

    /**
     * This method allows the setting of each personal
     * information of each person in the file .xml
     * inputPersone.xml.
     */
    public Person() {
        setters.put(ATTRIBUTE_STRINGS.get(0), this::setName);
        setters.put(ATTRIBUTE_STRINGS.get(1), this::setSurname);
        setters.put(ATTRIBUTE_STRINGS.get(2), this::setGender);
        setters.put(ATTRIBUTE_STRINGS.get(3), this::setTownString);
        setters.put(ATTRIBUTE_STRINGS.get(4), this::setDateOfBirth);
        setters.put("id", this::setId);
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
    public String getTownString() {
        return town.getName();
    }

    /**
     * @return a town.
     */
    public Town getTown() {
        return town;
    }

    /**
     * @param town, initialization.
     */
    public void setTownString(String town) {
        this.town = new Town(town, "");
    }

    /**
     * @param town, initialization.
     */
    public void setTown(Town town) {
        this.town = town;
    }

    /**
     * @return a string that is date of birth.
     */
    public String getDateOfBirth() {
        Calendar date = new GregorianCalendar();
        date.setTime(dateOfBirth);
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        return year + "-" + (month < 10 ? "0" : "") + month + "-" + (day < 10 ? "0" : "") + day;

    }

    /**
     * @return a Calendar that represent the date of birth
     */
    public Calendar getDateOfBirthCalendar() {
        Calendar dateOfBirthCalendar = new GregorianCalendar();
        dateOfBirthCalendar.setTime(dateOfBirth);
        return dateOfBirthCalendar;
    }

    /**
     * @param dateOfBirth, initialization.
     */
    public void setDateOfBirth(String dateOfBirth) {
        try {
            SimpleDateFormat dateOfBirthFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.dateOfBirth = dateOfBirthFormat.parse(dateOfBirth);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the fiscalCode
     */
    public String getFiscalCode() {
        return fiscalCode;
    }

    /**
     * @param fiscalCode in String format
     */
    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
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
        getters.put(ATTRIBUTE_STRINGS.get(0), this::getName);
        getters.put(ATTRIBUTE_STRINGS.get(1), this::getSurname);
        getters.put(ATTRIBUTE_STRINGS.get(2), this::getGender);
        getters.put(ATTRIBUTE_STRINGS.get(3), this::getTownString);
        getters.put(ATTRIBUTE_STRINGS.get(4), this::getDateOfBirth);
        getters.put("codice_fiscale", this::getFiscalCode);
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
        return ATTRIBUTE_STRINGS;
    }
}
