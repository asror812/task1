package org.example.order_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.order_management.enums.Status;
import org.example.order_management.model.base.BaseEntity;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("deleted=false")
public class CustomerOrder extends BaseEntity {

    @Column(nullable = false)
    private Long orderNumber;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "delivery_city")
    private String deliveryCity;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "delivery_date")
    private Instant deliveryDate;
}
