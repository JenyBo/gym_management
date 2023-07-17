package services;

import static utils.Utils.convertDate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.db.GymDB;
import entity.model.ThietBi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ThietBiServices {
	
	public static int getPhongTapId(String tenPhongTap) throws SQLException {
	    String SELECT_QUERY = "SELECT id FROM `phong_tap` WHERE ten_phong = ?";
	    PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
	    preparedStatement.setString(1, tenPhongTap);
	    ResultSet result = preparedStatement.executeQuery();

	    if (result.next()) {
	        return result.getInt("id");
	    } else {
	        // Handle the case where the PhongTap with the given tenPhongTap doesn't exist
	        return -1; // or throw an exception, return a default value, etc.
	    }
	}
	
	public static String getNameViaId(int id) throws SQLException {
	    String SELECT_QUERY = "SELECT thiet_bi.ten FROM thiet_bi WHERE id = ?";
	    PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
	    preparedStatement.setInt(1, id);
	    ResultSet result = preparedStatement.executeQuery();

	    result.next();
	    return result.getString("ten");
	}
	
	public static int getCountStatus(String name, String status) throws SQLException {
	    String SELECT_QUERY = "SELECT COUNT(DISTINCT id) as cnt FROM thiet_bi WHERE ten = ? AND tinh_trang = ?";
	    PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
	    preparedStatement.setString(1, name);
	    preparedStatement.setString(2, status);
	    ResultSet result = preparedStatement.executeQuery();

	    result.next();
	    return result.getInt("cnt");
	}
	
//    public static List<ThietBi> getStatisticCSVC() {
//    	List<ThietBi>result = new ArrayList<ThietBi>();
//        String GET_QUERY = "SELECT LoaiDoDung, TinhTrang, COUNT(*) AS SoLuong FROM cosovatchat GROUP BY LoaiDoDung, TinhTrang;";
//        try {
//            PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(GET_QUERY);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                result.add(new ThietBi(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return result;
//    }
	
    public static int getTotalThietBi() {
        int total = 0;
        String GET_QUERY = "SELECT COUNT(*) FROM thiet_bi";
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
	
	public static ObservableList<ThietBi> getAllThietBi() throws SQLException {

		ObservableList<ThietBi> thietBiList = FXCollections.observableArrayList();
		String SELECT_QUERY = "SELECT tb.id, tb.ten, tb.ngay_nhap_ve, tb.xuat_xu, tb.tinh_trang, pt.ten_phong AS ten_phong FROM `thiet_bi` tb " +
				"JOIN `phong_tap` pt ON tb.id_phong_tap = pt.id";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
		ResultSet result = preparedStatement.executeQuery();

		while (result.next()) {
			int id = result.getInt("id");
			String ten = result.getString("ten");
			String ngayNhapVe = convertDate(result.getString("ngay_nhap_ve"));
			String xuatXu = result.getString("xuat_xu");
			String tinhTrang = result.getString("tinh_trang");
			String tenPhong = result.getString("ten_phong");

			ThietBi thietBi = new ThietBi(id, ten, ngayNhapVe, xuatXu, tinhTrang, tenPhong);
			thietBiList.add(thietBi);
		}

		return thietBiList;
	}

	public static int addThietBi(int idRoom, String ten, String ngayNhapVe, String xuatXu, String tinhTrang) throws SQLException {

		String INSERT_QUERY = "INSERT INTO `thiet_bi`(`id_phong_tap`, `ten`, `ngay_nhap_ve`, " +
				"`xuat_xu`, `tinh_trang`) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(INSERT_QUERY);
		preparedStatement.setInt(1, idRoom);
		preparedStatement.setString(2, ten);
		preparedStatement.setString(3, ngayNhapVe);
		preparedStatement.setString(4, xuatXu);
		preparedStatement.setString(5, tinhTrang);

		return preparedStatement.executeUpdate();
	}

//	public static ThietBi findThietBiById(int ID) throws SQLException{
//		String SELECT_QUERY = "SELECT * FROM `thiet_bi` WHERE `id` = ?";
//		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
//		ResultSet result = preparedStatement.executeQuery();
//		ThietBi thietBi = new ThietBi(result.getInt("id"), result.getInt("id_phong_tap"),
//				result.getString("ten"), convertDate(result.getString("ngay_nhap_ve")),
//				result.getString("xuat_xu"),result.getString("tinh_trang"));
//
//		return thietBi;
//	}
//
//	public static ObservableList<ThietBi> findThietBiByIdRoom(int idRoom) throws SQLException {
//		ObservableList<ThietBi> thietBiList = FXCollections.observableArrayList();
//		String SELECT_QUERY = "SELECT * FROM `thiet_bi` WHERE `id_phong_tap` = ?";
//		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
//		ResultSet result = preparedStatement.executeQuery();
//		while (result.next()) {
//			thietBiList.add(new ThietBi(result.getInt("id"), result.getInt("id_phong_tap"),
//					result.getString("ten"), convertDate(result.getString("ngay_nhap_ve")),
//					result.getString("xuat_xu"), result.getString("tinh_trang")));
//		}
//		return thietBiList;
//	}

	public static int deleteThietBi(int ID) throws SQLException {
		String DELETE_QUERY =
				"DELETE FROM `thiet_bi` " +
						"WHERE `id` =?";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(DELETE_QUERY);
		preparedStatement.setInt(1, ID);
		return preparedStatement.executeUpdate();
	}
	
	public static ArrayList<Integer> getNumberDistinct() throws SQLException {
		ArrayList<Integer> idList = new ArrayList<Integer>();
		ArrayList<String> nameList = new ArrayList<String>();
		String QUERY = "SELECT thiet_bi.id, thiet_bi.ten FROM thiet_bi";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(QUERY);
		ResultSet res = preparedStatement.executeQuery();
		while(res.next()) {
			if (!nameList.contains(res.getString("ten"))){
				nameList.add(res.getString("ten"));
				idList.add(Integer.valueOf(res.getInt("id")));
			}
		}
		return idList;
	}

	public static int updateThietBi(int ID, int idRoom, String ten, String ngayNhapVe, String xuatXu, String tinhTrang) throws SQLException {
		String UPDATE_QUERY = "UPDATE `thiet_bi` SET `id_phong_tap`=?, `ten`=?, `ngay_nhap_ve`=?," +
				"`xuat_xu`=?, `tinh_trang`=? WHERE `id` = ?";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(UPDATE_QUERY);
		preparedStatement.setInt(1, idRoom);
		preparedStatement.setString(2, ten);
		preparedStatement.setString(3, ngayNhapVe);
		preparedStatement.setString(4, xuatXu);
		preparedStatement.setString(5, tinhTrang);
		preparedStatement.setInt(6, ID);
		preparedStatement.execute();
		return preparedStatement.executeUpdate();
	}
	public ThietBiServices() {
		// TODO Auto-generated constructor stub
	}

}
