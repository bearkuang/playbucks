package com.mini.util;

import java.util.ArrayList;
import java.util.Scanner;

import com.mini.db.MySQLconnector;
import com.mini.dto.Sku;
import com.mini.view.PrintResult;

public class UtilClass {

	public UtilClass() {// 기본 생성자
	}
	
	public static void start() {
		MySQLconnector mysql = new MySQLconnector("db_playbucks");
		mysql.connectMySQL();
		// css 저장경로  -> 추후 수정 꼭 필요!!
		String css = "C:\\filetest\\playbucks\\style.css";
		// html 저장경로 -> 추후 수정 꼭 필요!!
		String html = "C:\\filetest\\playbucks\\playbucks.html";
		PrintResult.styleCSS(css);	// html 생성할 때 쓰는 style.css 미리 생성 
		
		Scanner scan = new Scanner(System.in);
		boolean status = true;
		//int select = 0;
		
		while (status) {
			PrintResult.printJobSelect();
			
			int selectMenuNo = scan.nextInt();
			
			/** 작업요청사항과 실행할 메서드 MAPPING **/
			switch (selectMenuNo) {
			// 1. 전체 레코드 조회
			case 1:
				
				mysql.selectAll();
				PrintResult.SelectAll();
				
				// case1 결과 태그에 담기, html 생성  
				String title1 = " 전체상품목록 ";
				String sub1 = " 전체 상품 목록조회 ";
				String tags1 =HtmlTagClass.selectHtml(mysql.numList, title1, sub1, css);
				PrintResult.skuHtml(html, tags1);
				
				break;
			
			// 2. 이름 입력에 따른 조회
			case 2:
				// 이름 검색 -> 유통기한 임박 순으로 정렬 
				Scanner scanKey = new Scanner(System.in);
				System.out.println("제품명을 입력해주세요. :");
				String keyword = scanKey.nextLine();
				
				
				ArrayList<Sku> selectName = mysql.selectName(keyword);
				PrintResult.listAll(selectName, keyword);
				
				// case2 결과 태그에 담기, html 생성 
				String title2 = " 검색결과 ";
				String sub2 = " 검색결과 내역 조회 ";
				String tags2 =HtmlTagClass.selectHtml(selectName, title2, sub2,css);
				PrintResult.skuHtml(html, tags2);
				
				break;
				
			// 3. 새상품 정보입력
			case 3:
				// 3. 새상품 정보입력
				Sku cafe = UtilClass.InsertInputData();
				mysql.insertNow(cafe);

				mysql.selectAll();
				PrintResult.SelectAll();
				
				// case3 결과 태그에 담기, html 생성 
				String title3 = " 검색결과 ";
				String sub3 = " 검색결과 내역 조회 ";
				String tags3 =HtmlTagClass.selectHtml(mysql.numList, title3, sub3,css);
				PrintResult.skuHtml(html, tags3);
				
				break;
				
			// 4. 이름으로 검색해서 입고 수량 조정
			case 4:
				
				scan.nextLine();
				System.out.print("검색 상품명을 입력해주세요\n" + "-->");

				String selectName1 = scan.nextLine();

				ArrayList<Sku> nowList2 = mysql.selectName(selectName1);
				PrintResult.listAll(nowList2, selectName1);

				System.out.println("입고할 상품명을 입력해주세요\n" + "-->");
				String changedName = scan.nextLine();

				System.out.println("입고할 상품 수량을 입력해주세요\n" + "-->");

				int selectNow = scan.nextInt();

				mysql.updatePlus(selectNow, changedName);
				nowList2 = mysql.selectName(changedName);
				PrintResult.listAll(nowList2, changedName);
				
				// case4 결과 태그에 담기, html 생성
				String title4 = " 입고결과 ";
				String sub4 = " 입고결과 내역 조회 ";
				String menu1 = "입고 ";
				String tags4 = HtmlTagClass.updateHtml(nowList2, title4, sub4, css,changedName,selectNow, menu1);
				PrintResult.skuHtml(html, tags4);

				break;
			
			// 5. 이름으로 검색해서 출고 수량 조정
			case 5:
				scan.nextLine();
				System.out.print("검색 상품명을 입력해주세요\n" + "-->");
				String selectName2 = scan.nextLine();

				ArrayList<Sku> nowList3 = mysql.selectName(selectName2);
				PrintResult.listAll(nowList3, selectName2);

				System.out.println("출고할 상품명을 입력해주세요\n" + "-->");
				String changedName1 = scan.nextLine();

				System.out.println("출고할 상품 수량을 입력해주세요\n" + "-->");

				int selectNow1 = scan.nextInt();

				mysql.updateMinus(selectNow1, changedName1);
				nowList3 = mysql.selectName(changedName1);
				PrintResult.listAll(nowList3, changedName1);
				
				// case5 결과 태그에 담기, html 생성
				String title5 = " 출고결과 ";
				String sub5 = " 출고결과 내역 조회 ";
				String menu2 = "출고 ";
				String tags5 = HtmlTagClass.updateHtml(nowList3, title5, sub5, css,changedName1,selectNow1,menu2);
				PrintResult.skuHtml(html, tags5);
				break;
			
				// 6. 재고가 없는 상품 레코드 삭제
			case 6:
				mysql.delete();
				mysql.selectAll();
				PrintResult.SelectAll();
				
				// case1 결과 태그에 담기, html 생성  
				String title6 = " 전체상품목록 ";
				String sub6 = " 전체 상품 목록조회 ";
				String tags6 =HtmlTagClass.selectHtml(mysql.numList, title6, sub6, css);
				PrintResult.skuHtml(html, tags6);
				
				break;
							
				// 7. 작업종료
			case 7:
				status = false;
				break;
			}
		}
		scan.close();
		System.out.println("작업이 종료 되었습니다..");
		
		
	}
	
	// case 3 : 입고물품 정보 입력 (사용자의 입력값 받는 메서드)
	// (category, SKU, name, now, bestBefore
	public static Sku InsertInputData() {
		System.out.println("상품 정보를 입력해주세요");
		Scanner scan = new Scanner(System.in);

		System.out.println("1) 품목 카테고리 : ");
		System.out.println("예) 병음료 / 상온빵 / 샌드위치 / 케이크");
		String category = scan.nextLine();

		System.out.println("2) SKU 번호 : ");
		System.out.println("예) 5210004003");
		String sKU = scan.nextLine();

		System.out.println("3) 제품명 : ");
		System.out.println("예) 블루문 에이드 190ML");
		String name = scan.nextLine();

		System.out.println("4) 수량 : ");
		System.out.println("예) 3");
		int now = scan.nextInt();
		scan.nextLine();

		System.out.println("5) 유통기한 : ");
		System.out.println("예) 2023-07-17");
		String bestBefore = scan.nextLine();
		
//		System.out.println("6) dDay : ");
//		int dDay = scan.nextInt();
//		
		return new Sku(category, sKU, name, now, bestBefore);
	}// inputData() END
	

}
