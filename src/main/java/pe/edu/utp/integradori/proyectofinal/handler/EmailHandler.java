package pe.edu.utp.integradori.proyectofinal.handler;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EmailHandler {

    public static Logger logger = LoggerFactory.getLogger(EmailHandler.class);

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
            logger.error("Error enviando correo electrónico: ", e);
        }
    }

}
