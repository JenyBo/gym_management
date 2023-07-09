package entity.model;

public class HoiVien {
	private int id;
	private String hoTen, ngaySinh, loaiThanhVien, gioiTinh, ngheNghiep;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public String getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(String ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getLoaiThanhVien() {
		return loaiThanhVien;
	}
	public void setLoaiThanhVien(String loaiThanhVien) {
		this.loaiThanhVien = loaiThanhVien;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getNgheNghiep() {
		return ngheNghiep;
	}
	public void setNgheNghiep(String ngheNghiep) {
		this.ngheNghiep = ngheNghiep;
	}
	public HoiVien(int id, String hoTen, String ngaySinh, String loaiThanhVien, String gioiTinh, String ngheNghiep) {
		super();
		this.id = id;
		this.hoTen = hoTen;
		this.ngaySinh = ngaySinh;
		this.loaiThanhVien = loaiThanhVien;
		this.gioiTinh = gioiTinh;
		this.ngheNghiep = ngheNghiep;
	}
	

}
