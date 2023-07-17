package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.db.GymDB;
import entity.model.GoiTapDaDangKi;
import entity.model.ThongKeGoiTap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("unused")
public class GoiTapDaDangKiServices {

	public static ObservableList<GoiTapDaDangKi> getAll() throws SQLException {

	    ObservableList<GoiTapDaDangKi> goiTapList = FXCollections.observableArrayList();
	    String SELECT_QUERY = "SELECT dkgt.id AS id, dkgt.id_hoi_vien AS idHoiVien, dkgt.id_goi_tap AS idGoiTap, hv.ho_ten AS ten_hoi_vien, gt.ten_goi_tap AS ten_goi_tap, gt.loai_goi_tap AS loai_goi_tap, dkgt.ngay_dang_ki AS ngay_dang_ki " +
	                          "FROM dang_ki_goi_tap AS dkgt " +
	                          "JOIN hoi_vien AS hv ON dkgt.id_hoi_vien = hv.id " +
	                          "JOIN goi_tap AS gt ON dkgt.id_goi_tap = gt.id";
	    PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
	    ResultSet result = preparedStatement.executeQuery();

	    while (result.next()) {
	        String ngayDangKi = result.getDate("ngay_dang_ki").toString();
	        goiTapList.add(new GoiTapDaDangKi(result.getInt("id"), result.getInt("idHoiVien"), result.getInt("idGoiTap"), result.getString("ten_hoi_vien"), result.getString("ten_goi_tap"), result.getString("loai_goi_tap"), ngayDangKi));
	    }

	    return goiTapList;
	}
	
	public static ObservableList<GoiTapDaDangKi> getAllUser(int id_nguoi_dung) throws SQLException {

	    ObservableList<GoiTapDaDangKi> goiTapList = FXCollections.observableArrayList();
	    String SELECT_QUERY = "SELECT dkgt.id AS id, dkgt.id_hoi_vien AS idHoiVien, dkgt.id_goi_tap AS idGoiTap, hv.ho_ten AS ten_hoi_vien, gt.ten_goi_tap AS ten_goi_tap, gt.loai_goi_tap AS loai_goi_tap, dkgt.ngay_dang_ki AS ngay_dang_ki " +
	                          "FROM dang_ki_goi_tap AS dkgt " +
	                          "JOIN hoi_vien AS hv ON dkgt.id_hoi_vien = hv.id " +
	                          "JOIN goi_tap AS gt ON dkgt.id_goi_tap = gt.id  WHERE hv.id = ?";
	    PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
	    preparedStatement.setInt(1, id_nguoi_dung);
	    ResultSet result = preparedStatement.executeQuery();

	    while (result.next()) {
	        String ngayDangKi = result.getDate("ngay_dang_ki").toString();
	        goiTapList.add(new GoiTapDaDangKi(result.getInt("id"), result.getInt("idHoiVien"), result.getInt("idGoiTap"), result.getString("ten_hoi_vien"), result.getString("ten_goi_tap"), result.getString("loai_goi_tap"), ngayDangKi));
	    }

	    return goiTapList;
	}
	
	public static ObservableList<ThongKeGoiTap> getAllCountDangKi() throws SQLException{
		ObservableList<ThongKeGoiTap> thongKeList = FXCollections.observableArrayList();
		String QUERY = "SELECT goi_tap.ten_goi_tap, goi_tap.loai_goi_tap, COUNT(goi_tap.id) AS so_luong FROM dang_ki_goi_tap JOIN goi_tap ON dang_ki_goi_tap.id_goi_tap = goi_tap.id GROUP BY goi_tap.id ";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(QUERY);
		ResultSet result = preparedStatement.executeQuery();
		
		while (result.next()) {
	        thongKeList.add(new ThongKeGoiTap(result.getString("ten_goi_tap") + " - " + result.getString("loai_goi_tap"), result.getInt("so_luong")));
	 
	    }
		return thongKeList;
	}
	
	
	public static ObservableList<ThongKeGoiTap> getAllDangKi(String fromDate, String toDate) throws SQLException{
		ObservableList<ThongKeGoiTap> thongKeList = FXCollections.observableArrayList();
		String QUERY = "SELECT goi_tap.ten_goi_tap, goi_tap.loai_goi_tap, COUNT(goi_tap.id) AS so_luong FROM dang_ki_goi_tap JOIN goi_tap ON dang_ki_goi_tap.id_goi_tap = goi_tap.id \r\n"
				+ "WHERE dang_ki_goi_tap.ngay_dang_ki >= ?\r\n"
				+ "AND dang_ki_goi_tap.ngay_dang_ki <= ?\r\n"
				+ "GROUP BY goi_tap.id ";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(QUERY);
		preparedStatement.setString(1, fromDate);
		preparedStatement.setString(2, toDate);
		ResultSet result = preparedStatement.executeQuery();
		
		while (result.next()) {
	        thongKeList.add(new ThongKeGoiTap(result.getString("ten_goi_tap") + " - " + result.getString("loai_goi_tap"), result.getInt("so_luong")));
	 
	    }
		return thongKeList;
	}
	
	
    public static int getTotalGoiTap() {
        int total = 0;
        String GET_QUERY = "SELECT COUNT(*) FROM dang_ki_goi_tap";
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
	
	
    public static boolean checkExist(int idHoiVien, int idGoiTap) throws SQLException {
    	String sql = "SELECT * FROM dang_ki_goi_tap WHERE id_hoi_vien = ? AND id_goi_tap = ?";
        boolean exists = false;

        try (PreparedStatement statement = GymDB.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idHoiVien);
            statement.setInt(2, idGoiTap);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    exists = true;
                }
            }
        }
        return exists;

    }
    
    public static ObservableList<Integer> getGoiTapList(int idHoiVien) throws SQLException {
        String sql = "SELECT id_goi_tap FROM dang_ki_goi_tap WHERE id_hoi_vien = " + idHoiVien;
        ObservableList<Integer> goiTapList = FXCollections.observableArrayList();

        try (PreparedStatement statement = GymDB.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idGoiTap = resultSet.getInt("id_goi_tap");
                goiTapList.add(idGoiTap);
            }
        }
        return goiTapList;

    }
	
    public static int addDangKi(int ID, int idHoiVien, int idGoiTap, String ngayDangKi) throws SQLException {
        String UPDATE_QUERY = "INSERT INTO `dang_ki_goi_tap`(`id_hoi_vien`, `id_goi_tap`, `ngay_dang_ki`) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(UPDATE_QUERY); 
        preparedStatement.setInt(1, idHoiVien);
        preparedStatement.setInt(2, idGoiTap);
        preparedStatement.setString(3, ngayDangKi);
        return preparedStatement.executeUpdate();
        
    }

    
    public static int deleteGoiTap(int ID) throws SQLException {
        String DELETE_QUERY =
                "DELETE FROM dang_ki_goi_tap " +
                        "WHERE ID =?";
        PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(DELETE_QUERY);
        preparedStatement.setInt(1, ID);
        return preparedStatement.executeUpdate();
    }
    
    public static int updateGoiTap(int ID, int idGoiTap, String ngayDangKi) throws SQLException {
		String UPDATE_QUERY = "UPDATE `dang_ki_goi_tap` SET `id_goi_tap`=?, `ngay_dang_ki`=? WHERE id = ?";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(UPDATE_QUERY);
		preparedStatement.setInt(1, idGoiTap);
		preparedStatement.setString(2, ngayDangKi);
		preparedStatement.setInt(3, ID);
		preparedStatement.execute();
		return preparedStatement.executeUpdate();
	}
	
}
