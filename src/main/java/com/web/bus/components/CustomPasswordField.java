package com.web.bus.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.value.ValueChangeMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomPasswordField extends PasswordField {
    private int minLength;
    private int maxLength;
    private Button toggle;

    /*
     *  @author: Daksh & Ashwin
     *  Date: Jan. 2023
     *  Description: Customer password field
     *  Method List: public CustomPasswordField(int min, int max)
     *               private boolean isValidPassword(String password)
     *               togglePasswordVisibility()
     */
    public CustomPasswordField(String label, int maxLength, int minLength) {
        super(label);
        this.minLength = minLength;
        this.maxLength = maxLength;
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

    private boolean isValidPassword(String password) {
        // check for length
        if (password.length() < minLength || password.length() > maxLength) {
            return false;
        }
        // Check for at least one number, one lowercase letter, and one uppercase letter
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{" + minLength + "," + maxLength + "}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
