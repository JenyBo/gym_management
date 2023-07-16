package views.screen.thuphi;

import static utils.Configs.DETAIL_THU_PHI_VIEW_FXML;
import static utils.Configs.ROWS_PER_PAGE;
import static utils.Configs.THIET_BI_SCREEN_PATH;
import static utils.Utils.createDialog;
import static utils.deAccent.removeAccent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entity.db.GymDB;
import entity.model.ThuPhi;
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
import services.TaiKhoanServices;
import services.ThuPhiServices;
import utils.ViewUtils;

public class ThuPhiScreenHandler implements Initializable {
	
	private String role = GymDB.getUserPreferences().get("role", "");
	private String username = GymDB.getUserPreferences().get("username", "");
	
    @FXML
    private Button addBtn;
    
    @FXML
    private Button deleteBtn;

    @FXML
    private AnchorPane basePane;

    @FXML
    private TableColumn indexColumn;

    @FXML
    private TableColumn<ThuPhi, String> loaiPhiColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableColumn<ThuPhi, Integer> soTienColumn;

    @FXML
    private TableView<ThuPhi> tableView;

    @FXML
    private TableColumn<ThuPhi, String> tenGoiTapColumn;

    @FXML
    private TableColumn<ThuPhi, String> tenHoiVienColumn;

    @FXML
    private TableColumn<ThuPhi, String> ngayThuPhiColumn;

    private ObservableList<ThuPhi> thuPhiList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
        	int id_role = Integer.parseInt(role);
			if (id_role != 5) {
				thuPhiList = ThuPhiServices.getAllThuPhi();				
			}else {
				searchTextField.setVisible(false);
				addBtn.setVisible(false);
				deleteBtn.setVisible(false);
				int id_nguoi_dung = TaiKhoanServices.getIDViaUsername(username);
				thuPhiList = ThuPhiServices.getAllThuPhiUser(id_nguoi_dung);	
			}
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        int soDu = thuPhiList.size() % ROWS_PER_PAGE;
        if (soDu != 0)
            pagination.setPageCount(thuPhiList.size() / ROWS_PER_PAGE + 1);
        else
            pagination.setPageCount(thuPhiList.size() / ROWS_PER_PAGE);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory(this::createTableView);

        tableView.setRowFactory(tv -> {
            TableRow<ThuPhi> row = new TableRow<>();
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
    void addThuPhi(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(DETAIL_THU_PHI_VIEW_FXML));
        Parent thuPhiViewParent = loader.load();
        Scene scene = new Scene(thuPhiViewParent);
        ThuPhiDetailScreenHandler controller = loader.getController();
        controller.hide_update_btn();
        stage.setScene(scene);
    }

    @FXML
    void deleteThuPhi(ActionEvent event) {
        ThuPhi selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null)
            createDialog(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn thu phí để tiếp tục", "");
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận xóa thu phí ?");
            alert.setContentText("Bạn muốn xóa thu phí này?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    // Delete in Database
                    try {
                        int ID = selected.getId();
                        int result = ThuPhiServices.deleteThuPhi(ID);
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
        FilteredList<ThuPhi> filteredData = new FilteredList<>(thuPhiList, p -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = removeAccent(searchTextField.getText().toLowerCase());
                if (removeAccent(person.getTenHoiVien().toLowerCase()).contains(lowerCaseFilter)) {
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
                        (Callback<TableColumn.CellDataFeatures<ThuPhi, ThuPhi>, ObservableValue<ThuPhi>>) p -> new ReadOnlyObjectWrapper(
                                p.getValue()));

                indexColumn
                        .setCellFactory(new Callback<TableColumn<ThuPhi, ThuPhi>, TableCell<ThuPhi, ThuPhi>>() {
                            @Override
                            public TableCell<ThuPhi, ThuPhi> call(TableColumn<ThuPhi, ThuPhi> param) {
                                return new TableCell<ThuPhi, ThuPhi>() {
                                    @Override
                                    protected void updateItem(ThuPhi item, boolean empty) {
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
                tenHoiVienColumn.setCellValueFactory(new PropertyValueFactory<ThuPhi, String>("tenHoiVien"));
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
                (Callback<TableColumn.CellDataFeatures<ThuPhi, ThuPhi>, ObservableValue<ThuPhi>>) p -> new ReadOnlyObjectWrapper(
                        p.getValue()));

        indexColumn.setCellFactory(new Callback<TableColumn<ThuPhi, ThuPhi>, TableCell<ThuPhi, ThuPhi>>() {
            @Override
            public TableCell<ThuPhi, ThuPhi> call(TableColumn<ThuPhi, ThuPhi> param) {
                return new TableCell<ThuPhi, ThuPhi>() {
                    @Override
                    protected void updateItem(ThuPhi item, boolean empty) {
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
        tenHoiVienColumn.setCellValueFactory(new PropertyValueFactory<ThuPhi, String>("tenHoiVien"));
        tenGoiTapColumn.setCellValueFactory(new PropertyValueFactory<ThuPhi, String>("tenGoiTap"));
        soTienColumn.setCellValueFactory(new PropertyValueFactory<ThuPhi, Integer>("soTien"));
        loaiPhiColumn.setCellValueFactory(new PropertyValueFactory<ThuPhi, String>("loaiGoiTap"));
        ngayThuPhiColumn.setCellValueFactory(new PropertyValueFactory<ThuPhi, String>("ngayThuPhi"));

        int lastIndex = 0;
        int displace = thuPhiList.size() % ROWS_PER_PAGE;
        if (displace > 0) {
            lastIndex = thuPhiList.size() / ROWS_PER_PAGE;
        } else {
            lastIndex = thuPhiList.size() / ROWS_PER_PAGE - 1;
        }
        // Add thiet bi to table
        if (thuPhiList.isEmpty())
            tableView.setItems(FXCollections.observableArrayList(thuPhiList));
        else {
            if (lastIndex == pageIndex && displace > 0) {
                tableView.setItems(FXCollections.observableArrayList(
                        thuPhiList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
            } else {
                tableView.setItems(FXCollections.observableArrayList(
                        thuPhiList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
            }
        }
        return tableView;

    }
    public void detail(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(DETAIL_THU_PHI_VIEW_FXML));
        Parent thuPhiViewParent = loader.load();
        Scene scene = new Scene(thuPhiViewParent);
        ThuPhiDetailScreenHandler controller = loader.getController();
        ThuPhi selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null)
            createDialog(Alert.AlertType.WARNING, "Từ từ đã Bạn", "", "Vui lòng chọn ");
        else {
            controller.setThuPhi(selected);
            controller.setID(selected.getId());
            controller.hide_add_btn();
            controller.setTitle("Cập nhật thông tin thu phí");
            stage.setScene(scene);
        }
    }


}