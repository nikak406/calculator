package calculator.logic;

import java.util.*;

import static java.lang.Math.PI;

public class Calculator {

    private static final String DELIMITERS =   "+-/*^()";
    private static final Double EXP = Math.exp(1);

    private static final String TOKENS_ARRAY[]= {"sinh",    "cosh",    "tanh",    "sin",    "cos",    "tan",    "asin",
            "acos",    "atan",    "abs",    "exp",    "sqrt",    "log",    "ln"};

    private List<String> list;
    private String result;

    public static String calculate(String equation){
        Calculator calculator;
        String result;
        try {
            calculator = new Calculator(createList(equation));
            result = calculator.result;
        } catch (ParameterSyntaxException e) {
            result = "Syntax error: " + e.getMessage();
        } catch (ArithmeticException e) {
            result = "Arithmetic error: " + e.getMessage();
        } catch (NumberFormatException e) {
            result = "Number format error: " + e.getMessage();
        } catch (Exception e) {
            result = "Unknown error: " + e.getMessage();
        }
        return result;
    }

    private static List<String> createList(String equation) throws ParameterSyntaxException{
        equation = equation.replaceAll(" ", "");
        equation = equation.toLowerCase();
        equation = equation.replaceAll("pi", Double.toString(PI));
        equation = equation.replaceAll("exp(1)", (EXP).toString());
        if (equation.isEmpty()) throw new ParameterSyntaxException("Input is empty");
        while (equation.endsWith("=")) {
            equation = equation.substring(0, equation.length() - 1);
        }
        if (equation.startsWith("+")) equation = equation.substring(1);
        StringTokenizer stringTokenizer = new StringTokenizer(equation, DELIMITERS, true);
        List<String> list = new LinkedList<>();
        while (stringTokenizer.hasMoreTokens()) list.add(stringTokenizer.nextToken());
        return list;
    }

    private Calculator(List<String> list) throws ParameterSyntaxException, ArithmeticException, NumberFormatException{
        this.list = list;
        compute();
    }

    private String formatResult(String s) throws NumberFormatException{
        double d = Double.parseDouble(s);
        Formatter fmt = new Formatter();
        fmt.format("%.7g", d);
        if (s.length()>12) s = fmt.toString();
        String zeros = "00000000";
        String nines = "99999999";
        if (s.contains("."+zeros)||s.contains("."+nines))
            s = s.substring(0, s.indexOf("."));
        if (s.contains(zeros)&&(s.indexOf(zeros)>s.indexOf("."))){
            s = s.substring(0, s.indexOf(zeros));
        }
        if (s.contains(nines)&&(s.indexOf(nines)>s.indexOf("."))){
            s = s.substring(0, s.indexOf(nines));
            char c = s.charAt(s.length()-1);
            s = s.substring(0, s.length()-1)+(char)((int)c+1);
        }
        if (s.endsWith(".0")) s=s.substring(0, s.length()-2);
        if (s.equals("NaN")) s = "Not a number";
        return s;
    }

    private boolean containsLaps() throws ParameterSyntaxException {
        int laps = 0;
        for (String aList : list) {
            if (aList.equals("("))
                laps++;
            if (aList.equals(")"))
                laps--;
            if (laps < 0)
                throw new ParameterSyntaxException("Laps order error");
        }
        if (laps!=0) throw new ParameterSyntaxException("Laps quantity error");
        return list.contains("(");
    }

    private int openLapIndex(){ return list.indexOf("(");}

    private int closeLapIndex() throws ParameterSyntaxException{
        int count = 1;
        for (int i = openLapIndex()+1; i< list.size(); i++){
            if (list.get(i).equals("(")) count++;
            if (list.get(i).equals(")")) count--;
            if (count==0) return i;
        }
        throw new ParameterSyntaxException("Laps error");
    }

    private void removeLaps() throws ParameterSyntaxException{
        while (containsLaps()){
            List<String> InsideLaps = list.subList(openLapIndex()+1, closeLapIndex());
            new Calculator(InsideLaps);
            list.remove(closeLapIndex());
            list.remove(openLapIndex());
        }
    }

    private void minus(){
        for (int i=0; i< list.size(); i++){
            if (list.get(i).equals("-")){
                list.set(i, "+");
                list.add(i+1, "-1");
                list.add(i+2, "*");
                if (i>0 && DELIMITERS.contains(list.get(i-1))) list.remove(i);
            }
        }
        if (list.get(0).equals("+")) list.remove(0);
    }

    private void calculateDegrees() throws NumberFormatException{

        for(int i=0; i< list.size(); i++){
            String s = list.get(i);
            Double d;
            if (!DELIMITERS.contains(s) && s.endsWith("d")){
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

    private void calculateTokens() throws NumberFormatException, ArithmeticException, ParameterSyntaxException{
        removeLonelyTokens();
        for(int i=0; i< list.size(); i++){
            String currentElement = list.get(i);
            Double d;
            if (!DELIMITERS.contains(currentElement)){
                if (currentElement.startsWith("ln")) {
                    d = Double.parseDouble(currentElement.substring(2));
                    d = Math.log(d);
                    list.set(i, d.toString());
                }
                if (currentElement.startsWith("log")) {
                    d = Double.parseDouble(currentElement.substring(3));
                    d = Math.log10(d);
                    list.set(i, d.toString());
                }
                if (currentElement.startsWith("abs")) {
                    d = Math.abs(Double.parseDouble(currentElement.substring(3)));
                    list.set(i, d.toString());
                }
                if (currentElement.startsWith("sin")) {
                    d = Math.sin(Double.parseDouble(currentElement.substring(3)));
                    list.set(i, d.toString());
                }
                if (currentElement.startsWith("cos")) {
                    d = Math.cos(Double.parseDouble(currentElement.substring(3)));
                    list.set(i, d.toString());
                }
                if (currentElement.startsWith("tan")) {
                    d = Math.tan(Double.parseDouble(currentElement.substring(3)));
                    list.set(i, d.toString());
                }
                if (currentElement.startsWith("sqrt")) {
                    d = Double.parseDouble(currentElement.substring(4));
                    d = Math.sqrt(d);
                    list.set(i, d.toString());
                }
                if (currentElement.startsWith("sinh")) {
                    d = Math.sinh(Double.parseDouble(currentElement.substring(4)));
                    list.set(i, d.toString());
                }
                if (currentElement.startsWith("tanh")) {
                    d = Math.tanh(Double.parseDouble(currentElement.substring(4)));
                    list.set(i, d.toString());
                }
                if (currentElement.startsWith("acos")) {
                    d = Math.acos(Double.parseDouble(currentElement.substring(4)));
                    list.set(i, d.toString());
                }
                if (currentElement.startsWith("asin")) {
                    d = Math.asin(Double.parseDouble(currentElement.substring(4)));
                    list.set(i, d.toString());
                }
                if (currentElement.startsWith("exp")) {
                    d = Math.exp(Double.parseDouble(currentElement.substring(3)));
                    list.set(i, d.toString());
                }
                if (currentElement.startsWith("atan")) {
                    d = Math.atan(Double.parseDouble(currentElement.substring(4)));
                    list.set(i, d.toString());
                }
            }
        }
    }

    private void operator(String operator) throws ParameterSyntaxException{
        for(int i = 0; i< list.size(); i++){
            String currentElement = list.get(i);
            double a, b, c;
            if (currentElement.equals(operator)) {
                a = Double.parseDouble(list.get(i - 1));
                b = Double.parseDouble(list.get(i + 1));
                switch (operator){
                    case "^":
                        c = Math.pow(a, b);
                        break;
                    case "*":
                        c = a*b;
                        break;
                    case "/":
                        c = a/b;
                        break;
                    case "+":
                        c = a+b;
                        break;
                    default:
                        throw new ParameterSyntaxException("Operator sequence error");
                }
                list.set(i, Double.toString(c));
                list.remove(i+1);
                list.remove(i-1);
                i--;
            }
        }
    }

    private void removeLonelyTokens() throws ParameterSyntaxException{
        for(int i=0; i< list.size(); i++) {
            String listElement = list.get(i);
            if (Arrays.asList(TOKENS_ARRAY).contains(listElement)) {
                if (i != list.size() - 1) {
                    list.set(i, list.get(i) + list.get(i + 1));
                    list.remove(i + 1);
                } else throw new ParameterSyntaxException("Function format error");
            }
        }
    }

    private void compute() throws ArithmeticException, NumberFormatException, ParameterSyntaxException{
        try{
            minus();
            removeLaps();
            calculateDegrees();
            calculateTokens();
            operator("^");
            operator("*");
            operator("/");
            operator("+");
        }catch(IndexOutOfBoundsException e){
            throw new ParameterSyntaxException("Operator sequence error");
        }
        if (list.size()==1){
            String result = list.get(0);
            this.result = formatResult(result);
        }else throw new ParameterSyntaxException("Operator sequence error");
    }

    public static class ParameterSyntaxException extends Exception{
        public ParameterSyntaxException(String str) {super(str);}
    }
}

