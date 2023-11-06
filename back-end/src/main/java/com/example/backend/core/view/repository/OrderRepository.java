package com.example.backend.core.view.repository;

import com.example.backend.core.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByIdCustomer(Long idCustomer);

    Page<Order> findByIdCustomerOrderByCreateDate(Long idCustomer, Pageable pageable);
}
