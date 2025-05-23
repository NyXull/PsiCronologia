package util;

import javafx.scene.control.TextField;

public class Constraints {

	public static void setTextFieldInteger(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
	        if (newValue != null && !newValue.matches("\\d*")) {
	        	txt.setText(oldValue);
	        }
	    });
	}

	public static void setTextFieldMaxLength(TextField txt, int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
	        if (newValue != null && newValue.length() > max) {
	        	txt.setText(oldValue);
	        }
	    });
	}

	public static void setTextFieldDouble(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
		    	if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
                    txt.setText(oldValue);
                }
		    });
	}
	
	public static boolean validacaoSintaxeEmail(String email) {
		return email.contains("@") && email.contains(".") && email.indexOf("@") < email.lastIndexOf(".");
	}
	
	public static boolean senhaValida(String senha) {
		boolean temMaiuscula = senha.chars().anyMatch(Character::isUpperCase);
		boolean temSimbolo = senha.chars().anyMatch(c -> !Character.isLetterOrDigit(c));
		return senha.length() >= 8 && temMaiuscula && temSimbolo;
	}	
}