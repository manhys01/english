package dev.manhnd.english.controllers;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import dev.manhnd.english.application.ApplicationDataModel;
import dev.manhnd.english.entities.Sentence;
import dev.manhnd.english.utils.FXUtils;
import dev.manhnd.english.utils.SimpleAudioPlayer;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SentenceController implements Initializable {

	@FXML
	private TableView<Sentence> table;
	@FXML
	private TableColumn<Sentence, Long> id_COL;
	@FXML
	private TableColumn<Sentence, String> sentence_COL;
	@FXML
	private TableColumn<Sentence, String> ipa_COL;
	@FXML
	private TableColumn<Sentence, String> audio_COL;
	@FXML
	private TableColumn<Sentence, String> def_COL;
	@FXML
	private MenuItem viewBtn;
	@FXML
	private MenuItem addBtn;
	@FXML
	private MenuItem editBtn;
	@FXML
	private TextField searchFld;

	private AutoCompletionBinding<Sentence> autoCompleteSentence;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		id_COL.setCellValueFactory(new PropertyValueFactory<>("id"));
		sentence_COL.setCellValueFactory(new PropertyValueFactory<>("sentence"));
		ipa_COL.setCellValueFactory(new PropertyValueFactory<>("ipa"));
		def_COL.setCellValueFactory(new PropertyValueFactory<>("definition"));
		audio_COL.setCellValueFactory(new PropertyValueFactory<>("audio"));

		table.setItems(ApplicationDataModel.getInstance().getSentences());

		ApplicationDataModel.getInstance().getSentences().addListener(new ListChangeListener<Sentence>() {
			@Override
			public void onChanged(Change<? extends Sentence> c) {
				while (c.next()) {
					if (c.wasPermutated()) {
						System.out.println("Sentence Permutated from " + c.getFrom() + " to " + c.getTo());
					} else if (c.wasUpdated()) {
						System.out.println("Sentence Updated from " + c.getFrom() + " to " + c.getTo());
					} else if (c.wasReplaced()) {
						System.out.println("Sentence Replaced from " + c.getFrom() + " to " + c.getTo());
					} else {
						if (c.wasRemoved()) {
							System.out.println("Sentence Removed from " + c.getFrom() + " to " + c.getTo());
						} else if (c.wasAdded()) {
							System.out.println("Sentence Added from " + c.getFrom() + " to " + c.getTo());
							Sentence s = c.getList().get(c.getFrom());
							table.scrollTo(s);
							table.getSelectionModel().select(s);
						}
					}
				}
			}
		});

		autoCompleteSentence = TextFields.bindAutoCompletion(searchFld,
				ApplicationDataModel.getInstance().getSentenceSuggestionProvider());
		autoCompleteSentence.setHideOnEscape(true);
		autoCompleteSentence.minWidthProperty().bind(searchFld.widthProperty());
		autoCompleteSentence.setOnAutoCompleted(s -> {
			Sentence sentence = s.getCompletion();
			table.scrollTo(sentence);
			table.getSelectionModel().select(sentence);
			searchFld.textProperty().set(sentence.getSentence());
		});
	}

	@FXML
	void handleAddBtn() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SentenceForm.fxml"));
			Parent root = fxmlLoader.load();
			SentenceFormController controller = fxmlLoader.getController();
			controller.setSentence(null);
			controller.getActionBtn().setText("Add");
			
			Stage popUpStage = new Stage();
			popUpStage.setTitle("Thêm câu");
			popUpStage.setScene(new Scene(root));
			
			Stage primaryStage = (Stage) table.getScene().getWindow();
			FXUtils.centerPopUpStage(primaryStage, popUpStage);
			
			popUpStage.showAndWait();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	

	@FXML
	void handleEditBtn() {
		try {
			int index = table.getSelectionModel().getSelectedIndex();
			Sentence s = table.getItems().get(index);
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SentenceForm.fxml"));
			Parent root = fxmlLoader.load();
			SentenceFormController controller = fxmlLoader.getController();
			controller.setSentence(s);
			controller.getActionBtn().setText("Update");
			
			Stage popUpStage = new Stage();
			popUpStage.setTitle("Sửa câu");
			popUpStage.setScene(new Scene(root));
			
			Stage primaryStage = (Stage) table.getScene().getWindow();
			FXUtils.centerPopUpStage(primaryStage, popUpStage);
			
			popUpStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handleViewBtn() {
		int index = table.getSelectionModel().getSelectedIndex();
		Sentence s = table.getItems().get(index);
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SentenceDetails.fxml"));
			Parent root = fxmlLoader.load();
			SentenceDetailsController controller = fxmlLoader.getController();
			
			Stage popUpStage = new Stage();
			popUpStage.setTitle("Xem chi tiết");
			popUpStage.setScene(new Scene(root));
			
			Stage primaryStage = (Stage) table.getScene().getWindow();
			FXUtils.centerPopUpStage(primaryStage, popUpStage);
			
			if (s.getAudio() != null) {
				if (!s.getAudio().isEmpty()) {
					File file = new File(s.getAudio());
					SimpleAudioPlayer audioPlayer = new SimpleAudioPlayer(file);
					controller.sendData(s, audioPlayer);
					popUpStage.showAndWait();
					audioPlayer.getPlayer().stop();
				} else {
					controller.sendData(s);
					popUpStage.showAndWait();
				}
			} else {
				controller.sendData(s);
				popUpStage.showAndWait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handleDeleteBtn() {
		System.out.println("Xóa");
		int index = table.getSelectionModel().getSelectedIndex();
		Sentence s = table.getItems().get(index);
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Xóa bản ghi");
			alert.setContentText("Bạn có chắc chắn muốn xóa bản ghi này không?");
			alert.initModality(Modality.APPLICATION_MODAL);
			Optional<ButtonType> optional = alert.showAndWait();
			if (ButtonType.OK == optional.get()) {
				System.out.println("Không thể xóa: "  + s);
				// DAOFactory.getSentenceDAO().deleteSentence(s);
				// table.getItems().remove(index);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
