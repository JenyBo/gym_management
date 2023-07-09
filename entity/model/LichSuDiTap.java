package entity.model;

public class LichSuDiTap {
	int id;
	String tenHoiVien, tenGoiTap, tenPhongTap, ngaySuDung;
	
	

	public LichSuDiTap(int id, String tenHoiVien, String tenGoiTap, String tenPhongTap, String ngaySuDung) {
		super();
		this.id = id;
		this.tenHoiVien = tenHoiVien;
		this.tenGoiTap = tenGoiTap;
		this.tenPhongTap = tenPhongTap;
		this.ngaySuDung = ngaySuDung;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTenHoiVien() {
		return tenHoiVien;
	}


	public void setTenHoiVien(String tenHoiVien) {
		this.tenHoiVien = tenHoiVien;
	}


	public String getTenGoiTap() {
		return tenGoiTap;
	}


	public void setTenGoiTap(String tenGoiTap) {
		this.tenGoiTap = tenGoiTap;
	}


	public String getTenPhongTap() {
		return tenPhongTap;
	}


	public void setTenPhongTap(String tenPhongTap) {
		this.tenPhongTap = tenPhongTap;
	}


	public String getNgaySuDung() {
		return ngaySuDung;
	}


	public void setNgaySuDung(String ngaySuDung) {
		this.ngaySuDung = ngaySuDung;
	}


	public LichSuDiTap() {
		// TODO Auto-generated constructor stub
	}

}
