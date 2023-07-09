package entity.model;

import java.text.NumberFormat;
import java.util.Locale;

public class GoiTap {

	private int id, soTien;
	private String tenGoiTap, loaiGoiTap;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSoTien() {
	    Locale locale = new Locale("vi", "VN");
	    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
	    String strNumber = currencyFormat.format(soTien);
	    return strNumber;
	}
	public void setSoTien(int soTien) {
		this.soTien = soTien;
	}
	public String getTenGoiTap() {
		return tenGoiTap;
	}
	public void setTenGoiTap(String tenGoiTap) {
		this.tenGoiTap = tenGoiTap;
	}
	public String getLoaiGoiTap() {
		return loaiGoiTap;
	}
	public void setLoaiGoiTap(String loaiGoiTap) {
		this.loaiGoiTap = loaiGoiTap;
	}
	public GoiTap(int id, int soTien, String tenGoiTap, String loaiGoiTap) {
		super();
		this.id = id;
		this.soTien = soTien;
		this.tenGoiTap = tenGoiTap;
		this.loaiGoiTap = loaiGoiTap;
	}
	public int getSoTien_int() {
		// TODO Auto-generated method stub
		return soTien;
	}
	

}
