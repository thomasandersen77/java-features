package no.playground.catalog;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import no.playground.catalog.CatalogItemDtos.CatalogItemResponse;
import no.playground.catalog.CatalogItemDtos.CreateCatalogItemRequest;

@RestController
@RequestMapping("/api/catalog-items")
public class CatalogItemController {

    private final CatalogItemService service;

    public CatalogItemController(CatalogItemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CatalogItemResponse> create(@Valid @RequestBody CreateCatalogItemRequest request) {
        var created = service.create(request);
        return ResponseEntity
                .created(URI.create("/api/catalog-items/" + created.id()))
                .body(created);
    }

    @GetMapping("/{id}")
    public CatalogItemResponse getById(@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<CatalogItemResponse> listAll() {
        return service.listAll();
    }
}
