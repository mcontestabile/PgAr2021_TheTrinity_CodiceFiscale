package it.unibs.fp.fiscal_code;
import java.util.Arrays;
import java.util.Calendar;

/**
 * This is the Class that generates the fiscal codes. We use as
 * input the file .xml named inputPersone, which contains all
 * people's personal information, that we need for the fiscal
 * code's generation.
 * The italian fiscal code is made of sixteen chars (we only
 * consider physical persons), and this is the subdivision:
 * 1-3 : surname, is made of the first three consonants, if
 *       consonants are not enough, we add vocals. In the
 *       case of a surname with less than three letters, we
 *       add Xs.
 * 4-6 : name, is made of the first three consonants. If
 *       a name has more than four consonants, we choose
 *       the first, third and fourth ones, otherwise, the
 *       first three in order. If a name has less than three
 *       consonants, we have to pick up the vocals, but after
 *       the consonants. If a name has less than three
 *       letters, we add Xs.
 * 7-9 : birth date, the year is the made by two chars, that
 *        are the last two numbers of the year of the year's birth.
 *        Then, the last char is the month of birth, that is
 *        expressed by a letter (A: Jan, B: Feb, C: Mar, D: Apr,
 *        E: May, H: Jun, L: Jul, M: Aug, P: Sept, R: Oct, S: Nov,
 *        T: Dec).
 * 10-11 : birth day, is made of two int, from 01 to 31 for males,
 *         from 41 to 71 for females (so we also say the gender
 *         of the person). If the birth day is from 1 to
 *         9, we add a 0 before.
 *12-15 : town (we do not consider foreign citizens!), we use the
 *        'Codice Belfiore', made of one letter and three int.
 *        The source of this parte of Fiscal Code is in the .xml
 *        file comuni.xml.
 *16 : CIN, Control Internal Number. It's a particular algorithm
 *     that operates with the previous fifteen chars. We put on a side
 *     the odd chars and the equal ones on the other. Then, with a table
 *     (that is     char  value   char  value   char  value   char  value
 *                   0	    1	   9	 21	     I	   19	   R	  8
 *                   1	    0	   A	  1	     J	   21	   S	 12
 *                   2	    5	   B	  0	     K	    2	   T	 14
 *                   3	    7	   C	  5	     L	    4	   U	 16
 *                   4	    9	   D	  7	     M	   18	   V	 10
 *                   5	   13      E	  9	     N	   20	   W	 22
 *                   6	   15	   F	 13	     O	   11	   X	 25
 *                   7	   17	   G	 15	     P	    3	   Y	 24
 *                   8	   19	   H	 17	     Q	    6	   Z	 23 )
 *      Then the values are summed and the result divided by twenty-six.
 *      r = sum % 26 . r gives us the CIN.
 *
 *      0	A	7	H	14	O	21	V
 *      1	B	8	I	15	P	22	W
 *      2	C	9	J	16	Q	23	X
 *      3	D	10	K	17	R	24	Y
 *      4	E	11	L	18	S	25	Z
 *      5	F	12	M	19	T
 *      6	G	13	N	20	U
 *
 *      WARNING : We do not consider the homocodia.
 */
public class FiscalCodeUtils {
    public static final String MALE_CODE = "M";
    public static final String FEMALE_CODE = "F";
    public static final String[] ORDERED_MONTH_CODES = { "A", "B", "C", "D", "E", "H", "L", "M", "P", "R", "S", "T" };
    private static final String VOWELS_LIST = "AEIOU";
    private final char[] EVENS_LIST = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    private final int[] ODDS_LIST = {
            1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13,
            15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16,
            10, 22, 25, 24, 23
    };
    private String fiscalCode;

    public FiscalCodeUtils() { }

    /**
     * It's the constructor, it
     * generates the fiscal code.
     * @param p represents a person.
     */
    public FiscalCodeUtils(Person p) {
        Calendar date = p.getDateOfBirthCalendar();
        String betaCode =   generateSurnameCode(p.getSurname()) +
                            generateNameCode(p.getName())+
                            generateYearCode(date.get(Calendar.YEAR)) +
                            generateMonthCode(date.get(Calendar.MONTH)) +
                            generateDayCode(date.get(Calendar.DAY_OF_MONTH),
                                    p.getGender()) +
                            generateTownCode(p.getTown());
        fiscalCode = betaCode + generateControlCode(betaCode);
    }

    /**
     * @return fiscalCode, String format.
     */
    public String getFiscalCodeString() {
        return fiscalCode;
    }

    /**
     * @return the fiscalCode, FiscalCode format.
     */
    public FiscalCode getFiscalCode() {
        return new FiscalCode(fiscalCode);
    }

    /**
     * Checking fiscal codes' validity.
     * @param codeToCheck in String format
     * @return valid
     */
    public boolean checkFiscalCode(String codeToCheck) {
        codeToCheck = codeToCheck.toUpperCase();
        char[] cf = codeToCheck.toCharArray();
        boolean valid = true;
        char monthCode = cf[0];
        int dayCode;
        String code = codeToCheck.substring(15);
        String sCode = codeToCheck.substring(0, 3);
        String nCode = codeToCheck.substring(3, 6);

        /*
         * If the fiscal code's longer than sixteen chars,
         * it isn't valid.
         */
        if (codeToCheck.length() != 16) return false;

        for (int i = 0; i < cf.length; i++) {
            valid = switch (i) {
                /* Checking if letters and numbers are in the right position. */
                case 0, 1, 2, 3, 4, 5, 8, 11, 15 -> cf[i] >= 'A' && cf[i] <= 'Z';
                default -> cf[i] >= '1' && cf[i] <= '9';
            };
        }
        if (valid)
            valid = switch (monthCode) {
                /* Checking if month is a right char. */
                case 'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T' -> true;
                default -> false;
            };

        /* Checking if month is right. */
        dayCode = Integer.parseInt(codeToCheck.substring(9, 11));
        if (dayCode > 31) dayCode -= 40;
        if (!(dayCode >= 1 && dayCode <= getMonthDays(monthCode)) ||
                !generateControlCode(codeToCheck.substring(0, 15)).equals(code) ||
                !checkNameOrSurname(nCode) || !checkNameOrSurname(sCode))
            valid = false;

        return valid;
    }

    /**
     * Generation surname's string.
     * @return surname.
     */
    private String generateSurnameCode(String surname) {
        return getNounCode(surname.toUpperCase(), false);
    }

    /**
     * Generation name' string.
     * @return name.
     */
    private String generateNameCode(String name) {
        return getNounCode(name.toUpperCase(), true);
    }

    /**
     * Generation gender's string.
     * @return MALE_CODE.
     */
    private String generateGenderCode(String gender) {
        String genderCode = (gender.length() == 1 ? gender : gender.substring(0, 1)).toUpperCase();
        if (genderCode.equals(MALE_CODE) || genderCode.equals(FEMALE_CODE))
            return genderCode;
        return MALE_CODE;
    }

    /**
     * Generation towm's string.
     * @return town.
     */
    private String generateTownCode(Town town) {
        return town.getCode();
    }

    /**
     * Generation year code's string.
     */
    private String generateYearCode(int year) {
        int yearCode = year % 100;
        return ((yearCode < 10 ? "0" : "") + yearCode);
    }

    /**
     * Generation month code's string.
     */
    private String generateMonthCode(int monthNumber) {
        return ORDERED_MONTH_CODES[monthNumber];
    }

    private String generateDayCode(int day, String gender) {
        gender = generateGenderCode(gender);
        day = (gender.equals(MALE_CODE) ? day : (day + 40));
        return "" + (day < 10 ? "0" : "") + day;
    }

    /**
     * Generation day's string.
     * @return day (and also gender)
     */
    private String generateControlCode(String betaCode) {
        int evens = 0, odds = 0, check, index;

        for (int i = 0; i < betaCode.length(); i++) {
            char ch = betaCode.charAt(i);
            index = Arrays.binarySearch(EVENS_LIST, ch);
            if ((i + 1) % 2 == 0) evens += (index >= 10) ? index - 10 : index;
            else odds += ODDS_LIST[index];
        }
        check = ((evens + odds) % 26) + 10;

        return EVENS_LIST[check] + "";
    }

    /**
     * Generation control code's string.
     * @return EVENS_LIST.
     */
    private String getNounCode(String noun, boolean isName) {
        noun = (noun.trim()).toUpperCase();
        String nounCode = "";
        String consonants = getConsonants(noun);
        String vowels = getVowels(noun);

        if (consonants.length() == 3) nounCode = consonants;
        else if ((consonants.length() < 3) && (noun.length() >= 3)) nounCode = addVowels(consonants, vowels);
        else if ((consonants.length() < 3) && (noun.length() < 3)) nounCode = addX(consonants + vowels);
        else if (consonants.length() > 3) {
            if (!isName) nounCode = consonants.substring(0, 3);
            else nounCode = consonants.charAt(0) + "" + consonants.charAt(2) + "" + consonants.charAt(3);
        }
        return nounCode;
    }

    /**
     * Getting name and surname.
     * @return nounCode.
     */
    private String getVowels(String noun) {
        return noun.replaceAll("[^" + VOWELS_LIST + "]", "");
    }

    /**
     * Getting vowels.
     * @return VOWELS_LIST.
     */
    private String getConsonants(String noun) {
        return noun.replaceAll("[" + VOWELS_LIST + "]", "");
    }

    /**
     * @param noun to add x.
     * @return updated noun string.
     */
    private String addX(String noun) {
        while (noun.length() < 3) noun += "X";
        return noun;
    }

    /**
     * @param noun represents name's or surname's consonants.
     * @param vowels contains noun vowels.
     * @return updated noun string.
     */
    private String addVowels(String noun, String vowels) {
        int index = 0;
        while (noun.length() < 3) {
            noun += vowels.charAt(index);
            index++;
        }
        return noun;
    }

    /**
     * @param nameOrSurnameCode contains name or surname code.
     * @return checks if code is correct.
     */
    private boolean checkNameOrSurname(String nameOrSurnameCode) {
        boolean containsVowel = false;
        for (int i=0; i<nameOrSurnameCode.length(); i++) {
            boolean contains = VOWELS_LIST.contains(nameOrSurnameCode.substring(i, i + 1));
            if (containsVowel && !contains) return false;
            if (!containsVowel && contains) containsVowel = true;
        }
        return true;
    }

    /**
     * @param monthName contains month's char value.
     * @return month's name numbers.
     */
    private int getMonthDays(char monthName) {
        return switch (monthName) {
            case 'S', 'D', 'H', 'P' -> 30;
            case 'B' -> 28;
            default -> 31;
        };
    }
}