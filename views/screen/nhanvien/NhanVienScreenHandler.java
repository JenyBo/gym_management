package views.screen.nhanvien;

import static utils.Configs.*;
import static utils.Utils.createDialog;
import static utils.deAccent.removeAccent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entity.model.HoiVien;
import entity.model.NhanVien;

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
import services.NhanVienServices;
import utils.ViewUtils;
import views.screen.phongtap.PhongTapDetailScreenHandler;


public class NhanVienScreenHandler implements Initializable{

    @FXML
    private AnchorPane basePane;

    @FXML
    private TableColumn<NhanVien, String> hoVaTenColumn;

    @FXML
    private TableColumn indexColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TableColumn<NhanVien, String> phongTapColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<NhanVien> tableView;

    @FXML
    private TableColumn<NhanVien, String> vaiTroColumn;
    
    private ObservableList<NhanVien> nhanVienList = FXCollections.observableArrayList();
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	try {
    		nhanVienList = NhanVienServices.getAllNhanVien();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int soDu = nhanVienList.size() % ROWS_PER_PAGE;
		if (soDu != 0)
			pagination.setPageCount(nhanVienList.size() / ROWS_PER_PAGE + 1);
		else
			pagination.setPageCount(nhanVienList.size() / ROWS_PER_PAGE);
		pagination.setMaxPageIndicatorCount(5);
		pagination.setPageFactory(this::createTableView);

		tableView.setRowFactory(tv -> {
			TableRow<NhanVien> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					try {
						try {
							detail(event);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			});
			return row;
		});

	}
    
    public void detail(MouseEvent event) throws IOException, SQLException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(DETAIL_NHAN_VIEN_VIEW_FXML));
		Parent studentViewParent = loader.load();
		Scene scene = new Scene(studentViewParent);
		NhanVienDetailScreenHandler controller = loader.getController();
		NhanVien selected = tableView.getSelectionModel().getSelectedItem();
		if (selected == null)
			createDialog(Alert.AlertType.WARNING, "Từ từ đã Bạn", "", "Vui lòng chọn một nhân viên");
		else {
			controller.setNhanVien(selected);
			controller.setID(selected.getId());
			controller.hide_add_btn();
			controller.setTitle("Cập nhật thông tin nhân viên");
			stage.setScene(scene);
		}
	}
    
    public Node createTableView(int pageIndex) {

		indexColumn.setCellValueFactory(
				(Callback<TableColumn.CellDataFeatures<NhanVien, NhanVien>, ObservableValue<NhanVien>>) p -> new ReadOnlyObjectWrapper(
						p.getValue()));

		indexColumn.setCellFactory(new Callback<TableColumn<NhanVien, NhanVien>, TableCell<NhanVien, NhanVien>>() {
			@Override
			public TableCell<NhanVien, NhanVien> call(TableColumn<NhanVien, NhanVien> param) {
				return new TableCell<NhanVien, NhanVien>() {
					@Override
					protected void updateItem(NhanVien item, boolean empty) {
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
		hoVaTenColumn.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("hoVaTen"));
		phongTapColumn.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("tenPhongTap"));
		vaiTroColumn.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("tenRole"));

		
		
		int lastIndex = 0;
		int displace = nhanVienList.size() % ROWS_PER_PAGE;
		if (displace > 0) {
			lastIndex = nhanVienList.size() / ROWS_PER_PAGE;
		} else {
			lastIndex = nhanVienList.size() / ROWS_PER_PAGE - 1;
		}

		if (nhanVienList.isEmpty())
			tableView.setItems(FXCollections.observableArrayList(nhanVienList));
		else {
			if (lastIndex == pageIndex && displace > 0) {
				tableView.setItems(FXCollections.observableArrayList(
						nhanVienList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
			} else {
				tableView.setItems(FXCollections.observableArrayList(
						nhanVienList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
			}
		}
		return tableView;

    }

    @FXML
    void addNhanVien(ActionEvent event) throws IOException, SQLException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(DETAIL_NHAN_VIEN_VIEW_FXML));
		Parent studentViewParent = loader.load();
		Scene scene = new Scene(studentViewParent);
		NhanVienDetailScreenHandler controller = loader.getController();
		controller.hide_update_btn();
		controller.setComboBox();
		stage.setScene(scene);
    }

    @FXML
    void deleteNhanVien(ActionEvent event) {
    	NhanVien selected = tableView.getSelectionModel().getSelectedItem();
		if (selected == null)
			createDialog(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn nhân viên để tiếp tục", "");
		else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Xác nhận xóa nhân viên");
			alert.setContentText("Bạn muốn xóa nhân viên này?");
			ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
			ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
			alert.getButtonTypes().setAll(okButton, noButton);
			alert.showAndWait().ifPresent(type -> {
				if (type == okButton) {
					// Delete in Database
					try {
						int ID = selected.getId();
						int result = NhanVienServices.deleteNhanVien(ID);
						if (result == 1)
							createDialog(Alert.AlertType.INFORMATION, "Thông báo", "Xóa thành công!", "");
						else
							createDialog(Alert.AlertType.WARNING, "Thông báo", "Có lỗi, thử lại sau!", "");
						ViewUtils viewUtils = new ViewUtils();
						viewUtils.changeAnchorPane(basePane, NHAN_VIEN_SCREEN_PATH);

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
    public void search(MouseEvent event) {
		FilteredList<NhanVien> filteredData = new FilteredList<>(nhanVienList, p -> true);
		searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = removeAccent(searchTextField.getText().toLowerCase());
				if (removeAccent(person.getHoVaTen().toLowerCase()).contains(lowerCaseFilter)) {
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
						(Callback<TableColumn.CellDataFeatures<NhanVien, NhanVien>, ObservableValue<NhanVien>>) p -> new ReadOnlyObjectWrapper(
								p.getValue()));

				indexColumn
						.setCellFactory(new Callback<TableColumn<NhanVien, NhanVien>, TableCell<NhanVien, NhanVien>>() {
							@Override
							public TableCell<NhanVien, NhanVien> call(TableColumn<NhanVien, NhanVien> param) {
								return new TableCell<NhanVien, NhanVien>() {
									@Override
									protected void updateItem(NhanVien item, boolean empty) {
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
				hoVaTenColumn.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("hoVaTen"));
				phongTapColumn.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("tenPhongTap"));
				vaiTroColumn.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("tenRole"));
				
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
