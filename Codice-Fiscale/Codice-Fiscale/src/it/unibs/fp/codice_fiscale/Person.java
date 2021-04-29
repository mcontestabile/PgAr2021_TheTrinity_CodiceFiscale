package it.unibs.fp.codice_fiscale;

public class Person extends Parsable {
    private String name;
    private String surname;
    private String gender;
    private String town; // poi da modificare con un oggetto di tipo Town
    private String dateOfBirth; // eventualmente da modificare con un oggetto di tipo Date
    public static final String START_STRING = "persona";

    public Person() {
        methods.put("nome", this::setName);
        methods.put("cognome", this::setSurname);
        methods.put("sesso", this::setGender);
        methods.put("comune_nascita", this::setTown);
        methods.put("data_nascita", this::setDateOfBirth);
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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", town='" + town + '\'' +
                ", dateOfBirth='" + dateOfBirth + "'" +
                '}';
    }

    @Override
    public String getStartString() {
        return START_STRING;
    }
}
