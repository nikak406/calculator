package calculator.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Calculator {

	private static final String DELIMITERS =   "+-/*^()";
	private static final Double PI = Math.PI;
	private static final Double EXP = Math.exp(1);

	private static final String TOKENS_ARRAY[]= {"sinh",    "cosh",    "tanh",    "sin",    "cos",    "tan",    "asin",
			"acos",    "atan",    "abs",    "exp",    "sqrt",    "log",    "ln"};

	private List<String> mList;
	private double result;

	public Calculator(List<String> list) throws ParameterSyntaxException{
		mList = list;
		calculate();
	}

	public Calculator(String str) throws ParameterSyntaxException, ArithmeticException, NumberFormatException {
		createList(str);
		calculate();
	}

	private void setResult(double res) { result = res; }

	public String getResult() {
		String s = (new Double(roundResult(result))).toString();
		if (s.endsWith(".0")) s=s.substring(0, s.length()-2);
		return  s;
	}

	private boolean containsLaps() throws ParameterSyntaxException {
		int laps = 0;
		for (String aList : mList) {
			if (aList.equals("("))
				laps++;
			if (aList.equals(")"))
				laps--;
			if (laps < 0)
				throw new ParameterSyntaxException("Laps order error");
		}
		if (laps!=0) throw new ParameterSyntaxException("Laps quantity error");
		return mList.contains("(");
	}

	private int openLapIndex(){ return mList.indexOf("(");}

	private int closeLapIndex() throws ParameterSyntaxException{
		int count = 1;
		for (int i = openLapIndex()+1; i< mList.size(); i++){
			if (mList.get(i).equals("(")) count++;
			if (mList.get(i).equals(")")) count--;
			if (count==0) return i;
		}
		throw new ParameterSyntaxException("Laps error");
	}

	private void removeLaps() throws ParameterSyntaxException{
		while (containsLaps()){
			List<String> Laps = mList.subList(openLapIndex(), closeLapIndex()+1);
			List<String> InsideLaps = Laps.subList(1, Laps.size()-1);
			new Calculator(InsideLaps);
			mList.remove(closeLapIndex());
			mList.remove(openLapIndex());
		}
	}

	private void unarMinus(){
		for(int i = 0; i< mList.size(); i++){
			String str2 = mList.get(i);
			if (str2.equals("-")&&(i==0)) {
				mList.set(1, "-"+ mList.get(1));
				mList.remove(0);
				continue;
			}
			if (i>0) if (str2.equals("-") && DELIMITERS.contains(mList.get(i-1))){
				mList.set(i+1, "-"+ mList.get(i+1));
				mList.remove(i);
				continue;
			}
			if (i>0) {
				Character c = (mList.get(i-1).charAt(mList.get(i-1).length()-1));
				if ( str2.equals("-") && !c.equals('d') &&
						(!mList.get(i-1).endsWith("pi")) && !Character.isDigit(c)){
					mList.set(i-1, mList.get(i-1)+ mList.get(i)+ mList.get(i+1));
					mList.remove(i+1);
					mList.remove(i);
					i--;
				}
			}
		}
	}

	private void calculateDegrees() throws NumberFormatException{

		for(int i=0; i< mList.size(); i++){
			String s = mList.get(i);
			Double d;
			if (!DELIMITERS.contains(s) && s.endsWith("d")){
				int k=0;
				while(!Character.isDigit(s.charAt(k))){
					if (k == s.length()-1) break;
					k++;
				}
				d = Double.parseDouble(s.substring(k, s.length()-1));
				d = Math.toRadians(d);
				mList.set(i, s.substring(0, k)+d.toString());
			}
		}
	}

	private void calculateTokens() throws NumberFormatException, ArithmeticException, ParameterSyntaxException{
		removeLonelyTokens();
		for(int i=0; i< mList.size(); i++){
			String s = mList.get(i);
			Double d;
			int sign = 1;
			if (s.startsWith("-")){
				sign = -1;
				s = s.substring(1);
			}
			if (!DELIMITERS.contains(s)){
				if (s.startsWith("ln")) {
					d = Double.parseDouble(s.substring(2));
					d = sign*Math.log(d);
					mList.set(i, d.toString());
				}
				if (s.startsWith("log")) {
					d = Double.parseDouble(s.substring(3));
					d = sign*Math.log10(d);
					mList.set(i, d.toString());
				}
				if (s.startsWith("abs")) {
					d = sign*Math.abs(Double.parseDouble(s.substring(3)));
					mList.set(i, d.toString());
				}
				if (s.startsWith("sin")) {
					d = sign*Math.sin(Double.parseDouble(s.substring(3)));
					mList.set(i, d.toString());
				}
				if (s.startsWith("cos")) {
					d = sign*Math.cos(Double.parseDouble(s.substring(3)));
					mList.set(i, d.toString());
				}
				if (s.startsWith("tan")) {
					d = sign*Math.tan(Double.parseDouble(s.substring(3)));
					mList.set(i, d.toString());
				}
				if (s.startsWith("sqrt")) {
					d = Double.parseDouble(s.substring(4));
					d = sign*Math.sqrt(d);
					mList.set(i, d.toString());
				}
				if (s.startsWith("sinh")) {
					d = sign*Math.sinh(Double.parseDouble(s.substring(4)));
					mList.set(i, d.toString());
				}
				if (s.startsWith("tanh")) {
					d = sign*Math.tanh(Double.parseDouble(s.substring(4)));
					mList.set(i, d.toString());
				}
				if (s.startsWith("acos")) {
					d = sign*Math.acos(Double.parseDouble(s.substring(4)));
					mList.set(i, d.toString());
				}
				if (s.startsWith("asin")) {
					d = sign*Math.asin(Double.parseDouble(s.substring(4)));
					mList.set(i, d.toString());
				}
				if (s.startsWith("exp")) {
					d = sign*Math.exp(Double.parseDouble(s.substring(3)));
					mList.set(i, d.toString());
				}
				if (s.startsWith("atan")) {
					d = sign*Math.atan(Double.parseDouble(s.substring(4)));
					mList.set(i, d.toString());
				}
			}
		}
	}

	private void operator(String s) throws ParameterSyntaxException{
		for(int i = 0; i< mList.size(); i++){
			String str2 = mList.get(i);
			double a, b, c;
			if (str2.equals(s)) {
				a = Double.parseDouble(mList.get(i - 1));
				b = Double.parseDouble(mList.get(i + 1));
				switch (s){
				case "^": c = Math.pow(a, b); break;
				case "*": c = a*b; break;
				case "/": c = a/b; break;
				case "+": c = a+b; break;
				case "-": c = a-b; break;
				default: throw new ParameterSyntaxException("Operator sequence error");
				}
				mList.set(i, (new Double(c)).toString());
				mList.remove(i+1);
				mList.remove(i-1);
				i--;
			}
		}
	}

	private void removeLonelyTokens() throws ParameterSyntaxException{
		for(int i=0; i< mList.size(); i++) {
			String listElement = mList.get(i);
			for (String token : TOKENS_ARRAY) {
				if (listElement.equals(token)) {
					if (i != mList.size() - 1) {
						mList.set(i, mList.get(i) + mList.get(i + 1));
						mList.remove(i + 1);
					} else throw new ParameterSyntaxException("Syntax error");
				}
			}
		}
	}

	private double roundResult(double x){
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

	private void createList(String equation) throws ParameterSyntaxException{
		equation = equation.replaceAll(" ", "");
		equation = equation.toLowerCase();
		equation = equation.replaceAll("pi", (PI).toString());
		equation = equation.replaceAll("exp(1)", (EXP).toString());
		if (equation.isEmpty()) throw new ParameterSyntaxException("String is empty");
		while (equation.endsWith("=")) {
			equation = equation.substring(0, equation.length() - 1);
		}
		if (equation.startsWith("+")) equation = equation.substring(1);
		StringTokenizer stringTokenizer = new StringTokenizer(equation, DELIMITERS, true);
		mList = new ArrayList<>(50);
		while (stringTokenizer.hasMoreTokens()) mList.add(stringTokenizer.nextToken());
	}

	private void calculate() throws ArithmeticException, NumberFormatException, ParameterSyntaxException{
		try{
			removeLaps();
			unarMinus();
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
		if (mList.size()==1){
			double x = Double.parseDouble(mList.get(0));
			setResult(x);
		}else throw new ParameterSyntaxException("Operator sequence error");
	}
}

