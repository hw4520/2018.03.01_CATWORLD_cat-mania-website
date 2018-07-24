package gallary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import board.BoardBean;
import room.RoomBean;

public class GallaryDAO {
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
		}//end connect
	
	public int getGallaryCount(){
		 Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 int count = 0;
		 try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select count(*) as count from gallary";				 				 
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
	
	public int getGallaryCount(String search){
		 Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 int count = 0;
		 try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select count(*) as count from gallary where subject like ?";				 				 
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
	
	public List<GallaryBean> getGallaryList(int startRow, int pageSize){
		 List<GallaryBean> list = new ArrayList<GallaryBean>();
		 Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select * from gallary order by num desc limit ?,?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, startRow-1); //�벐�뿬吏� �닽�옄�쓽 �떎�쓬�뻾遺��꽣 寃��깋�빐二쇰�濡� startRow�뻾�룄 �룷�븿�븯�젮硫� -1 �빐以��떎.
				pstmt.setInt(2, pageSize);
				rs = pstmt.executeQuery();
				
				while(rs.next()){			
					GallaryBean gb = new GallaryBean();
					gb.setNum(rs.getInt("num"));
					gb.setName(rs.getString("name"));
					gb.setSubject(rs.getString("subject"));
					gb.setContent(rs.getString("content"));	
					gb.setFile(rs.getString("file"));
					gb.setDate(rs.getDate("date"));
					gb.setReadcount(rs.getInt("readcount"));
					list.add(gb);
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
	
	public List<GallaryBean> getGallaryList(int startRow, int pageSize,String search){
		 List<GallaryBean> list = new ArrayList<GallaryBean>();
		 Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select * from gallary where subject like ? order by num desc limit ?,? ";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,"%"+search+"%");
				pstmt.setInt(2, startRow-1); //�벐�뿬吏� �닽�옄�쓽 �떎�쓬�뻾遺��꽣 寃��깋�빐二쇰�濡� startRow�뻾�룄 �룷�븿�븯�젮硫� -1 �빐以��떎.
				pstmt.setInt(3, pageSize);				
				rs = pstmt.executeQuery();
				
				while(rs.next()){			
					GallaryBean gb = new GallaryBean();
					gb.setNum(rs.getInt("num"));
					gb.setName(rs.getString("name"));
					gb.setSubject(rs.getString("subject"));
					gb.setContent(rs.getString("content"));									
					gb.setDate(rs.getDate("date"));
					gb.setReadcount(rs.getInt("readcount"));
					gb.setFile(rs.getString("file"));
					list.add(gb);
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
	
	public void insertGallary(GallaryBean gb){
		 Connection con=null;
		 	String sql="";			
			PreparedStatement pstmt=null;			
			ResultSet rs=null;
			int max=0;
			try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select max(num) as mnum from gallary";				 				 
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					max = rs.getInt("mnum")+1;
				}
				sql="insert into gallary (num,name,pass,subject,content,readcount,date,file) values(?,?,?,?,?,?,now(),?)";	
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, max); 
				pstmt.setString(2, gb.getName()); 
				pstmt.setString(3, gb.getPass());
				pstmt.setString(4, gb.getSubject());
				pstmt.setString(5, gb.getContent());
				pstmt.setInt(6, gb.getReadcount());//readcount
				pstmt.setString(7, gb.getFile());
							
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
			}//insert() 硫붿꽌�뱶
	
	public void updateReadcount(int num){
		 Connection con=null;
		 	String sql="";			
			PreparedStatement pstmt=null;			
			ResultSet rs=null;		
			int count=0;
			try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select * from gallary where num=?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num); 
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					count=rs.getInt("readcount")+1;
				}
				sql="update gallary set readcount=? where num=?";	
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
	
	public GallaryBean getGallary(int num){
		 GallaryBean gb = new GallaryBean();
		 Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select * from gallary where num=?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num);			
				rs = pstmt.executeQuery();
				
				while(rs.next()){								
					gb.setNum(rs.getInt("num"));
					gb.setName(rs.getString("name"));
					gb.setSubject(rs.getString("subject"));
					gb.setContent(rs.getString("content"));									
					gb.setDate(rs.getDate("date"));		
					gb.setReadcount(rs.getInt("readcount"));
					gb.setFile(rs.getString("file"));					
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
		 return gb;
	 }//end get
	
	public int deleteGallary(GallaryBean gb){
		 int check=0;
		 Connection con=null;
		 	String sql="";			
			PreparedStatement pstmt=null;			
			ResultSet rs=null;	
			try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select * from gallary where num=? and pass=?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, gb.getNum()); 
				pstmt.setString(2,gb.getPass());
				rs = pstmt.executeQuery();
				
				if(rs.next()){									
				sql="delete from gallary where num=?";	
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, gb.getNum());
				if(gb.getPass().equals(rs.getString("pass"))){
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
	
	public int updateGallary(GallaryBean gb){
		 Connection con=null;
		 	String sql="";			
			PreparedStatement pstmt=null;			
			ResultSet rs=null;		
			int check=0;			
			try{ //예외가 발생할 것 같은 명령, 	필수적으로 외부파일접근, 디비접근
				con = getConnection();
				sql="select * from gallary where num=? and pass=?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, gb.getNum()); 
				pstmt.setString(2,gb.getPass());
				rs = pstmt.executeQuery();				
				if(rs.next()){					
				sql="update gallary set name=?,subject=?,content=?,file=? where num=?";	
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, gb.getName());
				pstmt.setString(2, gb.getSubject()); 
				pstmt.setString(3, gb.getContent()); 
				pstmt.setString(4, gb.getFile()); 
				pstmt.setInt(5, gb.getNum());
				if(gb.getPass().equals(rs.getString("pass"))){
					pstmt.executeUpdate();
					check=1;
					} 
					
			}
				
				
			} catch(Exception e) {
					//예외 생기면 변수 e에 저장
					//예외를 잡아서 처리 -> 메시지 출력
					e.printStackTrace();
					}finally{
						//예외가 발생하든 말든 상관없이 마무리작업
						//객체 기억장소 마무리
						
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
	
}
