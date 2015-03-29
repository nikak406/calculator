package calculator.javafx;

import calculator.logic.Calculator;
import calculator.javafx.element.Button;
import calculator.javafx.element.CalculationField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class Controller implements Initializable{
	@FXML private CalculationField calculationField;
	@FXML private Label memoryLabel;
	@FXML private Label resultLabel;

	@FXML Button backspace;
	@FXML Button cancel;
	@FXML Button memoryRead;
	@FXML Button openLap;
	@FXML Button divide;
	@FXML Button plus;
	@FXML Button minus;
	@FXML Button multiply;
	@FXML Button power;
	@FXML Button dot;
	@FXML Button closeLap;
	@FXML Button memory;
	@FXML Button equals;
	@FXML Button one;
	@FXML Button two;
	@FXML Button three;
	@FXML Button four;
	@FXML Button five;
	@FXML Button six;
	@FXML Button seven;
	@FXML Button eight;
	@FXML Button nine;
	@FXML Button zero;

	private String memoryString;

	public void initialize(URL url, ResourceBundle rb){
		equals.setOnAction(event -> {
			String text = getText();
			resultLabel.setText(text);
			calculationField.setText(Calculator.calculate(text));
			if (text.contains("rror")
					|| text.contains("Not a number")
					|| text.contains("Infinity")){
				resultLabel.setText(getText());
				calculationField.setText("");
			}
		});
		cancel.setOnAction(event -> calculationField.setText(""));
		backspace.setOnAction(event -> calculationField.setText(getText().substring(0, getText().length()-1)));
		memory.setOnAction(event -> setMemory(getText()));
		memoryRead.setOnAction(event-> calculationField.setText(getText() + getMemory()));

		Button[] regularButtons = {openLap, divide, plus, minus, multiply, power, dot, closeLap, one, two,
				three, four, five, six, seven, eight, nine, zero};
		Stream.of(regularButtons).forEach(button -> button.setOnAction(event -> calculationField.setText(getText() + button.getText())));
	}

	public String getText(){
		return calculationField.getText();
	}

	public void setMemory(String memory){
		memory = (memory == null)? "" : memory;
		memoryString = memory;
		if (memory.equals("")) {
			memoryLabel.setText("");
		} else {
			memoryLabel.setText("M");
		}
	}

	public String  getMemory(){
		return memoryString;
	}

}
