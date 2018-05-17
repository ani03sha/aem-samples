package org.anirudh.redquark.aemsamples.core.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	public static void sendEmail(String messageText) {

		/**
		 * Recipient's email address
		 */
		String to = "abc@xyz.com";

		/**
		 * Sender's email address
		 */
		String from = "weather@report.com";

		/**
		 * SMTP host
		 */
		String host = "SMTP HOST";

		/**
		 * Getting system properties
		 */
		Properties properties = System.getProperties();

		/**
		 * Set up email server
		 */
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.user", "test@test.com");

		/**
		 * Getting default session object
		 */
		Session session = Session.getDefaultInstance(properties);

		try {

			/**
			 * Creating default message
			 */
			MimeMessage message = new MimeMessage(session);

			/**
			 * Setting From: header field of the header
			 */
			message.setFrom(new InternetAddress(from));

			/**
			 * Setting To: header field of the header
			 */
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			/**
			 * Setting Subject: header field
			 */
			message.setSubject("Weather Report");

			/**
			 * Setting the actual message
			 */
			message.setText(messageText);

			/**
			 * Send the email
			 */
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
