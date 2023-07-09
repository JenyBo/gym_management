package entity.model;

public class Role {

	int id;
	String tenRole;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTenRole() {
		return tenRole;
	}
	public void setTenRole(String tenRole) {
		this.tenRole = tenRole;
	}
	public Role(int id, String tenRole) {
		super();
		this.id = id;
		this.tenRole = tenRole;
	}

}
