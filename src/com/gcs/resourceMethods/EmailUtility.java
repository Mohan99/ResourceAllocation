package com.gcs.resourceMethods;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class EmailUtility {
	 public static void sendEmailCall(String toAddress,String attachmentFileName,HSSFWorkbook hssWorkBook,String ccEmails,String mailsubject,String message) throws AddressException, MessagingException
	    {
	    //	String host="smtp.gmail.com"; 
	    	//String port="587";
	    	final String uName="gcstimetracker@gmail.com";
	    	final String password="Gemini@1234";
	    	 String defaultsubject="Employee Login report  file";
	    	 String defaultmessage="Employee report list";
	        // sets SMTP server properties
	        Properties props = new Properties();
	        props.put("mail.smtp.user",uName); 
	        props.put("mail.smtp.host", "smtp.gmail.com"); 
	        props.put("mail.smtp.port", "465"); 
	        props.put("mail.debug", "true"); 
	        props.put("mail.smtp.auth", "true"); 
	        props.put("mail.smtp.starttls.enable","true"); 
	        props.put("mail.smtp.EnableSSL.enable","true");
	        props.setProperty("mail.smtp.**ssl.enable", "true");
	        props.setProperty("mail.smtp.**ssl.required", "true");

	        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
	        props.setProperty("mail.smtp.socketFactory.fallback", "false");   
	      //  props.setProperty("mail.smtp.port", "465");   
	    //    props.setProperty("mail.smtp.socketFactory.port", "465"); 
	 try{
	        // creates a new session with an authenticator
	        Authenticator auth = new Authenticator() {
	            public PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(uName, password);
	            }
	        };
	     
	      String  mMessage=message==null || message.equals("")?defaultmessage:message;
	      String  mSubject=mailsubject==null || mailsubject.equals("") ?defaultsubject:mailsubject;
	        Session session = Session.getInstance(props, auth);
	 
	        // creates a new e-mail message
	        Message msg = new MimeMessage(session);
	 
	        msg.setFrom(new InternetAddress(uName));
	        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
	        
	        if(ccEmails!=null){
	        InternetAddress[] myCcList =InternetAddress.parse(ccEmails);
	        msg.setRecipients(Message.RecipientType.CC, myCcList);
	        }
	        
	        msg.setRecipients(Message.RecipientType.TO, toAddresses);
	       
	        msg.setSubject(mSubject);
	        msg.setSentDate(new Date());
	        msg.setText(mMessage);
	        
	        if(hssWorkBook!=null){
	        DataSource ds = null;
	    
	     // create and fill the first message part
	       MimeBodyPart mimeBodyPart1 = new MimeBodyPart();
	        mimeBodyPart1.setText(message);
	        
	        // create the second message part
	        MimeBodyPart mimeBodyPart2 = new MimeBodyPart();
	        
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        try{
	        hssWorkBook.write(baos);
	        byte[] bytes = baos.toByteArray();
	        ds = new ByteArrayDataSource(bytes, "application/excel");
	        }catch (IOException ioe ){       
	        ioe.printStackTrace();
	        }
	        DataHandler dh = new DataHandler(ds);
	        mimeBodyPart2.setHeader("Content-Disposition", "attachment;filename="+attachmentFileName);
	        mimeBodyPart2.setDataHandler(dh);
	        mimeBodyPart2.setFileName(attachmentFileName);
	        // create the Multipart and add its parts to it
	        Multipart multiPart = new MimeMultipart();
	       multiPart.addBodyPart(mimeBodyPart1);
	        multiPart.addBodyPart(mimeBodyPart2);
	        
	        // add the Multipart to the message
	        msg.setContent(multiPart);
	        	      
	        }
	 
	        // sends the e-mail
	        Transport.send(msg);
	 }catch (MessagingException e) {
	     // TODO Auto-generated catch block
	     e.printStackTrace();
	   //  Transport.close();
	 } 
	 
	    }
}
