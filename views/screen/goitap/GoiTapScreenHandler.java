package views.screen.goitap;

import static utils.Configs.DETAIL_GOI_TAP_VIEW_FXML;
import static utils.Configs.GOI_TAP_SCREEN_PATH;
import static utils.Configs.ROWS_PER_PAGE;
import static utils.Utils.createDialog;
import static utils.deAccent.removeAccent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entity.model.GoiTap;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.GoiTapServices;
import utils.ViewUtils;

public class GoiTapScreenHandler implements Initializable{

    @FXML
    private AnchorPane basePane;

    @FXML
    private TableView<GoiTap> tableView;
    
    @SuppressWarnings("rawtypes")
	@FXML
    private TableColumn indexColumn;

    @FXML
    private TableColumn<GoiTap, String> loaiGoiTapColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableColumn<GoiTap, String> soTienColumn;


    @FXML
    private TableColumn<GoiTap, String> tenGoiTapColumn;

    
    private ObservableList<GoiTap> goiTapList = FXCollections.observableArrayList();
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	try {
    		goiTapList = GoiTapServices.getAllGoiTap();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int soDu = goiTapList.size() % ROWS_PER_PAGE;
		if (soDu != 0)
			pagination.setPageCount(goiTapList.size() / ROWS_PER_PAGE + 1);
		else
			pagination.setPageCount(goiTapList.size() / ROWS_PER_PAGE);
		pagination.setMaxPageIndicatorCount(4);
		pagination.setPageFactory(this::createTableView);

		tableView.setRowFactory(tv -> {
			TableRow<GoiTap> row = new TableRow<>();
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
    
    @SuppressWarnings("unused")
	public void detail(MouseEvent event) throws IOException, SQLException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(DETAIL_GOI_TAP_VIEW_FXML));
		Parent studentViewParent = loader.load();
		Scene scene = new Scene(studentViewParent);
		GoiTapDetailScreenHandler controller = loader.getController();
		GoiTap selected = tableView.getSelectionModel().getSelectedItem();
		if (selected == null)
			createDialog(Alert.AlertType.WARNING, "Từ từ đã Bạn", "", "Vui lòng chọn một gói tập");
		else {
			controller.setGoiTap(selected);
			controller.setID(selected.getId());
			controller.hide_add_btn();
			controller.setTitle("Cập nhật thông tin gói tập");
			stage.setScene(scene);
		}
	}
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Node createTableView(int pageIndex) {

        indexColumn.setCellValueFactory(
                (Callback<CellDataFeatures<GoiTap, String>, ObservableValue<String>>) p -> new ReadOnlyObjectWrapper(
                        p.getValue()));

        indexColumn.setCellFactory(new Callback<TableColumn<GoiTap, GoiTap>, TableCell<GoiTap, GoiTap>>() {
            public TableCell<GoiTap, GoiTap> call(TableColumn<GoiTap, GoiTap> param) {
                return new TableCell<GoiTap, GoiTap>() {
                    @Override
                    protected void updateItem(GoiTap item, boolean empty) {
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
        tenGoiTapColumn.setCellValueFactory(new PropertyValueFactory<GoiTap, String>("tenGoiTap"));
        loaiGoiTapColumn.setCellValueFactory(new PropertyValueFactory<GoiTap, String>("loaiGoiTap"));
        soTienColumn.setCellValueFactory(new PropertyValueFactory<GoiTap, String>("soTien"));

        int lastIndex = 0;
        int displace = goiTapList.size() % ROWS_PER_PAGE;
        if (displace > 0) {
            lastIndex = goiTapList.size() / ROWS_PER_PAGE;
        } else {
            lastIndex = goiTapList.size() / ROWS_PER_PAGE - 1;
        }

        if (goiTapList.isEmpty())
            tableView.setItems(FXCollections.observableArrayList(goiTapList));
        else {
            if (lastIndex == pageIndex && displace > 0) {
                tableView.setItems(FXCollections.observableArrayList(
                        goiTapList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
            } else {
                tableView.setItems(FXCollections.observableArrayList(
                        goiTapList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
            }
        }
        return tableView;
    }
    
    @FXML
    void addGoiTap(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(DETAIL_GOI_TAP_VIEW_FXML));
		Parent studentViewParent = loader.load();
		Scene scene = new Scene(studentViewParent);
		GoiTapDetailScreenHandler controller = loader.getController();
		controller.hide_update_btn();
		stage.setScene(scene);
    }

    @FXML
    void deleteGoiTap(ActionEvent event) {
    	GoiTap selected = tableView.getSelectionModel().getSelectedItem();
		if (selected == null)
			createDialog(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn gói tập để tiếp tục", "");
		else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Xác nhận xóa gói tập");
			alert.setContentText("Bạn muốn xóa gói tập này?");
			ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
			ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
			alert.getButtonTypes().setAll(okButton, noButton);
			alert.showAndWait().ifPresent(type -> {
				if (type == okButton) {
					// Delete in Database
					try {
						int ID = selected.getId();
						int result = GoiTapServices.deleteGoiTap(ID);
						if (result == 1)
							createDialog(Alert.AlertType.INFORMATION, "Thông báo", "Xóa thành công!", "");
						else
							createDialog(Alert.AlertType.WARNING, "Thông báo", "Có lỗi, thử lại sau!", "");
						ViewUtils viewUtils = new ViewUtils();
						viewUtils.changeAnchorPane(basePane, GOI_TAP_SCREEN_PATH);

					} catch (SQLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			});
		}
    }


    @SuppressWarnings("unchecked")
	@FXML
    public void search(MouseEvent event) {
		FilteredList<GoiTap> filteredData = new FilteredList<>(goiTapList, p -> true);
		searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = removeAccent(searchTextField.getText().toLowerCase());
				if (removeAccent(person.getTenGoiTap().toLowerCase()).contains(lowerCaseFilter)) {
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
						(Callback<TableColumn.CellDataFeatures<GoiTap, GoiTap>, ObservableValue<GoiTap>>) p -> new ReadOnlyObjectWrapper(
								p.getValue()));

				indexColumn
						.setCellFactory(new Callback<TableColumn<GoiTap, GoiTap>, TableCell<GoiTap, GoiTap>>() {
							@Override
							public TableCell<GoiTap, GoiTap> call(TableColumn<GoiTap, GoiTap> param) {
								return new TableCell<GoiTap, GoiTap>() {
									@Override
									protected void updateItem(GoiTap item, boolean empty) {
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
		        tenGoiTapColumn.setCellValueFactory(new PropertyValueFactory<GoiTap, String>("tenGoiTap"));
		        loaiGoiTapColumn.setCellValueFactory(new PropertyValueFactory<GoiTap, String>("loaiGoiTap"));
		        soTienColumn.setCellValueFactory(new PropertyValueFactory<GoiTap, String>("soTien"));
				int lastIndex = 0;
				int displace = filteredData.size() % ROWS_PER_PAGE;
				if (displace > 0) {
					lastIndex = filteredData.size() / ROWS_PER_PAGE;
				} else {
					lastIndex = filteredData.size() / ROWS_PER_PAGE - 1;
				}
				// Add goitap to table
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
