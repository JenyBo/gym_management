package views.screen.hoivien;

import static utils.Utils.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;


import utils.ViewUtils;

import entity.model.HoiVien;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.GeneralServices;
import services.HoiVienServices;
import services.TaiKhoanServices;

public class HoiVienDetailScreenHandler implements Initializable{
	
	private int ID;
	
    @FXML
    private Button add_btn;

    @FXML
    private ChoiceBox<String> gioiTinhChoiceBox;

    @FXML
    private TextField hoVaTenTextField;

    @FXML
    private TextField loaiThanhVienTextField;

    @FXML
    private DatePicker ngaySinhDatePicker;

    @FXML
    private TextField ngheNghiepTextField;

    @FXML
    private Text title;

    @FXML
    private Button update_btn;
    
    
    public void setHoiVien(HoiVien hoiVien) {
        hoVaTenTextField.setText(hoiVien.getHoTen());
        ngaySinhDatePicker.setValue(LOCAL_DATE(hoiVien.getNgaySinh()));
        gioiTinhChoiceBox.setValue(hoiVien.getGioiTinh());
        ngheNghiepTextField.setText(hoiVien.getNgheNghiep());
        loaiThanhVienTextField.setText(hoiVien.getLoaiThanhVien());
    }
    
    public void setTitle(String title) {
        this.title.setText(title);
    }
    
    public void hide_add_btn() {
        add_btn.setVisible(false);
    }
    
    public void setID(int ID) {
        this.ID = ID;
    }
    
    public void hide_update_btn() {
        update_btn.setVisible(false);
        add_btn.setTranslateX(100);
    }
    
    @FXML
    public void addHoiVien(ActionEvent event) throws IOException {
        ViewUtils viewUtils = new ViewUtils();
        if (ngaySinhDatePicker.getValue() == null) createDialog(
                Alert.AlertType.WARNING,
                "Thông báo",
                "", "Vui lòng nhập đủ thông tin!");
        else {
            String hoVaTen = hoVaTenTextField.getText();
            String ngaySinh = ngaySinhDatePicker.getValue().toString();
            String gioiTinh = gioiTinhChoiceBox.getValue();
            String loaiThanhVien = loaiThanhVienTextField.getText();
            String ngheNghiep = ngheNghiepTextField.getText();
            if (hoVaTen.trim().equals("") || ngaySinh.trim().equals("") || ngheNghiep.trim().equals("") ||
                    ngheNghiep.trim().equals("")) {

                createDialog(
                        Alert.AlertType.WARNING,
                        "Thông báo",
                        "", "Vui lòng nhập đủ thông tin!")
                ;
            } else {
                    try {
                        int result1 = HoiVienServices.addHoiVien(hoVaTen, ngaySinh, gioiTinh, loaiThanhVien, ngheNghiep);
                        
                        int id_hoi_vien = GeneralServices.getIdViaName("hoi_vien", "ho_ten", hoVaTen);
                        
                        int result2 = TaiKhoanServices.addTaiKhoanHoiVien(5, id_hoi_vien);
                        if (result1 == 1 && result2 ==1) {
                            createDialog(
                                    Alert.AlertType.CONFIRMATION,
                                    "Thành công",
                                    "Thêm hội viên thành công!", "Hãy tạo tài khoản cho hội viên này"
                            );
                            viewUtils.switchToDangKi(event, hoVaTen, "Hội viên", id_hoi_vien, 5, false);;
                        } else {
                            createDialog(
                                    Alert.AlertType.ERROR,
                                    "Thất bại",
                                    "", "Có lỗi xảy ra, vui lòng thử lại!"
                            );
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
//                    viewUtils.switchToHoiVien(event);
                
            }
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gioiTinhChoiceBox.getItems().add("Nam");
        gioiTinhChoiceBox.getItems().add("Nữ");
        gioiTinhChoiceBox.setValue("Nam");
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        ViewUtils viewUtils = new ViewUtils();
        viewUtils.switchToHoiVien(event);
    }

    @FXML
    void update(ActionEvent event) throws IOException {
    	ViewUtils viewUtils = new ViewUtils();
        if (ngaySinhDatePicker.getValue() == null) createDialog(
                Alert.AlertType.WARNING,
                "Thông báo",
                "", "Vui lòng nhập đủ thông tin!");
        else {
            String hoVaTen = hoVaTenTextField.getText();
            String ngaySinh = ngaySinhDatePicker.getValue().toString();
            String gioiTinh = gioiTinhChoiceBox.getValue();
            String loaiThanhVien = loaiThanhVienTextField.getText();
            String ngheNghiep = ngheNghiepTextField.getText();
            if (hoVaTen.trim().equals("") || ngaySinh.trim().equals("") || ngheNghiep.trim().equals("") ||
                    ngheNghiep.trim().equals("")) {

                createDialog(
                        Alert.AlertType.WARNING,
                        "Thông báo",
                        "", "Vui lòng nhập đủ thông tin!");
            } else {
                    try {
                        int result = HoiVienServices.updateHoiVien(ID, hoVaTen, ngaySinh, gioiTinh, loaiThanhVien, ngheNghiep);
                        if (result == 1) {
                            createDialog(
                                    Alert.AlertType.CONFIRMATION,
                                    "Thành công",
                                    "", "Cập nhật thông tin hội viên thành công!"
                            );
                        } else {
                            createDialog(
                                    Alert.AlertType.ERROR,
                                    "Thất bại",
                                    "", "Có lỗi xảy ra, vui lòng thử lại!"
                            );
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    viewUtils.switchToHoiVien(event);
                
            }
        }
    }

}
