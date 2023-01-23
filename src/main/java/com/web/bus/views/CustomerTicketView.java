package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.web.bus.records.Bus;

@Route("ticketView")
@PageTitle("Ticket")
public class CustomerTicketView extends VerticalLayout {

    private Label companyInfo, companyName, startDestination, endDestination, duration, cost;

    public CustomerTicketView() {
        // Get selected bus from session data
        Bus selectedBus = (Bus) UI.getCurrent().getSession().getAttribute("selectedBus");
        companyInfo = new Label("For further information about your bus, please contact " + selectedBus.getBusID());
        companyName = new Label("Company: " + selectedBus.getBusID());
        startDestination = new Label("Start Destination: " + selectedBus.getStartDestination());
        endDestination = new Label("End Destination: " + selectedBus.getEndDestination());
        duration = new Label("Trip Duration: " + selectedBus.getTimeInMinutes()/60 + " hours " + selectedBus.getTimeInMinutes() %60 + " minutes");
        cost = new Label("Ticket Price: $18.95");

        setHorizontalComponentAlignment(
                Alignment.CENTER,
                companyInfo,
                companyName,
                startDestination,
                endDestination,
                duration,
                cost
        );

        add(companyInfo, companyName, startDestination, endDestination, duration, cost);
    }
}