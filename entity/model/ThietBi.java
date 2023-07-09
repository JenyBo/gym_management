package entity.model;

public class ThietBi {
	
	private int id;
	private String ten, ngayNhapVe, xuatXu, tinhTrang, tenPhongTap;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getNgayNhapVe() {
		return ngayNhapVe;
	}

	public void setNgayNhapVe(String ngayNhapVe) {
		this.ngayNhapVe = ngayNhapVe;
	}

	public String getXuatXu() {
		return xuatXu;
	}

	public void setXuatXu(String xuatXu) {
		this.xuatXu = xuatXu;
	}

	public String getTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	public String getTenPhongTap() {
		return tenPhongTap;
	}

	public void setTenPhongTap(String tenPhongTap) {
		this.tenPhongTap = tenPhongTap;
	}

	public ThietBi(int id, String ten, String ngayNhapVe, String xuatXu, String tinhTrang, String tenPhongTap) {
		this.id = id;
		this.ten = ten;
		this.tenPhongTap = tenPhongTap;
		this.ngayNhapVe = ngayNhapVe;
		this.xuatXu = xuatXu;
		this.tinhTrang = tinhTrang;
		this.tenPhongTap = tenPhongTap;
	}
}
