package dev.manhnd.english.utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import dev.manhnd.english.entities.GrammarAnswer;
import dev.manhnd.english.entities.GrammarQuestion;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;

public class ExerciseGrammar extends StackPane {

	private List<VBox> boxes;
	private List<GrammarQuestion> questions;
	private Pagination pagination;
	private int size;

	public static final String RIGHT_ICON = "/images/icons/right.png";
	public static final String WRONG_ICON = "/images/icons/wrong.png";

	public ExerciseGrammar(List<GrammarQuestion> questions) throws Exception {
		if (questions.isEmpty())
			throw new Exception("List questions empty!");
		this.questions = questions;
		init();
	}

	private void init() {
		size = questions.size();
		boxes = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			VBox box = new VBox(10.0);
			createPage(i, box);
			boxes.add(box);
		}

		pagination = new Pagination(size);
		pagination.setPageFactory(new Callback<Integer, Node>() {
			@Override
			public Node call(Integer pageIndex) {
				return boxes.get(pageIndex);
			}
		});
		StackPane.setAlignment(pagination, Pos.CENTER_LEFT);
		this.setPadding(new Insets(20));
		this.getChildren().add(pagination);
	}

	private void createPage(int pageIndex, VBox box) {
		GrammarQuestion q = questions.get(pageIndex);

		DropShadow shadow = new DropShadow();
		shadow.setOffsetX(-2);
		shadow.setOffsetY(3);
		shadow.setColor(Color.rgb(155, 155, 155, 0.55));
		box.setEffect(shadow);

		Text questText = new Text(q.getQuestion());
		questText.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		TextFlow questionTextFlow = new TextFlow(questText);
		box.getChildren().add(questionTextFlow);

		ToggleGroup group = new ToggleGroup();

		q.getAnswers().forEach(a -> {
			VBox vBox = new VBox(5);
			HBox hBox = new HBox(10);

			// Choicer Answer radio button
			RadioButton answerRadioBtn = new RadioButton(a.getAnswer());
			answerRadioBtn.setFont(Font.font("Arial", 12));
			answerRadioBtn.setUserData(a);
			answerRadioBtn.setToggleGroup(group);
			// answerRadioBtn.setEffect(shadow);

			// Icon answer
			String url = a.isRightAnswer() ? getClass().getResource(RIGHT_ICON).toString()
					: getClass().getResource(WRONG_ICON).toString();
			ImageView icon = new ImageView(new Image(url));
			icon.setVisible(false);

			hBox.getChildren().addAll(answerRadioBtn, icon);

			// Description
			Text description = new Text(a.getDescription());
			description.setVisible(false);
			description.setFont(Font.font("Arial", 12));
			description.setFill(a.isRightAnswer() ? Color.GREEN : Color.RED);

			TextFlow answerExplain = new TextFlow(description);

			answerExplain.setPadding(new Insets(0, 0, 0, 20));
			answerExplain.managedProperty().bind(description.visibleProperty());

			vBox.getChildren().addAll(hBox, answerExplain);
			box.getChildren().add(vBox);
		});

		group.selectedToggleProperty().addListener(e -> {
			if (group.getSelectedToggle() != null) {
				GrammarAnswer answer = (GrammarAnswer)((RadioButton)group.getSelectedToggle()).getUserData();
				if(answer.isRightAnswer()) {
					URL correct = getClass().getResource("/effects/sounds/correct.mp3");
					MediaPlayer mediaPlayer = new MediaPlayer(new Media(correct.toString()));
					mediaPlayer.play();
				}else {
					URL incorrect = getClass().getResource("/effects/sounds/incorrect.mp3");
					MediaPlayer mediaPlayer = new MediaPlayer(new Media(incorrect.toString()));
					mediaPlayer.play();
				}
				for (Toggle toggle : group.getToggles()) {
					((RadioButton) toggle).setDisable(true);
				}
				for (int i = 1; i < box.getChildren().size(); i++) {
					VBox vBox = (VBox) box.getChildren().get(i);
					// Display icon
					ImageView image = (ImageView) ((HBox) vBox.getChildren().get(0)).getChildren().get(1);
					image.setVisible(true);

					// Display explain
					TextFlow explain = (TextFlow) vBox.getChildren().get(1);
					explain.getChildren().get(0).setVisible(true);
				}
//				if (pageIndex + 1 < size) {
//					pagination.setCurrentPageIndex(pageIndex + 1);
//				}
			}
		});
	}

}
