package calculator.web;

import java.io.IOException;
import calculator.logic.Calculator;
import javax.servlet.http.HttpServlet;

@javax.servlet.annotation.WebServlet(name = "CalcServlet", urlPatterns = "/")
public class CalcServlet extends HttpServlet {

	protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		String eq = request.getParameter("equation");
		String result = Calculator.calculate(eq);
		request.setAttribute("equation", eq);
		request.setAttribute("result", result);
		doGet(request, response);
	}

	protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
}
