package no.playground.catalog;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public final class CatalogItemDtos {

    private CatalogItemDtos() {
    }

    public record CreateCatalogItemRequest(
            @NotBlank @Size(max = 120) String name,
            @NotNull @DecimalMin("0.00") BigDecimal price
    ) {
    }

    public record CatalogItemResponse(Long id, String name, BigDecimal price) {
        static CatalogItemResponse from(CatalogItem item) {
            return new CatalogItemResponse(item.getId(), item.getName(), item.getPrice());
        }
    }
}
