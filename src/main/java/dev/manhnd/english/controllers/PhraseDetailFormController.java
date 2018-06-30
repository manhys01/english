package dev.manhnd.english.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import dev.manhnd.english.application.ApplicationDataModel;
import dev.manhnd.english.dao.DAOFactory;
import dev.manhnd.english.entities.Phrase;
import dev.manhnd.english.entities.PhraseDetail;
import dev.manhnd.english.utils.TextFieldUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PhraseDetailFormController implements Initializable {

	@FXML
	private TextField ipaFld;

	@FXML
	private Button actionBtn;

	@FXML
	private TextField defViFld;

	@FXML
	private TextField defEnFld;

	@FXML
	private TextField phraseFld;

	@FXML
	private TextArea descTxa;

	@FXML
	private TextField imageFld;

	private PhraseDetail phraseDetail;
	private PhraseDetail oldPhraseDetail;

	private AutoCompletionBinding<Phrase> autoCompletePhraseFld;

	private int index;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		autoCompletePhraseFld = TextFields.bindAutoCompletion(phraseFld,
				ApplicationDataModel.getInstance().getPhraseSuggestionProvider());

		autoCompletePhraseFld.setOnAutoCompleted(p -> {
			Phrase phrase = p.getCompletion();
			phraseFld.setText(phrase.getPhrase());
			ipaFld.setText(phrase.getIpa());
		});

		autoCompletePhraseFld.minWidthProperty().bind(phraseFld.widthProperty());

		TextFieldUtils.formatTextField(phraseFld);
		TextFieldUtils.formatTextInputControl(ipaFld);
		TextFieldUtils.formatTextField(defEnFld);
		TextFieldUtils.formatTextInputControl(defViFld);
	}

	@FXML
	void handleActionBtn() {
		Stage window = (Stage) actionBtn.getScene().getWindow();

		String phrase = phraseFld.getText() == null ? null : phraseFld.getText().trim();
		String ipa = ipaFld.getText() == null ? null : ipaFld.getText().trim();
		String definition = defEnFld.getText() == null ? null : defEnFld.getText().trim();
		String meaning = defViFld.getText() == null ? null : defViFld.getText().trim();
		String desc = descTxa.getText() == null ? null : descTxa.getText().trim();

		Alert alert = new Alert(AlertType.ERROR);
		if (actionBtn.getText().equals("Update")) {
			try {
				if (oldPhraseDetail != null) {
					phraseDetail = oldPhraseDetail;
				}
				Phrase phraseHaveGot = getPhrase(true, phrase, ipa);
				if (phraseHaveGot == null)
					return;
				phraseDetail.setPhrase(phraseHaveGot);
				phraseDetail.setDefinition(definition);
				phraseDetail.setMeaning(meaning);
				phraseDetail.setDescription(desc);
				boolean updated = DAOFactory.getPhraseDetailDAO().updatePhraseDetail(phraseDetail);
				if (updated) {
					ApplicationDataModel.getInstance().getPhraseDetails().set(index, this.phraseDetail);
					window.close();
				}
			} catch (Exception e) {
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				oldPhraseDetail = this.phraseDetail;
				phraseDetail = null;
			}
		} else {
			try {
				Phrase phraseHaveGot = getPhrase(false, phrase, ipa);
				if (phraseHaveGot == null)
					return;
				this.phraseDetail = new PhraseDetail(phraseHaveGot, definition, meaning, desc);
				long id = DAOFactory.getPhraseDetailDAO().createPhraseDetail(phraseDetail);
				if (id > 0) {
					this.phraseDetail.setId(id);
					ApplicationDataModel.getInstance().getPhraseDetails().add(phraseDetail);
					ApplicationDataModel.getInstance().getPhraseDetailSuggestionProvider()
							.addPossibleSuggestions(phraseDetail);
					window.close();
				}
			} catch (Exception e) {
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				this.phraseDetail = null;
			}
		}
	}

	/**
	 * Lấy về cụm từ nếu tồn tại, ngược lại sẽ tạo mới và lấy về
	 * 
	 * @param phrase
	 * @param ipa
	 * @return
	 * @throws Exception
	 */
	private Phrase getPhrase(boolean update, String phrase, String ipa) throws Exception {
		Phrase p = DAOFactory.getPhraseDAO().getPhrase(phrase);
		if (p == null) {
			Phrase newPhrase = new Phrase(phrase, ipa);
			if (update) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Cụm từ chưa tồn tại");
				alert.setHeaderText("Cụm từ [" + phrase + "] chưa tồn tại!");
				alert.setContentText("Bạn có muốn tạo ra cụm từ này không?");
				Optional<ButtonType> optional = alert.showAndWait();
				if (optional.get() == ButtonType.OK) {
					Long id = DAOFactory.getPhraseDAO().createPhrase(newPhrase);
					if (id > 0) {
						System.out.println("Tạo ra cụm từ thành công!");
						newPhrase.setId(id);
						ApplicationDataModel.getInstance().getPhrases().add(newPhrase);
						ApplicationDataModel.getInstance().getPhraseSuggestionProvider()
								.addPossibleSuggestions(newPhrase);
						return newPhrase;
					} else {
						throw new Exception("Lỗi DAO: Không thể tạo cụm từ này!");
					}
				}
			} else {
				Long id = DAOFactory.getPhraseDAO().createPhrase(newPhrase);
				if (id > 0) {
					System.out.println("Tạo ra cụm từ thành công!");
					newPhrase.setId(id);
					ApplicationDataModel.getInstance().getPhrases().add(newPhrase);
					ApplicationDataModel.getInstance().getPhraseSuggestionProvider().addPossibleSuggestions(newPhrase);
					return newPhrase;
				} else {
					throw new Exception("Lỗi DAO: Không thể tạo cụm từ này!");
				}
			}
		}
		if (update) {
			return p;
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Cụm từ đã tồn tại");
			alert.setHeaderText("Cụm từ [" + phrase + "] đã tồn tại!");
			alert.setContentText("Bạn có muốn sử dụng cụm từ này để tạo mới một nghĩa khác không?");
			Optional<ButtonType> optional = alert.showAndWait();
			if (optional.get() == ButtonType.OK) {
				return p;
			}
			return null;
		}
	}

	public void setPhraseDetail(PhraseDetail phraseDetail) {
		this.phraseDetail = phraseDetail;
		if (this.phraseDetail != null) {
			index = ApplicationDataModel.getInstance().getPhraseDetails().indexOf(phraseDetail);
			phraseFld.setText(phraseDetail.getPhrase().getPhrase());
			ipaFld.setText(phraseDetail.getPhrase().getIpa());
			defEnFld.setText(phraseDetail.getDefinition());
			defViFld.setText(phraseDetail.getMeaning());
			descTxa.setText(phraseDetail.getDescription());
		}
	}

	public Button getActionBtn() {
		return actionBtn;
	}
}
