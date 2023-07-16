package views.screen.goitap;

import static utils.Utils.createDialog;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import entity.model.GoiTap;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.GoiTapServices;
import services.HoiVienServices;
import services.PhongTapServices;
import services.RoleServices;
import utils.ViewUtils;

public class GoiTapDetailScreenHandler implements Initializable{
	private int ID;

    @FXML
    private Button add_btn;

    @FXML
    private ChoiceBox<String> loaiGoiTapChoiceBox;

    @FXML
    private TextField soTienTextField;

    @FXML
    private TextField tenGoiTapTextField;

    @FXML
    private Text title;

    @FXML
    private Button update_btn;

    @SuppressWarnings("unused")
	public void setComboBox() throws SQLException {
    	ObservableList<String> goiTapList = GoiTapServices.getLoaiGoiTapAll();
    	
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    
    public void setGoiTap(GoiTap goiTap) throws SQLException {
	    String soTien_format = String.valueOf(goiTap.getSoTien_int());
    	tenGoiTapTextField.setText(goiTap.getTenGoiTap());
    	soTienTextField.setText(soTien_format);
    	loaiGoiTapChoiceBox.setValue(goiTap.getLoaiGoiTap());
    	setComboBox();
    }
        
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	loaiGoiTapChoiceBox.getItems().add("Theo ngày");
    	loaiGoiTapChoiceBox.getItems().add("Theo tháng");
    	loaiGoiTapChoiceBox.getItems().add("Theo năm");
    	loaiGoiTapChoiceBox.setValue("Theo ngày");
    }

    @FXML
    public void addGoiTap(ActionEvent event) throws IOException {
        ViewUtils viewUtils = new ViewUtils();
       

        String tenGoiTap = tenGoiTapTextField.getText();
        String soTien = soTienTextField.getText();
        String loaiGoiTap = loaiGoiTapChoiceBox.getValue();

        if (tenGoiTap.trim().equals("") || soTien.trim().equals("") || loaiGoiTap == null) {

            createDialog(
                    Alert.AlertType.WARNING,
                    "Thông báo",
                    "", "Vui lòng nhập đủ thông tin!"
                    );
        }
        else {
        	try {
        	    Integer.parseInt(soTien);
        	    
        	    int result = GoiTapServices.addGoiTap(ID, tenGoiTap, soTien, loaiGoiTap);
        	    
        	    if (result == 1) {
        	        createDialog(
        	            Alert.AlertType.CONFIRMATION,
        	            "Thành công",
        	            "", "Thêm gói tập thành công!"
        	        );
        	    } else {
        	        createDialog(
        	            Alert.AlertType.ERROR,
        	            "Thất bại",
        	            "", "Có lỗi xảy ra, vui lòng thử lại!"
        	        );
        	    }
        	} catch (NumberFormatException e) {
        	    createDialog(
        	        Alert.AlertType.WARNING,
        	        "Thông báo",
        	        "", "Vui lòng nhập đúng số tiền!"
        	    );
        	} catch (SQLException e) {
        	    e.printStackTrace();
        	}

        	viewUtils.switchToGoiTap(event);
            
        }
 
    }
    @FXML
    void goBack(ActionEvent event) throws IOException {
        ViewUtils viewUtils = new ViewUtils();
        viewUtils.switchToGoiTap(event);
    }

    @FXML
    void update(ActionEvent event) throws IOException {
    	ViewUtils viewUtils = new ViewUtils();

        String tenGoiTap = tenGoiTapTextField.getText();
        String soTien = soTienTextField.getText();
        if (tenGoiTap.trim().equals("") || soTien.trim().equals("")) {

            createDialog(
                    Alert.AlertType.WARNING,
                    "Thông báo",
                    "", "Vui lòng nhập đủ thông tin!");
        } else {
                try {
                    int result = GoiTapServices.updateGoiTap(ID, tenGoiTap, soTien);
                    if (result == 1) {
                        createDialog(
                                Alert.AlertType.CONFIRMATION,
                                "Thành công",
                                "", "Cập nhật thông tin gói tập thành công!"
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

}
