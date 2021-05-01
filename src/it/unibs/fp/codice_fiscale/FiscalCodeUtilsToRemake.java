package it.unibs.fp.codice_fiscale;
import java.util.*;

class FiscalCodeUtilsToRemake {
    private String townCode;
    private String name;
    private String surname;
    private String gender;
    private int year;
    private int month;
    private int day;

    private final char[] evenList = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private final int[] oddList = {
            1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13,
            15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16,
            10, 22, 25, 24, 23
    };

    private final String[][] monthList = {
            {"1", "A"},
            {"2", "B"},
            {"3", "C"},
            {"4", "D"},
            {"5", "E"},
            {"6", "H"},
            {"7", "L"},
            {"8", "M"},
            {"9", "P"},
            {"10", "R"},
            {"11", "S"},
            {"12", "T"}
    };

    private static final String vowelsList = "AEIOU";

    public FiscalCodeUtilsToRemake() {}

    // Inizializza le variabili di istanza della classe
    public FiscalCodeUtilsToRemake(Person person) {
        this.name = person.getName();
        this.surname = person.getSurname();
        this.townCode = person.getTown().getCode();
        this.gender = person.getGender();
        Calendar dateOfBirthCalendar = person.getDateOfBirthCalendar();
        this.month = dateOfBirthCalendar.get(Calendar.MONTH);
        this.year = dateOfBirthCalendar.get(Calendar.YEAR);
        this.day = dateOfBirthCalendar.get(Calendar.DAY_OF_MONTH);
    }

    // Metodi getter per ottenere gli elementi della classe
    // Interfacce più comode ed ordinate per l'accesso alle funzionalità
    String getName() {
        return changeNS(name, true);
    }

    String getSurname() {
        return changeNS(surname, false);
    }

    String getMonth() {
        return changeMonth();
    }

    int getYear() {
        return (year % 100);
    }

    int getDay() {
        return (gender.equals("M")) ? day : (day + 40);
    }

    String getTown() {
        return getTownCode();
    }

    String getCode() {
        return computeCode(getSurname().toUpperCase() + getName().toUpperCase() + getYear() + getMonth() + getDay() + getTown());
    }

    // I seguenti metodi svolgono le operazioni specifiche sui dati

    /**
     * @param string Corrisponde al nome/cognome da modificare
     * @param isName Se cod e' true, indica il nome; altrimenti il cognome
     * @return nuovaStringa Restituisce la string modificata
     */

    private String changeNS(String string, boolean isName) {
        String newString = "";
        string = string.trim();           // Rimuovo eventuali spazi
        string = string.toUpperCase();

        String consonants = getConsonants(string);      // Ottengo tutte le consonants e tutte le vowels della string
        String vowels = getVowels(string);

        // Controlla i possibili casi
        if (consonants.length() == 3)                 // La string contiene solo 3 consonants, quindi ho gia' la modifica
            newString = consonants;
            // Le consonants non sono sufficienti, e la stinga e' più lunga o
            // uguale a 3 caratteri [aggiungo le vowels mancanti]
        else if ((consonants.length() < 3) && (string.length() >= 3))
            newString = addVowels(consonants, vowels);

            // Le consonants non sono sufficienti, e la string
            //contiene meno di 3 caratteri [aggiungo consonants e vowels, e le x]
        else if ((consonants.length() < 3) && (string.length() < 3))
            newString = addX(consonants + vowels);

            // Le consonants sono in eccesso, prendo solo le
            // prime 3 nel caso del cognome; nel caso del nome la 0, 2, 3
        else if (consonants.length() > 3) {
            // true indica il nome e false il cognome
            if (!isName) newString = consonants.substring(0, 3);
            else newString = consonants.charAt(0) + "" + consonants.charAt(2) + "" + consonants.charAt(3);
        }

        return newString;
    }

    // Aggiunge le X sino a raggiungere una lunghezza complessiva di 3 caratteri
    private String addX(String string) {
        while (string.length() < 3)
            string += "x";
        return string;
    }

    // Aggiunge le vowels alla string passata per parametro
    private String addVowels(String string, String vowels) {
        int index = 0;
        while (string.length() < 3) {
            string += vowels.charAt(index);
            index++;
        }
        return string;
    }

    // Toglie dalla string tutte le consonanti
    private String getVowels(String string) {
        string = string.replaceAll("[^" + vowelsList + "]", "");
        return string;
    }

    // Toglie dalla string tutte le vocali
    private String getConsonants(String string) {
        string = string.replaceAll("[" + vowelsList + "]", "");
        return string;
    }

    // Restituisce il codice del mese
    private String changeMonth() {
        for (String[] strings : monthList) {
            if (strings[0].equalsIgnoreCase(String.valueOf(month))) return strings[1];
        }
        return null;
    }

    // Elabora codice del comune
    private String getTownCode() {
        return townCode;
    }

    // Calcolo del Codice
    private String computeCode(String betaCode) {
        int evens = 0, odds = 0;

        for (int i = 0; i < betaCode.length(); i++) {
            char ch = betaCode.charAt(i);              // i-esimo carattere della stringa

            // Il primo carattere e' il numero 1 non 0
            if ((i + 1) % 2 == 0) {
                int index = Arrays.binarySearch(evenList, ch);
                evens += (index >= 10) ? index - 10 : index;
            } else {
                int index = Arrays.binarySearch(evenList, ch);
                odds += oddList[index];
            }
        }

        int check = (evens + odds) % 26;
        check += 10;

        return evenList[check] + "";
    }

    // Viene richiamato per una stampa
    public String fiscalCode() {
        return getSurname().toUpperCase() + getName().toUpperCase() + getYear() + getMonth() + getDay() + getTown() + getCode();
    }

    //Validation Check
    private static int getMonthDays(char monthName) {
        return switch (monthName) {
            // 30 giorni ha
            case 'S',
            // con
            'D', 'H',
            // e
            'P' -> 30;
            // di 28 ce n'è uno
            case 'B' -> 28;
            // tutti gli altri ne han
            default -> 31;
        };
    }

    public static boolean checkFiscalCode(String fiscalCode) {
        fiscalCode = fiscalCode.toUpperCase();
        char[] cf = fiscalCode.toCharArray();
        boolean valid = true;
        char monthCode = cf[0];
        int dayCode;
        String code = fiscalCode.substring(15);
        String sCode = fiscalCode.substring(0, 3);
        String nCode = fiscalCode.substring(3, 6);

        // controllo se fiscalCode e' lungo 16
        if (fiscalCode.length() != 16) {
            return false;
        }

        // controllo se in posizione 9 e 10 c'e' un numero compreso tra 01 e 31, oppure tra 41 e 71

        for (int i = 0; i < cf.length; i++) {
            valid = switch (i) {
                case 0, 1, 2, 3, 4, 5, 8, 11, 15 -> cf[i] >= 'A' && cf[i] <= 'Z';
                default -> cf[i] >= '1' && cf[i] <= '9';
            };
        }
        if (valid) {
            // controllo la validita' della lunghezza del mese, ricavo l'anno
            valid = switch (monthCode) {
                case 'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T' -> true;
                default -> false;
            };
        }
        if (valid) {
            dayCode = Integer.parseInt(fiscalCode.substring(9, 11));
            if (dayCode > 31) dayCode -= 40;
            if (!(dayCode >= 1 && dayCode <= getMonthDays(monthCode)) ||
                    !new FiscalCodeUtilsToRemake().computeCode(fiscalCode.substring(0, 15)).equals(code) ||
                    !checkNameOrSurname(nCode) || !checkNameOrSurname(sCode))
                valid = false;
        }

        return valid;
    }

    private static boolean checkNameOrSurname(String nameOrSurnameCode) {
        boolean containsVowel = false;
        for (int i=0; i<nameOrSurnameCode.length(); i++) {
            boolean contains = vowelsList.contains(nameOrSurnameCode.substring(i, i + 1));
            if (containsVowel && !contains) return false;
            if (!containsVowel && contains) containsVowel = true;
        }
        return true;
    }
}

