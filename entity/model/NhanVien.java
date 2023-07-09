package entity.model;

public class NhanVien {

	private int id, idPhongTap;
	private String hoVaTen, tenPhongTap, tenRole;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdPhongTap() {
		return idPhongTap;
	}
	public void setIdPhongTap(int idPhongTap) {
		this.idPhongTap = idPhongTap;
	}
	public String getHoVaTen() {
		return hoVaTen;
	}
	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
	}
	public String getTenPhongTap() {
		return tenPhongTap;
	}
	public void setTenPhongTap(String tenPhongTap) {
		this.tenPhongTap = tenPhongTap;
	}
	public String getTenRole() {
		return tenRole;
	}
	public void setTenRole(String tenRole) {
		this.tenRole = tenRole;
	}
	public NhanVien(int id, int idPhongTap, String hoVaTen, String tenPhongTap, String tenRole) {
		super();
		this.id = id;
		this.idPhongTap = idPhongTap;
		this.hoVaTen = hoVaTen;
		this.tenPhongTap = tenPhongTap;
		this.tenRole = tenRole;
	}
	

}
