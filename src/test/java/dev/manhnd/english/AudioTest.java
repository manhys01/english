package dev.manhnd.english;

import java.io.File;
import java.util.List;

import dev.manhnd.english.dao.ConversationDAO;
import dev.manhnd.english.dao.ConversationDAOImpl;
import dev.manhnd.english.entities.PersonTalking;
import dev.manhnd.english.utils.HibernateUtil;
import dev.manhnd.english.utils.SimpleAudioPlayer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class AudioTest extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = new VBox(10);
		root.setAlignment(Pos.CENTER_LEFT);
		root.setPadding(new Insets(20, 20, 20, 20));
		ConversationDAO conversationDAO = new ConversationDAOImpl();
		try {
			List<PersonTalking> conversation = conversationDAO.getConversationDetail(1);
			if (!conversation.isEmpty()) {
				final File file = new File(conversation.get(1).getPersonInConversation().getConversation().getAudio());
				SimpleAudioPlayer player = new SimpleAudioPlayer(file);
				root.getChildren().add(player);
				
				for (PersonTalking p : conversation) {
					TextFlow textFlow = new TextFlow();
					VBox.setVgrow(textFlow, Priority.NEVER);
					Text text = new Text(p.getPersonInConversation().getName() + ": " + p.getSentence());
					text.setFont(Font.font("Arial", 13));
					text.setFill(Color.BLUE);
					textFlow.getChildren().add(text);
					root.getChildren().add(textFlow);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scene scence = new Scene(root, 500, 400);
		scence.setFill(Color.WHITE);

		primaryStage.setScene(scence);
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		HibernateUtil.closeSessionFactory();
	}

}
