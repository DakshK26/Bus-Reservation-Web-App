package com.web.bus.components;

import com.vaadin.flow.component.progressbar.ProgressBar;

public class PasswordStrengthBar extends ProgressBar {
    /*
    Private Instance Data
     */
    private CustomPasswordField passwordField;
    /*
    Default Constructor
     */
    public PasswordStrengthBar(CustomPasswordField passwordField){
        this.passwordField = passwordField;
        this.setWidth("100%");
        this.setHeight("5px");
        this.setVisible(false);

        passwordField.addValueChangeListener(event -> {
            if (passwordField.isValidPassword(event.getValue())) {
                this.setVisible(true);
                double strength = calculatePasswordStrength(event.getValue());
                this.setValue(strength);
            } else {
                this.setVisible(false);
            }
        });
    }

    /*
    Method to calculate password strength
     */
    private double calculatePasswordStrength(String password) {
        double strength = 0; // Strength of password
        int length = password.length();
        // check for uppercase letters
        if (password.matches(".*[A-Z].*")) {
            strength += 0.1;
        }
        // check for lowercase letters
        if (password.matches(".*[a-z].*")) {
            strength += 0.1;
        }
        // check for digits
        if (password.matches(".*\\d.*")) {
            strength += 0.1;
        }
        // check for special characters
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            strength += 0.1;
        }
        // check for length
        strength += (length-passwordField.getMinLength()) * 0.1 / (passwordField.getMaxLength()-passwordField.getMinLength());
        return strength * 2;
    }
}
