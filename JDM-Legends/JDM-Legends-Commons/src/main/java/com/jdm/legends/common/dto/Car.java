package com.jdm.legends.common.dto;

import com.jdm.legends.common.enums.CarColor;
import com.jdm.legends.common.enums.CarCompany;
import com.jdm.legends.common.enums.CarFuelType;
import com.jdm.legends.common.enums.CarTransmissionType;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;


@Entity(name = "cars")
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String carName;

    @Enumerated(STRING)
    private CarColor carColor;

    @Enumerated(STRING)
    private CarTransmissionType carTransmissionType;

    @Enumerated(STRING)
    private CarCompany carCompany;

    @Enumerated(STRING)
    private CarFuelType carFuelType;

    private int km;

    private int productionYear;

    private Integer initialPrice;

    private int hp;

    private boolean damaged;

    private int quantityInStock;

    @OneToMany(cascade = ALL, mappedBy = "car")
    private List<HistoryBid> historyBidList = new ArrayList<>();

    private LocalDateTime startDateCarPostedOnline;
    private LocalDateTime deadlineCarToSell;

    public void addHistoryBid(HistoryBid historyBid) {
        historyBidList.add(historyBid);
        historyBid.setCar(this);
    }

}
