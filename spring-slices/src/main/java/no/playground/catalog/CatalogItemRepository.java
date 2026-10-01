package no.playground.catalog;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogItemRepository extends JpaRepository<CatalogItem, Long> {

    Optional<CatalogItem> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
