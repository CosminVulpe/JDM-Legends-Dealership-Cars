package com.jdm.legends.dealership.cars.service.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addressList;

    private String phoneNumber;

    private String portName;

    private Long temporaryCustomerId;

    private UUID orderReferenceId;

    private String addressIds;

    public Order(String phoneNumber, Long temporaryCustomerId, String portName) {
        this.addressList = new ArrayList<>();
        this.phoneNumber = phoneNumber;
        this.temporaryCustomerId = temporaryCustomerId;
        this.portName = portName;
        this.orderReferenceId = UUID.randomUUID();
    }

    public void addAddressToOrder(Address address, String ids) {
        this.addressList.add(address);
        address.setOrder(this);
        this.addressIds = ids;
    }
}
