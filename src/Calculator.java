import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Calculator {

    private String Equation;
    private List<String> list   =   new ArrayList<>();
    final String Delimiters   =   "+-/*^()";
    final Double PI  =   Math.PI;
    final Double e =   Math.exp(1);
    final String token_list []= {"sinh",    "cosh",    "tanh",    "sin",    "cos",    "tan",    "asin",
            "acos",    "atan",    "abs",    "exp",    "sqrt",    "log",    "ln"};
    private void setResult(double res) { result = res; }
    public String getResult() {
        String s = (new Double(round(result))).toString();
        if (s.endsWith(".0")) s=s.substring(0, s.length()-2);
        return  s;
    }
    private double result;
    //-----------------------------------------------------------------
    private boolean containsLaps() //This method checks that number of "(" = number of ")" and order
            throws ParameterSyntaxException{
        int laps = 0;
        for (int i=0; i<list.size() ; i++){
            if (list.get(i)=="(") laps++;
            if (list.get(i)==")") laps--;
            if (laps<0) throw new ParameterSyntaxException("Laps order error");
        }
        if (laps!=0) throw new ParameterSyntaxException("Laps quantity error");
        return list.contains("(");
    }
    private int openLapIndex(){ return list.indexOf("(");}
    private int closeLapIndex() {
        int count = 1;
        for (int i = openLapIndex()+1; i<list.size(); i++){
            if (list.get(i).equals("(")) count++;
            if (list.get(i).equals(")")) count--;
            if (count==0) return i;
        }
        return -1;
    }
    private void removeLaps()  //we recursively launching Calculator for every laps pair
            throws Exception{
        while (containsLaps()){
            List<String> Laps = list.subList(openLapIndex(), closeLapIndex()+1);
            List<String> InsideLaps = Laps.subList(1, Laps.size()-1);
            new Calculator(InsideLaps);
            list.remove(openLapIndex());
            list.remove(closeLapIndex());
        }
        return;
    }
    //-----------------------------------------------------------------
    private void unarminus(){
        for(int i = 0; i<list.size(); i++){
            String str2 = list.get(i);
            if (str2.equals("-")&&(i==0)) {
                list.set(1, "-"+list.get(1));
                list.remove(0);
                continue;
            }
            if (i>0) if (str2.equals("-") && Delimiters.contains(list.get(i-1))){
                list.set(i+1, "-"+list.get(i+1));
                list.remove(i);
                continue;
            }
            if (i>0) {
                Character c = (list.get(i-1).charAt(list.get(i-1).length()-1));
                if ( str2.equals("-") && !c.equals('d') &&
                        (!list.get(i-1).endsWith("pi")) && !Character.isDigit(c)){
                    list.set(i-1, list.get(i-1)+list.get(i)+list.get(i+1));
                    list.remove(i+1);
                    list.remove(i);
                    i--;
                }
            }
        }
    }
    private void calculateDegrees()throws NumberFormatException{

        for(int i=0; i<list.size(); i++){
            String s = list.get(i);
            Double d;
            if (!Delimiters.contains(s) && s.endsWith("d")){
                int k=0;
                while(!Character.isDigit(s.charAt(k))){
                    if (k == s.length()-1) break;
                    k++;
                }
                d = Double.parseDouble(s.substring(k, s.length()-1));
                d = Math.toRadians(d);
                list.set(i, s.substring(0, k)+d.toString());
            }
        }
    }
    private void calculateTokens()
            throws NumberFormatException, ArithmeticException{
        removeLonelyTokens();
        for(int i=0; i<list.size(); i++){
            String s = list.get(i);
            Double d;
            int sign = 1;
            if (s.startsWith("-")){
                sign = -1;
                s = s.substring(1);
            }
            if (!Delimiters.contains(s)){
                if (s.startsWith("ln")) {
                    d = Double.parseDouble(s.substring(2));
                    d = sign*Math.log(d);
                    list.set(i, d.toString());
                }
                if (s.startsWith("log")) {
                    d = Double.parseDouble(s.substring(3));
                    d = sign*Math.log10(d);
                    list.set(i, d.toString());
                }
                if (s.startsWith("abs")) {
                    d = sign*Math.abs(Double.parseDouble(s.substring(3)));
                    list.set(i, d.toString());
                }
                if (s.startsWith("sin")) {
                    d = sign*Math.sin(Double.parseDouble(s.substring(3)));
                    list.set(i, d.toString());
                }
                if (s.startsWith("cos")) {
                    d = sign*Math.cos(Double.parseDouble(s.substring(3)));
                    list.set(i, d.toString());
                }
                if (s.startsWith("tan")) {
                    d = sign*Math.tan(Double.parseDouble(s.substring(3)));
                    list.set(i, d.toString());
                }
                if (s.startsWith("sqrt")) {
                    d = Double.parseDouble(s.substring(4));
                    d = sign*Math.sqrt(d);
                    list.set(i, d.toString());
                }
                if (s.startsWith("sinh")) {
                    d = sign*Math.sinh(Double.parseDouble(s.substring(4)));
                    list.set(i, d.toString());
                }
                if (s.startsWith("tanh")) {
                    d = sign*Math.tanh(Double.parseDouble(s.substring(4)));
                    list.set(i, d.toString());
                }
                if (s.startsWith("acos")) {
                    d = sign*Math.acos(Double.parseDouble(s.substring(4)));
                    list.set(i, d.toString());
                }
                if (s.startsWith("asin")) {
                    d = sign*Math.asin(Double.parseDouble(s.substring(4)));
                    list.set(i, d.toString());
                }
                if (s.startsWith("exp")) {
                    d = sign*Math.exp(Double.parseDouble(s.substring(3)));
                    list.set(i, d.toString());
                }
                if (s.startsWith("atan")) {
                    d = sign*Math.atan(Double.parseDouble(s.substring(4)));
                    list.set(i, d.toString());
                }
            }
        }
    }
    private void operator(String s)
            throws ParameterSyntaxException{
        for(int i = 0; i<list.size(); i++){
            String str2 = list.get(i);
            double a, b, c;
            if (str2.equals(s)) {
                a = Double.parseDouble(list.get(i - 1));
                b = Double.parseDouble(list.get(i + 1));
                switch (s){
                    case "^": c = Math.pow(a, b); break;
                    case "*": c = a*b; break;
                    case "/": c = a/b; break;
                    case "+": c = a+b; break;
                    case "-": c = a-b; break;
                    default: throw new ParameterSyntaxException("Operator sequence error");
                }
                list.set(i, (new Double(c)).toString());
                list.remove(i+1);
                list.remove(i-1);
                i--;
            }
        }
    }
    //----------------------------------------------------------------------
    private void removeLonelyTokens() {
        for(int i=0; i<list.size(); i++) {
            String listel = list.get(i);
            for (String token : token_list) {
                if (listel.equals(token)) {
                    if (i != list.size() - 1) {
                        list.set(i, list.get(i) + list.get(i + 1));
                        list.remove(i + 1);
                    }
                }
            }
        }
    }  //this removes lonely tokens like "sin" (supposed to be "sin0")
    private double round(double x){
        String zeros = "00000000";
        String nines = "99999999";
        String s = BigDecimal.valueOf(x).toPlainString();
        if (s.contains("."+zeros)||s.contains("."+nines)) return Math.round(x);
        if (s.contains(zeros)&&(s.indexOf(zeros)>s.indexOf("."))){
            s = s.substring(0, s.indexOf(zeros));
            return Double.parseDouble(s);
        }
        if (s.contains(nines)&&(s.indexOf(nines)>s.indexOf("."))){
            s = s.substring(0, s.indexOf(nines));
            char c = s.charAt(s.length()-1);
            s = s.substring(0, s.length()-1)+(char)((int)c+1);
            return Double.parseDouble(s);
        }
        return x;
    }
    private void createList(String str)
        throws ParameterSyntaxException{
        Equation = str;
        Equation = Equation.replaceAll(" ", "");
        Equation = Equation.toLowerCase();
        Equation = Equation.replaceAll("pi", (new Double(PI)).toString());
        Equation = Equation.replaceAll("exp(1)", (new Double(e)).toString());
        while (Equation.endsWith("=")) {Equation = Equation.substring(0, Equation.length()-1);}
        if (Equation.startsWith("+")) Equation = Equation.substring(1);
        if (Equation.isEmpty()) throw new ParameterSyntaxException("String is empty");
        StringTokenizer st = new StringTokenizer(Equation, Delimiters, true);
        while (st.hasMoreTokens()) list.add(st.nextToken());
    }
    //---------------------------------------------------------------------
    private void calculate()
        throws Exception{
        try{
            removeLaps();
            unarminus();
            calculateDegrees();
            calculateTokens();
            operator("^");
            operator("*");
            operator("/");
            operator("+");
            operator("-");
        }catch(IndexOutOfBoundsException e){
            throw new ParameterSyntaxException("Operator sequence error");
        }
        if (list.size()==1){
            double x = Double.parseDouble(list.get(0));
            setResult(x);
        }else throw new ParameterSyntaxException("Operator sequence error");
    }
    //----------------------------------------------------------------------
    public Calculator(List<String> listparam)
            throws Exception{
        list = listparam;
        calculate();
    }
    public Calculator(String str)
            throws Exception {
        createList(str);
        calculate();
    }
}

