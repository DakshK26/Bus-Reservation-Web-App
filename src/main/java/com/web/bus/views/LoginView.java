package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import com.web.bus.components.CustomPasswordField;
import com.web.bus.components.PasswordStrengthBar;
import com.web.bus.components.UsernameField;
import com.web.bus.security.CustomerAuthenticationService;
import com.web.bus.security.CustomerPrincipal;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: GUI for login, Initial user screen
 * Method List: public static void main(String[] args)
 */
@Route("")
@PageTitle("Login")
@CssImport("./styles/views/login/login-view.css")
public class LoginView extends Div {
    /*
    Private Instance Data
     */
    private UsernameField username;
    private CustomPasswordField password;
    PasswordStrengthBar PasswordStrengthBar;
    private Button login, register, companyRedirect;
    @Autowired
    private CustomerAuthenticationService authenticationService;
    public LoginView(CustomerAuthenticationService authenticationService) {
        setId("login-view"); // Set element ID
        // Declare components
        username = new UsernameField(16, 4);
        password = new CustomPasswordField("Password", 20, 4);
        PasswordStrengthBar = new PasswordStrengthBar(password);

        // Create Login Button
        login = new Button("Login", event -> {
            // Get the entered username and password
            String enteredUsername = username.getValue();
            String enteredPassword = password.getValue();

            // Call the authentication service to verify the user's credentials
            boolean isAuthenticated = authenticationService.authenticate(enteredUsername, enteredPassword);

            if (isAuthenticated) {
                // Redirect the user to the "main" route
                UI.getCurrent().navigate("main");
            } else {
                // Show an error message
                Notification.show("Invalid username or password. Please try again.", 5000, Notification.Position.TOP_CENTER);
            }
        });
        // Add components
        add(
                new H1("Welcome!"),
                username,
                password,
                PasswordStrengthBar,
                login,
                register = new Button("Register", event ->{ // Register action event
                    UI.getCurrent().navigate("register"); // Send user to register route
                }),
                companyRedirect = new Button("Company Sign In", event -> { // Company action event
                    UI.getCurrent().navigate("company"); // Send user to register route
                })
        );
        // Change theme of buttons
        login.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        register.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        companyRedirect.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_CONTRAST);
    }
}