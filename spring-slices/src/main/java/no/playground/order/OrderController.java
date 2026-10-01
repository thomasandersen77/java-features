package no.playground.order;

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
import no.playground.order.OrderDtos.CreateOrderRequest;
import no.playground.order.OrderDtos.OrderResponse;

/**
 * REST adapter for orders — same shape as {@link no.playground.catalog.CatalogItemController}.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> place(@Valid @RequestBody CreateOrderRequest request) {
        var created = service.place(request);
        return ResponseEntity
                .created(URI.create("/api/orders/" + created.id()))
                .body(created);
    }

    @GetMapping("/{id}")
    public OrderResponse getById(@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<OrderResponse> listAll() {
        return service.listAll();
    }
}
