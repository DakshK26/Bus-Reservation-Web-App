package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.web.bus.components.CustomPasswordField;
import com.web.bus.components.PasswordStrengthBar;
import com.web.bus.components.UsernameField;
import com.web.bus.entities.Customer;
import com.web.bus.services.CompanyRepository;
import com.web.bus.services.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: GUI for register, Initial user screen
 * Method List:
 * public RegisterView()
 * public static void main(String[] args)
 **/
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
    private CustomerRepository customerController;

    /*
     Constructor to build the Register View/page
      */
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
                    //get the values of the input fields
                    String enteredName = name.getValue();
                    String enteredEmail = email.getValue();
                    String enteredUsername = username.getValue();
                    String enteredPassword = password.getValue();
                    String enteredConfirmPassword = confirmPassword.getValue();
                    // Validate the name is not empty
                    if(enteredName.isEmpty()) {
                        Notification.show("Name cannot be empty. Please try again.", 5000, Notification.Position.TOP_CENTER);
                    }
                    // Validate the email meets condition of email
                    else if(!enteredEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                        Notification.show("Email is not valid. Please try again.", 5000, Notification.Position.TOP_CENTER);
                    }
                    // Validate the username is not already taken
                    else if(this.customerController.existsByUsername(enteredUsername)) {
                        Notification.show("Username is already taken. Please try again.", 5000, Notification.Position.TOP_CENTER);
                    }
                    // Validate the passwords match
                    else if(!enteredPassword.equals(enteredConfirmPassword)) {
                        Notification.show("Passwords do not match. Please try again.", 5000, Notification.Position.TOP_CENTER);
                    }
                    else {
                        // Create a new customer object
                        Customer newCustomer = new Customer(enteredName, enteredEmail, enteredUsername, enteredPassword);
                        // Save the new customer
                        customerController.save(newCustomer);
                        // Show a success message
                        Notification.show("Registration successful! Please login to continue.", 5000, Notification.Position.TOP_CENTER);
                        // Redirect the user to the login route
                        UI.getCurrent().navigate("");
                    }
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