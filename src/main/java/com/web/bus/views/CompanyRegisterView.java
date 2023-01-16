package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import com.web.bus.components.CustomPasswordField;
import com.web.bus.components.UsernameField;

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
    public CompanyRegisterView() {
        setId("login-view"); // Set element ID
        // Declare components
        companyName = new TextField("Company Name");
        companyEmail = new EmailField("Company Email");
        username = new UsernameField(16, 4);
        password = new CustomPasswordField("Password", 20, 4);
        confirmPassword = new CustomPasswordField("Confirm Password", 20, 4);
        // Add components
        add(
                new H1("Company Registration"),
                companyName,
                companyEmail,
                username,
                password,
                confirmPassword,
                register = new Button("Register", event -> { // Register action event

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