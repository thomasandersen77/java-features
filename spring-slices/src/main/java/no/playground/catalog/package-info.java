/**
 * Spring Boot vertical slice: catalog items.
 *
 * <pre>
 * Controller → Service → Repository → Entity
 *      ↑           ↑
 *    DTOs      validation / transactions
 * </pre>
 *
 * <p>REST base path: {@code /api/catalog-items}.
 * This is the reference slice — copy the shape when implementing {@code no.playground.order}.
 */
package no.playground.catalog;
