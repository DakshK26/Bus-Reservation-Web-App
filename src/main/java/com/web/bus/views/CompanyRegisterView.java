package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import com.web.bus.components.CustomPasswordField;
import com.web.bus.components.UsernameField;
import com.web.bus.entities.Company;
import com.web.bus.entities.Customer;
import com.web.bus.services.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: GUI for register, Initial user screen
 * Method List: public static void main(String[] args)
 */
@Route("companyRegister")
@PageTitle("CompanyRegister")
@CssImport("./styles/views/login/login-view.css")
public class CompanyRegisterView extends Div {
    private UsernameField username;
    private CustomPasswordField password, confirmPassword;
    private Button register, loginRedirect, customerRedirect;
    private TextField companyName;
    private EmailField companyEmail;
    @Autowired
    private CompanyRepository companyController;
    public CompanyRegisterView() {
        setId("login-view"); // Set element ID
        // Declare components
        companyName = new TextField("Company Name");
        companyEmail = new EmailField("Company Email");
        password = new CustomPasswordField("Password", 20, 4);
        confirmPassword = new CustomPasswordField("Confirm Password", 20, 4);
        // Add components
        add(
                new H1("Company Registration"),
                companyName,
                companyEmail,
                password,
                confirmPassword,
                register = new Button("Register", event -> { // Register action event
                    String enteredName = companyName.getValue();
                    String enteredEmail = companyEmail.getValue();
                    String enteredPassword = password.getValue();
                    String enteredConfirmPassword = confirmPassword.getValue();

                    // Validate the username is not already taken
                    if(this.companyController.existsByEmail(enteredEmail)) {
                        Notification.show("Username is already taken. Please try again.", 5000, Notification.Position.TOP_CENTER);
                    }
                    // Validate the passwords match
                    else if(!enteredPassword.equals(enteredConfirmPassword)) {
                        Notification.show("Passwords do not match. Please try again.", 5000, Notification.Position.TOP_CENTER);
                    }
                    else {
                        // Create a new customer object
                        Company newCompany = new Company(enteredName, enteredPassword, enteredEmail);
                        // Save the new customer
                        companyController.save(newCompany);
                        // Show a success message
                        Notification.show("Registration successful! Please login to continue.", 5000, Notification.Position.TOP_CENTER);
                        // Redirect the user to the login route
                        UI.getCurrent().navigate("company");
                    }
                }),
                loginRedirect = new Button("Login", event ->{ // Login action event
                    UI.getCurrent().navigate("company"); // Send user to login route
                }
                ),
                customerRedirect = new Button("User Sign In", event -> { // Company action event
                    UI.getCurrent().navigate(""); // Send user to register route
                })
        );
        // Change theme of buttons
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        loginRedirect.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        customerRedirect.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_CONTRAST);
    }
}