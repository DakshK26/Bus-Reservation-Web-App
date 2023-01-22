package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("customerPurchaseView")
@PageTitle("customerPurchase")
public class customerPurchaseView extends VerticalLayout {

    private Label busIDLbl, startDestinationLbl, endDestinationLbl, durationLbl, costLbl;
    private TextField name, phoneNumber, cardNum, expiration;
    private EmailField emailAddress;
    private IntegerField cvc;
    private Select<String> cardTypes;
    private Button purchase, cancel;

    public customerPurchaseView() {
        busIDLbl = new Label("Bus ID: ");
        startDestinationLbl = new Label("Start Destination:");
        endDestinationLbl = new Label("End Destination:");
        durationLbl = new Label("Trip Duration:");
        costLbl = new Label("Ticket Price");

        name = new TextField("First & Last Name");
        name.setWidth("300px");
        emailAddress = new EmailField("Email Address");
        emailAddress.setWidth("300px");
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
        phoneNumber.setMinLength(16);
        phoneNumber.setMaxLength(16);
        expiration = new TextField("Card Expiration Date");
        expiration.setPlaceholder("MM/YYYY");
        expiration.setMinLength(7);
        expiration.setMaxLength(7);
        cvc = new IntegerField("CVC");

        purchase = new Button("Purchase", event ->{ // Register action event
            String enteredName = name.getValue();
            String enteredEmail = emailAddress.getValue();
            long enteredPhoneNumber = Long.parseLong(phoneNumber.getValue());
            String enteredCardType = cardTypes.getValue();
            long enteredCardNum = Long.parseLong(cardNum.getValue());
            String enteredExpiration = expiration.getValue();
            int enteredCVC = cvc.getValue();

            System.out.println(enteredName);
            System.out.println(enteredEmail);
            System.out.println(enteredPhoneNumber);
            System.out.println(enteredCardType);
            System.out.println(enteredCardNum);
            System.out.println(enteredExpiration);
            System.out.println(enteredCVC);



            //   UI.getCurrent().navigate(""); // Send user to register route
        });
        cancel = new Button("Cancel Purchase", event -> { // Company action event
            UI.getCurrent().navigate("customerHomeView"); // Send user to register route
        });

        setHorizontalComponentAlignment(Alignment.CENTER, busIDLbl, startDestinationLbl,  endDestinationLbl, durationLbl, costLbl,
                                        name, emailAddress, phoneNumber, cardTypes, cardNum, expiration, cvc, purchase, cancel);

        add (
            new H1("Purchase Ticket"),
                busIDLbl,
                startDestinationLbl,
                endDestinationLbl,
                durationLbl,
                costLbl,
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
