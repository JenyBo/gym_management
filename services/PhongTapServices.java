package services;

import static utils.Utils.convertDate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.db.GymDB;
import entity.model.HoiVien;
import entity.model.PhongTap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PhongTapServices {
	
	public static ObservableList<PhongTap> getAllPhongTap() throws SQLException {
		
		ObservableList<PhongTap> phongTapList = FXCollections.observableArrayList();
        String SELECT_QUERY = "SELECT * FROM `phong_tap`";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        ResultSet result = preparedStatement.executeQuery();
        
        while (result.next()) {
        	phongTapList.add(new PhongTap(result.getInt("id"), result.getString("ten_phong")));
		}
        
        return phongTapList;
    }
	
    public static int getTotalPhongTap() {
        int total = 0;
        String GET_QUERY = "SELECT COUNT(*) FROM phong_tap";
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
	
	
	public static ObservableList<String> getTenAll() throws SQLException {
		
		ObservableList<String> phongTapList = FXCollections.observableArrayList();
        String SELECT_QUERY = "SELECT `ten_phong` FROM `phong_tap`";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        ResultSet result = preparedStatement.executeQuery();
        
        while (result.next()) {
        	phongTapList.add(result.getString("ten_phong"));
		}
        
        return phongTapList;
    }
	
	
	public static int addPhongTap(String tenPhongTap) throws SQLException {
		
        String INSERT_QUERY = "INSERT INTO `phong_tap`(`ten_phong`) VALUES (?)";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(INSERT_QUERY);
        preparedStatement.setString(1, tenPhongTap);
        return preparedStatement.executeUpdate();
    }
	
    public static int deletePhongTap(int ID) throws SQLException {
        String DELETE_QUERY =
                "DELETE FROM phong_tap " +
                        "WHERE ID =?";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(DELETE_QUERY);
        preparedStatement.setInt(1, ID);
        return preparedStatement.executeUpdate();
    }
    
    public static int updatePhongTap(int ID, String tenPhongTap) throws SQLException {
		String UPDATE_QUERY = "UPDATE `phong_tap` SET `ten_phong`=? WHERE id = ?";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(UPDATE_QUERY);
		preparedStatement.setString(1, tenPhongTap);
		preparedStatement.setInt(2, ID);
		preparedStatement.execute();
		return preparedStatement.executeUpdate();
	}

	public PhongTapServices() {
		// TODO Auto-generated constructor stub
	}

}
