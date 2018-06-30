package dev.manhnd.english.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import dev.manhnd.english.application.ApplicationDataModel;
import dev.manhnd.english.dao.DAOFactory;
import dev.manhnd.english.entities.Vocabulary;
import dev.manhnd.english.entities.Word;
import dev.manhnd.english.entities.WordClass;
import dev.manhnd.english.utils.TextFieldUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class VocabularyFormController implements Initializable {

	@FXML
	private TextField wordFld;

	@FXML
	private TextField ipaFld;

	@FXML
	private TextField wordClassFld;

	@FXML
	private TextField definitionFld;

	@FXML
	private TextField meaningFld;

	@FXML
	private Button chooserImageBtn;

	@FXML
	private TextField imageFld;

	@FXML
	private Button chooseNormalAudioBtn;

	@FXML
	private TextField normalAudioFld;

	@FXML
	private Button chooseSpecialAudioBtn;

	@FXML
	private TextField specialAudioFld;

	@FXML
	private Button actionBtn;

	private Vocabulary vocabulary;

	private Vocabulary oldVocabulary;

	private AutoCompletionBinding<Word> autoCompleteWord;

	private AutoCompletionBinding<WordClass> autoCompleteWordClass;

	private AutoCompletionBinding<String> autoCompleteDefinition;

	private AutoCompletionBinding<String> autoCompleteMeaning;

	private int index;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		autoCompleteWord = TextFields.bindAutoCompletion(wordFld,
				ApplicationDataModel.getInstance().getWordsSuggestionProvider());
		autoCompleteWord.minWidthProperty().bind(wordFld.widthProperty());
		autoCompleteWord.setOnAutoCompleted(e -> {
			Word w = e.getCompletion();
			ipaFld.setText(w.getIpa());
			normalAudioFld.setText(w.getNormalAudio());
			specialAudioFld.setText(w.getSpecialAudio());
		});

		autoCompleteWordClass = TextFields.bindAutoCompletion(wordClassFld,
				ApplicationDataModel.getInstance().getWordClassesSuggestionProvider());
		autoCompleteWordClass.minWidthProperty().bind(wordClassFld.widthProperty());
		autoCompleteWordClass.setOnAutoCompleted(e -> {
			WordClass wc = e.getCompletion();
			wordClassFld.textProperty().set(wc.getName());
		});

		List<String> listDefinition = new ArrayList<>();
		List<String> listMeaning = new ArrayList<>();

		for (Vocabulary v : ApplicationDataModel.getInstance().getVocabularies()) {
			listDefinition.add(v.getDefinition());
			listMeaning.add(v.getMeaning());
		}
		autoCompleteDefinition = TextFields.bindAutoCompletion(definitionFld, listDefinition);
		autoCompleteDefinition.minWidthProperty().bind(definitionFld.widthProperty());
		autoCompleteMeaning = TextFields.bindAutoCompletion(meaningFld, listMeaning);
		autoCompleteMeaning.minWidthProperty().bind(meaningFld.widthProperty());

		TextFieldUtils.formatTextInputControl(wordFld);
		TextFieldUtils.formatTextInputControl(ipaFld);
		TextFieldUtils.formatTextInputControl(wordClassFld);
		TextFieldUtils.formatTextInputControl(definitionFld);
		TextFieldUtils.formatTextInputControl(meaningFld);
	}

	@FXML
	void handleActionBtn(ActionEvent event) {
		Stage stage = (Stage) actionBtn.getScene().getWindow();

		String word = wordFld.getText() == null ? null : wordFld.getText();
		String ipa = ipaFld.getText() == null ? null : ipaFld.getText();
		String name = wordClassFld.getText() == null ? null : wordClassFld.getText();
		String definition = definitionFld.getText() == null ? null : definitionFld.getText();
		String meaning = meaningFld.getText() == null ? null : meaningFld.getText();
		String image = imageFld.getText() == null ? null : imageFld.getText();
		String normalAudio = normalAudioFld.getText() == null ? null : normalAudioFld.getText();
		String specialAudio = specialAudioFld.getText() == null ? null : specialAudioFld.getText();

		Alert alert = new Alert(AlertType.ERROR);
		if (actionBtn.getText().equals("Add")) {
			try {
				Word w = getWord(word, ipa, normalAudio, specialAudio);
				WordClass wc = getWordClass(name);
				vocabulary = new Vocabulary(w, wc, definition, meaning, image);
				setImageView(image);
				Long id = DAOFactory.getVocabularyDAO().createVocabulary(vocabulary);
				if (id > 0) {
					vocabulary.setId(id);
					ApplicationDataModel.getInstance().getVocabularies().add(vocabulary);
					ApplicationDataModel.getInstance().getVocabulariesSuggestionProvider()
							.addPossibleSuggestions(vocabulary);
					stage.close();
				} else {
					throw new RuntimeException("Lỗi insert!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				vocabulary = null;
			}
		} else {
			System.out.println("update");
			try {
				Word w = getWord(word, ipa, normalAudio, specialAudio);
				WordClass wc = getWordClass(name);
				if (vocabulary == null) {
					vocabulary = oldVocabulary;
				}
				vocabulary.setWord(w);
				vocabulary.setWordClass(wc);
				vocabulary.setDefinition(definition);
				vocabulary.setMeaning(meaning);
				vocabulary.setImage(image);
				setImageView(image);
				boolean success = DAOFactory.getVocabularyDAO().updateVocabulary(vocabulary);
				if (success) {
					ObservableList<Word> words = ApplicationDataModel.getInstance().getWords();
					for (Word e : ApplicationDataModel.getInstance().getWords()) {
						if (e.getId().equals(w.getId())) {
							boolean updated = DAOFactory.getWordDAO().updateWord(w);
							if (updated)
								words.set(words.indexOf(e), w);
							break;
						}
					}
					ApplicationDataModel.getInstance().getVocabularies().set(index, vocabulary);
					stage.close();
				} else {
					throw new RuntimeException("Lỗi update!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				oldVocabulary = vocabulary;
				vocabulary = null;
			}
		}
	}

	private void setImageView(String image) {
		if (image == null) {
			vocabulary.setImageView(null);
			return;
		}
		File file = new File(image);
		if (file.isFile()) {
			ImageView imageView = new ImageView(new Image(file.toURI().toString()));
			vocabulary.setImageView(imageView);
		} else {
			vocabulary.setImageView(null);
		}
	}

	public void setVocabulary(Vocabulary vocabulary, int index) {
		this.vocabulary = vocabulary;
		this.index = index;
		if (vocabulary != null) {
			wordFld.setText(vocabulary.getWord().getWord());
			ipaFld.setText(vocabulary.getWord().getIpa());
			wordClassFld.setText(vocabulary.getWordClass().getName());
			definitionFld.setText(vocabulary.getDefinition());
			meaningFld.setText(vocabulary.getMeaning());
			imageFld.setText(vocabulary.getImage());
			normalAudioFld.setText(vocabulary.getWord().getNormalAudio());
			specialAudioFld.setText(vocabulary.getWord().getSpecialAudio());
		}
	}

	public Button getActionBtn() {
		return actionBtn;
	}

	@FXML
	void handleChooseNormalAudioBtn(ActionEvent event) {
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
	void handleChooseSpecialAudioBtn(ActionEvent event) {
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
	void handleChooserImageBtn(ActionEvent event) {
		String path = imageFld.textProperty().get();
		String imagePath;
		if (path != null) {
			File f = new File(path);
			if (f.isFile()) {
				imagePath = getPathImage(f.getParentFile());
			} else {
				imagePath = getPathImage(new File(ApplicationDataModel.getInstance().getVocabularyImagePath()));
			}
		} else {
			imagePath = getPathImage(new File(ApplicationDataModel.getInstance().getVocabularyImagePath()));
		}
		if (imagePath != null)
			imageFld.textProperty().set(imagePath);
	}

	private String getPathImage(File initialDirectory) {
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(initialDirectory);
		chooser.setSelectedExtensionFilter(new ExtensionFilter("*.mp3", "mp3"));
		File file = chooser.showOpenDialog(null);
		if (file == null)
			return null;
		ApplicationDataModel.getInstance().setVocabularyImagePath(file.getParent());
		return file.getAbsolutePath();
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

	private Word getWord(String word, String ipa, String normalAudio, String specialAudio) throws Exception {
		Word w = DAOFactory.getWordDAO().getWord(word);
		if (w == null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Từ [" + word + "] chưa tồn tại.");
			alert.setContentText("Bạn có muốn thêm từ này không?");
			Optional<ButtonType> optional = alert.showAndWait();
			if (optional.get() == ButtonType.OK) {
				Word newWord = new Word(word, ipa, normalAudio, specialAudio);
				Long id = DAOFactory.getWordDAO().createWord(newWord);
				if (id > 0) {
					newWord.setId(id);
					ApplicationDataModel.getInstance().getWords().add(newWord);
					ApplicationDataModel.getInstance().getWordsSuggestionProvider().addPossibleSuggestions(newWord);
					return newWord;
				} else {
					wordFld.requestFocus();
					throw new Exception("Lỗi WordDAO!");
				}
			} else {
				wordFld.requestFocus();
				throw new Exception("Vui lòng nhập từ khác!");
			}
		} else {
			w.setNormalAudio(normalAudio);
			w.setSpecialAudio(specialAudio);
			return w;
		}
	}

	private WordClass getWordClass(String name) throws Exception {
		WordClass wc = DAOFactory.getWordClassDAO().getWordClass(name);
		if (wc == null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Từ loại [" + name + "] chưa tồn tại.");
			alert.setContentText("Bạn có muốn thêm từ loại này không?");
			Optional<ButtonType> optional = alert.showAndWait();
			if (optional.get() == ButtonType.OK) {
				WordClass newWC = new WordClass(name, null, null);
				Long id = DAOFactory.getWordClassDAO().createWordClass(newWC);
				if (id > 0) {
					newWC.setId(id);
					ApplicationDataModel.getInstance().getWordClasses().add(newWC);
					ApplicationDataModel.getInstance().getWordClassesSuggestionProvider().addPossibleSuggestions(newWC);
					return newWC;
				} else {
					wordClassFld.requestFocus();
					throw new Exception("Lỗi WordClassDAO!");
				}
			} else {
				wordClassFld.requestFocus();
				throw new Exception("Vui lòng nhập từ loại khác!");
			}
		} else {
			return wc;
		}
	}

}