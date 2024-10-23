package pe.edu.utp.integradori.proyectofinal.handler;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public abstract class EmailHandler {

    public static void sendEmail(String to, String subject, String body) {
        Dotenv dotenv = Dotenv.load();
        String username = dotenv.get("GMAIL_USERNAME");
        String appPassword = dotenv.get("GMAIL_APP_PASSWORD");

        if(username == null || appPassword == null) return;
        if(username.isEmpty() || appPassword.isEmpty()) return;

        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator(username, appPassword));
            email.setSSLOnConnect(true);
            email.setFrom(username);
            email.setSubject(subject);
            email.setMsg(body);
            email.addTo(to);
            email.send();
        } catch (EmailException e) {
            System.out.println("Correo no enviado!");
            System.out.println(e.getMessage());
        }
    }

}
