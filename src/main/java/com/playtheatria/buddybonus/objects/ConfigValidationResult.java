package com.playtheatria.buddybonus.objects;

public class ConfigValidationResult {

    private boolean isValid;

    private String message;

    public ConfigValidationResult(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getIsValid() { return isValid; }

    public String getMessage() { return message; }
}

