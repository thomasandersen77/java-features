package no.playground.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA entity for a placed order (Spring slice).
 * Named {@code CustomerOrder} to avoid clashing with pure-Java
 * {@link no.playground.features.shop.Order}.
 *
 * <p>TODO: add line items (element collection or {@code OrderLine} entity),
 * customer reference, status enum, etc.
 */
@Entity
@Table(name = "customer_order")
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String customerName;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private Instant createdAt;

    protected CustomerOrder() {
        // JPA
    }

    public CustomerOrder(String customerName, BigDecimal totalAmount, Instant createdAt) {
        this.customerName = requireCustomerName(customerName);
        this.totalAmount = requireTotal(totalAmount);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    private static String requireCustomerName(String name) {
        Objects.requireNonNull(name, "customerName");
        var trimmed = name.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("customerName must not be blank");
        }
        return trimmed;
    }

    private static BigDecimal requireTotal(BigDecimal total) {
        Objects.requireNonNull(total, "totalAmount");
        if (total.signum() < 0) {
            throw new IllegalArgumentException("totalAmount must be >= 0");
        }
        return total;
    }
}
