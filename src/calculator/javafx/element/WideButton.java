package calculator.javafx.element;

import calculator.javafx.CalcConstantsFX;

public class WideButton extends calculator.javafx.element.Button implements CalcConstantsFX{
	public WideButton(){
		super();
		this.setWidth(DOUBLE_BUTTON_SIZE_X);
	}
}
