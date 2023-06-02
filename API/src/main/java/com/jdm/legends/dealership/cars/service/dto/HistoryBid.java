package com.jdm.legends.dealership.cars.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;


@Entity(name = "HistoryBid")
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryBid {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private Integer bidValue;
    private LocalDateTime timeOfTheBid;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Car car;

}
