package it.unibs.fp.codice_fiscale;

import it.unibs.fp.utilities.Parsable;
import it.unibs.fp.utilities.XMLTag;
import it.unibs.fp.utilities.Writable;

import java.util.ArrayList;

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
public class FiscalCode implements Parsable, Writable {
    private String fiscalCode;
    public static final String START_STRING = "codice";

    private static final ArrayList<String> attributeStrings = new ArrayList<>();

    static {
        attributeStrings.add("codice");
    }

    public FiscalCode() {
        methods.put("codice", this::setFiscalCode);
    }

    public String getFiscalCode() {
        return fiscalCode;
    }
    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    @Override
    public String toString() {
        return "FiscalCode{" +
                "fiscalCode='" + fiscalCode + '\'' +
                '}';
    }

    @Override
    public String getStartString() {
        return START_STRING;
    }

    @Override
    public void setGetters() {
        getters.put("codice", this::getFiscalCode);
    }

    @Override
    public XMLTag getStartTag() {
        return new XMLTag(START_STRING);
    }

    @Override
    public ArrayList<String> getStringsToWrite() {
        return attributeStrings;
    }
}
