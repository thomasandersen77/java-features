package no.playground.order;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import no.playground.order.OrderDtos.CreateOrderRequest;
import no.playground.order.OrderDtos.OrderLineResponse;
import no.playground.order.OrderDtos.OrderResponse;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    OrderService orderService;

    @Test
    void place_returns201WithLocation() throws Exception {
        when(orderService.place(any(CreateOrderRequest.class)))
                .thenReturn(new OrderResponse(
                        1L,
                        "Ada",
                        new BigDecimal("20.00"),
                        Instant.parse("2026-01-01T00:00:00Z"),
                        List.of(new OrderLineResponse(
                                1L, "Espresso", 2, new BigDecimal("10.00"), new BigDecimal("20.00")))
                ));

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerName": "Ada",
                                  "lines": [ { "catalogItemId": 1, "quantity": 2 } ]
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/orders/1"))
                .andExpect(jsonPath("$.customerName").value("Ada"))
                .andExpect(jsonPath("$.totalAmount").value(20.00))
                .andExpect(jsonPath("$.lines[0].itemName").value("Espresso"));
    }

    @Test
    void place_rejectsEmptyLines() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerName": "Ada",
                                  "lines": []
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listAll_returnsOk() throws Exception {
        when(orderService.listAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk());
    }
}
