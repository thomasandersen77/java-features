package no.playground.catalog;

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

@SpringBootTest
@AutoConfigureMockMvc
class CatalogItemIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CatalogItemRepository repository;

    @BeforeEach
    void cleanDatabase() {
        repository.deleteAll();
    }

    @Test
    void createAndFetchCatalogItemEndToEnd() throws Exception {
        mockMvc.perform(post("/api/catalog-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "House Roast",
                                  "price": 129.00
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", Matchers.matchesPattern("/api/catalog-items/\\d+")))
                .andExpect(jsonPath("$.name").value("House Roast"))
                .andExpect(jsonPath("$.price").value(129.00));

        assertThat(repository.findByNameIgnoreCase("house roast"))
                .isPresent()
                .get()
                .extracting(CatalogItem::getPrice)
                .isEqualTo(new BigDecimal("129.00"));

        var id = repository.findByNameIgnoreCase("house roast").orElseThrow().getId();

        mockMvc.perform(get("/api/catalog-items/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("House Roast"));
    }

    @Test
    void duplicateNameReturnsConflict() throws Exception {
        repository.save(new CatalogItem("House Roast", new BigDecimal("129.00")));

        mockMvc.perform(post("/api/catalog-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "house roast",
                                  "price": 99.00
                                }
                                """))
                .andExpect(status().isConflict());
    }
}
