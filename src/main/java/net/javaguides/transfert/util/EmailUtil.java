package net.javaguides.transfert.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {
    public static void sendEmail(String to, String subject, String body) {
        final String from = "razafitsotraheriniainayonnih@gmail.com"; // changez cette adresse
        final String password = "vlsp ktws rsto dvcz"; // changez ce mot de passe

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // changez l'hôte SMTP
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
        	System.out.println("Error Mail: " + e);
            e.printStackTrace();
        }
    }
}