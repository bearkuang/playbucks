package com.mini;

import com.mini.util.UtilClass;

public class MainClass {

	public static void main(String[] args) {
		System.out.println("Playbucks 재고관리 프로그램을 준비중입니다. 잠시만 기다려주세요.");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		UtilClass.start();
		
	}

}
