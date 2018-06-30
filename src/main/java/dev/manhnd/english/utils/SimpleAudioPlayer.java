package dev.manhnd.english.utils;

import java.io.File;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SimpleAudioPlayer extends HBox {

	private Media media;
	private MediaPlayer player;
	private Button playBtn;
	private Text timer;
	private Duration duration;
	private Duration currentTime;
	private Slider timeSlider;

	public SimpleAudioPlayer(File file) throws Exception {
		if (!file.isFile()) {
			throw new Exception("File not found!");
		}
		this.setSpacing(5);
		this.setAlignment(Pos.CENTER_LEFT);
		playBtn = new Button(">");
		playBtn.setPrefWidth(30);

		timeSlider = new Slider();
		timeSlider.setValue(0.0d);
		timeSlider.setMin(0.0d);
		timeSlider.setMax(Double.MAX_VALUE);

		timer = new Text("00:00/00:00");

		// Media
		media = new Media(file.toURI().toString());
		player = new MediaPlayer(media);

		playBtn.setOnAction(e -> {
			Status status = player.getStatus();
			System.out.println("Status: " + status);
			if (status == Status.READY || status == Status.PAUSED || status == Status.STOPPED) {
				play();
				return;
			}
			if (status == Status.PLAYING) {
				pause();
			}
		});

		player.setOnReady(() -> {
			duration = media.getDuration();
			currentTime = Duration.ZERO;
			// Set max value of time slider to millisecond
			timeSlider.setMax(duration.toMillis());
			timer.setText(formatTime(currentTime, duration));
		});

		player.setOnEndOfMedia(() -> {
			stop();
		});

		player.currentTimeProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				currentTime = player.getCurrentTime();
				timeSlider.setValue(currentTime.toMillis());
				timer.setText(formatTime(currentTime, duration));
			}
		});

		timeSlider.setOnMousePressed(e -> {
			Node node = e.getPickResult().getIntersectedNode();
			if (node instanceof StackPane) {
				System.out.println("Pressed: " + player.getStatus());
				if (player.getStatus() == Status.READY) {
					seek();
					return;
				}
				pause();
				seek();
			}
		});

		timeSlider.setOnMouseDragged(e -> {
			Node node = e.getPickResult().getIntersectedNode();
			if (node instanceof StackPane) {
				System.out.println("Dragged: " + player.getStatus());
				if (player.getStatus() == Status.READY) {
					seek();
					return;
				}
				pause();
			}
		});

		timeSlider.setOnMouseReleased(e -> {
			Node node = e.getPickResult().getIntersectedNode();
			if (node instanceof StackPane) {
				System.out.println("Released: " + player.getStatus());
				if (player.getStatus() == Status.PAUSED || player.getStatus() == Status.PLAYING) {
					play();
				}
			}
		});

		timeSlider.valueProperty().addListener(e -> {
			if (timeSlider.isValueChanging()) {
				seek();
			}
		});

		this.getChildren().addAll(playBtn, timeSlider, timer);
	}

	private void pause() {
		player.pause();
		playBtn.setText(">");
	}

	private void play() {
		player.play();
		playBtn.setText("||");
	}

	private void stop() {
		player.stop();
		playBtn.setText(">");
		currentTime = Duration.ZERO;
		player.seek(currentTime);
		timer.setText(formatTime(currentTime, duration));
	}

	private synchronized void seek() {
		currentTime = Duration.millis(timeSlider.getValue());
		player.seek(currentTime);
		timer.setText(formatTime(currentTime, duration));
	}

	private String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int) Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int) Math.ceil(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;
			if (durationHours > 0) {
				return String.format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds,
						durationHours, durationMinutes, durationSeconds);
			} else {
				return String.format("%02d:%02d/%02d:%02d", elapsedMinutes, elapsedSeconds, durationMinutes,
						durationSeconds);
			}
		} else {
			if (elapsedHours > 0) {
				return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
			} else {
				return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
			}
		}
	}

	public MediaPlayer getPlayer() {
		return player;
	}

}
