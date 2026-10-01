package no.playground.catalog;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import no.playground.catalog.CatalogItemDtos.CatalogItemResponse;
import no.playground.catalog.CatalogItemDtos.CreateCatalogItemRequest;

@WebMvcTest(controllers = CatalogItemController.class)
class CatalogItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CatalogItemService catalogItemService;

    @Test
    void createReturns201WithLocation() throws Exception {
        when(catalogItemService.create(any(CreateCatalogItemRequest.class)))
                .thenReturn(new CatalogItemResponse(1L, "Espresso Blend", new BigDecimal("89.50")));

        mockMvc.perform(post("/api/catalog-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Espresso Blend",
                                  "price": 89.50
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/catalog-items/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Espresso Blend"))
                .andExpect(jsonPath("$.price").value(89.50));
    }

    @Test
    void listAllReturnsItems() throws Exception {
        when(catalogItemService.listAll()).thenReturn(List.of(
                new CatalogItemResponse(1L, "Espresso Blend", new BigDecimal("89.50"))
        ));

        mockMvc.perform(get("/api/catalog-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Espresso Blend"));
    }

    @Test
    void createRejectsBlankName() throws Exception {
        mockMvc.perform(post("/api/catalog-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": " ",
                                  "price": 10.00
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}
