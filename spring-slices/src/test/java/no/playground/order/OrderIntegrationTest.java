package no.playground.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import no.playground.catalog.CatalogItem;
import no.playground.catalog.CatalogItemRepository;

@SpringBootTest
@AutoConfigureMockMvc
class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CatalogItemRepository catalogItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Long catalogItemId;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        catalogItemRepository.deleteAll();
        catalogItemId = catalogItemRepository
                .save(new CatalogItem("Espresso Blend", new BigDecimal("89.50")))
                .getId();
    }

    @Test
    void placeAndFetchOrderEndToEnd() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerName": "Ada Lovelace",
                                  "lines": [ { "catalogItemId": %d, "quantity": 2 } ]
                                }
                                """.formatted(catalogItemId)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", Matchers.matchesPattern("/api/orders/\\d+")))
                .andExpect(jsonPath("$.customerName").value("Ada Lovelace"))
                .andExpect(jsonPath("$.totalAmount").value(179.00))
                .andExpect(jsonPath("$.lines[0].itemName").value("Espresso Blend"))
                .andExpect(jsonPath("$.lines[0].quantity").value(2));

        assertThat(orderRepository.findAll()).hasSize(1);
        var order = orderRepository.findAll().getFirst();
        assertThat(order.getTotalAmount()).isEqualByComparingTo("179.00");
        assertThat(order.getLines()).hasSize(1);

        mockMvc.perform(get("/api/orders/{id}", order.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.lines[0].catalogItemId").value(catalogItemId));
    }

    @Test
    void place_unknownCatalogItemReturns404() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerName": "Ada",
                                  "lines": [ { "catalogItemId": 99999, "quantity": 1 } ]
                                }
                                """))
                .andExpect(status().isNotFound());
    }
}
