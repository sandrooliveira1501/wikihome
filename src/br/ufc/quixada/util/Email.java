package br.ufc.quixada.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email{

	public static final String emailAltorizacao = "wikiape@gmail.com";
	public static final String senhaAltorizacao = "danrleygay";

	public static void enviarEmail(String remetente, String destinatario, String assunto, String mensagem){
		Properties props = new Properties();
		
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		Session session = Session.getDefaultInstance(props, 
				new javax.mail.Authenticator(){
					protected PasswordAuthentication getPasswordAuthentication(){
						return new PasswordAuthentication(Email.emailAltorizacao, Email.senhaAltorizacao);
					}
				});
		session.setDebug(true);
		
		try{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(remetente));
			Address toUser[] = InternetAddress.parse(destinatario);
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(assunto);
			message.setContent(mensagem, "text/html; charset=ISO-8859-1;");
			
			Transport.send(message);
			
		} catch(MessagingException e){
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			//tratamento RuntimeException
		}
	}
}
