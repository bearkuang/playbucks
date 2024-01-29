package com.mini.util;

import java.util.ArrayList;

import com.mini.dto.Sku;

public class HtmlTagClass {

	public HtmlTagClass() {
		
	}
	
	public static String selectHtml (ArrayList<Sku> list,String title, String sub, String css) {
		String tags1 = "";
		
		tags1 = tags1 + "<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "    <meta charset=\"utf-8\">\n"
				+ "    <title> "+title+"</title>\n"
				+ "    <link rel=\"stylesheet\" href="+css+" type=\"text/css\">\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "<header>\n"
				+ "    <h2>PLAYBUCKS SYSTEM</h2>\n"
				+ "    <nav>\n"
				+ "        <span> "+sub+"</span>\n"
				+ "    </nav>\n"
				+ "</header>\n"
				+ "\n"
				+ "<table width=auto;>\n"
				+ "\n"
				+ "\n"
				+ "<colgroup style='text-align:left;'>\n"
				+ "<col style='width: auto;'>\n"
				+ "</colgroup>\n"
				+ "\n"
				+ "<thead>\n"
				+ "    <tr>\n"
				+ "        <th>조회번호</th>\n"
				+ "        <th>분류</th>\n"
				+ "        <th>SKU 번호</th>\n"
				+ "        <th>SKU 명</th>\n"
				+ "        <th>재고수량</th>\n"
				+ "        <th>유통기한</th>\n"
				+ "        <th>D-day</th>\n"
				+ "    </tr>\n"
				+ "</thead>\n"
				+ "<tbody>";
		
			for(int n= 0; n<list.size(); n++) {
					tags1 += "<tr>";
				    tags1 += "<td>" + list.get(n).getSeqNo() + "</td>";
				    tags1 += "<td>" + list.get(n).getCategory() + "</td>";
				    tags1 += "<td>" + list.get(n).getSKU() + "</td>";
				    tags1 += "<td>" + list.get(n).getName() + "</td>";
				    tags1 += "<td>" + list.get(n).getNow() + "</td>";
				    tags1 += "<td>" + list.get(n).getBestBefore() + "</td>";
				    tags1 += "<td>" + list.get(n).getdDay() + "</td>";
				    tags1 += "</tr>";
				}
		tags1 = tags1 + "</tbody>\n";
		tags1 = tags1+ "</table>\n";
		tags1 = tags1+ "</body>\n";
		tags1 = tags1+ "</html>";

				return tags1;
	}	// selctHtml END
	
	// 입고, 출고 case 4/5번 내역 같이 입출고 기능 표현 
	public static String updateHtml (ArrayList<Sku> list,String title, String sub, String css, String changedName, int selectNow,String menu) {
		String num = Integer.toString(selectNow);
		String tags1 = "";
		
		tags1 = tags1 + "<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "    <meta charset=\"utf-8\">\n"
				+ "    <title> "+title+"</title>\n"
				+ "    <link rel=\"stylesheet\" href="+css+" type=\"text/css\">\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "<header>\n"
				+ "    <h2>PLAYBUCKS SYSTEM</h2>\n"
				+ "    <nav>\n"
				+ "        <span> "+sub+"</span>\n"
				+ "    </nav>\n"
				+ "</header>\n"
				+ "\n"
				+ "<table width=auto;>\n"
				+ "\n"
				+ "\n"
				+ "<colgroup style='text-align:left;'>\n"
				+ "<col style='width: auto;'>\n"
				+ "</colgroup>\n"
				+ "\n"
				+ "<thead>\n"
				+ "    <tr>\n"
				+ "        <th>조회번호</th>\n"
				+ "        <th>분류</th>\n"
				+ "        <th>SKU 번호</th>\n"
				+ "        <th>SKU 명</th>\n"
				+ "        <th>재고수량</th>\n"
				+ "        <th>유통기한</th>\n"
				+ "        <th>D-day</th>\n"
				+ "    </tr>\n"
				+ "</thead>\n"
				+ "<tbody>";
		
			for(int n= 0; n<list.size(); n++) {
					tags1 += "<tr>";
				    tags1 += "<td>" + list.get(n).getSeqNo() + "</td>";
				    tags1 += "<td>" + list.get(n).getCategory() + "</td>";
				    tags1 += "<td>" + list.get(n).getSKU() + "</td>";
				    tags1 += "<td>" + list.get(n).getName() + "</td>";
				    tags1 += "<td>" + list.get(n).getNow() + "</td>";
				    tags1 += "<td>" + list.get(n).getBestBefore() + "</td>";
				    tags1 += "<td>" + list.get(n).getdDay() + "</td>";
				    tags1 += "</tr>";
				}
		tags1 = tags1 + "<br>\n";
		tags1 = tags1 + "<div>"+changedName+"가 \t"+num+"개 "+menu+"되었습니다.";
		tags1 = tags1 + "</tbody>\n";
		tags1 = tags1+ "</table>\n";
		tags1 = tags1+ "</body>\n";
		tags1 = tags1+ "</html>";

				return tags1;
	}	// selctHtml END
	
	
}
