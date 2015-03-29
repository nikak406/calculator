package calculator.javafx.element;

import calculator.javafx.CalcConstantsFX;

public class CalculationField extends javafx.scene.control.TextField implements CalcConstantsFX{
	public CalculationField(){
		this.setHeight(CALCULATION_AREA_HEIGHT);
		this.setWidth(CALCULATION_AREA_LENGTH);
	}
}
