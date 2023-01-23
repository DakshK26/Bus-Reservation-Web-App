package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.web.bus.components.CustomPasswordField;
import com.web.bus.components.UsernameField;
import com.web.bus.entities.Company;
import com.web.bus.entities.Customer;
import com.web.bus.services.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;
import java.util.Optional;


/**
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: GUI for login, Initial user screen
 * Method List:
 * public CompanyLoginView()
 * public static void main(String[] args)
 **/
@Route("company")
@PageTitle("Login")
@CssImport("./styles/views/login/login-view.css")
public class CompanyLoginView extends Div {
    /*
    Private Instance Data
     */
    private EmailField email;
    private CustomPasswordField password;
    private Button login, register, userRedirect;
    @Autowired
    private CompanyRepository authenticationService;

    public CompanyLoginView() {
        setId("login-view"); // Set element ID
        // Declare components
        email = new EmailField("Email");
        password = new CustomPasswordField("Password", 20, 4);
        // Add components
        add(
                new H1("Salutations To Your Organization!"),
                email,
                password,
                login = new Button("Login", event -> { // Login action event
                    // Call the authentication service to verify the user's credentials
                    Optional<Company> optionalCompany = authenticationService.findByEmail(email.getValue());
                    try{
                        if (optionalCompany.isEmpty()) { // If user is not found
                            throw new NoSuchElementException("Invalid email or password. Please try again.");
                        }
                    } catch (NoSuchElementException e) { // Catch exception
                        Notification.show(e.getMessage(), 5000, Notification.Position.TOP_CENTER);
                    }
                    Company Company = optionalCompany.get();

                    if (Company.getPassword().equals(password.getValue())) {  // If user is found
                        // Save customer to UI session data
                        UI.getCurrent().getSession().setAttribute("company", Company);
                        // Redirect the user to the "main" route
                        UI.getCurrent().navigate("companyHomeView"); // Send user to main route
                    } else {
                        // Show an error message
                        Notification.show("Invalid username or password. Please try again.", 5000, Notification.Position.TOP_CENTER);
                    }
                }),
                register = new Button("Register", event ->{ // Register action event
                    UI.getCurrent().navigate("companyRegister"); // Send user to register route
                }),
                userRedirect = new Button("User Sign In", event -> { // Company action event
                    UI.getCurrent().navigate(""); // Send user to register route
                })
        );
        // Change theme of buttons
        login.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        register.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        userRedirect.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_CONTRAST);
    }
}