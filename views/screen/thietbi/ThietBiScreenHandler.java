package views.screen.thietbi;

import entity.model.HoiVien;
import entity.model.PhongTap;
import entity.model.ThietBi;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.HoiVienServices;
import services.ThietBiServices;
import utils.ViewUtils;
import views.screen.hoivien.HoiVienDetailScreenHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static utils.deAccent.removeAccent;
import static utils.Configs.*;
import static utils.Utils.createDialog;

public class ThietBiScreenHandler implements Initializable {

    @FXML
    private AnchorPane basePane;

    @FXML
    private TableColumn indexColumn;

    @FXML
    private TableColumn<ThietBi, String> ngayNhapVeColumn;

    @FXML
    private TableColumn<ThietBi, String> oPhongColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<ThietBi> tableView;

    @FXML
    private TableColumn<ThietBi, String> tenThietBiColumn;

    @FXML
    private TableColumn<ThietBi, String> tinhTrangColumn;

    @FXML
    private TableColumn<ThietBi, String> xuatXuColumn;

    private ObservableList<ThietBi> thietBiList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            thietBiList = ThietBiServices.getAllThietBi();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        int soDu = thietBiList.size() % ROWS_PER_PAGE;
        if (soDu != 0)
            pagination.setPageCount(thietBiList.size() / ROWS_PER_PAGE + 1);
        else
            pagination.setPageCount(thietBiList.size() / ROWS_PER_PAGE);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory(this::createTableView);

        tableView.setRowFactory(tv -> {
            TableRow<ThietBi> row = new TableRow<>();
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

    @FXML
    void addThietBi(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(DETAIL_THIET_BI_VIEW_FXML));
        Parent thietBiViewParent = loader.load();
        Scene scene = new Scene(thietBiViewParent);
        ThietBiDetailScreenHandler controller = loader.getController();
        controller.hide_update_btn();
        stage.setScene(scene);
    }

    @FXML
    void deleteThietBi(ActionEvent event) {
        ThietBi selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null)
            createDialog(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn thiết bị để tiếp tục", "");
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận xóa thiết bị");
            alert.setContentText("Bạn muốn xóa thiết bị này?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    // Delete in Database
                    try {
                        int ID = selected.getId();
                        int result = ThietBiServices.deleteThietBi(ID);
                        if (result == 1)
                            createDialog(Alert.AlertType.INFORMATION, "Thông báo", "Xóa thành công!", "");
                        else
                            createDialog(Alert.AlertType.WARNING, "Thông báo", "Có lỗi, thử lại sau!", "");
                        ViewUtils viewUtils = new ViewUtils();
                        viewUtils.changeAnchorPane(basePane, THIET_BI_SCREEN_PATH);

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
        FilteredList<ThietBi> filteredData = new FilteredList<>(thietBiList, p -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = removeAccent(searchTextField.getText().toLowerCase());
				if (removeAccent(person.getTen().toLowerCase()).contains(lowerCaseFilter)) {
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
                        (Callback<TableColumn.CellDataFeatures<ThietBi, ThietBi>, ObservableValue<ThietBi>>) p -> new ReadOnlyObjectWrapper(
                                p.getValue()));

                indexColumn
                        .setCellFactory(new Callback<TableColumn<ThietBi, ThietBi>, TableCell<ThietBi, ThietBi>>() {
                            @Override
                            public TableCell<ThietBi, ThietBi> call(TableColumn<ThietBi, ThietBi> param) {
                                return new TableCell<ThietBi, ThietBi>() {
                                    @Override
                                    protected void updateItem(ThietBi item, boolean empty) {
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
                tenThietBiColumn.setCellValueFactory(new PropertyValueFactory<ThietBi, String>("ten"));
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

    public Node createTableView(int pageIndex) {

        indexColumn.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<ThietBi, ThietBi>, ObservableValue<ThietBi>>) p -> new ReadOnlyObjectWrapper(
                        p.getValue()));

        indexColumn.setCellFactory(new Callback<TableColumn<ThietBi, ThietBi>, TableCell<ThietBi, ThietBi>>() {
            @Override
            public TableCell<ThietBi, ThietBi> call(TableColumn<ThietBi, ThietBi> param) {
                return new TableCell<ThietBi, ThietBi>() {
                    @Override
                    protected void updateItem(ThietBi item, boolean empty) {
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
        tenThietBiColumn.setCellValueFactory(new PropertyValueFactory<ThietBi, String>("ten"));
        ngayNhapVeColumn.setCellValueFactory(new PropertyValueFactory<ThietBi, String>("ngayNhapVe"));
        xuatXuColumn.setCellValueFactory(new PropertyValueFactory<ThietBi, String>("xuatXu"));
        tinhTrangColumn.setCellValueFactory(new PropertyValueFactory<ThietBi, String>("tinhTrang"));
        oPhongColumn.setCellValueFactory(new PropertyValueFactory<ThietBi, String>("tenPhongTap"));


        int lastIndex = 0;
        int displace = thietBiList.size() % ROWS_PER_PAGE;
        if (displace > 0) {
            lastIndex = thietBiList.size() / ROWS_PER_PAGE;
        } else {
            lastIndex = thietBiList.size() / ROWS_PER_PAGE - 1;
        }
        // Add thiet bi to table
        if (thietBiList.isEmpty())
            tableView.setItems(FXCollections.observableArrayList(thietBiList));
        else {
            if (lastIndex == pageIndex && displace > 0) {
                tableView.setItems(FXCollections.observableArrayList(
                        thietBiList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
            } else {
                tableView.setItems(FXCollections.observableArrayList(
                        thietBiList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
            }
        }
        return tableView;

    }
    public void detail(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(DETAIL_THIET_BI_VIEW_FXML));
        Parent thietBiViewParent = loader.load();
        Scene scene = new Scene(thietBiViewParent);
        ThietBiDetailScreenHandler controller = loader.getController();
        ThietBi selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null)
            createDialog(Alert.AlertType.WARNING, "Từ từ đã Bạn", "", "Vui lòng chọn một thiết bị");
        else {
            controller.setThietBi(selected);
            controller.setID(selected.getId());
            controller.hide_add_btn();
            controller.setTitle("Cập nhật thông tin thiết bị");
            stage.setScene(scene);
        }
    }


}
