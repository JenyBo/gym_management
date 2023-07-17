package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.db.GymDB;
import entity.model.NhanVien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NhanVienServices {
	
	public static ObservableList<NhanVien> getAllNhanVien() throws SQLException {
		
		ObservableList<NhanVien> phongTapList = FXCollections.observableArrayList();
        String SELECT_QUERY = "SELECT nhan_vien.id, nhan_vien.id_phong_tap, nhan_vien.ho_va_ten, phong_tap.ten_phong, role.ten_role FROM `nhan_vien` JOIN phong_tap ON nhan_vien.id_phong_tap = phong_tap.id LEFT JOIN tai_khoan ON tai_khoan.id_nhan_vien = nhan_vien.id JOIN role ON tai_khoan.id_role = role.id WHERE role.id NOT IN (5) \r\n"
        		+ "UNION \r\n"
        		+ "SELECT nhan_vien.id, nhan_vien.id_phong_tap, nhan_vien.ho_va_ten, phong_tap.ten_phong, role.ten_role FROM `nhan_vien` JOIN phong_tap ON nhan_vien.id_phong_tap = phong_tap.id RIGHT JOIN tai_khoan ON tai_khoan.id_nhan_vien = nhan_vien.id JOIN role ON tai_khoan.id_role = role.id WHERE role.id NOT IN (5)";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        ResultSet result = preparedStatement.executeQuery();
        
        while (result.next()) {
        	phongTapList.add(new NhanVien(result.getInt("id"), result.getInt("id_phong_tap"),
        			result.getString("ho_va_ten"), result.getString("ten_phong"), result.getString("ten_role")));
		}
        
        return phongTapList;
    }
	
    public static int getTotalNhanVien() {
        int total = 0;
        String GET_QUERY = "SELECT COUNT(*) FROM nhan_vien";
        try {
            PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(GET_QUERY);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                total = result.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return total;
    }
	
	public static int addNhanVien(String hoVaTen, int id_phong_tap) throws SQLException {
		
        String INSERT_QUERY = "INSERT INTO `nhan_vien`(`id_phong_tap`, `ho_va_ten`) VALUES (?, ?)";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(INSERT_QUERY);
        preparedStatement.setInt(1, id_phong_tap);
        preparedStatement.setString(2, hoVaTen);
        return preparedStatement.executeUpdate();
    }
	
    public static int deleteNhanVien(int ID) throws SQLException {
        String DELETE_QUERY =
                "DELETE FROM nhan_vien " +
                        "WHERE ID =?";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(DELETE_QUERY);
        preparedStatement.setInt(1, ID);
        return preparedStatement.executeUpdate();
    }
    
    public static int updateNhanVien(int ID, int id_phong_tap, String hoVaTen) throws SQLException {
		String UPDATE_QUERY = "UPDATE `nhan_vien` SET `id_phong_tap`=?,`ho_va_ten`=? WHERE ID=? ";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(UPDATE_QUERY);
		preparedStatement.setInt(1, id_phong_tap);
		preparedStatement.setString(2, hoVaTen);
		preparedStatement.setInt(3, ID);
		preparedStatement.execute();
		return preparedStatement.executeUpdate();
	}
	
	
	public NhanVienServices() {
		// TODO Auto-generated constructor stub
	}

}
