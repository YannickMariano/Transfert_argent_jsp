package net.javaguides.transfert.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.javaguides.transfert.dao.TransfertDAO;
import net.javaguides.transfert.model.Client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Servlet implementation class transfertServlet
 */
@WebServlet("/transfert")

public class TransfertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TransfertDAO transfertDAO;
	
	 @Override
    public void init() {
        transfertDAO = new TransfertDAO();
    }

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String senderNumTel = request.getParameter("senderNumTel");
        String receiverNumTel = request.getParameter("receiverNumTel");
        int amount = Integer.parseInt(request.getParameter("amount"));

        try {
            Client sender = transfertDAO.getClientByNumTel(senderNumTel);
            Client receiver = transfertDAO.getClientByNumTel(receiverNumTel);

            if (sender != null && receiver != null && sender.getSolde() >= amount) {
                transfertDAO.updateSolde(senderNumTel, sender.getSolde() - amount);
                transfertDAO.updateSolde(receiverNumTel, receiver.getSolde() + amount);

                sendEmail(sender.getMail(), "Transfert de fonds", "Vous avez transféré " + amount + " à " + receiver.getNumtel());
                sendEmail(receiver.getMail(), "Transfert de fonds reçu", "Vous avez reçu " + amount + " de " + sender.getNumtel());

                response.sendRedirect("client-list.jsp");
            } else {
                response.getWriter().println("Échec du transfert. Vérifiez les numéros de téléphone et le solde.");
            }
        } catch (SQLException | MessagingException e) {
            throw new ServletException(e);
        }
    }

    private void sendEmail(String to, String subject, String messageText) throws MessagingException {
        String from = "r.yannick.mariano@example.com"; // Remplacez par votre email
        String host = "smtp.gmail.com"; // Remplacez par votre serveur SMTP
        String username = "r.yannick.mariano@gmail.com"; // Remplacez par votre email
        String password = "#01YannicK#"; // Remplacez par votre mot de passe

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(messageText);

        Transport.send(message);
    }
}
