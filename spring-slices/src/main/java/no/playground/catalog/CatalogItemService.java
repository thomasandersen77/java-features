package no.playground.catalog;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import no.playground.catalog.CatalogItemDtos.CatalogItemResponse;
import no.playground.catalog.CatalogItemDtos.CreateCatalogItemRequest;

@Service
@Transactional
public class CatalogItemService {

    private final CatalogItemRepository repository;

    public CatalogItemService(CatalogItemRepository repository) {
        this.repository = repository;
    }

    public CatalogItemResponse create(CreateCatalogItemRequest request) {
        if (repository.existsByNameIgnoreCase(request.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Catalog item already exists");
        }

        var saved = repository.save(new CatalogItem(request.name(), request.price()));
        return CatalogItemResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public CatalogItemResponse getById(long id) {
        return repository.findById(id)
                .map(CatalogItemResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Catalog item not found"));
    }

    @Transactional(readOnly = true)
    public List<CatalogItemResponse> listAll() {
        return repository.findAll().stream()
                .map(CatalogItemResponse::from)
                .toList();
    }
}
