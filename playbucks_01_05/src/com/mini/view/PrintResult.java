package com.mini.view;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.mini.db.MySQLconnector;
import com.mini.dto.Sku;

public class PrintResult {

	public PrintResult() {	// 기본 생성자
	}
	
	public static void printJobSelect() {
		System.out.println();
		System.out.println("메뉴를 선택해주세요..");
		System.out.println("=> 1. 전체상품 현황조회");
		System.out.println("=> 2. 검색");
		System.out.println("=> 3. 상품등록");
		System.out.println("=> 4. 입고");
		System.out.println("=> 5. 출고");
		System.out.println("=> 6. 삭제(재고없는 품목정리)");
		System.out.println("=> 7. 작업종료하겠습니다.");
		System.out.println("=> ");
		
	}
	
	public static void SelectAll() {
		System.out.println("전체상품 현황");
		System.out.println("--------------------------------------");
		for (Sku skuList : MySQLconnector.numList) {
			System.out.println(skuList);
		}
	}// SelectAll END
	
	public static void listAll(ArrayList<Sku> selectName, String keyword) {
		System.out.println("====================================");
		System.out.println("검색어 '" + keyword + "' 의 결과는 다음과 같습니다.");
		System.out.println("------------------------------------------------");
		for (Sku nameList : selectName) {
			System.out.println(nameList);
			
		}
	}// listAll END
	
	public static void styleCSS(String css) { // style.css 저장
		FileWriter fw = null;

		try {
			fw = new FileWriter(css); // 파일 경로

			String cssTags = "";

			cssTags = cssTags + "@charset \"urf-8\";\r\n" + "\r\n" + "body {\r\n" + "    background-color: #f6f5ef;\r\n"
					+ "    border-radius: 10px;\r\n" + "    width: auto;\r\n" + "    height: auto;\r\n"
					+ "    padding-top: 100px;\r\n" + "}\r\n" + "header {\r\n" + "    position: fixed;\r\n"
					+ "    top: 0;\r\n" + "    left: 0;\r\n" + "    right: 0;\r\n" + "    \r\n"
					+ "    height: 65px;\r\n" + "    padding: 1rem;\r\n" + "    color: white;\r\n"
					+ "    background: #2F6E46;\r\n" + "    font-weight: bold;\r\n" + "    display: flex;\r\n"
					+ "    justify-content: space-between;\r\n" + "    align-items: center;\r\n" + "}\r\n" + "th {\r\n"
					+ "    text-align: center;\r\n" + "}\r\n" + "table {\r\n" + "width: 100%;\r\n"
					+ "border: 1px solid #444444;\r\n" + "border-collapse: collapse;\r\n" + "}\r\n" + "th, td {\r\n"
					+ "border: 1px solid #444444;\r\n" + "padding-left: 10px;\r\n" + "padding-right:  10px;\r\n"
					+ "padding-top: 5px;\r\n" + "padding-bottom: 5px;\r\n" + "}";

			fw.write(cssTags);

		} catch (IOException e) {
			System.out.println("IOException ERR : " + e.getMessage());
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				System.out.println("파일 생성 실패 : " + e.getMessage());
			}
		}

	}// styleCSS END
	
	public static void skuHtml (String html, String one_tags) {
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(html);
			fw.write(one_tags);
			System.out.println("--------------------------------------------------");
			System.out.println("파일 저장에 성공 했습니다.");
			System.out.println("위치" + html);
			System.out.println("--------------------------------------------------");
		} catch (IOException e) {
			System.out.println("IOException ERR : " + e.getMessage());
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				System.out.println("전체조회 파일생성 실패 : " + e.getMessage());
			}
		}
		
	} // allSKU_html END
	

}
	


