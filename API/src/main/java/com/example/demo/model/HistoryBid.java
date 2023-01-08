package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;


@Entity(name = "HistoryBid")
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryBid {
    @Id
//    @SequenceGenerator(
//            name = "history_bid_sequence",
//            sequenceName = "history_bid_sequence",
//            allocationSize = 1
//    )
    @GeneratedValue(strategy= AUTO)
    private Integer id;
    private Integer bidValue;
    private LocalDateTime timeOfTheBid;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

}
