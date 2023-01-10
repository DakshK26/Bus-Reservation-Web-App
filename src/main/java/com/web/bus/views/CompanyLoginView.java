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
/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: GUI for login, Initial user screen
 * Method List: public static void main(String[] args)
 */
@Route("company")
@PageTitle("Login")
@CssImport("./styles/views/login/login-view.css")
public class CompanyLoginView extends Div {

    public CompanyLoginView() {
        setId("login-view"); // Set element ID
        // Declare components
        var username = new TextField("Username");
        var password = new PasswordField("Password");
        Button login, register, userRedirect;
        // Add components
        add(
                new H1("Welcome!"),
                username,
                password,
                login = new Button("Login", event -> { // Login action event

                }),
                register = new Button("Register", event ->{ // Register action event
                    UI.getCurrent().navigate("company/register"); // Send user to register route
                }),
                userRedirect = new Button("User Sign In", event -> { // Company action event
                    UI.getCurrent().navigate("company"); // Send user to register route
                })
        );
        // Change theme of buttons
        login.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        register.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        userRedirect.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_CONTRAST);
    }
}