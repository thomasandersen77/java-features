package no.playground.catalog;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class CatalogItemRepositoryTest {

    @Autowired
    private CatalogItemRepository repository;

    @Test
    void savesAndFindsByNameIgnoreCase() {
        repository.save(new CatalogItem("Espresso Blend", new BigDecimal("89.50")));

        assertThat(repository.findByNameIgnoreCase("espresso blend"))
                .isPresent()
                .get()
                .extracting(CatalogItem::getName, CatalogItem::getPrice)
                .containsExactly("Espresso Blend", new BigDecimal("89.50"));
    }

    @Test
    void existsByNameIgnoreCase() {
        repository.save(new CatalogItem("Filter", new BigDecimal("12.00")));

        assertThat(repository.existsByNameIgnoreCase("filter")).isTrue();
        assertThat(repository.existsByNameIgnoreCase("missing")).isFalse();
    }
}
