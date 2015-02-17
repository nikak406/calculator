package calculator.swing;

import java.awt.Font;

public interface CalcConstants{

	final int FRAMEH = 360;
	final int FRAMEW = 750;
	final int BUTTONH = 40;
	final int BUTTONW = 65;
	final int WIDE_BUTTONW = 139;
	final int SPACEW = 9;
	final int SPACEH = 10;
	final int NUMBER_OF_PANELS = 6;
	final int NUMBER_OF_LABELS = 7;
	final int TEXT_AREA_LENGTH = 22;
	final String GAP_LENGTH = "                ";
	final String DEGREE = Character.toString((char) 186);
	final String PI = "\u03C0";
	final String BCSP = "\u2190";
	final String SQRT = "\u221A";
	final String X2 = "\u00B2";
	final String EXP = "\u212f";
	final String MULTIPLICATION = "\u00d7";
	final String CANCEL = "C";
	final String EQUALS = "=";
	final String ADD_MEMORY = "M";
	final String DROP_MEMORY = "MC";
	final String SHOW_MEMORY = "MR";
	final String[] BUTTON_NAMES = {
			"0",    "1",    "2",    "3",    "4",    "5",    "6",    "7",    "8",    "9",
			EQUALS,    CANCEL,    BCSP,   ADD_MEMORY,    CANCEL,   "+",    "-", MULTIPLICATION,    "/",    "^",
			"(",    ")",    ".",    PI, EXP,      "log",  "ln",   "sin",  "cos",  "tan",
			"asin", "acos", "atan", "sinh", "cosh", "tanh", "abs",  SHOW_MEMORY, DEGREE, SQRT,
			X2 };
	final String[] SPECIAL_BUTTONS = { EQUALS, CANCEL, BCSP, ADD_MEMORY, DROP_MEMORY, SHOW_MEMORY };
	final int[] WIDE_BUTTONS = {10, 11, 12, 13, 20, 21};
	final int[] BIGFONT_BUTTONS =
			{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 20, 21, 22};
	final Font BIGFONT = new Font("Sans", Font.BOLD, 25);
	final Font REGFONT = new Font("Sans", Font.BOLD, 14);
	final Font MIDFONT = new Font("Sans", Font.BOLD, 21);
}