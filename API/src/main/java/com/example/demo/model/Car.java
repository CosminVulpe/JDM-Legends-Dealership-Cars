package com.example.demo.model;


import com.example.demo.model.enums.CarColor;
import com.example.demo.model.enums.CarCompany;
import com.example.demo.model.enums.CarFuelType;
import com.example.demo.model.enums.CarTransmissionType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity(name = "cars")
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @SequenceGenerator(
            name = "car_sequence",
            sequenceName = "car_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "car_sequence"
    )
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


    public void addHistoryBid(HistoryBid historyBid) {
        historyBidList.add(historyBid);
        historyBid.setCar(this);
    }

}
