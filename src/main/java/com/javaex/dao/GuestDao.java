package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestVo;

@Repository
public class GuestDao {
	
	@Autowired
	private DataSource dataSource;

	//필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//생성자
	//메소드-g/s
	//메소드-일반
	
	//DB접속
	private void getConnection() {
		
		try {
			conn = dataSource.getConnection();
		
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
	}
	
	private void close() {
	   
	    // 5. 자원정리
	    try {              
	    	if (rs != null) {
	            rs.close();
	        }                
	        if (pstmt != null) {
	            pstmt.close();
	        }
	        if (conn != null) {
	            conn.close();
	        }
	    } catch (SQLException e) {
	        System.out.println("error:" + e);
	    }
	} 
	
	
	//1명 조회하기
	public GuestVo getGuest(int num) {		
		GuestVo guestVo = null;
		
		getConnection();
		
		try {
				String query ="";
				query += " select no, ";
				query += "        name, ";
				query += "        password, ";
				query += "        content, ";
				query += "        reg_date ";
				query += " from guestbook ";
				
				System.out.println(query);
				pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				
				//결과처리
				while(rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					String password = rs.getString("password");
					String content = rs.getString("content");
					String regDate = rs.getString("reg_date");
					
					guestVo = new GuestVo(no, name, password, content, regDate);
				}
			
		}catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
	    close();
		return guestVo;	
	}
	
	
	
	//delete
	public int guestDelete(GuestVo guestVo) {
		
		int count =0;
		
		getConnection();
		
		
	    try {
	    	/*delete from guestbook
              where no = 3
              and password = 1234;
            */
	    	String query ="";
	    	query += " delete from guestbook ";
	    	query += " where no = ? ";
	    	query += " and password = ? ";
	    	
	    	System.out.println(query);
	    	
	    	pstmt = conn.prepareStatement(query);
	    	pstmt.setInt(1, guestVo.getNo());
	    	pstmt.setString(2, guestVo.getPassword());
	    	
	    	count = pstmt.executeUpdate();
	    	
	    	// 4.결과처리
	    	System.out.println(count + "건 삭제");
		    

		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
	    close();
        return count;
	}
	
	//insert
	public int guestInsert(GuestVo guestVo) {
		
		int count =0;
		
		getConnection();

		try {
		    /*
		    insert into guestbook
            values(seq_guestbook_id.nextval,'이정재', '1234', '본문', sysdate);
		     */
			String query =  "";
			query += " insert into guestbook ";
			query += " values(seq_guestbook_id.nextval, ?, ?, ?, sysdate ) ";
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, guestVo.getName());
			pstmt.setString(2, guestVo.getPassword());
			pstmt.setString(3, guestVo.getContent());

			//쿼리문 실행하는 코드가 없어요
			count = pstmt.executeUpdate();
			
		    // 4.결과처리
			System.out.println(count + "건 저장");
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
	    close();
	    return count;
	}
	
	//select
	public List<GuestVo> ListAllGuest(){
		getConnection(); //없었음

		List<GuestVo> guestList = new ArrayList<GuestVo>();
		
		try {
				String query ="";
				query += " select no, ";
				query += "        name, ";
				query += "        password, ";
				query += "        content, ";
				query += "        to_char(reg_date, 'yyyy-mm-dd') reg_date ";
				query += " from guestbook ";
				
				System.out.println(query);
				pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				
				//결과처리
				while(rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					String password = rs.getString("password");
					String content = rs.getString("content");
					String regDate = rs.getString("reg_date");
					
					GuestVo guestVo = new GuestVo(no, name, password, content, regDate);
					guestList.add(guestVo);
				}
			
		}catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
	    close();
		return guestList;
		
	}
	
}
