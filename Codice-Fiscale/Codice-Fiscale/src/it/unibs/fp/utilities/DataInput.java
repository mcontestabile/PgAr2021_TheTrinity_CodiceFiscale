package it.unibs.fp.utilities;
import java.util.*;

public class DataInput {
	private static final Scanner reader = createScanner();
	private static final String FORMAT_ERROR = "Attenzione: il dato inserito non e' nel formato corretto";
	private static final String MINIMUM_ERROR = "Attenzione: e' richiesto un valore maggiore a ";
	private static final String EMPTY_STRING_ERROR = "Attenzione: non hai inserito alcun carattere";
	private static final String MAXIMUM_ERROR = "Attenzione: e' richiesto un valore minore o uguale a ";
	private static final String ALLOWED_CHARS = "Attenzione: i caratteri ammissibili sono: ";
	private static final char YES = 'S';
	private static final char NO = 'N';

	public DataInput() {
	}

	private static Scanner createScanner() {
		Scanner created = new Scanner(System.in);
		created.useDelimiter(System.lineSeparator() + "|\n");
		return created;
	}

	public static String readString(String message) {
		System.out.print(message);
		return reader.next();
	}

	public static String readNotEmptyString(String message) {
		boolean ended = false;
		String read = null;

		do {
			read = readString(message);
			read = read.trim();
			if (read.length() > 0) {
				ended = true;
			} else {
				System.out.println("Attenzione: non hai inserito alcun carattere");
			}
		} while(!ended);

		return read;
	}

	public static char readChar(String message) {
		boolean ended = false;
		char readValue = 0;

		do {
			System.out.print(message);
			String reading = reader.next();
			if (reading.length() > 0) {
				readValue = reading.charAt(0);
				ended = true;
			} else {
				System.out.println("Attenzione: non hai inserito alcun carattere");
			}
		} while(!ended);

		return readValue;
	}

	public static char readUpperChar(String read, String allowed) {
		boolean ended = false;
		boolean var3 = false;

		char readValue;
		do {
			readValue = readChar(read);
			readValue = Character.toUpperCase(readValue);
			if (allowed.indexOf(readValue) != -1) {
				ended = true;
			} else {
				System.out.println("Attenzione: i caratteri ammissibili sono: " + allowed);
			}
		} while(!ended);

		return readValue;
	}

	public static int readInt(String message) {
		boolean ended = false;
		int readValue = 0;

		do {
			System.out.print(message);

			try {
				readValue = reader.nextInt();
				ended = true;
			} catch (InputMismatchException var5) {
				System.out.println("Attenzione: il dato inserito non e' nel formato corretto");
				String var4 = reader.next();
			}
		} while(!ended);

		return readValue;
	}

	public static int readPositiveInt(String message) {
		return readIntWithMinimum(message, 1);
	}

	public static int readNonNegativeInt(String message) {
		return readIntWithMinimum(message, 0);
	}

	public static int readIntWithMinimum(String message, int minimum) {
		boolean ended = false;
		boolean var3 = false;

		int readValue;
		do {
			readValue = readInt(message);
			if (readValue >= minimum) {
				ended = true;
			} else {
				System.out.println("Attenzione: e' richiesto un valore maggiore a " + minimum);
			}
		} while(!ended);

		return readValue;
	}

	public static int readIntWIthMaxAndMin(String message, int minimum, int maximum) {
		boolean finito = false;
		boolean var4 = false;

		int readValue;
		do {
			readValue = readInt(message);
			if (readValue >= minimum && readValue <= maximum) {
				finito = true;
			} else if (readValue < minimum) {
				System.out.println("Attenzione: e' richiesto un valore maggiore a " + minimum);
			} else {
				System.out.println("Attenzione: e' richiesto un valore minore o uguale a " + maximum);
			}
		} while(!finito);

		return readValue;
	}

	public static double readDouble(String message) {
		boolean ended = false;
		double readValue = 0.0D;

		do {
			System.out.print(message);

			try {
				readValue = reader.nextDouble();
				ended = true;
			} catch (InputMismatchException var6) {
				System.out.println("Attenzione: il dato inserito non e' nel formato corretto");
				String var5 = reader.next();
			}
		} while(!ended);

		return readValue;
	}

	public static double readPositiveDouble(String message) {
		return readDoubleWithMinimum(message, 0.0D);
	}

	public static double readDoubleWithMinimum(String message, double minimum) {
		boolean finito = false;
		double readValue = 0.0D;

		do {
			readValue = readDouble(message);
			if (readValue > minimum) {
				finito = true;
			} else {
				System.out.println("Attenzione: e' richiesto un valore maggiore a " + minimum);
			}
		} while(!finito);

		return readValue;
	}

	public static boolean yesOrNo(String message) {
		String myMessage = message + "(" + 'S' + "/" + 'N' + ")";
		char readValue = readUpperChar(myMessage, String.valueOf('S') + String.valueOf('N'));
		return readValue == 'S';
	}
}
