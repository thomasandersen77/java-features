package no.playground.order;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_line")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private CustomerOrder order;

    @Column(nullable = false)
    private Long catalogItemId;

    @Column(nullable = false, length = 120)
    private String itemName;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    protected OrderLine() {
        // JPA
    }

    public OrderLine(Long catalogItemId, String itemName, int quantity, BigDecimal unitPrice) {
        this.catalogItemId = Objects.requireNonNull(catalogItemId, "catalogItemId");
        this.itemName = requireName(itemName);
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }
        this.quantity = quantity;
        this.unitPrice = requirePrice(unitPrice);
    }

    void assignTo(CustomerOrder order) {
        this.order = Objects.requireNonNull(order, "order");
    }

    public Long getId() {
        return id;
    }

    public Long getCatalogItemId() {
        return catalogItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal lineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    private static String requireName(String name) {
        Objects.requireNonNull(name, "itemName");
        var trimmed = name.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("itemName must not be blank");
        }
        return trimmed;
    }

    private static BigDecimal requirePrice(BigDecimal price) {
        Objects.requireNonNull(price, "unitPrice");
        if (price.signum() < 0) {
            throw new IllegalArgumentException("unitPrice must be >= 0");
        }
        return price;
    }
}
