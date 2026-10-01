package no.playground.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {

    // TODO: List<CustomerOrder> findByCustomerNameIgnoreCase(String customerName);

    List<CustomerOrder> findAllByOrderByCreatedAtDesc();
}
