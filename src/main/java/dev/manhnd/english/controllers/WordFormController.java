package dev.manhnd.english.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import dev.manhnd.english.application.ApplicationDataModel;
import dev.manhnd.english.dao.DAOFactory;
import dev.manhnd.english.entities.Vocabulary;
import dev.manhnd.english.entities.Word;
import dev.manhnd.english.utils.TextFieldUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class WordFormController implements Initializable {

	@FXML
	private TextField wordFld;

	@FXML
	private TextField ipaFld;

	@FXML
	private Button normalAudioBtn;

	@FXML
	private Button specialAudioBtn;

	@FXML
	private TextField normalAudioFld;

	@FXML
	private TextField specialAudioFld;

	@FXML
	private Button saveBtn;

	private Word word;
	private Word oldWord;

	private int index;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TextFields.bindAutoCompletion(wordFld, ApplicationDataModel.getInstance().getWordsSuggestionProvider())
				.minWidthProperty().bind(wordFld.widthProperty());
		TextFieldUtils.formatTextInputControl(wordFld);
		TextFieldUtils.formatTextInputControl(ipaFld);
	}

	@FXML
	void handleNormalAudioBtn() {
		String path = normalAudioFld.textProperty().get();
		String audioPath;
		if (path != null) {
			File f = new File(path);
			if (f.isFile()) {
				audioPath = getAudioPath(f.getParentFile());
			} else {
				audioPath = getAudioPath(new File(ApplicationDataModel.getInstance().getNormalWordsAudioPath()));
			}
		} else {
			audioPath = getAudioPath(new File(ApplicationDataModel.getInstance().getNormalWordsAudioPath()));
		}
		if (audioPath != null)
			normalAudioFld.textProperty().set(audioPath);
	}

	@FXML
	void handleSpecialAudioBtn() {
		String path = specialAudioFld.textProperty().get();
		String audioPath;
		if (path != null) {
			File f = new File(path);
			if (f.isFile()) {
				audioPath = getAudioPath(f.getParentFile());
			} else {
				audioPath = getAudioPath(new File(ApplicationDataModel.getInstance().getSpecialWordsAudioPath()));
			}
		} else {
			audioPath = getAudioPath(new File(ApplicationDataModel.getInstance().getSpecialWordsAudioPath()));
		}
		if (audioPath != null)
			specialAudioFld.textProperty().set(audioPath);
	}

	@FXML
	void handleSaveBtn() {
		Stage stage = (Stage) specialAudioFld.getScene().getWindow();
		String title = stage.getTitle();
		Alert alert = new Alert(AlertType.ERROR);

		if (title.equals("Thêm từ")) {
			word = new Word(getWordFld(), getIpaFld(), getAudioFld(), getSpecialAudioFld());
			try {
				long id = DAOFactory.getWordDAO().createWord(word);
				if (id > 0) {
					ApplicationDataModel.getInstance().getWords().add(word);
					ApplicationDataModel.getInstance().getWordsSuggestionProvider().addPossibleSuggestions(word);
					stage.close();
				} else {
					throw new Exception("Lỗi thêm từ!");
				}
			} catch (Exception e) {
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				word = null;
			}
		} else {
			if (word == null) {
				word = oldWord;
			}
			word.setWord(getWordFld());
			word.setIpa(getIpaFld());
			word.setNormalAudio(getAudioFld());
			word.setSpecialAudio(getSpecialAudioFld());
			try {
				boolean success = DAOFactory.getWordDAO().updateWord(word);
				if (success) {
					ObservableList<Word> words = ApplicationDataModel.getInstance().getWords();
					words.set(index, word);
					ObservableList<Vocabulary> vocabularies = ApplicationDataModel.getInstance().getVocabularies();
					for(Vocabulary v: vocabularies) {
						if(v.getWord().getId().equals(word.getId())) {
							v.setWord(word);
						}
					}
					stage.close();
				} else {
					throw new Exception("Lỗi sửa từ!");
				}
			} catch (Exception e) {
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				oldWord = word;
				word = null;
			}

		}
	}

	private String getAudioPath(File initialDirectory) {
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(initialDirectory);
		chooser.setSelectedExtensionFilter(new ExtensionFilter("*.mp3", "mp3"));
		File file = chooser.showOpenDialog(null);
		if (file == null)
			return null;
		return file.getAbsolutePath();
	}

	private String getWordFld() {
		return wordFld.textProperty().get();
	}

	private String getIpaFld() {
		return ipaFld.textProperty().get();
	}

	private String getAudioFld() {
		return normalAudioFld.textProperty().get();
	}

	private String getSpecialAudioFld() {
		return specialAudioFld.textProperty().get();
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
		if (word != null) {
			wordFld.setText(word.getWord());
			ipaFld.setText(word.getIpa());
			normalAudioFld.setText(word.getNormalAudio());
			specialAudioFld.setText(word.getSpecialAudio());
			index = ApplicationDataModel.getInstance().getWords().indexOf(word);
		}
	}

}
