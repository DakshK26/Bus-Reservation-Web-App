package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
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


        Div container = new Div();
        container.add(companyInfo, companyName, startDestination, endDestination, duration, cost);
        container.setWidth("300px");
        container.setHeight("200px");
        container.getStyle().set("border", "2px solid black");
        container.getStyle().set("text-align", "center");
        container.getStyle().set("vertical-align", "middle");
        add(container);

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