package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.web.bus.entities.Company;
import com.web.bus.records.Bus;
import com.web.bus.records.BusList;

import java.io.IOException;

@Route("companyAddRouteView")
@PageTitle("companyAddRoute")
public class CompanyAddRouteView extends VerticalLayout {
private TextField startDestination, endDestination, companyName;
private IntegerField numSeatsAvailable;
private Button add, cancel;
    public CompanyAddRouteView() {
        // Get company from session data
        Company company = (Company) UI.getCurrent().getSession().getAttribute("company");

        // Create text fields
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
            String companyNameValue = company.getName();
            String startDestinationValue = startDestination.getValue();
            String endDestinationValue = endDestination.getValue();
            int numSeatsAvailableValue = numSeatsAvailable.getValue();

            System.out.println(companyNameValue);
            System.out.println(startDestinationValue);
            System.out.println(endDestinationValue);
            System.out.println(numSeatsAvailableValue);

            if (!companyNameValue.equalsIgnoreCase("") && !startDestinationValue.equalsIgnoreCase("") && !endDestinationValue.equalsIgnoreCase("") && numSeatsAvailable.getValue() > 0) {
                try {
                    Bus bus = new Bus(startDestinationValue, endDestinationValue, numSeatsAvailableValue, companyNameValue);
                    BusList temp = new BusList();
                    temp = temp.readFileMaster();
                    temp.insert(bus);
                    temp.writeFileMaster(temp);

                   System.out.println(bus.toStringFile());
                    UI.getCurrent().navigate("companyRoutesView");
                }
                catch (IOException ignored) {

                }
            }
        });

        cancel = new Button("Cancel", event -> { // Company action event
            UI.getCurrent().navigate("companyRoutesView"); // Send user to register route
        });

        setHorizontalComponentAlignment(Alignment.CENTER, companyName, startDestination, endDestination, numSeatsAvailable,
                                        add, cancel);

        add (
                new H1("Add Route"),
                companyName,
                startDestination,
                endDestination,
                numSeatsAvailable,
                add,
                cancel
        );


        add.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_CONTRAST);
    }
}
