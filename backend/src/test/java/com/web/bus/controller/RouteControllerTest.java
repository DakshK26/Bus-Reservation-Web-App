package com.web.bus.controller;

import com.web.bus.entity.Company;
import com.web.bus.entity.Route;
import com.web.bus.security.JwtTokenProvider;
import com.web.bus.service.BusService;
import com.web.bus.service.RouteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.bean.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RouteController.class)
class RouteControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private RouteService routeService;
    @MockBean private BusService busService;
    @MockBean private JwtTokenProvider jwtTokenProvider;

    @Test
    void searchRoutes_returnsResults() throws Exception {
        Company company = new Company("TestBus", "test@bus.com", "hash");
        company.setId(1L);

        Route route = new Route(company, "New York", "Boston", 346.0, 240, new BigDecimal("29.99"));
        route.setId(1L);

        when(routeService.searchRoutes(anyString(), anyString())).thenReturn(List.of(route));

        mockMvc.perform(get("/api/routes")
                        .param("origin", "New York")
                        .param("dest", "Boston"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].origin").value("New York"))
                .andExpect(jsonPath("$[0].destination").value("Boston"))
                .andExpect(jsonPath("$[0].basePrice").value(29.99));
    }

    @Test
    void searchRoutes_emptyResults() throws Exception {
        when(routeService.searchRoutes(anyString(), anyString())).thenReturn(List.of());

        mockMvc.perform(get("/api/routes")
                        .param("origin", "Nowhere")
                        .param("dest", "Nowhereville"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
