package dev.manhnd.english.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import dev.manhnd.english.application.ApplicationDataModel;
import dev.manhnd.english.dao.DAOFactory;
import dev.manhnd.english.entities.Grammar;
import dev.manhnd.english.utils.ExerciseGrammar;
import dev.manhnd.english.utils.ScreenUtils;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GrammarController implements Initializable {

	@FXML
	private TableView<Grammar> grammarTable;

	@FXML
	private TableColumn<Grammar, Integer> id_col;

	@FXML
	private TableColumn<Grammar, String> lesson_col;

	@FXML
	private TextField searchFld;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
		lesson_col.setCellValueFactory(new PropertyValueFactory<>("lesson"));
		grammarTable.setItems(ApplicationDataModel.getInstance().getGrammars());

		ApplicationDataModel.getInstance().getGrammars().addListener(new ListChangeListener<Grammar>() {
			@Override
			public void onChanged(Change<? extends Grammar> c) {
				while (c.next()) {
					if (c.wasPermutated()) {
						System.out.println("Grammar Permutated from " + c.getFrom() + " to " + c.getTo());
					} else if (c.wasUpdated()) {
						System.out.println("Grammar Updated from " + c.getFrom() + " to " + c.getTo());
					} else if (c.wasReplaced()) {
						System.out.println("Grammar Replaced from " + c.getFrom() + " to " + c.getTo());
					} else {
						if (c.wasRemoved()) {
							System.out.println("Grammar Removed from " + c.getFrom() + " to " + c.getTo());
						} else if (c.wasAdded()) {
							System.out.println("Grammar Added from " + c.getFrom() + " to " + c.getTo());
							Grammar g = c.getList().get(c.getFrom());
							grammarTable.scrollTo(g);
							grammarTable.getSelectionModel().select(g);
						}
					}
				}
			}
		});
	}

	@FXML
	void handleAddAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/GrammarForm.fxml"));
			Parent form = loader.load();
			GrammarFormController controller = loader.getController();

			controller.setData("Add", null, 0);

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(form));
			stage.setTitle("Thêm");
			ScreenUtils.setStageToScreen(searchFld, stage);
			stage.getIcons().add(new Image(ApplicationDataModel.APPLICATION_ICON));

			stage.showAndWait();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	void handleEditAction(ActionEvent event) throws IOException {
		try {
			int index = grammarTable.getSelectionModel().getSelectedIndex();
			if (index < 0)
				return;
			Grammar g = grammarTable.getItems().get(index);

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/GrammarForm.fxml"));
			Parent form = loader.load();
			GrammarFormController controller = loader.getController();
			controller.setData("Update", g, index);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(form));
			stage.setTitle("Sửa");
			ScreenUtils.setStageToScreen(searchFld, stage);
			stage.getIcons().add(new Image(ApplicationDataModel.APPLICATION_ICON));

			stage.showAndWait();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	void handleViewAction(ActionEvent event) throws MalformedURLException {
		int index = grammarTable.getSelectionModel().getSelectedIndex();
		if (index < 0)
			return;
		Grammar g = grammarTable.getItems().get(index);
		WebView view = new WebView();
		view.getEngine().load(new File(g.getPath()).toURI().toURL().toString());

		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(g.getLesson());
		stage.setScene(new Scene(view, 800, 600));
		stage.getIcons().add(new Image(ApplicationDataModel.APPLICATION_ICON));
		stage.showAndWait();
	}

	@FXML
	void exerciseGrammar(ActionEvent event) {
		try {
			int index = grammarTable.getSelectionModel().getSelectedIndex();
			if (index < 0)
				return;
			Grammar g = grammarTable.getItems().get(index);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Bài " + g.getId() + ": " + g.getLesson());

			ExerciseGrammar content = new ExerciseGrammar(
					DAOFactory.getGrammarQuestionDAO().getGrammarQuestionsByDay(g.getId()));
			Scene scene = new Scene(content, 800, 350);
			scene.getStylesheets().add("/styles/grammar_exercise.css");
			stage.getIcons().add(new Image(ApplicationDataModel.APPLICATION_ICON));
			stage.setScene(scene);
			stage.showAndWait();
		} catch (Exception e) {
			new Alert(AlertType.ERROR).showAndWait();
		}
	}

}
