package com.web.bus.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.value.ValueChangeMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 *  @author: Daksh & Ashwin
 *  Date: Jan. 2023
 *  Description: Customer password field
 *  Method List: public CustomPasswordField(int min, int max)
 *               private boolean isValidPassword(String password)
 *               togglePasswordVisibility()
 */
public class CustomPasswordField extends PasswordField {
    /*
    Private Instance Data
     */
    private int minLength;
    private int maxLength;
    public CustomPasswordField(String label, int maxLength, int minLength) {
        super(label);
        this.minLength = minLength;
        this.maxLength = maxLength;
        // Show value error constantly
        setValueChangeMode(ValueChangeMode.EAGER);
        addFocusListener(e -> setHelperText("Enter " + this.minLength + " - " + this.maxLength + " characters, at least one number, one uppercase letter, and one lowercase letter."));
        addBlurListener(e -> setHelperText(""));
        addValueChangeListener(e -> {
            if (!isValidPassword(e.getValue())) {
                setInvalid(true);
            } else {
                setInvalid(false);
            }
        });

    }

    /*
    Method to check if password meets criteria of password
     */
    public boolean isValidPassword(String password) {
        // Check for at least one number, one lowercase letter, and one uppercase letter
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{" + minLength + "," + maxLength + "}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /*
    Getters and Setters
     */

    @Override
    public int getMinLength() {
        return minLength;
    }

    @Override
    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public int getMaxLength() {
        return maxLength;
    }

    @Override
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
