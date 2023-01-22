package com.web.bus.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("customerMainView")
public class CustomerMainLayout extends AppLayout {

    private RouterLink customerMainLink, customerHistoryLink;
    private  HorizontalLayout header;
    private Button logout;
    public CustomerMainLayout() {
        createNavbar();
        createDrawer();
    }

    private void createNavbar() {
        H1 title = new H1("Bus Reservation Web App");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        header = new HorizontalLayout(new DrawerToggle());
        header.addClassName("header");
        header.setWidth("3%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        logout = new Button("Logout");
        logout.getStyle().set("margin-left", "20px");
        addToNavbar(header, title, logout);
    }

    private void createDrawer() {
       customerMainLink = new RouterLink("Book A Bus", CustomerHomeView.class);
        customerMainLink.setHighlightCondition(HighlightConditions.sameLocation());
        customerHistoryLink = new RouterLink("View Past Purchases", CustomerHistoryView.class);
        customerHistoryLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                customerMainLink,
                customerHistoryLink
        ));
    }

}
