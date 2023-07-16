package views.screen.thietbi;

import entity.model.HoiVien;
import entity.model.PhongTap;
import entity.model.ThietBi;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import services.HoiVienServices;
import services.PhongTapServices;
import services.ThietBiServices;
import utils.ViewUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static utils.Utils.*;
import static utils.Utils.createDialog;

public class ThietBiDetailScreenHandler implements Initializable {
    private int ID;

    @FXML
    private Button add_btn;

    @FXML
    private DatePicker ngayNhapVeDatePicker;

    @FXML
    private ComboBox<String> phongComboBox;

    @FXML
    private TextField tenThietBiTextField;

    @FXML
    private ChoiceBox<String> tinhTrangChoiceBox;

    @FXML
    private Text title;

    @FXML
    private Button update_btn;

    @FXML
    private TextField xuatXuTextField;

    public void setThietBi(ThietBi thietBi) {
        tenThietBiTextField.setText(thietBi.getTen());
        ngayNhapVeDatePicker.setValue(LOCAL_DATE(thietBi.getNgayNhapVe()));
        xuatXuTextField.setText(thietBi.getXuatXu());
        tinhTrangChoiceBox.setValue(thietBi.getTinhTrang());
        String tenPhongTap = thietBi.getTenPhongTap();
        int phongTapId = 0;
        try {
			phongTapId = ThietBiServices.getPhongTapId(tenPhongTap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        phongComboBox.setValue(phongTapId + " : " + thietBi.getTenPhongTap());
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
    void addThietBi(ActionEvent event) throws IOException {
        ViewUtils viewUtils = new ViewUtils();
        if (ngayNhapVeDatePicker.getValue() == null) createDialog(
                Alert.AlertType.WARNING,
                "Thông báo",
                "", "Vui lòng nhập đủ thông tin!");
        else {
            String tenThietBi = tenThietBiTextField.getText();
            String ngayNhapVe = ngayNhapVeDatePicker.getValue().toString();
            String xuatXu = xuatXuTextField.getText();
            String tinhTrang = tinhTrangChoiceBox.getValue().toString();
            int idPhongTap = Integer.parseInt(phongComboBox.getValue().toString().split(":")[0].strip());
            if (tenThietBi.trim().equals("") || ngayNhapVe.trim().equals("") || xuatXu.trim().equals("") ||
                    tinhTrang.trim().equals("")) {
                createDialog(
                        Alert.AlertType.WARNING,
                        "Thông báo",
                        "", "Vui lòng nhập đủ thông tin!");
            } else {
                try {
                    int result = ThietBiServices.addThietBi(idPhongTap,tenThietBi, ngayNhapVe, xuatXu, tinhTrang);
                    if (result == 1) {
                        createDialog(
                                Alert.AlertType.CONFIRMATION,
                                "Thành công",
                                "", "Thêm thiết bị thành công!"
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
                viewUtils.switchToThietBi(event);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tinhTrangChoiceBox.getItems().add("Hỏng");
        tinhTrangChoiceBox.getItems().add("Còn sử dùng được");
        tinhTrangChoiceBox.setValue("Còn sử dùng được");
        ObservableList<PhongTap> phongTapList;
        try {
            phongTapList = PhongTapServices.getAllPhongTap();
            for(PhongTap phongTap : phongTapList){
                phongComboBox.getItems().add(phongTap.getId() + " : " + phongTap.getTenPhong());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        ViewUtils viewUtils = new ViewUtils();
        viewUtils.switchToThietBi(event);
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        ViewUtils viewUtils = new ViewUtils();
        if (ngayNhapVeDatePicker.getValue() == null) createDialog(
                Alert.AlertType.WARNING,
                "Thông báo",
                "", "Vui lòng nhập đủ thông tin!");
        else {
            String tenThietBi = tenThietBiTextField.getText();
            String ngayNhapVe = ngayNhapVeDatePicker.getValue().toString();
            String xuatXu = xuatXuTextField.getText();
            String tinhTrang = tinhTrangChoiceBox.getValue().toString();
            int idPhongTap = Integer.parseInt(phongComboBox.getValue().toString().split(":")[0].strip());
            if (tenThietBi.trim().equals("") || ngayNhapVe.trim().equals("") || xuatXu.trim().equals("")) {
                createDialog(
                        Alert.AlertType.WARNING,
                        "Thông báo",
                        "", "Vui lòng nhập đủ thông tin!");
            } else {
                try {
                    int result = ThietBiServices.updateThietBi(ID, idPhongTap,tenThietBi, ngayNhapVe, xuatXu, tinhTrang);
                    if (result == 1) {
                        createDialog(
                                Alert.AlertType.CONFIRMATION,
                                "Thành công",
                                "", "Cập nhật thiết bị thành công!"
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
                viewUtils.switchToThietBi(event);
            }
        }
    }

}
