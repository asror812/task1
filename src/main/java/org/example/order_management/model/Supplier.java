package org.example.order_management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.order_management.model.base.BaseEntity;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted=false")
public class Supplier extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String phone;

    @Column(unique = true)
    private String email;
}
