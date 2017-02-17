package com.prueba;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class SendMail
 */
@WebServlet("/send")
public class SendMailServlet extends HttpServlet {

	final static Logger logger = Logger.getLogger(SendMailServlet.class);

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendMailServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		// Set response content type
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

		out.println(docType + "<html>" + "<body>" + "<form action=\"send\" method=\"POST\">"
				+ "Username: <input type=\"text\" name=\"username\">" + "<br />"
				+ "Password: <input type=\"password\" name=\"password\" />" + "<input type=\"submit\" value=\"Submit\" />"
				+ " </form> " + "</body>" + "</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sendEmail(request.getParameter("username"), request.getParameter("password"));
	}

	public void sendEmail(String username, String password) {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.host", "smtp.office365.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			String htmlBody = "<strong>This is an HTML Message</strong>";
			String textBody = "This is a Text Message.";
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("e.macarron@iberdrola.es"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("eduardo.macarron@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText(htmlBody);
			message.setContent(textBody, "text/html");
			Transport.send(message);

			logger.info("Done!!");

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
}