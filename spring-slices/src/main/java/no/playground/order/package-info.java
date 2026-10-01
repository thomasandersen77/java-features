/**
 * Spring Boot vertical slice: customer orders.
 *
 * <p>Mirrors {@link no.playground.catalog}: REST + JPA + validation.
 * Pure-Java domain practice lives in {@code no.playground.features.shop}.
 *
 * <pre>
 * OrderController → OrderService → OrderRepository → CustomerOrder (+ OrderLine)
 *        ↑               ↑
 *     OrderDtos    CatalogItemRepository (prices / existence)
 * </pre>
 *
 * <p>REST base path: {@code /api/orders}.
 */
package no.playground.order;
