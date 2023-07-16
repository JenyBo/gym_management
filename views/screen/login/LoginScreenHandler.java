package views.screen.login;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import entity.db.GymDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.Authentication;
import utils.ViewUtils;

import static utils.Utils.*;
import static utils.Configs.*;
import static entity.db.GymDB.*;

public class LoginScreenHandler {
	
//	public static Preferences userPreferences = Preferences.userRoot();
	
    @FXML
    private Button buttonLogin;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private TextField inputUsername;

    @FXML
    public void handleLogin(ActionEvent event) throws SQLException, IOException {
    	String username = inputUsername.getText();
        String password = inputPassword.getText();
        if (username.trim().equals("") || password.trim().equals("")) {
            createDialog(
                    Alert.AlertType.WARNING,
                    "Cảnh báo!",
                    "",
                    "Vui lòng nhập đầy đủ username và password!"
            );
        }   else {
        	ResultSet result = Authentication.authentication(username, password);
            if (result.next()) {
//                Preferences userPreferences = Preferences.userRoot();
                GymDB.getUserPreferences().put("role", result.getString(6));
                GymDB.getUserPreferences().put("username", result.getString(2));
                System.out.println(GymDB.getUserPreferences().get("role", ""));
                ViewUtils viewUtils = new ViewUtils();
                viewUtils.changeScene(event, ADMIN_SCREEN_PATH);
            } else {
                createDialog(
                        Alert.AlertType.ERROR,
                        "Cảnh báo!",
                        "",
                        "Sai username hoặc password!"
                );
            }

        }
    }

	public LoginScreenHandler() {
		// TODO Auto-generated constructor stub
	}

}
