package services;

import static utils.Utils.convertDate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import entity.db.GymDB;
import entity.model.GoiTap;
import entity.model.HoiVien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GoiTapServices {
    public static int getSoTienById(int id) throws SQLException {
        String SELECT_QUERY = "SELECT `so_tien` FROM `goi_tap` WHERE id = ?";

        PreparedStatement statement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("so_tien");
            } else {
                return 0;
            }
    }

	public static ObservableList<GoiTap> getAllGoiTap() throws SQLException {

        ObservableList<GoiTap> goiTapList = FXCollections.observableArrayList();
        String SELECT_QUERY = "SELECT * FROM goi_tap";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            goiTapList.add(new GoiTap(result.getInt("id"), result.getInt("so_tien"),
                    result.getString("ten_goi_tap"), result.getString("loai_goi_tap")));
        }

        return goiTapList;
    }
	
	
    public static int getTotalGoiTap() {
        int total = 0;
        String GET_QUERY = "SELECT COUNT(*) FROM goi_tap";
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
	

	public static ObservableList<String> getLoaiGoiTapAll() throws SQLException {
		
		ObservableList<String> goiTapList = FXCollections.observableArrayList();
        
        
        return goiTapList;
    }

    public static ObservableList<String> getTenGoiTapAll() throws SQLException {
        ObservableList<String> tenGoiTapList = FXCollections.observableArrayList();
        Set<String> uniqueValues = new HashSet<>(); // To store unique values

        String SELECT_QUERY = "SELECT `ten_goi_tap` FROM `goi_tap`";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            String tenGoiTap = result.getString("ten_goi_tap");
            if (!uniqueValues.contains(tenGoiTap)) { // Check if the value already exists
                tenGoiTapList.add(tenGoiTap);
                uniqueValues.add(tenGoiTap); // Add the value to the set
            }
        }

        return tenGoiTapList;
    }

    public static ObservableList<String> getLoaiGoiTapList() throws SQLException {

        ObservableList<String> loaiGoiTapList = FXCollections.observableArrayList();
        Set<String> uniqueValues = new HashSet<>(); // To store unique values

        String SELECT_QUERY = "SELECT `loai_goi_tap` FROM `goi_tap`";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            String loadiGoiTap = result.getString("loai_goi_tap");
            if (!uniqueValues.contains(loadiGoiTap)) { // Check if the value already exists
                loaiGoiTapList.add(loadiGoiTap);
                uniqueValues.add(loadiGoiTap); // Add the value to the set
            }
        }
        return loaiGoiTapList;
    }
	
	
    public static int addGoiTap(int ID, String tenGoiTap, String soTien, String loaiGoiTap) throws SQLException {
        int soTien_int = Integer.parseInt(soTien);
        String UPDATE_QUERY = "INSERT INTO `goi_tap`(`ten_goi_tap`, `so_tien`, `loai_goi_tap`) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(UPDATE_QUERY); 
        preparedStatement.setString(1, tenGoiTap);
        preparedStatement.setInt(2, soTien_int);
        preparedStatement.setString(3, loaiGoiTap);
        return preparedStatement.executeUpdate();
        
    
    }

    
    public static int deleteGoiTap(int ID) throws SQLException {
        String DELETE_QUERY =
                "DELETE FROM goi_tap " +
                        "WHERE ID =?";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(DELETE_QUERY);
        preparedStatement.setInt(1, ID);
        return preparedStatement.executeUpdate();
    }
    
    public static int updateGoiTap(int ID, String tenGoiTap, String soTien) throws SQLException {
    	int soTien_int = Integer.parseInt(soTien);
		String UPDATE_QUERY = "UPDATE `goi_tap` SET `ten_goi_tap`=?,`so_tien`=? WHERE id = ?";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(UPDATE_QUERY);
		preparedStatement.setString(1, tenGoiTap);
		preparedStatement.setInt(2, soTien_int);
		preparedStatement.setInt(3, ID);
		preparedStatement.execute();
		return preparedStatement.executeUpdate();
	}


	public static ObservableList<GoiTap> findGoiTap(int idGoiTap) throws SQLException {
		ObservableList<GoiTap> goiTapList = FXCollections.observableArrayList();

		 String SELECT_QUERY =
	                "SELECT * FROM goi_tap " +
	                        "WHERE ID =?";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        preparedStatement.setInt(1, idGoiTap);
        ResultSet result = preparedStatement.executeQuery();
	        

        while (result.next()) {
            goiTapList.add(new GoiTap(result.getInt("id"), result.getInt("so_tien"),
                    result.getString("ten_goi_tap"), result.getString("loai_goi_tap")));
        }

        return goiTapList;
    }
	
}
