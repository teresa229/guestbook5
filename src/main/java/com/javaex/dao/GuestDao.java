package com.javaex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestVo;

@Repository
public class GuestDao {
	
	@Autowired
	private SqlSession sqlSession;

	//delete
	public int guestDelete(GuestVo guestVo) {
		System.out.println("dao: guestDelete");
		
		int count = sqlSession.delete("guestbook.delete", guestVo);
		return count;
		
	}
	
	//insert
	public void guestInsert(GuestVo guestVo) {
		System.out.println("dao: guestInsert");
		
		sqlSession.insert("guestbook.insert", guestVo);
	}
	
	//select
	public List<GuestVo> ListAllGuest(){
		System.out.println("dao: ListAllGuest");
		sqlSession.selectList("guestbook.selectList");
		//List<GuestVo> guestList = sqlSession.selectList("guestbook.selectList");
		//System.out.println(guestList.toString());
		//return guestList;
		
		return ListAllGuest();
	}
	
}
