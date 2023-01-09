package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.web.bus.entities.Customer;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: GUI for register, Initial user screen
 * Method List: public static void main(String[] args)
 */
@Route("register")
@PageTitle("Register")
@CssImport("./styles/views/register/login-view.css")
public class RegisterView extends Div {

    public RegisterView() {
        setId("register-view"); // Set element ID
        // Declare components
        var username = new TextField("Username");
        var password = new PasswordField("Password");
        var confirmPassword = new PasswordField("Confirm Password");
        Button register, loginRedirect, companyRedirect;
        // Add components
        add(
                new H1("Register"),
                username,
                password,
                confirmPassword,
                register = new Button("Register", event -> { // Register action event

                }),
                loginRedirect = new Button("Login", event ->{ // Login action event
                    UI.getCurrent().navigate(""); // Send user to login route
                }
                ),
                companyRedirect = new Button("Company Sign In", event -> { // Company action event
                    UI.getCurrent().navigate("company"); // Send user to register route
                })
        );
        // Change theme of buttons
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        loginRedirect.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        companyRedirect.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_CONTRAST);
    }
}