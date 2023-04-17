package com.example.legend.service.dto;


import com.example.legend.service.enums.CarColor;
import com.example.legend.service.enums.CarCompany;
import com.example.legend.service.enums.CarFuelType;
import com.example.legend.service.enums.CarTransmissionType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "cars")
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String carName;

    @Enumerated(EnumType.STRING)
    private CarColor carColor;

    @Enumerated(EnumType.STRING)
    private CarTransmissionType carTransmissionType;

    @Enumerated(EnumType.STRING)
    private CarCompany carCompany;

    @Enumerated(EnumType.STRING)
    private CarFuelType carFuelType;

    private Integer km;

    private Integer productionYear;

    private Integer initialPrice;

    private Integer hp;

    private boolean damaged;

    private int quantityInStock;
    @OneToMany(cascade = ALL
            , mappedBy = "car")
    private List<HistoryBid> historyBidList = new ArrayList<>();

    private LocalDateTime startDateCarPostedOnline;
    private LocalDateTime deadlineCarToSell;


    public void addHistoryBid(HistoryBid historyBid) {
        historyBidList.add(historyBid);
        historyBid.setCar(this);
    }

}
