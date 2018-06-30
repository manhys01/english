package dev.manhnd.english.utils;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Control;
import javafx.stage.Screen;
import javafx.stage.Window;

public class ScreenUtils {
	
	public static void setStageToScreen(Control control, Window stage) {
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
		double screenWidth = visualBounds.getWidth();
		double screenHeight = visualBounds.getHeight();
		Window owner = control.getScene().getWindow();
		double xOwner = owner.getX();
		double width = screenWidth - xOwner;
		double centerX = width / 2;
		double centerY = screenHeight / 2;
		double x = xOwner + centerX / 4;
		stage.setX(x);
		stage.setWidth(owner.getWidth() * 80 / 100);
		stage.setY(centerY / 2);
	}

}
