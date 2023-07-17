package services;

import entity.db.GymDB;
import entity.model.GoiTap;
import entity.model.ThietBi;
import entity.model.ThuPhi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static utils.Utils.convertDate;

public class ThuPhiServices {

	public static String getHoiVienById(int id) throws SQLException {
		String SELECT_QUERY = "SELECT `ho_ten` FROM `hoi_vien` WHERE `id` = ?";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
		preparedStatement.setInt(1, id);
		ResultSet result = preparedStatement.executeQuery();

		if (result.next()) {
			return result.getString("ho_ten");
		} else {
			return null; // or throw an exception, return a default value, etc.
		}
	}
	
	public static int getAllSumPhi() throws SQLException {
		String SELECT_QUERY = "SELECT SUM(goi_tap.so_tien) AS doanh_thu from thu_phi JOIN goi_tap ON thu_phi.id_goi_tap = goi_tap.id";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
		ResultSet result = preparedStatement.executeQuery();

		result.next();
		return result.getInt("doanh_thu");
	}
	
	public static int getSumPhi(String fromDate, String toDate) throws SQLException {
		String SELECT_QUERY = "SELECT SUM(goi_tap.so_tien) AS doanh_thu from thu_phi JOIN goi_tap ON thu_phi.id_goi_tap = goi_tap.id\r\n"
				+ "WHERE thu_phi.ngay_thu_phi >= ?\r\n"
				+ "AND thu_phi.ngay_thu_phi <= ?";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
		preparedStatement.setString(1, fromDate);
		preparedStatement.setString(2, toDate);
		ResultSet result = preparedStatement.executeQuery();

		result.next();
		return result.getInt("doanh_thu");
	}

	public static GoiTap getGoiTapById(int id) throws SQLException {
		String SELECT_QUERY = "SELECT `ten_goi_tap` , `so_tien`  FROM `goi_tap` WHERE `id` = ?";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
		preparedStatement.setInt(1, id);
		ResultSet result = preparedStatement.executeQuery();

		if (result.next()) {
			return new GoiTap(id, result.getInt("so_tien"),result.getString("ten_goi_tap"),
					result.getString("loai_goi_tap"));
		} else {
			return null; // or throw an exception, return a default value, etc.
		}
	}

	public static ObservableList<ThuPhi> getAllThuPhi() throws SQLException {

		ObservableList<ThuPhi> thuPhiList = FXCollections.observableArrayList();
		String SELECT_QUERY = "SELECT tp.id , tp.ngay_thu_phi, hv.ho_ten, gt.ten_goi_tap, gt.so_tien, gt.loai_goi_tap FROM `thu_phi` tp " +
				"JOIN `hoi_vien` hv ON tp.id_hoi_vien = hv.id " +
				"JOIN `goi_tap` gt ON tp.id_goi_tap = gt.id";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
		ResultSet result = preparedStatement.executeQuery();

		while (result.next()) {
			int id = result.getInt("id");
			String ngayThuPhi = convertDate(result.getString("ngay_thu_phi"));
			String hoTen = result.getString("ho_ten");
			String tenGoiTap = result.getString("ten_goi_tap");
			int soTien = result.getInt("so_tien");
			String loaiGoiTap = result.getString("loai_goi_tap");

			ThuPhi thuPhi = new ThuPhi(id, hoTen, tenGoiTap, soTien, loaiGoiTap, ngayThuPhi);
			thuPhiList.add(thuPhi);
		}

		return thuPhiList;
	}
	
	public static ObservableList<ThuPhi> getAllThuPhiUser(int id_nguoi_dung) throws SQLException {

		ObservableList<ThuPhi> thuPhiList = FXCollections.observableArrayList();
		String SELECT_QUERY = "SELECT tp.id , tp.ngay_thu_phi, hv.ho_ten, gt.ten_goi_tap, gt.so_tien, gt.loai_goi_tap FROM `thu_phi` tp " +
				"JOIN `hoi_vien` hv ON tp.id_hoi_vien = hv.id " +
				"JOIN `goi_tap` gt ON tp.id_goi_tap = gt.id WHERE hv.id=?";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(SELECT_QUERY);
		preparedStatement.setInt(1, id_nguoi_dung);
		ResultSet result = preparedStatement.executeQuery();

		while (result.next()) {
			int id = result.getInt("id");
			String ngayThuPhi = convertDate(result.getString("ngay_thu_phi"));
			String hoTen = result.getString("ho_ten");
			String tenGoiTap = result.getString("ten_goi_tap");
			int soTien = result.getInt("so_tien");
			String loaiGoiTap = result.getString("loai_goi_tap");

			ThuPhi thuPhi = new ThuPhi(id, hoTen, tenGoiTap, soTien, loaiGoiTap, ngayThuPhi);
			thuPhiList.add(thuPhi);
		}

		return thuPhiList;
	}

	public static int addThuPhi(int idHoiVien, int idGoiTap, String ngayThuPhi) throws SQLException {

		String INSERT_QUERY = "INSERT INTO `thu_phi`(`id_hoi_vien`, `id_goi_tap`, `ngay_thu_phi`) VALUES (?, ?, ?)";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(INSERT_QUERY);
		preparedStatement.setInt(1, idHoiVien);
		preparedStatement.setInt(2, idGoiTap);
		preparedStatement.setString(3, ngayThuPhi);

		return preparedStatement.executeUpdate();
	}
	public static int deleteThuPhi(int ID) throws SQLException {
		String DELETE_QUERY =
				"DELETE FROM `thu_phi` " +
						"WHERE `id` =?";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(DELETE_QUERY);
		preparedStatement.setInt(1, ID);
		return preparedStatement.executeUpdate();
	}

	public static int updateThuPhi(int ID, int idHoiVien, int idGoiTap, String ngayThuPh) throws SQLException {
		String UPDATE_QUERY = "UPDATE `thu_phi` SET `id_hoi_vien`=?, `id_goi_tap`=?, `ngay_thu_phi`=? WHERE `id` = ?";
		PreparedStatement preparedStatement = GymDB.getConnection().prepareStatement(UPDATE_QUERY);
		preparedStatement.setInt(1, idHoiVien);
		preparedStatement.setInt(2, idGoiTap);
		preparedStatement.setString(3, ngayThuPh);
		preparedStatement.setInt(4, ID);
		preparedStatement.execute();
		return preparedStatement.executeUpdate();
	}
}
