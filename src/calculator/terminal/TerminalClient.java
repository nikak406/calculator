package calculator.terminal;

import calculator.logic.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class TerminalClient {

	private static String WELCOME_MESSAGE = "Welcome to Calculator terminal client\n"
			+ "Type \"help\" for help or \"exit\" for exit.\n"
			+ "\u00A9 Eugene Savochkin, 2015";
	private static String HELP_MESSAGE = "Just type an equation you need to compute and press ENTER, e.g. '2+2'. +-*/^ are allowed operators.\n"
			+ "Use Pi for pi constants, and exp(n) for e in n power. You may use d for degrees, for example 30d means 30 degrees.\n"
			+ "You may use sinh, cosh, tanh, sin, cos, tan, asin, acos, atan, abs, exp, sqrt, log, ln functions. Don't forget to use laps ().\n"
			+ "Example: '-2/(sin(30d)+1.5) ENTER'\n"
			+ "Enter \"help\" for help or \"exit\" for exit";

	public static void main(String[] args){
		System.out.println(WELCOME_MESSAGE);
		while(true) {
			System.out.println("Enter string to calculate:");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String input, result = null;
			try {
				input = reader.readLine();
			} catch (IOException e) {
				System.out.println("Cannot read from keyboard");
				return;
			}
			if (input.equals("help")){
				System.out.println(HELP_MESSAGE);
				continue;
			}if (input.equals("exit")){
				System.out.println("Bye!");
				return;
			}
			result = Calculator.calculate(input);
			System.out.println(result);
		}
	}
}
