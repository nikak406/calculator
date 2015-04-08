package calculator.soap;
import calculator.logic.Calculator;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService()
public class SOAPEndpoint {
	@WebMethod
	public String calculate(String from) {
		return Calculator.calculate(from);
	}

	public static void main(String[] argv) {
		Object implementer = new SOAPEndpoint();
		System.out.println("Trying to start service");
		String address = "http://localhost:9000/CalculatorSOAP";
		Endpoint.publish(address, implementer);
		System.out.println("Published!");
	}
}
