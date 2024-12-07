package com.lara.c2c.service;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.mail.internet.MimeMessage;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lara.c2c.constants.ApplicationConfig;
import com.lara.c2c.dto.BaseResponse;
import com.lara.c2c.dto.learner.LearnerBulkEmailRequest;
import com.lara.c2c.dto.learner.LearnerBulkEmailResponse;
import com.lara.c2c.dto.user.SendEmailResponse;
import com.lara.c2c.model.ExceptionReport;

@Service
public class MailService {
	
	@Autowired
    private JavaMailSender sender;
	
	@Value("${admin.mail.username}")
	private String scheduleMailUsername;
	
	@Value("${admin.mail.password}")
	private String scheduleMailPassword;
	
	@Value("${spring.mail.host}")
	private String mailHost;
	
	@Value("${spring.mail.port}")
	private int mailPort;
	
	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private boolean smtpStarttlsEnable;
	
	@Value("${spring.mail.properties.mail.smtp.starttls.required}")
	private boolean smtpStarttlsRequired;
	
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private boolean mailSmtpAuthRequired;
	
	@Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
	private int msilSMtpConTimeOut;
	
	@Value("${spring.mail.properties.mail.smtp.timeout}")
	private int mailSmtpTimeOut;
	
	@Value("${spring.mail.properties.mail.smtp.writetimeout}")
	private int mailSmtpWriteTimeOut;
	
	public void sendEmail(String email, String activationCode, String userId, String firstName) throws Exception{	
		System.out.println("mail initiation");
        MimeMessage message = sender.createMimeMessage(); 
        MimeMessageHelper helper = new MimeMessageHelper(message);        
        helper.setTo(email);
        
        String htmlTemplate = getAccountActivationTemplate(userId, activationCode, firstName);
        
        helper.setText(htmlTemplate, true);
        //helper.setText(activationCode);

        helper.setSubject("Mail from Lara ==> Activate your profile");
        System.out.println("mail sending1");
        sender.send(message);
        System.out.println("mail sending2");        
    }
	
	public void sendPwdResetRequestEmail(String email, String activationCode, String userId, String firstName) throws Exception{	    	
        MimeMessage message = sender.createMimeMessage(); 
        MimeMessageHelper helper = new MimeMessageHelper(message);        
        helper.setTo(email);
        
        String htmlTemplate = getPwdRequestTemplate(userId, activationCode, firstName);
        
        helper.setText(htmlTemplate, true);
        //helper.setText(activationCode);

        helper.setSubject("Reset Password");        
        sender.send(message);
    }
	
	public String getAccountActivationTemplate(String userId, String activationCode, String firstName) {
		String htmlTemplate = "<!doctype html>\n" + 
        		"<html>\n" + 
        		"<head>\n" + 
        		"<meta charset='utf-8'>\n" + 
        		"<title>Untitled Document</title>\n" + 
        		"	<style>\n" + 
        		"		body{margin: 0; padding: 0; background-color: #F0F0F0;}\n" + 
        		"		\n" + 
        		"		@media only screen and (max-width: 540px) {\n" + 
        		"			\n" + 
        		"			\n" + 
        		"		}\n" + 
        		"	</style>\n" + 
        		"</head>";
		 htmlTemplate += "<body>\n" + 
	        		"	<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>\n" + 
	        		"  <tbody>\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='border:2px solid #EC830D; background-color: #ffffff;'>\n" + 
	        		"		<table width='100%' border='0' cellspacing='0' cellpadding='0'>\n" + 
	        		"  <tbody>\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='padding: 20px 0 0 20px;'><img src='https://lara.co.in/assets/images/laralogo.png' border='0' alt='logo' style='width: 100px;'/></td>\n" + 
	        		"    </tr>\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='font-family: Arial, sans-serif; font-size:14px; padding-left:20px; padding-top: 30px;'><span>Dear "+firstName+" ,</span><br/>\n" + 
	        		"\n" + 
	        		"<p style='line-height: 22px;'>Thank you for registering with us.<br>\n" + 
	        		"Please  click on below button to activate your profile.</p></td>\n" + 
	        		"    </tr>\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='padding: 10px 0 40px 0;'><table border='0' cellspacing='0' cellpadding='0' width='auto' align='center'>\n" + 
	        		"  <tbody>\n" + 
	        		"\n" + 
	        		"\n" + 
	        		"\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='background-color:#0C6ECE; padding:6px 20px;'>\n" + 
	        		"        <a href='"+ ApplicationConfig.APPURL + "/#/activate/"+userId+"/"+activationCode+        		
	        		"' target='_blank' style='color:#ffffff; font-family: Arial, sans-serif; text-decoration: none; font-size:12px; text-transform: uppercase;' >Activate</a></td>\n" + 
	        		"    </tr>\n" + 
	        		"  </tbody>\n" + 
	        		"</table>\n" + 
	        		"</td>\n" + 
	        		"    </tr>\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='font-family: Arial, sans-serif; font-size:12px; padding: 10px 0 10px 0; background-color:#EC830D; color:#ffffff;' align='center'>© 2019 Laratechnology.</td>\n" + 
	        		"    </tr>\n" + 
	        		"  </tbody>\n" + 
	        		"</table>\n" + 
	        		"\n" + 
	        		"		\n" + 
	        		"		</td>\n" + 
	        		"    </tr>\n" + 
	        		"  </tbody>\n" + 
	        		"</table>\n" + 
	        		"\n" + 
	        		"</body>\n" + 
	        		"</html>";
		 return htmlTemplate;
	}
	
	public String getPwdRequestTemplate(String userId, String activationCode, String firstName) {
		String htmlTemplate = "<!doctype html>\n" + 
        		"<html>\n" + 
        		"<head>\n" + 
        		"<meta charset='utf-8'>\n" + 
        		"<title>Untitled Document</title>\n" + 
        		"	<style>\n" + 
        		"		body{margin: 0; padding: 0; background-color: #F0F0F0;}\n" + 
        		"		\n" + 
        		"		@media only screen and (max-width: 540px) {\n" + 
        		"			\n" + 
        		"			\n" + 
        		"		}\n" + 
        		"	</style>\n" + 
        		"</head>";
		 htmlTemplate += "<body>\n" + 
	        		"	<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>\n" + 
	        		"  <tbody>\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='border:2px solid #EC830D; background-color: #ffffff;'>\n" + 
	        		"		<table width='100%' border='0' cellspacing='0' cellpadding='0'>\n" + 
	        		"  <tbody>\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='padding: 20px 0 0 20px;'><img src='https://lara.co.in/assets/images/laralogo.png' border='0' alt='logo' style='width: 100px;'/></td>\n" + 
	        		"    </tr>\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='font-family: Arial, sans-serif; font-size:14px; padding-left:20px; padding-top: 30px;'><span>Dear "+firstName+ ",</span><br/>\n" + 
	        		"\n" + 
	        		"<p style='line-height: 22px;'>We have got a request to reset your password.<br>\n" + 
	        		"Please  click on below button to reset your password.</p></td>\n" + 
	        		"    </tr>\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='padding: 10px 0 40px 0;'><table border='0' cellspacing='0' cellpadding='0' width='auto' align='center'>\n" + 
	        		"  <tbody>\n" + 
	        		"\n" + 
	        		"\n" + 
	        		"\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='background-color:#0C6ECE; padding:6px 20px;'>\n" + 
	        		"        <a href='"+ ApplicationConfig.APPURL + "/#/resetpwd/"+userId+"/"+activationCode+        		
	        		"' target='_blank' style='color:#ffffff; font-family: Arial, sans-serif; text-decoration: none; font-size:12px; text-transform: uppercase;' >Reset Password</a></td>\n" + 
	        		"    </tr>\n" + 
	        		"  </tbody>\n" + 
	        		"</table>\n" + 
	        		"</td>\n" + 
	        		"    </tr>\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='font-family: Arial, sans-serif; font-size:12px; padding: 10px 0 10px 0; background-color:#EC830D; color:#ffffff;' align='center'>© 2019 Laratechnology.</td>\n" + 
	        		"    </tr>\n" + 
	        		"  </tbody>\n" + 
	        		"</table>\n" + 
	        		"\n" + 
	        		"		\n" + 
	        		"		</td>\n" + 
	        		"    </tr>\n" + 
	        		"  </tbody>\n" + 
	        		"</table>\n" + 
	        		"\n" + 
	        		"</body>\n" + 
	        		"</html>";
		 return htmlTemplate;
	}
	
	public LearnerBulkEmailResponse _sendBulkEmailToLearners(LearnerBulkEmailRequest request){	  
		
		JavaMailSender adminMailSender = getJavaMailSender();
		LearnerBulkEmailResponse response = new LearnerBulkEmailResponse();
		
		String mailSubject = request.getMailSubject();
		
		for(int i=0; i< request.getUserEmailsList().size(); i++) {
			
			String mailParams [] = request.getUserEmailsList().get(i).split(":");
			String toMail = mailParams[0];
			String deactivationCode = mailParams[1];
			MimeMessage message = adminMailSender.createMimeMessage(); 
	        MimeMessageHelper helper = new MimeMessageHelper(message);        
	        
	        try {
	        	helper.setTo(toMail);		        
	        	String htmlTemplate = getMailTemplate(request.getMailContent());
	    		String unsubscriptionLink = "<br><br><a href='"+ ApplicationConfig.REDIRECT_UNSUBSCRIBE_URL+"/"+ toMail+"/"+deactivationCode+ "' >Unsubscribe</a>";
	            htmlTemplate = htmlTemplate + unsubscriptionLink;	        
		        helper.setText(htmlTemplate, true);
		        helper.setSubject(request.getMailSubject());    
		        adminMailSender.send(message);
	        }catch(Exception ex) {
	        	response.setExceptionReport(new ExceptionReport(ex));
	        }
		}
		return response;
    }
	
	public String getMailTemplate(String mailContent) {
		
		return mailContent;
	}

	public SendEmailResponse _sendUnsubscriptionInfo(String email, String activationCode) {
		SendEmailResponse response = new SendEmailResponse();
		JavaMailSender adminMailSender = getJavaMailSender();
		try {
			MimeMessage message = adminMailSender.createMimeMessage(); 
	        MimeMessageHelper helper = new MimeMessageHelper(message);        
	        helper.setTo(email);
	  
	        String htmlTemplate = getUnsubscriptionInfoTemplate(email, activationCode);
	        
	        helper.setText(htmlTemplate, true);
	        //helper.setText(activationCode);

	        helper.setSubject("Unsubscribed from mail");        
	        adminMailSender.send(message);
		}catch(Exception ex) {
			response.setExceptionReport(new ExceptionReport(ex));
		}		
		return response;
	}
	
	public String getUnsubscriptionInfoTemplate(String email, String activationCode) {
		String htmlTemplate = "<!doctype html>\n" + 
        		"<html>\n" + 
        		"<head>\n" + 
        		"<meta charset='utf-8'>\n" + 
        		"<title>Untitled Document</title>\n" + 
        		"	<style>\n" + 
        		"		body{margin: 0; padding: 0; background-color: #F0F0F0;}\n" + 
        		"		\n" + 
        		"		@media only screen and (max-width: 540px) {\n" + 
        		"			\n" + 
        		"			\n" + 
        		"		}\n" + 
        		"	</style>\n" + 
        		"</head>";
		 htmlTemplate += "<body>\n" + 
	        		"	<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>\n" + 
	        		"  <tbody>\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='border:2px solid #EC830D; background-color: #ffffff;'>\n" + 
	        		"		<table width='100%' border='0' cellspacing='0' cellpadding='0'>\n" + 
	        		"  <tbody>\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='padding: 20px 0 0 20px;'><img src='https://lara.co.in/assets/images/laralogo.png' border='0' alt='logo' style='width: 100px;'/></td>\n" + 
	        		"    </tr>\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='font-family: Arial, sans-serif; font-size:14px; padding-left:20px; padding-top: 30px;'><span>Dear Learner, </span><br/>\n" + 
	        		"\n" + 
	        		"<p style='line-height: 22px;'>We have unsubscribed your email id from daily mailing list. But whenever you wanted to subscribe from daily mailing, you are always welcome.<br>\n" + 
	        		"Please  click on below button to subscribe.</p></td>\n" + 
	        		"    </tr>\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='padding: 10px 0 40px 0;'><table border='0' cellspacing='0' cellpadding='0' width='auto' align='center'>\n" + 
	        		"  <tbody>\n" + 
	        		"\n" + 
	        		"\n" + 
	        		"\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='background-color:#0C6ECE; padding:6px 20px;'>\n" + 
	        		"        <a href='"+ ApplicationConfig.REDIRECT_SUBSCRIBE_URL + "/"+email +"/" +activationCode+        		
	        		"' target='_blank' style='color:#ffffff; font-family: Arial, sans-serif; text-decoration: none; font-size:12px; text-transform: uppercase;' >Subscribe</a></td>\n" + 
	        		"    </tr>\n" + 
	        		"  </tbody>\n" + 
	        		"</table>\n" + 
	        		"</td>\n" + 
	        		"    </tr>\n" + 
	        		"    <tr>\n" + 
	        		"      <td style='font-family: Arial, sans-serif; font-size:12px; padding: 10px 0 10px 0; background-color:#EC830D; color:#ffffff;' align='center'>© 2019 Laratechnology.</td>\n" + 
	        		"    </tr>\n" + 
	        		"  </tbody>\n" + 
	        		"</table>\n" + 
	        		"\n" + 
	        		"		\n" + 
	        		"		</td>\n" + 
	        		"    </tr>\n" + 
	        		"  </tbody>\n" + 
	        		"</table>\n" + 
	        		"\n" + 
	        		"</body>\n" + 
	        		"</html>";
		 return htmlTemplate;
	}
	
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(mailHost);
	    mailSender.setPort(mailPort);
	     
	    mailSender.setUsername(scheduleMailUsername);
	    mailSender.setPassword(scheduleMailPassword);
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");	    
	    props.put("mail.smtp.auth", mailSmtpAuthRequired);
	    props.put("mail.smtp.starttls.enable", smtpStarttlsEnable);
	    props.put("mail.smtp.starttls.required", smtpStarttlsRequired);
	    props.put("mail.smtp.connectiontimeout", msilSMtpConTimeOut);
	    props.put("mail.smtp.timeout", mailSmtpTimeOut);
	    props.put("mail.smtp.writetimeout", mailSmtpWriteTimeOut);
	    return mailSender;
	}
}
