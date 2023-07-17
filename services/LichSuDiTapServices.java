package services;

import static utils.Utils.convertDate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.db.GymDB;
import entity.model.LichSuDiTap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LichSuDiTapServices {
	
	public static ObservableList<LichSuDiTap> getAllLichSu(String tuNgay, String denNgay) throws SQLException {
		
		ObservableList<LichSuDiTap> lichSuList = FXCollections.observableArrayList();
        String SELECT_QUERY = "SELECT lich_su.id, hoi_vien.ho_ten, goi_tap.ten_goi_tap, goi_tap.loai_goi_tap, phong_tap.ten_phong, `ngay_su_dung` FROM `lich_su` \r\n"
        		+ "JOIN hoi_vien ON hoi_vien.id = lich_su.id_hoi_vien\r\n"
        		+ "JOIN goi_tap ON goi_tap.id = lich_su.id_goi_tap\r\n"
        		+ "JOIN phong_tap ON phong_tap.id = lich_su.id_phong_tap\r\n"
        		+ "WHERE (ngay_su_dung >= ? OR ? IS NULL)\r\n"
        		+ "AND (ngay_su_dung <= ? OR ? IS NULL)";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        preparedStatement.setString(1, tuNgay);
        preparedStatement.setString(2, tuNgay);
        preparedStatement.setString(3, denNgay);
        preparedStatement.setString(4, denNgay);
        ResultSet result = preparedStatement.executeQuery();
        
        while (result.next()) {
        	lichSuList.add(new LichSuDiTap(result.getInt("id"), result.getString("ho_ten"),
        			result.getString("ten_goi_tap") + " - " + result.getString("loai_goi_tap"), result.getString("ten_phong"), convertDate(result.getString("ngay_su_dung"))));
		}
        
        return lichSuList;
    }
	
public static ObservableList<LichSuDiTap> getAllLichSuUser(int idHoiVien, String tuNgay, String denNgay) throws SQLException {
		
		ObservableList<LichSuDiTap> lichSuList = FXCollections.observableArrayList();
        String SELECT_QUERY = "SELECT lich_su.id, hoi_vien.ho_ten, goi_tap.ten_goi_tap, goi_tap.loai_goi_tap, phong_tap.ten_phong, `ngay_su_dung` FROM `lich_su` \r\n"
        		+ "JOIN hoi_vien ON hoi_vien.id = lich_su.id_hoi_vien\r\n"
        		+ "JOIN goi_tap ON goi_tap.id = lich_su.id_goi_tap\r\n"
        		+ "JOIN phong_tap ON phong_tap.id = lich_su.id_phong_tap\r\n"
        		+ "WHERE hoi_vien.id = ? \r\n"
        		+ "AND (ngay_su_dung >= ? OR ? IS NULL)\r\n"
        		+ "AND (ngay_su_dung <= ? OR ? IS NULL)";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
        preparedStatement.setInt(1, idHoiVien);
        preparedStatement.setString(2, tuNgay);
        preparedStatement.setString(3, tuNgay);
        preparedStatement.setString(4, denNgay);
        preparedStatement.setString(5, denNgay);
        ResultSet result = preparedStatement.executeQuery();
        
        while (result.next()) {
        	lichSuList.add(new LichSuDiTap(result.getInt("id"), result.getString("ho_ten"),
        			result.getString("ten_goi_tap") + " - " + result.getString("loai_goi_tap"), result.getString("ten_phong"), convertDate(result.getString("ngay_su_dung"))));
		}
        
        return lichSuList;
    }

	public LichSuDiTapServices() {
		// TODO Auto-generated constructor stub
	}

}
