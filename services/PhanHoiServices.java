package services;

import static utils.Utils.convertDate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.db.GymDB;
import entity.model.HoiVien;
import entity.model.PhanHoi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PhanHoiServices {
	
	public static ObservableList<PhanHoi> getAllPhanHoi() throws SQLException {
		
		ObservableList<PhanHoi> phanHoiList = FXCollections.observableArrayList();
        String SELECT_QUERY = "SELECT phan_hoi.id, hoi_vien.ho_ten, `noi_dung`, `hoi_dap` FROM `phan_hoi` JOIN hoi_vien ON phan_hoi.id_hoi_vien = hoi_vien.id";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        ResultSet result = preparedStatement.executeQuery();
        
        while (result.next()) {
        	phanHoiList.add(new PhanHoi(result.getInt("id"), result.getString("ho_ten"), result.getString("noi_dung"),
        			result.getString("hoi_dap")));
		}
        
        return phanHoiList;
    }
	
	public static ObservableList<PhanHoi> getAllPhanHoiUser(int id_nguoi_dung) throws SQLException {
		
		ObservableList<PhanHoi> phanHoiList = FXCollections.observableArrayList();
        String SELECT_QUERY = "SELECT phan_hoi.id, hoi_vien.ho_ten, `noi_dung`, `hoi_dap` FROM `phan_hoi` JOIN hoi_vien ON phan_hoi.id_hoi_vien = hoi_vien.id WHERE hoi_vien.id=?";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        preparedStatement.setInt(1, id_nguoi_dung);
        ResultSet result = preparedStatement.executeQuery();
        
        while (result.next()) {
        	phanHoiList.add(new PhanHoi(result.getInt("id"), result.getString("ho_ten"), result.getString("noi_dung"),
        			result.getString("hoi_dap")));
		}
        
        return phanHoiList;
    }
	
	public static int addPhanHoi(int id_hoi_vien, String phanHoi) throws SQLException {
		String QUERY = "INSERT INTO `phan_hoi`(`id_hoi_vien`, `noi_dung`) VALUES (?, ?)";
		
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(QUERY);
        preparedStatement.setInt(1, id_hoi_vien);
        preparedStatement.setString(2, phanHoi);
        return preparedStatement.executeUpdate();
		
	}
	
	public static int deletePhanHoi(int ID) throws SQLException {
        String DELETE_QUERY = "DELETE FROM `phan_hoi` WHERE id = ?";
                
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(DELETE_QUERY);
        preparedStatement.setInt(1, ID);
        return preparedStatement.executeUpdate();
	}
	
	public static int updatePhanHoi(int ID, String phanHoi, String hoiDap) throws SQLException {
		String QUERY = "UPDATE `phan_hoi` SET `noi_dung`=?,`hoi_dap`=? WHERE id = ?";
		
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(QUERY);
		preparedStatement.setString(1, phanHoi);
		preparedStatement.setString(2, hoiDap);
        preparedStatement.setInt(3, ID);
        return preparedStatement.executeUpdate();
		
	}
	
	

	public PhanHoiServices() {
		// TODO Auto-generated constructor stub
	}

}
