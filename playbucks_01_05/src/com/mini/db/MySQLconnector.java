package com.mini.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.mini.dto.Sku;

public class MySQLconnector extends Querys {
	private String dbName = "";
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/";
	private String params = "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
	private String id_mysql = "root";
	private String pw_mysql = "1234"; // 비밀번호 ! 나중에 변경해야 할 수있음
	
	public Connection conn = null;
	
	public static ArrayList<Sku> numList = null;
	
	public MySQLconnector() { // 기본 생성자
		
	}
	public MySQLconnector(String dbName) {
		this.dbName=dbName;
		this.url = this.url + this.dbName + this.params;
	}
	
	
	public void connectMySQL() {
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id_mysql, pw_mysql);
			System.out.println("MySQL 접속 성공 !");
			
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException ERR : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQLException ERR : " + e.getMessage());
		}
		
		
	}// connectMySQL() END
	@Override
	public void selectAll() {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
            // D-Day 값을 업데이트
            updateDDay();
            
            lineUp();

            stmt = conn.createStatement();
            rs = stmt.executeQuery(queryLineup);

            numList = new ArrayList<>();

            while (rs.next()) {
                int seqNo = rs.getInt("seqNo");
                String category = rs.getString("category");
                String SKU = rs.getString("SKU");
                String name = rs.getString("name");
                int now = rs.getInt("now");
                String bestBefore = rs.getString("bestBefore");
                int dDay = rs.getInt("dDay");

                numList.add(new Sku(seqNo, category, SKU, name, now, bestBefore, dDay));
            }
        } catch (SQLException e) {
            System.out.println("selectAll() ERR: " + e.getMessage());
        } finally {
            close(stmt, rs);
        }
		
		if (numList == null) {
	        numList = new ArrayList<>();
	    }
    }
	
	
	//QUERY_UPDATE_NOW = 
	//"update playbucks set now = ? where name = ?"
	// 이름 검색하면 입출고 업데이트 -> 출고 수량 부족하면 메시지 출력
	// 이름 검색
	@Override
	public ArrayList<Sku> selectName(String n){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<Sku> nameList = null;
		
		try {
			 // D-Day 값을 업데이트
            updateDDay();
			
			pstmt = conn.prepareStatement(querySearchName);
			//Scanner scanKey = new Scanner(System.in);
			//System.out.println("제품명을 입력해주세요. :");
			//String keyword = scanKey.nextLine();
			pstmt.setString(1, n);
			rs = pstmt.executeQuery();
			
			nameList = new ArrayList<Sku>();
			
			while (rs.next()) {
				int seqNo = rs.getInt("seqNo");
				String category = rs.getString("category");
				String SKU = rs.getString("SKU");
				String name = rs.getString("name");
				int now = rs.getInt("now");
				String bestBefore = rs.getString("bestBefore");
				int dDay = rs.getInt("dDay");
				
				nameList.add(new Sku(seqNo, category, SKU, name, now, bestBefore, dDay));
				//System.out.println(nameList.size());
			}
			
			System.out.println("제품이 조회 되었습니다.");
			
		}catch (SQLException e) {
			System.out.println("selectName ERR : " + e.getMessage());
		} finally {
			this.close(pstmt, rs);
		} 
		return nameList;
	}// selectName END

	
	
	/** 입고 품목 정보입력 **/
	public void insertNow(Sku cafe) {
		PreparedStatement pstmt = null;
		// "insert into playbucks (category, SKU, name, now, bestBefore) values (?, ? ,
		// ?, ? , ?)";
		int n = 0;

		try {
			// 유통기한 입력 유효성 검사
	        String bestBeforeInput = cafe.getBestBefore();
	        java.sql.Date bestBefore = null;
	        if (!bestBeforeInput.isEmpty()) {
	            try {
	                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	                java.util.Date parsedDate = dateFormat.parse(bestBeforeInput);
	                bestBefore = new java.sql.Date(parsedDate.getTime());
	            } catch (ParseException e) {
	                System.out.println("유통기한 형식이 올바르지 않습니다.");
	                return; 
	            }
	        } else {
	            System.out.println("유통기한을 입력해주세요.");
	            return; 
	        }
			
			
	        pstmt = conn.prepareStatement(QUERY_INSERT_NOW);
	        pstmt.setString(1, cafe.getCategory());
	        pstmt.setString(2, cafe.getSKU());
	        pstmt.setString(3, cafe.getName());
	        pstmt.setInt(4, cafe.getNow());
	        pstmt.setString(5, cafe.getBestBefore());

	        n = pstmt.executeUpdate();

	        if (n > 0) {
	            System.out.println("입고 처리가 완료되었습니다.");
	        }
	    } catch (SQLException e) {
	        System.out.println("queryInsertNow ERR: " + e.getMessage());
	    } finally {
	        if (pstmt != null) {
	            close(pstmt);
	        }
	    }
	}// insertNow END

	@Override
	/** 재고수량 0인 상품 레코드 삭제 **/
	public void delete() {
		PreparedStatement pstmt = null;
		int now = 0;

		try {
			pstmt = conn.prepareStatement(QUERY_DELETE);
			pstmt.setInt(1, now);
			int n = pstmt.executeUpdate();
			if (n > 0) {
				System.out.println("재고가 없는 상품 목록 " + n + "개 삭제 성공 !");
			}
		} catch (SQLException e) {
			System.out.println("delete() ERR : " + e.getMessage());
		} finally {
			close(pstmt);
		}
	}
	
	@Override // "update playbucks set now = ? where name = ?";
	// 수량 입력에 따른 현 재고량 수정
	public void updatePlus(int selectNow, String selectName) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(QUERY_UPDATE_NOW_PLUS);
			pstmt.setInt(1, selectNow);

			pstmt.setString(2, selectName);
			int n = pstmt.executeUpdate();
			if (n > 0) {
				System.out.println("수량 입력에 따른 현 재고량 " + n + "개 수정 성공 !");
			} else {
				System.out.println("검색 결과가 없습니다~~~");
			}
		} catch (SQLException e) {
			System.out.println("update() ERR : " + e.getMessage());
		} finally {
			close(pstmt);
		}
	}

	@Override
	public void updateMinus(int selectNow, String selectName) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(QUERY_UPDATE_NOW_MINUS);
			pstmt.setInt(1, selectNow);
			pstmt.setString(2, selectName);
			int n = pstmt.executeUpdate();
			if (n > 0) {
				System.out.println("수량 입력에 따른 현 재고량 " + n + "개 수정 성공 !");
			} else {
				System.out.println("검색 결과가 없습니다~~~");
			}
		} catch (SQLException e) {
			System.out.println("update() ERR : " + e.getMessage());
		} finally {
			close(pstmt);
		}
	}

	public void updateDDay() {
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("UPDATE playbucks SET dDay = DATEDIFF(bestBefore, CURDATE())");
		} catch (SQLException e) {
        System.out.println("updateDDay() ERR: " + e.getMessage());
		}
	}	
	
	public void lineUp() {
		try(Statement stmt = conn.createStatement()){
			stmt.executeUpdate("SELECT * FROM playbucks ORDER BY bestBefore ASC");
		}catch(SQLException e) {
			e.getStackTrace();
		}
	}
}

















