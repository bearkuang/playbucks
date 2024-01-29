package com.mini.dto;

//seqNo int not null auto_increment,
//SKU char(20) not null,
//name varchar(20) not null,
//now int(1) default 0,
//bestBefore char(10) not null,
//dDay int(1) default 0,
//primary key(seqNo, name)

public class Sku {
	
	private int seqNo = 0;
	private String category = "";
	private String SKU ="";
	private String name = "";
	private int now = 0; 
	private String bestBefore = "";
	private int dDay = 0;

	// insert() 입력 생성자
		public Sku(String category, String sKU, String name, int now, String bestBefore) {
			this.category = category;
			this.SKU = sKU;
			this.name = name;
			this.now = now;
			this.bestBefore = bestBefore;
		}

		public Sku(String inputName, String inputNow) {

		}

		public Sku(int seqNo, String category, String sKU, String name, int now, String bestBefore, int dDay) {
			// super();
			this.seqNo = seqNo;
			this.category = category;
			this.SKU = sKU;
			this.name = name;
			this.now = now;
			this.bestBefore = bestBefore;
			this.dDay = dDay;
		}

		public int getSeqNo() {
			return seqNo;
		}

		public void setSeqNo(int seqNo) {
			this.seqNo = seqNo;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getSKU() {
			return SKU;
		}

		public void setSKU(String sKU) {
			SKU = sKU;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getNow() {
			return now;
		}

		public void setNow(int now) {
			this.now = now;
		}

		public String getBestBefore() {
			return bestBefore;
		}

		public void setBestBefore(String bestBefore) {
			this.bestBefore = bestBefore;
		}

		public int getdDay() {
			return dDay;
		}

		public void setdDay(int dDay) {
			this.dDay = dDay;
		}

		@Override
		public String toString() {
			return "Sku [seqNo=" + seqNo + ", category=" + category + ", SKU=" + SKU + ", name=" + name + ", now=" + now
					+ ", bestBefore=" + bestBefore + ", dDay=" + dDay + "]";
		}


}
