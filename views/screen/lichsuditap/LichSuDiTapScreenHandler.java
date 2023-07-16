package views.screen.lichsuditap;

import static utils.Configs.ROWS_PER_PAGE;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import entity.db.GymDB;
import entity.model.LichSuDiTap;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import services.LichSuDiTapServices;
import services.TaiKhoanServices;

public class LichSuDiTapScreenHandler implements Initializable{
	
	private String role = GymDB.getUserPreferences().get("role", "");
	private String username = GymDB.getUserPreferences().get("username", "");
	private String fromDate = null;
	private String toDate = null ;
	
    @FXML
    private DatePicker fromDatePicker;
    
    @FXML
    private DatePicker toDatePicker;

    @FXML
    private AnchorPane basePane;

    @FXML
    private TableColumn<LichSuDiTap, String> hoVaTenColumn;

    @FXML
    private TableColumn indexColumn;

    @FXML
    private TableColumn<LichSuDiTap, String> ngaySuDungColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<LichSuDiTap> tableView;

    @FXML
    private TableColumn<LichSuDiTap, String> tenGoiTapColumn;

    @FXML
    private TableColumn<LichSuDiTap, String>tenPhongTapColumn;
    
    private ObservableList<LichSuDiTap> lichSuList = FXCollections.observableArrayList();

    @FXML
    void addLichSuDiTap(ActionEvent event) {

    }

    @FXML
    void deleteLichSuDiTap(ActionEvent event) {

    }

    @FXML
    void filter(MouseEvent event) {
    	

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			int id_role = Integer.parseInt(role);
			if (id_role != 5) {
				lichSuList = LichSuDiTapServices.getAllLichSu(null, null);
			}else {
				int id_nguoi_dung = TaiKhoanServices.getIDViaUsername(username);
				lichSuList = LichSuDiTapServices.getAllLichSuUser(id_nguoi_dung, null, null);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int soDu = lichSuList.size() % ROWS_PER_PAGE;
		if (soDu != 0)
			pagination.setPageCount(lichSuList.size() / ROWS_PER_PAGE + 1);
		else
			pagination.setPageCount(lichSuList.size() / ROWS_PER_PAGE);
		pagination.setMaxPageIndicatorCount(5);
		pagination.setPageFactory(this::createTableView);
		
		
		toDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
    		this.toDate = newValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			if (Integer.parseInt(this.role) != 5) {
				try {
					lichSuList = LichSuDiTapServices.getAllLichSu(this.fromDate, this.toDate);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				try {
					lichSuList = LichSuDiTapServices.getAllLichSuUser(TaiKhoanServices.getIDViaUsername(this.username), this.fromDate, this.toDate);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			int soDu1 = lichSuList.size() % ROWS_PER_PAGE;
			if (soDu1 != 0)
				pagination.setPageCount(lichSuList.size() / ROWS_PER_PAGE + 1);
			else
				pagination.setPageCount(lichSuList.size() / ROWS_PER_PAGE);
			pagination.setMaxPageIndicatorCount(5);
			pagination.setPageFactory(this::createTableView);
    	});
		
		fromDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
    		this.fromDate = newValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			if (Integer.parseInt(this.role) != 5) {
				try {
					lichSuList = LichSuDiTapServices.getAllLichSu(this.fromDate, this.toDate);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				try {
					lichSuList = LichSuDiTapServices.getAllLichSuUser(TaiKhoanServices.getIDViaUsername(this.username), this.fromDate, this.toDate);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			int soDu2 = lichSuList.size() % ROWS_PER_PAGE;
			if (soDu2 != 0)
				pagination.setPageCount(lichSuList.size() / ROWS_PER_PAGE + 1);
			else
				pagination.setPageCount(lichSuList.size() / ROWS_PER_PAGE);
			pagination.setMaxPageIndicatorCount(5);
			pagination.setPageFactory(this::createTableView);
    	});
		
	}
	
	public Node createTableView(int pageIndex) {

		indexColumn.setCellValueFactory(
				(Callback<TableColumn.CellDataFeatures<LichSuDiTap, LichSuDiTap>, ObservableValue<LichSuDiTap>>) p -> new ReadOnlyObjectWrapper(
						p.getValue()));

		indexColumn.setCellFactory(new Callback<TableColumn<LichSuDiTap, LichSuDiTap>, TableCell<LichSuDiTap, LichSuDiTap>>() {
			@Override
			public TableCell<LichSuDiTap, LichSuDiTap> call(TableColumn<LichSuDiTap, LichSuDiTap> param) {
				return new TableCell<LichSuDiTap, LichSuDiTap>() {
					@Override
					protected void updateItem(LichSuDiTap item, boolean empty) {
						super.updateItem(item, empty);

						if (this.getTableRow() != null && item != null) {
							setText(this.getTableRow().getIndex() + 1 + pageIndex * ROWS_PER_PAGE + "");
						} else {
							setText("");
						}
					}
				};
			}
		});
		indexColumn.setSortable(false);
		hoVaTenColumn.setCellValueFactory(new PropertyValueFactory<LichSuDiTap, String>("tenHoiVien"));
		tenGoiTapColumn.setCellValueFactory(new PropertyValueFactory<LichSuDiTap, String>("tenGoiTap"));
		tenPhongTapColumn.setCellValueFactory(new PropertyValueFactory<LichSuDiTap, String>("tenPhongTap"));
		ngaySuDungColumn.setCellValueFactory(new PropertyValueFactory<LichSuDiTap, String>("ngaySuDung"));

		
		
		int lastIndex = 0;
		int displace = lichSuList.size() % ROWS_PER_PAGE;
		if (displace > 0) {
			lastIndex = lichSuList.size() / ROWS_PER_PAGE;
		} else {
			lastIndex = lichSuList.size() / ROWS_PER_PAGE - 1;
		}
		// Add nhankhau to table
		if (lichSuList.isEmpty())
			tableView.setItems(FXCollections.observableArrayList(lichSuList));
		else {
			if (lastIndex == pageIndex && displace > 0) {
				tableView.setItems(FXCollections.observableArrayList(
						lichSuList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
			} else {
				tableView.setItems(FXCollections.observableArrayList(
						lichSuList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
			}
		}
		return tableView;

    }
	

}
