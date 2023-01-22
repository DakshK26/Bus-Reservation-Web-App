package com.web.bus.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("companyMainView")
public class CompanyMainLayout extends AppLayout {

    public CompanyMainLayout() {
        createTitle();
        createDrawer();
    }

    private void createTitle() {
        H1 title = new H1("Bus Reservation Web App");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle());
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink companyMainLink = new RouterLink("View All Busses", CompanyHomeView.class);
        companyMainLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink companyRoutesLink = new RouterLink("View Company Active Routes", CompanyRoutesView.class);
        companyRoutesLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                companyMainLink,
                companyRoutesLink
        ));
    }

}
