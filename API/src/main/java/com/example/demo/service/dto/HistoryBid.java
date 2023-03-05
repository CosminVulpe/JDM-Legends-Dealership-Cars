package com.example.demo.service.dto;

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

    @ManyToMany(fetch = LAZY, cascade = PERSIST)
    @JoinTable(name = "historyBid_TemporaryUser"
            , joinColumns = {
            @JoinColumn(name = "history_bid_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "temporary_user_id", referencedColumnName = "id")
    })
    private List<TemporaryUser> temporaryUsersList = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Car car;

}
