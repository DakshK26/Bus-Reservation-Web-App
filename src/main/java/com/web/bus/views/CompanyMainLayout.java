package com.web.bus.views;

import com.vaadin.flow.component.UI;
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

@Route("companyMainView")
public class CompanyMainLayout extends AppLayout {
    HorizontalLayout header;
    RouterLink companyMainLink, companyRoutesLink;
    private Button logout;
    public CompanyMainLayout() {
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
        logout.addClickListener(event -> {
            UI.getCurrent().getSession().close();
            UI.getCurrent().navigate("");
        });

        addToNavbar(header, title, logout);
    }

    private void createDrawer() {
        companyMainLink = new RouterLink("View All Buses", CompanyHomeView.class);
        companyMainLink.setHighlightCondition(HighlightConditions.sameLocation());
        companyRoutesLink = new RouterLink("View Company Active Routes", CompanyRoutesView.class);
        companyRoutesLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                companyMainLink,
                companyRoutesLink
        ));
    }

}
