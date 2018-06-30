package dev.manhnd.english.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import dev.manhnd.english.application.ApplicationDataModel;
import dev.manhnd.english.entities.Phrase;
import dev.manhnd.english.entities.PhraseDetail;
import dev.manhnd.english.utils.ScreenUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PhraseDetailController implements Initializable {

	@FXML
	private TableView<PhraseDetail> table;

	@FXML
	private TableColumn<PhraseDetail, Long> id_COL;

	@FXML
	private TableColumn<PhraseDetail, String> phrase_COL;

	@FXML
	private TableColumn<PhraseDetail, String> ipa_COL;

	@FXML
	private TableColumn<PhraseDetail, String> definition_COL;

	@FXML
	private TableColumn<PhraseDetail, String> meaning_COL;

	@FXML
	private TableColumn<PhraseDetail, String> description_COL;

	@FXML
	private TextField searchFld;

	@FXML
	private MenuItem addBtn;

	@FXML
	private MenuItem editBtn;

	@FXML
	private MenuItem updatePhraseBtn;

	@FXML
	private MenuItem deleteBtn;

	private AutoCompletionBinding<PhraseDetail> autoCompleteSearchFld;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		id_COL.setCellValueFactory(new PropertyValueFactory<>("id"));
		phrase_COL.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getPhrase().getPhrase()));
		ipa_COL.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getPhrase().getIpa()));
		definition_COL.setCellValueFactory(new PropertyValueFactory<>("definition"));
		meaning_COL.setCellValueFactory(new PropertyValueFactory<>("meaning"));
		description_COL.setCellValueFactory(new PropertyValueFactory<>("description"));

		table.setItems(ApplicationDataModel.getInstance().getPhraseDetails());

		ApplicationDataModel.getInstance().getPhraseDetails().addListener(new ListChangeListener<PhraseDetail>() {
			@Override
			public void onChanged(Change<? extends PhraseDetail> c) {
				while (c.next()) {
					if (c.wasPermutated()) {
						System.out.println("PhraseDetail Permutated from " + c.getFrom() + " to " + c.getTo());
					} else if (c.wasUpdated()) {
						System.out.println("PhraseDetail Updated from " + c.getFrom() + " to " + c.getTo());
					} else if (c.wasReplaced()) {
						System.out.println("PhraseDetail Replaced from " + c.getFrom() + " to " + c.getTo());
					} else {
						if (c.wasRemoved()) {
							System.out.println("PhraseDetail Removed from " + c.getFrom() + " to " + c.getTo());
						} else if (c.wasAdded()) {
							System.out.println("PhraseDetail Added from " + c.getFrom() + " to " + c.getTo());
							PhraseDetail p = c.getList().get(c.getFrom());
							table.scrollTo(p);
							table.getSelectionModel().select(p);
						}
					}
				}
			}
		});

		autoCompleteSearchFld = TextFields.bindAutoCompletion(searchFld,
				ApplicationDataModel.getInstance().getPhraseDetailSuggestionProvider());
		autoCompleteSearchFld.setHideOnEscape(true);
		autoCompleteSearchFld.minWidthProperty().bind(searchFld.widthProperty());
		autoCompleteSearchFld.setOnAutoCompleted(e -> {
			PhraseDetail p = e.getCompletion();
			table.scrollTo(p);
			table.getSelectionModel().select(p);
			searchFld.textProperty().set(p.getPhrase().getPhrase());
		});

	}

	@FXML
	void handelAddBtn() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/fxml/PhraseDetailForm.fxml"));
			Parent root = fxmlLoader.load();
			PhraseDetailFormController controller = fxmlLoader.getController();
			controller.setPhraseDetail(null);
			controller.getActionBtn().setText("Add");
			Stage stage = new Stage();
			stage.setTitle("Thêm cụm từ");
			ScreenUtils.setStageToScreen(searchFld, stage);
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.getIcons().add(new Image(ApplicationDataModel.APPLICATION_ICON));
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handelEditBtn() {
		try {
			int index = table.getSelectionModel().getSelectedIndex();
			PhraseDetail p = table.getItems().get(index);
			if (index < 0)
				return;
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/fxml/PhraseDetailForm.fxml"));
			Parent root = fxmlLoader.load();
			PhraseDetailFormController controller = fxmlLoader.getController();
			controller.setPhraseDetail(p);
			controller.getActionBtn().setText("Update");
			Stage stage = new Stage();
			ScreenUtils.setStageToScreen(searchFld, stage);
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.getIcons().add(new Image(ApplicationDataModel.APPLICATION_ICON));
			stage.setTitle("Sửa cụm từ");
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handleUpdatePhraseBtn(ActionEvent event) {
		try {
			int phraseDetailIndex = table.getSelectionModel().getSelectedIndex();
			Phrase p = table.getItems().get(phraseDetailIndex).getPhrase();
			if (phraseDetailIndex < 0)
				return;
			int phraseIndex = -1;
			ObservableList<Phrase> phrases = ApplicationDataModel.getInstance().getPhrases();
			for(Phrase phrase: phrases) {
				if(phrase.getPhrase().equals(p.getPhrase())) {
					phraseIndex = phrases.indexOf(phrase);
					break;
				}
			}
			System.out.println("Phrase index = " + phraseIndex);
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/fxml/PhraseForm.fxml"));
			Parent root = fxmlLoader.load();
			PhraseFormController controller = fxmlLoader.getController();
			controller.setPhrase(p, phraseIndex, phraseDetailIndex);
			Stage stage = new Stage();
			ScreenUtils.setStageToScreen(searchFld, stage);
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.getIcons().add(new Image(ApplicationDataModel.APPLICATION_ICON));
			stage.setTitle("Sửa cụm từ");
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handleDeleteBtn() {
		PhraseDetail p = table.getItems().get(table.getSelectionModel().getSelectedIndex());
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Xác nhận");
		alert.setHeaderText("Bạn có thực sự muốn xóa không?");
		Optional<ButtonType> button = alert.showAndWait();
		if (button.get() == ButtonType.OK) {
			System.out.println("Chấp nhận xóa");
			try {
				System.out.println(p.getPhrase().getPhrase() + " >> sẽ không được xóa!");
				return;
				// DAOFactory.getPhraseDeitalDAO().deletePhraseDetail(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
