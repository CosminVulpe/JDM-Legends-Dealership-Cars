package com.jdm.legends.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;


@Entity(name = "HistoryBid")
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryBid {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer bidValue;

    private LocalDateTime timeOfTheBid;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Car car;

}
