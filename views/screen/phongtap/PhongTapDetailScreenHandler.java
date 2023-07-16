package views.screen.phongtap;

import static utils.Utils.LOCAL_DATE;
import static utils.Utils.createDialog;

import java.io.IOException;
import java.sql.SQLException;

import entity.model.HoiVien;
import entity.model.PhongTap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.HoiVienServices;
import services.PhongTapServices;
import utils.ViewUtils;

public class PhongTapDetailScreenHandler {
	
	private int ID;

    @FXML
    private Button add_btn;

    @FXML
    private TextField tenPhongTapTextField;

    @FXML
    private Text title;

    @FXML
    private Button update_btn;
    
    public void setPhongTap(PhongTap phongTap) {
        tenPhongTapTextField.setText(phongTap.getTenPhong());
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
    void addPhongTap(ActionEvent event) throws IOException {
    	ViewUtils viewUtils = new ViewUtils();
    	String tenPhongTap = tenPhongTapTextField.getText();
        if (tenPhongTap.trim().equals("")) {
            createDialog(
                    Alert.AlertType.WARNING,
                    "Thông báo",
                    "", "Vui lòng nhập đủ thông tin!")
            ;
        } else {
                try {
                    int result = PhongTapServices.addPhongTap(tenPhongTap);
                    if (result == 1) {
                        createDialog(
                                Alert.AlertType.CONFIRMATION,
                                "Thành công",
                                "", "Thêm phòng tập thành công!"
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
                viewUtils.switchToPhongTap(event);
            
        }

    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        ViewUtils viewUtils = new ViewUtils();
        viewUtils.switchToPhongTap(event);
    }

    @FXML
    void update(ActionEvent event) throws IOException {
    	ViewUtils viewUtils = new ViewUtils();
    	String tenPhongTap = tenPhongTapTextField.getText();
        if (tenPhongTap.trim().equals("")) {
            createDialog(
                    Alert.AlertType.WARNING,
                    "Thông báo",
                    "", "Vui lòng nhập đủ thông tin!")
            ;
        } else {
                try {
                    int result = PhongTapServices.updatePhongTap(ID, tenPhongTap);
                    if (result == 1) {
                        createDialog(
                                Alert.AlertType.CONFIRMATION,
                                "Thành công",
                                "", "Cập nhật thông tin phòng tập thành công!"
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
                viewUtils.switchToPhongTap(event);
            
        }
    }

}
