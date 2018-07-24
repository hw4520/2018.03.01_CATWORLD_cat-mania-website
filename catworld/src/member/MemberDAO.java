package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;





public class MemberDAO {
			//占쎈탵�뜮袁⑸염野껓옙 筌롫뗄苑뚳옙諭�
			private Connection getConnection() throws Exception{
				Context init=new InitialContext();
				// 占쎌쁽占쎌뜚占쎌벥 占쎌뵠�뵳占� �겫�뜄�쑎占쎌궎疫뀐옙  占쎌쁽占쎌뜚占쎌맄燁삼옙 java:comp/env 占쎌쁽占쎌뜚占쎌뵠�뵳占� jdbc/Mysql
				//DataSource ds=init.lookup(占쎌쁽占쎌뜚占쎌벥 占쎌맄燁삼옙 占쎌쁽占쎌뜚占쎌벥占쎌뵠�뵳占�);
				DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/Mysql");
				Connection con=ds.getConnection();
				
				return con;
				}
				
			
			public void insertMember(MemberBean mb){
				Connection con=null;
				String sql="";
				PreparedStatement pstmt=null;
				try{ //占쎌굙占쎌뇚揶쏉옙 獄쏆뮇源�占쎈막 野껓옙 揶쏆늿占� 筌뤿굝議�, 	占쎈툡占쎈땾占쎌읅占쎌몵嚥∽옙 占쎌뇚�겫占쏙옙�솁占쎌뵬占쎌젔域뱄옙, 占쎈탵�뜮袁⑹젔域뱄옙
					 con = getConnection();
					 sql="insert into member (id,pass,name,email,reg_date,address,phone,mobile) values(?,?,?,?,?,?,?,?)";
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, mb.getId()); // ? 筌ｃ꺂苡뀐쭪占� �눧�눘�벉占쎈ご, 占쎈툡占쎌뵠占쎈탵揶쏉옙
					pstmt.setString(2, mb.getPass()); // ? 占쎈あ甕곕뜆�럮 �눧�눘�벉占쎈ご, �뜮袁⑨옙甕곕뜇�깈揶쏉옙
					pstmt.setString(3, mb.getName()); // ? 占쎄쉭甕곕뜆�럮 �눧�눘�벉占쎈ご, 占쎌뵠�뵳袁㏃빽										
					pstmt.setString(4, mb.getEmail());
					pstmt.setTimestamp(5, mb.getReg_date());
					pstmt.setString(6, mb.getAddress()+" "+mb.getAddress2());
					pstmt.setString(7, mb.getPhone());
					pstmt.setString(8, mb.getMobile());
					pstmt.executeUpdate();
				} catch(Exception e) {
						//占쎌굙占쎌뇚 占쎄문疫꿸퀡�늺 癰귨옙占쎈땾 e占쎈퓠 占쏙옙占쎌삢
						//占쎌굙占쎌뇚�몴占� 占쎌삜占쎈툡占쎄퐣 筌ｌ꼶�봺 -> 筌롫뗄�뻻筌욑옙 �빊�뮆�젾
						e.printStackTrace();
						}finally{
							//占쎌굙占쎌뇚揶쏉옙 獄쏆뮇源�占쎈릭占쎈군 筌띾Ŧ諭� 占쎄맒�꽴占쏙옙毓억옙�뵠 筌띾뜄龜�뵳�딆삂占쎈씜
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
				}//insertMember() 筌롫뗄苑뚳옙諭�
			public int userCheck(String id, String pass){
				int check=0;
				Connection con =null;
				String sql="";
				PreparedStatement  pstmt=null;
				ResultSet rs = null;
				try{
					 con = getConnection();
				//3占쎈뼊�⑨옙 sql	鈺곌퀗援� id揶쏉옙 鈺곕똻�삺占쎈릭占쎈뮉 占쎌돳占쎌뜚占쎌젟癰귨옙 揶쏉옙占쎌죬占쎌궎疫뀐옙
				 sql="select * from member where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				//4占쎈뼊�⑨옙 占쎈탵�뜮袁㏐퍙�⑥눘占쏙옙�삢 揶쏆빘猿� = 占쎈뼄占쎈뻬 野껉퀗�궢 占쏙옙占쎌삢
				rs = pstmt.executeQuery();
				//5占쎈뼊�⑨옙 rs占쎈퓠 占쏙옙占쎌삢占쎈쭆 占쎈쑓占쎌뵠占쎄숲 占쎌젔域뱄옙 占쎈뼄占쎌벉占쎈뻬(筌ｃ꺂苡뀐쭪紐낅뻬) 占쎌뵠占쎈짗 占쎈쑓占쎌뵠占쎄숲 占쎌뿳占쎌몵筌롳옙 "占쎈툡占쎌뵠占쎈탵占쎌뿳占쎌벉"
//													占쎈쨲�뜮袁⑨옙甕곕뜇�깈 占쎈탵�뜮袁⑸퓠占쎄퐣 揶쏉옙占쎌죬占쎌궔 �뜮袁⑨옙甕곕뜇�깈 �뜮袁㏉꺍
//																										 筌띿쉸�몵筌롳옙 占쎄쉭占쎈�▼첎誘り문占쎄쉐 "id", 揶쏉옙
//																													main.jsp 占쎌뵠占쎈짗
//																										 占쏙옙�뵳�됥늺 "�뜮袁⑨옙甕곕뜇�깈占쏙옙�뵳占�" 占쎈츟嚥≪뮇�뵠占쎈짗
//																									 占쎈씨占쎌몵筌롳옙 "占쎈툡占쎌뵠占쎈탵占쎈씨占쎌벉" 占쎈츟嚥≪뮇�뵠占쎈짗
				
				if(rs.next()) {		 
					//占쎈툡占쎌뵠占쎈탵 占쎌뿳占쎌벉
				  if (pass.equals(rs.getString("pass"))) { 
					  check = 1;
					 
				    }else { 
				    	check=0;
				    	
				    }
				} else {//占쎈툡占쎌뵠占쎈탵 占쎈씨占쎌벉
					check=-1;
					
				}
				
				} catch(Exception e) {
					
					e.printStackTrace();
					}finally{
						//占쎌굙占쎌뇚揶쏉옙 獄쏆뮇源�占쎈릭占쎈군 筌띾Ŧ諭� 占쎄맒�꽴占쏙옙毓억옙�뵠 筌띾뜄龜�뵳�딆삂占쎈씜
						//揶쏆빘猿� 疫꿸퀣堉뀐옙�삢占쎈꺖 筌띾뜄龜�뵳占�
					 if(rs!=null)try{rs.close();}catch(SQLException e){e.printStackTrace();}
					 if(pstmt!=null)try{pstmt.close();}catch(SQLException e){e.printStackTrace();}
					 if(con!=null)try{con.close();}catch(SQLException e){e.printStackTrace();}
					}
				return check;
				
			}//end usercheck
			public int userCheck(String id){
				int check=0;
				Connection con =null;
				String sql="";
				PreparedStatement  pstmt=null;
				ResultSet rs = null;
				try{
					 con = getConnection();
				//3�떒怨� sql	議곌굔 id媛� 議댁옱�븯�뒗 �쉶�썝�젙蹂� 媛��졇�삤湲�
				 sql="select * from member where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				//4�떒怨� �뵒鍮꾧껐怨쇱��옣 媛앹껜 = �떎�뻾 寃곌낵 ���옣
				rs = pstmt.executeQuery();
				//5�떒怨� rs�뿉 ���옣�맂 �뜲�씠�꽣 �젒洹� �떎�쓬�뻾(泥ル쾲吏명뻾) �씠�룞 �뜲�씠�꽣 �엳�쑝硫� "�븘�씠�뵒�엳�쓬"
//													�뤌鍮꾨�踰덊샇 �뵒鍮꾩뿉�꽌 媛��졇�삩 鍮꾨�踰덊샇 鍮꾧탳
//																										 留욎쑝硫� �꽭�뀡媛믪깮�꽦 "id", 媛�
//																													main.jsp �씠�룞
//																										 ��由щ㈃ "鍮꾨�踰덊샇��由�" �뮘濡쒖씠�룞
//																									 �뾾�쑝硫� "�븘�씠�뵒�뾾�쓬" �뮘濡쒖씠�룞
				
				if(rs.next()) {		 
					//�븘�씠�뵒 �엳�쓬				   
					  check = 1;				 				    
				} else {//�븘�씠�뵒 �뾾�쓬
					check=-1;
					
				}
				
				} catch(Exception e) {
					//�삁�쇅 �깮湲곕㈃ 蹂��닔 e�뿉 ���옣
					//�삁�쇅瑜� �옟�븘�꽌 泥섎━ -> 硫붿떆吏� 異쒕젰
					e.printStackTrace();
					}finally{
						//�삁�쇅媛� 諛쒖깮�븯�뱺 留먮뱺 �긽愿��뾾�씠 留덈Т由ъ옉�뾽
						//媛앹껜 湲곗뼲�옣�냼 留덈Т由�
					 if(rs!=null)try{rs.close();}catch(SQLException e){e.printStackTrace();}
					 if(pstmt!=null)try{pstmt.close();}catch(SQLException e){e.printStackTrace();}
					 if(con!=null)try{con.close();}catch(SQLException e){e.printStackTrace();}
					}
				return check;
				
			}//end usercheck
			public MemberBean getMember(String id){
				MemberBean mb = new MemberBean();
				Connection con = null;
				String sql = "";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					con = getConnection();
					//3占쎈뼊�⑨옙 sql 鈺곌퀗援� id占쎈퓠 筌띾슣�앾옙釉�占쎈뮉 占쎌돳占쎌뜚占쎌젟癰귨옙 揶쏉옙占쎌죬占쎌궎疫뀐옙
					 sql="select * from member where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, id);
					//4占쎈뼊�⑨옙 野껉퀗�궢占쏙옙占쎌삢<=占쎈뼄占쎈뻬
					rs = pstmt.executeQuery();
					if(rs.next()){
					mb.setId(rs.getString("id"));
					mb.setPass(rs.getString("pass"));
					mb.setName(rs.getString("name"));					
					mb.setEmail(rs.getString("email"));
					mb.setReg_date(rs.getTimestamp("reg_date"));
					mb.setAddress(rs.getString("address"));
					mb.setPhone(rs.getString("phone"));
					mb.setMobile(rs.getString("mobile"));
					}
				}catch(Exception e) {
					//占쎌굙占쎌뇚 占쎄문疫꿸퀡�늺 癰귨옙占쎈땾 e占쎈퓠 占쏙옙占쎌삢
					//占쎌굙占쎌뇚�몴占� 占쎌삜占쎈툡占쎄퐣 筌ｌ꼶�봺 -> 筌롫뗄�뻻筌욑옙 �빊�뮆�젾
					e.printStackTrace();
					}finally{
						//占쎌굙占쎌뇚揶쏉옙 獄쏆뮇源�占쎈릭占쎈군 筌띾Ŧ諭� 占쎄맒�꽴占쏙옙毓억옙�뵠 筌띾뜄龜�뵳�딆삂占쎈씜
						//揶쏆빘猿� 疫꿸퀣堉뀐옙�삢占쎈꺖 筌띾뜄龜�뵳占�
						if(rs!=null)try{rs.close();}catch(SQLException e){e.printStackTrace();}
						if(pstmt!=null)try{pstmt.close();}catch(SQLException e){e.printStackTrace();}
						if(con!=null)try{con.close();}catch(SQLException e){e.printStackTrace();}
					}
				return mb;
			}//end getMember
			public int updateMember(MemberBean mb){
				int check=0;	
				Connection con=null;
				String sql="";
				PreparedStatement pstmt=null;
				try{con = getConnection();
				sql="update member set name=?, email=?,address=?,phone=?,mobile=? where id =?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, mb.getName()); 
				pstmt.setString(2, mb.getEmail());
				pstmt.setString(3, mb.getAddress());
				pstmt.setString(4, mb.getPhone()); // ? 占쎄퐬甕곕뜆�럮 �눧�눘�벉占쎈ご, 占쎄텊筌욎뮄而�
				pstmt.setString(5, mb.getMobile());
				pstmt.setString(6, mb.getId()); 

				String sql2="select * from member where id=?";
				PreparedStatement pstmt2 = con.prepareStatement(sql2);
				pstmt2.setString(1, mb.getId());
				//4占쎈뼊�⑨옙 揶쏆빘猿쒙옙�뼄占쎈뻬
				ResultSet rs = pstmt2.executeQuery();
				if(rs.next()){			
					if(mb.getPass().equals(rs.getString("pass"))){
						pstmt.executeUpdate();
						check=1;
						updatePass(mb);
						} else{ 
						check=0;
						}
				}else{
						check=-1;
				}
			
					
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
				return check;
			}//end updateMember
			public void updatePass(MemberBean mb){					
				Connection con=null;
				String sql="";
				PreparedStatement pstmt=null;
				try{con = getConnection();
				if(mb.getRe_pass()!=null){
				sql="update member set pass=? where id =?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, mb.getRe_pass()); 
				pstmt.setString(2, mb.getId());
				pstmt.executeUpdate();
				}
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
			public int deleteMember(MemberBean mb){
				Connection con=null;
				String sql="";
				PreparedStatement pstmt=null;
				int check=0;
				ResultSet rs=null;
				try{
				con = getConnection();
				sql="select * from member where id=? and pass=?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, mb.getId()); 
				pstmt.setString(2,mb.getPass());
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					sql="delete from member where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, mb.getId());
					if(mb.getPass().equals(rs.getString("pass"))){
						pstmt.executeUpdate();					
						check=1;
					}						
				}
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
			return check;
			}//end deleteMember
			
			public List <MemberBean>getAllMembers(){
				List<MemberBean> list = new ArrayList<MemberBean>();
				Connection con = null;
				String sql = "";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					con = getConnection();
					sql="select * from member";
					pstmt = con.prepareStatement(sql);									
					rs = pstmt.executeQuery();
					while(rs.next()){			
						MemberBean mb = new MemberBean();
						mb.setId(rs.getString("id"));
						mb.setPass(rs.getString("pass"));
						mb.setName(rs.getString("name"));
						
						mb.setEmail(rs.getString("email"));
						mb.setReg_date(rs.getTimestamp("reg_date"));
						list.add(mb);
						}
				 }catch(Exception e) {
					//占쎌굙占쎌뇚 占쎄문疫꿸퀡�늺 癰귨옙占쎈땾 e占쎈퓠 占쏙옙占쎌삢
					//占쎌굙占쎌뇚�몴占� 占쎌삜占쎈툡占쎄퐣 筌ｌ꼶�봺 -> 筌롫뗄�뻻筌욑옙 �빊�뮆�젾
					e.printStackTrace();
					}finally{
						//占쎌굙占쎌뇚揶쏉옙 獄쏆뮇源�占쎈릭占쎈군 筌띾Ŧ諭� 占쎄맒�꽴占쏙옙毓억옙�뵠 筌띾뜄龜�뵳�딆삂占쎈씜
						//揶쏆빘猿� 疫꿸퀣堉뀐옙�삢占쎈꺖 筌띾뜄龜�뵳占�
						if(rs!=null)try{rs.close();}catch(SQLException e){e.printStackTrace();}
						if(pstmt!=null)try{pstmt.close();}catch(SQLException e){e.printStackTrace();}
						if(con!=null)try{con.close();}catch(SQLException e){e.printStackTrace();}
					}
				return list;
			}//end getallmembers
			
			}//end class

