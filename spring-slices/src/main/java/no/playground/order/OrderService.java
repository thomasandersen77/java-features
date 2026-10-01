package no.playground.order;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import no.playground.catalog.CatalogItem;
import no.playground.catalog.CatalogItemRepository;
import no.playground.order.OrderDtos.CreateOrderRequest;
import no.playground.order.OrderDtos.OrderLineRequest;
import no.playground.order.OrderDtos.OrderResponse;

/**
 * Application service for the order vertical slice.
 * Resolves catalog items, computes BigDecimal totals, and persists order + lines.
 */
@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CatalogItemRepository catalogItemRepository;

    public OrderService(OrderRepository orderRepository, CatalogItemRepository catalogItemRepository) {
        this.orderRepository = orderRepository;
        this.catalogItemRepository = catalogItemRepository;
    }

    public OrderResponse place(CreateOrderRequest request) {
        var lines = new ArrayList<OrderLine>();
        for (OrderLineRequest lineRequest : request.lines()) {
            var item = requireCatalogItem(lineRequest.catalogItemId());
            lines.add(new OrderLine(
                    item.getId(),
                    item.getName(),
                    lineRequest.quantity(),
                    item.getPrice()
            ));
        }

        var saved = orderRepository.save(new CustomerOrder(request.customerName(), Instant.now(), lines));
        return OrderResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public OrderResponse getById(long id) {
        return orderRepository.findById(id)
                .map(OrderResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> listAll() {
        return orderRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(OrderResponse::from)
                .toList();
    }

    private CatalogItem requireCatalogItem(Long catalogItemId) {
        return catalogItemRepository.findById(catalogItemId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Catalog item not found: " + catalogItemId));
    }
}
