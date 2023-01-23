package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.web.bus.records.Bus;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: GUI for Ticket Reciept
 * Method List:
 * public CustomerTicketView()
 */

@Route("ticketView")
@PageTitle("Ticket")
public class CustomerTicketView extends VerticalLayout {

    private Label companyInfo, companyName, startDestination, endDestination, duration, cost;
    private Button home;

    public CustomerTicketView() {
        // Get selected bus from session data
        Bus selectedBus = (Bus) UI.getCurrent().getSession().getAttribute("selectedBus");
        companyInfo = new Label("For further information about your bus, please contact " + selectedBus.getBusID());
        companyName = new Label("Company: " + selectedBus.getBusID());
        startDestination = new Label("Start Destination: " + selectedBus.getStartDestination());
        endDestination = new Label("End Destination: " + selectedBus.getEndDestination());
        duration = new Label("Trip Duration: " + selectedBus.getTimeInMinutes()/60 + " hours " + selectedBus.getTimeInMinutes() %60 + " minutes");
        cost = new Label("Ticket Price: $18.95");
        home = new Button("Home", event -> {
            UI.getCurrent().navigate("customerHomeView");
        });

        // Create container for ticket information
        Div container = new Div();
        container.add(new H1("Reciept: "), companyInfo, companyName, startDestination, endDestination, duration, cost, home);
        container.setWidth("300px");
        container.setHeight("200px");
        container.getStyle().set("text-align", "center");
        container.getStyle().set("vertical-align", "middle");
        add(container);

        // Set alignment
        setHorizontalComponentAlignment(
                Alignment.CENTER,
                companyInfo,
                companyName,
                startDestination,
                endDestination,
                duration,
                cost,
                home
        );

        // Add all components to view
        add(companyName, startDestination, endDestination, duration, cost, companyInfo, home);
    }
}