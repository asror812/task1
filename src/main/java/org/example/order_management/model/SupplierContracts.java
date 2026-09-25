package org.example.order_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.order_management.model.base.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "supplier_contracts")
public class SupplierContracts extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(nullable = false, length = 100, unique = true)
    private String contactName;

    @Column
    private String phone;

    @Column(unique = true)
    private String email;

    @Column(name = "is_primary")
    private boolean isPrimary = false;
}
