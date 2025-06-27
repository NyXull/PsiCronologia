package model.services;

import jakarta.mail.PasswordAuthentication;
import java.util.Properties;

import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService {
	
	private final String remetente = "psicronologia@gmail.com";
	private final String nomeRemetente = "PsiCronologia";
	private String password;
	private final String userName = "psicronologia@gmail.com";
	
	public void enviarEmail(String assunto, String msg, String endereco) {
		
		password = System.getenv("SENHA_GOOGLE_JAVAMAIL");
		
		try {
			Properties properties = getSmtpProperties();
			
			Session session = Session.getInstance(properties, new Authenticator() {
				
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			});
			
			session.setDebug(false);
			
			Address[] distinatario = InternetAddress.parse(endereco);
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(remetente, nomeRemetente));
			message.setRecipients(Message.RecipientType.TO, distinatario);
			message.setSubject(assunto);
			message.setContent(msg, "text/html; charset=utf-8");
			
			Transport.send(message);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Properties getSmtpProperties() {		
	    Properties properties = new Properties();
	   
	    properties.put("mail.smtp.host", "smtp.gmail.com");
	    properties.put("mail.smtp.port", "587");
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.smtp.starttls.enable", "true");
	    properties.put("mail.smtp.starttls.required", "true");
	    properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	    properties.put("mail.smtp.ssl.checkserveridentity", "true");
	   
	    return properties;
	}
}