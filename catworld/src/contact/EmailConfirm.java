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

public class EmailConfirm{
	private Connection getConnection() throws Exception{
		Context init=new InitialContext();
		// 占쎌쁽占쎌뜚占쎌벥 占쎌뵠�뵳占� �겫�뜄�쑎占쎌궎疫뀐옙  占쎌쁽占쎌뜚占쎌맄燁삼옙 java:comp/env 占쎌쁽占쎌뜚占쎌뵠�뵳占� jdbc/Mysql
		//DataSource ds=init.lookup(占쎌쁽占쎌뜚占쎌벥 占쎌맄燁삼옙 占쎌쁽占쎌뜚占쎌벥占쎌뵠�뵳占�);
		DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/Mysql");
		Connection con=ds.getConnection();
		
		return con;
		}
	public void updateMember(String pass, String email){			
		Connection con=null;
		String sql="";
		PreparedStatement pstmt=null;
		try{con = getConnection();
		//3占쎈뼊�⑨옙 占쎈염野껉퀣�젟癰귣�占쏙옙  占쎌뵠占쎌뒠占쎈퉸占쎄퐣 sql �뤃�됎�占쎌뱽 筌띾슢諭얏�⑨옙 占쎈뼄占쎈뻬占쎈막 揶쏆빘猿� 占쎄문占쎄쉐
//			占쎄땀占쎌삢揶쏆빘猿� Statement, PreparedStatement, CallableStatement
		//String sql="insert into member values('"+id+"','"+pass+"','"+name+"','"+reg_date+"')";
		sql="update member set pass=? where email=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1,pass);
		pstmt.setString(2,email);
		pstmt.executeUpdate();			
		}catch(Exception e) {
			//占쎌굙占쎌뇚 占쎄문疫꿸퀡�늺 癰귨옙占쎈땾 e占쎈퓠 占쏙옙占쎌삢
			//占쎌굙占쎌뇚�몴占� 占쎌삜占쎈툡占쎄퐣 筌ｌ꼶�봺 -> 筌롫뗄�뻻筌욑옙 �빊�뮆�젾
			e.printStackTrace();
			}finally{
				//占쎌굙占쎌뇚揶쏉옙 獄쏆뮇源�占쎈릭占쎈군 筌띾Ŧ諭� 占쎄맒�꽴占쏙옙毓억옙�뵠 筌띾뜄龜�뵳�딆삂占쎈씜 => 疫꿸퀣堉뀐옙�삢占쎈꺖 占쎌젟�뵳占�
				//揶쏆빘猿� 疫꿸퀣堉뀐옙�삢占쎈꺖 筌띾뜄龜�뵳占�
			 if(pstmt!=null){
				try{pstmt.close();						
				}catch(SQLException e){
					e.printStackTrace();
				}
			 }
				if(con!=null){
					try{con.close();
					}catch(SQLException e){
						e.printStackTrace();
					 }
					}				
			}
	}//end updateMember
	public String connectEmail(String email){
		String to1=email; // 
		String host="smtp.naver.com"; // 
		String subject="임시비밀번호 발급"; // 
		String fromName="관리자"; // 
		String from="lhw4417@naver.com"; 
		String authNum=EmailConfirm.authNum(); // 
		String content="임시비밀번호 발급 ["+authNum+"]"; //         
		try{
	    updateMember(authNum,to1);
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
                                        ("lhw4417","aldoaldo1225"); // naver�④쑴�젟
			}
		});
		
		Message msg = new MimeMessage(mailSession);
		InternetAddress []address1 = {new InternetAddress(to1)};
		msg.setFrom(new InternetAddress
                      (from, MimeUtility.encodeText(fromName,"utf-8","B")));
		msg.setRecipients(Message.RecipientType.TO, address1); // 獄쏆룆�뮉占쎄텢占쎌뿺 占쎄퐬占쎌젟
		msg.setSubject(subject); // 占쎌젫筌뤴뫗苑뺧옙�젟
		msg.setSentDate(new java.util.Date()); // 癰귣�沅∽옙�뮉 占쎄텊筌욑옙 占쎄퐬占쎌젟
		msg.setContent(content,"text/html; charset=utf-8"); // 占쎄땀占쎌뒠占쎄퐬占쎌젟
		
		Transport.send(msg); // 筌롫뗄�뵬癰귣�沅→묾占�
		}catch(MessagingException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return authNum;
	}

    // 占쎄텆占쎈땾獄쏆뮇源� function
	public static String authNum(){
		StringBuffer buffer=new StringBuffer();
		for(int i=0;i<=4;i++){
			int num=(int)(Math.random()*9+1);
			buffer.append(num);
		}
		return buffer.toString();
	}
}



