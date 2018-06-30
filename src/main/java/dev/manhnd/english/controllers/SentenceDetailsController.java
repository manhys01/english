package dev.manhnd.english.controllers;

import dev.manhnd.english.entities.Sentence;
import dev.manhnd.english.utils.SimpleAudioPlayer;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SentenceDetailsController {

	@FXML
	private VBox parent;

	@FXML
	private Text sentenceTxt;

	@FXML
	private Text ipaTxt;

	@FXML
	private Text definitionTxt;

	public void sendData(Sentence sentence, SimpleAudioPlayer control) {
		if (sentence != null) {
			sentenceTxt.setText(sentence.getSentence());
			String ipa = sentence.getIpa();
			if (ipa == null || ipa.isEmpty()) {
				ipa = "Phiên âm IPA chưa được cập nhật...";
			}
			ipaTxt.setText(ipa);
			ipaTxt.setText(ipa);
			definitionTxt.setText(sentence.getDefinition());
			parent.getChildren().add(control);
		}
	}

	public void sendData(Sentence sentence) {
		if (sentence != null) {
			sentenceTxt.setText(sentence.getSentence());
			String ipa = sentence.getIpa();
			if (ipa == null || ipa.isEmpty()) {
				ipa = "Phiên âm IPA chưa được cập nhật...";
			}
			ipaTxt.setText(ipa);
			definitionTxt.setText(sentence.getDefinition());
		}
	}

}
