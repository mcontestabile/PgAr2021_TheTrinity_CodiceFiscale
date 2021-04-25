package it.unibs.fp.utilities;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class NiceStrings {
	private static final String SPACE = " ";
	private static final String FRAME = "---------------------------------------------------";
	private static final String ESCAPE = "\n";

	public NiceStrings() {
	}

	public static String frame(String s) {
		StringBuffer res = new StringBuffer();
		res.append("---------------------------------------------------\n");
		res.append(s + "\n");
		res.append("---------------------------------------------------\n");
		return res.toString();
	}

	public static String incolumn(String s, int width) {
		StringBuffer res = new StringBuffer(width);
		int charValueWeHaveToPrint = Math.min(width, s.length());
		res.append(s, 0, charValueWeHaveToPrint);

		for(int i = s.length() + 1; i <= width; ++i) {
			res.append(" ");
		}

		return res.toString();
	}

	public static String centered(String s, int width) {
		StringBuffer res = new StringBuffer(width);
		if (width <= s.length()) {
			res.append(s.substring(width));
		} else {
			int previousSpaces = (width - s.length()) / 2;
			int nextSpaces = width - previousSpaces - s.length();

			int i;
			for(i = 1; i <= previousSpaces; ++i) {
				res.append(" ");
			}

			res.append(s);

			for(i = 1; i <= nextSpaces; ++i) {
				res.append(" ");
			}
		}

		return res.toString();
	}

	public static String repeatChar(char element, int width) {
		StringBuffer result = new StringBuffer(width);

		for(int i = 0; i < width; ++i) {
			result.append(element);
		}

		return result.toString();
	}

	public static String isolatedString(String weHaveToIsolate) {
		StringBuffer result = new StringBuffer();
		result.append("\n");
		result.append(weHaveToIsolate);
		result.append("\n");
		return result.toString();
	}
}
