package entity.model;

public class PhanHoi {

	private int id;
	private String tenHoiVien, noiDung, hoiDap;
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
	public String getNoiDung() {
		return noiDung;
	}
	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}
	public String getHoiDap() {
		return hoiDap;
	}
	public void setHoiDap(String hoiDap) {
		this.hoiDap = hoiDap;
	}
	public PhanHoi(int id, String tenHoiVien, String noiDung, String hoiDap) {
		super();
		this.id = id;
		this.tenHoiVien = tenHoiVien;
		this.noiDung = noiDung;
		this.hoiDap = hoiDap;
	}
	
	
	
	
	
}
