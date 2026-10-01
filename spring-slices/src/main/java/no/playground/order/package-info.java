/**
 * Spring Boot vertical slice skeleton: customer orders.
 *
 * <p>Next exercise after {@link no.playground.catalog}: wire REST + JPA the same way.
 * Pure-Java domain practice lives in {@code no.playground.features.shop} — keep that
 * separate from these Spring types.
 *
 * <pre>
 * OrderController → OrderService → OrderRepository → CustomerOrder
 *        ↑               ↑
 *     OrderDtos     (TODO: implement)
 * </pre>
 *
 * <p>Intended REST base path: {@code /api/orders}.
 */
package no.playground.order;
