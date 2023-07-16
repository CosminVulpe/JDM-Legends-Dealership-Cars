package com.jdm.legends.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    private BigDecimal bidValue;

    private LocalDateTime timeOfTheBid;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Car car;

    @ManyToMany(mappedBy = "historyBidList")
    @JsonIgnore
    private Set<TemporaryUser> temporaryUsersList = new HashSet<>();

}
