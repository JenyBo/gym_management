package views.screen.nhanvien;

import static utils.Utils.createDialog;
import static utils.Configs.*;

import java.io.IOException;
import java.sql.SQLException;

import entity.model.NhanVien;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import services.GeneralServices;
import services.HoiVienServices;
import services.NhanVienServices;
import services.PhongTapServices;
import services.RoleServices;
import services.TaiKhoanServices;
import utils.ViewUtils;

public class NhanVienDetailScreenHandler {
	
	private int ID;
	
    public void setID(int ID) {
        this.ID = ID;
    }
    
    public void setTitle(String title) {
        this.title.setText(title);
    }
    
    public void hide_add_btn() {
        add_btn.setVisible(false);
    }
    
    public void hide_update_btn() {
        update_btn.setVisible(false);
        add_btn.setTranslateX(100);
    }

    @FXML
    private Button add_btn;

    @FXML
    private AnchorPane ho;

    @FXML
    private TextField hoVaTenTextField;

    @FXML
    private ComboBox<String> phongTapComboBox;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Text title;

    @FXML
    private Button update_btn;
    
    public void setComboBox() throws SQLException {
    	ObservableList<String> phongTapList = PhongTapServices.getTenAll();
    	ObservableList<String> roleList = RoleServices.getTenAll();
    	roleList.remove("Hội viên");
    	
    	
    	phongTapComboBox.setItems(phongTapList);
    	roleComboBox.setItems(roleList);
    }
    
    public void setNhanVien(NhanVien nhanVien) throws SQLException {
    	hoVaTenTextField.setText(nhanVien.getHoVaTen());
    	phongTapComboBox.setValue(nhanVien.getTenPhongTap());
    	roleComboBox.setValue(nhanVien.getTenRole());
    	
    	setComboBox();
    }

    @FXML
    void addNhanVien(ActionEvent event) throws IOException {
    	ViewUtils viewUtils = new ViewUtils();
    	String hoVaTen = hoVaTenTextField.getText();
    	String tenPhongTap = phongTapComboBox.getValue();
    	String tenRole = roleComboBox.getValue();
        if (hoVaTen.trim().equals("")) {
            createDialog(
                    Alert.AlertType.WARNING,
                    "Thông báo",
                    "", "Vui lòng nhập đủ thông tin!")
            ;
        } else {
                try {
                	int id_role = GeneralServices.getIdViaName("role", "ten_role", tenRole);
                	int id_phong_tap = GeneralServices.getIdViaName("phong_tap", "ten_phong", tenPhongTap);
                	int result1 = NhanVienServices.addNhanVien(hoVaTen, id_phong_tap);
                	
                	int id_nhan_vien = GeneralServices.getIdViaName("nhan_vien", "ho_va_ten", hoVaTen);
                	
                	int result2 = TaiKhoanServices.addTaiKhoanNhanVien(id_role, id_nhan_vien);
                    
                    if (result1 == 1 && result2 == 1) {
                        createDialog(
                                Alert.AlertType.INFORMATION,
                                "Thành công",
                                "Thêm nhân viên thành công!", "Hãy tạo tài khoản cho nhân viên này"
                        );
                        viewUtils.switchToDangKi(event, hoVaTen, tenRole, id_nhan_vien, id_role, true);;
                        
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
//                viewUtils.switchToNhanVien(event);
            
        }
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        ViewUtils viewUtils = new ViewUtils();
        viewUtils.switchToNhanVien(event);
    }

    @FXML
    void update(ActionEvent event) throws IOException, SQLException {
    	ViewUtils viewUtils = new ViewUtils();
        if (hoVaTenTextField.getText().trim().equals("")) createDialog(
                Alert.AlertType.WARNING,
                "Thông báo",
                "", "Vui lòng nhập đủ thông tin!");
        else {
            String hoVaTen = hoVaTenTextField.getText();
            String phongTap = phongTapComboBox.getValue();
            String vaiTro = roleComboBox.getValue();
            int id_phong_tap = GeneralServices.getIdViaName("phong_tap", "ten_phong", phongTap);
            int id_role = GeneralServices.getIdViaName("role", "ten_role", vaiTro);
            try {
                int result1 = NhanVienServices.updateNhanVien(ID, id_phong_tap, hoVaTen);
                int result2 = TaiKhoanServices.updateRole(ID, id_role);
                if (result1 == 1 && result2 == 1) {
                    createDialog(
                            Alert.AlertType.CONFIRMATION,
                            "Thành công",
                            "", "Cập nhật thông tin nhân viên thành công!"
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
            viewUtils.switchToNhanVien(event);
                
            
        }
    }


}
