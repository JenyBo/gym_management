package views.screen.dangki;

import static utils.Configs.ADMIN_SCREEN_PATH;
import static utils.Utils.createDialog;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.TaiKhoanServices;
import utils.ViewUtils;

public class DangKiScreenHandler{
	
	int id_role;
	int id_nguoi_dung;
	boolean isNhanVien;
	
	public void setup(String name, String role_name, int id_nguoi_dung, int id_role, boolean isNhanVien) {
		this.id_nguoi_dung = id_nguoi_dung;
		this.id_role = id_role;
		this.isNhanVien = isNhanVien;
		
		
		roleDisplayTextField.setText(role_name);
		roleDisplayTextField.setEditable(false);
		
		nameDisplayTextField.setText(name);
		nameDisplayTextField.setEditable(false);
	}
	
    @FXML
    private TextField roleDisplayTextField;

    @FXML
    private Button signUpButton;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private TextField signUpUsername;

    @FXML
    private TextField nameDisplayTextField;

    @FXML
    void signUp(ActionEvent event) throws SQLException, IOException {
    	String tai_khoan = signUpUsername.getText();
    	String mat_khau = signUpPassword.getText();
    	if (tai_khoan.trim().equals("") || mat_khau.trim().equals("")) {

            createDialog(
                    Alert.AlertType.WARNING,
                    "Thông báo",
                    "", "Vui lòng nhập đủ thông tin!")
            ;
        }else {
        	if (isNhanVien) {
        		TaiKhoanServices.updateTaiKhoanNhanVien(tai_khoan, mat_khau, id_nguoi_dung);
        	}else {
        		TaiKhoanServices.updateTaiKhoanHoiVien(tai_khoan, mat_khau, id_nguoi_dung);
        	}
        	createDialog(
        			Alert.AlertType.INFORMATION,
        			"Thông báo",
        			"", "Thêm tài khoản thành công");
        	ViewUtils viewUtils = new ViewUtils();
        	viewUtils.changeScene(event, ADMIN_SCREEN_PATH);
        }
    	
    }

//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//		roleDisplayTextField.setEditable(false);
//		userComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//			  @Override 
//			  public void changed(ObservableValue<? extends String> selected, String oldSelect, String newSelect) {
//				  try {
//					id_role = GeneralServices.getRoleViaName(newSelect);
//					String role_name = RoleServices.getNameViaId(id_role);
//					roleDisplayTextField.setText(role_name);
//					if (id_role == 5) {
//						isNhanVien = false;
//						id_nguoi_dung = GeneralServices.getIdViaName("hoi_vien", "ho_ten", newSelect);
//					}else {
//						isNhanVien = true;
//						id_nguoi_dung = GeneralServices.getIdViaName("nhan_vien", "ho_va_ten", newSelect);
//					}
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} 
//				
//				  
//			  }
//		});
//		
//		ObservableList<String> chuaCoTaiKhoanList;
//		try {
//			chuaCoTaiKhoanList = GeneralServices.getDanhSachChuaCoTaiKhoan();
//			userComboBox.setItems(chuaCoTaiKhoanList);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}

}