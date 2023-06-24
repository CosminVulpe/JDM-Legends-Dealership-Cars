package com.jdm.legends.dealership.cars.service.dto;


import com.jdm.legends.dealership.cars.service.enums.CarColor;
import com.jdm.legends.dealership.cars.service.enums.CarCompany;
import com.jdm.legends.dealership.cars.service.enums.CarFuelType;
import com.jdm.legends.dealership.cars.service.enums.CarTransmissionType;
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

    private int km;

    private int productionYear;

    private int initialPrice;

    private int hp;

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
