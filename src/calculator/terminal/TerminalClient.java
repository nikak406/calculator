package calculator.terminal;

import calculator.logic.Calculator;
import calculator.logic.ParameterSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by isavochkin on 2/17/15.
 */
public class TerminalClient {
	public static void main(String[] args){
		while(true) {
			System.out.println("Enter string to calculate:");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String equation, result = null;
			try {
				equation = reader.readLine();
				result = Calculator.calculate(equation);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			} catch (ParameterSyntaxException e) {
				e.printStackTrace();
			}
			System.out.println(result);
		}
	}
}
