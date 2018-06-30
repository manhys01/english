package dev.manhnd.english.controllers;

import java.io.File;

import dev.manhnd.english.application.ApplicationDataModel;
import dev.manhnd.english.dao.DAOFactory;
import dev.manhnd.english.entities.Grammar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class GrammarFormController {

	@FXML
	private TextField idFld;

	@FXML
	private TextField lessonFld;

	@FXML
	private TextField pathFld;

	@FXML
	private Button actionBtn;

	private Grammar g;
	private int index;

	@FXML
	void handleActionBtn(ActionEvent event) {
		Window window = actionBtn.getScene().getWindow();
		Long id = Long.parseLong(idFld.getText());
		String lesson = lessonFld.getText().trim();
		String path = pathFld.getText().trim();

		if (actionBtn.getText().equals("Add")) {
			System.out.println("add");
			g = new Grammar(id, lesson, path);
			try {
				Long result = DAOFactory.getGrammarDAO().createGrammar(g);
				if (result > 0) {
					ApplicationDataModel.getInstance().getGrammars().add(g);
					window.hide();
				}
			} catch (Exception e) {
				e.printStackTrace();
				g = null;
			}

		} else if (actionBtn.getText().equals("Update")) {
			System.out.println("update");
			g.setId(id);
			g.setLesson(lesson);
			g.setPath(path);
			try {
				boolean updated = DAOFactory.getGrammarDAO().updateGrammar(g);
				if (updated) {
					ApplicationDataModel.getInstance().getGrammars().set(index, g);
					window.hide();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void handleFileChooser(ActionEvent event) {
		String path = pathFld.textProperty().get();
		String grammarPath;
		if (path != null) {
			File f = new File(path);
			if (f.isFile()) {
				grammarPath = getGrammarPath(f.getParentFile());
			} else if (f.isDirectory()) {
				grammarPath = getGrammarPath(new File(pathFld.getText()));
			} else {
				grammarPath = getGrammarPath(new File(ApplicationDataModel.getInstance().getGrammarPath()));
			}
		} else {
			grammarPath = getGrammarPath(new File(ApplicationDataModel.getInstance().getGrammarPath()));
		}
		if (grammarPath != null)
			pathFld.textProperty().set(grammarPath);
	}

	private String getGrammarPath(File initialDirectory) {
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(initialDirectory);
		chooser.setSelectedExtensionFilter(new ExtensionFilter("*.html", "html"));
		File file = chooser.showOpenDialog(null);
		if (file == null)
			return null;
		return file.getAbsolutePath();
	}

	public void setData(String action, Grammar g, int index) throws Exception {
		actionBtn.setText(action);
		this.g = g;
		this.index = index;
		idFld.setDisable(true);
		if (g != null) {
			idFld.setText(String.valueOf(g.getId()));
			lessonFld.setText(g.getLesson());
			pathFld.setText(g.getPath());
		} else {
			try {
				Long id = DAOFactory.getGrammarDAO().getLastGrammar().getId() + 1;
				idFld.setText(id.toString());
				if (id > 180)
					throw new Exception("Số bài vượt quá giới hạn!");
				File file = new File(ApplicationDataModel.getInstance().getGrammarPath() + String.format("/%s/", id));
				if (file.isDirectory())
					pathFld.setText(file.getAbsolutePath());
			} catch (Exception e) {
				throw e;
			}
		}
	}

}
