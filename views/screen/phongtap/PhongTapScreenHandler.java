package views.screen.phongtap;
import static utils.deAccent.removeAccent;
import static utils.Configs.*;
import static utils.Utils.createDialog;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entity.model.HoiVien;
import entity.model.PhongTap;
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
import services.PhongTapServices;
import utils.ViewUtils;

public class PhongTapScreenHandler implements Initializable{

    @FXML
    private AnchorPane basePane;

    @FXML
    private TableColumn indexColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<PhongTap> tableView;

    @FXML
    private TableColumn<PhongTap, String> tenPhongColumn;
    
    private ObservableList<PhongTap> phongTapList = FXCollections.observableArrayList();
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	try {
    		phongTapList = PhongTapServices.getAllPhongTap();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int soDu = phongTapList.size() % ROWS_PER_PAGE;
		if (soDu != 0)
			pagination.setPageCount(phongTapList.size() / ROWS_PER_PAGE + 1);
		else
			pagination.setPageCount(phongTapList.size() / ROWS_PER_PAGE);
		pagination.setMaxPageIndicatorCount(5);
		pagination.setPageFactory(this::createTableView);

		tableView.setRowFactory(tv -> {
			TableRow<PhongTap> row = new TableRow<>();
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
		loader.setLocation(getClass().getResource(DETAIL_PHONG_TAP_VIEW_FXML));
		Parent studentViewParent = loader.load();
		Scene scene = new Scene(studentViewParent);
		PhongTapDetailScreenHandler controller = loader.getController();
		PhongTap selected = tableView.getSelectionModel().getSelectedItem();
		if (selected == null)
			createDialog(Alert.AlertType.WARNING, "Từ từ đã Bạn", "", "Vui lòng chọn một phòng tập");
		else {
			controller.setPhongTap(selected);
			controller.setID(selected.getId());
			controller.hide_add_btn();
			controller.setTitle("Cập nhật thông tin phòng tập");
			stage.setScene(scene);
		}
	}
    
    public Node createTableView(int pageIndex) {

		indexColumn.setCellValueFactory(
				(Callback<TableColumn.CellDataFeatures<PhongTap, PhongTap>, ObservableValue<PhongTap>>) p -> new ReadOnlyObjectWrapper(
						p.getValue()));

		indexColumn.setCellFactory(new Callback<TableColumn<PhongTap, PhongTap>, TableCell<PhongTap, PhongTap>>() {
			@Override
			public TableCell<PhongTap, PhongTap> call(TableColumn<PhongTap, PhongTap> param) {
				return new TableCell<PhongTap, PhongTap>() {
					@Override
					protected void updateItem(PhongTap item, boolean empty) {
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
		tenPhongColumn.setCellValueFactory(new PropertyValueFactory<PhongTap, String>("tenPhong"));

		
		
		int lastIndex = 0;
		int displace = phongTapList.size() % ROWS_PER_PAGE;
		if (displace > 0) {
			lastIndex = phongTapList.size() / ROWS_PER_PAGE;
		} else {
			lastIndex = phongTapList.size() / ROWS_PER_PAGE - 1;
		}

		if (phongTapList.isEmpty())
			tableView.setItems(FXCollections.observableArrayList(phongTapList));
		else {
			if (lastIndex == pageIndex && displace > 0) {
				tableView.setItems(FXCollections.observableArrayList(
						phongTapList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
			} else {
				tableView.setItems(FXCollections.observableArrayList(
						phongTapList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
			}
		}
		return tableView;

    }
    
    @FXML
    void addPhongTap(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(DETAIL_PHONG_TAP_VIEW_FXML));
		Parent studentViewParent = loader.load();
		Scene scene = new Scene(studentViewParent);
		PhongTapDetailScreenHandler controller = loader.getController();
		controller.hide_update_btn();
		stage.setScene(scene);
    }

    @FXML
    void deletePhongTap(ActionEvent event) {
    	PhongTap selected = tableView.getSelectionModel().getSelectedItem();
		if (selected == null)
			createDialog(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn phòng tập để tiếp tục", "");
		else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Xác nhận xóa phòng tập");
			alert.setContentText("Bạn muốn xóa phòng tập này?");
			ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
			ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
			alert.getButtonTypes().setAll(okButton, noButton);
			alert.showAndWait().ifPresent(type -> {
				if (type == okButton) {
					// Delete in Database
					try {
						int ID = selected.getId();
						int result = PhongTapServices.deletePhongTap(ID);
						if (result == 1)
							createDialog(Alert.AlertType.INFORMATION, "Thông báo", "Xóa thành công!", "");
						else
							createDialog(Alert.AlertType.WARNING, "Thông báo", "Có lỗi, thử lại sau!", "");
						ViewUtils viewUtils = new ViewUtils();
						viewUtils.changeAnchorPane(basePane, PHONG_TAP_SCREEN_PATH);

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
    	FilteredList<PhongTap> filteredData = new FilteredList<>(phongTapList, p -> true);
		searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = removeAccent(searchTextField.getText().toLowerCase());
				if (removeAccent(person.getTenPhong().toLowerCase()).contains(lowerCaseFilter)) {
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
						(Callback<TableColumn.CellDataFeatures<PhongTap, PhongTap>, ObservableValue<PhongTap>>) p -> new ReadOnlyObjectWrapper(
								p.getValue()));

				indexColumn
						.setCellFactory(new Callback<TableColumn<PhongTap, PhongTap>, TableCell<PhongTap, PhongTap>>() {
							@Override
							public TableCell<PhongTap, PhongTap> call(TableColumn<PhongTap, PhongTap> param) {
								return new TableCell<PhongTap, PhongTap>() {
									@Override
									protected void updateItem(PhongTap item, boolean empty) {
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
				tenPhongColumn.setCellValueFactory(new PropertyValueFactory<PhongTap, String>("tenPhong"));
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
