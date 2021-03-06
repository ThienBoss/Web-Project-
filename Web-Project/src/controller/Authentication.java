package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import model.User;

/**
 * Servlet implementation class RegistrantionController
 */
@WebServlet("/Authentication")
public class Authentication extends HttpServlet {
    private UserDao userDao;
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws ClassNotFoundException 
     * @throws SQLException 
     * @see HttpServlet#HttpServlet()
     */
    public Authentication() throws ClassNotFoundException, SQLException {
        super();
        userDao = new UserDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
        response.setContentType("text/html");
        String fullName = "";
        String email = request.getParameter("Email");
        String userName = request.getParameter("signUpName");
        System.out.println("This is user name :" + userName);
        String password = request.getParameter("signUpPassword");
        String rePassword = request.getParameter("signUpRePassword");
        int age = Integer.parseInt(request.getParameter("Age"));
        int donate = 0;
       
        try {
        	if (password.equals(rePassword)){
            User user = new User(fullName,email,userName,password,age,donate);
            userDao.save(user);
            RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
            dispatcher.forward(request,response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("FailedPopup.jsp");
            dispatcher.forward(request,response);
        }
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
        String userName = request.getParameter("loginName");
        String password = request.getParameter("loginPassword");
        if (userDao.validateLogin(userName, password)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("RentPlayer.jsp");
            dispatcher.forward(request,response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("FailedPopup.jsp");
            dispatcher.forward(request,response);
        }
	}

}
