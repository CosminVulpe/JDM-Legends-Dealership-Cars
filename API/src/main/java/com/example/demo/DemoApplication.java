package com.example.demo;

import com.example.demo.model.Car;
import com.example.demo.model.enums.CarColor;
import com.example.demo.model.enums.CarCompany;
import com.example.demo.model.enums.CarFuelType;
import com.example.demo.model.enums.CarTransmissionType;
import com.example.demo.service.DAO.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static com.example.demo.model.enums.CarColor.BLACK;
import static com.example.demo.model.enums.CarCompany.NISSAN;
import static com.example.demo.model.enums.CarFuelType.GASOLINE;
import static com.example.demo.model.enums.CarTransmissionType.MANUAL_TRANSMISSION;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CarRepository carRepository) {
        return args -> {
            Car nissan = Car
                    .builder()
                    .carName("Nissan GT-R R33")
                    .carColor(BLACK)
                    .carTransmissionType(MANUAL_TRANSMISSION)
                    .carCompany(NISSAN)
                    .carFuelType(GASOLINE)
                    .km(120000)
                    .price(68980)
                    .hp(300)
                    .damaged(false)
                    .build();

            carRepository.save(nissan);
        };
    }
}
