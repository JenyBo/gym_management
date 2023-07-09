package entity.model;

public class ThongKeGoiTap {
	
	String tenGoiTap;
	int luongDangKi;
	
	

	public ThongKeGoiTap(String tenGoiTap, int luongDangKi) {
		super();
		this.tenGoiTap = tenGoiTap;
		this.luongDangKi = luongDangKi;
	}



	public String getTenGoiTap() {
		return tenGoiTap;
	}



	public void setTenGoiTap(String tenGoiTap) {
		this.tenGoiTap = tenGoiTap;
	}



	public int getLuongDangKi() {
		return luongDangKi;
	}



	public void setLuongDangKi(int luongDangKi) {
		this.luongDangKi = luongDangKi;
	}



	public ThongKeGoiTap() {
		// TODO Auto-generated constructor stub
	}

}
