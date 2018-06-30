package dev.manhnd.english.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import dev.manhnd.english.application.ApplicationDataModel;
import dev.manhnd.english.dao.DAOFactory;
import dev.manhnd.english.entities.Phrase;
import dev.manhnd.english.entities.PhraseDetail;
import dev.manhnd.english.utils.TextFieldUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PhraseFormController implements Initializable {

	@FXML
	private TextField phraseFld;

	@FXML
	private TextField ipaFld;

	@FXML
	private Button actionBtn;

	private Phrase phrase;
	private Phrase oldPhrase;

	private int phraseDetailIndex;

	private AutoCompletionBinding<Phrase> autoCompleteWordClass;

	private int phraseIndex;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		autoCompleteWordClass = TextFields.bindAutoCompletion(phraseFld,
				ApplicationDataModel.getInstance().getPhraseSuggestionProvider());
		autoCompleteWordClass.minWidthProperty().bind(phraseFld.widthProperty());
		
		TextFieldUtils.formatTextField(phraseFld);
		TextFieldUtils.formatTextInputControl(ipaFld);
	}

	@FXML
	void handleActionBtn(ActionEvent event) {
		Stage stage = (Stage) actionBtn.getScene().getWindow();
		Alert alert = new Alert(AlertType.ERROR);
		String phrase = phraseFld.getText() == null ? null : phraseFld.getText();
		String ipa = ipaFld.getText() == null ? null : ipaFld.getText();
		try {
			if (this.phrase == null) {
				this.phrase = oldPhrase;
			}
			this.phrase.setPhrase(phrase);
			this.phrase.setIpa(ipa);
			boolean updated = DAOFactory.getPhraseDAO().updatePhrase(this.phrase);
			if (updated) {
				PhraseDetail phraseDetail = ApplicationDataModel.getInstance().getPhraseDetails().get(phraseDetailIndex);
				ApplicationDataModel.getInstance().getPhrases().set(phraseIndex , this.phrase);
				phraseDetail.setPhrase(this.phrase);
				ApplicationDataModel.getInstance().getPhraseDetails().set(phraseDetailIndex, phraseDetail);
				stage.close();
			} else {
				throw new Exception("Không thể sửa vì lỗi DAO!");
			}
		} catch (Exception e) {
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			oldPhrase = this.phrase;
			phrase = null;
		}
	}

	public void setPhrase(Phrase phrase, int phraseIndex, int phraseDetailIndex) {
		this.phrase = phrase;
		phraseFld.setText(phrase.getPhrase());
		ipaFld.setText(phrase.getIpa());
		this.phraseIndex = phraseIndex;
		this.phraseDetailIndex = phraseDetailIndex;
	}

}
