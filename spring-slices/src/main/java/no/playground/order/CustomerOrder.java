package no.playground.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * JPA entity for a placed order (Spring slice).
 * Named {@code CustomerOrder} to avoid clashing with pure-Java
 * {@code no.playground.features.shop.Order}.
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

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private final List<OrderLine> lines = new ArrayList<>();

    protected CustomerOrder() {
        // JPA
    }

    public CustomerOrder(String customerName, Instant createdAt, List<OrderLine> lines) {
        this.customerName = requireCustomerName(customerName);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");
        Objects.requireNonNull(lines, "lines");
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("order must have at least one line");
        }
        lines.forEach(this::addLine);
        this.totalAmount = this.lines.stream()
                .map(OrderLine::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addLine(OrderLine line) {
        Objects.requireNonNull(line, "line");
        line.assignTo(this);
        this.lines.add(line);
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

    public List<OrderLine> getLines() {
        return Collections.unmodifiableList(lines);
    }

    private static String requireCustomerName(String name) {
        Objects.requireNonNull(name, "customerName");
        var trimmed = name.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("customerName must not be blank");
        }
        return trimmed;
    }
}
