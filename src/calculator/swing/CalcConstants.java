package calculator.swing;

import java.awt.Font;

public interface CalcConstants{

	final int FRAMEH = 360;
	final int FRAMEW = 750;
	final int BUTTONH = 40;
	final int BUTTONW = 65;
	final int WIDEBUTTONW = 139;
	final int SPACEW = 9;
	final int SPACEH = 10;
	final int NUMBEROFPANELS = 6;
	final int NUMBEROFLABELS = 7;
	final int TEXTAREALENGTH = 22;
	final String gaplength = "                ";
	final String deg = Character.toString((char) 186);
	final String PI = "\u03C0";
	final String BCSP = "\u2190";
	final String sqrt = "\u221A";
	final String x2 = "\u00B2";
	final String e = "\u212f";
	final String mult = "\u00d7";
	final String[] BUTTON_NAMES = {
			"0",    "1",    "2",    "3",    "4",    "5",    "6",    "7",    "8",    "9",
			"=",    "C",    BCSP,   "M",    "MC",   "+",    "-",    mult,    "/",    "^",
			"(",    ")",    ".",    PI,     e,      "log",  "ln",   "sin",  "cos",  "tan",
			"asin", "acos", "atan", "sinh", "cosh", "tanh", "abs",  "MR",   deg,    sqrt,
			x2};
	final String[] SPECIAL_BUTTONS = {"=", "C", BCSP, "M", "MC", "MR"};
	final int[] WIDE_BUTTONS = {10, 11, 12, 13, 20, 21};
	final int[] BIGFONT_BUTTONS =
			{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 20, 21, 22};
	final Font BIGFONT = new Font("Sans", Font.BOLD, 25);
	final Font REGFONT = new Font("Sans", Font.BOLD, 14);
	final Font MIDFONT = new Font("Sans", Font.BOLD, 21);
}