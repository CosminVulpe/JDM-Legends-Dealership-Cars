package com.example.demo.model;


import com.example.demo.model.enums.CarColor;
import com.example.demo.model.enums.CarCompany;
import com.example.demo.model.enums.CarFuelType;
import com.example.demo.model.enums.CarTransmissionType;
import lombok.*;

import javax.persistence.*;

@Entity(name = "Cars")
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    private Integer price;

    private Integer hp;

    private boolean damaged;

}
