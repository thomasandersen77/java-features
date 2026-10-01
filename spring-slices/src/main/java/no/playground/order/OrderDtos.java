package no.playground.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public final class OrderDtos {

    private OrderDtos() {
    }

    public record CreateOrderRequest(
            @NotBlank @Size(max = 120) String customerName,
            @NotEmpty List<@Valid OrderLineRequest> lines
    ) {
    }

    public record OrderLineRequest(
            @NotNull Long catalogItemId,
            @Positive int quantity
    ) {
    }

    public record OrderLineResponse(
            Long catalogItemId,
            String itemName,
            int quantity,
            BigDecimal unitPrice,
            BigDecimal lineTotal
    ) {
        static OrderLineResponse from(OrderLine line) {
            return new OrderLineResponse(
                    line.getCatalogItemId(),
                    line.getItemName(),
                    line.getQuantity(),
                    line.getUnitPrice(),
                    line.lineTotal()
            );
        }
    }

    public record OrderResponse(
            Long id,
            String customerName,
            BigDecimal totalAmount,
            Instant createdAt,
            List<OrderLineResponse> lines
    ) {
        static OrderResponse from(CustomerOrder order) {
            return new OrderResponse(
                    order.getId(),
                    order.getCustomerName(),
                    order.getTotalAmount(),
                    order.getCreatedAt(),
                    order.getLines().stream().map(OrderLineResponse::from).toList()
            );
        }
    }
}
