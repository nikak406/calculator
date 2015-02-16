import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class CalculatorFrame extends JFrame implements CalcConstants {
    CalcButton BUTTON[] = new CalcButton[BUTTON_NAMES.length];
    JTextField textfield;
    FlowLayout flowlayout;
    JPanel panel[] = new JPanel[NUMBEROFPANELS];
    JLabel[] gaplabel = new JLabel[NUMBEROFLABELS];
    private String memory = "";
    JLabel M, resultlabel;
    private boolean calcDone;
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
    CalculatorFrame(String str){
        super(str);
        setSize(FRAMEW, FRAMEH);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        flowlayout = new FlowLayout(FlowLayout.RIGHT, SPACEW, SPACEH);
        setLayout(new GridLayout(NUMBEROFPANELS, 1));
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception e) {}
        createButtons();
        createTextArea();
        createLabels();
        createPanels();
        fillPanels();
        setVisible(true);
    }
    void createButtons(){
        ButtonActionListener buttonactionlistener = new ButtonActionListener();
        for (int j=0; j<BUTTON_NAMES.length; j++){
            BUTTON[j] = new CalcButton(BUTTON_NAMES[j]);
            BUTTON[j].addActionListener(buttonactionlistener);
        }
        for (int j : WIDE_BUTTONS) {
            BUTTON[j].setPreferredSize(new Dimension(WIDEBUTTONW, BUTTONH));
        }
        for (int j : BIGFONT_BUTTONS){
            BUTTON[j].setFont(BIGFONT);
        }
    }
    void createTextArea(){
        textfield = new JTextField(TEXTAREALENGTH);
        textfield.setEditable(true);
        TextFieldActionListener textfieldactionlistener = new TextFieldActionListener();
        textfield.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                textfield.setText("");
            }
        });
        textfield.addActionListener(textfieldactionlistener);
        textfield.setFont(MIDFONT);
        textfield.setText("");
    }
    void createLabels() {
        for (int i = 0; i < NUMBEROFLABELS; i++) {
            gaplabel[i] = new JLabel(gaplength, Label.LEFT);
        }
        M = new JLabel("M", Label.LEFT);
        M.setFont(BIGFONT);
        M.setPreferredSize(new Dimension(BUTTONW, BUTTONH));
        M.setVisible(false);
        resultlabel = new JLabel("", Label.RIGHT);
        resultlabel.setFont(MIDFONT);
        resultlabel.setPreferredSize(new Dimension(3*BUTTONW + SPACEW, 40));
        resultlabel.setVisible(true);
    }
    void createPanels(){
        for (int i = 0; i<NUMBEROFPANELS; i++) {
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
    class CalcButton extends JButton{
        CalcButton(String str){
            super(str);
            setPreferredSize(new Dimension(BUTTONW, BUTTONH));
            setFont(REGFONT);
        }
    }
    class ButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            String str = ae.getActionCommand();
            if (getCalcDone())
                if ((!"×*/-+^".contains(str))||(getResultLabel().contains("rror"))) textfield.setText("");
                    else textfield.setText(resultlabel.getText());
            setCalcDone(false);
            boolean isnotspecial = true;
            for(String s : SPECIAL_BUTTONS){
                if(s==str){
                    isnotspecial = false;
                    if (str=="C")  {setResultField(""); setTextField("");}
                    if (str=="MC") dropMemory();
                    if (str=="MR") appendTextField(getMemory());
                    if (str=="M") setMemory(getResultLabel());
                    if (str==BCSP) setTextField(backspace(getTextField()));
                    if (str=="=") {setResultField(calculate(getTextField())); setCalcDone(true);}
                }
            }
            if(isnotspecial) appendTextField(str);
        }
    }
    class TextFieldActionListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            setResultField(calculate(getTextField()));
        }
    }
    String backspace(String s){
        if (s!="") return s.substring(0, s.length()-1);
        else return s;
    }
    String calculate(String s){
        s=s.replaceAll(sqrt, "sqrt");
        s=s.replaceAll(PI, "PI");
        s=s.replaceAll(deg, "d");
        s=s.replaceAll(x2, "^(2)");
        s=s.replaceAll(mult, "*");
        s=s.replaceAll(e, "exp(1)");
        try{
            Calculator calc = new Calculator(s);
            return calc.getResult();
        }
        catch(ParameterSyntaxException e){ return "Operator sequence error"; }
        catch(ArithmeticException e){ return "Math error"; }
        catch(NumberFormatException e){ return "Math error"; }
        catch(Exception e){ return "Error"; }
    }
}
