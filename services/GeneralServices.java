package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.db.GymDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GeneralServices {

	public static int getIdVia2Condition (String tableName, String col1, String col2, String text1, String text2) throws SQLException {
		String SELECT_QUERY = String.format("SELECT `id` FROM %s WHERE %s = ? AND %s = ?", tableName, col1, col2);

		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
		preparedStatement.setString(1, text1);
		preparedStatement.setString(2, text2);
		ResultSet result = preparedStatement.executeQuery();
		result.next();
		return result.getInt("id");
	}
	public static int getIdViaName(String tableName, String colName, String name) throws SQLException {
        String SELECT_QUERY = String.format("SELECT `id` FROM %s WHERE %s = ?", tableName, colName);

        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        preparedStatement.setString(1, name);
        ResultSet result = preparedStatement.executeQuery();
        result.next();
        return result.getInt("id");
    }
	
	public static ObservableList<String> getDanhSachChuaCoTaiKhoan() throws SQLException{
		ObservableList<String> chuaCoTaiKhoanList = FXCollections.observableArrayList();
		String SELECT_QUERY = "SELECT hoi_vien.ho_ten as Ho_ten FROM `hoi_vien` JOIN tai_khoan ON hoi_vien.id = tai_khoan.id_hoi_vien WHERE tai_khoan.tai_khoan IS NULL\r\n"
				+ "UNION\r\n"
				+ "SELECT nhan_vien.ho_va_ten as Ho_ten FROM `nhan_vien` JOIN tai_khoan ON nhan_vien.id = tai_khoan.id_nhan_vien WHERE tai_khoan.tai_khoan IS NULL";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
		ResultSet result = preparedStatement.executeQuery();
		while (result.next()) {
			chuaCoTaiKhoanList.add(result.getString("Ho_ten"));
		}
		return chuaCoTaiKhoanList;
	}
	
	public static int getRoleViaName(String name) throws SQLException{
		int id_role;
		String SELECT_QUERY = "SELECT tai_khoan.id_role FROM `hoi_vien` JOIN tai_khoan ON hoi_vien.id = tai_khoan.id_hoi_vien WHERE hoi_vien.ho_ten = ?\r\n"
				+ "UNION\r\n"
				+ "SELECT tai_khoan.id_role FROM `nhan_vien` JOIN tai_khoan ON nhan_vien.id = tai_khoan.id_nhan_vien WHERE nhan_vien.ho_va_ten = ?";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, name);
		ResultSet result = preparedStatement.executeQuery();
		result.next();
		id_role = result.getInt("id_role");
		
		return id_role;
	}
	

	public GeneralServices() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws SQLException {
//		System.out.println(GeneralServices.getIdViaName("role", "ten_role", "Chủ phòng gym"));
		System.out.println(GeneralServices.getDanhSachChuaCoTaiKhoan().get(0));
	}

}
