package dev.manhnd.english.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import dev.manhnd.english.application.ApplicationDataModel;
import dev.manhnd.english.entities.Word;
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

public class WordController implements Initializable {

	@FXML
	private TableColumn<Word, Long> id_COL;

	@FXML
	private TableColumn<Word, String> word_COL;

	@FXML
	private TableColumn<Word, String> normal_audio_COL;

	@FXML
	private TableColumn<Word, String> special_audio_COL;

	@FXML
	private TableColumn<Word, String> ipa_COL;

	@FXML
	private TableView<Word> table;

	@FXML
	private TextField searchFld;

	@FXML
	private MenuItem addBtn;

	@FXML
	private MenuItem editBtn;

	private AutoCompletionBinding<Word> bindAutoCompletion;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		id_COL.setCellValueFactory(new PropertyValueFactory<>("id"));
		word_COL.setCellValueFactory(new PropertyValueFactory<>("word"));
		ipa_COL.setCellValueFactory(new PropertyValueFactory<>("ipa"));
		normal_audio_COL.setCellValueFactory(new PropertyValueFactory<>("normalAudio"));
		special_audio_COL.setCellValueFactory(new PropertyValueFactory<>("specialAudio"));

		ApplicationDataModel.getInstance().getWords().addListener(new ListChangeListener<Word>() {
			@Override
			public void onChanged(Change<? extends Word> c) {
				while (c.next()) {
					if (c.wasPermutated()) {
						System.out.println("Word Permutated from " + c.getFrom() + " to " + c.getTo());
					} else if (c.wasUpdated()) {
						System.out.println("Word Updated from " + c.getFrom() + " to " + c.getTo());
					} else if (c.wasReplaced()) {
						System.out.println("Word Replaced from " + c.getFrom() + " to " + c.getTo());
						Word word = c.getList().get(c.getFrom());
						//setAudioControl(word);
						table.scrollTo(word);
						table.getSelectionModel().select(word);
					} else {
						if (c.wasRemoved()) {
							System.out.println("Word Removed from " + c.getFrom() + " to " + c.getTo());
						} else if (c.wasAdded()) {
							System.out.println("Word Added from " + c.getFrom() + " to " + c.getTo());
							Word word = c.getList().get(c.getFrom());
							//setAudioControl(word);
							table.scrollTo(word);
							table.getSelectionModel().select(word);
						}
					}
				}
			}
		});

		table.setItems(ApplicationDataModel.getInstance().getWords());

		bindAutoCompletion = TextFields.bindAutoCompletion(searchFld,
				ApplicationDataModel.getInstance().getWordsSuggestionProvider());
		bindAutoCompletion.minWidthProperty().bind(searchFld.widthProperty());
		bindAutoCompletion.setOnAutoCompleted(e -> {
			Word word = e.getCompletion();
			table.scrollTo(word);
			table.getSelectionModel().select(word);
			searchFld.textProperty().set(word.getWord());
		});
	}

	@FXML
	void handleAddBtn(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WordForm.fxml"));
			Parent root = loader.load();
			
			Stage popUpStage = new Stage();
			popUpStage.setTitle("Thêm từ");
			popUpStage.setScene(new Scene(root));
			
			Stage primaryStage = (Stage) table.getScene().getWindow();
			FXUtils.centerPopUpStage(primaryStage, popUpStage);
			
			popUpStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handlEditBtn(ActionEvent event) {
		System.out.println("edit");
		try {
			int index = table.getSelectionModel().getSelectedIndex();
			if (index < 0)
				return;
			Word w = table.getItems().get(index);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WordForm.fxml"));
			VBox root = loader.load();
			WordFormController controller = loader.getController();
			controller.setWord(w);
			
			Stage popUpStage = new Stage();
			popUpStage.setTitle("Sửa từ");
			popUpStage.setScene(new Scene(root));
			
			Stage primaryStage = (Stage) table.getScene().getWindow();
			FXUtils.centerPopUpStage(primaryStage, popUpStage);
			
			popUpStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// private void setAudioControl(Word word) {
	// String normalAudio = word.getNormalAudio();
	// if (normalAudio != null) {
	// if (!normalAudio.trim().isEmpty()) {
	// try {
	// word.setPlayAudio(new SimpleAudioPlayer(new File(normalAudio)));
	// } catch (Exception e) {
	// word.setPlayAudio(null);
	// }
	// } else {
	// word.setPlayAudio(null);
	// }
	// } else {
	// word.setPlayAudio(null);
	// }
	// String specialAudio = word.getSpecialAudio();
	// if (specialAudio != null) {
	// if (!specialAudio.trim().isEmpty()) {
	// try {
	// word.setPlaySpecialAudio(new SimpleAudioPlayer(new File(specialAudio)));
	// } catch (Exception e) {
	// word.setPlaySpecialAudio(null);
	// }
	// } else {
	// word.setPlaySpecialAudio(null);
	// }
	// } else {
	// word.setPlaySpecialAudio(null);
	// }
	// }

}
