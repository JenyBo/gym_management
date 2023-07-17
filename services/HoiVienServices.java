package services;

import static utils.Utils.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import entity.db.GymDB;
import entity.model.HoiVien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HoiVienServices {
	
	public static ObservableList<HoiVien> getAllHoiVien() throws SQLException {
		
		ObservableList<HoiVien> hoiVienList = FXCollections.observableArrayList();
        String SELECT_QUERY = "SELECT * FROM `hoi_vien`";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        ResultSet result = preparedStatement.executeQuery();
        
        while (result.next()) {
        	hoiVienList.add(new HoiVien(result.getInt("id"), result.getString("ho_ten"),convertDate(result.getString("sinh_nhat")),
        			result.getString("loai_thanh_vien"), result.getString("gioi_tinh"), result.getString("nghe_nghiep")));
		}
        
        return hoiVienList;
    }
	
	
	public static ObservableList<HoiVien> findHoiVien(int idHoiVien) throws SQLException {
		ObservableList<HoiVien> hoiVienList = FXCollections.observableArrayList();

		 String SELECT_QUERY =
	                "SELECT * FROM hoi_vien " +
	                        "WHERE ID =?";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        preparedStatement.setInt(1, idHoiVien);
        ResultSet result = preparedStatement.executeQuery();
	        
        while (result.next()) {
        	hoiVienList.add(new HoiVien(result.getInt("id"), result.getString("ho_ten"),convertDate(result.getString("sinh_nhat")),
        			result.getString("loai_thanh_vien"), result.getString("gioi_tinh"), result.getString("nghe_nghiep")));
		}
        
        return hoiVienList;
    }
	
    public static int getTotalHoiVien() {
        int total = 0;
        String GET_QUERY = "SELECT COUNT(*) FROM hoi_vien";
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
	
	public static int addHoiVien(String hoVaTen, String ngaySinh, String gioiTinh, String loaiThanhVien, String ngheNghiep) throws SQLException {
		
        String INSERT_QUERY = "INSERT INTO `hoi_vien`(`ho_ten`, `sinh_nhat`, `nghe_nghiep`, `gioi_tinh`, `loai_thanh_vien`) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(INSERT_QUERY);
        preparedStatement.setString(1, hoVaTen);
        preparedStatement.setString(2, ngaySinh);
        preparedStatement.setString(3, ngheNghiep);
        preparedStatement.setString(4, gioiTinh);
        preparedStatement.setString(5, loaiThanhVien);
        return preparedStatement.executeUpdate();
    }
	
    public static int deleteHoiVien(int ID) throws SQLException {
        String DELETE_QUERY =
                "DELETE FROM hoi_vien " +
                        "WHERE ID =?";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(DELETE_QUERY);
        preparedStatement.setInt(1, ID);
        return preparedStatement.executeUpdate();
    }
    
    public static int updateHoiVien(int ID, String hoVaTen, String ngaySinh, String gioiTinh, String loaiThanhVien, String ngheNghiep) throws SQLException {
		String UPDATE_QUERY = "UPDATE `hoi_vien` SET `ho_ten`=?,`sinh_nhat`=?,`nghe_nghiep`=?,`gioi_tinh`=?,`loai_thanh_vien`=? WHERE id = ?";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(UPDATE_QUERY);
		preparedStatement.setString(1, hoVaTen);
		preparedStatement.setString(2, ngaySinh);
		preparedStatement.setString(3, ngheNghiep);
		preparedStatement.setString(4, gioiTinh);
		preparedStatement.setString(5, loaiThanhVien);
		preparedStatement.setInt(6, ID);
		preparedStatement.execute();
		return preparedStatement.executeUpdate();
	}

	public HoiVienServices() {
		// TODO Auto-generated constructor stub
	}

}
