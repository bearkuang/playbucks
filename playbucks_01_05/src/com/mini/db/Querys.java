package com.mini.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mini.dto.Sku;


public abstract class Querys {
	
	//D-DAY 계산
	final String querydDay = "UPDATE playbucks SET dDay = DATEDIFF(bestBefore, CURDATE())";
	//유통기한 얼마 안남은 순으로 정렬
	final String queryLineup = "SELECT * FROM playbucks ORDER BY bestBefore ";
	//final String queryLineup = "SELECT * FROM playbucks ORDER BY bestBefore ASC";
	//전체 레코드 조회
	final String querySelectAll = "select * from playbucks";
	//이름 입력에 따른 조회
	final String querySearchName = "select * from playbucks where name like CONCAT('%', ?, '%' ) ORDER BY bestBefore;";
	// 재고가 없는 상품 레코드 삭제
	final String QUERY_DELETE = "delete from playbucks where now = ?";
	// 새상품 정보입력
	final String QUERY_INSERT_NOW = "INSERT INTO playbucks (category, SKU, name, now, bestBefore) VALUES (?, ?, ?, ?, ?)";
	// 이름으로 검색해서 입고 수량 조정
	final String QUERY_UPDATE_NOW_PLUS = "update playbucks set now = now + ? where name = ?";
	// 이름으로 검색해서 출고 수량 조정
	final String QUERY_UPDATE_NOW_MINUS = "update playbucks set now = now - ? where name = ?";
	
	
	//final String querySearchName = "select * from playbucks where name like CONCAT('%', ?, '%' ) order by bestBefore";
	
	

	public Querys() {
	}
	
	//전체 레코드 조회
	public abstract void selectAll();
	// 이름 입력에 따른 조회
	public abstract ArrayList<Sku> selectName(String n);
	// 재고가 없는 상품 레코드 삭제
	public abstract void delete();
	// 새상품 정보입력
	public abstract void insertNow(Sku cafe);
	// 이름으로 검색해서 입고 수량 조정
	public abstract void updatePlus(int selectNow, String selectName);
	// 이름으로 검색해서 출고 수량 조정
	public abstract void updateMinus(int selectNow, String selectName);
	
	
	/** 자원 해제 메서드들 : Overloading 기법 **/
	public void close(Statement stmt, ResultSet rs) {
		try {
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Statement, ResultSet CLOSE  ERR : " + e.getMessage());
		}
	} // close() END

	public void close(PreparedStatement pstmt, ResultSet rs) {
		try {
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("PreparedStatement, ResultSet  CLOSE  ERR : " + e.getMessage());
		}
	} // close() END

	public static void close(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("PreparedStatement  CLOSE  ERR : " + e.getMessage());
		}
	} // close() END

	public void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Connection  CLOSE  ERR : " + e.getMessage());
		}
	} // close() END
}
