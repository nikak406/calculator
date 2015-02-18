package calculator.swing;

import calculator.logic.Calculator;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.WindowConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class CalculatorFrame extends JFrame implements CalcConstants, SwingConstants{

	private CalcButton BUTTON[] = new CalcButton[BUTTON_NAMES.length];
	private JTextField textField;
	private FlowLayout flowLayout;
	private JPanel panel[] = new JPanel[NUMBER_OF_PANELS];
	private JLabel[] gapLabel = new JLabel[NUMBER_OF_LABELS];
	private String memory = "";
	private JLabel M, resultLabel;
	private boolean calcDone;

	CalculatorFrame(String str){
		super(str);
		setSize(FRAMEW, FRAMEH);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		flowLayout = new FlowLayout(FlowLayout.RIGHT, SPACEW, SPACEH);
		setLayout(new GridLayout(NUMBER_OF_PANELS, 1));
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception ignored) {}
		createButtons();
		createTextArea();
		createLabels();
		createPanels();
		fillPanels();
		setVisible(true);
	}

	public void setMemory(String s){
		memory = s;
		if (!memory.isEmpty())
			M.setVisible(true);
		else
			M.setVisible(false);
	}

	public String getMemory(){
		return memory;
	}

	void createButtons(){
		ButtonActionListener buttonactionlistener = new ButtonActionListener();
		for (int j=0; j< BUTTON_NAMES.length; j++){
			BUTTON[j] = new CalcButton(BUTTON_NAMES[j]);
			BUTTON[j].addActionListener(buttonactionlistener);
		}
		for (int j : WIDE_BUTTONS) {
			BUTTON[j].setPreferredSize(new Dimension(WIDE_BUTTONW, BUTTONH));
		}
		for (int j : BIGFONT_BUTTONS){
			BUTTON[j].setFont(BIGFONT);
		}
	}

	void createTextArea(){
		textField = new JTextField(TEXT_AREA_LENGTH);
		textField.setEditable(true);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("");
			}
		});
		textField.addActionListener(new TextFieldActionListener());
		textField.setFont(MIDFONT);
		textField.setText("");
	}

	void createLabels() {
		for (int i = 0; i < NUMBER_OF_LABELS; i++) {
			gapLabel[i] = new JLabel(GAP_LENGTH, LEFT);
		}
		M = new JLabel("M", LEFT);
		M.setFont(BIGFONT);
		M.setPreferredSize(new Dimension(BUTTONW, BUTTONH));
		M.setVisible(false);
		resultLabel = new JLabel("", RIGHT);
		resultLabel.setFont(MIDFONT);
		resultLabel.setPreferredSize(new Dimension(3 * BUTTONW + SPACEW, 40));
		resultLabel.setVisible(true);
	}

	void createPanels(){
		for (int i = 0; i< NUMBER_OF_PANELS; i++) {
			panel[i] = new JPanel(flowLayout);
			panel[i].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			panel[i].setAlignmentX(RIGHT_ALIGNMENT);
		}
	}

	void fillPanels(){
		panel[0].add(textField);
		panel[0].add(gapLabel[0]);
		panel[0].add(resultLabel);
		add(panel[0]);
		panel[1].add(BUTTON[12]);
		panel[1].add(BUTTON[11]);
		panel[1].add(BUTTON[10]);
		panel[1].add(gapLabel[1]);
		panel[1].add(BUTTON[40]);
		panel[1].add(BUTTON[39]);
		panel[1].add(M);
		add(panel[1]);
		panel[2].add(BUTTON[20]);
		panel[2].add(BUTTON[18]);
		panel[2].add(BUTTON[9]);
		panel[2].add(BUTTON[8]);
		panel[2].add(BUTTON[7]);
		panel[2].add(gapLabel[2]);
		panel[2].add(BUTTON[38]);
		panel[2].add(BUTTON[24]);
		panel[2].add(BUTTON[23]);
		add(panel[2]);
		panel[3].add(BUTTON[21]);
		panel[3].add(BUTTON[17]);
		panel[3].add(BUTTON[6]);
		panel[3].add(BUTTON[5]);
		panel[3].add(BUTTON[4]);
		panel[3].add(gapLabel[3]);
		panel[3].add(BUTTON[36]);
		panel[3].add(BUTTON[25]);
		panel[3].add(BUTTON[26]);
		add(panel[3]);
		panel[4].add(BUTTON[13]);
		panel[4].add(BUTTON[15]);
		panel[4].add(BUTTON[3]);
		panel[4].add(BUTTON[2]);
		panel[4].add(BUTTON[1]);
		panel[4].add(gapLabel[4]);
		panel[4].add(BUTTON[29]);
		panel[4].add(BUTTON[28]);
		panel[4].add(BUTTON[27]);
		add(panel[4]);
		panel[5].add(BUTTON[37]);
		panel[5].add(BUTTON[14]);
		panel[5].add(BUTTON[16]);
		panel[5].add(BUTTON[19]);
		panel[5].add(BUTTON[22]);
		panel[5].add(BUTTON[0]);
		panel[5].add(gapLabel[5]);
		panel[5].add(BUTTON[32]);
		panel[5].add(BUTTON[31]);
		panel[5].add(BUTTON[30]);
		add(panel[5]);
	}

	public void appendTextField(String appendText) {
		textField.setText(textField.getText()+appendText);
	}

	String backspace(String s){
		if (!s.equals("")) return s.substring(0, s.length()-1);
		else return s;
	}

	String calculate(String s){
		s=s.replaceAll(SQRT, "sqrt");
		s=s.replaceAll(PI, "PI");
		s=s.replaceAll(DEGREE, "d");
		s=s.replaceAll(X2, "^(2)");
		s=s.replaceAll(MULTIPLICATION, "*");
		s=s.replaceAll(EXP, "exp(1)");
		return Calculator.calculate(s);
	}

	class CalcButton extends JButton{
		CalcButton(String str){
			super(str);
			setPreferredSize(new Dimension(BUTTONW, BUTTONH));
			setFont(REGFONT);
		}
	}

	class ButtonActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			String actionCommandName = ae.getActionCommand();
			if (calcDone)
				if ((!"×*/-+^".contains(actionCommandName))||(resultLabel.getText().contains("error")))
					textField.setText("");
				else textField.setText(resultLabel.getText());
			calcDone = false;
			switch (actionCommandName) {
			case (EQUALS):
				resultLabel.setText(calculate(textField.getText()));
				calcDone = true;
				break;
			case (CANCEL):
				resultLabel.setText("");
				textField.setText("");
				break;
			case (BCSP):
				textField.setText(backspace(textField.getText()));
				break;
			case (ADD_MEMORY):
				setMemory(resultLabel.getText());
				break;
			case (DROP_MEMORY):
				setMemory("");
				break;
			case (SHOW_MEMORY):
				appendTextField(getMemory());
				break;
			default:
				appendTextField(actionCommandName);
				break;
			}
		}
	}

	class TextFieldActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			resultLabel.setText(calculate(textField.getText()));
		}
	}
}
