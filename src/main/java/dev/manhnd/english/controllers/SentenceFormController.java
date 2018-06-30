package dev.manhnd.english.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import dev.manhnd.english.application.ApplicationDataModel;
import dev.manhnd.english.dao.DAOFactory;
import dev.manhnd.english.entities.Sentence;
import dev.manhnd.english.utils.TextFieldUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SentenceFormController implements Initializable {

	@FXML
	private TextField ipaFld;

	@FXML
	private TextField audioFld;

	@FXML
	private Button clearBtn;

	@FXML
	private Button actionBtn;

	@FXML
	private TextField sentenceFld;

	@FXML
	private Button openFileBtn;

	@FXML
	private TextField definitionFld;

	private Sentence sentence;
	private Sentence oldSentence;
	private int index;

	private AutoCompletionBinding<Sentence> autocompleteSentence;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		autocompleteSentence = TextFields.bindAutoCompletion(sentenceFld,
				ApplicationDataModel.getInstance().getSentences());
		autocompleteSentence.minWidthProperty().bind(sentenceFld.widthProperty());
		autocompleteSentence.setDelay(10);
		autocompleteSentence.setOnAutoCompleted(s -> {
			sentenceFld.textProperty().set(s.getCompletion().getSentence());
		});
		
		TextFieldUtils.formatTextInputControl(sentenceFld);
		TextFieldUtils.formatTextInputControl(ipaFld);
		TextFieldUtils.formatTextInputControl(definitionFld);
	}

	@FXML
	void handleOpenFileBtn(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		String path = audioFld.getText();
		if (path != null) {
			File f = new File(path);
			if (f.isFile()) {
				chooser.setInitialDirectory(new File(path).getParentFile());
			} else {
				chooser.setInitialDirectory(new File(ApplicationDataModel.getInstance().getSentencesAudioPath()));
			}
		} else {
			chooser.setInitialDirectory(new File(ApplicationDataModel.getInstance().getSentencesAudioPath()));
		}
		File file = chooser.showOpenDialog(null);
		if (file != null) {
			ApplicationDataModel.getInstance().setSentencesAudioPath(file.getParent());
			audioFld.setText(file.getAbsolutePath());
		}
	}

	@FXML
	void handleActionBtn(ActionEvent event) {
		Stage stage = (Stage) actionBtn.getScene().getWindow();

		String action = actionBtn.getText();

		String sentence = sentenceFld.getText() == null ? null : sentenceFld.getText().replaceAll("\\s+", " ").trim();
		String ipa = ipaFld.getText() == null ? null : ipaFld.getText().trim();
		String definition = definitionFld.getText() == null ? null
				: definitionFld.getText().replaceAll("\\s+", " ").trim();
		String audio = audioFld.getText() == null ? null : audioFld.getText().trim();

		Alert alert = new Alert(AlertType.ERROR);
		if (action.equals("Add")) {
			System.out.println("Add");
			try {
				this.sentence = new Sentence(sentence, ipa, audio, definition);
				long id = DAOFactory.getSentenceDAO().createSentence(this.sentence);
				if (id > 0) {
					this.sentence.setId(id);
					ApplicationDataModel.getInstance().getSentences().add(this.sentence);
					ApplicationDataModel.getInstance().getSentenceSuggestionProvider().addPossibleSuggestions(this.sentence);
					stage.close();
				}else {
					throw new Exception("Lỗi thêm câu!");
				}
			} catch (Exception e) {
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				this.sentence = null;
			}
		} else {
			System.out.println("Update");
			try {
				if (this.sentence == null) {
					this.sentence = oldSentence;
				}
				this.sentence.setSentence(sentence);
				this.sentence.setIpa(ipa);
				this.sentence.setDefinition(definition);
				this.sentence.setAudio(audio);
				boolean updated = DAOFactory.getSentenceDAO().updateSentence(this.sentence);
				if (updated) {
					ApplicationDataModel.getInstance().getSentences().set(index, this.sentence);
					stage.close();
				}else {
					throw new Exception("Lỗi sửa câu!");
				}
			} catch (Exception e) {
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				this.oldSentence = this.sentence;
				this.sentence = null;
			}
		}

	}

	public Button getActionBtn() {
		return actionBtn;
	}

	public void setSentence(Sentence sentence) {
		this.sentence = sentence;
		if (sentence != null) {
			sentenceFld.setText(this.sentence.getSentence());
			ipaFld.setText(this.sentence.getIpa());
			definitionFld.setText(this.sentence.getDefinition());
			audioFld.setText(this.sentence.getAudio());
			index = ApplicationDataModel.getInstance().getSentences().indexOf(this.sentence);
		}
	}
}
