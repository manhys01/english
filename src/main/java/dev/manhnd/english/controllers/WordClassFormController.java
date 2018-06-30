package dev.manhnd.english.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import dev.manhnd.english.application.ApplicationDataModel;
import dev.manhnd.english.dao.DAOFactory;
import dev.manhnd.english.entities.WordClass;
import dev.manhnd.english.utils.TextFieldUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WordClassFormController implements Initializable {

	@FXML
	private TextField nameFld;

	@FXML
	private TextArea descriptionFld;

	@FXML
	private TextField definitionFld;

	@FXML
	private Button actionBtn;

	private WordClass wordClass;
	private WordClass oldWordClass;

	private AutoCompletionBinding<WordClass> autoCompleteWordClass;

	private int index;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		autoCompleteWordClass = TextFields.bindAutoCompletion(nameFld,
				ApplicationDataModel.getInstance().getWordClassesSuggestionProvider());
		
		autoCompleteWordClass.minWidthProperty().bind(nameFld.widthProperty());
		
		TextFieldUtils.formatTextInputControl(nameFld);
		TextFieldUtils.formatTextInputControl(definitionFld);
		TextFieldUtils.formatTextInputControl(descriptionFld);
	}

	@FXML
	void handleActionBtn(ActionEvent event) {
		Stage stage = (Stage) actionBtn.getScene().getWindow();
		String action = stage.getTitle();

		String name = nameFld.getText() == null ? null : nameFld.getText();
		String definition = definitionFld.getText() == null ? null : definitionFld.getText();
		String description = descriptionFld.getText() == null ? null : descriptionFld.getText();

		Alert alert = new Alert(AlertType.ERROR);

		if (action.equals("Thêm từ loại")) {
			this.wordClass = new WordClass(name, definition, description);
			try {
				long id = DAOFactory.getWordClassDAO().createWordClass(wordClass);
				if (id > 0) {
					wordClass.setId(id);
					ApplicationDataModel.getInstance().getWordClasses().add(wordClass);
					ApplicationDataModel.getInstance().getWordClassesSuggestionProvider()
							.addPossibleSuggestions(wordClass);
					stage.close();
				} else {
					throw new Exception("Lỗi WordClassDAO!");
				}
			} catch (Exception e) {
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				wordClass = null;
			}
		} else {
			try {
				if (wordClass == null) {
					wordClass = oldWordClass;
				}
				wordClass.setName(name);
				wordClass.setDefinition(definition);
				wordClass.setDescription(description);
				boolean success = DAOFactory.getWordClassDAO().updateWordClass(wordClass);
				if (success) {
					ApplicationDataModel.getInstance().getWordClasses().set(index, wordClass);
					stage.close();
				} else {
					throw new Exception("Lỗi WordClassDAO!");
				}
			} catch (Exception e) {
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				oldWordClass = wordClass;
				wordClass = null;
			}
		}
	}

	public void setWordClass(WordClass wordClass) {
		this.wordClass = wordClass;
		if (wordClass != null) {
			nameFld.setText(wordClass.getName());
			definitionFld.setText(wordClass.getDefinition());
			descriptionFld.setText(wordClass.getDescription());
			index = ApplicationDataModel.getInstance().getWordClasses().indexOf(wordClass);
		}
	}

}
