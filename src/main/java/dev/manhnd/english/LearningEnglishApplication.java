package dev.manhnd.english;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import dev.manhnd.english.application.ApplicationConstant;
import dev.manhnd.english.application.ApplicationDataModel;
import dev.manhnd.english.utils.HibernateUtil;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class LearningEnglishApplication extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	private Parent root;
	private VBox splashLayout;
	private ProgressBar loadProgress;
	private Label progressText;
	private Stage mainStage;
	private Text nickName;
	int count = 0;

	private File startUpFile;
	private OutputStream os;
	private OutputStreamWriter oswr;
	private BufferedWriter br;

	private double lastTime;

	private double totalTime;

	private static final int SPLASH_WIDTH = 500;

	@Override
	public void init() throws Exception {
		lastTime = System.currentTimeMillis();
		startUpFile = new File(getClass().getClassLoader().getResource(".").getPath() + "/startUpLog.txt");
		if (!startUpFile.exists()) {
			startUpFile.createNewFile();
		}

		ApplicationDataModel.getInstance().setLoadingInformation("Starting program");
		ApplicationDataModel.getInstance().setLoadingInformation(ApplicationConstant.STARTUP_TASKS[0]);
		ImageView splash = new ImageView(new Image(ApplicationDataModel.SPLASH_IMAGE));
		nickName = new Text("Yo Shi");
		StackPane stackPane = new StackPane(splash, nickName);
		loadProgress = new ProgressBar(100);
		loadProgress.setPrefWidth(SPLASH_WIDTH);
		progressText = new Label("Bắt đầu");
		splashLayout = new VBox(5);
		splashLayout.setPadding(new Insets(20));
		splashLayout.getChildren().addAll(stackPane, loadProgress, progressText);
		progressText.setAlignment(Pos.CENTER);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		os = new FileOutputStream(startUpFile);
		oswr = new OutputStreamWriter(os, StandardCharsets.UTF_8);
		br = new BufferedWriter(oswr);

		final Task<ObservableList<String>> startUpTasks = new Task<ObservableList<String>>() {
			@Override
			protected ObservableList<String> call() throws InterruptedException, IOException {
				ObservableList<String> tasks = FXCollections.observableArrayList();
				ChangeListener<String> changeListener = new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> obs, String oValue, String nValue) {

						double currentTime = System.currentTimeMillis();
						double time = (currentTime - lastTime) / 1000;
						totalTime += time;
						lastTime = currentTime;

						updateProgress(count, ApplicationConstant.STARTUP_TASKS.length - 1);
						updateMessage(nValue);
						count++;
						try {
							br.write(String.format("Thời gian tải %s = %s giây.",
									ApplicationConstant.STARTUP_TASKS[count - 1], time));
							br.newLine();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				ApplicationDataModel.getInstance().loadingInformationProperty().addListener(changeListener);

				root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));

				// Done
				ApplicationDataModel.getInstance().setLoadingInformation(ApplicationConstant.STARTUP_TASKS[13]);
				ApplicationDataModel.getInstance().loadingInformationProperty().removeListener(changeListener);

				br.newLine();
				br.write("Tổng thời gian khởi động là " + totalTime + " giây.");

				os.flush();
				oswr.flush();
				br.flush();

				os.close();
				oswr.close();
				br.close();
				return tasks;
			}
		};

		showSplash(primaryStage, startUpTasks, () -> showMainStage());
		new Thread(startUpTasks).start();
	}

	private void showSplash(final Stage initStage, Task<?> task, InitCompletionHandler initCompletionHandler) {
		progressText.textProperty().bind(task.messageProperty());
		loadProgress.progressProperty().bind(task.progressProperty());
		task.stateProperty().addListener((observableValue, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED) {
				loadProgress.progressProperty().unbind();
				loadProgress.setProgress(1);
				initStage.toFront();
				FadeTransition fadeSplash = new FadeTransition(Duration.seconds(0.1), splashLayout);
				fadeSplash.setFromValue(1.0);
				fadeSplash.setToValue(0.0);
				fadeSplash.setOnFinished(actionEvent -> initStage.hide());
				fadeSplash.play();
				initCompletionHandler.complete();
			}
		});

		Scene splashScene = new Scene(splashLayout);
		initStage.setScene(splashScene);
		initStage.initStyle(StageStyle.TRANSPARENT);
		initStage.setAlwaysOnTop(true);
		initStage.show();
	}

	private void showMainStage() {
		mainStage = new Stage(StageStyle.DECORATED);
		mainStage.setTitle("Learning English Application");
		mainStage.getIcons().add(new Image(ApplicationDataModel.APPLICATION_ICON));

		Scene scene = new Scene(root);
		mainStage.setScene(scene);
		mainStage.show();

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(startUpFile), StandardCharsets.UTF_8))) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông tin thời gian khởi động chương trình");
			alert.setHeaderText("THỜI GIAN KHỞI ĐỘNG");
			String line = br.readLine();
			StringBuilder builder = new StringBuilder();
			while (line != null) {
				builder.append(line + "\n");
				line = br.readLine();
			}
			alert.setContentText(builder.toString());
			alert.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() throws Exception {
		HibernateUtil.closeSessionFactory();
	}

	public interface InitCompletionHandler {
		void complete();
	}

}
