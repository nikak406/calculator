package calculator.javafx.element;

import calculator.javafx.CalcConstantsFX;

public class Button extends javafx.scene.control.Button implements CalcConstantsFX {
    public Button(){
        super();
        this.setWidth(BUTTON_SIZE_X);
        this.setHeight(BUTTON_SIZE_Y);
    }
}
