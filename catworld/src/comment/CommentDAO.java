package comment;

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


public class CommentDAO {

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
	
	public void insertComment(CommentBean cb){
		 Connection con=null;
		 	String sql="";			
			PreparedStatement pstmt=null;			
			ResultSet rs=null;
			int max=0;
			try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select max(num) as mnum from comment";				 				 
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					max = rs.getInt("mnum")+1;
				}
				sql="insert into comment (num,id,content,parent_num,date,re_ref,re_lev,re_seq,checknum) values(?,?,?,?,now(),?,?,?,?)";	
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, max); 
				pstmt.setString(2, cb.getId()); 
				pstmt.setString(3, cb.getContent());
				pstmt.setInt(4, cb.getParent_num());				
				pstmt.setInt(5, max);
				pstmt.setInt(6, 0);
				pstmt.setInt(7, 0);
				pstmt.setInt(8, 0);
							
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
	
	public int getCommentCount(int parent_num){
		 Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 int count = 0;
		 try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select count(*) as count from comment where parent_num=?";	
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,parent_num);				
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
	
	public List<CommentBean> getCommentList(int parent_num, int startRow, int pageSize){
		 List<CommentBean> list = new ArrayList<CommentBean>();
		 Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select * from comment where parent_num=? order by re_ref asc, re_seq asc limit ?,?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, parent_num);
				pstmt.setInt(2, startRow-1); //�벐�뿬吏� �닽�옄�쓽 �떎�쓬�뻾遺��꽣 寃��깋�빐二쇰�濡� startRow�뻾�룄 �룷�븿�븯�젮硫� -1 �빐以��떎.
				pstmt.setInt(3, pageSize);
				rs = pstmt.executeQuery();
				
				while(rs.next()){			
					CommentBean cb = new CommentBean();
					cb.setId(rs.getString("id"));
					cb.setContent(rs.getString("content"));									
					cb.setDate(rs.getTimestamp("date"));
					cb.setNum(rs.getInt("num"));
					list.add(cb);
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
	public void updateCommentCheck(int num){	
			Connection con=null;
			String sql="";
			PreparedStatement pstmt=null;	
			try{con = getConnection();			
			sql="update comment set checknum=1 where parent_num =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num); 
			pstmt.executeUpdate();
			}catch(Exception e) {
				//�삁�쇅 �깮湲곕㈃ 蹂��닔 e�뿉 ���옣
				//�삁�쇅瑜� �옟�븘�꽌 泥섎━ -> 硫붿떆吏� 異쒕젰
				e.printStackTrace();
				}finally{
					//�삁�쇅媛� 諛쒖깮�븯�뱺 留먮뱺 �긽愿��뾾�씠 留덈Т由ъ옉�뾽 => 湲곗뼲�옣�냼 �젙由�
					//媛앹껜 湲곗뼲�옣�냼 留덈Т由�
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
			
		}//end updatecheck
	
	public int countNewComment(String id){
		Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 ResultSet rs2=null;
		 int count = 0;
		 int num=0;
		 try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select num from board where name=?";	
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,id);			
				rs = pstmt.executeQuery();
				while(rs.next()){					
					num=rs.getInt("num");
					sql="select count(*) as count from comment where parent_num=? and checknum=0";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1,num);			
					rs2 = pstmt.executeQuery();
					while(rs2.next()){
					count = count+rs2.getInt("count");
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
		 return count;
	}//end count
	public List <BoardBean> NewComment(String id){
		List<BoardBean> list = new ArrayList<BoardBean>();
		 Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 ResultSet rs2=null;
		 int num=0;
		 try{
			con = getConnection();
			sql="select distinct parent_num from comment where checknum=0";				 				 
			pstmt = con.prepareStatement(sql); 
			rs = pstmt.executeQuery();
			while(rs.next()){
			sql="select * from board where num=? and name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, rs.getInt("parent_num"));
			pstmt.setString(2, id);
			rs2=pstmt.executeQuery();
			 while(rs2.next()){
				 BoardBean bb = new BoardBean();
				 bb.setSubject(rs2.getString("subject"));
				 bb.setNum(rs2.getInt("num"));
				 list.add(bb);
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
		return list;
	}//end list
	
	public int countBoardNewComment(int num){
		Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 int count = 0;
		 try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select count(*) as count from comment where parent_num=? and checknum=0";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1,num);			
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
	
	 public CommentBean getComment(int parent_num, int num){
		 CommentBean cb = new CommentBean();
		 Connection con=null;
		 String sql="";
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select * from comment where parent_num=? and num=?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, parent_num);
				pstmt.setInt(2, num);	
				rs = pstmt.executeQuery();				
				while(rs.next()){						
					cb.setNum(rs.getInt("num"));
					cb.setId(rs.getString("id"));					
					cb.setContent(rs.getString("content"));									
					cb.setDate(rs.getTimestamp("date"));																
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
		 return cb;
	 }//end board
	 
	 public void updateComment(CommentBean cb){
		 Connection con=null;
		 	String sql="";			
			PreparedStatement pstmt=null;			
			ResultSet rs=null;		
			int check=0;			
			try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select * from comment where num=?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cb.getNum()); 				
				rs = pstmt.executeQuery();
				
				if(rs.next()){									
				sql="update comment set content=? where num=?";	
				pstmt = con.prepareStatement(sql);								 
				pstmt.setString(1, cb.getContent()); 
				pstmt.setInt(2, cb.getNum());
				pstmt.executeUpdate();
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
	 }//end update
	 
	 public void deleteComment(int num){		 
		 Connection con=null;
		 	String sql="";			
			PreparedStatement pstmt=null;			
			ResultSet rs=null;	
			try{ //�삁�쇅媛� 諛쒖깮�븷 寃� 媛숈� 紐낅졊, 	�븘�닔�쟻�쑝濡� �쇅遺��뙆�씪�젒洹�, �뵒鍮꾩젒洹�
				con = getConnection();
				sql="select * from comment where num=?";				 				 
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,num); 		
				rs = pstmt.executeQuery();
				
				if(rs.next()){									
				sql="delete from comment where num=? ";	
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.executeUpdate();
				
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
		 
	 }//end delete
	 
	}

