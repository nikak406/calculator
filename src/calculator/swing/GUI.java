package calculator.swing;

import javax.swing.SwingUtilities;

class GUI {
	GUI () { new CalculatorFrame("Java Calculator"); }
	public static void main(String[] args) { SwingUtilities.invokeLater(GUI::new); }
}
