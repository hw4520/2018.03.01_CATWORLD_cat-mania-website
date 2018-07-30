package contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import member.MemberBean;

import javax.mail.Session;

public class SendEmail2{
	private Connection getConnection() throws Exception{
		Context init=new InitialContext();
		// 占쎌쁽占쎌뜚占쎌벥 占쎌뵠�뵳占� �겫�뜄�쑎占쎌궎疫뀐옙  占쎌쁽占쎌뜚占쎌맄燁삼옙 java:comp/env 占쎌쁽占쎌뜚占쎌뵠�뵳占� jdbc/Mysql
		//DataSource ds=init.lookup(占쎌쁽占쎌뜚占쎌벥 占쎌맄燁삼옙 占쎌쁽占쎌뜚占쎌벥占쎌뵠�뵳占�);
		DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/Mysql");
		Connection con=ds.getConnection();
		
		return con;
		}
 	 public void connectEmail(String email,String sub, String con){
		String to1="lhw4417@naver.com"; // 
		String host="smtp.naver.com"; // 
		String subject=sub; // 
		String fromName="캣월드 회원<"+email+">"; // 
		String from="lhw4417@naver.com"; 
		//String authNum=EmailConfirm.authNum(); // 
		String content=con; //   		
		try{
	    
		Properties props=new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", host);
		props.setProperty
                       ("mail.smtp.socketFactory.class",
                        "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.port","587");
		props.put("mail.smtp.user",from);
		props.put("mail.smtp.auth","true");
		
		Session mailSession 
           = Session.getInstance(props,new javax.mail.Authenticator(){
			    protected PasswordAuthentication getPasswordAuthentication(){
				    return new PasswordAuthentication
                                        ("lhw4417","비공개입니다^^"); // naver�④쑴�젟
			}
		});
		
		Message msg = new MimeMessage(mailSession);
		InternetAddress []address1 = {new InternetAddress(to1)};
		msg.setFrom(new InternetAddress
                      (from, MimeUtility.encodeText(fromName,"utf-8","B")));
		msg.setRecipients(Message.RecipientType.TO, address1); // 獄쏆룆�뮉占쎄텢占쎌뿺 占쎄퐬占쎌젟
		msg.setSubject(subject); // 占쎌젫筌뤴뫗苑뺧옙�젟
		msg.setSentDate(new java.util.Date()); // 癰귣�沅∽옙�뮉 占쎄텊筌욑옙 占쎄퐬占쎌젟
		msg.setContent(content,"text/html; charset=euc-kr"); // 占쎄땀占쎌뒠占쎄퐬占쎌젟
		
		Transport.send(msg); // 筌롫뗄�뵬癰귣�沅→묾占�
		}catch(MessagingException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	   
	}
}



