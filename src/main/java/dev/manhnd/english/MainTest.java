package dev.manhnd.english;

import java.util.List;

import dev.manhnd.english.dao.DAOFactory;
import dev.manhnd.english.entities.GrammarQuestion;
import dev.manhnd.english.utils.ExerciseGrammar;
import dev.manhnd.english.utils.HibernateUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainTest extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		List<GrammarQuestion> grammarQuestionsByDay = DAOFactory.getGrammarQuestionDAO().getGrammarQuestionsByDay(1);
		ExerciseGrammar g = new ExerciseGrammar(grammarQuestionsByDay);
		Scene scene = new Scene(g, 600, 300);
		scene.getStylesheets().add("/styles/grammar_exercise.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		HibernateUtil.closeSessionFactory();
	}
}
