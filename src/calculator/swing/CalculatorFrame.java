package calculator.swing;

import calculator.logic.Calculator;
import calculator.logic.ParameterSyntaxException;

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

class CalculatorFrame extends JFrame implements CalcConstants {

	CalcButton BUTTON[] = new CalcButton[CalcConstants.BUTTON_NAMES.length];
	JTextField textfield;
	FlowLayout flowlayout;
	JPanel panel[] = new JPanel[CalcConstants.NUMBEROFPANELS];
	JLabel[] gaplabel = new JLabel[CalcConstants.NUMBEROFLABELS];
	private String memory = "";
	JLabel M, resultlabel;
	private boolean calcDone;

	CalculatorFrame(String str){
		super(str);
		setSize(CalcConstants.FRAMEW, CalcConstants.FRAMEH);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		flowlayout = new FlowLayout(FlowLayout.RIGHT, CalcConstants.SPACEW, CalcConstants.SPACEH);
		setLayout(new GridLayout(CalcConstants.NUMBEROFPANELS, 1));
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

	void setCalcDone(boolean b) { calcDone = b; }

	boolean getCalcDone(){
		return calcDone;
	}

	public void setMemory(String s){
		memory = s;
		if (!memory.isEmpty()) M.setVisible(true); else M.setVisible(false);
	}

	public String getMemory(){ return memory; }

	void dropMemory(){
		memory = "";
		M.setVisible(false);
	}

	void createButtons(){
		ButtonActionListener buttonactionlistener = new ButtonActionListener();
		for (int j=0; j< CalcConstants.BUTTON_NAMES.length; j++){
			BUTTON[j] = new CalcButton(CalcConstants.BUTTON_NAMES[j]);
			BUTTON[j].addActionListener(buttonactionlistener);
		}
		for (int j : CalcConstants.WIDE_BUTTONS) {
			BUTTON[j].setPreferredSize(new Dimension(CalcConstants.WIDEBUTTONW, CalcConstants.BUTTONH));
		}
		for (int j : CalcConstants.BIGFONT_BUTTONS){
			BUTTON[j].setFont(CalcConstants.BIGFONT);
		}
	}

	void createTextArea(){
		textfield = new JTextField(CalcConstants.TEXTAREALENGTH);
		textfield.setEditable(true);
		TextFieldActionListener textfieldactionlistener = new TextFieldActionListener();
		textfield.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				textfield.setText("");
			}
		});
		textfield.addActionListener(textfieldactionlistener);
		textfield.setFont(CalcConstants.MIDFONT);
		textfield.setText("");
	}

	void createLabels() {
		for (int i = 0; i < CalcConstants.NUMBEROFLABELS; i++) {
			gaplabel[i] = new JLabel(CalcConstants.gaplength, SwingConstants.LEFT);
		}
		M = new JLabel("M", SwingConstants.LEFT);
		M.setFont(CalcConstants.BIGFONT);
		M.setPreferredSize(new Dimension(CalcConstants.BUTTONW, CalcConstants.BUTTONH));
		M.setVisible(false);
		resultlabel = new JLabel("", SwingConstants.RIGHT);
		resultlabel.setFont(CalcConstants.MIDFONT);
		resultlabel.setPreferredSize(new Dimension(3* CalcConstants.BUTTONW + CalcConstants.SPACEW, 40));
		resultlabel.setVisible(true);
	}

	void createPanels(){
		for (int i = 0; i< CalcConstants.NUMBEROFPANELS; i++) {
			panel[i] = new JPanel(flowlayout);
			panel[i].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			panel[i].setAlignmentX(RIGHT_ALIGNMENT);
		}
	}

	void fillPanels(){
		panel[0].add(textfield);
		panel[0].add(gaplabel[0]);
		panel[0].add(resultlabel);
		add(panel[0]);
		panel[1].add(BUTTON[12]);
		panel[1].add(BUTTON[11]);
		panel[1].add(BUTTON[10]);
		panel[1].add(gaplabel[1]);
		panel[1].add(BUTTON[40]);
		panel[1].add(BUTTON[39]);
		panel[1].add(M);
		add(panel[1]);
		panel[2].add(BUTTON[20]);
		panel[2].add(BUTTON[18]);
		panel[2].add(BUTTON[9]);
		panel[2].add(BUTTON[8]);
		panel[2].add(BUTTON[7]);
		panel[2].add(gaplabel[2]);
		panel[2].add(BUTTON[38]);
		panel[2].add(BUTTON[24]);
		panel[2].add(BUTTON[23]);
		add(panel[2]);
		panel[3].add(BUTTON[21]);
		panel[3].add(BUTTON[17]);
		panel[3].add(BUTTON[6]);
		panel[3].add(BUTTON[5]);
		panel[3].add(BUTTON[4]);
		panel[3].add(gaplabel[3]);
		panel[3].add(BUTTON[36]);
		panel[3].add(BUTTON[25]);
		panel[3].add(BUTTON[26]);
		add(panel[3]);
		panel[4].add(BUTTON[13]);
		panel[4].add(BUTTON[15]);
		panel[4].add(BUTTON[3]);
		panel[4].add(BUTTON[2]);
		panel[4].add(BUTTON[1]);
		panel[4].add(gaplabel[4]);
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
		panel[5].add(gaplabel[5]);
		panel[5].add(BUTTON[32]);
		panel[5].add(BUTTON[31]);
		panel[5].add(BUTTON[30]);
		add(panel[5]);
	}

	public String getTextField() { return textfield.getText(); }

	public String getResultLabel(){ return resultlabel.getText(); }

	public void setResultField(String str) { resultlabel.setText(str); }

	public void setTextField(String str)   {textfield.setText(str); }

	public void appendTextField(String str) { textfield.setText(textfield.getText()+str); }

	String backspace(String s){
		if (!s.equals("")) return s.substring(0, s.length()-1);
		else return s;
	}

	String calculate(String s){
		s=s.replaceAll(CalcConstants.sqrt, "sqrt");
		s=s.replaceAll(CalcConstants.PI, "PI");
		s=s.replaceAll(CalcConstants.deg, "d");
		s=s.replaceAll(CalcConstants.x2, "^(2)");
		s=s.replaceAll(CalcConstants.mult, "*");
		s=s.replaceAll(CalcConstants.e, "exp(1)");
		try{
			Calculator calc = new Calculator(s);
			return calc.getResult();
		}
		catch(ParameterSyntaxException e){ return "Operator sequence error"; }
		catch(ArithmeticException e){ return "Math error"; }
		catch(NumberFormatException e){ return "Math error"; }
		catch(Exception e){ return "Error"; }
	}

	class CalcButton extends JButton{
		CalcButton(String str){
			super(str);
			setPreferredSize(new Dimension(CalcConstants.BUTTONW, CalcConstants.BUTTONH));
			setFont(CalcConstants.REGFONT);
		}
	}

	class ButtonActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			String actionCommandName = ae.getActionCommand();
			if (getCalcDone())
				if ((!"×*/-+^".contains(actionCommandName))||(getResultLabel().contains("rror"))) textfield.setText("");
				else textfield.setText(resultlabel.getText());
			setCalcDone(false);
			boolean isNotSpecial = true;
			for(String s : SPECIAL_BUTTONS)
				if (s.equals(actionCommandName)) {
					isNotSpecial = false;
					switch (actionCommandName) {
					case "=":
						setResultField(calculate(getTextField()));
						setCalcDone(true);
						break;
					case "C":
						setResultField("");
						setTextField("");
						break;
					case CalcConstants.BCSP:
						setTextField(backspace(getTextField()));
						break;
					case "M":
						setMemory(getResultLabel());
						break;
					case "MC":
						dropMemory();
						break;
					case "MR":
						appendTextField(getMemory());
						break;
					}
				}
			if(isNotSpecial) appendTextField(actionCommandName);
		}
	}

	class TextFieldActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			setResultField(calculate(getTextField()));
		}
	}

}
