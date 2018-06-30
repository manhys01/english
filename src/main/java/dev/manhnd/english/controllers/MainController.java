package dev.manhnd.english.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dev.manhnd.english.application.ApplicationConstant;
import dev.manhnd.english.application.ApplicationDataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainController implements Initializable {

	@FXML
	private BorderPane root;

	@FXML
	private VBox menuBox;

	@FXML
	private MenuBar menuBar;

	@FXML
	private Menu fileMenu;

	@FXML
	private MenuItem closeMenuItem;

	@FXML
	private Menu windowMenu;

	@FXML
	private CheckMenuItem treeViewMenuItem;

	@FXML
	private SplitPane mainSplitPane;

	@FXML
	private VBox treeBox;

	@FXML
	private TreeView<String> treeView;

	@FXML
	private VBox tabPaneBox;

	@FXML
	private TabPane tabPane;

	private TreeItem<String> treeItems, phraseItem, sentenceItem, wordItem, wordClassItem, vocabularyItem, grammarItem;

	private Tab phrases, sentences, words, wordClasses, vocabularies, grammars;

	private VBox phraseView, sentenceView, wordView, wordClassView, vocabularyView, grammarView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ApplicationDataModel.getInstance().loadDataModel();

		// Tải giao diện
		ApplicationDataModel.getInstance().setLoadingInformation(ApplicationConstant.STARTUP_TASKS[12]);
		try {
			phraseView = FXMLLoader.load(getClass().getResource("/fxml/PhraseDetail.fxml"));
			sentenceView = FXMLLoader.load(getClass().getResource("/fxml/Sentence.fxml"));
			wordClassView = FXMLLoader.load(getClass().getResource("/fxml/WordClass.fxml"));
			wordView = FXMLLoader.load(getClass().getResource("/fxml/Word.fxml"));
			vocabularyView = FXMLLoader.load(getClass().getResource("/fxml/Vocabulary.fxml"));
			grammarView = FXMLLoader.load(getClass().getResource("/fxml/Grammar.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setTabPane();
		setTreeViewItem();
	}

	@FXML
	void showTreeView(ActionEvent event) {
		if (treeViewMenuItem.isSelected()) {
			mainSplitPane.setDividerPosition(0, 0.2);
			mainSplitPane.getItems().add(0, treeBox);
		} else {
			mainSplitPane.getItems().remove(0);
		}
	}

	@FXML
	void closeTreeView(MouseEvent event) {
		mainSplitPane.getItems().remove(0);
		treeViewMenuItem.setSelected(false);
	}

	private void setTabPane() {
		tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
		phrases = new Tab("Phrases", phraseView);
		sentences = new Tab("Sentences", sentenceView);
		words = new Tab("Words", wordView);
		wordClasses = new Tab("Word Classes", wordClassView);
		vocabularies = new Tab("Vocabularies", vocabularyView);
		grammars = new Tab("Grammars", grammarView);
	}

	private void setTreeViewItem() {
		
		treeItems = new TreeItem<>("Tiếng Anh giao tiếp 360");
		treeItems.setExpanded(true);

		phraseItem = new TreeItem<>("Phrases");

		treeItems.getChildren().add(phraseItem);

		sentenceItem = new TreeItem<>("Sentences");
		treeItems.getChildren().add(sentenceItem);

		wordItem = new TreeItem<>("Words");
		treeItems.getChildren().add(wordItem);

		wordClassItem = new TreeItem<>("Word Classes");
		treeItems.getChildren().add(wordClassItem);

		vocabularyItem = new TreeItem<>("Vocabularies");
		treeItems.getChildren().add(vocabularyItem);
		
		grammarItem = new TreeItem<>("Grammars");
		treeItems.getChildren().add(grammarItem);

		treeView.setRoot(treeItems);
		treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleMouseClicked(event));
	}

	private void handleMouseClicked(MouseEvent event) {
		Node node = event.getPickResult().getIntersectedNode();
		// Accept clicks only on node cells, and not on empty spaces of the TreeView
		if (node instanceof Text || (node instanceof TreeCell && ((TreeCell<?>) node).getText() != null)) {
			if (event.getClickCount() == 2) {
				String value = treeView.getSelectionModel().getSelectedItem().getValue();
				switch (value) {
				case "Phrases":
					if (!tabPane.getTabs().contains(phrases)) {
						tabPane.getTabs().add(phrases);
					}
					tabPane.getSelectionModel().select(phrases);
					break;
				case "Sentences":
					if (!tabPane.getTabs().contains(sentences)) {
						tabPane.getTabs().add(sentences);
					}
					tabPane.getSelectionModel().select(sentences);
					break;
				case "Words":
					if (!tabPane.getTabs().contains(words)) {
						tabPane.getTabs().add(words);
					}
					tabPane.getSelectionModel().select(words);
					break;
				case "Word Classes":
					if (!tabPane.getTabs().contains(wordClasses)) {
						tabPane.getTabs().add(wordClasses);
					}
					tabPane.getSelectionModel().select(wordClasses);
					break;
				case "Vocabularies":
					if (!tabPane.getTabs().contains(vocabularies)) {
						tabPane.getTabs().add(vocabularies);
					}
					tabPane.getSelectionModel().select(vocabularies);
					break;
				case "Grammars":
					if (!tabPane.getTabs().contains(grammars)) {
						tabPane.getTabs().add(grammars);
					}
					tabPane.getSelectionModel().select(grammars);
					break;
				default:
					break;
				}
			}
		}
	}
}
