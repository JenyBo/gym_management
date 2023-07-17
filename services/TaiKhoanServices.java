package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.db.GymDB;

public class TaiKhoanServices {
	
	public static int addTaiKhoanNhanVien(int id_role, int id_nhan_vien) throws SQLException {
		
        String INSERT_QUERY = "INSERT INTO `tai_khoan`(`id_nhan_vien`, `id_role`) VALUES (?, ?)";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(INSERT_QUERY);
        preparedStatement.setInt(1, id_nhan_vien);
        preparedStatement.setInt(2, id_role);
        return preparedStatement.executeUpdate();
    }
	
	public static String getNameViaUsername(String username) throws SQLException {
		String QUERY = "SELECT nhan_vien.ho_va_ten AS Ho_Ten FROM nhan_vien JOIN tai_khoan ON nhan_vien.id = tai_khoan.id_nhan_vien WHERE tai_khoan.tai_khoan = ?\r\n"
				+ "UNION\r\n"
				+ "SELECT hoi_vien.ho_ten AS Ho_Ten FROM `hoi_vien` JOIN tai_khoan ON hoi_vien.id = tai_khoan.id_hoi_vien WHERE tai_khoan.tai_khoan = ?";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, username);
        ResultSet res = preparedStatement.executeQuery();
        res.next();
        return res.getString("Ho_Ten");
	}
	
	public static int getIDViaUsername(String username) throws SQLException {
		String QUERY = "SELECT nhan_vien.id AS ID FROM nhan_vien JOIN tai_khoan ON nhan_vien.id = tai_khoan.id_nhan_vien WHERE tai_khoan.tai_khoan = ?\r\n"
				+ "UNION\r\n"
				+ "SELECT hoi_vien.id AS ID FROM `hoi_vien` JOIN tai_khoan ON hoi_vien.id = tai_khoan.id_hoi_vien WHERE tai_khoan.tai_khoan = ?";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, username);
        ResultSet res = preparedStatement.executeQuery();
        res.next();
        return res.getInt("ID");
	}
	
	public static int addTaiKhoanHoiVien(int id_role, int id_hoi_vien) throws SQLException {
		
        String INSERT_QUERY = "INSERT INTO `tai_khoan`(`id_hoi_vien`, `id_role`) VALUES (?, ?)";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(INSERT_QUERY);
        preparedStatement.setInt(1, id_hoi_vien);
        preparedStatement.setInt(2, id_role);
        return preparedStatement.executeUpdate();
    }
	
	public static int updateMatKhauVidID(int id_tai_khoan, String newPassword) throws SQLException {
		
        String QUERY = "UPDATE `tai_khoan` SET `mat_khau`=? WHERE id=?";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, newPassword);
        preparedStatement.setInt(2, id_tai_khoan);
        return preparedStatement.executeUpdate();
    }
	
	public static int updateTaiKhoanNhanVien(String tai_khoan, String mat_khau, int id_nhan_vien) throws SQLException {
		
        String INSERT_QUERY = "UPDATE `tai_khoan` SET `tai_khoan`=?,`mat_khau`=? WHERE tai_khoan.id_nhan_vien = ?";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(INSERT_QUERY);
        preparedStatement.setString(1, tai_khoan);
        preparedStatement.setString(2, mat_khau);
        preparedStatement.setInt(3, id_nhan_vien);
        return preparedStatement.executeUpdate();
    }
	
	public static int updateRole(int ID, int id_role) throws SQLException {
        String INSERT_QUERY = "UPDATE `tai_khoan` SET `id_role`=? WHERE tai_khoan.id_nhan_vien = ?";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(INSERT_QUERY);
        preparedStatement.setInt(1, id_role);
        preparedStatement.setInt(2, ID);
        return preparedStatement.executeUpdate();
    }
	
	public static int updateTaiKhoanHoiVien(String tai_khoan, String mat_khau, int id_hoi_vien) throws SQLException {
		
        String INSERT_QUERY = "UPDATE `tai_khoan` SET `tai_khoan`=?,`mat_khau`=? WHERE tai_khoan.id_hoi_vien = ?";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(INSERT_QUERY);
        preparedStatement.setString(1, tai_khoan);
        preparedStatement.setString(2, mat_khau);
        preparedStatement.setInt(3, id_hoi_vien);
        return preparedStatement.executeUpdate();
    }
	

	public TaiKhoanServices() {
		// TODO Auto-generated constructor stub
	}

}
