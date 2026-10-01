package no.playground.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import no.playground.order.OrderDtos.CreateOrderRequest;
import no.playground.order.OrderDtos.OrderResponse;

/**
 * Application service for the order vertical slice.
 *
 * <p>TODO checklist (mirror catalog, then extend):
 * <ol>
 *   <li>Resolve each {@code catalogItemId} via {@code CatalogItemRepository}.</li>
 *   <li>Compute total with {@link BigDecimal} (unit price × quantity).</li>
 *   <li>Persist {@link CustomerOrder} (and later line items).</li>
 *   <li>Map 404 when catalog item missing; 400 on empty/invalid lines.</li>
 * </ol>
 */
@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    // TODO: private final CatalogItemRepository catalogItemRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponse place(CreateOrderRequest request) {
        // TODO: look up catalog items, sum lines, save full order graph
        var stubTotal = BigDecimal.ZERO;
        var saved = orderRepository.save(
                new CustomerOrder(request.customerName(), stubTotal, Instant.now())
        );
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
}
