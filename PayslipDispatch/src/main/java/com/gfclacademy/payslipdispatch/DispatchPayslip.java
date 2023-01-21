/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gfclacademy.payslipdispatch;

import java.io.IOException;
import java.io.File;
import java.util.Properties;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.text.PDFTextStripper;
import java.util.*;  
//import javax.mail.*;  
//import javax.mail.internet.*;  
import javax.activation.*; 

/**
 *
 * @author DownTown
 */
public class DispatchPayslip {
    
    PDFTextStripper pageContent = new PDFTextStripper();
    
    public DispatchPayslip(PDDocument doc, String email, String filename) throws IOException{
        String to=email;
        final String user="gfclcodevalley@gmail.com"; 
        final String password="1973April13**"; 

        //1) get the session object     
        Properties properties = System.getProperties();  
        properties.setProperty("mail.smtp.host", "localhost");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties,  
         new javax.mail.Authenticator() {  
         protected PasswordAuthentication getPasswordAuthentication() {  
         return new PasswordAuthentication(user,password);  
         }  
        });  

        //2) compose message     
        try{  
          MimeMessage message = new MimeMessage(session);  
          message.setFrom(new InternetAddress(user));  
          message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
          message.setSubject("Payslip Notification");  

          //3) create MimeBodyPart object and set your message text     
          BodyPart messageBodyPart1 = new MimeBodyPart();  
          messageBodyPart1.setText("Please find attached your payslip for this mpnth");  

          //4) create new MimeBodyPart object and set DataHandler object to this object      
          MimeBodyPart messageBodyPart2 = new MimeBodyPart();  

          //String payslipfile = filename;//change accordingly  
          DataSource source = new FileDataSource(filename);  
          messageBodyPart2.setDataHandler(new DataHandler(source));  
          messageBodyPart2.setFileName(filename);  


          //5) create Multipart object and add MimeBodyPart objects to this object      
          Multipart multipart = new MimeMultipart();  
          multipart.addBodyPart(messageBodyPart1);  
          multipart.addBodyPart(messageBodyPart2);  

          //6) set the multiplart object to the message object  
          message.setContent(multipart );  

          //7) send message  
          Transport.send(message);  

         System.out.println("Payslip sent successfully to "+email);  
         }catch (MessagingException ex) {ex.printStackTrace();} 
        
    }
    
}
