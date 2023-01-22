package com.web.bus.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("customerMainView")
public class CustomerMainLayout extends AppLayout {

    public CustomerMainLayout() {
        createTitle();
        createDrawer();
    }

    private void createTitle() {
        H1 title = new H1("Bus Reservation Web App");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle());
        header.addClassName("header");
        header.setWidth("3%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header, title);
    }

    private void createDrawer() {
        RouterLink customerMainLink = new RouterLink("Book A Bus", CustomerHomeView.class);
        customerMainLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink customerHistoryLink = new RouterLink("View Past Purchases", CustomerHistoryView.class);
        customerHistoryLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                customerMainLink,
                customerHistoryLink
        ));
    }

}
