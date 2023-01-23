package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.web.bus.entities.Customer;
import com.web.bus.records.Bus;

import java.math.BigInteger;

@Route("customerPurchaseView")
@PageTitle("customerPurchase")
public class CustomerPurchaseView extends VerticalLayout {

    private Label company, companyName, startDestinationLbl, startDestinationName,
            endDestinationLbl, endDestinationName, durationLbl, durationName, costLbl, costName;
    private TextField name, phoneNumber, cardNum, expiration;
    private EmailField emailAddress;
    private IntegerField cvc;
    private Select<String> cardTypes;
    private Button purchase, cancel;
    private String busCost;

    private BigInteger cardNumLength = new BigInteger("1000000000000000");

    public CustomerPurchaseView() {
        // Get customer from session data
        Customer customer = (Customer) UI.getCurrent().getSession().getAttribute("customer");
        // Get bus from session data
        Bus bus = (Bus) UI.getCurrent().getSession().getAttribute("selectedBus");

        // Set bus cost
        busCost = "$18.95";

        company = new Label("Company: ");
        companyName = new Label(bus.getBusID());
        startDestinationLbl = new Label("Start Destination:");
        startDestinationName = new Label(bus.getStartDestination());
        endDestinationLbl = new Label("End Destination:");
        endDestinationName = new Label(bus.getEndDestination());
        durationLbl = new Label("Trip Duration:");
        durationName = new Label("" + bus.getTimeInMinutes());
        costLbl = new Label("Ticket Price: ");
        costName = new Label(busCost);

        name = new TextField("First & Last Name");
        name.setWidth("300px");
        name.setMinLength(1);
        name.setValue(customer.getName());
        emailAddress = new EmailField("Email Address");
        emailAddress.setValue(customer.getEmail());
        emailAddress.setWidth("300px");
        emailAddress.setMinLength(1);
        emailAddress.setPlaceholder("example@asdk.com");
        phoneNumber = new TextField("Phone Number");
        phoneNumber.setWidth("300px");
        phoneNumber.setMinLength(10);
        phoneNumber.setMaxLength(10);
        cardTypes = new Select<>();
        cardTypes.setItems("Visa", "Mastercard", "American Express");
        cardTypes.setValue("Visa");
        cardTypes.setWidth("200px");
        cardNum = new TextField("Card Number");
        cardNum.setWidth("300px");
        cardNum.setMinLength(16);
        cardNum.setMaxLength(16);
        expiration = new TextField("Card Expiration Date");
        expiration.setPlaceholder("MM/YYYY");
        expiration.setMinLength(7);
        expiration.setMaxLength(7);
        cvc = new IntegerField("CVC");
        cvc.setMin(0);
        cvc.setMax(9999);

        purchase = new Button("Purchase", event ->{ // Register action event
            String enteredName = name.getValue();
            String enteredEmail = emailAddress.getValue();
            long enteredPhoneNumber = Long.parseLong(phoneNumber.getValue());
            String enteredCardType = cardTypes.getValue();
            BigInteger enteredCardNum = new BigInteger(cardNum.getValue());
            String enteredExpiration = expiration.getValue();
            int enteredCVC = cvc.getValue();

            String[] expirations = enteredExpiration.split("/");
            int month = Integer.parseInt(expirations[0]);
            int year = Integer.parseInt(expirations[1]);


            System.out.println(enteredName);
            System.out.println(enteredEmail);
            System.out.println(enteredPhoneNumber);
            System.out.println(enteredCardType);
            System.out.println(enteredCardNum);
            System.out.println(enteredExpiration);
            System.out.println(enteredCVC);

            if (!enteredName.equalsIgnoreCase("") && !enteredEmail.equalsIgnoreCase("") && enteredPhoneNumber >= 1000000000 && enteredCardNum.compareTo(cardNumLength) >=0 && (month > 0 && month <13) && (year > 0) && (enteredCVC >= 0 && enteredCVC < 10000)) {
                UI.getCurrent().navigate("customerHomeView"); // Send user to register route
            }
            else{
                Notification.show("Please fill all fields!", 5000, Notification.Position.TOP_CENTER);
            }
        });
        cancel = new Button("Cancel Purchase", event -> { // Company action event
            UI.getCurrent().getSession().setAttribute("selectedBus", null);
            UI.getCurrent().navigate("customerHomeView"); // Send user to register route
        });

        var title = new H1("Purchase a Ticket: ");

        setHorizontalComponentAlignment(
                Alignment.CENTER,
                title,
                company,
                companyName,
                startDestinationLbl,
                startDestinationName,
                endDestinationLbl,
                endDestinationName,
                durationLbl,
                durationName,
                costLbl,
                costName,
                name,
                emailAddress,
                phoneNumber,
                cardTypes,
                cardNum,
                expiration,
                cvc,
                purchase,
                cancel);

        add (
                title,
                company,
                companyName,
                startDestinationLbl,
                startDestinationName,
                endDestinationLbl,
                endDestinationName,
                durationLbl,
                durationName,
                costLbl,
                costName,
                name,
                emailAddress,
                phoneNumber,
                cardTypes,
                cardNum,
                expiration,
                cvc,
                purchase,
                cancel
        );
        purchase.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_CONTRAST);
    }
}
