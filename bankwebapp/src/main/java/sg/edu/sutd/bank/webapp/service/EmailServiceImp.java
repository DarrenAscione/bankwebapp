/*
 * Copyright 2017 SUTD Licensed under the
	Educational Community License, Version 2.0 (the "License"); you may
	not use this file except in compliance with the License. You may
	obtain a copy of the License at

https://opensource.org/licenses/ECL-2.0

	Unless required by applicable law or agreed to in writing,
	software distributed under the License is distributed on an "AS IS"
	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
	or implied. See the License for the specific language governing
	permissions and limitations under the License.
 */

package sg.edu.sutd.bank.webapp.service;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import sg.edu.sutd.bank.webapp.commons.ServiceException;

public class EmailServiceImp implements EmailService {
	private static Properties emailProperties;
	private static String userName;
	private static String password;

	static {
		emailProperties = new Properties();
		ResourceBundle bundle = ResourceBundle.getBundle("email");
		emailProperties.setProperty("mail.smtp.host", bundle.getString("mail.smtp.host"));
		emailProperties.setProperty("mail.smtp.socketFactory.port", bundle.getString("mail.smtp.socketFactory.port"));
		emailProperties.setProperty("mail.smtp.socketFactory.class", bundle.getString("mail.smtp.socketFactory.class"));
		emailProperties.setProperty("mail.smtp.auth", bundle.getString("mail.smtp.auth"));
		emailProperties.setProperty("mail.smtp.port", bundle.getString("mail.smtp.port"));
		userName = bundle.getString("mail.smtp.username");
		password = bundle.getString("mail.smtp.password");
	}

	@Override
	public void sendMail(String toAddr, String subject, String msg) throws ServiceException {
		Session session = Session.getDefaultInstance(emailProperties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from@no-spam.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddr));
			message.setSubject(subject);
			message.setText(msg);
			Transport.send(message);
		} catch (MessagingException e) {
			throw ServiceException.wrap(e);
		}
	}
}
