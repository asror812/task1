package org.example.order_management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Customer extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String fullName;

    private String email;
    private String phone;
}
