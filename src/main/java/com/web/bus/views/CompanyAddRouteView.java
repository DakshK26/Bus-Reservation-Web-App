package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.web.bus.entities.Company;
import com.web.bus.records.Bus;
import com.web.bus.records.BusList;

import java.io.IOException;

/**
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: GUI for the company add route screen
 * Method List:
 * public CompanyAddRouteView()
 **/

@Route("companyAddRouteView")
@PageTitle("companyAddRoute")
public class CompanyAddRouteView extends VerticalLayout {
private TextField startDestination, endDestination;
private IntegerField numSeatsAvailable;
private Button add, cancel;

    /*
    Constructor to build the company add route view/GUI
     */
    public CompanyAddRouteView() {
        // Get company from session data
        Company company = (Company) UI.getCurrent().getSession().getAttribute("company");

        // Create input fields for required information
        startDestination = new TextField("Start Destination");
        startDestination.setWidth("250px");
        startDestination.setMinLength(1);
        endDestination = new TextField("End Destination");
        endDestination.setWidth("250px");
        endDestination.setMinLength(1);
        numSeatsAvailable = new IntegerField("Number Of Seats Available");
        numSeatsAvailable.setWidth("200px");
        numSeatsAvailable.setMin(1);


        add = new Button("Add Route", event -> {
            // Get values from text fields
            String companyNameValue = company.getName();
            String startDestinationValue = startDestination.getValue();
            String endDestinationValue = endDestination.getValue();
            int numSeatsAvailableValue = numSeatsAvailable.getValue();

            // Check if all fields are filled
            if (!companyNameValue.equalsIgnoreCase("") && !startDestinationValue.equalsIgnoreCase("") && !endDestinationValue.equalsIgnoreCase("") && numSeatsAvailable.getValue() > 0) {
                try {
                    // Create bus object and a bus list
                    Bus bus = new Bus(startDestinationValue, endDestinationValue, numSeatsAvailableValue, companyNameValue);
                    BusList temp = new BusList();
                    temp = temp.readFileMaster(); //read from the master bus list and initialize it to the temp bus list
                    temp.insert(bus); //insert the bus into the temp bus list
                    temp.writeFileMaster(temp); // Write to master file
                    //renavigate user back to the company routes view
                    UI.getCurrent().navigate("companyRoutesView");
                }
                catch (IOException e) {
                    // Notification to tell user to enter valid locations
                    Notification.show("Please Enter Valid Locations!", 5000, Notification.Position.TOP_CENTER);
                }
            }
        });
        //cancel button
        cancel = new Button("Cancel", event -> { // Company action event
            UI.getCurrent().navigate("companyRoutesView"); // Send user to register route
        });

        //center components
        setHorizontalComponentAlignment(Alignment.CENTER, startDestination, endDestination, numSeatsAvailable,
                                        add, cancel);

        add ( // Add components to layout
                new H1("Add Route"),
                startDestination,
                endDestination,
                numSeatsAvailable,
                add,
                cancel
        );

        // Add theme variants
        add.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_CONTRAST);
    }
}
