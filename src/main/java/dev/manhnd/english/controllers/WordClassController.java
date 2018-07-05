package dev.manhnd.english.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import dev.manhnd.english.application.ApplicationDataModel;
import dev.manhnd.english.entities.WordClass;
import dev.manhnd.english.utils.FXUtils;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WordClassController implements Initializable {

	@FXML
	private TextField searchFld;

	@FXML
	private TableColumn<WordClass, Long> id_COL;

	@FXML
	private TableColumn<WordClass, String> name_COL;

	@FXML
	private TableColumn<WordClass, String> definition_COL;

	@FXML
	private TableColumn<WordClass, String> description_COL;

	@FXML
	private MenuItem addBtn;

	@FXML
	private MenuItem editBtn;

	@FXML
	private TableView<WordClass> table;

	private AutoCompletionBinding<WordClass> bindAutoCompletion;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		id_COL.setCellValueFactory(new PropertyValueFactory<>("id"));
		name_COL.setCellValueFactory(new PropertyValueFactory<>("name"));
		definition_COL.setCellValueFactory(new PropertyValueFactory<>("definition"));
		description_COL.setCellValueFactory(new PropertyValueFactory<>("description"));
		table.setItems(ApplicationDataModel.getInstance().getWordClasses());

		ApplicationDataModel.getInstance().getWordClasses().addListener(new ListChangeListener<WordClass>() {
			@Override
			public void onChanged(Change<? extends WordClass> c) {
				while (c.next()) {
					if (c.wasPermutated()) {
						System.out.println("WordClass Permutated from " + c.getFrom() + " to " + c.getTo());
					} else if (c.wasUpdated()) {
						System.out.println("WordClass Updated from " + c.getFrom() + " to " + c.getTo());
					} else if (c.wasReplaced()) {
						System.out.println("WordClass Replaced from " + c.getFrom() + " to " + c.getTo());
					} else {
						if (c.wasRemoved()) {
							System.out.println("WordClass Removed from " + c.getFrom() + " to " + c.getTo());
						} else if (c.wasAdded()) {
							System.out.println(">> WordClass Added from " + c.getFrom() + " to " + c.getTo());
							WordClass wc = c.getList().get(c.getFrom());
							table.scrollTo(wc);
							table.getSelectionModel().select(wc);
						}
					}
				}
			}
		});

		bindAutoCompletion = TextFields.bindAutoCompletion(searchFld,
				ApplicationDataModel.getInstance().getWordClassesSuggestionProvider());

		bindAutoCompletion.minWidthProperty().bind(searchFld.widthProperty());
		bindAutoCompletion.setOnAutoCompleted(e -> {
			WordClass wordClass = e.getCompletion();
			table.scrollTo(wordClass);
			table.getSelectionModel().select(wordClass);
			searchFld.textProperty().set(wordClass.getName());
		});
	}

	@FXML
	void handleAddBtn(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WordClassForm.fxml"));
			VBox root = loader.load();
			WordClassFormController controller = loader.getController();
			controller.setWordClass(null);

			Stage popUpStage = new Stage();
			popUpStage.setTitle("Thêm từ loại");
			popUpStage.setScene(new Scene(root));

			Stage primaryStage = (Stage) table.getScene().getWindow();
			FXUtils.centerPopUpStage(primaryStage, popUpStage);

			popUpStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handleEditBtn(ActionEvent event) {
		try {
			int index = table.getSelectionModel().getSelectedIndex();
			if (index < 0)
				return;
			WordClass w = table.getItems().get(index);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WordClassForm.fxml"));
			Parent layout = loader.load();
			WordClassFormController controller = loader.getController();
			controller.setWordClass(w);
			
			Stage popUpStage = new Stage();
			popUpStage.setTitle("Sửa từ loại");
			popUpStage.setScene(new Scene(layout));

			Stage primaryStage = (Stage) table.getScene().getWindow();
			FXUtils.centerPopUpStage(primaryStage, popUpStage);
			
			popUpStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
