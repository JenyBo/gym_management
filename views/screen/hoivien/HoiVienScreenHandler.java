package views.screen.hoivien;

import static utils.deAccent.removeAccent;
import static utils.Configs.DETAIL_HOI_VIEN_VIEW_FXML;
import static utils.Configs.HOI_VIEN_SCREEN_PATH;
import static utils.Configs.ROWS_PER_PAGE;
import static utils.Utils.createDialog;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entity.model.HoiVien;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import services.HoiVienServices;
import utils.ViewUtils;

public class HoiVienScreenHandler implements Initializable{

    @FXML
    private AnchorPane basePane;

    @FXML
    private TableColumn<HoiVien, String> hoVaTenColumn;

    @FXML
    private TableColumn indexColumn;

    @FXML
    private TableColumn<HoiVien, String> loaiThanhVienColumn;
    
    @FXML
    private TableColumn<HoiVien, String> gioiTinhColumn;
    
    @FXML
    private TableColumn<HoiVien, String> ngheNghiepColumn;

    @FXML
    private TableColumn<HoiVien, String> ngaySinhColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<HoiVien> tableView;
    
    private ObservableList<HoiVien> hoiVienList = FXCollections.observableArrayList();
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	try {
			hoiVienList = HoiVienServices.getAllHoiVien();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int soDu = hoiVienList.size() % ROWS_PER_PAGE;
		if (soDu != 0)
			pagination.setPageCount(hoiVienList.size() / ROWS_PER_PAGE + 1);
		else
			pagination.setPageCount(hoiVienList.size() / ROWS_PER_PAGE);
		pagination.setMaxPageIndicatorCount(5);
		pagination.setPageFactory(this::createTableView);

		tableView.setRowFactory(tv -> {
			TableRow<HoiVien> row = new TableRow<>();
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
    
	public void detail(MouseEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(DETAIL_HOI_VIEN_VIEW_FXML));
		Parent studentViewParent = loader.load();
		Scene scene = new Scene(studentViewParent);
		HoiVienDetailScreenHandler controller = loader.getController();
		HoiVien selected = tableView.getSelectionModel().getSelectedItem();
		if (selected == null)
			createDialog(Alert.AlertType.WARNING, "Từ từ đã Bạn", "", "Vui lòng chọn một hội viên");
		else {
			controller.setHoiVien(selected);
			controller.setID(selected.getId());
			controller.hide_add_btn();
			controller.setTitle("Cập nhật thông tin hội viên");
			stage.setScene(scene);
		}
	}

    @FXML
    void addHoiVien(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(DETAIL_HOI_VIEN_VIEW_FXML));
		Parent studentViewParent = loader.load();
		Scene scene = new Scene(studentViewParent);
		HoiVienDetailScreenHandler controller = loader.getController();
		controller.hide_update_btn();
		stage.setScene(scene);
    }

    @FXML
    void deleteHoiVien(ActionEvent event) {
    	HoiVien selected = tableView.getSelectionModel().getSelectedItem();
		if (selected == null)
			createDialog(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn hội viên để tiếp tục", "");
		else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Xác nhận xóa hội viên");
			alert.setContentText("Bạn muốn xóa hội viên này?");
			ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
			ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
			alert.getButtonTypes().setAll(okButton, noButton);
			alert.showAndWait().ifPresent(type -> {
				if (type == okButton) {
					// Delete in Database
					try {
						int ID = selected.getId();
						int result = HoiVienServices.deleteHoiVien(ID);
						if (result == 1)
							createDialog(Alert.AlertType.INFORMATION, "Thông báo", "Xóa thành công!", "");
						else
							createDialog(Alert.AlertType.WARNING, "Thông báo", "Có lỗi, thử lại sau!", "");
						ViewUtils viewUtils = new ViewUtils();
						viewUtils.changeAnchorPane(basePane, HOI_VIEN_SCREEN_PATH);

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
    void yeuCauUpdateMK(MouseEvent event) {

    }
    
    public Node createTableView(int pageIndex) {

		indexColumn.setCellValueFactory(
				(Callback<TableColumn.CellDataFeatures<HoiVien, HoiVien>, ObservableValue<HoiVien>>) p -> new ReadOnlyObjectWrapper(
						p.getValue()));

		indexColumn.setCellFactory(new Callback<TableColumn<HoiVien, HoiVien>, TableCell<HoiVien, HoiVien>>() {
			@Override
			public TableCell<HoiVien, HoiVien> call(TableColumn<HoiVien, HoiVien> param) {
				return new TableCell<HoiVien, HoiVien>() {
					@Override
					protected void updateItem(HoiVien item, boolean empty) {
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
		hoVaTenColumn.setCellValueFactory(new PropertyValueFactory<HoiVien, String>("hoTen"));
		ngaySinhColumn.setCellValueFactory(new PropertyValueFactory<HoiVien, String>("ngaySinh"));
		loaiThanhVienColumn.setCellValueFactory(new PropertyValueFactory<HoiVien, String>("loaiThanhVien"));
		gioiTinhColumn.setCellValueFactory(new PropertyValueFactory<HoiVien, String>("gioiTinh"));
		ngheNghiepColumn.setCellValueFactory(new PropertyValueFactory<HoiVien, String>("ngheNghiep"));
		
		
		int lastIndex = 0;
		int displace = hoiVienList.size() % ROWS_PER_PAGE;
		if (displace > 0) {
			lastIndex = hoiVienList.size() / ROWS_PER_PAGE;
		} else {
			lastIndex = hoiVienList.size() / ROWS_PER_PAGE - 1;
		}
		// Add nhankhau to table
		if (hoiVienList.isEmpty())
			tableView.setItems(FXCollections.observableArrayList(hoiVienList));
		else {
			if (lastIndex == pageIndex && displace > 0) {
				tableView.setItems(FXCollections.observableArrayList(
						hoiVienList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
			} else {
				tableView.setItems(FXCollections.observableArrayList(
						hoiVienList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
			}
		}
		return tableView;

    }
    
    @FXML
    public void search(MouseEvent event) {
		FilteredList<HoiVien> filteredData = new FilteredList<>(hoiVienList, p -> true);
		searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = removeAccent(searchTextField.getText().toLowerCase());
				if (removeAccent(person.getHoTen().toLowerCase()).contains(lowerCaseFilter)) {
					return true;
				} else {
					return false;
				}
			});
			int soDu = filteredData.size() % ROWS_PER_PAGE;
			if (soDu != 0)
				pagination.setPageCount(filteredData.size() / ROWS_PER_PAGE + 1);
			else
				pagination.setPageCount(filteredData.size() / ROWS_PER_PAGE);
			pagination.setMaxPageIndicatorCount(5);
			pagination.setPageFactory(pageIndex -> {
				indexColumn.setCellValueFactory(
						(Callback<TableColumn.CellDataFeatures<HoiVien, HoiVien>, ObservableValue<HoiVien>>) p -> new ReadOnlyObjectWrapper(
								p.getValue()));

				indexColumn
						.setCellFactory(new Callback<TableColumn<HoiVien, HoiVien>, TableCell<HoiVien, HoiVien>>() {
							@Override
							public TableCell<HoiVien, HoiVien> call(TableColumn<HoiVien, HoiVien> param) {
								return new TableCell<HoiVien, HoiVien>() {
									@Override
									protected void updateItem(HoiVien item, boolean empty) {
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
				hoVaTenColumn.setCellValueFactory(new PropertyValueFactory<HoiVien, String>("hoTen"));
				ngaySinhColumn.setCellValueFactory(new PropertyValueFactory<HoiVien, String>("ngaySinh"));
				gioiTinhColumn.setCellValueFactory(new PropertyValueFactory<HoiVien, String>("gioiTinh"));
				ngheNghiepColumn.setCellValueFactory(new PropertyValueFactory<HoiVien, String>("ngheNghiep"));
				loaiThanhVienColumn.setCellValueFactory(new PropertyValueFactory<HoiVien, String>("loaiThanhVien"));
				int lastIndex = 0;
				int displace = filteredData.size() % ROWS_PER_PAGE;
				if (displace > 0) {
					lastIndex = filteredData.size() / ROWS_PER_PAGE;
				} else {
					lastIndex = filteredData.size() / ROWS_PER_PAGE - 1;
				}
				// Add nhankhau to table
				if (filteredData.isEmpty())
					tableView.setItems(FXCollections.observableArrayList(filteredData));
				else {
					if (lastIndex == pageIndex && displace > 0) {
						tableView.setItems(FXCollections.observableArrayList(
								filteredData.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
					} else {
						tableView.setItems(FXCollections.observableArrayList(filteredData
								.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
					}
				}
				return tableView;
			});
		});

	}
}
