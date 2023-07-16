package views.screen.phanhoi;

import static utils.Configs.DETAIL_PHAN_HOI_VIEW_FXML;
import static utils.Configs.PHAN_HOI_SCREEN_PATH;
import static utils.Configs.ROWS_PER_PAGE;
import static utils.Utils.createDialog;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import entity.db.GymDB;
import entity.model.PhanHoi;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.PhanHoiServices;
import services.TaiKhoanServices;
import utils.ViewUtils;

@SuppressWarnings("unused")
public class PhanHoiScreenHandler implements Initializable{
	
	private String role = GymDB.getUserPreferences().get("role", "");
	private String username = GymDB.getUserPreferences().get("username", "");

    @FXML
    private AnchorPane basePane;
    

    @FXML
    private Button deleteBtn;
    
    @FXML
    private Button addBtn;

    @FXML
    private TableColumn<PhanHoi, String> hoVaTenColumn;

    @FXML
    private TableColumn<PhanHoi, String> hoiDapColumn;

    @FXML
    private TableColumn indexColumn;

    @FXML
    private TableColumn<PhanHoi, String> noiDungColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<PhanHoi> tableView;
    
    private ObservableList<PhanHoi> phanHoiList = FXCollections.observableArrayList();

    @FXML
    void addPhanHoi(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(DETAIL_PHAN_HOI_VIEW_FXML));
		Parent studentViewParent = loader.load();
		Scene scene = new Scene(studentViewParent);
		PhanHoiDetailScreenHandler controller = loader.getController();
		controller.hide_update_btn();
		controller.setName();
		stage.setScene(scene);
    }

    @FXML
    void deletePhanHoi(ActionEvent event) {
    	PhanHoi selected = tableView.getSelectionModel().getSelectedItem();
		if (selected == null)
			createDialog(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn phản hồi để tiếp tục", "");
		else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Xác nhận xóa phản hổi");
			alert.setContentText("Bạn muốn xóa phản hồi này?");
			ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
			ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
			
			alert.getButtonTypes().setAll(okButton, noButton);
			alert.showAndWait().ifPresent(type -> {
				if (type == okButton) {
					// Delete in Database
					try {
						int ID = selected.getId();
						System.out.println(ID);
						int result = PhanHoiServices.deletePhanHoi(ID);
						if (result == 1)
							createDialog(Alert.AlertType.INFORMATION, "Thông báo", "Xóa thành công!", "");
						else
							createDialog(Alert.AlertType.WARNING, "Thông báo", "Có lỗi, thử lại sau!", "");
						ViewUtils viewUtils = new ViewUtils();
						viewUtils.changeAnchorPane(basePane, PHAN_HOI_SCREEN_PATH);

					} catch (SQLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			});
		}
    }

    @FXML
    void search(MouseEvent event) {

    }
    
    public void detail(MouseEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(DETAIL_PHAN_HOI_VIEW_FXML));
		Parent studentViewParent = loader.load();
		Scene scene = new Scene(studentViewParent);
		PhanHoiDetailScreenHandler controller = loader.getController();
		PhanHoi selected = tableView.getSelectionModel().getSelectedItem();
		if (selected == null)
			createDialog(Alert.AlertType.WARNING, "Từ từ đã Bạn", "", "Vui lòng chọn một phản hồi");
		else {
			controller.setPhanHoi(selected);
			controller.setID(selected.getId());
			controller.hide_add_btn();
			controller.setTitle("Cập nhật thông tin phản hồi");
			stage.setScene(scene);
		}
	}
    
    
    @SuppressWarnings("unchecked")
	public Node createTableView(int pageIndex) {

		indexColumn.setCellValueFactory(
				(Callback<TableColumn.CellDataFeatures<PhanHoi, PhanHoi>, ObservableValue<PhanHoi>>) p -> new ReadOnlyObjectWrapper(
						p.getValue()));

		indexColumn.setCellFactory(new Callback<TableColumn<PhanHoi, PhanHoi>, TableCell<PhanHoi, PhanHoi>>() {
			@Override
			public TableCell<PhanHoi, PhanHoi> call(TableColumn<PhanHoi, PhanHoi> param) {
				return new TableCell<PhanHoi, PhanHoi>() {
					@Override
					protected void updateItem(PhanHoi item, boolean empty) {
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
		hoVaTenColumn.setCellValueFactory(new PropertyValueFactory<PhanHoi, String>("tenHoiVien"));
		noiDungColumn.setCellValueFactory(new PropertyValueFactory<PhanHoi, String>("noiDung"));
		hoiDapColumn.setCellValueFactory(new PropertyValueFactory<PhanHoi, String>("hoiDap"));
		
		
		int lastIndex = 0;
		int displace = phanHoiList.size() % ROWS_PER_PAGE;
		if (displace > 0) {
			lastIndex = phanHoiList.size() / ROWS_PER_PAGE;
		} else {
			lastIndex = phanHoiList.size() / ROWS_PER_PAGE - 1;
		}
		// Add nhankhau to table
		if (phanHoiList.isEmpty())
			tableView.setItems(FXCollections.observableArrayList(phanHoiList));
		else {
			if (lastIndex == pageIndex && displace > 0) {
				tableView.setItems(FXCollections.observableArrayList(
						phanHoiList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
			} else {
				tableView.setItems(FXCollections.observableArrayList(
						phanHoiList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
			}
		}
		return tableView;

    }
    
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			int id_role = Integer.parseInt(role);
			if (id_role != 5) {
				addBtn.setVisible(false);
				deleteBtn.setVisible(false);
				phanHoiList = PhanHoiServices.getAllPhanHoi();
			}else {
				int id_nguoi_dung = TaiKhoanServices.getIDViaUsername(username);
				phanHoiList = PhanHoiServices.getAllPhanHoiUser(id_nguoi_dung);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int soDu = phanHoiList.size() % ROWS_PER_PAGE;
		if (soDu != 0)
			pagination.setPageCount(phanHoiList.size() / ROWS_PER_PAGE + 1);
		else
			pagination.setPageCount(phanHoiList.size() / ROWS_PER_PAGE);
		pagination.setMaxPageIndicatorCount(5);
		pagination.setPageFactory(this::createTableView);

		tableView.setRowFactory(tv -> {
			TableRow<PhanHoi> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					try {
						detail(event);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			});
			return row;
		});
		
	}

}
