package entity.model;

import java.util.Date;

public class ThuPhi {

	int id, idHoiVien, idGoiTap, soTien;

	String tenGoiTap, tenHoiVien, loaiGoiTap, ngayThuPhi;

	public String getLoaiGoiTap() {
		return loaiGoiTap;
	}

	public void setLoaiGoiTap(String loaiGoiTap) {
		this.loaiGoiTap = loaiGoiTap;
	}

	public String getNgayThuPhi() {
		return ngayThuPhi;
	}

	public void setNgayThuPhi(String ngayThuPhi) {
		this.ngayThuPhi = ngayThuPhi;
	}

	public int getSoTien() {
		return soTien;
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

	public String getTenHoiVien() {
		return tenHoiVien;
	}

	public void setTenHoiVien(String tenHoiVien) {
		this.tenHoiVien = tenHoiVien;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdHoiVien() {
		return idHoiVien;
	}

	public void setIdHoiVien(int idHoiVien) {
		this.idHoiVien = idHoiVien;
	}

	public int getIdGoiTap() {
		return idGoiTap;
	}

	public void setIdGoiTap(int idGoiTap) {
		this.idGoiTap = idGoiTap;
	}

	public ThuPhi(int id, int idHoiVien, int idGoiTap, String ngayThuPhi) {
		super();
		this.id = id;
		this.idHoiVien = idHoiVien;
		this.idGoiTap = idGoiTap;
		this.ngayThuPhi = ngayThuPhi;
	}

	public ThuPhi(int id,String tenHoiVien, String tenGoiTap, int soTien, String loaiGoiTap, String ngayThuPhi) {
		this.id = id;
		this.soTien = soTien;
		this.tenGoiTap = tenGoiTap;
		this.tenHoiVien = tenHoiVien;
		this.loaiGoiTap = loaiGoiTap;
		this.ngayThuPhi = ngayThuPhi;
	}
}
