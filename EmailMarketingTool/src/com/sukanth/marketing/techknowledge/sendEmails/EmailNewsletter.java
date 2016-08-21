package com.sukanth.marketing.techknowledge.sendEmails;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class EmailNewsletter {
    	public static Connection con = null;
	public static PreparedStatement pstmt = null;
	public static ResultSet rs = null;
	public static int counter = 0;
	public static String selectQuery = "select * from temp where status ='A' and debugJavaPrgm='N'";
	public static String updateQuery = "UPDATE temp SET debugJavaPrgm=? where email=?";
	public static String updateRevertQuery = "UPDATE temp SET debugJavaPrgm=? where email=?";
	public static String subject = "Video Tutorial - How to Debug a java program in eclipse and other tips or shortcuts ";
	public static ArrayList<String> errorEmailAddress = new ArrayList<String>();
	public static void main(String args[]) {
	    try{
	    ArrayList<String> names = new ArrayList<>();
	    ArrayList<String> emailAddress = new ArrayList<>();
	    con = getConnection();
	    pstmt = con.prepareStatement(selectQuery);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		emailAddress.add(rs.getString("email").trim());
		names.add(rs.getString("name"));
	    }
	    for(String toEmail : emailAddress){
		sendEmailAlert(toEmail);
		pstmt = con.prepareStatement(updateQuery);
		pstmt.setString(1, "Y");
		pstmt.setString(2, toEmail);
		pstmt.executeUpdate();
		System.out.println(counter+"   : email sent to "+toEmail);
	    }
	    if(errorEmailAddress.size()>0){
	    for(String errorEmail:errorEmailAddress){
		    pstmt = con.prepareStatement(updateRevertQuery);
		    pstmt.setString(1, "N");
		    pstmt.setString(2, errorEmail);
		    pstmt.executeUpdate();
		    }
	    }
	    }
	    catch(Exception e){
		e.printStackTrace();
	    }
	    finally{
		try {
		    con.close();
		} catch (SQLException e) {
		    System.out.println("Error occured while closing connection");
		}
	    }
    }
	
	/**@author sukanthgunda
	 * Send an email newsletter
	 */
    public static void sendEmailAlert(String toEmail) {
	
	counter = counter +1;
	final String username = "contact.sukanth@gmail.com";
	final String password = "sukindi@";
	 
	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");
	
	Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	    protected PasswordAuthentication getPasswordAuthentication() {
	        return new PasswordAuthentication(username, password);
	    }
	});	
	  
	try {
	    MimeMessage message = new MimeMessage(session);
	    message.setFrom(new InternetAddress("contact.sukanth@gmail.com","TechKnowledge"));
	    message.setRecipients(Message.RecipientType.TO,
		    InternetAddress.parse(toEmail));
	    message.setSubject(subject);
	    Multipart multipart = new MimeMultipart("mixed");
	    MimeBodyPart messageBodyPart = new MimeBodyPart();
	    MimeBodyPart imagePart = new MimeBodyPart();
	    imagePart.attachFile("/Users/sukanthgunda/Documents/ems_workspace/EmailMarketingTool/images/video-1.jpg");
	    imagePart.setContentID("<myVideo>");
	    imagePart.setDisposition(MimeBodyPart.INLINE);
	    multipart.addBodyPart(imagePart);
	    DataSource htmlcontent = new FileDataSource("/Users/sukanthgunda/Documents/ems_workspace/EmailMarketingTool/htmlContent/simple-announcement.html");
	    messageBodyPart.setDataHandler( new DataHandler(htmlcontent));
	    messageBodyPart.setFileName("newsletter.html");
	    messageBodyPart.setHeader("Content-Type", htmlcontent.getContentType());
	    messageBodyPart.setDisposition(MimeBodyPart.INLINE);
	    multipart.addBodyPart(messageBodyPart);
	    message.setContent(multipart);
	    Transport.send(message);	    
	} catch (Exception e) {
	    errorEmailAddress.add(toEmail);
	    e.printStackTrace();
	}

    }
    
/**@author sukanthgunda
 * Get Connection to mysql DataBase
 */
    public static Connection getConnection() {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    con = (Connection) DriverManager.getConnection(
		    "jdbc:mysql://localhost/emailMarketing", "root", "sukanth");
	    System.out.println("Connection to DataBase Established");
	    System.out.println(
		    "-------------------------------------------------------");
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return con;
    }
}
   