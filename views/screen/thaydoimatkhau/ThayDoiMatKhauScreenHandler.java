package views.screen.thaydoimatkhau;

import static utils.Configs.LOGIN_PATH;
import static utils.Utils.createDialog;

import java.io.IOException;
import java.sql.SQLException;

import entity.db.GymDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import services.GeneralServices;
import services.TaiKhoanServices;
import utils.ViewUtils;

public class ThayDoiMatKhauScreenHandler {

    @FXML
    private Button changePasswordButton;

    @FXML
    private PasswordField confirmPasswordTextField;

    @FXML
    private PasswordField newPasswordTextField;

    @FXML
    void changePassword(ActionEvent event) throws SQLException, IOException {
    	ViewUtils viewUtils = new ViewUtils();
    	String username = GymDB.getUserPreferences().get("username", "");
    	int id_tai_khoan = GeneralServices.getIdViaName("tai_khoan", "tai_khoan", username);
    	String newPass = newPasswordTextField.getText();
    	String confirmNewPass = confirmPasswordTextField.getText();
    	if (!newPass.equals(confirmNewPass)) {
    		createDialog(
                    Alert.AlertType.ERROR,
                    "Thay đổi mật khẩu thất bại",
                    "", "Mật khẩu mới và xác nhận mật khẩu mới phải giống nhau!"
            );
    		newPasswordTextField.setText("");
    		confirmPasswordTextField.setText("");
    	}else {
    		int result = TaiKhoanServices.updateMatKhauVidID(id_tai_khoan, newPass);
    		if (result == 1) {
                createDialog(
                        Alert.AlertType.CONFIRMATION,
                        "Thay đổi mật khẩu thành công!",
                        "Xin mời bạn đăng nhập lại", "Chúc bạn 1 ngày tốt lành!!"
                );
            } else {
                createDialog(
                        Alert.AlertType.ERROR,
                        "Thất bại",
                        "", "Có lỗi xảy ra, vui lòng thử lại!"
                );
            }
    		viewUtils.changeScene(event, LOGIN_PATH);
    	}

    }

}
