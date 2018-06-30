package dev.manhnd.english.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import dev.manhnd.english.application.ApplicationDataModel;
import dev.manhnd.english.entities.Vocabulary;
import dev.manhnd.english.utils.ScreenUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VocabularyController implements Initializable {

	@FXML
	private TextField searchFld;

	@FXML
	private TableColumn<Vocabulary, Long> id_COL;

	@FXML
	private TableColumn<Vocabulary, String> word_COL;

	@FXML
	private TableColumn<Vocabulary, String> ipa_COL;

	@FXML
	private TableColumn<Vocabulary, String> word_class_COL;

	@FXML
	private TableColumn<Vocabulary, String> definition_COL;

	@FXML
	private TableColumn<Vocabulary, String> meaning_COL;

	@FXML
	private TableColumn<Vocabulary, ImageView> image_COL;

	@FXML
	private TableView<Vocabulary> table;

	@FXML
	private MenuItem addBtn;

	@FXML
	private MenuItem editBtn;

	private AutoCompletionBinding<Vocabulary> bindAutoCompletion;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		id_COL.setCellValueFactory(new PropertyValueFactory<>("id"));
		word_COL.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getWord().getWord()));
		ipa_COL.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getWord().getIpa()));
		word_class_COL.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getWordClass().getName()));
		definition_COL.setCellValueFactory(new PropertyValueFactory<>("definition"));
		meaning_COL.setCellValueFactory(new PropertyValueFactory<>("meaning"));
		image_COL.setCellValueFactory(new PropertyValueFactory<>("imageView"));
		image_COL.styleProperty().set("-fx-alignment: CENTER");

		table.setItems(ApplicationDataModel.getInstance().getVocabularies());

		ApplicationDataModel.getInstance().getVocabularies().addListener(new ListChangeListener<Vocabulary>() {
			@Override
			public void onChanged(Change<? extends Vocabulary> c) {
				while (c.next()) {
					if (c.wasPermutated()) {
						System.out.println("Vocabulary Permutated from " + c.getFrom() + " to " + c.getTo());
					} else if (c.wasUpdated()) {
						System.out.println("Vocabulary Updated from " + c.getFrom() + " to " + c.getTo());
					} else if (c.wasReplaced()) {
						System.out.println("Vocabulary Replaced from " + c.getFrom() + " to " + c.getTo());
					} else {
						if (c.wasRemoved()) {
							System.out.println("Vocabulary Removed from " + c.getFrom() + " to " + c.getTo());
						} else if (c.wasAdded()) {
							System.out.println("Vocabulary Added from " + c.getFrom() + " to " + c.getTo());
							Vocabulary v = c.getList().get(c.getFrom());
							table.scrollTo(v);
							table.getSelectionModel().select(v);
						}
					}
				}
			}
		});

		bindAutoCompletion = TextFields.bindAutoCompletion(searchFld,
				ApplicationDataModel.getInstance().getVocabulariesSuggestionProvider());
		bindAutoCompletion.minWidthProperty().bind(searchFld.widthProperty());
		bindAutoCompletion.setOnAutoCompleted(e -> {
			Vocabulary vocabulary = e.getCompletion();
			searchFld.textProperty().set(vocabulary.getWord().getWord());
			table.scrollTo(vocabulary);
			table.getSelectionModel().select(vocabulary);
		});
	}

	@FXML
	void handleAddBtn(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VocabularyForm.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Thêm từ vựng");
			VBox root = loader.load();
			VocabularyFormController controller = loader.getController();
			controller.getActionBtn().setText("Add");
			Scene scene = new Scene(root);
			ScreenUtils.setStageToScreen(searchFld, stage);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.getIcons().add(new Image(ApplicationDataModel.APPLICATION_ICON));
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handleEditBtn(ActionEvent event) {
		int index = table.getSelectionModel().getSelectedIndex();
		Vocabulary v = table.getItems().get(index);
		try {
			if (index < 0) {
				return;
			}
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VocabularyForm.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Thêm từ vựng");
			VBox root = loader.load();
			VocabularyFormController controller = loader.getController();
			controller.setVocabulary(v, index);
			controller.getActionBtn().setText("Update");
			ScreenUtils.setStageToScreen(searchFld, stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.getIcons().add(new Image(ApplicationDataModel.APPLICATION_ICON));
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
