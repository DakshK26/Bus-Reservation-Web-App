package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import com.web.bus.components.CustomPasswordField;
import com.web.bus.components.PasswordStrengthBar;
import com.web.bus.components.UsernameField;
import com.web.bus.entities.Customer;
import com.web.bus.services.CustomerController;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: GUI for register, Initial user screen
 * Method List: public static void main(String[] args)
 */
@Route("register")
@PageTitle("Register")
@CssImport("./styles/views/login/login-view.css")
public class RegisterView extends Div {
    /*
    Private Instance Data
     */
    private UsernameField username;
    private CustomPasswordField password, confirmPassword;
    private Button register, loginRedirect, companyRedirect;
    private TextField name;
    private EmailField email;
    private PasswordStrengthBar passwordStrengthBar;
    @Autowired
    private CustomerController customerController;
    public RegisterView() {
        setId("login-view"); // Set element ID
        // Declare components
        name = new TextField("First and Last Name");
        email = new EmailField("Email");
        username = new UsernameField(16, 4);
        password = new CustomPasswordField("Password", 20, 4);
        confirmPassword = new CustomPasswordField("Confirm Password", 20, 4);
        passwordStrengthBar = new PasswordStrengthBar(password);
        register = new Button("Register");
        // Add components
        add(
                new H1("Register!"),
                name,
                email,
                username,
                password,
                passwordStrengthBar,
                confirmPassword,
                register = new Button("Register", event -> { // Register action event
                    String enteredName = name.getValue();
                    String enteredEmail = email.getValue();
                    String enteredUsername = username.getValue();
                    String enteredPassword = password.getValue();
                    String enteredConfirmPassword = confirmPassword.getValue();

                    // Validate the passwords match
                    if (!enteredPassword.equals(enteredConfirmPassword)) {
                        Notification.show("Passwords do not match. Please try again.", 5000, Notification.Position.TOP_CENTER);
                    }
                    // Validate the username is not already taken
                    if (this.customerController.isUsernameTaken(enteredUsername)) {
                        Notification.show("Username is already taken. Please try again.", 5000, Notification.Position.TOP_CENTER);
                    }
                    // Create a new customer object
                    Customer newCustomer = new Customer(enteredName, enteredEmail, enteredUsername, enteredPassword);
                    // Save the new customer
                    this.customerController.createCustomer(newCustomer);
                    // Show a success message
                    Notification.show("Registration successful! Please login to continue.", 5000, Notification.Position.TOP_CENTER);
                    // Redirect the user to the login route
                    UI.getCurrent().navigate("");
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