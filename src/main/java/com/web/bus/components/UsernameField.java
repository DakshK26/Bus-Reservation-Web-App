package com.web.bus.components;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: Customer username component that forces a min - max with only alphanumeric characters
 * Method List: public UsernameField(String caption)
 *               private boolean isValidUsername(String username)
 */
public class UsernameField extends TextField {
    /*
    Private Instance Data
     */
    private int minLength;
    private int maxLength;
    /*
    Default Constructor
     */
    public UsernameField(int maxLength, int minLength) {
        super("Username");
        // Set max and min length
        this.minLength = minLength;
        this.maxLength = maxLength;
        // Show value error constantly
        setValueChangeMode(ValueChangeMode.EAGER);
        // Set helper text
        this.addFocusListener(e-> setHelperText("Enter " + this.minLength + " - " + this.maxLength + " alphanumeric characters"));
        this.addBlurListener(e-> setHelperText(""));
        this.addValueChangeListener(e -> {
            if (!isValidUsername(e.getValue())) {
                setInvalid(true);
            } else {
                setInvalid(false);
            }
        });
    }

    /*
    Method to check if username is valid
     */
    private boolean isValidUsername(String username) {
        // Alphanumeric pattern check
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{"+minLength+","+maxLength+"}$");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}

