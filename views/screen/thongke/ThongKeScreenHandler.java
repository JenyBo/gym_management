package views.screen.thongke;

import static utils.Configs.ROWS_PER_PAGE;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import entity.model.ThongKeGoiTap;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.util.Callback;
import services.GoiTapDaDangKiServices;
import services.ThuPhiServices;

public class ThongKeScreenHandler implements Initializable{
	
	private String fromDate = null;
	private String toDate = null ;
	
	private ObservableList<ThongKeGoiTap> lichSuList = FXCollections.observableArrayList();
	
    @FXML
    private TextField doanhThuTextField;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private TableColumn indexColumn;

    @FXML
    private TableColumn<ThongKeGoiTap, Integer> luongDangKiColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TableView<ThongKeGoiTap> tableView;

    @FXML
    private TableColumn<ThongKeGoiTap, String> tenGoiTapColumn;

    @FXML
    private DatePicker toDatePicker;
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			doanhThuTextField.setEditable(false);
			doanhThuTextField.setText(Integer.toString(ThuPhiServices.getAllSumPhi()));
			lichSuList = GoiTapDaDangKiServices.getAllCountDangKi();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			try {
				doanhThuTextField.setText(Integer.toString(ThuPhiServices.getSumPhi(this.fromDate, this.toDate)));
				lichSuList = GoiTapDaDangKiServices.getAllDangKi(this.fromDate, this.toDate);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			try {
				doanhThuTextField.setText(Integer.toString(ThuPhiServices.getSumPhi(this.fromDate, this.toDate)));
				lichSuList = GoiTapDaDangKiServices.getAllDangKi(this.fromDate, this.toDate);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int soDu1 = lichSuList.size() % ROWS_PER_PAGE;
			if (soDu1 != 0)
				pagination.setPageCount(lichSuList.size() / ROWS_PER_PAGE + 1);
			else
				pagination.setPageCount(lichSuList.size() / ROWS_PER_PAGE);
			pagination.setMaxPageIndicatorCount(5);
			pagination.setPageFactory(this::createTableView);
    	});
		
	}
	
	public Node createTableView(int pageIndex) {

		indexColumn.setCellValueFactory(
				(Callback<TableColumn.CellDataFeatures<ThongKeGoiTap, ThongKeGoiTap>, ObservableValue<ThongKeGoiTap>>) p -> new ReadOnlyObjectWrapper(
						p.getValue()));

		indexColumn.setCellFactory(new Callback<TableColumn<ThongKeGoiTap, ThongKeGoiTap>, TableCell<ThongKeGoiTap, ThongKeGoiTap>>() {
			@Override
			public TableCell<ThongKeGoiTap, ThongKeGoiTap> call(TableColumn<ThongKeGoiTap, ThongKeGoiTap> param) {
				return new TableCell<ThongKeGoiTap, ThongKeGoiTap>() {
					@Override
					protected void updateItem(ThongKeGoiTap item, boolean empty) {
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
		tenGoiTapColumn.setCellValueFactory(new PropertyValueFactory<ThongKeGoiTap, String>("tenGoiTap"));
		luongDangKiColumn.setCellValueFactory(new PropertyValueFactory<ThongKeGoiTap, Integer>("luongDangKi"));

		
		
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
