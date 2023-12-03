package com.jdm.legends.dealership.cars.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class HistoryBid {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private BigDecimal bidValue;

    private LocalDateTime timeOfTheBid;

    @ManyToOne(cascade = MERGE, fetch = LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    private Long temporaryCustomerId;
}
