package it.unibs.fp.codice_fiscale;
import java.util.Arrays;
import java.util.Calendar;

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

    public FiscalCodeUtils(Person p) {
        Calendar date = p.getDateOfBirthCalendar();
        String betaCode =   generateSurnameCode(p.getSurname()) +
                            generateNameCode(p.getName())+
                            generateYearCode(date.get(Calendar.YEAR)) +
                            generateMonthCode(date.get(Calendar.MONTH)) +
                            generateDayCode(date.get(Calendar.DAY_OF_MONTH),
                                    p.getGender()) +
                            generateTownCode(p.getTown()) +
                            generateGenderCode(p.getGender());
        fiscalCode = betaCode + generateControlCode(betaCode);
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public boolean checkFiscalCode(String codeToCheck) {
        codeToCheck = codeToCheck.toUpperCase();
        char[] cf = codeToCheck.toCharArray();
        boolean valid = true;
        char monthCode = cf[0];
        int dayCode;
        String code = codeToCheck.substring(15);
        String sCode = codeToCheck.substring(0, 3);
        String nCode = codeToCheck.substring(3, 6);

        if (codeToCheck.length() != 16) return false;

        for (int i = 0; i < cf.length; i++) {
            valid = switch (i) {
                case 0, 1, 2, 3, 4, 5, 8, 11, 15 -> cf[i] >= 'A' && cf[i] <= 'Z';
                default -> cf[i] >= '1' && cf[i] <= '9';
            };
        }
        if (valid)
            valid = switch (monthCode) {
                case 'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T' -> true;
                default -> false;
            };

        dayCode = Integer.parseInt(codeToCheck.substring(9, 11));
        if (dayCode > 31) dayCode -= 40;
        if (!(dayCode >= 1 && dayCode <= getMonthDays(monthCode)) ||
                !generateControlCode(codeToCheck.substring(0, 15)).equals(code) ||
                !checkNameOrSurname(nCode) || !checkNameOrSurname(sCode))
            valid = false;

        return valid;
    }

    private String generateSurnameCode(String surname) {
        return getNounCode(surname.toUpperCase(), false);
    }

    private String generateNameCode(String name) {
        return getNounCode(name.toUpperCase(), true);
    }

    private String generateGenderCode(String gender) {
        String genderCode = (gender.length() == 1 ? gender : gender.substring(0, 1)).toUpperCase();
        if (genderCode.equals(MALE_CODE) || genderCode.equals(FEMALE_CODE))
            return genderCode;
        return MALE_CODE;
    }

    private String generateTownCode(Town town) {
        return town.getCode();
    }

    private String generateYearCode(int year) {
        int yearCode = year % 100;
        return ((yearCode < 10 ? "0" : "") + yearCode);
    }

    private String generateMonthCode(int monthNumber) {
        return ORDERED_MONTH_CODES[monthNumber];
    }

    private String generateDayCode(int day, String gender) {
        gender = generateGenderCode(gender);
        day = (gender.equals(MALE_CODE) ? day : (day + 40));
        return "" + (day < 10 ? "0" : "") + day;
    }

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

    private String getVowels(String noun) {
        return noun.replaceAll("[^" + VOWELS_LIST + "]", "");
    }

    private String getConsonants(String noun) {
        return noun.replaceAll("[" + VOWELS_LIST + "]", "");
    }

    private String addX(String noun) {
        while (noun.length() < 3) noun += "X";
        return noun;
    }

    private String addVowels(String noun, String vowels) {
        int index = 0;
        while (noun.length() < 3) {
            noun += vowels.charAt(index);
            index++;
        }
        return noun;
    }

    private boolean checkNameOrSurname(String nameOrSurnameCode) {
        boolean containsVowel = false;
        for (int i=0; i<nameOrSurnameCode.length(); i++) {
            boolean contains = VOWELS_LIST.contains(nameOrSurnameCode.substring(i, i + 1));
            if (containsVowel && !contains) return false;
            if (!containsVowel && contains) containsVowel = true;
        }
        return true;
    }

    private int getMonthDays(char monthName) {
        return switch (monthName) {
            case 'S', 'D', 'H', 'P' -> 30;
            case 'B' -> 28;
            default -> 31;
        };
    }
}