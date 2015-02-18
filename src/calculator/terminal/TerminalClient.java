package calculator.terminal;

import calculator.logic.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalClient {
	public static void main(String[] args){
		while(true) {
			System.out.println("Enter string to calculate:");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String equation, result = null;
			try {
				equation = reader.readLine();
			} catch (IOException e) {
				System.out.println("Cannot read from keyboard");
				return;
			}
			result = Calculator.calculate(equation);
			System.out.println(result);
		}
	}
}
