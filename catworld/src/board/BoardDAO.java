package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import member.MemberBean;

public class BoardDAO {
	//�뵒鍮꾩뿰寃� 硫붿꽌�뱶
	private Connection getConnection() throws Exception{
//		//1.�뱶�씪�씠踰꾨줈�뜑
//		Class.forName("com.mysql.jdbc.Driver");
//		//2. �뵒鍮꾩뿰寃�
//		String dbUrl="jdbc:mysql://localhost:3306/jspdb2";
//		String dbUser="jspid";
//		String dbPass="jsppass";
//		Connection con=DriverManager.getConnection(dbUrl, dbUser, dbPass);
//		return con;
		
		// 而ㅻ꽖�뀡 ��(Connection Pool)
		// - �뜲�씠�꽣踰좎씠�뒪�� �뿰寃곕맂 Connection 媛앹껜瑜� 誘몃━ �깮�꽦�븯�뿬 ��(Pool)
		// 	 �냽�뿉 ���옣�빐 �몢怨� �븘�슂�븷 �븣留덈떎 �씠 ���뿉 �젒洹쇳븯�뿬 Connection媛앹껜瑜�
		//	 �궗�슜�븯怨� �옉�뾽�씠 �걹�굹硫� �떎�떆 諛섑솚�븯�뒗 寃�
		// 	 而ㅻ꽖�뀡 �옱�궗�슜, �봽濡쒓렇�옩 �슚�쑉 �꽦�뒫 利앷�
		// 	 �옄移대Ⅴ�� DBCP API - �븘�뙆移섑넱罹� 踰꾩쟾 7踰꾩쟾 �씠�긽 �궡�옣�릺�뼱�엳�쓬
		//	 http://commons.apache.org
		//	 commons-collections.zip
		//   commons-dbcp .zip
		// 	 commons-pool .zip
		
		//1. webContent/META-INF/context.xml
		//	 �뵒鍮꾩뿰寃� �옄�썝 ���옣
		//2. webContent/WEB-INF/web.xml
		//	 �옄�썝�쓣 紐⑤뱺 �럹�씠吏��뿉 �븣�젮以�
		//3. BoardDAO �궗�슜
			
		//context �뙆�씪�쓣 遺덈윭�삤湲� �쐞�븳 �뙆�씪 媛앹껜 �깮�꽦
		Context init=new InitialContext();
		// �옄�썝�쓽 �씠由� 遺덈윭�삤湲�  �옄�썝�쐞移� java:comp/env �옄�썝�씠由� jdbc/Mysql
		//DataSource ds=init.lookup(�옄�썝�쓽 �쐞移� �옄�썝�쓽�씠由�);
		DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/Mysql");
		Connection con=ds.getConnection();
		
		return con;
		}
	
	 public void insertBoard(BoardBean bb){
		 Connection con=null;
		 	String sql="";			
			PreparedStatement pstmt=null;			
			ResultSet rs=null;
			int max=0;
			try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select max(num) as mnum from board";				 				 
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					max = rs.getInt("mnum")+1;
				}
				sql="insert into board (num,name,pass,subject,content,readcount,date,file,re_ref,parent_num) values(?,?,?,?,?,?,now(),?,?,?)";	
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, max); 
				pstmt.setString(2, bb.getName()); 
				pstmt.setString(3, bb.getPass());
				pstmt.setString(4, bb.getSubject());
				pstmt.setString(5, bb.getContent());
				pstmt.setInt(6, bb.getReadcount());//readcount
				pstmt.setString(7, bb.getFile());
				pstmt.setInt(8, max);
				pstmt.setInt(9, bb.getParent_num());			
				pstmt.executeUpdate();
			} catch(Exception e) {
					//�삁�쇅 �깮湲곕㈃ 蹂��닔 e�뿉 ���옣
					//�삁�쇅瑜� �옟�븘�꽌 泥섎━ -> 硫붿떆吏� 異쒕젰
					e.printStackTrace();
					}finally{
						//�삁�쇅媛� 諛쒖깮�븯�뱺 留먮뱺 �긽愿��뾾�씠 留덈Т由ъ옉�뾽
						//媛앹껜 湲곗뼲�옣�냼 留덈Т由�
						
						if(rs!=null){
							try{rs.close();
							}catch(SQLException e){
								e.printStackTrace();
							 }
							}//end if
						if(pstmt!=null){
							try{pstmt.close();						
							}catch(SQLException e){
								e.printStackTrace();
							}
						 }//end if
							if(con!=null){
								try{con.close();
								}catch(SQLException e){
									e.printStackTrace();
								 }
								}//end if
					}
			}//insertMember() 硫붿꽌�뱶
	 public void ReinsertBoard(BoardBean bb){
		 Connection con=null;
		 	String sql="";			
			PreparedStatement pstmt=null;			
			ResultSet rs=null;
			int max=0;
			try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select max(num) as mnum from board";				 				 
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					max = rs.getInt("mnum")+1;
				}
				sql="insert into board (num,name,pass,subject,content,readcount,date,file,re_ref,re_lev,re_seq) values(?,?,?,?,?,?,now(),?,?,?,?)";	
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, max); 
				pstmt.setString(2, bb.getName()); 
				pstmt.setString(3, bb.getPass());
				pstmt.setString(4, bb.getSubject());
				pstmt.setString(5, bb.getContent());
				pstmt.setInt(6, bb.getReadcount());//readcount
				pstmt.setString(7, bb.getFile());
				pstmt.setInt(8, bb.getRe_ref());
				pstmt.setInt(9, bb.getRe_lev());
				pstmt.setInt(10,bb.getRe_seq());			
				pstmt.executeUpdate();
			} catch(Exception e) {
					//�삁�쇅 �깮湲곕㈃ 蹂��닔 e�뿉 ���옣
					//�삁�쇅瑜� �옟�븘�꽌 泥섎━ -> 硫붿떆吏� 異쒕젰
					e.printStackTrace();
					}finally{
						//�삁�쇅媛� 諛쒖깮�븯�뱺 留먮뱺 �긽愿��뾾�씠 留덈Т由ъ옉�뾽
						//媛앹껜 湲곗뼲�옣�냼 留덈Т由�
						
						if(rs!=null){
							try{rs.close();
							}catch(SQLException e){
								e.printStackTrace();
							 }
							}//end if
						if(pstmt!=null){
							try{pstmt.close();						
							}catch(SQLException e){
								e.printStackTrace();
							}
						 }//end if
							if(con!=null){
								try{con.close();
								}catch(SQLException e){
									e.printStackTrace();
								 }
								}//end if
					}
	 }
	 public int getBoardCount(){
		 Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 int count = 0;
		 try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select count(*) as count from board";				 				 
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					count = rs.getInt("count");
				}
				
			} catch(Exception e) {
					//�삁�쇅 �깮湲곕㈃ 蹂��닔 e�뿉 ���옣
					//�삁�쇅瑜� �옟�븘�꽌 泥섎━ -> 硫붿떆吏� 異쒕젰
					e.printStackTrace();
					}finally{
						//�삁�쇅媛� 諛쒖깮�븯�뱺 留먮뱺 �긽愿��뾾�씠 留덈Т由ъ옉�뾽
						//媛앹껜 湲곗뼲�옣�냼 留덈Т由�
						
						if(rs!=null){
							try{rs.close();
							}catch(SQLException e){
								e.printStackTrace();
							 }
							}//end if
						if(pstmt!=null){
							try{pstmt.close();						
							}catch(SQLException e){
								e.printStackTrace();
							}
						 }//end if
							if(con!=null){
								try{con.close();
								}catch(SQLException e){
									e.printStackTrace();
								 }
								}//end if
					}
		 return count;
	 }//end count
	 public int getBoardCount(String search){
		 Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 int count = 0;
		 try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select count(*) as count from board where subject like ?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,"%"+search+"%");
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					count = rs.getInt("count");
				}
				
			} catch(Exception e) {
					//�삁�쇅 �깮湲곕㈃ 蹂��닔 e�뿉 ���옣
					//�삁�쇅瑜� �옟�븘�꽌 泥섎━ -> 硫붿떆吏� 異쒕젰
					e.printStackTrace();
					}finally{
						//�삁�쇅媛� 諛쒖깮�븯�뱺 留먮뱺 �긽愿��뾾�씠 留덈Т由ъ옉�뾽
						//媛앹껜 湲곗뼲�옣�냼 留덈Т由�
						
						if(rs!=null){
							try{rs.close();
							}catch(SQLException e){
								e.printStackTrace();
							 }
							}//end if
						if(pstmt!=null){
							try{pstmt.close();						
							}catch(SQLException e){
								e.printStackTrace();
							}
						 }//end if
							if(con!=null){
								try{con.close();
								}catch(SQLException e){
									e.printStackTrace();
								 }
								}//end if
					}
		 return count;
	 }//end count
	 public List<BoardBean> getBoardList(int startRow, int pageSize){
		 List<BoardBean> list = new ArrayList<BoardBean>();
		 Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select * from board order by re_ref desc, re_seq asc limit ?,?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, startRow-1); //�벐�뿬吏� �닽�옄�쓽 �떎�쓬�뻾遺��꽣 寃��깋�빐二쇰�濡� startRow�뻾�룄 �룷�븿�븯�젮硫� -1 �빐以��떎.
				pstmt.setInt(2, pageSize);
				rs = pstmt.executeQuery();
				
				while(rs.next()){			
					BoardBean bb = new BoardBean();
					bb.setNum(rs.getInt("num"));
					bb.setName(rs.getString("name"));
					bb.setSubject(rs.getString("subject"));
					bb.setContent(rs.getString("content"));									
					bb.setDate(rs.getDate("date"));
					bb.setReadcount(rs.getInt("readcount"));
					bb.setRe_ref(rs.getInt("re_ref"));
					bb.setRe_lev(rs.getInt("re_lev"));
					bb.setRe_seq(rs.getInt("re_seq"));
					list.add(bb);
					}
				
			} catch(Exception e) {
					//�삁�쇅 �깮湲곕㈃ 蹂��닔 e�뿉 ���옣
					//�삁�쇅瑜� �옟�븘�꽌 泥섎━ -> 硫붿떆吏� 異쒕젰
					e.printStackTrace();
					}finally{
						//�삁�쇅媛� 諛쒖깮�븯�뱺 留먮뱺 �긽愿��뾾�씠 留덈Т由ъ옉�뾽
						//媛앹껜 湲곗뼲�옣�냼 留덈Т由�
						
						if(rs!=null){
							try{rs.close();
							}catch(SQLException e){
								e.printStackTrace();
							 }
							}//end if
						if(pstmt!=null){
							try{pstmt.close();						
							}catch(SQLException e){
								e.printStackTrace();
							}
						 }//end if
							if(con!=null){
								try{con.close();
								}catch(SQLException e){
									e.printStackTrace();
								 }
								}//end if
					}
		 return list;
	 }//end list
	 public List<BoardBean> getBoardList(int startRow, int pageSize,String search){
		 List<BoardBean> list = new ArrayList<BoardBean>();
		 Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select * from board where subject like ? order by re_ref desc, re_seq asc limit ?,? ";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,"%"+search+"%");
				pstmt.setInt(2, startRow-1); //�벐�뿬吏� �닽�옄�쓽 �떎�쓬�뻾遺��꽣 寃��깋�빐二쇰�濡� startRow�뻾�룄 �룷�븿�븯�젮硫� -1 �빐以��떎.
				pstmt.setInt(3, pageSize);				
				rs = pstmt.executeQuery();
				
				while(rs.next()){			
					BoardBean bb = new BoardBean();
					bb.setNum(rs.getInt("num"));
					bb.setName(rs.getString("name"));
					bb.setSubject(rs.getString("subject"));
					bb.setContent(rs.getString("content"));									
					bb.setDate(rs.getDate("date"));
					bb.setReadcount(rs.getInt("readcount"));
					bb.setRe_ref(rs.getInt("re_ref"));
					bb.setRe_lev(rs.getInt("re_lev"));
					bb.setRe_seq(rs.getInt("re_seq"));
					list.add(bb);
					}
				
			} catch(Exception e) {
					//�삁�쇅 �깮湲곕㈃ 蹂��닔 e�뿉 ���옣
					//�삁�쇅瑜� �옟�븘�꽌 泥섎━ -> 硫붿떆吏� 異쒕젰
					e.printStackTrace();
					}finally{
						//�삁�쇅媛� 諛쒖깮�븯�뱺 留먮뱺 �긽愿��뾾�씠 留덈Т由ъ옉�뾽
						//媛앹껜 湲곗뼲�옣�냼 留덈Т由�
						
						if(rs!=null){
							try{rs.close();
							}catch(SQLException e){
								e.printStackTrace();
							 }
							}//end if
						if(pstmt!=null){
							try{pstmt.close();						
							}catch(SQLException e){
								e.printStackTrace();
							}
						 }//end if
							if(con!=null){
								try{con.close();
								}catch(SQLException e){
									e.printStackTrace();
								 }
								}//end if
					}
		 return list;
	 }//end list
	 
	 public BoardBean getBoard(int num){
		 BoardBean bb = new BoardBean();
		 Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select * from board where num=?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num);			
				rs = pstmt.executeQuery();
				
				while(rs.next()){								
					bb.setNum(rs.getInt("num"));
					bb.setName(rs.getString("name"));
					bb.setSubject(rs.getString("subject"));
					bb.setContent(rs.getString("content"));									
					bb.setDate(rs.getDate("date"));		
					bb.setReadcount(rs.getInt("readcount"));
					bb.setFile(rs.getString("file"));
					bb.setRe_ref(rs.getInt("re_ref"));
					bb.setRe_lev(rs.getInt("re_lev"));
					bb.setRe_seq(rs.getInt("re_seq"));
					}
				
			} catch(Exception e) {
					//�삁�쇅 �깮湲곕㈃ 蹂��닔 e�뿉 ���옣
					//�삁�쇅瑜� �옟�븘�꽌 泥섎━ -> 硫붿떆吏� 異쒕젰
					e.printStackTrace();
					}finally{
						//�삁�쇅媛� 諛쒖깮�븯�뱺 留먮뱺 �긽愿��뾾�씠 留덈Т由ъ옉�뾽
						//媛앹껜 湲곗뼲�옣�냼 留덈Т由�
						
						if(rs!=null){
							try{rs.close();
							}catch(SQLException e){
								e.printStackTrace();
							 }
							}//end if
						if(pstmt!=null){
							try{pstmt.close();						
							}catch(SQLException e){
								e.printStackTrace();
							}
						 }//end if
							if(con!=null){
								try{con.close();
								}catch(SQLException e){
									e.printStackTrace();
								 }
								}//end if		
	 }
		 return bb;
	 }//end board
	 
	 public void updateReadcount(int num){
		 Connection con=null;
		 	String sql="";			
			PreparedStatement pstmt=null;			
			ResultSet rs=null;		
			int count=0;
			try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select * from board where num=?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num); 
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					count=rs.getInt("readcount")+1;
				}
				sql="update board set readcount=? where num=?";	
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, count); 
				pstmt.setInt(2,num); 
				
				
				pstmt.executeUpdate();
			} catch(Exception e) {
					//�삁�쇅 �깮湲곕㈃ 蹂��닔 e�뿉 ���옣
					//�삁�쇅瑜� �옟�븘�꽌 泥섎━ -> 硫붿떆吏� 異쒕젰
					e.printStackTrace();
					}finally{
						//�삁�쇅媛� 諛쒖깮�븯�뱺 留먮뱺 �긽愿��뾾�씠 留덈Т由ъ옉�뾽
						//媛앹껜 湲곗뼲�옣�냼 留덈Т由�
						
						if(rs!=null){
							try{rs.close();
							}catch(SQLException e){
								e.printStackTrace();
							 }
							}//end if
						if(pstmt!=null){
							try{pstmt.close();						
							}catch(SQLException e){
								e.printStackTrace();
							}
						 }//end if
							if(con!=null){
								try{con.close();
								}catch(SQLException e){
									e.printStackTrace();
								 }
								}//end if
					
					}
			
	 }//end update
	 
	 public int updateBoard(BoardBean bb){
		 Connection con=null;
		 	String sql="";			
			PreparedStatement pstmt=null;			
			ResultSet rs=null;		
			int check=0;			
			try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select * from board where num=? and pass=?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, bb.getNum()); 
				pstmt.setString(2,bb.getPass());
				rs = pstmt.executeQuery();
				
				if(rs.next()){									
				sql="update board set name=?,subject=?,content=? where num=?";	
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, bb.getName());
				pstmt.setString(2, bb.getSubject()); 
				pstmt.setString(3, bb.getContent()); 
				pstmt.setInt(4, bb.getNum());
				if(bb.getPass().equals(rs.getString("pass"))){
					pstmt.executeUpdate();
					check=1;
					} 
					
			}
				
				
			} catch(Exception e) {
					//�삁�쇅 �깮湲곕㈃ 蹂��닔 e�뿉 ���옣
					//�삁�쇅瑜� �옟�븘�꽌 泥섎━ -> 硫붿떆吏� 異쒕젰
					e.printStackTrace();
					}finally{
						//�삁�쇅媛� 諛쒖깮�븯�뱺 留먮뱺 �긽愿��뾾�씠 留덈Т由ъ옉�뾽
						//媛앹껜 湲곗뼲�옣�냼 留덈Т由�
						
						if(rs!=null){
							try{rs.close();
							}catch(SQLException e){
								e.printStackTrace();
							 }
							}//end if
						if(pstmt!=null){
							try{pstmt.close();						
							}catch(SQLException e){
								e.printStackTrace();
							}
						 }//end if
							if(con!=null){
								try{con.close();
								}catch(SQLException e){
									e.printStackTrace();
								 }
								}//end if
					
					}
			return check;
	 }//end updateboard
	 
	 public int deleteBoard(BoardBean bb){
		 int check=0;
		 Connection con=null;
		 	String sql="";			
			PreparedStatement pstmt=null;			
			ResultSet rs=null;	
			try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select * from board where num=? and pass=?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, bb.getNum()); 
				pstmt.setString(2,bb.getPass());
				rs = pstmt.executeQuery();
				
				if(rs.next()){									
				sql="delete from board where num=? ";	
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, bb.getNum());				
				if(bb.getPass().equals(rs.getString("pass"))){
					pstmt.executeUpdate();					
					check=1;
					} 
			}
				
				
			} catch(Exception e) {
					//�삁�쇅 �깮湲곕㈃ 蹂��닔 e�뿉 ���옣
					//�삁�쇅瑜� �옟�븘�꽌 泥섎━ -> 硫붿떆吏� 異쒕젰
					e.printStackTrace();
					}finally{
						//�삁�쇅媛� 諛쒖깮�븯�뱺 留먮뱺 �긽愿��뾾�씠 留덈Т由ъ옉�뾽
						//媛앹껜 湲곗뼲�옣�냼 留덈Т由�
						
						if(rs!=null){
							try{rs.close();
							}catch(SQLException e){
								e.printStackTrace();
							 }
							}//end if
						if(pstmt!=null){
							try{pstmt.close();						
							}catch(SQLException e){
								e.printStackTrace();
							}
						 }//end if
							if(con!=null){
								try{con.close();
								}catch(SQLException e){
									e.printStackTrace();
								 }
								}//end if
					
					}
		 return check;
	 }//end delete
	 
	 public void reinsert(BoardBean bb){
		 Connection con=null;
		 	String sql="";			
			PreparedStatement pstmt=null;			
			ResultSet rs=null;
			int max=0;
			int re_seq=0;
			int seq;
			try{
				con = getConnection();
				
				sql="select max(num) as mnum from board";				 				 
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					max = rs.getInt("mnum")+1;
				}
				
				sql="select min(re_seq) as minnum from board where re_ref=? and re_seq>? and re_lev<=?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,bb.getRe_ref());
				pstmt.setInt(2, bb.getRe_seq());
				pstmt.setInt(3, bb.getRe_lev());
				rs = pstmt.executeQuery();
			
			
				if(rs.next()){
					if(rs.getInt("minnum")==0){
					//System.out.println("true"+rs.getInt("minnum"));
					sql="select max(re_seq) as maxnum from board where re_ref=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1,bb.getRe_ref());
					rs = pstmt.executeQuery();
					
					if(rs.next()){
					re_seq=rs.getInt("maxnum");			
					}
					
					sql="insert into board (num,name,pass,subject,content,readcount,date,file,re_ref,re_lev,re_seq) values(?,?,?,?,?,?,now(),?,?,?,?)";	
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, max); 
					pstmt.setString(2, bb.getName()); 
					pstmt.setString(3, bb.getPass());
					pstmt.setString(4, bb.getSubject());
					pstmt.setString(5, bb.getContent());
					pstmt.setInt(6, bb.getReadcount());//readcount
					pstmt.setString(7, bb.getFile());
					pstmt.setInt(8, bb.getRe_ref());
					pstmt.setInt(9, bb.getRe_lev()+1);
					pstmt.setInt(10, re_seq+1);			
					pstmt.executeUpdate();
					
				}else{
					//System.out.println("false");
					seq = rs.getInt("minnum");
					sql="update board set re_seq = re_seq+1 where re_ref=? and re_seq>=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, bb.getRe_ref());
					pstmt.setInt(2, seq);
					pstmt.executeUpdate();
					
					sql="insert into board (num,name,pass,subject,content,readcount,date,file,re_ref,re_lev,re_seq) values(?,?,?,?,?,?,now(),?,?,?,?)";	
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, max); 
					pstmt.setString(2, bb.getName()); 
					pstmt.setString(3, bb.getPass());
					pstmt.setString(4, bb.getSubject());
					pstmt.setString(5, bb.getContent());
					pstmt.setInt(6, bb.getReadcount());//readcount
					pstmt.setString(7, bb.getFile());
					pstmt.setInt(8, bb.getRe_ref());
					pstmt.setInt(9, bb.getRe_lev()+1);
					pstmt.setInt(10,seq);			
					pstmt.executeUpdate();
					
				}
				
				}
	 }catch(Exception e) {
			//�삁�쇅 �깮湲곕㈃ 蹂��닔 e�뿉 ���옣
			//�삁�쇅瑜� �옟�븘�꽌 泥섎━ -> 硫붿떆吏� 異쒕젰
			e.printStackTrace();
			}finally{
				//�삁�쇅媛� 諛쒖깮�븯�뱺 留먮뱺 �긽愿��뾾�씠 留덈Т由ъ옉�뾽
				//媛앹껜 湲곗뼲�옣�냼 留덈Т由�
				
				if(rs!=null){
					try{rs.close();
					}catch(SQLException e){
						e.printStackTrace();
					 }
					}//end if
				if(pstmt!=null){
					try{pstmt.close();						
					}catch(SQLException e){
						e.printStackTrace();
					}
				 }//end if
					if(con!=null){
						try{con.close();
						}catch(SQLException e){
							e.printStackTrace();
						 }
						}//end if
			}
}
}