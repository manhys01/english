package dev.manhnd.english.utils;

import dev.manhnd.english.application.ApplicationDataModel;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXUtils {
	
	public static void centerPopUpStage(Stage primaryStage, Stage popUpStage) {
		
		popUpStage.initModality(Modality.APPLICATION_MODAL);
		popUpStage.getIcons().add(new Image(ApplicationDataModel.APPLICATION_ICON));
		popUpStage.initOwner(primaryStage);
		
		// Calculate the center position of the parent Stage
		double centerXPosition = primaryStage.getX() + primaryStage.getWidth()/2d;
		double centerYPosition = primaryStage.getY() + primaryStage.getHeight()/2d;

		// Hide the pop-up stage before it is shown and becomes relocated
		popUpStage.setOnShowing(ev -> popUpStage.hide());

		// Relocate the pop-up Stage
		popUpStage.setOnShown(ev -> {
		    popUpStage.setX(centerXPosition - popUpStage.getWidth()/2d);
		    popUpStage.setY(centerYPosition - popUpStage.getHeight()/2d);
		    popUpStage.show();
		});
	}
	
}
