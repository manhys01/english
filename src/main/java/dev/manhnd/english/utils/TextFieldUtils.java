package dev.manhnd.english.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

public class TextFieldUtils {

	public static void formatTextField(TextField textField) {
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					System.out.println("lost");
					String text = textField.getText();
					if (text != null) {
						text = StringUtils.format(text).replaceAll("sb", "somebody").replaceAll("sth", "something");
						System.out.println("Text: " + text);
						textField.setText(text);
					}
				} else {
					System.out.println("gain");
				}
			}
		});
	}
	
	public static void formatTextInputControl(TextInputControl textField) {
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					System.out.println("lost");
					String text = textField.getText();
					if (text != null) {
						text = StringUtils.format(text);
						System.out.println("Text: " + text);
						textField.setText(text);
					}
				} else {
					System.out.println("gain");
				}
			}
		});
	}
	
}
