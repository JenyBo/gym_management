package views.screen.goitapdangki;

import static utils.Configs.ROWS_PER_PAGE;
import static utils.Utils.createDialog;
import static utils.deAccent.removeAccent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;

import entity.model.GoiTap;
import entity.model.GoiTapDaDangKi;
import entity.model.HoiVien;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import services.GoiTapDaDangKiServices;
import services.GoiTapServices;
import services.HoiVienServices;
import utils.ViewUtils;

@SuppressWarnings("unused")
public class GoiTapDaDangKiDetailScreenHandler implements Initializable{
	private int ID;

    @FXML
    private Button add_btn;

    @FXML
    private ComboBox<String> goiTapComboBox;

    @FXML
    private ComboBox<String> hoiVienComboBox;

    @FXML
    private DatePicker ngayDangKiDatePicker;

    @FXML
    private TextField searchTextField;

    @FXML
    private Text title;

    @FXML
    private Button update_btn;

    public void initialize(URL location, ResourceBundle resources) {
        // Add a listener to the text field
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
				search();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        hoiVienComboBox.setOnAction(event -> {
        	try {
				set_ComboBox();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
    }

    private void search() throws SQLException {
        String searchText = searchTextField.getText();
        System.out.println("Searching for: " + searchText);

        ObservableList<HoiVien> stringList = HoiVienServices.getAllHoiVien();
        ObservableList<String> nameList = getAllName(stringList);

        // Filter the nameList based on the searchText
        ObservableList<String> filteredNameList = FXCollections.observableArrayList();
        for (String name : nameList) {
        	String searchText_tmp = removeAccent(searchText.toLowerCase());
            if (removeAccent(name.toLowerCase()).contains(searchText)) {
                filteredNameList.add(name);
            }
        }

        hoiVienComboBox.setItems(filteredNameList);
        try {
        	hoiVienComboBox.setPromptText(filteredNameList.get(0));
        } catch (Exception e){
        	hoiVienComboBox.setPromptText("");
        }
        hoiVienComboBox.show();
    }
    
    @FXML
    void addDangKiGoiTap(ActionEvent event) throws IOException{
    	 ViewUtils viewUtils = new ViewUtils();
         

     	 int idGoiTap = Integer.parseInt(goiTapComboBox.getValue().toString().strip().split(" - ")[0]);
         String tenGoiTap = goiTapComboBox.getValue().toString().strip().split(" - ")[1];
     	 int idHoiVien = Integer.parseInt(hoiVienComboBox.getValue().toString().strip().split(" - ")[0]);
         String tenHoiVien = hoiVienComboBox.getValue().toString().strip().split(" - ")[1];
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         String date = ngayDangKiDatePicker.getValue().format(formatter);

         if (tenGoiTap.trim().equals("") || tenHoiVien.trim().equals("")) {

             createDialog(
                     Alert.AlertType.WARNING,
                     "Thông báo",
                     "", "Vui lòng nhập đủ thông tin!"
                     );
         }
         else {
         	try {
         	    // check exist
         		boolean exist = GoiTapDaDangKiServices.checkExist(idHoiVien, idGoiTap);
         		if (exist == true) {
         	        createDialog(
         	            Alert.AlertType.ERROR,
         	            "Thất bại",
         	            "", "Người dùng đã đăng kí gói tập này!"
         	        );
         	        return;
         		}
         	    int result = GoiTapDaDangKiServices.addDangKi(ID, idHoiVien, idGoiTap, date);
         	    
         	    if (result == 1) {
         	        createDialog(
         	            Alert.AlertType.CONFIRMATION,
         	            "Thành công",
         	            "", "Thêm đăng kí thành công!"
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

         	viewUtils.switchToGoiTapDaDangKi(event);
             
         }
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        ViewUtils viewUtils = new ViewUtils();
        viewUtils.switchToGoiTapDaDangKi(event);
    }

    @FXML
    void update(ActionEvent event) throws IOException {
    	ViewUtils viewUtils = new ViewUtils();
        
    	int id = getID();
    	int idGoiTap = Integer.parseInt(goiTapComboBox.getValue().toString().strip().split(" - ")[0]);
        String tenGoiTap = goiTapComboBox.getValue().toString().strip().split(" - ")[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = ngayDangKiDatePicker.getValue().format(formatter);
        if (tenGoiTap.trim().equals("") || date.trim().equals("")) {

            createDialog(
                    Alert.AlertType.WARNING,
                    "Thông báo",
                    "", "Vui lòng nhập đủ thông tin!");
        } else {
                try {
                    int result = GoiTapDaDangKiServices.updateGoiTap(ID, idGoiTap, date);
                    if (result == 1) {
                        createDialog(
                                Alert.AlertType.CONFIRMATION,
                                "Thành công",
                                "", "Cập nhật thông tin đăng kí thành công!"
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
                viewUtils.switchToGoiTapDaDangKi(event);
            
        }
        
    }


    public void setGoiTapDaDangKi(GoiTapDaDangKi goiTap) throws SQLException {
		String dateString = goiTap.getNgayDangKi();
        String pattern = "yyyy-MM-dd";
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate date = LocalDate.parse(dateString, formatter);
        
        ngayDangKiDatePicker.setValue(date);
        hoiVienComboBox.setVisible(false);
        searchTextField.setText(goiTap.getHoTen());
        searchTextField.setEditable(false);

	             
	    ObservableList<GoiTap> goiTapList = GoiTapServices.getAllGoiTap();
	    ObservableList<Integer> goiTapDaDangKiList = GoiTapDaDangKiServices.getGoiTapList(goiTap.getIdHoiVien());
	    // Add the strings to the ComboBox
	    
	    goiTapComboBox.setItems(getAllGoiTap(goiTapList, goiTapDaDangKiList));
	    GoiTap selectedGoiTap = GoiTapServices.findGoiTap(goiTap.getIdGoiTap()).get(0);
	    goiTapComboBox.setPromptText(selectedGoiTap.getId() + " - " + selectedGoiTap.getTenGoiTap() + " - " + selectedGoiTap.getLoaiGoiTap());
	    goiTapComboBox.setValue(selectedGoiTap.getId() + " - " + selectedGoiTap.getTenGoiTap() + " - " + selectedGoiTap.getLoaiGoiTap());
    }

    private static ObservableList<String> getAllName(ObservableList<HoiVien> stringList) {
    	ObservableList<String> nameList = FXCollections.observableArrayList();
        for (HoiVien hoiVien : stringList) {
            nameList.add(hoiVien.getId() + " - " + hoiVien.getHoTen());
        }
        return nameList;
    }
    private static ObservableList<String> getAllGoiTap(ObservableList<GoiTap> stringList, ObservableList<Integer> goiTapDaDangKiList) {
    	ObservableList<String> goiTapList = FXCollections.observableArrayList();
        for (GoiTap gt : stringList) {
        	if (goiTapDaDangKiList.contains(gt.getId())) {
            	goiTapList.add(gt.getId() + " - " + gt.getTenGoiTap() + " - " + gt.getLoaiGoiTap() + " - Đã đăng kí" );
        	}
        	else goiTapList.add(gt.getId() + " - " + gt.getTenGoiTap() + " - " + gt.getLoaiGoiTap());
        }
        return goiTapList;
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

    public void setID(int ID) {
        this.ID = ID;
    }
    
    public int getID() {
        return this.ID;
    }

	public void set_ComboBox() throws SQLException {
		// TODO Auto-generated method stub
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Convert date and time to string
        String dateString = now.format(pattern);
        LocalDate date = LocalDate.parse(dateString, pattern);

        ngayDangKiDatePicker.setValue(date);
        
		
		ObservableList<HoiVien> stringList = HoiVienServices.getAllHoiVien();

	    hoiVienComboBox.setItems(getAllName(stringList));
//        hoiVienComboBox.setPromptText(selectedOption.getHoTen());
        if (hoiVienComboBox.getValue() == null) hoiVienComboBox.setValue(getAllName(stringList).get(0));
        
        ObservableList<GoiTap> goiTapList = GoiTapServices.getAllGoiTap();
    	int idHoiVien = Integer.parseInt(hoiVienComboBox.getValue().toString().strip().split(" - ")[0]);

	    ObservableList<Integer> goiTapDaDangKiList = GoiTapDaDangKiServices.getGoiTapList(idHoiVien);

        goiTapComboBox.setItems(getAllGoiTap(goiTapList, goiTapDaDangKiList));
//        goiTapComboBox.setPromptText(selectedGoiTap.getTenGoiTap());
        goiTapComboBox.setValue(getAllGoiTap(goiTapList, goiTapDaDangKiList).get(0));
	}



    
	


}
