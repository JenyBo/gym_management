package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.db.GymDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoleServices {
	
	public static ObservableList<String> getTenAll() throws SQLException {
		
		ObservableList<String> tenRoleList = FXCollections.observableArrayList();
        String SELECT_QUERY = "SELECT `ten_role` FROM `role`";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        ResultSet result = preparedStatement.executeQuery();
        
        while (result.next()) {
        	tenRoleList.add(result.getString("ten_role"));
		}
        
        return tenRoleList;
    }
	
	public static String getNameViaId(int id_role) throws SQLException {
		String SELECT_QUERY = "SELECT `ten_role` FROM `role` WHERE id = ?";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        preparedStatement.setInt(1, id_role);
        ResultSet result = preparedStatement.executeQuery();
        result.next();
        return result.getString("ten_role");
	}

	public RoleServices() {
		// TODO Auto-generated constructor stub
	}

}
