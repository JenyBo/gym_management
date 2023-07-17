package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.db.GymDB;

public class Authentication {
	
	public static ResultSet authentication(String username, String password) throws SQLException {
		String SELECT_QUERY = "SELECT * FROM tai_khoan WHERE tai_khoan = ? AND mat_khau = ?";
        
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet result = preparedStatement.executeQuery();
        return result;
	}
	
	public Authentication() {
		// TODO Auto-generated constructor stub
	}

}
